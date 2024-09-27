package imgui.extension.imguiknobs;




import imgui.type.ImFloat;
import imgui.type.ImInt;

/**
 * ImGuiKnobs extension for ImGui
 * Repo: https://github.com/altschuler/imgui-knobs
 */

public final class ImGuiKnobs {
    private ImGuiKnobs() {
    }

    /*JNI
        #include "_imguiknobs.h"
     */

     /**
     * Create a knob with float values
     *
     * @param label
     * 		knob Label
     * @param pValue
     * 		knob value
     * @param vMin
     * 		minimum value
     * @param vMax
     * 		maximum value
     */
    public static boolean knob(final String label, final ImFloat pValue, final float vMin, final float vMax) {
        return nKnob(label, pValue != null ? pValue.getData() : null, vMin, vMax);
    }

    /**
     * Create a knob with float values
     *
     * @param label
     * 		knob Label
     * @param pValue
     * 		knob value
     * @param vMin
     * 		minimum value
     * @param vMax
     * 		maximum value
     * @param speed
     * 		speed of the knob
     */
    public static boolean knob(final String label, final ImFloat pValue, final float vMin, final float vMax, final float speed) {
        return nKnob(label, pValue != null ? pValue.getData() : null, vMin, vMax, speed);
    }

    /**
     * Create a knob with float values
     *
     * @param label
     * 		knob Label
     * @param pValue
     * 		knob value
     * @param vMin
     * 		minimum value
     * @param vMax
     * 		maximum value
     * @param speed
     * 		speed of the knob
     * @param format
     * 		string format
     */
    public static boolean knob(final String label, final ImFloat pValue, final float vMin, final float vMax, final float speed, final String format) {
        return nKnob(label, pValue != null ? pValue.getData() : null, vMin, vMax, speed, format);
    }

    /**
     * Create a knob with float values
     *
     * @param label
     * 		knob Label
     * @param pValue
     * 		knob value
     * @param vMin
     * 		minimum value
     * @param vMax
     * 		maximum value
     * @param speed
     * 		speed of the knob
     * @param format
     * 		string format
     */
    public static boolean knob(final String label, final ImFloat pValue, final float vMin, final float vMax, final float speed, final String format, final int variant) {
        return nKnob(label, pValue != null ? pValue.getData() : null, vMin, vMax, speed, format, variant);
    }

    /**
     * Create a knob with float values
     *
     * @param label
     * 		knob Label
     * @param pValue
     * 		knob value
     * @param vMin
     * 		minimum value
     * @param vMax
     * 		maximum value
     * @param speed
     * 		speed of the knob
     * @param format
     * 		string format
     * @param size
     * 		size of the knob
     */
    public static boolean knob(final String label, final ImFloat pValue, final float vMin, final float vMax, final float speed, final String format, final int variant, final float size) {
        return nKnob(label, pValue != null ? pValue.getData() : null, vMin, vMax, speed, format, variant, size);
    }

    /**
     * Create a knob with float values
     *
     * @param label
     * 		knob Label
     * @param pValue
     * 		knob value
     * @param vMin
     * 		minimum value
     * @param vMax
     * 		maximum value
     * @param speed
     * 		speed of the knob
     * @param format
     * 		string format
     * @param size
     * 		size of the knob
     */
    public static boolean knob(final String label, final ImFloat pValue, final float vMin, final float vMax, final float speed, final String format, final int variant, final float size, final int flags) {
        return nKnob(label, pValue != null ? pValue.getData() : null, vMin, vMax, speed, format, variant, size, flags);
    }

    /**
     * Create a knob with float values
     *
     * @param label
     * 		knob Label
     * @param pValue
     * 		knob value
     * @param vMin
     * 		minimum value
     * @param vMax
     * 		maximum value
     * @param speed
     * 		speed of the knob
     * @param format
     * 		string format
     * @param size
     * 		size of the knob
     * @param steps
     * 		determines the number of steps draw, it is only used for the ImGuiKnobVariant_Stepped variant.
     */
    public static boolean knob(final String label, final ImFloat pValue, final float vMin, final float vMax, final float speed, final String format, final int variant, final float size, final int flags, final int steps) {
        return nKnob(label, pValue != null ? pValue.getData() : null, vMin, vMax, speed, format, variant, size, flags, steps);
    }

