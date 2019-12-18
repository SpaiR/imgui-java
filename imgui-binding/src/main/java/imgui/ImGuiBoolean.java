package imgui;

public class ImGuiBoolean {
    public static ImGuiBoolean TMP = new ImGuiBoolean();

    boolean[] data = new boolean[]{false};

    public ImGuiBoolean() {
    }

    public ImGuiBoolean(boolean value) {
        setValue(value);
    }

    public boolean getValue() {
        return this.data[0];
    }

    public void setValue(boolean value) {
        this.data[0] = value;
    }

    @Override
    public String toString() {
        return String.valueOf(getValue());
    }
}
