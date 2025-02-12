package org.bxteam.nexus.core.feature.essentials.item.enchant;

import com.google.inject.Inject;
import dev.rollczi.litecommands.argument.Argument;
import dev.rollczi.litecommands.argument.parser.ParseResult;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.suggestion.SuggestionContext;
import dev.rollczi.litecommands.suggestion.SuggestionResult;
import org.bukkit.command.CommandSender;
import org.bxteam.nexus.core.configuration.plugin.PluginConfigurationProvider;
import org.bxteam.nexus.core.multification.MultificationLiteArgument;
import org.bxteam.nexus.core.registration.annotations.litecommands.LiteArgument;
import org.bxteam.nexus.core.translation.Translation;
import org.bxteam.nexus.core.translation.TranslationProvider;

import java.util.List;

@LiteArgument(type = int.class, name = EnchantCommandArgument.KEY)
public class EnchantCommandArgument extends MultificationLiteArgument<Integer> {
    private static final List<Integer> suggestions = List.of(1, 2, 3, 4, 5);
    public static final String KEY = "enchant-level";

    @Inject
    public EnchantCommandArgument(TranslationProvider translationProvider, PluginConfigurationProvider configurationProvider) {
        super(translationProvider, configurationProvider);
    }

    @Override
    public ParseResult<Integer> parse(Invocation<CommandSender> invocation, String argument, Translation translation) {
        try {
            int value = Integer.parseInt(argument);

            return ParseResult.success(value);
        }
        catch (NumberFormatException exception) {
            return ParseResult.failure(translation.argument().noValidEnchantmentLevel());
        }
    }

    @Override
    public SuggestionResult suggest(Invocation<CommandSender> invocation, Argument<Integer> argument, SuggestionContext context) {
        return suggestions.stream()
                .map(String::valueOf)
                .collect(SuggestionResult.collector());
    }
}
