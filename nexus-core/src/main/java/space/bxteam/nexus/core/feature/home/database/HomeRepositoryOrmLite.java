package space.bxteam.nexus.core.feature.home.database;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.table.TableUtils;
import space.bxteam.commons.scheduler.Scheduler;
import space.bxteam.nexus.core.database.wrapper.AbstractOrmLiteDatabase;
import space.bxteam.nexus.core.database.DatabaseClient;
import space.bxteam.nexus.feature.home.Home;

import java.sql.SQLException;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Singleton
public class HomeRepositoryOrmLite extends AbstractOrmLiteDatabase implements HomeRepository {
    private static final String OWNER_COLUMN = "owner";
    private static final String NAME_COLUMN = "name";

    @Inject
    public HomeRepositoryOrmLite(DatabaseClient client, Scheduler scheduler) throws SQLException {
        super(client, scheduler);
        TableUtils.createTableIfNotExists(client.getConnectionSource(), HomeWrapper.class);
    }

    @Override
    public CompletableFuture<Optional<Home>> getHome(UUID playerUniqueId) {
        return this.select(HomeWrapper.class, playerUniqueId)
                .thenApply(Optional::of)
                .thenApply(home -> home.map(HomeWrapper::toHome));
    }

    @Override
    public CompletableFuture<Optional<Home>> getHome(UUID playerUniqueId, String homeName) {
        return this.action(HomeWrapper.class, dao -> Optional.of(dao.queryBuilder()
                .where()
                .eq(OWNER_COLUMN, playerUniqueId)
                .and()
                .eq(NAME_COLUMN, homeName)
                .queryForFirst()).map(HomeWrapper::toHome));
    }

    @Override
    public CompletableFuture<Void> saveHome(Home home) {
        return this.save(HomeWrapper.class, HomeWrapper.from(home)).thenApply(result -> null);
    }

    @Override
    public CompletableFuture<Integer> deleteHome(UUID playerUniqueId) {
        return this.deleteById(HomeWrapper.class, playerUniqueId);
    }

    @Override
    public CompletableFuture<Integer> deleteHome(UUID playerUniqueId, String homeName) {
        return this.action(HomeWrapper.class, dao -> {
            DeleteBuilder<HomeWrapper, Object> builder = dao.deleteBuilder();
            builder.where()
                    .eq(OWNER_COLUMN, playerUniqueId)
                    .and()
                    .eq(NAME_COLUMN, homeName);
            return builder.delete();
        });
    }

    @Override
    public CompletableFuture<Set<Home>> getHomes() {
        return this.selectAll(HomeWrapper.class)
                .thenApply(homeOrmLites -> homeOrmLites.stream().map(HomeWrapper::toHome).collect(Collectors.toSet()));
    }

    @Override
    public CompletableFuture<Set<Home>> getHomes(UUID playerUniqueId) {
        return this.action(HomeWrapper.class, dao -> dao.queryBuilder()
                .where()
                .eq(OWNER_COLUMN, playerUniqueId)
                .query()).thenApply(homes -> homes.stream()
                .map(HomeWrapper::toHome)
                .collect(Collectors.toSet()));
    }
}
