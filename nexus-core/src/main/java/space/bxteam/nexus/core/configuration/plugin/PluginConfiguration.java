package space.bxteam.nexus.core.configuration.plugin;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.Exclude;
import eu.okaeri.configs.annotation.Header;
import lombok.Getter;
import space.bxteam.commons.bukkit.position.Position;
import space.bxteam.nexus.core.feature.randomteleport.RandomTeleportType;
import space.bxteam.nexus.core.translation.Language;

import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@SuppressWarnings("FieldMayBeFinal")
@Header("███╗░░██╗███████╗██╗░░██╗██╗░░░██╗░██████╗")
@Header("████╗░██║██╔════╝╚██╗██╔╝██║░░░██║██╔════╝")
@Header("██╔██╗██║█████╗░░░╚███╔╝░██║░░░██║╚█████╗░")
@Header("██║╚████║██╔══╝░░░██╔██╗░██║░░░██║░╚═══██╗")
@Header("██║░╚███║███████╗██╔╝╚██╗╚██████╔╝██████╔╝")
@Header("╚═╝░░╚══╝╚══════╝╚═╝░░╚═╝░╚═════╝░╚═════╝░")
@Header("")
@Header("Discord server: https://discord.gg/p7cxhw7E2M")
@Header("Modrinth: https://modrinth.com/plugin/nexuss")
public class PluginConfiguration extends OkaeriConfig {
    @Comment("Should the plugin check for updates?")
    private boolean checkForUpdates = true;

    @Comment("Plugin prefix")
    private String prefix = "&7[&6Nexus&7] ";

    @Comment({
            "Select the language of the plugin",
            "The following languages are supported:",
            "- EN - English",
            "- RU - Russian"
    })
    private Language language = Language.EN;

    @Comment({"", "Database configuration"})
    private DatabaseConfig database = new DatabaseConfig();

    @Getter
    public static class DatabaseConfig extends OkaeriConfig {
        @Comment({
                "Select here the database you want to use",
                "The following databases are supported:",
                " - sqlite - (default) stores all data in a local file",
                " - mariadb - allows using a remote database"
        })
        private String type = "sqlite";

        @Comment({"", "SQLite configuration"})
        private SQLiteConfig sqlite = new SQLiteConfig();

        @Getter
        public static class SQLiteConfig extends OkaeriConfig {
            private String file = "nexus.db";
        }

        @Comment({"", "MariaDB configuration"})
        private MariaDBConfig mariadb = new MariaDBConfig();

        @Getter
        public static class MariaDBConfig extends OkaeriConfig {
            private String jdbc = "jdbc:mariadb://localhost:3306/nexus";
            private String username = "root";
            private String password = "password";
        }
    }

    @Comment("")
    private Spawn spawn = new Spawn();
    @Exclude
    public static final Position EMPTY_POSITION = new Position(0, 0, 0, 0.0f, 0.0f, Position.NONE_WORLD);

    @Getter
    public static class Spawn extends OkaeriConfig {
        @Comment({"The spawn location", "WE DO NOT RECOMMEND CHANGING THIS VALUE MANUALLY"})
        private Position location = EMPTY_POSITION;
    }

    @Comment("")
    private Items items = new Items();

    @Getter
    public static class Items extends OkaeriConfig {
        @Comment("Use unsafe enchantments? Allows you to apply custom enchants to various items")
        private boolean unsafeEnchantments = true;

        @Comment({"", "The default item give amount, when no amount is specified in the command."})
        private int defaultGiveAmount = 1;
    }

    @Comment("")
    private Homes homes = new Homes();

    @Getter
    public static class Homes extends OkaeriConfig {
        @Comment("Default home name")
        private String defaultHomeName = "home";

        @Comment({"", "Maximum amount of homes per permission"})
        private Map<String, Integer> maxHomes = new LinkedHashMap<>() {
            {
                put("nexus.home.default", 1);
                put("nexus.home.extended", 2);
            }
        };
    }

    @Comment("")
    private Chat chat = new Chat();

    @Getter
    public static class Chat extends OkaeriConfig {
        @Comment("Is the slowmode enabled?")
        private boolean slowModeEnabled = true;
        @Comment("Chat slowmode time in seconds")
        private Duration slowMode = Duration.ofSeconds(5);

        @Comment("How many lines should be cleared when using the /chat clear command")
        private int clearLines = 100;
    }

    @Comment("")
    private RandomTeleport randomTeleport = new RandomTeleport();

    @Getter
    public static class RandomTeleport extends OkaeriConfig {
        @Comment({
                "Type of the random teleportation:",
                "- WORLD_BORDER_RADIUS - Teleports the player to a random location within the world border",
                "- STATIC_RADIUS - Teleports the player to a random location within the specified radius",
        })
        private RandomTeleportType randomTeleportType = RandomTeleportType.WORLD_BORDER_RADIUS;

        @Comment("The radius in which the player will be teleported")
        private int randomTeleportRadius = 1000;

        @Comment("The maximum amount of attempts to find a safe location")
        private int maxAttempts = 10;

        @Comment("Teleport to a specific world, if left empty it will teleport to the player's current world")
        private String randomTeleportWorld = "world";
    }

    @Comment("")
    private TeleportRequest teleportRequest = new TeleportRequest();

    @Getter
    public static class TeleportRequest extends OkaeriConfig {
        @Comment("The time in seconds after which the teleport request will expire")
        private Duration requestTimeout = Duration.ofSeconds(30);
    }
}
