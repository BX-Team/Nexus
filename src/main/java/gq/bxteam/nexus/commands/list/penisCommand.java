package gq.bxteam.nexus.commands.list;

import gq.bxteam.nexus.Nexus;
import gq.bxteam.nexus.commands.CommandBase;
import gq.bxteam.nexus.utils.TextUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class penisCommand extends CommandBase implements CommandExecutor {
    public penisCommand() { super("penis", "", "/penis xyz", "", "nexus.command.penis"); }

    @Override
    protected void execute(CommandSender sender, String label, String[] args)
    {

        sender.sendMessage(TextUtils.applyColor("&atest1"));

        if (sender instanceof Player)
        {

            sender.sendMessage(TextUtils.applyColor("&atest2"));

            Player player = (Player) sender;

            if (!player.hasPermission("nexus.command.teleportpos"))
            {
                player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("no-permission")));
                return;
            }

            sender.sendMessage(TextUtils.applyColor("&atest"));

            if (args.length == 3 || args.length == 4)
            {
                try
                {
                    double x = Double.parseDouble(args[0]);
                    double y = Double.parseDouble(args[1]);
                    double z = Double.parseDouble(args[2]);

                    Location teleportLocation = new Location(player.getWorld(), x, y, z);

                    if (args.length == 4)
                    {
                        Player targetPlayer = Bukkit.getPlayer(args[3]);

                        if (targetPlayer != null)
                        {
                            targetPlayer.teleport(teleportLocation);
                            player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("tppos-complete-2.1").replace("%t1", targetPlayer.getName()).replace("%t2", teleportLocation.toString())));
                            player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("tppos-complete-2.2").replace("%t1", sender.getName()).replace("%t2", teleportLocation.toString())));
                        }

                        else
                        {
                            player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("invalid-player")));
                        }
                    }

                    else
                    {
                        player.teleport(teleportLocation);
                        player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("tppos-complete-1").replace("%t1", teleportLocation.toString())));
                    }
                }

                catch (NumberFormatException e)
                {
                    player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("tppos-usage")));
                }
            }
            else
            {
                player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("tppos-usage")));
            }
        }
    }
}
