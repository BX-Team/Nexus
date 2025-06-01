package org.bxteam.nexus.core.feature.signeditor;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.join.Join;
import dev.rollczi.litecommands.annotations.permission.Permission;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.block.sign.Side;
import org.bukkit.block.sign.SignSide;
import org.bukkit.entity.Player;
import org.bxteam.commons.adventure.util.AdventureUtil;
import org.bxteam.nexus.core.annotations.compatibility.Compatibility;
import org.bxteam.nexus.core.annotations.compatibility.Version;
import org.bxteam.nexus.core.multification.MultificationManager;
import org.bxteam.nexus.docs.scan.command.CommandDocs;

@Command(name = "signeditor")
@Permission("nexus.signeditor")
@Compatibility(from = @Version(minor = 20, patch = 0))
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class SignSideEditorCommand {
    private final MultificationManager multificationManager;
    @Named("colorMiniMessage")
    private final MiniMessage miniMessage;

    @SuppressWarnings("DuplicatedCode")
    @Execute(name = "setline")
    @CommandDocs(description = "Set the text of a specific line on a chosen side of the sign you are targeting.", arguments = "<side> <line> <text...>")
    void execute(@Context Player player, @Arg Side side, @Arg(SignEditorArgument.KEY) int line, @Join String text) {
        Block targetBlock = player.getTargetBlockExact(5);

        if (targetBlock == null || !(targetBlock.getState() instanceof Sign sign)) {
            this.multificationManager.create()
                    .player(player.getUniqueId())
                    .notice(translation -> translation.signEditor().noSignFound())
                    .send();
            return;
        }

        SignSide signSide = sign.getSide(side);
        signSide.setLine(line - 1, AdventureUtil.SECTION_SERIALIZER.serialize(this.miniMessage.deserialize(text)));
        sign.update();

        this.multificationManager.create()
                .player(player.getUniqueId())
                .placeholder("{LINE}", String.valueOf(line))
                .placeholder("{TEXT}", text)
                .notice(translation -> translation.signEditor().lineSet())
                .send();
    }
}
