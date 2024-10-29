package space.bxteam.nexus.core.feature.essentials.gamemode;

import com.google.inject.Inject;
import dev.rollczi.litecommands.argument.Argument;
import dev.rollczi.litecommands.argument.parser.ParseResult;
import dev.rollczi.litecommands.argument.resolver.ArgumentResolver;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.suggestion.SuggestionContext;
import dev.rollczi.litecommands.suggestion.SuggestionResult;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import space.bxteam.nexus.core.configuration.PluginConfigurationProvider;

import java.util.HashMap;
import java.util.Map;

public class GamemodeCommandArgument extends ArgumentResolver<CommandSender, GameMode> {
    private static final Map<String, GameMode> GAME_MODE_ARGUMENTS = new HashMap<>();
    private final PluginConfigurationProvider configurationProvider;

    @Inject
    public GamemodeCommandArgument(
            PluginConfigurationProvider configurationProvider
    ) {
        this.configurationProvider = configurationProvider;
    }

    static {
        for (GameMode value : GameMode.values()) {
            GAME_MODE_ARGUMENTS.put(value.name().toLowerCase(), value);
            GAME_MODE_ARGUMENTS.put(String.valueOf(value.getValue()), value);
            GAME_MODE_ARGUMENTS.put(value.name().substring(0, 1).toLowerCase(), value);
        }
    }

    @Override
    protected ParseResult<GameMode> parse(Invocation<CommandSender> invocation, Argument<GameMode> context, String argument) {
        GameMode gameMode = GAME_MODE_ARGUMENTS.get(argument.toLowerCase());

        if (gameMode == null) {
            invocation.sender().sendMessage(configurationProvider.configuration().prefix() + "§cInvalid gamemode argument");
            return null;
        }

        return ParseResult.success(gameMode);
    }

    @Override
    public SuggestionResult suggest(Invocation<CommandSender> invocation, Argument<GameMode> argument, SuggestionContext context) {
        return SuggestionResult.of(GAME_MODE_ARGUMENTS.keySet());
    }
}
