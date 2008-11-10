package features.cli;

import javax.sql.DataSource;

import org.exigencecorp.domainobjects.updater.Resetter;
import org.exigencecorp.domainobjects.updater.ResetterConfig;

import features.Registry;

public class ResetDatabaseCli implements Runnable {

    public void run() {
        new Resetter(new Config()).reset();
    }

    public static class Config extends ResetterConfig {
        public DataSource getDataSource() {
            return Registry.getSaDataSource();
        }

        public String getDatabaseName() {
            return "features";
        }

        public String getUsername() {
            return "features_role";
        }

        public String getPassword() {
            return "features_role";
        }
    }

}
