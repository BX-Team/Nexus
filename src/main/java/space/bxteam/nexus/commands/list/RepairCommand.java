package space.bxteam.nexus.commands.list;

import space.bxteam.nexus.Nexus;
import space.bxteam.nexus.commands.CommandBase;
import space.bxteam.nexus.utils.TextUtils;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class RepairCommand extends CommandBase implements CommandExecutor {
    public RepairCommand() {
        super("repair", "Repair item in your main hand", "/repair", "", "nexus.command.repair");
    }

    @Override
    protected void execute(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("not-player")));
            return;
        }

        Player player = (Player) (sender);
        ItemStack item = player.getInventory().getItemInMainHand();

        if (!player.hasPermission("nexus.command.repair")) {
            player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("no-permission")));
            return;
        }

        if (!(item.getType().getMaxDurability() > 0)) {
            player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("repair-error")));
            return;
        }

        item.setDurability((short) 0);
        player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("repair-success")));
    }
}
