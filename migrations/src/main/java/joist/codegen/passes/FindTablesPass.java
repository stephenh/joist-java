package joist.codegen.passes;

import joist.codegen.Codegen;
import joist.codegen.InformationSchemaColumn;
import joist.codegen.dtos.CodeEntity;
import joist.codegen.dtos.Entity;

public class FindTablesPass implements Pass {

  // Use the primary key 'id' to find our entity tables--and watch for many to many join tables to skip
  public void pass(Codegen codegen) {
    for (InformationSchemaColumn column : codegen.getColumns()) {
      if (!column.name.equals("id")) {
        continue;
      }

      if (codegen.isCodeTable(column)) {
        codegen.getEntities().put(column.tableName, new CodeEntity(codegen, column.tableName));
      } else {
        codegen.getEntities().put(column.tableName, new Entity(codegen, column.tableName));
      }
    }
  }

}
