package org.bxteam.nexus.core.integration;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bxteam.commons.logger.ExtendedLogger;
import org.bxteam.nexus.core.integration.placeholderapi.PlaceholderAPIIntegration;
import org.bxteam.nexus.core.integration.placeholderapi.PlaceholderAPIReplacer;
import org.bxteam.nexus.core.placeholder.PlaceholderRegistry;

@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class IntegrationRegistry {
    private final PluginManager pluginManager;
    private final PluginDescriptionFile pluginDescriptionFile;
    private final PlaceholderRegistry placeholderRegistry;
    private final ExtendedLogger logger;

    public void init() {
        this.tryEnable("PlaceholderAPI", () -> {
            this.placeholderRegistry.registerPlaceholder(new PlaceholderAPIReplacer());
            new PlaceholderAPIIntegration(this.placeholderRegistry, this.pluginDescriptionFile).enable();
        });
    }

    private void tryEnable(String pluginName, Integration integration) {
        if (pluginManager.isPluginEnabled(pluginName)) {
            try {
                integration.enable();
                logger.info("Enabled integration " + pluginName);
            } catch (Exception e) {
                logger.error("Failed to enable integration " + pluginName);
                e.printStackTrace();
            }
        }
    }
}
