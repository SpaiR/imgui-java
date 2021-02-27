package imgui.type;

public final class ImDouble implements Cloneable {
    private final double[] data = new double[]{0.0d};

    public ImDouble() {
    }

    public ImDouble(final ImDouble imDouble) {
        this.data[0] = imDouble.data[0];
    }

    public ImDouble(final double value) {
        set(value);
    }

    public double get() {
        return this.data[0];
    }

    public double[] getData() {
        return data;
    }

    public void set(final double value) {
        this.data[0] = value;
    }

    public void set(final ImDouble value) {
        set(value.get());
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
        return Double.hashCode(data[0]);
    }

    @Override
    public ImDouble clone() {
        return new ImDouble(this);
    }
}
