package joist.domain.orm;

import java.util.concurrent.atomic.AtomicBoolean;

/** Class to flag unit testing on to more easily work with models sans the db. */
public class UnitTesting {

  private static AtomicBoolean enabled = new AtomicBoolean(false);

  public static boolean isEnabled() {
    return enabled.get();
  }

  public static void setEnabled(boolean enabled) {
    UnitTesting.enabled.set(enabled);
  }

}
