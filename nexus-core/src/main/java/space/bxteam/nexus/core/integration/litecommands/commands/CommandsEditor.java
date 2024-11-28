package space.bxteam.nexus.core.integration.litecommands.commands;

import com.google.inject.Inject;
import dev.rollczi.litecommands.command.builder.CommandBuilder;
import dev.rollczi.litecommands.cooldown.CooldownContext;
import dev.rollczi.litecommands.editor.Editor;
import dev.rollczi.litecommands.meta.Meta;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.CommandSender;
import space.bxteam.nexus.core.configuration.commands.CommandsConfigProvider;
import space.bxteam.nexus.core.integration.litecommands.commands.config.CommandConfiguration;
import space.bxteam.nexus.core.scanner.annotations.litecommands.LiteEditor;

import java.util.Map;

@LiteEditor
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class CommandsEditor implements Editor<CommandSender> {
    private final CommandsConfigProvider commandsConfigProvider;

    @Override
    public CommandBuilder<CommandSender> edit(CommandBuilder<CommandSender> context) {
        CommandConfiguration commandConfiguration = this.commandsConfigProvider.commandsConfig().commands().get(context.name());

        if (commandConfiguration == null) {
            return context;
        }

        for (Map.Entry<String, CommandConfiguration.SubCommand> entry : commandConfiguration.subCommands().entrySet()) {
            String defaultSubCommandName = entry.getKey();
            CommandConfiguration.SubCommand subCommand = entry.getValue();

            context = context.editChild(defaultSubCommandName, editor -> editor
                    .name(subCommand.name())
                    .aliases(subCommand.aliases())
                    .applyMeta(meta -> meta.list(Meta.PERMISSIONS, permissions -> permissions.addAll(subCommand.permissions())))
                    .enabled(subCommand.enabled())
            );
        }

        context = context
                .name(commandConfiguration.name())
                .aliases(commandConfiguration.aliases())
                .applyMeta(meta -> meta.list(Meta.PERMISSIONS, permissions -> permissions.addAll(commandConfiguration.permissions())));

        CommandConfiguration.Cooldown cooldown = commandConfiguration.cooldown();
        if (cooldown.enabled()) {
            Meta meta = context.meta();
            meta.put(Meta.COOLDOWN, new CooldownContext(
                    commandConfiguration.name(),
                    cooldown.duration(),
                    cooldown.bypassPermission()
            ));
        }

        return context;
    }
}
