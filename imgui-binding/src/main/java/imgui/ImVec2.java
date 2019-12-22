package imgui;

import java.util.Objects;

public final class ImVec2 {
    public float x;
    public float y;

    public ImVec2() {
    }

    public ImVec2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "ImVec2{" +
            "x=" + x +
            ", y=" + y +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImVec2 imVec2 = (ImVec2) o;
        return Float.compare(imVec2.x, x) == 0 && Float.compare(imVec2.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
