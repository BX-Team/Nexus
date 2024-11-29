package space.bxteam.nexus.core.feature.teleport.command;

import com.eternalcode.multification.shared.Formatter;
import com.google.inject.Inject;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import space.bxteam.nexus.core.multification.formatter.MultificationFormatter;
import space.bxteam.nexus.core.multification.MultificationManager;
import space.bxteam.nexus.feature.teleport.TeleportService;

@Command(name = "teleport", aliases = {"tp"})
@Permission("nexus.teleport")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class TeleportCommand {
    private final TeleportService teleportService;
    private final MultificationManager multificationManager;

    private static final MultificationFormatter<TeleportContext> CONTEXT = MultificationFormatter.<TeleportContext>builder()
            .with("{PLAYER}", context -> context.player().getName())
            .with("{X}", context -> String.valueOf(context.location().getX()))
            .with("{Y}", context -> String.valueOf(context.location().getY()))
            .with("{Z}", context -> String.valueOf(context.location().getZ()))
            .build();

    private static final MultificationFormatter<Player> OTHER_PLAYER = MultificationFormatter.<Player>builder()
            .with("{SENDER}", Player::getName)
            .build();

    @Execute
    void execute(@Context Player sender, @Arg Player target) {
        this.teleportService.teleport(sender, target.getLocation());

        var formatter = formatter(sender, target.getLocation());
        this.multificationManager.create()
                .player(sender.getUniqueId())
                .notice(translation -> translation.teleport().teleportedToPlayer())
                .formatter(formatter)
                .send();
    }

    @Execute
    void execute(@Context CommandSender sender, @Arg Player player, @Arg Player target) {
        this.teleportService.teleport(player, target.getLocation());

        var formatter = formatter(player, target.getLocation());
        var otherFormatter = OTHER_PLAYER.toFormatter(player);
        this.multificationManager.create()
                .viewer(sender)
                .notice(translation -> translation.teleport().teleportedPlayerToPlayer())
                .formatter(formatter, otherFormatter)
                .send();
    }

    @Execute
    void location(@Context Player sender, @Arg Location location) {
        location.setWorld(sender.getWorld());
        this.teleportService.teleport(sender, location);

        var formatter = formatter(sender, location);
        this.multificationManager.create()
                .player(sender.getUniqueId())
                .notice(translation -> translation.teleport().teleportedToCoordinates())
                .formatter(formatter)
                .send();
    }

    @Execute
    void location(@Context Player sender, @Arg Location location, @Arg World world) {
        location.setWorld(world);
        this.teleportService.teleport(sender, location);

        var formatter = formatter(sender, location);
        this.multificationManager.create()
                .player(sender.getUniqueId())
                .notice(translation -> translation.teleport().teleportedToCoordinates())
                .formatter(formatter)
                .send();
    }

    @Execute
    void locationOther(@Context CommandSender sender, @Arg Player target, @Arg Location location) {
        location.setWorld(target.getWorld());
        this.teleportService.teleport(target, location);

        var formatter = formatter(target, location);
        this.multificationManager.create()
                .viewer(sender)
                .notice(translation -> translation.teleport().teleportedSpecifiedPlayerToCoordinates())
                .formatter(formatter)
                .send();
    }

    @Execute
    void locationOther(@Context CommandSender sender, @Arg Player target, @Arg Location location, @Arg World world) {
        location.setWorld(world);
        this.teleportService.teleport(target, location);

        var formatter = formatter(target, location);
        this.multificationManager.create()
                .viewer(sender)
                .notice(translation -> translation.teleport().teleportedSpecifiedPlayerToCoordinates())
                .formatter(formatter)
                .send();
    }

    private Formatter formatter(Player player, Location location) {
        return CONTEXT.toFormatter(new TeleportContext(player, location));
    }

    private record TeleportContext(Player player, Location location) { }
}
