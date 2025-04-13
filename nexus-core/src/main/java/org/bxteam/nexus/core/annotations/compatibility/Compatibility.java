package org.bxteam.nexus.core.annotations.compatibility;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to make a specific feature compatible with a specific version of the server.
 * <p>
 * It is used to specify the minimum and maximum version of the server that a specific feature is compatible with.
 *
 * @see Version
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Compatibility {
    Version from() default @Version(minor = Integer.MIN_VALUE, patch = Integer.MIN_VALUE);

    Version to() default @Version(minor = Integer.MAX_VALUE, patch = Integer.MAX_VALUE);
}
