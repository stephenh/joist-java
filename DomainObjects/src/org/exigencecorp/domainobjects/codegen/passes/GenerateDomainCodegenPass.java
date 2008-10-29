package org.exigencecorp.domainobjects.codegen.passes;

import java.util.ArrayList;
import java.util.List;

import org.exigencecorp.domainobjects.Id;
import org.exigencecorp.domainobjects.Shim;
import org.exigencecorp.domainobjects.codegen.Codegen;
import org.exigencecorp.domainobjects.codegen.dtos.Entity;
import org.exigencecorp.domainobjects.codegen.dtos.ManyToOneProperty;
import org.exigencecorp.domainobjects.codegen.dtos.OneToManyProperty;
import org.exigencecorp.domainobjects.codegen.dtos.PrimitiveProperty;
import org.exigencecorp.domainobjects.queries.Alias;
import org.exigencecorp.domainobjects.queries.Select;
import org.exigencecorp.domainobjects.uow.UoW;
import org.exigencecorp.gen.GClass;
import org.exigencecorp.gen.GField;
import org.exigencecorp.gen.GMethod;

public class GenerateDomainCodegenPass implements Pass {

    public void pass(Codegen codegen) {
        for (Entity entity : codegen.getEntities().values()) {
            if (entity.isEnum()) {
                continue;
            }

            GClass domainCodegen = codegen.getOutputCodegenDirectory().getClass(entity.getFullCodegenClassName());
            domainCodegen.isAbstract();
            domainCodegen.baseClassName(entity.getParentClassName());

            this.addAlias(domainCodegen, entity);
            this.primitiveProperties(domainCodegen, entity);
            this.manyToOneProperties(domainCodegen, entity);
            this.oneToManyProperties(domainCodegen, entity);
            this.manyToManyProperties(domainCodegen, entity);
        }
    }

    private void addAlias(GClass domainCodegen, Entity entity) {
        GMethod m = domainCodegen.getMethod("newAlias");
        m.returnType("Alias<? extends {}>", entity.getClassName()).arguments("String alias");
        m.body.line("return new {}Alias(alias);", entity.getClassName());
        domainCodegen.addImports(Alias.class);
        domainCodegen.addImports(entity.getConfig().getMapperPackage() + "." + entity.getClassName() + "Alias");
    }

    private void primitiveProperties(GClass domainCodegen, Entity entity) {
        for (PrimitiveProperty p : entity.getPrimitiveProperties()) {
            GField field = domainCodegen.getField(p.getVariableName());
            field.type(p.getJavaType());
            field.initialValue(p.getDefaultJavaString());

            if (p.getColumnName().equals("id")) {
                domainCodegen.addImports(Id.class);
            }

            GMethod getter = domainCodegen.getMethod("get" + p.getCapitalVariableName());
            getter.returnType(p.getJavaType());
            getter.body.append("return this.{};", p.getVariableName());

            if (!"version".equals(p.getColumnName())) {
                GMethod setter = domainCodegen.getMethod("set" + p.getCapitalVariableName());
                setter.argument(p.getJavaType(), p.getVariableName());
                setter.body.line("this.recordIfChanged('{}', this.{}, {});", p.getVariableName(), p.getVariableName(), p.getVariableName());
                setter.body.line("this.{} = {};", p.getVariableName(), p.getVariableName());
            }

            GClass shims = domainCodegen.getInnerClass("Shims");
            GField shimField = shims.getField(p.getVariableName()).isPublic().isStatic().isFinal();
            shimField.type("Shim<" + entity.getClassName() + ", " + p.getJavaTypeNonPrimitive() + ">");
            GClass shimClass = shimField.initialAnonymousClass();

            GMethod shimSetter = shimClass.getMethod("set");
            shimSetter.argument(entity.getClassName(), "instance").argument(p.getJavaTypeNonPrimitive(), p.getVariableName());
            shimSetter.body.line("(({}) instance).{} = {};", entity.getCodegenClassName(), p.getVariableName(), p.getVariableName());

            GMethod shimGetter = shimClass.getMethod("get");
            shimGetter.argument(entity.getClassName(), "instance");
            shimGetter.returnType(p.getJavaTypeNonPrimitive());
            shimGetter.body.line("return (({}) instance).{};", entity.getCodegenClassName(), p.getVariableName());

            domainCodegen.addImports(Shim.class);
        }
    }

