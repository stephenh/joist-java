package joist.domain.codegen.passes;

import joist.domain.codegen.Codegen;
import joist.domain.codegen.dtos.Entity;
import joist.domain.codegen.dtos.ManyToOneProperty;
import joist.domain.codegen.dtos.OneToManyProperty;
import joist.sourcegen.GClass;
import joist.sourcegen.GMethod;
import joist.util.Copy;

public class GenerateQueriesCodegenPass implements Pass {

    public void pass(Codegen codegen) {
        for (Entity entity : codegen.getEntities().values()) {
            if (entity.isCodeEntity()) {
                continue;
            }

            GClass queriesCodegen = this.createCodegenOutput(codegen, entity);

            this.setupBaseClassAndConstructor(codegen, entity, queriesCodegen);
            this.addDelete(codegen, entity, queriesCodegen);
        }
    }

    private GClass createCodegenOutput(Codegen codegen, Entity entity) {
        return codegen.getOutputCodegenDirectory().getClass(entity.getFullQueriesCodegenClassName());
    }

    private void setupBaseClassAndConstructor(Codegen codegen, Entity entity, GClass queriesCodegen) {
        queriesCodegen.setAbstract();
        queriesCodegen.baseClassName(codegen.getConfig().getQueriesBaseClass(), entity.getClassName());
        queriesCodegen.addImports(entity.getFullClassName());
        queriesCodegen.getConstructor().body.line("super({}.class);", entity.getClassName());
    }

    private void addDelete(Codegen codegen, Entity entity, GClass queriesCodegen) {
        GMethod delete = queriesCodegen.getMethod("delete").argument(entity.getClassName(), "instance");

        for (OneToManyProperty otmp : entity.getOneToManyProperties()) {
            if (otmp.isOwnerMe()) {
                if (otmp.isOneToOne()) {
                    delete.body.line("{}.queries.delete(instance.get{}());", otmp.getTargetJavaType(), otmp.getCapitalVariableNameSingular());
                    queriesCodegen.addImports(otmp.getManySide().getFullClassName());
                } else {
                    delete.body.line("for ({} o : Copy.list(instance.get{}())) {", otmp.getTargetJavaType(), otmp.getCapitalVariableName());
                    delete.body.line("    {}.queries.delete(o);", otmp.getTargetJavaType());
                    delete.body.line("}");
                    queriesCodegen.addImports(otmp.getManySide().getFullClassName(), Copy.class.getName());
                }
            }
        }

        for (ManyToOneProperty mtop : entity.getManyToOneProperties()) {
            if (mtop.isOwnerMe()) {
                delete.body.line("{}.queries.delete(instance.get{}());", mtop.getJavaType(), mtop.getCapitalVariableName());
                queriesCodegen.addImports(mtop.getOneSide().getFullClassName());
            }
        }

        if (entity.isRoot()) {
            delete.body.line("super.delete(instance);");
        } else {
            delete.body.line("{}.queries.delete(instance);", entity.getBaseEntity().getClassName());
            queriesCodegen.addImports(entity.getBaseEntity().getFullClassName());
        }
    }

}
