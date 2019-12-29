package imgui;

import java.util.Arrays;

public final class ImString {
    private static final int DEFAULT_SIZE = 100;

    ImGuiInputTextData inputData = new ImGuiInputTextData();
    byte[] data;
    private String text;

    public ImString() {
        this(DEFAULT_SIZE);
    }

    public ImString(int size) {
        data = new byte[size];
    }

    public ImString(String text) {
        this(text.length());
        set(text);
    }

    public ImString(String text, int size) {
        this(size);
        set(text);
    }

    public String get() {
        if (inputData.isDirty) {
            inputData.isDirty = false;
            text = new String(data, 0, inputData.size);
        }
        return text;
    }

    public void set(String value) {
        inputData.size = value.length();
        text = value;
        for (int i = 0; i < inputData.size; i++) {
            data[i] = (byte) value.charAt(i);
        }
    }

    @Override
    public String toString() {
        return get();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImString imString = (ImString) o;
        return Arrays.equals(data, imString.data);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(data);
    }
}
