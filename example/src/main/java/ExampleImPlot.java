import imgui.ImColor;
import imgui.extension.implot.ImPlot;
import imgui.extension.implot.ImPlotPoint;
import imgui.extension.implot.ImPlotSpec;
import imgui.extension.implot.flag.ImPlotMarker;
import imgui.flag.ImGuiCond;
import imgui.internal.ImGui;
import imgui.type.ImBoolean;

import java.awt.Desktop;
import java.net.URI;

public class ExampleImPlot {
    private static final String URL = "https://github.com/epezent/implot/tree/v1.0";
    private static final ImBoolean showDemo = new ImBoolean(false);

    private static final int[] xs = {0, 1, 2, 3, 4, 5};
    private static final int[] ys = {0, 1, 2, 3, 4, 5};
    private static final int[] ys1 = {0, 0, 1, 2, 3, 4};
    private static final int[] ys2 = {1, 2, 3, 4, 5, 6};

    // Showcase for implot v1.0 PR #672: per-index LineColors, MarkerFillColors, and MarkerSizes.
    private static final ImPlotSpec perIndexSpec;

    static {
        ImPlot.createContext();

        perIndexSpec = new ImPlotSpec();
        perIndexSpec.setMarker(ImPlotMarker.Circle);
        perIndexSpec.setLineColors(new int[]{
            ImColor.rgb(239, 83, 80),   // red
            ImColor.rgb(255, 167, 38),  // orange
            ImColor.rgb(255, 238, 88),  // yellow
            ImColor.rgb(102, 187, 106), // green
            ImColor.rgb(66, 165, 245),  // blue
            ImColor.rgb(171, 71, 188),  // purple
        });
        perIndexSpec.setMarkerFillColors(new int[]{
            ImColor.rgb(239, 83, 80),
            ImColor.rgb(255, 167, 38),
            ImColor.rgb(255, 238, 88),
            ImColor.rgb(102, 187, 106),
            ImColor.rgb(66, 165, 245),
            ImColor.rgb(171, 71, 188),
        });
        perIndexSpec.setMarkerSizes(new float[]{4f, 6f, 8f, 10f, 12f, 14f});
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

            if (showDemo.get()) {
                ImPlot.showDemoWindow(showDemo);
            } else {
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

                if (ImPlot.beginPlot("Example Piechart")) {
                    ImPlot.plotPieChart(new String[]{"1", "2", "3", "4", "5", "6"}, xs, .5, .5, .4);
                    ImPlot.endPlot();
                }

                if (ImPlot.beginPlot("Example Heatmap")) {
                    ImPlot.plotHeatmap("Heatmap", new int[]{1, 3, 6, 2, 8, 5, 4, 3}, 2, 4, 0, 0, "%d", new ImPlotPoint(0, 0), new ImPlotPoint(10, 10));
                    ImPlot.endPlot();
                }

                // ImPlot v1.0 PR #672: per-index color/size via ImPlotSpec.
                if (ImPlot.beginPlot("Per-Index Colors (v1.0 PR #672)")) {
                    ImPlot.plotLine("Rainbow", xs, ys, perIndexSpec);
                    ImPlot.endPlot();
                }
            }
        }

        ImGui.end();
    }
}
