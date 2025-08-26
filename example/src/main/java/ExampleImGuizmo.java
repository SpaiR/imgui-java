import imgui.ImGui;
import imgui.extension.imguizmo.ImGuizmo;
import imgui.extension.imguizmo.flag.Mode;
import imgui.extension.imguizmo.flag.Operation;
import imgui.flag.ImGuiCond;
import imgui.flag.ImGuiInputTextFlags;
import imgui.flag.ImGuiWindowFlags;
import imgui.type.ImBoolean;
import imgui.type.ImFloat;

import java.awt.Desktop;
import java.net.URI;
import java.util.Arrays;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_SHIFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_R;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_T;

public class ExampleImGuizmo {
    private static final String URL = "https://github.com/CedricGuillemet/ImGuizmo/tree/f7bbbe";

    private static final int CAM_DISTANCE = 8;
    private static final float CAM_Y_ANGLE = 165.f / 180.f * (float) Math.PI;
    private static final float CAM_X_ANGLE = 32.f / 180.f * (float) Math.PI;
    private static final float FLT_EPSILON = 1.19209290E-07f;

    private static final float[][] OBJECT_MATRICES = {
        {
            1.f, 0.f, 0.f, 0.f,
            0.f, 1.f, 0.f, 0.f,
            0.f, 0.f, 1.f, 0.f,
            0.f, 0.f, 0.f, 1.f
        },
        {
            1.f, 0.f, 0.f, 0.f,
            0.f, 1.f, 0.f, 0.f,
            0.f, 0.f, 1.f, 0.f,
            2.f, 0.f, 0.f, 1.f
        },
        {
            1.f, 0.f, 0.f, 0.f,
            0.f, 1.f, 0.f, 0.f,
            0.f, 0.f, 1.f, 0.f,
            2.f, 0.f, 2.f, 1.f
        },
        {
            1.f, 0.f, 0.f, 0.f,
            0.f, 1.f, 0.f, 0.f,
            0.f, 0.f, 1.f, 0.f,
            0.f, 0.f, 2.f, 1.f
        }
    };

    private static final float[] IDENTITY_MATRIX = {
        1.f, 0.f, 0.f, 0.f,
        0.f, 1.f, 0.f, 0.f,
        0.f, 0.f, 1.f, 0.f,
        0.f, 0.f, 0.f, 1.f
    };

    private static final float[] EMPTY = new float[]{0};

    private static final float[] INPUT_CAMERA_VIEW = {
        1.f, 0.f, 0.f, 0.f,
        0.f, 1.f, 0.f, 0.f,
        0.f, 0.f, 1.f, 0.f,
        0.f, 0.f, 0.f, 1.f
    };

    private static final float[] INPUT_BOUNDS = new float[]{-0.5f, -0.5f, -0.5f, 0.5f, 0.5f, 0.5f};
    private static final float[] INPUT_BOUNDS_SNAP = new float[]{1f, 1f, 1f};

    private static final float[] INPUT_SNAP_VALUE = new float[]{1f, 1f, 1f};
    private static final float[] INPUT_MATRIX_TRANSLATION = new float[3];
    private static final float[] INPUT_MATRIX_SCALE = new float[3];
    private static final float[] INPUT_MATRIX_ROTATION = new float[3];

    private static final ImFloat INPUT_FLOAT = new ImFloat();

    private static final ImBoolean BOUNDING_SIZE = new ImBoolean(false);
    private static final ImBoolean USE_SNAP = new ImBoolean(false);

    private static int currentMode = Mode.LOCAL;
    private static int currentGizmoOperation;

    private static boolean boundSizingSnap = false;
    private static boolean firstFrame = true;

    public static void show(final ImBoolean showImGuizmoWindow) {
        ImGuizmo.beginFrame();

        if (ImGui.begin("ImGuizmo Demo", showImGuizmoWindow)) {
            ImGui.text("This a demo for ImGuizmo");

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
            ImGui.separator();

            if (firstFrame) {
                float[] eye = new float[]{
                    (float) (Math.cos(CAM_Y_ANGLE) * Math.cos(CAM_X_ANGLE) * CAM_DISTANCE),
                    (float) (Math.sin(CAM_X_ANGLE) * CAM_DISTANCE),
                    (float) (Math.sin(CAM_Y_ANGLE) * Math.cos(CAM_X_ANGLE) * CAM_DISTANCE)
                };
                float[] at = new float[]{0.f, 0.f, 0.f};
                float[] up = new float[]{0.f, 1.f, 0.f};
                lookAt(eye, at, up, INPUT_CAMERA_VIEW);
                firstFrame = false;
            }

            ImGui.text("Keybindings:");
            ImGui.text("T - Translate");
            ImGui.text("R - Rotate");
            ImGui.text("S - Scale");
            ImGui.separator();

            if (ImGuizmo.isUsing()) {
                ImGui.text("Using gizmo");
                if (ImGuizmo.isOver()) {
                    ImGui.text("Over a gizmo");
                }
                if (ImGuizmo.isOver(Operation.TRANSLATE)) {
                    ImGui.text("Over translate gizmo");
                } else if (ImGuizmo.isOver(Operation.ROTATE)) {
                    ImGui.text("Over rotate gizmo");
                } else if (ImGuizmo.isOver(Operation.SCALE)) {
                    ImGui.text("Over scale gizmo");
                }
            } else {
                ImGui.text("Not using gizmo");
            }

            editTransform(showImGuizmoWindow);
            ImGui.end();
        }
    }

