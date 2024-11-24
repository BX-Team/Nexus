package space.bxteam.nexus.core.translation;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import lombok.Getter;
import space.bxteam.nexus.core.configuration.ConfigurationManager;
import space.bxteam.nexus.core.configuration.plugin.PluginConfigurationProvider;
import space.bxteam.nexus.core.utils.Logger;

import java.io.File;
import java.nio.file.Path;
import java.util.EnumMap;
import java.util.Map;

@Singleton
public class TranslationProvider {
    private final Map<Language, Translation> translations = new EnumMap<>(Language.class);
    @Getter
    private volatile Translation translation;

    private final Path dataFolder;
    private final ConfigurationManager configurationManager;

    @Inject
    public TranslationProvider(@Named("dataFolder") Path dataFolder, PluginConfigurationProvider configurationProvider, ConfigurationManager configurationManager) {
        this.dataFolder = dataFolder;
        this.configurationManager = configurationManager;

        this.createTranslationFiles();
        this.setTranslation(configurationProvider.configuration().language());
    }

    private void createTranslationFiles() {
        for (Language language : Language.values()) {
            File translationFile = this.dataFolder.resolve("lang").resolve(language.lang() + ".yml").toFile();

            try {
                Object config = configurationManager.create(language.clazz().get().getClass(), translationFile);

                if (config instanceof Translation translation) {
                    translations.put(language, translation);
                }
            } catch (Exception e) {
                Logger.log("Could not create translation file for " + language.lang() + ".yml", Logger.LogLevel.ERROR);
                e.printStackTrace();
            }
        }
    }

    public void setTranslation(Language language) {
        Translation newTranslation = translations.get(language);
        if (newTranslation != null) {
            this.translation = newTranslation;
        }
    }

    public Translation getMessages(Language language) {
        return translations.getOrDefault(language, this.translation);
    }

    public Translation getCurrentTranslation() {
        return this.translation;
    }
}
