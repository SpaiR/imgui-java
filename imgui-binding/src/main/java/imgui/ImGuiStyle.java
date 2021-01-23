package imgui;

import imgui.binding.ImGuiStructDestroyable;

/**
 * You may modify the ImGui::GetStyle() main instance during initialization and before NewFrame().
 * During the frame, use ImGui::PushStyleVar(ImGuiStyleVar_XXXX)/PopStyleVar() to alter the main style values,
 * and ImGui::PushStyleColor(ImGuiCol_XXX)/PopStyleColor() for colors.
 */
public final class ImGuiStyle extends ImGuiStructDestroyable {
    public ImGuiStyle() {
    }

    public ImGuiStyle(final long ptr) {
        super(ptr);
    }

    /*JNI
        #include <imgui.h>
        #include <stdint.h>
        #include "jni_common.h"
        #include "jni_binding_struct.h"

        #define IMGUI_STYLE ((ImGuiStyle*)STRUCT_PTR)
     */

    @Override
    protected long create() {
        return nCreate();
    }

    private native long nCreate(); /*
        return (intptr_t)(new ImGuiStyle());
    */

    /**
     * Global alpha applies to everything in Dear ImGui.
     */
    public native float getAlpha(); /*
        return IMGUI_STYLE->Alpha;
    */

    /**
     * Global alpha applies to everything in Dear ImGui.
     */
    public native void setAlpha(float alpha); /*
        IMGUI_STYLE->Alpha = alpha;
    */

    /**
     * Padding within a window.
     */
    public native void getWindowPadding(ImVec2 dstImVec2); /*
        Jni::ImVec2Cpy(env, &IMGUI_STYLE->WindowPadding, dstImVec2);
    */

    /**
     * Padding within a window.
     */
    public native float getWindowPaddingX(); /*
        return IMGUI_STYLE->WindowPadding.x;
    */

    /**
     * Padding within a window.
     */
    public native float getWindowPaddingY(); /*
        return IMGUI_STYLE->WindowPadding.y;
    */

    /**
     * Padding within a window.
     */
    public native void setWindowPadding(float x, float y); /*
        IMGUI_STYLE->WindowPadding.x = x;
        IMGUI_STYLE->WindowPadding.y = y;
    */

    /**
     * Radius of window corners rounding. Set to 0.0f to have rectangular windows.
     * Large values tend to lead to variety of artifacts and are not recommended.
     */
    public native float getWindowRounding(); /*
        return IMGUI_STYLE->WindowRounding;
    */

    /**
     * Radius of window corners rounding. Set to 0.0f to have rectangular windows.
     * Large values tend to lead to variety of artifacts and are not recommended.
     */
    public native void setWindowRounding(float windowRounding); /*
        IMGUI_STYLE->WindowRounding = windowRounding;
    */

    /**
     * Thickness of border around windows. Generally set to 0.0f or 1.0f. (Other values are not well tested and more CPU/GPU costly).
     */
    public native float getWindowBorderSize(); /*
        return IMGUI_STYLE->WindowBorderSize;
    */

    /**
     * Thickness of border around windows. Generally set to 0.0f or 1.0f. (Other values are not well tested and more CPU/GPU costly).
     */
    public native void setWindowBorderSize(float windowBorderSize); /*
        IMGUI_STYLE->WindowBorderSize = windowBorderSize;
    */

    /**
     * Minimum window size. This is a global setting. If you want to constraint individual windows, use SetNextWindowSizeConstraints().
     */
    public native void getWindowMinSize(ImVec2 dstImVec2); /*
        Jni::ImVec2Cpy(env, &IMGUI_STYLE->WindowMinSize, dstImVec2);
    */

    /**
     * Minimum window size. This is a global setting. If you want to constraint individual windows, use SetNextWindowSizeConstraints().
     */
    public native float getWindowMinSizeX(); /*
        return IMGUI_STYLE->WindowMinSize.x;
    */

