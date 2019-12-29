package imgui;

import java.util.Objects;

public final class ImDouble {
    double[] data = new double[]{0.0d};

    public ImDouble() {
    }

    public ImDouble(double value) {
        set(value);
    }

    public double get() {
        return this.data[0];
    }

    public void set(double value) {
        this.data[0] = value;
    }

    @Override
    public String toString() {
        return String.valueOf(get());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImDouble imDouble = (ImDouble) o;
        return data[0] == imDouble.data[0];
    }

    @Override
    public int hashCode() {
        return Objects.hash(data[0]);
    }
}
