package joist.domain;

import joist.domain.orm.Repository;
import joist.jdbc.Jdbc;

/**
 * Flushes test data before each test.
 *
 * Of course this shouldn't be called in production, however, as a safety, the
 * {@code flush_test_database} stored procedure should only ever be created
 * by running the code generation step (and hence only live in your local
 * database).
 */
public class FlushTestDatabase {

  public static void execute() {
    if (Repository.db.isPg()) {
      Jdbc.queryForInt(Repository.datasource, "select * from flush_test_database()");
    } else if (Repository.db.isMySQL()) {
      Jdbc.queryForInt(Repository.datasource, "CALL flush_test_database()");
    } else {
      throw new IllegalStateException("Unhandled db " + Repository.db);
    }
  }
}
