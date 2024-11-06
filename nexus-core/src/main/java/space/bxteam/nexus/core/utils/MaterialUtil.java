package space.bxteam.nexus.core.utils;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

public final class MaterialUtil {
    private MaterialUtil() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }

    public static String format(@NotNull Material material) {
        return StringUtils.capitalize(material.name().toLowerCase().replace("_", " "));
    }
}
