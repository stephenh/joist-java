package org.exigencecorp.domainobjects.codegen.passes;

import java.util.ArrayList;
import java.util.List;

import org.exigencecorp.domainobjects.DomainObject;
import org.exigencecorp.domainobjects.codegen.Codegen;
import org.exigencecorp.domainobjects.codegen.dtos.Entity;
import org.exigencecorp.domainobjects.codegen.dtos.ManyToOneProperty;
import org.exigencecorp.domainobjects.codegen.dtos.PrimitiveProperty;
import org.exigencecorp.domainobjects.queries.Alias;
import org.exigencecorp.domainobjects.queries.JoinClause;
import org.exigencecorp.domainobjects.queries.columns.AliasColumn;
import org.exigencecorp.domainobjects.queries.columns.CodeAliasColumn;
import org.exigencecorp.domainobjects.queries.columns.ForeignKeyAliasColumn;
import org.exigencecorp.domainobjects.queries.columns.IdAliasColumn;
import org.exigencecorp.domainobjects.queries.columns.IntAliasColumn;
import org.exigencecorp.gen.GClass;
import org.exigencecorp.gen.GField;
import org.exigencecorp.gen.GMethod;
import org.exigencecorp.util.ToString;

public class GenerateAliasesPass implements Pass {

    public void pass(Codegen codegen) {
        for (Entity entity : codegen.getEntities().values()) {
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
                Class<?> aliasColumnClass = p.getAliasColumnClass();
                aliasClass.addImports(aliasColumnClass);

                GField field = aliasClass.getField(p.getVariableName()).setPublic().setFinal();
                field.type("{}<{}>", aliasColumnClass.getSimpleName(), baseEntity.getClassName());

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
        versionColumnMethod.returnType("IntAliasColumn<{}>", rootEntity.getClassName());
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
        GMethod constructor = aliasClass.getConstructor("String alias");
        constructor.body.line("super({}.class, {}.class, \"{}\", alias);",//
            entity.getClassName(),
            this.getBaseOrCurrentClassName(entity),
            entity.getTableName());

        // If any sub-classes, we want to know about their sub-aliases to instantiate during SELECTs
        int i = 0;
        for (Entity subEntity : entity.getSubEntities()) {
            constructor.body.line("this.addSubClassAlias(new {}Alias(this, alias + \"_{}\"));", subEntity.getClassName(), i++);
            aliasClass.addImports(subEntity.getFullAliasClassName());
        }

        // If a base class, we'll need another constructor for the bootstrap call we added above
        if (entity.getBaseEntity() != null) {
            Entity baseEntity = entity.getBaseEntity();
            constructor.body.line("this.baseAlias = new {}Alias(alias + \"_b\");", baseEntity.getClassName());

            GMethod otherConstructor = aliasClass.getConstructor(baseEntity.getClassName() + "Alias baseAlias", "String alias");
            otherConstructor.body.line("super({}.class, {}.class, \"{}\", alias);",//
                entity.getClassName(),
                baseEntity.getClassName(),
                entity.getTableName());
            otherConstructor.body.line("this.baseAlias = baseAlias;");
        }

        aliasClass.addImports(ArrayList.class, List.class, Alias.class, AliasColumn.class, IdAliasColumn.class, IntAliasColumn.class);
        aliasClass.addImports(entity.getFullClassName());
    }

    private String getBaseOrCurrentClassName(Entity entity) {
        if (entity.getBaseEntity() != null) {
            return entity.getBaseEntity().getClassName();
        } else {
            return entity.getClassName();
        }
    }

    private void addPrimitiveColumns(GClass aliasClass, Entity entity) {
        for (PrimitiveProperty p : entity.getPrimitiveProperties()) {
            Class<?> aliasColumnClass = p.getAliasColumnClass();
            aliasClass.addImports(aliasColumnClass);

            GField field = aliasClass.getField(p.getVariableName()).setPublic().setFinal();
            field.type("{}<{}>", aliasColumnClass.getSimpleName(), entity.getClassName());
            field.initialValue("new {}<{}>(this, \"{}\", {}.Shims.{})",//
                aliasColumnClass.getSimpleName(),
                entity.getClassName(),
                p.getColumnName(),
                entity.getCodegenClassName(),
                p.getVariableName());

            this.appendToConstructors(aliasClass, "this.columns.add(this.{});", p.getVariableName());
        }

        aliasClass.addImports(entity.getFullCodegenClassName());
    }

    private void addManyToOneColumns(Codegen codegen, GClass aliasClass, Entity entity) {
        for (ManyToOneProperty p : entity.getManyToOneProperties()) {
            Class<?> aliasColumnClass = (p.getManySide().isCodeEntity()) ? CodeAliasColumn.class : ForeignKeyAliasColumn.class;
            aliasClass.addImports(aliasColumnClass);
            aliasClass.addImports(p.getManySide().getFullClassName());

            GField field = aliasClass.getField(p.getVariableName()).setPublic().setFinal();
            field.type("{}<{}, {}>", aliasColumnClass.getSimpleName(), entity.getClassName(), p.getJavaType());
            field.initialValue("new {}<{}, {}>(this, \"{}\", {}.Shims.{}Id)",//
                aliasColumnClass.getSimpleName(),
                entity.getClassName(),
                p.getJavaType(),
                p.getColumnName(),
                entity.getCodegenClassName(),
                p.getVariableName());

            if (!p.getManySide().isCodeEntity()) {
                GClass otherAliasClass = codegen.getOutputCodegenDirectory().getClass(p.getManySide().getFullAliasClassName());
                if (!otherAliasClass.hasMethod("on")) {
                    GMethod on = otherAliasClass.getMethod("on");
                    on.argument("ForeignKeyAliasColumn<? extends DomainObject, " + p.getJavaType() + ">", "on");
                    on.returnType("JoinClause");
                    on.body.line("return new JoinClause(\"INNER JOIN\", this, on);");
                    otherAliasClass.addImports(ForeignKeyAliasColumn.class, JoinClause.class, DomainObject.class);
                }
            }

            this.appendToConstructors(aliasClass, "this.columns.add(this.{});", p.getVariableName());
        }
    }

    private void appendToConstructors(GClass aliasClass, String line, Object... args) {
        line = ToString.interpolate(line, args);
        for (GMethod constructor : aliasClass.getConstructors()) {
            constructor.body.line(line);
        }
    }

}
