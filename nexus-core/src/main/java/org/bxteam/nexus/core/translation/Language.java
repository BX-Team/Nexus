package org.bxteam.nexus.core.translation;

import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import org.bxteam.nexus.core.translation.implementation.ENTranslation;
import org.bxteam.nexus.core.translation.implementation.RUTranslation;

import java.util.function.Supplier;

@Getter
public enum Language {
    EN("en", ENTranslation::new),
    RU("ru", RUTranslation::new);

    private final String lang;
    private final Supplier<? extends OkaeriConfig> clazz;

    Language(String lang, Supplier<? extends OkaeriConfig> clazz) {
        this.lang = lang;
        this.clazz = clazz;
    }
}
