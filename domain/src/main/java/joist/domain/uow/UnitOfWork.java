package joist.domain.uow;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import joist.domain.DomainObject;
import joist.domain.exceptions.NotFoundException;
import joist.domain.orm.*;
import joist.domain.orm.impl.*;
import joist.domain.orm.queries.Alias;
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
  private final Snapshot snapshot;
  private Updater updater;
  private boolean rolledBack;
  private boolean implicitDeletionOfChildrenEnabled;
  private boolean creatingSnapshot;
  private boolean readOnly;

  public UnitOfWork(final Repository repo, final Connection connection, Updater updater, Snapshot snapshot) {
    this.repo = repo;
    this.connection = connection;
    this.updater = updater;
    this.db = repo.getDb();
    this.snapshot = snapshot;
    this.setUpdater(updater);
    if (snapshot != null) {
      snapshot.populate(this);
    }
  }

  void close() {
    try {
      // clear the user variable so it doesn't leak into other connections
      // (which it will since our connection is pooled)
      this.setUpdater(null);
    } finally {
      // we close the connection in a finally in case the setUpdater
      // call fails, we still want to call .close()
      try {
        this.connection.close();
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
    }
  }

  void flush() {
    this.failIfRolledBack();
    this.validator.validate();
    this.flush(this.validator.getQueue(), this.validator.getDequeue());
    this.validator.resetQueueAndChangedProperties();
  }

  void commit() {
    this.failIfRolledBack();
    this.flush();
    try {
      this.connection.commit();
    } catch (SQLException se) {
      throw new RuntimeException(se);
    }
  }

  void commitUnlessRolledBack() {
    if (this.rolledBack) {
      return;
    }
    this.commit();
  }

  void rollback() {
    this.rolledBack = true;
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
      Jdbc.update(this.connection, "set @updater=?", updater.getUpdaterId());
    }
    this.updater = updater;
  }

  <T extends DomainObject> T load(Class<T> type, Long id) {
    T loaded = (T) this.identityMap.findOrNull(type, id);
    if (loaded != null) {
      return loaded;
    }

    Alias<T> a = AliasRegistry.get(type);
    try {
      return Select.from(a).where(a.getIdColumn().eq(id)).unique();
    } catch (NotFoundException nfe) {
      // throw a more specific NotFoundException
      throw new NotFoundException(type, id);
    }
  }

  <T extends DomainObject> List<T> load(Class<T> type, Collection<Long> ids) {
    List<Long> missingIds = new ArrayList<Long>();
    List<T> loaded = new ArrayList<T>();
    for (Long id : ids) {
      T obj = (T) this.identityMap.findOrNull(type, id);
      if (obj == null) {
        missingIds.add(id);
      } else {
        loaded.add(obj);
      }
    }

    Alias<T> a = AliasRegistry.get(type);
    try {
      if (!missingIds.isEmpty()) {
        loaded.addAll(Select.from(a).where(a.getIdColumn().in(missingIds)).list());
      }
      return loaded;
    } catch (NotFoundException nfe) {
      throw new NotFoundException(type, ids);
    }
  }

  void delete(DomainObject instance) {
    // we'll do the actual delete in flush
    this.validator.dequeue(instance);
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

  Snapshot getSnapshot() {
    return this.snapshot;
  }

  boolean isImplicitDeletionOfChildrenEnabled() {
    return this.implicitDeletionOfChildrenEnabled;
  }

  void setImplicitDeletionOfChildren(boolean implicitDeletionOfChildrenEnabled) {
    this.implicitDeletionOfChildrenEnabled = implicitDeletionOfChildrenEnabled;
  }

  boolean isCreatingSnapshot() {
    return this.creatingSnapshot;
  }

  void setCreatingSnapshot(boolean snapshotting) {
    this.creatingSnapshot = snapshotting;
  }

  boolean isReadOnly() {
    return this.readOnly;
  }

  void setReadOnly(boolean readOnly) {
    this.readOnly = readOnly;
  }

  private void flush(Set<DomainObject> insertOrUpdate, Set<DomainObject> delete) {
    if (this.db.isPg()) {
      // pg can bulk assign ids, then just do insert+update, unsorted thanks to initially deferred
      SortInstancesPg sorted = new SortInstancesPg(insertOrUpdate, delete);
      new SequenceIdAssigner().assignIds(sorted.inserts);
      for (Entry<Class<DomainObject>, List<DomainObject>> entry : sorted.inserts.entrySet()) {
        InstanceInserter.get(entry.getKey()).insertHasId(entry.getValue());
      }
      for (Entry<Class<DomainObject>, List<DomainObject>> entry : sorted.updates.entrySet()) {
        InstanceUpdater.get(entry.getKey()).update(entry.getValue());
      }
      for (Entry<Class<DomainObject>, List<DomainObject>> entry : sorted.deletes.entrySet()) {
        InstanceDeleter.get(entry.getKey()).delete(entry.getValue());
      }
    } else if (this.db.isMySQL()) {
      // mysql assigns ids as you go, and needs sorting around foreign keys
      SortInstancesMySQL sorted = new SortInstancesMySQL(insertOrUpdate, delete);
      for (Class<DomainObject> key : sorted.insertsByForeignKey) {
        InstanceInserter.get(key).insertHasId(sorted.insertHasIds.get(key));
        InstanceInserter.get(key).insertNewId(sorted.insertNewIds.get(key));
      }
      for (Entry<Class<DomainObject>, List<DomainObject>> entry : sorted.updates.entrySet()) {
        InstanceUpdater.get(entry.getKey()).update(entry.getValue());
      }
      for (Class<DomainObject> key : sorted.deletesByForeignKey) {
        InstanceDeleter.get(key).delete(sorted.deletes.get(key));
      }
    } else {
      throw new IllegalStateException("Unhandled db " + this.db);
    }
  }

  private void failIfRolledBack() {
    if (this.rolledBack) {
      throw new IllegalStateException("UoW has been rolled back");
    }
  }
}
