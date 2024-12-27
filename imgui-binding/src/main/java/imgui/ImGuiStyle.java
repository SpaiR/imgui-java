package imgui;

import imgui.binding.ImGuiStructDestroyable;
import imgui.binding.annotation.BindingField;
import imgui.binding.annotation.BindingMethod;
import imgui.binding.annotation.BindingSource;
import imgui.binding.annotation.TypeArray;

/**
 * You may modify the ImGui::GetStyle() main instance during initialization and before NewFrame().
 * During the frame, use ImGui::PushStyleVar(ImGuiStyleVar_XXXX)/PopStyleVar() to alter the main style values,
 * and ImGui::PushStyleColor(ImGuiCol_XXX)/PopStyleColor() for colors.
 */
@BindingSource
public final class ImGuiStyle extends ImGuiStructDestroyable {
    public ImGuiStyle() {
        super();
    }

    public ImGuiStyle(final long ptr) {
        super(ptr);
    }

    @Override
    protected long create() {
        return nCreate();
    }

    /*JNI
        #include "_common.h"
        #define THIS ((ImGuiStyle*)STRUCT_PTR)
     */

    private native long nCreate(); /*
        return (uintptr_t)(new ImGuiStyle());
    */

    /**
     * Global alpha applies to everything in Dear ImGui.
     */
    @BindingField
    public float Alpha;

    /**
     * Additional alpha multiplier applied by BeginDisabled(). Multiply over current value of Alpha.
     */
    @BindingField
    public float DisabledAlpha;

    /**
     * Padding within a window.
     */
    @BindingField
    public ImVec2 WindowPadding;

    /**
     * Radius of window corners rounding. Set to 0.0f to have rectangular windows.
     * Large values tend to lead to variety of artifacts and are not recommended.
     */
    @BindingField
    public float WindowRounding;

    /**
     * Thickness of border around windows. Generally set to 0.0f or 1.0f. (Other values are not well tested and more CPU/GPU costly).
     */
    @BindingField
    public float WindowBorderSize;

    /**
     * Minimum window size. This is a global setting. If you want to constraint individual windows, use SetNextWindowSizeConstraints().
     */
    @BindingField
    public ImVec2 WindowMinSize;

    /**
     * Alignment for title bar text. Defaults to (0.0f,0.5f) for left-aligned,vertically centered.
     */
    @BindingField
    public ImVec2 WindowTitleAlign;

    /**
     * Side of the collapsing/docking button in the title bar (None/Left/Right). Defaults to ImGuiDir_Left.
     */
    @BindingField
    public int WindowMenuButtonPosition;

    /**
     * Radius of child window corners rounding. Set to 0.0f to have rectangular windows.
     */
    @BindingField
    public float ChildRounding;

    /**
     * Thickness of border around child windows. Generally set to 0.0f or 1.0f. (Other values are not well tested and more CPU/GPU costly).
     */
    @BindingField
    public float ChildBorderSize;

    /**
     * Radius of popup window corners rounding. (Note that tooltip windows use WindowRounding)
     */
    @BindingField
    public float PopupRounding;

    /**
     * Thickness of border around popup/tooltip windows. Generally set to 0.0f or 1.0f. (Other values are not well tested and more CPU/GPU costly).
     */
    @BindingField
    public float PopupBorderSize;

    /**
     * Padding within a framed rectangle (used by most widgets).
     */
    @BindingField
    public ImVec2 FramePadding;

    /**
     * Radius of frame corners rounding. Set to 0.0f to have rectangular frame (used by most widgets).
     */
    @BindingField
    public float FrameRounding;

    /**
     * Thickness of border around frames. Generally set to 0.0f or 1.0f. (Other values are not well tested and more CPU/GPU costly).
     */
    @BindingField
    public float FrameBorderSize;

    /**
     * Horizontal and vertical spacing between widgets/lines.
     */
    @BindingField
    public ImVec2 ItemSpacing;

    /**
     * Horizontal and vertical spacing between within elements of a composed widget (e.g. a slider and its label).
     */
    @BindingField
    public ImVec2 ItemInnerSpacing;

    /**
     * Padding within a table cell.
     */
    @BindingField
    public ImVec2 CellPadding;

    /**
     * Expand reactive bounding box for touch-based system where touch position is not accurate enough.
     * Unfortunately we don't sort widgets so priority on overlap will always be given to the first widget. So don't grow this too much!
     */
    @BindingField
    public ImVec2 TouchExtraPadding;

    /**
     * Horizontal indentation when e.g. entering a tree node. Generally == (FontSize + FramePadding.x*2).
     */
    @BindingField
    public float IndentSpacing;

    /**
     * Minimum horizontal spacing between two columns. Preferably {@code >} (FramePadding.x + 1).
     */
    @BindingField
    public float ColumnsMinSpacing;

