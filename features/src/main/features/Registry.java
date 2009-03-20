package features;

import javax.sql.DataSource;

import joist.registry.ResourceBag;
import features.resources.ApplicationInstance;
import features.resources.FeaturesDataSourceFactory;

public class Registry {

    private static final ResourceBag RESOURCES = new ResourceBag();

    public static DataSource getDataSource() {
        return Registry.RESOURCES.get(FeaturesDataSourceFactory.class);
    }

    public static ApplicationInstance getApplicationInstance() {
        return Registry.RESOURCES.get(ApplicationInstance.Factory.class);
    }

}
