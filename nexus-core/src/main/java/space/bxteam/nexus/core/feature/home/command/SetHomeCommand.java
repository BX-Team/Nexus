package space.bxteam.nexus.core.feature.home.command;

import com.google.inject.Inject;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import space.bxteam.nexus.core.configuration.plugin.PluginConfigurationProvider;
import space.bxteam.nexus.core.multification.MultificationManager;
import space.bxteam.nexus.feature.home.HomeService;

import java.util.UUID;

@Command(name = "sethome")
@Permission("nexus.sethome")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class SetHomeCommand {
    private final MultificationManager multificationManager;
    private final HomeService homeService;
    private final PluginConfigurationProvider pluginConfiguration;

    @Execute
    void sethome(@Context Player player) {
        this.createHome(player, this.pluginConfiguration.configuration().homes().defaultHomeName());
    }

    @Execute
    void sethome(@Context Player player, @Arg String name) {
        this.createHome(player, name);
    }

    private void createHome(Player player, String name) {
        UUID playerUUID = player.getUniqueId();

        if (this.homeService.hasHome(playerUUID, name)) {
            this.multificationManager.create()
                    .player(player.getUniqueId())
                    .notice(translation -> translation.home().homeAlreadyExists())
                    .placeholder("{HOME}", name)
                    .send();
            return;
        }

        int amountOfUserHomes = this.homeService.getHomes(player.getUniqueId()).size();
        int maxAmountOfUserHomes = this.homeService.getHomeLimit(player);

        if (amountOfUserHomes >= maxAmountOfUserHomes) {
            this.multificationManager.create()
                    .player(player.getUniqueId())
                    .notice(translation -> translation.home().limit())
                    .placeholder("{LIMIT}", String.valueOf(maxAmountOfUserHomes))
                    .send();
            return;
        }

        this.homeService.createHome(playerUUID, name, player.getLocation());
        this.multificationManager.create()
                .player(player.getUniqueId())
                .notice(translation -> translation.home().create())
                .placeholder("{HOME}", name)
                .send();
    }
}
