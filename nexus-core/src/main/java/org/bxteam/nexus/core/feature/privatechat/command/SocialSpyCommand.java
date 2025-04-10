package org.bxteam.nexus.core.feature.privatechat.command;

import com.google.inject.Inject;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bxteam.nexus.docs.scan.command.CommandDocs;
import org.bxteam.nexus.core.multification.MultificationManager;
import org.bxteam.nexus.feature.privatechat.PrivateChatService;

import java.util.UUID;

@Command(name = "socialspy", aliases = {"ss", "spy"})
@Permission("nexus.socialspy")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class SocialSpyCommand {
    private final PrivateChatService privateChatService;
    private final MultificationManager multificationManager;

    @Execute
    @CommandDocs(description = "Enables or disables social spy")
    void execute(@Context Player player) {
        UUID uuid = player.getUniqueId();

        if (this.privateChatService.isSpyEnabled(uuid)) {
            this.privateChatService.disableSpy(uuid);
            this.notify(uuid);
        } else {
            this.privateChatService.enableSpy(uuid);
            this.notify(uuid);
        }
    }

    private void notify(UUID uuid) {
        this.multificationManager.create()
                .player(uuid)
                .notice(translation -> translation.privateChat().socialSpyStatus())
                .placeholder("{STATE}", translation -> this.privateChatService.isSpyEnabled(uuid)
                    ? translation.format().enable()
                    : translation.format().disable())
                .send();
    }
}
