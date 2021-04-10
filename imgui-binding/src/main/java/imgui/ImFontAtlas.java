package imgui;

import imgui.binding.ImGuiStructDestroyable;
import imgui.type.ImInt;

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
 *   You can set font_cfg.FontDataOwnedByAtlas=false to keep ownership of your data and it won't be freed,
 * - Even though many functions are suffixed with "TTF", OTF data is supported just as well.
 * - This is an old API and it is currently awkward for those and and various other reasons! We will address them in the future!
 */
public final class ImFontAtlas extends ImGuiStructDestroyable {
    private ByteBuffer alpha8pixels = null;
    private ByteBuffer rgba32pixels = null;

    public ImFontAtlas() {
    }

    public ImFontAtlas(final long ptr) {
        super(ptr);
    }

    /*JNI
        #include "_common.h"

        #define IM_FONT_ATLAS ((ImFontAtlas*)STRUCT_PTR)

        jmethodID jImFontAtlasCreateAlpha8PixelsMID;
        jmethodID jImFontAtlasCreateRgba32PixelsMID;
     */

    static native void nInit(); /*
        jclass jImFontAtlasClass = env->FindClass("imgui/ImFontAtlas");

        jImFontAtlasCreateAlpha8PixelsMID = env->GetMethodID(jImFontAtlasClass, "createAlpha8Pixels", "(I)Ljava/nio/ByteBuffer;");
        jImFontAtlasCreateRgba32PixelsMID = env->GetMethodID(jImFontAtlasClass, "createRgba32Pixels", "(I)Ljava/nio/ByteBuffer;");
    */

    @Override
    protected long create() {
        return nCreate();
    }

    private native long nCreate(); /*
        return (intptr_t)(new ImFontConfig());
    */

    public ImFont addFont(final ImFontConfig imFontConfig) {
        return new ImFont(nAddFont(imFontConfig.ptr));
    }

    private native long nAddFont(long imFontConfigPtr); /*
        return (intptr_t)IM_FONT_ATLAS->AddFont((ImFontConfig*)imFontConfigPtr);
    */

    public ImFont addFontDefault() {
        return new ImFont(nAddFontDefault());
    }

    private native long nAddFontDefault(); /*
        return (intptr_t)IM_FONT_ATLAS->AddFontDefault();
    */

    public ImFont addFontDefault(final ImFontConfig imFontConfig) {
        return new ImFont(nAddFontDefault(imFontConfig.ptr));
    }

    private native long nAddFontDefault(long imFontConfigPtr); /*
        return (intptr_t)IM_FONT_ATLAS->AddFontDefault((ImFontConfig*)imFontConfigPtr);
    */

    public ImFont addFontFromFileTTF(final String filename, final float sizePixels) {
        return new ImFont(nAddFontFromFileTTF(filename, sizePixels));
    }

    private native long nAddFontFromFileTTF(String filename, float sizePixels); /*
        return (intptr_t)IM_FONT_ATLAS->AddFontFromFileTTF(filename, sizePixels);
    */

    public ImFont addFontFromFileTTF(final String filename, final float sizePixels, final ImFontConfig imFontConfig) {
        return new ImFont(nAddFontFromFileTTF(filename, sizePixels, imFontConfig.ptr));
    }

    private native long nAddFontFromFileTTF(String filename, float sizePixels, long imFontConfigPtr); /*
        return (intptr_t)IM_FONT_ATLAS->AddFontFromFileTTF(filename, sizePixels, (ImFontConfig*)imFontConfigPtr);
    */

    public ImFont addFontFromFileTTF(final String filename, final float sizePixels, final short[] glyphRanges) {
        return new ImFont(nAddFontFromFileTTF(filename, sizePixels, glyphRanges));
    }

    private native long nAddFontFromFileTTF(String filename, float sizePixels, short[] glyphRanges); /*
        return (intptr_t)IM_FONT_ATLAS->AddFontFromFileTTF(filename, sizePixels, NULL, (ImWchar*)&glyphRanges[0]);
    */

    public ImFont addFontFromFileTTF(final String filename, final float sizePixels, final ImFontConfig imFontConfig, final short[] glyphRanges) {
        return new ImFont(nAddFontFromFileTTF(filename, sizePixels, imFontConfig.ptr, glyphRanges));
    }

