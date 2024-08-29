package imgui;

import imgui.binding.ImGuiStructDestroyable;

/**
 * You may modify the ImGui::GetStyle() main instance during initialization and before NewFrame().
 * During the frame, use ImGui::PushStyleVar(ImGuiStyleVar_XXXX)/PopStyleVar() to alter the main style values,
 * and ImGui::PushStyleColor(ImGuiCol_XXX)/PopStyleColor() for colors.
 */
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
    public float getAlpha() {
        return nGetAlpha();
    }

    /**
     * Global alpha applies to everything in Dear ImGui.
     */
    public void setAlpha(final float value) {
        nSetAlpha(value);
    }

    private native float nGetAlpha(); /*
        return THIS->Alpha;
    */

    private native void nSetAlpha(float value); /*
        THIS->Alpha = value;
    */

    /**
     * Additional alpha multiplier applied by BeginDisabled(). Multiply over current value of Alpha.
     */
    public float getDisabledAlpha() {
        return nGetDisabledAlpha();
    }

    /**
     * Additional alpha multiplier applied by BeginDisabled(). Multiply over current value of Alpha.
     */
    public void setDisabledAlpha(final float value) {
        nSetDisabledAlpha(value);
    }

    private native float nGetDisabledAlpha(); /*
        return THIS->DisabledAlpha;
    */

    private native void nSetDisabledAlpha(float value); /*
        THIS->DisabledAlpha = value;
    */

    /**
     * Padding within a window.
     */
    public ImVec2 getWindowPadding() {
        final ImVec2 dst = new ImVec2();
        nGetWindowPadding(dst);
        return dst;
    }

    /**
     * Padding within a window.
     */
    public float getWindowPaddingX() {
        return nGetWindowPaddingX();
    }

    /**
     * Padding within a window.
     */
    public float getWindowPaddingY() {
        return nGetWindowPaddingY();
    }

    /**
     * Padding within a window.
     */
    public void getWindowPadding(final ImVec2 dst) {
        nGetWindowPadding(dst);
    }

    /**
     * Padding within a window.
     */
    public void setWindowPadding(final ImVec2 value) {
        nSetWindowPadding(value.x, value.y);
    }

    /**
     * Padding within a window.
     */
    public void setWindowPadding(final float valueX, final float valueY) {
        nSetWindowPadding(valueX, valueY);
    }

    private native void nGetWindowPadding(ImVec2 dst); /*
        Jni::ImVec2Cpy(env, THIS->WindowPadding, dst);
    */

    private native float nGetWindowPaddingX(); /*
        return THIS->WindowPadding.x;
    */

    private native float nGetWindowPaddingY(); /*
        return THIS->WindowPadding.y;
    */

    private native void nSetWindowPadding(float valueX, float valueY); /*MANUAL
        ImVec2 value = ImVec2(valueX, valueY);
        THIS->WindowPadding = value;
    */

    /**
     * Radius of window corners rounding. Set to 0.0f to have rectangular windows.
     * Large values tend to lead to variety of artifacts and are not recommended.
     */
    public float getWindowRounding() {
        return nGetWindowRounding();
    }

    /**
     * Radius of window corners rounding. Set to 0.0f to have rectangular windows.
     * Large values tend to lead to variety of artifacts and are not recommended.
     */
    public void setWindowRounding(final float value) {
        nSetWindowRounding(value);
    }

    private native float nGetWindowRounding(); /*
        return THIS->WindowRounding;
    */

    private native void nSetWindowRounding(float value); /*
        THIS->WindowRounding = value;
    */

    /**
     * Thickness of border around windows. Generally set to 0.0f or 1.0f. (Other values are not well tested and more CPU/GPU costly).
     */
    public float getWindowBorderSize() {
        return nGetWindowBorderSize();
    }

    /**
     * Thickness of border around windows. Generally set to 0.0f or 1.0f. (Other values are not well tested and more CPU/GPU costly).
     */
    public void setWindowBorderSize(final float value) {
        nSetWindowBorderSize(value);
    }

    private native float nGetWindowBorderSize(); /*
        return THIS->WindowBorderSize;
    */

    private native void nSetWindowBorderSize(float value); /*
        THIS->WindowBorderSize = value;
    */

    /**
     * Minimum window size. This is a global setting. If you want to constraint individual windows, use SetNextWindowSizeConstraints().
     */
    public ImVec2 getWindowMinSize() {
        final ImVec2 dst = new ImVec2();
        nGetWindowMinSize(dst);
        return dst;
    }

    /**
     * Minimum window size. This is a global setting. If you want to constraint individual windows, use SetNextWindowSizeConstraints().
     */
    public float getWindowMinSizeX() {
        return nGetWindowMinSizeX();
    }

    /**
     * Minimum window size. This is a global setting. If you want to constraint individual windows, use SetNextWindowSizeConstraints().
     */
    public float getWindowMinSizeY() {
        return nGetWindowMinSizeY();
    }

    /**
     * Minimum window size. This is a global setting. If you want to constraint individual windows, use SetNextWindowSizeConstraints().
     */
    public void getWindowMinSize(final ImVec2 dst) {
        nGetWindowMinSize(dst);
    }

    /**
     * Minimum window size. This is a global setting. If you want to constraint individual windows, use SetNextWindowSizeConstraints().
     */
    public void setWindowMinSize(final ImVec2 value) {
        nSetWindowMinSize(value.x, value.y);
    }

    /**
     * Minimum window size. This is a global setting. If you want to constraint individual windows, use SetNextWindowSizeConstraints().
     */
    public void setWindowMinSize(final float valueX, final float valueY) {
        nSetWindowMinSize(valueX, valueY);
    }

    private native void nGetWindowMinSize(ImVec2 dst); /*
        Jni::ImVec2Cpy(env, THIS->WindowMinSize, dst);
    */

    private native float nGetWindowMinSizeX(); /*
        return THIS->WindowMinSize.x;
    */

    private native float nGetWindowMinSizeY(); /*
        return THIS->WindowMinSize.y;
    */

    private native void nSetWindowMinSize(float valueX, float valueY); /*MANUAL
        ImVec2 value = ImVec2(valueX, valueY);
        THIS->WindowMinSize = value;
    */

    /**
     * Alignment for title bar text. Defaults to (0.0f,0.5f) for left-aligned,vertically centered.
     */
    public ImVec2 getWindowTitleAlign() {
        final ImVec2 dst = new ImVec2();
        nGetWindowTitleAlign(dst);
        return dst;
    }

    /**
     * Alignment for title bar text. Defaults to (0.0f,0.5f) for left-aligned,vertically centered.
     */
    public float getWindowTitleAlignX() {
        return nGetWindowTitleAlignX();
    }

    /**
     * Alignment for title bar text. Defaults to (0.0f,0.5f) for left-aligned,vertically centered.
     */
    public float getWindowTitleAlignY() {
        return nGetWindowTitleAlignY();
    }

    /**
     * Alignment for title bar text. Defaults to (0.0f,0.5f) for left-aligned,vertically centered.
     */
    public void getWindowTitleAlign(final ImVec2 dst) {
        nGetWindowTitleAlign(dst);
    }

    /**
     * Alignment for title bar text. Defaults to (0.0f,0.5f) for left-aligned,vertically centered.
     */
    public void setWindowTitleAlign(final ImVec2 value) {
        nSetWindowTitleAlign(value.x, value.y);
    }

    /**
     * Alignment for title bar text. Defaults to (0.0f,0.5f) for left-aligned,vertically centered.
     */
    public void setWindowTitleAlign(final float valueX, final float valueY) {
        nSetWindowTitleAlign(valueX, valueY);
    }

    private native void nGetWindowTitleAlign(ImVec2 dst); /*
        Jni::ImVec2Cpy(env, THIS->WindowTitleAlign, dst);
    */

    private native float nGetWindowTitleAlignX(); /*
        return THIS->WindowTitleAlign.x;
    */

    private native float nGetWindowTitleAlignY(); /*
        return THIS->WindowTitleAlign.y;
    */

    private native void nSetWindowTitleAlign(float valueX, float valueY); /*MANUAL
        ImVec2 value = ImVec2(valueX, valueY);
        THIS->WindowTitleAlign = value;
    */

    /**
     * Side of the collapsing/docking button in the title bar (None/Left/Right). Defaults to ImGuiDir_Left.
     */
    public int getWindowMenuButtonPosition() {
        return nGetWindowMenuButtonPosition();
    }

    /**
     * Side of the collapsing/docking button in the title bar (None/Left/Right). Defaults to ImGuiDir_Left.
     */
    public void setWindowMenuButtonPosition(final int value) {
        nSetWindowMenuButtonPosition(value);
    }

    private native int nGetWindowMenuButtonPosition(); /*
        return THIS->WindowMenuButtonPosition;
    */

    private native void nSetWindowMenuButtonPosition(int value); /*
        THIS->WindowMenuButtonPosition = value;
    */

    /**
     * Radius of child window corners rounding. Set to 0.0f to have rectangular windows.
     */
    public float getChildRounding() {
        return nGetChildRounding();
    }

    /**
     * Radius of child window corners rounding. Set to 0.0f to have rectangular windows.
     */
    public void setChildRounding(final float value) {
        nSetChildRounding(value);
    }

    private native float nGetChildRounding(); /*
        return THIS->ChildRounding;
    */

    private native void nSetChildRounding(float value); /*
        THIS->ChildRounding = value;
    */

    /**
     * Thickness of border around child windows. Generally set to 0.0f or 1.0f. (Other values are not well tested and more CPU/GPU costly).
     */
    public float getChildBorderSize() {
        return nGetChildBorderSize();
    }

    /**
     * Thickness of border around child windows. Generally set to 0.0f or 1.0f. (Other values are not well tested and more CPU/GPU costly).
     */
    public void setChildBorderSize(final float value) {
        nSetChildBorderSize(value);
    }

    private native float nGetChildBorderSize(); /*
        return THIS->ChildBorderSize;
    */

    private native void nSetChildBorderSize(float value); /*
        THIS->ChildBorderSize = value;
    */

    /**
     * Radius of popup window corners rounding. (Note that tooltip windows use WindowRounding)
     */
    public float getPopupRounding() {
        return nGetPopupRounding();
    }

    /**
     * Radius of popup window corners rounding. (Note that tooltip windows use WindowRounding)
     */
    public void setPopupRounding(final float value) {
        nSetPopupRounding(value);
    }

    private native float nGetPopupRounding(); /*
        return THIS->PopupRounding;
    */

    private native void nSetPopupRounding(float value); /*
        THIS->PopupRounding = value;
    */

    /**
     * Thickness of border around popup/tooltip windows. Generally set to 0.0f or 1.0f. (Other values are not well tested and more CPU/GPU costly).
     */
    public float getPopupBorderSize() {
        return nGetPopupBorderSize();
    }

    /**
     * Thickness of border around popup/tooltip windows. Generally set to 0.0f or 1.0f. (Other values are not well tested and more CPU/GPU costly).
     */
    public void setPopupBorderSize(final float value) {
        nSetPopupBorderSize(value);
    }

    private native float nGetPopupBorderSize(); /*
        return THIS->PopupBorderSize;
    */

    private native void nSetPopupBorderSize(float value); /*
        THIS->PopupBorderSize = value;
    */

    /**
     * Padding within a framed rectangle (used by most widgets).
     */
    public ImVec2 getFramePadding() {
        final ImVec2 dst = new ImVec2();
        nGetFramePadding(dst);
        return dst;
    }

    /**
     * Padding within a framed rectangle (used by most widgets).
     */
    public float getFramePaddingX() {
        return nGetFramePaddingX();
    }

    /**
     * Padding within a framed rectangle (used by most widgets).
     */
    public float getFramePaddingY() {
        return nGetFramePaddingY();
    }

    /**
     * Padding within a framed rectangle (used by most widgets).
     */
    public void getFramePadding(final ImVec2 dst) {
        nGetFramePadding(dst);
    }

    /**
     * Padding within a framed rectangle (used by most widgets).
     */
    public void setFramePadding(final ImVec2 value) {
        nSetFramePadding(value.x, value.y);
    }

    /**
     * Padding within a framed rectangle (used by most widgets).
     */
    public void setFramePadding(final float valueX, final float valueY) {
        nSetFramePadding(valueX, valueY);
    }

    private native void nGetFramePadding(ImVec2 dst); /*
        Jni::ImVec2Cpy(env, THIS->FramePadding, dst);
    */

    private native float nGetFramePaddingX(); /*
        return THIS->FramePadding.x;
    */

    private native float nGetFramePaddingY(); /*
        return THIS->FramePadding.y;
    */

    private native void nSetFramePadding(float valueX, float valueY); /*MANUAL
        ImVec2 value = ImVec2(valueX, valueY);
        THIS->FramePadding = value;
    */

    /**
     * Radius of frame corners rounding. Set to 0.0f to have rectangular frame (used by most widgets).
     */
    public float getFrameRounding() {
        return nGetFrameRounding();
    }

    /**
     * Radius of frame corners rounding. Set to 0.0f to have rectangular frame (used by most widgets).
     */
    public void setFrameRounding(final float value) {
        nSetFrameRounding(value);
    }

    private native float nGetFrameRounding(); /*
        return THIS->FrameRounding;
    */

    private native void nSetFrameRounding(float value); /*
        THIS->FrameRounding = value;
    */

    /**
     * Thickness of border around frames. Generally set to 0.0f or 1.0f. (Other values are not well tested and more CPU/GPU costly).
     */
    public float getFrameBorderSize() {
        return nGetFrameBorderSize();
    }

    /**
     * Thickness of border around frames. Generally set to 0.0f or 1.0f. (Other values are not well tested and more CPU/GPU costly).
     */
    public void setFrameBorderSize(final float value) {
        nSetFrameBorderSize(value);
    }

    private native float nGetFrameBorderSize(); /*
        return THIS->FrameBorderSize;
    */

    private native void nSetFrameBorderSize(float value); /*
        THIS->FrameBorderSize = value;
    */

    /**
     * Horizontal and vertical spacing between widgets/lines.
     */
    public ImVec2 getItemSpacing() {
        final ImVec2 dst = new ImVec2();
        nGetItemSpacing(dst);
        return dst;
    }

    /**
     * Horizontal and vertical spacing between widgets/lines.
     */
    public float getItemSpacingX() {
        return nGetItemSpacingX();
    }

    /**
     * Horizontal and vertical spacing between widgets/lines.
     */
    public float getItemSpacingY() {
        return nGetItemSpacingY();
    }

    /**
     * Horizontal and vertical spacing between widgets/lines.
     */
    public void getItemSpacing(final ImVec2 dst) {
        nGetItemSpacing(dst);
    }

    /**
     * Horizontal and vertical spacing between widgets/lines.
     */
    public void setItemSpacing(final ImVec2 value) {
        nSetItemSpacing(value.x, value.y);
    }

    /**
     * Horizontal and vertical spacing between widgets/lines.
     */
    public void setItemSpacing(final float valueX, final float valueY) {
        nSetItemSpacing(valueX, valueY);
    }

    private native void nGetItemSpacing(ImVec2 dst); /*
        Jni::ImVec2Cpy(env, THIS->ItemSpacing, dst);
    */

    private native float nGetItemSpacingX(); /*
        return THIS->ItemSpacing.x;
    */

    private native float nGetItemSpacingY(); /*
        return THIS->ItemSpacing.y;
    */

    private native void nSetItemSpacing(float valueX, float valueY); /*MANUAL
        ImVec2 value = ImVec2(valueX, valueY);
        THIS->ItemSpacing = value;
    */

    /**
     * Horizontal and vertical spacing between within elements of a composed widget (e.g. a slider and its label).
     */
    public ImVec2 getItemInnerSpacing() {
        final ImVec2 dst = new ImVec2();
        nGetItemInnerSpacing(dst);
        return dst;
    }

    /**
     * Horizontal and vertical spacing between within elements of a composed widget (e.g. a slider and its label).
     */
    public float getItemInnerSpacingX() {
        return nGetItemInnerSpacingX();
    }

    /**
     * Horizontal and vertical spacing between within elements of a composed widget (e.g. a slider and its label).
     */
    public float getItemInnerSpacingY() {
        return nGetItemInnerSpacingY();
    }

    /**
     * Horizontal and vertical spacing between within elements of a composed widget (e.g. a slider and its label).
     */
    public void getItemInnerSpacing(final ImVec2 dst) {
        nGetItemInnerSpacing(dst);
    }

    /**
     * Horizontal and vertical spacing between within elements of a composed widget (e.g. a slider and its label).
     */
    public void setItemInnerSpacing(final ImVec2 value) {
        nSetItemInnerSpacing(value.x, value.y);
    }

    /**
     * Horizontal and vertical spacing between within elements of a composed widget (e.g. a slider and its label).
     */
    public void setItemInnerSpacing(final float valueX, final float valueY) {
        nSetItemInnerSpacing(valueX, valueY);
    }

    private native void nGetItemInnerSpacing(ImVec2 dst); /*
        Jni::ImVec2Cpy(env, THIS->ItemInnerSpacing, dst);
    */

    private native float nGetItemInnerSpacingX(); /*
        return THIS->ItemInnerSpacing.x;
    */

    private native float nGetItemInnerSpacingY(); /*
        return THIS->ItemInnerSpacing.y;
    */

    private native void nSetItemInnerSpacing(float valueX, float valueY); /*MANUAL
        ImVec2 value = ImVec2(valueX, valueY);
        THIS->ItemInnerSpacing = value;
    */

    /**
     * Padding within a table cell.
     */
    public ImVec2 getCellPadding() {
        final ImVec2 dst = new ImVec2();
        nGetCellPadding(dst);
        return dst;
    }

    /**
     * Padding within a table cell.
     */
    public float getCellPaddingX() {
        return nGetCellPaddingX();
    }

    /**
     * Padding within a table cell.
     */
    public float getCellPaddingY() {
        return nGetCellPaddingY();
    }

    /**
     * Padding within a table cell.
     */
    public void getCellPadding(final ImVec2 dst) {
        nGetCellPadding(dst);
    }

    /**
     * Padding within a table cell.
     */
    public void setCellPadding(final ImVec2 value) {
        nSetCellPadding(value.x, value.y);
    }

    /**
     * Padding within a table cell.
     */
    public void setCellPadding(final float valueX, final float valueY) {
        nSetCellPadding(valueX, valueY);
    }

    private native void nGetCellPadding(ImVec2 dst); /*
        Jni::ImVec2Cpy(env, THIS->CellPadding, dst);
    */

    private native float nGetCellPaddingX(); /*
        return THIS->CellPadding.x;
    */

    private native float nGetCellPaddingY(); /*
        return THIS->CellPadding.y;
    */

    private native void nSetCellPadding(float valueX, float valueY); /*MANUAL
        ImVec2 value = ImVec2(valueX, valueY);
        THIS->CellPadding = value;
    */

    /**
     * Expand reactive bounding box for touch-based system where touch position is not accurate enough.
     * Unfortunately we don't sort widgets so priority on overlap will always be given to the first widget. So don't grow this too much!
     */
    public ImVec2 getTouchExtraPadding() {
        final ImVec2 dst = new ImVec2();
        nGetTouchExtraPadding(dst);
        return dst;
    }

    /**
     * Expand reactive bounding box for touch-based system where touch position is not accurate enough.
     * Unfortunately we don't sort widgets so priority on overlap will always be given to the first widget. So don't grow this too much!
     */
    public float getTouchExtraPaddingX() {
        return nGetTouchExtraPaddingX();
    }

    /**
     * Expand reactive bounding box for touch-based system where touch position is not accurate enough.
     * Unfortunately we don't sort widgets so priority on overlap will always be given to the first widget. So don't grow this too much!
     */
    public float getTouchExtraPaddingY() {
        return nGetTouchExtraPaddingY();
    }

    /**
     * Expand reactive bounding box for touch-based system where touch position is not accurate enough.
     * Unfortunately we don't sort widgets so priority on overlap will always be given to the first widget. So don't grow this too much!
     */
    public void getTouchExtraPadding(final ImVec2 dst) {
        nGetTouchExtraPadding(dst);
    }

    /**
     * Expand reactive bounding box for touch-based system where touch position is not accurate enough.
     * Unfortunately we don't sort widgets so priority on overlap will always be given to the first widget. So don't grow this too much!
     */
    public void setTouchExtraPadding(final ImVec2 value) {
        nSetTouchExtraPadding(value.x, value.y);
    }

    /**
     * Expand reactive bounding box for touch-based system where touch position is not accurate enough.
     * Unfortunately we don't sort widgets so priority on overlap will always be given to the first widget. So don't grow this too much!
     */
    public void setTouchExtraPadding(final float valueX, final float valueY) {
        nSetTouchExtraPadding(valueX, valueY);
    }

    private native void nGetTouchExtraPadding(ImVec2 dst); /*
        Jni::ImVec2Cpy(env, THIS->TouchExtraPadding, dst);
    */

    private native float nGetTouchExtraPaddingX(); /*
        return THIS->TouchExtraPadding.x;
    */

    private native float nGetTouchExtraPaddingY(); /*
        return THIS->TouchExtraPadding.y;
    */

    private native void nSetTouchExtraPadding(float valueX, float valueY); /*MANUAL
        ImVec2 value = ImVec2(valueX, valueY);
        THIS->TouchExtraPadding = value;
    */

    /**
     * Horizontal indentation when e.g. entering a tree node. Generally == (FontSize + FramePadding.x*2).
     */
    public float getIndentSpacing() {
        return nGetIndentSpacing();
    }

    /**
     * Horizontal indentation when e.g. entering a tree node. Generally == (FontSize + FramePadding.x*2).
     */
    public void setIndentSpacing(final float value) {
        nSetIndentSpacing(value);
    }

    private native float nGetIndentSpacing(); /*
        return THIS->IndentSpacing;
    */

    private native void nSetIndentSpacing(float value); /*
        THIS->IndentSpacing = value;
    */

    /**
     * Minimum horizontal spacing between two columns. Preferably {@code >} (FramePadding.x + 1).
     */
    public float getColumnsMinSpacing() {
        return nGetColumnsMinSpacing();
    }

    /**
     * Minimum horizontal spacing between two columns. Preferably {@code >} (FramePadding.x + 1).
     */
    public void setColumnsMinSpacing(final float value) {
        nSetColumnsMinSpacing(value);
    }

    private native float nGetColumnsMinSpacing(); /*
        return THIS->ColumnsMinSpacing;
    */

    private native void nSetColumnsMinSpacing(float value); /*
        THIS->ColumnsMinSpacing = value;
    */

    /**
     * Width of the vertical scrollbar, Height of the horizontal scrollbar.
     */
    public float getScrollbarSize() {
        return nGetScrollbarSize();
    }

    /**
     * Width of the vertical scrollbar, Height of the horizontal scrollbar.
     */
    public void setScrollbarSize(final float value) {
        nSetScrollbarSize(value);
    }

    private native float nGetScrollbarSize(); /*
        return THIS->ScrollbarSize;
    */

    private native void nSetScrollbarSize(float value); /*
        THIS->ScrollbarSize = value;
    */

    /**
     * Radius of grab corners for scrollbar.
     */
    public float getScrollbarRounding() {
        return nGetScrollbarRounding();
    }

    /**
     * Radius of grab corners for scrollbar.
     */
    public void setScrollbarRounding(final float value) {
        nSetScrollbarRounding(value);
    }

    private native float nGetScrollbarRounding(); /*
        return THIS->ScrollbarRounding;
    */

    private native void nSetScrollbarRounding(float value); /*
        THIS->ScrollbarRounding = value;
    */

    /**
     * Minimum width/height of a grab box for slider/scrollbar.
     */
    public float getGrabMinSize() {
        return nGetGrabMinSize();
    }

    /**
     * Minimum width/height of a grab box for slider/scrollbar.
     */
    public void setGrabMinSize(final float value) {
        nSetGrabMinSize(value);
    }

    private native float nGetGrabMinSize(); /*
        return THIS->GrabMinSize;
    */

    private native void nSetGrabMinSize(float value); /*
        THIS->GrabMinSize = value;
    */

    /**
     * Radius of grabs corners rounding. Set to 0.0f to have rectangular slider grabs.
     */
    public float getGrabRounding() {
        return nGetGrabRounding();
    }

    /**
     * Radius of grabs corners rounding. Set to 0.0f to have rectangular slider grabs.
     */
    public void setGrabRounding(final float value) {
        nSetGrabRounding(value);
    }

    private native float nGetGrabRounding(); /*
        return THIS->GrabRounding;
    */

    private native void nSetGrabRounding(float value); /*
        THIS->GrabRounding = value;
    */

    /**
     * The size in pixels of the dead-zone around zero on logarithmic sliders that cross zero.
     */
    public float getLogSliderDeadzone() {
        return nGetLogSliderDeadzone();
    }

    /**
     * The size in pixels of the dead-zone around zero on logarithmic sliders that cross zero.
     */
    public void setLogSliderDeadzone(final float value) {
        nSetLogSliderDeadzone(value);
    }

    private native float nGetLogSliderDeadzone(); /*
        return THIS->LogSliderDeadzone;
    */

    private native void nSetLogSliderDeadzone(float value); /*
        THIS->LogSliderDeadzone = value;
    */

    /**
     * Radius of upper corners of a tab. Set to 0.0f to have rectangular tabs.
     */
    public float getTabRounding() {
        return nGetTabRounding();
    }

    /**
     * Radius of upper corners of a tab. Set to 0.0f to have rectangular tabs.
     */
    public void setTabRounding(final float value) {
        nSetTabRounding(value);
    }

    private native float nGetTabRounding(); /*
        return THIS->TabRounding;
    */

    private native void nSetTabRounding(float value); /*
        THIS->TabRounding = value;
    */

    /**
     * Thickness of border around tabs.
     */
    public float getTabBorderSize() {
        return nGetTabBorderSize();
    }

    /**
     * Thickness of border around tabs.
     */
    public void setTabBorderSize(final float value) {
        nSetTabBorderSize(value);
    }

    private native float nGetTabBorderSize(); /*
        return THIS->TabBorderSize;
    */

    private native void nSetTabBorderSize(float value); /*
        THIS->TabBorderSize = value;
    */

    /**
     * Minimum width for close button to appears on an unselected tab when hovered.
     * Set to 0.0f to always show when hovering, set to FLT_MAX to never show close button unless selected.
     */
    public float getTabMinWidthForCloseButton() {
        return nGetTabMinWidthForCloseButton();
    }

    /**
     * Minimum width for close button to appears on an unselected tab when hovered.
     * Set to 0.0f to always show when hovering, set to FLT_MAX to never show close button unless selected.
     */
    public void setTabMinWidthForCloseButton(final float value) {
        nSetTabMinWidthForCloseButton(value);
    }

    private native float nGetTabMinWidthForCloseButton(); /*
        return THIS->TabMinWidthForCloseButton;
    */

    private native void nSetTabMinWidthForCloseButton(float value); /*
        THIS->TabMinWidthForCloseButton = value;
    */

    /**
     * Side of the color button in the ColorEdit4 widget (left/right). Defaults to ImGuiDir_Right.
     */
    public int getColorButtonPosition() {
        return nGetColorButtonPosition();
    }

    /**
     * Side of the color button in the ColorEdit4 widget (left/right). Defaults to ImGuiDir_Right.
     */
    public void setColorButtonPosition(final int value) {
        nSetColorButtonPosition(value);
    }

    private native int nGetColorButtonPosition(); /*
        return THIS->ColorButtonPosition;
    */

    private native void nSetColorButtonPosition(int value); /*
        THIS->ColorButtonPosition = value;
    */

    /**
     * Alignment of button text when button is larger than text. Defaults to (0.5f, 0.5f) (centered).
     */
    public ImVec2 getButtonTextAlign() {
        final ImVec2 dst = new ImVec2();
        nGetButtonTextAlign(dst);
        return dst;
    }

    /**
     * Alignment of button text when button is larger than text. Defaults to (0.5f, 0.5f) (centered).
     */
    public float getButtonTextAlignX() {
        return nGetButtonTextAlignX();
    }

    /**
     * Alignment of button text when button is larger than text. Defaults to (0.5f, 0.5f) (centered).
     */
    public float getButtonTextAlignY() {
        return nGetButtonTextAlignY();
    }

    /**
     * Alignment of button text when button is larger than text. Defaults to (0.5f, 0.5f) (centered).
     */
    public void getButtonTextAlign(final ImVec2 dst) {
        nGetButtonTextAlign(dst);
    }

    /**
     * Alignment of button text when button is larger than text. Defaults to (0.5f, 0.5f) (centered).
     */
    public void setButtonTextAlign(final ImVec2 value) {
        nSetButtonTextAlign(value.x, value.y);
    }

    /**
     * Alignment of button text when button is larger than text. Defaults to (0.5f, 0.5f) (centered).
     */
    public void setButtonTextAlign(final float valueX, final float valueY) {
        nSetButtonTextAlign(valueX, valueY);
    }

    private native void nGetButtonTextAlign(ImVec2 dst); /*
        Jni::ImVec2Cpy(env, THIS->ButtonTextAlign, dst);
    */

    private native float nGetButtonTextAlignX(); /*
        return THIS->ButtonTextAlign.x;
    */

    private native float nGetButtonTextAlignY(); /*
        return THIS->ButtonTextAlign.y;
    */

    private native void nSetButtonTextAlign(float valueX, float valueY); /*MANUAL
        ImVec2 value = ImVec2(valueX, valueY);
        THIS->ButtonTextAlign = value;
    */

    /**
     * Alignment of selectable text. Defaults to (0.0f, 0.0f) (top-left aligned).
     * It's generally important to keep this left-aligned if you want to lay multiple items on a same line.
     */
    public ImVec2 getSelectableTextAlign() {
        final ImVec2 dst = new ImVec2();
        nGetSelectableTextAlign(dst);
        return dst;
    }

    /**
     * Alignment of selectable text. Defaults to (0.0f, 0.0f) (top-left aligned).
     * It's generally important to keep this left-aligned if you want to lay multiple items on a same line.
     */
    public float getSelectableTextAlignX() {
        return nGetSelectableTextAlignX();
    }

    /**
     * Alignment of selectable text. Defaults to (0.0f, 0.0f) (top-left aligned).
     * It's generally important to keep this left-aligned if you want to lay multiple items on a same line.
     */
    public float getSelectableTextAlignY() {
        return nGetSelectableTextAlignY();
    }

    /**
     * Alignment of selectable text. Defaults to (0.0f, 0.0f) (top-left aligned).
     * It's generally important to keep this left-aligned if you want to lay multiple items on a same line.
     */
    public void getSelectableTextAlign(final ImVec2 dst) {
        nGetSelectableTextAlign(dst);
    }

    /**
     * Alignment of selectable text. Defaults to (0.0f, 0.0f) (top-left aligned).
     * It's generally important to keep this left-aligned if you want to lay multiple items on a same line.
     */
    public void setSelectableTextAlign(final ImVec2 value) {
        nSetSelectableTextAlign(value.x, value.y);
    }

    /**
     * Alignment of selectable text. Defaults to (0.0f, 0.0f) (top-left aligned).
     * It's generally important to keep this left-aligned if you want to lay multiple items on a same line.
     */
    public void setSelectableTextAlign(final float valueX, final float valueY) {
        nSetSelectableTextAlign(valueX, valueY);
    }

    private native void nGetSelectableTextAlign(ImVec2 dst); /*
        Jni::ImVec2Cpy(env, THIS->SelectableTextAlign, dst);
    */

    private native float nGetSelectableTextAlignX(); /*
        return THIS->SelectableTextAlign.x;
    */

    private native float nGetSelectableTextAlignY(); /*
        return THIS->SelectableTextAlign.y;
    */

    private native void nSetSelectableTextAlign(float valueX, float valueY); /*MANUAL
        ImVec2 value = ImVec2(valueX, valueY);
        THIS->SelectableTextAlign = value;
    */

    /**
     * Window position are clamped to be visible within the display area by at least this amount. Only applies to regular windows.
     */
    public ImVec2 getDisplayWindowPadding() {
        final ImVec2 dst = new ImVec2();
        nGetDisplayWindowPadding(dst);
        return dst;
    }

    /**
     * Window position are clamped to be visible within the display area by at least this amount. Only applies to regular windows.
     */
    public float getDisplayWindowPaddingX() {
        return nGetDisplayWindowPaddingX();
    }

    /**
     * Window position are clamped to be visible within the display area by at least this amount. Only applies to regular windows.
     */
    public float getDisplayWindowPaddingY() {
        return nGetDisplayWindowPaddingY();
    }

    /**
     * Window position are clamped to be visible within the display area by at least this amount. Only applies to regular windows.
     */
    public void getDisplayWindowPadding(final ImVec2 dst) {
        nGetDisplayWindowPadding(dst);
    }

    /**
     * Window position are clamped to be visible within the display area by at least this amount. Only applies to regular windows.
     */
    public void setDisplayWindowPadding(final ImVec2 value) {
        nSetDisplayWindowPadding(value.x, value.y);
    }

    /**
     * Window position are clamped to be visible within the display area by at least this amount. Only applies to regular windows.
     */
    public void setDisplayWindowPadding(final float valueX, final float valueY) {
        nSetDisplayWindowPadding(valueX, valueY);
    }

    private native void nGetDisplayWindowPadding(ImVec2 dst); /*
        Jni::ImVec2Cpy(env, THIS->DisplayWindowPadding, dst);
    */

    private native float nGetDisplayWindowPaddingX(); /*
        return THIS->DisplayWindowPadding.x;
    */

    private native float nGetDisplayWindowPaddingY(); /*
        return THIS->DisplayWindowPadding.y;
    */

    private native void nSetDisplayWindowPadding(float valueX, float valueY); /*MANUAL
        ImVec2 value = ImVec2(valueX, valueY);
        THIS->DisplayWindowPadding = value;
    */

    /**
     * If you cannot see the edges of your screen (e.g. on a TV) increase the safe area padding.
     * Apply to popups/tooltips as well regular windows. NB: Prefer configuring your TV sets correctly!
     */
    public ImVec2 getDisplaySafeAreaPadding() {
        final ImVec2 dst = new ImVec2();
        nGetDisplaySafeAreaPadding(dst);
        return dst;
    }

    /**
     * If you cannot see the edges of your screen (e.g. on a TV) increase the safe area padding.
     * Apply to popups/tooltips as well regular windows. NB: Prefer configuring your TV sets correctly!
     */
    public float getDisplaySafeAreaPaddingX() {
        return nGetDisplaySafeAreaPaddingX();
    }

    /**
     * If you cannot see the edges of your screen (e.g. on a TV) increase the safe area padding.
     * Apply to popups/tooltips as well regular windows. NB: Prefer configuring your TV sets correctly!
     */
    public float getDisplaySafeAreaPaddingY() {
        return nGetDisplaySafeAreaPaddingY();
    }

    /**
     * If you cannot see the edges of your screen (e.g. on a TV) increase the safe area padding.
     * Apply to popups/tooltips as well regular windows. NB: Prefer configuring your TV sets correctly!
     */
    public void getDisplaySafeAreaPadding(final ImVec2 dst) {
        nGetDisplaySafeAreaPadding(dst);
    }

    /**
     * If you cannot see the edges of your screen (e.g. on a TV) increase the safe area padding.
     * Apply to popups/tooltips as well regular windows. NB: Prefer configuring your TV sets correctly!
     */
    public void setDisplaySafeAreaPadding(final ImVec2 value) {
        nSetDisplaySafeAreaPadding(value.x, value.y);
    }

    /**
     * If you cannot see the edges of your screen (e.g. on a TV) increase the safe area padding.
     * Apply to popups/tooltips as well regular windows. NB: Prefer configuring your TV sets correctly!
     */
    public void setDisplaySafeAreaPadding(final float valueX, final float valueY) {
        nSetDisplaySafeAreaPadding(valueX, valueY);
    }

    private native void nGetDisplaySafeAreaPadding(ImVec2 dst); /*
        Jni::ImVec2Cpy(env, THIS->DisplaySafeAreaPadding, dst);
    */

    private native float nGetDisplaySafeAreaPaddingX(); /*
        return THIS->DisplaySafeAreaPadding.x;
    */

    private native float nGetDisplaySafeAreaPaddingY(); /*
        return THIS->DisplaySafeAreaPadding.y;
    */

    private native void nSetDisplaySafeAreaPadding(float valueX, float valueY); /*MANUAL
        ImVec2 value = ImVec2(valueX, valueY);
        THIS->DisplaySafeAreaPadding = value;
    */

    /**
     * Scale software rendered mouse cursor (when io.MouseDrawCursor is enabled). May be removed later.
     */
    public float getMouseCursorScale() {
        return nGetMouseCursorScale();
    }

    /**
     * Scale software rendered mouse cursor (when io.MouseDrawCursor is enabled). May be removed later.
     */
    public void setMouseCursorScale(final float value) {
        nSetMouseCursorScale(value);
    }

    private native float nGetMouseCursorScale(); /*
        return THIS->MouseCursorScale;
    */

    private native void nSetMouseCursorScale(float value); /*
        THIS->MouseCursorScale = value;
    */

    /**
     * Enable anti-aliased lines/borders. Disable if you are really tight on CPU/GPU. Latched at the beginning of the frame (copied to ImDrawList).
     */
    public boolean getAntiAliasedLines() {
        return nGetAntiAliasedLines();
    }

    /**
     * Enable anti-aliased lines/borders. Disable if you are really tight on CPU/GPU. Latched at the beginning of the frame (copied to ImDrawList).
     */
    public void setAntiAliasedLines(final boolean value) {
        nSetAntiAliasedLines(value);
    }

    private native boolean nGetAntiAliasedLines(); /*
        return THIS->AntiAliasedLines;
    */

    private native void nSetAntiAliasedLines(boolean value); /*
        THIS->AntiAliasedLines = value;
    */

    /**
     * Enable anti-aliased lines/borders using textures where possible.
     * Require backend to render with bilinear filtering.
     * Latched at the beginning of the frame (copied to ImDrawList).
     */
    public boolean getAntiAliasedLinesUseTex() {
        return nGetAntiAliasedLinesUseTex();
    }

    /**
     * Enable anti-aliased lines/borders using textures where possible.
     * Require backend to render with bilinear filtering.
     * Latched at the beginning of the frame (copied to ImDrawList).
     */
    public void setAntiAliasedLinesUseTex(final boolean value) {
        nSetAntiAliasedLinesUseTex(value);
    }

    private native boolean nGetAntiAliasedLinesUseTex(); /*
        return THIS->AntiAliasedLinesUseTex;
    */

    private native void nSetAntiAliasedLinesUseTex(boolean value); /*
        THIS->AntiAliasedLinesUseTex = value;
    */

    /**
     * Enable anti-aliased edges around filled shapes (rounded rectangles, circles, etc.).
     * Disable if you are really tight on CPU/GPU. Latched at the beginning of the frame (copied to ImDrawList).
     */
    public boolean getAntiAliasedFill() {
        return nGetAntiAliasedFill();
    }

    /**
     * Enable anti-aliased edges around filled shapes (rounded rectangles, circles, etc.).
     * Disable if you are really tight on CPU/GPU. Latched at the beginning of the frame (copied to ImDrawList).
     */
    public void setAntiAliasedFill(final boolean value) {
        nSetAntiAliasedFill(value);
    }

    private native boolean nGetAntiAliasedFill(); /*
        return THIS->AntiAliasedFill;
    */

    private native void nSetAntiAliasedFill(boolean value); /*
        THIS->AntiAliasedFill = value;
    */

    /**
     * Tessellation tolerance when using PathBezierCurveTo() without a specific number of segments.
     * Decrease for highly tessellated curves (higher quality, more polygons), increase to reduce quality.
     */
    public float getCurveTessellationTol() {
        return nGetCurveTessellationTol();
    }

    /**
     * Tessellation tolerance when using PathBezierCurveTo() without a specific number of segments.
     * Decrease for highly tessellated curves (higher quality, more polygons), increase to reduce quality.
     */
    public void setCurveTessellationTol(final float value) {
        nSetCurveTessellationTol(value);
    }

    private native float nGetCurveTessellationTol(); /*
        return THIS->CurveTessellationTol;
    */

    private native void nSetCurveTessellationTol(float value); /*
        THIS->CurveTessellationTol = value;
    */

    /**
     * Maximum error (in pixels) allowed when using AddCircle()/AddCircleFilled() or drawing rounded corner rectangles with no explicit segment count specified.
     * Decrease for higher quality but more geometry.
     */
    public float getCircleTessellationMaxError() {
        return nGetCircleTessellationMaxError();
    }

    /**
     * Maximum error (in pixels) allowed when using AddCircle()/AddCircleFilled() or drawing rounded corner rectangles with no explicit segment count specified.
     * Decrease for higher quality but more geometry.
     */
    public void setCircleTessellationMaxError(final float value) {
        nSetCircleTessellationMaxError(value);
    }

    private native float nGetCircleTessellationMaxError(); /*
        return THIS->CircleTessellationMaxError;
    */

    private native void nSetCircleTessellationMaxError(float value); /*
        THIS->CircleTessellationMaxError = value;
    */

    public ImVec4[] getColors() {
        return nGetColors();
    }

    public void setColors(final ImVec4[] value) {
        nSetColors(value);
    }

    private native ImVec4[] nGetColors(); /*
        return Jni::NewImVec4Array(env, THIS->Colors, ImGuiCol_COUNT);
    */

    private native void nSetColors(ImVec4[] value); /*
        Jni::ImVec4ArrayCpy(env, value, THIS->Colors, ImGuiCol_COUNT);
    */

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

    public void scaleAllSizes(final float scaleFactor) {
        nScaleAllSizes(scaleFactor);
    }

    private native void nScaleAllSizes(float scaleFactor); /*
        THIS->ScaleAllSizes(scaleFactor);
    */

    /*JNI
        #undef THIS
     */
}
