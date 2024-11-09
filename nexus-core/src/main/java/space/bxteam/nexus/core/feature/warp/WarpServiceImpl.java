package space.bxteam.nexus.core.feature.warp;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import space.bxteam.nexus.core.database.DatabaseClient;
import space.bxteam.nexus.feature.warp.Warp;
import space.bxteam.nexus.feature.warp.WarpService;

import java.sql.SQLException;
import java.util.*;

@Singleton
public class WarpServiceImpl implements WarpService {
    private final Map<String, Warp> warpMap = new HashMap<>();
    private final DatabaseClient client;

    @Inject
    private WarpServiceImpl(DatabaseClient client) {
        this.client = client;
        loadWarpsFromDatabase();
    }

    @Override
    public void createWarp(String name, Location location) {
        Warp warp = new WarpImpl(name, location);

        this.warpMap.put(name, warp);
        saveWarpToDatabase(warp);
    }

    @Override
    public void removeWarp(String warp) {
        Warp remove = this.warpMap.remove(warp);

        if (remove != null) {
            deleteWarpFromDatabase(warp);
        }
    }

    @Override
    public boolean warpExists(String name) {
        return this.warpMap.containsKey(name);
    }

    @Override
    public Optional<Warp> findWarp(String name) {
        return Optional.ofNullable(this.warpMap.get(name));
    }

    @Override
    public Optional<Warp> getWarp(String name) {
        return Optional.ofNullable(this.warpMap.get(name));
    }

    @Override
    public Collection<String> getWarpNames() {
        return Collections.unmodifiableCollection(this.warpMap.keySet());
    }

    private void saveWarpToDatabase(Warp warp) {
        String query = "INSERT INTO warps (name, world, x, y, z, yaw, pitch) VALUES (?, ?, ?, ?, ?, ?, ?)";
        client.newBuilder(query)
                .appends(
                        warp.getName(),
                        warp.getLocation().getWorld().getName(),
                        warp.getLocation().getX(),
                        warp.getLocation().getY(),
                        warp.getLocation().getZ(),
                        warp.getLocation().getYaw(),
                        warp.getLocation().getPitch()
                )
                .execute();
    }

    private void deleteWarpFromDatabase(String name) {
        String query = "DELETE FROM warps WHERE name = ?";
        client.newBuilder(query)
                .append(name)
                .execute();
    }

    private void loadWarpsFromDatabase() {
        String query = "SELECT * FROM warps";
        client.newBuilder(query)
                .queryAll(resultSet -> {
                    try {
                        String name = resultSet.getString("name");
                        String world = resultSet.getString("world");
                        double x = resultSet.getDouble("x");
                        double y = resultSet.getDouble("y");
                        double z = resultSet.getDouble("z");
                        float yaw = resultSet.getFloat("yaw");
                        float pitch = resultSet.getFloat("pitch");

                        Location location = new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
                        warpMap.put(name, new WarpImpl(name, location));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return null;
                });
    }
}
