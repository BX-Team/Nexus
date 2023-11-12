package gq.bxteam.nexus.listeners;

import gq.bxteam.nexus.Nexus;
import gq.bxteam.nexus.utils.TextUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.inventory.meta.BookMeta;
import org.jetbrains.annotations.NotNull;

public class BookAndSignEditListener implements Listener {
    @EventHandler
    public void onPlayerSignEdit(SignChangeEvent event) {
        if (event.isCancelled()) return;
        Player player = event.getPlayer();
        String[] lines = event.getLines();
        if (!Nexus.getInstance().getConfigBoolean("player.sign-formatting.enable") ||
                !player.hasPermission("nexus.feature.sign-formatting")) return;

        for (int i = 0; i < lines.length; i++) {
            event.setLine(i, TextUtils.applyColor(lines[i]));
        }
    }

    @EventHandler
    public void onPlayerBookEdit(@NotNull PlayerEditBookEvent event) {
        if (event.isCancelled()) return;
        Player player = event.getPlayer();
        if (!Nexus.getInstance().getConfigBoolean("player.book-formatting.enable") ||
                !player.hasPermission("nexus.feature.book-formatting")) return;

        BookMeta bookMeta = event.getNewBookMeta();

        for (int x = 1; x <= event.getNewBookMeta().getPages().size(); x++) {
            String string = bookMeta.getPage(x);
            if (string.isEmpty()) continue;

            bookMeta.setPage(x, TextUtils.applyColor(string));
        }

        if (event.isSigning()) {
            bookMeta.setTitle(TextUtils.applyColor(bookMeta.getTitle()));
        }

        event.setNewBookMeta(bookMeta);
    }
}
