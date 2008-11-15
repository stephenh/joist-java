package org.exigencecorp.domainobjects.codegen.passes;

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

            // GClass allQueries = codegen.getOutputCodegenDirectory().getClass(codegen.getConfig().getQueriesPackage() + ".Query");
            // GMethod queryMethod = allQueries.getMethod(StringUtils.uncapitalize(entity.getClassName()));
            // queryMethod.setStatic();
            // queryMethod.returnType(entity.getFullQueriesClassName());
            // queryMethod.body.line("return new {}Queries();", entity.getClassName());
            // GField queryField = allQueries.getField(StringUtils.uncapitalize(entity.getClassName()));
            // queryField.setPublic().setStatic().setFinal();
            // queryField.type(entity.getFullQueriesClassName());
            // queryField.initialValue("new {}Queries()", entity.getClassName());

            GClass domainCodegen = codegen.getOutputCodegenDirectory().getClass(entity.getFullCodegenClassName());
            GField query = domainCodegen.getField("queries").setPublic().setStatic().setFinal();
            if (entity.isRoot()) {
                query.type("{}Queries", entity.getClassName());
            } else {
                query.type("@SuppressWarnings(\"hiding\") {}Queries", entity.getClassName());
            }
            query.initialValue("new {}Queries()", entity.getClassName());
            domainCodegen.addImports(entity.getFullQueriesClassName());
        }
    }
}
