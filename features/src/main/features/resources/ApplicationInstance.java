package features.resources;

import joist.domain.orm.Repository;

import org.exigencecorp.util.Log;

import features.Registry;

public class ApplicationInstance {

    public void start() {
        Log.debug("Starting...");
        Repository.datasource = Registry.getDataSource();
    }

    public void stop() {
        Repository.datasource = null;
    }

}
