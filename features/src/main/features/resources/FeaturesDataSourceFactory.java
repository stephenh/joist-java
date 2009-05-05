package features.resources;

import joist.domain.util.AbstractPgWithUtilDataSourceFactory;

public class FeaturesDataSourceFactory extends AbstractPgWithUtilDataSourceFactory {

    public FeaturesDataSourceFactory() {
        super("features");
    }

}
