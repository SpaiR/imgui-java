import imgui.ImGui;
import imgui.app.Application;
import imgui.type.ImBoolean;

public class Extra {
    private static final ImBoolean SHOW_DEMO_WINDOW = new ImBoolean(false);
    private static final ImBoolean SHOW_IMNODES_DEMO_WINDOW = new ImBoolean(false);
    private static final ImBoolean SHOW_IMGUI_NODE_EDITOR_DEMO_WINDOW = new ImBoolean(false);

    private static final Graph GRAPH = new Graph();

    public static void show(final Application app) {
        ImGui.colorEdit3("Background Color", app.getColorBg().getData());
        ImGui.checkbox("Show Demo Window", SHOW_DEMO_WINDOW);
        ImGui.checkbox("Show ImNodes Demo Window", SHOW_IMNODES_DEMO_WINDOW);
        ImGui.checkbox("Show imgui-node-editor Demo Window", SHOW_IMGUI_NODE_EDITOR_DEMO_WINDOW);

        if (SHOW_DEMO_WINDOW.get()) {
            ImGui.showDemoWindow(SHOW_DEMO_WINDOW);
        }

        if (SHOW_IMNODES_DEMO_WINDOW.get()) {
            ExampleImNodes.show(SHOW_IMNODES_DEMO_WINDOW, GRAPH);
        }

        if (SHOW_IMGUI_NODE_EDITOR_DEMO_WINDOW.get()) {
            ExampleImGuiNodeEditor.show(SHOW_IMGUI_NODE_EDITOR_DEMO_WINDOW, GRAPH);
        }
    }
}
