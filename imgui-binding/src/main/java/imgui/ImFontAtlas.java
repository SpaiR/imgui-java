package imgui;

import imgui.binding.ImGuiStructDestroyable;
import imgui.binding.annotation.ArgValue;
import imgui.binding.annotation.BindingField;
import imgui.binding.annotation.BindingMethod;
import imgui.binding.annotation.BindingSource;
import imgui.binding.annotation.OptArg;
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
@BindingSource
public final class ImFontAtlas extends ImGuiStructDestroyable {
    private ByteBuffer alpha8pixels = null;
    private ByteBuffer rgba32pixels = null;

    public ImFontAtlas() {
        super();
    }

    public ImFontAtlas(final long ptr) {
        super(ptr);
    }

    @Override
    protected long create() {
        return nCreate();
    }

    /*JNI
        #include "_common.h"
        #define THIS ((ImFontAtlas*)STRUCT_PTR)

        jmethodID jImFontAtlasCreateAlpha8PixelsMID;
        jmethodID jImFontAtlasCreateRgba32PixelsMID;
     */

    static native void nInit(); /*
        jclass jImFontAtlasClass = env->FindClass("imgui/ImFontAtlas");
        jImFontAtlasCreateAlpha8PixelsMID = env->GetMethodID(jImFontAtlasClass, "createAlpha8Pixels", "(I)Ljava/nio/ByteBuffer;");
        jImFontAtlasCreateRgba32PixelsMID = env->GetMethodID(jImFontAtlasClass, "createRgba32Pixels", "(I)Ljava/nio/ByteBuffer;");
    */

    private native long nCreate(); /*
        return (intptr_t)(new ImFontConfig());
    */

    @BindingMethod
    public native ImFont AddFont(ImFontConfig imFontConfig);

    @BindingMethod
    public native ImFont AddFontDefault(@OptArg ImFontConfig imFontConfig);

    @BindingMethod
    public native ImFont AddFontFromFileTTF(String filename, float sizePixels, @OptArg(callValue = "NULL") ImFontConfig fontConfig, @OptArg @ArgValue(callPrefix = "(ImWchar*)") short[] glyphRanges);

    /**
     * Note: Transfer ownership of 'ttf_data' to ImFontAtlas! Will be deleted after destruction of the atlas.
     * Set font_cfg.FontDataOwnedByAtlas=false to keep ownership of your data and it won't be freed.
     */
    @BindingMethod
    public native ImFont AddFontFromMemoryTTF(byte[] fontData, @ArgValue(callValue = "(int)env->GetArrayLength(obj_fontData)") Void fontSize, float sizePixels, @OptArg(callValue = "NULL") ImFontConfig fontConfig, @OptArg @ArgValue(callPrefix = "(ImWchar*)") short[] glyphRanges);

    /**
     * Note: Transfer ownership of 'ttf_data' to ImFontAtlas! Will be deleted after destruction of the atlas.
     * Set font_cfg.FontDataOwnedByAtlas=false to keep ownership of your data and it won't be freed.
     */
    @BindingMethod
    public native ImFont AddFontFromMemoryTTF(byte[] fontData, int fontSize, float sizePixels, @OptArg(callValue = "NULL") ImFontConfig fontConfig, @OptArg @ArgValue(callPrefix = "(ImWchar*)") short[] glyphRanges);

    /**
     * 'compressed_font_data' still owned by caller. Compress with binary_to_compressed_c.cpp.
     */
    @BindingMethod
    public native ImFont AddFontFromMemoryCompressedTTF(byte[] compressedFontData, @ArgValue(callValue = "(int)env->GetArrayLength(obj_compressedFontData)") Void compressedFontSize, float sizePixels, @OptArg(callValue = "NULL") ImFontConfig imFontConfig, @OptArg @ArgValue(callPrefix = "(ImWchar*)") short[] glyphRanges);

    /**
     * 'compressed_font_data' still owned by caller. Compress with binary_to_compressed_c.cpp.
     */
    @BindingMethod
    public native ImFont AddFontFromMemoryCompressedTTF(byte[] compressedFontData, int compressedFontSize, float sizePixels, @OptArg(callValue = "NULL") ImFontConfig imFontConfig, @OptArg @ArgValue(callPrefix = "(ImWchar*)") short[] glyphRanges);

    /**
     * 'compressed_font_data_base85' still owned by caller. Compress with binary_to_compressed_c.cpp with -base85 parameter.
     */
    @BindingMethod
    public native ImFont AddFontFromMemoryCompressedBase85TTF(String compressedFontDataBase85, float sizePixels, ImFontConfig fontConfig, @OptArg @ArgValue(callPrefix = "(ImWchar*)") short[] glyphRanges);

    /**
     * Clear input data (all ImFontConfig structures including sizes, TTF data, glyph ranges, etc.) = all the data used to build the texture and fonts.
     */
    @BindingMethod
    public native void ClearInputData();

    /**
     * Clear output texture data (CPU side). Saves RAM once the texture has been copied to graphics memory.
     */
    @BindingMethod
    public native void ClearTexData();

    /**
     * Clear output font data (glyphs storage, UV coordinates).
     */
    @BindingMethod
    public native void ClearFonts();

    /**
     * Clear all input and output.
     */
    @BindingMethod
    public native void Clear();

    // Build atlas, retrieve pixel data.
    // User is in charge of copying the pixels into graphics memory (e.g. create a texture with your engine). Then store your texture handle with SetTexID().
    // The pitch is always = Width * BytesPerPixels (1 or 4)
    // Building in RGBA32 format is provided for convenience and compatibility, but note that unless you manually manipulate or copy color data into
    // the texture (e.g. when using the AddCustomRect*** api), then the RGB pixels emitted will always be white (~75% of memory/bandwidth waste.

