package joist.codegen.passes;

import joist.codegen.InformationSchemaColumn;
import joist.codegen.Schema;
import joist.codegen.dtos.Entity;
import joist.codegen.dtos.ManyToManyProperty;
import joist.codegen.dtos.ManyToOneProperty;

public class FindManyToManyPropertiesPass implements Pass<Schema> {

  public void pass(Schema schema) {
    for (InformationSchemaColumn column : schema.getColumns()) {
      if (column.foreignKeyConstraintName == null || !schema.isManyToManyTable(column)) {
        continue;
      }

      Entity joinTable = schema.getEntity(column.tableName);

      for (ManyToOneProperty mtop : joinTable.getManyToOneProperties()) {
        mtop.getOneToManyProperty().setManyToMany(true);
      }

      Entity mySide = schema.getEntity(column.foreignKeyTableName);
      if (mySide == null) {
        throw new RuntimeException("Could not find entity " + column.foreignKeyTableName);
      }

      Entity otherSide = schema.getEntity(this.findOtherColumnInTable(schema, column).foreignKeyTableName);
      if (otherSide == null) {
        throw new RuntimeException("Could not find entity " + column.tableName);
      }

      ManyToManyProperty mtmp = new ManyToManyProperty(schema.getConfig(), joinTable, mySide, otherSide, column);
      mySide.getManyToManyProperties().add(mtmp);

      // Go find our corresponding property--if it is there yet (of 2, last one adds both)
      for (ManyToManyProperty other : otherSide.getManyToManyProperties()) {
        if (other.getJoinTable().getTableName().equals(mtmp.getJoinTable().getTableName()) && other != mtmp) {
          other.setOther(mtmp);
          mtmp.setOther(other);
        }
      }
    }
  }

  private InformationSchemaColumn findOtherColumnInTable(Schema schema, InformationSchemaColumn column1) {
    for (InformationSchemaColumn column2 : schema.getColumns()) {
      if (column2.foreignKeyConstraintName != null //
        && column2.tableName.equals(column1.tableName)
        && !column2.name.equals(column1.name)) {
        return column2;
      }
    }
    throw new RuntimeException("Other column in many to many table " + column1.tableName + " not found");
  }

}