    private native long nAddFontFromFileTTF(String filename, float sizePixels, long imFontConfigPtr, short[] glyphRanges); /*
        return (intptr_t)IM_FONT_ATLAS->AddFontFromFileTTF(filename, sizePixels, (ImFontConfig*)imFontConfigPtr, (ImWchar*)&glyphRanges[0]);
    */

    /**
     * Note: Transfer ownership of 'ttf_data' to ImFontAtlas! Will be deleted after destruction of the atlas.
     * Set font_cfg.FontDataOwnedByAtlas=false to keep ownership of your data and it won't be freed.
     */
    public ImFont addFontFromMemoryTTF(final byte[] fontData, final float sizePixels) {
        return new ImFont(nAddFontFromMemoryTTF(fontData, fontData.length, sizePixels));
    }

    private native long nAddFontFromMemoryTTF(byte[] fontData, int fontSize, float sizePixels); /*
        return (intptr_t)IM_FONT_ATLAS->AddFontFromMemoryTTF(&fontData[0], fontSize, sizePixels);
    */


    /**
     * Note: Transfer ownership of 'ttf_data' to ImFontAtlas! Will be deleted after destruction of the atlas.
     * Set font_cfg.FontDataOwnedByAtlas=false to keep ownership of your data and it won't be freed.
     */
    public ImFont addFontFromMemoryTTF(final byte[] fontData, final float sizePixels, final ImFontConfig imFontConfig) {
        return new ImFont(nAddFontFromMemoryTTF(fontData, fontData.length, sizePixels, imFontConfig.ptr));
    }

    private native long nAddFontFromMemoryTTF(byte[] fontData, int fontSize, float sizePixels, long imFontConfigPtr); /*
        return (intptr_t)IM_FONT_ATLAS->AddFontFromMemoryTTF(&fontData[0], fontSize, sizePixels, (ImFontConfig*)imFontConfigPtr);
    */

    /**
     * Note: Transfer ownership of 'ttf_data' to ImFontAtlas! Will be deleted after destruction of the atlas.
     * Set font_cfg.FontDataOwnedByAtlas=false to keep ownership of your data and it won't be freed.
     */
    public ImFont addFontFromMemoryTTF(final byte[] fontData, final float sizePixels, final short[] glyphRanges) {
        return new ImFont(nAddFontFromMemoryTTF(fontData, fontData.length, sizePixels, glyphRanges));
    }

    private native long nAddFontFromMemoryTTF(byte[] fontData, int fontSize, float sizePixels, short[] glyphRanges); /*
        return (intptr_t)IM_FONT_ATLAS->AddFontFromMemoryTTF(&fontData[0], fontSize, sizePixels, NULL, (ImWchar*)&glyphRanges[0]);
    */

    /**
     * Note: Transfer ownership of 'ttf_data' to ImFontAtlas! Will be deleted after destruction of the atlas.
     * Set font_cfg.FontDataOwnedByAtlas=false to keep ownership of your data and it won't be freed.
     */
    public ImFont addFontFromMemoryTTF(final byte[] fontData, final float sizePixels, final ImFontConfig imFontConfig, final short[] glyphRanges) {
        return new ImFont(nAddFontFromMemoryTTF(fontData, fontData.length, sizePixels, imFontConfig.ptr, glyphRanges));
    }

    private native long nAddFontFromMemoryTTF(byte[] fontData, int fontSize, float sizePixels, long imFontConfigPtr, short[] glyphRanges); /*
        return (intptr_t)IM_FONT_ATLAS->AddFontFromMemoryTTF(&fontData[0], fontSize, sizePixels, (ImFontConfig*)imFontConfigPtr, (ImWchar*)&glyphRanges[0]);
    */

    /**
     * 'compressed_font_data' still owned by caller. Compress with binary_to_compressed_c.cpp.
     */
    public ImFont addFontFromMemoryCompressedTTF(final byte[] compressedFontData, final float sizePixels) {
        return new ImFont(nAddFontFromMemoryCompressedTTF(compressedFontData, compressedFontData.length, sizePixels));
    }

    private native long nAddFontFromMemoryCompressedTTF(byte[] compressedFontData, int fontSize, float sizePixels); /*
        return (intptr_t)IM_FONT_ATLAS->AddFontFromMemoryCompressedTTF(&compressedFontData[0], fontSize, sizePixels);
    */

