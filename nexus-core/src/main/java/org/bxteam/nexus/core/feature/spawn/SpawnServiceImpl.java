package org.bxteam.nexus.core.feature.spawn;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.papermc.lib.PaperLib;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bxteam.commons.bukkit.position.Position;
import org.bxteam.commons.bukkit.position.PositionFactory;
import org.bxteam.nexus.core.configuration.ConfigurationManager;
import org.bxteam.nexus.core.configuration.plugin.PluginConfigurationProvider;
import org.bxteam.nexus.feature.spawn.SpawnService;

@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class SpawnServiceImpl implements SpawnService {
    private final PluginConfigurationProvider configurationProvider;
    private final ConfigurationManager configurationManager;

    @Override
    public void teleportToSpawn(Player player) {
        Position spawn = this.configurationProvider.configuration().spawn().location();

        if (spawn.isNoneWorld()) {
            return;
        }

        PaperLib.teleportAsync(player, PositionFactory.convert(spawn));
    }

    @Override
    public void setSpawnLocation(Location location) {
        this.configurationProvider.configuration().spawn().set("location", PositionFactory.convert(location));
        this.configurationManager.save(configurationProvider.configuration());
    }

    @Override
    public Location getSpawnLocation() {
        Position spawn = this.configurationProvider.configuration().spawn().location();
        return PositionFactory.convert(spawn);
    }
}
