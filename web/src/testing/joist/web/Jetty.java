package joist.web;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.List;

import joist.util.Log;

import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Handler;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.bio.SocketConnector;
import org.mortbay.jetty.webapp.WebAppContext;
import org.mortbay.jetty.webapp.WebInfConfiguration;
import org.mortbay.jetty.webapp.WebXmlConfiguration;

public class Jetty {

    private static final Server SERVER = new Server();

    /** @param args <code>/=./content, /foo=./bar</code> */
    public static void main(String[] args) {
        Log.init();

        if (args == null || args.length == 0) {
            args = new String[] { "/=content" };
        }

        List<Handler> handlers = new ArrayList<Handler>();
        for (String arg : args) {
            String[] parts = arg.split("=");
            WebAppContext app = new WebAppContext();
            app.setContextPath(parts[0]);
            app.setWar(parts[1]);
            app.setConfigurationClasses(new String[] { WebInfConfiguration.class.getName(), WebXmlConfiguration.class.getName() });
            app.setParentLoaderPriority(true);
            handlers.add(app);
        }

        // Use SocketConnector because SelectChannelConnector locks files
        Connector connector = new SocketConnector();
        connector.setPort(new Integer(System.getProperty("jetty.port", "8080")));
        connector.setMaxIdleTime(60000);

        Jetty.SERVER.setConnectors(new Connector[] { connector });
        Jetty.SERVER.setHandlers(handlers.toArray(new Handler[] {}));
        Jetty.SERVER.setAttribute("org.mortbay.jetty.Request.maxFormContentSize", 0);
        Jetty.SERVER.setStopAtShutdown(true);

        try {
            Jetty.SERVER.start();
            getLock();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void startIfNotRunning() {
        if (getLock() != null) {
            Jetty.main(null);
        }
    }

    private static FileLock getLock() {
        try {
            RandomAccessFile r = new RandomAccessFile("./bin/jetty.lock", "rw");
            return r.getChannel().tryLock();
        } catch (IOException io) {
            throw new RuntimeException(io);
        }
    }

}
