package imgui.enums;

/**
 * Gamepad/Keyboard directional navigation
 * Keyboard: Set io.ConfigFlags |= ImGuiConfigFlags_NavEnableKeyboard to enable. NewFrame() will automatically fill io.NavInputs[] based on your io.KeysDown[] + io.KeyMap[] arrays.
 * Gamepad:  Set io.ConfigFlags |= ImGuiConfigFlags_NavEnableGamepad to enable. Back-end: set ImGuiBackendFlags_HasGamepad and fill the io.NavInputs[] fields before calling NewFrame(). Note that io.NavInputs[] is cleared by EndFrame().
 * Read instructions in imgui.cpp for more details. Download PNG/PSD at http://goo.gl/9LgVZW.
 **/
public enum ImGuiNavInput {
    Activate(0),
    Cancel(1),
    Input(2),
    Menu(3),
    DpadLeft(4),
    DpadRight(5),
    DpadUp(6),
    DpadDown(7),
    LStickLeft(8),
    LStickRight(9),
    LStickUp(10),
    LStickDown(11),
    FocusPrev(12),
    FocusNext(13),
    TweakSlow(14),
    TweakFast(15);

    private final int code;

    ImGuiNavInput(int code) {
        this.code = code;
    }

    public int toInt() {
        return code;
    }
}
