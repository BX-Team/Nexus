package gq.bxteam.nexus.managers;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WarpManager {
    private JavaPlugin plugin;
    private FileConfiguration warpsConfig;
    private File warpsFile;

    private Map<String, Location> warps;

    public WarpManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.warps = new HashMap<>();
        this.warpsFile = new File(plugin.getDataFolder(), "warps.yml");
        this.warpsConfig = YamlConfiguration.loadConfiguration(warpsFile);

        if (!warpsFile.exists()) {
            plugin.saveResource("warps.yml", false);
        }

        loadWarps();
    }

    public void saveWarps() {
        for (String warpName : warps.keySet()) {
            warpsConfig.set("warps." + warpName, warps.get(warpName));
        }

        try {
            warpsConfig.save(warpsFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadWarps() {
        if (warpsConfig.contains("warps")) {
            for (String warpName : warpsConfig.getConfigurationSection("warps").getKeys(false)) {
                Location location = (Location) warpsConfig.get("warps." + warpName);
                warps.put(warpName, location);
            }
        }
    }

    public void setWarp(String warpName, Location location) {
        warps.put(warpName, location);
    }

    public Location getWarp(String warpName) {
        return warps.get(warpName);
    }

    public void removeWarp(String warpName) {
        warps.remove(warpName);
        warpsConfig.set("warps." + warpName, null);
        saveWarps();
    }

    public List<String> getAllWarps() {
        return new ArrayList<>(warps.keySet());
    }
}
