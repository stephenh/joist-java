package features.resources;

import org.exigencecorp.registry.resources.AbstractPostgresWithc3p0DataSourceFactory;

public class FeaturesSaDataSourceFactory extends AbstractPostgresWithc3p0DataSourceFactory {

    public FeaturesSaDataSourceFactory() {
        super("postgres");
    }

}
