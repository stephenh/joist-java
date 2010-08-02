package joist.domain.util;

import java.util.Date;

import com.domainlanguage.time.TimePoint;
import com.domainlanguage.time.TimeSource;

/** A {@link TimeSource} for the current time. */
public class WallClock implements TimeSource {

  public TimePoint now() {
    return TimePoint.from(new Date());
  }

}
