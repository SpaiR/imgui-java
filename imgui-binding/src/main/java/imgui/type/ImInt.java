package imgui.type;

public final class ImInt implements Cloneable {
    private final int[] data = new int[]{0};

    public ImInt() {
    }

    public ImInt(final ImInt imInt) {
        this.data[0] = imInt.data[0];
    }

    public ImInt(final int value) {
        set(value);
    }

    public int get() {
        return this.data[0];
    }

    public int[] getData() {
        return data;
    }

    public void set(final int value) {
        this.data[0] = value;
    }

    public void set(final ImInt value) {
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
        final ImInt imInt = (ImInt) o;
        return data[0] == imInt.data[0];
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(data[0]);
    }

    @Override
    public ImInt clone() {
        return new ImInt(this);
    }
}
