package imgui.extension.imguizmo;

/**
 * This is just the java representtation of the imguizmo operation
 */
public enum Operation {
    TRANSLATE_X(1 << 0),
    TRANSLATE_Y(1 << 1),
    TRANSLATE_Z(1 << 2),
    ROTATE_X(1 << 3),
    ROTATE_Y(1 << 4),
    ROTATE_Z(1 << 5),
    ROTATE_SCREEN(1 << 6),
    SCALE_X(1 << 7),
    SCALE_Y(1 << 8),
    SCALE_Z(1 << 9),
    TRANSLATE(TRANSLATE_X.value | TRANSLATE_Y.value | TRANSLATE_Z.value),
    ROTATE(ROTATE_X.value | ROTATE_Y.value | ROTATE_Z.value),
    SCALE(SCALE_X.value | SCALE_Y.value | SCALE_Z.value);

    final int value;

    Operation(int value) {
        this.value = value;
    }
}
