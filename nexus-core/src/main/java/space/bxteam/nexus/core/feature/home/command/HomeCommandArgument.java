package space.bxteam.nexus.core.feature.home.command;

import com.google.inject.Inject;
import dev.rollczi.litecommands.argument.Argument;
import dev.rollczi.litecommands.argument.parser.ParseResult;
import dev.rollczi.litecommands.argument.resolver.ArgumentResolver;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.suggestion.SuggestionContext;
import dev.rollczi.litecommands.suggestion.SuggestionResult;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import space.bxteam.nexus.core.message.MessageManager;
import space.bxteam.nexus.core.scanner.annotations.litecommands.LiteArgument;
import space.bxteam.nexus.feature.home.Home;
import space.bxteam.nexus.feature.home.HomeService;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@LiteArgument(type = Home.class)
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class HomeCommandArgument extends ArgumentResolver<Player, Home> {
    private final MessageManager messageManager;
    private final HomeService homeService;

    @Override
    protected ParseResult<Home> parse(Invocation<Player> invocation, Argument<Home> context, String argument) {
        UUID playerUUID = invocation.sender().getUniqueId();

        Optional<Home> getHome = this.homeService.getHome(playerUUID, argument);

        if (getHome.isPresent()) {
            return ParseResult.success(getHome.get());
        }

        String homes = this.homeService.getHomes(playerUUID).stream()
                .map(Home::name)
                .collect(Collectors.joining(", "));

        Map<String, String> placeholders = Map.of(
                "{HOMES}", homes
        );
        return ParseResult.failure(this.messageManager.getMessage(translation -> translation.home().homeList(), placeholders));
    }

    @Override
    public SuggestionResult suggest(Invocation<Player> invocation, Argument<Home> argument, SuggestionContext context) {
        return this.homeService.getHomes(invocation.sender().getUniqueId()).stream()
                .map(Home::name)
                .collect(SuggestionResult.collector());
    }
}
