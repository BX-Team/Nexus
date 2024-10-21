package space.bxteam.nexus.core.database.statement;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface ResultSetConsumer {
    void accept(ResultSet resultSet) throws SQLException;
}
