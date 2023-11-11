package gq.bxteam.nexus;

import gq.bxteam.nexus.commands.list.*;
import gq.bxteam.nexus.listeners.*;
import gq.bxteam.nexus.managers.*;
import gq.bxteam.nexus.utils.Metrics;
import gq.bxteam.nexus.utils.locale.LocaleConfig;
import gq.bxteam.nexus.utils.locale.LocaleReader;
import gq.bxteam.nexus.utils.logger.Logger;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static gq.bxteam.nexus.utils.locale.LocaleConfig.getLangFile;

public final class Nexus extends JavaPlugin {
    public static Nexus instance;
    public File langFile;
    public LocaleReader localeReader;
    public PlayerManager playerManager;
    public WarpManager warpManager;

    public static Nexus getInstance() {
        return Nexus.instance;
    }

    @Override
    public void onEnable() {
        Nexus.instance = this;
        playerManager = new PlayerManager(this);
        Logger.log("PlayerManager initialized!", Logger.LogLevel.INFO, false);
        warpManager = new WarpManager(this);
        Logger.log("WarpManager initialized!", Logger.LogLevel.INFO, false);

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
        Logger.log("Cancelling all tasks...", Logger.LogLevel.INFO, false);
    }

    public void reload() {
        this.reloadConfig();
        this.langFile = getLangFile();
        localeReader = new LocaleReader(langFile);
    }

    @SuppressWarnings("DataFlowIssue")
    private void registerCommands() {
        Map<String, CommandExecutor> commands = new HashMap<>();
        commands.put("anvil", new AnvilCommand());
        commands.put("back", new BackCommand());
        commands.put("broadcast", new BroadcastCommand());
        commands.put("cartography", new CartographyCommand());
        commands.put("enderchest", new EnderChestCommand());
        commands.put("fly", new FlyCommand());
        commands.put("gamemode", new GamemodeCommand());
        commands.put("give", new GiveCommand());
        commands.put("god", new GodCommand());
        commands.put("grindstone", new GrindstoneCommand());
        commands.put("hat", new HatCommand());
        commands.put("heal", new HealCommand());
        commands.put("home", new HomeCommands());
        commands.put("invsee", new InvSeeCommand());
        commands.put("loom", new LoomCommand());
        commands.put("message", new MsgCommand());
        getCommand("nexus").setExecutor(new NexusCommand());
        commands.put("ping", new PingCommand());
        commands.put("repair", new RepairCommand());
        commands.put("smithingtable", new SmithingTableCommand());
        commands.put("speed", new SpeedCommand());
        commands.put("spit", new SpitCommand());
        commands.put("day", new TimeCommand());
        commands.put("tp", new TpCommand());
        commands.put("tphere", new TpHereCommand());
        commands.put("tpposition", new TpPosCommand());
        commands.put("vanish", new VanishCommand());
        commands.put("warp", new WarpCommands());
        commands.put("whois", new WhoisCommand());
        commands.put("workbench", new WorkbenchCommand());

        for (Map.Entry<String, CommandExecutor> entry : commands.entrySet()) {
            String commandName = entry.getKey();
            CommandExecutor commandExecutor = entry.getValue();
            if (Nexus.getInstance().getConfigBoolean("commands." + commandName + ".enable")) {
                getCommand(commandName).setExecutor(commandExecutor);
            }
        }
    }

    private void registerListeners() {
        Bukkit.getServer().getPluginManager().registerEvents(new BookAndSignEditListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new DoorKnockingListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new GlassKnockingListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerQuitListener(), this);
    }

    public void updateConfig() {
        // use this method to update config.yml when a new version is released
    }

    public String getConfigString(String path) {
        return Nexus.getInstance().getConfig().getString(path);
    }

    public boolean getConfigBoolean(String path) {
        return Nexus.getInstance().getConfig().getBoolean(path);
    }
}
