package imgui;

import java.util.Objects;

/**
 * 2D vector (often used to store positions or sizes)
 */
public final class ImVec2 {
    public float x;
    public float y;

    public ImVec2() {
    }

    public ImVec2(final float x, final float y) {
        this.x = x;
        this.y = y;
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
}
