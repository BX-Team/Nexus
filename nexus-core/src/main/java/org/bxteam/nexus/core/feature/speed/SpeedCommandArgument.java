package org.bxteam.nexus.core.feature.speed;

import com.google.inject.Inject;
import dev.rollczi.litecommands.argument.Argument;
import dev.rollczi.litecommands.argument.parser.ParseResult;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.suggestion.SuggestionContext;
import dev.rollczi.litecommands.suggestion.SuggestionResult;
import org.bukkit.command.CommandSender;
import org.bxteam.nexus.core.configuration.plugin.PluginConfigurationProvider;
import org.bxteam.nexus.core.multification.MultificationLiteArgument;
import org.bxteam.nexus.core.annotations.litecommands.LiteArgument;
import org.bxteam.nexus.core.translation.Translation;
import org.bxteam.nexus.core.translation.TranslationProvider;

import java.util.stream.IntStream;

@LiteArgument(type = Integer.class, name = SpeedCommandArgument.KEY)
public class SpeedCommandArgument extends MultificationLiteArgument<Integer> {
    public static final String KEY = "speed";

    @Inject
    public SpeedCommandArgument(TranslationProvider translationProvider, PluginConfigurationProvider configurationProvider) {
        super(translationProvider, configurationProvider);
    }

    @Override
    public ParseResult<Integer> parse(Invocation<CommandSender> invocation, String argument, Translation translation) {
        try {
            int value = Integer.parseInt(argument);

            if (value < 0 || value > 10) {
                return ParseResult.failure(translation.player().speedBetweenZeroAndTen());
            }

            return ParseResult.success(value);
        }
        catch (NumberFormatException exception) {
            return ParseResult.failure(translation.player().speedBetweenZeroAndTen());
        }
    }

    @Override
    public SuggestionResult suggest(Invocation<CommandSender> invocation, Argument<Integer> argument, SuggestionContext context) {
        return IntStream.range(0, 11)
                .mapToObj(String::valueOf)
                .collect(SuggestionResult.collector());
    }
}
