package joist.domain.codegen.passes;

import joist.domain.codegen.Codegen;
import joist.domain.codegen.dtos.Entity;

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

            if (!codegen.getOutputSourceDirectory().exists(entity.getFullQueriesClassName())) {
                GClass queries = codegen.getOutputSourceDirectory().getClass(entity.getFullQueriesClassName());
                queries.baseClassName(codegen.getConfig().getQueriesBaseClass(), entity.getClassName());
                queries.getConstructor().body.line("super({}.class);", entity.getClassName());
                queries.addImports(entity.getFullClassName());
            }
        }
    }
}
