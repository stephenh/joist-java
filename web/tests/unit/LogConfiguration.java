import joist.util.Log;
import joist.util.LogAppenderFactory;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;

public class LogConfiguration {

    public static void setup() {
        LogManager.resetConfiguration();
        LogManager.getRootLogger().addAppender(LogAppenderFactory.newConsoleAppender());
        LogManager.getRootLogger().setLevel(Level.INFO);
        LogManager.getLogger("joist").setLevel(Level.DEBUG);
        Log.info("Configuration reset, ConsoleAppender added");
    }

}
