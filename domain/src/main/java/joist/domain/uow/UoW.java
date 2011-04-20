package joist.domain.uow;

import java.sql.Connection;

import joist.domain.DomainObject;
import joist.domain.orm.EagerCache;
import joist.domain.orm.IdentityMap;
import joist.domain.orm.Updater;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UoW {

  private static final ThreadLocal<UnitOfWork> uowForThread = new ThreadLocal<UnitOfWork>();

  public static void go(Updater updater, Block block) {
    boolean committed = false;
    try {
      UoW.open(updater);
      block.go();
      UoW.commit();
      committed = true;
    } finally {
      UoW.safelyRollbackAndCloseIfNeeded(committed);
    }
  }

  public static <T> T go(Updater updater, BlockWithReturn<T> block) {
    boolean committed = false;
    try {
      UoW.open(updater);
      T value = block.go();
      UoW.commit();
      committed = true;
      return value;
    } finally {
      UoW.safelyRollbackAndCloseIfNeeded(committed);
    }
  }

  public static <T> T read(BlockWithReturn<T> block) {
    try {
      UoW.open(null);
      return block.go();
    } finally {
      UoW.safelyRollbackAndCloseIfNeeded(true);
    }
  }

  public static void go(Updater updater, BlockWithSafety block) {
    boolean committed = false;
    try {
      UoW.open(updater);
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
    try {
      UoW.open(updater);
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
  public static <T extends DomainObject> T load(Class<T> type, Long id) {
    return UoW.getCurrent().getRepository().load(type, id);
  }

  public static IdentityMap getIdentityMap() {
    return UoW.getCurrent().getIdentityMap();
  }

  public static EagerCache getEagerCache() {
    return UoW.getCurrent().getEagerCache();
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
