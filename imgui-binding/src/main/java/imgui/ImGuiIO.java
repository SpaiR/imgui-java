package imgui;

import imgui.binding.ImGuiStruct;
import imgui.binding.annotation.ArgValue;
import imgui.binding.annotation.BindingField;
import imgui.binding.annotation.BindingMethod;
import imgui.binding.annotation.BindingSource;
import imgui.binding.annotation.OptArg;
import imgui.binding.annotation.ReturnValue;
import imgui.binding.annotation.TypeArray;
import imgui.callback.ImStrConsumer;
import imgui.callback.ImStrSupplier;

/**
 * Communicate most settings and inputs/outputs to Dear ImGui using this structure.
 * Access via ImGui::GetIO(). Read 'Programmer guide' section in .cpp file for general usage.
 */
@BindingSource
public final class ImGuiIO extends ImGuiStruct {
    public ImGuiIO(final long ptr) {
        super(ptr);
    }

    /*JNI
        #include "_common.h"
        #define THIS ((ImGuiIO*)STRUCT_PTR)
     */

    //------------------------------------------------------------------
    // Configuration (fill once)
    //------------------------------------------------------------------

    /**
     * See ImGuiConfigFlags enum. Set by user/application. Gamepad/keyboard navigation options, etc.
     */
    @BindingField(isFlag = true)
    public int ConfigFlags;

    /**
     * See ImGuiBackendFlags enum. Set by backend to communicate features supported by the backend.
     */
    @BindingField(isFlag = true)
    public int BackendFlags;

    /**
     * Main display size, in pixels (generally == {@code GetMainViewport()->Size}). May change every frame.
     */
    @BindingField
    public ImVec2 DisplaySize;

    /**
     * Time elapsed since last frame, in seconds. May change every frame.
     */
    @BindingField
    public float DeltaTime;

    /**
     * Minimum time between saving positions/sizes to .ini file, in seconds.
     */
    @BindingField
    public float IniSavingRate;

    /**
     * Path to .ini file. Set NULL to disable automatic .ini loading/saving, if e.g. you want to manually load/save from memory.
     */
    @BindingField
    public String IniFilename;

    /**
     * Path to .log file (default parameter to ImGui::LogToFile when no file is specified).
     */
    @BindingField
    public String LogFilename;

    /**
     * Time for a double-click, in seconds.
     */
    @BindingField
    public float MouseDoubleClickTime;

    /**
     * Distance threshold to stay in to validate a double-click, in pixels.
     */
    @BindingField
    public float MouseDoubleClickMaxDist;

    /**
     * Distance threshold before considering we are dragging.
     */
    @BindingField
    public float MouseDragThreshold;

    /**
     * When holding a key/button, time before it starts repeating, in seconds (for buttons in Repeat mode, etc.).
     */
    @BindingField
    public float KeyRepeatDelay;

    /**
     * When holding a key/button, rate at which it repeats, in seconds.
     */
    @BindingField
    public float KeyRepeatRate;

    /**
     * Font atlas: load, rasterize and pack one or more fonts into a single texture.
     */
    @BindingField
    @ReturnValue(isStatic = true)
    public ImFontAtlas Fonts;

    /**
     * Global scale all fonts
     */
    @BindingField
    public float FontGlobalScale;

    /**
     * Allow user scaling text of individual window with CTRL+Wheel.
     */
    @BindingField
    public boolean FontAllowUserScaling;

    /**
     * Font to use on NewFrame(). Use NULL to uses Fonts{@code ->}Fonts[0].
     */
    @BindingField
    public ImFont FontDefault;

    /**
     * For retina display or other situations where window coordinates are different from framebuffer coordinates. This generally ends up in ImDrawData::FramebufferScale.
     */
    @BindingField
    public ImVec2 DisplayFramebufferScale;

    // Docking options (when ImGuiConfigFlags_DockingEnable is set)

    /**
     * Simplified docking mode: disable window splitting, so docking is limited to merging multiple windows together into tab-bars.
     */
    @BindingField
    public boolean ConfigDockingNoSplit;

