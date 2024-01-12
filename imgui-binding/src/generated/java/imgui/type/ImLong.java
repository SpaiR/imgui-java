package imgui.type;

public final class ImLong extends Number implements Cloneable, Comparable<ImLong> {
    private final long[] data = new long[]{0};

    public ImLong() {
    }

    public ImLong(final ImLong imLong) {
        this.data[0] = imLong.data[0];
    }

    public ImLong(final long value) {
        set(value);
    }

    public long get() {
        return this.data[0];
    }

    public long[] getData() {
        return data;
    }

    public void set(final long value) {
        this.data[0] = value;
    }

    public void set(final ImLong value) {
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
        final ImLong imLong = (ImLong) o;
        return data[0] == imLong.data[0];
    }

    @Override
    public int hashCode() {
        return Long.hashCode(data[0]);
    }

    @Override
    @SuppressWarnings("MethodDoesntCallSuperMethod")
    public ImLong clone() {
        return new ImLong(this);
    }

    @Override
    public int compareTo(final ImLong o) {
        return Long.compare(get(), o.get());
    }

    @Override
    public int intValue() {
        return (int) get();
    }

    @Override
    public long longValue() {
        return get();
    }

    @Override
    public float floatValue() {
        return (float) get();
    }

    @Override
    public double doubleValue() {
        return (double) get();
    }
}
