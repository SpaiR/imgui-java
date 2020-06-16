package imgui;

/**
 * Helper class to get ABGR packed color used by Dear ImGui.
 */
public final class ImColor {
    private ImColor() {
    }

    public static int intToColor(final int r, final int g, final int b, final int a) {
        return a << 24 | b << 16 | g << 8 | r;
    }

    public static int intToColor(final int r, final int g, final int b) {
        return intToColor(r, g, b, 255);
    }

    public static int floatToColor(final float r, final float g, final float b, final float a) {
        return intToColor((int) (r * 255), (int) (g * 255), (int) (b * 255), (int) (a * 255));
    }

    public static int floatToColor(final float r, final float g, final float b) {
        return floatToColor(r, g, b, 1f);
    }

    /**
     * @param hex e.g. "#FFFFFF"
     */
    public static int rgbToColor(final String hex) {
        return intToColor(
            Integer.parseInt(hex.substring(1, 3), 16),
            Integer.parseInt(hex.substring(3, 5), 16),
            Integer.parseInt(hex.substring(5, 7), 16)
        );
    }

    /**
     * @param hex e.g. "#FFFFFFFF"
     */
    public static int rgbaToColor(final String hex) {
        return intToColor(
            Integer.parseInt(hex.substring(1, 3), 16),
            Integer.parseInt(hex.substring(3, 5), 16),
            Integer.parseInt(hex.substring(5, 7), 16),
            Integer.parseInt(hex.substring(7, 9), 16)
        );
    }
}
