package gq.bxteam.nexus.listeners;

import gq.bxteam.nexus.Nexus;
import gq.bxteam.nexus.utils.TextUtils;
import gq.bxteam.nexus.utils.logger.Logger;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.util.CachedServerIcon;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;
import java.util.Random;

public class ServerListPingListener implements Listener {
    @EventHandler
    public void updateServerList(@NotNull ServerListPingEvent event) {
        if (Nexus.getInstance().getConfigBoolean("server.motd.enable")) {
            List<String> motds = Nexus.getInstance().localeReader.getStringList("server.motd.messages");
            if (!motds.isEmpty()) {
                int numberMotd = getRandomNumber(0, motds.size());
                event.setMotd(TextUtils.applyColor(motds.get(numberMotd)));
            }
        }

        if (Nexus.getInstance().getConfigBoolean("server.online.enable")) {
            event.setMaxPlayers(Nexus.getInstance().getConfigInt("server.online.count"));
        }

        if (Nexus.getInstance().getConfigBoolean("server.icon.enable")) {
            List<String> iconNames = Nexus.getInstance().getConfig().getStringList("server.icon.icons");
            if (!iconNames.isEmpty()) {
                int numberIcon = Nexus.getInstance().getConfigString("server.icon.mode").equals("single")
                        ? 0
                        : getRandomNumber(0, iconNames.size());

                setIcon(event, iconNames.get(numberIcon));
            }
        }
    }

    private void setIcon(@NotNull ServerListPingEvent event, @NotNull String iconName) {
        try {
            CachedServerIcon serverIcon = Bukkit.loadServerIcon(new File(Nexus.getInstance().getDataFolder(), "icons" + File.separator + iconName + ".png"));
            event.setServerIcon(serverIcon);
        } catch (Exception ex) {
            Logger.log("Unable to load and install " + iconName + ".png image", Logger.LogLevel.ERROR, true);
            ex.printStackTrace();
        }
    }

    private int getRandomNumber(int start, int end) {
        if (start > end) return 0;
        return start == end ? start : start + new Random().nextInt(end - start);
    }
}
