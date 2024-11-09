package space.bxteam.nexus.core.translation.implementation;

import de.exlll.configlib.Comment;
import de.exlll.configlib.Configuration;
import lombok.Getter;
import lombok.experimental.Accessors;
import space.bxteam.nexus.core.translation.Translation;

import java.util.List;

@Configuration
@Getter
@Accessors(fluent = true)
@SuppressWarnings("FieldMayBeFinal")
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
        private String onlyPlayers = "<dark_red>Only players can execute this command.";
        private String noItem = "<dark_red>You need item to use this command!";

        @Comment({"", "{PERMISSIONS} - Required permissions"})
        private String noPermission = "<dark_red>You don't have permission to execute this command! <red>({PERMISSIONS})";

        @Comment({"", "{USAGE} - Correct usage of the command"})
        private String usageMessage = "<green>Correct usage: <white>{USAGE}";
        private String usageMessageHead = "<green>Correct usage:";
        private String usageMessageEntry = "<gray> - <white>{USAGE}";
        private String numberBiggerThanOrEqualZero = "<dark_red>Number must be bigger than or equal to 0!";
        private String noDamaged = "<dark_red>This item can't be repaired!";
        private String noDamagedItems = "<dark_red>There are no damaged items in your inventory!";
        private String noEnchantment = "<dark_red>This enchantment doesn't exist!";
        private String noValidEnchantmentLevel = "<dark_red>Not a valid enchantment level!";
        private String giveNoItem = "<dark_red>This item is not obtainable!";
    }

    @Comment({"", "This answer is responsible for the general formatting of some values"})
    public ENFormatSection format = new ENFormatSection();

    @Getter
    @Configuration
    public static class ENFormatSection implements Format {
        private String enable = "<green>enabled";
        private String disable = "<red>disabled";
    }

    @Comment({"", "This section is responsible for player-related stuff and interactions with them."})
    private ENPlayerSection player = new ENPlayerSection();

    @Getter
    @Configuration
    public static class ENPlayerSection implements PlayerSection {
        private String feedMessage = "<green>You have been fed!";
        private String feedMessageBy = "<green>You've fed the player <white>{PLAYER}";

        private String healMessage = "<green>You have been healed!";
        private String healMessageBy = "<green>You've healed the player <white>{PLAYER}";

        private String killedMessage = "<dark_red>Killed <red>{PLAYER}";

        @Comment({"", "{STATE} - Fly status"})
        private String flyEnable = "<green>Fly is now <white>{STATE}";
        private String flyDisable = "<green>Fly is now <white>{STATE}";

        @Comment({"", "{PLAYER} - Target player, {STATE} - Target player fly status"})
        private String flySetEnable = "<green>Fly for <white>{PLAYER} <green>is now <white>{STATE}";
        private String flySetDisable = "<green>Fly for <white>{PLAYER} <green>is now <white>{STATE}";

        @Comment({"", "{STATE} - Godmode status"})
        private String godEnable = "<green>God mode is now <white>{STATE}";
        private String godDisable = "<green>God mode is now <white>{STATE}";

        @Comment({"", "{PLAYER} - Target player, {STATE} - Target player godmode status"})
        private String godSetEnable = "<green>God mode for <white>{PLAYER} <green>is now <white>{STATE}";
        private String godSetDisable = "<green>God mode for <white>{PLAYER} <green>is now <white>{STATE}";

        @Comment({"", "{ONLINE} - Online players count"})
        private String onlinePlayersCountMessage = "<green>Online players: <white>{ONLINE}";

        @Comment({"", "{ONLINE} - Current online players, {PLAYERS} - Player list"})
        private String onlinePlayersMessage = "<green>Online players: <white>{ONLINE} <green>Players: <white>{PLAYERS}";

        @Comment({"", "{PING} - Player ping"})
        private String pingMessage = "<green>Your ping: <white>{PING}ms";

        @Comment({"", "{PLAYER} - Target player, {PING} - Target player ping"})
        private String pingOtherMessage = "<green>Ping of the player <white>{PLAYER} <green>is <white>{PING}ms";

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
        private String speedBetweenZeroAndTen = "<dark_red>Speed must be between 0 and 10!";
        private String speedTypeNotCorrect = "<dark_red>Invalid speed type!";

        @Comment("{SPEED} - Walk or fly speed value")
        private String speedWalkSet = "<green>Walking speed is set to <white>{SPEED}";
        private String speedFlySet = "<green>Flying speed is set to <white>{SPEED}";

        @Comment("{PLAYER} - Target player, {SPEED} - Target player walk or fly speed value")
        private String speedWalkSetBy = "<green>Walking speed for <white>{PLAYER} <green>is set to <white>{SPEED}";
        private String speedFlySetBy = "<green>Flying speed for <green>{PLAYER} <green>is set to <white>{SPEED}";

        @Comment("")
        private String gameModeNotCorrect = "<dark_red>Not a valid gamemode type";
        @Comment("GAMEMODE} - Gamemode name")
        private String gameModeMessage = "<green>Gamemode is now set to <white>{GAMEMODE}";
        @Comment("{PLAYER} - Target player, {GAMEMODE} - Gamemode name")
        private String gameModeSetMessage = "<green>Changed gamemode to <white>{GAMEMODE}<green> for player <white>{PLAYER}";
    }

    @Comment({"", "This section is responsible for the inventory-related messages."})
    private ENInventorySection inventory = new ENInventorySection();

    @Getter
    @Configuration
    public static class ENInventorySection implements InventorySection {
        private String inventoryClearMessage = "<green>Your inventory has been cleared!";
        private String inventoryClearMessageBy = "<green>The inventory of the player <white>{PLAYER} <green>has been cleared!";
    }

    @Comment({"", "This section is responsible for the sudo command messages."})
    private ENSudoSection sudo = new ENSudoSection();

    @Getter
    @Configuration
    public static class ENSudoSection implements SudoSection {
        @Comment("{PLAYER} - Player who executed the command, {COMMAND} - Command that the player executed")
        private String sudoMessageSpy = "<gray>[SUDO] <white>{PLAYER} <gray>executed command: <white>{COMMAND}";
        private String sudoMessage = "<green>You have executed the command: <white>{COMMAND} <green>on player <white>{PLAYER}";
    }

    @Comment({"", "This section is responsible for the time and weather-related messages."})
    private ENTimeAndWeatherSection timeAndWeather = new ENTimeAndWeatherSection();

    @Getter
    @Configuration
    public static class ENTimeAndWeatherSection implements TimeAndWeatherSection {
        @Comment("{TIME} - Changed time in ticks")
        private String timeSet = "<green>Time has been set to <white>{TIME}";
        private String timeAdd = "<green>Time has been added by <white>{TIME}";

        @Comment("{WORLD} - World name")
        private String weatherSetRain = "<green>Weather set to rain in the world <white>{WORLD}";
        private String weatherSetSun = "<green>Weather set to sun in the world <white>{WORLD}";
        private String weatherSetThunder = "<green>Weather set to thunder in the world <white>{WORLD}";
    }

    @Comment({"", "This section is responsible for the item-related messages."})
    private ENItemSection item = new ENItemSection();

    @Getter
    @Configuration
    public static class ENItemSection implements ItemSection {
        @Comment("{ITEM_NAME} - New item name")
        private String itemChangeNameMessage = "<green>Item name has been changed to: <white>{ITEM_NAME}";
        private String itemClearNameMessage = "<green>Item name has been cleared!";

        @Comment({"", "{ITEM_LORE} - New item lore"})
        private String itemChangeLoreMessage = "<green>Item lore has been changed to: <white>{ITEM_LORE}";
        private String itemClearLoreMessage = "<green>Item lore has been cleared!";

        @Comment("")
        private String repairMessage = "<green>Item has been repaired!";
        private String repairAllMessage = "<green>All items have been repaired!";

        @Comment("")
        private String incorrectItem = "<dark_red>This is not a valid item!";

        @Comment("")
        private String enchantedMessage = "<green>Item has been enchanted!";
        private String enchantedMessageFor = "<green>Item in hand of <white>{PLAYER} <green>has been enchanted!";
        private String enchantedMessageBy = "<green>Your item in hand has been enchanted by <white>{PLAYER}";

        @Comment({"", "{ITEM} - Name of received item"})
        private String giveReceived = "<green>You have received the item <white>{ITEM}";

        @Comment({"", "{PLAYER} - Name of item receiver, {ITEM} - the item"})
        private String giveGiven = "<green>You have given the item <white>{ITEM} <green>to <white>{PLAYER}";
    }

    @Comment({"", "This section is responsible for the warp-related messages."})
    private ENWarpSection warp = new ENWarpSection();

    @Getter
    @Configuration
    public static class ENWarpSection implements WarpSection {
        @Comment("{WARP} - Warp name")
        private String create = "<green>Warp <white>{WARP} <green>has been created.";
        private String remove = "<green>Warp <white>{WARP} <green>has been deleted.";

        @Comment("")
        private String warpAlreadyExists = "<dark_red>Warp <white>{WARP} <dark_red>already exists!";
        private String noWarps = "<dark_red>There are no warps!";
        private String notExist = "<dark_red>This warp doesn't exist";
    }
}
