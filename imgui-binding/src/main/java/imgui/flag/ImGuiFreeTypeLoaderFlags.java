package imgui.flag;

import imgui.binding.annotation.BindingAstEnum;
import imgui.binding.annotation.BindingSource;

/**
 * Renamed from {@code ImGuiFreeTypeBuilderFlags} in imgui 1.92.
 */
@BindingSource
public final class ImGuiFreeTypeLoaderFlags {
    private ImGuiFreeTypeLoaderFlags() {
    }

    @BindingAstEnum(file = "ast-imgui_freetype.json", qualType = "ImGuiFreeTypeLoaderFlags", sanitizeName = "ImGuiFreeTypeLoaderFlags_")
    public Void __;
}
