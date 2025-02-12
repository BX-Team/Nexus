package org.bxteam.nexus.core.feature.home;

import org.bukkit.Location;
import org.bxteam.nexus.feature.home.Home;

import java.util.UUID;

public record HomeImpl(UUID owner, String name, Location location) implements Home { }
