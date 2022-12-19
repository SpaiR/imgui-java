package imgui;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ImVec2Test {
    @Nested
    class constructor {
        @Test
        void raw() {
            final ImVec2 value = new ImVec2();
            assertEquals(0, value.x);
            assertEquals(0, value.y);
        }

        @Test
        void fromFloats() {
            final ImVec2 value = new ImVec2(1, 2);
            assertEquals(1, value.x);
            assertEquals(2, value.y);
        }

        @Test
        void fromImVec2() {
            final ImVec2 value = new ImVec2(new ImVec2(1, 2));
            assertEquals(1, value.x);
            assertEquals(2, value.y);
        }
    }

    @Nested
    class set {
        @Test
        void withFloats() {
            final ImVec2 value = new ImVec2().set(1, 2);
            assertEquals(1, value.x);
            assertEquals(2, value.y);
        }

        @Test
        void withImVec2() {
            final ImVec2 value = new ImVec2().set(new ImVec2(1, 2));
            assertEquals(1, value.x);
            assertEquals(2, value.y);
        }
    }

    @Nested
    class plus {
        @Test
        void withFloats() {
            final ImVec2 value = new ImVec2(5, 5).plus(1, 2);
            assertEquals(6, value.x);
            assertEquals(7, value.y);
        }

        @Test
        void withImVec2() {
            final ImVec2 value = new ImVec2(5, 5).plus(new ImVec2(1, 2));
            assertEquals(6, value.x);
            assertEquals(7, value.y);
        }
    }

    @Nested
    class minus {
        @Test
        void withFloats() {
            final ImVec2 value = new ImVec2(5, 5).minus(1, 2);
            assertEquals(4, value.x);
            assertEquals(3, value.y);
        }

        @Test
        void withImVec2() {
            final ImVec2 value = new ImVec2(5, 5).minus(new ImVec2(1, 2));
            assertEquals(4, value.x);
            assertEquals(3, value.y);
        }
    }

    @Nested
    class times {
        @Test
        void withFloats() {
            final ImVec2 value = new ImVec2(5, 5).times(1, 2);
            assertEquals(5, value.x);
            assertEquals(10, value.y);
        }

        @Test
        void withImVec2() {
            final ImVec2 value = new ImVec2(5, 5).times(new ImVec2(1, 2));
            assertEquals(5, value.x);
            assertEquals(10, value.y);
        }
    }
}
