package joist.domain.orm;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import javax.sql.DataSource;

import joist.domain.DomainObject;
import joist.domain.orm.impl.InstanceInserter;
import joist.domain.orm.impl.InstanceUpdater;
import joist.domain.orm.impl.SequenceIdAssigner;
import joist.domain.orm.impl.SortInstancesMySQL;
import joist.domain.orm.impl.SortInstancesPg;
import joist.domain.orm.queries.Alias;
import joist.domain.orm.queries.Delete;
import joist.domain.orm.queries.Select;
import joist.domain.uow.UoW;
import joist.domain.util.ConnectionSettings;
import joist.domain.util.pools.MySqlC3p0Factory;
import joist.domain.util.pools.Pgc3p0Factory;
import joist.jdbc.Jdbc;
import joist.util.Reflection;

public class Repository {

  public static Db db = null;
  public static DataSource datasource = null;
  private Connection connection;

  public static void configure(Db db, String projectName) {
    Repository.db = db;
    final ConnectionSettings cs = ConnectionSettings.forApp(db, projectName);
    if (db.isPg()) {
      Repository.datasource = new Pgc3p0Factory(cs).create();
      Reflection.forName("org.postgresql.Driver");
    } else if (db.isMySQL()) {
      Repository.datasource = new MySqlC3p0Factory(cs).create();
    } else {
      throw new IllegalStateException("Unhandled db " + db);
    }
  }

  public <T extends DomainObject> T load(Class<T> type, Long id) {
    T instance = (T) UoW.getIdentityMap().findOrNull(type, id);
    if (instance == null) {
      Alias<T> a = AliasRegistry.get(type);
      instance = Select.from(a).where(a.getIdColumn().eq(id)).unique();
    }
    return instance;
  }

  public void delete(DomainObject instance) {
    Alias<? super DomainObject> current = AliasRegistry.get(instance);
    while (current != null) {
      // ugly hack
      if (current.isRootClass()) {
        Delete.from(current).where(current.getIdColumn().eq(instance)).execute();
      } else {
        Delete.from(current).where(current.getSubClassIdColumn().eq(instance)).execute();
      }
      current = current.getBaseClassAlias();
    }
  }

  public void store(Set<DomainObject> instances) {
    if (Repository.db.isPg()) {
      // pg can bulk assign ids, then just do insert+update, unsorted thanks to initially deferred
      SortInstancesPg sorted = new SortInstancesPg(instances);
      new SequenceIdAssigner().assignIds(sorted.inserts);
      for (Entry<Class<DomainObject>, List<DomainObject>> entry : sorted.inserts.entrySet()) {
        InstanceInserter.get(entry.getKey()).insertHasId(entry.getValue());
      }
      for (Entry<Class<DomainObject>, List<DomainObject>> entry : sorted.updates.entrySet()) {
        InstanceUpdater.get(entry.getKey()).update(entry.getValue());
      }
    } else if (Repository.db.isMySQL()) {
      // mysql assigns ids as you go, and needs sorting around foreign keys
      SortInstancesMySQL sorted = new SortInstancesMySQL(instances);
      for (Class<DomainObject> key : sorted.insertsByForeignKey) {
        InstanceInserter.get(key).insertHasId(sorted.insertHasIds.get(key));
        InstanceInserter.get(key).insertNewId(sorted.insertNewIds.get(key));
      }
      for (Entry<Class<DomainObject>, List<DomainObject>> entry : sorted.updates.entrySet()) {
        InstanceUpdater.get(entry.getKey()).update(entry.getValue());
      }
    } else {
      throw new IllegalStateException("Unhandled db " + Repository.db);
    }
  }

  public void open(final Updater updater) {
    if (Repository.datasource == null) {
      throw new RuntimeException("The Repository datasource has not been configured");
    } else if (Repository.db == null) {
      throw new RuntimeException("The Repository db has not been configured");
    }
    try {
      this.connection = Repository.datasource.getConnection();
      this.connection.setAutoCommit(false);

      if (updater != null && Repository.db.isMySQL()) {
        // pg doesn't have session variables
        Jdbc.update(this.connection, "set @updater='{}'", updater.getUpdaterId());
      }
    } catch (SQLException se) {
      throw new RuntimeException(se);
    }
  }

  public void close() {
    if (this.connection == null) {
      return;
    }
    try {
      this.connection.close();
    } catch (SQLException se) {
      throw new RuntimeException(se);
    }
  }

  public void commit() {
    try {
      this.connection.commit();
    } catch (SQLException se) {
      throw new RuntimeException(se);
    }
  }

  public void rollback() {
    if (this.connection == null) {
      return;
    }
    try {
      this.connection.rollback();
    } catch (SQLException se) {
      throw new RuntimeException(se);
    }
  }

  public Connection getConnection() {
    return this.connection;
  }

}
