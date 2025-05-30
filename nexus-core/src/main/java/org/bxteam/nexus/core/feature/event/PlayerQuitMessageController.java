package org.bxteam.nexus.core.feature.event;

import com.google.inject.Inject;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bxteam.nexus.core.annotations.component.Controller;
import org.bxteam.nexus.core.multification.MultificationManager;
import org.bxteam.nexus.core.utils.RandomElementUtil;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class PlayerQuitMessageController implements Listener {
    private final MultificationManager multificationManager;

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        event.setQuitMessage(null);

        this.multificationManager.create()
                .noticeOptional(translation -> RandomElementUtil.randomElement(translation.event().quitMessage()))
                .placeholder("{PLAYER}", player.getName())
                .onlinePlayers()
                .send();
    }
}
