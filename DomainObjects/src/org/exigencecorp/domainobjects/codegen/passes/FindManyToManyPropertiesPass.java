package org.exigencecorp.domainobjects.codegen.passes;

import java.util.List;

import org.exigencecorp.domainobjects.codegen.Codegen;
import org.exigencecorp.domainobjects.codegen.InformationSchemaColumn;
import org.exigencecorp.domainobjects.codegen.dtos.Entity;
import org.exigencecorp.domainobjects.codegen.dtos.ManyToManyProperty;
import org.exigencecorp.util.Copy;

public class FindManyToManyPropertiesPass implements Pass {

    public void pass(Codegen codegen) {
        for (InformationSchemaColumn column : codegen.getColumns()) {
            if (column.foreignKeyConstraintName == null || !column.isManyToManyTable()) {
                continue;
            }

            Entity mySide = codegen.getEntity(column.foreignKeyTableName);
            if (mySide == null) {
                throw new RuntimeException("Could not find entity " + column.foreignKeyTableName);
            }

            // Figure out if our hbm file gets inverse=true or not
            // boolean isFirst = !(column.tableName.startsWith(sideA.getTableName()));

            // Guess about the target table
            List<String> names = Copy.list(column.tableName.split("_to_"));
            names.remove(mySide.getTableName());
            Entity otherSide = codegen.getEntity(names.get(0));
            if (otherSide == null) {
                throw new RuntimeException("Could not find entity " + names.get(0));
            }

            ManyToManyProperty mtmp = new ManyToManyProperty(codegen, mySide, otherSide, column);
            mySide.getManyToManyProperties().add(mtmp);

            // Go find our corresponding property--if it is there yet (of 2, last one adds both)
            for (ManyToManyProperty other : otherSide.getManyToManyProperties()) {
                if (other.getJoinTableName().equals(mtmp.getJoinTableName())) {
                    other.setOther(mtmp);
                    mtmp.setOther(other);
                }
            }
        }
    }

}
