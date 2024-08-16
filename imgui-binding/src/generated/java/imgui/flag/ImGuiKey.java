package imgui.flag;


/**
 * User fill ImGuiIO.KeyMap[] array with indices into the ImGuiIO.KeysDown[512] array
 */
public final class ImGuiKey {
    private ImGuiKey() {
    }

    /**
     * Keyboard
     *
     * <p>Definition: {@code 0}
     */
    public static final int None = 0;

    /**
     * == ImGuiKey_NamedKey_BEGIN
     *
     * <p>Definition: {@code 512}
     */
    public static final int Tab = 512;

    public static final int LeftArrow = 513;

    public static final int RightArrow = 514;

    public static final int UpArrow = 515;

    public static final int DownArrow = 516;

    public static final int PageUp = 517;

    public static final int PageDown = 518;

    public static final int Home = 519;

    public static final int End = 520;

    public static final int Insert = 521;

    public static final int Delete = 522;

    public static final int Backspace = 523;

    public static final int Space = 524;

    public static final int Enter = 525;

    public static final int Escape = 526;

    public static final int LeftCtrl = 527;

    public static final int LeftShift = 528;

    public static final int LeftAlt = 529;

    public static final int LeftSuper = 530;

    public static final int RightCtrl = 531;

    public static final int RightShift = 532;

    public static final int RightAlt = 533;

    public static final int RightSuper = 534;

    public static final int Menu = 535;

    public static final int _0 = 536;

    public static final int _1 = 537;

    public static final int _2 = 538;

    public static final int _3 = 539;

    public static final int _4 = 540;

    public static final int _5 = 541;

    public static final int _6 = 542;

    public static final int _7 = 543;

    public static final int _8 = 544;

    public static final int _9 = 545;

    public static final int A = 546;

    public static final int B = 547;

    public static final int C = 548;

    public static final int D = 549;

    public static final int E = 550;

    public static final int F = 551;

    public static final int G = 552;

    public static final int H = 553;

    public static final int I = 554;

    public static final int J = 555;

    public static final int K = 556;

    public static final int L = 557;

    public static final int M = 558;

    public static final int N = 559;

    public static final int O = 560;

    public static final int P = 561;

    public static final int Q = 562;

    public static final int R = 563;

    public static final int S = 564;

    public static final int T = 565;

    public static final int U = 566;

    public static final int V = 567;

    public static final int W = 568;

    public static final int X = 569;

    public static final int Y = 570;

    public static final int Z = 571;

    public static final int F1 = 572;

    public static final int F2 = 573;

    public static final int F3 = 574;

    public static final int F4 = 575;

    public static final int F5 = 576;

    public static final int F6 = 577;

    public static final int F7 = 578;

    public static final int F8 = 579;

    public static final int F9 = 580;

    public static final int F10 = 581;

    public static final int F11 = 582;

    public static final int F12 = 583;

    /**
     * '
     */
    public static final int Apostrophe = 584;

    /**
     * ,
     */
    public static final int Comma = 585;

    /**
     * -
     */
    public static final int Minus = 586;

    /**
     * .
     */
    public static final int Period = 587;

    /**
     * /
     */
    public static final int Slash = 588;

    /**
     * ;
     */
    public static final int Semicolon = 589;

    /**
     * =
     */
    public static final int Equal = 590;

    /**
     * [
     */
    public static final int LeftBracket = 591;

    /**
     * \ (this text inhibit multiline comment caused by backslash)
     */
    public static final int Backslash = 592;

    /**
     * ]
     */
    public static final int RightBracket = 593;

    /**
     * `
     */
    public static final int GraveAccent = 594;

    public static final int CapsLock = 595;

    public static final int ScrollLock = 596;

    public static final int NumLock = 597;

    public static final int PrintScreen = 598;

    public static final int Pause = 599;

    public static final int Keypad0 = 600;

    public static final int Keypad1 = 601;

    public static final int Keypad2 = 602;

    public static final int Keypad3 = 603;

    public static final int Keypad4 = 604;

    public static final int Keypad5 = 605;

    public static final int Keypad6 = 606;

    public static final int Keypad7 = 607;

    public static final int Keypad8 = 608;

    public static final int Keypad9 = 609;

    public static final int KeypadDecimal = 610;

    public static final int KeypadDivide = 611;

    public static final int KeypadMultiply = 612;

    public static final int KeypadSubtract = 613;

    public static final int KeypadAdd = 614;

    public static final int KeypadEnter = 615;

    public static final int KeypadEqual = 616;

