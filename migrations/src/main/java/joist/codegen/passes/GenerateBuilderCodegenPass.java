package joist.codegen.passes;

import joist.codegen.Codegen;
import joist.codegen.dtos.Entity;
import joist.codegen.dtos.ManyToOneProperty;
import joist.codegen.dtos.PrimitiveProperty;
import joist.domain.builders.AbstractBuilder;
import joist.sourcegen.Argument;
import joist.sourcegen.GClass;
import joist.sourcegen.GMethod;

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

  private void primitiveProperties(GClass builderCodegen, Entity entity) {
    for (PrimitiveProperty p : entity.getPrimitiveProperties()) {
      if (p.getVariableName().equals("version")) {
        continue;
      }
      GMethod m = builderCodegen.getMethod(p.getVariableName()).returnType(entity.getBuilderClassName());
      m.argument(p.getJavaType(), p.getVariableName());
      m.body.line("get().set{}({});", p.getCapitalVariableName(), p.getVariableName());
      m.body.line("return ({}) this;", entity.getBuilderClassName());
    }
  }

  private void manyToOneProperties(GClass c, Entity entity) {
    for (ManyToOneProperty mtop : entity.getManyToOneProperties()) {
      GMethod m1 = c.getMethod(mtop.getVariableName(), Argument.arg(mtop.getOneSide().getFullClassName(), mtop.getVariableName()));
      m1.returnType(entity.getBuilderClassName());
      m1.body.line("get().set{}({});", mtop.getCapitalVariableName(), mtop.getVariableName());
      m1.body.line("return ({}) this;", entity.getBuilderClassName());

      if (!mtop.getOneSide().isCodeEntity()) {
        GMethod m2 = c.getMethod(mtop.getVariableName(), Argument.arg(mtop.getOneSide().getBuilderClassName(), mtop.getVariableName()));
        m2.returnType(entity.getBuilderClassName());
        m2.body.line("get().set{}({}.get());", mtop.getCapitalVariableName(), mtop.getVariableName());
        m2.body.line("return ({}) this;", entity.getBuilderClassName());
      }
    }
  }
}