    /**
     * Minimum window size. This is a global setting. If you want to constraint individual windows, use SetNextWindowSizeConstraints().
     */
    public native float getWindowMinSizeY(); /*
        return IMGUI_STYLE->WindowMinSize.y;
    */

    /**
     * Minimum window size. This is a global setting. If you want to constraint individual windows, use SetNextWindowSizeConstraints().
     */
    public native void setWindowMinSize(float x, float y); /*
        IMGUI_STYLE->WindowMinSize.x = x;
        IMGUI_STYLE->WindowMinSize.y = y;
    */

    /**
     * Alignment for title bar text. Defaults to (0.0f,0.5f) for left-aligned,vertically centered.
     */
    public native void getWindowTitleAlign(ImVec2 dstImVec2); /*
        Jni::ImVec2Cpy(env, &IMGUI_STYLE->WindowTitleAlign, dstImVec2);
    */

    /**
     * Alignment for title bar text. Defaults to (0.0f,0.5f) for left-aligned,vertically centered.
     */
    public native float getWindowTitleAlignX(); /*
        return IMGUI_STYLE->WindowTitleAlign.x;
    */

    /**
     * Alignment for title bar text. Defaults to (0.0f,0.5f) for left-aligned,vertically centered.
     */
    public native float getWindowTitleAlignY(); /*
        return IMGUI_STYLE->WindowTitleAlign.y;
    */

    /**
     * Alignment for title bar text. Defaults to (0.0f,0.5f) for left-aligned,vertically centered.
     */
    public native void setWindowTitleAlign(float x, float y); /*
        IMGUI_STYLE->WindowTitleAlign.x = x;
        IMGUI_STYLE->WindowTitleAlign.y = y;
    */

    /**
     * Side of the collapsing/docking button in the title bar (None/Left/Right). Defaults to ImGuiDir_Left.
     */
    public native int getWindowMenuButtonPosition(); /*
        return (int)IMGUI_STYLE->WindowMenuButtonPosition;
    */

    /**
     * Side of the collapsing/docking button in the title bar (None/Left/Right). Defaults to ImGuiDir_Left.
     */
    public native void setWindowMenuButtonPosition(int windowMenuButtonPosition); /*
        IMGUI_STYLE->WindowMenuButtonPosition = windowMenuButtonPosition;
    */

    /**
     * Radius of child window corners rounding. Set to 0.0f to have rectangular windows.
     */
    public native float getChildRounding(); /*
        return IMGUI_STYLE->ChildRounding;
    */

    /**
     * Radius of child window corners rounding. Set to 0.0f to have rectangular windows.
     */
    public native void setChildRounding(float childRounding); /*
        IMGUI_STYLE->ChildRounding = childRounding;
    */

    /**
     * Thickness of border around child windows. Generally set to 0.0f or 1.0f. (Other values are not well tested and more CPU/GPU costly).
     */
    public native float getChildBorderSize(); /*
        return IMGUI_STYLE->ChildBorderSize;
    */

    /**
     * Thickness of border around child windows. Generally set to 0.0f or 1.0f. (Other values are not well tested and more CPU/GPU costly).
     */
    public native void setChildBorderSize(float childBorderSize); /*
        IMGUI_STYLE->ChildBorderSize = childBorderSize;
    */

    /**
     * Radius of popup window corners rounding. (Note that tooltip windows use WindowRounding)
     */
    public native float getPopupRounding(); /*
        return IMGUI_STYLE->PopupRounding;
    */

    /**
     * Radius of popup window corners rounding. (Note that tooltip windows use WindowRounding)
     */
    public native void setPopupRounding(float popupRounding); /*
        IMGUI_STYLE->PopupRounding = popupRounding;
    */

    /**
     * Thickness of border around popup/tooltip windows. Generally set to 0.0f or 1.0f. (Other values are not well tested and more CPU/GPU costly).
     */
    public native float getPopupBorderSize(); /*
        return IMGUI_STYLE->PopupBorderSize;
    */

