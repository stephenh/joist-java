import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.exigencecorp.util.Log;
import org.exigencecorp.util.LogAppenderFactory;

public class LogConfiguration {

    public static void setup() {
        LogManager.resetConfiguration();
        LogManager.getRootLogger().setLevel(Level.INFO);
        LogManager.getRootLogger().addAppender(LogAppenderFactory.newConsoleAppender());
        LogManager.getLogger("org.exigencecorp").setLevel(Level.DEBUG);
        Log.debug("Configuration reset, ConsoleAppender added");
    }

}
