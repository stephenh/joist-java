package joist.codegen.passes;

import joist.codegen.Codegen;
import joist.codegen.dtos.Entity;
import joist.codegen.dtos.ManyToOneProperty;
import joist.codegen.dtos.PrimitiveProperty;
import joist.domain.builders.AbstractBuilder;
import joist.sourcegen.Argument;
import joist.sourcegen.GClass;
import joist.sourcegen.GMethod;
import joist.util.Inflector;
import joist.util.MapToList;

public class GenerateBuilderCodegenPass implements Pass {

  public void pass(Codegen codegen) {
    for (Entity entity : codegen.getEntities().values()) {
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

      this.constructor(builderCodegen, entity);
      this.primitiveProperties(builderCodegen, entity);
      this.manyToOneProperties(builderCodegen, entity);
      this.overrideGet(builderCodegen, entity);
    }
  }

  private void constructor(GClass builderCodegen, Entity entity) {
    GMethod m = builderCodegen.getConstructor(entity.getFullClassName() + " instance");
    m.body.line("super(instance);");
  }

  private void overrideGet(GClass builderCodegen, Entity entity) {
    builderCodegen.getMethod("get").returnType(entity.getFullClassName()).body.line("return ({}) super.get();", entity.getFullClassName());
  }

  private void primitiveProperties(GClass c, Entity entity) {
    // first pass to get the types
    MapToList<String, String> perType = new MapToList<String, String>();
    for (PrimitiveProperty p : entity.getPrimitiveProperties()) {
      perType.get(p.getJavaType()).add(p.getVariableName());
    }

    for (PrimitiveProperty p : entity.getPrimitiveProperties()) {
      if (p.getVariableName().equals("version")) {
        continue;
      }
      // regular foo(value) setter
      this.addFluentSetter(c, entity, p.getVariableName(), p.getVariableName(), p.getJavaType());
      // overload with(value) setter
      if (perType.get(p.getJavaType()).size() == 1) {
        this.addFluentSetter(c, entity, "with", p.getVariableName(), p.getJavaType());
      }
    }
  }

  private void manyToOneProperties(GClass c, Entity entity) {
    // first pass to get the types
    MapToList<String, String> perType = new MapToList<String, String>();
    for (ManyToOneProperty mtop : entity.getManyToOneProperties()) {
      perType.get(mtop.getJavaType()).add(mtop.getVariableName());
    }

    for (ManyToOneProperty mtop : entity.getManyToOneProperties()) {
      // regular foo(value) setter
      this.addFluentSetter(c, entity, mtop.getVariableName(), mtop.getVariableName(), mtop.getOneSide().getFullClassName());
      // overload with(value) setter
      if (perType.get(mtop.getJavaType()).size() == 1) {
        this.addFluentSetter(c, entity, "with", mtop.getVariableName(), mtop.getOneSide().getFullClassName());
      }

      if (!mtop.getOneSide().isCodeEntity()) {
        // regular foo(valueBuilder) setter
        this.addFluentBuilderSetter(c, entity, mtop.getVariableName(), mtop.getVariableName(), mtop.getOneSide().getBuilderClassName());
        // overload with(valueBuilder) setter
        if (perType.get(mtop.getJavaType()).size() == 1) {
          this.addFluentBuilderSetter(c, entity, "with", mtop.getVariableName(), mtop.getOneSide().getBuilderClassName());
        }
      }
    }
  }

  private void addFluentSetter(GClass builderCodegen, Entity entity, String methodName, String variableName, String javaType) {
    GMethod m = builderCodegen.getMethod(methodName, Argument.arg(javaType, variableName));
    m.returnType(entity.getBuilderClassName());
    m.body.line("get().set{}({});", Inflector.capitalize(variableName), variableName);
    m.body.line("return ({}) this;", entity.getBuilderClassName());
  }

  private void addFluentBuilderSetter(GClass builderCodegen, Entity entity, String methodName, String variableName, String javaType) {
    GMethod m = builderCodegen.getMethod(methodName, Argument.arg(javaType, variableName));
    m.returnType(entity.getBuilderClassName());
    m.body.line("get().set{}({}.get());", Inflector.capitalize(variableName), variableName);
    m.body.line("return ({}) this;", entity.getBuilderClassName());
  }

}
