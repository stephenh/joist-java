package joist.codegen.passes;

import joist.codegen.Codegen;
import joist.codegen.dtos.Entity;
import joist.sourcegen.Argument;
import joist.sourcegen.GClass;
import joist.sourcegen.GMethod;
import joist.util.Copy;

public class GenerateBuildersClassPass implements Pass<Codegen> {

  public void pass(Codegen codegen) {
    GClass builders = codegen.getOutputCodegenDirectory().getClass(codegen.getConfig().getBuildersPackage() + ".Builders");

    for (Entity entity : codegen.getSchema().getEntities().values()) {
      if (entity.isCodeEntity()) {
        continue;
      }

      builders.addImports(entity.getFullClassName());

      if (entity.isConcrete()) {
        this.aMethod(builders, entity);
      }
      this.existingMethod(builders, entity);
      this.theMethodWithLong(builders, entity);
      this.theMethodWithInt(builders, entity);
    }
  }

  private void aMethod(GClass builders, Entity entity) {
    GMethod m = builders//
      .getMethod("a" + entity.getClassName())
      .returnType(entity.getBuilderClassName())
      .setStatic();
    m.body.line("return new {}(new {}());", entity.getBuilderClassName(), entity.getClassName());
  }

  private void existingMethod(GClass builders, Entity entity) {
    GMethod m = builders
      .getMethod("existing", Argument.arg(entity.getFullClassName(), entity.getVariableName()))
      .returnType(entity.getBuilderClassName())
      .setStatic();
    for (Entity sub : Copy.reverse(entity.getSubEntitiesRecursively())) {
      m.body.line("if ({} instanceof {}) {", entity.getVariableName(), sub.getClassName());
      m.body.line("_  return new {}(({}) {});", sub.getBuilderClassName(), sub.getClassName(), entity.getVariableName());
      m.body.line("}");
    }
    m.body.line("return new {}({});", entity.getBuilderClassName(), entity.getVariableName());
  }

  private void theMethodWithInt(GClass builders, Entity entity) {
    GMethod m = builders.//
      getMethod("the" + entity.getClassName(), Argument.arg("int", "id"))
      .returnType(entity.getBuilderClassName())
      .setStatic();
    m.body.line("return new {}({}.queries.find((long) id).get());", entity.getBuilderClassName(), entity.getClassName());
  }

  private void theMethodWithLong(GClass builders, Entity entity) {
    GMethod m = builders//
      .getMethod("the" + entity.getClassName(), Argument.arg("long", "id"))
      .returnType(entity.getBuilderClassName())
      .setStatic();
    m.body.line("return new {}({}.queries.find(id).get());", entity.getBuilderClassName(), entity.getClassName());
  }

}
