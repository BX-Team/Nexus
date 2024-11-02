package space.bxteam.nexus.core.message;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import net.kyori.adventure.text.minimessage.MiniMessage;
import space.bxteam.nexus.core.translation.Translation;

public record MessageManager(Translation translation, MiniMessage miniMessage) {
    @Inject
    public MessageManager(Translation translation, @Named("colorMiniMessage") MiniMessage miniMessage) {
        this.translation = translation;
        this.miniMessage = miniMessage;
    }

    public MessageBuilder create() {
        return new MessageBuilder(miniMessage);
    }
}
