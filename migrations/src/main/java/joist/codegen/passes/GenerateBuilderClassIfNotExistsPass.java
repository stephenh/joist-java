package joist.codegen.passes;

import joist.codegen.Codegen;
import joist.codegen.dtos.Entity;
import joist.sourcegen.GClass;

public class GenerateBuilderClassIfNotExistsPass implements Pass {

  public void pass(Codegen codegen) {
    for (Entity entity : codegen.getEntities().values()) {
      if (entity.isCodeEntity()) {
        continue;
      }
      if (!codegen.getOutputSourceDirectory().exists(entity.getFullBuilderClassName())) {
        GClass builder = codegen.getOutputSourceDirectory().getClass(entity.getFullBuilderClassName());
        builder.baseClassName(entity.getFullBuilderCodegenClassName());
        builder.getConstructor().argument(entity.getFullClassName(), "instance").body.line("super(instance);");
      }
    }
  }

}
