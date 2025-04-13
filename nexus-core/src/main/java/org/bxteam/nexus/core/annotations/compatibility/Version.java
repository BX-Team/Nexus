package org.bxteam.nexus.core.annotations.compatibility;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is mainly used to specify the minimum server version to use a specific feature.
 *
 * @see Compatibility
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Version {
    int minor();

    int patch();
}