    /**
     * Create a knob with float values
     *
     * @param label
     * 		knob Label
     * @param pValue
     * 		knob value
     * @param vMin
     * 		minimum value
     * @param vMax
     * 		maximum value
     * @param format
     * 		string format
     * @param size
     * 		size of the knob
     * @param steps
     * 		determines the number of steps draw, it is only used for the ImGuiKnobVariant_Stepped variant.
     */
    public static boolean knob(final String label, final ImFloat pValue, final float vMin, final float vMax, final String format, final int variant, final float size, final int flags, final int steps) {
        return nKnob(label, pValue != null ? pValue.getData() : null, vMin, vMax, format, variant, size, flags, steps);
    }

    /**
     * Create a knob with float values
     *
     * @param label
     * 		knob Label
     * @param pValue
     * 		knob value
     * @param vMin
     * 		minimum value
     * @param vMax
     * 		maximum value
     * @param size
     * 		size of the knob
     * @param steps
     * 		determines the number of steps draw, it is only used for the ImGuiKnobVariant_Stepped variant.
     */
    public static boolean knob(final String label, final ImFloat pValue, final float vMin, final float vMax, final int variant, final float size, final int flags, final int steps) {
        return nKnob(label, pValue != null ? pValue.getData() : null, vMin, vMax, variant, size, flags, steps);
    }

    /**
     * Create a knob with float values
     *
     * @param label
     * 		knob Label
     * @param pValue
     * 		knob value
     * @param vMin
     * 		minimum value
     * @param vMax
     * 		maximum value
     * @param speed
     * 		speed of the knob
     * @param size
     * 		size of the knob
     * @param steps
     * 		determines the number of steps draw, it is only used for the ImGuiKnobVariant_Stepped variant.
     */
    public static boolean knob(final String label, final ImFloat pValue, final float vMin, final float vMax, final float speed, final int variant, final float size, final int flags, final int steps) {
        return nKnob(label, pValue != null ? pValue.getData() : null, vMin, vMax, speed, variant, size, flags, steps);
    }

    /**
     * Create a knob with float values
     *
     * @param label
     * 		knob Label
     * @param pValue
     * 		knob value
     * @param vMin
     * 		minimum value
     * @param vMax
     * 		maximum value
     * @param speed
     * 		speed of the knob
     * @param size
     * 		size of the knob
     * @param steps
     * 		determines the number of steps draw, it is only used for the ImGuiKnobVariant_Stepped variant.
     */
    public static boolean knob(final String label, final ImFloat pValue, final float vMin, final float vMax, final float speed, final float size, final int flags, final int steps) {
        return nKnob(label, pValue != null ? pValue.getData() : null, vMin, vMax, speed, size, flags, steps);
    }

    /**
     * Create a knob with float values
     *
     * @param label
     * 		knob Label
     * @param pValue
     * 		knob value
     * @param vMin
     * 		minimum value
     * @param vMax
     * 		maximum value
     * @param speed
     * 		speed of the knob
     * @param steps
     * 		determines the number of steps draw, it is only used for the ImGuiKnobVariant_Stepped variant.
     */
    public static boolean knob(final String label, final ImFloat pValue, final float vMin, final float vMax, final float speed, final int flags, final int steps) {
        return nKnob(label, pValue != null ? pValue.getData() : null, vMin, vMax, speed, flags, steps);
    }

    /**
     * Create a knob with float values
     *
     * @param label
     * 		knob Label
     * @param pValue
     * 		knob value
     * @param vMin
     * 		minimum value
     * @param vMax
     * 		maximum value
     * @param speed
     * 		speed of the knob
     * @param steps
     * 		determines the number of steps draw, it is only used for the ImGuiKnobVariant_Stepped variant.
     */
    public static boolean knob(final String label, final ImFloat pValue, final float vMin, final float vMax, final float speed, final int steps) {
        return nKnob(label, pValue != null ? pValue.getData() : null, vMin, vMax, speed, steps);
    }

    /**
     * Create a knob with float values
     *
     * @param label
     * 		knob Label
     * @param pValue
     * 		knob value
     * @param vMin
     * 		minimum value
     * @param vMax
     * 		maximum value
     * @param speed
     * 		speed of the knob
     * @param format
     * 		string format
     * @param size
     * 		size of the knob
     * @param steps
     * 		determines the number of steps draw, it is only used for the ImGuiKnobVariant_Stepped variant.
     */
    public static boolean knob(final String label, final ImFloat pValue, final float vMin, final float vMax, final float speed, final String format, final float size, final int flags, final int steps) {
        return nKnob(label, pValue != null ? pValue.getData() : null, vMin, vMax, speed, format, size, flags, steps);
    }

