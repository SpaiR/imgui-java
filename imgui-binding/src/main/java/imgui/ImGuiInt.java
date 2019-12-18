package imgui;

public class ImGuiInt {
    public static ImGuiInt TMP = new ImGuiInt();

    int[] data = new int[]{0};

    public ImGuiInt() {
    }

    public ImGuiInt(int value) {
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
