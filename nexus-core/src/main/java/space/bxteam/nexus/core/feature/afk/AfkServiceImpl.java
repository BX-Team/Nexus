package space.bxteam.nexus.core.feature.afk;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import space.bxteam.nexus.core.configuration.plugin.PluginConfigurationProvider;
import space.bxteam.nexus.core.event.EventCaller;
import space.bxteam.nexus.core.multification.MultificationManager;
import space.bxteam.nexus.feature.afk.AfkPlayer;
import space.bxteam.nexus.feature.afk.AfkReason;
import space.bxteam.nexus.feature.afk.AfkService;
import space.bxteam.nexus.feature.afk.event.AfkSwitchEvent;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class AfkServiceImpl implements AfkService {
    private final PluginConfigurationProvider configurationProvider;
    private final MultificationManager multificationManager;
    private final EventCaller eventCaller;

    private final Map<UUID, AfkPlayer> afkPlayers = new HashMap<>();
    private final Map<UUID, Integer> interactionsCount = new HashMap<>();
    private final Map<UUID, Instant> lastInteraction = new HashMap<>();

    @Override
    public void switchAfk(UUID playerUuid, AfkReason reason) {
        if (this.isAfk(playerUuid)) {
            this.unmarkAfk(playerUuid);
        } else {
            this.markAfk(playerUuid, reason);
        }
    }

    @Override
    public void markAfk(UUID playerUuid, AfkReason reason) {
        AfkPlayer afkPlayer = new AfkPlayer(playerUuid, reason, Instant.now());
        AfkSwitchEvent event = this.eventCaller.callEvent(new AfkSwitchEvent(afkPlayer, reason, true));

        if (event.isCancelled()) {
            return;
        }

        this.afkPlayers.put(playerUuid, afkPlayer);
        this.sendFeedback(playerUuid, true);
    }

    @Override
    public void unmarkAfk(UUID playerUuid) {
        AfkPlayer afkPlayer = this.afkPlayers.get(playerUuid);

        if (afkPlayer == null) {
            return;
        }

        AfkSwitchEvent event = this.eventCaller.callEvent(new AfkSwitchEvent(afkPlayer, afkPlayer.reason(), false));

        if (event.isCancelled()) {
            return;
        }

        this.afkPlayers.remove(playerUuid);
        this.interactionsCount.remove(playerUuid);
        this.lastInteraction.remove(playerUuid);
        this.sendFeedback(playerUuid, false);
    }

    @Override
    public void markInteraction(UUID playerUuid) {
        this.lastInteraction.put(playerUuid, Instant.now());

        if (this.isAfk(playerUuid)) {
            int count = this.interactionsCount.merge(playerUuid, 1, Integer::sum);

            if (count >= this.configurationProvider.configuration().afk().interactionsRequired()) {
                this.unmarkAfk(playerUuid);
            }
        }
    }

    @Override
    public boolean isAfk(UUID playerUuid) {
        return this.afkPlayers.containsKey(playerUuid);
    }

    @Override
    public boolean isInactive(UUID playerUuid) {
        Instant lastInteraction = this.lastInteraction.get(playerUuid);

        return lastInteraction != null && Duration.between(lastInteraction, Instant.now()).compareTo(this.configurationProvider.configuration().afk().autoMarkTime()) >= 0;
    }

    private void sendFeedback(UUID playerUuid, boolean isAfk) {
        this.multificationManager.create()
                .player(playerUuid)
                .notice(translation -> isAfk ? translation.afk().afkOn() : translation.afk().afkOff())
                .send();
    }
}
