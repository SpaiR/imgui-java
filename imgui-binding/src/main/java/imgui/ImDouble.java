package imgui;

import java.util.Objects;

public final class ImDouble {
    final double[] data = new double[]{0.0d};

    public ImDouble() {
    }

    public ImDouble(final double value) {
        set(value);
    }

    public double get() {
        return this.data[0];
    }

    public void set(final double value) {
        this.data[0] = value;
    }

    @Override
    public String toString() {
        return String.valueOf(get());
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ImDouble imDouble = (ImDouble) o;
        return data[0] == imDouble.data[0];
    }

    @Override
    public int hashCode() {
        return Objects.hash(data[0]);
    }
}
