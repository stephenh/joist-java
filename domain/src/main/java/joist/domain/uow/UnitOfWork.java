package joist.domain.uow;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import joist.domain.DomainObject;
import joist.domain.exceptions.NotFoundException;
import joist.domain.orm.AliasRegistry;
import joist.domain.orm.Db;
import joist.domain.orm.EagerCache;
import joist.domain.orm.IdentityMap;
import joist.domain.orm.Repository;
import joist.domain.orm.Updater;
import joist.domain.orm.impl.InstanceInserter;
import joist.domain.orm.impl.InstanceUpdater;
import joist.domain.orm.impl.SequenceIdAssigner;
import joist.domain.orm.impl.SortInstancesMySQL;
import joist.domain.orm.impl.SortInstancesPg;
import joist.domain.orm.queries.Alias;
import joist.domain.orm.queries.Delete;
import joist.domain.orm.queries.Select;
import joist.domain.validation.Validator;
import joist.jdbc.Jdbc;

/** Coordinates validation, object identity, and storing/retrieving domain objects.
 *
 * All of these methods are package private so that the UoW facade class has to
 * be used. I'm not entirely sure this is a good idea.
 */
public class UnitOfWork {

  private final Validator validator = new Validator();
  private final IdentityMap identityMap = new IdentityMap();
  private final EagerCache eagerCache = new EagerCache();
  private final Repository repo;
  private final Connection connection;
  private final Db db;
  private Updater updater;

  public UnitOfWork(final Repository repo, final Connection connection, Updater updater) {
    this.repo = repo;
    this.connection = connection;
    this.updater = updater;
    this.db = repo.getDb();
    this.setUpdater(updater);
  }

  void close() {
    try {
      // clear the user variable so it doesn't leak into other connections
      // (which it will since our connection is pooled)
      this.setUpdater(null);
      this.connection.close();
    } catch (SQLException se) {
      throw new RuntimeException(se);
    }
  }

  void flush() {
    this.validator.validate();
    this.store(this.validator.getQueue());
    this.validator.resetQueueAndChangedProperties();
  }

  void commit() {
    this.flush();
    try {
      this.connection.commit();
    } catch (SQLException se) {
      throw new RuntimeException(se);
    }
  }

  void rollback() {
    try {
      this.connection.rollback();
    } catch (SQLException se) {
      throw new RuntimeException(se);
    }
  }

  void setUpdater(Updater updater) {
    if (this.db.isPg()) {
      // pg isn't supported yet
      return;
    }
    if (updater == null) {
      // only issue the unset if we had a different updater
      if (this.updater != null) {
        Jdbc.update(this.connection, "set @updater=null");
      }
    } else {
      Jdbc.update(this.connection, "set @updater='{}'", updater.getUpdaterId());
    }
    this.updater = updater;
  }

  <T extends DomainObject> T load(Class<T> type, Long id) {
    T instance = (T) this.identityMap.findOrNull(type, id);
    if (instance == null) {
      Alias<T> a = AliasRegistry.get(type);
      try {
        instance = Select.from(a).where(a.getIdColumn().eq(id)).unique();
      } catch (NotFoundException nfe) {
        // throw a more specific NotFoundException
        throw new NotFoundException(type, id);
      }
    }
    return instance;
  }

  void delete(DomainObject instance) {
    this.validator.dequeue(instance);
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

  /** Queues <code>instance</code> for validation on flush. */
  void enqueue(DomainObject instance) {
    this.validator.enqueue(instance);
  }

  IdentityMap getIdentityMap() {
    return this.identityMap;
  }

  EagerCache getEagerCache() {
    return this.eagerCache;
  }

  Connection getConnection() {
    return this.connection;
  }

  Db getDb() {
    return this.db;
  }

  Repository getRepository() {
    return this.repo;
  }

  Updater getUpdater() {
    return this.updater;
  }

  private void store(Set<DomainObject> instances) {
    if (this.db.isPg()) {
      // pg can bulk assign ids, then just do insert+update, unsorted thanks to initially deferred
      SortInstancesPg sorted = new SortInstancesPg(instances);
      new SequenceIdAssigner().assignIds(sorted.inserts);
      for (Entry<Class<DomainObject>, List<DomainObject>> entry : sorted.inserts.entrySet()) {
        InstanceInserter.get(entry.getKey()).insertHasId(entry.getValue());
      }
      for (Entry<Class<DomainObject>, List<DomainObject>> entry : sorted.updates.entrySet()) {
        InstanceUpdater.get(entry.getKey()).update(entry.getValue());
      }
    } else if (this.db.isMySQL()) {
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
      throw new IllegalStateException("Unhandled db " + this.db);
    }
  }

}
