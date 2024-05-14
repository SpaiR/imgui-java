import imgui.extension.imguifiledialog.ImGuiFileDialog;
import imgui.extension.imguifiledialog.callback.ImGuiFileDialogPaneFun;
import imgui.extension.imguifiledialog.flag.ImGuiFileDialogFlags;
import imgui.flag.ImGuiCond;
import imgui.internal.ImGui;
import imgui.type.ImBoolean;

import java.awt.*;
import java.net.URI;
import java.util.Map;

public class ExampleImGuiFileDialog {
    private static final String URL = "https://github.com/aiekick/ImGuiFileDialog";

    private static Map<String, String> selection = null;
    private static long userData = 0;
    private static ImGuiFileDialogPaneFun callback = new ImGuiFileDialogPaneFun() {
        @Override
        public void accept(String filter, long userDatas, boolean canContinue) {
            ImGui.text("Filter: " + filter);
        }
    };

    public static void show(ImBoolean showImGuiFileDialogDemo) {
        ImGui.setNextWindowSize(800, 200, ImGuiCond.Once);
        ImGui.setNextWindowPos(ImGui.getMainViewport().getPosX() + 100, ImGui.getMainViewport().getPosY() + 100, ImGuiCond.Once);
        if (ImGui.begin("ImGuiFileDialogDemo Demo", showImGuiFileDialogDemo)) {
            ImGui.text("This a demo for ImGuiFileDialog");

            ImGui.alignTextToFramePadding();
            ImGui.text("Repo:");
            ImGui.sameLine();
            if (ImGui.button(URL)) {
                try {
                    Desktop.getDesktop().browse(new URI(URL));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (ImGui.button("Browse File")) {
                ImGuiFileDialog.openModal("browse-key", "Choose File", ".java", ".", callback, 250, 1, 42, ImGuiFileDialogFlags.None);
            }

            if (ImGuiFileDialog.display("browse-key", ImGuiFileDialogFlags.None, 200, 400, 800, 600)) {
                if (ImGuiFileDialog.isOk()) {
                    selection = ImGuiFileDialog.getSelection();
                    userData = ImGuiFileDialog.getUserDatas();
                }
                ImGuiFileDialog.close();
            }

            if (ImGui.button("Browse Folder")) {
                ImGuiFileDialog.openDialog("browse-folder-key", "Choose Folder", null, ".", "", callback, 1, 7L, ImGuiFileDialogFlags.None);
            }

            if (ImGuiFileDialog.display("browse-folder-key", ImGuiFileDialogFlags.None, 200, 400, 800, 600)) {
                if (ImGuiFileDialog.isOk()) {
                    selection = ImGuiFileDialog.getSelection();
                    userData = ImGuiFileDialog.getUserDatas();
                }
                ImGuiFileDialog.close();
            }
        }

        if (selection != null && !selection.isEmpty()) {
            ImGui.text("Selected: " + selection.values().stream().findFirst().get());
            ImGui.text("User Data: " + userData);
        }

        ImGui.end();
    }
}
