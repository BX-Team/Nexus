package space.bxteam.nexus.core.translation;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.MapBinder;
import space.bxteam.nexus.core.translation.implementation.ENTranslation;

public class TranslationModule extends AbstractModule {
    @Override
    protected void configure() {
        this.bind(TranslationManager.class).asEagerSingleton();

        MapBinder<Language, Translation> mapBinder = MapBinder.newMapBinder(binder(), Language.class, Translation.class);

        mapBinder.addBinding(Language.EN).to(ENTranslation.class);
    }
}
