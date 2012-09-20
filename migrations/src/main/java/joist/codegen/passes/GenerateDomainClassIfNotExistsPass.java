package joist.codegen.passes;

import joist.codegen.Codegen;
import joist.codegen.dtos.Entity;
import joist.sourcegen.GClass;

public class GenerateDomainClassIfNotExistsPass implements Pass<Codegen> {

  public void pass(Codegen codegen) {
    for (Entity entity : codegen.getSchema().getEntities().values()) {
      if (entity.isCodeEntity()) {
        continue;
      }
      if (!codegen.getOutputSourceDirectory().exists(entity.getFullClassName())) {
        GClass domain = codegen.getOutputSourceDirectory().getClass(entity.getFullClassName());
        domain.baseClassName(entity.getFullCodegenClassName());
      }
    }
  }

}
