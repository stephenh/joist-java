package joist.codegen.passes;

import java.util.ArrayList;
import java.util.List;

import joist.codegen.Codegen;
import joist.codegen.dtos.Entity;
import joist.codegen.dtos.ManyToOneProperty;
import joist.codegen.dtos.PrimitiveProperty;
import joist.domain.DomainObject;
import joist.domain.orm.AliasRegistry;
import joist.domain.orm.queries.Alias;
import joist.domain.orm.queries.JoinClause;
import joist.domain.orm.queries.columns.AliasColumn;
import joist.domain.orm.queries.columns.CodeAliasColumn;
import joist.domain.orm.queries.columns.ForeignKeyAliasColumn;
import joist.domain.orm.queries.columns.IdAliasColumn;
import joist.domain.orm.queries.columns.LongAliasColumn;
import joist.sourcegen.Argument;
import joist.sourcegen.GClass;
import joist.sourcegen.GField;
import joist.sourcegen.GMethod;
import joist.util.Interpolate;
import joist.util.TopologicalSort;
import joist.util.TopologicalSort.CycleException;

public class GenerateAliasesPass implements Pass<Codegen> {

  public void pass(Codegen codegen) {
    GClass aliasesClass = codegen.getOutputCodegenDirectory().getClass(codegen.getConfig().getDomainObjectPackage() + ".Aliases");
    aliasesClass.addImports(AliasRegistry.class);

    List<Entity> sorted;
    if (codegen.getConfig().db.isMySQL()) {
      sorted = this.getEntitiesSortedByForeignKeys(codegen);
    } else {
      sorted = new ArrayList<Entity>(); // pg doesn't need fk order
    }

    for (Entity entity : codegen.getSchema().getEntities().values()) {
      if (entity.isCodeEntity()) {
        continue;
      }

      GClass aliasClass = codegen.getOutputCodegenDirectory().getClass(entity.getFullAliasClassName());
      aliasClass.baseClassName("Alias<{}>", entity.getClassName());
      aliasClass.addImports(Alias.class);

      this.addColumnsFieldAndGetter(aliasClass, entity);
      this.addIdAndVersionAndSubClassIdMethods(aliasClass, entity);
      this.addConstructors(aliasClass, entity);

      this.addPrimitiveColumns(aliasClass, entity);
      this.addManyToOneColumns(codegen, aliasClass, entity);
      this.addInheritedColumns(aliasClass, entity);

      this.addOrderMethod(aliasClass, sorted.indexOf(entity));

      this.addAliasesField(aliasesClass, entity);
    }
  }

  private void addInheritedColumns(GClass aliasClass, Entity entity) {
    boolean first = true;
    Entity baseEntity = entity.getBaseEntity();
    while (baseEntity != null) {
      if (first) {
        GField baseAliasField = aliasClass.getField("baseAlias").setFinal();
        baseAliasField.type(baseEntity.getFullAliasClassName());

        GMethod baseClassAliasGetter = aliasClass.getMethod("getBaseClassAlias");
        baseClassAliasGetter.returnType("Alias<{}>", baseEntity.getClassName());
        baseClassAliasGetter.body.line("return this.baseAlias;");
        first = false;
      }

      for (PrimitiveProperty p : baseEntity.getPrimitiveProperties()) {
        GField field = aliasClass.getField(p.getVariableName()).setPublic().setFinal();
        field.type("{}<{}>", p.getAliasColumnClassName(), baseEntity.getClassName());
        this.appendToConstructors(aliasClass, "this.{} = this.baseAlias.{};", p.getVariableName(), p.getVariableName());
      }

      for (ManyToOneProperty p : baseEntity.getManyToOneProperties()) {
        Class<?> aliasColumnClass = (p.getOneSide().isCodeEntity()) ? CodeAliasColumn.class : ForeignKeyAliasColumn.class;
        aliasClass.addImports(aliasColumnClass);
        aliasClass.addImports(p.getOneSide().getFullClassName());

        GField field = aliasClass.getField(p.getVariableName()).setPublic().setFinal();
        field.type("{}<{}, {}>", aliasColumnClass.getSimpleName(), p.getManySide().getClassName(), p.getJavaType());
        this.appendToConstructors(aliasClass, "this.{} = this.baseAlias.{};", p.getVariableName(), p.getVariableName());
      }

      aliasClass.addImports(entity.getConfig().getDomainObjectPackage() + "." + baseEntity.getClassName());
      baseEntity = baseEntity.getBaseEntity();
    }
  }

  private void addColumnsFieldAndGetter(GClass aliasClass, Entity entity) {
    GField columns = aliasClass.getField("columns").setFinal();
    columns.type("List<AliasColumn<{}, ?, ?>>", entity.getClassName());
    columns.initialValue("new ArrayList<AliasColumn<{}, ?, ?>>()", entity.getClassName());

    GMethod columnsGetter = aliasClass.getMethod("getColumns");
    columnsGetter.returnType("List<AliasColumn<{}, ?, ?>>", entity.getClassName());
    columnsGetter.body.line("return this.columns;");
  }

