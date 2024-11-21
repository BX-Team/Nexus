package space.bxteam.nexus.core.feature.fullserverbypass;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import space.bxteam.nexus.core.configuration.PluginConfigurationProvider;
import space.bxteam.nexus.core.scanner.annotations.component.Controller;
import space.bxteam.nexus.core.translation.TranslationManager;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class FullServerBypassController implements Listener {
    @Named("colorMiniMessage")
    private final MiniMessage miniMessage;
    private final TranslationManager translationManager;
    private final PluginConfigurationProvider configurationProvider;

    @EventHandler
    public void onLogin(PlayerLoginEvent event) {
        if (event.getResult() == PlayerLoginEvent.Result.KICK_FULL) {
            Player player = event.getPlayer();

            if (player.hasPermission("nexus.fullserverbypass")) {
                event.allow();
                return;
            }

            String kickMessage = String.join("\n", this.translationManager.getMessages(configurationProvider.configuration().language()).player().fullServerSlots());
            Component message = this.miniMessage.deserialize(kickMessage);
            event.disallow(PlayerLoginEvent.Result.KICK_FULL, message);
        }
    }
}
