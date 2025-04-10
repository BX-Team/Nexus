package org.bxteam.nexus.core.feature.item.give;

import com.google.inject.Inject;
import dev.rollczi.litecommands.argument.Argument;
import dev.rollczi.litecommands.argument.parser.ParseResult;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.suggestion.SuggestionContext;
import dev.rollczi.litecommands.suggestion.SuggestionResult;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bxteam.nexus.core.configuration.plugin.PluginConfigurationProvider;
import org.bxteam.nexus.core.multification.MultificationLiteArgument;
import org.bxteam.nexus.core.annotations.litecommands.LiteArgument;
import org.bxteam.nexus.core.translation.Translation;
import org.bxteam.nexus.core.translation.TranslationProvider;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@LiteArgument(type = Material.class)
public class MaterialArgument extends MultificationLiteArgument<Material> {
    @Inject
    public MaterialArgument(TranslationProvider translationProvider, PluginConfigurationProvider configurationProvider) {
        super(translationProvider, configurationProvider);
    }

    @Override
    public ParseResult<Material> parse(Invocation<CommandSender> invocation, String argument, Translation translation) {
        Material material = Material.matchMaterial(argument);
        if (material == null || material.name().startsWith("LEGACY")) {
            return ParseResult.failure(translation.argument().noValidItem());
        }
        return ParseResult.success(material);
    }

    @Override
    public SuggestionResult suggest(Invocation<CommandSender> invocation, Argument<Material> argument, SuggestionContext context) {
        List<String> suggestions = Arrays.stream(Material.values())
                .map(Material::name)
                .filter(name -> !name.startsWith("LEGACY"))
                .flatMap(name -> Stream.of("minecraft:" + name.toLowerCase(), name.toLowerCase()))
                .collect(Collectors.toList());
        return SuggestionResult.of(suggestions);
    }
}
