package space.bxteam.nexus.core.feature.privatechat;

import com.eternalcode.multification.shared.Formatter;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import space.bxteam.nexus.core.event.EventCaller;
import space.bxteam.nexus.core.multification.MultificationFormatter;
import space.bxteam.nexus.core.multification.MultificationManager;
import space.bxteam.nexus.feature.ignore.IgnoreService;
import space.bxteam.nexus.feature.privatechat.PrivateChatService;
import space.bxteam.nexus.feature.privatechat.event.PrivateChatEvent;

import java.time.Duration;
import java.util.*;

@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class PrivateChatServiceImpl implements PrivateChatService {
    private final MultificationManager multificationManager;
    private final IgnoreService ignoreService;
    private final EventCaller eventCaller;

    private final Cache<UUID, UUID> replies = CacheBuilder.newBuilder()
            .expireAfterWrite(Duration.ofHours(1))
            .build();

    private final Set<UUID> socialSpy = new HashSet<>();

    private static final MultificationFormatter<MessageContent> MESSAGE_CONTENT = MultificationFormatter.<MessageContent>builder()
            .with("{MESSAGE}", MessageContent::message)
            .with("{TARGET}", privateMessage -> privateMessage.target().getName())
            .with("{SENDER}", privateMessage -> privateMessage.sender().getName())
            .build();

    @Override
    public void sendMessage(Player sender, Player receiver, String message) {
        if (!receiver.isOnline()) {
            this.multificationManager.player(sender.getUniqueId(), translation -> translation.argument().offlinePlayer());
            return;
        }

        this.ignoreService.isIgnored(receiver.getUniqueId(), sender.getUniqueId()).thenAccept(ignored -> {
            if (!ignored) {
                this.replies.put(sender.getUniqueId(), receiver.getUniqueId());
                this.replies.put(receiver.getUniqueId(), sender.getUniqueId());
            }

            PrivateChatEvent event = new PrivateChatEvent(sender.getUniqueId(), receiver.getUniqueId(), message);
            this.eventCaller.callEvent(event);
            this.sendPrivateMessage(new MessageContent(sender, receiver, event.getContent(), this.socialSpy, ignored));
        });
    }

    @Override
    public void replyMessage(Player sender, String message) {
        UUID receiverUuid = this.replies.getIfPresent(sender.getUniqueId());

        if (receiverUuid == null) {
            this.multificationManager.player(sender.getUniqueId(), translation -> translation.privateChat().noReply());
            return;
        }

        Player receiver = sender.getServer().getPlayer(receiverUuid);

        if (!receiver.isOnline()) {
            this.multificationManager.player(sender.getUniqueId(), translation -> translation.argument().offlinePlayer());
            return;
        }

        this.sendMessage(sender, receiver, message);
    }

    @Override
    public void enableSpy(UUID player) {
        this.socialSpy.add(player);
    }

    @Override
    public void disableSpy(UUID player) {
        this.socialSpy.remove(player);
    }

    @Override
    public boolean isSpyEnabled(UUID player) {
        return this.socialSpy.contains(player);
    }

    private void sendPrivateMessage(MessageContent messageContent) {
        Formatter formatter = MESSAGE_CONTENT.toFormatter(messageContent);
        UUID sender = messageContent.sender().getUniqueId();
        UUID target = messageContent.target().getUniqueId();

        if (!messageContent.isIgnored()) {
            this.multificationManager.player(target, translation -> translation.privateChat().targetToYouMessage(), formatter);
        }

        this.multificationManager.player(sender, translation -> translation.privateChat().youToTargetMessage(), formatter);
        this.multificationManager.players(messageContent.socialSpy(), translation -> translation.privateChat().socialSpyMessage(), formatter);
        this.multificationManager.console(translation -> translation.privateChat().socialSpyMessage(), formatter);
    }

    private record MessageContent(Player sender, Player target, String message, Collection<UUID> socialSpy, boolean isIgnored) { }
}
