package imgui;

import imgui.binding.ImGuiStruct;
import imgui.flag.ImTextureFormat;
import imgui.flag.ImTextureStatus;

import java.nio.ByteBuffer;

/**
 * Specs and pixel storage for a texture used by Dear ImGui.
 * <p>
 * This is only useful for (1) core library and (2) renderer backends. End-user/applications do not need to care about
 * this. Renderer Backends will create a GPU-side version of this and communicate back through {@link #setTexID(long)}
 * and {@link #setStatus(int)}.
 * <p>
 * A texture stores two identifiers: {@code TexID} (the lower-level backend identifier stored in draw commands) and
 * {@code BackendUserData} (higher-level opaque storage for the backend's own book-keeping).
 */
public final class ImTextureData extends ImGuiStruct {
    private static final ImTextureRect TMP_USED_RECT = new ImTextureRect(0);
    private static final ImTextureRect TMP_UPDATE_RECT = new ImTextureRect(0);
    private static final ImTextureRect TMP_UPDATE = new ImTextureRect(0);

    public ImTextureData(final long ptr) {
        super(ptr);
    }

    /*JNI
        #include "_common.h"
        #define THIS ((ImTextureData*)STRUCT_PTR)
     */

    /**
     * [DEBUG] Sequential index to facilitate identifying a texture when debugging/printing. Unique per atlas.
     */
    public native int getUniqueID(); /*
        return THIS->UniqueID;
    */

    /**
     * Texture status, one of the {@link ImTextureStatus} values. Always use {@link #setStatus(int)} to modify!
     *
     * @return one of the {@link ImTextureStatus} values
     */
    public native int getStatus(); /*
        return THIS->Status;
    */

    /**
     * Set the texture status. Called by the renderer backend after honoring a texture request.
     *
     * @param status one of the {@link ImTextureStatus} values
     */
    public native void setStatus(int status); /*
        THIS->SetStatus((ImTextureStatus)status);
    */

    /**
     * Convenience storage for the backend. Some backends may have enough with {@code TexID}.
     */
    public native long getBackendUserData(); /*
        return (uintptr_t)THIS->BackendUserData;
    */

    /**
     * Convenience storage for the backend. Some backends may have enough with {@code TexID}.
     */
    public native void setBackendUserData(long backendUserData); /*
        THIS->BackendUserData = (void*)backendUserData;
    */

    /**
     * Backend-specific texture identifier, stored in {@code ImDrawCmd::GetTexID()} and passed to the backend's render
     * function.
     */
    public native long getTexID(); /*
        return (uintptr_t)THIS->TexID;
    */

    /**
     * Set the backend-specific texture identifier. Called by the renderer backend after uploading the texture.
     */
    public native void setTexID(long texID); /*
        THIS->SetTexID((ImTextureID)(uintptr_t)texID);
    */

    /**
     * Texture format, one of the {@link ImTextureFormat} values.
     *
     * @return one of the {@link ImTextureFormat} values
     */
    public native int getFormat(); /*
        return THIS->Format;
    */

    /**
     * Texture width, in pixels.
     */
    public native int getWidth(); /*
        return THIS->Width;
    */

    /**
     * Texture height, in pixels.
     */
    public native int getHeight(); /*
        return THIS->Height;
    */

    /**
     * Bytes per pixel: 4 for {@code ImTextureFormat_RGBA32} or 1 for {@code ImTextureFormat_Alpha8}.
     */
    public native int getBytesPerPixel(); /*
        return THIS->BytesPerPixel;
    */

    /**
     * Direct view over the CPU-side pixel buffer holding {@code Width*Height} pixels
     * ({@code Width*Height*BytesPerPixel} bytes). The returned buffer wraps the native memory directly (no copy), so it
     * is only valid while the texture keeps its pixels (i.e. before {@code DestroyPixels}).
     *
     * @return a direct {@link ByteBuffer} over the pixel data, or {@code null} if the pixels have been freed
     */
    public native ByteBuffer getPixels(); /*
        if (THIS->Pixels == NULL) {
            return NULL;
        }
        return env->NewDirectByteBuffer(THIS->Pixels, THIS->GetSizeInBytes());
    */

    /**
     * Total size of the pixel buffer in bytes ({@code Width*Height*BytesPerPixel}).
     */
    public native int getSizeInBytes(); /*
        return THIS->GetSizeInBytes();
    */

    /**
     * Row pitch in bytes ({@code Width*BytesPerPixel}).
     */
    public native int getPitch(); /*
        return THIS->GetPitch();
    */

    /**
     * Bounding box encompassing all past and queued updates.
     */
    public ImTextureRect getUsedRect() {
        TMP_USED_RECT.ptr = nGetUsedRect();
        return TMP_USED_RECT;
    }

    private native long nGetUsedRect(); /*
        return (uintptr_t)&THIS->UsedRect;
    */

    /**
     * Bounding box encompassing all queued updates.
     */
    public ImTextureRect getUpdateRect() {
        TMP_UPDATE_RECT.ptr = nGetUpdateRect();
        return TMP_UPDATE_RECT;
    }

    private native long nGetUpdateRect(); /*
        return (uintptr_t)&THIS->UpdateRect;
    */

    /**
     * Number of individual update rectangles queued for this texture.
     */
    public native int getUpdatesSize(); /*
        return THIS->Updates.Size;
    */

    /**
     * Individual update rectangle at the given index. The returned value is a shared instance, valid only until the
     * next call to this method.
     *
     * @param idx index in {@code [0, getUpdatesSize())}
     */
    public ImTextureRect getUpdates(final int idx) {
        TMP_UPDATE.ptr = nGetUpdates(idx);
        return TMP_UPDATE;
    }

    private native long nGetUpdates(int idx); /*
        return (uintptr_t)&THIS->Updates[idx];
    */

    /**
     * Count of successive frames where the texture was not used. Always {@code > 0} when the status is
     * {@code ImTextureStatus_WantDestroy}.
     */
    public native int getUnusedFrames(); /*
        return THIS->UnusedFrames;
    */

    /**
     * Number of contexts using this texture. Used during backend shutdown.
     */
    public native int getRefCount(); /*
        return THIS->RefCount;
    */

    /**
     * Whether the texture data is known to use colors (rather than just white + alpha).
     */
    public native boolean getUseColors(); /*
        return THIS->UseColors;
    */

    /**
     * [Internal] Whether the texture is queued to be destroyed next frame. May still be used in the current frame.
     */
    public native boolean getWantDestroyNextFrame(); /*
        return THIS->WantDestroyNextFrame;
    */

    /**
     * Allocate the CPU-side pixel buffer for the given format and size. Generally called by the core library.
     *
     * @param format one of the {@link ImTextureFormat} values
     * @param width  texture width in pixels
     * @param height texture height in pixels
     */
    public native void create(int format, int width, int height); /*
        THIS->Create((ImTextureFormat)format, width, height);
    */

    /**
     * Free the CPU-side pixel buffer.
     */
    public native void destroyPixels(); /*
        THIS->DestroyPixels();
    */

    /*JNI
        #undef THIS
     */
}
