package org.exigencecorp.domainobjects.codegen.passes;

import org.exigencecorp.domainobjects.codegen.Codegen;
import org.exigencecorp.domainobjects.codegen.dtos.Entity;
import org.exigencecorp.gen.GClass;

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

            if (!codegen.getOutputSourceDirectory().exists(entity.getFullMapperClassName())) {
                codegen.getOutputSourceDirectory().getClass(entity.getFullMapperClassName());
            }
        }
    }
}
