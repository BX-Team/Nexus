package org.bxteam.nexus.core.feature.jail;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;
import org.bxteam.commons.bukkit.position.Position;
import org.bxteam.commons.bukkit.position.PositionFactory;
import org.bxteam.nexus.core.configuration.plugin.PluginConfigurationProvider;
import org.bxteam.nexus.core.event.EventCaller;
import org.bxteam.nexus.core.feature.jail.database.JailRepository;
import org.bxteam.nexus.feature.jail.JailPlayer;
import org.bxteam.nexus.feature.jail.JailService;
import org.bxteam.nexus.feature.jail.event.PlayerJailedEvent;
import org.bxteam.nexus.feature.jail.event.PlayerReleaseEvent;
import org.bxteam.nexus.feature.spawn.SpawnService;
import org.bxteam.nexus.feature.teleport.TeleportService;

import java.time.Duration;
import java.time.Instant;
import java.util.*;

@Singleton
public class JailServiceImpl implements JailService {
    private final Map<UUID, JailPlayer> jailedPlayers = new HashMap<>();

    private final PluginConfigurationProvider configurationProvider;
    private final JailRepository jailRepository;
    private final TeleportService teleportService;
    private final SpawnService spawnService;
    private final EventCaller eventCaller;
    private final Server server;

    @Inject
    public JailServiceImpl(PluginConfigurationProvider configurationProvider, JailRepository jailRepository, TeleportService teleportService, SpawnService spawnService, EventCaller eventCaller, Server server) {
        this.configurationProvider = configurationProvider;
        this.jailRepository = jailRepository;
        this.teleportService = teleportService;
        this.spawnService = spawnService;
        this.eventCaller = eventCaller;
        this.server = server;

        jailRepository.getPrisoners().thenAccept(prisoners -> {
            for (JailPlayer jailPlayer : prisoners) {
                this.jailedPlayers.put(jailPlayer.playerUuid(), jailPlayer);
            }
        });
    }

    @Override
    public void jailPlayer(String jailName, Player player, CommandSender detainedBy, @Nullable Duration duration) {
        if (duration == null) {
            duration = this.configurationProvider.configuration().jail().jailTime();
        }

        Position jailPosition = this.configurationProvider.configuration().jail().jailArea().get(jailName);
        if (jailPosition == null) {
            return;
        }

        Location jailLocation = PositionFactory.convert(jailPosition);

        PlayerJailedEvent event = new PlayerJailedEvent(player, detainedBy);
        this.eventCaller.callEvent(event);

        if (event.isCancelled()) {
            return;
        }

        JailPlayer jailPlayer = new JailPlayer(player.getUniqueId(), Instant.now(), duration, detainedBy.getName());

        this.jailRepository.savePrisoner(jailPlayer);
        this.jailedPlayers.put(player.getUniqueId(), jailPlayer);
        this.teleportService.teleport(player, jailLocation);
    }

    @Override
    public void releasePlayer(Player player) {
        PlayerReleaseEvent event = new PlayerReleaseEvent(player.getUniqueId());
        this.eventCaller.callEvent(event);

        if (event.isCancelled()) {
            return;
        }

        this.jailRepository.deletePrisoner(player.getUniqueId());
        this.jailedPlayers.remove(player.getUniqueId());
        this.spawnService.teleportToSpawn(player);
    }

    @Override
    public void releaseAllPlayers() {
        this.jailedPlayers.forEach((uuid, prisoner) -> {
            Player player = this.server.getPlayer(uuid);
            PlayerReleaseEvent event = new PlayerReleaseEvent(uuid);
            this.eventCaller.callEvent(event);

            if (event.isCancelled()) {
                return;
            }

            if (player != null) {
                this.spawnService.teleportToSpawn(player);
            }
        });

        this.jailedPlayers.clear();
        this.jailRepository.deleteAllPrisoners();
    }

    @Override
    public boolean isPlayerJailed(UUID player) {
        JailPlayer jailedPlayer = this.jailedPlayers.get(player);

        return jailedPlayer != null && !jailedPlayer.isPrisonExpired();
    }

    @Override
    public Collection<JailPlayer> getJailedPlayers() {
        return Collections.unmodifiableCollection(this.jailedPlayers.values());
    }

    @Override
    public void createJailLocation(String name, Location jailLocation) {
        this.configurationProvider.configuration().jail().jailArea().put(name, PositionFactory.convert(jailLocation));
        this.configurationProvider.configuration().save();
    }

    @Override
    public void removeJailArea(String name) {
        this.configurationProvider.configuration().jail().jailArea().remove(name);
        this.configurationProvider.configuration().save();
    }

    @Override
    public boolean jailExists(String name) {
        return this.configurationProvider.configuration().jail().jailArea().containsKey(name);
    }

    @Override
    public Collection<String> getAllJailNames() {
        return this.configurationProvider.configuration().jail().jailArea().keySet();
    }
}
