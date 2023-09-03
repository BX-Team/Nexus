package gq.bxteam.nexus;

import gq.bxteam.nexus.utils.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

public final class Nexus extends JavaPlugin {
    public static Nexus instance;

    public static Nexus getInstance() {
        return Nexus.instance;
    }

    @Override
    public void onEnable() {
        Nexus.instance = this;
        new Metrics(Nexus.getInstance(), 19684);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
