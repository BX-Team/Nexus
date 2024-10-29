package space.bxteam.nexus.core.translation.implementation;

import de.exlll.configlib.Comment;
import de.exlll.configlib.Configuration;
import lombok.Getter;
import lombok.experimental.Accessors;
import space.bxteam.nexus.core.translation.Translation;

@Configuration
@Getter
@Accessors(fluent = true)
public class RUTranslation implements Translation {
    @Override
    public String getLanguage() {
        return "ru";
    }

    @Comment({
            "Этот файл отвечает за русский язык в Nexus.",
            "",
            "Если вам нужна помощь с настройкой или у вас есть какие-либо вопросы, связанные с Nexus,",
            "присоединяйтесь к нам на нашем сервере Discord или откройте проблему на GitHub.",
            "",
            "Проблемы: https://github.com/BX-Team/Nexus/issues",
            "Discord: https://discord.gg/p7cxhw7E2M",
            "",
            "Вы можете использовать MiniMessage или же стандартные цветовые коды &7, &c и т.д.",
            "Для получения дополнительной информации о MiniMessage посетите сайт https://docs.adventure.kyori.net/minimessage/format.html",
            "Вы также можете использовать веб-генератор для создания и предварительного просмотра сообщений: https://webui.adventure.kyori.net/",
            "",
            "Этот раздел отвечает за аргументы команд, которые будут отосланы если что то не так.",
    })
    public RUArgumentSection argument = new RUArgumentSection();

    @Getter
    @Configuration
    public static class RUArgumentSection implements ArgumentSection {
        private String onlyPlayers = "<red>Только игроки могут выполнить эту команду.";
        private String noPermission = "<red>У вас нет разрешения на выполнение этой команды.";
    }
}
