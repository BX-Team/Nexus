package space.bxteam.nexus.core.integration.placeholderapi;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import space.bxteam.nexus.core.utils.meta.PluginVersionMeta;
import space.bxteam.nexus.core.integration.Integration;
import space.bxteam.nexus.core.integration.placeholderapi.resolver.PlaceholderRaw;
import space.bxteam.nexus.core.integration.placeholderapi.resolver.PlaceholderRegistry;
import space.bxteam.nexus.core.integration.placeholderapi.resolver.PlaceholderReplacer;

import java.util.Optional;

@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class PlaceholderAPIIntegration extends PlaceholderExpansion implements Integration, PlaceholderReplacer {
    private final PlaceholderRegistry placeholderRegistry;
    private final Plugin plugin;
    private final PluginManager pluginManager;
    private final PluginVersionMeta versionMeta;

    @Override
    public boolean available() {
        return this.pluginManager.getPlugin("PlaceholderAPI") != null;
    }

    @Override
    public void enable() {
        this.placeholderRegistry.registerPlaceholder(this);
        this.register();
    }

    @Override
    public String apply(String text, Player targetPlayer) {
        return PlaceholderAPI.setPlaceholders(targetPlayer, text);
    }

    @Override
    public @NotNull String getIdentifier() {
        return "nexus";
    }

    @Override
    public @NotNull String getAuthor() {
        return String.join(", ", plugin.getPluginMeta().getAuthors());
    }

    @Override
    public @NotNull String getVersion() {
        return this.versionMeta.version();
    }

    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        Optional<PlaceholderRaw> optional = this.placeholderRegistry.getRawPlaceholder(params);

        return optional.map(placeholderRaw -> placeholderRaw.rawApply(player)).orElse("Unknown placeholder");
    }
}
