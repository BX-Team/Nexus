package space.bxteam.nexus.core.utils;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

/**
 * Utility class for handling {@link Material} manipulations.
 */
public final class MaterialUtil {
    private MaterialUtil() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }

    /**
     * Formats the {@link Material} into a human-readable string.
     * @param material the material to format
     * @return the formatted material name
     */
    public static String format(@NotNull Material material) {
        return StringUtils.capitalize(material.name().toLowerCase().replace("_", " "));
    }
}
