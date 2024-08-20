package space.bxteam.nexus.core.loader.records;

import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.graph.Dependency;
import org.eclipse.aether.repository.RemoteRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public record PluginLibraries(Map<String, String> repositories, List<String> dependencies) {
    public Stream<Dependency> asDependencies() {
        return this.dependencies.stream().map(d -> new Dependency(new DefaultArtifact(d), null));
    }

    public Stream<RemoteRepository> asRepositories() {
        return this.repositories.entrySet().stream()
                .map(e -> new RemoteRepository.Builder(e.getKey(), "default", e.getValue()).build());
    }
}
