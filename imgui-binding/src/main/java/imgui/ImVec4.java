package imgui;

import java.util.Objects;

/**
 * 4D vector (often used to store floating-point colors)
 */
public final class ImVec4 implements Cloneable {
    public float x;
    public float y;
    public float z;
    public float w;

    public ImVec4() {
    }

    public ImVec4(final ImVec4 imVec4) {
        this.x = imVec4.x;
        this.y = imVec4.y;
        this.z = imVec4.z;
        this.w = imVec4.w;
    }

    public ImVec4(final float x, final float y, final float z, final float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public void set(final float x, final float y, final float z, final float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public void set(final ImVec4 value) {
        this.x = value.x;
        this.y = value.y;
        this.z = value.z;
        this.w = value.w;
    }

    @Override
    public String toString() {
        return "ImVec4{"
            + "x=" + x
            + ", y=" + y
            + ", z=" + z
            + ", w=" + w
            + '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ImVec4 imVec4 = (ImVec4) o;
        return Float.compare(imVec4.x, x) == 0 && Float.compare(imVec4.y, y) == 0 && Float.compare(imVec4.z, z) == 0 && Float.compare(imVec4.w, w) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z, w);
    }

    @Override
    public ImVec4 clone() {
        return new ImVec4(this);
    }
}
