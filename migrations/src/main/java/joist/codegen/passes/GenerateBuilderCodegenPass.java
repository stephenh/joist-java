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
import joist.domain.builders.AbstractBuilder;
import joist.domain.builders.DefaultsContext;
import joist.domain.uow.UoW;
import joist.sourcegen.Argument;
import joist.sourcegen.GClass;
import joist.sourcegen.GMethod;
import joist.util.Inflector;

import org.apache.commons.lang.StringUtils;

public class GenerateBuilderCodegenPass implements Pass<Codegen> {

  public void pass(Codegen codegen) {
    for (Entity entity : codegen.getSchema().getEntities().values()) {
      if (entity.isCodeEntity()) {
        continue;
      }

      GClass builderCodegen = codegen.getOutputCodegenDirectory().getClass(entity.getFullBuilderCodegenClassName());
      builderCodegen.setAbstract();
      if (entity.isRoot()) {
        builderCodegen.baseClassName("AbstractBuilder<{}>", entity.getFullClassName());
        builderCodegen.addImports(AbstractBuilder.class);
      } else {
        builderCodegen.baseClassName(entity.getParentClassName() + "Builder");
      }
      builderCodegen.addAnnotation("@SuppressWarnings(\"all\")");

      GMethod defaults = builderCodegen.getMethod("defaults");
      defaults.addAnnotation("@Override");
      defaults.returnType(entity.getBuilderClassName());
      defaults.body.line("try {");
      defaults.body.line("_   DefaultsContext c = DefaultsContext.push();");
      builderCodegen.addImports(DefaultsContext.class);

      this.constructor(builderCodegen, entity);
      this.primitiveProperties(codegen, builderCodegen, entity);
      this.manyToOneProperties(builderCodegen, entity);
      this.oneToManyProperties(builderCodegen, entity);
      this.manyToManyProperties(builderCodegen, entity);
      this.overrideGet(builderCodegen, entity);
      this.ensureSaved(builderCodegen, entity);
      this.delete(builderCodegen, entity);

      defaults.body.line("_   return ({}) super.defaults();", entity.getBuilderClassName());
      defaults.body.line("} finally {");
      defaults.body.line("_   DefaultsContext.pop();");
      defaults.body.line("}");
    }
  }

  private void constructor(GClass builderCodegen, Entity entity) {
    GMethod m = builderCodegen.getConstructor(entity.getFullClassName() + " instance");
    m.body.line("super(instance);");
  }

  private void overrideGet(GClass builderCodegen, Entity entity) {
    builderCodegen.getMethod("get").returnType(entity.getFullClassName()).body.line("return ({}) super.get();", entity.getFullClassName());
  }

  private void ensureSaved(GClass builderCodegen, Entity entity) {
    GMethod m = builderCodegen.getMethod("ensureSaved").returnType(entity.getBuilderClassName()).addAnnotation("@Override");
    m.body.line("doEnsureSaved();");
    m.body.line("return ({}) this;", entity.getBuilderClassName());
    builderCodegen.addImports(UoW.class);
  }

  private void delete(GClass builderCodegen, Entity entity) {
    if (entity.isSubclass()) {
      return;
    }
    GMethod delete = builderCodegen.getMethod("delete").addAnnotation("@Override");
    delete.body.line("{}.queries.delete(get());", entity.getClassName());

    GMethod deleteAll = builderCodegen.getMethod("deleteAll").setStatic();
    deleteAll.body.line("List<Long> ids = {}.queries.findAllIds();", entity.getClassName());
    deleteAll.body.line("for (Long id : ids) {");
    deleteAll.body.line("  {}.queries.delete({}.queries.find(id));", entity.getClassName(), entity.getClassName());
    deleteAll.body.line("}");
    builderCodegen.addImports(List.class);
  }

  private void primitiveProperties(Codegen codegen, GClass c, Entity entity) {
    for (PrimitiveProperty p : entity.getPrimitiveProperties()) {
      if (p.getVariableName().equals("version")) {
        continue;
      }
      if (p.getVariableName().equals("id")) {
        GMethod m = c.getMethod("id").returnType(p.getJavaType());
        m.body.line("if (UoW.isOpen() && get().getId() == null) {");
        m.body.line("_   UoW.flush();");
        m.body.line("}");
        // let addFluentGetter call below finish the method (add the return)
        c.addImports(UoW.class);
      }
      // regular foo() getter
      this.addFluentGetter(c, p.getVariableName(), p.getJavaType());
      // regular foo(value) setter
      this.addFluentSetter(c, entity, p.getVariableName(), p.getJavaType());
      // overload with(value) setter
      if (entity.getUniquePropertyTypes().contains(p.getJavaType())) {
        this.addFluentWith(c, entity, p.getVariableName(), p.getJavaType());
      }
      // add to defaults
      if (p.shouldHaveNotNullRule()) {
        String defaultValue;
        if (String.class.getName().equals(p.getJavaType())) {
          defaultValue = "\"" + p.getVariableName() + "\"";
        } else {
          defaultValue = codegen.getConfig().getBuildersDefault(p.getJavaType());
        }
        // user types may not have configured defaults
        if (defaultValue != null) {
          this.addToDefaults(c, p.getVariableName(), p.getJavaType(), defaultValue);
        }
      }
    }
    // add covariant return types
    for (Entity base : entity.getBaseEntities()) {
      for (PrimitiveProperty p : base.getPrimitiveProperties()) {
        if (p.getVariableName().equals("version") || p.getVariableName().equals("id")) {
          continue;
        }
        this.addFluentGetter(c, p.getVariableName(), p.getJavaType()); // for scalac bug
        this.addFluentSetter(c, entity, p.getVariableName(), p.getJavaType());
      }
    }
  }