    /**
     * Enable docking with holding Shift key (reduce visual noise, allows dropping in wider space)
     */
    @BindingField
    public boolean ConfigDockingWithShift;

    @BindingField
    public boolean ConfigDockingAlwaysTabBar;

    /**
     * Make window or viewport transparent when docking and only display docking boxes on the target viewport. Useful if rendering of multiple viewport cannot be synced. Best used with ConfigViewportsNoAutoMerge.
     */
    @BindingField
    public boolean ConfigDockingTransparentPayload;

    // Viewport options (when ImGuiConfigFlags_ViewportsEnable is set)

    /**
     * Set to make all floating imgui windows always create their own viewport. Otherwise, they are merged into the main host viewports when overlapping it. May also set ImGuiViewportFlags_NoAutoMerge on individual viewport.
     */
    @BindingField
    public boolean ConfigViewportsNoAutoMerge;

    /**
     * Disable default OS task bar icon flag for secondary viewports. When a viewport doesn't want a task bar icon, ImGuiViewportFlags_NoTaskBarIcon will be set on it.
     */
    @BindingField
    public boolean ConfigViewportsNoTaskBarIcon;

    /**
     * Disable default OS window decoration flag for secondary viewports. When a viewport doesn't want window decorations, ImGuiViewportFlags_NoDecoration will be set on it. Enabling decoration can create subsequent issues at OS levels (e.g. minimum window size).
     */
    @BindingField
    public boolean ConfigViewportsNoDecoration;

    /**
     * Disable default OS parenting to main viewport for secondary viewports. By default, viewports are marked with ParentViewportId = {@code <main_viewport>}, expecting the platform backend to setup a parent/child relationship between the OS windows (some backend may ignore this). Set to true if you want the default to be 0, then all viewports will be top-level OS windows.
     */
    @BindingField
    public boolean ConfigViewportsNoDefaultParent;

    // Miscellaneous options

    /**
     * Request ImGui to draw a mouse cursor for you (if you are on a platform without a mouse cursor).
     * Cannot be easily renamed to 'io.ConfigXXX' because this is frequently used by backend implementations.
     */
    @BindingField
    public boolean MouseDrawCursor;

    /**
     * OS X style: Text editing cursor movement using Alt instead of Ctrl, Shortcuts using Cmd/Super instead of Ctrl,
     * Line/Text Start and End using Cmd+Arrows instead of Home/End, Double click selects by word instead of selecting whole text,
     * Multi-selection in lists uses Cmd/Super instead of Ctrl
     */
    @BindingField
    public boolean ConfigMacOSXBehaviors;

    /**
     * Enable input queue trickling: some types of events submitted during the same frame (e.g. button down + up) will be spread over multiple frames, improving interactions with low framerates.
     */
    @BindingField
    public boolean ConfigInputTrickleEventQueue;

    /**
     * Set to false to disable blinking cursor, for users who consider it distracting.
     */
    @BindingField
    public boolean ConfigInputTextCursorBlink;

    /**
     * [BETA] Enable turning DragXXX widgets into text input with a simple mouse click-release (without moving). Not desirable on devices without a keyboard.
     */
    @BindingField
    public boolean ConfigDragClickToInputText;

    /**
     * Enable resizing of windows from their edges and from the lower-left corner.
     * This requires (io.BackendFlags {@code &} ImGuiBackendFlags_HasMouseCursors) because it needs mouse cursor feedback.
     * (This used to be a per-window ImGuiWindowFlags_ResizeFromAnySide flag)
     */
    @BindingField
    public boolean ConfigWindowsResizeFromEdges;

    /**
     * Enable allowing to move windows only when clicking on their title bar. Does not apply to windows without a title bar.
     */
    @BindingField
    public boolean ConfigWindowsMoveFromTitleBarOnly;

    /**
     * [Timer (in seconds) to free transient windows/tables memory buffers when unused. Set to -1.0f to disable.
     */
    @BindingField
    public boolean ConfigMemoryCompactTimer;

    //------------------------------------------------------------------
    // Platform Functions
    //------------------------------------------------------------------

