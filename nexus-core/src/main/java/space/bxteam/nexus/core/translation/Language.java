package space.bxteam.nexus.core.translation;

import lombok.Getter;
import space.bxteam.nexus.core.translation.implementation.ENTranslation;

import java.util.Locale;
import java.util.function.Supplier;

public enum Language {
    EN("en", ENTranslation::new);

    @Getter
    private final String lang;
    @Getter
    private final Supplier<Translation> clazz;

    Language(String lang, Supplier<Translation> clazz) {
        this.lang = lang;
        this.clazz = clazz;
    }

    public boolean isEquals(Language other) {
        return this == other;
    }

    public static Language fromLocale(Locale locale) {
        for (Language language : values()) {
            if (language.lang.equals(locale.getLanguage())) {
                return language;
            }
        }
        return EN;
    }

    public Locale toLocale() {
        return new Locale(this.lang);
    }
}
