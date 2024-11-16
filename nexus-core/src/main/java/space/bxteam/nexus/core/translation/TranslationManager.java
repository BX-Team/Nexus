package space.bxteam.nexus.core.translation;

import com.eternalcode.multification.notice.Notice;
import com.eternalcode.multification.translation.TranslationProvider;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import de.exlll.configlib.YamlConfigurationProperties;
import de.exlll.configlib.YamlConfigurations;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import space.bxteam.nexus.core.multification.MultificationManager;
import space.bxteam.nexus.core.multification.serializer.ConfigLibNoticeSerializer;
import space.bxteam.nexus.core.translation.implementation.ENTranslation;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Supplier;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class TranslationManager implements TranslationProvider<Translation> {
    private final Map<Language, Translation> translatedMessages = new HashMap<>();
    private final MultificationManager multificationManager;
    @Named("dataFolder")
    private final Path dataFolder;

    private static final Map<Language, Supplier<Translation>> DEFAULT_TRANSLATIONS = Map.of(
            Language.EN, ENTranslation::new
    );

    public Translation getMessages(Language language) {
        if (translatedMessages.containsKey(language)) {
            return translatedMessages.get(language);
        }

        return translatedMessages.entrySet().stream()
                .filter(entry -> entry.getKey().isEquals(language))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElseGet(() -> {
                    return translatedMessages.getOrDefault(Language.EN, new ENTranslation());
                });
    }

    public void create(Language selectedLanguage) {
        DEFAULT_TRANSLATIONS.forEach((language, supplier) -> translatedMessages.putIfAbsent(language, supplier.get()));

        Path languagesDirectory = dataFolder.resolve("languages");
        if (!languagesDirectory.toFile().exists() && !languagesDirectory.toFile().mkdir()) {
            throw new RuntimeException("Failed to create languages directory");
        }

        Path languageFile = languagesDirectory.resolve(selectedLanguage.lang() + ".yml");
        YamlConfigurations.update(
                languageFile,
                selectedLanguage.clazz().get().getClass(),
                YamlConfigurationProperties.newBuilder()
                        .charset(StandardCharsets.UTF_8)
                        .addSerializer(Notice.class, new ConfigLibNoticeSerializer(multificationManager.getNoticeRegistry()))
                        .build()
        );
    }

    @Override
    public @NotNull Translation provide(Locale locale) {
        return getMessages(Language.fromLocale(locale));
    }
}
