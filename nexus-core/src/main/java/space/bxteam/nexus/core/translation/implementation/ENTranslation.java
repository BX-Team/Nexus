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
public class ENTranslation extends OkaeriConfig implements Translation {
    @Override
    public Language language() {
        return Language.EN;
    }

    @Comment({
            "This file is responsible for the English translationConfig in the Nexus plugin.",
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
    public ENArgumentSection argument = new ENArgumentSection();

    @Getter
    public class ENArgumentSection extends OkaeriConfig implements ArgumentSection {
        public Notice onlyPlayers = Notice.chat("<dark_red>Only players can execute this command.");
        public Notice noItem = Notice.chat("<dark_red>You need item to use this command!");

        @Comment({"", "{PERMISSIONS} - Required permissions"})
        public Notice noPermission = Notice.chat("<dark_red>You don't have permission to execute this command! <red>({PERMISSIONS})");

        @Comment({"", "{USAGE} - Correct usage of the command"})
        public Notice usageMessage = Notice.chat("<green>Correct usage: <white>{USAGE}");
        public Notice usageMessageHead = Notice.chat("<green>Correct usage:");
        public Notice usageMessageEntry = Notice.chat("<gray> - <white>{USAGE}");
        public Notice numberBiggerThanOrEqualZero = Notice.chat("<dark_red>Number must be bigger than or equal to 0!");
        public Notice noDamaged = Notice.chat("<dark_red>This item can't be repaired!");
        public Notice noDamagedItems = Notice.chat("<dark_red>There are no damaged items in your inventory!");
        public Notice noEnchantment = Notice.chat("<dark_red>This enchantment doesn't exist!");
        public Notice noValidEnchantmentLevel = Notice.chat("<dark_red>Not a valid enchantment level!");
        public Notice giveNoItem = Notice.chat("<dark_red>This item is not obtainable!");
    }

    @Comment({"", "This answer is responsible for the general formatting of some values"})
    public ENFormatSection format = new ENFormatSection();

    @Getter
    public class ENFormatSection extends OkaeriConfig implements Format {
        public String enable = "<green>enabled";
        public String disable = "<red>disabled";
    }

    @Comment({"", "This section is responsible for player-related stuff and interactions with them."})
    public ENPlayerSection player = new ENPlayerSection();

    @Getter
    public class ENPlayerSection extends OkaeriConfig implements PlayerSection {
        public Notice feedMessage = Notice.chat("<green>You have been fed!");
        public Notice feedMessageBy = Notice.chat("<green>You've fed the player <white>{PLAYER}");

        public Notice healMessage = Notice.chat("<green>You have been healed!");
        public Notice healMessageBy = Notice.chat("<green>You've healed the player <white>{PLAYER}");

        public Notice killedMessage = Notice.chat("<dark_red>Killed <red>{PLAYER}");

        @Comment({"", "{STATE} - Fly status"})
        public Notice flyEnable = Notice.chat("<green>Fly is now <white>{STATE}");
        public Notice flyDisable = Notice.chat("<green>Fly is now <white>{STATE}");

        @Comment({"", "{PLAYER} - Target player, {STATE} - Target player fly status"})
        public Notice flySetEnable = Notice.chat("<green>Fly for <white>{PLAYER} <green>is now <white>{STATE}");
        public Notice flySetDisable = Notice.chat("<green>Fly for <white>{PLAYER} <green>is now <white>{STATE}");

        @Comment({"", "{STATE} - Godmode status"})
        public Notice godEnable = Notice.chat("<green>God mode is now <white>{STATE}");
        public Notice godDisable = Notice.chat("<green>God mode is now <white>{STATE}");

        @Comment({"", "{PLAYER} - Target player, {STATE} - Target player godmode status"})
        public Notice godSetEnable = Notice.chat("<green>God mode for <white>{PLAYER} <green>is now <white>{STATE}");
        public Notice godSetDisable = Notice.chat("<green>God mode for <white>{PLAYER} <green>is now <white>{STATE}");

        @Comment({"", "{ONLINE} - Online players count"})
        public Notice onlinePlayersCountMessage = Notice.chat("<green>Online players: <white>{ONLINE}");

        @Comment({"", "{ONLINE} - Current online players, {PLAYERS} - Player list"})
        public Notice onlinePlayersMessage = Notice.chat("<green>Online players: <white>{ONLINE} <green>Players: <white>{PLAYERS}");

        @Comment({"", "{PING} - Player ping"})
        public Notice pingMessage = Notice.chat("<green>Your ping: <white>{PING}ms");

        @Comment({"", "{PLAYER} - Target player, {PING} - Target player ping"})
        public Notice pingOtherMessage = Notice.chat("<green>Ping of the player <white>{PLAYER} <green>is <white>{PING}ms");

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
        public List<String> whoisCommand = List.of(
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
        public Notice speedBetweenZeroAndTen = Notice.chat("<dark_red>Speed must be between 0 and 10!");
        public Notice speedTypeNotCorrect = Notice.chat("<dark_red>Invalid speed type!");

        @Comment("{SPEED} - Walk or fly speed value")
        public Notice speedWalkSet = Notice.chat("<green>Walking speed is set to <white>{SPEED}");
        public Notice speedFlySet = Notice.chat("<green>Flying speed is set to <white>{SPEED}");

        @Comment("{PLAYER} - Target player, {SPEED} - Target player walk or fly speed value")
        public Notice speedWalkSetBy = Notice.chat("<green>Walking speed for <white>{PLAYER} <green>is set to <white>{SPEED}");
        public Notice speedFlySetBy = Notice.chat("<green>Flying speed for <green>{PLAYER} <green>is set to <white>{SPEED}");

        @Comment("")
        public Notice gameModeNotCorrect = Notice.chat("<dark_red>Not a valid gamemode type");
        @Comment("GAMEMODE} - Gamemode name")
        public Notice gameModeMessage = Notice.chat("<green>Gamemode is now set to <white>{GAMEMODE}");
        @Comment("{PLAYER} - Target player, {GAMEMODE} - Gamemode name")
        public Notice gameModeSetMessage = Notice.chat("<green>Changed gamemode to <white>{GAMEMODE}<green> for player <white>{PLAYER}");

        @Comment("")
        private List<String> fullServerSlots = List.of(
                "<dark_red>Server is full!",
                "<dark_red>Try again later!"
        );
    }

    @Comment({"", "This section is responsible for the inventory-related messages."})
    public ENInventorySection inventory = new ENInventorySection();

    @Getter
    public class ENInventorySection extends OkaeriConfig implements InventorySection {
        public Notice inventoryClearMessage = Notice.chat("<green>Your inventory has been cleared!");
        public Notice inventoryClearMessageBy = Notice.chat("<green>The inventory of the player <white>{PLAYER} <green>has been cleared!");
    }

    @Comment({"", "This section is responsible for the sudo command messages."})
    public ENSudoSection sudo = new ENSudoSection();

    @Getter
    public class ENSudoSection extends OkaeriConfig implements SudoSection {
        @Comment("{PLAYER} - Player who executed the command, {COMMAND} - Command that the player executed")
        public Notice sudoMessageSpy = Notice.chat("<gray>[SUDO] <white>{PLAYER} <gray>executed command: <white>{COMMAND}");
        public Notice sudoMessage = Notice.chat("<green>You have executed the command: <white>{COMMAND} <green>on player <white>{PLAYER}");
    }

    @Comment({"", "This section is responsible for the time and weather-related messages."})
    public ENTimeAndWeatherSection timeAndWeather = new ENTimeAndWeatherSection();

    @Getter
    public class ENTimeAndWeatherSection extends OkaeriConfig implements TimeAndWeatherSection {
        @Comment("{TIME} - Changed time in ticks")
        public Notice timeSet = Notice.chat("<green>Time has been set to <white>{TIME}");
        public Notice timeAdd = Notice.chat("<green>Time has been added by <white>{TIME}");

        @Comment("{WORLD} - World name")
        public Notice weatherSetRain = Notice.chat("<green>Weather set to rain in the world <white>{WORLD}");
        public Notice weatherSetSun = Notice.chat("<green>Weather set to sun in the world <white>{WORLD}");
        public Notice weatherSetThunder = Notice.chat("<green>Weather set to thunder in the world <white>{WORLD}");
    }

    @Comment({"", "This section is responsible for the item-related messages."})
    public ENItemSection item = new ENItemSection();

    @Getter
    public class ENItemSection extends OkaeriConfig implements ItemSection {
        @Comment("{ITEM_NAME} - New item name")
        public Notice itemChangeNameMessage = Notice.chat("<green>Item name has been changed to: <white>{ITEM_NAME}");
        public Notice itemClearNameMessage = Notice.chat("<green>Item name has been cleared!");

        @Comment({"", "{ITEM_LORE} - New item lore"})
        public Notice itemChangeLoreMessage = Notice.chat("<green>Item lore has been changed to: <white>{ITEM_LORE}");
        public Notice itemClearLoreMessage = Notice.chat("<green>Item lore has been cleared!");

        @Comment("")
        public Notice repairMessage = Notice.chat("<green>Item has been repaired!");
        public Notice repairAllMessage = Notice.chat("<green>All items have been repaired!");

        @Comment("")
        public Notice incorrectItem = Notice.chat("<dark_red>This is not a valid item!");

        @Comment("")
        public Notice enchantedMessage = Notice.chat("<green>Item has been enchanted!");
        public Notice enchantedMessageFor = Notice.chat("<green>Item in hand of <white>{PLAYER} <green>has been enchanted!");
        public Notice enchantedMessageBy = Notice.chat("<green>Your item in hand has been enchanted by <white>{PLAYER}");

        @Comment({"", "{ITEM} - Name of received item"})
        public Notice giveReceived = Notice.chat("<green>You have received the item <white>{ITEM}");

        @Comment({"", "{PLAYER} - Name of item receiver, {ITEM} - the item"})
        public Notice giveGiven = Notice.chat("<green>You have given the item <white>{ITEM} <green>to <white>{PLAYER}");
    }

    @Comment({"", "This section is responsible for the warp-related messages."})
    public ENWarpSection warp = new ENWarpSection();

    @Getter
    public class ENWarpSection extends OkaeriConfig implements WarpSection {
        @Comment("{WARP} - Warp name")
        public Notice create = Notice.chat("<green>Warp <white>{WARP} <green>has been created.");
        public Notice remove = Notice.chat("<green>Warp <white>{WARP} <green>has been deleted.");

        @Comment("")
        public Notice warpAlreadyExists = Notice.chat("<dark_red>Warp <white>{WARP} <dark_red>already exists!");
        public Notice noWarps = Notice.chat("<dark_red>There are no warps!");
        public Notice notExist = Notice.chat("<dark_red>This warp doesn't exist");
    }

    @Comment({"", "This section is responsible for the home-related messages."})
    public ENHomeSection home = new ENHomeSection();

    @Getter
    public class ENHomeSection extends OkaeriConfig implements HomeSection {
        @Comment("{HOMES} - List of homes (separated by commas)")
        public Notice homeList = Notice.chat("<green>Available homes: <white>{HOMES}");

        @Comment({"", "{HOME} - Home name"})
        public Notice create = Notice.chat("<green>Home <white>{HOME} <green>has been created.");
        public Notice delete = Notice.chat("<green>Home <white>{HOME} <green>has been deleted.");
        public Notice homeAlreadyExists = Notice.chat("<dark_red>Home with name <white>{HOME} <dark_red>already exists!");

        @Comment({"", "{LIMIT} - Limit of homes"})
        public Notice limit = Notice.chat("<dark_red>You have reached the limit of homes! <red>({LIMIT})");
        public Notice noHomes = Notice.chat("<dark_red>You don't have any homes!");
    }

    @Comment({"", "This section is responsible for the teleport-related messages."})
    public ENTeleportSection teleport = new ENTeleportSection();

    @Getter
    public class ENTeleportSection extends OkaeriConfig implements TeleportSection {
        @Comment("{PLAYER} - Player who will be teleported")
        public Notice teleportedToPlayer = Notice.chat("<green>You have been teleported to the player <white>{PLAYER}");
        @Comment("{PLAYER} - Player who will be teleported, {SENDER} - Player who executed the command")
        public Notice teleportedPlayerToPlayer = Notice.chat("<green>You have teleported the player <white>{PLAYER} <green>to <white>{SENDER}");

        @Comment({"", "{Y} - Highest block by Y"})
        public Notice teleportedToHighestBlock = Notice.chat("<green>You have been teleported to the highest block! (Y: {Y})");

        @Comment({"", "{X} - X coordinate, {Y} - Y coordinate, {Z} - Z coordinate"})
        public Notice teleportedToCoordinates = Notice.chat("<green>You have been teleported to the coordinates: <white>X: {X}, Y: {Y}, Z: {Z}");
        @Comment("{PLAYER} - Player who will be teleported, {X} - X coordinate, {Y} - Y coordinate, {Z} - Z coordinate")
        public Notice teleportedSpecifiedPlayerToCoordinates = Notice.chat("<green>You have teleported the player <white>{PLAYER} <green>to the coordinates: <white>X: {X}, Y: {Y}, Z: {Z}");

        @Comment("")
        public Notice teleportedToLastLocation = Notice.chat("<green>You have been teleported to your last location!");
        @Comment("{PLAYER} - Player who will be teleported")
        public Notice teleportedSpecifiedPlayerLastLocation = Notice.chat("<green>You have teleported the player <white>{PLAYER} <green>to their last location!");
        public Notice lastLocationNoExist = Notice.chat("<dark_red>Last location doesn't exist!");
    }

    @Comment({"", "This section is responsible for the chat-related messages."})
    public ENChatSection chat = new ENChatSection();

    @Getter
    public class ENChatSection extends OkaeriConfig implements ChatSection {
        @Comment("{PLAYER} - Player who executed the command")
        public Notice enabled = Notice.chat("<green>Chat has been enabled by <white>{PLAYER}");
        public Notice disabled = Notice.chat("<dark_red>Chat is disabled by <white>{PLAYER}");
        public Notice cleared = Notice.chat("<green>Chat has been cleared by <white>{PLAYER}");

        @Comment("")
        public Notice alreadyDisabled = Notice.chat("<dark_red>Chat is already disabled!");
        public Notice alreadyEnabled = Notice.chat("<dark_red>Chat is already enabled!");

        @Comment("")
        public Notice chatDisabled = Notice.chat("<dark_red>Chat is disabled!");

        @Comment("{TIME} - Remaining time in seconds")
        public Notice slowMode = Notice.chat("<dark_red>You can write next message in <white>{TIME} <dark_red>seconds!");

        @Comment({"", "{MESSAGE} - Broadcast content"})
        public Notice broadcastMessage = Notice.chat("<red>[Broadcast] <white>{MESSAGE}");
    }

    @Comment({"", "This section is responsible for the fun-related messages."})
    public ENFunSection fun = new ENFunSection();

    @Getter
    public class ENFunSection extends OkaeriConfig implements FunSection {
        public Notice spitSound = Notice.sound(Key.key("entity.llama.spit"), 1.0f, 1.0f);
    }
}
