package space.bxteam.nexus.core;

import com.google.common.base.Stopwatch;
import com.google.inject.Inject;
import dev.rollczi.litecommands.annotations.async.Async;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import lombok.RequiredArgsConstructor;
import space.bxteam.nexus.core.configuration.PluginConfigurationProvider;
import space.bxteam.nexus.core.message.MessageManager;

@Command(name = "nexus")
@Permission("nexus.nexus")
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class NexusCommand {
    private final PluginConfigurationProvider configurationProvider;
    private final MessageManager messageManager;

    @Async
    @Execute(name = "reload")
    void reload() {
        Stopwatch stopwatch = Stopwatch.createStarted();
        this.configurationProvider.loadConfig();
    }

    @Execute(name = "version")
    void version() {
        // Send current version and start update checker
    }
}
