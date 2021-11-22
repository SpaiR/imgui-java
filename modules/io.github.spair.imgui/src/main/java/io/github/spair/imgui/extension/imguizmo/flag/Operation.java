package io.github.spair.imgui.extension.imguizmo.flag;

/**
 * Needs view and projection matrices.
 * Matrix parameter is the source matrix (where will be gizmo be drawn) and might be transformed by the function. Return deltaMatrix is optional.
 * Translation is applied in world space.
 */
public final class Operation {
    private Operation() {
    }

    /**
     * Translating X axis
     */
    public static final int TRANSLATE_X = 1;
    /**
     * Translating Y axis
     */
    public static final int TRANSLATE_Y = 1 << 1;
    /**
     * Translating Z axis
     */
    public static final int TRANSLATE_Z = 1 << 2;
    /**
     * Rotating X axis
     */
    public static final int ROTATE_X = 1 << 3;
    /**
     * Rotating Y axis
     */
    public static final int ROTATE_Y = 1 << 4;
    /**
     * Rotating Z axis
     */
    public static final int ROTATE_Z = 1 << 5;
    /**
     * Rotating screen
     */
    public static final int ROTATE_SCREEN = 1 << 6;
    /**
     * Scaling X axis
     */
    public static final int SCALE_X = 1 << 7;
    /**
     * Scaling Y axis
     */
    public static final int SCALE_Y = 1 << 8;
    /**
     * Scaling Z axis
     */
    public static final int SCALE_Z = 1 << 9;
    /**
     * Translating all axis
     */
    public static final int TRANSLATE = TRANSLATE_X | TRANSLATE_Y | TRANSLATE_Z;
    /**
     * Rotating all axis
     */
    public static final int ROTATE = ROTATE_X | ROTATE_Y | ROTATE_Z | ROTATE_SCREEN;
    /**
     * Scaling all axis
     */
    public static final int SCALE = SCALE_X | SCALE_Y | SCALE_Z;
}
