package org.bxteam.nexus.core.registration.litecommands;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.LiteCommandsBuilder;
import dev.rollczi.litecommands.adventure.bukkit.platform.LiteAdventurePlatformExtension;
import dev.rollczi.litecommands.annotations.LiteCommandsAnnotations;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.argument.ArgumentKey;
import dev.rollczi.litecommands.argument.resolver.ArgumentResolverBase;
import dev.rollczi.litecommands.bukkit.LiteBukkitFactory;
import dev.rollczi.litecommands.editor.Editor;
import dev.rollczi.litecommands.handler.result.ResultHandler;
import dev.rollczi.litecommands.message.LiteMessages;
import dev.rollczi.litecommands.scope.Scope;
import net.kyori.adventure.platform.AudienceProvider;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bxteam.nexus.core.registration.litecommands.commands.CommandCooldownMessage;
import org.bxteam.nexus.core.configuration.commands.CommandsConfigProvider;
import org.bxteam.nexus.core.multification.MultificationManager;
import org.bxteam.nexus.core.registration.annotations.litecommands.LiteArgument;
import org.bxteam.nexus.core.registration.annotations.litecommands.LiteEditor;
import org.bxteam.nexus.core.registration.annotations.litecommands.LiteHandler;
import org.bxteam.nexus.core.utils.ClassgraphUtil;
import org.bxteam.nexus.core.utils.Logger;

import java.lang.annotation.Annotation;
import java.util.function.Consumer;

@Singleton
public class LiteCommandsRegistry {
    private final Injector injector;
    private final LiteCommandsAnnotations<CommandSender> annotations;
    private final LiteCommandsBuilder<CommandSender, ?, ?> liteCommandsBuilder;
    private LiteCommands<CommandSender> liteCommands;

    @Inject
    public LiteCommandsRegistry(Injector injector, Plugin plugin, Server server, AudienceProvider audiencesProvider, @Named("colorMiniMessage") MiniMessage miniMessage) {
        this.injector = injector;
        this.annotations = LiteCommandsAnnotations.create();
        registerAnnotatedClasses(annotations, Command.class, annotations::load);

        this.liteCommandsBuilder = LiteBukkitFactory.builder("nexus", plugin, server)
                .commands(annotations)
                .message(LiteMessages.COMMAND_COOLDOWN, new CommandCooldownMessage(injector.getInstance(MultificationManager.class), injector.getInstance(CommandsConfigProvider.class)))
                .extension(new LiteAdventurePlatformExtension<>(audiencesProvider), extension -> extension.serializer(miniMessage));

        registerHandlersAndArguments();
        registerEditor();
    }

    public void onEnable() {
        liteCommands = liteCommandsBuilder.build();
    }

    public void onDisable() {
        liteCommands.unregister();
    }

    private <A extends Annotation> void registerAnnotatedClasses(LiteCommandsAnnotations<CommandSender> annotations, Class<A> annotation, Consumer<Object> consumer) {
        ClassgraphUtil.scanClassesWithAnnotation("org.bxteam.nexus.core", annotation, classInfo -> {
            try {
                Object instance = injector.getInstance(classInfo.loadClass());
                consumer.accept(instance);
            } catch (Exception e) {
                Logger.log("Failed to load instance: " + e.getMessage(), Logger.LogLevel.ERROR);
            }
        });
    }

    @SuppressWarnings("unchecked")
    private void registerHandlersAndArguments() {
        ClassgraphUtil.scanClassesWithAnnotation("org.bxteam.nexus.core.feature", LiteArgument.class, classInfo -> {
            try {
                Class<?> argumentClass = classInfo.loadClass();
                LiteArgument liteArgument = argumentClass.getAnnotation(LiteArgument.class);
                ArgumentResolverBase<CommandSender, Object> argumentResolver = (ArgumentResolverBase<CommandSender, Object>) injector.getInstance(argumentClass);
                registerArgument((Class<Object>) liteArgument.type(), ArgumentKey.of(liteArgument.name()), argumentResolver);
            } catch (Exception e) {
                Logger.log("Failed to load argument instance: " + e.getMessage(), Logger.LogLevel.ERROR);
            }
        });

        ClassgraphUtil.scanClassesWithAnnotation("org.bxteam.nexus.core.integration.litecommands.handler", LiteHandler.class, classInfo -> {
            try {
                Class<?> handlerClass = classInfo.loadClass();
                LiteHandler liteHandler = handlerClass.getAnnotation(LiteHandler.class);
                ResultHandler<CommandSender, Object> resultHandler = (ResultHandler<CommandSender, Object>) injector.getInstance(handlerClass);
                registerResultHandler((Class<Object>) liteHandler.value(), resultHandler);
            } catch (Exception e) {
                Logger.log("Failed to load handler instance: " + e.getMessage(), Logger.LogLevel.ERROR);
            }
        });
    }

    @SuppressWarnings("unchecked")
    private void registerEditor() {
        ClassgraphUtil.scanClassesWithAnnotation("org.bxteam.nexus.core.integration.litecommands", LiteEditor.class, classInfo -> {
            Class<?> handlerClass = classInfo.loadClass();
            LiteEditor liteEditor = handlerClass.getAnnotation(LiteEditor.class);

            Scope scope = Scope.global();

            if (liteEditor.command() != Object.class) {
                scope = Scope.command(liteEditor.command());
            }

            if (!liteEditor.name().isEmpty()) {
                scope = Scope.command(liteEditor.name());
            }

            liteCommandsBuilder.editor(scope, (Editor<CommandSender>) injector.getInstance(handlerClass));
        });
    }

    private <T> void registerArgument(Class<T> argumentClass, ArgumentKey key, ArgumentResolverBase<CommandSender, T> resolver) {
        liteCommandsBuilder.argument(argumentClass, key, resolver);
    }

    private <T> void registerResultHandler(Class<T> resultClass, ResultHandler<CommandSender, ? extends T> resultHandler) {
        liteCommandsBuilder.result(resultClass, resultHandler);
    }
}
