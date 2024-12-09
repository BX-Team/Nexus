package space.bxteam.nexus.core.integration.placeholderapi.resolver;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Optional;

public interface PlaceholderRegistry {
    void registerPlaceholder(PlaceholderReplacer replacer);

    String format(String text, CommandSender target);

    Optional<PlaceholderRaw> getRawPlaceholder(String target);
}
