package imgui.extension.imguizmo;

import imgui.ImDrawList;
import imgui.ImVec2;
import imgui.internal.ImGuiContext;

public final class ImGuizmo {
    private ImGuizmo() {
    }

    /*JNI
        #include "_imguizmo.h"
     */

    /**
     * Call inside your own window and before Manipulate() in order to draw gizmo to that window.
     * Or pass a specific ImDrawList to draw to (e.g. ImGui::GetForegroundDrawList()).
     */
    public static void setDrawList() {
        nSetDrawList();
    }

    /**
     * Call inside your own window and before Manipulate() in order to draw gizmo to that window.
     * Or pass a specific ImDrawList to draw to (e.g. ImGui::GetForegroundDrawList()).
     */
    public static void setDrawList(final ImDrawList drawList) {
        nSetDrawList(drawList.ptr);
    }

    private static native void nSetDrawList(); /*
        ImGuizmo::SetDrawlist();
    */

    private static native void nSetDrawList(long drawList); /*
        ImGuizmo::SetDrawlist(reinterpret_cast<ImDrawList*>(drawList));
    */

    /**
     * Call BeginFrame right after ImGui_XXXX_NewFrame();
     */
    public static void beginFrame() {
        nBeginFrame();
    }

    private static native void nBeginFrame(); /*
        ImGuizmo::BeginFrame();
    */

    /**
     * This is necessary because when imguizmo is compiled into a dll, and imgui into another
     * Globals are not shared between them.
     * More details at https://stackoverflow.com/questions/19373061/what-happens-to-global-and-static-variables-in-a-shared-library-when-it-is-dynam
     * Expose method to set imgui context
     */
    public static void setImGuiContext(final ImGuiContext ctx) {
        nSetImGuiContext(ctx.ptr);
    }

    private static native void nSetImGuiContext(long ctx); /*
        ImGuizmo::SetImGuiContext(reinterpret_cast<ImGuiContext*>(ctx));
    */

    /**
     * Return true if mouse cursor is over any gizmo control (axis, plan or screen component)
     */
    public static boolean isOver() {
        return nIsOver();
    }

    private static native boolean nIsOver(); /*
        return ImGuizmo::IsOver();
    */

    /**
     * Return true if mouse IsOver or if the gizmo is in moving state
     */
    public static boolean isUsing() {
        return nIsUsing();
    }

    private static native boolean nIsUsing(); /*
        return ImGuizmo::IsUsing();
    */

    /**
     * Enable/disable the gizmo. Stay in the state until next call to Enable.
     * Gizmo is rendered with gray half transparent color when disabled
     */
    public static void enable(final boolean enable) {
        nEnable(enable);
    }

    private static native void nEnable(boolean enable); /*
        ImGuizmo::Enable(enable);
    */

    /**
     * Helper function for manually editing Translation/Rotation/Scale with an input float.
     * Translation, Rotation and Scale float points to 3 floats each.
     * Angles are in degrees (more suitable for human editing).
     *
     * @param matrix
     * 		the model matrix
     * @param translation
     * 		the model translation
     * @param rotation
     * 		the model rotation
     * @param scale
     * 		the model scale
     * @see <a target="_blank" href="https://github.com/CedricGuillemet/ImGuizmo">Reference Page</a>
     */
    public static void decomposeMatrixToComponents(final float[] matrix, final float[] translation, final float[] rotation, final float[] scale) {
        nDecomposeMatrixToComponents(matrix, translation, rotation, scale);
    }

    private static native void nDecomposeMatrixToComponents(float[] matrix, float[] translation, float[] rotation, float[] scale); /*MANUAL
        auto matrix = obj_matrix == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_matrix, JNI_FALSE);
        auto translation = obj_translation == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_translation, JNI_FALSE);
        auto rotation = obj_rotation == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_rotation, JNI_FALSE);
        auto scale = obj_scale == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_scale, JNI_FALSE);
        ImGuizmo::DecomposeMatrixToComponents(&matrix[0], &translation[0], &rotation[0], &scale[0]);
        if (matrix != NULL) env->ReleasePrimitiveArrayCritical(obj_matrix, matrix, JNI_FALSE);
        if (translation != NULL) env->ReleasePrimitiveArrayCritical(obj_translation, translation, JNI_FALSE);
        if (rotation != NULL) env->ReleasePrimitiveArrayCritical(obj_rotation, rotation, JNI_FALSE);
        if (scale != NULL) env->ReleasePrimitiveArrayCritical(obj_scale, scale, JNI_FALSE);
    */

