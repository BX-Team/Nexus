package org.bxteam.nexus.core.updater;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.bukkit.plugin.Plugin;
import org.bxteam.commons.github.GitCheck;
import org.bxteam.commons.github.GitCheckResult;
import org.bxteam.commons.github.GitRepository;
import org.bxteam.commons.github.GitTag;

import java.util.function.Supplier;

@Singleton
public class UpdateService {
    private static final GitRepository GIT_REPOSITORY = GitRepository.of("BX-Team", "Nexus");

    private final GitCheck gitCheck = new GitCheck();
    private final Supplier<GitCheckResult> gitCheckResult;

    @Inject
    public UpdateService(Plugin plugin) {
        this.gitCheckResult = () -> {
            String version = plugin.getPluginMeta().getVersion();
            return this.gitCheck.checkRelease(GIT_REPOSITORY, GitTag.of("v" + version));
        };
    }

    public boolean isUpToDate() {
        GitCheckResult result = this.gitCheckResult.get();
        return result.isUpToDate();
    }

    public String getReleaseTag() {
        GitCheckResult result = this.gitCheckResult.get();
        if (result.isUpToDate()) {
            return null;
        }
        return result.getLatestRelease().getTag().getTag();
    }

    public String getReleasePageLink() {
        GitCheckResult result = this.gitCheckResult.get();
        if (result.isUpToDate()) {
            return null;
        }
        return result.getLatestRelease().getPageUrl();
    }
}
