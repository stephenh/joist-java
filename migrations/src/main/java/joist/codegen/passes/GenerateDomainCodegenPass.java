package joist.codegen.passes;

import java.util.ArrayList;
import java.util.List;

import joist.codegen.Codegen;
import joist.codegen.dtos.CodeEntity;
import joist.codegen.dtos.CodeValue;
import joist.codegen.dtos.Entity;
import joist.codegen.dtos.ManyToManyProperty;
import joist.codegen.dtos.ManyToOneProperty;
import joist.codegen.dtos.OneToManyProperty;
import joist.codegen.dtos.PrimitiveProperty;
import joist.domain.AbstractChanged;
import joist.domain.Changed;
import joist.domain.Shim;
import joist.domain.orm.ForeignKeyCodeHolder;
import joist.domain.orm.ForeignKeyHolder;
import joist.domain.orm.ForeignKeyListHolder;
import joist.domain.uow.UoW;
import joist.domain.validation.rules.MaxLength;
import joist.domain.validation.rules.NotNull;
import joist.sourcegen.Argument;
import joist.sourcegen.GClass;
import joist.sourcegen.GField;
import joist.sourcegen.GMethod;
import joist.util.Copy;
import joist.util.ListDiff;

public class GenerateDomainCodegenPass implements Pass {

  public void pass(Codegen codegen) {
    for (Entity entity : codegen.getEntities().values()) {
      if (entity.isCodeEntity()) {
        continue;
      }

      GClass domainCodegen = codegen.getOutputCodegenDirectory().getClass(entity.getFullCodegenClassName());
      domainCodegen.setAbstract();
      domainCodegen.baseClassName(entity.getParentClassName());
      domainCodegen.addAnnotation("@SuppressWarnings(\"all\")");

      domainCodegen.getConstructor().setProtected().body.line("this.addExtraRules();");
      domainCodegen.getMethod("addExtraRules").setPrivate();

      this.addQueries(domainCodegen, entity);
      this.primitiveProperties(domainCodegen, entity);
      this.manyToOneProperties(domainCodegen, entity);
      this.oneToManyProperties(domainCodegen, entity);
      this.manyToManyProperties(domainCodegen, entity);
      this.changed(domainCodegen, entity);
      this.clearAssociations(domainCodegen, entity);
    }
  }

  private void addQueries(GClass domainCodegen, Entity entity) {
    if (!entity.isCodeEntity()) {
      GField query = domainCodegen.getField("queries").setPublic().setStatic().setFinal();
      query.type(entity.getFullQueriesClassName());
      domainCodegen.staticInitializer.line("Aliases.{}();", entity.getVariableName());
      domainCodegen.staticInitializer.line("queries = new {}Queries();", entity.getClassName());
    }
  }

