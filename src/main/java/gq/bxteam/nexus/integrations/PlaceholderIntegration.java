package gq.bxteam.nexus.integrations;

import gq.bxteam.nexus.Nexus;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public class PlaceholderIntegration extends PlaceholderExpansion {
    @Override
    public @NotNull String getAuthor() {
        return Nexus.getInstance().getDescription().getAuthors().toString();
    }

    @Override
    public @NotNull String getIdentifier() {
        return "ndr";
    }

    @Override
    public @NotNull String getVersion() {
        return Nexus.getInstance().getDescription().getVersion();
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    public String onRequest(OfflinePlayer player, @NotNull String identifier) {
        return null; // TODO: placeholders
    }
}
