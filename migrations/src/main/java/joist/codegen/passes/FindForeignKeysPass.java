package joist.codegen.passes;

import joist.codegen.InformationSchemaColumn;
import joist.codegen.Schema;
import joist.codegen.dtos.Entity;
import joist.codegen.dtos.ManyToOneProperty;
import joist.codegen.dtos.OneToManyProperty;

public class FindForeignKeysPass implements Pass<Schema> {

  public void pass(Schema schema) {
    for (InformationSchemaColumn column : schema.getColumns()) {
      if (column.foreignKeyTableName == null) {
        continue;
      }

      Entity manySide = schema.getEntity(column);
      if (manySide == null) {
        continue;
      }

      Entity oneSide = schema.getEntity(column.foreignKeyTableName);
      if (oneSide == null) {
        throw new RuntimeException("Could not deduce referencedTableName for " + column.name);
      }

      if ("id".equals(column.name)) {
        // A foreign key on an "id" column means subclass!
        manySide.setBaseEntity(oneSide);
        oneSide.addSubEntity(manySide);
        continue;
      }

      ManyToOneProperty mtop = new ManyToOneProperty(manySide, column);
      OneToManyProperty otmp = new OneToManyProperty(oneSide, column);

      if (schema.getConfig().isPropertySkipped(manySide.getClassName(), mtop.getVariableName())) {
        continue;
      }

      manySide.getManyToOneProperties().add(mtop);
      mtop.setOneToManyProperty(otmp);

      oneSide.getOneToManyProperties().add(otmp);
      otmp.setManyToOneProperty(mtop);

      otmp.setOneToOne(column.unique);
    }
  }
}
