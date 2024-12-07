package space.bxteam.nexus.core.feature.home.command;

import com.eternalcode.multification.notice.NoticeBroadcast;
import com.google.inject.Inject;
import dev.rollczi.litecommands.argument.Argument;
import dev.rollczi.litecommands.argument.parser.ParseResult;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.suggestion.SuggestionContext;
import dev.rollczi.litecommands.suggestion.SuggestionResult;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import space.bxteam.nexus.core.configuration.plugin.PluginConfigurationProvider;
import space.bxteam.nexus.core.multification.argument.MultificationLiteArgument;
import space.bxteam.nexus.core.multification.MultificationManager;
import space.bxteam.nexus.core.scanner.annotations.litecommands.LiteArgument;
import space.bxteam.nexus.core.translation.Translation;
import space.bxteam.nexus.core.translation.TranslationProvider;
import space.bxteam.nexus.feature.home.Home;
import space.bxteam.nexus.feature.home.HomeService;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@LiteArgument(type = Home.class)
public class HomeCommandArgument extends MultificationLiteArgument<Home> {
    private final HomeService homeService;
    private final MultificationManager multificationManager;

    @Inject
    public HomeCommandArgument(TranslationProvider translationProvider, PluginConfigurationProvider configurationProvider, HomeService homeService, MultificationManager multificationManager) {
        super(translationProvider, configurationProvider);
        this.homeService = homeService;
        this.multificationManager = multificationManager;
    }

    @Override
    public ParseResult<Home> parse(Invocation<CommandSender> invocation, String argument, Translation translation) {
        UUID playerUUID = ((Player) invocation.sender()).getUniqueId();

        Optional<Home> getHome = this.homeService.getHome(playerUUID, argument);

        if (getHome.isPresent()) {
            return ParseResult.success(getHome.get());
        }

        String homes = this.homeService.getHomes(playerUUID).stream()
                .map(Home::name)
                .collect(Collectors.joining(", "));

        NoticeBroadcast homeListNotice = this.multificationManager.create()
                .viewer(invocation.sender())
                .notice(translate -> translate.home().homeList())
                .placeholder("{HOMES}", homes);

        return ParseResult.failure(homeListNotice);
    }

    @Override
    public SuggestionResult suggest(Invocation<CommandSender> invocation, Argument<Home> argument, SuggestionContext context) {
        Player player = (Player) invocation.sender();
        return this.homeService.getHomes(player.getUniqueId()).stream()
                .map(Home::name)
                .collect(SuggestionResult.collector());
    }
}