    /**
     * Optional: Platform backend name (informational only! will be displayed in About Window) + User data for backend/wrappers to store their own stuff.
     */
    @BindingField
    public String BackendPlatformName;

    @BindingField
    public String BackendRendererName;

    // Optional: Access OS clipboard
    // (default to use native Win32 clipIsMouseDraggingboard on Windows, otherwise uses a private clipboard. Override to access OS clipboard on other architectures)

    /*JNI
        jobject _setClipboardTextCallback = NULL;
        jobject _getClipboardTextCallback = NULL;

        void setClipboardTextStub(void* userData, const char* text) {
            Jni::CallImStrConsumer(Jni::GetEnv(), _setClipboardTextCallback, text);
        }

        const char* getClipboardTextStub(void* user_data) {
            JNIEnv* env = Jni::GetEnv();
            jstring jstr = Jni::CallImStrSupplier(env, _getClipboardTextCallback);
            return env->GetStringUTFChars(jstr, 0);
        }
     */

    public native void setSetClipboardTextFn(ImStrConsumer setClipboardTextCallback); /*
        if (_setClipboardTextCallback != NULL) {
            env->DeleteGlobalRef(_setClipboardTextCallback);
        }
        _setClipboardTextCallback = env->NewGlobalRef(setClipboardTextCallback);
        THIS->SetClipboardTextFn = setClipboardTextStub;
    */

    public native void setGetClipboardTextFn(ImStrSupplier getClipboardTextCallback); /*
        if (_getClipboardTextCallback != NULL) {
            env->DeleteGlobalRef(_getClipboardTextCallback);
        }
        _getClipboardTextCallback = env->NewGlobalRef(getClipboardTextCallback);
        THIS->GetClipboardTextFn = getClipboardTextStub;
    */

    //------------------------------------------------------------------
    // Input - Call before calling NewFrame()
    //------------------------------------------------------------------

    // Input Functions

    /**
     * Queue a new key down/up event. Key should be "translated" (as in, generally ImGuiKey_A matches the key end-user would use to emit an 'A' character)
     */
    @BindingMethod
    public native void AddKeyEvent(@ArgValue(staticCast = "ImGuiKey") int key, boolean down);

    /**
     * Queue a new key down/up event for analog values (e.g. ImGuiKey_Gamepad_ values). Dead-zones should be handled by the backend.
     */
    @BindingMethod
    public native void AddKeyAnalogEvent(@ArgValue(staticCast = "ImGuiKey") int key, boolean down, float v);

    /**
     * Queue a mouse position update. Use -FLT_MAX,-FLT_MAX to signify no mouse (e.g. app not focused and not hovered)
     */
    @BindingMethod
    public native void AddMousePosEvent(float x, float y);

    /**
     * Queue a mouse button change
     */
    @BindingMethod
    public native void AddMouseButtonEvent(int button, boolean down);

    /**
     * Queue a mouse wheel update
     */
    @BindingMethod
    public native void AddMouseWheelEvent(float whX, float whY);

    /**
     * Queue a mouse hovered viewport. Requires backend to set ImGuiBackendFlags_HasMouseHoveredViewport to call this (for multi-viewport support).
     */
    @BindingMethod
    public native void AddMouseViewportEvent(@ArgValue(staticCast = "ImGuiID") int id);

    /**
     * Queue a gain/loss of focus for the application (generally based on OS/platform focus of your window)
     */
    @BindingMethod
    public native void AddFocusEvent(boolean focused);

    /**
     * Queue new character input.
     */
    @BindingMethod
    public native void AddInputCharacter(@ArgValue(callPrefix = "(unsigned int)") int c);

    /**
     * Queue new character input from an UTF-16 character, it can be a surrogate
     */
    @BindingMethod
    public native void AddInputCharacterUTF16(@ArgValue(callPrefix = "(ImWchar16)") short c);

    /**
     * Queue new characters input from an UTF-8 string.
     */
    @BindingMethod
    public native void AddInputCharactersUTF8(String str);

    /**
     * [Optional] Specify index for legacy {@code <1.87} IsKeyXXX() functions with native indices + specify native keycode, scancode.
     */
    @BindingMethod
    public native void SetKeyEventNativeData(@ArgValue(staticCast = "ImGuiKey") int key, int nativeKeycode, int nativeScancode, @OptArg int nativeLegacyIndex);

