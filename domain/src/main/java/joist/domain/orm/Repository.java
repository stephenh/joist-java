package joist.domain.orm;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import joist.domain.uow.UnitOfWork;
import joist.domain.util.ConnectionSettings;
import joist.domain.util.pools.MySqlC3p0Factory;
import joist.domain.util.pools.Pgc3p0Factory;
import joist.jdbc.Jdbc;
import joist.util.Reflection;

/** An app-wide instance for the application's db + datasource. */
public class Repository {

  private final Db db;
  private final DataSource datasource;

  public Repository(Db db, String projectName) {
    this.db = db;
    final ConnectionSettings cs = ConnectionSettings.forApp(db, projectName);
    if (db.isPg()) {
      this.datasource = new Pgc3p0Factory(cs).create();
      Reflection.forName("org.postgresql.Driver");
    } else if (db.isMySQL()) {
      this.datasource = new MySqlC3p0Factory(cs).create();
    } else {
      throw new IllegalStateException("Unhandled db " + db);
    }
  }

  public Repository(Db db, DataSource datasource) {
    this.db = db;
    this.datasource = datasource;
  }

  /**
   * Opens a new UnitOfWork (connection) to the database for doing work.
   *
   * Caller is responsible for calling commit/close on the returned instance.
   */
  public UnitOfWork open(final Updater updater) {
    try {
      Connection connection = this.datasource.getConnection();
      connection.setAutoCommit(false);
      if (updater != null && this.db.isMySQL()) {
        // pg doesn't have session variables
        Jdbc.update(connection, "set @updater='{}'", updater.getUpdaterId());
      }
      return new UnitOfWork(this, connection, updater);
    } catch (SQLException se) {
      throw new RuntimeException(se);
    }
  }

  public Db getDb() {
    return this.db;
  }

  public DataSource getDataSource() {
    return this.datasource;
  }
}
