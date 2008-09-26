package click.jetty;

import org.exigencecorp.util.Log;
import org.exigencecorp.util.RunningTimer;
import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Handler;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.bio.SocketConnector;
import org.mortbay.jetty.webapp.WebAppContext;
import org.mortbay.jetty.webapp.WebInfConfiguration;
import org.mortbay.jetty.webapp.WebXmlConfiguration;

public class Jetty {

    private Jetty() {
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            Log.error("Usage: webapp");
            return;
        }

        Log.debug("Starting {}...", args[0]);
        RunningTimer.init();

        // The "org.mortbay.jetty.webapp.TagLibConfiguration" is a pain in the ass because it
        // loads the spring TLDs and ends up trying to hit java.sun.com to resolve an xsd file
        // or something dumb like that. And it stack traces out if you don't have a net connection.
        // Thanks, spring. Not scanning all the jars for TLDs seems to make Jetty load faster too.
        String[] configurationClasses = { WebInfConfiguration.class.getName(), WebXmlConfiguration.class.getName() };

        WebAppContext app = new WebAppContext();
        app.setContextPath("/webapp");
        app.setWar(args[0]);
        app.setConfigurationClasses(configurationClasses);

        WebAppContext selenium = new WebAppContext();
        selenium.setContextPath("/selenium");
        selenium.setWar(args[0]);
        selenium.setConfigurationClasses(configurationClasses);

        // We explicitly use the SocketConnector because the SelectChannelConnector locks files
        Connector connector = new SocketConnector();
        connector.setPort(80);
        connector.setMaxIdleTime(60000);

        Server server = new Server();
        server.setConnectors(new Connector[] { connector });
        server.setHandlers(new Handler[] { app, selenium });
        server.setAttribute("org.mortbay.jetty.Request.maxFormContentSize", 0);
        server.setStopAtShutdown(true);
        try {
            server.start();
        } catch (Exception e) {
            Log.error(e.getMessage(), e);
        }

        RunningTimer.tick("Started");
    }

}
