package space.bxteam.nexus.core.environment;

public enum Environment {
    PAPER,
    OTHER;

    public static final Environment ENVIRONMENT = detectEnvironment();

    public boolean isPaper() {
        return this == PAPER;
    }

    public boolean isOther() {
        return this == OTHER;
    }

    public boolean isJavaVersion(int version) {
        String javaVersion = System.getProperty("java.version");
        return javaVersion.startsWith(version + ".");
    }

    private static Environment detectEnvironment() {
        if (hasClass("com.destroystokyo.paper.PaperConfig") || hasClass("io.papermc.paper.configuration.Configuration")) {
            return PAPER;
        } else {
            return OTHER;
        }
    }

    private static boolean hasClass(String className) {
        try {
            Class.forName(className);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
}
