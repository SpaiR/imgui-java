package imgui.flag;

import imgui.binding.annotation.BindingAstEnum;
import imgui.binding.annotation.BindingSource;

/**
 * Flags for ImFontAtlas build
 */
@BindingSource
public final class ImFontAtlasFlags {
    private ImFontAtlasFlags() {
    }

    @BindingAstEnum(file = "ast-imgui.json", qualType = "ImFontAtlasFlags_")
    public Void __;
}
