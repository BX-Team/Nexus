package space.bxteam.nexus.core.multification.module;

import com.eternalcode.multification.notice.resolver.NoticeResolverDefaults;
import com.eternalcode.multification.notice.resolver.NoticeResolverRegistry;
import com.eternalcode.multification.notice.resolver.sound.SoundAdventureResolver;
import com.google.inject.AbstractModule;

public class MultificationModule extends AbstractModule {
    @Override
    protected void configure() {
        this.bind(NoticeResolverRegistry.class).toInstance(NoticeResolverDefaults.createRegistry().registerResolver(new SoundAdventureResolver()));
    }
}
