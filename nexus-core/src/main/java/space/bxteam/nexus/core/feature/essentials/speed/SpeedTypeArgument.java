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

import java.util.Arrays;

@LiteArgument(type = SpeedType.class)
public class SpeedTypeArgument extends MultificationLiteArgument<SpeedType> {
    @Inject
    public SpeedTypeArgument(TranslationManager translationManager, PluginConfigurationProvider configurationProvider) {
        super(translationManager, configurationProvider);
    }

    @Override
    public ParseResult<SpeedType> parse(Invocation<CommandSender> invocation, String argument, Translation translation) {
        try {
            return ParseResult.success(SpeedType.valueOf(argument.toUpperCase()));
        }
        catch (IllegalArgumentException exception) {
            return ParseResult.failure(translation.player().speedTypeNotCorrect());
        }
    }

    @Override
    public SuggestionResult suggest(Invocation<CommandSender> invocation, Argument<SpeedType> argument, SuggestionContext context) {
        return Arrays.stream(SpeedType.values())
                .map(SpeedType::name)
                .collect(SuggestionResult.collector());
    }
}
