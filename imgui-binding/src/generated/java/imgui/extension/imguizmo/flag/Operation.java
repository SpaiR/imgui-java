package imgui.extension.imguizmo.flag;


/**
 * Needs view and projection matrices.
 * Matrix parameter is the source matrix (where will be gizmo be drawn) and might be transformed by the function. Return deltaMatrix is optional.
 * Translation is applied in world space.
 */
public final class Operation {
    private Operation() {
    }

    /**
     * Definition: {@code (1u << 0)}
     */
    public static final int TRANSLATE_X = 1;

    /**
     * Definition: {@code (1u << 1)}
     */
    public static final int TRANSLATE_Y = 2;

    /**
     * Definition: {@code (1u << 2)}
     */
    public static final int TRANSLATE_Z = 4;

    /**
     * Definition: {@code (1u << 3)}
     */
    public static final int ROTATE_X = 8;

    /**
     * Definition: {@code (1u << 4)}
     */
    public static final int ROTATE_Y = 16;

    /**
     * Definition: {@code (1u << 5)}
     */
    public static final int ROTATE_Z = 32;

    /**
     * Definition: {@code (1u << 6)}
     */
    public static final int ROTATE_SCREEN = 64;

    /**
     * Definition: {@code (1u << 7)}
     */
    public static final int SCALE_X = 128;

    /**
     * Definition: {@code (1u << 8)}
     */
    public static final int SCALE_Y = 256;

    /**
     * Definition: {@code (1u << 9)}
     */
    public static final int SCALE_Z = 512;

    /**
     * Definition: {@code (1u << 10)}
     */
    public static final int BOUNDS = 1024;

    /**
     * Definition: {@code (1u << 11)}
     */
    public static final int SCALE_XU = 2048;

    /**
     * Definition: {@code (1u << 12)}
     */
    public static final int SCALE_YU = 4096;

    /**
     * Definition: {@code (1u << 13)}
     */
    public static final int SCALE_ZU = 8192;

    /**
     * Definition: {@code TRANSLATE_X | TRANSLATE_Y | TRANSLATE_Z}
     */
    public static final int TRANSLATE = 7;

    /**
     * Definition: {@code ROTATE_X | ROTATE_Y | ROTATE_Z | ROTATE_SCREEN}
     */
    public static final int ROTATE = 120;

    /**
     * Definition: {@code SCALE_X | SCALE_Y | SCALE_Z}
     */
    public static final int SCALE = 896;

    /**
     * universal
     *
     * <p>Definition: {@code SCALE_XU | SCALE_YU | SCALE_ZU}
     */
    public static final int SCALEU = 14336;

    /**
     * Definition: {@code TRANSLATE | ROTATE | SCALEU}
     */
    public static final int UNIVERSAL = 14463;
}
