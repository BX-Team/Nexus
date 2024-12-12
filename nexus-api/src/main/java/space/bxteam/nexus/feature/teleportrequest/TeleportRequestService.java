package space.bxteam.nexus.feature.teleportrequest;

import java.util.List;
import java.util.UUID;

/**
 * Service for teleport requests.
 */
public interface TeleportRequestService {
    /**
     * Create a teleport request.
     *
     * @param requester player who requests the teleport
     * @param target player who receives the request
     */
    void createRequest(UUID requester, UUID target);

    /**
     * Remove a teleport request.
     *
     * @param requester player who requests the teleport
     */
    void removeRequest(UUID requester);

    /**
     * Check if a player has a request.
     *
     * @param requester player who requests the teleport
     * @param target player who receives the request
     * @return true if the player has a request
     */
    boolean hasRequest(UUID requester, UUID target);

    /**
     * Find requests for a player.
     *
     * @param target player who receives the request
     * @return list of requesters
     */
    List<UUID> findRequests(UUID target);
}
