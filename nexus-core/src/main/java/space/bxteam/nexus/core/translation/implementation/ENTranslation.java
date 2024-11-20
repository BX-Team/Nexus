package space.bxteam.nexus.core.translation.implementation;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;
import space.bxteam.nexus.core.translation.Translation;

import java.util.List;

@Getter
@Accessors(fluent = true)
@SuppressWarnings("FieldMayBeFinal")
public class ENTranslation extends OkaeriConfig implements Translation {
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
    public static class ENArgumentSection extends OkaeriConfig implements ArgumentSection {
        private Notice onlyPlayers = Notice.chat("<dark_red>Only players can execute this command.");
        private Notice noItem = Notice.chat("<dark_red>You need item to use this command!");

        @Comment({"", "{PERMISSIONS} - Required permissions"})
        private Notice noPermission = Notice.chat("<dark_red>You don't have permission to execute this command! <red>({PERMISSIONS})");

        @Comment({"", "{USAGE} - Correct usage of the command"})
        private Notice usageMessage = Notice.chat("<green>Correct usage: <white>{USAGE}");
        private Notice usageMessageHead = Notice.chat("<green>Correct usage:");
        private Notice usageMessageEntry = Notice.chat("<gray> - <white>{USAGE}");
        private Notice numberBiggerThanOrEqualZero = Notice.chat("<dark_red>Number must be bigger than or equal to 0!");
        private Notice noDamaged = Notice.chat("<dark_red>This item can't be repaired!");
        private Notice noDamagedItems = Notice.chat("<dark_red>There are no damaged items in your inventory!");
        private Notice noEnchantment = Notice.chat("<dark_red>This enchantment doesn't exist!");
        private Notice noValidEnchantmentLevel = Notice.chat("<dark_red>Not a valid enchantment level!");
        private Notice giveNoItem = Notice.chat("<dark_red>This item is not obtainable!");
    }

    @Comment({"", "This answer is responsible for the general formatting of some values"})
    public ENFormatSection format = new ENFormatSection();

    @Getter
    public static class ENFormatSection extends OkaeriConfig implements Format {
        private String enable = "<green>enabled";
        private String disable = "<red>disabled";
    }

    @Comment({"", "This section is responsible for player-related stuff and interactions with them."})
    private ENPlayerSection player = new ENPlayerSection();

    @Getter
    public static class ENPlayerSection extends OkaeriConfig implements PlayerSection {
        private Notice feedMessage = Notice.chat("<green>You have been fed!");
        private Notice feedMessageBy = Notice.chat("<green>You've fed the player <white>{PLAYER}");

        private Notice healMessage = Notice.chat("<green>You have been healed!");
        private Notice healMessageBy = Notice.chat("<green>You've healed the player <white>{PLAYER}");

        private Notice killedMessage = Notice.chat("<dark_red>Killed <red>{PLAYER}");

        @Comment({"", "{STATE} - Fly status"})
        private Notice flyEnable = Notice.chat("<green>Fly is now <white>{STATE}");
        private Notice flyDisable = Notice.chat("<green>Fly is now <white>{STATE}");

        @Comment({"", "{PLAYER} - Target player, {STATE} - Target player fly status"})
        private Notice flySetEnable = Notice.chat("<green>Fly for <white>{PLAYER} <green>is now <white>{STATE}");
        private Notice flySetDisable = Notice.chat("<green>Fly for <white>{PLAYER} <green>is now <white>{STATE}");

        @Comment({"", "{STATE} - Godmode status"})
        private Notice godEnable = Notice.chat("<green>God mode is now <white>{STATE}");
        private Notice godDisable = Notice.chat("<green>God mode is now <white>{STATE}");

        @Comment({"", "{PLAYER} - Target player, {STATE} - Target player godmode status"})
        private Notice godSetEnable = Notice.chat("<green>God mode for <white>{PLAYER} <green>is now <white>{STATE}");
        private Notice godSetDisable = Notice.chat("<green>God mode for <white>{PLAYER} <green>is now <white>{STATE}");

        @Comment({"", "{ONLINE} - Online players count"})
        private Notice onlinePlayersCountMessage = Notice.chat("<green>Online players: <white>{ONLINE}");

        @Comment({"", "{ONLINE} - Current online players, {PLAYERS} - Player list"})
        private Notice onlinePlayersMessage = Notice.chat("<green>Online players: <white>{ONLINE} <green>Players: <white>{PLAYERS}");

        @Comment({"", "{PING} - Player ping"})
        private Notice pingMessage = Notice.chat("<green>Your ping: <white>{PING}ms");

        @Comment({"", "{PLAYER} - Target player, {PING} - Target player ping"})
        private Notice pingOtherMessage = Notice.chat("<green>Ping of the player <white>{PLAYER} <green>is <white>{PING}ms");

