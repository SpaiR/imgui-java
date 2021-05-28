package imgui.extension.imguizmo;

import imgui.ImDrawList;
import imgui.ImGui;

public final class ImGuizmo {
    private static final float[] MATRICES = new float[4 * 16]; //for drawCubes()

    private ImGuizmo() {
    }

    /*JNI
        #include "_imguizmo.h"
    */

    private static native void nEnabled(boolean enabled); /*
        ImGuizmo::Enable(enabled);
    */

    /**
     * Enable/Disable the gizmo.
     */
    public static void setEnabled(final boolean isEnabled) {
        nEnabled(isEnabled);
    }

    private static native boolean nIsUsing(); /*
        return ImGuizmo::IsUsing();
    */

    /**
     * Checks to see if we're using the Gizmo.
     */
    public static boolean isUsing() {
        return nIsUsing();
    }

    private static native boolean nIsOver(); /*
        return ImGuizmo::IsOver();
    */

    /**
     * Checks to see if we're over the Gizmo.
     */
    public static boolean isOver() {
        return nIsOver();
    }

    private static native void nSetDrawList(long pointer); /*
        ImGuizmo::SetDrawlist((ImDrawList*)pointer);
    */

    /**
     * Setting the draw list of the given Gizmo.
     *
     * @param drawList the target drawlist
     */
    public static void setDrawList(final ImDrawList drawList) {
        nSetDrawList(drawList.ptr);
    }

    /**
     * Setting the default window drawlist.
     */
    public static void setDrawList() {
        setDrawList(ImGui.getWindowDrawList());
    }

    /**
     * Starts the next frame for the Gizmo.
     * Call this after you've called ImGui.beginFrame().
     */
    public static native void beginFrame(); /*
        ImGuizmo::BeginFrame();
    */

    private static native void nDecomposeMatrixToComponents(float[] matrix, float[] translation, float[] rotation, float[] scale); /*
        ImGuizmo::DecomposeMatrixToComponents(matrix, translation, rotation, scale);
    */

    /**
     * Helper function for manually editing Translation/Rotation/Scale with an input float.
     * Translation, Rotation and Scale float points to 3 floats each.
     * Angles are in degrees (more suitable for human editing).
     *
     * @param modelMatrix the model matrix
     * @param translation the model translation
     * @param rotation    the model rotation
     * @param scale       the model scale
     * @see <a target="_blank" href="https://github.com/CedricGuillemet/ImGuizmo">Reference Page</a>
     */
    public static void decomposeMatrixToComponents(final float[] modelMatrix, final float[] translation, final float[] rotation, final float[] scale) {
        nDecomposeMatrixToComponents(modelMatrix, translation, rotation, scale);
    }

    private static native void nRecomposeMatrixFromComponents(float[] matrix, float[] translation, float[] rotation, float[] scale); /*
        ImGuizmo::RecomposeMatrixFromComponents(translation, rotation, scale, matrix);
    */

    /**
     * Helper function for manually editing Translation/Rotation/Scale with an input float.
     * Translation, Rotation and Scale float points to 3 floats each.
     * Angles are in degrees (more suitable for human editing).
     *
     * @param modelMatrix the model matrix
     * @param translation the model translation
     * @param rotation    the model rotation
     * @param scale       the model scale
     * @see <a target="_blank" href="https://github.com/CedricGuillemet/ImGuizmo">Reference Page</a>
     */
    public static void recomposeMatrixFromComponents(final float[] modelMatrix, final float[] translation, final float[] rotation, final float[] scale) {
        nRecomposeMatrixFromComponents(modelMatrix, translation, rotation, scale);
    }

    public static native void nSetRect(float x, float y, float width, float height); /*
        ImGuizmo::SetRect(x, y, width, height);
    */

    /**
     * This will set the rect position.
     *
     * @param x      x coordinate of the rectangle
     * @param y      y coordinate of the rectangle
     * @param width  width of the rectangle
     * @param height height of the rectangle
     */
    public static void setRect(final float x, final float y, final float width, final float height) {
        nSetRect(x, y, width, height);
    }

