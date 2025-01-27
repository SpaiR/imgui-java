package imgui.flag;


/**
 * Enumeration for ImGui::SetWindow***(), SetNextWindow***(), SetNextItem***() functions
 * Represent a condition.
 * Important: Treat as a regular enum! Do NOT combine multiple values using binary operators! All the functions above treat 0 as a shortcut to ImGuiCond_Always.
 */
public final class ImGuiCond {
    private ImGuiCond() {
    }

    /**
     * No condition (always set the variable), same as _Always
     *
     * <p>Definition: {@code 0}
     */
    public static final int None = 0;

    /**
     * No condition (always set the variable), same as _None
     *
     * <p>Definition: {@code 1 << 0}
     */
    public static final int Always = 1;

    /**
     * Set the variable once per runtime session (only the first call will succeed)
     *
     * <p>Definition: {@code 1 << 1}
     */
    public static final int Once = 2;

    /**
     * Set the variable if the object/window has no persistently saved data (no entry in .ini file)
     *
     * <p>Definition: {@code 1 << 2}
     */
    public static final int FirstUseEver = 4;

    /**
     * Set the variable if the object/window is appearing after being hidden/inactive (or the first time)
     *
     * <p>Definition: {@code 1 << 3}
     */
    public static final int Appearing = 8;
}
