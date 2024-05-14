package imgui.binding.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The marked method will be used as a metadata provider for generating binding methods.
 */
@ExcludedSource
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.SOURCE)
public @interface BindingField {
    /**
     * Accessors to generate for field.
     */
    Accessor[] accessors() default {Accessor.SETTER, Accessor.GETTER};

    enum Accessor {
        SETTER,
        GETTER;
    }

    /**
     * When true, additions util methods will be generated for field configuration.
     */
    boolean isFlag() default false;

    /**
     * Name of the method which will be used to call native API.
     */
    String callName() default "";
}