    /**
     * Set master flag for accepting key/mouse/text events (default to true). Useful if you have native dialog boxes that are interrupting your application loop/refresh, and you want to disable events being queued while your app is frozen.
     */
    @BindingMethod
    public native void SetAppAcceptingEvents(boolean acceptingEvents);

    /**
     * [Internal] Clear the text input buffer manually
     */
    @BindingMethod
    public native void ClearInputCharacters();

    /**
     * [Internal] Release all keys
     */
    @BindingMethod
    public native void ClearInputKeys();

    //------------------------------------------------------------------
    // Output - Updated by NewFrame() or EndFrame()/Render()
    // (when reading from the io.WantCaptureMouse, io.WantCaptureKeyboard flags to dispatch your inputs, it is
    //  generally easier and more correct to use their state BEFORE calling NewFrame(). See FAQ for details!)
    //------------------------------------------------------------------

    /**
     * Set when Dear ImGui will use mouse inputs, in this case do not dispatch them to your main game/application
     * (either way, always pass on mouse inputs to imgui).
     * (e.g. unclicked mouse is hovering over an imgui window, widget is active, mouse was clicked over an imgui window, etc.).
     */
    @BindingField
    public boolean WantCaptureMouse;

    /**
     * Set when Dear ImGui will use keyboard inputs, in this case do not dispatch them to your main game/application
     * (either way, always pass keyboard inputs to imgui). (e.g. InputText active, or an imgui window is focused and navigation is enabled, etc.).
     */
    @BindingField
    public boolean WantCaptureKeyboard;

    /**
     * Mobile/console: when set, you may display an on-screen keyboard.
     * This is set by Dear ImGui when it wants textual keyboard input to happen (e.g. when a InputText widget is active).
     */
    @BindingField
    public boolean WantTextInput;

    /**
     * MousePos has been altered, backend should reposition mouse on next frame. Rarely used! Set only when ImGuiConfigFlags_NavEnableSetMousePos flag is enabled.
     */
    @BindingField
    public boolean WantSetMousePos;

    /**
     * When manual .ini load/save is active (io.IniFilename == NULL),
     * this will be set to notify your application that you can call SaveIniSettingsToMemory() and save yourself.
     * Important: clear io.WantSaveIniSettings yourself after saving!
     */
    @BindingField
    public boolean WantSaveIniSettings;

    /**
     * Keyboard/Gamepad navigation is currently allowed (will handle ImGuiKey_NavXXX events) = a window is focused
     * and it doesn't use the ImGuiWindowFlags_NoNavInputs flag.
     */
    @BindingField
    public boolean NavActive;

    /**
     * Keyboard/Gamepad navigation is visible and allowed (will handle ImGuiKey_NavXXX events).
     */
    @BindingField
    public boolean NavVisible;

    /**
     * Application framerate estimate, in frame per second. Solely for convenience. Rolling average estimation based on io.DeltaTime over 120 frames.
     * Solely for convenience. Rolling average estimation based on IO.DeltaTime over 120 frames
     */
    @BindingField
    public float Framerate;

    /**
     * Vertices output during last call to Render()
     */
    @BindingField
    public int MetricsRenderVertices;

    /**
     * Indices output during last call to Render() = number of triangles * 3
     */
    @BindingField
    public int MetricsRenderIndices;

    /**
     * Number of visible windows
     */
    @BindingField
    public int MetricsRenderWindows;

    /**
     * Number of active windows
     */
    @BindingField
    public int MetricsActiveWindows;

    /**
     * Number of active allocations, updated by MemAlloc/MemFree based on current context. May be off if you have multiple imgui contexts.
     */
    @BindingField
    public int MetricsActiveAllocations;

    /**
     * Mouse delta. Note that this is zero if either current or previous position are invalid (-FLT_MAX,-FLT_MAX), so a disappearing/reappearing mouse won't have a huge delta.
     */
    @BindingField
    public ImVec2 MouseDelta;

