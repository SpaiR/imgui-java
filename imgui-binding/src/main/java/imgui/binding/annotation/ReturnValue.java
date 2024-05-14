package imgui.binding.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Provides an additional metadata for the return value.
 */
@ExcludedSource
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.SOURCE)
public @interface ReturnValue {
    /**
     * When provided, value returned at the JNI side will have an additional prefix.
     * Helpfully to do type casts etc.
     */
    String callPrefix() default "";

    /**
     * When provided, value returned at the JNI side will have an additional suffix.
     * Helpfully to call additional methods.
     */
    String callSuffix() default "";

    /**
     * When true, returned struct pointer will be passed to the static field.
     * Should be used for global and immutable by their nature structs.
     */
    boolean isStatic() default false;
}
