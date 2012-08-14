package joist.domain.orm;

import java.util.concurrent.atomic.AtomicBoolean;

/** Class to flag eager loading. */
public class EagerLoading {

  private static AtomicBoolean enabled = new AtomicBoolean(true);

  public static boolean isEnabled() {
    return enabled.get();
  }

  public static void setEnabled(boolean enabled) {
    EagerLoading.enabled.set(enabled);
  }

}
