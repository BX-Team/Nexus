package org.bxteam.nexus.core.feature.jail;

import com.google.inject.Inject;
import lombok.RequiredArgsConstructor;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bxteam.nexus.core.multification.MultificationManager;
import org.bxteam.nexus.core.annotations.component.Task;
import org.bxteam.nexus.core.utils.DurationUtil;
import org.bxteam.nexus.feature.jail.JailPlayer;
import org.bxteam.nexus.feature.jail.JailService;

@Task(delay = 1L, period = 1L)
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class JailTask implements Runnable {
    private final JailService jailService;
    private final Server server;
    private final MultificationManager multificationManager;

    @Override
    public void run() {
        for (JailPlayer jailPlayer : this.jailService.getJailedPlayers()) {
            Player player = this.server.getPlayer(jailPlayer.playerUuid());

            if (player == null) {
                continue;
            }

            this.multificationManager.create()
                    .player(jailPlayer.playerUuid())
                    .notice(translation -> translation.jail().jailCountdown())
                    .placeholder("{TIME}", DurationUtil.format(jailPlayer.getRemainingTime(), true))
                    .send();

            if (jailPlayer.isPrisonExpired()) {
                this.multificationManager.create()
                        .player(jailPlayer.playerUuid())
                        .notice(translation -> translation.jail().jailReleasePrivate())
                        .send();

                this.jailService.releasePlayer(player);
            }
        }
    }
}
