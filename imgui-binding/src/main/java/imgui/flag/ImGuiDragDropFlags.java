package imgui.flag;

import imgui.binding.annotation.BindingAstEnum;
import imgui.binding.annotation.BindingSource;

/**
 * Flags for ImGui::BeginDragDropSource(), ImGui::AcceptDragDropPayload()
 */
@BindingSource
public final class ImGuiDragDropFlags {
    private ImGuiDragDropFlags() {
    }

    @BindingAstEnum(file = "ast-imgui.json", qualType = "ImGuiDragDropFlags_")
    public Void __;
}
