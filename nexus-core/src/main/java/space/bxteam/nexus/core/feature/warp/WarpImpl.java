package space.bxteam.nexus.core.feature.warp;

import org.bukkit.Location;
import space.bxteam.nexus.feature.warp.Warp;

public record WarpImpl(String name, Location location) implements Warp { }
