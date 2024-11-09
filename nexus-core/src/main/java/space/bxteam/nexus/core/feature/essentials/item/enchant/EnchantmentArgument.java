package space.bxteam.nexus.core.feature.essentials.item.enchant;

import com.google.inject.Inject;
import dev.rollczi.litecommands.argument.Argument;
import dev.rollczi.litecommands.argument.parser.ParseResult;
import dev.rollczi.litecommands.argument.resolver.ArgumentResolver;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.suggestion.SuggestionContext;
import dev.rollczi.litecommands.suggestion.SuggestionResult;
import lombok.RequiredArgsConstructor;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import space.bxteam.nexus.core.integration.litecommands.annotations.LiteArgument;
import space.bxteam.nexus.core.message.MessageManager;

import java.util.Arrays;
import java.util.Locale;

@LiteArgument(type = Enchantment.class)
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class EnchantmentArgument extends ArgumentResolver<CommandSender, Enchantment> {
    private final MessageManager messageManager;

    @Override
    protected ParseResult<Enchantment> parse(Invocation<CommandSender> invocation, Argument<Enchantment> context, String argument) {
        Enchantment enchantment = Enchantment.getByKey(NamespacedKey.minecraft(argument.toLowerCase(Locale.ROOT)));

        if (enchantment == null) {
            return ParseResult.failure(messageManager.getMessage(translation -> translation.argument().noEnchantment()));
        }

        return ParseResult.success(enchantment);
    }

    @Override
    public SuggestionResult suggest(Invocation<CommandSender> invocation, Argument<Enchantment> argument, SuggestionContext context) {
        return Arrays.stream(Enchantment.values())
                .map(Enchantment::getKey)
                .map(NamespacedKey::getKey)
                .collect(SuggestionResult.collector());
    }
}
