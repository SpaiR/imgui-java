package imgui;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ImVec4Test {
    @Nested
    class constructor {
        @Test
        void raw() {
            final ImVec4 value = new ImVec4();
            assertEquals(0, value.x);
            assertEquals(0, value.y);
            assertEquals(0, value.z);
            assertEquals(0, value.w);
        }

        @Test
        void fromFloats() {
            final ImVec4 value = new ImVec4(1, 2, 3, 4);
            assertEquals(1, value.x);
            assertEquals(2, value.y);
            assertEquals(3, value.z);
            assertEquals(4, value.w);
        }

        @Test
        void fromImVec2() {
            final ImVec4 value = new ImVec4(new ImVec4(1, 2, 3, 4));
            assertEquals(1, value.x);
            assertEquals(2, value.y);
            assertEquals(3, value.z);
            assertEquals(4, value.w);
        }
    }

    @Nested
    class set {
        @Test
        void withFloats() {
            final ImVec4 value = new ImVec4().set(1, 2, 3, 4);
            assertEquals(1, value.x);
            assertEquals(2, value.y);
            assertEquals(3, value.z);
            assertEquals(4, value.w);
        }

        @Test
        void withImVec4() {
            final ImVec4 value = new ImVec4().set(new ImVec4(1, 2, 3, 4));
            assertEquals(1, value.x);
            assertEquals(2, value.y);
            assertEquals(3, value.z);
            assertEquals(4, value.w);
        }
    }

    @Nested
    class plus {
        @Test
        void withFloats() {
            final ImVec4 value = new ImVec4(5, 5, 5, 5).plus(1, 2, 3, 4);
            assertEquals(6, value.x);
            assertEquals(7, value.y);
        }

        @Test
        void withImVec4() {
            final ImVec4 value = new ImVec4(5, 5, 5, 5).plus(new ImVec4(1, 2, 3, 4));
            assertEquals(6, value.x);
            assertEquals(7, value.y);
        }
    }

    @Nested
    class minus {
        @Test
        void withFloats() {
            final ImVec4 value = new ImVec4(5, 5, 5, 5).minus(1, 2, 3, 4);
            assertEquals(4, value.x);
            assertEquals(3, value.y);
            assertEquals(2, value.z);
            assertEquals(1, value.w);
        }

        @Test
        void withImVec4() {
            final ImVec4 value = new ImVec4(5, 5, 5, 5).minus(new ImVec4(1, 2, 3, 4));
            assertEquals(4, value.x);
            assertEquals(3, value.y);
            assertEquals(2, value.z);
            assertEquals(1, value.w);
        }
    }

    @Nested
    class times {
        @Test
        void withFloats() {
            final ImVec4 value = new ImVec4(5, 5, 5, 5).times(1, 2, 3, 4);
            assertEquals(5, value.x);
            assertEquals(10, value.y);
            assertEquals(15, value.z);
            assertEquals(20, value.w);
        }

        @Test
        void withImVec4() {
            final ImVec4 value = new ImVec4(5, 5, 5, 5).times(new ImVec4(1, 2, 3, 4));
            assertEquals(5, value.x);
            assertEquals(10, value.y);
            assertEquals(15, value.z);
            assertEquals(20, value.w);
        }
    }
}
