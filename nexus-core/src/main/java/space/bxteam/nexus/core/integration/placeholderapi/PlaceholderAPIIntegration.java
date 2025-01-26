package space.bxteam.nexus.core.integration.placeholderapi;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import space.bxteam.nexus.core.integration.Integration;
import space.bxteam.nexus.core.placeholder.PlaceholderRaw;
import space.bxteam.nexus.core.placeholder.PlaceholderRegistry;

import java.util.Optional;

@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class PlaceholderAPIIntegration extends PlaceholderExpansion implements Integration {
    private final PlaceholderRegistry placeholderRegistry;
    private final PluginDescriptionFile pluginDescriptionFile;

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
        return String.join(", ", pluginDescriptionFile.getAuthors());
    }

    @Override
    public @NotNull String getVersion() {
        return this.pluginDescriptionFile.getVersion();
    }

    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        Optional<PlaceholderRaw> optional = this.placeholderRegistry.getRawPlaceholder(params);

        return optional.map(placeholderRaw -> placeholderRaw.rawApply(player)).orElse("Unknown placeholder");
    }
}
