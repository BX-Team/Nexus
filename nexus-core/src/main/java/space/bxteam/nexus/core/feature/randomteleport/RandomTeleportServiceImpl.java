package space.bxteam.nexus.core.feature.randomteleport;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.papermc.lib.PaperLib;
import lombok.RequiredArgsConstructor;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import space.bxteam.nexus.core.configuration.plugin.PluginConfigurationProvider;
import space.bxteam.nexus.core.event.EventCaller;
import space.bxteam.nexus.feature.randomteleport.RandomTeleportService;
import space.bxteam.nexus.feature.randomteleport.RandomTeleportResult;
import space.bxteam.nexus.feature.randomteleport.event.PreRandomTeleportEvent;
import space.bxteam.nexus.feature.randomteleport.event.RandomTeleportEvent;

import java.util.EnumSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
@Singleton
public class RandomTeleportServiceImpl implements RandomTeleportService {
    private final PluginConfigurationProvider configurationProvider;
    private final Server server;
    private final EventCaller eventCaller;
    private final Random random = new Random();

    private static final Set<Material> UNSAFE_BLOCKS = EnumSet.of(
            Material.LAVA, Material.WATER, Material.CACTUS, Material.FIRE,
            Material.COBWEB, Material.SWEET_BERRY_BUSH, Material.MAGMA_BLOCK
    );

    private static final Set<Material> AIR_BLOCKS = EnumSet.of(
            Material.AIR, Material.CAVE_AIR, Material.VOID_AIR,
            Material.TALL_GRASS, Material.VINE
    );

    private static final int DEFAULT_NETHER_HEIGHT = 125;
    private static final int NETHER_MAX_HEIGHT = 127;

    @Override
    public CompletableFuture<RandomTeleportResult> teleport(Player player) {
        World world = getWorld(player);

        if (!this.configurationProvider.configuration().randomTeleport().randomTeleportWorld().isBlank()) {
            world = this.server.getWorld(this.configurationProvider.configuration().randomTeleport().randomTeleportWorld());

            if (world == null) {
                throw new IllegalStateException("World " + this.configurationProvider.configuration().randomTeleport().randomTeleportWorld() + " does not exist.");
            }
        }

        return this.teleport(player, world);
    }

    @Override
    public CompletableFuture<RandomTeleportResult> teleport(Player player, World world) {
        if (this.eventCaller.callEvent(new PreRandomTeleportEvent(player)).isCancelled()) {
            return CompletableFuture.completedFuture(new RandomTeleportResult(false, player.getLocation()));
        }

        return this.getSafeRandomLocation(world, this.configurationProvider.configuration().randomTeleport().maxAttempts())
                .thenCompose(location -> PaperLib.teleportAsync(player, location).thenApply(success -> {
                    this.eventCaller.callEvent(new RandomTeleportEvent(player, location));
                    return new RandomTeleportResult(success, location);
                }));
    }

    @Override
    public CompletableFuture<Location> getSafeRandomLocation(World world, int attemptCount) {
        int radius = configurationProvider.configuration().randomTeleport().randomTeleportRadius();
        return getSafeRandomLocation(world, RandomTeleportType.STATIC_RADIUS, radius, attemptCount);
    }

    @Override
    public CompletableFuture<Location> getSafeRandomLocation(World world, int radius, int attemptCount) {
        return getSafeRandomLocation(world, RandomTeleportType.STATIC_RADIUS, radius, attemptCount);
    }

    @Override
    public CompletableFuture<Location> getSafeRandomLocationInWorldBorder(World world, int attemptCount) {
        return getSafeRandomLocation(world, RandomTeleportType.WORLD_BORDER_RADIUS, 0, attemptCount);
    }

    private CompletableFuture<Location> getSafeRandomLocation(World world, RandomTeleportType type, int radius, int maxTries) {
        if (maxTries < 0) {
            return CompletableFuture.failedFuture(new RuntimeException("Could not find a safe location."));
        }

        if (type == RandomTeleportType.WORLD_BORDER_RADIUS) {
            radius = (int) (world.getWorldBorder().getSize() / 2);
        }

        Location spawn = world.getSpawnLocation();
        int randomX = spawn.getBlockX() + random.nextInt(-radius, radius);
        int randomZ = spawn.getBlockZ() + random.nextInt(-radius, radius);

        return PaperLib.getChunkAtAsync(new Location(world, randomX, 100, randomZ)).thenCompose(chunk -> {
            int randomY = getRandomY(world, randomX, randomZ);
            Location location = new Location(world, randomX, randomY, randomZ).add(0.5, 1, 0.5);

            if (isSafeLocation(chunk, location)) {
                return CompletableFuture.completedFuture(location);
            }

            return this.getSafeRandomLocation(world, maxTries - 1);
        });
    }

    private int getRandomY(World world, int x, int z) {
        if (world.getEnvironment() == World.Environment.NETHER) {
            return random.nextInt(DEFAULT_NETHER_HEIGHT);
        }
        return world.getHighestBlockYAt(x, z);
    }

    private boolean isSafeLocation(Chunk chunk, Location location) {
        if (location == null || location.getWorld() == null) {
            return false;
        }

        Block block = chunk.getWorld().getBlockAt(location);
        Block blockAbove = block.getRelative(BlockFace.UP);
        Block blockFloor = block.getRelative(BlockFace.DOWN);

        return !UNSAFE_BLOCKS.contains(blockFloor.getType()) &&
                AIR_BLOCKS.contains(block.getType()) &&
                AIR_BLOCKS.contains(blockAbove.getType()) &&
                blockFloor.getType().isSolid() &&
                chunk.getWorld().getWorldBorder().isInside(location) &&
                isValidEnvironment(chunk.getWorld(), location);
    }

    private boolean isValidEnvironment(World world, Location location) {
        return switch (world.getEnvironment()) {
            case NORMAL, THE_END -> true;
            case NETHER -> location.getY() <= NETHER_MAX_HEIGHT;
            default -> false;
        };
    }

    private World getWorld(Player player) {
        String worldName = configurationProvider.configuration().randomTeleport().randomTeleportWorld();
        if (!worldName.isBlank()) {
            World world = server.getWorld(worldName);
            if (world == null) {
                throw new IllegalArgumentException("World " + worldName + " does not exist.");
            }
            return world;
        }
        return player.getWorld();
    }
}
