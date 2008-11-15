package features.cli;

import javax.sql.DataSource;

import org.exigencecorp.domainobjects.migrations.Migrater;
import org.exigencecorp.domainobjects.migrations.MigraterConfig;

import features.Registry;

public class UpdateDatabaseCli implements Runnable {

    public void run() {
        new Migrater(new Config()).performMigrations();
    }

    public static class Config extends MigraterConfig {
        public DataSource getDataSource() {
            return Registry.getDataSource();
        }

        public String[] getPackageNamesContainingMigrations() {
            return new String[] { "features.migrations" };
        }
    }

}
