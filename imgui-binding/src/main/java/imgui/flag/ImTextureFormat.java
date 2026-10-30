package imgui.flag;

import imgui.binding.annotation.BindingAstEnum;
import imgui.binding.annotation.BindingSource;

/**
 * We intentionally support a limited amount of texture formats to limit burden on CPU-side code and extension.
 * Most standard backends only support RGBA32 but we provide a single channel option for low-resource/embedded systems.
 */
@BindingSource
public final class ImTextureFormat {
    private ImTextureFormat() {
    }

    @BindingAstEnum(file = "ast-imgui.json", qualType = "ImTextureFormat", sanitizeName = "ImTextureFormat_")
    public Void __;
}
