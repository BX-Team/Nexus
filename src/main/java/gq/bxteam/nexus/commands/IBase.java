package gq.bxteam.nexus.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import gq.bxteam.nexus.Nexus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public abstract class IBase implements CommandExecutor {
    private final String command;
    private final String permission;
    private final String description;
    private final String usage;
    private final String aliases;

    public static HashMap<String, ArrayList<String>> commands = new HashMap<>();
    protected IBase(String command, String description, String usage, String aliases, String permission) {
        this.command = command;
        this.description = description;
        this.usage = usage;
        this.permission = permission;
        this.aliases = aliases;
        ArrayList<String> commandInfo = new ArrayList<>();
        commandInfo.add(0, usage);
        commandInfo.add(1, description);
        commandInfo.add(2, aliases);
        commands.put(command, commandInfo);
    }
    public String getCommandName() {
        return command;
    }
    public String getPermission() {
        return permission;
    }
    protected abstract void execute(CommandSender sender, String label, String[] args);

    @SuppressWarnings("all")
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!cmd.getName().equalsIgnoreCase(command)) return false;
        if (!Objects.equals(permission, "") && !sender.hasPermission(permission)) {
            sender.sendMessage(Nexus.getInstance().getConfig().getString("messages.error.no-permission"));
            return true;
        }
        execute(sender, label, args);
        return true;
    }

    public static String getName(String name){
        return name;
    }

    public static String getDescription(String name){
        ArrayList<String> localCommandInfo = commands.get(name);
        return localCommandInfo.get(1);
    }

    public static String getUsage(String name){
        ArrayList<String> localCommandInfo = commands.get(name);
        String usage = localCommandInfo.get(0);
        usage = usage.replace("\\n", "\n");
        return usage;
    }

    public static String getAliases(String name){
        ArrayList<String> localCommandInfo = commands.get(name);
        String aliasses = localCommandInfo.get(2);
        aliasses = aliasses.replace("\\n", "\n");
        return aliasses;
    }
}