    /**
     * Map of indices into the KeysDown[512] entries array which represent your "native" keyboard state.
     */
    @BindingField
    @TypeArray(type = "int", size = "ImGuiKey_COUNT")
    @Deprecated
    public int[] KeyMap;

    /**
     * Keyboard keys that are pressed (ideally left in the "native" order your engine has access to keyboard keys, so you can use your own defines/enums for keys).
     * This used to be [512] sized. It is now ImGuiKey_COUNT to allow legacy io.KeysDown[GetKeyIndex(...)] to work without an overflow.
     */
    @BindingField
    @TypeArray(type = "boolean", size = "ImGuiKey_COUNT")
    @Deprecated
    public boolean[] KeysDown;

    //------------------------------------------------------------------
    // [Internal] Dear ImGui will maintain those fields. Forward compatibility not guaranteed!
    //------------------------------------------------------------------

    /**
     * Mouse position, in pixels. Set to ImVec2(-FLT_MAX, -FLT_MAX) if mouse is unavailable (on another screen, etc.)
     */
    @BindingField
    public ImVec2 MousePos;

    /**
     * Mouse buttons: 0=left, 1=right, 2=middle + extras (ImGuiMouseButton_COUNT == 5). Dear ImGui mostly uses left and right buttons.
     * Others buttons allows us to track if the mouse is being used by your application + available to user as a convenience via IsMouse** API.
     */
    @BindingField
    @TypeArray(type = "boolean", size = "5")
    public boolean[] MouseDown;

    /**
     * Mouse wheel Vertical: 1 unit scrolls about 5 lines text.
     */
    @BindingField
    public float MouseWheel;

    /**
     * Mouse wheel Horizontal. Most users don't have a mouse with an horizontal wheel, may not be filled by all backends.
     */
    @BindingField
    public float MouseWheelH;

    /**
     * (Optional) When using multiple viewports: viewport the OS mouse cursor is hovering _IGNORING_ viewports with the ImGuiViewportFlags_NoInputs flag,
     * and _REGARDLESS_ of whether another viewport is focused. Set io.BackendFlags |= ImGuiBackendFlags_HasMouseHoveredViewport if you can provide this info.
     * If you don't imgui will infer the value using the rectangles and last focused time of the viewports it knows about (ignoring other OS windows).
     */
    @BindingField
    public int MouseHoveredViewport;

    /**
     * Keyboard modifier pressed: Control
     */
    @BindingField
    public boolean KeyCtrl;

    /**
     * Keyboard modifier pressed: Shift
     */
    @BindingField
    public boolean KeyShift;

    /**
     * Keyboard modifier pressed: Alt
     */
    @BindingField
    public boolean KeyAlt;

    /**
     * Keyboard modifier pressed: Cmd/Super/Windows
     */
    @BindingField
    public boolean KeySuper;

    /**
     * Gamepad inputs. Cleared back to zero by EndFrame(). Keyboard keys will be auto-mapped and be written here by NewFrame().
     */
    @BindingField
    @TypeArray(type = "float", size = "512")
    public float[] NavInputs;

    // Other state maintained from data above + IO function calls

    /**
     * Key mods flags (same as io.KeyCtrl/KeyShift/KeyAlt/KeySuper but merged into flags), updated by NewFrame()
     */
    @BindingField
    public int KeyMods;

    /**
     * Key state for all known keys. Use IsKeyXXX() functions to access this.
     */
    @BindingField
    @TypeArray(type = "ImGuiKeyData", size = "ImGuiKey_KeysData_SIZE")
    public ImGuiKeyData[] KeysData;

    /**
     * Alternative to WantCaptureMouse: (WantCaptureMouse == true {@code &&} WantCaptureMouseUnlessPopupClose == false) when a click over void is expected to close a popup.
     */
    @BindingField
    public boolean WantCaptureMouseUnlessPopupClose;

    /**
     * Previous mouse position (note that MouseDelta is not necessary == MousePos-MousePosPrev, in case either position is invalid)
     */
    @BindingField
    public ImVec2 MousePosPrev;

