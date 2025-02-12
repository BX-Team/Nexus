package org.bxteam.nexus.core.feature.warp.command;

import com.google.inject.Inject;
import dev.rollczi.litecommands.argument.Argument;
import dev.rollczi.litecommands.argument.parser.ParseResult;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.suggestion.SuggestionContext;
import dev.rollczi.litecommands.suggestion.SuggestionResult;
import org.bukkit.command.CommandSender;
import org.bxteam.nexus.core.configuration.plugin.PluginConfigurationProvider;
import org.bxteam.nexus.core.multification.MultificationLiteArgument;
import org.bxteam.nexus.core.multification.MultificationManager;
import org.bxteam.nexus.core.registration.annotations.litecommands.LiteArgument;
import org.bxteam.nexus.core.translation.Translation;
import org.bxteam.nexus.core.translation.TranslationProvider;
import org.bxteam.nexus.feature.warp.Warp;
import org.bxteam.nexus.feature.warp.WarpService;

import java.util.Optional;

@LiteArgument(type = Warp.class)
public class WarpCommandArgument extends MultificationLiteArgument<Warp> {
    private final MultificationManager multificationManager;
    private final WarpService warpService;

    @Inject
    public WarpCommandArgument(TranslationProvider translationProvider, PluginConfigurationProvider configurationProvider, MultificationManager multificationManager, WarpService warpService) {
        super(translationProvider, configurationProvider);
        this.multificationManager = multificationManager;
        this.warpService = warpService;
    }

    @Override
    public ParseResult<Warp> parse(Invocation<CommandSender> invocation, String argument, Translation translation) {
        Optional<Warp> warpOption = this.warpService.getWarp(argument);

        return warpOption.map(ParseResult::success).orElseGet(() -> ParseResult.failure(translation.warp().notExist()));
    }

    @Override
    public SuggestionResult suggest(Invocation<CommandSender> invocation, Argument<Warp> argument, SuggestionContext context) {
        return this.warpService.getWarpNames().stream().collect(SuggestionResult.collector());
    }
}
