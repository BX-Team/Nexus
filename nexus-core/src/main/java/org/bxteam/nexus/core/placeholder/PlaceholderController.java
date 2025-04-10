package org.bxteam.nexus.core.placeholder;

import com.google.inject.Inject;
import lombok.RequiredArgsConstructor;
import org.bukkit.Server;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bxteam.nexus.core.annotations.component.Controller;
import org.bxteam.nexus.event.NexusInitializeEvent;

@Controller
@RequiredArgsConstructor(onConstructor_ = @__(@Inject))
public class PlaceholderController implements Listener {
    private final Server server;
    private final PlaceholderRegistry placeholderRegistry;

    @EventHandler
    public void onInitialize(NexusInitializeEvent event) {
        placeholderRegistry.registerPlaceholder(PlaceholderReplacer.of("online", (text, player) -> String.valueOf(this.server.getOnlinePlayers().stream().count())));
    }
}
