package space.bxteam.nexus.utils.locale;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.List;

public class LocaleReader {
    private final File langFile;
    private final YamlConfiguration langConfig;

    public LocaleReader(File langFile) {
        this.langFile = langFile;
        this.langConfig = YamlConfiguration.loadConfiguration(langFile);
    }

    public String getString(String path) {
        return langConfig.getString(path);
    }

    public List<String> getStringList(String path) {
        return langConfig.getStringList(path);
    }

    public String getPrefix() {
        return langConfig.getString("prefix");
    }
}
