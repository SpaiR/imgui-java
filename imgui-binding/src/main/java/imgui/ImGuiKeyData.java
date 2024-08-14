package imgui;

import imgui.binding.ImGuiStructDestroyable;
import imgui.binding.annotation.BindingField;
import imgui.binding.annotation.BindingSource;

/**
 * [Internal] Storage used by IsKeyDown(), IsKeyPressed() etc functions.
 * If prior to 1.87 you used io.KeysDownDuration[] (which was marked as internal), you should use GetKeyData(key).DownDuration and not io.KeysData[key].DownDuration.
 */
@BindingSource
public final class ImGuiKeyData extends ImGuiStructDestroyable {
    public ImGuiKeyData() {
        super();
    }

    public ImGuiKeyData(final long ptr) {
        super(ptr);
    }

    @Override
    protected long create() {
        return nCreate();
    }

    /*JNI
        #include "_common.h"
        #define THIS ((ImGuiKeyData*)STRUCT_PTR)
     */

    private native long nCreate(); /*
        return (intptr_t)(new ImGuiKeyData());
    */

    /**
     * True for if key is down
     */
    @BindingField
    public boolean Down;

    /**
     * Duration the key has been down ({@code <}0.0f: not pressed, 0.0f: just pressed, {@code >}0.0f: time held)
     */
    @BindingField
    public float DownDuration;

    /**
     * Last frame duration the key has been down
     */
    @BindingField
    public float DownDurationPrev;

    /**
     * 0.0f..1.0f for gamepad values
     */
    @BindingField
    public float AnalogValue;

    /*JNI
        #undef THIS
     */
}
