package space.bxteam.nexus.core.configuration;

import com.eternalcode.multification.notice.resolver.NoticeResolverDefaults;
import com.eternalcode.multification.notice.resolver.NoticeResolverRegistry;
import com.eternalcode.multification.notice.resolver.sound.SoundAdventureResolver;
import com.eternalcode.multification.okaeri.MultificationSerdesPack;
import com.google.inject.Singleton;
import eu.okaeri.configs.ConfigManager;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.serdes.commons.SerdesCommons;
import eu.okaeri.configs.yaml.bukkit.serdes.SerdesBukkit;
import eu.okaeri.configs.yaml.snakeyaml.YamlSnakeYamlConfigurer;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.representer.Representer;
import org.yaml.snakeyaml.resolver.Resolver;
import space.bxteam.nexus.core.configuration.seriazlier.position.PositionSerdesPack;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

@Singleton
public class ConfigurationManager {
    private final Set<OkaeriConfig> configs = new HashSet<>();

    public <T extends OkaeriConfig> T create(Class<T> config, File file) {
        T configFile = ConfigManager.create(config);

        YamlSnakeYamlConfigurer yamlConfigurer = new YamlSnakeYamlConfigurer(this.createYaml());

        NoticeResolverRegistry noticeRegistry = NoticeResolverDefaults.createRegistry().registerResolver(new SoundAdventureResolver());
        configFile
                .withConfigurer(yamlConfigurer, new SerdesBukkit(), new SerdesCommons(), new PositionSerdesPack())
                .withConfigurer(yamlConfigurer, new MultificationSerdesPack(noticeRegistry))
                .withBindFile(file)
                .withRemoveOrphans(true)
                .saveDefaults()
                .load(true);

        this.configs.add(configFile);

        return configFile;
    }

    private Yaml createYaml() {
        LoaderOptions loaderOptions = new LoaderOptions();
        Constructor constructor = new Constructor(loaderOptions);

        DumperOptions dumperOptions = new DumperOptions();
        dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.AUTO);
        dumperOptions.setIndent(2);
        dumperOptions.setSplitLines(false);

        Representer representer = new CustomSnakeYamlRepresenter(dumperOptions);
        Resolver resolver = new Resolver();

        return new Yaml(constructor, representer, dumperOptions, loaderOptions, resolver);
    }

    public void reload() {
        for (OkaeriConfig config : this.configs) {
            config.load();
        }
    }

    public void save(OkaeriConfig config) {
        config.save();
    }
}
