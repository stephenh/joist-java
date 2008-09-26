package features.resources;

import org.exigencecorp.registry.resources.AbstractPostgresWithc3p0DataSourceFactory;

public class FeaturesDataSourceFactory extends AbstractPostgresWithc3p0DataSourceFactory {

    public FeaturesDataSourceFactory() {
        super("features");
    }

}