  private void oneToManyProperties(GClass c, Entity entity) {
    for (OneToManyProperty otom : entity.getOneToManyProperties()) {
      if (otom.isCollectionSkipped() || otom.isManyToMany()) {
        continue;
      }
      if (otom.isOneToOne()) {
        // child() -> ChildBuilder
        GMethod m = c.getMethod(otom.getVariableNameSingular());
        m.returnType("{}Builder", otom.getTargetJavaType());
        m.body.line("if (get().get{}() == null) {", otom.getCapitalVariableNameSingular());
        m.body.line("_   return null;");
        m.body.line("}");
        m.body.line("return Builders.existing(get().get{}());", otom.getCapitalVariableNameSingular());
      } else {
        // childs() -> List<ChildBuilder>
        {
          GMethod m = c.getMethod(otom.getVariableName());
          m.returnType("List<{}Builder>", otom.getTargetJavaType());
          m.body.line("List<{}Builder> b = new ArrayList<{}Builder>();", otom.getTargetJavaType(), otom.getTargetJavaType());
          m.body.line("for ({} e : get().get{}()) {", otom.getTargetJavaType(), otom.getCapitalVariableName());
          m.body.line("_   b.add(Builders.existing(e));");
          m.body.line("}");
          m.body.line("return b;");
          c.addImports(List.class, ArrayList.class);
          c.addImports(otom.getManySide().getFullClassName());
        }
        // child(i) -> ChildBuilder
        {
          GMethod m = c.getMethod(StringUtils.uncapitalize(otom.getCapitalVariableNameSingular()), Argument.arg("int", "i"));
          m.returnType("{}Builder", otom.getTargetJavaType());
          m.body.line("return Builders.existing(get().get{}().get(i));", otom.getCapitalVariableName());
        }
      }
      // newChild() -> ChildBuilder
      if (!otom.getManySide().isAbstract()) {
        GMethod m = c.getMethod("new" + otom.getCapitalVariableNameSingular());
        m.returnType("{}Builder", otom.getTargetJavaType());
        m.body.line("return Builders.a{}().{}(({}) this);", //
          otom.getTargetJavaType(),
          StringUtils.uncapitalize(otom.getManyToOneProperty().getCapitalVariableName()),
          entity.getBuilderClassName());
      }
    }
  }

