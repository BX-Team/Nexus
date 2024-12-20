package space.bxteam.nexus.core.feature.home.command;

import com.google.inject.Inject;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import space.bxteam.nexus.annotations.scan.command.CommandDocs;
import space.bxteam.nexus.core.multification.MultificationManager;
import space.bxteam.nexus.feature.home.Home;
import space.bxteam.nexus.feature.home.HomeService;

@Command(name = "delhome")
@Permission("nexus.delhome")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class DelHomeCommand {
    private final MultificationManager multificationManager;
    private final HomeService homeService;

    @Execute
    @CommandDocs(description = "Delete your home.", arguments = "<home>")
    void delhome(@Context Player player, @Arg Home home) {
        this.homeService.deleteHome(player.getUniqueId(), home.name());
        this.multificationManager.create()
                .player(player.getUniqueId())
                .notice(translation -> translation.home().delete())
                .placeholder("{HOME}", home.name())
                .send();
    }
}
