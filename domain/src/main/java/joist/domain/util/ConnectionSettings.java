package joist.domain.util;

import joist.domain.orm.Db;

public class ConnectionSettings {

    public Db db;
    public String schemaName;
    public String databaseName;
    public String user;
    public String password;
    public String host;
    public int maxPoolSize = 3;
    public int initialPoolSize = 1;

    public static ConnectionSettings forApp(Db db, String databaseName) {
        ConnectionSettings settings = new ConnectionSettings();
        settings.db = db;
        settings.host = System.getProperty("db.host", "localhost");
        settings.databaseName = System.getProperty("db.name", databaseName);
        settings.schemaName = db.isPg() ? "public" : settings.databaseName;
        settings.user = System.getProperty("db.username", databaseName + "_role");
        settings.password = System.getProperty("db.password", databaseName + "_role");
        return settings;
    }

    public static ConnectionSettings forSa(Db db, String databaseName) {
        ConnectionSettings settings = new ConnectionSettings();
        settings.db = db;
        settings.host = System.getProperty("db.host", "localhost");
        settings.databaseName = System.getProperty("db.name", databaseName);
        settings.schemaName = db.isPg() ? "public" : settings.databaseName;
        settings.user = System.getProperty("db.sa.username", db.isPg() ? "postgres" : "root");
        settings.password = System.getProperty("db.sa.password", ".");
        return settings;
    }

}
