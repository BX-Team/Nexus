package space.bxteam.nexus.core.feature.server;

import com.google.inject.Inject;
import io.papermc.lib.PaperLib;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import space.bxteam.commons.bukkit.position.Position;
import space.bxteam.commons.bukkit.position.PositionFactory;
import space.bxteam.nexus.core.configuration.plugin.PluginConfigurationProvider;
import space.bxteam.nexus.core.registration.annotations.component.Controller;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class PlayerJoinController implements Listener {
    private final PluginConfigurationProvider configurationProvider;

    @EventHandler
    public void onFirstJoin(PlayerJoinEvent event) {
        if (!this.configurationProvider.configuration().spawn().teleportOnFirstJoin()) {
            return;
        }

        Player player = event.getPlayer();

        if (player.hasPlayedBefore()) {
            return;
        }

        this.teleportToSpawn(player);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (!this.configurationProvider.configuration().spawn().teleportOnJoin()) {
            return;
        }

        Player player = event.getPlayer();
        this.teleportToSpawn(player);
    }

    private void teleportToSpawn(Player player) {
        Position spawn = this.configurationProvider.configuration().spawn().location();

        if (spawn == null || spawn.isNoneWorld()) {
            return;
        }

        Location spawnLocation = PositionFactory.convert(spawn);
        PaperLib.teleportAsync(player, spawnLocation);
    }
}
