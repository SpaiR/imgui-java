package imgui.binding;

import imgui.ImGui;

/**
 * Class provides a communication layer between binding and Dear ImGui library through the "pointer" field.
 */
public abstract class ImGuiStruct {
    /**
     * Pointer to the natively allocated object.
     * Could be zero if JNI returned NULL instead of the real object.
     * For such cases methods {@link #isValidPtr()} and {@link #isNotValidPtr()} could be used to verify that object points to the real object.
     */
    public long ptr;

    public ImGuiStruct(final long ptr) {
        ImGui.init();
        this.ptr = ptr;
    }

    /**
     * Shows if object pointer is valid and does it point to the existing object.
     *
     * @return true, if pointer is valid
     */
    public final boolean isValidPtr() {
        return ptr != 0;
    }

    /**
     * Shows if object pointer is valid and does it point to the existing object.
     *
     * @return true, if pointer is invalid
     */
    public final boolean isNotValidPtr() {
        return !isValidPtr();
    }
}
