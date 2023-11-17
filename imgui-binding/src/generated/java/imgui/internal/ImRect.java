package imgui.internal;

import imgui.ImVec2;

import java.util.Objects;

/**
 * Helper: ImRect (2D axis aligned bounding-box)
 * NB: we can't rely on ImVec2 math operators being available here!
 */
public final class ImRect implements Cloneable {
    /**
     * Upper-left
     */
    public final ImVec2 min = new ImVec2();
    /**
     * Lower-right
     */
    public final ImVec2 max = new ImVec2();

    public ImRect() {
    }

    public ImRect(final ImVec2 min, final ImVec2 max) {
        this.min.x = min.x;
        this.min.y = min.y;
        this.max.x = max.x;
        this.max.y = max.y;
    }

    public ImRect(final float x1, final  float y1, final  float x2, final float y2) {
        min.x = x1;
        min.y = y1;
        max.x = x2;
        max.y = y2;
    }

    public ImRect(final ImRect imRect) {
        this(imRect.min, imRect.max);
    }

    public void set(final ImRect value) {
        min.x = value.min.x;
        min.y = value.min.y;
        max.x = value.max.x;
        max.y = value.max.y;
    }

    @Override
    public String toString() {
        return "ImRect{"
            + "min=" + min
            + ", max=" + max
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

        final ImRect imRect = (ImRect) o;
        return Objects.equals(min, imRect.min) && Objects.equals(max, imRect.max);
    }

    @Override
    public int hashCode() {
        return Objects.hash(min, max);
    }

    @Override
    public ImRect clone() {
        return new ImRect(this);
    }
}
