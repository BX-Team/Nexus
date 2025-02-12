package org.bxteam.nexus.core.multification;

import com.eternalcode.multification.Multification;
import com.eternalcode.multification.adventure.AudienceConverter;
import com.eternalcode.multification.executor.AsyncExecutor;
import com.eternalcode.multification.notice.resolver.NoticeResolverRegistry;
import com.eternalcode.multification.platform.PlatformBroadcaster;
import com.eternalcode.multification.shared.Replacer;
import com.eternalcode.multification.translation.TranslationProvider;
import com.eternalcode.multification.viewer.ViewerProvider;
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
import org.bxteam.commons.scheduler.Scheduler;
import org.bxteam.nexus.core.placeholder.PlaceholderRegistry;
import org.bxteam.nexus.core.multification.bukkit.BukkitViewerProvider;
import org.bxteam.nexus.core.translation.Translation;

@SuppressWarnings("UnstableApiUsage")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class MultificationManager extends Multification<CommandSender, Translation> {
    private final org.bxteam.nexus.core.translation.TranslationProvider translationProvider;
    private final AudienceProvider audienceProvider;
    private final Scheduler scheduler;
    private final NoticeResolverRegistry noticeRegistry;
    private final PlaceholderRegistry placeholderRegistry;
    @Named("colorMiniMessage") private final MiniMessage miniMessage;

    @Override
    public @NotNull AsyncExecutor asyncExecutor() {
        return this.scheduler::runTaskAsynchronously;
    }

    @Override
    public @NotNull TranslationProvider<Translation> translationProvider() {
        return locale -> this.translationProvider.getCurrentTranslation();
    }

    @Override
    public @NotNull ViewerProvider<CommandSender> viewerProvider() {
        return new BukkitViewerProvider();
    }

    @Override
    public PlatformBroadcaster platformBroadcaster() {
        return PlatformBroadcaster.create(this.serializer(), this.noticeRegistry);
    }

    @Override
    public @NotNull AudienceConverter<CommandSender> audienceConverter() {
        return commandSender -> {
            if (commandSender instanceof Player player) {
                return this.audienceProvider.player(player.getUniqueId());
            }

            return this.audienceProvider.console();
        };
    }

    @Override
    public @NotNull Replacer<CommandSender> globalReplacer() {
        return (sender, text) -> this.placeholderRegistry.format(text, sender);
    }

    @Override
    public NoticeResolverRegistry getNoticeRegistry() {
        return this.noticeRegistry;
    }

    @Override
    protected @NotNull ComponentSerializer<Component, Component, String> serializer() {
        return this.miniMessage;
    }

    @Override
    public MultificationBroadcast<CommandSender, Translation, ?> create() {
        return new MultificationBroadcast<>(
                this.asyncExecutor(),
                this.translationProvider(),
                this.viewerProvider(),
                this.platformBroadcaster(),
                this.localeProvider(),
                this.audienceConverter(),
                this.globalReplacer(),
                this.noticeRegistry
        );
    }
}
