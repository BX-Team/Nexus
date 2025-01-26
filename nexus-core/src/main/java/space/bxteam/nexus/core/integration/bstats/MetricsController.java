package space.bxteam.nexus.core.integration.bstats;

import com.google.inject.Inject;
import lombok.RequiredArgsConstructor;
import org.bstats.bukkit.Metrics;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import space.bxteam.nexus.core.registration.annotations.component.Controller;
import space.bxteam.nexus.event.NexusInitializeEvent;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class MetricsController implements Listener {
    private final Plugin plugin;

    @EventHandler
    public void onInitialize(NexusInitializeEvent event) {
        new Metrics(this.plugin, 19684);
    }
}
