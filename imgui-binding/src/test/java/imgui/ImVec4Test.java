package imgui;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

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
        void fromImVec4() {
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
            final ImVec4 original = new ImVec4(1, 2, 3, 4);
            final ImVec4 value = original.set(1, 2, 3, 4);
            assertEquals(1, value.x);
            assertEquals(2, value.y);
            assertEquals(3, value.z);
            assertEquals(4, value.w);
            assertSame(original, value);
        }

        @Test
        void withImVec4() {
            final ImVec4 original = new ImVec4(1, 2, 3, 4);
            final ImVec4 value = original.set(new ImVec4(1, 2, 3, 4));
            assertEquals(1, value.x);
            assertEquals(2, value.y);
            assertEquals(3, value.z);
            assertEquals(4, value.w);
            assertSame(original, value);
        }
    }

    @Nested
    class plus {
        @Test
        void withFloats() {
            final ImVec4 original = new ImVec4(5, 5, 5, 5);
            final ImVec4 value = original.plus(1, 2, 3, 4);
            assertEquals(6, value.x);
            assertEquals(7, value.y);
            assertEquals(8, value.z);
            assertEquals(9, value.w);
            assertNotSame(original, value);
        }

        @Test
        void withImVec4() {
            final ImVec4 original = new ImVec4(5, 5, 5, 5);
            final ImVec4 value = original.plus(new ImVec4(1, 2, 3, 4));
            assertEquals(6, value.x);
            assertEquals(7, value.y);
            assertEquals(8, value.z);
            assertEquals(9, value.w);
            assertNotSame(original, value);
        }
    }

    @Nested
    class minus {
        @Test
        void withFloats() {
            final ImVec4 original = new ImVec4(5, 5, 5, 5);
            final ImVec4 value = original.minus(1, 2, 3, 4);
            assertEquals(4, value.x);
            assertEquals(3, value.y);
            assertEquals(2, value.z);
            assertEquals(1, value.w);
            assertNotSame(original, value);
        }

        @Test
        void withImVec4() {
            final ImVec4 original = new ImVec4(5, 5, 5, 5);
            final ImVec4 value = original.minus(new ImVec4(1, 2, 3, 4));
            assertEquals(4, value.x);
            assertEquals(3, value.y);
            assertEquals(2, value.z);
            assertEquals(1, value.w);
            assertNotSame(original, value);
        }
    }

    @Nested
    class times {
        @Test
        void withFloats() {
            final ImVec4 original = new ImVec4(5, 5, 5, 5);
            final ImVec4 value = original.times(1, 2, 3, 4);
            assertEquals(5, value.x);
            assertEquals(10, value.y);
            assertEquals(15, value.z);
            assertEquals(20, value.w);
            assertNotSame(original, value);
        }

        @Test
        void withImVec4() {
            final ImVec4 original = new ImVec4(5, 5, 5, 5);
            final ImVec4 value = original.times(new ImVec4(1, 2, 3, 4));
            assertEquals(5, value.x);
            assertEquals(10, value.y);
            assertEquals(15, value.z);
            assertEquals(20, value.w);
            assertNotSame(original, value);
        }
    }

    @Nested
    class div {
        @Test
        void withFloats() {
            final ImVec4 original = new ImVec4(10, 10, 15, 15);
            final ImVec4 value = original.div(5, 2, 3, 5);
            assertEquals(2, value.x);
            assertEquals(5, value.y);
            assertEquals(5, value.z);
            assertEquals(3, value.w);
            assertNotSame(original, value);
        }

        @Test
        void withImVec4() {
            final ImVec4 original = new ImVec4(10, 10, 15, 15);
            final ImVec4 value = original.div(new ImVec4(5, 2, 3, 5));
            assertEquals(2, value.x);
            assertEquals(5, value.y);
            assertEquals(5, value.z);
            assertEquals(3, value.w);
            assertNotSame(original, value);
        }
    }
}
