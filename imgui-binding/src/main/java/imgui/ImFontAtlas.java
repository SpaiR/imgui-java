package imgui;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Load and rasterize multiple TTF/OTF fonts into a same texture. The font atlas will build a single texture holding:
 *  - One or more fonts.
 *  - Custom graphics data needed to render the shapes needed by Dear ImGui.
 *  - Mouse cursor shapes for software cursor rendering (unless setting 'Flags |= ImFontAtlasFlags_NoMouseCursors' in the font atlas).
 * It is the user-code responsibility to setup/build the atlas, then upload the pixel data into a texture accessible by your graphics api.
 *  - Optionally, call any of the AddFont*** functions. If you don't call any, the default font embedded in the code will be loaded for you.
 *  - Call GetTexDataAsAlpha8() or GetTexDataAsRGBA32() to build and retrieve pixels data.
 *  - Upload the pixels data into a texture within your graphics system (see imgui_impl_xxxx.cpp examples)
 *  - Call SetTexID(my_tex_id); and pass the pointer/identifier to your texture in a format natural to your graphics API.
 *    This value will be passed back to you during rendering to identify the texture. Read FAQ entry about ImTextureID for more details.
 * Common pitfalls:
 * - If you pass a 'glyph_ranges' array to AddFont*** functions, you need to make sure that your array persist up until the
 *   atlas is build (when calling GetTexData*** or Build()). We only copy the pointer, not the data.
 * - Important: By default, AddFontFromMemoryTTF() takes ownership of the data. Even though we are not writing to it, we will free the pointer on destruction.
 *   You can set font_cfg->FontDataOwnedByAtlas=false to keep ownership of your data and it won't be freed,
 * - Even though many functions are suffixed with "TTF", OTF data is supported just as well.
 * - This is an old API and it is currently awkward for those and and various other reasons! We will address them in the future!
 */
public final class ImFontAtlas implements ImDestroyable {
    final long ptr;

    /**
     * This class will create a native structure.
     * Call {@link #destroy()} method to manually free used memory.
     */
    public ImFontAtlas() {
        ImGui.touch();
        ptr = nCreate();
    }

    ImFontAtlas(final long ptr) {
        this.ptr = ptr;
    }

    @Override
    public void destroy() {
        nDestroy(ptr);
    }

    /*JNI
        #include <imgui.h>

        jfieldID imFontAtlasPtrID;

        #define IM_FONT_ATLAS ((ImFontAtlas*)env->GetLongField(object, imFontAtlasPtrID))
     */

    static native void nInit(); /*
        jclass jImFontAtlasClass = env->FindClass("imgui/ImFontAtlas");
        imFontAtlasPtrID = env->GetFieldID(jImFontAtlasClass, "ptr", "J");
    */

    private native long nCreate(); /*
        ImFontAtlas* imFontAtlas = new ImFontAtlas();
        return (long)imFontAtlas;
    */

    private native void nDestroy(long ptr); /*
        delete (ImFontAtlas*)ptr;
    */

    // Build atlas, retrieve pixel data.
    // User is in charge of copying the pixels into graphics memory (e.g. create a texture with your engine). Then store your texture handle with SetTexID().
    // The pitch is always = Width * BytesPerPixels (1 or 4)
    // Building in RGBA32 format is provided for convenience and compatibility, but note that unless you manually manipulate or copy color data into
    // the texture (e.g. when using the AddCustomRect*** api), then the RGB pixels emitted will always be white (~75% of memory/bandwidth waste.

    /**
     * Build pixels data. This is called automatically for you by the GetTexData*** functions.
     */
    public native boolean build(); /*
        return IM_FONT_ATLAS->Build();
    */

    /**
     * 1 byte-per-pixel
     */
    public ByteBuffer getTexDataAsAlpha8(final ImInt outWidth, final ImInt outHeight) {
        return getTexDataAsAlpha8(outWidth, outHeight, new ImInt());
    }

    /**
     * 1 byte-per-pixel
     */
    public ByteBuffer getTexDataAsAlpha8(final ImInt outWidth, final ImInt outHeight, final ImInt outBytesPerPixel) {
        final byte[] tmpBuff = getTexDataAsAlpha8(outWidth.data, outHeight.data, outBytesPerPixel.data);
        final ByteBuffer buffer = ByteBuffer.allocateDirect(tmpBuff.length).order(ByteOrder.nativeOrder());
        buffer.put(tmpBuff);
        buffer.flip();
        return buffer;
    }

    private native byte[] getTexDataAsAlpha8(int[] outWidth, int[] outHeight, int[] outBytesPerPixel); /*
        unsigned char* pixels;
        IM_FONT_ATLAS->GetTexDataAsAlpha8(&pixels, &outWidth[0], &outHeight[0], &outBytesPerPixel[0]);
        int size = outWidth[0] * outHeight[0] * outBytesPerPixel[0];
        jbyteArray jBytes = env->NewByteArray(size);
        env->SetByteArrayRegion(jBytes, 0, size, (jbyte*)pixels);
        return jBytes;
    */

    /**
     * 4 bytes-per-pixel
     */
    public ByteBuffer getTexDataAsRGBA32(final ImInt outWidth, final ImInt outHeight) {
        return getTexDataAsRGBA32(outWidth, outHeight, new ImInt());
    }

    /**
     * 4 bytes-per-pixel
     */
    public ByteBuffer getTexDataAsRGBA32(final ImInt outWidth, final ImInt outHeight, final ImInt outBytesPerPixel) {
        final byte[] tmpBuff = nGetTexDataAsRGBA32(outWidth.data, outHeight.data, outBytesPerPixel.data);
        final ByteBuffer buffer = ByteBuffer.allocateDirect(tmpBuff.length).order(ByteOrder.nativeOrder());
        buffer.put(tmpBuff);
        buffer.flip();
        return buffer;
    }

    private native byte[] nGetTexDataAsRGBA32(int[] outWidth, int[] outHeight, int[] outBytesPerPixel); /*
        unsigned char* pixels;
        IM_FONT_ATLAS->GetTexDataAsRGBA32(&pixels, &outWidth[0], &outHeight[0], &outBytesPerPixel[0]);
        int size = outWidth[0] * outHeight[0] * outBytesPerPixel[0];
        jbyteArray jBytes = env->NewByteArray(size);
        env->SetByteArrayRegion(jBytes, 0, size, (jbyte*)pixels);
        return jBytes;
    */

    public native boolean isBuilt(); /*
        return IM_FONT_ATLAS->IsBuilt();
    */

    public native void setTexID(int imTextureId); /*
        IM_FONT_ATLAS->SetTexID((ImTextureID)imTextureId);
    */
}
