package space.bxteam.nexus.core.feature.jail.command;

import com.google.inject.Inject;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.CommandSender;
import space.bxteam.nexus.annotations.scan.command.CommandDocs;
import space.bxteam.nexus.core.multification.MultificationManager;
import space.bxteam.nexus.feature.jail.JailService;

@Command(name = "deljail")
@Permission("nexus.jail.delete")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class DelJailCommand {
    private final JailService jailService;
    private final MultificationManager multificationManager;

    @Execute
    @CommandDocs(description = "Remove a jail location with specified name.", arguments = "<name>")
    void executeRemove(@Context CommandSender sender, @Arg(JailCommandArgument.KEY) String name) {
        if (!this.jailService.jailExists(name)) {
            this.multificationManager.create()
                    .viewer(sender)
                    .notice(translation -> translation.jail().jailLocationNotExists())
                    .placeholder("{JAIL}", name)
                    .send();
            return;
        }

        this.jailService.removeJailArea(name);
        this.multificationManager.create()
                .viewer(sender)
                .notice(translation -> translation.jail().jailLocationRemove())
                .placeholder("{JAIL}", name)
                .send();
    }
}
