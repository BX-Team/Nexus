package space.bxteam.nexus.core.feature.essentials.time;

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

import java.util.List;

@LiteArgument(type = int.class, name = TimeCommandArgument.KEY)
public class TimeCommandArgument extends MultificationLiteArgument<Integer> {
    private static final List<Integer> suggestions = List.of(1000, 6000, 13000, 18000);
    public static final String KEY = "time";

    @Inject
    public TimeCommandArgument(TranslationManager translationManager, PluginConfigurationProvider configurationProvider) {
        super(translationManager, configurationProvider);
    }

    @Override
    public ParseResult<Integer> parse(Invocation<CommandSender> invocation, String argument, Translation translation) {
        try {
            int value = Integer.parseInt(argument);

            if (value < 0) {
                return ParseResult.failure(translation.argument().numberBiggerThanOrEqualZero());
            }

            return ParseResult.success(value);
        }
        catch (NumberFormatException exception) {
            return ParseResult.failure(translation.argument().numberBiggerThanOrEqualZero());
        }
    }

    @Override
    public SuggestionResult suggest(Invocation<CommandSender> invocation, Argument<Integer> argument, SuggestionContext context) {
        return suggestions.stream()
                .map(String::valueOf)
                .collect(SuggestionResult.collector());
    }
}