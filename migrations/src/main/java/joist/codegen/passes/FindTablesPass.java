package joist.codegen.passes;

import joist.codegen.InformationSchemaColumn;
import joist.codegen.Schema;
import joist.codegen.dtos.CodeEntity;
import joist.codegen.dtos.Entity;

public class FindTablesPass implements Pass<Schema> {

  // Use the primary key 'id' to find our entity tables--and watch for many to many join tables to skip
  public void pass(Schema schema) {
    for (InformationSchemaColumn column : schema.getColumns()) {
      if (!column.name.equals("id") || schema.getConfig().isTableSkipped(column.tableName)) {
        continue;
      }

      if (schema.isCodeTable(column)) {
        schema.getEntities().put(column.tableName, new CodeEntity(schema.getConfig(), column.tableName));
      } else {
        schema.getEntities().put(column.tableName, new Entity(schema.getConfig(), column.tableName));
      }
    }
  }

}
