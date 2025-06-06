package org.bxteam.nexus.core.configuration.plugin;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.Exclude;
import eu.okaeri.configs.annotation.Header;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bxteam.commons.bukkit.position.Position;
import org.bxteam.nexus.core.database.DatabaseType;
import org.bxteam.nexus.core.feature.randomteleport.RandomTeleportType;
import org.bxteam.nexus.core.translation.Language;

import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Getter
@SuppressWarnings({"FieldMayBeFinal", "InnerClassMayBeStatic"})
@Header("███╗░░██╗███████╗██╗░░██╗██╗░░░██╗░██████╗")
@Header("████╗░██║██╔════╝╚██╗██╔╝██║░░░██║██╔════╝")
@Header("██╔██╗██║█████╗░░░╚███╔╝░██║░░░██║╚█████╗░")
@Header("██║╚████║██╔══╝░░░██╔██╗░██║░░░██║░╚═══██╗")
@Header("██║░╚███║███████╗██╔╝╚██╗╚██████╔╝██████╔╝")
@Header("╚═╝░░╚══╝╚══════╝╚═╝░░╚═╝░╚═════╝░╚═════╝░")
@Header("")
@Header("Discord server: https://discord.gg/qNyybSSPm5")
@Header("Modrinth: https://modrinth.com/plugin/nexuss")
public class PluginConfiguration extends OkaeriConfig {
    @Comment("Should the plugin check for updates?")
    private boolean checkForUpdates = true;

    @Comment({
            "",
            "Select the language of the plugin",
            "The following languages are supported:",
            "- EN - English",
            "- RU - Russian"
    })
    private Language language = Language.EN;

    @Comment({"", "Database configuration"})
    private DatabaseConfig database = new DatabaseConfig();

    @Getter
    public class DatabaseConfig extends OkaeriConfig {
        @Comment({
                "Select here the database you want to use",
                "The following databases are supported:",
                " - SQLITE - (default) stores all data in a local file",
                " - MARIADB - stores data in a remote MariaDB database",
                " - POSTGRESQL - stores data in a remote PostgreSQL database"
        })
        private DatabaseType type = DatabaseType.SQLITE;

        @Comment({"", "SQLite configuration"})
        private SQLiteConfig sqlite = new SQLiteConfig();

        @Getter
        public class SQLiteConfig extends OkaeriConfig {
            private String file = "nexus.db";
        }

        @Comment({"", "MariaDB configuration"})
        private MariaDBConfig mariadb = new MariaDBConfig();

        @Getter
        public class MariaDBConfig extends OkaeriConfig {
            private String jdbc = "jdbc:mariadb://localhost:3306/nexus";
            private String username = "root";
            private String password = "password";
        }

        @Comment({"", "PostgreSQL configuration"})
        private PostgreSQLConfig postgresql = new PostgreSQLConfig();

        @Getter
        public class PostgreSQLConfig extends OkaeriConfig {
            private String jdbc = "jdbc:postgresql://localhost:5432/";
            private String username = "root";
            private String password = "password";
        }
    }

    @Comment("")
    private Afk afk = new Afk();

    @Getter
    public class Afk extends OkaeriConfig {
        @Comment("Amount of interactions required to unmark the player as AFK")
        private int interactionsRequired = 20;

        @Comment("Should the player be marked as AFK after a certain time of inactivity?")
        private boolean autoMark = true;
        @Comment("Amount of time after which the player will be marked as AFK")
        private Duration autoMarkTime = Duration.ofMinutes(10);
        @Comment("Should the player be kicked from the server when marked as AFK?")
        private boolean kickOnAfk = false;
    }

    @Comment("")
    private Server server = new Server();

    @Getter
    public class Server extends OkaeriConfig {
        @Comment("This value will be shown in the server list as fake value, not actually used for player limit")
        private int maxPlayers = 100;

        @Comment({
                "",
                "If you want to use a custom MOTD, set this value to true and edit the motd values below",
                "The MOTD (Message of the Day) will be displayed to players when they join the server",
                "You can specify multiple MOTDs, and one will be chosen at random each time a player joins",
                "It supports MiniMessage formatting"
        })
        private boolean useRandomMotd = false;
        private Set<String> motd = Set.of("<green>Welcome to the server!<newline><gold>We hope you enjoy your stay", "<blue>Hello <red>world!");
    }

    @Comment("")
    private ServerLinks serverLinks = new ServerLinks();

