package org.bxteam.nexus.core.feature.event;

import com.eternalcode.multification.notice.Notice;
import com.google.inject.Inject;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bxteam.nexus.core.annotations.component.Controller;
import org.bxteam.nexus.core.multification.MultificationManager;
import org.bxteam.nexus.core.utils.RandomElementUtil;

import java.util.List;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class DeathMessageController implements Listener {
    private final MultificationManager multificationManager;

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getPlayer();
        event.setDeathMessage(null);
        EntityDamageEvent damageCause = player.getLastDamageCause();

        if (damageCause instanceof EntityDamageByEntityEvent causeByEntity && causeByEntity.getDamager() instanceof Player killer) {
            this.multificationManager.create()
                    .noticeOptional(translation -> RandomElementUtil.randomElement(translation.event().deathMessage()))
                    .placeholder("{PLAYER}", player.getName())
                    .placeholder("{KILLER}", killer.getName())
                    .onlinePlayers()
                    .send();
            return;
        }

        if (damageCause != null) {
            EntityDamageEvent.DamageCause cause = damageCause.getCause();

            this.multificationManager.create()
                    .noticeOptional(translation -> {
                        List<Notice> notifications = translation.event().deathMessageByDamageCause().get(cause);

                        if (notifications == null) return RandomElementUtil.randomElement(translation.event().unknownDeathCause());

                        return RandomElementUtil.randomElement(notifications);
                    })
                    .placeholder("{PLAYER}", player.getName())
                    .placeholder("{CAUSE}", cause.name())
                    .onlinePlayers()
                    .send();
            return;
        }

        this.multificationManager.create()
                .noticeOptional(translation -> RandomElementUtil.randomElement(translation.event().unknownDeathCause()))
                .placeholder("{PLAYER}", player.getName())
                .onlinePlayers()
                .send();
    }
}
