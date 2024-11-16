package space.bxteam.nexus.core.environment;

import com.google.common.base.Stopwatch;
import org.bukkit.Server;
import space.bxteam.nexus.core.utils.Logger;

import java.util.concurrent.TimeUnit;

public class NexusEnvironment {
    private final Server server;
    private final Stopwatch stopwatch = Stopwatch.createStarted();

    public NexusEnvironment(Server server) {
        this.server = server;
        this.checkEnvironment();
    }

    public void finalizeLoading() {
        long millis = this.stopwatch.elapsed(TimeUnit.MILLISECONDS);
        Logger.log("Nexus has been enabled in " + millis + "ms.", Logger.LogLevel.INFO);
    }

    private void checkEnvironment() {
        Logger.log("Checking requirements...", Logger.LogLevel.INFO);

        if (!Environment.ENVIRONMENT.isPaper()) {
            Logger.log("Your server is running on unsupported software. Some features may not work properly on this software.", Logger.LogLevel.WARNING);
            Logger.log("Please consider using Paper or their forks for better performance and compatibility.", Logger.LogLevel.WARNING);
            Logger.log("You can download Paper from https://papermc.io/downloads", Logger.LogLevel.WARNING);
            return;
        }

        if (!Environment.ENVIRONMENT.isJavaVersion(21)) {
            Logger.log("Your server is running on an unsupported Java version. Please consider using Java 21 or you may encounter issues and bugs.", Logger.LogLevel.WARNING);
            return;
        }

        Logger.log("Your server is running on a supported software.", Logger.LogLevel.INFO);
        Logger.log("Server version: " + server.getMinecraftVersion(), Logger.LogLevel.INFO);
    }
}
