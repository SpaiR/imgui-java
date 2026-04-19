package imgui.internal.flag;


public final class ImGuiButtonFlags {
    private ImGuiButtonFlags() {
    }

    /**
     * Definition: {@code 0}
     */
    public static final int None = 0;

    /**
     * React on left mouse button (default)
     *
     * <p>Definition: {@code 1 << 0}
     */
    public static final int MouseButtonLeft = 1;

    /**
     * React on right mouse button
     *
     * <p>Definition: {@code 1 << 1}
     */
    public static final int MouseButtonRight = 2;

    /**
     * React on center mouse button
     *
     * <p>Definition: {@code 1 << 2}
     */
    public static final int MouseButtonMiddle = 4;

    /**
     * [Internal]
     *
     * <p>Definition: {@code ImGuiButtonFlags_MouseButtonLeft | ImGuiButtonFlags_MouseButtonRight | ImGuiButtonFlags_MouseButtonMiddle}
     */
    public static final int MouseButtonMask_ = 7;

    /**
     * InvisibleButton(): do not disable navigation/tabbing. Otherwise disabled by default.
     *
     * <p>Definition: {@code 1 << 3}
     */
    public static final int EnableNav = 8;

    /**
     * Hit testing will allow subsequent widgets to overlap this one. Require previous frame HoveredId to match before being usable. Shortcut to calling SetNextItemAllowOverlap().
     *
     * <p>Definition: {@code 1 << 12}
     */
    public static final int AllowOverlap = 4096;

    /**
     * return true on click (mouse down event)
     *
     * <p>Definition: {@code 1 << 4}
     */
    public static final int PressedOnClick = 16;

    /**
     * [Default] return true on click + release on same item{@code <}-- this is what the majority of Button are using
     *
     * <p>Definition: {@code 1 << 5}
     */
    public static final int PressedOnClickRelease = 32;

    /**
     * return true on click + release even if the release event is not done while hovering the item
     *
     * <p>Definition: {@code 1 << 6}
     */
    public static final int PressedOnClickReleaseAnywhere = 64;

    /**
     * return true on release (default requires click+release). Prior to 2026/03/20 this implied ImGuiButtonFlags_NoHoldingActiveId but they are separate now.
     *
     * <p>Definition: {@code 1 << 7}
     */
    public static final int PressedOnRelease = 128;

    /**
     * return true on double-click (default requires click+release)
     *
     * <p>Definition: {@code 1 << 8}
     */
    public static final int PressedOnDoubleClick = 256;

    /**
     * return true when held into while we are drag and dropping another item (used by e.g. tree nodes, collapsing headers)
     *
     * <p>Definition: {@code 1 << 9}
     */
    public static final int PressedOnDragDropHold = 512;

    /**
     * allow interactions even if a child window is overlapping
     *
     * <p>Definition: {@code 1 << 11}
     */
    public static final int FlattenChildren = 2048;

    /**
     * vertically align button to match text baseline - ButtonEx() only // FIXME: Should be removed and handled by SmallButton(), not possible currently because of DC.CursorPosPrevLine
     *
     * <p>Definition: {@code 1 << 15}
     */
    public static final int AlignTextBaseLine = 32768;

    /**
     * disable mouse interaction if a key modifier is held
     *
     * <p>Definition: {@code 1 << 16}
     */
    public static final int NoKeyModsAllowed = 65536;

    /**
     * don't set ActiveId while holding the mouse (ImGuiButtonFlags_PressedOnClick only)
     *
     * <p>Definition: {@code 1 << 17}
     */
    public static final int NoHoldingActiveId = 131072;

    /**
     * don't override navigation focus when activated (FIXME: this is essentially used every time an item uses ImGuiItemFlags_NoNav, but because legacy specs don't requires LastItemData to be set ButtonBehavior(), we can't poll g.LastItemData.ItemFlags)
     *
     * <p>Definition: {@code 1 << 18}
     */
    public static final int NoNavFocus = 262144;

    /**
     * don't report as hovered when nav focus is on this item
     *
     * <p>Definition: {@code 1 << 19}
     */
    public static final int NoHoveredOnFocus = 524288;

    /**
     * don't set key/input owner on the initial click (note: mouse buttons are keys! often, the key in question will be ImGuiKey_MouseLeft!)
     *
     * <p>Definition: {@code 1 << 20}
     */
    public static final int NoSetKeyOwner = 1048576;

    /**
     * don't test key/input owner when polling the key (note: mouse buttons are keys! often, the key in question will be ImGuiKey_MouseLeft!)
     *
     * <p>Definition: {@code 1 << 21}
     */
    public static final int NoTestKeyOwner = 2097152;

    /**
     * [EXPERIMENTAL: Not very well specced]. Don't focus parent window when clicking.
     *
     * <p>Definition: {@code 1 << 22}
     */
    public static final int NoFocus = 4194304;

    /**
     * Definition: {@code ImGuiButtonFlags_PressedOnClick | ImGuiButtonFlags_PressedOnClickRelease | ImGuiButtonFlags_PressedOnClickReleaseAnywhere | ImGuiButtonFlags_PressedOnRelease | ImGuiButtonFlags_PressedOnDoubleClick | ImGuiButtonFlags_PressedOnDragDropHold}
     */
    public static final int PressedOnMask_ = 1008;

    /**
     * Definition: {@code ImGuiButtonFlags_PressedOnClickRelease}
     */
    public static final int PressedOnDefault_ = 32;
}
