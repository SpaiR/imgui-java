package imgui;

public final class ImString {
    public static ImString TMP = new ImString();
    ImGuiInputTextData inputData = new ImGuiInputTextData();
    byte[] data;
    private String text;

    public ImString() {
        this(100);
    }

    public ImString(int size) {
        data = new byte[size];
    }

    public ImString(String text) {
        this(text.length());
        setValue(text);
    }

    public ImString(String text, int size) {
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
