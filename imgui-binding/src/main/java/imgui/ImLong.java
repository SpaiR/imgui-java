package imgui;

public class ImLong {
    long[] data = new long[]{0};

    public ImLong() {
    }

    public ImLong(long value) {
        setValue(value);
    }

    public long getValue() {
        return this.data[0];
    }

    public void setValue(long value) {
        this.data[0] = value;
    }

    @Override
    public String toString() {
        return String.valueOf(getValue());
    }
}
