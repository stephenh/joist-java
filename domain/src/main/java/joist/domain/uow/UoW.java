package joist.domain.uow;

import java.sql.Connection;
import java.util.Collection;
import java.util.List;

import joist.domain.AbstractDomainObject;
import joist.domain.DomainObject;
import joist.domain.orm.*;
import joist.domain.validation.ValidationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UoW {

  private static final Logger log = LoggerFactory.getLogger(UoW.class);
  private static final ThreadLocal<UnitOfWork> uowForThread = new ThreadLocal<UnitOfWork>();

  public static void go(Repository repo, Updater updater, Block block) {
    boolean committed = false;
    try {
      UoW.open(repo, updater);
      block.go();
      UoW.commitUnlessRolledBack();
      committed = true;
    } finally {
      UoW.safelyRollbackAndCloseIfNeeded(committed);
    }
  }

  public static <T> T go(Repository repo, Updater updater, BlockWithReturn<T> block) {
    boolean committed = false;
    try {
      UoW.open(repo, updater);
      T value = block.go();
      UoW.commitUnlessRolledBack();
      committed = true;
      return value;
    } finally {
      UoW.safelyRollbackAndCloseIfNeeded(committed);
    }
  }

  public static void read(Repository repo, Block block) {
    try {
      UoW.open(repo, null);
      getCurrent().setReadOnly(true);
      block.go();
    } finally {
      UoW.safelyRollbackAndCloseIfNeeded(true);
    }
  }

  public static <T> T read(Repository repo, BlockWithReturn<T> block) {
    try {
      UoW.open(repo, null);
      getCurrent().setReadOnly(true);
      return block.go();
    } finally {
      UoW.safelyRollbackAndCloseIfNeeded(true);
    }
  }

  public static void go(Repository repo, Updater updater, BlockWithSafety block) {
    boolean committed = false;
    try {
      UoW.open(repo, updater);
      block.go();
      UoW.commitUnlessRolledBack();
      committed = true;
    } catch (Exception e) {
      UoW.safelyRollbackAndCloseIfNeeded(committed);
      block.stopped(e);
    } finally {
      UoW.safelyRollbackAndCloseIfNeeded(committed);
    }
  }

  public static <T> T go(Repository repo, Updater updater, BlockWithReturnAndSafety<T> block) {
    boolean committed = false;
    try {
      UoW.open(repo, updater);
      T value = block.go();
      UoW.commitUnlessRolledBack();
      committed = true;
      return value;
    } catch (Exception e) {
      UoW.safelyRollbackAndCloseIfNeeded(committed);
      return block.stopped(e);
    } finally {
      UoW.safelyRollbackAndCloseIfNeeded(committed);
    }
  }

  /**
   * Executes {@code block} within a read-only {@link UnitOfWork} and returns a snapshot of its state.
   */
  public static Snapshot snapshot(final Repository repo, Block block) {
    return snapshot(repo, null, block);
  }

  /**
   * Executes {@code block} within a read-only {@link UnitOfWork}, seeded with {@code snapshot}, and returns a snapshot of its state.
   */
  public static Snapshot snapshot(final Repository repo, Snapshot snapshot, Block block) {
    try {
      UoW.open(repo, null, snapshot);
      UoW.getCurrent().setCreatingSnapshot(true);
      block.go();
      return new Snapshot(UoW.getCurrent());
    } finally {
      UoW.safelyRollbackAndCloseIfNeeded(true);
    }
  }

  public static boolean isOpen() {
    return UoW.uowForThread.get() != null;
  }

  /**
   * Opens a new {@link UnitOfWork} and database connection.
   */
  public static void open(final Repository repository, Updater updater) {
    open(repository, updater, null);
  }

  /**
   * Opens a new {@link UnitOfWork} and database connection.
   */
  public static void open(final Repository repository, Updater updater, Snapshot snapshot) {
    UoW.assertClosed();
    UnitOfWork uow = repository.open(updater, snapshot);
    UoW.uowForThread.set(uow);
  }

  /**
   * Closes the current {@link UnitOfWork} and database connection, without any flush or txn commit/rollback.
   */
  public static void close() {
    UnitOfWork current = UoW.getCurrent();
    // unset our ThreadLocal first, in case .close() fails
    UoW.uowForThread.set(null);
    current.close();
  }

  /**
   * Closes the current {@link UnitOfWork}, without any flush or txn commit/rollback, only if it is currently open.
   */
  public static void closeIfOpen() {
    if (UoW.isOpen()) {
      UoW.close();
    }
  }

  /**
   * Flushes changes to the database without committing the transaction.
   *
   * @throws ValidationException if validation errors occur
   */
  public static void flush() {
    UoW.getCurrent().flush();
  }

  /**
   * Commits the transaction, after flushing.
   *
   * @throws ValidationException if validation errors occur
   */
  public static void commit() {
    UoW.getCurrent().commit();
  }

  /**
   * Commits the transaction, after flushing, unless it's been rolled back.
   *
   * @throws ValidationException if validation errors occur
   */
  public static void commitUnlessRolledBack() {
    UoW.getCurrent().commitUnlessRolledBack();
  }

  /** Rolls back the transaction. */
  public static void rollback() {
    UoW.getCurrent().rollback();
  }

  /** Commits the current {@link UnitOfWork} and opens a new one with a different connection. */
  public static void commitAndReOpen() {
    final Repository repo = UoW.getCurrent().getRepository();
    final Updater updater = UoW.getCurrent().getUpdater();
    final Snapshot snapshot = UoW.getCurrent().getSnapshot();
    UoW.commitUnlessRolledBack();
    UoW.close();
    UoW.open(repo, updater, snapshot);
  }

  /** Queues <code>instance</code> for validation on flush. */
  public static void enqueue(DomainObject instance) {
    if (UoW.getCurrent().isCreatingSnapshot()) {
      throw new RuntimeException("Cannot modify " + instance + " while creating a snapshot");
    }
    if (UoW.getCurrent().isReadOnly()) {
      throw new RuntimeException("Cannot modify " + instance + " while reading");
    }
    if (AbstractDomainObject.isFromSnapshot(instance)) {
      throw new RuntimeException("Cannot modify " + instance + " as it was loaded from a snapshot");
    }
    UoW.getCurrent().enqueue(instance);
  }

  /** Queues <code>instance</code> for deletion on flush. */
  public static void delete(DomainObject instance) {
    UoW.getCurrent().delete(instance);
  }

  /** @return the instance of <code>type</code> for <code>id</code>, checking the identity map */
  public static <T extends DomainObject> T load(Class<T> type, Long id) {
    return UoW.getCurrent().load(type, id);
  }

  public static <T extends DomainObject> List<T> load(Class<T> type, Collection<Long> ids) {
    return UoW.getCurrent().load(type, ids);
  }

  /** Changes the updater for the current UoW. */
  public static void setUpdater(Updater updater) {
    UoW.getCurrent().setUpdater(updater);
  }

  public static IdentityMap getIdentityMap() {
    return UoW.getCurrent().getIdentityMap();
  }

  public static EagerCache getEagerCache() {
    return UoW.getCurrent().getEagerCache();
  }

  /** @return the current database connection */
  public static Connection getConnection() {
    return UoW.getCurrent().getConnection();
  }

  /** @return the current {@link Db} type */
  public static Db getDb() {
    return UoW.getCurrent().getDb();
  }

  /** Enables implicit deletion of children by their owner parents. */
  public static void enableImplicitDeletionOfChildren() {
    UoW.getCurrent().setImplicitDeletionOfChildren(true);
  }

  /** @return whether children should be implicitly deleted if removed from their owner parent. */
  public static boolean isImplicitDeletionOfChildrenEnabled() {
    return UoW.getCurrent().isImplicitDeletionOfChildrenEnabled();
  }

  private static UnitOfWork getCurrent() {
    UoW.assertOpen();
    return UoW.uowForThread.get();
  }

  private static void assertClosed() {
    if (UoW.uowForThread.get() != null) {
      throw new RuntimeException("Not closed");
    }
  }

  private static void assertOpen() {
    if (UoW.uowForThread.get() == null) {
      throw new RuntimeException("Not open");
    }
  }

  private static void safelyRollbackAndCloseIfNeeded(boolean committed) {
    if (!UoW.isOpen()) {
      // UoW.open failed, likely due to bad db connection
      return;
    }
    if (!committed) {
      try {
        UoW.rollback();
      } catch (Exception e) {
        log.error("Error while rolling back", e);
      }
    }
    try {
      UoW.close();
    } catch (Exception e) {
      log.error("Error while closing", e);
    }
  }

}
