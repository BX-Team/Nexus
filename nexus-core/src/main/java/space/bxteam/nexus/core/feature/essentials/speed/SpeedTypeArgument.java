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
import space.bxteam.nexus.core.scanner.annotations.litecommands.LiteArgument;
import space.bxteam.nexus.core.message.MessageManager;

import java.util.Arrays;

@LiteArgument(type = SpeedType.class)
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class SpeedTypeArgument extends ArgumentResolver<CommandSender, SpeedType> {
    private final MessageManager messageManager;

    @Override
    protected ParseResult<SpeedType> parse(Invocation<CommandSender> invocation, Argument<SpeedType> context, String argument) {
        try {
            return ParseResult.success(SpeedType.valueOf(argument.toUpperCase()));
        }
        catch (IllegalArgumentException exception) {
            return ParseResult.failure(messageManager.getMessage(translation -> translation.player().speedTypeNotCorrect()));
        }
    }

    @Override
    public SuggestionResult suggest(Invocation<CommandSender> invocation, Argument<SpeedType> argument, SuggestionContext context) {
        return Arrays.stream(SpeedType.values())
                .map(SpeedType::name)
                .collect(SuggestionResult.collector());
    }
}
