package space.bxteam.nexus.core.integration;

public interface Integration {
    boolean available();

    default void enable() {}
}
