package joist.domain.util.pools;

import joist.domain.util.ConnectionSettings;

public class MySqlC3p0Factory extends AbstractC3p0Factory {

  public MySqlC3p0Factory(ConnectionSettings settings) {
    super(settings, "mysql");
  }
}
