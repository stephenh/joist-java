package joist.domain.codegen.passes;

import joist.domain.codegen.Codegen;
import joist.domain.codegen.InformationSchemaColumn;
import joist.domain.codegen.dtos.Entity;
import joist.domain.codegen.dtos.PrimitiveProperty;

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
