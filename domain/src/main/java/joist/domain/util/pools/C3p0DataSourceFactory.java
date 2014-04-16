package joist.domain.util.pools;

import java.sql.SQLException;

import javax.sql.DataSource;

import joist.domain.util.ConnectionSettings;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;
import com.mchange.v2.c3p0.management.ManagementCoordinator;
import com.mchange.v2.c3p0.management.NullManagementCoordinator;

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
    String type = settings.db.isPg() ? "postgresql" : "mysql";
    ComboPooledDataSource ds = new ComboPooledDataSource();
    ds.setJdbcUrl("jdbc:" + type + "://" + settings.host + "/" + settings.databaseName);
    ds.setUser(settings.user);
    ds.setPassword(settings.password);
    ds.setMaxPoolSize(settings.maxPoolSize);
    ds.setInitialPoolSize(settings.initialPoolSize);
    ds.setPreferredTestQuery("select 1");
    ds.setTestConnectionOnCheckout(true);
    ds.setAcquireRetryAttempts(1); // only try once, then fail fast
    ds.setCheckoutTimeout(5000); // don't block on a full pool, but needs to be large enough for new connection spin up
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
