package org.bxteam.nexus.core.feature.ignore.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.UUID;

@DatabaseTable(tableName = "nexus_ignores")
public class IgnoreWrapper {
    @DatabaseField(generatedId = true)
    Long id;

    @DatabaseField(columnName = "player_id", uniqueCombo = true)
    UUID playerUuid;

    @DatabaseField(columnName = "ignored_id", uniqueCombo = true)
    UUID ignoredUuid;

    IgnoreWrapper() {}

    IgnoreWrapper(UUID playerUuid, UUID ignoredUuid) {
        this.playerUuid = playerUuid;
        this.ignoredUuid = ignoredUuid;
    }
}
