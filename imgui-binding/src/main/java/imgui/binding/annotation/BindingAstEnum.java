package imgui.binding.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Should be applied to a marker field. Provides metadata to generate "enum-like" values from the AST tree.
 * Marker field replaced with generated fields.
 */
@ExcludedSource
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.SOURCE)
public @interface BindingAstEnum {
    /**
     * AST file name to get the information.
     */
    String file();

    /**
     * Enum qualType to use for generation.
     */
    String qualType();

    /**
     * If provided, will be used to sanitize provided value from enum names.
     * Required for cases, when enum has one definition, but the value has different prefix.
     *
     * <p>Example:
     * <code>
     *     enum ImGuiDockNodeFlagsPrivate_ {
     *         ImGuiDockNodeFlags_DockSpace
     *     }
     * </code>
     */
    String sanitizeName() default "";
}
