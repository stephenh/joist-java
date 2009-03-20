package features;

import javax.sql.DataSource;

import joist.domain.orm.Repository;
import joist.registry.ResourceBag;
import joist.util.Log;
import features.resources.FeaturesDataSourceFactory;

public class Registry {

    private static final ResourceBag RESOURCES = new ResourceBag();

    public static void start() {
        Log.debug("Starting...");
        Repository.datasource = Registry.getDataSource();
    }

    public static void stop() {
        Registry.RESOURCES.destroyResources();
    }

    public static DataSource getDataSource() {
        return Registry.RESOURCES.get(FeaturesDataSourceFactory.class);
    }

}
