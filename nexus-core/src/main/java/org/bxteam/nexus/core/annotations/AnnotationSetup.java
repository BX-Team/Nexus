package org.bxteam.nexus.core.annotations;

import com.google.inject.Inject;
import com.google.inject.Injector;
import org.bukkit.Server;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bxteam.commons.logger.ExtendedLogger;
import org.bxteam.commons.scheduler.Scheduler;
import org.bxteam.nexus.core.utils.ClassgraphUtil;
import org.bxteam.nexus.core.annotations.component.Controller;
import org.bxteam.nexus.core.annotations.component.Task;

public class AnnotationSetup {
    private final Injector injector;
    private final Server server;
    private final Plugin plugin;
    private final ExtendedLogger logger;

    @Inject
    public AnnotationSetup(Injector injector, Server server, Plugin plugin, ExtendedLogger logger) {
        this.injector = injector;
        this.server = server;
        this.plugin = plugin;
        this.logger = logger;

        registerControllers();
        registerTasks();
    }

    private void registerControllers() {
        ClassgraphUtil.scanClassesWithAnnotation("org.bxteam.nexus.core", Controller.class, classInfo -> {
            try {
                Class<?> listenerClass = classInfo.loadClass();

                if (Listener.class.isAssignableFrom(listenerClass)) {
                    Listener listenerInstance = (Listener) injector.getInstance(listenerClass);

                    server.getPluginManager().registerEvents(listenerInstance, plugin);
                }
            } catch (Exception e) {
                logger.error("Failed to register listener: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }

    private void registerTasks() {
        ClassgraphUtil.scanClassesWithAnnotation("org.bxteam.nexus.core", Task.class, classInfo -> {
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
                logger.error("Failed to register task: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }
}
