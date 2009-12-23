import joist.util.Log;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.PatternLayout;

public class LogConfiguration {

    public static void setup() {
        LogManager.resetConfiguration();
        LogManager.getRootLogger().setLevel(Level.INFO);
        LogManager.getRootLogger().addAppender(new ConsoleAppender(new PatternLayout("%d [%t] %x %p %c - %m%n")));
        LogManager.getLogger("features").setLevel(Level.DEBUG);
        LogManager.getLogger("joist").setLevel(Level.DEBUG);
        // LogManager.getLogger("joist.jdbc").setLevel(Level.TRACE);
        Log.debug("Configuration reset, ConsoleAppender added");
    }

}
