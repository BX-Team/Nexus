package space.bxteam.nexus.core.configuration.serializer;

import de.exlll.configlib.Serializer;
import space.bxteam.nexus.core.translation.Language;

public class LanguageSerializer implements Serializer<Language, String> {
    @Override
    public String serialize(Language language) {
        return language.lang();
    }

    @Override
    public Language deserialize(String value) {
        for (Language language : Language.values()) {
            if (language.lang().equals(value)) {
                return language;
            }
        }
        return Language.EN;
    }
}
