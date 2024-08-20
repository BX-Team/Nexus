package space.bxteam.nexus.core;

import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.bukkit.LiteBukkitFactory;
import dev.rollczi.litecommands.bukkit.LiteBukkitMessages;
import lombok.Getter;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import space.bxteam.nexus.NexusApiProvider;
import space.bxteam.nexus.core.configuration.ConfigurationManager;
import space.bxteam.nexus.core.database.DatabaseManager;
import space.bxteam.nexus.core.feature.essentials.container.AnvilCommand;
import space.bxteam.nexus.core.feature.essentials.gamemode.GamemodeCommandArgument;
import space.bxteam.nexus.core.utils.LogUtil;

public class Nexus {
    @Getter
    private ConfigurationManager configurationManager;
    @Getter
    private DatabaseManager databaseManager;
    private LiteCommands<CommandSender> liteCommands;

    public Nexus(Plugin plugin) {
        NexusApiProvider.initialize(new NexusApiImpl());
        this.configurationManager = new ConfigurationManager(plugin.getDataFolder().toPath());
        this.databaseManager = new DatabaseManager(configurationManager, plugin.getDataFolder());

        LogUtil.log("Registering commands...", LogUtil.LogLevel.INFO);
        this.liteCommands = LiteBukkitFactory.builder("nexus", plugin)
                .commands(new AnvilCommand())

                .argument(GameMode.class, new GamemodeCommandArgument())

                //.message(LiteBukkitMessages.PLAYER_NOT_FOUND, configurationManager.language().playerNotFound())
                //.message(LiteBukkitMessages.PLAYER_ONLY, configurationManager.language().playerOnly())
                //.message(LiteBukkitMessages.MISSING_PERMISSIONS, configurationManager.language().noPermission())
                .message(LiteBukkitMessages.INVALID_USAGE,
                        invalidUsage -> configurationManager.configuration().prefix() + "&cUsage: " + invalidUsage.getSchematic().first())

                .build();
    }

    public void disable() {
        LogUtil.log("Disabling Nexus...", LogUtil.LogLevel.INFO);
        if (this.liteCommands != null) {
            this.liteCommands.unregister();
        }

        if (this.databaseManager != null) {
            this.databaseManager.close();
        }

        NexusApiProvider.shutdown();
    }
}
