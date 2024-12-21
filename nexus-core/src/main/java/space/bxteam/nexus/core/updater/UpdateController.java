package space.bxteam.nexus.core.updater;

import com.google.inject.Inject;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import space.bxteam.nexus.core.configuration.plugin.PluginConfigurationProvider;
import space.bxteam.nexus.core.multification.MultificationManager;
import space.bxteam.nexus.core.scanner.annotations.component.Controller;
import space.bxteam.nexus.core.utils.Logger;
import space.bxteam.nexus.event.NexusInitializeEvent;

import java.util.List;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class UpdateController implements Listener {
    private final PluginConfigurationProvider configurationProvider;
    private final MultificationManager multificationManager;
    private final UpdateService updateService;
    private final Plugin plugin;

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

        if (!this.updateService.isUpToDate()) {
            String releaseTag = this.updateService.getReleaseTag();
            String releasePageLink = this.updateService.getReleasePageLink();

            this.multificationManager.create()
                    .player(player.getUniqueId())
                    .messages(MESSAGE)
                    .placeholder("{CURRENT_VERSION}", this.plugin.getPluginMeta().getVersion())
                    .placeholder("{VERSION}", releaseTag.split("v")[1])
                    .placeholder("{URL}", releasePageLink)
                    .send();
        }
    }

    @EventHandler
    public void onInitialize(NexusInitializeEvent event) {
        if (!this.configurationProvider.configuration().checkForUpdates()) {
            return;
        }

        if (!this.updateService.isUpToDate()) {
            String releaseTag = this.updateService.getReleaseTag();
            String releasePageLink = this.updateService.getReleasePageLink();

            Logger.log("&aA new update &e" + releaseTag + " &afor Nexus is available.", Logger.LogLevel.INFO);
            Logger.log("&eDownload it from &e" + releasePageLink, Logger.LogLevel.INFO);
        }
    }
}
