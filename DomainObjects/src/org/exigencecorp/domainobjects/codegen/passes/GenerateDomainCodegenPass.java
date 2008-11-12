package org.exigencecorp.domainobjects.codegen.passes;

import java.util.ArrayList;
import java.util.List;

import org.exigencecorp.domainobjects.Id;
import org.exigencecorp.domainobjects.Shim;
import org.exigencecorp.domainobjects.codegen.Codegen;
import org.exigencecorp.domainobjects.codegen.dtos.Entity;
import org.exigencecorp.domainobjects.codegen.dtos.ManyToManyProperty;
import org.exigencecorp.domainobjects.codegen.dtos.ManyToOneProperty;
import org.exigencecorp.domainobjects.codegen.dtos.OneToManyProperty;
import org.exigencecorp.domainobjects.codegen.dtos.PrimitiveProperty;
import org.exigencecorp.domainobjects.orm.AliasRegistry;
import org.exigencecorp.domainobjects.orm.ForeignKeyCodeHolder;
import org.exigencecorp.domainobjects.orm.ForeignKeyHolder;
import org.exigencecorp.domainobjects.queries.Select;
import org.exigencecorp.domainobjects.uow.UoW;
import org.exigencecorp.domainobjects.validation.rules.MaxLength;
import org.exigencecorp.domainobjects.validation.rules.NotNull;
import org.exigencecorp.gen.GClass;
import org.exigencecorp.gen.GField;
import org.exigencecorp.gen.GMethod;
import org.exigencecorp.util.Copy;

public class GenerateDomainCodegenPass implements Pass {

    public void pass(Codegen codegen) {
        for (Entity entity : codegen.getEntities().values()) {
            if (entity.isCodeEntity()) {
                continue;
            }

            GClass domainCodegen = codegen.getOutputCodegenDirectory().getClass(entity.getFullCodegenClassName());
            domainCodegen.setAbstract();
            domainCodegen.baseClassName(entity.getParentClassName());

            domainCodegen.getConstructor().setProtected().body.line("this.addExtraRules();");
            domainCodegen.getMethod("addExtraRules").setPrivate();

            this.addAlias(domainCodegen, entity);
            this.primitiveProperties(domainCodegen, entity);
            this.manyToOneProperties(domainCodegen, entity);
            this.oneToManyProperties(domainCodegen, entity);
            this.manyToManyProperties(domainCodegen, entity);
        }
    }

    private void addAlias(GClass domainCodegen, Entity entity) {
        if (!entity.isCodeEntity()) {
            domainCodegen.staticInitializer.line("AliasRegistry.register({}.class, new {}Alias(\"a\"));",//
                entity.getClassName(),
                entity.getClassName());
            domainCodegen.addImports(AliasRegistry.class);
            domainCodegen.addImports(entity.getFullAliasClassName());
        }
    }

    private void primitiveProperties(GClass domainCodegen, Entity entity) {
        for (PrimitiveProperty p : entity.getPrimitiveProperties()) {
            GField field = domainCodegen.getField(p.getVariableName());
            field.type(p.getJavaType());
            field.initialValue(p.getDefaultJavaString());
            field.makeGetter();

            if (!"version".equals(p.getColumnName())) {
                GMethod setter = domainCodegen.getMethod("set" + p.getCapitalVariableName());
                setter.argument(p.getJavaType(), p.getVariableName());
                setter.body.line("this.recordIfChanged(\"{}\", this.{}, {});", p.getVariableName(), p.getVariableName(), p.getVariableName());
                setter.body.line("this.{} = {};", p.getVariableName(), p.getVariableName());
            }

            GClass shims = domainCodegen.getInnerClass("Shims");
            GField shimField = shims.getField(p.getVariableName()).setPublic().setStatic().setFinal();
            shimField.type("Shim<" + entity.getClassName() + ", " + p.getJavaTypeNonPrimitive() + ">");
            GClass shimClass = shimField.initialAnonymousClass();

            GMethod shimSetter = shimClass.getMethod("set");
            shimSetter.argument(entity.getClassName(), "instance").argument(p.getJavaTypeNonPrimitive(), p.getVariableName());
            shimSetter.body.line("(({}) instance).{} = {};", entity.getCodegenClassName(), p.getVariableName(), p.getVariableName());

            GMethod shimGetter = shimClass.getMethod("get");
            shimGetter.argument(entity.getClassName(), "instance");
            shimGetter.returnType(p.getJavaTypeNonPrimitive());
            shimGetter.body.line("return (({}) instance).{};", entity.getCodegenClassName(), p.getVariableName());

            if (p.shouldHaveNotNullRule()) {
                domainCodegen.getMethod("addExtraRules").body.line("this.addRule(new NotNull<{}>(\"{}\", Shims.{}));",//
                    entity.getClassName(),
                    p.getVariableName(),
                    p.getVariableName());
                domainCodegen.addImports(NotNull.class);
            }

            if (p.getMaxCharacterLength() != 0) {
                domainCodegen.getMethod("addExtraRules").body.line("this.addRule(new MaxLength<{}>(\"{}\", {}, Shims.{}));",//
                    entity.getClassName(),
                    p.getVariableName(),
                    p.getMaxCharacterLength(),
                    p.getVariableName());
                domainCodegen.addImports(MaxLength.class);
            }

            if (p.getColumnName().equals("id")) {
                domainCodegen.addImports(Id.class);
            }
            domainCodegen.addImports(Shim.class);
        }
    }