  private void manyToOneProperties(GClass c, Entity entity) {
    // add an up-front pass to get already-set properties into DefaultsContext
    for (ManyToOneProperty mtop : entity.getManyToOneProperties()) {
      if (!mtop.getOneSide().isCodeEntity()) {
        GMethod defaults = c.getMethod("defaults");
        defaults.body.line("_   c.rememberIfSet({}());", mtop.getVariableName());
      }
    }
    // now add the regular getters/setters/etc.
    for (ManyToOneProperty mtop : entity.getManyToOneProperties()) {
      // regular foo() getter
      if (mtop.getOneSide().isCodeEntity()) {
        this.addFluentGetter(c, mtop.getVariableName(), mtop.getJavaType());
      } else {
        this.addFluentBuilderGetter(c, mtop.getVariableName(), mtop.getJavaType());
      }

      // regular foo(value) setter
      this.addFluentSetter(c, entity, mtop.getVariableName(), mtop.getOneSide().getFullClassName());
      // overload with(value) setter
      if (entity.getUniquePropertyTypes().contains(mtop.getJavaType())) {
        this.addFluentWith(c, entity, mtop.getVariableName(), mtop.getOneSide().getFullClassName());
      }

      if (!mtop.getOneSide().isCodeEntity()) {
        // regular foo(valueBuilder) setter
        this.addFluentBuilderSetter(c, entity, mtop.getVariableName(), mtop.getOneSide().getBuilderClassName());
        // overload with(valueBuilder) setter
        if (entity.getUniquePropertyTypes().contains(mtop.getJavaType())) {
          this.addFluentWith(c, entity, mtop.getVariableName(), mtop.getOneSide().getBuilderClassName());
        }
      }

      // add to defaults
      if (mtop.isNotNull()) {
        if (mtop.getOneSide().isCodeEntity()) {
          CodeEntity ce = (CodeEntity) mtop.getOneSide();
          String defaultValue = ce.getClassName() + "." + ce.getCodes().get(0).getEnumName();
          this.addToDefaults(c, mtop.getVariableName(), mtop.getJavaType(), defaultValue);
        } else if (!mtop.getOneSide().isAbstract()) {
          String defaultValue = "Builders.a" + mtop.getOneSide().getClassName() + "().defaults()";
          this.addToDefaultsWithContextLookup(c, mtop, defaultValue);
        }
      }

      // add codeValue() methods
      if (mtop.getOneSide().isCodeEntity()) {
        for (CodeValue code : ((CodeEntity) mtop.getOneSide()).getCodes()) {
          if (entity.getUniqueCodeNames().contains(code.getEnumName())) {
            {
              // e.g. blue()
              GMethod m = c.getMethod(Inflector.uncapitalize(code.getNameCamelCased()));
              m.returnType(entity.getBuilderClassName());
              m.body.line("return {}({}.{});", mtop.getVariableName(), mtop.getOneSide().getClassName(), code.getEnumName());
            }
            {
              // e.g. isBlue()
              GMethod m = c.getMethod("is" + code.getNameCamelCased()).returnType("boolean");
              m.body.line("return get().is{}();", code.getNameCamelCased());
            }
          }
        }
      }
    }
    // add covariant return types
    for (Entity base : entity.getBaseEntities()) {
      for (ManyToOneProperty mtop : base.getManyToOneProperties()) {
        // regular foo() getter, for scalac bug
        if (mtop.getOneSide().isCodeEntity()) {
          this.addFluentGetter(c, mtop.getVariableName(), mtop.getJavaType());
        } else {
          this.addFluentBuilderGetter(c, mtop.getVariableName(), mtop.getJavaType());
        }
        // regular foo(value) setter
        this.addFluentSetter(c, entity, mtop.getVariableName(), mtop.getOneSide().getFullClassName());
        // overload with(value) setter
        if (entity.getUniquePropertyTypes().contains(mtop.getJavaType())) {
          this.addFluentWith(c, entity, mtop.getVariableName(), mtop.getOneSide().getFullClassName());
        }
        if (!mtop.getOneSide().isCodeEntity()) {
          // regular foo(valueBuilder) setter
          this.addFluentBuilderSetter(c, entity, mtop.getVariableName(), mtop.getOneSide().getBuilderClassName());
          // overload with(valueBuilder) setter
          if (entity.getUniquePropertyTypes().contains(mtop.getJavaType())) {
            this.addFluentWith(c, entity, mtop.getVariableName(), mtop.getOneSide().getBuilderClassName());
          }
        } else {
          for (CodeValue code : ((CodeEntity) mtop.getOneSide()).getCodes()) {
            if (entity.getUniqueCodeNames().contains(code.getEnumName())) {
              // e.g. blue()
              GMethod m = c.getMethod(Inflector.uncapitalize(code.getNameCamelCased()));
              m.returnType(entity.getBuilderClassName());
              m.body.line("return {}({}.{});", mtop.getVariableName(), mtop.getOneSide().getClassName(), code.getEnumName());
            }
          }
        }
      }
    }
  }

  private void manyToManyProperties(GClass c, Entity entity) {
    for (ManyToManyProperty mtmp : entity.getManyToManyProperties()) {
      if (mtmp.getMySideOneToMany().isCollectionSkipped() || mtmp.getTargetTable().isCodeEntity()) {
        continue;
      }

      // childs() -> List<ChildBuilder>
      {
        GMethod m = c.getMethod(mtmp.getVariableName());
        m.returnType("List<{}Builder>", mtmp.getTargetJavaType());
        m.body.line("List<{}Builder> b = new ArrayList<{}Builder>();", mtmp.getTargetJavaType(), mtmp.getTargetJavaType());
        m.body.line("for ({} e : get().get{}()) {", mtmp.getTargetJavaType(), mtmp.getCapitalVariableName());
        m.body.line("_   b.add(Builders.existing(e));");
        m.body.line("}");
        m.body.line("return b;");
        c.addImports(List.class, ArrayList.class);
        c.addImports(mtmp.getTargetTable().getFullClassName());
      }
      // child(i) -> ChildBuilder
      {
        GMethod m = c.getMethod(StringUtils.uncapitalize(mtmp.getCapitalVariableNameSingular()), Argument.arg("int", "i"));
        m.returnType("{}Builder", mtmp.getTargetJavaType());
        m.body.line("return Builders.existing(get().get{}().get(i));", mtmp.getCapitalVariableName());
      }

      this.addFluentSetter(c, entity, mtmp, false, mtmp.getCapitalVariableNameSingular()); // foo(OtherType)
      this.addFluentSetter(c, entity, mtmp, true, mtmp.getCapitalVariableNameSingular()); // foo(OtherTypeBuilder)
      this.addFluentSetter(c, entity, mtmp, false, "with"); // with(OtherType)
      this.addFluentSetter(c, entity, mtmp, true, "with"); // with(OtherTypeBuilder)
    }
  }

