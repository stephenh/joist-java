package features.resources;

import joist.domain.util.AbstractPgWithc3p0DataSourceFactory;

public class FeaturesDataSourceFactory extends AbstractPgWithc3p0DataSourceFactory {

    public FeaturesDataSourceFactory() {
        super("features");
    }

}
