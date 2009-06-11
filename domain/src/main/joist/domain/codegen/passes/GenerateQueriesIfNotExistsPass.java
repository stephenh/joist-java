package joist.domain.codegen.passes;

import joist.domain.codegen.Codegen;
import joist.domain.codegen.dtos.Entity;
import joist.sourcegen.GClass;

public class GenerateQueriesIfNotExistsPass implements Pass {

    public void pass(Codegen codegen) {
        for (Entity entity : codegen.getEntities().values()) {
            if (entity.isCodeEntity()) {
                continue;
            }
            if (!codegen.getOutputSourceDirectory().exists(entity.getFullQueriesClassName())) {
                GClass queries = codegen.getOutputSourceDirectory().getClass(entity.getFullQueriesClassName());
                queries.baseClassName(entity.getFullQueriesCodegenClassName());
            }
        }
    }

}
