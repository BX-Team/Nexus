package org.bxteam.nexus.core.feature.event;

import com.google.inject.Inject;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bxteam.nexus.core.annotations.component.Controller;
import org.bxteam.nexus.core.multification.MultificationManager;
import org.bxteam.nexus.core.utils.RandomElementUtil;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class PlayerJoinMessageController implements Listener {
    private final MultificationManager multificationManager;

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        event.setJoinMessage(null);

        if (!player.hasPlayedBefore()) {
            this.multificationManager.create()
                    .noticeOptional(translation -> RandomElementUtil.randomElement(translation.event().firstJoinMessage()))
                    .placeholder("{PLAYER}", player.getName())
                    .onlinePlayers()
                    .send();
            return;
        }

        this.multificationManager.create()
                .player(player.getUniqueId())
                .notice(translation -> translation.event().welcome())
                .placeholder("{PLAYER}", player.getName())
                .sendAsync();

        this.multificationManager.create()
                .noticeOptional(translation -> RandomElementUtil.randomElement(translation.event().joinMessage()))
                .placeholder("{PLAYER}", player.getName())
                .onlinePlayers()
                .sendAsync();
    }
}
