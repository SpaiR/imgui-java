package imgui;

/**
 * You may modify the ImGui::GetStyle() main instance during initialization and before NewFrame().
 * During the frame, use ImGui::PushStyleVar(ImGuiStyleVar_XXXX)/PopStyleVar() to alter the main style values,
 * and ImGui::PushStyleColor(ImGuiCol_XXX)/PopStyleColor() for colors.
 * <p>
 * BINDING NOTICE: This is not a fully featured representation of ImGuiStyle struct from C++.
 * Mostly it's a stub which communicates with default ImGuiStyle used by ImGuiIO.
 * Still can be used to make your interface look different, since all of the fields from the original header file are represented here.
 */
public final class ImGuiStyle {
    ImGuiStyle() {
    }

    /*JNI
        #include <imgui.h>
        #include "jni_common.h"
     */

    // Alpha
    // Global alpha applies to everything in Dear ImGui.
    //
    public native float getAlpha(); /*
        return ImGui::GetStyle().Alpha;
    */

    public native void setAlpha(float alpha); /*
        ImGui::GetStyle().Alpha = alpha;
    */

    // WindowPadding
    // Padding within a window.
    //
    public native void getWindowPadding(ImVec2 dstImVec2); /*
        Jni::ImVec2Cpy(env, &ImGui::GetStyle().WindowPadding, dstImVec2);
    */

    public native void setWindowPadding(float x, float y); /*
        ImGui::GetStyle().WindowPadding.x = x;
        ImGui::GetStyle().WindowPadding.y = y;
    */

    // WindowRounding
    // Radius of window corners rounding. Set to 0.0f to have rectangular windows.
    //
    public native float getWindowRounding(); /*
        return ImGui::GetStyle().WindowRounding;
    */

    public native void setWindowRounding(float windowRounding); /*
        ImGui::GetStyle().WindowRounding = windowRounding;
    */

    // WindowBorderSize
    // Thickness of border around windows. Generally set to 0.0f or 1.0f. (Other values are not well tested and more CPU/GPU costly).
    //
    public native float getWindowBorderSize(); /*
        return ImGui::GetStyle().WindowBorderSize;
    */

    public native void setWindowBorderSize(float windowBorderSize); /*
        ImGui::GetStyle().WindowBorderSize = windowBorderSize;
    */

    // WindowMinSize
    // Minimum window size. This is a global setting. If you want to constraint individual windows, use SetNextWindowSizeConstraints().
    //
    public native void getWindowMinSize(ImVec2 dstImVec2); /*
        Jni::ImVec2Cpy(env, &ImGui::GetStyle().WindowMinSize, dstImVec2);
    */

    public native void setWindowMinSize(float x, float y); /*
        ImGui::GetStyle().WindowMinSize.x = x;
        ImGui::GetStyle().WindowMinSize.y = y;
    */

    // WindowTitleAlign
    // Alignment for title bar text. Defaults to (0.0f,0.5f) for left-aligned,vertically centered.
    //
    public native void getWindowTitleAlign(ImVec2 dstImVec2); /*
        Jni::ImVec2Cpy(env, &ImGui::GetStyle().WindowTitleAlign, dstImVec2);
    */

    public native void setWindowTitleAlign(float x, float y); /*
        ImGui::GetStyle().WindowTitleAlign.x = x;
        ImGui::GetStyle().WindowTitleAlign.y = y;
    */

    // WindowMenuButtonPosition
    // Side of the collapsing/docking button in the title bar (None/Left/Right). Defaults to ImGuiDir_Left.
    //
    public native int getWindowMenuButtonPosition(); /*
        return (int)ImGui::GetStyle().WindowMenuButtonPosition;
    */

    public native void setWindowMenuButtonPosition(int windowMenuButtonPosition); /*
        ImGui::GetStyle().WindowMenuButtonPosition = windowMenuButtonPosition;
    */

    // ChildRounding
    // Radius of child window corners rounding. Set to 0.0f to have rectangular windows.
    //
    public native float getChildRounding(); /*
        return ImGui::GetStyle().ChildRounding;
    */

    public native void setChildRounding(float childRounding); /*
        ImGui::GetStyle().ChildRounding = childRounding;
    */

