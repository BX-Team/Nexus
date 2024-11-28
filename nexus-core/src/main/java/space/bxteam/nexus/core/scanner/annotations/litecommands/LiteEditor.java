package space.bxteam.nexus.core.scanner.annotations.litecommands;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used to register contextual bindings for LiteCommands
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface LiteEditor {
    Class<?> command() default Object.class;

    String name() default "";
}
