package imgui.flag;

import imgui.binding.annotation.BindingAstEnum;
import imgui.binding.annotation.BindingSource;

/**
 * Status of a texture to communicate with Renderer Backend.
 */
@BindingSource
public final class ImTextureStatus {
    private ImTextureStatus() {
    }

    @BindingAstEnum(file = "ast-imgui.json", qualType = "ImTextureStatus", sanitizeName = "ImTextureStatus_")
    public Void __;
}