        @Comment({
                "",
                "{PLAYER} - Target player name",
                "{UUID} - Target player UUID",
                "{IP} - Target player IP",
                "{GAMEMODE} - Target player game mode",
                "{PING} - Target player ping",
                "{HEALTH} - Target player health",
                "{LEVEL} - Target player level",
                "{FOOD_LEVEL} - Target player food level"
        })
        private List<String> whoisCommand = List.of(
                "<green>Target name: <white>{PLAYER}",
                "<green>Target UUID: <white>{UUID}",
                "<green>Target IP: <white>{IP}",
                "<green>Target game mode: <white>{GAMEMODE}",
                "<green>Target ping: <white>{PING}ms",
                "<green>Target health: <white>{HEALTH}",
                "<green>Target level: <white>{LEVEL}",
                "<green>Target food level: <white>{FOOD}"
        );

        @Comment("")
        private Notice speedBetweenZeroAndTen = Notice.chat("<dark_red>Speed must be between 0 and 10!");
        private Notice speedTypeNotCorrect = Notice.chat("<dark_red>Invalid speed type!");

        @Comment("{SPEED} - Walk or fly speed value")
        private Notice speedWalkSet = Notice.chat("<green>Walking speed is set to <white>{SPEED}");
        private Notice speedFlySet = Notice.chat("<green>Flying speed is set to <white>{SPEED}");

        @Comment("{PLAYER} - Target player, {SPEED} - Target player walk or fly speed value")
        private Notice speedWalkSetBy = Notice.chat("<green>Walking speed for <white>{PLAYER} <green>is set to <white>{SPEED}");
        private Notice speedFlySetBy = Notice.chat("<green>Flying speed for <green>{PLAYER} <green>is set to <white>{SPEED}");

        @Comment("")
        private Notice gameModeNotCorrect = Notice.chat("<dark_red>Not a valid gamemode type");
        @Comment("GAMEMODE} - Gamemode name")
        private Notice gameModeMessage = Notice.chat("<green>Gamemode is now set to <white>{GAMEMODE}");
        @Comment("{PLAYER} - Target player, {GAMEMODE} - Gamemode name")
        private Notice gameModeSetMessage = Notice.chat("<green>Changed gamemode to <white>{GAMEMODE}<green> for player <white>{PLAYER}");
    }

    @Comment({"", "This section is responsible for the inventory-related messages."})
    private ENInventorySection inventory = new ENInventorySection();

    @Getter
    public static class ENInventorySection extends OkaeriConfig implements InventorySection {
        private Notice inventoryClearMessage = Notice.chat("<green>Your inventory has been cleared!");
        private Notice inventoryClearMessageBy = Notice.chat("<green>The inventory of the player <white>{PLAYER} <green>has been cleared!");
    }

    @Comment({"", "This section is responsible for the sudo command messages."})
    private ENSudoSection sudo = new ENSudoSection();

    @Getter
    public static class ENSudoSection extends OkaeriConfig implements SudoSection {
        @Comment("{PLAYER} - Player who executed the command, {COMMAND} - Command that the player executed")
        private Notice sudoMessageSpy = Notice.chat("<gray>[SUDO] <white>{PLAYER} <gray>executed command: <white>{COMMAND}");
        private Notice sudoMessage = Notice.chat("<green>You have executed the command: <white>{COMMAND} <green>on player <white>{PLAYER}");
    }

    @Comment({"", "This section is responsible for the time and weather-related messages."})
    private ENTimeAndWeatherSection timeAndWeather = new ENTimeAndWeatherSection();

    @Getter
    public static class ENTimeAndWeatherSection extends OkaeriConfig implements TimeAndWeatherSection {
        @Comment("{TIME} - Changed time in ticks")
        private Notice timeSet = Notice.chat("<green>Time has been set to <white>{TIME}");
        private Notice timeAdd = Notice.chat("<green>Time has been added by <white>{TIME}");

        @Comment("{WORLD} - World name")
        private Notice weatherSetRain = Notice.chat("<green>Weather set to rain in the world <white>{WORLD}");
        private Notice weatherSetSun = Notice.chat("<green>Weather set to sun in the world <white>{WORLD}");
        private Notice weatherSetThunder = Notice.chat("<green>Weather set to thunder in the world <white>{WORLD}");
    }

    @Comment({"", "This section is responsible for the item-related messages."})
    private ENItemSection item = new ENItemSection();

    @Getter
    public static class ENItemSection extends OkaeriConfig implements ItemSection {
        @Comment("{ITEM_NAME} - New item name")
        private Notice itemChangeNameMessage = Notice.chat("<green>Item name has been changed to: <white>{ITEM_NAME}");
        private Notice itemClearNameMessage = Notice.chat("<green>Item name has been cleared!");

        @Comment({"", "{ITEM_LORE} - New item lore"})
        private Notice itemChangeLoreMessage = Notice.chat("<green>Item lore has been changed to: <white>{ITEM_LORE}");
        private Notice itemClearLoreMessage = Notice.chat("<green>Item lore has been cleared!");

