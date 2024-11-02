package space.bxteam.nexus.core.integration.litecommands;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.LiteCommandsBuilder;
import dev.rollczi.litecommands.adventure.bukkit.platform.LiteAdventurePlatformExtension;
import dev.rollczi.litecommands.annotations.LiteCommandsAnnotations;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.command.RootCommand;
import dev.rollczi.litecommands.argument.ArgumentKey;
import dev.rollczi.litecommands.argument.resolver.ArgumentResolverBase;
import dev.rollczi.litecommands.bukkit.LiteBukkitFactory;
import dev.rollczi.litecommands.handler.result.ResultHandler;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;
import net.kyori.adventure.platform.AudienceProvider;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import space.bxteam.nexus.core.integration.litecommands.annotations.LiteArgument;
import space.bxteam.nexus.core.integration.litecommands.annotations.LiteHandler;
import space.bxteam.nexus.core.utils.LogUtil;

import java.lang.annotation.Annotation;
import java.util.function.Consumer;

@Singleton
public class LiteCommandsRegister {
    private final Injector injector;
    private final LiteCommandsAnnotations<CommandSender> annotations;
    private final LiteCommandsBuilder<CommandSender, ?, ?> liteCommandsBuilder;
    private LiteCommands<CommandSender> liteCommands;

    @Inject
    public LiteCommandsRegister(Injector injector, Plugin plugin, Server server, AudienceProvider audiencesProvider, @Named("colorMiniMessage") MiniMessage miniMessage) {
        this.injector = injector;
        this.annotations = LiteCommandsAnnotations.create();
        registerAnnotatedClasses(annotations, Command.class, annotations::load);
        registerAnnotatedClasses(annotations, RootCommand.class, annotations::load);

        this.liteCommandsBuilder = LiteBukkitFactory.builder("nexus", plugin, server)
                .commands(annotations)
                .extension(new LiteAdventurePlatformExtension<>(audiencesProvider), extension -> extension.serializer(miniMessage));

        registerHandlersAndArguments();
    }

    public void onEnable() {
        liteCommands = liteCommandsBuilder.build();
    }

    public void onDisable() {
        liteCommands.unregister();
    }

    private <A extends Annotation> void registerAnnotatedClasses(LiteCommandsAnnotations<CommandSender> annotations, Class<A> annotation, Consumer<Object> consumer) {
        scanClassesWithAnnotation("space.bxteam.nexus.core", annotation, classInfo -> {
            try {
                Object instance = injector.getInstance(classInfo.loadClass());
                consumer.accept(instance);
            } catch (Exception e) {
                LogUtil.log("Failed to load instance: " + e.getMessage(), LogUtil.LogLevel.ERROR);
            }
        });
    }

    @SuppressWarnings("unchecked")
    private void registerHandlersAndArguments() {
        scanClassesWithAnnotation("space.bxteam.nexus.core.integration.litecommands", LiteArgument.class, classInfo -> {
            try {
                Class<?> argumentClass = classInfo.loadClass();
                LiteArgument liteArgument = argumentClass.getAnnotation(LiteArgument.class);
                ArgumentResolverBase<CommandSender, Object> argumentResolver = (ArgumentResolverBase<CommandSender, Object>) injector.getInstance(argumentClass);
                registerArgument((Class<Object>) liteArgument.type(), ArgumentKey.of(liteArgument.name()), argumentResolver);
            } catch (Exception e) {
                LogUtil.log("Failed to load argument instance: " + e.getMessage(), LogUtil.LogLevel.ERROR);
            }
        });

        scanClassesWithAnnotation("space.bxteam.nexus.core.integration.litecommands", LiteHandler.class, classInfo -> {
            try {
                Class<?> handlerClass = classInfo.loadClass();
                LiteHandler liteHandler = handlerClass.getAnnotation(LiteHandler.class);
                ResultHandler<CommandSender, Object> resultHandler = (ResultHandler<CommandSender, Object>) injector.getInstance(handlerClass);
                registerResultHandler((Class<Object>) liteHandler.value(), resultHandler);
            } catch (Exception e) {
                LogUtil.log("Failed to load handler instance: " + e.getMessage(), LogUtil.LogLevel.ERROR);
            }
        });
    }

    private void scanClassesWithAnnotation(String packageName, Class<? extends Annotation> annotation, Consumer<ClassInfo> consumer) {
        try (ScanResult scanResult = new ClassGraph()
                .enableClassInfo()
                .enableAnnotationInfo()
                .acceptPackages(packageName)
                .scan()) {
            scanResult.getClassesWithAnnotation(annotation.getName()).forEach(consumer);
        }
    }

    private <T> void registerArgument(Class<T> argumentClass, ArgumentKey key, ArgumentResolverBase<CommandSender, T> resolver) {
        liteCommandsBuilder.argument(argumentClass, key, resolver);
    }

    private <T> void registerResultHandler(Class<T> resultClass, ResultHandler<CommandSender, ? extends T> resultHandler) {
        liteCommandsBuilder.result(resultClass, resultHandler);
    }
}
