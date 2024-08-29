package imgui;

import imgui.binding.ImGuiStructDestroyable;
import imgui.binding.annotation.BindingField;
import imgui.binding.annotation.BindingSource;

/**
 * Hold rendering data for one glyph.
 * (Note: some language parsers may fail to convert the 31+1 bitfield members, in this case maybe drop store a single u32 or we can rework this)
 */
@BindingSource
public final class ImFontGlyph extends ImGuiStructDestroyable {
    public ImFontGlyph() {
        super();
    }

    public ImFontGlyph(final long ptr) {
        super(ptr);
    }

    @Override
    protected long create() {
        return nCreate();
    }

    /*JNI
        #include "_common.h"
        #define THIS ((ImFontGlyph*)STRUCT_PTR)
     */

    private native long nCreate(); /*
        return (uintptr_t)(new ImFontGlyph());
    */

    /**
     *  Flag to indicate glyph is colored and should generally ignore tinting (make it usable with no shift on little-endian as this is used in loops).
     */
    @BindingField
    public int Colored;

    /**
     * Flag to indicate glyph has no visible pixels (e.g. space). Allow early out when rendering.
     */
    @BindingField
    public int Visible;

    /**
     * 0x0000..0xFFFF
     */
    @BindingField
    public int Codepoint;

    /**
     * Distance to next character (= data from font + ImFontConfig::GlyphExtraSpacing.x baked in)
     */
    @BindingField
    public float AdvanceX;

    /**
     * Glyph corners
     */
    @BindingField
    public float X0;

    /**
     * Glyph corners
     */
    @BindingField
    public float Y0;

    /**
     * Glyph corners
     */
    @BindingField
    public float X1;

    /**
     * Glyph corners
     */
    @BindingField
    public float Y1;

    /**
     * Texture coordinates
     */
    @BindingField
    public float U0;

    /**
     * Texture coordinates
     */
    @BindingField
    public float V0;

    /**
     * Texture coordinates
     */
    @BindingField
    public float U1;

    /**
     * Texture coordinates
     */
    @BindingField
    public float V1;

    /*JNI
        #undef THIS
     */
}
