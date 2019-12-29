package imgui;

import java.util.Objects;

public class ImLong {
    long[] data = new long[]{0};

    public ImLong() {
    }

    public ImLong(long value) {
        set(value);
    }

    public long get() {
        return this.data[0];
    }

    public void set(long value) {
        this.data[0] = value;
    }

    @Override
    public String toString() {
        return String.valueOf(get());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImLong imLong = (ImLong) o;
        return data[0] == imLong.data[0];
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(data[0]);
    }
}
