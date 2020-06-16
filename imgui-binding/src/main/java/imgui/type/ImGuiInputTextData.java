package imgui.type;

/**
 * Use this class to customize your ImGui input.
 */
public final class ImGuiInputTextData {
    private static final short DEFAULT_RESIZE_FACTOR = 10;

    /**
     * If not empty, then other chars which are different from provided will be filtered during the {@link imgui.ImGui#inputText}
     * and {@link imgui.ImGui#inputTextMultiline} methods.
     */
    public String allowedChars = "";

    /**
     * If true, then string will be resized during the the {@link imgui.ImGui#inputText} and {@link imgui.ImGui#inputTextMultiline} methods.
     * Alternatively you can provide {@link imgui.flag.ImGuiInputTextFlags#CallbackResize} flag to the input text widgets to enable string resizing.
     * Resize factor of the string could be modified by changing {@link #resizeFactor} field.
     */
    public boolean isResizable;

    /**
     * String will be resized to the value equal to a new size plus this resize factor.
     * Default value is 10.
     */
    public int resizeFactor = DEFAULT_RESIZE_FACTOR;

    int size;
    boolean isDirty;
    boolean isResized = false;

    ImGuiInputTextData() {
    }
}
