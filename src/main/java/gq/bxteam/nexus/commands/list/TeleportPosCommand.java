package gq.bxteam.nexus.commands.list;

import gq.bxteam.nexus.commands.CommandBase;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class TeleportPosCommand extends CommandBase implements CommandExecutor {
    public TeleportPosCommand()
    {
        super("/tppos", "", "/teleportpos xyz", "/teleportpos", "nexus.command.teleportpos");
    }

    @Override
    protected void execute(CommandSender sender, String label, String[] args)
    {

    }
}
