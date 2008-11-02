package org.exigencecorp.domainobjects.codegen.passes;

import org.exigencecorp.domainobjects.Code;
import org.exigencecorp.domainobjects.codegen.Codegen;
import org.exigencecorp.domainobjects.codegen.dtos.Entity;
import org.exigencecorp.domainobjects.codegen.dtos.EnumCode;
import org.exigencecorp.domainobjects.codegen.dtos.EnumEntity;
import org.exigencecorp.gen.GClass;
import org.exigencecorp.gen.GMethod;

public class GenerateCodesPass implements Pass {

    public void pass(Codegen codegen) {
        for (Entity entity : codegen.getEntities().values()) {
            if (!entity.isEnum()) {
                continue;
            }

            GClass code = codegen.getOutputCodegenDirectory().getClass(entity.getFullClassName());
            code.setEnum().implementsInterface(Code.class);
            this.addFieldsAndConstructor(code);
            this.addValues((EnumEntity) entity, code);
            this.addFromId((EnumEntity) entity, code);
        }
    }

    private void addFieldsAndConstructor(GClass code) {
        code.getField("id").type(Integer.class).makeGetter();
        code.getField("code").type(String.class).makeGetter();
        code.getField("name").type(String.class).makeGetter();

        GMethod c = code.getConstructor("Integer id", "String code", "String name").isPrivate();
        c.body.line("this.id = id;");
        c.body.line("this.code = code;");
        c.body.line("this.name = name;");
    }

    private void addValues(EnumEntity entity, GClass code) {
        for (EnumCode value : entity.getCodes()) {
            code.addEnumValue("{}({}, \"{}\", \"{}\")", value.getEnumName(), value.id, value.code, value.name);
        }
    }

    private void addFromId(EnumEntity entity, GClass code) {
        GMethod from = code.getMethod("fromId").returnType(entity.getClassName()).arguments("Integer id").isStatic();
        from.body.line("return org.exigencecorp.domainobjects.util.Codes.fromInt({}.values(), id);", entity.getClassName());
    }

}
