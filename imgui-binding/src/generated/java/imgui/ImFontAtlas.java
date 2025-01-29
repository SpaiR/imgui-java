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
        return (uintptr_t)(new ImFontConfig());
    */

    public ImFont addFont(final ImFontConfig imFontConfig) {
        return new ImFont(nAddFont(imFontConfig.ptr));
    }

    private native long nAddFont(long imFontConfig); /*
        return (uintptr_t)THIS->AddFont(reinterpret_cast<ImFontConfig*>(imFontConfig));
    */

    public ImFont addFontDefault() {
        return new ImFont(nAddFontDefault());
    }

    public ImFont addFontDefault(final ImFontConfig imFontConfig) {
        return new ImFont(nAddFontDefault(imFontConfig.ptr));
    }

    private native long nAddFontDefault(); /*
        return (uintptr_t)THIS->AddFontDefault();
    */

    private native long nAddFontDefault(long imFontConfig); /*
        return (uintptr_t)THIS->AddFontDefault(reinterpret_cast<ImFontConfig*>(imFontConfig));
    */

    public ImFont addFontFromFileTTF(final String filename, final float sizePixels) {
        return new ImFont(nAddFontFromFileTTF(filename, sizePixels));
    }

    public ImFont addFontFromFileTTF(final String filename, final float sizePixels, final ImFontConfig fontConfig) {
        return new ImFont(nAddFontFromFileTTF(filename, sizePixels, fontConfig.ptr));
    }

    public ImFont addFontFromFileTTF(final String filename, final float sizePixels, final ImFontConfig fontConfig, final short[] glyphRanges) {
        return new ImFont(nAddFontFromFileTTF(filename, sizePixels, fontConfig.ptr, glyphRanges));
    }

    public ImFont addFontFromFileTTF(final String filename, final float sizePixels, final short[] glyphRanges) {
        return new ImFont(nAddFontFromFileTTF(filename, sizePixels, glyphRanges));
    }

    private native long nAddFontFromFileTTF(String obj_filename, float sizePixels); /*MANUAL
        auto filename = obj_filename == NULL ? NULL : (char*)env->GetStringUTFChars(obj_filename, JNI_FALSE);
        auto _result = (uintptr_t)THIS->AddFontFromFileTTF(filename, sizePixels);
        if (filename != NULL) env->ReleaseStringUTFChars(obj_filename, filename);
        return _result;
    */

    private native long nAddFontFromFileTTF(String obj_filename, float sizePixels, long fontConfig); /*MANUAL
        auto filename = obj_filename == NULL ? NULL : (char*)env->GetStringUTFChars(obj_filename, JNI_FALSE);
        auto _result = (uintptr_t)THIS->AddFontFromFileTTF(filename, sizePixels, reinterpret_cast<ImFontConfig*>(fontConfig));
        if (filename != NULL) env->ReleaseStringUTFChars(obj_filename, filename);
        return _result;
    */

    private native long nAddFontFromFileTTF(String obj_filename, float sizePixels, long fontConfig, short[] obj_glyphRanges); /*MANUAL
        auto filename = obj_filename == NULL ? NULL : (char*)env->GetStringUTFChars(obj_filename, JNI_FALSE);
        auto glyphRanges = obj_glyphRanges == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_glyphRanges, JNI_FALSE);
        auto _result = (uintptr_t)THIS->AddFontFromFileTTF(filename, sizePixels, reinterpret_cast<ImFontConfig*>(fontConfig), (ImWchar*)&glyphRanges[0]);
        if (filename != NULL) env->ReleaseStringUTFChars(obj_filename, filename);
        if (glyphRanges != NULL) env->ReleasePrimitiveArrayCritical(obj_glyphRanges, glyphRanges, JNI_FALSE);
        return _result;
    */

    private native long nAddFontFromFileTTF(String obj_filename, float sizePixels, short[] obj_glyphRanges); /*MANUAL
        auto filename = obj_filename == NULL ? NULL : (char*)env->GetStringUTFChars(obj_filename, JNI_FALSE);
        auto glyphRanges = obj_glyphRanges == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_glyphRanges, JNI_FALSE);
        auto _result = (uintptr_t)THIS->AddFontFromFileTTF(filename, sizePixels, NULL, (ImWchar*)&glyphRanges[0]);
        if (filename != NULL) env->ReleaseStringUTFChars(obj_filename, filename);
        if (glyphRanges != NULL) env->ReleasePrimitiveArrayCritical(obj_glyphRanges, glyphRanges, JNI_FALSE);
        return _result;
    */

    /**
     * Note: Transfer ownership of 'ttf_data' to ImFontAtlas! Will be deleted after destruction of the atlas.
     * Set font_cfg.FontDataOwnedByAtlas=false to keep ownership of your data and it won't be freed.
     */
    public ImFont addFontFromMemoryTTF(final byte[] fontData, final float sizePixels) {
        return new ImFont(nAddFontFromMemoryTTF(fontData, sizePixels));
    }

    /**
     * Note: Transfer ownership of 'ttf_data' to ImFontAtlas! Will be deleted after destruction of the atlas.
     * Set font_cfg.FontDataOwnedByAtlas=false to keep ownership of your data and it won't be freed.
     */
    public ImFont addFontFromMemoryTTF(final byte[] fontData, final float sizePixels, final ImFontConfig fontConfig) {
        return new ImFont(nAddFontFromMemoryTTF(fontData, sizePixels, fontConfig.ptr));
    }

    /**
     * Note: Transfer ownership of 'ttf_data' to ImFontAtlas! Will be deleted after destruction of the atlas.
     * Set font_cfg.FontDataOwnedByAtlas=false to keep ownership of your data and it won't be freed.
     */
    public ImFont addFontFromMemoryTTF(final byte[] fontData, final float sizePixels, final ImFontConfig fontConfig, final short[] glyphRanges) {
        return new ImFont(nAddFontFromMemoryTTF(fontData, sizePixels, fontConfig.ptr, glyphRanges));
    }

    /**
     * Note: Transfer ownership of 'ttf_data' to ImFontAtlas! Will be deleted after destruction of the atlas.
     * Set font_cfg.FontDataOwnedByAtlas=false to keep ownership of your data and it won't be freed.
     */
    public ImFont addFontFromMemoryTTF(final byte[] fontData, final float sizePixels, final short[] glyphRanges) {
        return new ImFont(nAddFontFromMemoryTTF(fontData, sizePixels, glyphRanges));
    }

    private native long nAddFontFromMemoryTTF(byte[] obj_fontData, float sizePixels); /*MANUAL
        auto fontData = obj_fontData == NULL ? NULL : (char*)env->GetPrimitiveArrayCritical(obj_fontData, JNI_FALSE);
        auto _result = (uintptr_t)THIS->AddFontFromMemoryTTF(&fontData[0], (int)env->GetArrayLength(obj_fontData), sizePixels);
        if (fontData != NULL) env->ReleasePrimitiveArrayCritical(obj_fontData, fontData, JNI_FALSE);
        return _result;
    */

    private native long nAddFontFromMemoryTTF(byte[] obj_fontData, float sizePixels, long fontConfig); /*MANUAL
        auto fontData = obj_fontData == NULL ? NULL : (char*)env->GetPrimitiveArrayCritical(obj_fontData, JNI_FALSE);
        auto _result = (uintptr_t)THIS->AddFontFromMemoryTTF(&fontData[0], (int)env->GetArrayLength(obj_fontData), sizePixels, reinterpret_cast<ImFontConfig*>(fontConfig));
        if (fontData != NULL) env->ReleasePrimitiveArrayCritical(obj_fontData, fontData, JNI_FALSE);
        return _result;
    */

    private native long nAddFontFromMemoryTTF(byte[] obj_fontData, float sizePixels, long fontConfig, short[] obj_glyphRanges); /*MANUAL
        auto fontData = obj_fontData == NULL ? NULL : (char*)env->GetPrimitiveArrayCritical(obj_fontData, JNI_FALSE);
        auto glyphRanges = obj_glyphRanges == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_glyphRanges, JNI_FALSE);
        auto _result = (uintptr_t)THIS->AddFontFromMemoryTTF(&fontData[0], (int)env->GetArrayLength(obj_fontData), sizePixels, reinterpret_cast<ImFontConfig*>(fontConfig), (ImWchar*)&glyphRanges[0]);
        if (fontData != NULL) env->ReleasePrimitiveArrayCritical(obj_fontData, fontData, JNI_FALSE);
        if (glyphRanges != NULL) env->ReleasePrimitiveArrayCritical(obj_glyphRanges, glyphRanges, JNI_FALSE);
        return _result;
    */

    private native long nAddFontFromMemoryTTF(byte[] obj_fontData, float sizePixels, short[] obj_glyphRanges); /*MANUAL
        auto fontData = obj_fontData == NULL ? NULL : (char*)env->GetPrimitiveArrayCritical(obj_fontData, JNI_FALSE);
        auto glyphRanges = obj_glyphRanges == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_glyphRanges, JNI_FALSE);
        auto _result = (uintptr_t)THIS->AddFontFromMemoryTTF(&fontData[0], (int)env->GetArrayLength(obj_fontData), sizePixels, NULL, (ImWchar*)&glyphRanges[0]);
        if (fontData != NULL) env->ReleasePrimitiveArrayCritical(obj_fontData, fontData, JNI_FALSE);
        if (glyphRanges != NULL) env->ReleasePrimitiveArrayCritical(obj_glyphRanges, glyphRanges, JNI_FALSE);
        return _result;
    */

    /**
     * Note: Transfer ownership of 'ttf_data' to ImFontAtlas! Will be deleted after destruction of the atlas.
     * Set font_cfg.FontDataOwnedByAtlas=false to keep ownership of your data and it won't be freed.
     */
    public ImFont addFontFromMemoryTTF(final byte[] fontData, final int fontSize, final float sizePixels) {
        return new ImFont(nAddFontFromMemoryTTF(fontData, fontSize, sizePixels));
    }

    /**
     * Note: Transfer ownership of 'ttf_data' to ImFontAtlas! Will be deleted after destruction of the atlas.
     * Set font_cfg.FontDataOwnedByAtlas=false to keep ownership of your data and it won't be freed.
     */
    public ImFont addFontFromMemoryTTF(final byte[] fontData, final int fontSize, final float sizePixels, final ImFontConfig fontConfig) {
        return new ImFont(nAddFontFromMemoryTTF(fontData, fontSize, sizePixels, fontConfig.ptr));
    }

    /**
     * Note: Transfer ownership of 'ttf_data' to ImFontAtlas! Will be deleted after destruction of the atlas.
     * Set font_cfg.FontDataOwnedByAtlas=false to keep ownership of your data and it won't be freed.
     */
    public ImFont addFontFromMemoryTTF(final byte[] fontData, final int fontSize, final float sizePixels, final ImFontConfig fontConfig, final short[] glyphRanges) {
        return new ImFont(nAddFontFromMemoryTTF(fontData, fontSize, sizePixels, fontConfig.ptr, glyphRanges));
    }

    /**
     * Note: Transfer ownership of 'ttf_data' to ImFontAtlas! Will be deleted after destruction of the atlas.
     * Set font_cfg.FontDataOwnedByAtlas=false to keep ownership of your data and it won't be freed.
     */
    public ImFont addFontFromMemoryTTF(final byte[] fontData, final int fontSize, final float sizePixels, final short[] glyphRanges) {
        return new ImFont(nAddFontFromMemoryTTF(fontData, fontSize, sizePixels, glyphRanges));
    }

    private native long nAddFontFromMemoryTTF(byte[] obj_fontData, int fontSize, float sizePixels); /*MANUAL
        auto fontData = obj_fontData == NULL ? NULL : (char*)env->GetPrimitiveArrayCritical(obj_fontData, JNI_FALSE);
        auto _result = (uintptr_t)THIS->AddFontFromMemoryTTF(&fontData[0], fontSize, sizePixels);
        if (fontData != NULL) env->ReleasePrimitiveArrayCritical(obj_fontData, fontData, JNI_FALSE);
        return _result;
    */

    private native long nAddFontFromMemoryTTF(byte[] obj_fontData, int fontSize, float sizePixels, long fontConfig); /*MANUAL
        auto fontData = obj_fontData == NULL ? NULL : (char*)env->GetPrimitiveArrayCritical(obj_fontData, JNI_FALSE);
        auto _result = (uintptr_t)THIS->AddFontFromMemoryTTF(&fontData[0], fontSize, sizePixels, reinterpret_cast<ImFontConfig*>(fontConfig));
        if (fontData != NULL) env->ReleasePrimitiveArrayCritical(obj_fontData, fontData, JNI_FALSE);
        return _result;
    */

    private native long nAddFontFromMemoryTTF(byte[] obj_fontData, int fontSize, float sizePixels, long fontConfig, short[] obj_glyphRanges); /*MANUAL
        auto fontData = obj_fontData == NULL ? NULL : (char*)env->GetPrimitiveArrayCritical(obj_fontData, JNI_FALSE);
        auto glyphRanges = obj_glyphRanges == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_glyphRanges, JNI_FALSE);
        auto _result = (uintptr_t)THIS->AddFontFromMemoryTTF(&fontData[0], fontSize, sizePixels, reinterpret_cast<ImFontConfig*>(fontConfig), (ImWchar*)&glyphRanges[0]);
        if (fontData != NULL) env->ReleasePrimitiveArrayCritical(obj_fontData, fontData, JNI_FALSE);
        if (glyphRanges != NULL) env->ReleasePrimitiveArrayCritical(obj_glyphRanges, glyphRanges, JNI_FALSE);
        return _result;
    */

    private native long nAddFontFromMemoryTTF(byte[] obj_fontData, int fontSize, float sizePixels, short[] obj_glyphRanges); /*MANUAL
        auto fontData = obj_fontData == NULL ? NULL : (char*)env->GetPrimitiveArrayCritical(obj_fontData, JNI_FALSE);
        auto glyphRanges = obj_glyphRanges == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_glyphRanges, JNI_FALSE);
        auto _result = (uintptr_t)THIS->AddFontFromMemoryTTF(&fontData[0], fontSize, sizePixels, NULL, (ImWchar*)&glyphRanges[0]);
        if (fontData != NULL) env->ReleasePrimitiveArrayCritical(obj_fontData, fontData, JNI_FALSE);
        if (glyphRanges != NULL) env->ReleasePrimitiveArrayCritical(obj_glyphRanges, glyphRanges, JNI_FALSE);
        return _result;
    */

    /**
     * 'compressed_font_data' still owned by caller. Compress with binary_to_compressed_c.cpp.
     */
    public ImFont addFontFromMemoryCompressedTTF(final byte[] compressedFontData, final float sizePixels) {
        return new ImFont(nAddFontFromMemoryCompressedTTF(compressedFontData, sizePixels));
    }

    /**
     * 'compressed_font_data' still owned by caller. Compress with binary_to_compressed_c.cpp.
     */
    public ImFont addFontFromMemoryCompressedTTF(final byte[] compressedFontData, final float sizePixels, final ImFontConfig imFontConfig) {
        return new ImFont(nAddFontFromMemoryCompressedTTF(compressedFontData, sizePixels, imFontConfig.ptr));
    }

    /**
     * 'compressed_font_data' still owned by caller. Compress with binary_to_compressed_c.cpp.
     */
    public ImFont addFontFromMemoryCompressedTTF(final byte[] compressedFontData, final float sizePixels, final ImFontConfig imFontConfig, final short[] glyphRanges) {
        return new ImFont(nAddFontFromMemoryCompressedTTF(compressedFontData, sizePixels, imFontConfig.ptr, glyphRanges));
    }

    /**
     * 'compressed_font_data' still owned by caller. Compress with binary_to_compressed_c.cpp.
     */
    public ImFont addFontFromMemoryCompressedTTF(final byte[] compressedFontData, final float sizePixels, final short[] glyphRanges) {
        return new ImFont(nAddFontFromMemoryCompressedTTF(compressedFontData, sizePixels, glyphRanges));
    }

    private native long nAddFontFromMemoryCompressedTTF(byte[] obj_compressedFontData, float sizePixels); /*MANUAL
        auto compressedFontData = obj_compressedFontData == NULL ? NULL : (char*)env->GetPrimitiveArrayCritical(obj_compressedFontData, JNI_FALSE);
        auto _result = (uintptr_t)THIS->AddFontFromMemoryCompressedTTF(&compressedFontData[0], (int)env->GetArrayLength(obj_compressedFontData), sizePixels);
        if (compressedFontData != NULL) env->ReleasePrimitiveArrayCritical(obj_compressedFontData, compressedFontData, JNI_FALSE);
        return _result;
    */

    private native long nAddFontFromMemoryCompressedTTF(byte[] obj_compressedFontData, float sizePixels, long imFontConfig); /*MANUAL
        auto compressedFontData = obj_compressedFontData == NULL ? NULL : (char*)env->GetPrimitiveArrayCritical(obj_compressedFontData, JNI_FALSE);
        auto _result = (uintptr_t)THIS->AddFontFromMemoryCompressedTTF(&compressedFontData[0], (int)env->GetArrayLength(obj_compressedFontData), sizePixels, reinterpret_cast<ImFontConfig*>(imFontConfig));
        if (compressedFontData != NULL) env->ReleasePrimitiveArrayCritical(obj_compressedFontData, compressedFontData, JNI_FALSE);
        return _result;
    */

    private native long nAddFontFromMemoryCompressedTTF(byte[] obj_compressedFontData, float sizePixels, long imFontConfig, short[] obj_glyphRanges); /*MANUAL
        auto compressedFontData = obj_compressedFontData == NULL ? NULL : (char*)env->GetPrimitiveArrayCritical(obj_compressedFontData, JNI_FALSE);
        auto glyphRanges = obj_glyphRanges == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_glyphRanges, JNI_FALSE);
        auto _result = (uintptr_t)THIS->AddFontFromMemoryCompressedTTF(&compressedFontData[0], (int)env->GetArrayLength(obj_compressedFontData), sizePixels, reinterpret_cast<ImFontConfig*>(imFontConfig), (ImWchar*)&glyphRanges[0]);
        if (compressedFontData != NULL) env->ReleasePrimitiveArrayCritical(obj_compressedFontData, compressedFontData, JNI_FALSE);
        if (glyphRanges != NULL) env->ReleasePrimitiveArrayCritical(obj_glyphRanges, glyphRanges, JNI_FALSE);
        return _result;
    */

    private native long nAddFontFromMemoryCompressedTTF(byte[] obj_compressedFontData, float sizePixels, short[] obj_glyphRanges); /*MANUAL
        auto compressedFontData = obj_compressedFontData == NULL ? NULL : (char*)env->GetPrimitiveArrayCritical(obj_compressedFontData, JNI_FALSE);
        auto glyphRanges = obj_glyphRanges == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_glyphRanges, JNI_FALSE);
        auto _result = (uintptr_t)THIS->AddFontFromMemoryCompressedTTF(&compressedFontData[0], (int)env->GetArrayLength(obj_compressedFontData), sizePixels, NULL, (ImWchar*)&glyphRanges[0]);
        if (compressedFontData != NULL) env->ReleasePrimitiveArrayCritical(obj_compressedFontData, compressedFontData, JNI_FALSE);
        if (glyphRanges != NULL) env->ReleasePrimitiveArrayCritical(obj_glyphRanges, glyphRanges, JNI_FALSE);
        return _result;
    */

    /**
     * 'compressed_font_data' still owned by caller. Compress with binary_to_compressed_c.cpp.
     */
    public ImFont addFontFromMemoryCompressedTTF(final byte[] compressedFontData, final int compressedFontSize, final float sizePixels) {
        return new ImFont(nAddFontFromMemoryCompressedTTF(compressedFontData, compressedFontSize, sizePixels));
    }

    /**
     * 'compressed_font_data' still owned by caller. Compress with binary_to_compressed_c.cpp.
     */
    public ImFont addFontFromMemoryCompressedTTF(final byte[] compressedFontData, final int compressedFontSize, final float sizePixels, final ImFontConfig imFontConfig) {
        return new ImFont(nAddFontFromMemoryCompressedTTF(compressedFontData, compressedFontSize, sizePixels, imFontConfig.ptr));
    }

    /**
     * 'compressed_font_data' still owned by caller. Compress with binary_to_compressed_c.cpp.
     */
    public ImFont addFontFromMemoryCompressedTTF(final byte[] compressedFontData, final int compressedFontSize, final float sizePixels, final ImFontConfig imFontConfig, final short[] glyphRanges) {
        return new ImFont(nAddFontFromMemoryCompressedTTF(compressedFontData, compressedFontSize, sizePixels, imFontConfig.ptr, glyphRanges));
    }

    /**
     * 'compressed_font_data' still owned by caller. Compress with binary_to_compressed_c.cpp.
     */
    public ImFont addFontFromMemoryCompressedTTF(final byte[] compressedFontData, final int compressedFontSize, final float sizePixels, final short[] glyphRanges) {
        return new ImFont(nAddFontFromMemoryCompressedTTF(compressedFontData, compressedFontSize, sizePixels, glyphRanges));
    }

    private native long nAddFontFromMemoryCompressedTTF(byte[] obj_compressedFontData, int compressedFontSize, float sizePixels); /*MANUAL
        auto compressedFontData = obj_compressedFontData == NULL ? NULL : (char*)env->GetPrimitiveArrayCritical(obj_compressedFontData, JNI_FALSE);
        auto _result = (uintptr_t)THIS->AddFontFromMemoryCompressedTTF(&compressedFontData[0], compressedFontSize, sizePixels);
        if (compressedFontData != NULL) env->ReleasePrimitiveArrayCritical(obj_compressedFontData, compressedFontData, JNI_FALSE);
        return _result;
    */

    private native long nAddFontFromMemoryCompressedTTF(byte[] obj_compressedFontData, int compressedFontSize, float sizePixels, long imFontConfig); /*MANUAL
        auto compressedFontData = obj_compressedFontData == NULL ? NULL : (char*)env->GetPrimitiveArrayCritical(obj_compressedFontData, JNI_FALSE);
        auto _result = (uintptr_t)THIS->AddFontFromMemoryCompressedTTF(&compressedFontData[0], compressedFontSize, sizePixels, reinterpret_cast<ImFontConfig*>(imFontConfig));
        if (compressedFontData != NULL) env->ReleasePrimitiveArrayCritical(obj_compressedFontData, compressedFontData, JNI_FALSE);
        return _result;
    */

    private native long nAddFontFromMemoryCompressedTTF(byte[] obj_compressedFontData, int compressedFontSize, float sizePixels, long imFontConfig, short[] obj_glyphRanges); /*MANUAL
        auto compressedFontData = obj_compressedFontData == NULL ? NULL : (char*)env->GetPrimitiveArrayCritical(obj_compressedFontData, JNI_FALSE);
        auto glyphRanges = obj_glyphRanges == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_glyphRanges, JNI_FALSE);
        auto _result = (uintptr_t)THIS->AddFontFromMemoryCompressedTTF(&compressedFontData[0], compressedFontSize, sizePixels, reinterpret_cast<ImFontConfig*>(imFontConfig), (ImWchar*)&glyphRanges[0]);
        if (compressedFontData != NULL) env->ReleasePrimitiveArrayCritical(obj_compressedFontData, compressedFontData, JNI_FALSE);
        if (glyphRanges != NULL) env->ReleasePrimitiveArrayCritical(obj_glyphRanges, glyphRanges, JNI_FALSE);
        return _result;
    */

    private native long nAddFontFromMemoryCompressedTTF(byte[] obj_compressedFontData, int compressedFontSize, float sizePixels, short[] obj_glyphRanges); /*MANUAL
        auto compressedFontData = obj_compressedFontData == NULL ? NULL : (char*)env->GetPrimitiveArrayCritical(obj_compressedFontData, JNI_FALSE);
        auto glyphRanges = obj_glyphRanges == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_glyphRanges, JNI_FALSE);
        auto _result = (uintptr_t)THIS->AddFontFromMemoryCompressedTTF(&compressedFontData[0], compressedFontSize, sizePixels, NULL, (ImWchar*)&glyphRanges[0]);
        if (compressedFontData != NULL) env->ReleasePrimitiveArrayCritical(obj_compressedFontData, compressedFontData, JNI_FALSE);
        if (glyphRanges != NULL) env->ReleasePrimitiveArrayCritical(obj_glyphRanges, glyphRanges, JNI_FALSE);
        return _result;
    */

    /**
     * 'compressed_font_data_base85' still owned by caller. Compress with binary_to_compressed_c.cpp with -base85 parameter.
     */
    public ImFont addFontFromMemoryCompressedBase85TTF(final String compressedFontDataBase85, final float sizePixels, final ImFontConfig fontConfig) {
        return new ImFont(nAddFontFromMemoryCompressedBase85TTF(compressedFontDataBase85, sizePixels, fontConfig.ptr));
    }

    /**
     * 'compressed_font_data_base85' still owned by caller. Compress with binary_to_compressed_c.cpp with -base85 parameter.
     */
    public ImFont addFontFromMemoryCompressedBase85TTF(final String compressedFontDataBase85, final float sizePixels, final ImFontConfig fontConfig, final short[] glyphRanges) {
        return new ImFont(nAddFontFromMemoryCompressedBase85TTF(compressedFontDataBase85, sizePixels, fontConfig.ptr, glyphRanges));
    }

    private native long nAddFontFromMemoryCompressedBase85TTF(String obj_compressedFontDataBase85, float sizePixels, long fontConfig); /*MANUAL
        auto compressedFontDataBase85 = obj_compressedFontDataBase85 == NULL ? NULL : (char*)env->GetStringUTFChars(obj_compressedFontDataBase85, JNI_FALSE);
        auto _result = (uintptr_t)THIS->AddFontFromMemoryCompressedBase85TTF(compressedFontDataBase85, sizePixels, reinterpret_cast<ImFontConfig*>(fontConfig));
        if (compressedFontDataBase85 != NULL) env->ReleaseStringUTFChars(obj_compressedFontDataBase85, compressedFontDataBase85);
        return _result;
    */

    private native long nAddFontFromMemoryCompressedBase85TTF(String obj_compressedFontDataBase85, float sizePixels, long fontConfig, short[] obj_glyphRanges); /*MANUAL
        auto compressedFontDataBase85 = obj_compressedFontDataBase85 == NULL ? NULL : (char*)env->GetStringUTFChars(obj_compressedFontDataBase85, JNI_FALSE);
        auto glyphRanges = obj_glyphRanges == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_glyphRanges, JNI_FALSE);
        auto _result = (uintptr_t)THIS->AddFontFromMemoryCompressedBase85TTF(compressedFontDataBase85, sizePixels, reinterpret_cast<ImFontConfig*>(fontConfig), (ImWchar*)&glyphRanges[0]);
        if (compressedFontDataBase85 != NULL) env->ReleaseStringUTFChars(obj_compressedFontDataBase85, compressedFontDataBase85);
        if (glyphRanges != NULL) env->ReleasePrimitiveArrayCritical(obj_glyphRanges, glyphRanges, JNI_FALSE);
        return _result;
    */

    /**
     * Clear input data (all ImFontConfig structures including sizes, TTF data, glyph ranges, etc.) = all the data used to build the texture and fonts.
     */
    public void clearInputData() {
        nClearInputData();
    }

    private native void nClearInputData(); /*
        THIS->ClearInputData();
    */

    /**
     * Clear output texture data (CPU side). Saves RAM once the texture has been copied to graphics memory.
     */
    public void clearTexData() {
        nClearTexData();
    }

    private native void nClearTexData(); /*
        THIS->ClearTexData();
    */

    /**
     * Clear output font data (glyphs storage, UV coordinates).
     */
    public void clearFonts() {
        nClearFonts();
    }

    private native void nClearFonts(); /*
        THIS->ClearFonts();
    */

    /**
     * Clear all input and output.
     */
    public void clear() {
        nClear();
    }

    private native void nClear(); /*
        THIS->Clear();
    */

    // Build atlas, retrieve pixel data.
    // User is in charge of copying the pixels into graphics memory (e.g. create a texture with your engine). Then store your texture handle with SetTexID().
    // The pitch is always = Width * BytesPerPixels (1 or 4)
    // Building in RGBA32 format is provided for convenience and compatibility, but note that unless you manually manipulate or copy color data into
    // the texture (e.g. when using the AddCustomRect*** api), then the RGB pixels emitted will always be white (~75% of memory/bandwidth waste.

    /*JNI
        #ifdef IMGUI_ENABLE_FREETYPE
        #include "misc/freetype/imgui_freetype.h"
        #endif
     */

    /**
     * <b>BINDING NOTICE:</b> This method is specific to the imgui-java binding.
     * <p>
     * Since FreeType is included in the final build, it's possible to use both font renderers (STB_TrueType and FreeType) simultaneously without needing to rebuild the library.
     * By default, we use small hacks to set STB_TrueType as the default font renderer. However, this method allows you to enforce the use of the FreeType renderer.
     * <p>
     * This method MUST be called before invoking the "#build" or "#getTexData*" methods.
     *
     * @param enabled true to enable the FreeType font renderer
     */
    public native void setFreeTypeRenderer(boolean enabled); /*
        #ifdef IMGUI_ENABLE_FREETYPE
        if (enabled) {
            THIS->FontBuilderIO = ImGuiFreeType::GetBuilderForFreeType();
        } else {
            THIS->FontBuilderIO = NULL;
        }
        #endif
    */

    /**
     * Build pixels data. This is called automatically for you by the GetTexData*** functions.
     */
    public boolean build() {
        return nBuild();
    }

    private native boolean nBuild(); /*
        return THIS->Build();
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

    public boolean isBuilt() {
        return nIsBuilt();
    }

    private native boolean nIsBuilt(); /*
        return THIS->IsBuilt();
    */

    /**
     * User data to refer to the texture once it has been uploaded to user's graphic systems.
     * It is passed back to you during rendering via the ImDrawCmd structure.
     */
    public void setTexID(final long textureID) {
        nSetTexID(textureID);
    }

    private native void nSetTexID(long textureID); /*
        THIS->SetTexID((ImTextureID)(uintptr_t)textureID);
    */

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
     * Default + Greek and Coptic
     */
    public native short[] getGlyphRangesGreek(); /*
        RETURN_GLYPH_2_SHORT(THIS->GetGlyphRangesGreek());
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

    public int addCustomRectRegular(final int width, final int height) {
        return nAddCustomRectRegular(width, height);
    }

    private native int nAddCustomRectRegular(int width, int height); /*
        return THIS->AddCustomRectRegular(width, height);
    */

    /**
     * Id needs to be {@code <} 0x110000 to register a rectangle to map into a specific font.
     */
    public int addCustomRectFontGlyph(final ImFont imFont, final short id, final int width, final int height, final float advanceX) {
        return nAddCustomRectFontGlyph(imFont.ptr, id, width, height, advanceX);
    }

    /**
     * Id needs to be {@code <} 0x110000 to register a rectangle to map into a specific font.
     */
    public int addCustomRectFontGlyph(final ImFont imFont, final short id, final int width, final int height, final float advanceX, final ImVec2 offset) {
        return nAddCustomRectFontGlyph(imFont.ptr, id, width, height, advanceX, offset.x, offset.y);
    }

    /**
     * Id needs to be {@code <} 0x110000 to register a rectangle to map into a specific font.
     */
    public int addCustomRectFontGlyph(final ImFont imFont, final short id, final int width, final int height, final float advanceX, final float offsetX, final float offsetY) {
        return nAddCustomRectFontGlyph(imFont.ptr, id, width, height, advanceX, offsetX, offsetY);
    }

    private native int nAddCustomRectFontGlyph(long imFont, short id, int width, int height, float advanceX); /*
        return THIS->AddCustomRectFontGlyph(reinterpret_cast<ImFont*>(imFont), (ImWchar)id, width, height, advanceX);
    */

    private native int nAddCustomRectFontGlyph(long imFont, short id, int width, int height, float advanceX, float offsetX, float offsetY); /*MANUAL
        ImVec2 offset = ImVec2(offsetX, offsetY);
        auto _result = THIS->AddCustomRectFontGlyph(reinterpret_cast<ImFont*>(imFont), (ImWchar)id, width, height, advanceX, offset);
        return _result;
    */

    // TODO GetCustomRectByIndex

    //-------------------------------------------
    // Members
    //-------------------------------------------

    /**
     * Build flags (see {@link imgui.flag.ImFontAtlasFlags})
     */
    public int getFlags() {
        return nGetFlags();
    }

    /**
     * Build flags (see {@link imgui.flag.ImFontAtlasFlags})
     */
    public void setFlags(final int value) {
        nSetFlags(value);
    }

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

    private native int nGetFlags(); /*
        return THIS->Flags;
    */

    private native void nSetFlags(int value); /*
        THIS->Flags = value;
    */

    // TexID implemented as SetTexID function

    /**
     * Texture width desired by user before Build(). Must be a power-of-two.
     * If have many glyphs your graphics API have texture size restrictions you may want to increase texture width to decrease height.
     */
    public int getTexDesiredWidth() {
        return nGetTexDesiredWidth();
    }

    /**
     * Texture width desired by user before Build(). Must be a power-of-two.
     * If have many glyphs your graphics API have texture size restrictions you may want to increase texture width to decrease height.
     */
    public void setTexDesiredWidth(final int value) {
        nSetTexDesiredWidth(value);
    }

    private native int nGetTexDesiredWidth(); /*
        return THIS->TexDesiredWidth;
    */

    private native void nSetTexDesiredWidth(int value); /*
        THIS->TexDesiredWidth = value;
    */

    /**
     * Padding between glyphs within texture in pixels. Defaults to 1.
     * If your rendering method doesn't rely on bilinear filtering you may set this to 0.
     */
    public int getTexGlyphPadding() {
        return nGetTexGlyphPadding();
    }

    /**
     * Padding between glyphs within texture in pixels. Defaults to 1.
     * If your rendering method doesn't rely on bilinear filtering you may set this to 0.
     */
    public void setTexGlyphPadding(final int value) {
        nSetTexGlyphPadding(value);
    }

    private native int nGetTexGlyphPadding(); /*
        return THIS->TexGlyphPadding;
    */

    private native void nSetTexGlyphPadding(int value); /*
        THIS->TexGlyphPadding = value;
    */

    /**
     * Marked as Locked by ImGui::NewFrame() so attempt to modify the atlas will assert.
     */
    public boolean getLocked() {
        return nGetLocked();
    }

    /**
     * Marked as Locked by ImGui::NewFrame() so attempt to modify the atlas will assert.
     */
    public void setLocked(final boolean value) {
        nSetLocked(value);
    }

    private native boolean nGetLocked(); /*
        return THIS->Locked;
    */

    private native void nSetLocked(boolean value); /*
        THIS->Locked = value;
    */

    /*JNI
        #undef THIS
     */
}
