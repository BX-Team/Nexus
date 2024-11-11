package space.bxteam.nexus.core.feature.warp.command;

import com.google.inject.Inject;
import dev.rollczi.litecommands.argument.Argument;
import dev.rollczi.litecommands.argument.parser.ParseResult;
import dev.rollczi.litecommands.argument.resolver.ArgumentResolver;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.suggestion.SuggestionContext;
import dev.rollczi.litecommands.suggestion.SuggestionResult;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.CommandSender;
import space.bxteam.nexus.core.scanner.annotations.litecommands.LiteArgument;
import space.bxteam.nexus.core.message.MessageManager;
import space.bxteam.nexus.feature.warp.Warp;
import space.bxteam.nexus.feature.warp.WarpService;

import java.util.Optional;

@LiteArgument(type = Warp.class)
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class WarpCommandArgument extends ArgumentResolver<CommandSender, Warp> {
    private final MessageManager messageManager;
    private final WarpService warpService;

    @Override
    protected ParseResult<Warp> parse(Invocation<CommandSender> invocation, Argument<Warp> context, String argument) {
        Optional<Warp> warpOption = this.warpService.getWarp(argument);

        return warpOption.map(ParseResult::success).orElseGet(() -> ParseResult.failure(messageManager.getMessage(translation -> translation.warp().notExist())));
    }

    @Override
    public SuggestionResult suggest(Invocation<CommandSender> invocation, Argument<Warp> argument, SuggestionContext context) {
        return this.warpService.getWarpNames().stream().collect(SuggestionResult.collector());
    }
}
