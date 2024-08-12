package imgui.extension.implot;

import imgui.ImVec2;

import java.util.Objects;

public final class ImPlotRect implements Cloneable {
    public final ImPlotRange x = new ImPlotRange();
    public final ImPlotRange y = new ImPlotRange();

    public ImPlotRect() {
    }

    public ImPlotRect(final double xMin, final double yMin, final double xMax, final double yMax) {
        set(xMin, yMin, xMax, yMax);
    }

    public ImPlotRect(final ImVec2 min, final ImVec2 max) {
        set(min, max);
    }

    public ImPlotRect(final ImPlotRect value) {
        set(value);
    }

    public boolean contains(final double x, final double y) {
        return this.x.contains(x) && this.y.contains(y);
    }

    public boolean contains(final ImPlotPoint value) {
        return contains(value.x, value.y);
    }

    public ImPlotPoint size() {
        return new ImPlotPoint(x.size(), y.size());
    }

    public ImPlotPoint clamp(final double x, final double y) {
        return new ImPlotPoint(this.x.clamp(x), this.y.clamp(y));
    }

    public ImPlotPoint clamp(ImPlotPoint p) {
        return clamp(p.x, p.y);
    }

    public ImPlotPoint min() {
        return new ImPlotPoint(x.min, y.min);
    }

    public ImPlotPoint max() {
        return new ImPlotPoint(x.max, y.max);
    }

    public void set(final double xMin, final double yMin, final double xMax, final double yMax) {
        this.x.min = xMin;
        this.x.max = xMax;
        this.y.min = yMin;
        this.y.max = yMax;
    }

    public void set(final ImVec2 min, final ImVec2 max) {
        set(min.x, min.y, max.x, max.y);
    }

    public void set(final ImPlotRange x, final ImPlotRange y) {
        set(x.min, y.min, x.max, y.max);
    }

    public void set(final ImPlotRect value) {
        set(value.x, value.y);
    }

    @Override
    public String toString() {
        return "ImPlotRect{"
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

        final ImPlotRect imPlotRect = (ImPlotRect) o;
        return Objects.equals(x, imPlotRect.x) && Objects.equals(y, imPlotRect.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    @SuppressWarnings("MethodDoesntCallSuperMethod")
    public ImPlotRect clone() {
        return new ImPlotRect(this);
    }
}
