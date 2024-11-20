package space.bxteam.nexus.core;

import com.google.common.base.Stopwatch;
import io.papermc.lib.PaperLib;
import io.papermc.lib.environments.Environment;
import space.bxteam.nexus.core.utils.Logger;

import java.util.concurrent.TimeUnit;

public class NexusEnvironment {
    private final Stopwatch stopwatch = Stopwatch.createStarted();
    private static final Runtime.Version version = Runtime.version();

    public NexusEnvironment() {
        this.checkEnvironment();
    }

    public void finalizeLoading() {
        long seconds = this.stopwatch.elapsed(TimeUnit.SECONDS);
        Logger.log("Nexus has been enabled in " + seconds + "s.", Logger.LogLevel.INFO);
    }

    private void checkEnvironment() {
        Logger.log("Checking requirements...", Logger.LogLevel.INFO);
        Environment environment = PaperLib.getEnvironment();

        if (version.feature() < 21) {
            Logger.log("Your server is running on an unsupported Java version. Please consider using Java 21 or you may encounter issues and bugs.", Logger.LogLevel.WARNING);
            return;
        }

        Logger.log("Your server is running on a supported software.", Logger.LogLevel.INFO);
        Logger.log("Server version: " + environment.getMinecraftVersion(), Logger.LogLevel.INFO);
    }
}
