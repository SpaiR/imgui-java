import imgui.ImGui;
import imgui.extension.imguiknobs.ImGuiKnobs;
import imgui.extension.imguiknobs.flag.ImGuiKnobFlags;
import imgui.extension.imguiknobs.flag.ImGuiKnobVariant;
import imgui.flag.ImGuiCond;
import imgui.flag.ImGuiWindowFlags;
import imgui.type.ImBoolean;
import imgui.type.ImFloat;

public class ExampleKnobs {

    private static final ImFloat exampleTickKnobVal = new ImFloat(0);
    private static final ImFloat exampleDotKnobVal = new ImFloat(0);
    private static final ImFloat exampleSpaceKnobVal = new ImFloat(0);
    private static final ImFloat exampleWiperKnobVal = new ImFloat(0);
    private static final ImFloat exampleWiperDotKnobVal = new ImFloat(0);
    private static final ImFloat exampleWiperOnlyKnobVal = new ImFloat(0);
    private static final ImFloat exampleSteppedKnobVal = new ImFloat(0);

    public static void show(final ImBoolean showKnobsWindow) {
        ImGui.setNextWindowSize(200, 400, ImGuiCond.Once);
        ImGui.setNextWindowPos(ImGui.getMainViewport().getPosX() + 200, ImGui.getMainViewport().getPosY() + 200, ImGuiCond.Once);
        if (ImGui.begin("Knobs Demo Window", showKnobsWindow, ImGuiWindowFlags.NoResize | ImGuiWindowFlags.NoCollapse)) {
            ImGui.textWrapped("This is a demo for dear ImGui Knobs");
            if (ImGui.beginChild("Float Knobs")) {
                ImGuiKnobs.knobFloat("Example Tick Knob", exampleTickKnobVal, 0.0f, 10.0f, 0.2f, "%.1f", ImGuiKnobVariant.tick, 50.0f, ImGuiKnobFlags.none, 1);

                ImGuiKnobs.knobFloat("Example Dot Knob", exampleDotKnobVal, 0.0f, 10.0f, 0.2f, "%.1f", ImGuiKnobVariant.dot, 50.0f, ImGuiKnobFlags.none, 1);

                ImGuiKnobs.knobFloat("Example Space Knob", exampleSpaceKnobVal, 0.0f, 10.0f, 0.2f, "%.1f", ImGuiKnobVariant.space, 50.0f, ImGuiKnobFlags.none, 1);

                ImGuiKnobs.knobFloat("Example Wiper Knob", exampleWiperKnobVal, 0.0f, 10.0f, 0.2f, "%.1f", ImGuiKnobVariant.wiper, 50.0f, ImGuiKnobFlags.none, 1);

                ImGuiKnobs.knobFloat("Example Wiper Dot Knob", exampleWiperDotKnobVal, 0.0f, 10.0f, 0.2f, "%.1f", ImGuiKnobVariant.wiperDot, 50.0f, ImGuiKnobFlags.none, 1);

                ImGuiKnobs.knobFloat("Example Wiper Only Knob", exampleWiperOnlyKnobVal, 0.0f, 10.0f, 0.2f, "%.1f", ImGuiKnobVariant.wiperOnly, 50.0f, ImGuiKnobFlags.none, 1);

                ImGuiKnobs.knobFloat("Example Stepped Knob", exampleSteppedKnobVal, 0.0f, 10.0f, 0.2f, "%.1f", ImGuiKnobVariant.stepped, 50.0f, ImGuiKnobFlags.none, 1);
                ImGui.endChild();
            }

            ImGui.end();
        }
    }

}
