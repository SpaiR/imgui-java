package imgui.internal.flag;


public final class ImGuiFocusRequestFlags {
    private ImGuiFocusRequestFlags() {
    }

    /**
     * Definition: {@code 0}
     */
    public static final int None = 0;

    /**
     * Find last focused child (if any) and focus it instead.
     *
     * <p>Definition: {@code 1 << 0}
     */
    public static final int RestoreFocusedChild = 1;

    /**
     * Do not set focus if the window is below a modal.
     *
     * <p>Definition: {@code 1 << 1}
     */
    public static final int UnlessBelowModal = 2;
}
