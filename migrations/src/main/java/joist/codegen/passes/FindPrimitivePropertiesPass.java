package joist.codegen.passes;

import joist.codegen.Codegen;
import joist.codegen.InformationSchemaColumn;
import joist.codegen.dtos.Entity;
import joist.codegen.dtos.PrimitiveProperty;

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

      PrimitiveProperty p = new PrimitiveProperty(codegen, entity, column);
      if (codegen.getConfig().isPropertySkipped(entity.getClassName(), p.getVariableName())) {
        continue;
      }
      entity.getPrimitiveProperties().add(p);
    }
  }

}
