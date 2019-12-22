package imgui;

public final class ImFloat {
    public static ImFloat TMP = new ImFloat();

    float[] data = new float[]{0.0f};

    public ImFloat() {
    }

    public ImFloat(float value) {
        setValue(value);
    }

    public float getValue() {
        return this.data[0];
    }

    public void setValue(float value) {
        this.data[0] = value;
    }

    @Override
    public String toString() {
        return String.valueOf(getValue());
    }
}
