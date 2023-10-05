package gq.bxteam.nexus;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import gq.bxteam.nexus.commands.list.GamemodeCommand;
import gq.bxteam.nexus.commands.list.NexusCommand;
import gq.bxteam.nexus.utils.Metrics;
import gq.bxteam.nexus.utils.logger.Logger;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

public final class Nexus extends JavaPlugin {
    public static ArrayList<UUID> chEnabled = new ArrayList<>();
    public static Nexus instance;

    public static Nexus getInstance() {
        return Nexus.instance;
    }

    @Override
    public void onEnable() {
        Nexus.instance = this;

        // Metrics
        new Metrics(Nexus.getInstance(), 19684);

        // TODO: Update Checker

        // Save config.yml
        this.saveDefaultConfig();

        // Register commands
        registerCommands();

        Logger.log("Nexus successfully started!", Logger.LogLevel.INFO, false);
    }

    @Override
    public void onDisable() {
        Bukkit.getScheduler().cancelTasks(Nexus.getInstance());
    }

    @SuppressWarnings("DataFlowIssue")
    private void registerCommands() {
        getCommand("gamemode").setExecutor(new GamemodeCommand());
        getCommand("nexus").setExecutor(new NexusCommand());
    }

    @SuppressWarnings("deprecation")
    public static Optional<String> checkForUpdates() {
        final String mcVersion = Nexus.getInstance().getServer().getMinecraftVersion();
        final String pluginName = Nexus.getInstance().getDescription().getName();
        final String pluginVersion = Nexus.getInstance().getDescription().getVersion();
        try {
            final HttpClient client = HttpClient.newHttpClient();
            final HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.modrinth.com/v2/project/4ut4u8Ay/version?featured=true&game_versions=[%22" + mcVersion + "%22]"))
                    .header("User-Agent",
                            pluginName + "/" + pluginVersion
                    )
                    .GET()
                    .build();
            final HttpResponse<String> res = client.send(req, HttpResponse.BodyHandlers.ofString());
            if (res.statusCode() < 400 && res.statusCode() >= 200 && res.body() != null) {
                final JsonObject json = JsonParser.parseString(res.body()).getAsJsonArray().get(0).getAsJsonObject();
                if (json.has("version_number")) {
                    final String latestVersion = json.get("version_number").getAsString();
                    if (!latestVersion.equals(pluginVersion))
                        return Optional.of(latestVersion);
                }
            }
        }
        catch (final Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public String getConfigString(String path) {
        return Nexus.getInstance().getConfig().getString(path);
    }

    public boolean getConfigBoolean(String path) {
        return Nexus.getInstance().getConfig().getBoolean(path);
    }
}
