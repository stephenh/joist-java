package joist.domain.orm;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import joist.domain.uow.Snapshot;
import joist.domain.uow.UnitOfWork;
import joist.domain.util.ConnectionSettings;
import joist.jdbc.Jdbc;

/** An app-wide instance for the application's db + datasource. */
public class Repository {

  private final Db db;
  private final DataSource datasource;

  public Repository(Db db, String projectName) {
    this(ConnectionSettings.forApp(db, projectName));
  }

  public Repository(ConnectionSettings settings) {
    this(settings.db, settings.getDataSource());
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
  public UnitOfWork open(Updater updater, Snapshot snapshot) {
    Connection connection = null;
    try {
      connection = this.datasource.getConnection();
      connection.setAutoCommit(false);
      return new UnitOfWork(this, connection, updater, snapshot);
    } catch (SQLException se) {
      // be paranoid and, if setAutoCommit/new UnitOfWork blew up, immediately
      // close our connection because without a valid UnitOfWork instance, the
      // UoW methods won't know about our connection
      Jdbc.closeSafely(connection);
      throw new RuntimeException(se);
    } catch (RuntimeException re) {
      // same but don't wrap the exception
      Jdbc.closeSafely(connection);
      throw re;
    }
  }

  public Db getDb() {
    return this.db;
  }

  public DataSource getDataSource() {
    return this.datasource;
  }
}
