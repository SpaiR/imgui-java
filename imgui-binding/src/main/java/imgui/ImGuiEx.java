package imgui;

import imgui.NativeCustomWidget.ImGuiCollapseLayoutOptions;

public class ImGuiEx {

    protected ImGuiEx() {
    }

    public static void ShowLayoutDebug() {
        NativeCustomWidget.ShowLayoutDebug();
    }

    public static void BeginLayout(String id, float sizeX, float sizeY) {
        NativeCustomWidget.BeginLayout(id, sizeX, sizeY);
    }

    public static void BeginLayout(String id, float sizeX, float sizeY, float paddingLeft, float paddingRight, float paddingTop, float paddingBottom) {
        NativeCustomWidget.BeginLayout(id, sizeX, sizeY, paddingLeft, paddingRight, paddingTop, paddingBottom);
    }

    public static void EndLayout() {
        NativeCustomWidget.EndLayout();
    }

    public static boolean BeginCollapseLayoutEx(String title, float sizeX, float sizeY) {
        return NativeCustomWidget.BeginCollapseLayoutEx2(title, sizeX, sizeY, NativeCustomWidget.defaultOptions);
    }

    public static boolean BeginCollapseLayoutEx(String title, float sizeX, float sizeY, ImGuiCollapseLayoutOptions options) {
        return NativeCustomWidget.BeginCollapseLayoutEx2(title, sizeX, sizeY, options);
    }

    public static void BeginCollapseLayoutEx(ImGuiBoolean isOpen, String title, float sizeX, float sizeY) {
        NativeCustomWidget.BeginCollapseLayoutEx(isOpen.data, title, sizeX, sizeY, NativeCustomWidget.defaultOptions);
    }

    public static void BeginCollapseLayoutEx(ImGuiBoolean isOpen, String title, float sizeX, float sizeY, ImGuiCollapseLayoutOptions options) {
        NativeCustomWidget.BeginCollapseLayoutEx(isOpen.data, title, sizeX, sizeY, options);
    }

    public static boolean BeginCollapseLayout(String title, float sizeX, float sizeY) {
        return NativeCustomWidget.BeginCollapseLayout2(title, sizeX, sizeY, NativeCustomWidget.defaultOptions);
    }

    public static boolean BeginCollapseLayout(String title, float sizeX, float sizeY, ImGuiCollapseLayoutOptions options) {
        return NativeCustomWidget.BeginCollapseLayout2(title, sizeX, sizeY, options);
    }

    public static void BeginCollapseLayout(ImGuiBoolean isOpen, String title, float sizeX, float sizeY) {
        NativeCustomWidget.BeginCollapseLayout(isOpen.data, title, sizeX, sizeY, NativeCustomWidget.defaultOptions);
    }

    public static void BeginCollapseLayout(ImGuiBoolean isOpen, String title, float sizeX, float sizeY, ImGuiCollapseLayoutOptions options) {
        NativeCustomWidget.BeginCollapseLayout(isOpen.data, title, sizeX, sizeY, options);
    }

    public static void EndCollapseFrameLayout() {
        NativeCustomWidget.EndCollapseFrameLayout();
    }

    public static void EndCollapseLayout() {
        NativeCustomWidget.EndCollapseLayout();
    }

    public static void BeginAlign(String id, float sizeX, float sizeY, float alignX, float alignY) {
        NativeCustomWidget.BeginAlign(id, sizeX, sizeY, alignX, alignY);
    }

    public static void BeginAlign(String id, float sizeX, float sizeY, float alignX, float alignY, float offsetX, float offsetY) {
        NativeCustomWidget.BeginAlign(id, sizeX, sizeY, alignX, alignY, offsetX, offsetY);
    }

    public static void EndAlign() {
        NativeCustomWidget.EndAlign();
    }

    public static void AlignLayout(float alignX, float alignY) {
        NativeCustomWidget.AlignLayout(alignX, alignY);
    }

    public static void AlignLayout(float alignX, float alignY, float offsetX, float offsetY) {
        NativeCustomWidget.AlignLayout(alignX, alignY, offsetX, offsetY);
    }


    // Helper methods

    /**
     * Packs the color components into a 32-bit integer with the format ABGR. Note that no range checking is performed for higher
     * performance.
     *
     * @param r the red component, 0 - 255
     * @param g the green component, 0 - 255
     * @param b the blue component, 0 - 255
     * @param a the alpha component, 0 - 255
     * @return the packed color as a 32-bit int
     */
    public static int colorToIntBits(int r, int g, int b, int a) {
        return (a << 24) | (b << 16) | (g << 8) | r;
    }

}
