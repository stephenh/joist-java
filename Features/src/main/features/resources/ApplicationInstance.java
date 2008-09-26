package features.resources;

import org.exigencecorp.domainobjects.orm.repos.sql.JdbcRepository;
import org.exigencecorp.util.Log;

import features.Registry;

public class ApplicationInstance {

    public void start() {
        Log.debug("Starting...");
        JdbcRepository.THIS_IS_DUMB = Registry.getDataSource();
    }

    public void stop() {
        JdbcRepository.THIS_IS_DUMB = null;
    }

}