    /**
     * Thickness of border around popup/tooltip windows. Generally set to 0.0f or 1.0f. (Other values are not well tested and more CPU/GPU costly).
     */
    public native void setPopupBorderSize(float popupBorderSize); /*
        IMGUI_STYLE->PopupBorderSize = popupBorderSize;
    */

    /**
     * Padding within a framed rectangle (used by most widgets).
     */
    public native void getFramePadding(ImVec2 dstImVec2); /*
        Jni::ImVec2Cpy(env, &IMGUI_STYLE->FramePadding, dstImVec2);
    */

    /**
     * Padding within a framed rectangle (used by most widgets).
     */
    public native float getFramePaddingX(); /*
        return IMGUI_STYLE->FramePadding.x;
    */

    /**
     * Padding within a framed rectangle (used by most widgets).
     */
    public native float getFramePaddingY(); /*
        return IMGUI_STYLE->FramePadding.y;
    */

    /**
     * Padding within a framed rectangle (used by most widgets).
     */
    public native void setFramePadding(float x, float y); /*
        IMGUI_STYLE->FramePadding.x = x;
        IMGUI_STYLE->FramePadding.y = y;
    */

    /**
     * Radius of frame corners rounding. Set to 0.0f to have rectangular frame (used by most widgets).
     */
    public native float getFrameRounding(); /*
        return IMGUI_STYLE->FrameRounding;
    */

    /**
     * Radius of frame corners rounding. Set to 0.0f to have rectangular frame (used by most widgets).
     */
    public native void setFrameRounding(float frameRounding); /*
        IMGUI_STYLE->FrameRounding = frameRounding;
    */

    /**
     * Thickness of border around frames. Generally set to 0.0f or 1.0f. (Other values are not well tested and more CPU/GPU costly).
     */
    public native float getFrameBorderSize(); /*
        return IMGUI_STYLE->FrameBorderSize;
    */

    /**
     * Thickness of border around frames. Generally set to 0.0f or 1.0f. (Other values are not well tested and more CPU/GPU costly).
     */
    public native void setFrameBorderSize(float frameBorderSize); /*
        IMGUI_STYLE->FrameBorderSize = frameBorderSize;
    */

    /**
     * Horizontal and vertical spacing between widgets/lines.
     */
    public native void getItemSpacing(ImVec2 dstImVec2); /*
        Jni::ImVec2Cpy(env, &IMGUI_STYLE->ItemSpacing, dstImVec2);
    */

    /**
     * Horizontal and vertical spacing between widgets/lines.
     */
    public native float getItemSpacingX(); /*
        return IMGUI_STYLE->ItemSpacing.x;
    */

    /**
     * Horizontal and vertical spacing between widgets/lines.
     */
    public native float getItemSpacingY(); /*
        return IMGUI_STYLE->ItemSpacing.y;
    */

    /**
     * Horizontal and vertical spacing between widgets/lines.
     */
    public native void setItemSpacing(float x, float y); /*
        IMGUI_STYLE->ItemSpacing.x = x;
        IMGUI_STYLE->ItemSpacing.y = y;
    */

    /**
     * Horizontal and vertical spacing between within elements of a composed widget (e.g. a slider and its label).
     */
    public native void getItemInnerSpacing(ImVec2 dstImVec2); /*
        Jni::ImVec2Cpy(env, &IMGUI_STYLE->ItemInnerSpacing, dstImVec2);
    */

    /**
     * Horizontal and vertical spacing between within elements of a composed widget (e.g. a slider and its label).
     */
    public native float getItemInnerSpacingX(); /*
        return IMGUI_STYLE->ItemInnerSpacing.x;
    */

    /**
     * Horizontal and vertical spacing between within elements of a composed widget (e.g. a slider and its label).
     */
    public native float getItemInnerSpacingY(); /*
        return IMGUI_STYLE->ItemInnerSpacing.y;
    */

