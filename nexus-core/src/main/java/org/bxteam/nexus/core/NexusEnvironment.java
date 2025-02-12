package org.bxteam.nexus.core;

import com.google.common.base.Stopwatch;
import org.bukkit.plugin.Plugin;
import org.bxteam.nexus.core.utils.Logger;

import java.util.concurrent.TimeUnit;

public class NexusEnvironment {
    private final Plugin plugin;
    private final Stopwatch stopwatch = Stopwatch.createStarted();

    public NexusEnvironment(Plugin plugin) {
        this.plugin = plugin;
    }

    public void finalizeLoading() {
        long milliseconds = this.stopwatch.elapsed(TimeUnit.MILLISECONDS);
        Logger.log("Successfully enabled. (took " + milliseconds + "ms)");
    }
}
