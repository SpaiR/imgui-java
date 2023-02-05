package imgui.callback;

import imgui.ImGuiInputTextCallbackData;

import java.util.function.Consumer;

/**
 *
 */
public abstract class ImGuiInputTextCallback implements Consumer<ImGuiInputTextCallbackData> {
    /**
     * This function will be called by the native Callback and wrapped
     * @param ptr Ptr
     */
    public void nAccept(final long ptr) {
        accept(new ImGuiInputTextCallbackData(ptr));
    }
}