    private void manyToOneProperties(GClass domainCodegen, Entity entity) {
        for (ManyToOneProperty mtop : entity.getManyToOneProperties()) {
            GField field = domainCodegen.getField(mtop.getVariableName());
            if (mtop.getManySide().isCodeEntity()) {
                field.type("ForeignKeyCodeHolder<" + mtop.getJavaType() + ">");
                field.initialValue("new ForeignKeyCodeHolder<" + mtop.getJavaType() + ">(" + mtop.getJavaType() + ".class)");
                domainCodegen.addImports(ForeignKeyCodeHolder.class);
            } else {
                field.type("ForeignKeyHolder<" + mtop.getJavaType() + ">");
                field.initialValue("new ForeignKeyHolder<" + mtop.getJavaType() + ">(" + mtop.getJavaType() + ".class)");
                domainCodegen.addImports(ForeignKeyHolder.class);
            }

            GMethod getter = domainCodegen.getMethod("get" + mtop.getCapitalVariableName());
            getter.returnType(mtop.getJavaType());
            getter.body.line("return this.{}.get();", mtop.getVariableName());

            GMethod setter = domainCodegen.getMethod("set{}", mtop.getCapitalVariableName());
            setter.argument(mtop.getJavaType(), mtop.getVariableName());
            if (!mtop.getManySide().isCodeEntity()) {
                setter.body.line("if (this.{}.get() != null) {", mtop.getVariableName());
                setter.body.line("   this.{}.get().remove{}WithoutPercolation(({}) this);",//
                    mtop.getVariableName(),
                    mtop.getOneToManyProperty().getCapitalVariableNameSingular(),
                    entity.getClassName());
                setter.body.line("}");
            }
            setter.body.line("this.set{}WithoutPercolation({});", mtop.getCapitalVariableName(), mtop.getVariableName());
            if (!mtop.getManySide().isCodeEntity()) {
                setter.body.line("if (this.{}.get() != null) {", mtop.getVariableName());
                setter.body.line("   this.{}.get().add{}WithoutPercolation(({}) this);",//
                    mtop.getVariableName(),
                    mtop.getOneToManyProperty().getCapitalVariableNameSingular(),
                    entity.getClassName());
                setter.body.line("}");
            }

            GMethod setter2 = domainCodegen.getMethod("set{}WithoutPercolation", mtop.getCapitalVariableName());
            setter2.argument(mtop.getJavaType(), mtop.getVariableName());
            setter2.body.line("this.recordIfChanged(\"{}\", this.{}, {});", mtop.getVariableName(), mtop.getVariableName(), mtop.getVariableName());
            setter2.body.line("this.{}.set({});", mtop.getVariableName(), mtop.getVariableName());

            GClass shims = domainCodegen.getInnerClass("Shims");
            GField shimField = shims.getField(mtop.getVariableName() + "Id").setPublic().setStatic().setFinal();
            shimField.type("Shim<" + entity.getClassName() + ", Integer>");
            GClass shimClass = shimField.initialAnonymousClass();

            GMethod shimSetter = shimClass.getMethod("set");
            shimSetter.argument(entity.getClassName(), "instance").argument("Integer", mtop.getVariableName() + "Id");
            shimSetter.body.line("(({}) instance).{}.setId({}Id);", entity.getCodegenClassName(), mtop.getVariableName(), mtop.getVariableName());

            GMethod shimGetter = shimClass.getMethod("get");
            shimGetter.argument(entity.getClassName(), "instance");
            shimGetter.returnType("Integer");
            shimGetter.body.line(0, "return (({}) instance).{}.getId();", entity.getCodegenClassName(), mtop.getVariableName());

            domainCodegen.addImports(Shim.class);
        }
    }