    /**
     * Horizontal and vertical spacing between within elements of a composed widget (e.g. a slider and its label).
     */
    public native void setItemInnerSpacing(float x, float y); /*
        IMGUI_STYLE->ItemInnerSpacing.x = x;
        IMGUI_STYLE->ItemInnerSpacing.y = y;
    */

    /**
     * Padding within a table cell.
     */
    public native void getCellPadding(ImVec2 dstImVec2); /*
        Jni::ImVec2Cpy(env, &IMGUI_STYLE->CellPadding, dstImVec2);
    */

    /**
     * Padding within a table cell.
     */
    public native float getCellPaddingX(); /*
        return IMGUI_STYLE->CellPadding.x;
    */

    /**
     * Padding within a table cell.
     */
    public native float getCellPaddingY(); /*
        return IMGUI_STYLE->CellPadding.y;
    */

    /**
     * Padding within a table cell.
     */
    public native void setCellPadding(float x, float y); /*
        IMGUI_STYLE->CellPadding.x = x;
        IMGUI_STYLE->CellPadding.y = y;
    */

    /**
     * Expand reactive bounding box for touch-based system where touch position is not accurate enough.
     * Unfortunately we don't sort widgets so priority on overlap will always be given to the first widget. So don't grow this too much!
     */
    public native void getTouchExtraPadding(ImVec2 dstImVec2); /*
        Jni::ImVec2Cpy(env, &IMGUI_STYLE->TouchExtraPadding, dstImVec2);
    */

    /**
     * Expand reactive bounding box for touch-based system where touch position is not accurate enough.
     * Unfortunately we don't sort widgets so priority on overlap will always be given to the first widget. So don't grow this too much!
     */
    public native float getTouchExtraPaddingX(); /*
        return IMGUI_STYLE->TouchExtraPadding.x;
    */

    /**
     * Expand reactive bounding box for touch-based system where touch position is not accurate enough.
     * Unfortunately we don't sort widgets so priority on overlap will always be given to the first widget. So don't grow this too much!
     */
    public native float getTouchExtraPaddingY(); /*
        return IMGUI_STYLE->TouchExtraPadding.y;
    */

    /**
     * Expand reactive bounding box for touch-based system where touch position is not accurate enough.
     * Unfortunately we don't sort widgets so priority on overlap will always be given to the first widget. So don't grow this too much!
     */
    public native void setTouchExtraPadding(float x, float y); /*
        IMGUI_STYLE->TouchExtraPadding.x = x;
        IMGUI_STYLE->TouchExtraPadding.y = y;
    */

    /**
     * Horizontal indentation when e.g. entering a tree node. Generally == (FontSize + FramePadding.x*2).
     */
    public native float getIndentSpacing(); /*
        return IMGUI_STYLE->IndentSpacing;
    */

    /**
     * Horizontal indentation when e.g. entering a tree node. Generally == (FontSize + FramePadding.x*2).
     */
    public native void setIndentSpacing(float indentSpacing); /*
        IMGUI_STYLE->IndentSpacing = indentSpacing;
    */

    /**
     * Minimum horizontal spacing between two columns. Preferably {@code >} (FramePadding.x + 1).
     */
    public native float getColumnsMinSpacing(); /*
        return IMGUI_STYLE->ColumnsMinSpacing;
    */

    /**
     * Minimum horizontal spacing between two columns. Preferably {@code >} (FramePadding.x + 1).
     */
    public native void setColumnsMinSpacing(float columnsMinSpacing); /*
        IMGUI_STYLE->ColumnsMinSpacing = columnsMinSpacing;
    */

    /**
     * Width of the vertical scrollbar, Height of the horizontal scrollbar.
     */
    public native float getScrollbarSize(); /*
        return IMGUI_STYLE->ScrollbarSize;
    */