    private static void editTransform(final ImBoolean showImGuizmoWindow) {
        if (ImGui.isKeyPressed(GLFW_KEY_T)) {
            currentGizmoOperation = Operation.TRANSLATE;
        } else if (ImGui.isKeyPressed(GLFW_KEY_R)) {
            currentGizmoOperation = Operation.ROTATE;
        } else if (ImGui.isKeyPressed(GLFW_KEY_S)) {
            currentGizmoOperation = Operation.SCALE;
        } else if (ImGui.isKeyPressed(GLFW_KEY_LEFT_SHIFT)) {
            USE_SNAP.set(!USE_SNAP.get());
        }

        if (ImGuizmo.isUsing()) {
            ImGuizmo.decomposeMatrixToComponents(OBJECT_MATRICES[0], INPUT_MATRIX_TRANSLATION, INPUT_MATRIX_ROTATION, INPUT_MATRIX_SCALE);
        }

        ImGui.inputFloat3("Tr", INPUT_MATRIX_TRANSLATION, "%.3f", ImGuiInputTextFlags.ReadOnly);
        ImGui.inputFloat3("Rt", INPUT_MATRIX_ROTATION, "%.3f", ImGuiInputTextFlags.ReadOnly);
        ImGui.inputFloat3("Sc", INPUT_MATRIX_SCALE, "%.3f", ImGuiInputTextFlags.ReadOnly);

        if (ImGuizmo.isUsing()) {
            ImGuizmo.recomposeMatrixFromComponents(INPUT_MATRIX_TRANSLATION, INPUT_MATRIX_ROTATION, INPUT_MATRIX_SCALE, OBJECT_MATRICES[0]);
        }

        if (currentGizmoOperation != Operation.SCALE) {
            if (ImGui.radioButton("Local", currentMode == Mode.LOCAL)) {
                currentMode = Mode.LOCAL;
            }
            ImGui.sameLine();
            if (ImGui.radioButton("World", currentMode == Mode.WORLD)) {
                currentMode = Mode.WORLD;
            }
        }

        ImGui.checkbox("Snap Checkbox", USE_SNAP);

        INPUT_FLOAT.set(INPUT_SNAP_VALUE[0]);
        switch (currentGizmoOperation) {
            case Operation.TRANSLATE:
                ImGui.inputFloat3("Snap Value", INPUT_SNAP_VALUE);
                break;
            case Operation.ROTATE:
                ImGui.inputFloat("Angle Value", INPUT_FLOAT);
                float rotateValue = INPUT_FLOAT.get();
                Arrays.fill(INPUT_SNAP_VALUE, rotateValue); //avoiding allocation
                break;
            case Operation.SCALE:
                ImGui.inputFloat("Scale Value", INPUT_FLOAT);
                float scaleValue = INPUT_FLOAT.get();
                Arrays.fill(INPUT_SNAP_VALUE, scaleValue);
                break;
        }

        ImGui.checkbox("Show Bound Sizing", BOUNDING_SIZE);

        if (BOUNDING_SIZE.get()) {
            if (ImGui.checkbox("BoundSizingSnap", boundSizingSnap)) {
                boundSizingSnap = !boundSizingSnap;
            }
            ImGui.sameLine();
            ImGui.inputFloat3("Snap", INPUT_BOUNDS_SNAP);
        }

        ImGui.setNextWindowPos(ImGui.getMainViewport().getPosX() + 100, ImGui.getMainViewport().getPosY() + 100, ImGuiCond.Once);
        ImGui.setNextWindowSize(800, 400, ImGuiCond.Once);
        ImGui.begin("Gizmo", showImGuizmoWindow);
        ImGui.beginChild("prevent_window_from_moving_by_drag", 0, 0, false, ImGuiWindowFlags.NoMove);

        float aspect = ImGui.getWindowWidth() / ImGui.getWindowHeight();
        float[] cameraProjection = perspective(27, aspect, 0.1f, 100f);

        ImGuizmo.setOrthographic(false);
        ImGuizmo.enable(true);
        ImGuizmo.setDrawList();

        float windowWidth = ImGui.getWindowWidth();
        float windowHeight = ImGui.getWindowHeight();
        ImGuizmo.setRect(ImGui.getWindowPosX(), ImGui.getWindowPosY(), windowWidth, windowHeight);

        ImGuizmo.drawGrid(INPUT_CAMERA_VIEW, cameraProjection, IDENTITY_MATRIX, 100);
        ImGuizmo.setID(0);
        ImGuizmo.drawCubes(INPUT_CAMERA_VIEW, cameraProjection, OBJECT_MATRICES[0]);

        if (USE_SNAP.get() && BOUNDING_SIZE.get() && boundSizingSnap) {
            ImGuizmo.manipulate(INPUT_CAMERA_VIEW, cameraProjection, currentGizmoOperation, currentMode, OBJECT_MATRICES[0], null, INPUT_SNAP_VALUE, INPUT_BOUNDS, INPUT_BOUNDS_SNAP);
        } else if (USE_SNAP.get() && BOUNDING_SIZE.get()) {
            ImGuizmo.manipulate(INPUT_CAMERA_VIEW, cameraProjection, currentGizmoOperation, currentMode, OBJECT_MATRICES[0], null, INPUT_SNAP_VALUE, INPUT_BOUNDS);
        } else if (BOUNDING_SIZE.get() && boundSizingSnap) {
            ImGuizmo.manipulate(INPUT_CAMERA_VIEW, cameraProjection, currentGizmoOperation, currentMode, OBJECT_MATRICES[0], null, EMPTY, INPUT_BOUNDS, INPUT_BOUNDS_SNAP);
        } else if (BOUNDING_SIZE.get()) {
            ImGuizmo.manipulate(INPUT_CAMERA_VIEW, cameraProjection, currentGizmoOperation, currentMode, OBJECT_MATRICES[0], null, EMPTY, INPUT_BOUNDS);
        } else if (USE_SNAP.get()) {
            ImGuizmo.manipulate(INPUT_CAMERA_VIEW, cameraProjection, currentGizmoOperation, currentMode, OBJECT_MATRICES[0], null, INPUT_SNAP_VALUE);
        } else {
            ImGuizmo.manipulate(INPUT_CAMERA_VIEW, cameraProjection, currentGizmoOperation, currentMode, OBJECT_MATRICES[0]);
        }

        float viewManipulateRight = ImGui.getWindowPosX() + windowWidth;
        float viewManipulateTop = ImGui.getWindowPosY();
        ImGuizmo.viewManipulate(INPUT_CAMERA_VIEW, CAM_DISTANCE, viewManipulateRight - 128, viewManipulateTop, 128, 128, 0x10101010);

        ImGui.endChild();
        ImGui.end();
    }

