import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.exigencecorp.util.Log;
import org.exigencecorp.util.LogAppenderFactory;

public class LogConfiguration {

    public static void setup() {
        LogManager.resetConfiguration();
        LogManager.getRootLogger().setLevel(Level.INFO);
        LogManager.getRootLogger().addAppender(LogAppenderFactory.newConsoleAppender());
        LogManager.getLogger("features").setLevel(Level.DEBUG);
        LogManager.getLogger("org.exigencecorp").setLevel(Level.DEBUG);
        // LogManager.getLogger("org.exigencecorp.jdbc").setLevel(Level.TRACE);
        Log.debug("Configuration reset, ConsoleAppender added");
    }

}
