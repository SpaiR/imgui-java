package imgui.binding.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The marked method will be used as a metadata provider for generating binding methods.
 */
@ExcludedSource
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.SOURCE)
public @interface BindingMethod {
    /**
     * Name of the generated method on JVM side.
     */
    String name() default "";

    /**
     * Name of the method which will be used to call native API.
     */
    String callName() default "";
}
