package space.bxteam.nexus.core.translation.implementation;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;
import net.kyori.adventure.key.Key;
import space.bxteam.nexus.core.translation.Language;
import space.bxteam.nexus.core.translation.Translation;

import java.util.List;

@Getter
@Accessors(fluent = true)
@SuppressWarnings({"FieldMayBeFinal", "InnerClassMayBeStatic"})
public class RUTranslation extends OkaeriConfig implements Translation {
    @Override
    public Language language() {
        return Language.RU;
    }

    @Comment({
            "Этот файл отвечает за русский перевод в плагине Nexus.",
            "",
            "Если вам нужна помощь с настройкой или у вас есть вопросы, связанные с Nexus,",
            "присоединяйтесь к нам на нашем сервере Discord или откройте issue на GitHub.",
            "",
            "Issues: https://github.com/BX-Team/Nexus/issues",
            "Discord: https://discord.gg/p7cxhw7E2M",
            "",
            "Вы можете использовать форматирование MiniMessage или стандартные цветовые коды &7, &c.",
            "Для получения дополнительной информации о MiniMessage, посетите https://docs.adventure.kyori.net/minimessage/format.html",
            "Также вы можете использовать веб-генератор для создания и предварительного просмотра сообщений: https://webui.adventure.kyori.net/",
            "",
            "Этот раздел отвечает за аргументы команд."
    })
    public RUArgumentSection argument = new RUArgumentSection();

    @Getter
    public class RUArgumentSection extends OkaeriConfig implements ArgumentSection {
        public Notice onlyPlayers = Notice.chat("<dark_red>Только игроки могут выполнять эту команду.");
        public Notice noItem = Notice.chat("<dark_red>Вам нужен предмет для использования этой команды!");

        @Comment({"", "{PERMISSIONS} - Необходимые разрешения"})
        public Notice noPermission = Notice.chat("<dark_red>У вас нет разрешения на выполнение этой команды! <red>({PERMISSIONS})");

        @Comment({"", "{USAGE} - Правильное использование команды"})
        public Notice usageMessage = Notice.chat("<green>Правильное использование: <white>{USAGE}");
        public Notice usageMessageHead = Notice.chat("<green>Правильное использование:");
        public Notice usageMessageEntry = Notice.chat("<gray> - <white>{USAGE}");
        public Notice numberBiggerThanOrEqualZero = Notice.chat("<dark_red>Число должно быть больше или равно 0!");
        public Notice noDamaged = Notice.chat("<dark_red>Этот предмет нельзя починить!");
        public Notice noDamagedItems = Notice.chat("<dark_red>В вашем инвентаре нет поврежденных предметов!");
        public Notice noEnchantment = Notice.chat("<dark_red>Такого зачарования не существует!");
        public Notice noValidEnchantmentLevel = Notice.chat("<dark_red>Недопустимый уровень зачарования!");
        public Notice giveNoItem = Notice.chat("<dark_red>Этот предмет недоступен!");
    }

    @Comment({"", "Этот ответ отвечает за общее форматирование некоторых значений"})
    public RUFormatSection format = new RUFormatSection();

    @Getter
    public class RUFormatSection extends OkaeriConfig implements Format {
        public String enable = "<green>включено";
        public String disable = "<red>отключено";
    }

    @Comment({"", "Этот раздел отвечает за взаимодействие с игроками."})
    public RUPlayerSection player = new RUPlayerSection();

    @Getter
    public class RUPlayerSection extends OkaeriConfig implements PlayerSection {
        public Notice feedMessage = Notice.chat("<green>Вы насытились!");
        public Notice feedMessageBy = Notice.chat("<green>Вы накормили игрока <white>{PLAYER}");

        public Notice healMessage = Notice.chat("<green>Вы исцелены!");
        public Notice healMessageBy = Notice.chat("<green>Вы исцелили игрока <white>{PLAYER}");

