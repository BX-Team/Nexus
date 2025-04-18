package org.bxteam.nexus.core.integration.placeholderapi;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.papermc.paper.plugin.configuration.PluginMeta;
import lombok.RequiredArgsConstructor;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.bxteam.nexus.core.integration.Integration;
import org.bxteam.nexus.core.placeholder.PlaceholderRaw;
import org.bxteam.nexus.core.placeholder.PlaceholderRegistry;

import java.util.Optional;

@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class PlaceholderAPIIntegration extends PlaceholderExpansion implements Integration {
    private final PlaceholderRegistry placeholderRegistry;
    private final PluginMeta pluginMeta;

    @Override
    public void enable() {
        this.register();
    }

    @Override
    public @NotNull String getIdentifier() {
        return "nexus";
    }

    @Override
    public @NotNull String getAuthor() {
        return String.join(", ", pluginMeta.getAuthors());
    }

    @Override
    public @NotNull String getVersion() {
        return this.pluginMeta.getVersion();
    }

    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        Optional<PlaceholderRaw> optional = this.placeholderRegistry.getRawPlaceholder(params);

        return optional.map(placeholderRaw -> placeholderRaw.rawApply(player)).orElse("Unknown placeholder");
    }
}
