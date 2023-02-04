package imgui.callback;

import imgui.ImGuiInputTextCallbackData;

import java.util.function.Consumer;

public abstract class ImGuiInputTextCallback implements Consumer<ImGuiInputTextCallbackData> {
    public void nAccept(long ptr) {
        accept(new ImGuiInputTextCallbackData(ptr));
    }
}
