package space.bxteam.nexus.core.multification;

import com.eternalcode.multification.adventure.AudienceConverter;
import com.eternalcode.multification.executor.AsyncExecutor;
import com.eternalcode.multification.locate.LocaleProvider;
import com.eternalcode.multification.notice.Notice;
import com.eternalcode.multification.notice.NoticeBroadcastImpl;
import com.eternalcode.multification.notice.resolver.NoticeResolverRegistry;
import com.eternalcode.multification.platform.PlatformBroadcaster;
import com.eternalcode.multification.shared.Replacer;
import com.eternalcode.multification.translation.TranslationProvider;
import com.eternalcode.multification.viewer.ViewerProvider;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.function.Function;

@SuppressWarnings("UnstableApiUsage")
public class MultificationBroadcast<CommandSender, Translation, B extends MultificationBroadcast<CommandSender, Translation, B>> extends NoticeBroadcastImpl<CommandSender, Translation, B> {
    public MultificationBroadcast(
            AsyncExecutor asyncExecutor,
            TranslationProvider<Translation> translationProvider,
            ViewerProvider<CommandSender> viewerProvider,
            PlatformBroadcaster platformBroadcaster,
            LocaleProvider<CommandSender> localeProvider,
            AudienceConverter<CommandSender> audienceConverter,
            Replacer<CommandSender> replacer,
            NoticeResolverRegistry noticeRegistry
    ) {
        super(asyncExecutor, translationProvider, viewerProvider, platformBroadcaster, localeProvider, audienceConverter, replacer, noticeRegistry);
    }

    public B sender(CommandSender sender) {
        if (sender instanceof Player player) {
            return this.player(player.getUniqueId());
        }

        return this.console();
    }

    public B messages(Function<Translation, List<String>> messages) {
        this.notifications.add(translation -> {
            List<String> list = messages.apply(translation);
            return Notice.chat(list);
        });

        return this.getThis();
    }
}