        @Comment("")
        private Notice repairMessage = Notice.chat("<green>Item has been repaired!");
        private Notice repairAllMessage = Notice.chat("<green>All items have been repaired!");

        @Comment("")
        private Notice incorrectItem = Notice.chat("<dark_red>This is not a valid item!");

        @Comment("")
        private Notice enchantedMessage = Notice.chat("<green>Item has been enchanted!");
        private Notice enchantedMessageFor = Notice.chat("<green>Item in hand of <white>{PLAYER} <green>has been enchanted!");
        private Notice enchantedMessageBy = Notice.chat("<green>Your item in hand has been enchanted by <white>{PLAYER}");

        @Comment({"", "{ITEM} - Name of received item"})
        private Notice giveReceived = Notice.chat("<green>You have received the item <white>{ITEM}");

        @Comment({"", "{PLAYER} - Name of item receiver, {ITEM} - the item"})
        private Notice giveGiven = Notice.chat("<green>You have given the item <white>{ITEM} <green>to <white>{PLAYER}");
    }

    @Comment({"", "This section is responsible for the warp-related messages."})
    private ENWarpSection warp = new ENWarpSection();

    @Getter
    public static class ENWarpSection extends OkaeriConfig implements WarpSection {
        @Comment("{WARP} - Warp name")
        private Notice create = Notice.chat("<green>Warp <white>{WARP} <green>has been created.");
        private Notice remove = Notice.chat("<green>Warp <white>{WARP} <green>has been deleted.");

        @Comment("")
        private Notice warpAlreadyExists = Notice.chat("<dark_red>Warp <white>{WARP} <dark_red>already exists!");
        private Notice noWarps = Notice.chat("<dark_red>There are no warps!");
        private Notice notExist = Notice.chat("<dark_red>This warp doesn't exist");
    }

    @Comment({"", "This section is responsible for the home-related messages."})
    private ENHomeSection home = new ENHomeSection();

    @Getter
    public static class ENHomeSection extends OkaeriConfig implements HomeSection {
        @Comment("{HOMES} - List of homes (separated by commas)")
        private Notice homeList = Notice.chat("<green>Available homes: <white>{HOMES}");

        @Comment({"", "{HOME} - Home name"})
        private Notice create = Notice.chat("<green>Home <white>{HOME} <green>has been created.");
        private Notice delete = Notice.chat("<green>Home <white>{HOME} <green>has been deleted.");
        private Notice homeAlreadyExists = Notice.chat("<dark_red>Home with name <white>{HOME} <dark_red>already exists!");

        @Comment({"", "{LIMIT} - Limit of homes"})
        private Notice limit = Notice.chat("<dark_red>You have reached the limit of homes! <red>({LIMIT})");
        private Notice noHomes = Notice.chat("<dark_red>You don't have any homes!");
    }

    @Comment({"", "This section is responsible for the teleport-related messages."})
    private ENTeleportSection teleport = new ENTeleportSection();

    @Getter
    @Configuration
    public static class ENTeleportSection implements TeleportSection {
        @Comment("{PLAYER} - Player who will be teleported")
        private Notice teleportedToPlayer = Notice.chat("<green>You have been teleported to the player <white>{PLAYER}");
        @Comment("{PLAYER} - Player who will be teleported, {SENDER} - Player who executed the command")
        private Notice teleportedPlayerToPlayer = Notice.chat("<green>You have teleported the player <white>{PLAYER} <green>to <white>{SENDER}");

        @Comment({"", "{Y} - Highest block by Y"})
        private Notice teleportedToHighestBlock = Notice.chat("<green>You have been teleported to the highest block! (Y: {Y})");

        @Comment({"", "{X} - X coordinate, {Y} - Y coordinate, {Z} - Z coordinate"})
        private Notice teleportedToCoordinates = Notice.chat("<green>You have been teleported to the coordinates: <white>X: {X}, Y: {Y}, Z: {Z}");
        @Comment("{PLAYER} - Player who will be teleported, {X} - X coordinate, {Y} - Y coordinate, {Z} - Z coordinate")
        private Notice teleportedSpecifiedPlayerToCoordinates = Notice.chat("<green>You have teleported the player <white>{PLAYER} <green>to the coordinates: <white>X: {X}, Y: {Y}, Z: {Z}");

        @Comment("")
        private Notice teleportedToLastLocation = Notice.chat("<green>You have been teleported to your last location!");
        @Comment("{PLAYER} - Player who will be teleported")
        private Notice teleportedSpecifiedPlayerLastLocation = Notice.chat("<green>You have teleported the player <white>{PLAYER} <green>to their last location!");
        private Notice lastLocationNoExist = Notice.chat("<dark_red>Last location doesn't exist!");
    }

    @Comment({"", "This section is responsible for the chat-related messages."})
    private ENChatSection chat = new ENChatSection();

    @Getter
    public static class ENChatSection extends OkaeriConfig implements ChatSection {
        private Notice broadcastMessage = Notice.chat("<red>[Broadcast] <white>{MESSAGE}");
    }
}
