package space.bxteam.nexus.managers;

import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import space.bxteam.nexus.data.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WarpManager {
    private final JavaPlugin plugin;
    private final Database database;
    private final Map<String, Location> warps;

    public WarpManager(@NotNull JavaPlugin plugin, @NotNull Database database) {
        this.plugin = plugin;
        this.database = database;
        this.warps = new HashMap<>();
        loadWarps();
    }

    private Connection getConnection() throws SQLException {
        return database.dbSource.getConnection();
    }

    /**
     * Save a single warp to the database
     */
    private void saveWarp(String warpName, Location location) {
        try (Connection connection = getConnection();
             PreparedStatement insertStatement = connection.prepareStatement(
                     "INSERT OR REPLACE INTO warps (warp_name, x, y, z, pitch, yaw, world) VALUES (?, ?, ?, ?, ?, ?, ?)")) {
            insertStatement.setString(1, warpName);
            insertStatement.setDouble(2, location.getX());
            insertStatement.setDouble(3, location.getY());
            insertStatement.setDouble(4, location.getZ());
            insertStatement.setFloat(5, location.getPitch());
            insertStatement.setFloat(6, location.getYaw());
            insertStatement.setString(7, location.getWorld().getName());
            insertStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Load all warps from the database
     */
    public void loadWarps() {
        warps.clear();
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM warps");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String warpName = resultSet.getString("warp_name");
                double x = resultSet.getDouble("x");
                double y = resultSet.getDouble("y");
                double z = resultSet.getDouble("z");
                float pitch = resultSet.getFloat("pitch");
                float yaw = resultSet.getFloat("yaw");
                String world = resultSet.getString("world");
                Location location = new Location(plugin.getServer().getWorld(world), x, y, z, yaw, pitch);
                warps.put(warpName, location);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Set a warp
     *
     * @param warpName name of the warp
     * @param location location of the warp
     */
    public void setWarp(String warpName, Location location) {
        warps.put(warpName, location);
        saveWarp(warpName, location);
    }

    /**
     * Get a warp
     *
     * @param warpName name of the warp
     * @return location of the warp
     */
    public Location getWarp(String warpName) {
        return warps.get(warpName);
    }

    /**
     * Remove a warp from the database
     *
     * @param warpName name of the warp
     */
    public void removeWarp(String warpName) {
        warps.remove(warpName);
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM warps WHERE warp_name = ?")) {
            statement.setString(1, warpName);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get all warps
     *
     * @return list of all warps
     */
    public List<String> getAllWarps() {
        return new ArrayList<>(warps.keySet());
    }
}
