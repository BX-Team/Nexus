package org.bxteam.nexus.core.feature.home;

import com.google.inject.Inject;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bxteam.nexus.core.placeholder.PlaceholderRegistry;
import org.bxteam.nexus.core.placeholder.PlaceholderReplacer;
import org.bxteam.nexus.core.annotations.component.Controller;
import org.bxteam.nexus.core.translation.Translation;
import org.bxteam.nexus.core.translation.TranslationProvider;
import org.bxteam.nexus.event.NexusInitializeEvent;
import org.bxteam.nexus.feature.home.Home;
import org.bxteam.nexus.feature.home.HomeService;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class HomeController implements Listener {
    private final TranslationProvider translationProvider;
    private final PlaceholderRegistry placeholderRegistry;
    private final HomeService homeService;

    @EventHandler
    public void registerPlaceholders(NexusInitializeEvent event) {
        Stream.of(
                PlaceholderReplacer.of("homes_left", (text, target) -> this.homesLeft(target)),
                PlaceholderReplacer.of("homes_count", (text, target) -> this.homesCount(target)),
                PlaceholderReplacer.of("homes_limit", (text, target) -> this.homesLimit(target)),
                PlaceholderReplacer.of("homes_owned", (text, target) -> this.ownedHomes(target))

        ).forEach(placeholderRegistry::registerPlaceholder);
    }

    private String homesLeft(Player player) {
        int homesLimit = this.homeService.getHomeLimit(player);
        int amountOfHomes = this.homeService.getAmountOfHomes(player.getUniqueId());

        return homesLeft(homesLimit, amountOfHomes);
    }

    static String homesLeft(int homesLimit, int amountOfHomes) {
        if (homesLimit < -1 || amountOfHomes > homesLimit) {
            return "0";
        }

        int result = homesLimit - amountOfHomes;

        return String.valueOf(result);
    }

    private String homesCount(Player player) {
        return String.valueOf(this.homeService.getAmountOfHomes(player.getUniqueId()));
    }

    private String homesLimit(Player player) {
        return String.valueOf(this.homeService.getHomeLimit(player));
    }

    private String ownedHomes(Player player) {
        Collection<Home> homes = this.homeService.getHomes(player.getUniqueId());

        Translation translation = this.translationProvider.getCurrentTranslation();

        if (homes.isEmpty()) {
            return translation.home().noHomesPlaceholder();
        }

        return homes.stream().map(Home::name).collect(Collectors.joining(", "));
    }
}
