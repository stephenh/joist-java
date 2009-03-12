package features;

import javax.sql.DataSource;

import org.exigencecorp.registry.ResourceBag;

import features.resources.ApplicationInstance;
import features.resources.FeaturesDataSourceFactory;

public class Registry {

    private static final ResourceBag RESOURCES = new ResourceBag();

    public static DataSource getDataSource() {
        return Registry.RESOURCES.getResourceForFactoryClass(FeaturesDataSourceFactory.class);
    }

    public static ApplicationInstance getApplicationInstance() {
        return Registry.RESOURCES.getResourceForInstanceClass(ApplicationInstance.class);
    }

}
