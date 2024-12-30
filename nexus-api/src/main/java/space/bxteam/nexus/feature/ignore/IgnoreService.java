package space.bxteam.nexus.feature.ignore;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * Service for ignoring system.
 */
public interface IgnoreService {
    /**
     * Ignore the target.
     *
     * @param requester the UUID of the player who is ignoring the target
     * @param target the UUID of the player who is being ignored
     */
    CompletableFuture<Boolean> ignore(UUID requester, UUID target);

    /**
     * Ignore all players.
     *
     * @param requester the UUID of the player who is ignoring all players
     */
    CompletableFuture<Boolean> ignoreAll(UUID requester);

    /**
     * Unignore the target.
     *
     * @param requester the UUID of the player who is unignoring the target
     * @param target the UUID of the player who is being unignored
     */
    CompletableFuture<Boolean> unIgnore(UUID requester, UUID target);

    /**
     * Unignore all players.
     *
     * @param requester the UUID of the player who is unignoring all players
     */
    CompletableFuture<Boolean> unIgnoreAll(UUID requester);

    /**
     * Check if the requester is ignoring the target.
     *
     * @param requester the UUID of the player who is checking if they are ignoring the target
     * @param target the UUID of the player who is being checked if they are being ignored
     */
    CompletableFuture<Boolean> isIgnored(UUID requester, UUID target);
}
