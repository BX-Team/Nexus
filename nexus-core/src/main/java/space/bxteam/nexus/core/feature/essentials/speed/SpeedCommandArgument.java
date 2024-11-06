package space.bxteam.nexus.core.feature.essentials.speed;

import com.google.inject.Inject;
import dev.rollczi.litecommands.argument.Argument;
import dev.rollczi.litecommands.argument.parser.ParseResult;
import dev.rollczi.litecommands.argument.resolver.ArgumentResolver;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.suggestion.SuggestionContext;
import dev.rollczi.litecommands.suggestion.SuggestionResult;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.CommandSender;
import space.bxteam.nexus.core.integration.litecommands.annotations.LiteArgument;
import space.bxteam.nexus.core.message.MessageManager;

import java.util.stream.IntStream;

@LiteArgument(type = Integer.class, name = SpeedCommandArgument.KEY)
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class SpeedCommandArgument extends ArgumentResolver<CommandSender, Integer> {
    public static final String KEY = "speed";
    private final MessageManager messageManager;

    @Override
    protected ParseResult<Integer> parse(Invocation<CommandSender> invocation, Argument<Integer> context, String argument) {
        try {
            int value = Integer.parseInt(argument);

            if (value < 0 || value > 10) {
                return ParseResult.failure(messageManager.getMessage(translation -> translation.player().speedBetweenZeroAndTen()));
            }

            return ParseResult.success(value);
        }
        catch (NumberFormatException exception) {
            return ParseResult.failure(messageManager.getMessage(translation -> translation.player().speedBetweenZeroAndTen()));
        }
    }

    @Override
    public SuggestionResult suggest(Invocation<CommandSender> invocation, Argument<Integer> argument, SuggestionContext context) {
        return IntStream.range(0, 11)
                .mapToObj(String::valueOf)
                .collect(SuggestionResult.collector());
    }
}
