package imgui.type;

public final class ImShort implements Cloneable {
    private final short[] data = new short[]{0};

    public ImShort() {
    }

    public ImShort(final ImShort imShort) {
        this.data[0] = imShort.data[0];
    }

    public ImShort(final short value) {
        set(value);
    }

    public short get() {
        return this.data[0];
    }

    public short[] getData() {
        return data;
    }

    public void set(final short value) {
        this.data[0] = value;
    }

    public void set(final ImShort value) {
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
        final ImShort imShort = (ImShort) o;
        return data[0] == imShort.data[0];
    }

    @Override
    public int hashCode() {
        return Short.hashCode(data[0]);
    }

    @Override
    public ImShort clone() {
        return new ImShort(this);
    }
}
