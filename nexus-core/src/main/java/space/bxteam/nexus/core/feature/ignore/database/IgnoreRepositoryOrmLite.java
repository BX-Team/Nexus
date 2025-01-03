package space.bxteam.nexus.core.feature.ignore.database;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.table.TableUtils;
import org.jetbrains.annotations.NotNull;
import space.bxteam.commons.scheduler.Scheduler;
import space.bxteam.nexus.core.database.DatabaseClient;
import space.bxteam.nexus.core.database.wrapper.AbstractOrmLiteDatabase;

import java.sql.SQLException;
import java.time.Duration;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Singleton
public class IgnoreRepositoryOrmLite extends AbstractOrmLiteDatabase implements IgnoreRepository {
    private static final UUID IGNORE_ALL = UUID.nameUUIDFromBytes("*".getBytes());

    private final Dao<IgnoreWrapper, Long> cachedDao;
    private final LoadingCache<UUID, Set<UUID>> ignores;

    @Inject
    public IgnoreRepositoryOrmLite(DatabaseClient client, Scheduler scheduler) throws SQLException {
        super(client, scheduler);

        this.cachedDao = client.getDao(IgnoreWrapper.class);
        this.ignores = CacheBuilder.newBuilder()
                .expireAfterAccess(Duration.ofMinutes(15))
                .refreshAfterWrite(Duration.ofMinutes(3))
                .build(new IgnoreLoader());

        TableUtils.createTableIfNotExists(client.getConnectionSource(), IgnoreWrapper.class);
    }

    @Override
    public CompletableFuture<Void> ignore(UUID sender, UUID target) {
        return CompletableFuture.runAsync(() -> {
            try {
                Set<UUID> uuids = this.ignores.get(sender);

                if (!uuids.contains(target)) {
                    this.save(IgnoreWrapper.class, new IgnoreWrapper(sender, target)).thenRun(() -> this.ignores.refresh(sender));
                }
            } catch (ExecutionException exception) {
                throw new RuntimeException(exception);
            }
        });
    }

    @Override
    public CompletableFuture<Void> ignoreAll(UUID sender) {
        return this.ignore(sender, IGNORE_ALL);
    }

    @Override
    public CompletableFuture<Void> unIgnore(UUID sender, UUID target) {
        return this.action(IgnoreWrapper.class, dao -> {
            DeleteBuilder<IgnoreWrapper, Object> builder = dao.deleteBuilder();

            builder.where()
                    .eq("player_id", sender)
                    .and()
                    .eq("ignored_id", target);

            return builder.delete();
        }).thenRun(() -> this.ignores.refresh(sender));
    }

    @Override
    public CompletableFuture<Void> unIgnoreAll(UUID sender) {
        return this.action(IgnoreWrapper.class, dao -> {
            DeleteBuilder<IgnoreWrapper, Object> builder = dao.deleteBuilder();

            builder.where()
                    .eq("player_id", sender);

            return builder.delete();
        }).thenRun(() -> this.ignores.refresh(sender));
    }

    @Override
    public CompletableFuture<Boolean> isIgnored(UUID sender, UUID target) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Set<UUID> uuids = this.ignores.get(sender);

                return uuids.contains(target) || uuids.contains(IGNORE_ALL);
            } catch (ExecutionException exception) {
                throw new RuntimeException(exception);
            }
        });
    }

    private class IgnoreLoader extends CacheLoader<UUID, Set<UUID>> {
        @Override
        public @NotNull Set<UUID> load(@NotNull UUID key) throws SQLException {
            return IgnoreRepositoryOrmLite.this.cachedDao.queryBuilder()
                    .where().eq("player_id", key)
                    .query()
                    .stream()
                    .map(ignoreWrapper -> ignoreWrapper.ignoredUuid)
                    .collect(Collectors.toSet());
        }
    }
}
