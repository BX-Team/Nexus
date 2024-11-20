package space.bxteam.nexus.commons.position;

import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a position in the world.
 */
public record Position(double x, double y, double z, float yaw, float pitch, String world) {
    public static final String NONE_WORLD = "__NONE__";

    private static final Pattern PARSE_FORMAT = Pattern.compile(
            "Position\\{x=(?<x>-?[\\d.]+), y=(?<y>-?[\\d.]+), z=(?<z>-?[\\d.]+), yaw=(?<yaw>-?[\\d.]+), pitch=(?<pitch>-?[\\d.]+), world='(?<world>.+)'}");

    public Position(double x, double y, double z, String world) {
        this(x, y, z, 0F, 0F, world);
    }

    public Position(double x, double z, String world) {
        this(x, 0.0, z, 0F, 0F, world);
    }

    public static @NotNull Position parse(String parse) {
        Matcher matcher = PARSE_FORMAT.matcher(parse);

        if (!matcher.find()) {
            throw new IllegalArgumentException("Invalid position format: " + parse);
        }

        return new Position(
                Double.parseDouble(matcher.group("x")),
                Double.parseDouble(matcher.group("y")),
                Double.parseDouble(matcher.group("z")),
                Float.parseFloat(matcher.group("yaw")),
                Float.parseFloat(matcher.group("pitch")),
                matcher.group("world")
        );
    }

    @Override
    public @NotNull String toString() {
        return String.format("Position{x=%.2f, y=%.2f, z=%.2f, yaw=%.2f, pitch=%.2f, world='%s'}",
                x, y, z, yaw, pitch, world);
    }

    public boolean isNoneWorld() {
        return NONE_WORLD.equals(world);
    }
}