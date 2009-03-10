package joist.domain.codegen.passes;

import joist.domain.Code;
import joist.domain.codegen.Codegen;
import joist.domain.codegen.dtos.CodeEntity;
import joist.domain.codegen.dtos.CodeValue;
import joist.domain.codegen.dtos.Entity;
import joist.domain.util.Codes;

import org.exigencecorp.gen.GClass;
import org.exigencecorp.gen.GMethod;

public class GenerateCodesPass implements Pass {

    public void pass(Codegen codegen) {
        for (Entity entity : codegen.getEntities().values()) {
            if (!entity.isCodeEntity()) {
                continue;
            }

            GClass code = codegen.getOutputCodegenDirectory().getClass(entity.getFullClassName());
            code.setEnum().implementsInterface(Code.class);
            this.addFieldsAndConstructor(code);
            this.addValues((CodeEntity) entity, code);
            this.addFromId((CodeEntity) entity, code);
        }
    }

    private void addFieldsAndConstructor(GClass code) {
        code.getField("id").type(Integer.class).makeGetter();
        code.getField("code").type(String.class).makeGetter();
        code.getField("name").type(String.class).makeGetter();

        GMethod c = code.getConstructor("Integer id", "String code", "String name").setPrivate();
        c.body.line("this.id = id;");
        c.body.line("this.code = code;");
        c.body.line("this.name = name;");
    }

    private void addValues(CodeEntity entity, GClass code) {
        for (CodeValue value : entity.getCodes()) {
            code.addEnumValue("{}({}, \"{}\", \"{}\")", value.getEnumName(), value.id, value.code, value.name);
        }
    }

    private void addFromId(CodeEntity entity, GClass code) {
        GMethod from = code.getMethod("fromId").returnType(entity.getClassName()).arguments("Integer id").setStatic();
        from.body.line("return {}.fromInt({}.values(), id);", Codes.class.getName(), entity.getClassName());
    }

}
