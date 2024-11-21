package space.bxteam.nexus.feature.chat;

import java.time.Duration;
import java.util.UUID;

public interface ChatService {
    /**
     * Mark the player that he used the chat.
     * This method updates slowmode time for the player, so it should be called every time the user sends a message in the chat.
     * @param player player from event who sent a message
     */
    void setCooldown(UUID player);

    /**
     * Check if the player has slowed chat.
     *
     * @param player player to check
     * @return true if the player has slowed chat, otherwise false
     */
    boolean hasSlowedChat(UUID player);

    /**
     * Get the remaining time of the slowmode for the player.
     *
     * @param player player to check
     * @return remaining time of the slowmode
     */
    Duration getRemainingCoolDown(UUID player);

    /**
     * Check if the chat is enabled.
     *
     * @return true if the chat is enabled, otherwise false
     */
    boolean isChatEnabled();

    void setChatEnabled(boolean enabled);
}
