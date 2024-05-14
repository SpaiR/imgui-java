package imgui.internal;

import imgui.binding.ImGuiStruct;

public class ImGuiContext extends ImGuiStruct {
    public ImGuiContext(final long ptr) {
        super(ptr);
        ImGui.init();
    }
}
