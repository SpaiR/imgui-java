package imgui.extension.imguifiledialog.callback;

import imgui.binding.annotation.ExcludedSource;

@ExcludedSource
public abstract class ImGuiFileDialogPaneFun {
    public abstract void accept(String filter, long userDatas, boolean canContinue);
}
