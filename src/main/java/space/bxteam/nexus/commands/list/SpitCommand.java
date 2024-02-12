package space.bxteam.nexus.commands.list;

import space.bxteam.nexus.Nexus;
import space.bxteam.nexus.commands.CommandBase;
import space.bxteam.nexus.utils.SoundUtil;
import space.bxteam.nexus.utils.TextUtils;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LlamaSpit;
import org.bukkit.entity.Player;

public class SpitCommand extends CommandBase implements CommandExecutor {
    public SpitCommand() {
        super("spit", "You can spit like a llama", "/spit", "", "nexus.command.spit");
    }

    @Override
    protected void execute(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("not-player")));
            return;
        }

        Player player = (Player) (sender);
        Location location = player.getEyeLocation();
        World world = player.getWorld();

        if (!player.hasPermission("nexus.command.spit")) {
            player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("no-permission")));
            return;
        }

        location.setY(location.getY() - 0.3);
        SoundUtil.playSound(player, location, "spit");
        LlamaSpit spit = (LlamaSpit) world.spawnEntity(location, org.bukkit.entity.EntityType.LLAMA_SPIT);
        spit.setVelocity(location.getDirection());
    }
}
