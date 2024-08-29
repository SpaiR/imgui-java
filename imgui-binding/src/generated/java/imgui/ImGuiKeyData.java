package imgui;

import imgui.binding.ImGuiStructDestroyable;

/**
 * [Internal] Storage used by IsKeyDown(), IsKeyPressed() etc functions.
 * If prior to 1.87 you used io.KeysDownDuration[] (which was marked as internal), you should use GetKeyData(key).DownDuration and not io.KeysData[key].DownDuration.
 */
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
        return (uintptr_t)(new ImGuiKeyData());
    */

    /**
     * True for if key is down
     */
    public boolean getDown() {
        return nGetDown();
    }

    /**
     * True for if key is down
     */
    public void setDown(final boolean value) {
        nSetDown(value);
    }

    private native boolean nGetDown(); /*
        return THIS->Down;
    */

    private native void nSetDown(boolean value); /*
        THIS->Down = value;
    */

    /**
     * Duration the key has been down ({@code <}0.0f: not pressed, 0.0f: just pressed, {@code >}0.0f: time held)
     */
    public float getDownDuration() {
        return nGetDownDuration();
    }

    /**
     * Duration the key has been down ({@code <}0.0f: not pressed, 0.0f: just pressed, {@code >}0.0f: time held)
     */
    public void setDownDuration(final float value) {
        nSetDownDuration(value);
    }

    private native float nGetDownDuration(); /*
        return THIS->DownDuration;
    */

    private native void nSetDownDuration(float value); /*
        THIS->DownDuration = value;
    */

    /**
     * Last frame duration the key has been down
     */
    public float getDownDurationPrev() {
        return nGetDownDurationPrev();
    }

    /**
     * Last frame duration the key has been down
     */
    public void setDownDurationPrev(final float value) {
        nSetDownDurationPrev(value);
    }

    private native float nGetDownDurationPrev(); /*
        return THIS->DownDurationPrev;
    */

    private native void nSetDownDurationPrev(float value); /*
        THIS->DownDurationPrev = value;
    */

    /**
     * 0.0f..1.0f for gamepad values
     */
    public float getAnalogValue() {
        return nGetAnalogValue();
    }

    /**
     * 0.0f..1.0f for gamepad values
     */
    public void setAnalogValue(final float value) {
        nSetAnalogValue(value);
    }

    private native float nGetAnalogValue(); /*
        return THIS->AnalogValue;
    */

    private native void nSetAnalogValue(float value); /*
        THIS->AnalogValue = value;
    */

    /*JNI
        #undef THIS
     */
}
