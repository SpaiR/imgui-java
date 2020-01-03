package imgui.callbacks;

import java.util.function.Supplier;

/**
 * Callback to get Java string from native code.
 */
public abstract class ImStrSupplier implements Supplier<String> {
    @Override
    public abstract String get();
}
