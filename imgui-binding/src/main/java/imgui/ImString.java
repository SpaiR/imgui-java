package imgui;

import java.util.Objects;

/**
 * Wrapper for {@link String} to use inside of th Dear ImGui input widgets.
 */
public final class ImString {
    private static final short DEFAULT_LENGTH = 100;
    private static final short CARET_LEN = 1;
    private static final short DEFAULT_RESIZE = 10;

    public final ImGuiInputTextData inputData = new ImGuiInputTextData();
    /**
     * String will be resized to the value equal to a new size plus this resize factor.
     */
    public int resizeFactor = DEFAULT_RESIZE;

    byte[] data;
    private String text = "";

    public ImString() {
        this(DEFAULT_LENGTH);
    }

    public ImString(int length) {
        data = new byte[length + CARET_LEN];
    }

    public ImString(String text) {
        this(text.length());
        set(text);
    }

    public ImString(String text, int length) {
        this(length);
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
        set(value.getBytes());
    }

    public void set(String value, boolean resize) {
        byte[] str = value.getBytes();
        if (resize && data.length - CARET_LEN < str.length) {
            data = new byte[str.length + resizeFactor + CARET_LEN];
        }
        set(str);
    }

    /**
     * Get the length of the text inside of the data buffer.
     * @return length of the text inside of the data buffer
     */
    public int getLength() {
        return get().length();
    }

    /**
     * Get the size of the data buffer. Buffer size will always have '+1' to its size, since it's used by the Dear ImGui to draw a caret char.
     * @return size of the data buffer
     */
    public int getBufferSize() {
        return data.length;
    }

    private void set(byte[] str) {
        int len = Math.min(str.length, data.length);
        System.arraycopy(str, 0, data, 0, len);
        inputData.isDirty = true;
        inputData.size = len;
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
        return Objects.equals(text, imString.text);
    }

    @Override
    public int hashCode() {
        return text.hashCode();
    }
}
