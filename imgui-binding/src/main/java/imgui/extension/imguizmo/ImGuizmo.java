package imgui.extension.imguizmo;

import imgui.ImDrawList;
import imgui.ImVec2;
import imgui.binding.annotation.ArgValue;
import imgui.binding.annotation.BindingMethod;
import imgui.binding.annotation.BindingSource;
import imgui.binding.annotation.OptArg;
import imgui.internal.ImGuiContext;

@BindingSource
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
    @BindingMethod(callName = "SetDrawlist")
    public static native void SetDrawList(@OptArg ImDrawList drawList);

    /**
     * Call BeginFrame right after ImGui_XXXX_NewFrame();
     */
    @BindingMethod
    public static native void BeginFrame();

    /**
     * This is necessary because when imguizmo is compiled into a dll, and imgui into another
     * Globals are not shared between them.
     * More details at https://stackoverflow.com/questions/19373061/what-happens-to-global-and-static-variables-in-a-shared-library-when-it-is-dynam
     * Expose method to set imgui context
     */
    @BindingMethod
    public static native void SetImGuiContext(ImGuiContext ctx);

    /**
     * Return true if mouse cursor is over any gizmo control (axis, plan or screen component)
     */
    @BindingMethod
    public static native boolean IsOver();

    /**
     * Return true if mouse IsOver or if the gizmo is in moving state
     */
    @BindingMethod
    public static native boolean IsUsing();

    /**
     * Enable/disable the gizmo. Stay in the state until next call to Enable.
     * Gizmo is rendered with gray half transparent color when disabled
     */
    @BindingMethod
    public static native void Enable(boolean enable);

    /**
     * Helper function for manually editing Translation/Rotation/Scale with an input float.
     * Translation, Rotation and Scale float points to 3 floats each.
     * Angles are in degrees (more suitable for human editing).
     *
     * @param matrix      the model matrix
     * @param translation the model translation
     * @param rotation    the model rotation
     * @param scale       the model scale
     * @see <a target="_blank" href="https://github.com/CedricGuillemet/ImGuizmo">Reference Page</a>
     */
    @BindingMethod
    public static native void DecomposeMatrixToComponents(float[] matrix, float[] translation, float[] rotation, float[] scale);

    /**
     * Helper function for manually editing Translation/Rotation/Scale with an input float.
     * Translation, Rotation and Scale float points to 3 floats each.
     * Angles are in degrees (more suitable for human editing).
     *
     * @param translation the model translation
     * @param rotation    the model rotation
     * @param scale       the model scale
     * @param matrix      the model matrix
     * @see <a target="_blank" href="https://github.com/CedricGuillemet/ImGuizmo">Reference Page</a>
     */
    @BindingMethod
    public static native void RecomposeMatrixFromComponents(float[] translation, float[] rotation, float[] scale, float[] matrix);

    /**
     * This will set the rect position.
     *
     * @param x      x coordinate of the rectangle
     * @param y      y coordinate of the rectangle
     * @param width  width of the rectangle
     * @param height height of the rectangle
     */
    @BindingMethod
    public static native void SetRect(float x, float y, float width, float height);

    /**
     * Making sure if we're set to ortho or not.
     */
    @BindingMethod
    public static native void SetOrthographic(boolean isOrthographic);

    /**
     * Drawing an arbitrary cube in the world.
     *
     * @param view       target camera view
     * @param projection target camera projection
     * @param matrices   cube matrices (max. 4 cubes allowed)
     */
    @BindingMethod
    public static native void DrawCubes(float[] view, float[] projection, float[] matrices, @ArgValue(callValue = "(int)(env->GetArrayLength(obj_matrices)/16)") Void matrixCount);

    /**
     * Drawing an arbitrary cube in the world.
     *
     * @param view        target camera view
     * @param projection  target camera projection
     * @param matrices    cube matrices (max. 4 cubes allowed)
     * @param matrixCount number of matrices
     */
    @BindingMethod
    public static native void DrawCubes(float[] view, float[] projection, float[] matrices, int matrixCount);

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
     * @param view       target camera view
     * @param projection target camera projection
     * @param matrix     grid matrix
     * @param gridSize   grid size
     */
    @BindingMethod
    public static native void DrawGrid(float[] view, float[] projection, float[] matrix, float gridSize);

    /**
     * Manipulating the given model matrix with delta matrix
     *
     * @param view        target camera view
     * @param projection  target camera projection
     * @param operation   target operation
     * @param mode        target mode
     * @param matrix      model matrix
     * @param deltaMatrix delta matrix
     * @param snap        snap value
     * @param localBounds bounds value
     * @param boundsSnap  bounds snap value
     */
    @BindingMethod
    public static native void Manipulate(float[] view, float[] projection, @ArgValue(staticCast = "ImGuizmo::OPERATION") int operation, @ArgValue(staticCast = "ImGuizmo::MODE") int mode, float[] matrix, @OptArg float[] deltaMatrix, @OptArg float[] snap, @OptArg float[] localBounds, @OptArg float[] boundsSnap);

    /**
     * Manipulating the view
     *
     * @param view            target camera view
     * @param length          camera distance/length
     * @param position        position
     * @param size            size
     * @param backgroundColor color
     */
    @BindingMethod
    public static native void ViewManipulate(float[] view, float length, ImVec2 position, ImVec2 size, int backgroundColor);

    /**
     * This will update the current id
     */
    @BindingMethod
    public static native void SetID(int id);

    /**
     * Return true if the cursor is over the operation's gizmo
     *
     * @param operation target
     */
    @BindingMethod
    public static native boolean IsOver(@ArgValue(staticCast = "ImGuizmo::OPERATION") int operation);

    @BindingMethod
    public static native void SetGizmoSizeClipSpace(float value);

    /**
     * Allow axis to flip.
     * When true (default), the guizmo axis flip for better visibility.
     * When false, they always stay along the positive world/local axis.
     */
    @BindingMethod
    public static native void AllowAxisFlip(boolean value);
}
