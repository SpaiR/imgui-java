package imgui;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ImStringTest {
    @Test
    public void testCreation() {
        ImString str = new ImString(10);
        assertEquals("", str.get());
        assertEquals(0, str.getLength(), "String length should be 0, since no data was provided");
        assertEquals(10 + 1, str.getBufferSize(), "Buffer size must be 'length + 1', +1 - size of the ImGui caret");

        str = new ImString("test");
        assertEquals("test", str.get());
        assertEquals(4, str.getLength());
        assertEquals(4 + 1, str.getBufferSize());

        str = new ImString("test", 10);
        assertEquals("test", str.get());
        assertEquals(4, str.getLength());
        assertEquals(10 + 1, str.getBufferSize());
    }

    @Test
    public void testGet() {
        final ImString str = new ImString("test");
        assertEquals("test", str.get());
        assertEquals(str.get(), str.toString(), "ImString#toString() must return value which should be equal to ImString#get()");
    }

    @Test
    public void testSet() {
        final ImString str = new ImString(3);
        assertEquals("", str.get());
        assertEquals(0, str.getLength());
        assertEquals(3 + 1, str.getBufferSize());

        str.set("test");
        assertEquals("tes", str.get());
        assertEquals(3, str.getLength());
        assertEquals(3 + 1, str.getBufferSize());

        str.set("test", true, 0);
        assertEquals("test", str.get());
        assertEquals(4, str.getLength());
        assertEquals(4 + 1, str.getBufferSize());

        str.set("0123456789", true);
        assertEquals("0123456789", str.get());
        assertEquals(10, str.getLength());
        assertEquals(10 + str.inputData.resizeFactor + 1, str.getBufferSize());
    }
}