    /**
     * Width of the vertical scrollbar, Height of the horizontal scrollbar.
     */
    public native void setScrollbarSize(float scrollbarSize); /*
        IMGUI_STYLE->ScrollbarSize = scrollbarSize;
    */

    /**
     * Radius of grab corners for scrollbar.
     */
    public native float getScrollbarRounding(); /*
        return IMGUI_STYLE->ScrollbarRounding;
    */

    /**
     * Radius of grab corners for scrollbar.
     */
    public native void setScrollbarRounding(float scrollbarRounding); /*
        IMGUI_STYLE->ScrollbarRounding = scrollbarRounding;
    */

    /**
     * Minimum width/height of a grab box for slider/scrollbar.
     */
    public native float getGrabMinSize(); /*
        return IMGUI_STYLE->GrabMinSize;
    */

    /**
     * Minimum width/height of a grab box for slider/scrollbar.
     */
    public native void setGrabMinSize(float grabMinSize); /*
        IMGUI_STYLE->GrabMinSize = grabMinSize;
    */

    /**
     * Radius of grabs corners rounding. Set to 0.0f to have rectangular slider grabs.
     */
    public native float getGrabRounding(); /*
        return IMGUI_STYLE->GrabRounding;
    */

    /**
     * Radius of grabs corners rounding. Set to 0.0f to have rectangular slider grabs.
     */
    public native void setGrabRounding(float grabRounding); /*
        IMGUI_STYLE->GrabRounding = grabRounding;
    */

    /**
     * The size in pixels of the dead-zone around zero on logarithmic sliders that cross zero.
     */
    public native float getLogSliderDeadzone(); /*
        return IMGUI_STYLE->LogSliderDeadzone;
    */

    /**
     * The size in pixels of the dead-zone around zero on logarithmic sliders that cross zero.
     */
    public native void setLogSliderDeadzone(float logSliderDeadzone); /*
        IMGUI_STYLE->LogSliderDeadzone = logSliderDeadzone;
    */

    /**
     * Radius of upper corners of a tab. Set to 0.0f to have rectangular tabs.
     */
    public native float getTabRounding(); /*
        return IMGUI_STYLE->TabRounding;
    */

    /**
     * Radius of upper corners of a tab. Set to 0.0f to have rectangular tabs.
     */
    public native void setTabRounding(float tabRounding); /*
        IMGUI_STYLE->TabRounding = tabRounding;
    */

    /**
     * Thickness of border around tabs.
     */
    public native float getTabBorderSize(); /*
        return IMGUI_STYLE->TabBorderSize;
    */

    /**
     * Thickness of border around tabs.
     */
    public native void setTabBorderSize(float tabBorderSize); /*
        IMGUI_STYLE->TabBorderSize = tabBorderSize;
    */

    /**
     * Minimum width for close button to appears on an unselected tab when hovered.
     * Set to 0.0f to always show when hovering, set to FLT_MAX to never show close button unless selected.
     */
    public native float getTabMinWidthForCloseButton(); /*
        return IMGUI_STYLE->TabMinWidthForCloseButton;
    */

    /**
     * Minimum width for close button to appears on an unselected tab when hovered.
     * Set to 0.0f to always show when hovering, set to FLT_MAX to never show close button unless selected.
     */
    public native void setTabMinWidthForCloseButton(float tabMinWidthForCloseButton); /*
        IMGUI_STYLE->TabMinWidthForCloseButton = tabMinWidthForCloseButton;
    */

    /**
     * Side of the color button in the ColorEdit4 widget (left/right). Defaults to ImGuiDir_Right.
     */
    public native int getColorButtonPosition(); /*
        return IMGUI_STYLE->ColorButtonPosition;
    */

    /**
     * Side of the color button in the ColorEdit4 widget (left/right). Defaults to ImGuiDir_Right.
     */
    public native void setColorButtonPosition(int colorButtonPosition); /*
        IMGUI_STYLE->ColorButtonPosition = colorButtonPosition;
    */