  private void addIdAndVersionAndSubClassIdMethods(GClass aliasClass, Entity entity) {
    Entity rootEntity = entity.getRootEntity();
    GMethod idColumnMethod = aliasClass.getMethod("getIdColumn");
    idColumnMethod.returnType("IdAliasColumn<{}>", rootEntity.getClassName());
    idColumnMethod.body.line("return this.id;");

    GMethod versionColumnMethod = aliasClass.getMethod("getVersionColumn");
    versionColumnMethod.returnType("LongAliasColumn<{}>", rootEntity.getClassName());
    versionColumnMethod.body.line("return this.version;");

    if (entity.getBaseEntity() == null) {
      GMethod subclassIdColumnMethod = aliasClass.getMethod("getSubClassIdColumn");
      subclassIdColumnMethod.returnType("IdAliasColumn<{}>", entity.getClassName());
      subclassIdColumnMethod.body.line("return null;");
    } else {
      GMethod subclassIdColumnMethod = aliasClass.getMethod("getSubClassIdColumn");
      subclassIdColumnMethod.returnType("IdAliasColumn<{}>", entity.getClassName());
      subclassIdColumnMethod.body.line("return this.subClassId;");

      GField subClassIdField = aliasClass.getField("subClassId").setFinal();
      subClassIdField.type("IdAliasColumn<{}>", entity.getClassName());
      subClassIdField.initialValue("new IdAliasColumn<{}>(this, \"id\", null)", entity.getClassName());
    }
  }

  private void addConstructors(GClass aliasClass, Entity entity) {
    GMethod cstr0 = aliasClass.getConstructor();
    cstr0.body.line("this(\"{}\", null, true);", entity.getAliasAlias());

    GMethod cstr1 = aliasClass.getConstructor("String alias");
    cstr1.body.line("this(alias, null, true);");

    GMethod cstr2;
    if (entity.getBaseEntity() != null) {
      cstr2 = aliasClass.getConstructor("String alias", entity.getBaseEntity().getAliasName() + " baseAlias", "boolean addSubClasses");
    } else {
      cstr2 = aliasClass.getConstructor("String alias", "Object noopBaseAlias", "boolean addSubClasses");
    }
    cstr2.body.line("super({}.class, \"{}\", alias);", entity.getClassName(), entity.getTableName());

    // If any sub-classes, we want to know about their sub-aliases to instantiate during SELECTs
    if (entity.getBaseEntity() != null) {
      cstr2.body.line("this.baseAlias = (baseAlias != null) ? baseAlias : new {}Alias(alias + \"_b\", null, false);",//
        entity.getBaseEntity().getClassName());
    }
    if (entity.getSubEntities().size() > 0) {
      cstr2.body.line("{} {} = this;", entity.getAliasName(), entity.getVariableName());
      cstr2.body.line("if (addSubClasses) {");
      int i = 0;
      for (Entity subEntity : entity.getSubEntitiesRecursively()) {
        cstr2.body.line("  {} {} = new {}(alias + \"_{}\", {}, false);",//
          subEntity.getAliasName(),
          subEntity.getVariableName(),
          subEntity.getAliasName(),
          i++,
          subEntity.getBaseEntity().getVariableName());
        cstr2.body.line("  this.addSubClassAlias({});", subEntity.getVariableName());
        aliasClass.addImports(subEntity.getFullAliasClassName());
      }
      cstr2.body.line("}");
    }

    aliasClass.addImports(ArrayList.class, List.class, Alias.class, AliasColumn.class, IdAliasColumn.class, LongAliasColumn.class);
    aliasClass.addImports(entity.getFullClassName());
  }

  private void addPrimitiveColumns(GClass aliasClass, Entity entity) {
    for (PrimitiveProperty p : entity.getPrimitiveProperties()) {
      GField field = aliasClass.getField(p.getVariableName()).setPublic().setFinal();
      field.type("{}<{}>", p.getAliasColumnClassName(), entity.getClassName());
      field.initialValue("new {}(this, \"{}\", {}.Shims.{})",//
        field.getTypeClassName(),
        p.getColumnName(),
        entity.getCodegenClassName(),
        p.getVariableName());
      this.appendToConstructors(aliasClass, "this.columns.add(this.{});", p.getVariableName());
    }
  }

