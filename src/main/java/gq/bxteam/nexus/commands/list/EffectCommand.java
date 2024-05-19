package gq.bxteam.nexus.commands.list;

import gq.bxteam.nexus.Nexus;
import gq.bxteam.nexus.commands.CommandBase;
import gq.bxteam.nexus.utils.TextUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.*;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class EffectCommand extends CommandBase implements CommandExecutor
{
    public EffectCommand()
    {
        super("give", "Giving player an item", "/give <item> [count] [player]", "/g", "nexus.command.give");
    }

    @Override
    protected void execute(CommandSender sender, String label, String[] args)
    {
        if (args.length < 1)
        {
            sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader
                    .getString("effect-usage")));
            return;
        }

        if (sender instanceof Player)
        {
            Player player = (Player) (sender);

            if (args.length == 1)
            {
                Player target = (Player) (sender);

            }
        }
    }
}