  private void primitiveProperties(GClass domainCodegen, Entity entity) {
    for (PrimitiveProperty p : entity.getPrimitiveProperties()) {
      GField field = domainCodegen.getField(p.getVariableName());
      field.type(p.getJavaType());
      field.initialValue(p.getDefaultJavaString());
      field.makeGetter();

      if (!"version".equals(p.getColumnName())) {
        GMethod setter = domainCodegen.getMethod("set" + p.getCapitalVariableName());
        setter.argument(p.getJavaType(), p.getVariableName());
        setter.body.line("this.getChanged().record(\"{}\", this.{}, {});", p.getVariableName(), p.getVariableName(), p.getVariableName());
        setter.body.line("this.{} = {};", p.getVariableName(), p.getVariableName());
        if ("id".equals(p.getColumnName())) {
          setter.body.line("if (UoW.isOpen()) {");
          setter.body.line("_   UoW.getIdentityMap().store(this);");
          setter.body.line("}");
          domainCodegen.addImports(UoW.class);
        }

        if (!"id".equals(p.getColumnName())) {
          GMethod defaultSetter = domainCodegen.getMethod("default{}", p.getCapitalVariableName()).setProtected();
          defaultSetter.argument(p.getJavaType(), p.getVariableName());
          defaultSetter.body.line("this.{} = {};", p.getVariableName(), p.getVariableName());
        }
      }

      GClass shims = domainCodegen.getInnerClass("Shims").setPackagePrivate();
      GField shimField = shims.getField(p.getVariableName()).setProtected().setStatic().setFinal();
      shimField.type("Shim<" + entity.getClassName() + ", " + p.getJavaType() + ">");
      GClass shimClass = shimField.initialAnonymousClass();

      GMethod shimSetter = shimClass.getMethod("set");
      shimSetter.argument(entity.getClassName(), "instance").argument(p.getJavaType(), p.getVariableName());
      shimSetter.body.line("(({}) instance).{} = {};", entity.getCodegenClassName(), p.getVariableName(), p.getVariableName());

      GMethod shimGetter = shimClass.getMethod("get");
      shimGetter.argument(entity.getClassName(), "instance");
      shimGetter.returnType(p.getJavaType());
      shimGetter.body.line("return (({}) instance).{};", entity.getCodegenClassName(), p.getVariableName());

      GMethod shimName = shimClass.getMethod("getName").returnType(String.class);
      shimName.body.line("return \"{}\";", p.getVariableName());

      if (p.shouldHaveNotNullRule()) {
        domainCodegen.getMethod("addExtraRules").body.line("this.addRule(new NotNull<{}>(Shims.{}));",//
          entity.getClassName(),
          p.getVariableName());
        domainCodegen.addImports(NotNull.class);
      }

      if (p.getMaxCharacterLength() != 0) {
        domainCodegen.getMethod("addExtraRules").body.line("this.addRule(new MaxLength<{}>(Shims.{}, {}));",//
          entity.getClassName(),
          p.getVariableName(),
          p.getMaxCharacterLength());
        domainCodegen.addImports(MaxLength.class);
      }

      domainCodegen.addImports(Shim.class);
    }
  }

