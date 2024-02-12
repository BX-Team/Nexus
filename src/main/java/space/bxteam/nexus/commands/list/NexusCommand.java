package space.bxteam.nexus.commands.list;

import space.bxteam.nexus.Nexus;
import space.bxteam.nexus.commands.CommandBase;
import space.bxteam.nexus.utils.TextUtils;
import org.bukkit.command.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NexusCommand extends CommandBase implements CommandExecutor, TabCompleter {
    public NexusCommand()
    {
        super("nexus", "Default plugin command that reload config and displays version", "/nexus reload/version", "", "nexus.command.nexus");
    }

    @Override
    protected void execute(CommandSender sender, String label, String[] args) {
        if (args.length == 0) {
            List<String> helpMessage = Nexus.getInstance().localeReader.getStringList("nexus-help");
            for (String message : helpMessage) {
                sender.sendMessage(TextUtils.applyColor(message));
            }
        } else if (args[0].equalsIgnoreCase("version")) {
            sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("nexus-version").replace("%v", Nexus.getInstance().getPluginMeta().getVersion())));
            // TODO: Update checker from modrinth (on release)
        } else if (args[0].equalsIgnoreCase("reload")) {
            if (sender.hasPermission("nexus.admin")) {
                Nexus.getInstance().reload();
                sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("nexus-reload")));
            } else {
                sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("no-permission")));
            }
        }
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (args.length == 1) {
            if (sender.hasPermission("nexus.admin")) {
                return Arrays.asList("reload", "version");
            } else {
                return List.of("version");
            }
        }

        return new ArrayList<>();
    }
}