        public Notice killedMessage = Notice.chat("<dark_red>Убит <red>{PLAYER}");

        @Comment({"", "{STATE} - Статус полета"})
        public Notice flyEnable = Notice.chat("<green>Полет теперь <white>{STATE}");
        public Notice flyDisable = Notice.chat("<green>Полет теперь <white>{STATE}");

        @Comment({"", "{PLAYER} - Целевой игрок, {STATE} - Статус полета целевого игрока"})
        public Notice flySetEnable = Notice.chat("<green>Полет для <white>{PLAYER} <green>теперь <white>{STATE}");
        public Notice flySetDisable = Notice.chat("<green>Полет для <white>{PLAYER} <green>теперь <white>{STATE}");

        @Comment({"", "{STATE} - Статус режима бога"})
        public Notice godEnable = Notice.chat("<green>Режим бога теперь <white>{STATE}");
        public Notice godDisable = Notice.chat("<green>Режим бога теперь <white>{STATE}");

        @Comment({"", "{PLAYER} - Целевой игрок, {STATE} - Статус режима бога целевого игрока"})
        public Notice godSetEnable = Notice.chat("<green>Режим бога для <white>{PLAYER} <green>теперь <white>{STATE}");
        public Notice godSetDisable = Notice.chat("<green>Режим бога для <white>{PLAYER} <green>теперь <white>{STATE}");

        @Comment({"", "{ONLINE} - Количество игроков онлайн"})
        public Notice onlinePlayersCountMessage = Notice.chat("<green>Игроков онлайн: <white>{ONLINE}");

        @Comment({"", "{ONLINE} - Текущие игроки онлайн, {PLAYERS} - Список игроков"})
        public Notice onlinePlayersMessage = Notice.chat("<green>Игроков онлайн: <white>{ONLINE} <green>Игроки: <white>{PLAYERS}");

        @Comment({"", "{PING} - Пинг игрока"})
        public Notice pingMessage = Notice.chat("<green>Ваш пинг: <white>{PING}мс");

        @Comment({"", "{PLAYER} - Целевой игрок, {PING} - Пинг целевого игрока"})
        public Notice pingOtherMessage = Notice.chat("<green>Пинг игрока <white>{PLAYER} <green>составляет <white>{PING}мс");

        @Comment({
                "",
                "{PLAYER} - Имя целевого игрока",
                "{UUID} - UUID целевого игрока",
                "{IP} - IP-адрес целевого игрока",
                "{GAMEMODE} - Режим игры целевого игрока",
                "{PING} - Пинг целевого игрока",
                "{HEALTH} - Здоровье целевого игрока",
                "{LEVEL} - Уровень целевого игрока",
                "{FOOD_LEVEL} - Уровень сытости целевого игрока"
        })
        public List<String> whoisCommand = List.of(
                "<green>Имя цели: <white>{PLAYER}",
                "<green>UUID цели: <white>{UUID}",
                "<green>IP-адрес цели: <white>{IP}",
                "<green>Режим игры цели: <white>{GAMEMODE}",
                "<green>Пинг цели: <white>{PING}мс",
                "<green>Здоровье цели: <white>{HEALTH}",
                "<green>Уровень цели: <white>{LEVEL}",
                "<green>Уровень сытости цели: <white>{FOOD}"
        );

        @Comment("")
        public Notice speedBetweenZeroAndTen = Notice.chat("<dark_red>Скорость должна быть между 0 и 10!");
        public Notice speedTypeNotCorrect = Notice.chat("<dark_red>Неверный тип скорости!");

        @Comment("{SPEED} - Значение скорости ходьбы или полета")
        public Notice speedWalkSet = Notice.chat("<green>Скорость ходьбы установлена на <white>{SPEED}");
        public Notice speedFlySet = Notice.chat("<green>Скорость полета установлена на <white>{SPEED}");

