import imgui.ImGui;
import imgui.ImGuiInputTextCallbackData;
import imgui.callback.ImGuiInputTextCallback;
import imgui.flag.ImGuiInputTextFlags;
import imgui.type.ImBoolean;
import imgui.type.ImString;

public class ExampleInputTextCallback {
    public static final ImString str = new ImString();
    public static final ImGuiInputTextCallback callback = new ImGuiInputTextCallback() {
        @Override
        public void accept(ImGuiInputTextCallbackData imGuiInputTextCallbackData) {
            System.out.print("InputText Callback:" + imGuiInputTextCallbackData.getBuf());
        }
    };

    public static void show(final ImBoolean showInputTextCallback) {
        if(ImGui.begin("ExampleInputTextCallback", showInputTextCallback)) {
            ImGui.inputText("InputText Callback", str,ImGuiInputTextFlags.CallbackAlways | ImGuiInputTextFlags.CallbackCompletion, callback);
        }
        ImGui.end();
    }
}
