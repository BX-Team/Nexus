package space.bxteam.nexus.core.integration.placeholderapi.resolver;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
@Singleton
public class PlaceholderRegistryImpl implements PlaceholderRegistry {
    private final Set<PlaceholderReplacer> replacerPlayers = new HashSet<>();
    private final Map<String, PlaceholderRaw> rawPlaceholders = new HashMap<>();

    @Override
    public void registerPlaceholder(PlaceholderReplacer replacer) {
        this.replacerPlayers.add(replacer);

        if (replacer instanceof PlaceholderRaw raw) {
            this.rawPlaceholders.put(raw.getRawTarget(), raw);
        }
    }

    @Override
    public String format(String text, CommandSender target) {
        if (target instanceof Player player) {
            for (PlaceholderReplacer replacer : this.replacerPlayers) {
                text = replacer.apply(text, player);
            }
        }

        return text;
    }

    @Override
    public Optional<PlaceholderRaw> getRawPlaceholder(String target) {
        return Optional.ofNullable(this.rawPlaceholders.get(target));
    }
}
