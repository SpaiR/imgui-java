package imgui;

/**
 * Hold rendering data for one glyph.
 * (Note: some language parsers may fail to convert the 31+1 bitfield members, in this case maybe drop store a single u32 or we can rework this)
 */
public final class ImFontGlyph implements ImGuiDestroyableStruct {
    final long ptr;

    /**
     * This class will create a native structure.
     * Call {@link #destroy()} method to manually free used memory.
     */
    public ImFontGlyph() {
        ImGui.touch();
        ptr = nCreate();
    }

    ImFontGlyph(final long ptr) {
        this.ptr = ptr;
    }

    @Override
    public void destroy() {
        nDestroy(ptr);
    }

    /*JNI
        #include <stdint.h>
        #include <imgui.h>

        jfieldID imFontGlyphPtrID;

        #define IM_FONT_GLYPH ((ImFontGlyph*)env->GetLongField(object, imFontGlyphPtrID))
     */

    static native void nInit(); /*
        jclass jImFontGlyphClass = env->FindClass("imgui/ImFontGlyph");
        imFontGlyphPtrID = env->GetFieldID(jImFontGlyphClass, "ptr", "J");
    */

    private native long nCreate(); /*
        ImFontConfig* imFontConfig = new ImFontConfig();
        return (intptr_t)imFontConfig;
    */

    private native void nDestroy(long ptr); /*
        delete (ImFontConfig*)ptr;
    */

    /**
     * 0x0000..0xFFFF
     */
    public native int getCodepoint(); /*
        return (unsigned int)IM_FONT_GLYPH->Codepoint;
    */

    /**
     * 0x0000..0xFFFF
     */
    public native void setCodepoint(int codepoint); /*
        IM_FONT_GLYPH->Codepoint = (unsigned int)codepoint;
    */

    /**
     * Flag to allow early out when rendering
     */
    public native int getVisible(); /*
        return (unsigned int)IM_FONT_GLYPH->Visible;
    */

    /**
     * Flag to allow early out when rendering
     */
    public native void setVisible(int visible); /*
        IM_FONT_GLYPH->Visible = (unsigned int)visible;
    */

    /**
     * Distance to next character (= data from font + ImFontConfig::GlyphExtraSpacing.x baked in)
     */
    public native float getAdvanceX(); /*
        return IM_FONT_GLYPH->AdvanceX;
    */

    /**
     * Distance to next character (= data from font + ImFontConfig::GlyphExtraSpacing.x baked in)
     */
    public native void setAdvanceX(float advanceX); /*
        IM_FONT_GLYPH->AdvanceX = advanceX;
    */

    /**
     * Glyph corners
     */
    public native float getX0(); /*
        return IM_FONT_GLYPH->X0;
    */

    /**
     * Glyph corners
     */
    public native void setX0(float x0); /*
        IM_FONT_GLYPH->X0 = x0;
    */

    /**
     * Glyph corners
     */
    public native float getY0(); /*
        return IM_FONT_GLYPH->Y0;
    */

    /**
     * Glyph corners
     */
    public native void setY0(float y0); /*
        IM_FONT_GLYPH->Y0 = y0;
    */

    /**
     * Glyph corners
     */
    public native float getX1(); /*
        return IM_FONT_GLYPH->X1;
    */

    /**
     * Glyph corners
     */
    public native void setX1(float x1); /*
        IM_FONT_GLYPH->X1 = x1;
    */

    /**
     * Glyph corners
     */
    public native float getY1(); /*
        return IM_FONT_GLYPH->Y1;
    */

    /**
     * Glyph corners
     */
    public native void setY1(float y1); /*
        IM_FONT_GLYPH->Y1 = y1;
    */

    /**
     * Texture coordinates
     */
    public native float getU0(); /*
        return IM_FONT_GLYPH->U0;
    */

    /**
     * Texture coordinates
     */
    public native void setU0(float u0); /*
        IM_FONT_GLYPH->U0 = u0;
    */

    /**
     * Texture coordinates
     */
    public native float getV0(); /*
        return IM_FONT_GLYPH->V0;
    */

    /**
     * Texture coordinates
     */
    public native void setV0(float v0); /*
        IM_FONT_GLYPH->V0 = v0;
    */

    /**
     * Texture coordinates
     */
    public native float getU1(); /*
        return IM_FONT_GLYPH->U1;
    */

    /**
     * Texture coordinates
     */
    public native void setU1(float u1); /*
        IM_FONT_GLYPH->U1 = u1;
    */

    /**
     * Texture coordinates
     */
    public native float getV1(); /*
        return IM_FONT_GLYPH->V1;
    */

    /**
     * Texture coordinates
     */
    public native void setV1(float v1); /*
        IM_FONT_GLYPH->V1 = v1;
    */
}
