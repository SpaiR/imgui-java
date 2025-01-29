package imgui;

import imgui.binding.ImGuiStruct;
import imgui.binding.annotation.BindingField;
import imgui.binding.annotation.BindingMethod;
import imgui.binding.annotation.BindingSource;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * All draw data to render Dear ImGui frame
 * (NB: the style and the naming convention here is a little inconsistent, we currently preserve them for backward compatibility purpose,
 * as this is one of the oldest structure exposed by the library! Basically, ImDrawList == CmdList)
 * <p>
 * BINDING NOTICE: Since it's impossible to do a 1:1 mapping with JNI, current class provides "getCmdList*()" methods.
 * Those are used to get the data needed to do a rendering.
 */
@BindingSource
public final class ImDrawData extends ImGuiStruct {
    private static final int RESIZE_FACTOR = 5_000;
    private static ByteBuffer dataBuffer = ByteBuffer.allocateDirect(25_000).order(ByteOrder.nativeOrder());

    public ImDrawData(final long ptr) {
        super(ptr);
    }

    /*JNI
        #include "_common.h"
        #define THIS ((ImDrawData*)STRUCT_PTR)
     */

    ///////// Start of Render Methods | Binding

    /**
     * Draw commands. Typically 1 command = 1 GPU draw call, unless the command is a callback.
     */
    public native int getCmdListCmdBufferSize(int cmdListIdx); /*
        return THIS->CmdLists[cmdListIdx]->CmdBuffer.Size;
    */

    /**
     * Number of indices (multiple of 3) to be rendered as triangles.
     * Vertices are stored in the callee ImDrawList's vtx_buffer[] array, indices in idx_buffer[].
     */
    public native int getCmdListCmdBufferElemCount(int cmdListIdx, int cmdBufferIdx); /*
        return THIS->CmdLists[cmdListIdx]->CmdBuffer[cmdBufferIdx].ElemCount;
    */

    /**
     * Clipping rectangle (x1, y1, x2, y2). Subtract ImDrawData.DisplayPos to get clipping rectangle in "viewport" coordinates
     */
    public ImVec4 getCmdListCmdBufferClipRect(final int cmdListIdx, final int cmdBufferIdx) {
        final ImVec4 dst = new ImVec4();
        getCmdListCmdBufferClipRect(dst, cmdListIdx, cmdBufferIdx);
        return dst;
    }

    /**
     * Clipping rectangle (x1, y1, x2, y2). Subtract ImDrawData.DisplayPos to get clipping rectangle in "viewport" coordinates
     */
    public native void getCmdListCmdBufferClipRect(ImVec4 dst, int cmdListIdx, int cmdBufferIdx); /*
        Jni::ImVec4Cpy(env, &THIS->CmdLists[cmdListIdx]->CmdBuffer[cmdBufferIdx].ClipRect, dst);
    */

    /**
     * User-provided texture ID. Set by user in ImfontAtlas::SetTexID() for fonts or passed to Image*() functions.
     * Ignore if never using images or multiple fonts atlas.
     */
    public native long getCmdListCmdBufferTextureId(int cmdListIdx, int cmdBufferIdx); /*
        return (uintptr_t)THIS->CmdLists[cmdListIdx]->CmdBuffer[cmdBufferIdx].GetTexID();
    */

    /**
     * Start offset in vertex buffer. Pre-1.71 or without ImGuiBackendFlags_RendererHasVtxOffset: always 0.
     * With ImGuiBackendFlags_RendererHasVtxOffset: may be {@code >}0 to support meshes larger than 64K vertices with 16-bit indices.
     */
    public native int getCmdListCmdBufferVtxOffset(int cmdListIdx, int cmdBufferIdx); /*
        return THIS->CmdLists[cmdListIdx]->CmdBuffer[cmdBufferIdx].VtxOffset;
    */

    /**
     * Start offset in index buffer. Always equal to sum of ElemCount drawn so far.
     */
    public native int getCmdListCmdBufferIdxOffset(int cmdListIdx, int cmdBufferIdx); /*
        return THIS->CmdLists[cmdListIdx]->CmdBuffer[cmdBufferIdx].IdxOffset;
    */

    /**
     * Index buffer. Each command consume ImDrawCmd::ElemCount of those
     */
    public native int getCmdListIdxBufferSize(int cmdListIdx); /*
        return THIS->CmdLists[cmdListIdx]->IdxBuffer.Size;
    */

