package gq.bxteam.nexus.managers;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlayerManager {
    private final JavaPlugin plugin;
    private final File dataFolder;

    public PlayerManager(@NotNull JavaPlugin plugin) {
        this.plugin = plugin;
        this.dataFolder = new File(plugin.getDataFolder(), "userdata");
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }
    }

    public FileConfiguration getPlayerData(Player player) {
        File playerFile = getPlayerFile(player);
        return YamlConfiguration.loadConfiguration(playerFile);
    }

    public FileConfiguration getPlayerData(OfflinePlayer player) {
        File playerFile = getPlayerFile(player);
        return YamlConfiguration.loadConfiguration(playerFile);
    }

    public void savePlayerData(Player player, FileConfiguration playerData) {
        File playerFile = getPlayerFile(player);
        try {
            playerData.save(playerFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File getPlayerFile(@NotNull Player player) {
        String fileName = player.getUniqueId() + ".yml";
        return new File(dataFolder, fileName);
    }

    private File getPlayerFile(@NotNull OfflinePlayer player) {
        String fileName = player.getUniqueId() + ".yml";
        return new File(dataFolder, fileName);
    }

    public void setPlayerHome(Player player, String homeName, Location location) {
        FileConfiguration playerData = getPlayerData(player);
        ConfigurationSection homesSection = playerData.getConfigurationSection("homes");
        if (homesSection == null) {
            homesSection = playerData.createSection("homes");
        }
        homesSection.set(homeName, location);
        savePlayerData(player, playerData);
    }

    public void deletePlayerHome(Player player, String homeName) {
        FileConfiguration playerData = getPlayerData(player);
        ConfigurationSection homesSection = playerData.getConfigurationSection("homes");
        if (homesSection != null && homesSection.contains(homeName)) {
            homesSection.set(homeName, null);
            savePlayerData(player, playerData);
        }
    }

    public Location getPlayerHome(Player player, String homeName) {
        FileConfiguration playerData = getPlayerData(player);
        ConfigurationSection homesSection = playerData.getConfigurationSection("homes");
        if (homesSection != null && homesSection.contains(homeName)) {
            return homesSection.getLocation(homeName);
        }
        return null;
    }

    public List<String> getAllPlayerHomes(Player player) {
        List<String> homes = new ArrayList<>();
        FileConfiguration playerData = getPlayerData(player);
        ConfigurationSection homesSection = playerData.getConfigurationSection("homes");
        if (homesSection != null) {
            homes.addAll(homesSection.getKeys(false));
        }
        return homes;
    }

    public Location getPlayerHome(OfflinePlayer player, String homeName) {
        FileConfiguration playerData = getPlayerData(player);
        ConfigurationSection homesSection = playerData.getConfigurationSection("homes");
        if (homesSection != null && homesSection.contains(homeName)) {
            return homesSection.getLocation(homeName);
        }
        return null;
    }

    public List<String> getAllPlayerHomes(OfflinePlayer player) {
        List<String> homes = new ArrayList<>();
        FileConfiguration playerData = getPlayerData(player);
        ConfigurationSection homesSection = playerData.getConfigurationSection("homes");
        if (homesSection != null) {
            homes.addAll(homesSection.getKeys(false));
        }
        return homes;
    }

    public void setPlayerPreviousLocation(Player player, Location previousLocation) {
        FileConfiguration playerData = getPlayerData(player);
        playerData.set("previousLocation", previousLocation);
        savePlayerData(player, playerData);
    }

    public Location getPlayerPreviousLocation(Player player) {
        FileConfiguration playerData = getPlayerData(player);
        return playerData.getLocation("previousLocation");
    }

    public void setLastRecipient(Player player, String lastRecipient) {
        FileConfiguration playerData = getPlayerData(player);
        String trimmedName = lastRecipient.substring(lastRecipient.indexOf("=") + 1, lastRecipient.length() - 1);
        playerData.set("lastRecipient", trimmedName);
        savePlayerData(player, playerData);
    }

    public String getLastRecipient(Player player) {
        FileConfiguration playerData = getPlayerData(player);
        return playerData.getString("lastRecipient");
    }

    public void setVanishedState(Player player, boolean vanished) {
        FileConfiguration playerData = getPlayerData(player);
        playerData.set("vanished", vanished);
        savePlayerData(player, playerData);
    }

    public boolean getVanishedState(Player player) {
        FileConfiguration playerData = getPlayerData(player);
        return playerData.getBoolean("vanished");
    }
}