    // ChildBorderSize
    // Thickness of border around child windows. Generally set to 0.0f or 1.0f. (Other values are not well tested and more CPU/GPU costly).
    //
    public native float getChildBorderSize(); /*
        return ImGui::GetStyle().ChildBorderSize;
    */

    public native void setChildBorderSize(float childBorderSize); /*
        ImGui::GetStyle().ChildBorderSize = childBorderSize;
    */

    // PopupRounding
    // Radius of popup window corners rounding. (Note that tooltip windows use WindowRounding)
    //
    public native float getPopupRounding(); /*
        return ImGui::GetStyle().PopupRounding;
    */

    public native void setPopupRounding(float popupRounding); /*
        ImGui::GetStyle().PopupRounding = popupRounding;
    */

    // PopupBorderSize
    // Thickness of border around popup/tooltip windows. Generally set to 0.0f or 1.0f. (Other values are not well tested and more CPU/GPU costly).
    //
    public native float getPopupBorderSize(); /*
        return ImGui::GetStyle().PopupBorderSize;
    */

    public native void setPopupBorderSize(float popupBorderSize); /*
        ImGui::GetStyle().PopupBorderSize = popupBorderSize;
    */

    // FramePadding
    // Padding within a framed rectangle (used by most widgets).
    //
    public native void getFramePadding(ImVec2 dstImVec2); /*
        Jni::ImVec2Cpy(env, &ImGui::GetStyle().FramePadding, dstImVec2);
    */

    public native void setFramePadding(float x, float y); /*
        ImGui::GetStyle().FramePadding.x = x;
        ImGui::GetStyle().FramePadding.y = y;
    */

    // FrameRounding
    // Radius of frame corners rounding. Set to 0.0f to have rectangular frame (used by most widgets).
    //
    public native float getFrameRounding(); /*
        return ImGui::GetStyle().FrameRounding;
    */

    public native void setFrameRounding(float frameRounding); /*
        ImGui::GetStyle().FrameRounding = frameRounding;
    */

    // FrameBorderSize
    // Thickness of border around frames. Generally set to 0.0f or 1.0f. (Other values are not well tested and more CPU/GPU costly).
    //
    public native float getFrameBorderSize(); /*
        return ImGui::GetStyle().FrameBorderSize;
    */

    public native void setFrameBorderSize(float frameBorderSize); /*
        ImGui::GetStyle().FrameBorderSize = frameBorderSize;
    */

    // ItemSpacing
    // Horizontal and vertical spacing between widgets/lines.
    //
    public native void getItemSpacing(ImVec2 dstImVec2); /*
        Jni::ImVec2Cpy(env, &ImGui::GetStyle().ItemSpacing, dstImVec2);
    */

    public native void setItemSpacing(float x, float y); /*
        ImGui::GetStyle().ItemSpacing.x = x;
        ImGui::GetStyle().ItemSpacing.y = y;
    */

    // ItemInnerSpacing
    // Horizontal and vertical spacing between within elements of a composed widget (e.g. a slider and its label).
    //
    public native void getItemInnerSpacing(ImVec2 dstImVec2); /*
        Jni::ImVec2Cpy(env, &ImGui::GetStyle().ItemInnerSpacing, dstImVec2);
    */

    public native void setItemInnerSpacing(float x, float y); /*
        ImGui::GetStyle().ItemInnerSpacing.x = x;
        ImGui::GetStyle().ItemInnerSpacing.y = y;
    */

    // TouchExtraPadding
    // Expand reactive bounding box for touch-based system where touch position is not accurate enough.
    // Unfortunately we don't sort widgets so priority on overlap will always be given to the first widget. So don't grow this too much!
    //
    public native void getTouchExtraPadding(ImVec2 dstImVec2); /*
        Jni::ImVec2Cpy(env, &ImGui::GetStyle().TouchExtraPadding, dstImVec2);
    */

    public native void setTouchExtraPadding(float x, float y); /*
        ImGui::GetStyle().TouchExtraPadding.x = x;
        ImGui::GetStyle().TouchExtraPadding.y = y;
    */

    // IndentSpacing
    // Horizontal indentation when e.g. entering a tree node. Generally == (FontSize + FramePadding.x*2).
    //
    public native float getIndentSpacing(); /*
        return ImGui::GetStyle().IndentSpacing;
    */

    public native void setIndentSpacing(float indentSpacing); /*
        ImGui::GetStyle().IndentSpacing = indentSpacing;
    */