    /**
     * Helper function for manually editing Translation/Rotation/Scale with an input float.
     * Translation, Rotation and Scale float points to 3 floats each.
     * Angles are in degrees (more suitable for human editing).
     *
     * @param translation
     * 		the model translation
     * @param rotation
     * 		the model rotation
     * @param scale
     * 		the model scale
     * @param matrix
     * 		the model matrix
     * @see <a target="_blank" href="https://github.com/CedricGuillemet/ImGuizmo">Reference Page</a>
     */
    public static void recomposeMatrixFromComponents(final float[] translation, final float[] rotation, final float[] scale, final float[] matrix) {
        nRecomposeMatrixFromComponents(translation, rotation, scale, matrix);
    }

    private static native void nRecomposeMatrixFromComponents(float[] translation, float[] rotation, float[] scale, float[] matrix); /*MANUAL
        auto translation = obj_translation == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_translation, JNI_FALSE);
        auto rotation = obj_rotation == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_rotation, JNI_FALSE);
        auto scale = obj_scale == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_scale, JNI_FALSE);
        auto matrix = obj_matrix == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_matrix, JNI_FALSE);
        ImGuizmo::RecomposeMatrixFromComponents(&translation[0], &rotation[0], &scale[0], &matrix[0]);
        if (translation != NULL) env->ReleasePrimitiveArrayCritical(obj_translation, translation, JNI_FALSE);
        if (rotation != NULL) env->ReleasePrimitiveArrayCritical(obj_rotation, rotation, JNI_FALSE);
        if (scale != NULL) env->ReleasePrimitiveArrayCritical(obj_scale, scale, JNI_FALSE);
        if (matrix != NULL) env->ReleasePrimitiveArrayCritical(obj_matrix, matrix, JNI_FALSE);
    */

    /**
     * This will set the rect position.
     *
     * @param x
     * 		x coordinate of the rectangle
     * @param y
     * 		y coordinate of the rectangle
     * @param width
     * 		width of the rectangle
     * @param height
     * 		height of the rectangle
     */
    public static void setRect(final float x, final float y, final float width, final float height) {
        nSetRect(x, y, width, height);
    }

    private static native void nSetRect(float x, float y, float width, float height); /*
        ImGuizmo::SetRect(x, y, width, height);
    */

    /**
     * Making sure if we're set to ortho or not.
     */
    public static void setOrthographic(final boolean isOrthographic) {
        nSetOrthographic(isOrthographic);
    }

    private static native void nSetOrthographic(boolean isOrthographic); /*
        ImGuizmo::SetOrthographic(isOrthographic);
    */

    /**
     * Drawing an arbitrary cube in the world.
     *
     * @param view
     * 		target camera view
     * @param projection
     * 		target camera projection
     * @param matrices
     * 		cube matrices (max. 4 cubes allowed)
     */
    public static void drawCubes(final float[] view, final float[] projection, final float[] matrices) {
        nDrawCubes(view, projection, matrices);
    }

    private static native void nDrawCubes(float[] view, float[] projection, float[] matrices); /*MANUAL
        auto view = obj_view == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_view, JNI_FALSE);
        auto projection = obj_projection == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_projection, JNI_FALSE);
        auto matrices = obj_matrices == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_matrices, JNI_FALSE);
        ImGuizmo::DrawCubes(&view[0], &projection[0], &matrices[0], (int)(env->GetArrayLength(obj_matrices)/16));
        if (view != NULL) env->ReleasePrimitiveArrayCritical(obj_view, view, JNI_FALSE);
        if (projection != NULL) env->ReleasePrimitiveArrayCritical(obj_projection, projection, JNI_FALSE);
        if (matrices != NULL) env->ReleasePrimitiveArrayCritical(obj_matrices, matrices, JNI_FALSE);
    */

