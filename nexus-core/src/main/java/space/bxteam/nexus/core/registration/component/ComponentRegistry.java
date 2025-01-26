package space.bxteam.nexus.core.registration.component;

import com.google.inject.Inject;
import com.google.inject.Injector;
import org.bukkit.Server;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import space.bxteam.commons.scheduler.Scheduler;
import space.bxteam.nexus.core.utils.ClassgraphUtil;
import space.bxteam.nexus.core.registration.annotations.component.Controller;
import space.bxteam.nexus.core.registration.annotations.component.Task;
import space.bxteam.nexus.core.utils.Logger;

public class ComponentRegistry {
    private final Injector injector;
    private final Server server;
    private final Plugin plugin;

    @Inject
    public ComponentRegistry(Injector injector, Server server, Plugin plugin) {
        this.injector = injector;
        this.server = server;
        this.plugin = plugin;

        registerControllers();
        registerTasks();
    }

    private void registerControllers() {
        ClassgraphUtil.scanClassesWithAnnotation("space.bxteam.nexus.core", Controller.class, classInfo -> {
            try {
                Class<?> listenerClass = classInfo.loadClass();

                if (Listener.class.isAssignableFrom(listenerClass)) {
                    Listener listenerInstance = (Listener) injector.getInstance(listenerClass);

                    server.getPluginManager().registerEvents(listenerInstance, plugin);
                }
            } catch (Exception e) {
                Logger.log("Failed to register listener: " + e.getMessage(), Logger.LogLevel.ERROR);
                e.printStackTrace();
            }
        });
    }

    private void registerTasks() {
        ClassgraphUtil.scanClassesWithAnnotation("space.bxteam.nexus.core", Task.class, classInfo -> {
            try {
                Class<?> taskClass = classInfo.loadClass();
                Scheduler scheduler = injector.getInstance(Scheduler.class);

                if (Runnable.class.isAssignableFrom(taskClass)) {
                    Runnable taskInstance = (Runnable) injector.getInstance(taskClass);
                    Task taskAnnotation = taskClass.getAnnotation(Task.class);
                    long delay = taskAnnotation.delay();
                    long period = taskAnnotation.period();

                    if (period == 0L) {
                        scheduler.runTaskLater(taskInstance, delay);
                    } else {
                        scheduler.runTaskTimer(taskInstance, delay, period);
                    }
                }
            } catch (Exception e) {
                Logger.log("Failed to register task: " + e.getMessage(), Logger.LogLevel.ERROR);
                e.printStackTrace();
            }
        });
    }
}
