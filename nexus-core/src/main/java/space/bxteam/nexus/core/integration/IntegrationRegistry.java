package space.bxteam.nexus.core.integration;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.bukkit.plugin.PluginManager;
import space.bxteam.nexus.core.integration.placeholderapi.PlaceholderAPIIntegration;
import space.bxteam.nexus.core.utils.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class IntegrationRegistry {
    private final Injector injector;
    private final List<Integration> integrations = new ArrayList<>();
    private final PluginManager pluginManager;

    public void init() {
        this.tryEnable(PlaceholderAPIIntegration.class, () -> this.pluginManager.getPlugin("PlaceholderAPI") != null);
    }

    private void tryEnable(Class<? extends Integration> integrationClass, Supplier<Boolean> availablePredicate) {
        if (!availablePredicate.get()) {
            return;
        }

        String integrationName = integrationClass.getSimpleName().replace("Integration", "");
        try {
            Integration integration = this.injector.getInstance(integrationClass);
            if (integration.available()) {
                integration.enable();
                this.integrations.add(integration);
                Logger.log("Enabled integration " + integrationName, Logger.LogLevel.INFO);
            }
        } catch (Exception e) {
            Logger.log("Failed to enable integration " + integrationName, Logger.LogLevel.ERROR);
            e.printStackTrace();
        }
    }
}