    /**
     * Drawing an arbitrary cube in the world.
     *
     * @param view
     * 		target camera view
     * @param projection
     * 		target camera projection
     * @param matrices
     * 		cube matrices (max. 4 cubes allowed)
     * @param matrixCount
     * 		number of matrices
     */
    public static void drawCubes(final float[] view, final float[] projection, final float[] matrices, final int matrixCount) {
        nDrawCubes(view, projection, matrices, matrixCount);
    }

    private static native void nDrawCubes(float[] view, float[] projection, float[] matrices, int matrixCount); /*MANUAL
        auto view = obj_view == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_view, JNI_FALSE);
        auto projection = obj_projection == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_projection, JNI_FALSE);
        auto matrices = obj_matrices == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_matrices, JNI_FALSE);
        ImGuizmo::DrawCubes(&view[0], &projection[0], &matrices[0], matrixCount);
        if (view != NULL) env->ReleasePrimitiveArrayCritical(obj_view, view, JNI_FALSE);
        if (projection != NULL) env->ReleasePrimitiveArrayCritical(obj_projection, projection, JNI_FALSE);
        if (matrices != NULL) env->ReleasePrimitiveArrayCritical(obj_matrices, matrices, JNI_FALSE);
    */

    /**
     * Drawing an arbitrary cube in the world.
     *
     * @param view       target camera view
     * @param projection target camera projection
     * @param matrices   cube matrices (max. 4 cubes allowed)
     */
    public static void drawCubes(final float[] view, final float[] projection, final float[]... matrices) {
        if (matrices.length > 4) {
            throw new IllegalArgumentException("Only up to 4 cubes matrices is supported");
        }

        final float[] cubeMatrices = new float[matrices.length * 16];
        for (int i = 0, cubeMatricesLength = matrices.length; i < cubeMatricesLength; i++) {
            final float[] cubeMatrix = matrices[i];
            System.arraycopy(cubeMatrix, 0, cubeMatrices, i * cubeMatrix.length, cubeMatrix.length);
        }

        drawCubes(view, projection, cubeMatrices, cubeMatrices.length);
    }

    /**
     * Drawing a grid to the world (should only be used for debugging purposes).
     *
     * @param view
     * 		target camera view
     * @param projection
     * 		target camera projection
     * @param matrix
     * 		grid matrix
     * @param gridSize
     * 		grid size
     */
    public static void drawGrid(final float[] view, final float[] projection, final float[] matrix, final float gridSize) {
        nDrawGrid(view, projection, matrix, gridSize);
    }

    private static native void nDrawGrid(float[] view, float[] projection, float[] matrix, float gridSize); /*MANUAL
        auto view = obj_view == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_view, JNI_FALSE);
        auto projection = obj_projection == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_projection, JNI_FALSE);
        auto matrix = obj_matrix == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_matrix, JNI_FALSE);
        ImGuizmo::DrawGrid(&view[0], &projection[0], &matrix[0], gridSize);
        if (view != NULL) env->ReleasePrimitiveArrayCritical(obj_view, view, JNI_FALSE);
        if (projection != NULL) env->ReleasePrimitiveArrayCritical(obj_projection, projection, JNI_FALSE);
        if (matrix != NULL) env->ReleasePrimitiveArrayCritical(obj_matrix, matrix, JNI_FALSE);
    */

    /**
     * Manipulating the given model matrix with delta matrix
     *
     * @param view
     * 		target camera view
     * @param projection
     * 		target camera projection
     * @param operation
     * 		target operation
     * @param mode
     * 		target mode
     * @param matrix
     * 		model matrix
     */
    public static void manipulate(final float[] view, final float[] projection, final int operation, final int mode, final float[] matrix) {
        nManipulate(view, projection, operation, mode, matrix);
    }