    /**
     * 'compressed_font_data' still owned by caller. Compress with binary_to_compressed_c.cpp.
     */
    public ImFont addFontFromMemoryCompressedTTF(final byte[] compressedFontData, final float sizePixels, final ImFontConfig imFontConfig) {
        return new ImFont(nAddFontFromMemoryCompressedTTF(compressedFontData, compressedFontData.length, sizePixels, imFontConfig.ptr));
    }

    private native long nAddFontFromMemoryCompressedTTF(byte[] compressedFontData, int fontSize, float sizePixels, long imFontConfigPtr); /*
        return (intptr_t)IM_FONT_ATLAS->AddFontFromMemoryCompressedTTF(&compressedFontData[0], fontSize, sizePixels, (ImFontConfig*)imFontConfigPtr);
    */

    /**
     * 'compressed_font_data' still owned by caller. Compress with binary_to_compressed_c.cpp.
     */
    public ImFont addFontFromMemoryCompressedTTF(final byte[] compressedFontData, final float sizePixels, final short[] glyphRanges) {
        return new ImFont(nAddFontFromMemoryCompressedTTF(compressedFontData, compressedFontData.length, sizePixels, glyphRanges));
    }

    private native long nAddFontFromMemoryCompressedTTF(byte[] compressedFontData, int fontSize, float sizePixels, short[] glyphRanges); /*
        return (intptr_t)IM_FONT_ATLAS->AddFontFromMemoryCompressedTTF(&compressedFontData[0], fontSize, sizePixels, NULL, (ImWchar*)&glyphRanges[0]);
    */

    /**
     * 'compressed_font_data' still owned by caller. Compress with binary_to_compressed_c.cpp.
     */
    public ImFont addFontFromMemoryCompressedTTF(final byte[] compressedFontData, final float sizePixels, final ImFontConfig imFontConfig, final short[] glyphRanges) {
        return new ImFont(nAddFontFromMemoryCompressedTTF(compressedFontData, compressedFontData.length, sizePixels, imFontConfig.ptr, glyphRanges));
    }

    private native long nAddFontFromMemoryCompressedTTF(byte[] compressedFontData, int fontSize, float sizePixels, long imFontConfigPtr, short[] glyphRanges); /*
        return (intptr_t)IM_FONT_ATLAS->AddFontFromMemoryCompressedTTF(&compressedFontData[0], fontSize, sizePixels, (ImFontConfig*)imFontConfigPtr, (ImWchar*)&glyphRanges[0]);
    */

    /**
     * 'compressed_font_data_base85' still owned by caller. Compress with binary_to_compressed_c.cpp with -base85 parameter.
     */
    public ImFont addFontFromMemoryCompressedBase85TTF(final String compressedFontDataBase85, final float sizePixels) {
        return new ImFont(nAddFontFromMemoryCompressedBase85TTF(compressedFontDataBase85, sizePixels));
    }

    private native long nAddFontFromMemoryCompressedBase85TTF(String compressedFontDataBase85, float sizePixels); /*
        return (intptr_t)IM_FONT_ATLAS->AddFontFromMemoryCompressedBase85TTF(compressedFontDataBase85, sizePixels);
    */

    /**
     * 'compressed_font_data' still owned by caller. Compress with binary_to_compressed_c.cpp.
     */
    public ImFont addFontFromMemoryCompressedBase85TTF(final String compressedFontDataBase85, final float sizePixels, final ImFontConfig imFontConfig) {
        return new ImFont(nAddFontFromMemoryCompressedBase85TTF(compressedFontDataBase85, sizePixels, imFontConfig.ptr));
    }

    private native long nAddFontFromMemoryCompressedBase85TTF(String compressedFontDataBase85, float sizePixels, long imFontConfigPtr); /*
        return (intptr_t)IM_FONT_ATLAS->AddFontFromMemoryCompressedBase85TTF(compressedFontDataBase85, sizePixels, (ImFontConfig*)imFontConfigPtr);
    */

