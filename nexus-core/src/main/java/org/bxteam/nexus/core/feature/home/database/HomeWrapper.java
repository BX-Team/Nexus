package org.bxteam.nexus.core.feature.home.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import org.bxteam.commons.bukkit.position.Position;
import org.bxteam.commons.bukkit.position.PositionFactory;
import org.bxteam.nexus.core.database.persister.PositionPersister;
import org.bxteam.nexus.core.feature.home.HomeImpl;
import org.bxteam.nexus.feature.home.Home;

import java.util.UUID;

@DatabaseTable(tableName = "nexus_homes")
public class HomeWrapper {
    @DatabaseField(columnName = "owner", id = true)
    private UUID owner;

    @DatabaseField(columnName = "name")
    private String name;

    @DatabaseField(columnName = "position", persisterClass = PositionPersister.class)
    private Position position;

    HomeWrapper() {}

    public HomeWrapper(UUID owner, String name, Position position) {
        this.owner = owner;
        this.name = name;
        this.position = position;
    }

    Home toHome() {
        return new HomeImpl(this.owner, this.name, PositionFactory.convert(this.position));
    }

    static HomeWrapper from(Home home) {
        return new HomeWrapper(home.owner(), home.name(), PositionFactory.convert(home.location()));
    }
}
