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

public abstract class AbstractC3p0Factory {

  private static final Logger log = LoggerFactory.getLogger(AbstractC3p0Factory.class);

  static {
    // Save 50-100ms because we don't care about JMX/whatever
    System.setProperty(ManagementCoordinator.class.getName(), NullManagementCoordinator.class.getName());
    // Save 50ms because we don't care about JNDI
    System.setProperty("com.mchange.v2.c3p0.VMID", "NONE");
  }

  protected final ConnectionSettings settings;
  private final String type;

  public AbstractC3p0Factory(ConnectionSettings settings, String type) {
    this.settings = settings;
    this.type = type;
  }

  public ComboPooledDataSource create() {
    ComboPooledDataSource ds = new ComboPooledDataSource();
    ds.setJdbcUrl("jdbc:" + this.type + "://" + this.settings.host + "/" + this.settings.databaseName);
    ds.setUser(this.settings.user);
    ds.setPassword(this.settings.password);
    ds.setMaxPoolSize(this.settings.maxPoolSize);
    ds.setInitialPoolSize(this.settings.initialPoolSize);
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
