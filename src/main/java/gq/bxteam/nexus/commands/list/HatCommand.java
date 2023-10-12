package gq.bxteam.nexus.commands.list;

import gq.bxteam.nexus.Nexus;
import gq.bxteam.nexus.commands.CommandBase;
import gq.bxteam.nexus.utils.TextUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.Material;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class HatCommand extends CommandBase implements CommandExecutor
{
    public HatCommand() {
        super("hat", "Puts an item from your hand on your head", "/hat", "", "nexus.command.hat");
    }

    @Override
    protected void execute(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("not-player")));
            return;
        }

        Player player = (Player) (sender);

        if (!player.hasPermission("nexus.command.hat")) {
            player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("no-permission")));
            return;
        }

        PlayerInventory playerInventory = player.getInventory();
        ItemStack hand = playerInventory.getItemInMainHand();
        ItemStack head = playerInventory.getHelmet();

        if (hand.getType() == Material.AIR) {
            player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("hat-error")));
            return;
        }

        if (head != null && head.getType() != Material.AIR) {
            playerInventory.addItem(head);
        }

        playerInventory.setHelmet(hand);
        playerInventory.setItemInMainHand(null);
        player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("hat-success")));
    }
}
