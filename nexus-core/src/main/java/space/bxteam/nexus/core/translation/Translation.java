package space.bxteam.nexus.core.translation;

public interface Translation {
    String getLanguage();

    ArgumentSection argument();

    interface ArgumentSection {
        String onlyPlayers();
        String noPermission();
    }
}
