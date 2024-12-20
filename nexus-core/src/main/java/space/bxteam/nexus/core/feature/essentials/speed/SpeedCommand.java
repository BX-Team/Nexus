package space.bxteam.nexus.core.feature.essentials.speed;

import com.google.inject.Inject;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import space.bxteam.nexus.annotations.scan.command.CommandDocs;
import space.bxteam.nexus.core.multification.MultificationManager;

@Command(name = "speed")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class SpeedCommand {
    private final MultificationManager multificationManager;

    @Execute
    @Permission("nexus.speed")
    @CommandDocs(description = "Change your speed.", arguments = "<speed>")
    void execute(@Context Player player, @Arg(SpeedCommandArgument.KEY) Integer speed) {
        this.setSpeed(player, speed);

        this.multificationManager.create()
                .player(player.getUniqueId())
                .notice(translation -> player.isFlying() ? translation.player().speedFlySet() : translation.player().speedWalkSet())
                .placeholder("{SPEED}", String.valueOf(speed))
                .send();
    }

    @Execute
    @Permission("nexus.speed.other")
    @CommandDocs(description = "Change the speed of another player.", arguments = "<speed> <player>")
    void execute(@Context CommandSender sender, @Arg(SpeedCommandArgument.KEY) Integer speed, @Arg Player target) {
        this.setSpeed(target, speed);

        this.multificationManager.create()
                .player(target.getUniqueId())
                .notice(translation -> target.isFlying() ? translation.player().speedFlySet() : translation.player().speedWalkSet())
                .placeholder("{SPEED}", String.valueOf(speed))
                .send();

        this.multificationManager.create()
                .viewer(sender)
                .notice(translation -> target.isFlying() ? translation.player().speedFlySetBy() : translation.player().speedWalkSetBy())
                .placeholder("{PLAYER}", target.getName())
                .placeholder("{SPEED}", String.valueOf(speed))
                .send();
    }

    @Execute
    @Permission("nexus.speed")
    @CommandDocs(description = "Set speed of walking or flying to specified amount", arguments = "<type> <speed>")
    void execute(@Context Player player, @Arg SpeedType speedType, @Arg(SpeedCommandArgument.KEY) Integer speed) {
        this.setSpeed(player, speedType, speed);

        this.multificationManager.create()
                .player(player.getUniqueId())
                .notice(translation -> speedType == SpeedType.WALK ? translation.player().speedWalkSet() : translation.player().speedFlySet())
                .placeholder("{SPEED}", String.valueOf(speed))
                .send();
    }

    @Execute
    @Permission("nexus.speed.other")
    @CommandDocs(description = "Set speed of walking or flying to specified amount for another player", arguments = "<type> <speed> <player>")
    void execute(@Context CommandSender sender, @Arg SpeedType speedType, @Arg(SpeedCommandArgument.KEY) Integer speed, @Arg Player target) {
        this.setSpeed(target, speedType, speed);

        this.multificationManager.create()
                .player(target.getUniqueId())
                .notice(translation -> speedType == SpeedType.WALK ? translation.player().speedWalkSet() : translation.player().speedFlySet())
                .placeholder("{SPEED}", String.valueOf(speed))
                .send();

        this.multificationManager.create()
                .viewer(sender)
                .notice(translation -> speedType == SpeedType.WALK ? translation.player().speedWalkSetBy() : translation.player().speedFlySetBy())
                .placeholder("{PLAYER}", target.getName())
                .placeholder("{SPEED}", String.valueOf(speed))
                .send();
    }

    private void setSpeed(Player player, int speed) {
        if (player.isFlying()) {
            player.setFlySpeed(speed / 10.0f);
        }
        else {
            player.setWalkSpeed(speed / 10.0f);
        }
    }

    private void setSpeed(Player player, SpeedType speedType, int speed) {
        switch (speedType) {
            case WALK -> player.setWalkSpeed(speed / 10.0f);
            case FLY -> player.setFlySpeed(speed / 10.0f);
            default -> throw new IllegalStateException("Unexpected value: " + speedType);
        }
    }
}
