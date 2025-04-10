package org.bxteam.nexus.core.feature.afk.command;

import com.google.inject.Inject;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bxteam.nexus.docs.scan.command.CommandDocs;
import org.bxteam.nexus.feature.afk.AfkReason;
import org.bxteam.nexus.feature.afk.AfkService;

import java.util.UUID;

@Command(name = "afk")
@Permission("nexus.afk")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class AfkCommand {
    private final AfkService afkService;

    @Execute
    @CommandDocs(description = "Mark yourself as AFK")
    void execute(@Context Player player) {
        UUID playerUuid = player.getUniqueId();

        this.afkService.switchAfk(playerUuid, AfkReason.COMMAND);
    }
}
