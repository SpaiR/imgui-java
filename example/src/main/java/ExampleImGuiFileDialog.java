import imgui.extension.imguifiledialog.ImGuiFileDialog;
import imgui.extension.imguifiledialog.flag.ImGuiFileDialogFlags;
import imgui.flag.ImGuiCond;
import imgui.internal.ImGui;
import imgui.type.ImBoolean;

import java.awt.*;
import java.net.URI;
import java.util.Map;

public class ExampleImGuiFileDialog {
    private static final String URL = "https://github.com/aiekick/ImGuiFileDialog";
    private static final ImBoolean showDemo = new ImBoolean(false);

    private static Map<String, String> selection = null;

    public static void show(ImBoolean showImGuiFileDialogDemo) {
        ImGui.setNextWindowSize(500, 400, ImGuiCond.Once);
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
                ImGuiFileDialog.openDialog("browse-key", "Choose File", ".*", ".", "", 1, null, ImGuiFileDialogFlags.None);
            }

            if (ImGuiFileDialog.display("browse-key", ImGuiFileDialogFlags.None, 200, 400, 800, 600)) {
                if (ImGuiFileDialog.isOk()) {
                    selection = ImGuiFileDialog.getSelection();
                }
                ImGuiFileDialog.close();
            }
        }

        if (selection != null && !selection.isEmpty()) {
            ImGui.text("Selected: " + selection.values().stream().findFirst().get());
        }

        ImGui.end();
    }
}