    /**
     * Position at time of clicking.
     */
    @BindingField
    @TypeArray(type = "ImVec2", size = "5")
    public ImVec2[] MouseClickedPos;

    /**
     * Time of last click (used to figure out double-click)
     */
    @BindingField
    @TypeArray(type = "double", size = "5")
    public double[] MouseClickedTime;

    /**
     * Mouse button went from !Down to Down (same as MouseClickedCount[x] != 0)
     */
    @BindingField
    @TypeArray(type = "boolean", size = "5")
    public boolean[] MouseClicked;

    /**
     * Has mouse button been double-clicked? (same as MouseClickedCount[x] == 2)
     */
    @BindingField
    @TypeArray(type = "boolean", size = "5")
    public boolean[] MouseDoubleClicked;

    /**
     * == 0 (not clicked), == 1 (same as MouseClicked[]), == 2 (double-clicked), == 3 (triple-clicked) etc. when going from !Down to Down
     */
    @BindingField
    @TypeArray(type = "int", size = "5")
    public int[] MouseClickedCount;

    /**
     * Count successive number of clicks. Stays valid after mouse release. Reset after another click is done.
     */
    @BindingField
    @TypeArray(type = "int", size = "5")
    public int[] MouseClickedLastCount;

    /**
     * Mouse button went from Down to !Down
     */
    @BindingField
    @TypeArray(type = "boolean", size = "5")
    public boolean[] MouseReleased;

    /**
     * Track if button was clicked inside a dear imgui window or over void blocked by a popup. We don't request mouse capture from the application if click started outside ImGui bounds.
     */
    @BindingField
    @TypeArray(type = "boolean", size = "5")
    public boolean[] MouseDownOwned;

    /**
     * Track if button was clicked inside a dear imgui window.
     */
    @BindingField
    @TypeArray(type = "boolean", size = "5")
    public boolean[] MouseDownOwnedUnlessPopupClose;

    /**
     * Duration the mouse button has been down (0.0f == just clicked)
     */
    @BindingField
    @TypeArray(type = "float", size = "5")
    public float[] MouseDownDuration;

    /**
     * Previous time the mouse button has been down
     */
    @BindingField
    @TypeArray(type = "float", size = "5")
    public float[] MouseDownDurationPrev;

    /**
     * Maximum distance, absolute, on each axis, of how much mouse has traveled from the clicking point
     */
    @BindingField
    @TypeArray(type = "ImVec2", size = "5")
    public ImVec2[] MouseDragMaxDistanceAbs;

    /**
     * Squared maximum distance of how much mouse has traveled from the clicking point
     */
    @BindingField
    @TypeArray(type = "float", size = "5")
    public float[] MouseDragMaxDistanceSqr;

    @BindingField
    @TypeArray(type = "float", size = "ImGuiNavInput_COUNT")
    public float[] NavInputsDownDuration;

    @BindingField
    @TypeArray(type = "float", size = "ImGuiNavInput_COUNT")
    public float[] NavInputsDownDurationPrev;

    /**
     * Touch/Pen pressure (0.0f to 1.0f, should be {@code >}0.0f only when MouseDown[0] == true). Helper storage currently unused by Dear ImGui.
     */
    @BindingField
    public float PenPressure;

    /**
     * Only modify via AddFocusEvent().
     */
    @BindingField(accessors = BindingField.Accessor.GETTER)
    public boolean AppFocusLost;

    /**
     * Only modify via SetAppAcceptingEvents().
     */
    @BindingField(accessors = BindingField.Accessor.GETTER)
    public boolean AppAcceptingEvents;

    /**
     * -1: unknown, 0: using AddKeyEvent(), 1: using legacy io.KeysDown[]
     */
    @BindingField
    public short BackendUsingLegacyKeyArrays;

    /**
     * 0: using AddKeyAnalogEvent(), 1: writing to legacy io.NavInputs[] directly
     */
    @BindingField
    public boolean BackendUsingLegacyNavInputArray;

    /**
     * For AddInputCharacterUTF16
     */
    @BindingField
    public short InputQueueSurrogate;

    // TODO: InputQueueCharacters

    /*JNI
        #undef THIS
     */
}
