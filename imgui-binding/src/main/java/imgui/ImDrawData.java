package imgui;

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
public final class ImDrawData {
    public static final int SIZEOF_IM_DRAW_IDX = 2;
    public static final int SIZEOF_IM_DRAW_VERT = (4 + 1) * 4;

    private static final int FACTOR = 5_000;

    final long ptr;

    private ByteBuffer idxBuffer = ByteBuffer.allocateDirect(SIZEOF_IM_DRAW_IDX * FACTOR).order(ByteOrder.nativeOrder());
    private ByteBuffer vtxBuffer = ByteBuffer.allocateDirect(SIZEOF_IM_DRAW_VERT * FACTOR).order(ByteOrder.nativeOrder());

    ImDrawData(final long ptr) {
        this.ptr = ptr;
    }

    /*JNI
        #include <stdint.h>
        #include <imgui.h>
        #include "jni_common.h"

        jfieldID imDrawDataPtrID;

        #define IM_DRAW_DATA ((ImDrawData*)env->GetLongField(object, imDrawDataPtrID))
     */

    static native void nInit(); /*
        jclass jImDrawDataClass = env->FindClass("imgui/ImDrawData");
        imDrawDataPtrID = env->GetFieldID(jImDrawDataClass, "ptr", "J");
    */

    ///////// Start of Render Methods | Binding

    /**
     * Draw commands. Typically 1 command = 1 GPU draw call, unless the command is a callback.
     */
    public native int getCmdListCmdBufferSize(int cmdListIdx); /*
        return IM_DRAW_DATA->CmdLists[cmdListIdx]->CmdBuffer.Size;
    */

    /**
     * Number of indices (multiple of 3) to be rendered as triangles.
     * Vertices are stored in the callee ImDrawList's vtx_buffer[] array, indices in idx_buffer[].
     */
    public native int getCmdListCmdBufferElemCount(int cmdListIdx, int cmdBufferIdx); /*
        return IM_DRAW_DATA->CmdLists[cmdListIdx]->CmdBuffer[cmdBufferIdx].ElemCount;
    */

    /**
     * Clipping rectangle (x1, y1, x2, y2). Subtract ImDrawData.DisplayPos to get clipping rectangle in "viewport" coordinates
     */
    public native void getCmdListCmdBufferClipRect(int cmdListIdx, int cmdBufferIdx, ImVec4 dstImVec4); /*
        Jni::ImVec4Cpy(env, &IM_DRAW_DATA->CmdLists[cmdListIdx]->CmdBuffer[cmdBufferIdx].ClipRect, dstImVec4);
    */

    /**
     * User-provided texture ID. Set by user in ImfontAtlas::SetTexID() for fonts or passed to Image*() functions.
     * Ignore if never using images or multiple fonts atlas.
     */
    public native int getCmdListCmdBufferTextureId(int cmdListIdx, int cmdBufferIdx); /*
        return (intptr_t)IM_DRAW_DATA->CmdLists[cmdListIdx]->CmdBuffer[cmdBufferIdx].TextureId;
    */

    /**
     * Start offset in vertex buffer. Pre-1.71 or without ImGuiBackendFlags_RendererHasVtxOffset: always 0.
     * With ImGuiBackendFlags_RendererHasVtxOffset: may be {@code >}0 to support meshes larger than 64K vertices with 16-bit indices.
     */
    public native int getCmdListCmdBufferVtxOffset(int cmdListIdx, int cmdBufferIdx); /*
        return IM_DRAW_DATA->CmdLists[cmdListIdx]->CmdBuffer[cmdBufferIdx].VtxOffset;
    */

    /**
     * Start offset in index buffer. Always equal to sum of ElemCount drawn so far.
     */
    public native int getCmdListCmdBufferIdxOffset(int cmdListIdx, int cmdBufferIdx); /*
        return IM_DRAW_DATA->CmdLists[cmdListIdx]->CmdBuffer[cmdBufferIdx].IdxOffset;
    */

    /**
     * Index buffer. Each command consume ImDrawCmd::ElemCount of those
     */
    public native int getCmdListIdxBufferSize(int cmdListIdx); /*
        return IM_DRAW_DATA->CmdLists[cmdListIdx]->IdxBuffer.Size;
    */

    public ByteBuffer getCmdListIdxBufferData(final int cmdListIdx) {
        final int idxBufferCapacity = getCmdListIdxBufferSize(cmdListIdx) * SIZEOF_IM_DRAW_IDX;
        if (idxBuffer.capacity() < idxBufferCapacity) {
            idxBuffer = ByteBuffer.allocateDirect(idxBufferCapacity + FACTOR).order(ByteOrder.nativeOrder());
        }

        nGetCmdListIdxBufferData(cmdListIdx, idxBuffer, idxBufferCapacity);

        idxBuffer.position(0);
        idxBuffer.limit(idxBufferCapacity);

        return idxBuffer;
    }

    private native void nGetCmdListIdxBufferData(int cmdListIdx, ByteBuffer idxBuffer, int idxBufferCapacity); /*
        memcpy(idxBuffer, IM_DRAW_DATA->CmdLists[cmdListIdx]->IdxBuffer.Data, idxBufferCapacity);
    */

    /**
     * Vertex buffer.
     */
    public native int getCmdListVtxBufferSize(int cmdListIdx); /*
        return IM_DRAW_DATA->CmdLists[cmdListIdx]->VtxBuffer.Size;
    */

