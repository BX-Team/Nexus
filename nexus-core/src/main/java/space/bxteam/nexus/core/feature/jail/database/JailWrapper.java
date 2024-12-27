package space.bxteam.nexus.core.feature.jail.database;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import space.bxteam.nexus.feature.jail.JailPlayer;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@DatabaseTable(tableName = "nexus_jailed_players")
public class JailWrapper {
    @DatabaseField(columnName = "id", id = true)
    private UUID uuid;

    @DatabaseField(columnName = "jailed_at", dataType = DataType.SERIALIZABLE)
    private Instant jailedAt;

    @DatabaseField(columnName = "duration", dataType = DataType.SERIALIZABLE)
    private Duration duration;

    @DatabaseField(columnName = "jailed_by")
    private String jailedBy;

    JailWrapper() {}

    JailWrapper(UUID uuid, Instant jailedAt, Duration duration, String jailedBy) {
        this.uuid = uuid;
        this.jailedAt = jailedAt;
        this.duration = duration;
        this.jailedBy = jailedBy;
    }

    JailPlayer toJailPlayer() {
        return new JailPlayer(this.uuid, this.jailedAt, this.duration, this.jailedBy);
    }

    static JailWrapper from(JailPlayer jailedPlayer) {
        return new JailWrapper(
                jailedPlayer.getPlayerUniqueId(),
                jailedPlayer.getJailedAt(),
                jailedPlayer.getPrisonTime(),
                jailedPlayer.getJailedBy()
        );
    }
}
