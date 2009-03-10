package joist.domain.codegen.passes;

import joist.domain.codegen.Codegen;

import org.exigencecorp.gen.GClass;
import org.exigencecorp.gen.GField;

public class GenerateSchemaHash implements Pass {

    public void pass(Codegen codegen) {
        GClass schemaHash = codegen.getOutputCodegenDirectory().getClass(codegen.getConfig().getDomainObjectPackage() + ".SchemaHash");
        GField field = schemaHash.getField("hashCode").setPublic().setStatic().setFinal().type("int");
        field.initialValue("{}", codegen.getSchemaHashCode());
    }

}
