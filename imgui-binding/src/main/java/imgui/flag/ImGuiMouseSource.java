package imgui.flag;

import imgui.binding.annotation.BindingAstEnum;
import imgui.binding.annotation.BindingSource;

/**
 * Enumeration for AddMouseSourceEvent() actual source of Mouse Input data.
 * Historically we use "Mouse" terminology everywhere to indicate pointer data, e.g. MousePos, IsMousePressed(), io.AddMousePosEvent()
 * But that "Mouse" data can come from different source which occasionally may be useful for application to know about.
 * You can submit a change of pointer type using io.AddMouseSourceEvent().
 */
@BindingSource
public final class ImGuiMouseSource {
    private ImGuiMouseSource() {
    }

    @BindingAstEnum(file = "ast-imgui.json", qualType = "ImGuiMouseSource", sanitizeName = "ImGuiMouseSource_")
    public Void __;
}