    @Getter
    @SuppressWarnings("UnstableApiUsage")
    public class ServerLinks extends OkaeriConfig {
        @Comment({
                "Configuration of server links displayed in the ESC/pause menu",
                "Links will be visible in the game's pause menu under server information",
                "Note: This feature requires Minecraft 1.21 or newer to work properly",
        })
        private boolean sendServerLinksOnJoin = true;

        @Comment({
                "",
                "Default Minecraft link types: https://jd.papermc.io/paper/1.21.5/org/bukkit/ServerLinks.Type.html#enum-constant-summary",
                "The text displayed depends on the player's localization"
        })
        private List<TypeLink> typeLinks = new ArrayList<>() {
            {
                add(new TypeLink(org.bukkit.ServerLinks.Type.WEBSITE, "https://example.com"));
            }
        };

        @Comment({"", "Custom links that will be displayed in the server links section"})
        private List<CustomLink> customLinks = new ArrayList<>() {
            {
                add(new CustomLink("<blue>Discord", "https://discord.gg/qNyybSSPm5"));
            }
        };

        @Getter
        @NoArgsConstructor
        public class TypeLink extends OkaeriConfig {
            private org.bukkit.ServerLinks.Type type;
            private String url;

            public TypeLink(org.bukkit.ServerLinks.Type type, String url) {
                this.type = type;
                this.url = url;
            }
        }

        @Getter
        @NoArgsConstructor
        public class CustomLink extends OkaeriConfig {
            private String name;
            private String url;

            public CustomLink(String name, String url) {
                this.name = name;
                this.url = url;
            }
        }
    }

    @Comment("")
    private Spawn spawn = new Spawn();
    @Exclude
    private static final Position EMPTY_POSITION = new Position(0, 0, 0, 0.0f, 0.0f, Position.NONE_WORLD);

    @Getter
    public class Spawn extends OkaeriConfig {
        @Comment({"The spawn location", "WE DO NOT RECOMMEND CHANGING THIS VALUE MANUALLY"})
        private Position location = EMPTY_POSITION;

        @Comment({"", "Should the player be teleported to the spawn on first join?"})
        private boolean teleportOnFirstJoin = true;
        @Comment("Should the player be teleported to the spawn on join?")
        private boolean teleportOnJoin = false;
    }

    @Comment("")
    private Warp warp = new Warp();

    @Getter
    public class Warp extends OkaeriConfig {
        @Comment("Time to teleport to the warp")
        private Duration timeToTeleport = Duration.ofSeconds(5);
    }

    @Comment("")
    private Items items = new Items();

    @Getter
    public class Items extends OkaeriConfig {
        @Comment("Use unsafe enchantments? Allows you to apply custom enchants to various items")
        private boolean unsafeEnchantments = true;

        @Comment({"", "The default item give amount, when no amount is specified in the command."})
        private int defaultGiveAmount = 1;
    }

    @Comment("")
    private Homes homes = new Homes();

    @Getter
    public class Homes extends OkaeriConfig {
        @Comment("Time to teleport to the home")
        private Duration timeToTeleport = Duration.ofSeconds(5);

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
    public class Chat extends OkaeriConfig {
        @Comment("Is the slowmode enabled?")
        private boolean slowModeEnabled = true;
        @Comment("Chat slowmode time in seconds")
        private Duration slowMode = Duration.ofSeconds(5);

        @Comment("How many lines should be cleared when using the /chat clear command")
        private int clearLines = 100;
    }

    @Comment("")
    private Jail jail = new Jail();

    @Getter
    public class Jail extends OkaeriConfig {
        @Comment("Default jail duration")
        private Duration jailTime = Duration.ofMinutes(30);

        @Comment({"", "List of allowed commands for jailed players"})
        private Set<String> allowedCommands = Set.of("msg", "tell", "r", "reply", "me");

        @Comment({"", "Jail area locations", "WE DO NOT RECOMMEND CHANGING THIS VALUE MANUALLY"})
        private Map<String, Position> jailArea = new LinkedHashMap<>() {
            {
                put("jail", EMPTY_POSITION);
            }
        };
    }

    @Comment("")
    private RandomTeleport randomTeleport = new RandomTeleport();

    @Getter
    public class RandomTeleport extends OkaeriConfig {
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
    public class TeleportRequest extends OkaeriConfig {
        @Comment("The time in seconds after which the teleport request will expire")
        private Duration requestTimeout = Duration.ofSeconds(30);
    }
}
