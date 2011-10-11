package joist.codegen.passes;

import joist.codegen.Codegen;
import joist.codegen.dtos.Entity;
import joist.domain.orm.Db;
import joist.jdbc.Jdbc;
import joist.util.StringBuilderr;
import joist.util.Wrap;

public class GenerateFlushFunction implements Pass {

  public void pass(Codegen codegen) {
    Db db = codegen.getAppDbSettings().db;
    if (db.isPg()) {
      this.generatePg(codegen);
    } else if (db.isMySQL()) {
      this.generateMySQL(codegen);
    } else {
      throw new IllegalStateException("Unhandled db " + db);
    }
  }

  private void generatePg(Codegen codegen) {
    StringBuilderr sql = new StringBuilderr();
    sql.line("CREATE OR REPLACE FUNCTION flush_test_database() RETURNS integer AS");
    sql.line("$BODY$");
    sql.line("BEGIN");
    sql.line("SET CONSTRAINTS ALL DEFERRED;");
    for (Entity entity : codegen.getEntities().values()) {
      if (entity.isRoot() && !entity.isStableTable()) {
        sql.line("ALTER SEQUENCE {} RESTART WITH 1 INCREMENT BY 1;", Wrap.quotes(entity.getTableName() + "_id_seq"));
      }
    }
    for (Entity entity : codegen.getEntities().values()) {
      if (entity.isRoot() && !entity.isStableTable()) {
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

  private void generateMySQL(Codegen codegen) {
    StringBuilderr sql = new StringBuilderr();
    sql.line("CREATE PROCEDURE flush_test_database()");
    sql.line("BEGIN");
    sql.line("SET FOREIGN_KEY_CHECKS=0;");
    for (Entity entity : codegen.getEntities().values()) {
      if (!entity.isCodeEntity() && !entity.isStableTable()) {
        sql.line("TRUNCATE TABLE {};", entity.getTableName());
      }
    }
    sql.line("SET FOREIGN_KEY_CHECKS=1;");
    sql.line("SELECT 1;");
    sql.line("END");
    Jdbc.update(codegen.getDataSource(), "DROP PROCEDURE IF EXISTS flush_test_database;");
    Jdbc.update(codegen.getDataSource(), sql.toString());
    Jdbc.update(codegen.getDataSource(), "GRANT ALL ON PROCEDURE flush_test_database TO {}@'%'", codegen.getAppDbSettings().user);
  }

}
