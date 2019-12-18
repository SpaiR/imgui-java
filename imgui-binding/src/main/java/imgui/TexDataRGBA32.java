package imgui;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class TexDataRGBA32 {
    public int width;
    public int height;

    public ByteBuffer pixelBuffer;

    public TexDataRGBA32() {
        int pixelMax = 131072;
        ByteBuffer buffer = ByteBuffer.allocateDirect(pixelMax);
        buffer.order(ByteOrder.nativeOrder());
        pixelBuffer = buffer;
    }
}
