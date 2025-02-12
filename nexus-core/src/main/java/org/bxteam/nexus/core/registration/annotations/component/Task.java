package org.bxteam.nexus.core.registration.annotations.component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to mark a class as a task.
 * The class must implement the {@link Runnable} interface.
 *
 * @see Controller
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Task {
    /**
     * The delay before the task is executed.
     *
     * @return the delay
     */
    long delay();

    /**
     * The period between each execution of the task.
     * If the value is 0, the task will only be executed once.
     *
     * @return the period
     */
    long period() default 0L;
}
