package imgui.flag;

import imgui.binding.annotation.BindingAstEnum;
import imgui.binding.annotation.BindingSource;

/**
 * Flags for ImGui::BeginChild()
 * (Legacy: bit 0 must always correspond to ImGuiChildFlags_Border to be backward compatible with old API using 'bool border = false'.
 * About using AutoResizeX/AutoResizeY flags:
 * - May be combined with SetNextWindowSizeConstraints() to set a min/max size for each axis (see "Demo->Child->Auto-resize with Constraints").
 * - Size measurement for a given axis is only performed when the child window is within visible boundaries, or is just appearing.
 *   - This allows BeginChild() to return false when not within boundaries (e.g. when scrolling), which is more optimal. BUT it won't update its auto-size while clipped.
 *     While not perfect, it is a better default behavior as the always-on performance gain is more valuable than the occasional "resizing after becoming visible again" glitch.
 *   - You may also use ImGuiChildFlags_AlwaysAutoResize to force an update even when child window is not in view.
 *     HOWEVER PLEASE UNDERSTAND THAT DOING SO WILL PREVENT BeginChild() FROM EVER RETURNING FALSE, disabling benefits of coarse clipping.
 */
@BindingSource
public final class ImGuiChildFlags {
    private ImGuiChildFlags() {
    }

    @BindingAstEnum(file = "ast-imgui.json", qualType = "ImGuiChildFlags_")
    public Void __;
}