        @Comment("{PLAYER} - Целевой игрок, {SPEED} - Значение скорости ходьбы или полета целевого игрока")
        public Notice speedWalkSetBy = Notice.chat("<green>Скорость ходьбы для <white>{PLAYER} <green>установлена на <white>{SPEED}");
        public Notice speedFlySetBy = Notice.chat("<green>Скорость полета для <white>{PLAYER} <green>установлена на <white>{SPEED}");

        @Comment("")
        public Notice gameModeNotCorrect = Notice.chat("<dark_red>Недопустимый тип режима игры");
        @Comment("GAMEMODE} - Название режима игры")
        public Notice gameModeMessage = Notice.chat("<green>Режим игры установлен на <white>{GAMEMODE}");
        @Comment("{PLAYER} - Целевой игрок, {GAMEMODE} - Название режима игры")
        public Notice gameModeSetMessage = Notice.chat("<green>Режим игры изменен на <white>{GAMEMODE}<green> для игрока <white>{PLAYER}");

        @Comment("")
        private List<String> fullServerSlots = List.of(
                "<dark_red>Сервер полон!",
                "<dark_red>Попробуйте позже!"
        );
    }

    @Comment({"", "Этот раздел отвечает за сообщения, связанные с инвентарем."})
    public RUInventorySection inventory = new RUInventorySection();

    @Getter
    public class RUInventorySection extends OkaeriConfig implements InventorySection {
        public Notice inventoryClearMessage = Notice.chat("<green>Ваш инвентарь был очищен!");
        public Notice inventoryClearMessageBy = Notice.chat("<green>Инвентарь игрока <white>{PLAYER} <green>был очищен!");
    }

    @Comment({"", "Этот раздел отвечает за сообщения, связанные с командой sudo."})
    public RUSudoSection sudo = new RUSudoSection();

    @Getter
    public class RUSudoSection extends OkaeriConfig implements SudoSection {
        @Comment("{PLAYER} - Игрок, выполнивший команду, {COMMAND} - Команда, которую выполнил игрок")
        public Notice sudoMessageSpy = Notice.chat("<gray>[SUDO] <white>{PLAYER} <gray>выполнил команду: <white>{COMMAND}");
        public Notice sudoMessage = Notice.chat("<green>Вы выполнили команду: <white>{COMMAND} <green>на игроке <white>{PLAYER}");
    }

    @Comment({"", "Этот раздел отвечает за сообщения, связанные с временем и погодой."})
    public RUTimeAndWeatherSection timeAndWeather = new RUTimeAndWeatherSection();

    @Getter
    public class RUTimeAndWeatherSection extends OkaeriConfig implements TimeAndWeatherSection {
        @Comment("{TIME} - Измененное время в тиках")
        public Notice timeSet = Notice.chat("<green>Время установлено на <white>{TIME}");
        public Notice timeAdd = Notice.chat("<green>Время добавлено на <white>{TIME}");

        @Comment("{WORLD} - Название мира")
        public Notice weatherSetRain = Notice.chat("<green>Погода установлена на дождь в мире <white>{WORLD}");
        public Notice weatherSetSun = Notice.chat("<green>Погода установлена на солнечную в мире <white>{WORLD}");
        public Notice weatherSetThunder = Notice.chat("<green>Погода установлена на грозу в мире <white>{WORLD}");
    }

    @Comment({"", "Этот раздел отвечает за сообщения, связанные с предметами."})
    public RUItemSection item = new RUItemSection();

    @Getter
    public class RUItemSection extends OkaeriConfig implements ItemSection {
        @Comment("{ITEM_NAME} - Новое имя предмета")
        public Notice itemChangeNameMessage = Notice.chat("<green>Имя предмета изменено на: <white>{ITEM_NAME}");
        public Notice itemClearNameMessage = Notice.chat("<green>Имя предмета очищено!");

