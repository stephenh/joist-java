package joist.codegen.passes;

import joist.codegen.InformationSchemaColumn;
import joist.codegen.Schema;
import joist.codegen.dtos.Entity;
import joist.codegen.dtos.PrimitiveProperty;

public class FindPrimitivePropertiesPass implements Pass<Schema> {

  public void pass(Schema schema) {
    for (InformationSchemaColumn column : schema.getColumns()) {
      if (column.foreignKeyConstraintName != null) {
        continue;
      }

      Entity entity = schema.getEntity(column);
      if (entity == null) {
        continue;
      }

      PrimitiveProperty p = new PrimitiveProperty(schema.getConfig(), entity, column);
      if (schema.getConfig().isPropertySkipped(entity.getClassName(), p.getVariableName())) {
        continue;
      }
      entity.getPrimitiveProperties().add(p);
    }
  }

}
