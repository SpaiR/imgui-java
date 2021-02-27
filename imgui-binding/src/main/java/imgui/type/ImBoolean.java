package imgui.type;

public final class ImBoolean implements Cloneable {
    private final boolean[] data = new boolean[]{false};

    public ImBoolean() {
    }

    public ImBoolean(final ImBoolean imBoolean) {
        this.data[0] = imBoolean.data[0];
    }

    public ImBoolean(final boolean value) {
        data[0] = value;
    }

    public boolean get() {
        return data[0];
    }

    public boolean[] getData() {
        return data;
    }

    public void set(final boolean value) {
        data[0] = value;
    }

    public void set(final ImBoolean value) {
        set(value.get());
    }

    @Override
    public String toString() {
        return String.valueOf(data[0]);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ImBoolean imBoolean = (ImBoolean) o;
        return data[0] == imBoolean.data[0];
    }

    @Override
    public int hashCode() {
        return Boolean.hashCode(data[0]);
    }

    @Override
    public ImBoolean clone() {
        return new ImBoolean(this);
    }
}
