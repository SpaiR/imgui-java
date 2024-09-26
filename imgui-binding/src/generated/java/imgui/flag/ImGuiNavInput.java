package imgui.flag;




/**
 * Gamepad/Keyboard directional navigation
 * Keyboard: Set io.ConfigFlags |= ImGuiConfigFlags_NavEnableKeyboard to enable.
 * NewFrame() will automatically fill io.NavInputs[] based on your io.KeysDown[] + io.KeyMap[] arrays.
 * Gamepad:  Set io.ConfigFlags |= ImGuiConfigFlags_NavEnableGamepad to enable.
 * Backend: set ImGuiBackendFlags_HasGamepad and fill the io.NavInputs[] fields before calling NewFrame().
 * Note that io.NavInputs[] is cleared by EndFrame().
 * Read instructions in imgui.cpp for more details. Download PNG/PSD at http://dearimgui.org/controls_sheets.
 */

public final class ImGuiNavInput {
    private ImGuiNavInput() {
    }

    /**
     * Activate / Open / Toggle / Tweak value       // e.g. Cross  (PS4), A (Xbox), A (Switch), Space (Keyboard)
     */
    public static final int Activate = 0;

    /**
     * Cancel / Close / Exit                        // e.g. Circle (PS4), B (Xbox), B (Switch), Escape (Keyboard)
     */
    public static final int Cancel = 1;

    /**
     * Text input / On-Screen keyboard              // e.g. Triang.(PS4), Y (Xbox), X (Switch), Return (Keyboard)
     */
    public static final int Input = 2;

    /**
     * Tap: Toggle menu / Hold: Focus, Move, Resize // e.g. Square (PS4), X (Xbox), Y (Switch), Alt (Keyboard)
     */
    public static final int Menu = 3;

    /**
     * Move / Tweak / Resize window (w/ PadMenu)    // e.g. D-pad Left/Right/Up/Down (Gamepads), Arrow keys (Keyboard)
     */
    public static final int DpadLeft = 4;

    public static final int DpadRight = 5;

    public static final int DpadUp = 6;

    public static final int DpadDown = 7;

    /**
     * Scroll / Move window (w/ PadMenu)            // e.g. Left Analog Stick Left/Right/Up/Down
     */
    public static final int LStickLeft = 8;

    public static final int LStickRight = 9;

    public static final int LStickUp = 10;

    public static final int LStickDown = 11;

    /**
     * Focus Next window (w/ PadMenu)               // e.g. L1 or L2 (PS4), LB or LT (Xbox), L or ZL (Switch)
     */
    public static final int FocusPrev = 12;

    /**
     * Focus Prev window (w/ PadMenu)               // e.g. R1 or R2 (PS4), RB or RT (Xbox), R or ZL (Switch)
     */
    public static final int FocusNext = 13;

    /**
     * Slower tweaks                                // e.g. L1 or L2 (PS4), LB or LT (Xbox), L or ZL (Switch)
     */
    public static final int TweakSlow = 14;

    /**
     * Faster tweaks                                // e.g. R1 or R2 (PS4), RB or RT (Xbox), R or ZL (Switch)
     */
    public static final int TweakFast = 15;

    /**
     * Move left                                    // = Arrow keys
     */
    public static final int KeyLeft_ = 16;

    /**
     * Move right
     */
    public static final int KeyRight_ = 17;

    /**
     * Move up
     */
    public static final int KeyUp_ = 18;

    /**
     * Move down
     */
    public static final int KeyDown_ = 19;

    public static final int COUNT = 20;
}
