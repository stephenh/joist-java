package joist.domain.uow;

import java.sql.Connection;

import joist.domain.DomainObject;
import joist.domain.orm.IdentityMap;
import joist.domain.orm.Updater;
import joist.util.Log;

public class UoW {

  private static final ThreadLocal<UnitOfWork> uowForThread = new ThreadLocal<UnitOfWork>();

  public static void go(Updater updater, Block block) {
    boolean committed = false;
    UoW.open(updater);
    try {
      block.go();
      UoW.commit();
      committed = true;
    } finally {
      UoW.safelyRollbackAndCloseIfNeeded(committed);
    }
  }

  public static <T> T go(Updater updater, BlockWithReturn<T> block) {
    boolean committed = false;
    UoW.open(updater);
    try {
      T value = block.go();
      UoW.commit();
      committed = true;
      return value;
    } finally {
      UoW.safelyRollbackAndCloseIfNeeded(committed);
    }
  }

  public static <T> T read(BlockWithReturn<T> block) {
    UoW.open(null);
    try {
      return block.go();
    } finally {
      UoW.safelyRollbackAndCloseIfNeeded(true);
    }
  }

  public static void go(Updater updater, BlockWithSafety block) {
    boolean committed = false;
    UoW.open(updater);
    try {
      block.go();
      UoW.commit();
      committed = true;
    } catch (Exception e) {
      block.stopped(e);
    } finally {
      UoW.safelyRollbackAndCloseIfNeeded(committed);
    }
  }

  public static <T> T go(Updater updater, BlockWithReturnAndSafety<T> block) {
    boolean committed = false;
    UoW.open(updater);
    try {
      T value = block.go();
      UoW.commit();
      committed = true;
      return value;
    } catch (Exception e) {
      return block.stopped(e);
    } finally {
      UoW.safelyRollbackAndCloseIfNeeded(committed);
    }
  }

  public static boolean isOpen() {
    return UoW.uowForThread.get() != null;
  }

  /**
   * Opens a new {@link UnitOfWork} and database connection.
   */
  public static void open(final Updater updater) {
    UoW.assertClosed();
    UoW.uowForThread.set(new UnitOfWork());
    UoW.getCurrent().open(updater);
  }

  /**
   * Closes the current {@link UnitOfWork} and database connection, without any flush or txn commit/rollback.
   */
  public static void close() {
    UoW.getCurrent().close();
    UoW.uowForThread.set(null);
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

  /** Rolls back the transaction. */
  public static void rollback() {
    UoW.getCurrent().rollback();
  }

  public static void commitAndReOpen() {
    final Updater updater = UoW.getCurrent().getUpdater();
    UoW.commit();
    UoW.close();
    UoW.open(updater);
  }

  /** Queues <code>instance</code> for validation on flush. */
  public static void enqueue(DomainObject instance) {
    UoW.getCurrent().getValidator().enqueue(instance);
  }

  /** Queues <code>instance</code> for deletion on flush. */
  public static void delete(DomainObject instance) {
    UoW.getCurrent().delete(instance);
  }

  /** @return the instance of <code>type</code> for <code>id</code>, checking the identity map */
  public static <T extends DomainObject> T load(Class<T> type, Integer id) {
    return UoW.getCurrent().getRepository().load(type, id);
  }

  public static IdentityMap getIdentityMap() {
    return UoW.getCurrent().getIdentityMap();
  }

  /** @return the current database connection */
  public static Connection getConnection() {
    return UoW.getCurrent().getRepository().getConnection();
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
    if (!committed) {
      try {
        UoW.rollback();
      } catch (Exception e) {
        Log.error("Error rolling back", e);
      }
    }
    try {
      UoW.close();
    } catch (Exception e) {
      Log.error("Error closing", e);
    }
  }

}