  private void manyToOneProperties(GClass domainCodegen, Entity entity) {
    for (ManyToOneProperty mtop : entity.getManyToOneProperties()) {
      GField field = domainCodegen.getField(mtop.getVariableName()).setFinal();
      if (mtop.getOneSide().isCodeEntity()) {
        field.type("ForeignKeyCodeHolder<{}>", mtop.getJavaType());
        field.initialValue("new ForeignKeyCodeHolder<{}>({}.class)", mtop.getJavaType(), mtop.getJavaType());
        domainCodegen.addImports(ForeignKeyCodeHolder.class);
      } else {
        field.type("ForeignKeyHolder<{}, {}>", entity.getClassName(), mtop.getJavaType());
        field.initialValue(
          "new ForeignKeyHolder<{}, {}>({}.class, {}.class, Aliases.{}(), Aliases.{}().{})",
          entity.getClassName(),
          mtop.getJavaType(),
          entity.getClassName(),
          mtop.getJavaType(),
          mtop.getOneSide().getVariableName(),
          entity.getVariableName(),
          mtop.getVariableName());
        domainCodegen.addImports(ForeignKeyHolder.class);
      }

      GMethod getter = domainCodegen.getMethod("get" + mtop.getCapitalVariableName());
      getter.returnType(mtop.getJavaType());
      getter.body.line("return this.{}.get();", mtop.getVariableName());

      GMethod setter = domainCodegen.getMethod("set{}", mtop.getCapitalVariableName());
      setter.argument(mtop.getJavaType(), mtop.getVariableName());
      if (!mtop.getOneSide().isCodeEntity() && !mtop.getOneToManyProperty().isCollectionSkipped()) {
        setter.body.line("if (this.{}.get() != null) {", mtop.getVariableName());
        setter.body.line("   this.{}.get().remove{}WithoutPercolation(({}) this);",//
          mtop.getVariableName(),
          mtop.getOneToManyProperty().getCapitalVariableNameSingular(),
          entity.getClassName());
        setter.body.line("}");
        if (mtop.getOneToManyProperty().isOneToOne()) {
          setter.body.line("{}.set{}(null);", mtop.getVariableName(), mtop.getOneToManyProperty().getCapitalVariableNameSingular());
        }
      }
      setter.body.line("this.set{}WithoutPercolation({});", mtop.getCapitalVariableName(), mtop.getVariableName());
      if (!mtop.getOneSide().isCodeEntity() && !mtop.getOneToManyProperty().isCollectionSkipped()) {
        setter.body.line("if (this.{}.get() != null) {", mtop.getVariableName());
        setter.body.line("   this.{}.get().add{}WithoutPercolation(({}) this);",//
          mtop.getVariableName(),
          mtop.getOneToManyProperty().getCapitalVariableNameSingular(),
          entity.getClassName());
        setter.body.line("}");
      }

      GMethod setter2 = domainCodegen.getMethod("set{}WithoutPercolation", mtop.getCapitalVariableName()).setProtected();
      setter2.argument(mtop.getJavaType(), mtop.getVariableName());
      setter2.body.line("this.getChanged().record(\"{}\", this.{}, {});", mtop.getVariableName(), mtop.getVariableName(), mtop.getVariableName());
      setter2.body.line("this.{}.set({});", mtop.getVariableName(), mtop.getVariableName());

      if (mtop.getOneSide().isCodeEntity()) {
        GMethod defaultSetter = domainCodegen.getMethod("default{}", mtop.getCapitalVariableName()).setProtected();
        defaultSetter.argument(mtop.getJavaType(), mtop.getVariableName());
        defaultSetter.body.line("this.{}.set({});", mtop.getVariableName(), mtop.getVariableName());
      }

      GClass shims = domainCodegen.getInnerClass("Shims");
      GField shimField = shims.getField(mtop.getVariableName() + "Id").setProtected().setStatic().setFinal();
      shimField.type("Shim<" + entity.getClassName() + ", Long>");
      GClass shimClass = shimField.initialAnonymousClass();

      GMethod shimSetter = shimClass.getMethod("set");
      shimSetter.argument(entity.getClassName(), "instance").argument("Long", mtop.getVariableName() + "Id");
      shimSetter.body.line("(({}) instance).{}.setId({}Id);", entity.getCodegenClassName(), mtop.getVariableName(), mtop.getVariableName());

      GMethod shimGetter = shimClass.getMethod("get");
      shimGetter.argument(entity.getClassName(), "instance");
      shimGetter.returnType("Long");
      shimGetter.body.line(0, "return (({}) instance).{}.getId();", entity.getCodegenClassName(), mtop.getVariableName());

      GMethod shimName = shimClass.getMethod("getName").returnType(String.class);
      shimName.body.line("return \"{}\";", mtop.getVariableName());

      if (mtop.isNotNull()) {
        domainCodegen.getMethod("addExtraRules").body.line("this.addRule(new NotNull<{}>(Shims.{}Id));",//
          entity.getClassName(),
          mtop.getVariableName());
        domainCodegen.addImports(NotNull.class);
      }

      if (mtop.getOneSide().isCodeEntity()) {
        CodeEntity c = (CodeEntity) mtop.getOneSide();
        for (CodeValue v : c.getCodes()) {
          GMethod m = domainCodegen.getMethod("is{}", v.getNameCamelCased()).returnType(boolean.class);
          m.body.line("return get{}() == {}.{};", mtop.getCapitalVariableName(), c.getClassName(), v.getEnumName());
        }
      }

      domainCodegen.addImports(Shim.class);
    }
  }

