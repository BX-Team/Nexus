package space.bxteam.nexus.annotations.scan.command;

import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import dev.rollczi.litecommands.annotations.permission.Permissions;
import space.bxteam.nexus.annotations.scan.ScanRecord;
import space.bxteam.nexus.annotations.scan.ScanResolver;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Function;

public class CommandScanResolver implements ScanResolver<CommandResult> {
    @Override
    public List<CommandResult> resolve(ScanRecord record) {
        Class<?> type = record.clazz();

        Command command = type.getAnnotation(Command.class);
        if (command != null) {
            return this.handleCommand(record, command);
        }

        return List.of();
    }

    private List<CommandResult> handleCommand(ScanRecord record, Command command) {
        List<CommandResult> results = new ArrayList<>();

        for (Method method : record.methods()) {
            Execute execute = method.getAnnotation(Execute.class);

            if (execute == null) {
                continue;
            }

            String name = command.name() + " " + execute.name();
            List<String> aliases = Arrays.stream(execute.aliases())
                    .map(alias -> command.name() + ", " + alias)
                    .toList();

            results.add(this.handleExecutor(record, method, name, aliases));
        }

        return removeDuplicates(results);
    }

    @SuppressWarnings("UnstableApiUsage")
    private CommandResult handleExecutor(ScanRecord record, Method method, String name, List<String> aliases) {
        Class<?> clazz = record.clazz();
        Set<String> permissions = new HashSet<>();

        permissions.addAll(this.scan(Permission.class, clazz, method, (Permission::value)));
        permissions.addAll(this.scan(Permissions.class, clazz, method, (permissionsAnnotation -> {
            List<String> permissionsList = new ArrayList<>();

            for (Permission permission : permissionsAnnotation.value()) {
                permissionsList.addAll(Arrays.asList(permission.value()));
            }

            return permissionsList.toArray(new String[0]);
        })));

        List<String> description = this.scan(CommandDocs.class, Object.class, method, (CommandDocs::description));
        List<String> arguments = this.scan(CommandDocs.class, Object.class, method, (CommandDocs::arguments));

        return new CommandResult(
                name,
                aliases,
                List.copyOf(permissions),
                description,
                arguments
        );
    }

    private <A extends Annotation, R> List<R> scan(Class<A> annotationClass, Class<?> type, Method method, Function<A, R[]> resolver) {
        List<R> results = new ArrayList<>();

        A annotation = type.getAnnotation(annotationClass);

        if (annotation != null) {
            results.addAll(List.of(resolver.apply(annotation)));
        }

        A methodAnnotation = method.getAnnotation(annotationClass);

        if (methodAnnotation != null) {
            results.addAll(List.of(resolver.apply(methodAnnotation)));
        }

        return results;
    }

    public static <T> List<T> removeDuplicates(List<T> list) {
        return new ArrayList<>(new HashSet<>(list));
    }
}
