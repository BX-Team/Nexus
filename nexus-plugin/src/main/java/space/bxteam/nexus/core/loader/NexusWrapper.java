package space.bxteam.nexus.core.loader;

import org.bukkit.plugin.Plugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class NexusWrapper {
    private static final String LOADER_CORE_CLASS = "space.bxteam.nexus.core.Nexus";

    private final Class<?> nexusCoreClass;
    private Object nexus;

    NexusWrapper(Class<?> nexusCoreClass) {
        this.nexusCoreClass = nexusCoreClass;
    }

    public void enable(Plugin plugin) {
        try {
            Constructor<?> constructor = this.nexusCoreClass.getConstructor(Plugin.class);
            constructor.setAccessible(true);

            this.nexus = constructor.newInstance(plugin);
        } catch (InvocationTargetException exception) {
            if (exception.getCause() instanceof RuntimeException runtimeException) {
                throw runtimeException;
            }

            throw new RuntimeException("Could not enable Nexus: ", exception.getCause());
        } catch (IllegalAccessException | NoSuchMethodException | InstantiationException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void disable() {
        try {
            Method disableMethod = this.nexusCoreClass.getMethod("disable");

            disableMethod.setAccessible(true);

            if (this.nexus != null) {
                disableMethod.invoke(this.nexus);
            }
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException exception) {
            throw new RuntimeException(exception);
        }
    }

    public static NexusWrapper create(ClassLoader loader) {
        try {
            Class<?> nexusCoreClass = Class.forName(LOADER_CORE_CLASS, true, loader);

            return new NexusWrapper(nexusCoreClass);
        } catch (ClassNotFoundException exception) {
            throw new RuntimeException(exception);
        }
    }
}