    public ByteBuffer getCmdListVtxBufferData(final int cmdListIdx) {
        final int vtxBufferCapacity = getCmdListVtxBufferSize(cmdListIdx) * SIZEOF_IM_DRAW_VERT;
        if (vtxBuffer.capacity() < vtxBufferCapacity) {
            vtxBuffer = ByteBuffer.allocateDirect(vtxBufferCapacity + FACTOR).order(ByteOrder.nativeOrder());
        }

        nGetCmdListVtxBufferData(cmdListIdx, vtxBuffer, vtxBufferCapacity);

        vtxBuffer.position(0);
        vtxBuffer.limit(vtxBufferCapacity);

        return vtxBuffer;
    }

    private native void nGetCmdListVtxBufferData(int cmdListIdx, ByteBuffer vtxBuffer, int vtxBufferCapacity); /*
        memcpy(vtxBuffer, IM_DRAW_DATA->CmdLists[cmdListIdx]->VtxBuffer.Data, vtxBufferCapacity);
    */

    ///////// End of Render Methods

    /**
     * Only valid after Render() is called and before the next NewFrame() is called.
     */
    public native boolean getValid(); /*
        return IM_DRAW_DATA->Valid;
    */

    /**
     * Number of ImDrawList* to render
     */
    public native int getCmdListsCount(); /*
        return IM_DRAW_DATA->CmdListsCount;
    */

    /**
     * For convenience, sum of all ImDrawList's IdxBuffer.Size
     */
    public native int getTotalIdxCount(); /*
        return IM_DRAW_DATA->TotalIdxCount;
    */

    /**
     * For convenience, sum of all ImDrawList's VtxBuffer.Size
     */
    public native int getTotalVtxCount(); /*
        return IM_DRAW_DATA->TotalVtxCount;
    */

    /**
     * Upper-left position of the viewport to render (== upper-left of the orthogonal projection matrix to use)
     */
    public native void getDisplayPos(ImVec2 dstImVec2); /*
        Jni::ImVec2Cpy(env, &IM_DRAW_DATA->DisplayPos, dstImVec2);
    */

    /**
     * Upper-left position of the viewport to render (== upper-left of the orthogonal projection matrix to use)
     */
    public native float getDisplayPosX(); /*
        return IM_DRAW_DATA->DisplayPos.x;
    */

    /**
     * Upper-left position of the viewport to render (== upper-left of the orthogonal projection matrix to use)
     */
    public native float getDisplayPosY(); /*
        return IM_DRAW_DATA->DisplayPos.y;
    */

    /**
     * Size of the viewport to render (== io.DisplaySize for the main viewport)
     * (DisplayPos + DisplaySize == lower-right of the orthogonal projection matrix to use)
     */
    public native void getDisplaySize(ImVec2 dstImVec2); /*
        Jni::ImVec2Cpy(env, &IM_DRAW_DATA->DisplaySize, dstImVec2);
    */

    /**
     * Size of the viewport to render (== io.DisplaySize for the main viewport)
     * (DisplayPos + DisplaySize == lower-right of the orthogonal projection matrix to use)
     */
    public native float getDisplaySizeX(); /*
        return IM_DRAW_DATA->DisplaySize.x;
    */

    /**
     * Size of the viewport to render (== io.DisplaySize for the main viewport)
     * (DisplayPos + DisplaySize == lower-right of the orthogonal projection matrix to use)
     */
    public native float getDisplaySizeY(); /*
        return IM_DRAW_DATA->DisplaySize.y;
    */

    /**
     * Amount of pixels for each unit of DisplaySize. Based on io.DisplayFramebufferScale. Generally (1,1) on normal display, (2,2) on OSX with Retina display.
     */
    public native void getFramebufferScale(ImVec2 dstImVec2); /*
        Jni::ImVec2Cpy(env, &IM_DRAW_DATA->FramebufferScale, dstImVec2);
    */

    /**
     * Amount of pixels for each unit of DisplaySize. Based on io.DisplayFramebufferScale. Generally (1,1) on normal display, (2,2) on OSX with Retina display.
     */
    public native float getFramebufferScaleX(); /*
        return IM_DRAW_DATA->FramebufferScale.x;
    */

    /**
     * Amount of pixels for each unit of DisplaySize. Based on io.DisplayFramebufferScale. Generally (1,1) on normal display, (2,2) on OSX with Retina display.
     */
    public native float getFramebufferScaleY(); /*
        return IM_DRAW_DATA->FramebufferScale.y;
    */

    // Functions

    /**
     * Helper to convert all buffers from indexed to non-indexed, in case you cannot render indexed. Note: this is slow and most likely a waste of resources.
     * Always prefer indexed rendering!
     */
    public native void deIndexAllBuffers(); /*
        IM_DRAW_DATA->DeIndexAllBuffers();
    */

    /**
     * Helper to scale the ClipRect field of each ImDrawCmd. Use if your final output buffer is at a different scale than Dear ImGui expects,
     * or if there is a difference between your window resolution and framebuffer resolution.
     */
    public native void scaleClipRects(float fbScaleX, float fbScaleY); /*
        const ImVec2 fbScale = ImVec2(fbScaleX, fbScaleY);
        IM_DRAW_DATA->ScaleClipRects(fbScale);
    */
}