    /**
     * Width of the vertical scrollbar, Height of the horizontal scrollbar.
     */
    @BindingField
    public float ScrollbarSize;

    /**
     * Radius of grab corners for scrollbar.
     */
    @BindingField
    public float ScrollbarRounding;

    /**
     * Minimum width/height of a grab box for slider/scrollbar.
     */
    @BindingField
    public float GrabMinSize;

    /**
     * Radius of grabs corners rounding. Set to 0.0f to have rectangular slider grabs.
     */
    @BindingField
    public float GrabRounding;

    /**
     * The size in pixels of the dead-zone around zero on logarithmic sliders that cross zero.
     */
    @BindingField
    public float LogSliderDeadzone;

    /**
     * Radius of upper corners of a tab. Set to 0.0f to have rectangular tabs.
     */
    @BindingField
    public float TabRounding;

    /**
     * Thickness of border around tabs.
     */
    @BindingField
    public float TabBorderSize;

    /**
     * Minimum width for close button to appears on an unselected tab when hovered.
     * Set to 0.0f to always show when hovering, set to FLT_MAX to never show close button unless selected.
     */
    @BindingField
    public float TabMinWidthForCloseButton;

    /**
     * Side of the color button in the ColorEdit4 widget (left/right). Defaults to ImGuiDir_Right.
     */
    @BindingField
    public int ColorButtonPosition;

    /**
     * Alignment of button text when button is larger than text. Defaults to (0.5f, 0.5f) (centered).
     */
    @BindingField
    public ImVec2 ButtonTextAlign;

    /**
     * Alignment of selectable text. Defaults to (0.0f, 0.0f) (top-left aligned).
     * It's generally important to keep this left-aligned if you want to lay multiple items on a same line.
     */
    @BindingField
    public ImVec2 SelectableTextAlign;

    /**
     * Window position are clamped to be visible within the display area by at least this amount. Only applies to regular windows.
     */
    @BindingField
    public ImVec2 DisplayWindowPadding;

    /**
     * If you cannot see the edges of your screen (e.g. on a TV) increase the safe area padding.
     * Apply to popups/tooltips as well regular windows. NB: Prefer configuring your TV sets correctly!
     */
    @BindingField
    public ImVec2 DisplaySafeAreaPadding;

    /**
     * Scale software rendered mouse cursor (when io.MouseDrawCursor is enabled). May be removed later.
     */
    @BindingField
    public float MouseCursorScale;

    /**
     * Enable anti-aliased lines/borders. Disable if you are really tight on CPU/GPU. Latched at the beginning of the frame (copied to ImDrawList).
     */
    @BindingField
    public boolean AntiAliasedLines;

    /**
     * Enable anti-aliased lines/borders using textures where possible.
     * Require backend to render with bilinear filtering (NOT point/nearest filtering).
     * Latched at the beginning of the frame (copied to ImDrawList).
     */
    @BindingField
    public boolean AntiAliasedLinesUseTex;

    /**
     * Enable anti-aliased edges around filled shapes (rounded rectangles, circles, etc.).
     * Disable if you are really tight on CPU/GPU. Latched at the beginning of the frame (copied to ImDrawList).
     */
    @BindingField
    public boolean AntiAliasedFill;

    /**
     * Tessellation tolerance when using PathBezierCurveTo() without a specific number of segments.
     * Decrease for highly tessellated curves (higher quality, more polygons), increase to reduce quality.
     */
    @BindingField
    public float CurveTessellationTol;

    /**
     * Maximum error (in pixels) allowed when using AddCircle()/AddCircleFilled() or drawing rounded corner rectangles with no explicit segment count specified.
     * Decrease for higher quality but more geometry.
     */
    @BindingField
    public float CircleTessellationMaxError;

    @BindingField
    @TypeArray(type = "ImVec4", size = "ImGuiCol_COUNT")
    public ImVec4[] Colors;

    public ImVec4 getColor(final int col) {
        final ImVec4 dst = new ImVec4();
        getColor(col, dst);
        return dst;
    }

    public native void getColor(int col, ImVec4 dst); /*
        Jni::ImVec4Cpy(env, THIS->Colors[col], dst);
    */

    public native void setColor(int col, float r, float g, float b, float a); /*
        THIS->Colors[col] = ImColor((float)r, (float)g, (float)b, (float)a);
    */

    public native void setColor(int col, int r, int g, int b, int a); /*
        THIS->Colors[col] = ImColor((int)r, (int)g, (int)b, (int)a);
    */

    public native void setColor(int col, int value); /*
        THIS->Colors[col] = ImColor(value);
    */

    @BindingMethod
    public native void ScaleAllSizes(float scaleFactor);

    /*JNI
        #undef THIS
     */
}
