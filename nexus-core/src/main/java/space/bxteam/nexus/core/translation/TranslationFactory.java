package space.bxteam.nexus.core.translation;

import com.google.inject.Inject;

import java.util.Map;

public class TranslationFactory {
    private final Map<Language, Translation> translations;

    @Inject
    public TranslationFactory(Map<Language, Translation> translations) {
        this.translations = translations;
    }

    public Translation getTranslation(Language language) {
        return translations.getOrDefault(language, translations.get(Language.EN));
    }
}
