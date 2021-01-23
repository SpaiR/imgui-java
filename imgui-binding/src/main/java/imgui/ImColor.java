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

    public static int hslToColor(final int h, final int s, final int l) {
        return hslToColor(h, s, l, 1);
    }

    public static int hslToColor(final int h, final int s, final int l, final float a) {
        return hslToColor(h / 360f, s / 100f, l / 100f, a);
    }

    public static int hslToColor(final float h, final float s, final float l) {
        return hslToColor(h, s, l, 1);
    }

    public static int hslToColor(final float h, final float s, final float l, final float a) {
        final float q;
        final float p;
        final float r;
        final float g;
        final float b;

        // Achromatic
        if (s == 0) {
            r = l;
            g = l;
            b = l;
        } else {
            q = l < 0.5 ? (l * (1 + s)) : (l + s - l * s);
            p = 2 * l - q;
            r = hue2rgb(p, q, h + 1.0f / 3);
            g = hue2rgb(p, q, h);
            b = hue2rgb(p, q, h - 1.0f / 3);
        }

        return floatToColor(r, g, b, a);
    }

    private static float hue2rgb(final float p, final float q, final float hue) {
        float h = hue;

        if (h < 0) {
            h += 1;
        }

        if (h > 1) {
            h -= 1;
        }

        if (6 * h < 1) {
            return p + ((q - p) * 6 * h);
        }

        if (2 * h < 1) {
            return q;
        }

        if (3 * h < 2) {
            return p + ((q - p) * 6 * ((2.0f / 3.0f) - h));
        }

        return p;
    }
}