        @Comment({"", "{ITEM_LORE} - Новое описание предмета"})
        public Notice itemChangeLoreMessage = Notice.chat("<green>Описание предмета изменено на: <white>{ITEM_LORE}");
        public Notice itemClearLoreMessage = Notice.chat("<green>Описание предмета очищено!");

        @Comment("")
        public Notice repairMessage = Notice.chat("<green>Предмет был починен!");
        public Notice repairAllMessage = Notice.chat("<green>Все предметы были починены!");

        @Comment("")
        public Notice incorrectItem = Notice.chat("<dark_red>Это недопустимый предмет!");

        @Comment("")
        public Notice enchantedMessage = Notice.chat("<green>Предмет был зачарован!");
        public Notice enchantedMessageFor = Notice.chat("<green>Предмет в руке игрока <white>{PLAYER} <green>был зачарован!");
        public Notice enchantedMessageBy = Notice.chat("<green>Ваш предмет в руке был зачарован игроком <white>{PLAYER}");

        @Comment({"", "{ITEM} - Название полученного предмета"})
        public Notice giveReceived = Notice.chat("<green>Вы получили предмет <white>{ITEM}");

        @Comment({"", "{PLAYER} - Имя получателя предмета, {ITEM} - предмет"})
        public Notice giveGiven = Notice.chat("<green>Вы дали предмет <white>{ITEM} <green>игроку <white>{PLAYER}");
    }

    @Comment({"", "Этот раздел отвечает за сообщения, связанные с телепортацией."})
    public RUWarpSection warp = new RUWarpSection();

    @Getter
    public class RUWarpSection extends OkaeriConfig implements WarpSection {
        @Comment("{WARP} - Название точки телепортации")
        public Notice create = Notice.chat("<green>Точка телепортации <white>{WARP} <green>создана.");
        public Notice remove = Notice.chat("<green>Точка телепортации <white>{WARP} <green>удалена.");

        @Comment("")
        public Notice warpAlreadyExists = Notice.chat("<dark_red>Точка телепортации <white>{WARP} <dark_red>уже существует!");
        public Notice noWarps = Notice.chat("<dark_red>Нет точек телепортации!");
        public Notice notExist = Notice.chat("<dark_red>Эта точка телепортации не существует");
    }

    @Comment({"", "Этот раздел отвечает за сообщения, связанные с домами."})
    public RUHomeSection home = new RUHomeSection();

    @Getter
    public class RUHomeSection extends OkaeriConfig implements HomeSection {
        @Comment("{HOMES} - Список домов (через запятую)")
        public Notice homeList = Notice.chat("<green>Доступные дома: <white>{HOMES}");

        @Comment({"", "{HOME} - Название дома"})
        public Notice create = Notice.chat("<green>Дом <white>{HOME} <green>создан.");
        public Notice delete = Notice.chat("<green>Дом <white>{HOME} <green>удален.");
        public Notice homeAlreadyExists = Notice.chat("<dark_red>Дом с именем <white>{HOME} <dark_red>уже существует!");

        @Comment({"", "{LIMIT} - Лимит домов"})
        public Notice limit = Notice.chat("<dark_red>Вы достигли лимита домов! <red>({LIMIT})");
        public Notice noHomes = Notice.chat("<dark_red>У вас нет домов!");
    }

    @Comment({"", "Этот раздел отвечает за сообщения, связанные с телепортацией."})
    public RUTeleportSection teleport = new RUTeleportSection();

    @Getter
    public class RUTeleportSection extends OkaeriConfig implements TeleportSection {
        @Comment("{PLAYER} - Игрок, который будет телепортирован")
        public Notice teleportedToPlayer = Notice.chat("<green>Вы были телепортированы к игроку <white>{PLAYER}");
        @Comment("{PLAYER} - Игрок, который будет телепортирован, {SENDER} - Игрок, выполнивший команду")
        public Notice teleportedPlayerToPlayer = Notice.chat("<green>Вы телепортировали игрока <white>{PLAYER} <green>к <white>{SENDER}");

