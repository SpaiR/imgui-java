package imgui;

import imgui.binding.ImGuiStructDestroyable;
import imgui.binding.annotation.ArgValue;
import imgui.binding.annotation.BindingField;
import imgui.binding.annotation.BindingMethod;
import imgui.binding.annotation.BindingSource;
import imgui.binding.annotation.OptArg;

/**
 * Font runtime data. A single logical font that can be baked at multiple sizes (see ImFontBaked via {@code getFontBaked}).
 *
 * <p>In Dear ImGui 1.92 the font subsystem was reworked: {@code ImFont} now represents the font source set,
 * while size-specific data (glyphs, ascent/descent, character metrics) lives on {@code ImFontBaked}.
 * Many previously-exposed fields moved there and are no longer reachable through {@code ImFont}.
 */
@BindingSource
public final class ImFont extends ImGuiStructDestroyable {
    public ImFont() {
        super();
    }

    public ImFont(final long ptr) {
        super(ptr);
    }

    @Override
    protected long create() {
        return nCreate();
    }

    /*JNI
        #include "_common.h"
        #define THIS ((ImFont*)STRUCT_PTR)
     */

    private native long nCreate(); /*
        return (uintptr_t)(new ImFont());
    */

    /**
     * Font size passed to {@code AddFontXXX()}. Use for legacy code calling {@link imgui.ImGui#pushFont(ImFont)}
     * that expected the original size (use {@code ImGui::GetFontBaked()} in new code).
     */
    @BindingField
    public float LegacySize;

    /**
     * Character used for ellipsis rendering.
     */
    @BindingField
    public short EllipsisChar;

    /**
     * Character used if a glyph isn't found (U+FFFD, '?').
     */
    @BindingField
    public short FallbackChar;

    /**
     * Legacy base font scale (~1.0f), multiplied by the per-window font scale which you can adjust with SetWindowFontScale().
     * Obsolete since 1.92; kept behind {@code IMGUI_DISABLE_OBSOLETE_FUNCTIONS}.
     */
    @BindingField
    public float Scale;

    // Methods

    @BindingMethod
    public native boolean IsLoaded();

    @BindingMethod
    public native String GetDebugName();

    /**
     * 'max_width' stops rendering after a certain width (could be turned into a 2d size). FLT_MAX to disable.
     * 'wrap_width' enable automatic word-wrapping across multiple lines to fit into given width. 0.0f to disable.
     */
    @BindingMethod
    public native ImVec2 CalcTextSizeA(float size, float maxWidth, float wrapWidth, String textBegin, @OptArg String textEnd);

    /**
     * @deprecated since imgui 1.92; prefer {@code CalcWordWrapPosition(size, ...)}. This overload is kept as a
     * legacy redirect; internally it passes {@code LegacySize * scale} as the size.
     */
    @Deprecated
    @BindingMethod
    public native String CalcWordWrapPositionA(float scale, String text, String textEnd, float wrapWidth);

    @BindingMethod
    public native void RenderChar(ImDrawList drawList, float size, ImVec2 pos, int col, @ArgValue(callPrefix = "(ImWchar)") int c);

    /**
     * The {@code flags} parameter is {@code ImDrawTextFlags} (since imgui 1.92; previously a {@code bool cpu_fine_clip}).
     */
    @BindingMethod
    public native void RenderText(ImDrawList drawList, float size, ImVec2 pos, int col, ImVec4 clipRect, String textBegin, String textEnd, @OptArg(callValue = "0.0f") float wrapWidth, @OptArg int flags);

    /*JNI
        #undef THIS
     */
}
