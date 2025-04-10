package org.bxteam.nexus.core.feature.afk;

import com.google.inject.Inject;
import lombok.RequiredArgsConstructor;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bxteam.nexus.core.configuration.plugin.PluginConfigurationProvider;
import org.bxteam.nexus.core.annotations.component.Task;
import org.bxteam.nexus.feature.afk.AfkReason;
import org.bxteam.nexus.feature.afk.AfkService;

import java.util.UUID;

@Task(delay = 1200L, period = 1200L)
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class AfkTask implements Runnable {
    private final AfkService afkService;
    private final PluginConfigurationProvider configurationProvider;
    private final Server server;

    @Override
    public void run() {
        this.markInactivePlayers();
    }

    private void markInactivePlayers() {
        if (!this.configurationProvider.configuration().afk().autoMark()) {
            return;
        }

        for (Player player : this.server.getOnlinePlayers()) {
            UUID playerUuid = player.getUniqueId();

            if (this.afkService.isAfk(playerUuid)) {
                continue;
            }

            if (this.afkService.isInactive(playerUuid)) {
                this.afkService.markAfk(playerUuid, AfkReason.INACTIVITY);
            }
        }
    }
}
