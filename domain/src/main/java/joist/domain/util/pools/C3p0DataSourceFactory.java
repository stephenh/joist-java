package joist.domain.util.pools;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;
import com.mchange.v2.c3p0.management.ManagementCoordinator;
import com.mchange.v2.c3p0.management.NullManagementCoordinator;

import joist.domain.util.ConnectionSettings;

/**
 * Creates C3P0-backed connection pools.
 *
 * Prefer the HikariDataSourceFactory instead.
 */
@Deprecated()
public class C3p0DataSourceFactory implements DataSourceFactory {

  private static final Logger log = LoggerFactory.getLogger(C3p0DataSourceFactory.class);

  static {
    // Save 50-100ms because we don't care about JMX/whatever
    System.setProperty(ManagementCoordinator.class.getName(), NullManagementCoordinator.class.getName());
    // Save 50ms because we don't care about JNDI
    System.setProperty("com.mchange.v2.c3p0.VMID", "NONE");
  }

  @Override
  public ComboPooledDataSource create(ConnectionSettings settings) {
    ComboPooledDataSource ds = new ComboPooledDataSource();
    String settingsSuffix = "?";
    if (settings.db.isPg()) {
      settingsSuffix += "loginTimeout=" + settings.timeoutInSeconds + "&socketTimeout=" + settings.timeoutInSeconds;
    } else if (settings.db.isMySQL()) {
      settingsSuffix += "connectTimeout=" + (settings.timeoutInSeconds * 1000) + "&socketTimeout=" + (settings.timeoutInSeconds * 1000);
      // convertToNull == don't blow up on '0000-00-00 00:00:00' timestamp values
      settingsSuffix += "&zeroDateTimeBehavior=convertToNull";
    }
    String type = settings.db.isPg() ? "postgresql" : "mysql";
    ds.setJdbcUrl("jdbc:" + type + "://" + settings.host + "/" + settings.databaseName + settingsSuffix);
    ds.setUser(settings.user);
    ds.setPassword(settings.password);
    ds.setMaxPoolSize(settings.maxPoolSize);
    ds.setInitialPoolSize(settings.initialPoolSize);
    ds.setPreferredTestQuery("select 1");
    ds.setTestConnectionOnCheckout(true);
    // only try once, then fail fast
    ds.setAcquireRetryAttempts(1);
    // don't block on a full pool, but needs to be large enough for new connection spin up
    ds.setCheckoutTimeout((settings.timeoutInSeconds + 1) * 1000);
    return ds;
  }

  public void destroy(DataSource dataSource) {
    try {
      DataSources.destroy(dataSource);
    } catch (SQLException se) {
      log.error("Error while destroying dataSource {}", se, dataSource);
    }
  }
}
