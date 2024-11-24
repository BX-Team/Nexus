package space.bxteam.nexus.core.feature.essentials.item.enchant;

import com.google.inject.Inject;
import dev.rollczi.litecommands.argument.Argument;
import dev.rollczi.litecommands.argument.parser.ParseResult;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.suggestion.SuggestionContext;
import dev.rollczi.litecommands.suggestion.SuggestionResult;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import space.bxteam.nexus.core.configuration.plugin.PluginConfigurationProvider;
import space.bxteam.nexus.core.multification.argument.MultificationLiteArgument;
import space.bxteam.nexus.core.scanner.annotations.litecommands.LiteArgument;
import space.bxteam.nexus.core.translation.Translation;
import space.bxteam.nexus.core.translation.TranslationProvider;

import java.util.Arrays;
import java.util.Locale;

@LiteArgument(type = Enchantment.class)
public class EnchantmentArgument extends MultificationLiteArgument<Enchantment> {
    @Inject
    public EnchantmentArgument(TranslationProvider translationProvider, PluginConfigurationProvider configurationProvider) {
        super(translationProvider, configurationProvider);
    }

    @Override
    public ParseResult<Enchantment> parse(Invocation<CommandSender> invocation, String argument, Translation translation) {
        Enchantment enchantment = Enchantment.getByKey(NamespacedKey.minecraft(argument.toLowerCase(Locale.ROOT)));

        if (enchantment == null) {
            return ParseResult.failure(translation.argument().noEnchantment());
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
