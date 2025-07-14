package org.bxteam.nexus.core.integration.litecommands.commands;

import com.google.inject.Inject;
import dev.rollczi.litecommands.command.builder.CommandBuilder;
import dev.rollczi.litecommands.cooldown.CooldownContext;
import dev.rollczi.litecommands.editor.Editor;
import dev.rollczi.litecommands.meta.Meta;
import dev.rollczi.litecommands.permission.PermissionSet;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.CommandSender;
import org.bxteam.nexus.core.configuration.commands.CommandsConfigProvider;
import org.bxteam.nexus.core.integration.litecommands.commands.config.CommandConfiguration;
import org.bxteam.nexus.core.annotations.litecommands.LiteEditor;

import java.util.List;
import java.util.Map;
import java.util.function.UnaryOperator;

@LiteEditor
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class CommandsEditor implements Editor<CommandSender> {
    private final CommandsConfigProvider commandsConfigProvider;

    @Override
    public CommandBuilder<CommandSender> edit(CommandBuilder<CommandSender> context) {
        CommandConfiguration commandConfiguration = getCommandConfiguration(context.name());

        if (commandConfiguration == null) {
            return context;
        }

        context = applySubCommandsConfiguration(context, commandConfiguration);
        context = applyMainCommandConfiguration(context, commandConfiguration);
        context = applyCooldownConfiguration(context, commandConfiguration);

        return context;
    }

    private CommandConfiguration getCommandConfiguration(String commandName) {
        return commandsConfigProvider.commandsConfig().commands().get(commandName);
    }

    private CommandBuilder<CommandSender> applySubCommandsConfiguration(
            CommandBuilder<CommandSender> context,
            CommandConfiguration commandConfiguration) {

        for (Map.Entry<String, CommandConfiguration.SubCommand> entry : commandConfiguration.subCommands().entrySet()) {
            context = configureSubCommand(context, entry.getKey(), entry.getValue());
        }

        return context;
    }

    private CommandBuilder<CommandSender> configureSubCommand(
            CommandBuilder<CommandSender> context,
            String defaultSubCommandName,
            CommandConfiguration.SubCommand subCommand) {

        return context.editChild(defaultSubCommandName, editor -> editor
                .name(subCommand.name())
                .aliases(subCommand.aliases())
                .applyMeta(createPermissionEditor(subCommand.permissions()))
                .enabled(subCommand.enabled())
        );
    }

    private CommandBuilder<CommandSender> applyMainCommandConfiguration(
            CommandBuilder<CommandSender> context,
            CommandConfiguration commandConfiguration) {

        return context
                .name(commandConfiguration.name())
                .aliases(commandConfiguration.aliases())
                .applyMeta(createPermissionEditor(commandConfiguration.permissions()));
    }

    private CommandBuilder<CommandSender> applyCooldownConfiguration(
            CommandBuilder<CommandSender> context,
            CommandConfiguration commandConfiguration) {

        CommandConfiguration.Cooldown cooldown = commandConfiguration.cooldown();

        if (cooldown.enabled()) {
            Meta meta = context.meta();
            meta.put(Meta.COOLDOWN, createCooldownContext(commandConfiguration.name(), cooldown));
        }

        return context;
    }

    private CooldownContext createCooldownContext(String commandName, CommandConfiguration.Cooldown cooldown) {
        return new CooldownContext(
                commandName,
                cooldown.duration(),
                cooldown.bypassPermission()
        );
    }

    private static UnaryOperator<Meta> createPermissionEditor(List<String> permissions) {
        return meta -> meta.listEditor(Meta.PERMISSIONS)
                .clear()
                .add(new PermissionSet(permissions))
                .apply();
    }
}
