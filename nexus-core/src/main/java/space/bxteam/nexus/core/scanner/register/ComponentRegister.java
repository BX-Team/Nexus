package space.bxteam.nexus.core.scanner.register;

import com.google.inject.Inject;
import com.google.inject.Injector;
import org.bukkit.Server;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import space.bxteam.nexus.core.scanner.ClassgraphScanner;
import space.bxteam.nexus.core.scanner.annotations.component.Controller;
import space.bxteam.nexus.core.utils.Logger;

public class ComponentRegister {
    private final Injector injector;
    private final Server server;
    private final Plugin plugin;

    @Inject
    public ComponentRegister(Injector injector, Server server, Plugin plugin) {
        this.injector = injector;
        this.server = server;
        this.plugin = plugin;

        registerControllers();
    }

    private void registerControllers() {
        ClassgraphScanner.scanClassesWithAnnotation("space.bxteam.nexus.core", Controller.class, classInfo -> {
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
}
