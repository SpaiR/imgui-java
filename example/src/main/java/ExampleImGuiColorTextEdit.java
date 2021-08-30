
import imgui.ImGui;
import imgui.extension.texteditor.TextEditor;
import imgui.extension.texteditor.TextEditorLanguageDefinition;
import imgui.extension.texteditor.flag.TextEditorPaletteIndex;
import imgui.flag.ImGuiWindowFlags;
import imgui.type.ImBoolean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ExampleImGuiColorTextEdit {

    private static final TextEditor editor;

    static {
        editor = new TextEditor();

        TextEditorLanguageDefinition lang = TextEditorLanguageDefinition.CPlusPlus();

        String[] ppnames = { "NULL", "PM_REMOVE",
            "ZeroMemory", "DXGI_SWAP_EFFECT_DISCARD", "D3D_FEATURE_LEVEL", "D3D_DRIVER_TYPE_HARDWARE", "WINAPI","D3D11_SDK_VERSION", "assert" };
        String[] ppvalues = {
            "#define NULL ((void*)0)",
                "#define PM_REMOVE (0x0001)",
                "Microsoft's own memory zapper function\n(which is a macro actually)\nvoid ZeroMemory(\n\t[in] PVOID  Destination,\n\t[in] SIZE_T Length\n); ",
                "enum DXGI_SWAP_EFFECT::DXGI_SWAP_EFFECT_DISCARD = 0",
                "enum D3D_FEATURE_LEVEL",
                "enum D3D_DRIVER_TYPE::D3D_DRIVER_TYPE_HARDWARE  = ( D3D_DRIVER_TYPE_UNKNOWN + 1 )",
                "#define WINAPI __stdcall",
                "#define D3D11_SDK_VERSION (7)",
                " #define assert(expression) (void)(                                                  \n",
            "    (!!(expression)) ||                                                              \n",
            "    (_wassert(_CRT_WIDE(#expression), _CRT_WIDE(__FILE__), (unsigned)(__LINE__)), 0) \n",
            " )"
        };

        // Adding custom preproc identifiers
        Map<String, String> preprocIdentifierMap = new HashMap<>();
        for (int i = 0; i < ppnames.length; ++i) {
            preprocIdentifierMap.put(ppnames[i], ppvalues[i]);
        }
        lang.setPreprocIdentifiers(preprocIdentifierMap);

        String[] identifiers = {
            "HWND", "HRESULT", "LPRESULT","D3D11_RENDER_TARGET_VIEW_DESC", "DXGI_SWAP_CHAIN_DESC","MSG","LRESULT","WPARAM", "LPARAM","UINT","LPVOID",
                "ID3D11Device", "ID3D11DeviceContext", "ID3D11Buffer", "ID3D11Buffer", "ID3D10Blob", "ID3D11VertexShader", "ID3D11InputLayout", "ID3D11Buffer",
                "ID3D10Blob", "ID3D11PixelShader", "ID3D11SamplerState", "ID3D11ShaderResourceView", "ID3D11RasterizerState", "ID3D11BlendState", "ID3D11DepthStencilState",
                "IDXGISwapChain", "ID3D11RenderTargetView", "ID3D11Texture2D", "TextEditor" };
        String[] idecls = {
            "typedef HWND_* HWND", "typedef long HRESULT", "typedef long* LPRESULT", "struct D3D11_RENDER_TARGET_VIEW_DESC", "struct DXGI_SWAP_CHAIN_DESC",
                "typedef tagMSG MSG\n * Message structure","typedef LONG_PTR LRESULT","WPARAM", "LPARAM","UINT","LPVOID",
                "ID3D11Device", "ID3D11DeviceContext", "ID3D11Buffer", "ID3D11Buffer", "ID3D10Blob", "ID3D11VertexShader", "ID3D11InputLayout", "ID3D11Buffer",
                "ID3D10Blob", "ID3D11PixelShader", "ID3D11SamplerState", "ID3D11ShaderResourceView", "ID3D11RasterizerState", "ID3D11BlendState", "ID3D11DepthStencilState",
                "IDXGISwapChain", "ID3D11RenderTargetView", "ID3D11Texture2D", "class TextEditor" };

        // Adding custom identifiers
        Map<String, String> identifierMap = new HashMap<>();
        for (int i = 0; i < ppnames.length; ++i) {
            identifierMap.put(identifiers[i], idecls[i]);
        }
        lang.setIdentifiers(identifierMap);

        editor.setLanguageDefinition(lang);

        // Adding error markers
        Map<Integer, String> errorMarkers = new HashMap<>();
        errorMarkers.put(1, "Expected '>'");
        editor.setErrorMarkers(errorMarkers);

        editor.setTextLines(new String[]{
            "#include <iostream",
            "",
            "int main() {",
            "   std::cout << \"Hello, World!\" << std::endl;",
            "}"
        });
    }

    public static void show(final ImBoolean showImColorTextEditWindow) {
        ImGui.setNextWindowSize(500, 400);
        if (ImGui.begin("Text Editor", showImColorTextEditWindow,
                ImGuiWindowFlags.HorizontalScrollbar | ImGuiWindowFlags.MenuBar)) {
            if (ImGui.beginMenuBar()) {
                if (ImGui.beginMenu("File")) {
                    if (ImGui.menuItem("Save")) {
                        String textToSave = editor.getText();
                        /// save text....
                    }

                    ImGui.endMenu();
                }
                if (ImGui.beginMenu("Edit")) {
                    boolean ro = editor.isReadOnly();
                    if (ImGui.menuItem("Read-only mode", "", ro))
                        editor.setReadOnly(!ro);
                    ImGui.separator();

                    if (ImGui.menuItem("Undo", "ALT-Backspace", !ro && editor.canUndo()))
                        editor.undo(1);
                    if (ImGui.menuItem("Redo", "Ctrl-Y", !ro && editor.canRedo()))
                        editor.redo(1);

                    ImGui.separator();

                    if (ImGui.menuItem("Copy", "Ctrl-C", editor.hasSelection()))
                        editor.copy();
                    if (ImGui.menuItem("Cut", "Ctrl-X", !ro && editor.hasSelection()))
                        editor.cut();
                    if (ImGui.menuItem("Delete", "Del", !ro && editor.hasSelection()))
                        editor.delete();
                    if (ImGui.menuItem("Paste", "Ctrl-V", !ro && ImGui.getClipboardText() != null))
                        editor.paste();

                    ImGui.endMenu();
                }

                ImGui.endMenuBar();
            }

            int cposX = editor.getCursorPositionLine();
            int cposY = editor.getCursorPositionColumn();

            String overwrite = editor.isOverwrite() ? "Ovr" : "Ins";
            String canUndo = editor.canUndo() ? "*" : " ";

            ImGui.text(cposX + "/" + cposY + " " + editor.getTotalLines() + " lines | " + overwrite + " | " + canUndo);

            editor.render("TextEditor");

            ImGui.end();
        }
    }

}
