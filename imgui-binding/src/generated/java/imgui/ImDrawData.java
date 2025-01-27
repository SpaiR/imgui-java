package imgui;

import imgui.binding.ImGuiStruct;

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
    public boolean getValid() {
        return nGetValid();
    }

    private native boolean nGetValid(); /*
        return THIS->Valid;
    */

    /**
     * Number of ImDrawList* to render
     */
    public int getCmdListsCount() {
        return nGetCmdListsCount();
    }

    private native int nGetCmdListsCount(); /*
        return THIS->CmdListsCount;
    */

    /**
     * For convenience, sum of all ImDrawList's IdxBuffer.Size
     */
    public int getTotalIdxCount() {
        return nGetTotalIdxCount();
    }

    private native int nGetTotalIdxCount(); /*
        return THIS->TotalIdxCount;
    */

    /**
     * For convenience, sum of all ImDrawList's VtxBuffer.Size
     */
    public int getTotalVtxCount() {
        return nGetTotalVtxCount();
    }

    private native int nGetTotalVtxCount(); /*
        return THIS->TotalVtxCount;
    */

    /**
     * Upper-left position of the viewport to render (== upper-left of the orthogonal projection matrix to use)
     */
    public ImVec2 getDisplayPos() {
        final ImVec2 dst = new ImVec2();
        nGetDisplayPos(dst);
        return dst;
    }

    /**
     * Upper-left position of the viewport to render (== upper-left of the orthogonal projection matrix to use)
     */
    public float getDisplayPosX() {
        return nGetDisplayPosX();
    }

    /**
     * Upper-left position of the viewport to render (== upper-left of the orthogonal projection matrix to use)
     */
    public float getDisplayPosY() {
        return nGetDisplayPosY();
    }

    /**
     * Upper-left position of the viewport to render (== upper-left of the orthogonal projection matrix to use)
     */
    public void getDisplayPos(final ImVec2 dst) {
        nGetDisplayPos(dst);
    }

    private native void nGetDisplayPos(ImVec2 dst); /*
        Jni::ImVec2Cpy(env, THIS->DisplayPos, dst);
    */

    private native float nGetDisplayPosX(); /*
        return THIS->DisplayPos.x;
    */

    private native float nGetDisplayPosY(); /*
        return THIS->DisplayPos.y;
    */

    /**
     * Size of the viewport to render (== io.DisplaySize for the main viewport)
     * (DisplayPos + DisplaySize == lower-right of the orthogonal projection matrix to use)
     */
    public ImVec2 getDisplaySize() {
        final ImVec2 dst = new ImVec2();
        nGetDisplaySize(dst);
        return dst;
    }

    /**
     * Size of the viewport to render (== io.DisplaySize for the main viewport)
     * (DisplayPos + DisplaySize == lower-right of the orthogonal projection matrix to use)
     */
    public float getDisplaySizeX() {
        return nGetDisplaySizeX();
    }

    /**
     * Size of the viewport to render (== io.DisplaySize for the main viewport)
     * (DisplayPos + DisplaySize == lower-right of the orthogonal projection matrix to use)
     */
    public float getDisplaySizeY() {
        return nGetDisplaySizeY();
    }

    /**
     * Size of the viewport to render (== io.DisplaySize for the main viewport)
     * (DisplayPos + DisplaySize == lower-right of the orthogonal projection matrix to use)
     */
    public void getDisplaySize(final ImVec2 dst) {
        nGetDisplaySize(dst);
    }

    private native void nGetDisplaySize(ImVec2 dst); /*
        Jni::ImVec2Cpy(env, THIS->DisplaySize, dst);
    */

    private native float nGetDisplaySizeX(); /*
        return THIS->DisplaySize.x;
    */

    private native float nGetDisplaySizeY(); /*
        return THIS->DisplaySize.y;
    */

    /**
     * Amount of pixels for each unit of DisplaySize. Based on io.DisplayFramebufferScale. Generally (1,1) on normal display, (2,2) on OSX with Retina display.
     */
    public ImVec2 getFramebufferScale() {
        final ImVec2 dst = new ImVec2();
        nGetFramebufferScale(dst);
        return dst;
    }

    /**
     * Amount of pixels for each unit of DisplaySize. Based on io.DisplayFramebufferScale. Generally (1,1) on normal display, (2,2) on OSX with Retina display.
     */
    public float getFramebufferScaleX() {
        return nGetFramebufferScaleX();
    }

    /**
     * Amount of pixels for each unit of DisplaySize. Based on io.DisplayFramebufferScale. Generally (1,1) on normal display, (2,2) on OSX with Retina display.
     */
    public float getFramebufferScaleY() {
        return nGetFramebufferScaleY();
    }

    /**
     * Amount of pixels for each unit of DisplaySize. Based on io.DisplayFramebufferScale. Generally (1,1) on normal display, (2,2) on OSX with Retina display.
     */
    public void getFramebufferScale(final ImVec2 dst) {
        nGetFramebufferScale(dst);
    }

    private native void nGetFramebufferScale(ImVec2 dst); /*
        Jni::ImVec2Cpy(env, THIS->FramebufferScale, dst);
    */

    private native float nGetFramebufferScaleX(); /*
        return THIS->FramebufferScale.x;
    */

    private native float nGetFramebufferScaleY(); /*
        return THIS->FramebufferScale.y;
    */

    /**
     * Viewport carrying the ImDrawData instance, might be of use to the renderer (generally not).
     */
    public ImGuiViewport getOwnerViewport() {
        return new ImGuiViewport(nGetOwnerViewport());
    }

    private native long nGetOwnerViewport(); /*
        return (uintptr_t)THIS->OwnerViewport;
    */

    // Functions

    public void clear() {
        nClear();
    }

    private native void nClear(); /*
        THIS->Clear();
    */

    /**
     * Helper to add an external draw list into an existing ImDrawData.
     */
    public void addDrawList(final ImDrawList drawList) {
        nAddDrawList(drawList.ptr);
    }

    private native void nAddDrawList(long drawList); /*
        THIS->AddDrawList(reinterpret_cast<ImDrawList*>(drawList));
    */

    /**
     * Helper to convert all buffers from indexed to non-indexed, in case you cannot render indexed. Note: this is slow and most likely a waste of resources.
     * Always prefer indexed rendering!
     */
    public void deIndexAllBuffers() {
        nDeIndexAllBuffers();
    }

    private native void nDeIndexAllBuffers(); /*
        THIS->DeIndexAllBuffers();
    */

    /**
     * Helper to scale the ClipRect field of each ImDrawCmd. Use if your final output buffer is at a different scale than Dear ImGui expects,
     * or if there is a difference between your window resolution and framebuffer resolution.
     */
    public void scaleClipRects(final ImVec2 fbScale) {
        nScaleClipRects(fbScale.x, fbScale.y);
    }

    /**
     * Helper to scale the ClipRect field of each ImDrawCmd. Use if your final output buffer is at a different scale than Dear ImGui expects,
     * or if there is a difference between your window resolution and framebuffer resolution.
     */
    public void scaleClipRects(final float fbScaleX, final float fbScaleY) {
        nScaleClipRects(fbScaleX, fbScaleY);
    }

    private native void nScaleClipRects(float fbScaleX, float fbScaleY); /*MANUAL
        ImVec2 fbScale = ImVec2(fbScaleX, fbScaleY);
        THIS->ScaleClipRects(fbScale);
    */

    /*JNI
        #undef THIS
     */
}
