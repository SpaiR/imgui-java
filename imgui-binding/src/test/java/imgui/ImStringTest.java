package imgui;

import imgui.type.ImString;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ImStringTest {
    @Nested
    class constructor {
        @Test
        void withLength() {
            final ImString str = new ImString(10);
            assertEquals("", str.get());
            assertEquals(0, str.getLength(), "String length should be 0, since no data was provided");
            assertEquals(10 + 1, str.getBufferSize(), "Buffer size must be 'length + 1', +1 - size of the ImGui caret");
        }

        @Test
        void withString() {
            final ImString str = new ImString("test");
            assertEquals("test", str.get());
            assertEquals(4, str.getLength());
            assertEquals(4 + 1, str.getBufferSize());
        }

        @Test
        void withStringAndLength() {
            final ImString str = new ImString("test", 10);
            assertEquals("test", str.get());
            assertEquals(4, str.getLength());
            assertEquals(10 + 1, str.getBufferSize());
        }
    }

    @Nested
    class get {
        @Test
        void simple() {
            final ImString str = new ImString("test");
            assertEquals("test", str.get());
            assertEquals(str.get(), str.toString(), "ImString#toString() must return value which should be equal to ImString#get()");
        }
    }

    @Nested
    class set {
        @Test
        void withEmpty() {
            final ImString str = new ImString(3);
            assertEquals("", str.get());
            assertEquals(0, str.getLength());
            assertEquals(3 + 1, str.getBufferSize());
        }

        @Test
        void withNotResized() {
            final ImString str = new ImString(3);
            str.set("test");
            assertEquals("tes", str.get());
            assertEquals(3, str.getLength());
            assertEquals(3 + 1, str.getBufferSize());
        }

        @Test
        void withResizedWithZeroResizeValue() {
            final ImString str = new ImString(3);
            str.set("test", true, 0);
            assertEquals("test", str.get());
            assertEquals(4, str.getLength());
            assertEquals(4 + 1, str.getBufferSize());
        }

        @Test
        void withResized() {
            final ImString str = new ImString(3);
            str.set("0123456789", true);
            assertEquals("0123456789", str.get());
            assertEquals(10, str.getLength());
            assertEquals(10 + str.inputData.resizeFactor + 1, str.getBufferSize());
        }

        @Test
        void withNullObject() {
            final ImString str = new ImString();
            final Object object = null;
            str.set(object);
            assertEquals("null", str.get());
        }

        @Test
        void withImString() {
            final ImString str = new ImString();
            final ImString string = new ImString("test");
            str.set(string);
            assertEquals("test", str.get());
        }

        @Test
        void withNumber() {
            final ImString str = new ImString();
            str.set(123);
            assertEquals("123", str.get());
        }
    }
}
