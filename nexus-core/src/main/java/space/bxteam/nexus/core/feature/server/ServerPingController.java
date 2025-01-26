package space.bxteam.nexus.core.feature.server;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import space.bxteam.nexus.core.configuration.plugin.PluginConfigurationProvider;
import space.bxteam.nexus.core.registration.annotations.component.Controller;
import space.bxteam.nexus.core.utils.RandomElementUtil;

import java.util.Optional;

@Controller
@RequiredArgsConstructor(onConstructor_ = @__(@Inject))
public class ServerPingController implements Listener {
    private final PluginConfigurationProvider configurationProvider;
    @Named("colorMiniMessage")
    private final MiniMessage miniMessage;

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void playerCount(ServerListPingEvent event) {
        event.setMaxPlayers(this.configurationProvider.configuration().server().maxPlayers());
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void serverListMotd(ServerListPingEvent event) {
        if (!this.configurationProvider.configuration().server().useRandomMotd()) return;

        Optional<String> motd = RandomElementUtil.randomElement(this.configurationProvider.configuration().server().motd());
        Component message = this.miniMessage.deserialize(motd.orElse(""));
        event.motd(message);
    }
}
