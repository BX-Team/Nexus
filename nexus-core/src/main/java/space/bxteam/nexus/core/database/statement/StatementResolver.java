package space.bxteam.nexus.core.database.statement;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface StatementResolver<T> {
    T resolve(ResultSet resultSet) throws SQLException;
}
