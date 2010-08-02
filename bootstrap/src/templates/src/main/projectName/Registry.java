package @projectName@;

import java.util.TimeZone;

import javax.sql.DataSource;

import joist.domain.orm.Repository;
import joist.domain.util.ConnectionSettings;
import joist.domain.util.PgUtilFactory;
import joist.registry.ResourceRef;
import joist.registry.ResourceRefs;
import joist.util.Log;
import joist.util.SystemProperties;

import com.domainlanguage.timeutil.Clock;

public class Registry {

    private final static Registry instance = new Registry();
    private final Environment env = Environment.valueOf(System.getProperty("@projectName@.env", "DEV"));
    private final ResourceRefs refs;
    private final ResourceRef<DataSource> appDatasource;

    public static void start() {
        Registry.instance.start2();
    }

    public static void stop() {
        Registry.instance.stop2();
    }

    public static DataSource getDataSource() {
        return Registry.instance.appDatasource.get();
    }

    public static Environment getEnv() {
        return Registry.instance.env;
    }

    private Registry() {
        SystemProperties.loadFromFileIfExists("./build.properties");
        this.refs = new ResourceRefs();
        this.appDatasource = this.refs.newRef(DataSource.class).factory(new PgUtilFactory(ConnectionSettings.forApp("@projectName@"))).make();
    }

    private void start2() {
        Log.debug("Starting {}", this.env);
        Clock.setDefaultTimeZone(TimeZone.getDefault());
        Repository.datasource = this.appDatasource;
    }

    private void stop2() {
        this.refs.stop();
    }

}
