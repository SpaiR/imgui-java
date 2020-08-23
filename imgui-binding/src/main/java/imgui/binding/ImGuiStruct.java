package imgui.binding;

import imgui.ImGui;

/**
 * Class provides a communication layer between binding and Dear ImGui library though the "pointer" field.
 */
public abstract class ImGuiStruct {
    /**
     * Pointer to the natively allocated object,
     */
    public long ptr;

    public ImGuiStruct(final long ptr) {
        ImGui.init();
        this.ptr = ptr;
    }
}