    /**
     * Alignment of button text when button is larger than text. Defaults to (0.5f, 0.5f) (centered).
     */
    public native void getButtonTextAlign(ImVec2 dstImVec2); /*
        Jni::ImVec2Cpy(env, &IMGUI_STYLE->ButtonTextAlign, dstImVec2);
    */

    /**
     * Alignment of button text when button is larger than text. Defaults to (0.5f, 0.5f) (centered).
     */
    public native float getButtonTextAlignX(); /*
        return IMGUI_STYLE->ButtonTextAlign.x;
    */

    /**
     * Alignment of button text when button is larger than text. Defaults to (0.5f, 0.5f) (centered).
     */
    public native float getButtonTextAlignY(); /*
        return IMGUI_STYLE->ButtonTextAlign.y;
    */

    /**
     * Alignment of button text when button is larger than text. Defaults to (0.5f, 0.5f) (centered).
     */
    public native void setButtonTextAlign(float x, float y); /*
        IMGUI_STYLE->ButtonTextAlign.x = x;
        IMGUI_STYLE->ButtonTextAlign.y = y;
    */

    /**
     * Alignment of selectable text. Defaults to (0.0f, 0.0f) (top-left aligned).
     * It's generally important to keep this left-aligned if you want to lay multiple items on a same line.
     */
    public native void getSelectableTextAlign(ImVec2 dstImVec2); /*
        Jni::ImVec2Cpy(env, &IMGUI_STYLE->SelectableTextAlign, dstImVec2);
    */

    /**
     * Alignment of selectable text. Defaults to (0.0f, 0.0f) (top-left aligned).
     * It's generally important to keep this left-aligned if you want to lay multiple items on a same line.
     */
    public native float getSelectableTextAlignX(); /*
        return IMGUI_STYLE->SelectableTextAlign.x;
    */

    /**
     * Alignment of selectable text. Defaults to (0.0f, 0.0f) (top-left aligned).
     * It's generally important to keep this left-aligned if you want to lay multiple items on a same line.
     */
    public native float getSelectableTextAlignY(); /*
        return IMGUI_STYLE->SelectableTextAlign.y;
    */

    /**
     * Alignment of selectable text. Defaults to (0.0f, 0.0f) (top-left aligned).
     * It's generally important to keep this left-aligned if you want to lay multiple items on a same line.
     */
    public native void setSelectableTextAlign(float x, float y); /*
        IMGUI_STYLE->SelectableTextAlign.x = x;
        IMGUI_STYLE->SelectableTextAlign.y = y;
    */

    /**
     * Window position are clamped to be visible within the display area by at least this amount. Only applies to regular windows.
     */
    public native void getDisplayWindowPadding(ImVec2 dstImVec2); /*
        Jni::ImVec2Cpy(env, &IMGUI_STYLE->DisplayWindowPadding, dstImVec2);
    */

    /**
     * Window position are clamped to be visible within the display area by at least this amount. Only applies to regular windows.
     */
    public native float getDisplayWindowPaddingX(); /*
        return IMGUI_STYLE->DisplayWindowPadding.x;
    */

    /**
     * Window position are clamped to be visible within the display area by at least this amount. Only applies to regular windows.
     */
    public native float getDisplayWindowPaddingY(); /*
        return IMGUI_STYLE->DisplayWindowPadding.y;
    */

    /**
     * Window position are clamped to be visible within the display area by at least this amount. Only applies to regular windows.
     */
    public native void setDisplayWindowPadding(float x, float y); /*
        IMGUI_STYLE->DisplayWindowPadding.x = x;
        IMGUI_STYLE->DisplayWindowPadding.y = y;
    */

    /**
     * If you cannot see the edges of your screen (e.g. on a TV) increase the safe area padding.
     * Apply to popups/tooltips as well regular windows. NB: Prefer configuring your TV sets correctly!
     */
    public native void getDisplaySafeAreaPadding(ImVec2 dstImVec2); /*
        Jni::ImVec2Cpy(env, &IMGUI_STYLE->DisplaySafeAreaPadding, dstImVec2);
    */

