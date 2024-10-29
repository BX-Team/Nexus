package space.bxteam.nexus.core.translation;

import de.exlll.configlib.YamlConfigurationProperties;
import de.exlll.configlib.YamlConfigurations;
import lombok.RequiredArgsConstructor;
import space.bxteam.nexus.core.translation.implementation.ENTranslation;
import space.bxteam.nexus.core.translation.implementation.RUTranslation;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

@RequiredArgsConstructor
public class TranslationManager {
    private final Path languageFolder;

    public void initializeLanguages() {
        saveLanguageFile(ENTranslation.class, "en");
        saveLanguageFile(RUTranslation.class, "ru");
    }

    private <T extends Translation> void saveLanguageFile(Class<T> translationClass, String languageCode) {
        try {
            Path languageFile = languageFolder.resolve(languageCode + ".yml");
            if (Files.notExists(languageFolder)) {
                Files.createDirectories(languageFolder);
            }
            if (Files.notExists(languageFile)) {
                YamlConfigurations.update(languageFile, translationClass, YamlConfigurationProperties.newBuilder()
                        .charset(StandardCharsets.UTF_8)
                        .build());
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to save language file for: " + languageCode, e);
        }
    }

    public <T extends Translation> T loadTranslation(Class<T> translationClass, String languageCode) {
        Path languageFile = languageFolder.resolve(languageCode + ".yml");
        return YamlConfigurations.update(languageFile, translationClass, YamlConfigurationProperties.newBuilder()
                .charset(StandardCharsets.UTF_8)
                .build());
    }
}
