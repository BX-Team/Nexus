package org.bxteam.nexus.core.feature.jail;

import com.google.inject.Inject;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bxteam.nexus.core.configuration.plugin.PluginConfigurationProvider;
import org.bxteam.nexus.core.multification.MultificationManager;
import org.bxteam.nexus.core.registration.annotations.component.Controller;
import org.bxteam.nexus.feature.jail.JailService;

import java.util.Set;
import java.util.UUID;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class JailController implements Listener {
    private final JailService jailService;
    private final MultificationManager multificationManager;
    private final PluginConfigurationProvider configurationProvider;

    private static final Set<PlayerTeleportEvent.TeleportCause> CANCELLED_CAUSES = Set.of(
            PlayerTeleportEvent.TeleportCause.CHORUS_FRUIT,
            PlayerTeleportEvent.TeleportCause.COMMAND,
            PlayerTeleportEvent.TeleportCause.ENDER_PEARL
    );

    @EventHandler
    public void handlePlayerCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        UUID playerId = player.getUniqueId();

        if (this.jailService.isPlayerJailed(playerId) && !player.hasPermission("nexus.jail.bypass")) {
            String command = event.getMessage().split(" ")[0].substring(1);

            if (!this.configurationProvider.configuration().jail().allowedCommands().contains(command)) {
                this.multificationManager.create()
                        .player(playerId)
                        .notice(translation -> translation.jail().jailCannotUseCommand())
                        .send();
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        UUID playerId = event.getPlayer().getUniqueId();

        if (!this.jailService.isPlayerJailed(playerId)) {
            return;
        }

        if (CANCELLED_CAUSES.contains(event.getCause())) {
            event.setCancelled(true);
        }
    }
}
