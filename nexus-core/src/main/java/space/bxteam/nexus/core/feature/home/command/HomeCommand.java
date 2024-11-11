package space.bxteam.nexus.core.feature.home.command;

import com.google.inject.Inject;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.RootCommand;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import lombok.RequiredArgsConstructor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import space.bxteam.nexus.core.configuration.PluginConfigurationProvider;
import space.bxteam.nexus.core.message.MessageManager;
import space.bxteam.nexus.feature.home.Home;
import space.bxteam.nexus.feature.home.HomeService;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@RootCommand
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class HomeCommand {
    private final MessageManager messageManager;
    private final HomeService homeService;
    private final PluginConfigurationProvider pluginConfiguration;

    @Execute(name = "home")
    @Permission("nexus.home")
    void home(@Context Player player) {
        Collection<Home> playerHomes = this.homeService.getHomes(player.getUniqueId());

        if (playerHomes.isEmpty()) {
            this.messageManager.create()
                    .player(player)
                    .message(translation -> translation.home().noHomes())
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
                this.messageManager.create()
                        .player(player)
                        .sound(Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1)
                        .send();
                return;
            }

            this.messageManager.create()
                    .player(player)
                    .message(translation -> translation.home().homeList())
                    .placeholder("{HOMES}", homes)
                    .send();
            return;
        }

        Home firstHome = playerHomes.iterator().next();

        player.teleport(firstHome.location());
        this.messageManager.create()
                .player(player)
                .sound(Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1)
                .send();
    }

    @Execute(name = "home")
    @Permission("nexus.home")
    void home(@Context Player player, @Arg Home home) {
        player.teleport(home.location());
        this.messageManager.create()
                .player(player)
                .sound(Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1)
                .send();
    }

    @Execute(name = "sethome")
    @Permission("nexus.sethome")
    void sethome(@Context Player player, @Arg String name) {
        this.createHome(player, name);
    }

    @Execute(name = "sethome")
    @Permission("nexus.sethome")
    void sethome(@Context Player player) {
        this.createHome(player, this.pluginConfiguration.configuration().homes().defaultHomeName());
    }

    @Execute(name = "delhome")
    @Permission("nexus.delhome")
    void delhome(@Context Player player, @Arg Home home) {
        this.homeService.deleteHome(player.getUniqueId(), home.name());
        this.messageManager.create()
                .player(player)
                .message(translation -> translation.home().delete())
                .placeholder("{HOME}", home.name())
                .send();
    }

    private void createHome(Player player, String name) {
        UUID playerUUID = player.getUniqueId();

        if (this.homeService.hasHome(playerUUID, name)) {
            this.messageManager.create()
                    .player(player)
                    .message(translation -> translation.home().homeAlreadyExists())
                    .placeholder("{HOME}", name)
                    .send();
            return;
        }

        int amountOfUserHomes = this.homeService.getHomes(player.getUniqueId()).size();
        int maxAmountOfUserHomes = this.homeService.getHomeLimit(player);

        if (amountOfUserHomes >= maxAmountOfUserHomes) {
            this.messageManager.create()
                    .player(player)
                    .message(translation -> translation.home().limit())
                    .placeholder("{LIMIT}", String.valueOf(maxAmountOfUserHomes))
                    .send();
            return;
        }

        this.homeService.createHome(playerUUID, name, player.getLocation());
        this.messageManager.create()
                .player(player)
                .message(translation -> translation.home().create())
                .placeholder("{HOME}", name)
                .send();
    }
}
