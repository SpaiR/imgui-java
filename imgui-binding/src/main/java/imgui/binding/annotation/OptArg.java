package imgui.binding.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used in conjunction with {@link BindingMethod} to indicate that the generated method parameter is optional.
 */
@ExcludedSource
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.SOURCE)
public @interface OptArg {
    /**
     * Used to define this default value. An additional method is generated without the annotated parameter on the
     * Java side, automatically passing the value specified in `callValue` to the native method.
     * <p><b>Constraints:</b>
     * <ul>
     *   <li>The `callValue` field should not be used on parameters that precede other
     *   optional parameters of the same type to avoid type collision.</li>
     *   <li>Ensure that the value in `callValue` is compatible with the parameter type.</li>
     * </ul>
     */
    String callValue() default "";
}