    /**
     * Manipulating the given model matrix with delta matrix
     *
     * @param view
     * 		target camera view
     * @param projection
     * 		target camera projection
     * @param operation
     * 		target operation
     * @param mode
     * 		target mode
     * @param matrix
     * 		model matrix
     * @param deltaMatrix
     * 		delta matrix
     */
    public static void manipulate(final float[] view, final float[] projection, final int operation, final int mode, final float[] matrix, final float[] deltaMatrix) {
        nManipulate(view, projection, operation, mode, matrix, deltaMatrix);
    }

    /**
     * Manipulating the given model matrix with delta matrix
     *
     * @param view
     * 		target camera view
     * @param projection
     * 		target camera projection
     * @param operation
     * 		target operation
     * @param mode
     * 		target mode
     * @param matrix
     * 		model matrix
     * @param deltaMatrix
     * 		delta matrix
     * @param snap
     * 		snap value
     */
    public static void manipulate(final float[] view, final float[] projection, final int operation, final int mode, final float[] matrix, final float[] deltaMatrix, final float[] snap) {
        nManipulate(view, projection, operation, mode, matrix, deltaMatrix, snap);
    }

    /**
     * Manipulating the given model matrix with delta matrix
     *
     * @param view
     * 		target camera view
     * @param projection
     * 		target camera projection
     * @param operation
     * 		target operation
     * @param mode
     * 		target mode
     * @param matrix
     * 		model matrix
     * @param deltaMatrix
     * 		delta matrix
     * @param snap
     * 		snap value
     * @param localBounds
     * 		bounds value
     */
    public static void manipulate(final float[] view, final float[] projection, final int operation, final int mode, final float[] matrix, final float[] deltaMatrix, final float[] snap, final float[] localBounds) {
        nManipulate(view, projection, operation, mode, matrix, deltaMatrix, snap, localBounds);
    }

    /**
     * Manipulating the given model matrix with delta matrix
     *
     * @param view
     * 		target camera view
     * @param projection
     * 		target camera projection
     * @param operation
     * 		target operation
     * @param mode
     * 		target mode
     * @param matrix
     * 		model matrix
     * @param deltaMatrix
     * 		delta matrix
     * @param snap
     * 		snap value
     * @param localBounds
     * 		bounds value
     * @param boundsSnap
     * 		bounds snap value
     */
    public static void manipulate(final float[] view, final float[] projection, final int operation, final int mode, final float[] matrix, final float[] deltaMatrix, final float[] snap, final float[] localBounds, final float[] boundsSnap) {
        nManipulate(view, projection, operation, mode, matrix, deltaMatrix, snap, localBounds, boundsSnap);
    }

    private static native void nManipulate(float[] view, float[] projection, int operation, int mode, float[] matrix); /*MANUAL
        auto view = obj_view == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_view, JNI_FALSE);
        auto projection = obj_projection == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_projection, JNI_FALSE);
        auto matrix = obj_matrix == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_matrix, JNI_FALSE);
        ImGuizmo::Manipulate(&view[0], &projection[0], static_cast<ImGuizmo::OPERATION>(operation), static_cast<ImGuizmo::MODE>(mode), &matrix[0]);
        if (view != NULL) env->ReleasePrimitiveArrayCritical(obj_view, view, JNI_FALSE);
        if (projection != NULL) env->ReleasePrimitiveArrayCritical(obj_projection, projection, JNI_FALSE);
        if (matrix != NULL) env->ReleasePrimitiveArrayCritical(obj_matrix, matrix, JNI_FALSE);
    */

    private static native void nManipulate(float[] view, float[] projection, int operation, int mode, float[] matrix, float[] deltaMatrix); /*MANUAL
        auto view = obj_view == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_view, JNI_FALSE);
        auto projection = obj_projection == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_projection, JNI_FALSE);
        auto matrix = obj_matrix == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_matrix, JNI_FALSE);
        auto deltaMatrix = obj_deltaMatrix == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_deltaMatrix, JNI_FALSE);
        ImGuizmo::Manipulate(&view[0], &projection[0], static_cast<ImGuizmo::OPERATION>(operation), static_cast<ImGuizmo::MODE>(mode), &matrix[0], &deltaMatrix[0]);
        if (view != NULL) env->ReleasePrimitiveArrayCritical(obj_view, view, JNI_FALSE);
        if (projection != NULL) env->ReleasePrimitiveArrayCritical(obj_projection, projection, JNI_FALSE);
        if (matrix != NULL) env->ReleasePrimitiveArrayCritical(obj_matrix, matrix, JNI_FALSE);
        if (deltaMatrix != NULL) env->ReleasePrimitiveArrayCritical(obj_deltaMatrix, deltaMatrix, JNI_FALSE);
    */

