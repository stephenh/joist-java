package org.exigencecorp.util;

import java.text.DecimalFormat;

import com.vladium.utils.timing.ITimer;
import com.vladium.utils.timing.TimerFactory;

public class RunningTimer {

    private static final ThreadLocal<ITimer> timer = new ThreadLocal<ITimer>();
    private static final DecimalFormat format = new DecimalFormat();

    static {
        RunningTimer.format.setMinimumFractionDigits(3);
        RunningTimer.format.setMaximumFractionDigits(3);
    }

    public static void init() {
        Log.debug("Starting...");
        RunningTimer.timer.set(TimerFactory.newTimer());
        RunningTimer.timer.get().start();
    }

    public static void tick(String name) {
        RunningTimer.timer.get().stop();
        Log.debug("{} took {}ms", name, RunningTimer.format.format(RunningTimer.timer.get().getDuration()));
        RunningTimer.timer.get().reset();
        RunningTimer.timer.get().start();
    }

}