  private void addFluentSetter(GClass builderCodegen, Entity entity, String variableName, String javaType) {
    GMethod m = builderCodegen.getMethod(variableName, Argument.arg(javaType, variableName));
    m.returnType(entity.getBuilderClassName());
    m.body.line("get().set{}({});", Inflector.capitalize(variableName), variableName);
    m.body.line("return ({}) this;", entity.getBuilderClassName());
  }

  private void addFluentWith(GClass builderCodegen, Entity entity, String variableName, String javaType) {
    GMethod m = builderCodegen.getMethod("with", Argument.arg(javaType, variableName));
    m.returnType(entity.getBuilderClassName());
    m.body.line("return {}({});", variableName, variableName);
  }

  private void addFluentBuilderSetter(GClass builderCodegen, Entity entity, String variableName, String javaType) {
    GMethod m = builderCodegen.getMethod(variableName, Argument.arg(javaType, variableName));
    m.returnType(entity.getBuilderClassName());
    m.body.line("return {}({} == null ? null : {}.get());", variableName, variableName, variableName);
  }

  private void addFluentGetter(GClass builderCodegen, String variableName, String javaType) {
    GMethod m = builderCodegen.getMethod(variableName + "()");
    m.returnType(javaType);
    m.body.line("return get().get{}();", Inflector.capitalize(variableName));
  }

  private void addFluentBuilderGetter(GClass builderCodegen, String variableName, String javaType) {
    GMethod m = builderCodegen.getMethod(variableName + "()");
    m.returnType(javaType + "Builder");
    m.body.line("if (get().get{}() == null) {", Inflector.capitalize(variableName));
    m.body.line("_   return null;");
    m.body.line("}");
    m.body.line("return Builders.existing(get().get{}());", Inflector.capitalize(variableName));
  }

  private void addFluentSetter(GClass builderCodegen, Entity entity, ManyToManyProperty mtmp, boolean isForBuilder, String methodName) {
    final String arg;
    if (isForBuilder) {
      arg = mtmp.getTargetTable().getBuilderClassName();
    } else {
      arg = mtmp.getTargetTable().getFullClassName();
    }
    GMethod m = builderCodegen.getMethod(Inflector.uncapitalize(methodName), Argument.arg(arg, mtmp.getVariableName()));
    m.returnType(entity.getBuilderClassName());
    if (isForBuilder) {
      m.body.line("get().add{}({}.get());", mtmp.getCapitalVariableNameSingular(), mtmp.getVariableName());
    } else {
      m.body.line("get().add{}({});", mtmp.getCapitalVariableNameSingular(), mtmp.getVariableName());
    }
    m.body.line("return ({}) this;", entity.getBuilderClassName());
  }

  private void addToDefaults(GClass c, String variableName, String type, String defaultValue) {
    String defaultMethodName = "default" + StringUtils.capitalize(variableName);

    GMethod defaults = c.getMethod("defaults");
    defaults.body.line("_   if ({}() == null) {", variableName);
    defaults.body.line("_   _   {}({}());", variableName, defaultMethodName);
    defaults.body.line("_   }");

    GMethod thisDefault = c.getMethod(defaultMethodName);
    thisDefault.setProtected().returnType(type).body.line("return {};", defaultValue);
  }

  private void addToDefaultsWithContextLookup(GClass c, ManyToOneProperty mtop, String defaultValue) {
    String variableName = mtop.getVariableName();
    String defaultMethodName = "default" + StringUtils.capitalize(variableName);

    GMethod defaults = c.getMethod("defaults");
    defaults.body.line("_   if ({}() == null) {", variableName);
    defaults.body.line("_   _   {}(c.getIfAvailable({}.class));", variableName, mtop.getOneSide().getClassName());
    defaults.body.line("_   _   if ({}() == null) {", variableName);
    defaults.body.line("_   _   _   {}({}());", variableName, defaultMethodName);
    defaults.body.line("_   _   _   c.rememberIfSet({}());", variableName);
    defaults.body.line("_   _   }");
    defaults.body.line("_   }");

    GMethod thisDefault = c.getMethod(defaultMethodName);
    thisDefault.setProtected().returnType(mtop.getJavaType() + "Builder").body.line("return {};", defaultValue);
  }

}
