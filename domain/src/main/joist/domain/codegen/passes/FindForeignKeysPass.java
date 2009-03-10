package joist.domain.codegen.passes;

import joist.domain.codegen.Codegen;
import joist.domain.codegen.InformationSchemaColumn;
import joist.domain.codegen.dtos.Entity;
import joist.domain.codegen.dtos.ManyToOneProperty;
import joist.domain.codegen.dtos.OneToManyProperty;

public class FindForeignKeysPass implements Pass {

    public void pass(Codegen codegen) {
        for (InformationSchemaColumn column : codegen.getColumns()) {
            if (column.foreignKeyTableName == null) {
                continue;
            }

            Entity oneSide = codegen.getEntity(column);
            if (oneSide == null) {
                continue;
            }

            Entity manySide = codegen.getEntity(column.foreignKeyTableName);
            if (manySide == null) {
                throw new RuntimeException("Could not deduce referencedTableName for " + column.name);
            }

            if ("id".equals(column.name)) {
                // A foreign key on an "id" column means subclass!
                oneSide.setBaseEntity(manySide);
                manySide.addSubEntity(oneSide);
                continue;
            }

            ManyToOneProperty mtop = new ManyToOneProperty(oneSide, column);
            OneToManyProperty otmp = new OneToManyProperty(manySide, column);

            oneSide.getManyToOneProperties().add(mtop);
            mtop.setOneToManyProperty(otmp);

            manySide.getOneToManyProperties().add(otmp);
            otmp.setForeignKeyColumn(mtop);

            otmp.setOneToOne(column.unique);
        }
    }
}
