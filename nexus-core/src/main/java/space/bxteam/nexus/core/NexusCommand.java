package space.bxteam.nexus.core;

import com.google.common.base.Stopwatch;
import com.google.inject.Inject;
import dev.rollczi.litecommands.annotations.async.Async;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.CommandSender;
import space.bxteam.nexus.core.configuration.PluginConfigurationProvider;
import space.bxteam.nexus.core.multification.MultificationManager;

@Command(name = "nexus")
@Permission("nexus.nexus")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class NexusCommand {
    private final PluginConfigurationProvider configurationProvider;
    private final MultificationManager multificationManager;

    @Async
    @Execute(name = "reload")
    void reload(@Context CommandSender sender) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        this.configurationProvider.loadConfig();
    }

    @Execute(name = "version")
    void version() {
        // Send current version and start update checker
    }
}
