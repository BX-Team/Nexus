package space.bxteam.nexus.core.feature.jail.command;

import com.google.inject.Inject;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import space.bxteam.nexus.annotations.scan.command.CommandDocs;
import space.bxteam.nexus.core.multification.MultificationManager;
import space.bxteam.nexus.feature.jail.JailService;

@Command(name = "setjail")
@Permission("nexus.jail.set")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class SetJailCommand {
    private final JailService jailService;
    private final MultificationManager multificationManager;

    @Execute
    @CommandDocs(description = "Set up a jail location with specified name.", arguments = "<name>")
    void executeSetup(@Context Player player, @Arg String name) {
        if (this.jailService.jailExists(name)) {
            this.multificationManager.create()
                    .player(player.getUniqueId())
                    .notice(translation -> translation.jail().jailLocationExists())
                    .placeholder("{JAIL}", name)
                    .send();
            return;
        }

        Location location = player.getLocation();

        this.jailService.createJailLocation(name, location);
        this.multificationManager.create()
                .player(player.getUniqueId())
                .notice(translation -> translation.jail().jailLocationSet())
                .placeholder("{JAIL}", name)
                .send();
    }

    @Execute
    @CommandDocs(description = "Set up a jail location with specified name and location.", arguments = "<name> <location>")
    void executeSetup(@Context Player player, @Arg String name, @Arg Location location) {
        if (this.jailService.jailExists(name)) {
            this.multificationManager.create()
                    .player(player.getUniqueId())
                    .notice(translation -> translation.jail().jailLocationExists())
                    .placeholder("{JAIL}", name)
                    .send();
            return;
        }

        location.setWorld(player.getWorld());

        this.jailService.createJailLocation(name, location);
        this.multificationManager.create()
                .player(player.getUniqueId())
                .notice(translation -> translation.jail().jailLocationSet())
                .placeholder("{JAIL}", name)
                .send();
    }
}
