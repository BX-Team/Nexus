package space.bxteam.nexus.core.utils.meta;

import io.papermc.lib.PaperLib;
import io.papermc.lib.environments.Environment;
import lombok.experimental.UtilityClass;
import org.bukkit.plugin.Plugin;

@UtilityClass
public class PluginVersionMetaFactory {
    public PluginVersionMeta create(Plugin plugin) {
        Environment environment = PaperLib.getEnvironment();
        if (environment.isPaper()) {
            return new PluginVersionMeta(plugin.getPluginMeta().getVersion());
        } else {
            return new PluginVersionMeta(plugin.getDescription().getVersion());
        }
    }
}
