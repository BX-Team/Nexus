package space.bxteam.nexus.core.registration.litecommands.commands;

import com.eternalcode.multification.notice.Notice;
import com.google.inject.Inject;
import dev.rollczi.litecommands.cooldown.CooldownState;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.message.InvokedMessage;
import dev.rollczi.litecommands.time.DurationParser;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.CommandSender;
import space.bxteam.nexus.core.configuration.commands.CommandsConfigProvider;
import space.bxteam.nexus.core.registration.litecommands.commands.config.CommandConfiguration;
import space.bxteam.nexus.core.multification.MultificationManager;

import java.time.Duration;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class CommandCooldownMessage implements InvokedMessage<CommandSender, Object, CooldownState> {
    private final MultificationManager multificationManager;
    private final CommandsConfigProvider commandsConfigProvider;

    private static final Notice DEFAULT_NOTICE = Notice.builder()
            .chat("<dark_red>You must wait <white>{TIME}</white> before using this command again.")
            .build();

    @Override
    public Object get(Invocation<CommandSender> invocation, CooldownState cooldownState) {
        CommandConfiguration commandConfiguration = this.commandsConfigProvider.commandsConfig().commands().get(cooldownState.getCooldownContext().getKey());

        if (commandConfiguration == null) {
            return this.multificationManager.create()
                    .viewer(invocation.sender())
                    .notice(DEFAULT_NOTICE)
                    .placeholder("{TIME}", DurationParser.TIME_UNITS.format(cooldownState.getRemainingDuration()));
        }

        String formatted = DurationParser.TIME_UNITS.format(Duration.ofSeconds(cooldownState.getRemainingDuration().getSeconds()));

        return this.multificationManager.create()
                .viewer(invocation.sender())
                .notice(notice -> commandConfiguration.cooldown().message())
                .placeholder("{TIME}", formatted);
    }
}