  private void oneToManyProperties(GClass domainCodegen, Entity entity) {
    for (OneToManyProperty otmp : entity.getOneToManyProperties()) {
      if (otmp.isCollectionSkipped()) {
        continue;
      }

      GField collection = domainCodegen.getField(otmp.getVariableName());
      collection.type("ForeignKeyListHolder<{}, {}>", entity.getClassName(), otmp.getTargetJavaType());
      collection.initialValue("new ForeignKeyListHolder<{}, {}>(({}) this, Aliases.{}(), Aliases.{}().{}, new {}ListDelegate())",//
        entity.getClassName(),
        otmp.getTargetJavaType(),
        entity.getClassName(),
        otmp.getManySide().getVariableName(),
        otmp.getManySide().getVariableName(),
        otmp.getKeyFieldName(),
        otmp.getCapitalVariableName());
      domainCodegen.addImports(ForeignKeyListHolder.class);

      if (!otmp.isOneToOne()) {
        GMethod getter = domainCodegen.getMethod("get" + otmp.getCapitalVariableName()).returnType(otmp.getJavaType());
        getter.body.line("return this.{}.get();", otmp.getVariableName());

        GMethod setter = domainCodegen.getMethod("set" + otmp.getCapitalVariableName()).argument(otmp.getJavaType(), otmp.getVariableName());
        setter.body.line(
          "ListDiff<{}> diff = ListDiff.of(this.get{}(), {});",
          otmp.getTargetJavaType(),
          otmp.getCapitalVariableName(),
          otmp.getVariableName());
        setter.body.line("for ({} o : diff.removed) {", otmp.getTargetJavaType());
        setter.body.line("_   this.remove{}(o);", otmp.getCapitalVariableNameSingular());
        setter.body.line("}");
        setter.body.line("for ({} o : diff.added) {", otmp.getTargetJavaType());
        setter.body.line("_   this.add{}(o);", otmp.getCapitalVariableNameSingular());
        setter.body.line("}");

        GMethod adder = domainCodegen.getMethod("add{}", otmp.getCapitalVariableNameSingular());
        adder.argument(otmp.getTargetJavaType(), "o");
        adder.body.line("if (o.get{}() == this) {", otmp.getManyToOneProperty().getCapitalVariableName());
        adder.body.line("_    return;");
        adder.body.line("}");
        adder.body.line("o.set{}WithoutPercolation(({}) this);", otmp.getManyToOneProperty().getCapitalVariableName(), entity.getClassName());
        adder.body.line("this.add{}WithoutPercolation(o);", otmp.getCapitalVariableNameSingular());

        GMethod remover = domainCodegen.getMethod("remove{}", otmp.getCapitalVariableNameSingular());
        remover.argument(otmp.getTargetJavaType(), "o");
        remover.body.line("if (o.get{}() != this) {", otmp.getManyToOneProperty().getCapitalVariableName());
        remover.body.line("_    return;");
        remover.body.line("}");
        remover.body.line("o.set{}WithoutPercolation(null);", otmp.getManyToOneProperty().getCapitalVariableName(), entity.getClassName());
        remover.body.line("this.remove{}WithoutPercolation(o);", otmp.getCapitalVariableNameSingular());

        domainCodegen.addImports(Copy.class, List.class, ListDiff.class);
      } else {
        GMethod getter = domainCodegen.getMethod("get" + otmp.getCapitalVariableNameSingular()).returnType(otmp.getTargetJavaType());
        getter.body.line("return (this.{}.get().size() == 0) ? null : this.{}.get().get(0);", otmp.getVariableName(), otmp.getVariableName());

        GMethod setter = domainCodegen.getMethod("set" + otmp.getCapitalVariableNameSingular());
        setter.argument(otmp.getTargetJavaType(), "n");
        setter.body.line("{} o = this.get{}();", otmp.getTargetJavaType(), otmp.getCapitalVariableNameSingular());
        setter.body.line("if (o != null) {", otmp.getVariableName());
        setter.body.line("_   o.set{}WithoutPercolation(null);", otmp.getManyToOneProperty().getCapitalVariableName(), entity.getClassName());
        setter.body.line("_   this.remove{}WithoutPercolation(o);", otmp.getCapitalVariableNameSingular());
        setter.body.line("}");
        setter.body.line("if (n != null) {");
        setter.body.line("_   n.set{}WithoutPercolation(({}) this);",//
          otmp.getManyToOneProperty().getCapitalVariableName(),
          entity.getClassName());
        setter.body.line("_   this.add{}WithoutPercolation(n);", otmp.getCapitalVariableNameSingular());
        setter.body.line("}");
      }

      GMethod adder2 = domainCodegen.getMethod("add{}WithoutPercolation", otmp.getCapitalVariableNameSingular());
      adder2.argument(otmp.getTargetJavaType(), "o").setProtected();
      adder2.body.line("this.getChanged().record(\"{}\");", otmp.getVariableName());
      adder2.body.line("this.{}.add(o);", otmp.getVariableName());

      GMethod remover2 = domainCodegen.getMethod("remove{}WithoutPercolation", otmp.getCapitalVariableNameSingular());
      remover2.argument(otmp.getTargetJavaType(), "o").setProtected();
      remover2.body.line("this.getChanged().record(\"{}\");", otmp.getVariableName());
      remover2.body.line("this.{}.remove(o);", otmp.getVariableName());

      GClass listDelegate = domainCodegen.getInnerClass("{}ListDelegate", otmp.getCapitalVariableName()).setPrivate().notStatic();
      listDelegate.implementsInterface("joist.domain.util.ListProxy.Delegate<{}>", otmp.getTargetJavaType());
      GMethod doAdd = listDelegate.getMethod("doAdd", Argument.arg(otmp.getTargetJavaType(), "e"));
      GMethod doRemove = listDelegate.getMethod("doRemove", Argument.arg(otmp.getTargetJavaType(), "e"));
      if (!otmp.isOneToOne()) {
        doAdd.body.line("add{}(e);", otmp.getCapitalVariableNameSingular());
        doRemove.body.line("remove{}(e);", otmp.getCapitalVariableNameSingular());
      } else {
        doAdd.body.line("throw new UnsupportedOperationException(\"Not implemented\");");
        doRemove.body.line("throw new UnsupportedOperationException(\"Not implemented\");");
      }

      domainCodegen.addImports(otmp.getManySide().getFullAliasClassName());
    }
  }