    /**
     * 'compressed_font_data_base85' still owned by caller. Compress with binary_to_compressed_c.cpp with -base85 parameter.
     */
    public ImFont addFontFromMemoryCompressedBase85TTF(final String compressedFontDataBase85, final float sizePixels, final short[] glyphRanges) {
        return new ImFont(nAddFontFromMemoryCompressedBase85TTF(compressedFontDataBase85, sizePixels, glyphRanges));
    }

    private native long nAddFontFromMemoryCompressedBase85TTF(String compressedFontDataBase85, float sizePixels, short[] glyphRanges); /*
        return (intptr_t)IM_FONT_ATLAS->AddFontFromMemoryCompressedBase85TTF(compressedFontDataBase85, sizePixels, NULL, (ImWchar*)&glyphRanges[0]);
    */

    /**
     * 'compressed_font_data_base85' still owned by caller. Compress with binary_to_compressed_c.cpp with -base85 parameter.
     */
    public ImFont addFontFromMemoryCompressedBase85TTF(final String compressedFontDataBase85, final float sizePixels, final ImFontConfig imFontConfig, final short[] glyphRanges) {
        return new ImFont(nAddFontFromMemoryCompressedBase85TTF(compressedFontDataBase85, sizePixels, imFontConfig.ptr, glyphRanges));
    }

    private native long nAddFontFromMemoryCompressedBase85TTF(String compressedFontDataBase85, float sizePixels, long imFontConfigPtr, short[] glyphRanges); /*
        return (intptr_t)IM_FONT_ATLAS->AddFontFromMemoryCompressedBase85TTF(compressedFontDataBase85, sizePixels, (ImFontConfig*)imFontConfigPtr, (ImWchar*)&glyphRanges[0]);
    */

    /**
     * Clear input data (all ImFontConfig structures including sizes, TTF data, glyph ranges, etc.) = all the data used to build the texture and fonts.
     */
    public native void clearInputData(); /*
        IM_FONT_ATLAS->ClearInputData();
    */

    /**
     * Clear output texture data (CPU side). Saves RAM once the texture has been copied to graphics memory.
     */
    public native void clearTexData(); /*
        IM_FONT_ATLAS->ClearTexData();
    */

    /**
     * Clear output font data (glyphs storage, UV coordinates).
     */
    public native void clearFonts(); /*
        IM_FONT_ATLAS->ClearFonts();
    */

    /**
     * Clear all input and output.
     */
    public native void clear(); /*
        IM_FONT_ATLAS->Clear();
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
        getTexDataAsAlpha8(outWidth.getData(), outHeight.getData(), outBytesPerPixel.getData());
        return alpha8pixels;
    }

    private ByteBuffer createAlpha8Pixels(final int size) {
        if (alpha8pixels == null || alpha8pixels.limit() != size) {
            alpha8pixels = ByteBuffer.allocateDirect(size).order(ByteOrder.nativeOrder());
        } else {
            alpha8pixels.clear();
        }

        return alpha8pixels;
    }

    private native void getTexDataAsAlpha8(int[] outWidth, int[] outHeight, int[] outBytesPerPixel); /*
        unsigned char* pixels;
        IM_FONT_ATLAS->GetTexDataAsAlpha8(&pixels, &outWidth[0], &outHeight[0], &outBytesPerPixel[0]);

        int size = outWidth[0] * outHeight[0] * outBytesPerPixel[0];

        jobject jBuffer = env->CallObjectMethod(object, jImFontAtlasCreateAlpha8PixelsMID, size);
        char* buffer = (char*)env->GetDirectBufferAddress(jBuffer);

        memcpy(buffer, pixels, size);
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
        nGetTexDataAsRGBA32(outWidth.getData(), outHeight.getData(), outBytesPerPixel.getData());
        return rgba32pixels;
    }

    private ByteBuffer createRgba32Pixels(final int size) {
        if (rgba32pixels == null || rgba32pixels.limit() != size) {
            rgba32pixels = ByteBuffer.allocateDirect(size).order(ByteOrder.nativeOrder());
        } else {
            rgba32pixels.clear();
        }

        return rgba32pixels;
    }

    private native void nGetTexDataAsRGBA32(int[] outWidth, int[] outHeight, int[] outBytesPerPixel); /*
        unsigned char* pixels;
        IM_FONT_ATLAS->GetTexDataAsRGBA32(&pixels, &outWidth[0], &outHeight[0], &outBytesPerPixel[0]);

        int size = outWidth[0] * outHeight[0] * outBytesPerPixel[0];

        jobject jBuffer = env->CallObjectMethod(object, jImFontAtlasCreateRgba32PixelsMID, size);
        char* buffer = (char*)env->GetDirectBufferAddress(jBuffer);

        memcpy(buffer, pixels, size);
    */

