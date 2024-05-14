package imgui.extension.implot;

import imgui.ImVec2;

import java.util.Objects;

public final class ImPlotPoint implements Cloneable {
    public double x;
    public double y;

    public ImPlotPoint() {
    }

    public ImPlotPoint(final double x, final double y) {
        set(x, y);
    }

    public ImPlotPoint(final ImVec2 value) {
        set(value.x, value.y);
    }

    public ImPlotPoint(final ImPlotPoint value) {
        set(value.x, value.y);
    }

    public ImPlotPoint set(final double x, final double y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public ImPlotPoint set(final ImPlotPoint value) {
        return set(value.x, value.y);
    }

    public ImPlotPoint set(final ImVec2 value) {
        return set(value.x, value.y);
    }

    public ImPlotPoint plus(final double x, final double y) {
        this.x += x;
        this.y += y;
        return this;
    }

    public ImPlotPoint plus(final ImPlotPoint value) {
        return plus(value.x, value.y);
    }

    public ImPlotPoint minus(final double x, final double y) {
        this.x -= x;
        this.y -= y;
        return this;
    }

    public ImPlotPoint minus(final ImPlotPoint value) {
        return minus(value.x, value.y);
    }

    public ImPlotPoint times(final double x, final double y) {
        this.x *= x;
        this.y *= y;
        return this;
    }

    public ImPlotPoint times(final ImPlotPoint value) {
        return times(value.x, value.y);
    }

    @Override
    public String toString() {
        return "ImPlotPoint{"
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
        final ImPlotPoint imPlotPoint = (ImPlotPoint) o;
        return Double.compare(imPlotPoint.x, x) == 0 && Double.compare(imPlotPoint.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    @SuppressWarnings("MethodDoesntCallSuperMethod")
    public ImPlotPoint clone() {
        return new ImPlotPoint(this);
    }
}
