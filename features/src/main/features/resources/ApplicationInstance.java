package features.resources;

import joist.domain.orm.Repository;

import org.exigencecorp.util.Log;

import features.Registry;

public class ApplicationInstance {

    public void start() {
        Log.debug("Starting...");
        Repository.THIS_IS_DUMB = Registry.getDataSource();
    }

    public void stop() {
        Repository.THIS_IS_DUMB = null;
    }

}
