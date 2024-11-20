package space.bxteam.nexus.commons.position;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

/**
 * A factory class for converting {@link Location} to {@link Position} and vice versa.
 */
public final class PositionFactory {
    private PositionFactory() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }

    /**
     * Converts a {@link Location} to a {@link Position}.
     *
     * @param location the location to convert
     * @return the converted position
     */
    public static Position convert(@NotNull Location location) {
        location.getWorld();

        return new Position(
                location.getX(),
                location.getY(),
                location.getZ(),
                location.getYaw(),
                location.getPitch(),
                location.getWorld().getName());
    }

    /**
     * Converts a {@link Position} to a {@link Location}.
     *
     * @param position the position to convert
     * @return the converted location
     */
    public static Location convert(Position position) {
        World world = Bukkit.getWorld(position.world());

        if (world == null) {
            throw new IllegalStateException("World is not defined");
        }

        return new Location(
                world,
                position.x(),
                position.y(),
                position.z(),
                position.yaw(),
                position.pitch());
    }
}
