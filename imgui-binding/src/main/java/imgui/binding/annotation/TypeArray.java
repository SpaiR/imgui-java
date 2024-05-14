package imgui.binding.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Provides a metadata for the primitive array type value.
 */
@ExcludedSource
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
public @interface TypeArray {
    /**
     * Array type.
     */
    String type();

    /**
     * Array size.
     */
    String size();
}
