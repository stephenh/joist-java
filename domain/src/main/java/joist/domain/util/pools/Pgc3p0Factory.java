package joist.domain.util.pools;

import joist.domain.util.ConnectionSettings;

public class Pgc3p0Factory extends AbstractC3p0Factory {

  public Pgc3p0Factory(ConnectionSettings settings) {
    super(settings, "postgresql");
  }

}
