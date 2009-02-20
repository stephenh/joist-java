package org.exigencecorp.domainobjects.uow;

import java.sql.Connection;

import org.exigencecorp.domainobjects.DomainObject;
import org.exigencecorp.domainobjects.orm.IdentityMap;
import org.exigencecorp.util.Log;

public class UoW {

    private static final ThreadLocal<UnitOfWork> uowForThread = new ThreadLocal<UnitOfWork>();

    public static void go(Block block) {
        boolean committed = false;
        UoW.open();
        try {
            block.go();
            UoW.commit();
            committed = true;
        } finally {
            UoW.safelyRollbackAndCloseIfNeeded(committed);
        }
    }

    public static <T> T go(BlockWithReturn<T> block) {
        boolean committed = false;
        UoW.open();
        try {
            T value = block.go();
            UoW.commit();
            committed = true;
            return value;
        } finally {
            UoW.safelyRollbackAndCloseIfNeeded(committed);
        }
    }

    public static void go(BlockWithSafety block) {
        boolean committed = false;
        UoW.open();
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

    public static <T> T go(BlockWithReturnAndSafety<T> block) {
        boolean committed = false;
        UoW.open();
        try {
            T value = block.go();
            UoW.commit();
            committed = true;
            return value;
        } catch (Exception e) {
            block.stopped(e);
            return null;
        } finally {
            UoW.safelyRollbackAndCloseIfNeeded(committed);
        }
    }

    public static boolean isOpen() {
        return UoW.uowForThread.get() != null;
    }

    public static void open() {
        UoW.assertClosed();
        UoW.uowForThread.set(new UnitOfWork());
        UoW.getCurrent().open();
    }

    public static void close() {
        UoW.getCurrent().close();
        UoW.uowForThread.set(null);
    }

    public static void flush() {
        UoW.getCurrent().flush();
    }

    public static void commit() {
        UoW.getCurrent().commit();
    }

    public static void rollback() {
        UoW.getCurrent().rollback();
    }

    public static <T extends DomainObject> void enqueue(T instance) {
        UoW.getCurrent().getValidator().enqueue(instance);
    }

    public static <T extends DomainObject> void delete(T instance) {
        UoW.getCurrent().delete(instance);
    }

    public static <T extends DomainObject> T load(Class<T> type, Integer id) {
        return UoW.getCurrent().getRepository().load(type, id);
    }

    public static IdentityMap getIdentityMap() {
        return UoW.getCurrent().getIdentityMap();
    }

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
