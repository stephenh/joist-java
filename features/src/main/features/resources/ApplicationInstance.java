package features.resources;

import joist.domain.orm.Repository;
import joist.registry.AbstractResourceFactory;
import joist.util.Log;
import features.Registry;

public class ApplicationInstance {

    public static class Factory extends AbstractResourceFactory<ApplicationInstance> {
    }

    /** Call when the app should come up. */
    public void start() {
        Log.debug("Starting...");
        Repository.datasource = Registry.getDataSource();
    }

    /** Call when the app should go down. */
    public void stop() {
        Repository.datasource = null;
    }

}
