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
import space.bxteam.nexus.feature.home.Home;
import space.bxteam.nexus.feature.home.HomeService;

import java.util.Collection;
import java.util.Optional;

@Command(name = "home")
@Permission("nexus.home")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class HomeCommand {
    private final MultificationManager multificationManager;
    private final HomeService homeService;
    private final PluginConfigurationProvider pluginConfiguration;

    @Execute
    void home(@Context Player player) {
        Collection<Home> playerHomes = this.homeService.getHomes(player.getUniqueId());

        if (playerHomes.isEmpty()) {
            this.multificationManager.create()
                    .player(player.getUniqueId())
                    .notice(translation -> translation.home().noHomes())
                    .send();
            return;
        }

        if (playerHomes.size() > 1) {
            String homes = String.join(
                    ", ",
                    playerHomes.stream()
                            .map(Home::name)
                            .toList());

            Optional<Home> mainHome = playerHomes.stream()
                    .filter(home -> home.name().equals(this.pluginConfiguration.configuration().homes().defaultHomeName()))
                    .findFirst();

            if (mainHome.isPresent()) {
                player.teleport(mainHome.get().location());
                // TODO: Add sound
                return;
            }

            this.multificationManager.create()
                    .player(player.getUniqueId())
                    .notice(translation -> translation.home().homeList())
                    .placeholder("{HOMES}", homes)
                    .send();
            return;
        }

        Home firstHome = playerHomes.iterator().next();

        player.teleport(firstHome.location());
        // TODO: Add sound
    }

    @Execute
    void home(@Context Player player, @Arg Home home) {
        player.teleport(home.location());
        // TODO: Add sound
    }
}