  private void addManyToOneColumns(Codegen codegen, GClass aliasClass, Entity entity) {
    for (ManyToOneProperty p : entity.getManyToOneProperties()) {
      Class<?> aliasColumnClass = (p.getOneSide().isCodeEntity()) ? CodeAliasColumn.class : ForeignKeyAliasColumn.class;
      aliasClass.addImports(aliasColumnClass);
      aliasClass.addImports(p.getOneSide().getFullClassName());

      GField field = aliasClass.getField(p.getVariableName()).setPublic().setFinal();
      field.type("{}<{}, {}>", aliasColumnClass.getSimpleName(), entity.getClassName(), p.getJavaType());
      field.initialValue("new {}<{}, {}>(this, \"{}\", {}.Shims.{}Id)",//
        aliasColumnClass.getSimpleName(),
        entity.getClassName(),
        p.getJavaType(),
        p.getColumnName(),
        entity.getCodegenClassName(),
        p.getVariableName());

      if (!p.getOneSide().isCodeEntity()) {
        GClass otherAliasClass = codegen.getOutputCodegenDirectory().getClass(p.getOneSide().getFullAliasClassName());
        GMethod on = otherAliasClass.getMethod("on", Argument.arg("ForeignKeyAliasColumn<T, " + p.getJavaType() + ">", "on"));
        if (on.body.toString().isEmpty()) {
          on.typeParameters("T extends DomainObject");
          on.returnType("JoinClause<T, {}>", p.getJavaType());
          on.body.line("return new JoinClause<T, {}>(\"INNER JOIN\", this, on);", p.getJavaType());
          otherAliasClass.addImports(ForeignKeyAliasColumn.class, JoinClause.class, DomainObject.class);
        }
        GMethod leftOn = otherAliasClass.getMethod("leftOn", Argument.arg("ForeignKeyAliasColumn<T, " + p.getJavaType() + ">", "on"));
        if (leftOn.body.toString().isEmpty()) {
          leftOn.typeParameters("T extends DomainObject");
          leftOn.returnType("JoinClause<T, {}>", p.getJavaType());
          leftOn.body.line("return new JoinClause<T, {}>(\"LEFT OUTER JOIN\", this, on);", p.getJavaType());
          otherAliasClass.addImports(ForeignKeyAliasColumn.class, JoinClause.class, DomainObject.class);
        }
      }

      this.appendToConstructors(aliasClass, "this.columns.add(this.{});", p.getVariableName());
    }
  }

  private void appendToConstructors(GClass aliasClass, String line, Object... args) {
    line = Interpolate.string(line, args);
    for (GMethod constructor : aliasClass.getConstructors()) {
      if (!constructor.hasSameArguments("String alias") && constructor.hasArguments()) {
        constructor.body.line(line);
      }
    }
  }

  private List<Entity> getEntitiesSortedByForeignKeys(Codegen codegen) {
    TopologicalSort<Entity> ts = new TopologicalSort<Entity>();
    for (Entity entity : codegen.getSchema().getEntities().values()) {
      if (!entity.isCodeEntity()) {
        ts.addNode(entity);
      }
    }
    try {
      for (Entity entity : codegen.getSchema().getEntities().values()) {
        // subclasses must come after their base class
        if (entity.isSubclass()) {
          ts.addDependency(entity, entity.getBaseEntity());
        }
        // not-null foreign keys must come before their target table
        for (ManyToOneProperty mtop : entity.getManyToOneProperties()) {
          if (mtop.isNotNull() && !mtop.getOneSide().isCodeEntity()) {
            // make sure our subclasses are added as dependencies as well
            for (Entity current : mtop.getOneSide().getAllBaseAndSubEntities()) {
              ts.addDependency(entity, current);
            }
          }
        }
      }
    } catch (CycleException ce) {
      String message = ce.getMessage()
        + ". Note: due to MySQL lacking deferred foreign key constraints,"
        + " Joist determines entity INSERT order at buildtime. For this to work, you cannot have any"
        + " foreign key cycles in your schema. I.e. make one of the columns nullable. Or use PostgreSQL.";
      throw new RuntimeException(message);
    }
    for (Entity entity : codegen.getSchema().getEntities().values()) {
      for (ManyToOneProperty mtop : entity.getManyToOneProperties()) {
        if (!mtop.isNotNull() && !mtop.getOneSide().isCodeEntity()) {
          ts.addDependencyIfNoCycle(entity, mtop.getOneSide());
        }
      }
    }
    return ts.get();
  }

  private void addOrderMethod(GClass aliasClass, int index) {
    GMethod order = aliasClass.getMethod("getOrder").returnType(int.class);
    order.body.line("return {};", index);
  }

  private void addAliasesField(GClass aliasesClass, Entity entity) {
    aliasesClass.getField(entity.getVariableName()).type(entity.getAliasName()).setStatic();

    GMethod method = aliasesClass.getMethod(entity.getVariableName()).returnType(entity.getAliasName()).setStatic();
    method.body.line("if ({} == null) {", entity.getVariableName());
    method.body.line("_   {} = new {}();", entity.getVariableName(), entity.getAliasName());
    method.body.line("_   AliasRegistry.register({}.class, {});", entity.getClassName(), entity.getVariableName());
    method.body.line("}");
    method.body.line("return {};", entity.getVariableName());
  }

}