  private void manyToManyProperties(GClass domainCodegen, Entity entity) {
    for (ManyToManyProperty mtmp : entity.getManyToManyProperties()) {
      if (mtmp.getMySideOneToMany().isCollectionSkipped()) {
        continue;
      }

      GMethod getter = domainCodegen.getMethod("get" + mtmp.getCapitalVariableName()).returnType(mtmp.getJavaType());
      getter.body.line("{} l = {};", mtmp.getJavaType(), mtmp.getDefaultJavaString());
      getter.body.line("for ({} o : this.get{}()) {",//
        mtmp.getJoinTable().getClassName(),
        mtmp.getMySideManyToOne().getOneToManyProperty().getCapitalVariableName());
      getter.body.line("_   l.add(o.get{}());", mtmp.getCapitalVariableNameSingular());
      getter.body.line("}");
      getter.body.line("return l;");

      GMethod setter = domainCodegen.getMethod("set" + mtmp.getCapitalVariableName()).argument(mtmp.getJavaType(), mtmp.getVariableName());
      setter.body.line(
        "ListDiff<{}> diff = ListDiff.of(this.get{}(), {});",
        mtmp.getTargetJavaType(),
        mtmp.getCapitalVariableName(),
        mtmp.getVariableName());
      setter.body.line("for ({} o : diff.removed) {", mtmp.getTargetJavaType());
      setter.body.line("_   this.remove{}(o);", mtmp.getCapitalVariableNameSingular());
      setter.body.line("}");
      setter.body.line("for ({} o : diff.added) {", mtmp.getTargetJavaType());
      setter.body.line("_   this.add{}(o);", mtmp.getCapitalVariableNameSingular());
      setter.body.line("}");

      GMethod adder = domainCodegen.getMethod("add{}", mtmp.getCapitalVariableNameSingular());
      adder.argument(mtmp.getTargetTable().getClassName(), "o");
      adder.body.line("{} a = new {}();", mtmp.getJoinTable().getClassName(), mtmp.getJoinTable().getClassName());
      adder.body.line("a.set{}(({}) this);", mtmp.getMySideManyToOne().getCapitalVariableName(), entity.getClassName());
      adder.body.line("a.set{}(o);", mtmp.getOther().getMySideManyToOne().getCapitalVariableName(), mtmp.getTargetTable().getClassName());

      GMethod remover = domainCodegen.getMethod("remove{}", mtmp.getCapitalVariableNameSingular());
      remover.argument(mtmp.getTargetTable().getClassName(), "o");
      remover.body.line("for ({} a : Copy.list(this.get{}())) {",//
        mtmp.getJoinTable().getClassName(),
        mtmp.getMySideManyToOne().getOneToManyProperty().getCapitalVariableName());
      remover.body.line("_   if (a.get{}().equals(o)) {", mtmp.getCapitalVariableNameSingular());
      remover.body.line("_   _   a.set{}(null);", mtmp.getCapitalVariableNameSingular());
      remover.body.line("_   _   a.set{}(null);", mtmp.getOther().getCapitalVariableNameSingular());
      remover.body.line("_   _   if (UoW.isOpen()) {");
      remover.body.line("_   _   _   UoW.delete(a);");
      remover.body.line("_   _   }");
      remover.body.line("_   }");
      remover.body.line("}");

      domainCodegen.addImports(Copy.class, ArrayList.class, UoW.class, ListDiff.class);
    }
  }

