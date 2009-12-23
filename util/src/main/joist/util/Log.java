package joist.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log {

    static {
        Log.init();
    }

    /** Looks for a LogConfiguration class.
     *
     * Kind of like log4j.properties, except log4j does not know about it, so
     * we need to explicitly init() it.
     */
    public static void init() {
        try {
            Object config = Class.forName("LogConfiguration").newInstance();
            config.getClass().getMethod("setup").invoke(null);
        } catch (Exception e) {
            System.err.println("Failed looking for a LogConfiguration: " + e.getMessage());
        }
    }

    private Log() {
    }

    public static void error(String message, Object... args) {
        Logger log = LoggerFactory.getLogger(Stack.getCallersClassName());
        if (log.isErrorEnabled()) {
            if (Log.firstIsThrowable(args)) {
                log.error(Log.interpolate(1, message, args), (Throwable) args[0]);
            } else {
                log.error(Log.interpolate(0, message, args));
            }
        }
    }

    public static void warn(String message, Object... args) {
        Logger log = LoggerFactory.getLogger(Stack.getCallersClassName());
        if (log.isWarnEnabled()) {
            if (Log.firstIsThrowable(args)) {
                log.warn(Log.interpolate(1, message, args), (Throwable) args[0]);
            } else {
                log.warn(Log.interpolate(0, message, args));
            }
        }
    }

    public static void info(String message, Object... args) {
        Logger log = LoggerFactory.getLogger(Stack.getCallersClassName());
        if (log.isInfoEnabled()) {
            if (Log.firstIsThrowable(args)) {
                log.info(Log.interpolate(1, message, args), (Throwable) args[0]);
            } else {
                log.info(Log.interpolate(0, message, args));
            }
        }
    }

    public static void debug(String message, Object... args) {
        Logger log = LoggerFactory.getLogger(Stack.getCallersClassName());
        if (log.isDebugEnabled()) {
            if (Log.firstIsThrowable(args)) {
                log.debug(Log.interpolate(1, message, args), (Throwable) args[0]);
            } else {
                log.debug(Log.interpolate(0, message, args));
            }
        }
    }

    public static void trace(String message, Object... args) {
        Logger log = LoggerFactory.getLogger(Stack.getCallersClassName());
        if (log.isTraceEnabled()) {
            if (Log.firstIsThrowable(args)) {
                log.trace(Log.interpolate(1, message, args), (Throwable) args[0]);
            } else {
                log.trace(Log.interpolate(0, message, args));
            }
        }
    }

    private static boolean firstIsThrowable(Object[] args) {
        return args.length > 0 && args[0] instanceof Throwable;
    }

    private static String interpolate(int start, String message, Object... args) {
        for (int i = start; i < args.length; i++) {
            int j = message.indexOf("{}");
            if (j != -1) {
                message = message.substring(0, j) + String.valueOf(args[i]) + message.substring(j + 2);
            }
        }
        return message;
    }

}
