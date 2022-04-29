import imgui.*;
import imgui.flag.*;
import imgui.type.ImBoolean;

import java.awt.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class ExampleCanvaEditor {
    private static final List<ImVec2> pointList = new ArrayList<>();
    private static final ImVec2 scrolling = new ImVec2();

    private static boolean addingLine = false;
    public static float thickness = 2.0f;

    /**
     * Computes the floating-point remainder of a / b.
     * @param a float
     * @param b float
     * @return computed floating-point remainder of a / b
     */
    private static float fmodf(float a, float b) {
        int result = (int) Math.floor(a / b);
        return a - result * b;
    }

    public static void show(final ImBoolean showCanvaWindow) {
        imgui.internal.ImGui.setNextWindowSize(500, 400, ImGuiCond.Once);
        imgui.internal.ImGui.setNextWindowPos(imgui.internal.ImGui.getMainViewport().getPosX() + 100,
            imgui.internal.ImGui.getMainViewport().getPosY() + 200, ImGuiCond.Once);

        if (ImGui.begin("Canva Demo Window", showCanvaWindow)) {
            imgui.internal.ImGui.text("This a demo for ImGui Canva editor");
            imgui.internal.ImGui.alignTextToFramePadding();
            imgui.internal.ImGui.text("Repo:");
            imgui.internal.ImGui.sameLine();
            imgui.internal.ImGui.text("Mouse Left: drag to add lines,\nMouse Right: drag to scroll, click for context menu.");

            // Typically, you would use a BeginChild()/EndChild() pair to benefit from a clipping region + own scrolling.
            // Here we demonstrate that this can be replaced by simple offsetting + custom drawing + PushClipRect/PopClipRect() calls.
            // To use a child window instead we could use, e.g:
            //      ImGui.pushStyleVar(ImGuiStyleVar.WindowPadding, 0, 0);      // Disable padding
            //      ImGui.pushStyleColor(ImGuiCol.ChildBg, ImColor.intToColor(50, 50, 50, 255));  // Set a background color
            //      ImGui.beginChild("canvas", new ImVec2(0.0f, 0.0f), true, ImGuiWindowFlags.NoMove);
            //      ImGui.popStyleColor();
            //      ImGui.popStyleVar();
            //      [...]
            //      ImGui.endChild();

            // Using InvisibleButton() as a convenience 1) it will advance the layout cursor and 2) allows us to use IsItemHovered()/IsItemActive()
            ImVec2 canvasP0 = ImGui.getCursorScreenPos();      // ImDrawList API uses screen coordinates!
            ImVec2 canvasSize = ImGui.getContentRegionAvail();   // Resize canvas to what's available

            if (canvasSize.x < 50.0f) {
                canvasSize.x = 50.0f;
            }

            if (canvasSize.y < 50.0f) {
                canvasSize.y = 50.0f;
            }

            ImVec2 canvasP1 = new ImVec2(canvasP0.x + canvasSize.x, canvasP0.y + canvasSize.y);

            // Draw border and background color
            ImGuiIO io = ImGui.getIO();
            ImDrawList drawList = ImGui.getWindowDrawList();
            drawList.addRectFilled(canvasP0.x, canvasP0.y, canvasP1.x, canvasP1.y,
                ImColor.intToColor(50, 50, 50, 255));
            drawList.addRect(canvasP0.x, canvasP0.y, canvasP1.x, canvasP1.y,
                ImColor.intToColor(255, 255, 255, 255));

            // This will catch our interactions
            ImGui.invisibleButton("canvas", canvasSize.x, canvasSize.y,
                ImGuiButtonFlags.MouseButtonLeft | ImGuiButtonFlags.MouseButtonRight);

            boolean isHovered = ImGui.isItemHovered(); // Hovered
            boolean isActive  = ImGui.isItemActive();  // Held

            ImVec2 origin = new ImVec2(canvasP0.x + scrolling.x, canvasP0.y + scrolling.y); // Lock scrolled origin

            ImVec2 mousePosInCanvas = new ImVec2(io.getMousePos().x - origin.x, io.getMousePos().y - origin.y);

            // Add first and second point
            if (isHovered && !addingLine && ImGui.isMouseClicked(ImGuiMouseButton.Left)) {
                pointList.add(mousePosInCanvas);
                pointList.add(mousePosInCanvas);
                addingLine = true;
            }
            if (addingLine) {
                pointList.set(pointList.size() - 1, mousePosInCanvas);
                if (!ImGui.isMouseDown(ImGuiMouseButton.Left)) {
                    addingLine = false;
                }
            }

            // Pan (we use a zero mouse threshold when there's no context menu)
            // You may decide to make that threshold dynamic based on whether the mouse is hovering something etc.
            float mouseThresholdForPan = -1.0f;
            if (isActive && ImGui.isMouseDragging(ImGuiMouseButton.Right, mouseThresholdForPan)) {
                scrolling.x += io.getMouseDelta().x;
                scrolling.y += io.getMouseDelta().y;
            }

            // Context menu (under default mouse threshold)
            ImVec2 dragDelta = ImGui.getMouseDragDelta(ImGuiMouseButton.Right);
            if (dragDelta.x == 0.0f && dragDelta.y == 0.0f)
                ImGui.openPopupOnItemClick("context", ImGuiPopupFlags.MouseButtonRight);

            // Draw grid + all lines in the canvas
            drawList.pushClipRect(canvasP0.x, canvasP0.y, canvasP1.x, canvasP1.y, false);
            float GRID_STEP = 64.0f;
            for (float x = fmodf(scrolling.x, GRID_STEP); x < canvasSize.x; x += GRID_STEP) {
                drawList.addLine(canvasP0.x + x, canvasP0.y, canvasP0.x + x, canvasP1.y,
                    ImColor.intToColor(200, 200, 200, 40));
            }
            for (float y = fmodf(scrolling.y, GRID_STEP); y < canvasSize.y; y += GRID_STEP) {
                drawList.addLine(canvasP0.x, canvasP0.y + y, canvasP1.x, canvasP0.y + y,
                    ImColor.intToColor(200, 200, 200, 40));
            }
            for (int n = 0; n < pointList.size(); n += 2) {
                drawList.addLine(origin.x + pointList.get(n).x, origin.y + pointList.get(n).y,
                    origin.x + pointList.get(n + 1).x, origin.y + pointList.get(n + 1).y,
                    ImColor.intToColor(255, 255, 0, 255), thickness);
            }
            drawList.popClipRect();

            // Menu properties
            if (ImGui.beginPopup("context")) {
                addingLine = false;
                if (ImGui.menuItem("Remove one", "", false, pointList.size() > 0)) {
                    pointList.remove(pointList.size() - 1);
                    pointList.remove(pointList.size() - 1);
                }
                if (ImGui.menuItem("Remove all", "", false, pointList.size() > 0)) {
                    pointList.clear();
                }
                ImGui.endPopup();
            }
        }

        ImGui.end();
    }
}
