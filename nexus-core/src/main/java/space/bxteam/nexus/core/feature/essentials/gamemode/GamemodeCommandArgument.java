package space.bxteam.nexus.core.feature.essentials.gamemode;

import com.google.inject.Inject;
import dev.rollczi.litecommands.argument.Argument;
import dev.rollczi.litecommands.argument.parser.ParseResult;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.suggestion.SuggestionContext;
import dev.rollczi.litecommands.suggestion.SuggestionResult;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import space.bxteam.nexus.core.configuration.plugin.PluginConfigurationProvider;
import space.bxteam.nexus.core.multification.MultificationLiteArgument;
import space.bxteam.nexus.core.scanner.annotations.litecommands.LiteArgument;
import space.bxteam.nexus.core.translation.Translation;
import space.bxteam.nexus.core.translation.TranslationProvider;

import java.util.HashMap;
import java.util.Map;

@LiteArgument(type = GameMode.class)
public class GamemodeCommandArgument extends MultificationLiteArgument<GameMode> {
    private static final Map<String, GameMode> GAME_MODE_ARGUMENTS = new HashMap<>();

    static {
        for (GameMode value : GameMode.values()) {
            GAME_MODE_ARGUMENTS.put(value.name().toLowerCase(), value);
            GAME_MODE_ARGUMENTS.put(String.valueOf(value.getValue()), value);
            GAME_MODE_ARGUMENTS.put(value.name().substring(0, 1).toLowerCase(), value);
        }
    }

    @Inject
    public GamemodeCommandArgument(TranslationProvider translationProvider, PluginConfigurationProvider configurationProvider) {
        super(translationProvider, configurationProvider);
    }

    @Override
    public ParseResult<GameMode> parse(Invocation<CommandSender> invocation, String argument, Translation translation) {
        GameMode gameMode = GAME_MODE_ARGUMENTS.get(argument.toLowerCase());

        if (gameMode != null) {
            return ParseResult.success(gameMode);
        } else {
            return ParseResult.failure(translation.player().gameModeNotCorrect());
        }
    }

    @Override
    public SuggestionResult suggest(Invocation<CommandSender> invocation, Argument<GameMode> argument, SuggestionContext context) {
        return GAME_MODE_ARGUMENTS.keySet()
                .stream()
                .collect(SuggestionResult.collector());
    }
}
