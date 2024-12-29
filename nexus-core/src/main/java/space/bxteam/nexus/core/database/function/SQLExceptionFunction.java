package space.bxteam.nexus.core.database.function;

import java.sql.SQLException;

@FunctionalInterface
public interface SQLExceptionFunction<T, R> {
    R apply(T t) throws SQLException;
}
