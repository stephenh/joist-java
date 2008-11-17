package features.resources;

import org.exigencecorp.domainobjects.util.AbstractPgWithc3p0DataSourceFactory;

public class FeaturesDataSourceFactory extends AbstractPgWithc3p0DataSourceFactory {

    public FeaturesDataSourceFactory() {
        super("features");
    }

}