    public native boolean isBuilt(); /*
        return IM_FONT_ATLAS->IsBuilt();
    */

    public native void setTexID(int textureID); /*
        IM_FONT_ATLAS->SetTexID((ImTextureID)(intptr_t)textureID);
    */

    //-------------------------------------------
    // Glyph Ranges
    //-------------------------------------------

    // Helpers to retrieve list of common Unicode ranges (2 value per range, values are inclusive, zero-terminated list)
    // NB: Make sure that your string are UTF-8 and NOT in your local code page. In C++11, you can create UTF-8 string literal using the u8"Hello world" syntax. See FAQ for details.
    // NB: Consider using ImFontGlyphRangesBuilder to build glyph ranges from textual data.

    /*JNI
        #define RETURN_GLYPH_2_SHORT(glyphs) \
            int size = sizeof(glyphs); \
            jshortArray jShorts = env->NewShortArray(size); \
            env->SetShortArrayRegion(jShorts, 0, size, (jshort*)glyphs); \
            return jShorts;
     */

    /**
     * Basic Latin, Extended Latin
     */
    public native short[] getGlyphRangesDefault(); /*
        RETURN_GLYPH_2_SHORT(IM_FONT_ATLAS->GetGlyphRangesDefault());
    */

    /**
     * Default + Korean characters
     */
    public native short[] getGlyphRangesKorean(); /*
        RETURN_GLYPH_2_SHORT(IM_FONT_ATLAS->GetGlyphRangesKorean());
    */

    /**
     * Default + Hiragana, Katakana, Half-Width, Selection of 2999 Ideographs
     */
    public native short[] getGlyphRangesJapanese(); /*
        RETURN_GLYPH_2_SHORT(IM_FONT_ATLAS->GetGlyphRangesJapanese());
    */

    /**
     * Default + Half-Width + Japanese Hiragana/Katakana + full set of about 21000 CJK Unified Ideographs
     */
    public native short[] getGlyphRangesChineseFull(); /*
        RETURN_GLYPH_2_SHORT(IM_FONT_ATLAS->GetGlyphRangesChineseFull());
    */

    /**
     * Default + Half-Width + Japanese Hiragana/Katakana + set of 2500 CJK Unified Ideographs for common simplified Chinese
     */
    public native short[] getGlyphRangesChineseSimplifiedCommon(); /*
        RETURN_GLYPH_2_SHORT(IM_FONT_ATLAS->GetGlyphRangesChineseSimplifiedCommon());
    */

    /**
     * Default + about 400 Cyrillic characters
     */
    public native short[] getGlyphRangesCyrillic(); /*
        RETURN_GLYPH_2_SHORT(IM_FONT_ATLAS->GetGlyphRangesCyrillic());
    */

    /**
     * Default + Thai characters
     */
    public native short[] getGlyphRangesThai(); /*
        RETURN_GLYPH_2_SHORT(IM_FONT_ATLAS->GetGlyphRangesThai());
    */

    /**
     * Default + Vietnamese characters
     */
    public native short[] getGlyphRangesVietnamese(); /*
        RETURN_GLYPH_2_SHORT(IM_FONT_ATLAS->GetGlyphRangesVietnamese());
    */

    //-------------------------------------------
    // [BETA] Custom Rectangles/Glyphs API
    //-------------------------------------------

    // You can request arbitrary rectangles to be packed into the atlas, for your own purposes.
    // After calling Build(), you can query the rectangle position and render your pixels.
    // You can also request your rectangles to be mapped as font glyph (given a font + Unicode point),
    // so you can render e.g. custom colorful icons and use them as regular glyphs.
    // Read docs/FONTS.md for more details about using colorful icons.
    // Note: this API may be redesigned later in order to support multi-monitor varying DPI settings.

    public native int addCustomRectRegular(int width, int height); /*
        return IM_FONT_ATLAS->AddCustomRectRegular(width, height);
    */

