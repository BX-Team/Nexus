package space.bxteam.nexus.core.feature.warp.database;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.j256.ormlite.table.TableUtils;
import space.bxteam.commons.bukkit.position.PositionFactory;
import space.bxteam.commons.scheduler.Scheduler;
import space.bxteam.nexus.core.database.wrapper.AbstractOrmLiteDatabase;
import space.bxteam.nexus.core.database.DatabaseClient;
import space.bxteam.nexus.feature.warp.Warp;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Singleton
public class WarpRepositoryOrmLite extends AbstractOrmLiteDatabase implements WarpRepository {
    @Inject
    private WarpRepositoryOrmLite(DatabaseClient client, Scheduler scheduler) throws SQLException {
        super(client, scheduler);
        TableUtils.createTableIfNotExists(client.getConnectionSource(), WarpWrapper.class);
    }

    @Override
    public void addWarp(Warp warp) {
        this.save(WarpWrapper.class, WarpWrapper.from(warp));
    }

    @Override
    public void removeWarp(Warp warp) {
        this.delete(WarpWrapper.class, new WarpWrapper(warp.name(), PositionFactory.convert(warp.location())));
    }

    @Override
    public CompletableFuture<Optional<Warp>> getWarp(String name) {
        return this.action(WarpWrapper.class, dao -> Optional.of(dao.queryBuilder()
                .where()
                .eq("name", name)
                .queryForFirst()).map(WarpWrapper::toWarp));
    }

    @Override
    public CompletableFuture<List<Warp>> getWarps() {
        return this.selectAll(WarpWrapper.class)
                .thenApply(warpWrappers -> warpWrappers.stream()
                        .map(WarpWrapper::toWarp)
                        .toList());
    }
}