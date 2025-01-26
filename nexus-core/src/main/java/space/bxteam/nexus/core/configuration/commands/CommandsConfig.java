package space.bxteam.nexus.core.configuration.commands;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import space.bxteam.nexus.core.registration.litecommands.commands.config.CommandConfiguration;

import java.util.Map;

@Getter
public class CommandsConfig extends OkaeriConfig {
    @Comment({
            "This file allows you to configure commands.",
            "You can change command name, aliases, permissions and set cooldown for each command.",
            "You can edit the commands as follows this template:",
            "commands:",
            "  <command_name>:",
            "    name: <command_name>",
            "    aliases:",
            "    - <new_command_aliases>",
            "    permissions:",
            "    - <new_command_permission>",
            "    subCommands:",
            "      <sub_command_name>:",
            "        enabled: <true/false>",
            "        name: <sub_command_name>",
            "        aliases:",
            "        - <new_sub_command_aliases>",
            "        permissions:",
            "        - <new_sub_command_permission>",
            "    cooldown:",
            "      enabled: <true/false>",
            "      duration: <cooldown_duration> (e.g. 5s, 1m, 1h)",
            "      bypassPermission: <cooldown_bypass_permission>",
            "      message:",
            "        chat: <cooldown_chat_message>",
            "        actionBar: <cooldown_action_bar_message>"
    })
    Map<String, CommandConfiguration> commands = Map.of(
            "randomteleport", new CommandConfiguration()
    );
}
