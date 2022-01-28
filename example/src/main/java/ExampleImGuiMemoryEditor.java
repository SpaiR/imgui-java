import imgui.extension.club.MemoryEditor;
import imgui.flag.ImGuiCond;
import imgui.internal.ImGui;
import imgui.type.ImBoolean;
import org.lwjgl.system.MemoryUtil;

import java.awt.*;
import java.net.URI;
import java.nio.ByteBuffer;

public class ExampleImGuiMemoryEditor {
    private static final String URL = "https://github.com/ocornut/imgui_club";
    private static ByteBuffer demoData;
    private static MemoryEditor memoryEditor;
    private static ImBoolean showingMemoryEditor = new ImBoolean(false);

    static {
        demoData = MemoryUtil.memASCII("Welcome to the demo for Dear ImGui Memory Editor." +
            "\n The git repo is located at " + URL + "." +
            "You can use this memory editor to view the raw memory values at some pointer location.");

        memoryEditor = new MemoryEditor();
    }

    public static void show(ImBoolean showImGuiMemoryEditor) {
        ImGui.setNextWindowSize(400, 200, ImGuiCond.Once);
        ImGui.setNextWindowPos(ImGui.getMainViewport().getPosX() + 100, ImGui.getMainViewport().getPosY() + 100, ImGuiCond.Once);
        if (ImGui.begin("ImGuiMemoryEditor Demo", showImGuiMemoryEditor)) {
            ImGui.text("This a demo for ImGui MemoryEditor");

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
            ImGui.checkbox("Memory Editor", showingMemoryEditor);
            if (showingMemoryEditor.get()) {
                memoryEditor.drawWindow("Example Data", MemoryUtil.memAddress(demoData), demoData.capacity());
            }
        }
        ImGui.end();
    }
}
