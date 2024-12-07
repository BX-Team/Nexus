package space.bxteam.nexus.core.feature.jail.command;

import com.google.inject.Inject;
import dev.rollczi.litecommands.argument.Argument;
import dev.rollczi.litecommands.argument.parser.ParseResult;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.suggestion.SuggestionContext;
import dev.rollczi.litecommands.suggestion.SuggestionResult;
import org.bukkit.command.CommandSender;
import space.bxteam.nexus.core.configuration.plugin.PluginConfigurationProvider;
import space.bxteam.nexus.core.multification.argument.MultificationLiteArgument;
import space.bxteam.nexus.core.scanner.annotations.litecommands.LiteArgument;
import space.bxteam.nexus.core.translation.Translation;
import space.bxteam.nexus.core.translation.TranslationProvider;
import space.bxteam.nexus.feature.jail.JailService;

@LiteArgument(type = String.class, name = JailCommandArgument.KEY)
public class JailCommandArgument extends MultificationLiteArgument<String> {
    private final JailService jailService;
    static final String KEY = "jails";

    @Inject
    public JailCommandArgument(TranslationProvider translationProvider, PluginConfigurationProvider configurationProvider, JailService jailService) {
        super(translationProvider, configurationProvider);
        this.jailService = jailService;
    }

    @Override
    public ParseResult<String> parse(Invocation<CommandSender> invocation, String argument, Translation translation) {
        return this.jailService.jailExists(argument)
                ? ParseResult.success(argument)
                : ParseResult.failure(translation.jail().jailLocationNotExists());
    }

    @Override
    public SuggestionResult suggest(Invocation<CommandSender> invocation, Argument<String> argument, SuggestionContext context) {
        return this.jailService.getAllJailNames().stream().collect(SuggestionResult.collector());
    }
}