    /**
     * Menu (Xbox)          + (Switch)   Start/Options (PS) // --
     */
    public static final int GamepadStart = 617;

    /**
     * View (Xbox)          - (Switch)   Share (PS)         // --
     */
    public static final int GamepadBack = 618;

    /**
     * Y (Xbox)             X (Switch)   Triangle (PS)      // {@code ->} ImGuiNavInput_Input
     */
    public static final int GamepadFaceUp = 619;

    /**
     * A (Xbox)             B (Switch)   Cross (PS)         // {@code ->} ImGuiNavInput_Activate
     */
    public static final int GamepadFaceDown = 620;

    /**
     * X (Xbox)             Y (Switch)   Square (PS)        // {@code ->} ImGuiNavInput_Menu
     */
    public static final int GamepadFaceLeft = 621;

    /**
     * B (Xbox)             A (Switch)   Circle (PS)        // {@code ->} ImGuiNavInput_Cancel
     */
    public static final int GamepadFaceRight = 622;

    /**
     * D-pad Up                                             // {@code ->} ImGuiNavInput_DpadUp
     */
    public static final int GamepadDpadUp = 623;

    /**
     * D-pad Down                                           // {@code ->} ImGuiNavInput_DpadDown
     */
    public static final int GamepadDpadDown = 624;

    /**
     * D-pad Left                                           // {@code ->} ImGuiNavInput_DpadLeft
     */
    public static final int GamepadDpadLeft = 625;

    /**
     * D-pad Right                                          // {@code ->} ImGuiNavInput_DpadRight
     */
    public static final int GamepadDpadRight = 626;

    /**
     * L Bumper (Xbox)      L (Switch)   L1 (PS)            // {@code ->} ImGuiNavInput_FocusPrev + ImGuiNavInput_TweakSlow
     */
    public static final int GamepadL1 = 627;

    /**
     * R Bumper (Xbox)      R (Switch)   R1 (PS)            // {@code ->} ImGuiNavInput_FocusNext + ImGuiNavInput_TweakFast
     */
    public static final int GamepadR1 = 628;

    /**
     * L Trigger (Xbox)     ZL (Switch)  L2 (PS) [Analog]
     */
    public static final int GamepadL2 = 629;

    /**
     * R Trigger (Xbox)     ZR (Switch)  R2 (PS) [Analog]
     */
    public static final int GamepadR2 = 630;

    /**
     * L Thumbstick (Xbox)  L3 (Switch)  L3 (PS)
     */
    public static final int GamepadL3 = 631;

    /**
     * R Thumbstick (Xbox)  R3 (Switch)  R3 (PS)
     */
    public static final int GamepadR3 = 632;

    /**
     * [Analog]                                             // {@code ->} ImGuiNavInput_LStickUp
     */
    public static final int GamepadLStickUp = 633;

    /**
     * [Analog]                                             // {@code ->} ImGuiNavInput_LStickDown
     */
    public static final int GamepadLStickDown = 634;

    /**
     * [Analog]                                             // {@code ->} ImGuiNavInput_LStickLeft
     */
    public static final int GamepadLStickLeft = 635;

    /**
     * [Analog]                                             // {@code ->} ImGuiNavInput_LStickRight
     */
    public static final int GamepadLStickRight = 636;

    /**
     * [Analog]
     */
    public static final int GamepadRStickUp = 637;

    /**
     * [Analog]
     */
    public static final int GamepadRStickDown = 638;

    /**
     * [Analog]
     */
    public static final int GamepadRStickLeft = 639;

    /**
     * [Analog]
     */
    public static final int GamepadRStickRight = 640;

    /**
     * Keyboard Modifiers - This is mirroring the data also written to io.KeyCtrl, io.KeyShift, io.KeyAlt, io.KeySuper, in a format allowing them to be accessed via standard key API, allowing calls such as IsKeyPressed(), IsKeyReleased(), querying duration etc. - Code polling every keys (e.g. an interface to detect a key press for input mapping) might want to ignore those and prefer using the real keys (e.g. ImGuiKey_LeftCtrl, ImGuiKey_RightCtrl instead of ImGuiKey_ModCtrl). - In theory the value of keyboard modifiers should be roughly equivalent to a logical or of the equivalent left/right keys. In practice: it's complicated; mods are often provided from different sources. Keyboard layout, IME, sticky keys and backends tend to interfere and break that equivalence. The safer decision is to relay that ambiguity down to the end-user...
     */
    public static final int ModCtrl = 641;

