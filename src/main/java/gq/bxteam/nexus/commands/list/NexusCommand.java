package gq.bxteam.nexus.commands.list;

import gq.bxteam.nexus.Nexus;
import gq.bxteam.nexus.commands.CommandBase;
import gq.bxteam.nexus.utils.TextUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
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
        if (args.length > 0) {
            switch (args[0].toLowerCase()) {
                case "version" -> {
                    sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("nexus-version").replace("%v", Nexus.getInstance().getPluginMeta().getVersion())));
                    // TODO: Update checker from modrinth (on release)
                }

                case "reload" -> {
                    if (sender.hasPermission("nexus.admin")) {
                        Nexus.getInstance().reload();
                        sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("nexus-reload")));
                    } else {
                        sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("no-permission")));
                    }
                }
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
