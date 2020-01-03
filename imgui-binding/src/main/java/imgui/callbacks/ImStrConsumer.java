package imgui.callbacks;

import java.util.function.Consumer;

/**
 * Callback to supply Java string form native code.
 */
public abstract class ImStrConsumer implements Consumer<String> {
    @Override
    public abstract void accept(String str);
}
