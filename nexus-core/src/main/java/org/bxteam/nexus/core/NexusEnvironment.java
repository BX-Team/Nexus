package org.bxteam.nexus.core;

import com.google.common.base.Stopwatch;
import org.bxteam.commons.logger.ExtendedLogger;

import java.util.concurrent.TimeUnit;

public class NexusEnvironment {
    private final ExtendedLogger logger;
    private final Stopwatch stopwatch = Stopwatch.createStarted();

    public NexusEnvironment(ExtendedLogger logger) {
        this.logger = logger;
    }

    public void finalizeLoading() {
        long milliseconds = this.stopwatch.elapsed(TimeUnit.MILLISECONDS);
        logger.info("Successfully enabled. (took " + milliseconds + "ms)");
    }
}
