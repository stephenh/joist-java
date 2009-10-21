package joist.domain.codegen.passes;

import java.sql.Connection;
import java.sql.SQLException;

import joist.domain.codegen.Codegen;
import joist.domain.codegen.dtos.Entity;
import joist.jdbc.Jdbc;
import joist.util.StringBuilderr;

public class GenerateFlushFunction implements Pass {

    public void pass(Codegen codegen) {
        StringBuilderr sql = new StringBuilderr();

        /*
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
        */

        Connection conn = null;
        try {
            conn = codegen.getDataSource().getConnection();
            Jdbc.update(conn, "DROP PROCEDURE IF EXISTS flush_test_database;");

            sql.line("CREATE PROCEDURE flush_test_database()");
            // sql.line("RETURNS int NOT DETERMINISTIC");
            sql.line("BEGIN");
            sql.line("SET FOREIGN_KEY_CHECKS=0;");
            for (Entity entity : codegen.getEntities().values()) {
                if (!entity.isCodeEntity()) {
                    sql.line("TRUNCATE TABLE {};", entity.getTableName());
                }
            }
            sql.line("SET FOREIGN_KEY_CHECKS=1;");
            sql.line("SELECT 1;");
            sql.line("END");
            Jdbc.update(conn, sql.toString());

            Jdbc.update(//
                conn,
                "GRANT ALL ON PROCEDURE `flush_test_database` TO {}@'%'",
                codegen.getAppDbSettings().user);
        } catch (SQLException se) {
            throw new RuntimeException(se);
        } finally {
            Jdbc.closeSafely(conn);
        }
    }

}
