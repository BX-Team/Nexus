package gq.bxteam.nexus;

import gq.bxteam.nexus.commands.list.*;
import gq.bxteam.nexus.listeners.*;
import gq.bxteam.nexus.utils.Metrics;
import gq.bxteam.nexus.utils.locale.LocaleConfig;
import gq.bxteam.nexus.utils.locale.LocaleReader;
import gq.bxteam.nexus.utils.logger.Logger;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

import static gq.bxteam.nexus.utils.locale.LocaleConfig.getLangFile;

public final class Nexus extends JavaPlugin {
    public static Nexus instance;
    public File langFile;
    public LocaleReader localeReader;

    public static Nexus getInstance() {
        return Nexus.instance;
    }

    @Override
    public void onEnable() {
        Nexus.instance = this;

        // Metrics
        new Metrics(Nexus.getInstance(), 19684);

        // TODO: Update Checker (on release)

        // Save config.yml and load language file
        this.saveDefaultConfig();
        LocaleConfig.saveLanguages();
        this.langFile = getLangFile();
        localeReader = new LocaleReader(langFile);

        // Register commands & listeners
        registerCommands();
        registerListeners();

        Logger.log("Nexus successfully started!", Logger.LogLevel.INFO, false);
    }

    @Override
    public void onDisable() {
        Bukkit.getScheduler().cancelTasks(Nexus.getInstance());
    }

    public void reload() {
        this.reloadConfig();
        this.langFile = getLangFile();
        localeReader = new LocaleReader(langFile);
    }

    @SuppressWarnings("DataFlowIssue")
    private void registerCommands() {
        getCommand("anvil").setExecutor(new AnvilCommand());
        getCommand("cartography").setExecutor(new CartographyCommand());
        getCommand("enderchest").setExecutor(new EnderChestCommand());
        getCommand("fly").setExecutor(new FlyCommand());
        getCommand("gamemode").setExecutor(new GamemodeCommand());
        getCommand("god").setExecutor(new GodCommand());
        getCommand("grindstone").setExecutor(new GrindstoneCommand());
        getCommand("hat").setExecutor(new HatCommand());
        getCommand("heal").setExecutor(new HealCommand());
        getCommand("invsee").setExecutor(new InvSeeCommand());
        getCommand("loom").setExecutor(new LoomCommand());
        getCommand("message").setExecutor(new MsgCommand());
        getCommand("nexus").setExecutor(new NexusCommand());
        getCommand("ping").setExecutor(new PingCommand());
        getCommand("speed").setExecutor(new SpeedCommand());
        getCommand("spit").setExecutor(new SpitCommand());
        getCommand("tp").setExecutor(new TpCommand());
        getCommand("tpposition").setExecutor(new TpPosCommand());
        getCommand("day").setExecutor(new TimeCommand());
        getCommand("workbench").setExecutor(new WorkbenchCommand());
    }

    private void registerListeners() {
        Bukkit.getServer().getPluginManager().registerEvents(new DoorKnockingListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new GlassKnockingListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new SignEditListener(), this);
    }

    public String getConfigString(String path) {
        return Nexus.getInstance().getConfig().getString(path);
    }

    public boolean getConfigBoolean(String path) {
        return Nexus.getInstance().getConfig().getBoolean(path);
    }
}
