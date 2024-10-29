package space.bxteam.nexus.core.loader;

import org.bukkit.plugin.Plugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class NexusWrapper {
    private static final String NEXUS_CLASS_NAME = "space.bxteam.nexus.core.Nexus";

    private final Class<?> nexusCoreClass;
    private Object nexusInstance;

    private NexusWrapper(Class<?> nexusCoreClass) {
        this.nexusCoreClass = nexusCoreClass;
    }

    public void initialize(Plugin plugin) {
        try {
            Constructor<?> nexusConstructor = nexusCoreClass.getConstructor(Plugin.class);
            nexusConstructor.setAccessible(true);
            this.nexusInstance = nexusConstructor.newInstance(plugin);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("Failed to initialize Nexus due to constructor issue: ", e);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof RuntimeException) {
                throw (RuntimeException) cause;
            } else {
                throw new RuntimeException("Unexpected error during Nexus initialization: ", cause);
            }
        }
    }

    public void terminate() {
        if (nexusInstance == null) {
            return;
        }

        try {
            Method shutdownMethod = nexusCoreClass.getMethod("disable");
            shutdownMethod.setAccessible(true);
            shutdownMethod.invoke(nexusInstance);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Failed to terminate Nexus: ", e);
        }
    }

    public static NexusWrapper load(ClassLoader classLoader) {
        try {
            Class<?> coreClass = Class.forName(NEXUS_CLASS_NAME, true, classLoader);
            return new NexusWrapper(coreClass);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Nexus core class not found in specified classloader: ", e);
        }
    }
}
