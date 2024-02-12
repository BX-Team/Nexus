package space.bxteam.nexus.commands.list;

import space.bxteam.nexus.Nexus;
import space.bxteam.nexus.commands.CommandBase;
import space.bxteam.nexus.utils.TextUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GiveCommand extends CommandBase implements CommandExecutor
{
    public GiveCommand()
    {
        super("give", "Giving player an item", "/give <item> [count] [player]", "/g", "nexus.command.give");
    }

    @Override
    protected void execute(CommandSender sender, String label, String[] args)
    {

        if (args.length < 1)
        {
            sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("give-usage")));
            return;
        }

        if (sender instanceof Player)
        {
            Player player = (Player) (sender);

            if (!player.hasPermission("nexus.command.give")) {
                player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("no-permission")));
                return;
            }

            if (args.length == 1)
            {
                String itemName = args[0]; // Получите имя предмета из аргументов команды

                try
                {
                    Material material = Material.matchMaterial(itemName);
                    // Попробуйте сопоставить имя предмета с материалом
                    if (material != null)
                    {
                        ItemStack itemStack = new ItemStack(material, 1); // Создайте ItemStack с предметом
                        player.getInventory().addItem(itemStack); // Добавьте предмет в инвентарь игрока
                    }
                    else
                    {
                        player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader
                                .getString("invalid-item")));
                        return;
                    }
                }
                catch (Exception e)
                {
                    player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader
                            .getString("give-usage")));
                    return;
                }
            }
            
            else if (args.length == 2) {

                String itemName = args[0]; // Получите имя предмета из аргументов команды
                int count = Integer.parseInt(args[1]);

                if (count <= 0)
                {
                    player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader
                            .getString("give-usage")));
                    return;
                }

                try
                {
                    Material material = Material.matchMaterial(itemName);
                    // Попробуйте сопоставить имя предмета с материалом
                    if (material != null)
                    {
                        ItemStack itemStack = new ItemStack(material, count); // Создайте ItemStack с предметом
                        player.getInventory().addItem(itemStack); // Добавьте предмет в инвентарь игрока
                    }
                    else
                    {
                        player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader
                                .getString("invalid-item")));
                        return;
                    }
                }
                catch (Exception e)
                {
                    player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader
                            .getString("give-usage")));
                    return;
                }
            }

            else if (args.length == 3)
            {
                String itemName = args[0];
                int count = Integer.parseInt(args[1]);
                Player target = Bukkit.getPlayer(args[2]);
                Material item = Material.matchMaterial(itemName);

                if (!player.hasPermission("nexus.command.give.other"))
                {
                    player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader
                            .getString("no-permission")));
                    return;
                }

                if (target == null)
                {
                    player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader
                            .getString("invalid-player")));
                    return;
                }

                if (count <= 0)
                {
                    player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader
                            .getString("give-usage")));
                    return;
                }

                try
                {
                    Material material = Material.matchMaterial(itemName);
                    // Попробуйте сопоставить имя предмета с материалом
                    if (material != null) {
                        ItemStack itemStack = new ItemStack(material, 1); // Создайте ItemStack с предметом
                        target.getInventory().addItem(itemStack); // Добавьте предмет в инвентарь игрока
                    }
                    else
                    {
                        player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader
                                .getString("invalid-item")));
                        return;
                    }
                }
                catch (Exception e)
                {
                    player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader
                            .getString("give-usage")));
                    return;
                }
            }
        }

        else // Если отправитель - консоль
        {

        }
    }
}
