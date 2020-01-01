package imgui;

import java.util.Objects;

public final class ImVec4 {
    public float x;
    public float y;
    public float z;
    public float w;

    public ImVec4() {
    }

    public ImVec4(final float x, final float y, final float z, final float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
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
}
