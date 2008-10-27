package org.exigencecorp.domainobjects.codegen.passes;

import org.exigencecorp.domainobjects.codegen.Codegen;
import org.exigencecorp.domainobjects.codegen.InformationSchemaColumn;
import org.exigencecorp.domainobjects.codegen.dtos.Entity;
import org.exigencecorp.domainobjects.codegen.dtos.EnumEntity;

public class FindTablesPass implements Pass {

    // Use the primary key 'id' to find our entity tables--and watch for many to many join tables to skip
    public void pass(Codegen codegen) {
        for (InformationSchemaColumn column : codegen.getColumns()) {
            if (!column.name.equals("id") || codegen.isManyToManyTable(column)) {
                continue;
            }

            if (codegen.isCodeTable(column)) {
                codegen.getEntities().put(column.tableName, new EnumEntity(codegen, column.tableName));
            } else {
                codegen.getEntities().put(column.tableName, new Entity(codegen, column.tableName));
            }
        }
    }

}
