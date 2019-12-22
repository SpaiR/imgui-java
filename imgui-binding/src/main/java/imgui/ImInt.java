package imgui;

public final class ImInt {
    public static ImInt TMP = new ImInt();

    int[] data = new int[]{0};

    public ImInt() {
    }

    public ImInt(int value) {
        setValue(value);
    }

    public int getValue() {
        return this.data[0];
    }

    public void setValue(int value) {
        this.data[0] = value;
    }

    @Override
    public String toString() {
        return String.valueOf(getValue());
    }
}
