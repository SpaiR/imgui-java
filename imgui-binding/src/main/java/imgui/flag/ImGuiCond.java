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
     * Set the variable
     */
    public static final int Always = 1;
    /**
     * Set the variable once per runtime session (only the first call with succeed)
     */
    public static final int Once = 1 << 1;
    /**
     * Set the variable if the object/window has no persistently saved data (no entry in .ini file)
     */
    public static final int FirstUseEver = 1 << 2;
    /**
     * Set the variable if the object/window is appearing after being hidden/inactive (or the first time)
     */
    public static final int Appearing = 1 << 3;
}
