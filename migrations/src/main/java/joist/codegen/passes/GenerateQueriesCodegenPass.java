package joist.codegen.passes;

import joist.codegen.Codegen;
import joist.codegen.dtos.Entity;
import joist.codegen.dtos.ManyToOneProperty;
import joist.codegen.dtos.OneToManyProperty;
import joist.codegen.dtos.PrimitiveProperty;
import joist.domain.orm.queries.Select;
import joist.sourcegen.Argument;
import joist.sourcegen.GClass;
import joist.sourcegen.GMethod;
import joist.util.Copy;

public class GenerateQueriesCodegenPass implements Pass<Codegen> {

  public void pass(Codegen codegen) {
    for (Entity entity : codegen.getSchema().getEntities().values()) {
      if (entity.isCodeEntity()) {
        continue;
      }

      GClass queriesCodegen = this.createCodegenOutput(codegen, entity);

      this.setupBaseClassAndConstructor(codegen, entity, queriesCodegen);
      this.addDelete(entity, queriesCodegen);
      this.addFindByForUniquePrimitives(entity, queriesCodegen);
      this.addFindByForCodes(entity, queriesCodegen);
    }
  }

  private GClass createCodegenOutput(Codegen codegen, Entity entity) {
    return codegen.getOutputCodegenDirectory().getClass(entity.getFullQueriesCodegenClassName());
  }

  private void setupBaseClassAndConstructor(Codegen codegen, Entity entity, GClass queriesCodegen) {
    queriesCodegen.setAbstract().setPackagePrivate();
    queriesCodegen.baseClassName(codegen.getConfig().getQueriesBaseClass(), entity.getClassName());
    queriesCodegen.addImports(entity.getFullClassName());
    queriesCodegen.getConstructor().body.line("super({}.class);", entity.getClassName());
  }

  private void addDelete(Entity entity, GClass queriesCodegen) {
    GMethod delete = queriesCodegen.getMethod("delete").argument(entity.getClassName(), "instance");

    for (OneToManyProperty otmp : entity.getOneToManyProperties()) {
      if (otmp.isOwnerMe() && !otmp.isManyToMany()) {
        if (otmp.isOneToOne()) {
          delete.body.line("if (instance.get{}() != null) {", otmp.getCapitalVariableNameSingular());
          delete.body.line("_   {}.queries.delete(instance.get{}());", otmp.getTargetJavaType(), otmp.getCapitalVariableNameSingular());
          delete.body.line("}");
          queriesCodegen.addImports(otmp.getManySide().getFullClassName());
        } else {
          delete.body.line("for ({} o : Copy.list(instance.get{}())) {", otmp.getTargetJavaType(), otmp.getCapitalVariableName());
          delete.body.line("_   {}.queries.delete(o);", otmp.getTargetJavaType());
          delete.body.line("}");
          queriesCodegen.addImports(otmp.getManySide().getFullClassName(), Copy.class.getName());
        }
      }
    }

    for (ManyToOneProperty mtop : entity.getManyToOneProperties()) {
      if (mtop.isOwnerMe() && !mtop.getOneSide().isCodeEntity()) {
        delete.body.line("if (instance.get{}() != null) {", mtop.getCapitalVariableName());
        delete.body.line("_   {}.queries.delete(instance.get{}());", mtop.getJavaType(), mtop.getCapitalVariableName());
        delete.body.line("}");
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

  private void addFindByForUniquePrimitives(Entity entity, GClass queriesCodegen) {
    for (PrimitiveProperty p : entity.getPrimitiveProperties()) {
      if (p.isUnique()) {
        GMethod findBy = queriesCodegen.getMethod("findBy" + p.getCapitalVariableName(), Argument.arg(p.getJavaType(), p.getVariableName()));
        findBy.returnType(entity.getClassName());
        findBy.body.line("{} {} = new {}(\"{}\");", entity.getAliasName(), entity.getAliasAlias(), entity.getAliasName(), entity.getAliasAlias());
        findBy.body.line(
          "return Select.from({}).where({}.{}.eq({})).unique();",
          entity.getAliasAlias(),
          entity.getAliasAlias(),
          p.getVariableName(),
          p.getVariableName());
        queriesCodegen.addImports(entity.getFullAliasClassName());
        queriesCodegen.addImports(Select.class);
      }
    }
  }

  private void addFindByForCodes(Entity entity, GClass queriesCodegen) {
    for (ManyToOneProperty mtop : entity.getManyToOneProperties()) {
      if (mtop.getOneSide().isCodeEntity()) {
        GMethod findBy = queriesCodegen.getMethod("findBy" + mtop.getCapitalVariableName(), Argument.arg(mtop.getJavaType(), mtop.getVariableName()));
        findBy.returnType("java.util.List<{}>", entity.getClassName());
        findBy.body.line("{} {} = new {}();", entity.getAliasName(), entity.getAliasAlias(), entity.getAliasName());
        findBy.body.line(
          "return Select.from({}).where({}.{}.eq({})).list();",
          entity.getAliasAlias(),
          entity.getAliasAlias(),
          mtop.getVariableName(),
          mtop.getVariableName());

        GMethod findIdsBy = queriesCodegen.getMethod(
          "findIdsBy" + mtop.getCapitalVariableName(),
          Argument.arg(mtop.getJavaType(), mtop.getVariableName()));
        findIdsBy.returnType("java.util.List<Long>");
        findIdsBy.body.line("{} {} = new {}();", entity.getAliasName(), entity.getAliasAlias(), entity.getAliasName());
        findIdsBy.body.line(
          "return Select.from({}).where({}.{}.eq({})).listIds();",
          entity.getAliasAlias(),
          entity.getAliasAlias(),
          mtop.getVariableName(),
          mtop.getVariableName());

        queriesCodegen.addImports(entity.getFullAliasClassName(), mtop.getOneSide().getFullClassName());
        queriesCodegen.addImports(Select.class);
      }
    }
  }

}
