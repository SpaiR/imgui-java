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

    public ImRect(final float minX, final float minY, final float maxX, final float maxY) {
        set(minX, minY, maxX, maxY);
    }

    public ImRect(final ImVec2 min, final ImVec2 max) {
        set(min, max);
    }

    public ImRect(final ImRect value) {
        set(value);
    }

    public void set(final float minX, final float minY, final float maxX, final float maxY) {
        this.min.x = minX;
        this.min.y = minY;
        this.max.x = maxX;
        this.max.y = maxY;
    }

    public void set(final ImVec2 min, final ImVec2 max) {
        set(min.x, min.y, max.x, max.y);
    }

    public void set(final ImRect value) {
        set(value.min, value.max);
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
    @SuppressWarnings("MethodDoesntCallSuperMethod")
    public ImRect clone() {
        return new ImRect(this);
    }
}