    private static native void nManipulate(float[] view, float[] projection, int operation, int mode, float[] matrix, float[] deltaMatrix, float[] snap); /*MANUAL
        auto view = obj_view == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_view, JNI_FALSE);
        auto projection = obj_projection == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_projection, JNI_FALSE);
        auto matrix = obj_matrix == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_matrix, JNI_FALSE);
        auto deltaMatrix = obj_deltaMatrix == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_deltaMatrix, JNI_FALSE);
        auto snap = obj_snap == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_snap, JNI_FALSE);
        ImGuizmo::Manipulate(&view[0], &projection[0], static_cast<ImGuizmo::OPERATION>(operation), static_cast<ImGuizmo::MODE>(mode), &matrix[0], &deltaMatrix[0], &snap[0]);
        if (view != NULL) env->ReleasePrimitiveArrayCritical(obj_view, view, JNI_FALSE);
        if (projection != NULL) env->ReleasePrimitiveArrayCritical(obj_projection, projection, JNI_FALSE);
        if (matrix != NULL) env->ReleasePrimitiveArrayCritical(obj_matrix, matrix, JNI_FALSE);
        if (deltaMatrix != NULL) env->ReleasePrimitiveArrayCritical(obj_deltaMatrix, deltaMatrix, JNI_FALSE);
        if (snap != NULL) env->ReleasePrimitiveArrayCritical(obj_snap, snap, JNI_FALSE);
    */

    private static native void nManipulate(float[] view, float[] projection, int operation, int mode, float[] matrix, float[] deltaMatrix, float[] snap, float[] localBounds); /*MANUAL
        auto view = obj_view == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_view, JNI_FALSE);
        auto projection = obj_projection == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_projection, JNI_FALSE);
        auto matrix = obj_matrix == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_matrix, JNI_FALSE);
        auto deltaMatrix = obj_deltaMatrix == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_deltaMatrix, JNI_FALSE);
        auto snap = obj_snap == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_snap, JNI_FALSE);
        auto localBounds = obj_localBounds == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_localBounds, JNI_FALSE);
        ImGuizmo::Manipulate(&view[0], &projection[0], static_cast<ImGuizmo::OPERATION>(operation), static_cast<ImGuizmo::MODE>(mode), &matrix[0], &deltaMatrix[0], &snap[0], &localBounds[0]);
        if (view != NULL) env->ReleasePrimitiveArrayCritical(obj_view, view, JNI_FALSE);
        if (projection != NULL) env->ReleasePrimitiveArrayCritical(obj_projection, projection, JNI_FALSE);
        if (matrix != NULL) env->ReleasePrimitiveArrayCritical(obj_matrix, matrix, JNI_FALSE);
        if (deltaMatrix != NULL) env->ReleasePrimitiveArrayCritical(obj_deltaMatrix, deltaMatrix, JNI_FALSE);
        if (snap != NULL) env->ReleasePrimitiveArrayCritical(obj_snap, snap, JNI_FALSE);
        if (localBounds != NULL) env->ReleasePrimitiveArrayCritical(obj_localBounds, localBounds, JNI_FALSE);
    */

