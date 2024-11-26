package space.bxteam.nexus.feature.teleportrequest;

import java.util.List;
import java.util.UUID;

public interface TeleportRequestService {
    void createRequest(UUID requester, UUID target);

    void removeRequest(UUID requester);

    boolean hasRequest(UUID requester, UUID target);

    List<UUID> findRequests(UUID target);
}
