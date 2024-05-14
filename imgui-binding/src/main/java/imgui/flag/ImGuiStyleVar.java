package imgui.flag;

import imgui.binding.annotation.BindingAstEnum;
import imgui.binding.annotation.BindingSource;

/**
 * Enumeration for PushStyleVar() / PopStyleVar() to temporarily modify the ImGuiStyle structure.
 * - The enum only refers to fields of ImGuiStyle which makes sense to be pushed/popped inside UI code.
 *   During initialization or between frames, feel free to just poke into ImGuiStyle directly.
 * - Tip: Use your programming IDE navigation facilities on the names in the _second column_ below to find the actual members and their description.
 *   In Visual Studio IDE: CTRL+comma ("Edit.NavigateTo") can follow symbols in comments, whereas CTRL+F12 ("Edit.GoToImplementation") cannot.
 *   With Visual Assist installed: ALT+G ("VAssistX.GoToImplementation") can also follow symbols in comments.
 * - When changing this enum, you need to update the associated internal table GStyleVarInfo[] accordingly. This is where we link enum values to members offset/type.
 */
@BindingSource
public final class ImGuiStyleVar {
    private ImGuiStyleVar() {
    }

    @BindingAstEnum(file = "ast-imgui.json", qualType = "ImGuiStyleVar_")
    public Void __;
}
