package space.bxteam.nexus.core.database.statement;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface StatementExecutor<T> {
    T apply(PreparedStatement statement) throws SQLException;
}