        @Comment({"", "{Y} - Высота по Y"})
        public Notice teleportedToHighestBlock = Notice.chat("<green>Вы были телепортированы на самый высокий блок! (Y: {Y})");

        @Comment({"", "{X} - Координата X, {Y} - Координата Y, {Z} - Координата Z"})
        public Notice teleportedToCoordinates = Notice.chat("<green>Вы были телепортированы на координаты: <white>X: {X}, Y: {Y}, Z: {Z}");
        @Comment("{PLAYER} - Игрок, который будет телепортирован, {X} - Координата X, {Y} - Координата Y, {Z} - Координата Z")
        public Notice teleportedSpecifiedPlayerToCoordinates = Notice.chat("<green>Вы телепортировали игрока <white>{PLAYER} <green>на координаты: <white>X: {X}, Y: {Y}, Z: {Z}");

        @Comment("")
        public Notice teleportedToLastLocation = Notice.chat("<green>Вы были телепортированы на ваше последнее местоположение!");
        @Comment("{PLAYER} - Игрок, который будет телепортирован")
        public Notice teleportedSpecifiedPlayerLastLocation = Notice.chat("<green>Вы телепортировали игрока <white>{PLAYER} <green>на его последнее местоположение!");
        public Notice lastLocationNoExist = Notice.chat("<dark_red>Последнее местоположение не существует!");
    }

    @Comment({"", "This section is responsible for the random teleport-related messages."})
    public RURandomTeleportSection randomTeleport = new RURandomTeleportSection();

    @Getter
    public class RURandomTeleportSection extends OkaeriConfig implements RandomTeleportSection {
        public Notice randomTeleportSearchStart = Notice.chat("<green>Поиск случайных коордиант начат...");
        public Notice randomTeleportSearchFailed = Notice.chat("<dark_red>Не удалось найти случайное местоположение! Попробуйте еще раз.");

        @Comment("")
        public Notice randomTeleportTeleported = Notice.chat("<green>Вы были телепортированы на случайное координаты!");
        @Comment("{PLAYER} - Игрок который будет телепортирован")
        public Notice teleportedSpecifiedPlayerToRandomLocation = Notice.chat("<green>Вы телепортировали игрока <white>{PLAYER} <green>на случайные координаты!");
    }

    @Comment({"", "Этот раздел отвечает за сообщения, связанные с чатом."})
    public RUChatSection chat = new RUChatSection();

    @Getter
    public class RUChatSection extends OkaeriConfig implements ChatSection {
        @Comment("{PLAYER} - Игрок, выполнивший команду")
        public Notice enabled = Notice.chat("<green>Чат включен игроком <white>{PLAYER}");
        public Notice disabled = Notice.chat("<dark_red>Чат отключен игроком <white>{PLAYER}");
        public Notice cleared = Notice.chat("<green>Чат очищен игроком <white>{PLAYER}");

        @Comment("")
        public Notice alreadyDisabled = Notice.chat("<dark_red>Чат уже отключен!");
        public Notice alreadyEnabled = Notice.chat("<dark_red>Чат уже включен!");

        @Comment("")
        public Notice chatDisabled = Notice.chat("<dark_red>Чат отключен!");

        @Comment("{TIME} - Оствшиеся время в секундах")
        public Notice slowMode = Notice.chat("<dark_red>Вы сможете написать следующее сообщение через <white>{TIME} секунд");

        @Comment({"", "{MESSAGE} - Broadcast content"})
        public Notice broadcastMessage = Notice.chat("<red>[Объявление] <white>{MESSAGE}");
    }

    @Comment({"", "Этот раздел отвечает за веселые команды :)"})
    public RUFunSection fun = new RUFunSection();

    @Getter
    public class RUFunSection extends OkaeriConfig implements FunSection {
        public Notice spitSound = Notice.sound(Key.key("entity.llama.spit"), 1.0f, 1.0f);
    }
}
