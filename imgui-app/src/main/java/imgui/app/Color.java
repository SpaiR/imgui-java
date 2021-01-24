package imgui.app;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Object for color data.
 */
@Data
@NoArgsConstructor
public class Color implements Cloneable {
    private final float[] data = new float[4];

    public Color(final float[] color) {
        set(color);
    }

    public Color(final float red, final float green, final float blue, final float alpha) {
        data[0] = red;
        data[1] = green;
        data[2] = blue;
        data[3] = alpha;
    }

    public final void set(final float red, final float green, final float blue, final float alpha) {
        data[0] = red;
        data[1] = green;
        data[2] = blue;
        data[3] = alpha;
    }

    public final void set(final float[] color) {
        System.arraycopy(color, 0, data, 0, 4);
    }

    public final float getRed() {
        return data[0];
    }

    public final float getGreen() {
        return data[1];
    }

    public final float getBlue() {
        return data[2];
    }

    public final float getAlpha() {
        return data[3];
    }

    /**
     * Method to clone Color instance.
     *
     * @return cloned Color instance
     */
    @Override
    @SuppressWarnings("MethodDoesntCallSuperMethod")
    public Color clone() {
        return new Color(data);
    }
}
