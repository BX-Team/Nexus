package space.bxteam.nexus.managers;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import space.bxteam.nexus.data.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerManager {
    private final JavaPlugin plugin;
    private Database database;

    public PlayerManager(@NotNull JavaPlugin plugin, @NotNull Database database) {
        this.plugin = plugin;
        this.database = database;
    }

    private Connection getConnection() throws SQLException {
        return database.dbSource.getConnection();
    }

    /**
     * Method to update player data in selected database
     *
     * @param playerUUID UUID of player
     * @param field field to update data
     * @param value what data will be set in field
     */
    public void updatePlayerData(UUID playerUUID, String field, String value) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE users SET " + field + " = ? WHERE player_uuid = ?")) {
            statement.setString(1, value);
            statement.setString(2, playerUUID.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to get player data from selected database
     *
     * @param playerUUID UUID of player
     * @param field field to read data from
     */
    private String getPlayerData(UUID playerUUID, String field) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT " + field + " FROM users WHERE player_uuid = ?")) {
            statement.setString(1, playerUUID.toString());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString(field);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Method to set player home
     *
     * @param player player to set home to
     * @param homeName name of home
     * @param location location of home
     */
    public void setPlayerHome(Player player, String homeName, Location location) {
        String homes = getAllPlayerHomesAsString(player);
        String homeData = homeName + ":" + location.getX() + ":" + location.getY() + ":" + location.getZ() + ":" + location.getPitch() + ":" + location.getYaw() + ":" + location.getWorld().getName();
        if (homes == null || homes.isEmpty()) {
            homes = homeData;
        } else {
            homes += ";" + homeData;
        }
        updatePlayerData(player.getUniqueId(), "Homes", homes);
    }

    /**
     * Method to delete player home
     *
     * @param player player to delete home from
     * @param homeName name of home
     */
    public void deletePlayerHome(Player player, String homeName) {
        String homes = getAllPlayerHomesAsString(player);
        if (homes == null || homes.isEmpty()) {
            return;
        }
        String[] homeArray = homes.split(";");
        StringBuilder updatedHomes = new StringBuilder();
        for (String home : homeArray) {
            if (!home.startsWith(homeName + ":")) {
                if (updatedHomes.length() > 0) {
                    updatedHomes.append(";");
                }
                updatedHomes.append(home);
            }
        }
        updatePlayerData(player.getUniqueId(), "Homes", updatedHomes.toString());
    }

    /**
     * Method to get player home
     *
     * @param player player to get data from
     * @param homeName name of home
     * @return location of home
     */
    public Location getPlayerHome(OfflinePlayer player, String homeName) {
        String homes = getAllPlayerHomesAsString(player);
        if (homes == null || homes.isEmpty()) {
            return null;
        }
        String[] homeArray = homes.split(";");
        for (String home : homeArray) {
            String[] parts = home.split(":");
            if (parts[0].equals(homeName)) {
                double x = Double.parseDouble(parts[1]);
                double y = Double.parseDouble(parts[2]);
                double z = Double.parseDouble(parts[3]);
                float pitch = Float.parseFloat(parts[4]);
                float yaw = Float.parseFloat(parts[5]);
                String world = parts[6];
                return new Location(plugin.getServer().getWorld(world), x, y, z, yaw, pitch);
            }
        }
        return null;
    }

    /**
     * Method to get all player homes
     *
     * @param player player to get data from
     * @return all homes as list
     */
    public List<String> getAllPlayerHomes(OfflinePlayer player) {
        List<String> homes = new ArrayList<>();
        String homesString = getAllPlayerHomesAsString(player);
        if (homesString == null || homesString.isEmpty()) {
            return homes;
        }
        String[] homeArray = homesString.split(";");
        for (String home : homeArray) {
            homes.add(home.split(":")[0]);
        }
        return homes;
    }

    /**
     * Method to get all player homes
     *
     * @param player player to get data from
     * @return all homes as string
     */
    private String getAllPlayerHomesAsString(OfflinePlayer player) {
        return getPlayerData(player.getUniqueId(), "Homes");
    }

    /**
     * Method to set player previous/last location
     *
     * @param player player to set last location to
     * @param previousLocation location to set as last location
     */
    public void setPlayerPreviousLocation(Player player, Location previousLocation) {
        String location = previousLocation.getX() + ":" + previousLocation.getY() + ":" + previousLocation.getZ() + ":" + previousLocation.getPitch() + ":" + previousLocation.getYaw() + ":" + previousLocation.getWorld().getName();
        updatePlayerData(player.getUniqueId(), "PreviousLocation", location);
    }

    /**
     * Method to get player previous/last location
     *
     * @param player player to get data from
     * @return last location
     */
    public Location getPlayerPreviousLocation(Player player) {
        String location = getPlayerData(player.getUniqueId(), "PreviousLocation");
        if (location == null || location.isEmpty()) {
            return null;
        }
        String[] parts = location.split(":");
        double x = Double.parseDouble(parts[0]);
        double y = Double.parseDouble(parts[1]);
        double z = Double.parseDouble(parts[2]);
        float pitch = Float.parseFloat(parts[3]);
        float yaw = Float.parseFloat(parts[4]);
        String world = parts[5];
        return new Location(plugin.getServer().getWorld(world), x, y, z, yaw, pitch);
    }

    /**
     * Method to set player last recipient
     *
     * @param player player to set last recipient to
     * @param lastRecipient name of last recipient
     */
    public void setLastRecipient(Player player, String lastRecipient) {
        updatePlayerData(player.getUniqueId(), "LastReceipt", lastRecipient);
    }

    /**
     * Method to get player last recipient
     *
     * @param player player to get data from
     * @return last recipient
     */
    public String getLastRecipient(Player player) {
        return getPlayerData(player.getUniqueId(), "LastReceipt");
    }

    /**
     * Method to set player vanished or no
     *
     * @param player player to set vanished state to
     * @param vanished state of vanished
     */
    public void setVanishedState(Player player, boolean vanished) {
        updatePlayerData(player.getUniqueId(), "Vanished", Boolean.toString(vanished));
    }

    /**
     * Method to get player vanished state
     *
     * @param player player to get data from
     * @return vanished state
     */
    public boolean getVanishedState(Player player) {
        return Boolean.parseBoolean(getPlayerData(player.getUniqueId(), "Vanished"));
    }
}