    private void manyToOneProperties(GClass domainCodegen, Entity entity) {
        for (ManyToOneProperty mtop : entity.getManyToOneProperties()) {
            GField objectField = domainCodegen.getField(mtop.getVariableName());
            objectField.type(mtop.getJavaType()).initialValue(mtop.getDefaultJavaString());

            GField idField = domainCodegen.getField(mtop.getVariableName() + "Id");
            idField.type("Integer").initialValue("null");

            GMethod getter = domainCodegen.getMethod("get" + mtop.getCapitalVariableName());
            getter.returnType(mtop.getJavaType());
            if (mtop.getManySide().isEnum()) {
                getter.body.line("if (this.{} == null && this.{}Id != null) {", mtop.getVariableName(), mtop.getVariableName());
                getter.body.line("    this.{} = {}.fromId(this.{}Id);", mtop.getVariableName(), mtop.getJavaType(), mtop.getVariableName());
                getter.body.line("}");
                getter.body.line("return this.{};", mtop.getVariableName());
            } else {
                getter.body.line("if (this.{} == null && this.{}Id != null && UoW.isOpen()) {", mtop.getVariableName(), mtop.getVariableName());
                getter.body.line("    {}Alias a = new {}Alias(\"a\");", mtop.getJavaType(), mtop.getJavaType());
                getter.body.line("    this.{} = Select.from(a).where(a.id.equals(this.{}Id)).unique();",//
                    mtop.getVariableName(),
                    mtop.getVariableName());
                getter.body.line("}");
                getter.body.line("return this.{};", mtop.getVariableName());
                domainCodegen.addImports(UoW.class, Select.class);
                domainCodegen.addImports(mtop.getManySide().getFullAliasClassName());
            }

            GMethod setter = domainCodegen.getMethod("set" + mtop.getCapitalVariableName());
            setter.argument(mtop.getJavaType(), mtop.getVariableName());
            setter.body.line("this.recordIfChanged(\"{}\", this.{}, {});", mtop.getVariableName(), mtop.getVariableName(), mtop.getVariableName());
            setter.body.line("this.{} = {};", mtop.getVariableName(), mtop.getVariableName());

            GClass shims = domainCodegen.getInnerClass("Shims");
            GField shimField = shims.getField(mtop.getVariableName() + "Id").isPublic().isStatic().isFinal();
            shimField.type("Shim<" + entity.getClassName() + ", Integer>");
            GClass shimClass = shimField.initialAnonymousClass();

            GMethod shimSetter = shimClass.getMethod("set");
            shimSetter.argument(entity.getClassName(), "instance").argument("Integer", mtop.getVariableName() + "Id");
            shimSetter.body.line("(({}) instance).{}Id = {}Id;", entity.getCodegenClassName(), mtop.getVariableName(), mtop.getVariableName());

            GMethod shimGetter = shimClass.getMethod("get");
            shimGetter.argument(entity.getClassName(), "instance");
            shimGetter.returnType("Integer");
            shimGetter.body.line(0, "{} instanceCodegen = instance;", entity.getCodegenClassName());
            shimGetter.body.line(0, "if (instanceCodegen.{} != null) {", mtop.getVariableName());
            shimGetter.body.line(1, "return instanceCodegen.{}.getId().intValue();", mtop.getVariableName());
            shimGetter.body.line(0, "}", mtop.getVariableName());
            shimGetter.body.line(0, "return (({}) instance).{}Id;", entity.getCodegenClassName(), mtop.getVariableName());

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
            getter.body.line("        {}Alias a = new {}Alias('a');", otmp.getTargetJavaType(), otmp.getTargetJavaType());
            getter.body.line("        this.{} = Select.from(a).where(a.{}.equals(this.getId())).orderBy(a.id.asc()).list();",//
                otmp.getVariableName(),
                otmp.getKeyFieldName(),
                otmp.getTargetJavaType());
            getter.body.line("    } else {");
            getter.body.line("        this.{} = {};", otmp.getVariableName(), otmp.getDefaultJavaString());
            getter.body.line("    }");
            getter.body.line("}");
            getter.body.line("return this.{};", otmp.getVariableName());
            domainCodegen.addImports(otmp.getOneSide().getFullAliasClassName(), Select.class.getName());
        }
    }

    private void manyToManyProperties(GClass domainCodegen, Entity entity) {
        /*
        for (ManyToManyProperty mtmp : entity.getManyToManyProperties()) {
            // Collection field
            domainCodegen.getField(mtmp.getVariableName()).type(mtmp.getJavaType());
            domainCodegen.addImports(List.class, ArrayList.class, UoW.class);

            GMethod getter = domainCodegen.getMethod("get" + mtmp.getCapitalVariableName()).returnType(mtmp.getJavaType());
            getter.body.line("if (this.{} == null) {", mtmp.getVariableName());
            getter.body.line("    if (UoW.isOpen() && this.getId() != null) {");
            getter.body.line("        {}Alias a = new {}Alias('a');", mtmp.getTargetJavaType(), mtmp.getTargetJavaType());
            getter.body.line("        this.{} = Select.from(a).where(a.{}.equals(this.getId())).orderBy(a.id.asc()).list();",//
                mtmp.getVariableName(),
                mtmp.getMyKeyColumnName(),
                mtmp.getTargetJavaType());
            getter.body.line("    } else {");
            getter.body.line("        this.{} = {};", mtmp.getVariableName(), mtmp.getDefaultJavaString());
            getter.body.line("    }");
            getter.body.line("}");
            getter.body.line("return this.{};", mtmp.getVariableName());
            domainCodegen.addImports(mtmp.getTargetTable().getFullAliasClassName(), Select.class.getName());
        }
        */
    }
}
