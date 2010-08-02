package joist.domain.util;

import com.domainlanguage.time.TimePoint;
import com.domainlanguage.time.TimeSource;
import com.domainlanguage.timeutil.Clock;

/** A {@link TimeSource} for a frozen point in time.
 *
 * See {@link Freeze} for a nicer interface with helper methods.
 */
public class FrozenClock implements TimeSource {

  private static boolean isFrozen;
  private final TimePoint frozen;

  public static boolean isFrozen() {
    return FrozenClock.isFrozen;
  }

  public static void freeze(TimePoint frozen) {
    Clock.setTimeSource(new FrozenClock(frozen));
    FrozenClock.isFrozen = true;
  }

  public static void unfreeze() {
    Clock.setTimeSource(null);
    FrozenClock.isFrozen = false;
  }

  public FrozenClock(TimePoint frozen) {
    this.frozen = frozen;
  }

  public TimePoint now() {
    return this.frozen;
  }

}
