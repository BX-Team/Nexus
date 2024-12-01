package space.bxteam.nexus.core.feature.spawn;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import space.bxteam.commons.bukkit.position.Position;
import space.bxteam.commons.bukkit.position.PositionFactory;
import space.bxteam.nexus.core.configuration.ConfigurationManager;
import space.bxteam.nexus.core.configuration.plugin.PluginConfigurationProvider;
import space.bxteam.nexus.core.multification.MultificationManager;
import space.bxteam.nexus.feature.spawn.SpawnService;

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

        player.teleport(PositionFactory.convert(spawn));
    }

    @Override
    public void setSpawnLocation(Location location) {
        this.configurationProvider.configuration().spawn().set("location", PositionFactory.convert(location));
        this.configurationProvider.configuration().save();
    }

    @Override
    public Location getSpawnLocation() {
        Position spawn = this.configurationProvider.configuration().spawn().location();
        return PositionFactory.convert(spawn);
    }
}
