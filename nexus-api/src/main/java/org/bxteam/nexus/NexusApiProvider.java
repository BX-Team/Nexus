package org.bxteam.nexus;

public final class NexusApiProvider {
    private static NexusApi api;

    private NexusApiProvider() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }

    public static NexusApi get() {
        if (api == null) {
            throw new IllegalStateException("NexusApiProvider is not initialized");
        }

        return api;
    }

    public static void initialize(NexusApi api) {
        if (NexusApiProvider.api != null) {
            throw new IllegalStateException("NexusApiProvider is already initialized");
        }

        if (api == null) {
            throw new IllegalArgumentException("api cannot be null");
        }

        NexusApiProvider.api = api;
    }

    public static void shutdown() {
        if (NexusApiProvider.api == null) {
            throw new IllegalStateException("NexusApiProvider is not initialized");
        }

        NexusApiProvider.api = null;
    }
}