    /**
     * Build pixels data. This is called automatically for you by the GetTexData*** functions.
     */
    @BindingMethod
    public native boolean Build();

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
        THIS->GetTexDataAsAlpha8(&pixels, &outWidth[0], &outHeight[0], &outBytesPerPixel[0]);
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
        THIS->GetTexDataAsRGBA32(&pixels, &outWidth[0], &outHeight[0], &outBytesPerPixel[0]);
        int size = outWidth[0] * outHeight[0] * outBytesPerPixel[0];
        jobject jBuffer = env->CallObjectMethod(object, jImFontAtlasCreateRgba32PixelsMID, size);
        char* buffer = (char*)env->GetDirectBufferAddress(jBuffer);
        memcpy(buffer, pixels, size);
    */

    @BindingMethod
    public native boolean IsBuilt();

    /**
     * User data to refer to the texture once it has been uploaded to user's graphic systems.
     * It is passed back to you during rendering via the ImDrawCmd structure.
     */
    @BindingMethod
    public native void SetTexID(@ArgValue(callPrefix = "(ImTextureID)(intptr_t)") int textureID);

    //-------------------------------------------
    // Glyph Ranges
    //-------------------------------------------

    // Helpers to retrieve list of common Unicode ranges (2 value per range, values are inclusive, zero-terminated list)
    // NB: Make sure that your string are UTF-8 and NOT in your local code page. In C++11, you can create UTF-8 string literal using the u8"Hello world" syntax. See FAQ for details.
    // NB: Consider using ImFontGlyphRangesBuilder to build glyph ranges from textual data.

    /*JNI
        #define RETURN_GLYPH_2_SHORT(glyphs) \
            const ImWchar* ranges = glyphs; \
            int size = 0; \
            for (; ranges[0]; ranges += 2) \
                size += 2; \
            jshortArray jShorts = env->NewShortArray(size); \
            env->SetShortArrayRegion(jShorts, 0, size, (jshort*)glyphs); \
            return jShorts;
     */

    /**
     * Basic Latin, Extended Latin
     */
    public native short[] getGlyphRangesDefault(); /*
        RETURN_GLYPH_2_SHORT(THIS->GetGlyphRangesDefault());
    */

    /**
     * Default + Korean characters
     */
    public native short[] getGlyphRangesKorean(); /*
        RETURN_GLYPH_2_SHORT(THIS->GetGlyphRangesKorean());
    */

    /**
     * Default + Hiragana, Katakana, Half-Width, Selection of 2999 Ideographs
     */
    public native short[] getGlyphRangesJapanese(); /*
        RETURN_GLYPH_2_SHORT(THIS->GetGlyphRangesJapanese());
    */

    /**
     * Default + Half-Width + Japanese Hiragana/Katakana + full set of about 21000 CJK Unified Ideographs
     */
    public native short[] getGlyphRangesChineseFull(); /*
        RETURN_GLYPH_2_SHORT(THIS->GetGlyphRangesChineseFull());
    */

    /**
     * Default + Half-Width + Japanese Hiragana/Katakana + set of 2500 CJK Unified Ideographs for common simplified Chinese
     */
    public native short[] getGlyphRangesChineseSimplifiedCommon(); /*
        RETURN_GLYPH_2_SHORT(THIS->GetGlyphRangesChineseSimplifiedCommon());
    */

    /**
     * Default + about 400 Cyrillic characters
     */
    public native short[] getGlyphRangesCyrillic(); /*
        RETURN_GLYPH_2_SHORT(THIS->GetGlyphRangesCyrillic());
    */

    /**
     * Default + Thai characters
     */
    public native short[] getGlyphRangesThai(); /*
        RETURN_GLYPH_2_SHORT(THIS->GetGlyphRangesThai());
    */

    /**
     * Default + Vietnamese characters
     */
    public native short[] getGlyphRangesVietnamese(); /*
        RETURN_GLYPH_2_SHORT(THIS->GetGlyphRangesVietnamese());
    */

    /*JNI
        #undef RETURN_GLYPH_2_SHORT
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

    @BindingMethod
    public native int AddCustomRectRegular(int width, int height);

    /**
     * Id needs to be {@code <} 0x110000 to register a rectangle to map into a specific font.
     */
    @BindingMethod
    public native int AddCustomRectFontGlyph(ImFont imFont, @ArgValue(callPrefix = "(ImWchar)") short id, int width, int height, float advanceX, @OptArg ImVec2 offset);

    // TODO GetCustomRectByIndex

    //-------------------------------------------
    // Members
    //-------------------------------------------

    /**
     * Build flags (see {@link imgui.flag.ImFontAtlasFlags})
     */
    @BindingField(isFlag = true)
    public int Flags;

    // TexID implemented as SetTexID function

    /**
     * Texture width desired by user before Build(). Must be a power-of-two.
     * If have many glyphs your graphics API have texture size restrictions you may want to increase texture width to decrease height.
     */
    @BindingField
    public int TexDesiredWidth;

    /**
     * Padding between glyphs within texture in pixels. Defaults to 1.
     * If your rendering method doesn't rely on bilinear filtering you may set this to 0.
     */
    @BindingField
    public int TexGlyphPadding;

    /**
     * Marked as Locked by ImGui::NewFrame() so attempt to modify the atlas will assert.
     */
    @BindingField
    public boolean Locked;

    /*JNI
        #undef THIS
     */
}
