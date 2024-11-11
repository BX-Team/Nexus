package space.bxteam.nexus.core.message;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import space.bxteam.nexus.core.translation.Translation;

import java.util.Map;
import java.util.function.Function;

public record MessageManager(Translation translation, MiniMessage miniMessage) {
    @Inject
    public MessageManager(Translation translation, @Named("colorMiniMessage") MiniMessage miniMessage) {
        this.translation = translation;
        this.miniMessage = miniMessage;
    }

    @Contract(" -> new")
    public @NotNull MessageBuilder create() {
        return new MessageBuilder(miniMessage, translation);
    }

    public Component getMessage(Function<Translation, String> messageFunction) {
        String messageTemplate = messageFunction.apply(translation);
        return miniMessage.deserialize(messageTemplate);
    }

    public Component getMessage(Function<Translation, String> messageFunction, Map<String, String> placeholders) {
        String messageTemplate = messageFunction.apply(translation);

        for (Map.Entry<String, String> entry : placeholders.entrySet()) {
            messageTemplate = messageTemplate.replace(entry.getKey(), entry.getValue());
        }

        return miniMessage.deserialize(messageTemplate);
    }
}
