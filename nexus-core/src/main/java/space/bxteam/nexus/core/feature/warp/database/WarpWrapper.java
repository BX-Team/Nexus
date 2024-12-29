package space.bxteam.nexus.core.feature.warp.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import space.bxteam.commons.bukkit.position.Position;
import space.bxteam.commons.bukkit.position.PositionFactory;
import space.bxteam.nexus.core.database.persister.PositionPersister;
import space.bxteam.nexus.core.feature.warp.WarpImpl;
import space.bxteam.nexus.feature.warp.Warp;

@DatabaseTable(tableName = "nexus_warps")
public class WarpWrapper {
    @DatabaseField(columnName = "name", unique = true, id = true)
    private String name;

    @DatabaseField(columnName = "location", persisterClass = PositionPersister.class)
    private Position location;

    WarpWrapper() {}

    WarpWrapper(String name, Position location) {
        this.name = name;
        this.location = location;
    }

    Warp toWarp() {
        return new WarpImpl(this.name, PositionFactory.convert(this.location));
    }

    static WarpWrapper from(Warp warp) {
        return new WarpWrapper(warp.name(), PositionFactory.convert(warp.location()));
    }
}