    public ByteBuffer getCmdListIdxBufferData(final int cmdListIdx) {
        final int idxBufferCapacity = getCmdListIdxBufferSize(cmdListIdx) * sizeOfImDrawIdx();
        if (dataBuffer.capacity() < idxBufferCapacity) {
            dataBuffer.clear();
            dataBuffer = ByteBuffer.allocateDirect(idxBufferCapacity + RESIZE_FACTOR).order(ByteOrder.nativeOrder());
        }

        nGetCmdListIdxBufferData(cmdListIdx, dataBuffer, idxBufferCapacity);

        dataBuffer.position(0);
        dataBuffer.limit(idxBufferCapacity);

        return dataBuffer;
    }

    private native void nGetCmdListIdxBufferData(int cmdListIdx, ByteBuffer idxBuffer, int idxBufferCapacity); /*
        memcpy(idxBuffer, THIS->CmdLists[cmdListIdx]->IdxBuffer.Data, idxBufferCapacity);
    */

    /**
     * Vertex buffer.
     */
    public native int getCmdListVtxBufferSize(int cmdListIdx); /*
        return THIS->CmdLists[cmdListIdx]->VtxBuffer.Size;
    */

    public ByteBuffer getCmdListVtxBufferData(final int cmdListIdx) {
        final int vtxBufferCapacity = getCmdListVtxBufferSize(cmdListIdx) * sizeOfImDrawVert();
        if (dataBuffer.capacity() < vtxBufferCapacity) {
            dataBuffer.clear();
            dataBuffer = ByteBuffer.allocateDirect(vtxBufferCapacity + RESIZE_FACTOR).order(ByteOrder.nativeOrder());
        }

        nGetCmdListVtxBufferData(cmdListIdx, dataBuffer, vtxBufferCapacity);

        dataBuffer.position(0);
        dataBuffer.limit(vtxBufferCapacity);

        return dataBuffer;
    }

    private native void nGetCmdListVtxBufferData(int cmdListIdx, ByteBuffer vtxBuffer, int vtxBufferCapacity); /*
        memcpy(vtxBuffer, THIS->CmdLists[cmdListIdx]->VtxBuffer.Data, vtxBufferCapacity);
    */

    public static native int sizeOfImDrawVert(); /*
        return (int)sizeof(ImDrawVert);
    */

    public static native int sizeOfImDrawIdx(); /*
        return (int)sizeof(ImDrawIdx);
    */

    ///////// End of Render Methods

    /**
     * Only valid after Render() is called and before the next NewFrame() is called.
     */
    @BindingField(accessors = BindingField.Accessor.GETTER)
    public boolean Valid;

    /**
     * Number of ImDrawList* to render
     */
    @BindingField(accessors = BindingField.Accessor.GETTER)
    public int CmdListsCount;

    /**
     * For convenience, sum of all ImDrawList's IdxBuffer.Size
     */
    @BindingField(accessors = BindingField.Accessor.GETTER)
    public int TotalIdxCount;

    /**
     * For convenience, sum of all ImDrawList's VtxBuffer.Size
     */
    @BindingField(accessors = BindingField.Accessor.GETTER)
    public int TotalVtxCount;

    /**
     * Upper-left position of the viewport to render (== upper-left of the orthogonal projection matrix to use)
     */
    @BindingField(accessors = BindingField.Accessor.GETTER)
    public ImVec2 DisplayPos;

    /**
     * Size of the viewport to render (== io.DisplaySize for the main viewport)
     * (DisplayPos + DisplaySize == lower-right of the orthogonal projection matrix to use)
     */
    @BindingField(accessors = BindingField.Accessor.GETTER)
    public ImVec2 DisplaySize;

    /**
     * Amount of pixels for each unit of DisplaySize. Based on io.DisplayFramebufferScale. Generally (1,1) on normal display, (2,2) on OSX with Retina display.
     */
    @BindingField(accessors = BindingField.Accessor.GETTER)
    public ImVec2 FramebufferScale;

    /**
     * Viewport carrying the ImDrawData instance, might be of use to the renderer (generally not).
     */
    @BindingField(accessors = BindingField.Accessor.GETTER)
    public ImGuiViewport OwnerViewport;

    // Functions

    @BindingMethod
    public native void Clear();

    /**
     * Helper to add an external draw list into an existing ImDrawData.
     */
    @BindingMethod
    public native void AddDrawList(ImDrawList drawList);

    /**
     * Helper to convert all buffers from indexed to non-indexed, in case you cannot render indexed. Note: this is slow and most likely a waste of resources.
     * Always prefer indexed rendering!
     */
    @BindingMethod
    public native void DeIndexAllBuffers();

    /**
     * Helper to scale the ClipRect field of each ImDrawCmd. Use if your final output buffer is at a different scale than Dear ImGui expects,
     * or if there is a difference between your window resolution and framebuffer resolution.
     */
    @BindingMethod
    public native void ScaleClipRects(ImVec2 fbScale);

    /*JNI
        #undef THIS
     */
}
