package imgui.binding.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Provides a sort of pre-processing experience, to generate method with additional argument variants.
 * Can generate methods with different arg type and different arg names.
 * Intended to be used for methods with template arguments.
 * {@link #type} and {@link #name()} arrays SHOULD have the same length.
 */
@ExcludedSource
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.SOURCE)
public @interface ArgVariant {
    /**
     * Variant for the argument type.
     */
    String[] type() default {};

    /**
     * Variant for the argument name.
     */
    String[] name() default {};
}
