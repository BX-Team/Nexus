package gq.bxteam.nexus;

import io.papermc.paper.plugin.loader.PluginClasspathBuilder;
import io.papermc.paper.plugin.loader.PluginLoader;
import io.papermc.paper.plugin.loader.library.impl.MavenLibraryResolver;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.graph.Dependency;
import org.eclipse.aether.repository.RemoteRepository;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class NexusLoader implements PluginLoader {
    @Override
    public void classloader(final @NotNull PluginClasspathBuilder classpathBuilder) {
        MavenLibraryResolver resolver = new MavenLibraryResolver();

        List<RemoteRepository> repositories = mapRepositories(
                new RepoInput(
                        "placeholder-api",
                        "https://repo.extendedclip.com/content/repositories/placeholderapi/")
        );

        repositories.forEach(resolver::addRepository);

        List<Dependency> dependencies = mapDependencies(
                "me.clip:placeholderapi:${placeholder_api}");

        dependencies.forEach(resolver::addDependency);
        classpathBuilder.addLibrary(resolver);
    }

    private List<Dependency> mapDependencies(String... dependencyCoords) {
        return Arrays
                .stream(dependencyCoords)
                .map(coords -> new Dependency(new DefaultArtifact(coords), null))
                .toList();
    }

    private List<RemoteRepository> mapRepositories(RepoInput... repositoryCoords) {
        return Arrays.stream(repositoryCoords)
                .map(value -> new RemoteRepository.Builder
                        (value.id, "default", value.url).build())
                .toList();
    }

    private record RepoInput(String id, String url) {}
}