    /**
     * If you cannot see the edges of your screen (e.g. on a TV) increase the safe area padding.
     * Apply to popups/tooltips as well regular windows. NB: Prefer configuring your TV sets correctly!
     */
    public native float getDisplaySafeAreaPaddingX(); /*
        return IMGUI_STYLE->DisplaySafeAreaPadding.x;
    */

    /**
     * If you cannot see the edges of your screen (e.g. on a TV) increase the safe area padding.
     * Apply to popups/tooltips as well regular windows. NB: Prefer configuring your TV sets correctly!
     */
    public native float getDisplaySafeAreaPaddingY(); /*
        return IMGUI_STYLE->DisplaySafeAreaPadding.y;
    */

    /**
     * If you cannot see the edges of your screen (e.g. on a TV) increase the safe area padding.
     * Apply to popups/tooltips as well regular windows. NB: Prefer configuring your TV sets correctly!
     */
    public native void setDisplaySafeAreaPadding(float x, float y); /*
        IMGUI_STYLE->DisplaySafeAreaPadding.x = x;
        IMGUI_STYLE->DisplaySafeAreaPadding.y = y;
    */

    /**
     * Scale software rendered mouse cursor (when io.MouseDrawCursor is enabled). May be removed later.
     */
    public native float getMouseCursorScale(); /*
        return IMGUI_STYLE->MouseCursorScale;
    */

    /**
     * Scale software rendered mouse cursor (when io.MouseDrawCursor is enabled). May be removed later.
     */
    public native void setMouseCursorScale(float mouseCursorScale); /*
        IMGUI_STYLE->MouseCursorScale = mouseCursorScale;
    */

    /**
     * Enable anti-aliased lines/borders. Disable if you are really tight on CPU/GPU. Latched at the beginning of the frame (copied to ImDrawList).
     */
    public native boolean getAntiAliasedLines(); /*
        return IMGUI_STYLE->AntiAliasedLines;
    */

    /**
     * Enable anti-aliased lines/borders. Disable if you are really tight on CPU/GPU. Latched at the beginning of the frame (copied to ImDrawList).
     */
    public native void setAntiAliasedLines(boolean antiAliasedLines); /*
        IMGUI_STYLE->AntiAliasedLines = antiAliasedLines;
    */

    /**
     * Enable anti-aliased lines/borders using textures where possible.
     * Require backend to render with bilinear filtering.
     * Latched at the beginning of the frame (copied to ImDrawList).
     */
    public native boolean getAntiAliasedLinesUseTex(); /*
        return IMGUI_STYLE->AntiAliasedLinesUseTex;
    */

    /**
     * Enable anti-aliased lines/borders using textures where possible.
     * Require backend to render with bilinear filtering.
     * Latched at the beginning of the frame (copied to ImDrawList).
     */
    public native void setAntiAliasedLinesUseTex(boolean antiAliasedLinesUseTex); /*
        IMGUI_STYLE->AntiAliasedLinesUseTex = antiAliasedLinesUseTex;
    */

    /**
     * Enable anti-aliased edges around filled shapes (rounded rectangles, circles, etc.).
     * Disable if you are really tight on CPU/GPU. Latched at the beginning of the frame (copied to ImDrawList).
     */
    public native boolean getAntiAliasedFill(); /*
        return IMGUI_STYLE->AntiAliasedFill;
    */

    /**
     * Enable anti-aliased edges around filled shapes (rounded rectangles, circles, etc.).
     * Disable if you are really tight on CPU/GPU. Latched at the beginning of the frame (copied to ImDrawList).
     */
    public native void setAntiAliasedFill(boolean antiAliasedFill); /*
        IMGUI_STYLE->AntiAliasedFill = antiAliasedFill;
    */

