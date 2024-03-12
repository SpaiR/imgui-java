package imgui.extension.imguiknobs;

import imgui.type.ImFloat;
import imgui.type.ImInt;

/**
 * ImGuiKnobs extension for ImGui
 * Repo: https://github.com/altschuler/imgui-knobs
 */
public final class ImGuiKnobs {

    private ImGuiKnobs() {
        throw new UnsupportedOperationException();
    }

     /*JNI
        #include "_imguiknobs.h"
    */

    /**
     * Create a knob with float values
     *
     * @param label    knob Label
     * @param pValue   knob value
     * @param minValue minimum value
     * @param maxValue maximum value
     * @param speed    speed of the knob
     * @param format   string format
     * @param variant  ImGuiKnobVariant
     * @param size     size of the knob
     * @param flags    ImGuiKnobFlags
     * @param steps    determines the number of steps draw, it is only used for the ImGuiKnobVariant_Stepped variant.
     */

    public static boolean knobFloat(final String label, final ImFloat pValue, final float minValue, final float maxValue, final float speed, final String format, final int variant, final float size, final int flags, final int steps) {
        return nKnob(label, pValue.getData(), minValue, maxValue, speed, format, variant, size, flags, steps);
    }

    private static native boolean nKnob(String label, float[] pValue, float minValue, float maxValue, float speed, String format, int variant, float size, int flags, int steps); /*
    return ImGuiKnobs::Knob(label, pValue, minValue, maxValue, speed, format, (ImGuiKnobVariant)variant, size, (ImGuiKnobFlags)flags, steps);
    */

    /**
     * Create a knob with int values
     *
     * @param label    knob Label
     * @param pValue   knob value
     * @param minValue minimum value
     * @param maxValue maximum value
     * @param speed    speed of the knob
     * @param format   string format
     * @param variant  ImGuiKnobVariant
     * @param size     Size for the knob
     * @param flags    ImGuiKnobFlags
     * @param steps    determines the number of steps draw, it is only used for the ImGuiKnobVariant_Stepped variant.
     */

    public static boolean knobInt(final String label, final ImInt pValue, final int minValue, final int maxValue, final float speed, final String format, final int variant, final float size, final int flags, final int steps) {
        return nKnobInt(label, pValue.getData(), minValue, maxValue, speed, format, variant, size, flags, steps);
    }

    private static native boolean nKnobInt(String label, int[] pValue, int minValue, int maxValue, float speed, String format, int variant, float size, int flags, int steps); /*
     return ImGuiKnobs::KnobInt(label, pValue, minValue, maxValue, speed, format, (ImGuiKnobVariant)variant, size, (ImGuiKnobFlags)flags, steps);
    */

}
