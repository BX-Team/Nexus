package space.bxteam.nexus.core.feature.home;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import space.bxteam.nexus.core.configuration.PluginConfigurationProvider;
import space.bxteam.nexus.core.database.DatabaseClient;
import space.bxteam.nexus.core.event.EventCaller;
import space.bxteam.nexus.feature.home.Home;
import space.bxteam.nexus.feature.home.HomeService;
import space.bxteam.nexus.feature.home.event.HomeCreateEvent;
import space.bxteam.nexus.feature.home.event.HomeDeleteEvent;

import java.util.*;
import java.util.stream.Stream;

@Singleton
public class HomeServiceImpl implements HomeService {
    private final Map<UUID, Map<String, Home>> userHomes = new HashMap<>();
    private final EventCaller eventCaller;
    private final DatabaseClient client;
    private final PluginConfigurationProvider pluginConfiguration;

    @Inject
    public HomeServiceImpl(EventCaller eventCaller, DatabaseClient client, PluginConfigurationProvider pluginConfiguration) {
        this.eventCaller = eventCaller;
        this.client = client;
        this.pluginConfiguration = pluginConfiguration;

        this.loadHomes();
    }

    @Override
    public void createHome(UUID player, String name, Location location) {
        Map<String, Home> homes = this.userHomes.computeIfAbsent(player, k -> new HashMap<>());

        HomeCreateEvent event = new HomeCreateEvent(player, name, location);
        this.eventCaller.callEvent(event);

        if (event.isCancelled()) {
            return;
        }

        Home newHome = new HomeImpl(player, event.name(), event.location());
        homes.put(event.name(), newHome);
        this.createHome(newHome);
    }

    @Override
    public void deleteHome(UUID player, String name) {
        Map<String, Home> homes = this.userHomes.get(player);

        if (homes == null) {
            return;
        }

        Home home = homes.get(name);

        if (home == null) {
            return;
        }

        HomeDeleteEvent event = new HomeDeleteEvent(player, home);
        this.eventCaller.callEvent(event);

        if (event.isCancelled()) {
            return;
        }

        homes.remove(name);
        this.deleteHome(name);
    }

    @Override
    public boolean hasHome(UUID player, String name) {
        Map<String, Home> homes = this.userHomes.get(player);

        if (homes == null) {
            return false;
        }

        return homes.containsKey(name);
    }

    @Override
    public boolean hasHome(UUID player, Home home) {
        Map<String, Home> homes = this.userHomes.get(player);

        if (homes == null) {
            return false;
        }

        return homes.containsValue(home);
    }

    @Override
    public Collection<Home> getHomes(UUID player) {
        return Collections.unmodifiableCollection(this.userHomes.getOrDefault(player, new HashMap<>()).values());
    }

    @Override
    public Optional<Home> getHome(UUID player, String name) {
        Map<String, Home> homes = this.userHomes.get(player);

        if (homes == null) {
            return Optional.empty();
        }

        return Optional.ofNullable(homes.get(name));
    }

    @Override
    public int getAmountOfHomes(UUID player) {
        Map<String, Home> homes = this.userHomes.get(player);

        if (homes == null) {
            return 0;
        }

        return homes.size();
    }

    @Override
    public int getHomeLimit(Player player) {
        Map<String, Integer> maxHomes = this.pluginConfiguration.configuration().homes().maxHomes();

        return maxHomes.entrySet().stream()
                .flatMap(entry -> {
                    if (player.hasPermission(entry.getKey())) {
                        return Stream.of(entry.getValue());
                    }

                    return Stream.empty();
                })
                .max(Integer::compareTo)
                .orElse(0);
    }

    private void createHome(Home home) {
        String query = "INSERT INTO homes (owner, name, world, x, y, z, yaw, pitch) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        client.newBuilder(query)
                .appends(
                        home.owner(),
                        home.name(),
                        home.location().getWorld().getName(),
                        home.location().getX(),
                        home.location().getY(),
                        home.location().getZ(),
                        home.location().getYaw(),
                        home.location().getPitch()
                )
                .execute();
    }

    private void deleteHome(String name) {
        String query = "DELETE FROM homes WHERE name = ?";
        client.newBuilder(query)
                .appends(name)
                .execute();
    }

    private void loadHomes() {
        String query = "SELECT * FROM homes";
        client.newBuilder(query)
                .queryAll(resultSet -> {
                    try {
                        UUID owner = UUID.fromString(resultSet.getString("owner"));
                        String name = resultSet.getString("name");
                        String world = resultSet.getString("world");
                        double x = resultSet.getDouble("x");
                        double y = resultSet.getDouble("y");
                        double z = resultSet.getDouble("z");
                        float yaw = resultSet.getFloat("yaw");
                        float pitch = resultSet.getFloat("pitch");

                        Location location = new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
                        Home home = new HomeImpl(owner, name, location);

                        this.userHomes.computeIfAbsent(owner, k -> new HashMap<>()).put(name, home);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                });
    }
}
