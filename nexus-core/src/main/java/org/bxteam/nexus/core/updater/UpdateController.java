package org.bxteam.nexus.core.updater;

import com.google.inject.Inject;
import lombok.RequiredArgsConstructor;
import org.apache.maven.artifact.versioning.ComparableVersion;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bxteam.commons.logger.ExtendedLogger;
import org.bxteam.commons.updater.VersionFetcher;
import org.bxteam.nexus.core.configuration.plugin.PluginConfigurationProvider;
import org.bxteam.nexus.core.multification.MultificationManager;
import org.bxteam.nexus.core.registration.annotations.component.Controller;
import org.bxteam.nexus.event.NexusInitializeEvent;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class UpdateController implements Listener {
    private final PluginConfigurationProvider configurationProvider;
    private final MultificationManager multificationManager;
    private final Plugin plugin;
    private final ExtendedLogger logger;
    private final VersionFetcher versionFetcher;

    private static final List<String> MESSAGE = List.of(
            "<gold>This server using an outdated version of Nexus",
            "<gold>Current version: <yellow>{CURRENT_VERSION}",
            "<gold>Latest version: <yellow>{VERSION}",
            "<gold>Download latest version: <u><click:open_url:{URL}><gold>click here</click></u>"
    );

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (!player.hasPermission("nexus.update.notify")) {
            return;
        }

        if (!this.configurationProvider.configuration().checkForUpdates()) {
            return;
        }

        final var current = new ComparableVersion(this.plugin.getPluginMeta().getVersion());

        CompletableFuture.supplyAsync(versionFetcher::fetchNewestVersion).thenApply(Objects::requireNonNull).whenComplete((newest, error) -> {
            if (error != null || newest.compareTo(current) <= 0) {
                return;
            }

            this.multificationManager.create()
                    .player(player.getUniqueId())
                    .messages(MESSAGE)
                    .placeholder("{CURRENT_VERSION}", current.toString())
                    .placeholder("{VERSION}", newest.toString())
                    .placeholder("{URL}", versionFetcher::getDownloadUrl)
                    .send();
        });
    }

    @EventHandler
    public void onInitialize(NexusInitializeEvent event) {
        if (!this.configurationProvider.configuration().checkForUpdates()) {
            return;
        }

        final var current = new ComparableVersion(this.plugin.getPluginMeta().getVersion());

        CompletableFuture.supplyAsync(versionFetcher::fetchNewestVersion).thenApply(Objects::requireNonNull).whenComplete((newest, error) -> {
            if (error != null || newest.compareTo(current) <= 0) {
                return;
            }

            this.logger.warn("""
                    A new version of Nexus is available!
                    Your version: %s
                    Newest version: %s
                    Download it at: %s
                    """.formatted(current.toString(), newest, versionFetcher.getDownloadUrl()));
        });
    }
}
