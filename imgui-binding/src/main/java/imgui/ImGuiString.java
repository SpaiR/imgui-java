package imgui;

public class ImGuiString {
    public static ImGuiString TMP = new ImGuiString();
    final ImGuiInputTextData inputData = new ImGuiInputTextData();
    byte[] data;
    private String text;

    public ImGuiString() {
        this(100);
    }

    public ImGuiString(int size) {
        data = new byte[size];
    }

    public ImGuiString(String text) {
        this(text.length());
        setValue(text);
    }

    public ImGuiString(String text, int size) {
        this(size);
        setValue(text);
    }

    public String getValue() {
        if (inputData.isDirty) {
            inputData.isDirty = false;
            text = new String(data, 0, inputData.size);
        }
        return text;
    }

    public void setValue(String value) {
        inputData.size = value.length();
        text = value;
        for (int i = 0; i < inputData.size; i++) {
            data[i] = (byte) value.charAt(i);
        }
    }

    @Override
    public String toString() {
        return getValue();
    }
}