    /**
     * Create a knob with float values
     *
     * @param label
     * 		knob Label
     * @param pValue
     * 		knob value
     * @param vMin
     * 		minimum value
     * @param vMax
     * 		maximum value
     * @param speed
     * 		speed of the knob
     * @param format
     * 		string format
     * @param steps
     * 		determines the number of steps draw, it is only used for the ImGuiKnobVariant_Stepped variant.
     */
    public static boolean knob(final String label, final ImFloat pValue, final float vMin, final float vMax, final float speed, final String format, final int variant, final int flags, final int steps) {
        return nKnob(label, pValue != null ? pValue.getData() : null, vMin, vMax, speed, format, variant, flags, steps);
    }

    /**
     * Create a knob with float values
     *
     * @param label
     * 		knob Label
     * @param pValue
     * 		knob value
     * @param vMin
     * 		minimum value
     * @param vMax
     * 		maximum value
     * @param speed
     * 		speed of the knob
     * @param format
     * 		string format
     * @param steps
     * 		determines the number of steps draw, it is only used for the ImGuiKnobVariant_Stepped variant.
     */
    public static boolean knob(final String label, final ImFloat pValue, final float vMin, final float vMax, final float speed, final String format, final int variant, final int steps) {
        return nKnob(label, pValue != null ? pValue.getData() : null, vMin, vMax, speed, format, variant, steps);
    }

