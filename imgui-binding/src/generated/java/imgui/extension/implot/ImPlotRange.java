package imgui.extension.implot;

import java.util.Objects;

public final class ImPlotRange implements Cloneable {
    public double min;
    public double max;

    public ImPlotRange() {
    }

    public ImPlotRange(final double min, final double max) {
        set(min, max);
    }

    public ImPlotRange(final ImPlotRange value) {
        set(value.min, value.max);
    }

    public boolean contains(final double value) {
        return value >= min && value <= max;
    }

    public double size() {
        return max - min;
    }

    public ImPlotRange set(final double min, final double max) {
        this.min = min;
        this.max = max;
        return this;
    }

    public ImPlotRange set(final ImPlotRange value) {
        return set(value.min, value.max);
    }

    public ImPlotRange plus(final double min, final double max) {
        this.min += min;
        this.max += max;
        return this;
    }

    public ImPlotRange plus(final ImPlotRange value) {
        return plus(value.min, value.max);
    }

    public ImPlotRange minus(final double min, final double max) {
        this.min -= min;
        this.max -= max;
        return this;
    }

    public ImPlotRange minus(final ImPlotRange value) {
        return minus(value.min, value.max);
    }

    public ImPlotRange times(final double min, final double max) {
        this.min *= min;
        this.max *= max;
        return this;
    }

    public ImPlotRange times(final ImPlotRange value) {
        return times(value.min, value.max);
    }

    @Override
    public String toString() {
        return "ImPlotRange{"
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
        final ImPlotRange ImPlotRange = (ImPlotRange) o;
        return Double.compare(ImPlotRange.min, min) == 0 && Double.compare(ImPlotRange.max, max) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(min, max);
    }

    @Override
    @SuppressWarnings("MethodDoesntCallSuperMethod")
    public ImPlotRange clone() {
        return new ImPlotRange(this);
    }
}
