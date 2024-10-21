package space.bxteam.nexus.core.database;

import space.bxteam.nexus.core.database.statement.StatementBuilder;

public interface DatabaseClient extends Connector {
    StatementBuilder newBuilder(String statement);

    DatabaseQueries queries();
}
