package space.bxteam.nexus.core.feature.essentials.item.enchant;

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

import java.util.List;

@LiteArgument(type = int.class, name = EnchantCommandArgument.KEY)
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class EnchantCommandArgument extends ArgumentResolver<CommandSender, Integer> {
    private static final List<Integer> suggestions = List.of(1, 2, 3, 4, 5);
    public static final String KEY = "enchant-level";
    private final MessageManager messageManager;

    @Override
    protected ParseResult<Integer> parse(Invocation<CommandSender> invocation, Argument<Integer> context, String argument) {
        try {
            int value = Integer.parseInt(argument);

            return ParseResult.success(value);
        }
        catch (NumberFormatException exception) {
            return ParseResult.failure(messageManager.getMessage(translation -> translation.argument().noValidEnchantmentLevel()));
        }
    }

    @Override
    public SuggestionResult suggest(Invocation<CommandSender> invocation, Argument<Integer> argument, SuggestionContext context) {
        return suggestions.stream()
                .map(String::valueOf)
                .collect(SuggestionResult.collector());
    }
}