    private void oneToManyProperties(GClass domainCodegen, Entity entity) {
        for (OneToManyProperty otmp : entity.getOneToManyProperties()) {
            domainCodegen.getField(otmp.getVariableName()).type(otmp.getJavaType());
            domainCodegen.addImports(List.class, ArrayList.class, UoW.class);

            GMethod getter = domainCodegen.getMethod("get" + otmp.getCapitalVariableName()).returnType(otmp.getJavaType());
            getter.body.line("if (this.{} == null) {", otmp.getVariableName());
            getter.body.line("    if (UoW.isOpen() && this.getId() != null) {");
            getter.body.line("        {}Alias a = new {}Alias(\"a\");", otmp.getTargetJavaType(), otmp.getTargetJavaType());
            getter.body.line("        this.{} = Select.from(a).where(a.{}.equals(this.getId())).orderBy(a.id.asc()).list();",//
                otmp.getVariableName(),
                otmp.getKeyFieldName(),
                otmp.getTargetJavaType());
            getter.body.line("    } else {");
            getter.body.line("        this.{} = {};", otmp.getVariableName(), otmp.getDefaultJavaString());
            getter.body.line("    }");
            getter.body.line("}");
            getter.body.line("return this.{};", otmp.getVariableName());

            GMethod adder = domainCodegen.getMethod("add{}", otmp.getCapitalVariableNameSingular());
            adder.argument(otmp.getTargetJavaType(), "o");
            adder.body.line("o.set{}WithoutPercolation(({}) this);", otmp.getForeignKeyColumn().getCapitalVariableName(), entity.getClassName());
            adder.body.line("this.add{}WithoutPercolation(o);", otmp.getCapitalVariableNameSingular());

            GMethod adder2 = domainCodegen.getMethod("add{}WithoutPercolation", otmp.getCapitalVariableNameSingular());
            adder2.argument(otmp.getTargetJavaType(), "o");
            adder2.body.line("this.get{}(); // hack", otmp.getCapitalVariableName());
            adder2.body.line("this.recordIfChanged(\"{}\");", otmp.getVariableName());
            adder2.body.line("this.{}.add(o);", otmp.getVariableName());

            GMethod remover = domainCodegen.getMethod("remove{}", otmp.getCapitalVariableNameSingular());
            remover.argument(otmp.getTargetJavaType(), "o");
            remover.body.line("o.set{}WithoutPercolation(null);", otmp.getForeignKeyColumn().getCapitalVariableName(), entity.getClassName());
            remover.body.line("this.remove{}WithoutPercolation(o);", otmp.getCapitalVariableNameSingular());

            GMethod remover2 = domainCodegen.getMethod("remove{}WithoutPercolation", otmp.getCapitalVariableNameSingular());
            remover2.argument(otmp.getTargetJavaType(), "o");
            remover2.body.line("this.get{}(); // hack", otmp.getCapitalVariableName());
            remover2.body.line("this.recordIfChanged(\"{}\");", otmp.getVariableName());
            remover2.body.line("this.{}.remove(o);", otmp.getVariableName());

            domainCodegen.addImports(otmp.getOneSide().getFullAliasClassName(), Select.class.getName());
        }
    }

    private void manyToManyProperties(GClass domainCodegen, Entity entity) {
        for (ManyToManyProperty mtmp : entity.getManyToManyProperties()) {
            GMethod getter = domainCodegen.getMethod("get" + mtmp.getCapitalVariableName()).returnType(mtmp.getJavaType());
            getter.body.line("{} l = {};", mtmp.getJavaType(), mtmp.getDefaultJavaString());
            getter.body.line("for ({} o : this.get{}s()) {", mtmp.getJoinTable().getClassName(), mtmp.getJoinTable().getClassName());
            getter.body.line("    l.add(o.get{}());", mtmp.getCapitalVariableNameSingular());
            getter.body.line("}");
            getter.body.line("return l;");

            GMethod adder = domainCodegen.getMethod("add{}", mtmp.getCapitalVariableNameSingular());
            adder.argument(mtmp.getTargetTable().getClassName(), "o");
            adder.body.line("{} a = new {}();", mtmp.getJoinTable().getClassName(), mtmp.getJoinTable().getClassName());
            adder.body.line("a.set{}(({}) this);", entity.getClassName(), entity.getClassName());
            adder.body.line("a.set{}(o);", mtmp.getTargetTable().getClassName());

            GMethod remover = domainCodegen.getMethod("remove{}", mtmp.getCapitalVariableNameSingular());
            remover.argument(mtmp.getTargetTable().getClassName(), "o");
            remover.body.line("for ({} a : Copy.shallow(this.get{}s())) {", mtmp.getJoinTable().getClassName(), mtmp.getJoinTable().getClassName());
            remover.body.line("    if (a.get{}().equals(o)) {", mtmp.getCapitalVariableNameSingular());
            remover.body.line("        a.set{}(null);", mtmp.getCapitalVariableNameSingular());
            remover.body.line("        a.set{}(null);", mtmp.getOther().getCapitalVariableNameSingular());
            remover.body.line("        UoW.getCurrent().delete(a);");
            remover.body.line("    }");
            remover.body.line("}");

            domainCodegen.addImports(Copy.class);
        }
    }

}
