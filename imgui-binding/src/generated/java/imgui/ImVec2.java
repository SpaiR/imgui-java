package imgui;

import java.util.Objects;

/**
 * 2D vector (often used to store positions or sizes).
 */
public final class ImVec2 implements Cloneable {
    public float x;
    public float y;

    public ImVec2() {
    }

    public ImVec2(final float x, final float y) {
        set(x, y);
    }

    public ImVec2(final ImVec2 value) {
        set(value.x, value.y);
    }

    public ImVec2 set(final float x, final float y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public ImVec2 set(final ImVec2 value) {
        return set(value.x, value.y);
    }

    public ImVec2 plus(final float x, final float y) {
        return new ImVec2(this.x + x, this.y + y);
    }

    public ImVec2 plus(final ImVec2 value) {
        return plus(value.x, value.y);
    }

    public ImVec2 minus(final float x, final float y) {
        return new ImVec2(this.x - x, this.y -y);
    }

    public ImVec2 minus(final ImVec2 value) {
        return minus(value.x, value.y);
    }

    public ImVec2 times(final float x, final float y) {
        return new ImVec2(this.x * x, this.y * y);
    }

    public ImVec2 times(final ImVec2 value) {
        return times(value.x, value.y);
    }

    public ImVec2 div(final float x, final float y) {
        return new ImVec2(this.x / x, this.y / y);
    }

    public ImVec2 div(final ImVec2 value) {
        return div(value.x, value.y);
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
    @SuppressWarnings("MethodDoesntCallSuperMethod")
    public ImVec2 clone() {
        return new ImVec2(this);
    }
}