    private static native void nSetOrthographic(boolean ortho); /*
        ImGuizmo::SetOrthographic(ortho);
    */

    /**
     * Making sure if we're set to ortho or not.
     */
    public static void setOrthographic(final boolean ortho) {
        nSetOrthographic(ortho);
    }

    private static native void nDrawCubes(float[] view, float[] projection, float[] matrices, int matrixCount); /*
        ImGuizmo::DrawCubes(view, projection, matrices, matrixCount);
    */

    /**
     * Drawing an arbitrary cube in the world.
     *
     * @param view         target camera view
     * @param projection   target camera projection
     * @param cubeMatrices cube matrices (max. 4 cubes allowed)
     */
    public static void drawCubes(final float[] view, final float[] projection, final float[]... cubeMatrices) {
        if (cubeMatrices.length > 4) {
            throw new IllegalArgumentException("Drawing cubes with ImGuizmo only supports up to 4 cubes because it should only be used for debugging purposes");
        }

        for (int i = 0, cubeMatricesLength = cubeMatrices.length; i < cubeMatricesLength; i++) {
            final float[] cubeMatrix = cubeMatrices[i];
            System.arraycopy(cubeMatrix, 0, MATRICES, i * cubeMatrix.length, cubeMatrix.length);
        }

        nDrawCubes(view, projection, MATRICES, cubeMatrices.length);
    }

    private static native void nDrawGrid(float[] view, float[] projection, float[] matrix, int gridSize); /*
        ImGuizmo::DrawGrid(view, projection, matrix, gridSize);
    */

    /**
     * Drawing a grid to the world (should only be used for debugging purposes).
     *
     * @param view       target camera view
     * @param projection target camera projection
     * @param matrix     grid matrix
     * @param gridSize   grid size
     */
    public static void drawGrid(final float[] view, final float[] projection, final float[] matrix, final int gridSize) {
        nDrawGrid(view, projection, matrix, gridSize);
    }

    private static native void nManipulate(float[] view, float[] projection, int operation, int mode, float[] matrix); /*
        ImGuizmo::Manipulate(view, projection, (ImGuizmo::OPERATION) operation, (ImGuizmo::MODE) mode, matrix);
    */

    private static native void nManipulate(float[] view, float[] projection, int operation, int mode, float[] matrix, float[] snap); /*
        ImGuizmo::Manipulate(view, projection, (ImGuizmo::OPERATION) operation, (ImGuizmo::MODE) mode, matrix, NULL, snap);
    */

    private static native void nManipulate(float[] view, float[] projection, int operation, int mode, float[] matrix, float[] snap, float[] bounds); /*
        ImGuizmo::Manipulate(view, projection, (ImGuizmo::OPERATION) operation, (ImGuizmo::MODE) mode, matrix, NULL, snap, bounds, NULL);
    */

    private static native void nManipulate(float[] view, float[] projection, int operation, int mode, float[] matrix, float[] snap, float[] bounds, float[] boundsSnap); /*
        ImGuizmo::Manipulate(view, projection, (ImGuizmo::OPERATION) operation, (ImGuizmo::MODE) mode, matrix, NULL, snap, bounds, boundsSnap);
    */

    private static native void nManipulate(float[] view, float[] projection, int operation, int mode, float[] matrix, float[] deltaMatrix, float[] snap, float[] bounds, float[] boundsSnap); /*
        ImGuizmo::Manipulate(view, projection, (ImGuizmo::OPERATION) operation, (ImGuizmo::MODE) mode, matrix, deltaMatrix, snap, bounds, boundsSnap);
    */

    /**
     * Manipulating the given model matrix.
     *
     * @param view        target camera view
     * @param projection  target camera projection
     * @param modelMatrix model matrix
     * @param operation   target operation
     * @param mode        target mode
     */
    public static void manipulate(final float[] view, final float[] projection, final float[] modelMatrix, final int operation, final int mode) {
        nManipulate(view, projection, operation, mode, modelMatrix);
    }

