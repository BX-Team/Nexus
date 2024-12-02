package space.bxteam.nexus.core.feature.jail;

import com.google.inject.Inject;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;
import space.bxteam.commons.bukkit.position.Position;
import space.bxteam.commons.bukkit.position.PositionFactory;
import space.bxteam.nexus.core.configuration.plugin.PluginConfigurationProvider;
import space.bxteam.nexus.core.database.DatabaseClient;
import space.bxteam.nexus.core.event.EventCaller;
import space.bxteam.nexus.feature.jail.JailPlayer;
import space.bxteam.nexus.feature.jail.JailService;
import space.bxteam.nexus.feature.jail.event.PlayerJailedEvent;
import space.bxteam.nexus.feature.jail.event.PlayerReleaseEvent;
import space.bxteam.nexus.feature.spawn.SpawnService;
import space.bxteam.nexus.feature.teleport.TeleportService;

import java.time.Duration;
import java.time.Instant;
import java.util.*;

public class JailServiceImpl implements JailService {
    private final Map<UUID, JailPlayer> jailedPlayers = new HashMap<>();

    private final PluginConfigurationProvider configurationProvider;
    private final DatabaseClient client;
    private final TeleportService teleportService;
    private final SpawnService spawnService;
    private final EventCaller eventCaller;
    private final Server server;

    @Inject
    public JailServiceImpl(PluginConfigurationProvider configurationProvider, DatabaseClient client, TeleportService teleportService, SpawnService spawnService, EventCaller eventCaller, Server server) {
        this.configurationProvider = configurationProvider;
        this.client = client;
        this.teleportService = teleportService;
        this.spawnService = spawnService;
        this.eventCaller = eventCaller;
        this.server = server;

        this.loadFromDatabase();
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

        this.saveJailPlayerDatabase(jailPlayer);
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

        this.deleteJailPlayerDatabase(player.getUniqueId());
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
        this.deleteAllJailPlayersDatabase();
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

    private void saveJailPlayerDatabase(JailPlayer jailPlayer) {
        String query = "INSERT INTO jailed_players (id, jailedAt, duration, jailedBy) VALUES (?, ?, ?, ?)";
        this.client.newBuilder(query)
                .appends(
                        jailPlayer.getPlayerUniqueId(),
                        jailPlayer.getJailedAt(),
                        jailPlayer.getPrisonTime(),
                        jailPlayer.getJailedBy()
                )
                .execute();
    }

    private void deleteJailPlayerDatabase(UUID player) {
        String query = "DELETE FROM jailed_players WHERE id = ?";
        this.client.newBuilder(query)
                .appends(player)
                .execute();
    }

    private void deleteAllJailPlayersDatabase() {
        String query = "DELETE FROM jailed_players";
        this.client.newBuilder(query)
                .execute();
    }

    private void loadFromDatabase() {
        String query = "SELECT * FROM jailed_players";
        this.client.newBuilder(query)
                .queryAll(resultSet -> {
                    while (resultSet.next()) {
                        UUID player = UUID.fromString(resultSet.getString("id"));
                        Instant detainedAt = Instant.parse(resultSet.getString("jailedAt"));
                        Duration prisonTime = Duration.parse(resultSet.getString("duration"));
                        String detainedBy = resultSet.getString("jailedBy");

                        JailPlayer jailPlayer = new JailPlayer(player, detainedAt, prisonTime, detainedBy);
                        this.jailedPlayers.put(player, jailPlayer);
                    }

                    return null;
                });
    }
}
