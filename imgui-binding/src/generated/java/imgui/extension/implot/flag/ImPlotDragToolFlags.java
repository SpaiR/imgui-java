package imgui.extension.implot.flag;





public final class ImPlotDragToolFlags {
    private ImPlotDragToolFlags() {
    }

    /**
     * default
     *
     * <p>Definition: {@code 0}
     */
    public static final int None = 0;

    /**
     * drag tools won't change cursor icons when hovered or held
     *
     * <p>Definition: {@code 1 << 0}
     */
    public static final int NoCursors = 1;

    /**
     * the drag tool won't be considered for plot fits
     *
     * <p>Definition: {@code 1 << 1}
     */
    public static final int NoFit = 2;

    /**
     * lock the tool from user inputs
     *
     * <p>Definition: {@code 1 << 2}
     */
    public static final int NoInputs = 4;

    /**
     * tool rendering will be delayed one frame; useful when applying position-constraints
     *
     * <p>Definition: {@code 1 << 3}
     */
    public static final int Delayed = 8;
}
