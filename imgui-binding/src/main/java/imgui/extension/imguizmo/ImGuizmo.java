package imgui.extension.imguizmo;

import imgui.ImDrawList;
import imgui.ImGui;
import imgui.ImVec2;


import java.util.List;

public class ImGuizmo {
    private static final boolean[] buffer = new boolean[1];
    private static final float[] matrixBuffer = new float[16];
    private static final float[] positionBuffer = new float[3];
    private static final float[] rotationBuffer = new float[3];
    private static final float[] scaleBuffer = new float[3];

    /*JNI
       #include <imgui.h>
       #include <ImGuizmo.h>
       #include "jni_common.h"
    */
    private native static void nEnabled(boolean[] buffer); /*
        ImGuizmo::Enable(&buffer[0]);
    */

    /**
     * This will set the orthographic view
     */
    public static void setEnabled(boolean isEnabled) {
        buffer[0] = isEnabled;
        nEnabled(buffer);
    }

    private native static void nIsUsing(boolean[] buffer); /*
        buffer[0] = ImGuizmo::IsUsing();
    */

    /**
     * Natively checks to see if we're using the guizmo
     */
    public static boolean isUsing() {
        nIsUsing(buffer);
        return buffer[0];
    }

    private native static void nIsOver(boolean[] buffer); /*
        buffer[0] = ImGuizmo::IsOver();
    */

    /**
     * Natively checks to see if we're over the guizmo
     */
    public static boolean isOver() {
        nIsOver(buffer);
        return buffer[0];
    }

    private native static void nSetDrawList(long pointer); /*
        ImGuizmo::SetDrawlist((ImDrawList*)pointer);
    */

    /**
     * This will set the draw list of the given guizmo
     */
    public static void setDrawList(ImDrawList drawList) {
        nSetDrawList(drawList.ptr);
    }


    /**
     * This will set the default window drawlist value
     */
    public static void setDrawList() {
        setDrawList(ImGui.getWindowDrawList());
    }

    private native static void nBeginFrame(); /*
        ImGuizmo::BeginFrame();
    */

    /**
     * Starts the next frame for the guizmo
     */
    public static void beginFrame() {
        nBeginFrame();
    }

    private native static void nDecomposeMatrixToComponents(float[] matrix, float[] translation, float[] rotation, float[] scale); /*
        ImGuizmo::DecomposeMatrixToComponents(matrix, translation, rotation, scale);
    */

    /**
     * This will allow for manual editing of the guizmos
     */
    public static void decomposeMatrixToComponents(float[] matrix, float[] translation, float[] rotation, float[] scale) {

        nDecomposeMatrixToComponents(matrixBuffer, positionBuffer, rotationBuffer, scaleBuffer);


    }

    private native static void nRecomposeMatrixFromComponents(float[] translation, float[] rotation, float[] scale, float[] matrix); /*
        ImGuizmo::RecomposeMatrixFromComponents(translation, rotation, scale, matrix);
    */


    /**
     * This will allow for manual editing of the guizmos
     */
    public static void recomposeMatrixFromComponents(float[] matrix, float[] translation, float[] rotation, float[] scale) {


        nRecomposeMatrixFromComponents(positionBuffer, rotationBuffer, scaleBuffer, matrixBuffer);


    }

    public native static void nSetRect(float x, float y, float width, float height); /*
        ImGuizmo::SetRect(x, y, width, height);
    */

    /**
     * This will set the rect position
     */
    public static void setRect(float x, float y, float width, float height) {
        nSetRect(x, y, width, height);
    }


    /**
     * This will set the rect position to the default current rect position
     */
    public static void setRect() {
        ImVec2 pos = ImGui.getWindowPos();
        ImVec2 size = ImGui.getWindowSize();
        setRect(pos.x, pos.y, size.x, size.y);
    }


    private native static void nSetOrthographic(boolean ortho); /*
        ImGuizmo::SetOrthographic(ortho);
    */

    /**
     * This will make sure we're set to ortho or not
     */
    public static void setOrthographic(boolean ortho) {
        nSetOrthographic(ortho);
    }

    private native static void nDrawCubes(float[] view, float[] projection, float[] matrices, int matrixCount); /*
        ImGuizmo::DrawCubes(view, projection, matrices, matrixCount);
    */

    /**
     * This will allow us to draw an arbitrary cube in the world.
     */
    public static void drawCubes(float[] view, float[] projection, float[]... cubeMatrices) {
        float[] matrices = new float[cubeMatrices.length * 16];
        int index = 0;
        for (int i = 0; i < matrices.length; i++) {
            for (int j = 0; j < 16; j++) {
                matrices[index++] = cubeMatrices[i][j];
            }
        }
        nDrawCubes(view, projection, matrices, cubeMatrices.length);

    }

    private native static void nDrawGrid(float[] view, float[] projection, float[] matrix, int gridSize); /*
        ImGuizmo::DrawGrid(view, projection, matrix, gridSize);
    */

    /**
     * This will allow us to draw an arbitrary cube in the world.
     */
    public static void drawGrid(float[] view, float[] projection, float[] matrix, int gridSize) {
        nDrawGrid(view, projection, matrix, gridSize);
    }

    private native static void nManipulate(float[] view, float[] projection, int operation, int mode, float[] matrix);/*
            ImGuizmo::Manipulate(view, projection, (ImGuizmo::OPERATION) operation, (ImGuizmo::MODE) mode, matrix);
    */

    /**
     * This code with actually manipulate the gizmo data
     */
    public static void manipulate(float[] view, float[] projection, float[] modelMatrix, Operation operation, Mode mode) {
        nManipulate(view, projection, operation.ordinal(), mode.ordinal(), modelMatrix);
    }

    private native static void nViewManipulate(float[] view, float length, float[] position, float[] size, int color);/*
        ImGuizmo::ViewManipulate(view, length, ImVec2(position[0], position[1]), ImVec2(size[0], size[1]), (ImU32) color);
    */

    /**
     * This will do the view manipulation
     */
    public static void viewManipulate(float[] view, float length, float[] position, float[] size, int color) {
        nViewManipulate(view, length, position, size, color);
    }

    private native static void nSetId(int id);/*
        ImGuizmo::SetID(id);
    */

    /**
     * This will update the current id
     */
    public static void setId(int id) {
        nSetId(id);
    }

    private native static boolean nIsOver(int operation);/*
        return ImGuizmo::IsOver((ImGuizmo::OPERATION) operation);
    */

    /**
     * Checks if we're over the current operation
     */
    public static boolean isOver(Operation operation) {
        return nIsOver(operation.ordinal());
    }

    private native static void nSetGizmoSizeClipSpace(float value);/*
        ImGuizmo::SetGizmoSizeClipSpace(value);
    */

    private native static void nAllowAxisFlip(boolean value);/*
        ImGuizmo::AllowAxisFlip(value);
     */

    /**
     * This will update the current axis flip value
     */
    public static void setAllowAxisFlip(boolean value) {
        nAllowAxisFlip(value);
    }

}
