package space.bxteam.nexus.core;

import com.eternalcode.multification.notice.Notice;
import com.google.common.base.Stopwatch;
import com.google.inject.Inject;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.CommandSender;
import space.bxteam.commons.scheduler.Scheduler;
import space.bxteam.nexus.annotations.scan.command.CommandDocs;
import space.bxteam.nexus.core.configuration.ConfigurationManager;
import space.bxteam.nexus.core.configuration.plugin.PluginConfigurationProvider;
import space.bxteam.nexus.core.multification.MultificationManager;
import space.bxteam.nexus.core.translation.TranslationProvider;

import java.time.Duration;

@Command(name = "nexus")
@Permission("nexus.nexus")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class NexusCommand {
    private final ConfigurationManager configurationManager;
    private final MultificationManager multificationManager;
    private final TranslationProvider translationProvider;
    private final PluginConfigurationProvider configurationProvider;
    private final Scheduler scheduler;

    private static final Notice RELOADED = Notice.chat("<green>Nexus configuration files has been reloaded in <white>{TIME}ms<green>.");

    @Execute(name = "reload")
    @CommandDocs(description = "Reloads the configuration files.")
    void reload(@Context CommandSender sender) {
        this.scheduler.runTask(() -> {
            Stopwatch stopwatch = Stopwatch.createStarted();
            this.configurationManager.reload();
            this.translationProvider.setTranslation(configurationProvider.configuration().language());

            Duration duration = stopwatch.elapsed();
            this.multificationManager.create()
                    .viewer(sender)
                    .notice(RELOADED)
                    .placeholder("{TIME}", String.valueOf(duration.toMillis()))
                    .send();
        });
    }
}
