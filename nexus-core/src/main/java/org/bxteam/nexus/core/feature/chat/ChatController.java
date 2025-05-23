package org.bxteam.nexus.core.feature.chat;

import com.google.inject.Inject;
import io.papermc.paper.event.player.AsyncChatEvent;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bxteam.nexus.core.configuration.plugin.PluginConfigurationProvider;
import org.bxteam.nexus.core.multification.MultificationManager;
import org.bxteam.nexus.core.annotations.component.Controller;
import org.bxteam.nexus.core.utils.DurationUtil;
import org.bxteam.nexus.feature.chat.ChatService;

import java.time.Duration;
import java.util.UUID;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class ChatController implements Listener {
    private final ChatService chatService;
    private final MultificationManager multificationManager;
    private final PluginConfigurationProvider configurationProvider;

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onChat(AsyncChatEvent event) {
        Player player = event.getPlayer();
        UUID uniqueId = player.getUniqueId();

        if (!this.chatService.isChatEnabled() && !player.hasPermission("nexus.chat.bypass")) {
            this.multificationManager.create()
                    .player(uniqueId)
                    .notice(translation -> translation.chat().chatDisabled())
                    .send();

            event.setCancelled(true);
            return;
        }

        if (this.chatService.hasSlowedChat(uniqueId) && this.configurationProvider.configuration().chat().slowModeEnabled() && !player.hasPermission("nexus.chat.slowmode.bypass")) {
            Duration remainingDuration = this.chatService.getRemainingCoolDown(uniqueId);

            this.multificationManager.create()
                    .player(uniqueId)
                    .notice(translation -> translation.chat().slowMode())
                    .placeholder("{TIME}", DurationUtil.format(remainingDuration, true))
                    .send();

            event.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    void markUseChat(AsyncChatEvent event) {
        this.chatService.setCooldown(event.getPlayer().getUniqueId());
    }
}
