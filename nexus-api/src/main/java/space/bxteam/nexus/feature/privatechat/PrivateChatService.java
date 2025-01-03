package space.bxteam.nexus.feature.privatechat;

import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Private chat service
 */
public interface PrivateChatService {
    /**
     * Send a message to a player
     *
     * @param sender Message sender
     * @param receiver Message receiver
     * @param message Message content
     */
    void sendMessage(Player sender, Player receiver, String message);

    /**
     * Reply to a last received message
     *
     * @param sender Message sender
     * @param message Message content
     */
    void replyMessage(Player sender, String message);

    /**
     * Enable social spy for a player
     *
     * @param player Player
     */
    void enableSpy(UUID player);

    /**
     * Disable social spy for a player
     *
     * @param player Player
     */
    void disableSpy(UUID player);

    /**
     * Check if social spy is enabled for a player
     *
     * @param player Player
     * @return true if social spy is enabled, false otherwise
     */
    boolean isSpyEnabled(UUID player);
}
