package org.exigencecorp.util;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class Log {

    static {
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
        Log.log(Stack.getCallersClassName(), Level.ERROR, message, args);
    }

    public static void warn(String message, Object... args) {
        Log.log(Stack.getCallersClassName(), Level.WARN, message, args);
    }

    public static void info(String message, Object... args) {
        Log.log(Stack.getCallersClassName(), Level.INFO, message, args);
    }

    public static void debug(String message, Object... args) {
        Log.log(Stack.getCallersClassName(), Level.DEBUG, message, args);
    }

    public static void trace(String message, Object... args) {
        Log.log(Stack.getCallersClassName(), Level.TRACE, message, args);
    }

    public static void log(Level level, String message, Object... args) {
        Log.log(Stack.getCallersClassName(), level, message, args);
    }

    private static void log(String callersClassName, Level level, String message, Object... args) {
        Logger logger = Logger.getLogger(callersClassName);
        if (!logger.isEnabledFor(level)) {
            return;
        }

        if (args.length > 0 && args[0] instanceof Throwable) {
            Throwable t = (Throwable) args[0];
            args = ArrayUtils.subarray(args, 1, args.length);
            logger.log(level, Interpolate.string(message, args), t);
        } else {
            logger.log(level, Interpolate.string(message, args));
        }
    }

}