    // ColumnsMinSpacing
    // Minimum horizontal spacing between two columns. Preferably > (FramePadding.x + 1).
    //
    public native float getColumnsMinSpacing(); /*
        return ImGui::GetStyle().ColumnsMinSpacing;
    */

    public native void setColumnsMinSpacing(float columnsMinSpacing); /*
        ImGui::GetStyle().ColumnsMinSpacing = columnsMinSpacing;
    */

    // ScrollbarSize
    // Width of the vertical scrollbar, Height of the horizontal scrollbar.
    //
    public native float getScrollbarSize(); /*
        return ImGui::GetStyle().ScrollbarSize;
    */

    public native void setScrollbarSize(float scrollbarSize); /*
        ImGui::GetStyle().ScrollbarSize = scrollbarSize;
    */

    // ScrollbarRounding
    // Radius of grab corners for scrollbar.
    //
    public native float getScrollbarRounding(); /*
        return ImGui::GetStyle().ScrollbarRounding;
    */

    public native void setScrollbarRounding(float scrollbarRounding); /*
        ImGui::GetStyle().ScrollbarRounding = scrollbarRounding;
    */

    // GrabMinSize
    // Minimum width/height of a grab box for slider/scrollbar.
    //
    public native float getGrabMinSize(); /*
        return ImGui::GetStyle().GrabMinSize;
    */

    public native void setGrabMinSize(float grabMinSize); /*
        ImGui::GetStyle().GrabMinSize = grabMinSize;
    */

    // GrabRounding
    // Radius of grabs corners rounding. Set to 0.0f to have rectangular slider grabs.
    //
    public native float getGrabRounding(); /*
        return ImGui::GetStyle().GrabRounding;
    */

    public native void setGrabRounding(float grabRounding); /*
        ImGui::GetStyle().GrabRounding = grabRounding;
    */

    // TabRounding
    // Radius of upper corners of a tab. Set to 0.0f to have rectangular tabs.
    //
    public native float getTabRounding(); /*
        return ImGui::GetStyle().TabRounding;
    */

    public native void setTabRounding(float tabRounding); /*
        ImGui::GetStyle().TabRounding = tabRounding;
    */

    // TabBorderSize
    // Thickness of border around tabs.
    //
    public native float getTabBorderSize(); /*
        return ImGui::GetStyle().TabBorderSize;
    */

    public native void setTabBorderSize(float tabBorderSize); /*
        ImGui::GetStyle().TabBorderSize = tabBorderSize;
    */

    // ColorButtonPosition
    // Side of the color button in the ColorEdit4 widget (left/right). Defaults to ImGuiDir_Right.
    //
    public native int getColorButtonPosition(); /*
        return ImGui::GetStyle().ColorButtonPosition;
    */

    public native void setColorButtonPosition(int colorButtonPosition); /*
        ImGui::GetStyle().ColorButtonPosition = colorButtonPosition;
    */

    // ButtonTextAlign
    // Alignment of button text when button is larger than text. Defaults to (0.5f, 0.5f) (centered).
    //
    public native void getButtonTextAlign(ImVec2 dstImVec2); /*
        Jni::ImVec2Cpy(env, &ImGui::GetStyle().ButtonTextAlign, dstImVec2);
    */

    public native void setButtonTextAlign(float x, float y); /*
        ImGui::GetStyle().ButtonTextAlign.x = x;
        ImGui::GetStyle().ButtonTextAlign.y = y;
    */

    // SelectableTextAlign
    // Alignment of selectable text when selectable is larger than text. Defaults to (0.0f, 0.0f) (top-left aligned).
    //
    public native void getSelectableTextAlign(ImVec2 dstImVec2); /*
        Jni::ImVec2Cpy(env, &ImGui::GetStyle().SelectableTextAlign, dstImVec2);
    */

    public native void setSelectableTextAlign(float x, float y); /*
        ImGui::GetStyle().SelectableTextAlign.x = x;
        ImGui::GetStyle().SelectableTextAlign.y = y;
    */

    // DisplayWindowPadding
    // Window position are clamped to be visible within the display area by at least this amount. Only applies to regular windows.
    //
    public native void getDisplayWindowPadding(ImVec2 dstImVec2); /*
        Jni::ImVec2Cpy(env, &ImGui::GetStyle().DisplayWindowPadding, dstImVec2);
    */

