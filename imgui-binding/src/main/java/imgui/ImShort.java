package imgui;

public class ImShort {
    short[] data = new short[]{0};

    public ImShort() {
    }

    public ImShort(short value) {
        setValue(value);
    }

    public short getValue() {
        return this.data[0];
    }

    public void setValue(short value) {
        this.data[0] = value;
    }

    @Override
    public String toString() {
        return String.valueOf(getValue());
    }
}