    private static native void nManipulate(float[] view, float[] projection, int operation, int mode, float[] matrix, float[] deltaMatrix, float[] snap, float[] localBounds, float[] boundsSnap); /*MANUAL
        auto view = obj_view == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_view, JNI_FALSE);
        auto projection = obj_projection == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_projection, JNI_FALSE);
        auto matrix = obj_matrix == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_matrix, JNI_FALSE);
        auto deltaMatrix = obj_deltaMatrix == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_deltaMatrix, JNI_FALSE);
        auto snap = obj_snap == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_snap, JNI_FALSE);
        auto localBounds = obj_localBounds == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_localBounds, JNI_FALSE);
        auto boundsSnap = obj_boundsSnap == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_boundsSnap, JNI_FALSE);
        ImGuizmo::Manipulate(&view[0], &projection[0], static_cast<ImGuizmo::OPERATION>(operation), static_cast<ImGuizmo::MODE>(mode), &matrix[0], &deltaMatrix[0], &snap[0], &localBounds[0], &boundsSnap[0]);
        if (view != NULL) env->ReleasePrimitiveArrayCritical(obj_view, view, JNI_FALSE);
        if (projection != NULL) env->ReleasePrimitiveArrayCritical(obj_projection, projection, JNI_FALSE);
        if (matrix != NULL) env->ReleasePrimitiveArrayCritical(obj_matrix, matrix, JNI_FALSE);
        if (deltaMatrix != NULL) env->ReleasePrimitiveArrayCritical(obj_deltaMatrix, deltaMatrix, JNI_FALSE);
        if (snap != NULL) env->ReleasePrimitiveArrayCritical(obj_snap, snap, JNI_FALSE);
        if (localBounds != NULL) env->ReleasePrimitiveArrayCritical(obj_localBounds, localBounds, JNI_FALSE);
        if (boundsSnap != NULL) env->ReleasePrimitiveArrayCritical(obj_boundsSnap, boundsSnap, JNI_FALSE);
    */

    /**
     * Manipulating the view
     *
     * @param view
     * 		target camera view
     * @param length
     * 		camera distance/length
     */
    public static void viewManipulate(final float[] view, final float length, final ImVec2 position, final ImVec2 size, final int backgroundColor) {
        nViewManipulate(view, length, position.x, position.y, size.x, size.y, backgroundColor);
    }

    /**
     * Manipulating the view
     *
     * @param view
     * 		target camera view
     * @param length
     * 		camera distance/length
     */
    public static void viewManipulate(final float[] view, final float length, final float positionX, final float positionY, final float sizeX, final float sizeY, final int backgroundColor) {
        nViewManipulate(view, length, positionX, positionY, sizeX, sizeY, backgroundColor);
    }

    private static native void nViewManipulate(float[] view, float length, float positionX, float positionY, float sizeX, float sizeY, int backgroundColor); /*MANUAL
        auto view = obj_view == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_view, JNI_FALSE);
        ImVec2 position = ImVec2(positionX, positionY);
        ImVec2 size = ImVec2(sizeX, sizeY);
        ImGuizmo::ViewManipulate(&view[0], length, position, size, backgroundColor);
        if (view != NULL) env->ReleasePrimitiveArrayCritical(obj_view, view, JNI_FALSE);
    */

    /**
     * This will update the current id
     */
    public static void setID(final int id) {
        nSetID(id);
    }

    private static native void nSetID(int id); /*
        ImGuizmo::SetID(id);
    */

    /**
     * Return true if the cursor is over the operation's gizmo
     */
    public static boolean isOver(final int operation) {
        return nIsOver(operation);
    }

    private static native boolean nIsOver(int operation); /*
        return ImGuizmo::IsOver(static_cast<ImGuizmo::OPERATION>(operation));
    */

    public static void setGizmoSizeClipSpace(final float value) {
        nSetGizmoSizeClipSpace(value);
    }

    private static native void nSetGizmoSizeClipSpace(float value); /*
        ImGuizmo::SetGizmoSizeClipSpace(value);
    */

    /**
     * Allow axis to flip.
     * When true (default), the guizmo axis flip for better visibility.
     * When false, they always stay along the positive world/local axis.
     */
    public static void allowAxisFlip(final boolean value) {
        nAllowAxisFlip(value);
    }

    private static native void nAllowAxisFlip(boolean value); /*
        ImGuizmo::AllowAxisFlip(value);
    */
}
