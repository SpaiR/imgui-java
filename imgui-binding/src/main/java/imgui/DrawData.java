package imgui;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public final class DrawData {
    public final static int vBufferSize = (4 + 1) * 4;
    public final static int iBufferSize = 2;
    public final static int cmdBufferSize = (1 + 4 + 1) * 4;
    public int cmdListsCount; // Number of ImDrawList* to render
    public int totalIdxCount; // For convenience, sum of all ImDrawList's IdxBuffer.Size
    public int totalVtxCount;
    public int totalCmdCount;
    public float displayPosX;
    public float displayPosY;
    public float displaySizeX;
    public float displaySizeY;
    public float framebufferScaleX;
    public float framebufferScaleY;
    public ByteBuffer vByteBuffer;
    public ByteBuffer iByteBuffer;
    public ByteBuffer cmdByteBuffer;

    public DrawData(ByteBuffer vByteBuffer, ByteBuffer iByteBuffer, ByteBuffer cmdByteBuffer) {
        this.vByteBuffer = vByteBuffer;
        this.iByteBuffer = iByteBuffer;
        this.cmdByteBuffer = cmdByteBuffer;
    }

    public DrawData(int maxVertice, int maxIndices, int maxCmd) {
        this.vByteBuffer = ByteBuffer.allocateDirect(maxVertice * vBufferSize);
        this.iByteBuffer = ByteBuffer.allocateDirect(maxIndices * iBufferSize);
        this.cmdByteBuffer = ByteBuffer.allocateDirect(maxCmd * cmdBufferSize);
        vByteBuffer.order(ByteOrder.nativeOrder());
        iByteBuffer.order(ByteOrder.nativeOrder());
        cmdByteBuffer.order(ByteOrder.nativeOrder());
    }
}
