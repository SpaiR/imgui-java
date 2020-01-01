package imgui;

/**
 * Use this class to customize your ImGui input.
 */
public final class ImGuiInputTextData {
    /**
     * If not empty, then other chars which are different from provided will be filtered during the {@link ImGui#inputText}
     * and {@link ImGui#inputTextMultiline} methods.
     */
    public String allowedChars = "";

    /**
     * If true, then string will be resized during the the {@link ImGui#inputText} and {@link ImGui#inputTextMultiline} methods.
     * Alternatively you can provide {@link imgui.enums.ImGuiInputTextFlags#CallbackResize} flag to the input text widgets to enable string resizing.
     * Resize factor of the string could be modified by changing {@link ImString#resizeFactor} field.
     */
    public boolean isResizable;

    int size;
    boolean isDirty;
    boolean isResized = false;

    ImGuiInputTextData() {
    }
}
