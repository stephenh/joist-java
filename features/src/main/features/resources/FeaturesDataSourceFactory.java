package features.resources;

import joist.domain.util.AbstractH2RamDataSourceFactory;

public class FeaturesDataSourceFactory extends AbstractH2RamDataSourceFactory {
    // public class FeaturesDataSourceFactory extends AbstractPgWithc3p0DataSourceFactory {

    public FeaturesDataSourceFactory() {
        super("features");
    }

}
