package features.cli;

import javax.sql.DataSource;

import org.exigencecorp.updater.Updater;
import org.exigencecorp.updater.UpdaterConfig;

import features.Registry;

public class UpdateDatabaseCli implements Runnable {

    public void run() {
        new Updater(new Config()).performUpdates();
    }

    public static class Config extends UpdaterConfig {
        public DataSource getDataSource() {
            return Registry.getDataSource();
        }

        public String[] getPackageNamesContainingUpdates() {
            return new String[] { "features.updates" };
        }
    }

}
