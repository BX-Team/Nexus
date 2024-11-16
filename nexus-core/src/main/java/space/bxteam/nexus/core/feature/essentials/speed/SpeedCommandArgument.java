package space.bxteam.nexus.core.feature.essentials.speed;

import com.google.inject.Inject;
import dev.rollczi.litecommands.argument.Argument;
import dev.rollczi.litecommands.argument.parser.ParseResult;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.suggestion.SuggestionContext;
import dev.rollczi.litecommands.suggestion.SuggestionResult;
import org.bukkit.command.CommandSender;
import space.bxteam.nexus.core.configuration.PluginConfigurationProvider;
import space.bxteam.nexus.core.multification.argument.MultificationLiteArgument;
import space.bxteam.nexus.core.scanner.annotations.litecommands.LiteArgument;
import space.bxteam.nexus.core.translation.Translation;
import space.bxteam.nexus.core.translation.TranslationManager;

import java.util.stream.IntStream;

@LiteArgument(type = Integer.class, name = SpeedCommandArgument.KEY)
public class SpeedCommandArgument extends MultificationLiteArgument<Integer> {
    public static final String KEY = "speed";

    @Inject
    public SpeedCommandArgument(TranslationManager translationManager, PluginConfigurationProvider configurationProvider) {
        super(translationManager, configurationProvider);
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
