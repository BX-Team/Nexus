package space.bxteam.nexus.core.feature.teleport;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public record TeleportContext(Player player, Location location) { }
