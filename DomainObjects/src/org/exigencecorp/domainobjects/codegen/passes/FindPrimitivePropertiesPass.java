package org.exigencecorp.domainobjects.codegen.passes;

import org.exigencecorp.domainobjects.codegen.Codegen;
import org.exigencecorp.domainobjects.codegen.InformationSchemaColumn;
import org.exigencecorp.domainobjects.codegen.dtos.Entity;
import org.exigencecorp.domainobjects.codegen.dtos.PrimitiveProperty;

public class FindPrimitivePropertiesPass implements Pass {

    public void pass(Codegen codegen) {
        for (InformationSchemaColumn column : codegen.getColumns()) {
            if (column.foreignKeyConstraintName != null) {
                continue;
            }

            Entity entity = codegen.getEntity(column);
            if (entity == null) {
                continue;
            }

            entity.getPrimitiveProperties().add(new PrimitiveProperty(codegen, entity, column));
        }
    }

}
