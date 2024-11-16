package space.bxteam.nexus.core.multification.argument;

import com.google.inject.Inject;
import dev.rollczi.litecommands.argument.Argument;
import dev.rollczi.litecommands.argument.parser.ParseResult;
import dev.rollczi.litecommands.argument.resolver.ArgumentResolver;
import dev.rollczi.litecommands.invocation.Invocation;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.CommandSender;
import space.bxteam.nexus.core.configuration.PluginConfigurationProvider;
import space.bxteam.nexus.core.translation.Language;
import space.bxteam.nexus.core.translation.Translation;
import space.bxteam.nexus.core.translation.TranslationManager;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
public abstract class MultificationLiteArgument<T> extends ArgumentResolver<CommandSender, T> {
    protected final TranslationManager translationManager;
    protected final PluginConfigurationProvider configurationProvider;

    @Override
    protected ParseResult<T> parse(Invocation<CommandSender> invocation, Argument<T> context, String argument) {
        Language language = this.configurationProvider.configuration().language();
        Translation translation = this.translationManager.getMessages(language);

        return this.parse(invocation, argument, translation);
    }

    public abstract ParseResult<T> parse(Invocation<CommandSender> invocation, String argument, Translation translation);
}