  private void changed(GClass domainCodegen, Entity entity) {
    if (entity.isRoot()) {
      domainCodegen.getField("changed").type(Changed.class).setProtected();
    }

    GMethod getter = domainCodegen.getMethod("getChanged").returnType("{}Changed", entity.getClassName());
    getter.body.line("if (this.changed == null) {");
    getter.body.line("_   this.changed = new {}Changed(({}) this);", entity.getClassName(), entity.getClassName());
    getter.body.line("}");
    getter.body.line("return ({}Changed) this.changed;", entity.getClassName());

    GClass changedClass = domainCodegen.getInnerClass("{}Changed", entity.getClassName());
    if (entity.isRoot()) {
      changedClass.baseClass(AbstractChanged.class);
    } else {
      changedClass.baseClassName("{}Changed", entity.getBaseEntity().getClassName());
    }
    changedClass.getConstructor(entity.getClassName() + " instance").body.line("super(instance);", entity.getClassName());

    for (PrimitiveProperty p : entity.getPrimitiveProperties()) {
      GMethod has = changedClass.getMethod("has{}", p.getCapitalVariableName()).returnType(boolean.class);
      has.body.line("return this.contains(\"{}\");", p.getVariableName());

      GMethod original = changedClass.getMethod("getOriginal{}", p.getCapitalVariableName()).returnType(p.getJavaType());
      original.body.line("return ({}) this.getOriginal(\"{}\");", p.getJavaType(), p.getVariableName());
    }

    for (ManyToOneProperty mtop : entity.getManyToOneProperties()) {
      GMethod has = changedClass.getMethod("has{}", mtop.getCapitalVariableName()).returnType(boolean.class);
      has.body.line("return this.contains(\"{}\");", mtop.getVariableName());

      GMethod original = changedClass.getMethod("getOriginal{}", mtop.getCapitalVariableName()).returnType(mtop.getJavaType());
      original.body.line("return ({}) this.getOriginal(\"{}\");", mtop.getJavaType(), mtop.getVariableName());
    }

    for (OneToManyProperty otmp : entity.getOneToManyProperties()) {
      if (otmp.isCollectionSkipped()) {
        continue;
      }
      GMethod has = changedClass.getMethod("has{}", otmp.getCapitalVariableName()).returnType(boolean.class);
      has.body.line("return this.contains(\"{}\");", otmp.getVariableName());
    }
  }

  private void clearAssociations(GClass domainCodegen, Entity entity) {
    GMethod clearAssociations = domainCodegen.getMethod("clearAssociations");
    clearAssociations.addAnnotation("@Override");
    clearAssociations.body.line("super.clearAssociations();");

    for (ManyToOneProperty mtop : entity.getManyToOneProperties()) {
      clearAssociations.body.line("this.set{}(null);", mtop.getCapitalVariableName());
    }
    for (OneToManyProperty otmp : entity.getOneToManyProperties()) {
      if (otmp.isCollectionSkipped()) {
        continue;
      }
      if (otmp.isOneToOne()) {
        clearAssociations.body.line("this.set{}(null);", otmp.getCapitalVariableNameSingular());
      } else {
        clearAssociations.body.line("for ({} o : Copy.list(this.get{}())) {", otmp.getTargetJavaType(), otmp.getCapitalVariableName());
        clearAssociations.body.line("_   o.set{}WithoutPercolation(null);", otmp.getManyToOneProperty().getCapitalVariableName());
        clearAssociations.body.line("}");
        domainCodegen.addImports(Copy.class, List.class);
      }
    }
  }

}
