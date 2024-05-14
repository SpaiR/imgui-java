package imgui.flag;

import imgui.binding.annotation.BindingAstEnum;
import imgui.binding.annotation.BindingSource;

/**
 * Flags for OpenPopup*(), BeginPopupContext*(), IsPopupOpen() functions.
 * - To be backward compatible with older API which took an 'int mouse_button = 1' argument, we need to treat
 *   small flags values as a mouse button index, so we encode the mouse button in the first few bits of the flags.
 *   It is therefore guaranteed to be legal to pass a mouse button index in ImGuiPopupFlags.
 * - For the same reason, we exceptionally default the ImGuiPopupFlags argument of BeginPopupContextXXX functions to 1 instead of 0.
 * - Multiple buttons currently cannot be combined/or-ed in those functions (we could allow it later).
 */
@BindingSource
public final class ImGuiPopupFlags {
    private ImGuiPopupFlags() {
    }

    @BindingAstEnum(file = "ast-imgui.json", qualType = "ImGuiPopupFlags_")
    public Void __;
}
