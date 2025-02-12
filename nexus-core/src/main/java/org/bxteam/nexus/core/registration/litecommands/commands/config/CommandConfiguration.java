package org.bxteam.nexus.core.registration.litecommands.commands.config;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Accessors(fluent = true)
@SuppressWarnings({"FieldMayBeFinal", "InnerClassMayBeStatic"})
public class CommandConfiguration extends OkaeriConfig {
    private String name = "randomteleport";
    private List<String> aliases = new ArrayList<>();
    private List<String> permissions = new ArrayList<>();

    @Comment("Configuration of sub-commands")
    private Map<String, SubCommand> subCommands = new HashMap<>();

    @Comment("Cooldown configuration for the command")
    private Cooldown cooldown = new Cooldown();

    @Getter
    public static class SubCommand extends OkaeriConfig {
        private boolean enabled = false;
        private String name = "default";
        private List<String> aliases = new ArrayList<>();
        private List<String> permissions = new ArrayList<>();
    }

    @Getter
    public static class Cooldown extends OkaeriConfig {
        private boolean enabled = true;
        @Comment("Duration of the cooldown (e.g. 5s, 10m, 1h)")
        private Duration duration = Duration.ofSeconds(60);
        @Comment("Permission required to bypass the cooldown")
        private String bypassPermission = "nexus.cooldown.bypass";
        @Comment("Cooldown notification")
        private Notice message = Notice.builder()
                .chat("<dark_red>You must wait <white>{TIME}</white> before using this command again.")
                .actionBar("<dark_red>Wait <white>{TIME}</white>!")
                .build();
    }
}
