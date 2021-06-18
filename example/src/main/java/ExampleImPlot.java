import imgui.extension.implot.ImPlot;
import imgui.flag.ImGuiCond;
import imgui.internal.ImGui;
import imgui.type.ImBoolean;

import java.awt.*;
import java.net.URI;

public class ExampleImPlot {
    private static final String URL = "https://github.com/epezent/implot";

    public static void show(ImBoolean showImPlotWindow) {
        ImGui.setNextWindowSize(500, 400, ImGuiCond.Once);
        ImGui.setNextWindowPos(ImGui.getMainViewport().getPosX() + 100, ImGui.getMainViewport().getPosY() + 100, ImGuiCond.Once);
        if (ImGui.begin("ImPlot Demo", showImPlotWindow))
        {
            ImGui.text("This a demo for ImPlot");

            final Integer[] xs = {0, 1, 2, 3, 4, 5};
            final Integer[] ys = {0, 1, 2, 3, 4, 5};

            if (ImPlot.beginPlot("Example Plot")) {

            ImPlot.plotLine("Line", xs, ys);
            ImPlot.plotBars("Bars", xs, ys);

            ImPlot.endPlot();
            }

            if (ImPlot.beginPlot("Example Scatterplot")) {
                ImPlot.plotScatter("Scatter", xs, ys);
                ImPlot.endPlot();
            }
        }

        ImGui.end();
    }
}
