package space.bxteam.nexus.core.feature.afk;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import space.bxteam.commons.adventure.AdventureUtil;
import space.bxteam.nexus.core.configuration.plugin.PluginConfigurationProvider;
import space.bxteam.nexus.core.placeholder.PlaceholderRegistry;
import space.bxteam.nexus.core.placeholder.PlaceholderReplacer;
import space.bxteam.nexus.core.scanner.annotations.component.Controller;
import space.bxteam.nexus.core.translation.Translation;
import space.bxteam.nexus.core.translation.TranslationProvider;
import space.bxteam.nexus.event.NexusInitializeEvent;
import space.bxteam.nexus.feature.afk.AfkReason;
import space.bxteam.nexus.feature.afk.AfkService;
import space.bxteam.nexus.feature.afk.event.AfkSwitchEvent;

import java.util.stream.Stream;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class AfkController implements Listener {
    private final AfkService afkService;
    private final PlaceholderRegistry placeholderRegistry;
    private final PluginConfigurationProvider configurationProvider;
    private final TranslationProvider translationProvider;
    @Named("colorMiniMessage")
    private final MiniMessage miniMessage;
    private final Server server;

    @EventHandler
    public void registerPlaceholders(NexusInitializeEvent event) {
        Stream.of(
                PlaceholderReplacer.of("afk", (text, target) -> this.afkService.isAfk(target.getUniqueId()) ? "true" : "false"),
                PlaceholderReplacer.of("afk_formatted", (text, target) -> {
                    Translation translation = this.translationProvider.getCurrentTranslation();
                    return this.afkService.isAfk(target.getUniqueId()) ? translation.afk().afkEnabledPlaceholder() : translation.afk().afkDisabledPlaceholder();
                })
        ).forEach(placeholderRegistry::registerPlaceholder);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    void onMove(PlayerMoveEvent event) {
        this.afkService.markInteraction(event.getPlayer().getUniqueId());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onQuit(PlayerQuitEvent event) {
        this.afkService.unmarkAfk(event.getPlayer().getUniqueId());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onAfkSwitch(AfkSwitchEvent event) {
        if (!event.isAfk() || !this.configurationProvider.configuration().afk().kickOnAfk()) {
            return;
        }

        Player player = this.server.getPlayer(event.getAfkPlayer().playerUuid());

        if (player == null) {
            return;
        }

        if (event.getAfkReason() == AfkReason.INACTIVITY) {
            Translation translation = this.translationProvider.getCurrentTranslation();
            player.kickPlayer(AdventureUtil.SECTION_SERIALIZER.serialize(this.miniMessage.deserialize(translation.afk().afkKickReason())));
        }
    }
}
