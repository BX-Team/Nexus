package gq.bxteam.nexus.utils.locale;

import gq.bxteam.nexus.Nexus;
import gq.bxteam.nexus.utils.logger.Logger;

import java.io.File;

public class LocaleConfig {
    private static final String languagesPath = "language" + File.separator;

    public static File getLangFile() {
        String configLang = Nexus.getInstance().getConfigString("language");

        String langPath = Nexus.getInstance().getDataFolder() + File.separator + languagesPath + configLang + ".yml";
        File langFile = new File(langPath);

        try {
            if (!langFile.exists()) {
                Logger.log("&cFailed to load language file. Check your config.yml file!", Logger.LogLevel.ERROR, true);
                return null;
            }
        } catch (SecurityException e) {
            return null;
        }

        return langFile;
    }
}
