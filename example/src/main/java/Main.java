import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.app.Application;
import imgui.app.Configuration;
import imgui.extension.imnodes.ImNodes;
import imgui.flag.ImGuiConfigFlags;
import imgui.flag.ImGuiInputTextFlags;
import imgui.type.ImString;

public class Main extends Application {
    private final ImString str = new ImString(5);
    private final float[] flt = new float[1];
    private int count = 0;

    @Override
    protected void configure(final Configuration config) {
        config.setTitle("Example Application");
    }

    @Override
    protected void initImGui(final Configuration config) {
        super.initImGui(config);

        final ImGuiIO io = ImGui.getIO();
        io.setIniFilename(null);                                // We don't want to save .ini file
        io.addConfigFlags(ImGuiConfigFlags.NavEnableKeyboard);  // Enable Keyboard Controls
        io.addConfigFlags(ImGuiConfigFlags.DockingEnable);      // Enable Docking
        io.addConfigFlags(ImGuiConfigFlags.ViewportsEnable);    // Enable Multi-Viewport / Platform Windows
        io.setConfigViewportsNoTaskBarIcon(true);

        ImNodes.initialize();
    }

    @Override
    protected void disposeImGui() {
        ImNodes.shutdown();
        super.disposeImGui();
    }

    @Override
    public void process() {
        ImGui.text("Hello, World!");
        if (ImGui.button("Save")) {
            count++;
        }
        ImGui.sameLine();
        ImGui.text(String.valueOf(count));
        ImGui.inputText("string", str, ImGuiInputTextFlags.CallbackResize);
        ImGui.sliderFloat("float", flt, 0, 1);
        ImGui.separator();
        ImGui.text("Extra");
        Extra.show(this);
    }

    public static void main(final String[] args) {
        launch(new Main());
    }
}
