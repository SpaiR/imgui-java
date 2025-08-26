package imgui.flag;


/**
 * Flags for ImGui::BeginChild()
 * (Legacy: bit 0 must always correspond to ImGuiChildFlags_Border to be backward compatible with old API using 'bool border = false'.
 * About using AutoResizeX/AutoResizeY flags:
 * - May be combined with SetNextWindowSizeConstraints() to set a min/max size for each axis (see {@code Demo->Child->Auto-resize with Constraints}).
 * - Size measurement for a given axis is only performed when the child window is within visible boundaries, or is just appearing.
 *   - This allows BeginChild() to return false when not within boundaries (e.g. when scrolling), which is more optimal. BUT it won't update its auto-size while clipped.
 *     While not perfect, it is a better default behavior as the always-on performance gain is more valuable than the occasional "resizing after becoming visible again" glitch.
 *   - You may also use ImGuiChildFlags_AlwaysAutoResize to force an update even when child window is not in view.
 *     HOWEVER PLEASE UNDERSTAND THAT DOING SO WILL PREVENT BeginChild() FROM EVER RETURNING FALSE, disabling benefits of coarse clipping.
 */
public final class ImGuiChildFlags {
    private ImGuiChildFlags() {
    }

    /**
     * Definition: {@code 0}
     */
    public static final int None = 0;

    /**
     * Show an outer border and enable WindowPadding. (IMPORTANT: this is always == 1 == true for legacy reason)
     *
     * <p>Definition: {@code 1 << 0}
     */
    public static final int Border = 1;

    /**
     * Pad with style.WindowPadding even if no border are drawn (no padding by default for non-bordered child windows because it makes more sense)
     *
     * <p>Definition: {@code 1 << 1}
     */
    public static final int AlwaysUseWindowPadding = 2;

    /**
     * Allow resize from right border (layout direction). Enable .ini saving (unless ImGuiWindowFlags_NoSavedSettings passed to window flags)
     *
     * <p>Definition: {@code 1 << 2}
     */
    public static final int ResizeX = 4;

    /**
     * Allow resize from bottom border (layout direction). "
     *
     * <p>Definition: {@code 1 << 3}
     */
    public static final int ResizeY = 8;

    /**
     * Enable auto-resizing width. Read "IMPORTANT: Size measurement" details above.
     *
     * <p>Definition: {@code 1 << 4}
     */
    public static final int AutoResizeX = 16;

    /**
     * Enable auto-resizing height. Read "IMPORTANT: Size measurement" details above.
     *
     * <p>Definition: {@code 1 << 5}
     */
    public static final int AutoResizeY = 32;

    /**
     * Combined with AutoResizeX/AutoResizeY. Always measure size even when child is hidden, always return true, always disable clipping optimization! NOT RECOMMENDED.
     *
     * <p>Definition: {@code 1 << 6}
     */
    public static final int AlwaysAutoResize = 64;

    /**
     * Style the child window like a framed item: use FrameBg, FrameRounding, FrameBorderSize, FramePadding instead of ChildBg, ChildRounding, ChildBorderSize, WindowPadding.
     *
     * <p>Definition: {@code 1 << 7}
     */
    public static final int FrameStyle = 128;

    /**
     * Share focus scope, allow gamepad/keyboard navigation to cross over parent border to this child or between sibling child windows.
     *
     * <p>Definition: {@code 1 << 8}
     */
    public static final int NavFlattened = 256;
}
