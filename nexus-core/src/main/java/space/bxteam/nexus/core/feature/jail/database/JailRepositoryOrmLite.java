package space.bxteam.nexus.core.feature.jail.database;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.j256.ormlite.table.TableUtils;
import space.bxteam.commons.scheduler.Scheduler;
import space.bxteam.nexus.core.database.wrapper.AbstractOrmLiteDatabase;
import space.bxteam.nexus.core.database.DatabaseClient;
import space.bxteam.nexus.feature.jail.JailPlayer;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Singleton
public class JailRepositoryOrmLite extends AbstractOrmLiteDatabase implements JailRepository {
    @Inject
    private JailRepositoryOrmLite(DatabaseClient client, Scheduler scheduler) throws SQLException {
        super(client, scheduler);
        TableUtils.createTableIfNotExists(client.getConnectionSource(), JailWrapper.class);
    }

    @Override
    public CompletableFuture<Optional<JailPlayer>> getPrisoner(UUID uuid) {
        return this.selectSafe(JailWrapper.class, uuid)
                .thenApply(optional -> optional.map(JailWrapper::toJailPlayer));
    }

    @Override
    public CompletableFuture<Set<JailPlayer>> getPrisoners() {
        return this.selectAll(JailWrapper.class)
                .thenApply(prisonerWrappers -> prisonerWrappers.stream()
                        .map(JailWrapper::toJailPlayer)
                        .collect(Collectors.toSet()));
    }

    @Override
    public void deletePrisoner(UUID uuid) {
        this.deleteById(JailWrapper.class, uuid);
    }

    @Override
    public void editPrisoner(JailPlayer jailedPlayer) {
        this.save(JailWrapper.class, JailWrapper.from(jailedPlayer));
    }

    @Override
    public void deleteAllPrisoners() {
        this.delete(JailWrapper.class, new JailWrapper());
    }

    @Override
    public CompletableFuture<List<JailPlayer>> getAllPrisoners() {
        return this.selectAll(JailWrapper.class)
                .thenApply(prisonerWrappers -> prisonerWrappers.stream()
                        .map(JailWrapper::toJailPlayer)
                        .toList());
    }

    @Override
    public void savePrisoner(JailPlayer jailedPlayer) {
        this.save(JailWrapper.class, JailWrapper.from(jailedPlayer));
    }
}
