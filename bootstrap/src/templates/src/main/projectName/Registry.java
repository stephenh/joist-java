package @projectName@;

import java.util.TimeZone;

import javax.sql.DataSource;

import joist.domain.orm.Repository;
import joist.registry.ResourceBag;
import joist.util.Log;
import @projectName@.resources.@ProjectName@DataSourceFactory;

import com.domainlanguage.timeutil.Clock;

public class Registry {

    private static final Environment env = Environment.valueOf(System.getProperty("@projectName@.env", "DEV"));
    private static final ResourceBag resources = new ResourceBag();

    public static void start() {
        Log.debug("Starting {}", env);
        Clock.setDefaultTimeZone(TimeZone.getDefault());
        Repository.datasource = Registry.resources.getLazy(@ProjectName@DataSourceFactory.class);
    }

    public static void stop() {
        Registry.resources.destroyResources();
    }

    public static DataSource getDataSource() {
        return Registry.resources.get(@ProjectName@DataSourceFactory.class);
    }

    public static Environment getEnv() {
        return env;
    }

}
