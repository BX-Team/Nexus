package org.bxteam.nexus.core.database.persister;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.BaseDataType;
import com.j256.ormlite.support.DatabaseResults;
import org.bxteam.commons.bukkit.position.Position;

import java.sql.SQLException;

public class PositionPersister extends BaseDataType {
    private static final PositionPersister instance = new PositionPersister();

    private PositionPersister() {
        super(SqlType.LONG_STRING, new Class<?>[] { Position.class });
    }

    @Override
    public Object parseDefaultString(FieldType fieldType, String defaultStr) {
        return Position.parse(defaultStr);
    }

    @Override
    public Object javaToSqlArg(FieldType fieldType, Object javaObject) throws SQLException {
        if (javaObject instanceof Position) {
            return javaObject.toString();
        }
        throw new SQLException("Invalid Position object: " + javaObject);
    }

    @Override
    public Object sqlArgToJava(FieldType fieldType, Object sqlArg, int columnPos) throws SQLException {
        if (sqlArg instanceof String) {
            return Position.parse((String) sqlArg);
        }
        throw new SQLException("Invalid SQL argument for Position: " + sqlArg);
    }

    @Override
    public Object resultToSqlArg(FieldType fieldType, DatabaseResults results, int columnPos) throws SQLException {
        return results.getString(columnPos);
    }

    public static PositionPersister getSingleton() {
        return instance;
    }
}