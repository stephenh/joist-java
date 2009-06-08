package features;

import javax.sql.DataSource;

import joist.domain.orm.Repository;
import joist.domain.util.ConnectionSettings;
import joist.domain.util.PgUtilFactory;
import joist.registry.ResourceRef;
import joist.registry.ResourceRefs;
import joist.util.Log;
import joist.util.SystemProperties;

public class Registry {

    public static DataSource getDataSource() {
        return Registry.instance.appDatasource.get();
    }

    public static DataSource getSaDataSource() {
        return Registry.instance.saDatasource.get();
    }

    public static void start() {
        Registry.instance.start2();
    }

    public static void stop() {
        Registry.instance.stop2();
    }

    private final static Registry instance = new Registry();
    private final ResourceRefs refs = new ResourceRefs();
    private final ResourceRef<DataSource> appDatasource;
    private final ResourceRef<DataSource> saDatasource;

    private Registry() {
        SystemProperties.loadFromFileIfExists("./build.properties");
        this.appDatasource = this.refs.newRef(DataSource.class).factory(new PgUtilFactory(ConnectionSettings.forApp("features"))).make();
        this.saDatasource = this.refs.newRef(DataSource.class).factory(new PgUtilFactory(ConnectionSettings.forSa("features"))).make();
        Repository.datasource = this.appDatasource;
    }

    private void start2() {
        Log.debug("Starting...");
    }

    private void stop2() {
        this.refs.stop();
    }

}
