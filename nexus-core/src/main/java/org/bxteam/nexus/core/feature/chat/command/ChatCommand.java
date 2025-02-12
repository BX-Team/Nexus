package org.bxteam.nexus.core.feature.chat.command;

import com.eternalcode.multification.notice.Notice;
import com.google.inject.Inject;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.command.CommandSender;
import org.bxteam.nexus.annotations.scan.command.CommandDocs;
import org.bxteam.nexus.core.configuration.plugin.PluginConfigurationProvider;
import org.bxteam.nexus.core.multification.MultificationManager;
import org.bxteam.nexus.feature.chat.ChatService;

import java.util.function.Supplier;

@Command(name = "chat")
@Permission("nexus.chat")
public class ChatCommand {
    private final ChatService chatService;
    private final MultificationManager multificationManager;

    private final Supplier<Notice> clear;

    @Inject
    public ChatCommand(ChatService chatService, MultificationManager multificationManager, PluginConfigurationProvider configurationProvider) {
        this.chatService = chatService;
        this.multificationManager = multificationManager;
        this.clear = create(configurationProvider);
    }

    @Execute(name = "clear", aliases = {"cc"})
    @CommandDocs(description = "Clears the chat.")
    void clear(@Context CommandSender sender) {
        this.multificationManager.create()
                .notice(this.clear.get())
                .notice(translation -> translation.chat().cleared())
                .placeholder("{PLAYER}", sender.getName())
                .onlinePlayers()
                .send();
    }

    @Execute(name = "on")
    @CommandDocs(description = "Enables the chat.")
    void on(@Context CommandSender sender) {
        if (this.chatService.isChatEnabled()) {
            this.multificationManager.create()
                    .viewer(sender)
                    .notice(translation -> translation.chat().alreadyEnabled())
                    .send();
            return;
        }

        this.chatService.setChatEnabled(true);
        this.multificationManager.create()
                .onlinePlayers()
                .notice(translation -> translation.chat().enabled())
                .placeholder("{PLAYER}", sender.getName())
                .send();
    }

    @Execute(name = "off")
    @CommandDocs(description = "Disables the chat.")
    void off(@Context CommandSender sender) {
        if (!this.chatService.isChatEnabled()) {
            this.multificationManager.create()
                    .viewer(sender)
                    .notice(translation -> translation.chat().alreadyDisabled())
                    .send();
            return;
        }

        this.chatService.setChatEnabled(false);
        this.multificationManager.create()
                .onlinePlayers()
                .notice(translation -> translation.chat().disabled())
                .placeholder("{PLAYER}", sender.getName())
                .send();
    }

    private static Supplier<Notice> create(PluginConfigurationProvider configurationProvider) {
        int line = configurationProvider.configuration().chat().clearLines();
        return () -> Notice.chat("<newline>".repeat(Math.max(0, line)));
    }
}
