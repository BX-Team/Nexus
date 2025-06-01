package org.bxteam.nexus.core.feature.serverlinks;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.ServerLinks;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bxteam.commons.adventure.util.AdventureUtil;
import org.bxteam.nexus.core.annotations.compatibility.Compatibility;
import org.bxteam.nexus.core.annotations.compatibility.Version;
import org.bxteam.nexus.core.annotations.component.Controller;
import org.bxteam.nexus.core.configuration.plugin.PluginConfiguration;
import org.bxteam.nexus.core.configuration.plugin.PluginConfigurationProvider;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@SuppressWarnings("UnstableApiUsage")
@Controller
@Compatibility(from = @Version(minor = 21, patch = 0))
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class ServerLinksController implements Listener {
    private final PluginConfigurationProvider configurationProvider;
    private final Plugin plugin;
    @Named("colorMiniMessage")
    private final MiniMessage miniMessage;

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (this.configurationProvider.configuration().serverLinks().sendServerLinksOnJoin()) {
            this.sendServerLinks(event.getPlayer());
        }
    }

    private void sendServerLinks(Player player) {
        ServerLinks serverLinks = this.plugin.getServer().getServerLinks().copy();

        processTypeLinks(serverLinks, this.configurationProvider.configuration().serverLinks().typeLinks());
        processCustomLinks(serverLinks, this.configurationProvider.configuration().serverLinks().customLinks());

        player.sendLinks(serverLinks);
    }
    
    @SuppressWarnings("HttpUrlsUsage")
    private URI parseUrl(String url) {
        try {
            if (!url.startsWith("https://") && !url.startsWith("http://")) {
                return null;
            }
            return new URI(url);
        } catch (URISyntaxException exception) {
            return null;
        }
    }
    
    private void processTypeLinks(ServerLinks serverLinks, List<PluginConfiguration.ServerLinks.TypeLink> typeLinks) {
        for (PluginConfiguration.ServerLinks.TypeLink link : typeLinks) {
            URI url = parseUrl(link.url());
            if (url != null) {
                serverLinks.addLink(link.type(), url);
            }
        }
    }
    
    private void processCustomLinks(ServerLinks serverLinks, List<PluginConfiguration.ServerLinks.CustomLink> customLinks) {
        for (PluginConfiguration.ServerLinks.CustomLink link : customLinks) {
            URI url = parseUrl(link.url());
            if (url != null) {
                serverLinks.addLink(AdventureUtil.SECTION_SERIALIZER.serialize(this.miniMessage.deserialize(link.name())), url);
            }
        }
    }
}
