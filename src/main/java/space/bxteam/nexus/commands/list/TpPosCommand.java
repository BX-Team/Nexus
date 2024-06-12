package space.bxteam.nexus.commands.list;

import space.bxteam.nexus.Nexus;
import space.bxteam.nexus.commands.CommandBase;
import space.bxteam.nexus.utils.SoundUtil;
import space.bxteam.nexus.utils.TextUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class TpPosCommand extends CommandBase implements CommandExecutor, TabCompleter {
    public TpPosCommand() {
        super("tpposition", "", "/tpposition [player] <X> <Y> <Z>", "/tppos", "nexus.command.tpposition");
    }

    @Override
    protected void execute(CommandSender sender, String label, String[] args) {
        if (sender instanceof Player player)
        {

            if (!player.hasPermission("nexus.command.tpposition")) {
                player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader
                        .getString("no-permission")));
                return;
            }

            if (args.length == 3 || args.length == 4) {
                try {
                    if (args.length == 4) {
                        Player targetPlayer = Bukkit.getPlayer(args[0]);

                        if (!player.hasPermission("nexus.command.tpposition.other")) {
                            player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader
                                    .getString("no-permission")));
                            return;
                        }

                        if (targetPlayer != null && sender != targetPlayer) {
                            double x = parseCoordinate(args[1], targetPlayer.getLocation().getX());
                            double y = parseCoordinate(args[2], targetPlayer.getLocation().getY());
                            double z = parseCoordinate(args[3], targetPlayer.getLocation().getZ());

                            Location teleportLocation = new Location(player.getWorld(), x, y, z);

                            int intX = (int) Math.round(x);
                            int intY = (int) Math.round(y);
                            int intZ = (int) Math.round(z);
                            String cords = intX + ", " + intY + ", " + intZ;

                            Nexus.getInstance().playerManager.setPlayerPreviousLocation(targetPlayer, targetPlayer.getLocation());
                            targetPlayer.teleport(teleportLocation);
                            SoundUtil.playSound(targetPlayer, "tppos");
                            player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader
                                    .getString("tppos-complete-2.1").replace("%t1", targetPlayer.getName()).replace("%t2", cords)));
                            targetPlayer.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader
                                    .getString("tppos-complete-2.2").replace("%t1", sender.getName()).replace("%t2", cords)));
                        } else if (sender == targetPlayer) {
                            double x = parseCoordinate(args[1], targetPlayer.getLocation().getX());
                            double y = parseCoordinate(args[2], targetPlayer.getLocation().getY());
                            double z = parseCoordinate(args[3], targetPlayer.getLocation().getZ());

                            Location teleportLocation = new Location(player.getWorld(), x, y, z);

                            int intX = (int) Math.round(x);
                            int intY = (int) Math.round(y);
                            int intZ = (int) Math.round(z);
                            String cords = intX + ", " + intY + ", " + intZ;

                            Nexus.getInstance().playerManager.setPlayerPreviousLocation(targetPlayer, targetPlayer.getLocation());
                            targetPlayer.teleport(teleportLocation);
                            SoundUtil.playSound(targetPlayer, "tppos");
                            player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader
                                    .getString("tppos-complete-1").replace("%t1", cords)));
                        } else {
                            player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader
                                    .getString("invalid-player")));
                        }
                    } else {
                        double x = parseCoordinate(args[0], player.getLocation().getX());
                        double y = parseCoordinate(args[1], player.getLocation().getY());
                        double z = parseCoordinate(args[2], player.getLocation().getZ());

                        Location teleportLocation = new Location(player.getWorld(), x, y, z);

                        int intX = (int) Math.round(x);
                        int intY = (int) Math.round(y);
                        int intZ = (int) Math.round(z);
                        String cords = intX + ", " + intY + ", " + intZ;

                        Nexus.getInstance().playerManager.setPlayerPreviousLocation(player, player.getLocation());
                        player.teleport(teleportLocation);
                        SoundUtil.playSound(player, "tppos");
                        player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader
                                .getString("tppos-complete-1").replace("%t1", cords)));
                    }
                } catch (NumberFormatException e) {
                    player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader
                            .getString("tppos-usage")));
                }
            } else {
                player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader
                        .getString("tppos-usage")));
            }
        } else if (args.length == 4) {

            if (!sender.hasPermission("nexus.command.tpposition.other")) {
                sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader
                        .getString("no-permission")));
                return;
            }

            Player target = Bukkit.getPlayer(args[0]);

            try {
                if (target != null) {
                    double x = parseCoordinate(args[1], target.getLocation().getX());
                    double y = parseCoordinate(args[2], target.getLocation().getY());
                    double z = parseCoordinate(args[3], target.getLocation().getZ());

                    Location teleportLocation = new Location(target.getWorld(), x, y, z);

                    int intX = (int) Math.round(x);
                    int intY = (int) Math.round(y);
                    int intZ = (int) Math.round(z);
                    String cords = intX + ", " + intY + ", " + intZ;

                    Nexus.getInstance().playerManager.setPlayerPreviousLocation(target, target.getLocation());
                    target.teleport(teleportLocation);
                    SoundUtil.playSound(target, "tppos");
                    sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader
                            .getString("tppos-complete-2.1").replace("%t1", target.getName()).replace("%t2", cords)));
                    target.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader
                            .getString("tppos-complete-2.3").replace("%t2", cords)));
                } else {
                    sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader
                            .getString("invalid-player")));
                }

            } catch (NumberFormatException e) {
                sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader
                        .getString("tppos-usage")));
            }
        } else {
            sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader
                    .getString("tppos-usage")));
        }
    }

    private double parseCoordinate(String arg, double defaultValue) throws NumberFormatException {
        if (arg.equals("~")) {
            return defaultValue;
        } else if (arg.startsWith("~")) {
            double relative = Double.parseDouble(arg.substring(1));
            return defaultValue + relative;
        } else {
            return Double.parseDouble(arg);
        }
    }


    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        ArrayList<String> completions = new ArrayList<>();

        if (args.length == 1) {
            completions.add("~");
            completions.add("~ ~");
            completions.add("~ ~ ~");
        } else if (args.length == 2) {
            completions.add("~");
            completions.add("~ ~");
        } else if (args.length == 3) {
            completions.add("~");
        } else if (args.length == 4) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                completions.add(player.getName());
            }
        }

        return completions;
    }
}
