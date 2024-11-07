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
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

public class MessageBuilder {
    private final MiniMessage miniMessage;
    private final Translation translation;
    private CommandSender recipient;
    private Function<Translation, String> singleMessageFunction;
    private Function<Translation, List<String>> listMessageFunction;
    private final Map<String, Function<Translation, String>> stringPlaceholders = new HashMap<>();
    private final Map<String, Component> componentPlaceholders = new HashMap<>();

    @Inject
    public MessageBuilder(@Named("colorMiniMessage") MiniMessage miniMessage, Translation translation) {
        this.miniMessage = miniMessage;
        this.translation = translation;
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

    public MessageBuilder message(Function<Translation, String> messageFunction) {
        this.singleMessageFunction = messageFunction;
        return this;
    }

    public MessageBuilder messages(Function<Translation, List<String>> messageFunction) {
        this.listMessageFunction = messageFunction;
        return this;
    }

    public MessageBuilder placeholder(String placeholder, String value) {
        return placeholder(placeholder, translation -> value);
    }

    public MessageBuilder placeholder(String placeholder, Function<Translation, String> translationFunction) {
        this.stringPlaceholders.put(placeholder, translationFunction);
        return this;
    }

    public MessageBuilder placeholder(String placeholder, Component value) {
        this.componentPlaceholders.put(placeholder, value);
        return this;
    }

    public void send() {
        if (recipient == null) {
            throw new IllegalStateException("Recipient must be set before sending a message.");
        }
        if (singleMessageFunction == null && listMessageFunction == null) {
            throw new IllegalStateException("Message function must be set before sending a message.");
        }

        if (singleMessageFunction != null) {
            String processedMessage = applyPlaceholders(singleMessageFunction.apply(translation));
            sendMessage(processedMessage);
        }

        if (listMessageFunction != null) {
            for (String messageTemplate : listMessageFunction.apply(translation)) {
                String processedMessage = applyPlaceholders(messageTemplate);
                sendMessage(processedMessage);
            }
        }
    }

    private String applyPlaceholders(String messageTemplate) {
        for (Map.Entry<String, Function<Translation, String>> entry : stringPlaceholders.entrySet()) {
            String placeholderValue = entry.getValue().apply(translation);
            messageTemplate = messageTemplate.replace(entry.getKey(), placeholderValue);
        }
        return messageTemplate;
    }

    private void sendMessage(String messageTemplate) {
        Component messageComponent = miniMessage.deserialize(messageTemplate);
        for (Map.Entry<String, Component> entry : componentPlaceholders.entrySet()) {
            messageComponent = messageComponent.replaceText(builder ->
                    builder.matchLiteral(entry.getKey()).replacement(entry.getValue()));
        }
        recipient.sendMessage(messageComponent);
    }
}
