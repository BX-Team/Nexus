package space.bxteam.nexus.core.translation;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import space.bxteam.nexus.core.configuration.PluginConfigurationProvider;
import space.bxteam.nexus.core.translation.implementation.ENTranslation;
import space.bxteam.nexus.core.translation.implementation.RUTranslation;

import java.nio.file.Path;

@RequiredArgsConstructor
public class TranslationModule extends AbstractModule {
    private final PluginConfigurationProvider configurationProvider;
    private final Path languageFolder;

    @Provides
    @Singleton
    Translation provideTranslation(TranslationManager translationManager) {
        translationManager.initializeLanguages();

        String languageCode = configurationProvider.configuration().language();
        if (languageCode.equals("en")) {
            return translationManager.loadTranslation(ENTranslation.class, languageCode);
        } else if (languageCode.equals("ru")) {
            return translationManager.loadTranslation(RUTranslation.class, languageCode);
        } else {
            throw new RuntimeException("Unsupported language: " + languageCode);
        }
    }

    @Provides
    @Singleton
    TranslationManager provideLanguageFileManager() {
        return new TranslationManager(languageFolder);
    }
}
