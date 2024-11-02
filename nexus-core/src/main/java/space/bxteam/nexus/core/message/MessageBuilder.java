package space.bxteam.nexus.core.message;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import space.bxteam.nexus.core.translation.Translation;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

public class MessageBuilder {
    private final MiniMessage miniMessage;
    private CommandSender recipient;
    private Function<Translation, String> messageFunction;
    private final Map<String, String> placeholders = new HashMap<>();

    @Inject
    public MessageBuilder(@Named("colorMiniMessage") MiniMessage miniMessage) {
        this.miniMessage = miniMessage;
    }

    public MessageBuilder player(Player player) {
        this.recipient = player;
        return this;
    }

    public MessageBuilder player(UUID uuid) {
        this.recipient = Bukkit.getPlayer(uuid);
        return this;
    }

    public MessageBuilder recipient(CommandSender recipient) {
        this.recipient = recipient;
        return this;
    }

    public MessageBuilder notice(Function<Translation, String> messageFunction) {
        this.messageFunction = messageFunction;
        return this;
    }

    public MessageBuilder placeholder(String placeholder, String value) {
        this.placeholders.put(placeholder, value);
        return this;
    }

    public void send(Translation translation) {
        if (recipient == null || messageFunction == null) {
            throw new IllegalStateException("Recipient and message function must be set before sending a message.");
        }

        String messageTemplate = messageFunction.apply(translation);

        for (Map.Entry<String, String> entry : placeholders.entrySet()) {
            messageTemplate = messageTemplate.replace(entry.getKey(), entry.getValue());
        }

        Component messageComponent = miniMessage.deserialize(messageTemplate);

        recipient.sendMessage(messageComponent);
    }
}
