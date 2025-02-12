package org.bxteam.nexus.core.placeholder;

import org.bukkit.command.CommandSender;

import java.util.Optional;

public interface PlaceholderRegistry {
    void registerPlaceholder(PlaceholderReplacer replacer);

    String format(String text, CommandSender target);

    Optional<PlaceholderRaw> getRawPlaceholder(String target);
}
