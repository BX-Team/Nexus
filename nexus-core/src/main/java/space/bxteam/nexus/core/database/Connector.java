package space.bxteam.nexus.core.database;

public interface Connector {
    void open();

    void close();

    boolean available();
}
