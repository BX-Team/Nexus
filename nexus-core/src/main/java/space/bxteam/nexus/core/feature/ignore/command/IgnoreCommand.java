package space.bxteam.nexus.core.feature.ignore.command;

import com.google.inject.Inject;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import space.bxteam.nexus.annotations.scan.command.CommandDocs;
import space.bxteam.nexus.core.event.EventCaller;
import space.bxteam.nexus.core.multification.MultificationManager;
import space.bxteam.nexus.feature.ignore.IgnoreService;
import space.bxteam.nexus.feature.ignore.event.IgnoreAllEvent;
import space.bxteam.nexus.feature.ignore.event.IgnoreEvent;

import java.util.UUID;

@Command(name = "ignore")
@Permission("nexus.ignore")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class IgnoreCommand {
    private final IgnoreService ignoreService;
    private final MultificationManager multificationManager;
    private final EventCaller eventCaller;

    @Execute
    @CommandDocs(description = "Ignore specified player.", arguments = "<player>")
    void execute(@Context Player sender, @Arg Player target) {
        UUID senderUuid = sender.getUniqueId();
        UUID targetUuid = target.getUniqueId();

        if (sender.equals(target)) {
            this.multificationManager.create()
                    .player(sender.getUniqueId())
                    .notice(translation -> translation.ignore().ignoreSelf())
                    .send();
            return;
        }

        this.ignoreService.isIgnored(senderUuid, targetUuid).thenAccept(isIgnored -> {
            if (isIgnored) {
                this.multificationManager.create()
                        .player(sender.getUniqueId())
                        .notice(translation -> translation.ignore().alreadyIgnored())
                        .send();
                return;
            }

            this.ignoreService.ignore(senderUuid, targetUuid).thenRun(() -> {
                IgnoreEvent event = new IgnoreEvent(senderUuid, targetUuid);
                this.eventCaller.callEvent(event);

                if (event.isCancelled()) {
                    return;
                }

                this.multificationManager.create()
                        .player(sender.getUniqueId())
                        .notice(translation -> translation.ignore().ignoredPlayer())
                        .placeholder("{PLAYER}", target.getName())
                        .send();
            });
        });
    }

    @Execute(name = "-all", aliases = "*")
    @CommandDocs(description = "Ignore all players.")
    void executeAll(@Context Player sender) {
        UUID senderUuid = sender.getUniqueId();

        this.ignoreService.ignoreAll(senderUuid).thenRun(() -> {
            IgnoreAllEvent event = new IgnoreAllEvent(senderUuid);
            this.eventCaller.callEvent(event);

            if (event.isCancelled()) {
                return;
            }

            this.multificationManager.create()
                    .player(senderUuid)
                    .notice(translation -> translation.ignore().ignoreAll())
                    .send();
        });
    }
}
