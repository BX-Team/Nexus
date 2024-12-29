package space.bxteam.nexus.core.feature.home;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import space.bxteam.nexus.core.configuration.plugin.PluginConfigurationProvider;
import space.bxteam.nexus.core.event.EventCaller;
import space.bxteam.nexus.core.feature.home.database.HomeRepository;
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
    private final HomeRepository homeRepository;
    private final PluginConfigurationProvider pluginConfiguration;

    @Inject
    public HomeServiceImpl(EventCaller eventCaller, HomeRepository homeRepository, PluginConfigurationProvider pluginConfiguration) {
        this.eventCaller = eventCaller;
        this.homeRepository = homeRepository;
        this.pluginConfiguration = pluginConfiguration;

        homeRepository.getHomes().thenAccept(homes -> {
            for (Home home : homes) {
                Map<String, Home> homesByUuid = this.userHomes.computeIfAbsent(home.owner(), k -> new HashMap<>());

                homesByUuid.put(home.name(), home);
            }
        });
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
        this.homeRepository.saveHome(newHome);
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
        this.homeRepository.deleteHome(player, name);
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
}
