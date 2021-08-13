package com.paloalto.utils;

import org.springframework.util.StopWatch;

public class StopWatchUtils {

    public static StopWatch getStopWatch() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        return stopWatch;
    }

    public static long getTotalTimeNanos(StopWatch stopWatch) {
        stopWatch.stop();
        return stopWatch.getTotalTimeNanos();
    }

    public static double getTotalTimeSeconds(StopWatch stopWatch) {
        stopWatch.stop();
        return stopWatch.getTotalTimeSeconds();
    }
}
