package imgui.extension.imguiknobs;

import imgui.binding.annotation.BindingMethod;
import imgui.binding.annotation.BindingSource;
import imgui.binding.annotation.OptArg;
import imgui.type.ImFloat;
import imgui.type.ImInt;

/**
 * ImGuiKnobs extension for ImGui
 * Repo: https://github.com/altschuler/imgui-knobs
 */
@BindingSource
public final class ImGuiKnobs {
    private ImGuiKnobs() {
    }

    /*JNI
        #include "_imguiknobs.h"
     */

    /**
     * Create a knob with float values
     *
     * @param label   knob Label
     * @param pValue  knob value
     * @param vMin    minimum value
     * @param vMax    maximum value
     * @param speed   speed of the knob
     * @param format  string format
     * @param variant ImGuiKnobVariant
     * @param size    size of the knob
     * @param flags   ImGuiKnobFlags
     * @param steps   determines the number of steps draw, it is only used for the ImGuiKnobVariant_Stepped variant.
     */
    @BindingMethod
    public static native boolean Knob(String label, ImFloat pValue, float vMin, float vMax, @OptArg(callValue = "0") float speed, @OptArg(callValue = "NULL") String format, @OptArg(callValue = "ImGuiKnobVariant_Tick") int variant, @OptArg(callValue = "0") float size, @OptArg(callValue = "0") int flags, @OptArg int steps);

    /**
     * Create a knob with int values
     *
     * @param label   knob Label
     * @param pValue  knob value
     * @param vMin    minimum value
     * @param vMax    maximum value
     * @param speed   speed of the knob
     * @param format  string format
     * @param variant ImGuiKnobVariant
     * @param size    Size for the knob
     * @param flags   ImGuiKnobFlags
     * @param steps   determines the number of steps draw, it is only used for the ImGuiKnobVariant_Stepped variant.
     */
    @BindingMethod(callName = "KnobInt")
    public static native boolean Knob(String label, ImInt pValue, int vMin, int vMax, @OptArg(callValue = "0") float speed, @OptArg(callValue = "NULL") String format, @OptArg(callValue = "ImGuiKnobVariant_Tick") int variant, @OptArg(callValue = "0") float size, @OptArg(callValue = "0") int flags, @OptArg int steps);
}
