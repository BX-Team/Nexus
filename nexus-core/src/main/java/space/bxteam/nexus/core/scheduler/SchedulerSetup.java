package space.bxteam.nexus.core.scheduler;

import com.google.inject.AbstractModule;
import lombok.RequiredArgsConstructor;
import org.bukkit.plugin.Plugin;
import space.bxteam.commons.bukkit.scheduler.BukkitScheduler;
import space.bxteam.commons.scheduler.Scheduler;

@RequiredArgsConstructor
public class SchedulerSetup extends AbstractModule {
    private final Plugin plugin;

    @Override
    protected void configure() {
        this.bind(Scheduler.class).toInstance(new BukkitScheduler(this.plugin));
    }
}
