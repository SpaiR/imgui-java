package imgui;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

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
            final ImVec2 original = new ImVec2();
            final ImVec2 value = original.set(1, 2);
            assertEquals(1, value.x);
            assertEquals(2, value.y);
            assertSame(original, value);
        }

        @Test
        void withImVec2() {
            final ImVec2 original = new ImVec2();
            final ImVec2 value = original.set(new ImVec2(1, 2));
            assertEquals(1, value.x);
            assertEquals(2, value.y);
            assertSame(original, value);
        }
    }

    @Nested
    class plus {
        @Test
        void withFloats() {
            final ImVec2 original = new ImVec2(5, 5);
            final ImVec2 value = original.plus(1, 2);
            assertEquals(6, value.x);
            assertEquals(7, value.y);
            assertNotSame(original, value);
        }

        @Test
        void withImVec2() {
            final ImVec2 original = new ImVec2(5, 5);
            final ImVec2 value = original.plus(new ImVec2(1, 2));
            assertEquals(6, value.x);
            assertEquals(7, value.y);
            assertNotSame(original, value);
        }
    }

    @Nested
    class minus {
        @Test
        void withFloats() {
            final ImVec2 original = new ImVec2(5, 5);
            final ImVec2 value = original.minus(1, 2);
            assertEquals(4, value.x);
            assertEquals(3, value.y);
            assertNotSame(original, value);
        }

        @Test
        void withImVec2() {
            final ImVec2 original = new ImVec2(5, 5);
            final ImVec2 value = original.minus(new ImVec2(1, 2));
            assertEquals(4, value.x);
            assertEquals(3, value.y);
            assertNotSame(original, value);
        }
    }

    @Nested
    class times {
        @Test
        void withFloats() {
            final ImVec2 original = new ImVec2(5, 5);
            final ImVec2 value = original.times(1, 2);
            assertEquals(5, value.x);
            assertEquals(10, value.y);
            assertNotSame(original, value);
        }

        @Test
        void withImVec2() {
            final ImVec2 original = new ImVec2(5, 5);
            final ImVec2 value = original.times(new ImVec2(1, 2));
            assertEquals(5, value.x);
            assertEquals(10, value.y);
            assertNotSame(original, value);
        }
    }

    @Nested
    class div {
        @Test
        void withFloats() {
            final ImVec2 original = new ImVec2(10, 10);
            final ImVec2 value = original.div(5, 2);
            assertEquals(2, value.x);
            assertEquals(5, value.y);
            assertNotSame(original, value);
        }

        @Test
        void withImVec2() {
            final ImVec2 original = new ImVec2(10, 10);
            final ImVec2 value = original.div(new ImVec2(5, 2));
            assertEquals(2, value.x);
            assertEquals(5, value.y);
            assertNotSame(original, value);
        }
    }
}
