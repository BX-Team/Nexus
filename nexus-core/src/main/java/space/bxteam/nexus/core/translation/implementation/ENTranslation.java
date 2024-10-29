package space.bxteam.nexus.core.translation.implementation;

import de.exlll.configlib.Comment;
import de.exlll.configlib.Configuration;
import lombok.Getter;
import lombok.experimental.Accessors;
import space.bxteam.nexus.core.translation.Translation;

@Configuration
@Getter
@Accessors(fluent = true)
public class ENTranslation implements Translation {
    @Override
    public String getLanguage() {
        return "en";
    }

    @Comment({
            "This file is responsible for the English translation in the Nexus plugin.",
            "",
            "If you need help with setup or have any questions related to Nexus,",
            "join us in our Discord server or open an issue on GitHub.",
            "",
            "Issues: https://github.com/BX-Team/Nexus/issues",
            "Discord: https://discord.gg/p7cxhw7E2M",
            "",
            "You can use MiniMessages formatting everywhere, or standard &7, &c color codes.",
            "For more information about MiniMessage, visit https://docs.adventure.kyori.net/minimessage/format.html",
            "You also can use the web generator to generate and preview messages: https://webui.adventure.kyori.net/",
            "",
            "This section is responsible for the arguments of the commands."
    })
    private ENArgumentSection argument = new ENArgumentSection();

    @Getter
    @Configuration
    public static class ENArgumentSection implements ArgumentSection {
        private String onlyPlayers = "<red>Only players can execute this command.";
        private String noPermission = "<red>You do not have permission to execute this command.";
    }
}