    public int addCustomRectFontGlyph(final ImFont imFont, final short id, final int width, final int height, final float advanceX) {
        return nAddCustomRectFontGlyph(imFont.ptr, id, width, height, advanceX);
    }

    private native int nAddCustomRectFontGlyph(long imFontPtr, short id, int width, int height, float advanceX); /*
        return IM_FONT_ATLAS->AddCustomRectFontGlyph((ImFont*)imFontPtr, id, width, height, advanceX);
    */

    /**
     * Id needs to be {@code <} 0x110000 to register a rectangle to map into a specific font.
     */
    public int addCustomRectFontGlyph(final ImFont imFont, final short id, final int width, final int height, final float advanceX, final float offsetX, final float offsetY) {
        return nAddCustomRectFontGlyph(imFont.ptr, id, width, height, advanceX, offsetX, offsetY);
    }

    private native int nAddCustomRectFontGlyph(long imFontPtr, short id, int width, int height, float advanceX, float offsetX, float offsetY); /*
        return IM_FONT_ATLAS->AddCustomRectFontGlyph((ImFont*)imFontPtr, id, width, height, advanceX, ImVec2(offsetX, offsetY));
    */

    // TODO GetCustomRectByIndex

    //-------------------------------------------
    // Members
    //-------------------------------------------

    /**
     * Marked as Locked by ImGui::NewFrame() so attempt to modify the atlas will assert.
     */
    public native boolean getLocked(); /*
        return IM_FONT_ATLAS->Locked;
    */

    /**
     * Marked as Locked by ImGui::NewFrame() so attempt to modify the atlas will assert.
     */
    public native void setLocked(boolean locked); /*
        IM_FONT_ATLAS->Locked = locked;
    */

    /**
     * Build flags (see {@link imgui.flag.ImFontAtlasFlags})
     */
    public native int getFlags(); /*
        return IM_FONT_ATLAS->Flags;
    */

    /**
     * Build flags (see {@link imgui.flag.ImFontAtlasFlags})
     */
    public native void setFlags(int imFontAtlasFlags); /*
        IM_FONT_ATLAS->Flags = imFontAtlasFlags;
    */

    /**
     * Build flags (see {@link imgui.flag.ImFontAtlasFlags})
     */
    public void addFlags(final int flags) {
        setFlags(getFlags() | flags);
    }

    /**
     * Build flags (see {@link imgui.flag.ImFontAtlasFlags})
     */
    public void removeFlags(final int flags) {
        setFlags(getFlags() & ~(flags));
    }

    /**
     * Build flags (see {@link imgui.flag.ImFontAtlasFlags})
     */
    public boolean hasFlags(final int flags) {
        return (getFlags() & flags) != 0;
    }

    /**
     * User data to refer to the texture once it has been uploaded to user's graphic systems.
     * It is passed back to you during rendering via the ImDrawCmd structure.
     */
    public native int getTexID(); /*
        return (int)(intptr_t)(void*)IM_FONT_ATLAS->TexID;
    */

    /**
     * Texture width desired by user before Build(). Must be a power-of-two.
     * If have many glyphs your graphics API have texture size restrictions you may want to increase texture width to decrease height.
     */
    public native int getTexDesiredWidth(); /*
        return IM_FONT_ATLAS->TexDesiredWidth;
    */

    /**
     * Texture width desired by user before Build(). Must be a power-of-two.
     * If have many glyphs your graphics API have texture size restrictions you may want to increase texture width to decrease height.
     */
    public native void setTexDesiredWidth(int texDesiredWidth); /*
        IM_FONT_ATLAS->TexDesiredWidth = texDesiredWidth;
    */

    /**
     * Padding between glyphs within texture in pixels. Defaults to 1.
     * If your rendering method doesn't rely on bilinear filtering you may set this to 0.
     */
    public native int getTexGlyphPadding(); /*
        return IM_FONT_ATLAS->TexGlyphPadding;
    */

    /**
     * Padding between glyphs within texture in pixels. Defaults to 1.
     * If your rendering method doesn't rely on bilinear filtering you may set this to 0.
     */
    public native void setTexGlyphPadding(int texGlyphPadding); /*
        IM_FONT_ATLAS->TexGlyphPadding = texGlyphPadding;
    */
}
