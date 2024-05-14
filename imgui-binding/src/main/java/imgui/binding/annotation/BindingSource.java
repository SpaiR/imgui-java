package imgui.binding.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks the class as a source for binding generation.
 * Will be parsed for additional metadata, like information about {@link BindingMethod} etc.
 */
@ExcludedSource
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface BindingSource {
    /**
     * Native pointer to use to do calls to the API.
     */
    String callPtr() default "";

    /**
     * Operator to call native pointer.
     */
    String callOperator() default "";
}