    /**
     * Keyboard Modifiers - This is mirroring the data also written to io.KeyCtrl, io.KeyShift, io.KeyAlt, io.KeySuper, in a format allowing them to be accessed via standard key API, allowing calls such as IsKeyPressed(), IsKeyReleased(), querying duration etc. - Code polling every keys (e.g. an interface to detect a key press for input mapping) might want to ignore those and prefer using the real keys (e.g. ImGuiKey_LeftCtrl, ImGuiKey_RightCtrl instead of ImGuiKey_ModCtrl). - In theory the value of keyboard modifiers should be roughly equivalent to a logical or of the equivalent left/right keys. In practice: it's complicated; mods are often provided from different sources. Keyboard layout, IME, sticky keys and backends tend to interfere and break that equivalence. The safer decision is to relay that ambiguity down to the end-user...
     */
    public static final int ModShift = 642;

    /**
     * Keyboard Modifiers - This is mirroring the data also written to io.KeyCtrl, io.KeyShift, io.KeyAlt, io.KeySuper, in a format allowing them to be accessed via standard key API, allowing calls such as IsKeyPressed(), IsKeyReleased(), querying duration etc. - Code polling every keys (e.g. an interface to detect a key press for input mapping) might want to ignore those and prefer using the real keys (e.g. ImGuiKey_LeftCtrl, ImGuiKey_RightCtrl instead of ImGuiKey_ModCtrl). - In theory the value of keyboard modifiers should be roughly equivalent to a logical or of the equivalent left/right keys. In practice: it's complicated; mods are often provided from different sources. Keyboard layout, IME, sticky keys and backends tend to interfere and break that equivalence. The safer decision is to relay that ambiguity down to the end-user...
     */
    public static final int ModAlt = 643;

    /**
     * Keyboard Modifiers - This is mirroring the data also written to io.KeyCtrl, io.KeyShift, io.KeyAlt, io.KeySuper, in a format allowing them to be accessed via standard key API, allowing calls such as IsKeyPressed(), IsKeyReleased(), querying duration etc. - Code polling every keys (e.g. an interface to detect a key press for input mapping) might want to ignore those and prefer using the real keys (e.g. ImGuiKey_LeftCtrl, ImGuiKey_RightCtrl instead of ImGuiKey_ModCtrl). - In theory the value of keyboard modifiers should be roughly equivalent to a logical or of the equivalent left/right keys. In practice: it's complicated; mods are often provided from different sources. Keyboard layout, IME, sticky keys and backends tend to interfere and break that equivalence. The safer decision is to relay that ambiguity down to the end-user...
     */
    public static final int ModSuper = 644;

    /**
     * No valid ImGuiKey is ever greater than this value
     */
    public static final int COUNT = 645;

    /**
     * [Internal] Prior to 1.87 we required user to fill io.KeysDown[512] using their own native index + a io.KeyMap[] array. We are ditching this method but keeping a legacy path for user code doing e.g. IsKeyPressed(MY_NATIVE_KEY_CODE)
     *
     * <p>Definition: {@code 512}
     */
    public static final int NamedKey_BEGIN = 512;

    /**
     * [Internal] Prior to 1.87 we required user to fill io.KeysDown[512] using their own native index + a io.KeyMap[] array. We are ditching this method but keeping a legacy path for user code doing e.g. IsKeyPressed(MY_NATIVE_KEY_CODE)
     *
     * <p>Definition: {@code ImGuiKey_COUNT}
     */
    public static final int NamedKey_END = 645;

    /**
     * [Internal] Prior to 1.87 we required user to fill io.KeysDown[512] using their own native index + a io.KeyMap[] array. We are ditching this method but keeping a legacy path for user code doing e.g. IsKeyPressed(MY_NATIVE_KEY_CODE)
     *
     * <p>Definition: {@code ImGuiKey_NamedKey_END - ImGuiKey_NamedKey_BEGIN}
     */
    public static final int NamedKey_COUNT = 133;

    /**
     * Size of KeysData[]: hold legacy 0..512 keycodes + named keys
     *
     * <p>Definition: {@code ImGuiKey_COUNT}
     */
    public static final int KeysData_SIZE = 645;

    /**
     * First key stored in KeysData[0]
     *
     * <p>Definition: {@code 0}
     */
    public static final int KeysData_OFFSET = 0;

    /**
     * Renamed in 1.87
     *
     * <p>Definition: {@code ImGuiKey_KeypadEnter}
     */
    public static final int KeyPadEnter = 615;
}
