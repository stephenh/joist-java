package features;

import javax.sql.DataSource;

import org.exigencecorp.registry.ResourceBag;

import features.resources.ApplicationInstance;
import features.resources.FeaturesDataSourceFactory;
import features.resources.FeaturesSaDataSourceFactory;

public class Registry {

    private static final ResourceBag RESOURCES = new ResourceBag();

    public static DataSource getDataSource() {
        return Registry.RESOURCES.getResourceForFactoryClass(FeaturesDataSourceFactory.class);
    }

    public static ApplicationInstance getApplicationInstance() {
        return Registry.RESOURCES.getResourceForInstanceClass(ApplicationInstance.class);
    }

    public static DataSource getSaDataSource() {
        return Registry.RESOURCES.getResourceForFactoryClass(FeaturesSaDataSourceFactory.class);
    }

}
