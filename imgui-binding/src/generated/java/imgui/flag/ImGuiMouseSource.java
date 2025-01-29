package imgui.flag;


/**
 * Enumeration for AddMouseSourceEvent() actual source of Mouse Input data.
 * Historically we use "Mouse" terminology everywhere to indicate pointer data, e.g. MousePos, IsMousePressed(), io.AddMousePosEvent()
 * But that "Mouse" data can come from different source which occasionally may be useful for application to know about.
 * You can submit a change of pointer type using io.AddMouseSourceEvent().
 */
public final class ImGuiMouseSource {
    private ImGuiMouseSource() {
    }

    /**
     * Input is coming from an actual mouse.
     *
     * <p>Definition: {@code 0}
     */
    public static final int Mouse = 0;

    /**
     * Input is coming from a touch screen (no hovering prior to initial press, less precise initial press aiming, dual-axis wheeling possible).
     */
    public static final int TouchScreen = 1;

    /**
     * Input is coming from a pressure/magnetic pen (often used in conjunction with high-sampling rates).
     */
    public static final int Pen = 2;

    public static final int COUNT = 3;
}