    /**
     * Manipulating the given model matrix with snap feature enabled!
     *
     * @param view        target camera view
     * @param projection  target camera projection
     * @param modelMatrix model matrix
     * @param operation   target operation
     * @param mode        target mode
     * @param snap        snap value
     */
    public static void manipulate(final float[] view, final float[] projection, final float[] modelMatrix, final int operation, final int mode, final float[] snap) {
        nManipulate(view, projection, operation, mode, modelMatrix, snap);
    }

    /**
     * Manipulating the given model matrix with snap and bounds feature enabled!
     *
     * @param view        target camera view
     * @param projection  target camera projection
     * @param modelMatrix model matrix
     * @param operation   target operation
     * @param mode        target mode
     * @param snap        snap value
     * @param bounds      bounds value
     */
    public static void manipulate(final float[] view, final float[] projection, final float[] modelMatrix, final int operation, final int mode, final float[] snap, final float[] bounds) {
        nManipulate(view, projection, operation, mode, modelMatrix, snap, bounds);
    }

    /**
     * Manipulating the given model matrix with snap and bounds(snap) feature enabled!
     *
     * @param view        target camera view
     * @param projection  target camera projection
     * @param modelMatrix model matrix
     * @param operation   target operation
     * @param mode        target mode
     * @param snap        snap value
     * @param bounds      bounds value
     * @param boundsSnap  bounds snap value
     */
    public static void manipulate(final float[] view, final float[] projection, final float[] modelMatrix, final int operation, final int mode, final float[] snap, final float[] bounds, final float[] boundsSnap) {
        nManipulate(view, projection, operation, mode, modelMatrix, snap, bounds, boundsSnap);
    }

    /**
     * Manipulating the given model matrix with delta matrix
     *
     * @param view        target camera view
     * @param projection  target camera projection
     * @param modelMatrix model matrix
     * @param deltaMatrix delta matrix
     * @param operation   target operation
     * @param mode        target mode
     * @param snap        snap value
     * @param bounds      bounds value
     * @param boundsSnap  bounds snap value
     */
    public static void manipulate(final float[] view, final float[] projection, final float[] modelMatrix, final float[] deltaMatrix, final int operation, final int mode, final float[] snap, final float[] bounds, final float[] boundsSnap) {
        nManipulate(view, projection, operation, mode, modelMatrix, deltaMatrix, snap, bounds, boundsSnap);
    }

    private static native void nViewManipulate(float[] view, float length, float[] position, float[] size, int color); /*
        ImGuizmo::ViewManipulate(view, length, ImVec2(position[0], position[1]), ImVec2(size[0], size[1]), (ImU32) color);
    */

    /**
     * Manipulating the view
     *
     * @param view     target camera view
     * @param length   camera distance/length
     * @param position position
     * @param size     size
     * @param color    color
     */
    public static void viewManipulate(final float[] view, final float length, final float[] position, final float[] size, final int color) {
        nViewManipulate(view, length, position, size, color);
    }

    private static native void nSetId(int id); /*
        ImGuizmo::SetID(id);
    */

    /**
     * This will update the current id
     */
    public static void setId(final int id) {
        nSetId(id);
    }

    private static native boolean nIsOver(int operation); /*
        return ImGuizmo::IsOver((ImGuizmo::OPERATION) operation);
    */

    /**
     * Checks if we're over the current operation
     *
     * One of:
     * <table summary="">
     *     <tr>
     *         <td>{@link imgui.extension.imguizmo.flag.Operation#ROTATE}</td>
     *         <td>{@link imgui.extension.imguizmo.flag.Operation#SCALE}</td>
     *         <td>{@link imgui.extension.imguizmo.flag.Operation#TRANSLATE}</td>
     *     </tr>
     * </table>
     *
     * @param operation target
     */
    public static boolean isOver(final int operation) {
        return nIsOver(operation);
    }

    private static native void nSetGizmoSizeClipSpace(float value); /*
        ImGuizmo::SetGizmoSizeClipSpace(value);
    */

    private static native void nAllowAxisFlip(boolean value); /*
        ImGuizmo::AllowAxisFlip(value);
     */

    /**
     * This will update the current axis flip value
     */
    public static void setAllowAxisFlip(final boolean value) {
        nAllowAxisFlip(value);
    }
}
