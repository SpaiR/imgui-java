package imgui.flag;

import imgui.binding.annotation.BindingAstEnum;
import imgui.binding.annotation.BindingSource;

/**
 * Gamepad/Keyboard directional navigation
 * Since {@code >=1.87} backends you generally don't need to care about this enum since io.NavInputs[] is setup automatically. This might become private/internal some day.
 * Keyboard: Set io.ConfigFlags |= ImGuiConfigFlags_NavEnableKeyboard to enable.
 * NewFrame() will automatically fill io.NavInputs[] based on your io.KeysDown[] + io.KeyMap[] arrays.
 * Gamepad:  Set io.ConfigFlags |= ImGuiConfigFlags_NavEnableGamepad to enable.
 * Backend: set ImGuiBackendFlags_HasGamepad and fill the io.NavInputs[] fields before calling NewFrame().
 * Note that io.NavInputs[] is cleared by EndFrame().
 * Read instructions in imgui.cpp for more details. Download PNG/PSD at http://dearimgui.org/controls_sheets.
 */
@BindingSource
public final class ImGuiNavInput {
    private ImGuiNavInput() {
    }

    @BindingAstEnum(file = "ast-imgui.json", qualType = "ImGuiNavInput_")
    public Void __;
}