    public native void setDisplayWindowPadding(float x, float y); /*
        ImGui::GetStyle().DisplayWindowPadding.x = x;
        ImGui::GetStyle().DisplayWindowPadding.y = y;
    */

    // DisplaySafeAreaPadding
    // If you cannot see the edges of your screen (e.g. on a TV) increase the safe area padding.
    // Apply to popups/tooltips as well regular windows. NB: Prefer configuring your TV sets correctly!
    //
    public native void getDisplaySafeAreaPadding(ImVec2 dstImVec2); /*
        Jni::ImVec2Cpy(env, &ImGui::GetStyle().DisplaySafeAreaPadding, dstImVec2);
    */

    public native void setDisplaySafeAreaPadding(float x, float y); /*
        ImGui::GetStyle().DisplaySafeAreaPadding.x = x;
        ImGui::GetStyle().DisplaySafeAreaPadding.y = y;
    */

    // MouseCursorScale
    // Scale software rendered mouse cursor (when io.MouseDrawCursor is enabled). May be removed later.
    //
    public native float getMouseCursorScale(); /*
        return ImGui::GetStyle().MouseCursorScale;
    */

    public native void setMouseCursorScale(float mouseCursorScale); /*
        ImGui::GetStyle().MouseCursorScale = mouseCursorScale;
    */

    // AntiAliasedLines
    // Enable anti-aliasing on lines/borders. Disable if you are really tight on CPU/GPU.
    //
    public native boolean getAntiAliasedLines(); /*
        return ImGui::GetStyle().AntiAliasedLines;
    */

    public native void setAntiAliasedLines(boolean antiAliasedLines); /*
        ImGui::GetStyle().AntiAliasedLines = antiAliasedLines;
    */

    // AntiAliasedFill
    // Enable anti-aliasing on filled shapes (rounded rectangles, circles, etc.)
    //
    public native boolean getAntiAliasedFill(); /*
        return ImGui::GetStyle().AntiAliasedFill;
    */

    public native void setAntiAliasedFill(boolean antiAliasedFill); /*
        ImGui::GetStyle().AntiAliasedFill = antiAliasedFill;
    */

    // CurveTessellationTol
    // Tessellation tolerance when using PathBezierCurveTo() without a specific number of segments.
    // Decrease for highly tessellated curves (higher quality, more polygons), increase to reduce quality.
    //
    public native float getCurveTessellationTol(); /*
        return ImGui::GetStyle().CurveTessellationTol;
    */

    public native void setCurveTessellationTol(float curveTessellationTol); /*
        ImGui::GetStyle().CurveTessellationTol = curveTessellationTol;
    */

    // Colors
    // BINDING NOTICE: buff is a 2d array with sizes: [ImGuiCol_COUNT][4]
    //
    public native void getColors(float[][] buff); /*
        for (int i = 0; i < ImGuiCol_COUNT; i++) {
            jfloatArray jColors = (jfloatArray)env->GetObjectArrayElement(buff, i);
            jfloat* jBuffColor = env->GetFloatArrayElements(jColors, 0);

            jBuffColor[0] = ImGui::GetStyle().Colors[i].x;
            jBuffColor[1] = ImGui::GetStyle().Colors[i].y;
            jBuffColor[2] = ImGui::GetStyle().Colors[i].z;
            jBuffColor[3] = ImGui::GetStyle().Colors[i].w;

            env->ReleaseFloatArrayElements(jColors, jBuffColor, 0);
            env->DeleteLocalRef(jColors);
        }
    */

    // BINDING NOTICE: colors is a 2d array with sizes: [ImGuiCol_COUNT][4]
    //
    public native void setColors(float[][] colors); /*
        for (int i = 0; i < ImGuiCol_COUNT; i++) {
            jfloatArray jColors = (jfloatArray)env->GetObjectArrayElement(colors, i);
            jfloat* jColor = env->GetFloatArrayElements(jColors, 0);

            ImGui::GetStyle().Colors[i].x = jColor[0];
            ImGui::GetStyle().Colors[i].y = jColor[1];
            ImGui::GetStyle().Colors[i].z = jColor[2];
            ImGui::GetStyle().Colors[i].w = jColor[3];

            env->ReleaseFloatArrayElements(jColors, jColor, 0);
            env->DeleteLocalRef(jColors);
        }
    */
}
