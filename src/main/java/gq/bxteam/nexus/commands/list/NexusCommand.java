package gq.bxteam.nexus.commands.list;

import gq.bxteam.nexus.Nexus;
import gq.bxteam.nexus.commands.IBase;
import gq.bxteam.nexus.utils.TextUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NexusCommand extends IBase implements CommandExecutor, TabCompleter {
    public NexusCommand() {
        super("nexus", "Default plugin command that reload config and displays version", "/nexus reload/version", "", "nexus.user");
    }

    @Override
    protected void execute(CommandSender sender, String label, String[] args) {
        if (args.length > 0) {
            switch (args[0].toLowerCase()) {
                case "version" -> sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().getConfigString("messages.prefix") + Nexus.getInstance().getConfigString("nexus.version").replace("%v", Nexus.getInstance().getPluginMeta().getVersion())));

                case "reload" -> {
                    Nexus.getInstance().reloadConfig();
                    sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().getConfigString("messages.prefix") + Nexus.getInstance().getConfigString("nexus.reload")));
                }
            }
        }
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (args.length == 1) {
            return Arrays.asList("reload", "version");
        }

        return new ArrayList<>();
    }
}
