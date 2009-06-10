package joist.domain.codegen.passes;

import joist.domain.codegen.Codegen;
import joist.domain.codegen.dtos.Entity;
import joist.jdbc.Jdbc;
import joist.util.StringBuilderr;
import joist.util.Wrap;

public class GenerateFlushFunction implements Pass {

    public void pass(Codegen codegen) {
        StringBuilderr sql = new StringBuilderr();

        sql.line("CREATE OR REPLACE FUNCTION flush_test_database() RETURNS integer AS");
        sql.line("$BODY$");
        sql.line("BEGIN");
        sql.line("SET CONSTRAINTS ALL DEFERRED;");

        for (Entity entity : codegen.getEntities().values()) {
            if (entity.isRoot()) {
                sql.line("ALTER SEQUENCE {} RESTART WITH 1 INCREMENT BY 1;", Wrap.quotes(entity.getTableName() + "_id_seq"));
            }
        }
        for (Entity entity : codegen.getEntities().values()) {
            if (entity.isRoot()) {
                sql.line("DELETE FROM {};", Wrap.quotes(entity.getTableName()));
            }
        }

        sql.line("RETURN 0;");
        sql.line("END;");
        sql.line("$BODY$");
        sql.line("  LANGUAGE 'plpgsql' VOLATILE");
        sql.line("  COST 100;");
        sql.line("ALTER FUNCTION flush_test_database() SECURITY DEFINER;");

        Jdbc.update(codegen.getDataSource(), sql.toString());
    }

}
