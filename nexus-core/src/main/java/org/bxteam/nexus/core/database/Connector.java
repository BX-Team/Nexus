package org.bxteam.nexus.core.database;

import java.sql.SQLException;

public interface Connector {
    void open() throws SQLException;

    void close();

    boolean available();
}
