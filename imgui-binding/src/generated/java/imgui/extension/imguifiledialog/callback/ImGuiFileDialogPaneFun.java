package imgui.extension.imguifiledialog.callback;

public abstract class ImGuiFileDialogPaneFun {
    public abstract void accept(String filter, long userDatas, boolean canContinue);
}
