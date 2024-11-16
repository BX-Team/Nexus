package space.bxteam.nexus.core.multification;

import com.eternalcode.multification.adventure.AudienceConverter;
import com.eternalcode.multification.bukkit.BukkitMultification;
import com.eternalcode.multification.translation.TranslationProvider;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.platform.AudienceProvider;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.ComponentSerializer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import space.bxteam.nexus.core.translation.Language;
import space.bxteam.nexus.core.translation.Translation;
import space.bxteam.nexus.core.translation.TranslationFactory;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class MultificationManager extends BukkitMultification<Translation> {
    private final TranslationFactory translationFactory;
    private final AudienceProvider audienceProvider;
    @Named("colorMiniMessage")
    private final MiniMessage miniMessage;

    @Override
    protected @NotNull TranslationProvider<Translation> translationProvider() {
        return locale -> this.translationFactory.getTranslation(Language.fromLocale(locale));
    }

    @Override
    protected @NotNull ComponentSerializer<Component, Component, String> serializer() {
        return this.miniMessage;
    }

    @Override
    protected @NotNull AudienceConverter<CommandSender> audienceConverter() {
        return commandSender -> {
            if (commandSender instanceof Player player) {
                return this.audienceProvider.player(player.getUniqueId());
            }

            return this.audienceProvider.console();
        };
    }
}
