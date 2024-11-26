package space.bxteam.nexus.core.feature.teleportrequest.command;

import com.google.inject.Inject;
import dev.rollczi.litecommands.argument.Argument;
import dev.rollczi.litecommands.argument.parser.ParseResult;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.suggestion.SuggestionContext;
import dev.rollczi.litecommands.suggestion.SuggestionResult;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import space.bxteam.nexus.core.configuration.plugin.PluginConfigurationProvider;
import space.bxteam.nexus.core.multification.argument.MultificationLiteArgument;
import space.bxteam.nexus.core.scanner.annotations.litecommands.LiteArgument;
import space.bxteam.nexus.core.translation.Translation;
import space.bxteam.nexus.core.translation.TranslationProvider;
import space.bxteam.nexus.feature.teleportrequest.TeleportRequestService;

import java.util.Objects;

@LiteArgument(type = Player.class, name = RequesterArgument.KEY)
public class RequesterArgument extends MultificationLiteArgument<Player> {
    static final String KEY = "requester";

    private final TeleportRequestService requestService;
    private final Server server;

    @Inject
    public RequesterArgument(TranslationProvider translationProvider, PluginConfigurationProvider configurationProvider, TeleportRequestService requestService, Server server) {
        super(translationProvider, configurationProvider);
        this.requestService = requestService;
        this.server = server;
    }

    @Override
    public ParseResult<Player> parse(Invocation<CommandSender> invocation, String argument, Translation translation) {
        Player target = this.server.getPlayer(argument);

        if (!(invocation.sender() instanceof Player player)) {
            return ParseResult.failure(translation.argument().onlyPlayers());
        }

        if (target == null || !this.requestService.hasRequest(target.getUniqueId(), player.getUniqueId())) {
            return ParseResult.failure(translation.teleportRequest().tpaDenyNoRequestMessage());
        }

        return ParseResult.success(target);
    }

    @Override
    public SuggestionResult suggest(Invocation<CommandSender> invocation, Argument<Player> argument, SuggestionContext context) {
        if (!(invocation.sender() instanceof Player player)) {
            return SuggestionResult.empty();
        }

        return this.requestService.findRequests(player.getUniqueId()).stream()
                .map(this.server::getPlayer)
                .filter(Objects::nonNull)
                .map(HumanEntity::getName)
                .collect(SuggestionResult.collector());
    }
}
