package features.resources;

import org.exigencecorp.domainobjects.util.AbstractPgWithc3p0DataSourceFactory;

public class FeaturesSaDataSourceFactory extends AbstractPgWithc3p0DataSourceFactory {

    public FeaturesSaDataSourceFactory() {
        super("postgres");
    }

}
