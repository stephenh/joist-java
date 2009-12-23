package joist.codegen.passes;

import joist.codegen.Codegen;
import joist.sourcegen.GClass;
import joist.sourcegen.GField;

public class GenerateSchemaHash implements Pass {

    public void pass(Codegen codegen) {
        GClass schemaHash = codegen.getOutputCodegenDirectory().getClass(codegen.getConfig().getDomainObjectPackage() + ".SchemaHash");
        GField field = schemaHash.getField("hashCode").setPublic().setStatic().setFinal().type("int");
        field.initialValue("{}", codegen.getSchemaHashCode());
    }

}