    /**
     * Tessellation tolerance when using PathBezierCurveTo() without a specific number of segments.
     * Decrease for highly tessellated curves (higher quality, more polygons), increase to reduce quality.
     */
    public native float getCurveTessellationTol(); /*
        return IMGUI_STYLE->CurveTessellationTol;
    */

    /**
     * Tessellation tolerance when using PathBezierCurveTo() without a specific number of segments.
     * Decrease for highly tessellated curves (higher quality, more polygons), increase to reduce quality.
     */
    public native void setCurveTessellationTol(float curveTessellationTol); /*
        IMGUI_STYLE->CurveTessellationTol = curveTessellationTol;
    */

    /**
     * Maximum error (in pixels) allowed when using AddCircle()/AddCircleFilled() or drawing rounded corner rectangles with no explicit segment count specified.
     * Decrease for higher quality but more geometry.
     */
    public native float getCircleSegmentMaxError(); /*
        return IMGUI_STYLE->CircleSegmentMaxError;
    */

    /**
     * Maximum error (in pixels) allowed when using AddCircle()/AddCircleFilled() or drawing rounded corner rectangles with no explicit segment count specified.
     * Decrease for higher quality but more geometry.
     */
    public native void setCircleSegmentMaxError(float circleSegmentMaxError); /*
        IMGUI_STYLE->CircleSegmentMaxError = circleSegmentMaxError;
    */

    /**
     * BINDING NOTICE: colors is a 2d array with sizes: [ImGuiCol_COUNT][4]
     */
    public native void getColors(float[][] buff); /*
        for (int i = 0; i < ImGuiCol_COUNT; i++) {
            jfloatArray jColors = (jfloatArray)env->GetObjectArrayElement(buff, i);
            jfloat* jBuffColor = env->GetFloatArrayElements(jColors, 0);

            jBuffColor[0] = IMGUI_STYLE->Colors[i].x;
            jBuffColor[1] = IMGUI_STYLE->Colors[i].y;
            jBuffColor[2] = IMGUI_STYLE->Colors[i].z;
            jBuffColor[3] = IMGUI_STYLE->Colors[i].w;

            env->ReleaseFloatArrayElements(jColors, jBuffColor, 0);
            env->DeleteLocalRef(jColors);
        }
    */

    /**
     * BINDING NOTICE: colors is a 2d array with sizes: [ImGuiCol_COUNT][4]
     */
    public native void setColors(float[][] colors); /*
        for (int i = 0; i < ImGuiCol_COUNT; i++) {
            jfloatArray jColors = (jfloatArray)env->GetObjectArrayElement(colors, i);
            jfloat* jColor = env->GetFloatArrayElements(jColors, 0);

            IMGUI_STYLE->Colors[i].x = jColor[0];
            IMGUI_STYLE->Colors[i].y = jColor[1];
            IMGUI_STYLE->Colors[i].z = jColor[2];
            IMGUI_STYLE->Colors[i].w = jColor[3];

            env->ReleaseFloatArrayElements(jColors, jColor, 0);
            env->DeleteLocalRef(jColors);
        }
    */

    public native void getColor(int imGuiCol, ImVec4 dstImVec4); /*
        Jni::ImVec4Cpy(env, IMGUI_STYLE->Colors[imGuiCol], dstImVec4);
    */

    public native void setColor(int imGuiCol, float r, float g, float b, float a); /*
        IMGUI_STYLE->Colors[imGuiCol] = ImColor((float)r, (float)g, (float)b, (float)a);
    */

    public native void setColor(int imGuiCol, int r, int g, int b, int a); /*
        IMGUI_STYLE->Colors[imGuiCol] = ImColor((int)r, (int)g, (int)b, (int)a);
    */

    public native void setColor(int imGuiCol, int col); /*
        IMGUI_STYLE->Colors[imGuiCol] = ImColor(col);
    */

    public native void scaleAllSizes(float scaleFactor); /*
        IMGUI_STYLE->ScaleAllSizes(scaleFactor);
    */
}
