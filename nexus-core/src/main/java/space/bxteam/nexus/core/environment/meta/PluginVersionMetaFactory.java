package space.bxteam.nexus.core.environment.meta;

import lombok.experimental.UtilityClass;
import org.bukkit.plugin.Plugin;
import space.bxteam.nexus.core.environment.Environment;

@UtilityClass
public class PluginVersionMetaFactory {
    public PluginVersionMeta create(Plugin plugin) {
        if (Environment.ENVIRONMENT.isPaper()) {
            return new PluginVersionMeta(plugin.getPluginMeta().getVersion());
        } else {
            return new PluginVersionMeta(plugin.getDescription().getVersion());
        }
    }
}
