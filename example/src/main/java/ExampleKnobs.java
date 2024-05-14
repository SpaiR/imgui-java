import imgui.ImGui;
import imgui.extension.imguiknobs.ImGuiKnobs;
import imgui.extension.imguiknobs.flag.ImGuiKnobFlags;
import imgui.extension.imguiknobs.flag.ImGuiKnobVariant;
import imgui.flag.ImGuiCond;
import imgui.flag.ImGuiWindowFlags;
import imgui.type.ImBoolean;
import imgui.type.ImFloat;

public class ExampleKnobs {

    private static final ImFloat EXAMPLE_TICK_KNOB_VAL = new ImFloat(0);
    private static final ImFloat EXAMPLE_DOT_KNOB_VAL = new ImFloat(0);
    private static final ImFloat EXAMPLE_SPACE_KNOB_VAL = new ImFloat(0);
    private static final ImFloat EXAMPLE_WIPER_KNOB_VAL = new ImFloat(0);
    private static final ImFloat EXAMPLE_WIPER_DOT_KNOB_VAL = new ImFloat(0);
    private static final ImFloat EXAMPLE_WIPER_ONLY_KNOB_VAL = new ImFloat(0);
    private static final ImFloat EXAMPLE_STEPPED_KNOB_VAL = new ImFloat(0);

    public static void show(final ImBoolean showKnobsWindow) {
        ImGui.setNextWindowSize(200, 400, ImGuiCond.Once);
        ImGui.setNextWindowPos(ImGui.getMainViewport().getPosX() + 200, ImGui.getMainViewport().getPosY() + 200, ImGuiCond.Once);
        if (ImGui.begin("Knobs Demo Window", showKnobsWindow, ImGuiWindowFlags.NoResize | ImGuiWindowFlags.NoCollapse)) {
            ImGui.textWrapped("This is a demo for dear ImGui Knobs");
            if (ImGui.beginChild("Float Knobs")) {
                ImGuiKnobs.knobFloat("Example Tick Knob", EXAMPLE_TICK_KNOB_VAL, 0.0f, 10.0f, 0.2f, "%.1f", ImGuiKnobVariant.TICK, 50.0f, ImGuiKnobFlags.NONE, 1);

                ImGuiKnobs.knobFloat("Example Dot Knob", EXAMPLE_DOT_KNOB_VAL, 0.0f, 10.0f, 0.2f, "%.1f", ImGuiKnobVariant.DOT, 50.0f, ImGuiKnobFlags.NONE, 1);

                ImGuiKnobs.knobFloat("Example Space Knob", EXAMPLE_SPACE_KNOB_VAL, 0.0f, 10.0f, 0.2f, "%.1f", ImGuiKnobVariant.SPACE, 50.0f, ImGuiKnobFlags.NONE, 1);

                ImGuiKnobs.knobFloat("Example Wiper Knob", EXAMPLE_WIPER_KNOB_VAL, 0.0f, 10.0f, 0.2f, "%.1f", ImGuiKnobVariant.WIPER, 50.0f, ImGuiKnobFlags.NONE, 1);

                ImGuiKnobs.knobFloat("Example Wiper Dot Knob", EXAMPLE_WIPER_DOT_KNOB_VAL, 0.0f, 10.0f, 0.2f, "%.1f", ImGuiKnobVariant.WIPER_DOT, 50.0f, ImGuiKnobFlags.NONE, 1);

                ImGuiKnobs.knobFloat("Example Wiper Only Knob", EXAMPLE_WIPER_ONLY_KNOB_VAL, 0.0f, 10.0f, 0.2f, "%.1f", ImGuiKnobVariant.WIPER_ONLY, 50.0f, ImGuiKnobFlags.NONE, 1);

                ImGuiKnobs.knobFloat("Example Stepped Knob", EXAMPLE_STEPPED_KNOB_VAL, 0.0f, 10.0f, 0.2f, "%.1f", ImGuiKnobVariant.STEPPED, 50.0f, ImGuiKnobFlags.NONE, 1);
                ImGui.endChild();
            }

            ImGui.end();
        }
    }

}
