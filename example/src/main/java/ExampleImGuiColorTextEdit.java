import imgui.ImGui;
import imgui.extension.texteditor.TextEditor;
import imgui.extension.texteditor.TextEditorCursorPosition;
import imgui.extension.texteditor.TextEditorLanguage;
import imgui.extension.texteditor.flag.TextEditorColor;
import imgui.extension.texteditor.flag.TextEditorScroll;
import imgui.flag.ImGuiCond;
import imgui.flag.ImGuiWindowFlags;
import imgui.type.ImBoolean;

public class ExampleImGuiColorTextEdit {
    private static final TextEditor EDITOR = new TextEditor();
    private static final String DEMO_TEXT =
        "// Demo C++ Code\n" +
        "\n" +
        "#include <iostream>\n" +
        "#include <random>\n" +
        "#include <vector>\n" +
        "\n" +
        "int main(int, char**) {\n" +
        "    std::random_device rd;\n" +
        "    std::mt19937 gen(rd());\n" +
        "    std::uniform_int_distribution<> distrib(0, 1000);\n" +
        "    std::vector<int> numbers;\n" +
        "\n" +
        "    for (int i = 0; i < 100; i++) {\n" +
        "        numbers.emplace_back(distrib(gen));\n" +
        "    }\n" +
        "\n" +
        "    for (auto n : numbers) {\n" +
        "        std::cout << n << std::endl;\n" +
        "    }\n" +
        "\n" +
        "    return 0;\n" +
        "}\n";


    static {
        EDITOR.setText(DEMO_TEXT);
        EDITOR.setLanguage(TextEditorLanguage.Cpp());
        EDITOR.setPaletteColor(TextEditorColor.currentLineNumber, 0xFFFFC080);
        EDITOR.scrollToLine(9, TextEditorScroll.alignMiddle);
    }

    public static void show(final ImBoolean showImColorTextEditWindow) {
        ImGui.setNextWindowSize(500, 400, ImGuiCond.FirstUseEver);
        if (ImGui.begin("Text Editor", showImColorTextEditWindow,
                ImGuiWindowFlags.HorizontalScrollbar | ImGuiWindowFlags.MenuBar)) {
            if (ImGui.beginMenuBar()) {
                if (ImGui.beginMenu("File")) {
                    if (ImGui.menuItem("Save")) {
                        String textToSave = EDITOR.getText();
                        /// save text....
                    }

                    ImGui.endMenu();
                }
                if (ImGui.beginMenu("Edit")) {
                    final boolean ro = EDITOR.isReadOnlyEnabled();
                    if (ImGui.menuItem("Read-only mode", "", ro)) {
                        EDITOR.setReadOnlyEnabled(!ro);
                    }

                    ImGui.separator();

                    if (ImGui.menuItem("Undo", "Ctrl-Z", !ro && EDITOR.canUndo())) {
                        EDITOR.undo();
                    }
                    if (ImGui.menuItem("Redo", "Ctrl-Y", !ro && EDITOR.canRedo())) {
                        EDITOR.redo();
                    }

                    ImGui.separator();

                    if (ImGui.menuItem("Copy", "Ctrl-C", EDITOR.anyCursorHasSelection())) {
                        EDITOR.copy();
                    }
                    if (ImGui.menuItem("Cut", "Ctrl-X", !ro && EDITOR.anyCursorHasSelection())) {
                        EDITOR.cut();
                    }
                    if (ImGui.menuItem("Delete", "Del", !ro && EDITOR.anyCursorHasSelection())) {
                        EDITOR.replaceTextInAllCursors("");
                    }
                    if (ImGui.menuItem("Paste", "Ctrl-V", !ro && ImGui.getClipboardText() != null)) {
                        EDITOR.paste();
                    }
                    if (ImGui.menuItem("Select All", "Ctrl-A", !EDITOR.isEmpty())) {
                        EDITOR.selectAll();
                    }

                    ImGui.endMenu();
                }

                ImGui.endMenuBar();
            }

            TextEditorCursorPosition cpos = EDITOR.getMainCursorPosition();

            String overwrite = EDITOR.isOverwriteEnabled() ? "Ovr" : "Ins";
            String canUndo = EDITOR.canUndo() ? "*" : " ";

            ImGui.text(cpos.line + "/" + cpos.column + " " + EDITOR.getLineCount() + " lines | " + overwrite + " | " + canUndo);

            EDITOR.render("TextEditor");

            ImGui.end();
        }
    }
}
