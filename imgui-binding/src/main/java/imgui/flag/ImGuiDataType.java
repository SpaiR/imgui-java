package imgui.flag;

import imgui.binding.annotation.BindingAstEnum;
import imgui.binding.annotation.BindingSource;

/**
 * A primary data type
 */
@BindingSource
public final class ImGuiDataType {
    private ImGuiDataType() {
    }

    @BindingAstEnum(file = "ast-imgui.json", qualType = "ImGuiDataType_")
    public Void __;
}
