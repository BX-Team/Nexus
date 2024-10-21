package space.bxteam.nexus.core.feature.warp.command;

import com.google.inject.Inject;
import dev.rollczi.litecommands.argument.Argument;
import dev.rollczi.litecommands.argument.parser.ParseResult;
import dev.rollczi.litecommands.argument.resolver.ArgumentResolver;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.suggestion.SuggestionContext;
import dev.rollczi.litecommands.suggestion.SuggestionResult;
import org.bukkit.command.CommandSender;
import space.bxteam.nexus.core.files.configuration.PluginConfigurationProvider;
import space.bxteam.nexus.feature.warp.Warp;
import space.bxteam.nexus.feature.warp.WarpService;

import java.util.ArrayList;

public class WarpCommandArgument extends ArgumentResolver<CommandSender, Warp> {
    private final WarpService warpFeature;
    private final PluginConfigurationProvider configurationProvider;

    @Inject
    public WarpCommandArgument(
            WarpService warpFeature,
            PluginConfigurationProvider configurationProvider
    ) {
        this.warpFeature = warpFeature;
        this.configurationProvider = configurationProvider;
    }

    @Override
    protected ParseResult<Warp> parse(Invocation<CommandSender> invocation, Argument<Warp> context, String argument) {
        if (!this.warpFeature.warpExists(argument)) {
            invocation.sender().sendMessage(configurationProvider.configuration().prefix() + "&cWarp not found");
            return null;
        }

        return ParseResult.success(this.warpFeature.getWarp(argument).orElseThrow());
    }

    @Override
    public SuggestionResult suggest(Invocation<CommandSender> invocation, Argument<Warp> argument, SuggestionContext context) {
        return SuggestionResult.of(new ArrayList<>(this.warpFeature.getWarpNames()));
    }
}
