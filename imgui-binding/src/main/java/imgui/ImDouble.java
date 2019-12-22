package imgui;

public final class ImDouble {
    public static ImDouble TMP = new ImDouble();

    double[] data = new double[]{0.0d};

    public ImDouble() {
    }

    public ImDouble(double value) {
        setValue(value);
    }

    public double getValue() {
        return this.data[0];
    }

    public void setValue(double value) {
        this.data[0] = value;
    }

    @Override
    public String toString() {
        return String.valueOf(getValue());
    }
}
