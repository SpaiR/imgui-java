import imgui.extension.implot.ImPlot;
import imgui.flag.ImGuiCond;
import imgui.internal.ImGui;
import imgui.type.ImBoolean;

import java.awt.Desktop;
import java.net.URI;

public class ExampleImPlot {
    private static final String URL = "https://github.com/epezent/implot/tree/555ff68";
    private static final ImBoolean showDemo = new ImBoolean(false);

    private static final Integer[] xs = {0, 1, 2, 3, 4, 5};
    private static final Integer[] ys = {0, 1, 2, 3, 4, 5};
    private static final Integer[] ys1 = {0, 0, 1, 2, 3, 4};
    private static final Integer[] ys2 = {1, 2, 3, 4, 5, 6};

    static {
        ImPlot.createContext();
    }

    public static void show(ImBoolean showImPlotWindow) {
        ImGui.setNextWindowSize(500, 400, ImGuiCond.Once);
        ImGui.setNextWindowPos(ImGui.getMainViewport().getPosX() + 100, ImGui.getMainViewport().getPosY() + 100, ImGuiCond.Once);
        if (ImGui.begin("ImPlot Demo", showImPlotWindow)) {
            ImGui.text("This a demo for ImPlot");

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

            ImGui.checkbox("Show ImPlot Built-In Demo", showDemo);

            if (ImPlot.beginPlot("Example Plot")) {
                ImPlot.plotShaded("Shaded", xs, ys1, ys2);
                ImPlot.plotLine("Line", xs, ys);
                ImPlot.plotBars("Bars", xs, ys);
                ImPlot.endPlot();
            }

            if (ImPlot.beginPlot("Example Scatterplot")) {
                ImPlot.plotScatter("Scatter", xs, ys);
                ImPlot.endPlot();
            }

            if (showDemo.get()) {
                ImPlot.showDemoWindow(showDemo);
            }
        }

        ImGui.end();
    }
}
