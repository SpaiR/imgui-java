package imgui.flag;

import imgui.binding.annotation.BindingAstEnum;
import imgui.binding.annotation.BindingSource;

/**
 * A key identifier (ImGuiKey_XXX or ImGuiMod_XXX value): can represent Keyboard, Mouse and Gamepad values.
 * All our named keys are {@code >=} 512. Keys value 0 to 511 are left unused as legacy native/opaque key values ({@code <} 1.87).
 * Since {@code >=} 1.89 we increased typing (went from int to enum), some legacy code may need a cast to ImGuiKey.
 * Read details about the 1.87 and 1.89 transition : https://github.com/ocornut/imgui/issues/4921
 * Note that "Keys" related to physical keys and are not the same concept as input "Characters", the later are submitted via io.AddInputCharacter().
 */
@BindingSource
public final class ImGuiKey {
    private ImGuiKey() {
    }

    @BindingAstEnum(file = "ast-imgui.json", qualType = "ImGuiKey", sanitizeName = "ImGuiKey_")
    public Void __;
}
