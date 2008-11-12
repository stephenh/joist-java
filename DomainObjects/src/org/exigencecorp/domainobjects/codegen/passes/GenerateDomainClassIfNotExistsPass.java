package org.exigencecorp.domainobjects.codegen.passes;

import org.apache.commons.lang.StringUtils;
import org.exigencecorp.domainobjects.codegen.Codegen;
import org.exigencecorp.domainobjects.codegen.dtos.Entity;
import org.exigencecorp.gen.GClass;
import org.exigencecorp.gen.GField;

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

            GClass allQueries = codegen.getOutputCodegenDirectory().getClass(codegen.getConfig().getQueriesPackage() + ".Query");
            GField queryField = allQueries.getField(StringUtils.uncapitalize(entity.getClassName()));
            queryField.setPublic().setStatic().setFinal();
            queryField.type(entity.getFullQueriesClassName());
            queryField.initialValue("new {}Queries()", entity.getClassName());
        }
    }
}
