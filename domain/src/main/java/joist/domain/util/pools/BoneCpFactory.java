package joist.domain.util.pools;

import joist.domain.util.ConnectionSettings;

import com.jolbox.bonecp.BoneCPConfig;
import com.jolbox.bonecp.BoneCPDataSource;

public class BoneCpFactory {

  private final ConnectionSettings settings;
  private final String type = "postgresql";

  public BoneCpFactory(ConnectionSettings settings) {
    this.settings = settings;
  }

  public BoneCPDataSource create() {
    BoneCPConfig config = new BoneCPConfig();
    config.setJdbcUrl("jdbc:" + this.type + "://" + this.settings.host + "/" + this.settings.databaseName);
    config.setUsername(this.settings.user);
    config.setPassword(this.settings.password);
    config.setMinConnectionsPerPartition(1);
    config.setMaxConnectionsPerPartition(50);
    config.setPartitionCount(2);
    config.setAcquireIncrement(1);
    config.setConnectionTestStatement("select 1");
    config.setIdleConnectionTestPeriodInSeconds(60);
    return new BoneCPDataSource(config);
  }

  public void destroy(BoneCPDataSource cp) {
    cp.close();
  }

}
