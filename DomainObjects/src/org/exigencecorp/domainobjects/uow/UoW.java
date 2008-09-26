package org.exigencecorp.domainobjects.uow;

public class UoW {

    private static final ThreadLocal<UnitOfWork> uowForThread = new ThreadLocal<UnitOfWork>();

    public static void go(Block block) {
        UoW.open();
        try {
            block.go();
            UoW.commit();
        } finally {
            UoW.close();
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

    public static UnitOfWork getCurrent() {
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

}
