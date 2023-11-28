import imgui.ImGui;
import imgui.app.Application;
import imgui.type.ImBoolean;

public class Extra {
    private static final ImBoolean SHOW_DEMO_WINDOW = new ImBoolean(false);
    private static final ImBoolean SHOW_IMNODES_DEMO_WINDOW = new ImBoolean(false);
    private static final ImBoolean SHOW_IMGUI_NODE_EDITOR_DEMO_WINDOW = new ImBoolean(false);
    private static final ImBoolean SHOW_DRAG_N_DROP_WINDOW = new ImBoolean(false);
    private static final ImBoolean SHOW_IMPLOT_DEMO_WINDOW = new ImBoolean(false);
    private static final ImBoolean SHOW_IMGUIZMO_DEMO = new ImBoolean(false);
    private static final ImBoolean SHOW_IMGUI_COLOR_TEXT_EDIT_WINDOW = new ImBoolean(false);
    private static final ImBoolean SHOW_IMGUI_FILE_DIALOG_WINDOW = new ImBoolean(false);
    private static final ImBoolean SHOW_IMGUI_MEMORY_EDITOR_WINDOW = new ImBoolean(false);
    private static final ImBoolean SHOW_IMGUI_CANVAS_EDITOR_WINDOW = new ImBoolean(false);
    private static final ImBoolean SHOW_IMGUI_INPUT_CALLBACK_WINDOW = new ImBoolean(false);
    private static final ImBoolean SHOW_IMGUI_KNOBS_DEMO = new ImBoolean(false);

    private static final Graph GRAPH = new Graph();

    public static void show(final Application app) {
        ImGui.colorEdit3("Background Color", app.getColorBg().data);
        ImGui.checkbox("Show Demo Window", SHOW_DEMO_WINDOW);
        ImGui.checkbox("Show ImNodes Demo Window", SHOW_IMNODES_DEMO_WINDOW);
        ImGui.checkbox("Show imgui-node-editor Demo Window", SHOW_IMGUI_NODE_EDITOR_DEMO_WINDOW);
        ImGui.checkbox("Show Drag'N'Drop Demo Window", SHOW_DRAG_N_DROP_WINDOW);
        ImGui.checkbox("Show ImPlot Demo Window", SHOW_IMPLOT_DEMO_WINDOW);
        ImGui.checkbox("Show ImGuizmo Demo Window", SHOW_IMGUIZMO_DEMO);
        ImGui.checkbox("Show ImGuiColorTextEdit Demo Window", SHOW_IMGUI_COLOR_TEXT_EDIT_WINDOW);
        ImGui.checkbox("Show ImGuiFileDialog Demo Window", SHOW_IMGUI_FILE_DIALOG_WINDOW);
        ImGui.checkbox("Show ImGui MemoryEditor Demo Window", SHOW_IMGUI_MEMORY_EDITOR_WINDOW);
        ImGui.checkbox("Show ImGui Canvas Demo Window", SHOW_IMGUI_CANVAS_EDITOR_WINDOW);
        ImGui.checkbox("Show Imgui InputText Callback Window", SHOW_IMGUI_INPUT_CALLBACK_WINDOW);
        ImGui.checkbox("Show Imgui Knobs Demo", SHOW_IMGUI_KNOBS_DEMO);

        if (SHOW_DEMO_WINDOW.get()) {
            ImGui.showDemoWindow(SHOW_DEMO_WINDOW);
        }

        if (SHOW_IMNODES_DEMO_WINDOW.get()) {
            ExampleImNodes.show(SHOW_IMNODES_DEMO_WINDOW, GRAPH);
        }

        if (SHOW_IMGUI_NODE_EDITOR_DEMO_WINDOW.get()) {
            ExampleImGuiNodeEditor.show(SHOW_IMGUI_NODE_EDITOR_DEMO_WINDOW, GRAPH);
        }

        if (SHOW_IMGUIZMO_DEMO.get()) {
            ExampleImGuizmo.show(SHOW_IMGUIZMO_DEMO);
        }

        if (SHOW_DRAG_N_DROP_WINDOW.get()) {
            ExampleDragAndDrop.show(SHOW_DRAG_N_DROP_WINDOW);
        }

        if (SHOW_IMPLOT_DEMO_WINDOW.get()) {
            ExampleImPlot.show(SHOW_IMPLOT_DEMO_WINDOW);
        }

        if (SHOW_IMGUI_COLOR_TEXT_EDIT_WINDOW.get()) {
            ExampleImGuiColorTextEdit.show(SHOW_IMGUI_COLOR_TEXT_EDIT_WINDOW);
        }

        if (SHOW_IMGUI_FILE_DIALOG_WINDOW.get()) {
            ExampleImGuiFileDialog.show(SHOW_IMGUI_FILE_DIALOG_WINDOW);
        }

        if (SHOW_IMGUI_MEMORY_EDITOR_WINDOW.get()) {
            ExampleImGuiMemoryEditor.show(SHOW_IMGUI_MEMORY_EDITOR_WINDOW);
        }

        if (SHOW_IMGUI_CANVAS_EDITOR_WINDOW.get()) {
            ExampleCanvasEditor.show(SHOW_IMGUI_CANVAS_EDITOR_WINDOW);
        }

        if (SHOW_IMGUI_INPUT_CALLBACK_WINDOW.get()) {
            ExampleInputTextCallback.show(SHOW_IMGUI_INPUT_CALLBACK_WINDOW);
        }

        if (SHOW_IMGUI_KNOBS_DEMO.get()) {
            ExampleKnobs.show(SHOW_IMGUI_KNOBS_DEMO);
        }
    }
}
