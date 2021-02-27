package imgui;

import java.util.Objects;

/**
 * 2D vector (often used to store positions or sizes)
 */
public final class ImVec2 implements Cloneable {
    public float x;
    public float y;

    public ImVec2() {
    }

    public ImVec2(final ImVec2 imVec2) {
        this.x = imVec2.x;
        this.y = imVec2.y;
    }

    public ImVec2(final float x, final float y) {
        this.x = x;
        this.y = y;
    }

    public void set(final float x, final float y) {
        this.x = x;
        this.y = y;
    }

    public void set(final ImVec2 value) {
        this.x = value.x;
        this.y = value.y;
    }

    @Override
    public String toString() {
        return "ImVec2{"
            + "x=" + x
            + ", y=" + y
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
        final ImVec2 imVec2 = (ImVec2) o;
        return Float.compare(imVec2.x, x) == 0 && Float.compare(imVec2.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public ImVec2 clone() {
        return new ImVec2(this);
    }
}
