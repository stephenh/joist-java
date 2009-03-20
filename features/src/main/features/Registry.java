package features;

import javax.sql.DataSource;

import joist.domain.orm.Repository;
import joist.registry.ResourceBag;
import joist.util.Log;
import features.resources.FeaturesDataSourceFactory;

public class Registry {

    private static final ResourceBag resourcesBag = new ResourceBag();

    public static void start() {
        Log.debug("Starting...");
        Repository.datasource = Registry.resourcesBag.getLazy(FeaturesDataSourceFactory.class);
    }

    public static void stop() {
        Registry.resourcesBag.destroyResources();
    }

    public static DataSource getDataSource() {
        return Registry.resourcesBag.get(FeaturesDataSourceFactory.class);
    }

}
