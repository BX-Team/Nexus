package space.bxteam.nexus.core.translation;

import com.eternalcode.multification.okaeri.MultificationSerdesPack;
import com.eternalcode.multification.translation.TranslationProvider;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import eu.okaeri.configs.ConfigManager;
import eu.okaeri.configs.serdes.commons.SerdesCommons;
import eu.okaeri.configs.yaml.bukkit.YamlBukkitConfigurer;
import eu.okaeri.configs.yaml.bukkit.serdes.SerdesBukkit;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import space.bxteam.nexus.core.multification.MultificationManager;
import space.bxteam.nexus.core.translation.implementation.ENTranslation;

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
                .orElseGet(() -> translatedMessages.getOrDefault(Language.EN, new ENTranslation()));
    }

    public void create(Language selectedLanguage) {
        DEFAULT_TRANSLATIONS.forEach((language, supplier) -> translatedMessages.putIfAbsent(language, supplier.get()));

        Path languagesDirectory = dataFolder.resolve("languages");
        if (!languagesDirectory.toFile().exists() && !languagesDirectory.toFile().mkdir()) {
            throw new RuntimeException("Failed to create languages directory");
        }

        Path languageFile = languagesDirectory.resolve(selectedLanguage.lang() + ".yml");
        ConfigManager.create(selectedLanguage.clazz().get().getClass(), (language) -> {
            language.withConfigurer(new YamlBukkitConfigurer(), new MultificationSerdesPack(multificationManager.getNoticeRegistry()));
            language.withConfigurer(new YamlBukkitConfigurer(), new SerdesBukkit(), new SerdesCommons());
            language.withBindFile(languageFile);
            language.withRemoveOrphans(true);
            language.saveDefaults();
            language.load(true);
        });
    }

    @Override
    public @NotNull Translation provide(Locale locale) {
        return getMessages(Language.fromLocale(locale));
    }
}
