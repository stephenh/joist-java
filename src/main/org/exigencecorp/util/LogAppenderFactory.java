package org.exigencecorp.util;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.PatternLayout;

public class LogAppenderFactory {

    private static final String LAYOUT = "%d [%t] %x %p %c - %m%n";

    private LogAppenderFactory() {
    }

    /** Sets up a console appender, merely for parallelism with the other "newXxxAppender" methods. */
    public static ConsoleAppender newConsoleAppender() {
        return new ConsoleAppender(new PatternLayout(LogAppenderFactory.LAYOUT));
    }

    /** Sets up a daily rolling file appender in <code>logDirectory</code>. */
    public static FileAppender newRollingFileAppender(String logDirectory, String fileName) {
        DailyRollingFileAppender appender = new DailyRollingFileAppender();
        appender.setDatePattern("'.'yyyy-MM-dd");
        appender.setFile(logDirectory + "/" + fileName);
        appender.setLayout(new PatternLayout(LogAppenderFactory.LAYOUT));
        appender.activateOptions();
        return appender;
    }

}
