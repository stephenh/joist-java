package joist.domain.codegen.passes;

import joist.domain.codegen.Codegen;
import joist.domain.codegen.dtos.Entity;
import joist.sourcegen.GClass;

public class GenerateDomainClassIfNotExistsPass implements Pass {

    public void pass(Codegen codegen) {
        for (Entity entity : codegen.getEntities().values()) {
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
