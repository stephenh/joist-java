package features.resources;

import org.exigencecorp.registry.resources.AbstractPostgresWithc3p0DataSourceFactory;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class FeaturesSaDataSourceFactory extends AbstractPostgresWithc3p0DataSourceFactory {

    public FeaturesSaDataSourceFactory() {
        super("postgres");
    }

    public ComboPooledDataSource create() {
        ComboPooledDataSource ds = super.create();
        ds.setUser("sa");
        ds.setPassword("postgresql_chimera");
        return ds;
    }

}