    private static float[] perspective(float fovY, float aspect, float near, float far) {
        float ymax, xmax;
        ymax = (float) (near * Math.tan(fovY * Math.PI / 180.0f));
        xmax = ymax * aspect;
        return frustum(-xmax, xmax, -ymax, ymax, near, far);
    }

    private static float[] frustum(float left, float right, float bottom, float top, float near, float far) {
        float[] r = new float[16];
        float temp = 2.0f * near;
        float temp2 = right - left;
        float temp3 = top - bottom;
        float temp4 = far - near;
        r[0] = temp / temp2;
        r[1] = 0.0f;
        r[2] = 0.0f;
        r[3] = 0.0f;
        r[4] = 0.0f;
        r[5] = temp / temp3;
        r[6] = 0.0f;
        r[7] = 0.0f;
        r[8] = (right + left) / temp2;
        r[9] = (top + bottom) / temp3;
        r[10] = (-far - near) / temp4;
        r[11] = -1.0f;
        r[12] = 0.0f;
        r[13] = 0.0f;
        r[14] = (-temp * far) / temp4;
        r[15] = 0.0f;
        return r;
    }

    private static float[] cross(float[] a, float[] b) {
        float[] r = new float[3];
        r[0] = a[1] * b[2] - a[2] * b[1];
        r[1] = a[2] * b[0] - a[0] * b[2];
        r[2] = a[0] * b[1] - a[1] * b[0];
        return r;
    }

    private static float dot(float[] a, float[] b) {
        return a[0] * b[0] + a[1] * b[1] + a[2] * b[2];
    }

    private static float[] normalize(float[] a) {
        float[] r = new float[3];
        float il = (float) (1.f / (Math.sqrt(dot(a, a)) + FLT_EPSILON));
        r[0] = a[0] * il;
        r[1] = a[1] * il;
        r[2] = a[2] * il;
        return r;
    }

    private static void lookAt(float[] eye, float[] at, float[] up, float[] m16) {
        float[] x;
        float[] y;
        float[] z;
        float[] tmp = new float[3];

        tmp[0] = eye[0] - at[0];
        tmp[1] = eye[1] - at[1];
        tmp[2] = eye[2] - at[2];
        z = normalize(tmp);
        y = normalize(up);

        tmp = cross(y, z);
        x = normalize(tmp);

        tmp = cross(z, x);
        y = normalize(tmp);

        m16[0] = x[0];
        m16[1] = y[0];
        m16[2] = z[0];
        m16[3] = 0.0f;
        m16[4] = x[1];
        m16[5] = y[1];
        m16[6] = z[1];
        m16[7] = 0.0f;
        m16[8] = x[2];
        m16[9] = y[2];
        m16[10] = z[2];
        m16[11] = 0.0f;
        m16[12] = -dot(x, eye);
        m16[13] = -dot(y, eye);
        m16[14] = -dot(z, eye);
        m16[15] = 1.0f;
    }
}
