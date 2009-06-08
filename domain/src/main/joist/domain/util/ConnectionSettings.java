package joist.domain.util;

public class ConnectionSettings {

    public String databaseName;
    public String user;
    public String password;
    public String host;
    public int maxPoolSize = 3;
    public int initialPoolSize = 1;

    public static ConnectionSettings forApp(String databaseName) {
        ConnectionSettings settings = new ConnectionSettings();
        settings.host = System.getProperty("db.host", "localhost");
        settings.databaseName = System.getProperty("db.name", databaseName);
        settings.user = System.getProperty("db.username", databaseName + "_role");
        settings.password = System.getProperty("db.password", databaseName + "_role");
        return settings;
    }

    public static ConnectionSettings forSa(String databaseName) {
        ConnectionSettings settings = new ConnectionSettings();
        settings.host = System.getProperty("db.host", "localhost");
        settings.databaseName = System.getProperty("db.name", databaseName);
        settings.user = System.getProperty("db.sa.username", "postgres");
        settings.password = System.getProperty("db.sa.password", ".");
        return settings;
    }

}
