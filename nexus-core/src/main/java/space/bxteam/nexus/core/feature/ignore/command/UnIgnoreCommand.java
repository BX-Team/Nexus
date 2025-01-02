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
import space.bxteam.nexus.feature.ignore.event.UnIgnoreAllEvent;
import space.bxteam.nexus.feature.ignore.event.UnIgnoreEvent;

import java.util.UUID;

@Command(name = "unignore")
@Permission("nexus.unignore")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class UnIgnoreCommand {
    private final IgnoreService ignoreService;
    private final MultificationManager multificationManager;
    private final EventCaller eventCaller;

    @Execute
    @CommandDocs(description = "Unignore specified player.", arguments = "<player>")
    void execute(@Context Player sender, @Arg Player target) {
        UUID senderUuid = sender.getUniqueId();
        UUID targetUuid = target.getUniqueId();

        if (sender.equals(target)) {
            this.multificationManager.create()
                    .player(sender.getUniqueId())
                    .notice(translation -> translation.ignore().unIgnoreSelf())
                    .send();
            return;
        }

        this.ignoreService.isIgnored(senderUuid, targetUuid).thenAccept(isIgnored -> {
            if (!isIgnored) {
                this.multificationManager.create()
                        .player(senderUuid)
                        .notice(translation -> translation.ignore().notIgnored())
                        .send();
                return;
            }

            this.ignoreService.unIgnore(senderUuid, targetUuid).thenRun(() -> {
                UnIgnoreEvent event = new UnIgnoreEvent(senderUuid, targetUuid);
                this.eventCaller.callEvent(event);

                if (event.isCancelled()) {
                    return;
                }

                this.multificationManager.create()
                        .player(senderUuid)
                        .notice(translation -> translation.ignore().unIgnoredPlayer())
                        .placeholder("{PLAYER}", target.getName())
                        .send();
            });
        });
    }

    @Execute(name = "-all", aliases = "*")
    @CommandDocs(description = "Unignore all players.")
    void executeAll(@Context Player sender) {
        UUID senderUuid = sender.getUniqueId();

        this.ignoreService.unIgnoreAll(senderUuid).thenRun(() -> {
            UnIgnoreAllEvent event = new UnIgnoreAllEvent(senderUuid);
            this.eventCaller.callEvent(event);

            if (event.isCancelled()) {
                return;
            }

            this.multificationManager.create()
                    .player(senderUuid)
                    .notice(translation -> translation.ignore().unIgnoreAll())
                    .send();
        });
    }
}
