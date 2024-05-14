package imgui.binding.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * This annotation is used as a marker to exclude source code from the final binding generation.
 */
@ExcludedSource
@Retention(RetentionPolicy.SOURCE)
public @interface ExcludedSource {
}
