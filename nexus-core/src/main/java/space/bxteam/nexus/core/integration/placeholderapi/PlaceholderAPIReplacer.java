package space.bxteam.nexus.core.integration.placeholderapi;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;
import space.bxteam.nexus.core.placeholder.PlaceholderReplacer;

public class PlaceholderAPIReplacer implements PlaceholderReplacer {
    @Override
    public String apply(String text, Player targetPlayer) {
        return PlaceholderAPI.setPlaceholders(targetPlayer, text);
    }
}
