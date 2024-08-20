package space.bxteam.nexus.core.loader;

import org.bukkit.plugin.java.JavaPlugin;

import java.net.URLClassLoader;

public class NexusPlugin extends JavaPlugin {
    private NexusWrapper nexusWrapper;

    @Override
    public void onEnable() {
        URLClassLoader pluginLoader = (URLClassLoader) this.getClassLoader();

        this.nexusWrapper = NexusWrapper.create(pluginLoader);
        this.nexusWrapper.enable(this);
    }

    @Override
    public void onDisable() {
        if (this.nexusWrapper != null) {
            this.nexusWrapper.disable();
        }
    }
}