    private static native boolean nKnob(String obj_label, float[] obj_pValue, float vMin, float vMax); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pValue = obj_pValue == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_pValue, JNI_FALSE);
        auto _result = ImGuiKnobs::Knob(label, (pValue != NULL ? &pValue[0] : NULL), vMin, vMax);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pValue != NULL) env->ReleasePrimitiveArrayCritical(obj_pValue, pValue, JNI_FALSE);
        return _result;
    */

    private static native boolean nKnob(String obj_label, float[] obj_pValue, float vMin, float vMax, float speed); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pValue = obj_pValue == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_pValue, JNI_FALSE);
        auto _result = ImGuiKnobs::Knob(label, (pValue != NULL ? &pValue[0] : NULL), vMin, vMax, speed);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pValue != NULL) env->ReleasePrimitiveArrayCritical(obj_pValue, pValue, JNI_FALSE);
        return _result;
    */

    private static native boolean nKnob(String obj_label, float[] obj_pValue, float vMin, float vMax, float speed, String obj_format); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pValue = obj_pValue == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_pValue, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGuiKnobs::Knob(label, (pValue != NULL ? &pValue[0] : NULL), vMin, vMax, speed, format);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pValue != NULL) env->ReleasePrimitiveArrayCritical(obj_pValue, pValue, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nKnob(String obj_label, float[] obj_pValue, float vMin, float vMax, float speed, String obj_format, int variant); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pValue = obj_pValue == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_pValue, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGuiKnobs::Knob(label, (pValue != NULL ? &pValue[0] : NULL), vMin, vMax, speed, format, variant);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pValue != NULL) env->ReleasePrimitiveArrayCritical(obj_pValue, pValue, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nKnob(String obj_label, float[] obj_pValue, float vMin, float vMax, float speed, String obj_format, int variant, float size); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pValue = obj_pValue == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_pValue, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGuiKnobs::Knob(label, (pValue != NULL ? &pValue[0] : NULL), vMin, vMax, speed, format, variant, size);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pValue != NULL) env->ReleasePrimitiveArrayCritical(obj_pValue, pValue, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nKnob(String obj_label, float[] obj_pValue, float vMin, float vMax, float speed, String obj_format, int variant, float size, int flags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pValue = obj_pValue == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_pValue, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGuiKnobs::Knob(label, (pValue != NULL ? &pValue[0] : NULL), vMin, vMax, speed, format, variant, size, flags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pValue != NULL) env->ReleasePrimitiveArrayCritical(obj_pValue, pValue, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nKnob(String obj_label, float[] obj_pValue, float vMin, float vMax, float speed, String obj_format, int variant, float size, int flags, int steps); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pValue = obj_pValue == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_pValue, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGuiKnobs::Knob(label, (pValue != NULL ? &pValue[0] : NULL), vMin, vMax, speed, format, variant, size, flags, steps);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pValue != NULL) env->ReleasePrimitiveArrayCritical(obj_pValue, pValue, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nKnob(String obj_label, float[] obj_pValue, float vMin, float vMax, String obj_format, int variant, float size, int flags, int steps); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pValue = obj_pValue == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_pValue, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGuiKnobs::Knob(label, (pValue != NULL ? &pValue[0] : NULL), vMin, vMax, 0, format, variant, size, flags, steps);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pValue != NULL) env->ReleasePrimitiveArrayCritical(obj_pValue, pValue, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nKnob(String obj_label, float[] obj_pValue, float vMin, float vMax, int variant, float size, int flags, int steps); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pValue = obj_pValue == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_pValue, JNI_FALSE);
        auto _result = ImGuiKnobs::Knob(label, (pValue != NULL ? &pValue[0] : NULL), vMin, vMax, 0, NULL, variant, size, flags, steps);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pValue != NULL) env->ReleasePrimitiveArrayCritical(obj_pValue, pValue, JNI_FALSE);
        return _result;
    */

    private static native boolean nKnob(String obj_label, float[] obj_pValue, float vMin, float vMax, float speed, int variant, float size, int flags, int steps); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pValue = obj_pValue == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_pValue, JNI_FALSE);
        auto _result = ImGuiKnobs::Knob(label, (pValue != NULL ? &pValue[0] : NULL), vMin, vMax, speed, NULL, variant, size, flags, steps);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pValue != NULL) env->ReleasePrimitiveArrayCritical(obj_pValue, pValue, JNI_FALSE);
        return _result;
    */

    private static native boolean nKnob(String obj_label, float[] obj_pValue, float vMin, float vMax, float speed, float size, int flags, int steps); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pValue = obj_pValue == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_pValue, JNI_FALSE);
        auto _result = ImGuiKnobs::Knob(label, (pValue != NULL ? &pValue[0] : NULL), vMin, vMax, speed, NULL, ImGuiKnobVariant_Tick, size, flags, steps);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pValue != NULL) env->ReleasePrimitiveArrayCritical(obj_pValue, pValue, JNI_FALSE);
        return _result;
    */

    private static native boolean nKnob(String obj_label, float[] obj_pValue, float vMin, float vMax, float speed, int flags, int steps); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pValue = obj_pValue == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_pValue, JNI_FALSE);
        auto _result = ImGuiKnobs::Knob(label, (pValue != NULL ? &pValue[0] : NULL), vMin, vMax, speed, NULL, ImGuiKnobVariant_Tick, 0, flags, steps);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pValue != NULL) env->ReleasePrimitiveArrayCritical(obj_pValue, pValue, JNI_FALSE);
        return _result;
    */

    private static native boolean nKnob(String obj_label, float[] obj_pValue, float vMin, float vMax, float speed, int steps); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pValue = obj_pValue == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_pValue, JNI_FALSE);
        auto _result = ImGuiKnobs::Knob(label, (pValue != NULL ? &pValue[0] : NULL), vMin, vMax, speed, NULL, ImGuiKnobVariant_Tick, 0, 0, steps);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pValue != NULL) env->ReleasePrimitiveArrayCritical(obj_pValue, pValue, JNI_FALSE);
        return _result;
    */

    private static native boolean nKnob(String obj_label, float[] obj_pValue, float vMin, float vMax, float speed, String obj_format, float size, int flags, int steps); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pValue = obj_pValue == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_pValue, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGuiKnobs::Knob(label, (pValue != NULL ? &pValue[0] : NULL), vMin, vMax, speed, format, ImGuiKnobVariant_Tick, size, flags, steps);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pValue != NULL) env->ReleasePrimitiveArrayCritical(obj_pValue, pValue, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nKnob(String obj_label, float[] obj_pValue, float vMin, float vMax, float speed, String obj_format, int variant, int flags, int steps); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pValue = obj_pValue == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_pValue, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGuiKnobs::Knob(label, (pValue != NULL ? &pValue[0] : NULL), vMin, vMax, speed, format, variant, 0, flags, steps);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pValue != NULL) env->ReleasePrimitiveArrayCritical(obj_pValue, pValue, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nKnob(String obj_label, float[] obj_pValue, float vMin, float vMax, float speed, String obj_format, int variant, int steps); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pValue = obj_pValue == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_pValue, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGuiKnobs::Knob(label, (pValue != NULL ? &pValue[0] : NULL), vMin, vMax, speed, format, variant, 0, 0, steps);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pValue != NULL) env->ReleasePrimitiveArrayCritical(obj_pValue, pValue, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

     /**
     * Create a knob with int values
     *
     * @param label
     * 		knob Label
     * @param pValue
     * 		knob value
     * @param vMin
     * 		minimum value
     * @param vMax
     * 		maximum value
     */
    public static boolean knob(final String label, final ImInt pValue, final int vMin, final int vMax) {
        return nKnob(label, pValue != null ? pValue.getData() : null, vMin, vMax);
    }

    /**
     * Create a knob with int values
     *
     * @param label
     * 		knob Label
     * @param pValue
     * 		knob value
     * @param vMin
     * 		minimum value
     * @param vMax
     * 		maximum value
     * @param speed
     * 		speed of the knob
     */
    public static boolean knob(final String label, final ImInt pValue, final int vMin, final int vMax, final float speed) {
        return nKnob(label, pValue != null ? pValue.getData() : null, vMin, vMax, speed);
    }

    /**
     * Create a knob with int values
     *
     * @param label
     * 		knob Label
     * @param pValue
     * 		knob value
     * @param vMin
     * 		minimum value
     * @param vMax
     * 		maximum value
     * @param speed
     * 		speed of the knob
     * @param format
     * 		string format
     */
    public static boolean knob(final String label, final ImInt pValue, final int vMin, final int vMax, final float speed, final String format) {
        return nKnob(label, pValue != null ? pValue.getData() : null, vMin, vMax, speed, format);
    }

    /**
     * Create a knob with int values
     *
     * @param label
     * 		knob Label
     * @param pValue
     * 		knob value
     * @param vMin
     * 		minimum value
     * @param vMax
     * 		maximum value
     * @param speed
     * 		speed of the knob
     * @param format
     * 		string format
     */
    public static boolean knob(final String label, final ImInt pValue, final int vMin, final int vMax, final float speed, final String format, final int variant) {
        return nKnob(label, pValue != null ? pValue.getData() : null, vMin, vMax, speed, format, variant);
    }

    /**
     * Create a knob with int values
     *
     * @param label
     * 		knob Label
     * @param pValue
     * 		knob value
     * @param vMin
     * 		minimum value
     * @param vMax
     * 		maximum value
     * @param speed
     * 		speed of the knob
     * @param format
     * 		string format
     * @param size
     * 		Size for the knob
     */
    public static boolean knob(final String label, final ImInt pValue, final int vMin, final int vMax, final float speed, final String format, final int variant, final float size) {
        return nKnob(label, pValue != null ? pValue.getData() : null, vMin, vMax, speed, format, variant, size);
    }

    /**
     * Create a knob with int values
     *
     * @param label
     * 		knob Label
     * @param pValue
     * 		knob value
     * @param vMin
     * 		minimum value
     * @param vMax
     * 		maximum value
     * @param speed
     * 		speed of the knob
     * @param format
     * 		string format
     * @param size
     * 		Size for the knob
     */
    public static boolean knob(final String label, final ImInt pValue, final int vMin, final int vMax, final float speed, final String format, final int variant, final float size, final int flags) {
        return nKnob(label, pValue != null ? pValue.getData() : null, vMin, vMax, speed, format, variant, size, flags);
    }

    /**
     * Create a knob with int values
     *
     * @param label
     * 		knob Label
     * @param pValue
     * 		knob value
     * @param vMin
     * 		minimum value
     * @param vMax
     * 		maximum value
     * @param speed
     * 		speed of the knob
     * @param format
     * 		string format
     * @param size
     * 		Size for the knob
     * @param steps
     * 		determines the number of steps draw, it is only used for the ImGuiKnobVariant_Stepped variant.
     */
    public static boolean knob(final String label, final ImInt pValue, final int vMin, final int vMax, final float speed, final String format, final int variant, final float size, final int flags, final int steps) {
        return nKnob(label, pValue != null ? pValue.getData() : null, vMin, vMax, speed, format, variant, size, flags, steps);
    }

    /**
     * Create a knob with int values
     *
     * @param label
     * 		knob Label
     * @param pValue
     * 		knob value
     * @param vMin
     * 		minimum value
     * @param vMax
     * 		maximum value
     * @param format
     * 		string format
     * @param size
     * 		Size for the knob
     * @param steps
     * 		determines the number of steps draw, it is only used for the ImGuiKnobVariant_Stepped variant.
     */
    public static boolean knob(final String label, final ImInt pValue, final int vMin, final int vMax, final String format, final int variant, final float size, final int flags, final int steps) {
        return nKnob(label, pValue != null ? pValue.getData() : null, vMin, vMax, format, variant, size, flags, steps);
    }

    /**
     * Create a knob with int values
     *
     * @param label
     * 		knob Label
     * @param pValue
     * 		knob value
     * @param vMin
     * 		minimum value
     * @param vMax
     * 		maximum value
     * @param size
     * 		Size for the knob
     * @param steps
     * 		determines the number of steps draw, it is only used for the ImGuiKnobVariant_Stepped variant.
     */
    public static boolean knob(final String label, final ImInt pValue, final int vMin, final int vMax, final int variant, final float size, final int flags, final int steps) {
        return nKnob(label, pValue != null ? pValue.getData() : null, vMin, vMax, variant, size, flags, steps);
    }

    /**
     * Create a knob with int values
     *
     * @param label
     * 		knob Label
     * @param pValue
     * 		knob value
     * @param vMin
     * 		minimum value
     * @param vMax
     * 		maximum value
     * @param speed
     * 		speed of the knob
     * @param size
     * 		Size for the knob
     * @param steps
     * 		determines the number of steps draw, it is only used for the ImGuiKnobVariant_Stepped variant.
     */
    public static boolean knob(final String label, final ImInt pValue, final int vMin, final int vMax, final float speed, final int variant, final float size, final int flags, final int steps) {
        return nKnob(label, pValue != null ? pValue.getData() : null, vMin, vMax, speed, variant, size, flags, steps);
    }

    /**
     * Create a knob with int values
     *
     * @param label
     * 		knob Label
     * @param pValue
     * 		knob value
     * @param vMin
     * 		minimum value
     * @param vMax
     * 		maximum value
     * @param speed
     * 		speed of the knob
     * @param size
     * 		Size for the knob
     * @param steps
     * 		determines the number of steps draw, it is only used for the ImGuiKnobVariant_Stepped variant.
     */
    public static boolean knob(final String label, final ImInt pValue, final int vMin, final int vMax, final float speed, final float size, final int flags, final int steps) {
        return nKnob(label, pValue != null ? pValue.getData() : null, vMin, vMax, speed, size, flags, steps);
    }

    /**
     * Create a knob with int values
     *
     * @param label
     * 		knob Label
     * @param pValue
     * 		knob value
     * @param vMin
     * 		minimum value
     * @param vMax
     * 		maximum value
     * @param speed
     * 		speed of the knob
     * @param steps
     * 		determines the number of steps draw, it is only used for the ImGuiKnobVariant_Stepped variant.
     */
    public static boolean knob(final String label, final ImInt pValue, final int vMin, final int vMax, final float speed, final int flags, final int steps) {
        return nKnob(label, pValue != null ? pValue.getData() : null, vMin, vMax, speed, flags, steps);
    }

    /**
     * Create a knob with int values
     *
     * @param label
     * 		knob Label
     * @param pValue
     * 		knob value
     * @param vMin
     * 		minimum value
     * @param vMax
     * 		maximum value
     * @param speed
     * 		speed of the knob
     * @param steps
     * 		determines the number of steps draw, it is only used for the ImGuiKnobVariant_Stepped variant.
     */
    public static boolean knob(final String label, final ImInt pValue, final int vMin, final int vMax, final float speed, final int steps) {
        return nKnob(label, pValue != null ? pValue.getData() : null, vMin, vMax, speed, steps);
    }

    /**
     * Create a knob with int values
     *
     * @param label
     * 		knob Label
     * @param pValue
     * 		knob value
     * @param vMin
     * 		minimum value
     * @param vMax
     * 		maximum value
     * @param speed
     * 		speed of the knob
     * @param format
     * 		string format
     * @param size
     * 		Size for the knob
     * @param steps
     * 		determines the number of steps draw, it is only used for the ImGuiKnobVariant_Stepped variant.
     */
    public static boolean knob(final String label, final ImInt pValue, final int vMin, final int vMax, final float speed, final String format, final float size, final int flags, final int steps) {
        return nKnob(label, pValue != null ? pValue.getData() : null, vMin, vMax, speed, format, size, flags, steps);
    }

    /**
     * Create a knob with int values
     *
     * @param label
     * 		knob Label
     * @param pValue
     * 		knob value
     * @param vMin
     * 		minimum value
     * @param vMax
     * 		maximum value
     * @param speed
     * 		speed of the knob
     * @param format
     * 		string format
     * @param steps
     * 		determines the number of steps draw, it is only used for the ImGuiKnobVariant_Stepped variant.
     */
    public static boolean knob(final String label, final ImInt pValue, final int vMin, final int vMax, final float speed, final String format, final int variant, final int flags, final int steps) {
        return nKnob(label, pValue != null ? pValue.getData() : null, vMin, vMax, speed, format, variant, flags, steps);
    }

    /**
     * Create a knob with int values
     *
     * @param label
     * 		knob Label
     * @param pValue
     * 		knob value
     * @param vMin
     * 		minimum value
     * @param vMax
     * 		maximum value
     * @param speed
     * 		speed of the knob
     * @param format
     * 		string format
     * @param steps
     * 		determines the number of steps draw, it is only used for the ImGuiKnobVariant_Stepped variant.
     */
    public static boolean knob(final String label, final ImInt pValue, final int vMin, final int vMax, final float speed, final String format, final int variant, final int steps) {
        return nKnob(label, pValue != null ? pValue.getData() : null, vMin, vMax, speed, format, variant, steps);
    }

    private static native boolean nKnob(String obj_label, int[] obj_pValue, int vMin, int vMax); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pValue = obj_pValue == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_pValue, JNI_FALSE);
        auto _result = ImGuiKnobs::KnobInt(label, (pValue != NULL ? &pValue[0] : NULL), vMin, vMax);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pValue != NULL) env->ReleasePrimitiveArrayCritical(obj_pValue, pValue, JNI_FALSE);
        return _result;
    */

    private static native boolean nKnob(String obj_label, int[] obj_pValue, int vMin, int vMax, float speed); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pValue = obj_pValue == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_pValue, JNI_FALSE);
        auto _result = ImGuiKnobs::KnobInt(label, (pValue != NULL ? &pValue[0] : NULL), vMin, vMax, speed);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pValue != NULL) env->ReleasePrimitiveArrayCritical(obj_pValue, pValue, JNI_FALSE);
        return _result;
    */

    private static native boolean nKnob(String obj_label, int[] obj_pValue, int vMin, int vMax, float speed, String obj_format); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pValue = obj_pValue == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_pValue, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGuiKnobs::KnobInt(label, (pValue != NULL ? &pValue[0] : NULL), vMin, vMax, speed, format);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pValue != NULL) env->ReleasePrimitiveArrayCritical(obj_pValue, pValue, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nKnob(String obj_label, int[] obj_pValue, int vMin, int vMax, float speed, String obj_format, int variant); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pValue = obj_pValue == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_pValue, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGuiKnobs::KnobInt(label, (pValue != NULL ? &pValue[0] : NULL), vMin, vMax, speed, format, variant);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pValue != NULL) env->ReleasePrimitiveArrayCritical(obj_pValue, pValue, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nKnob(String obj_label, int[] obj_pValue, int vMin, int vMax, float speed, String obj_format, int variant, float size); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pValue = obj_pValue == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_pValue, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGuiKnobs::KnobInt(label, (pValue != NULL ? &pValue[0] : NULL), vMin, vMax, speed, format, variant, size);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pValue != NULL) env->ReleasePrimitiveArrayCritical(obj_pValue, pValue, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nKnob(String obj_label, int[] obj_pValue, int vMin, int vMax, float speed, String obj_format, int variant, float size, int flags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pValue = obj_pValue == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_pValue, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGuiKnobs::KnobInt(label, (pValue != NULL ? &pValue[0] : NULL), vMin, vMax, speed, format, variant, size, flags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pValue != NULL) env->ReleasePrimitiveArrayCritical(obj_pValue, pValue, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nKnob(String obj_label, int[] obj_pValue, int vMin, int vMax, float speed, String obj_format, int variant, float size, int flags, int steps); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pValue = obj_pValue == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_pValue, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGuiKnobs::KnobInt(label, (pValue != NULL ? &pValue[0] : NULL), vMin, vMax, speed, format, variant, size, flags, steps);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pValue != NULL) env->ReleasePrimitiveArrayCritical(obj_pValue, pValue, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nKnob(String obj_label, int[] obj_pValue, int vMin, int vMax, String obj_format, int variant, float size, int flags, int steps); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pValue = obj_pValue == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_pValue, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGuiKnobs::KnobInt(label, (pValue != NULL ? &pValue[0] : NULL), vMin, vMax, 0, format, variant, size, flags, steps);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pValue != NULL) env->ReleasePrimitiveArrayCritical(obj_pValue, pValue, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nKnob(String obj_label, int[] obj_pValue, int vMin, int vMax, int variant, float size, int flags, int steps); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pValue = obj_pValue == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_pValue, JNI_FALSE);
        auto _result = ImGuiKnobs::KnobInt(label, (pValue != NULL ? &pValue[0] : NULL), vMin, vMax, 0, NULL, variant, size, flags, steps);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pValue != NULL) env->ReleasePrimitiveArrayCritical(obj_pValue, pValue, JNI_FALSE);
        return _result;
    */

    private static native boolean nKnob(String obj_label, int[] obj_pValue, int vMin, int vMax, float speed, int variant, float size, int flags, int steps); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pValue = obj_pValue == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_pValue, JNI_FALSE);
        auto _result = ImGuiKnobs::KnobInt(label, (pValue != NULL ? &pValue[0] : NULL), vMin, vMax, speed, NULL, variant, size, flags, steps);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pValue != NULL) env->ReleasePrimitiveArrayCritical(obj_pValue, pValue, JNI_FALSE);
        return _result;
    */

    private static native boolean nKnob(String obj_label, int[] obj_pValue, int vMin, int vMax, float speed, float size, int flags, int steps); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pValue = obj_pValue == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_pValue, JNI_FALSE);
        auto _result = ImGuiKnobs::KnobInt(label, (pValue != NULL ? &pValue[0] : NULL), vMin, vMax, speed, NULL, ImGuiKnobVariant_Tick, size, flags, steps);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pValue != NULL) env->ReleasePrimitiveArrayCritical(obj_pValue, pValue, JNI_FALSE);
        return _result;
    */

    private static native boolean nKnob(String obj_label, int[] obj_pValue, int vMin, int vMax, float speed, int flags, int steps); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pValue = obj_pValue == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_pValue, JNI_FALSE);
        auto _result = ImGuiKnobs::KnobInt(label, (pValue != NULL ? &pValue[0] : NULL), vMin, vMax, speed, NULL, ImGuiKnobVariant_Tick, 0, flags, steps);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pValue != NULL) env->ReleasePrimitiveArrayCritical(obj_pValue, pValue, JNI_FALSE);
        return _result;
    */

    private static native boolean nKnob(String obj_label, int[] obj_pValue, int vMin, int vMax, float speed, int steps); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pValue = obj_pValue == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_pValue, JNI_FALSE);
        auto _result = ImGuiKnobs::KnobInt(label, (pValue != NULL ? &pValue[0] : NULL), vMin, vMax, speed, NULL, ImGuiKnobVariant_Tick, 0, 0, steps);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pValue != NULL) env->ReleasePrimitiveArrayCritical(obj_pValue, pValue, JNI_FALSE);
        return _result;
    */

    private static native boolean nKnob(String obj_label, int[] obj_pValue, int vMin, int vMax, float speed, String obj_format, float size, int flags, int steps); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pValue = obj_pValue == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_pValue, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGuiKnobs::KnobInt(label, (pValue != NULL ? &pValue[0] : NULL), vMin, vMax, speed, format, ImGuiKnobVariant_Tick, size, flags, steps);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pValue != NULL) env->ReleasePrimitiveArrayCritical(obj_pValue, pValue, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nKnob(String obj_label, int[] obj_pValue, int vMin, int vMax, float speed, String obj_format, int variant, int flags, int steps); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pValue = obj_pValue == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_pValue, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGuiKnobs::KnobInt(label, (pValue != NULL ? &pValue[0] : NULL), vMin, vMax, speed, format, variant, 0, flags, steps);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pValue != NULL) env->ReleasePrimitiveArrayCritical(obj_pValue, pValue, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nKnob(String obj_label, int[] obj_pValue, int vMin, int vMax, float speed, String obj_format, int variant, int steps); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pValue = obj_pValue == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_pValue, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGuiKnobs::KnobInt(label, (pValue != NULL ? &pValue[0] : NULL), vMin, vMax, speed, format, variant, 0, 0, steps);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pValue != NULL) env->ReleasePrimitiveArrayCritical(obj_pValue, pValue, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */
}
