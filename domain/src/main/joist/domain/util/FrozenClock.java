package joist.domain.util;

import com.domainlanguage.time.TimePoint;
import com.domainlanguage.time.TimeSource;
import com.domainlanguage.timeutil.Clock;

public class FrozenClock implements TimeSource {

    private final TimePoint frozen;

    public static void freeze(TimePoint frozen) {
        Clock.setTimeSource(new FrozenClock(frozen));
    }

    public static void unfreeze() {
        Clock.setTimeSource(null);
    }

    private FrozenClock(TimePoint frozen) {
        this.frozen = frozen;
    }

    public TimePoint now() {
        return this.frozen;
    }

}
