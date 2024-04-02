package space.bxteam.nexus;

import org.bukkit.command.*;
import space.bxteam.nexus.commands.list.*;
import space.bxteam.nexus.data.Database;
import space.bxteam.nexus.integrations.PlaceholderIntegration;
import space.bxteam.nexus.listeners.*;
import space.bxteam.nexus.managers.*;
import space.bxteam.nexus.utils.Metrics;
import space.bxteam.nexus.utils.locale.LocaleConfig;
import space.bxteam.nexus.utils.locale.LocaleReader;
import space.bxteam.nexus.utils.logger.Logger;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static space.bxteam.nexus.utils.locale.LocaleConfig.getLangFile;

public final class Nexus extends JavaPlugin {
    public static Nexus instance;
    public File langFile;
    public LocaleReader localeReader;
    public PlayerManager playerManager;
    public WarpManager warpManager;
    public Database database;
    private static final String iconsPath = "icons" + File.separator;

    public static Nexus getInstance() {
        return Nexus.instance;
    }

    @Override
    public void onEnable() {
        Nexus.instance = this;
        playerManager = new PlayerManager(this);
        warpManager = new WarpManager(this);
        database = new Database(this);

        // Metrics
        new Metrics(Nexus.getInstance(), 19684);

        // TODO: Update Checker (on release)

        // Save config.yml and load other files
        this.saveDefaultConfig();
        LocaleConfig.saveLanguages();
        this.langFile = getLangFile();
        localeReader = new LocaleReader(langFile);
        loadIcons();

        // Register commands & listeners
        registerCommands();
        registerListeners();
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new PlaceholderIntegration().register();
            Logger.log("Loaded &bPlaceholderAPI &rhook!", Logger.LogLevel.INFO, false);
        }

        Logger.log("Nexus successfully started!", Logger.LogLevel.INFO, false);
    }

    @Override
    public void onDisable() {
        database.dbSource.close();
        Bukkit.getScheduler().cancelTasks(Nexus.getInstance());
        Logger.log("Cancelling all tasks...", Logger.LogLevel.INFO, false);
    }

    public void reload() {
        this.reloadConfig();
        this.langFile = getLangFile();
        localeReader = new LocaleReader(langFile);
    }

    @SuppressWarnings("DataFlowIssue")
    public void registerCommands() {
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
        commands.put("spawn", new SpawnCommand());
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

        try {
            final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            bukkitCommandMap.setAccessible(true);
            CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());

            for (Map.Entry<String, CommandExecutor> entry : commands.entrySet()) {
                String commandName = entry.getKey();
                CommandExecutor commandExecutor = entry.getValue();
                if (Nexus.getInstance().getConfigBoolean("commands." + commandName + ".enable")) {
                    Command command = new Command(commandName) {
                        @Override
                        public boolean execute(CommandSender sender, String label, String[] args) {
                            return commandExecutor.onCommand(sender, this, label, args);
                        }
                    };
                    List<String> aliases = Nexus.getInstance().getConfig().getStringList("commands." + commandName + ".aliases");
                    command.setAliases(aliases);
                    commandMap.register(commandName, command);
                }
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void registerListeners() {
        Bukkit.getServer().getPluginManager().registerEvents(new BookAndSignEditListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new DoorKnockingListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new GlassKnockingListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerQuitListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new ServerListPingListener(), this);
    }

    private void loadIcons() {
        File iconsDir = new File(Nexus.getInstance().getDataFolder() + File.separator + iconsPath);
        if (!iconsDir.exists()) {
            iconsDir.mkdir();
            Nexus.getInstance().saveResource(iconsPath + "server-icon-1.png", false);
            Nexus.getInstance().saveResource(iconsPath + "server-icon-2.png", false);
        }
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

    public int getConfigInt(String path) {
        return Nexus.getInstance().getConfig().getInt(path);
    }
}
