package imgui.type;

public final class ImFloat implements Cloneable {
    private final float[] data = new float[]{0};

    public ImFloat() {
    }

    public ImFloat(final ImFloat imFloat) {
        this.data[0] = imFloat.data[0];
    }

    public ImFloat(final float value) {
        set(value);
    }

    public float get() {
        return this.data[0];
    }

    public float[] getData() {
        return data;
    }

    public void set(final float value) {
        this.data[0] = value;
    }

    public void set(final ImFloat value) {
        set(value.get());
    }

    @Override
    public String toString() {
        return String.valueOf(get());
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ImFloat imFloat = (ImFloat) o;
        return data[0] == imFloat.data[0];
    }

    @Override
    public int hashCode() {
        return Float.hashCode(data[0]);
    }

    @Override
    public ImFloat clone() {
        return new ImFloat(this);
    }
}
