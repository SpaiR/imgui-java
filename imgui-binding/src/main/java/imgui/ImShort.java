package imgui;

import java.util.Objects;

public class ImShort {
    short[] data = new short[]{0};

    public ImShort() {
    }

    public ImShort(short value) {
        set(value);
    }

    public short get() {
        return this.data[0];
    }

    public void set(short value) {
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
        ImShort imShort = (ImShort) o;
        return data[0] == imShort.data[0];
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(data[0]);
    }
}
