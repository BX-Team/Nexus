package space.bxteam.nexus.core.integration.litecommands.argument;

import com.google.inject.Inject;
import dev.rollczi.litecommands.argument.Argument;
import dev.rollczi.litecommands.argument.parser.ParseResult;
import dev.rollczi.litecommands.argument.resolver.ArgumentResolver;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.suggestion.SuggestionContext;
import dev.rollczi.litecommands.suggestion.SuggestionResult;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.CommandSender;
import space.bxteam.nexus.core.configuration.PluginConfigurationProvider;
import space.bxteam.nexus.core.integration.litecommands.annotations.LiteArgument;
import space.bxteam.nexus.core.message.MessageManager;
import space.bxteam.nexus.feature.warp.Warp;
import space.bxteam.nexus.feature.warp.WarpService;

import java.util.ArrayList;

@LiteArgument(type = Warp.class)
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class WarpCommandArgument extends ArgumentResolver<CommandSender, Warp> {
    private final MessageManager messageManager;
    private final WarpService warpService;
    private final PluginConfigurationProvider configurationProvider;

    @Override
    protected ParseResult<Warp> parse(Invocation<CommandSender> invocation, Argument<Warp> context, String argument) {
        if (!this.warpService.warpExists(argument)) {
            invocation.sender().sendMessage(configurationProvider.configuration().prefix() + "&cWarp not found");
            return null;
        }

        return ParseResult.success(this.warpService.getWarp(argument).orElseThrow());
    }

    @Override
    public SuggestionResult suggest(Invocation<CommandSender> invocation, Argument<Warp> argument, SuggestionContext context) {
        return SuggestionResult.of(new ArrayList<>(this.warpService.getWarpNames()));
    }
}
