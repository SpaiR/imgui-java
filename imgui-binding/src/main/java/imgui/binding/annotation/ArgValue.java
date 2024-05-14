package imgui.binding.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Provides an additional metadata for the argument value.
 */
@ExcludedSource
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.SOURCE)
public @interface ArgValue {
    /**
     * Defines a prefix which will be appended to the call on JNI side.
     */
    String callPrefix() default "";

    /**
     * Defines a prefix which will be added to the call on JNI side.
     */
    String callSuffix() default "";

    /**
     * Defines what will be called on the native side instead of passing actual argument.
     */
    String callValue() default "";

    /**
     * Defines a value to cast with "static_cast" function.
     */
    String staticCast() default "";

    /**
     * Defines a value to cast with "reinterpret_cast" function.
     */
    String reinterpretCast() default "";
}
