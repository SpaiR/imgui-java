package imgui.extension.implot;

import imgui.ImVec4;
import imgui.binding.ImGuiStructDestroyable;

public final class ImPlotSpec extends ImGuiStructDestroyable {
    public ImPlotSpec() {
        super();
    }

    public ImPlotSpec(final long ptr) {
        super(ptr);
    }

    @Override
    protected long create() {
        return nCreate();
    }

    @Override
    public void destroy() {
        nFreeArrays(ptr);
        super.destroy();
    }

    /*JNI
        #include "_implot.h"
        #define THIS ((ImPlotSpec*)STRUCT_PTR)
     */

    private native long nCreate(); /*
        return (uintptr_t)(new ImPlotSpec());
    */

    // Frees any array backing buffers currently held by this spec. Called from destroy()
    // before the struct itself is deleted.
    private native void nFreeArrays(long specPtr); /*
        ImPlotSpec* spec = (ImPlotSpec*)specPtr;
        if (spec->LineColors)       { ImGui::MemFree(spec->LineColors);       spec->LineColors       = NULL; }
        if (spec->FillColors)       { ImGui::MemFree(spec->FillColors);       spec->FillColors       = NULL; }
        if (spec->MarkerSizes)      { ImGui::MemFree(spec->MarkerSizes);      spec->MarkerSizes      = NULL; }
        if (spec->MarkerLineColors) { ImGui::MemFree(spec->MarkerLineColors); spec->MarkerLineColors = NULL; }
        if (spec->MarkerFillColors) { ImGui::MemFree(spec->MarkerFillColors); spec->MarkerFillColors = NULL; }
    */

    public ImVec4 getLineColor() {
        final ImVec4 dst = new ImVec4();
        nGetLineColor(dst);
        return dst;
    }

    public float getLineColorX() {
        return nGetLineColorX();
    }

    public float getLineColorY() {
        return nGetLineColorY();
    }

    public float getLineColorZ() {
        return nGetLineColorZ();
    }

    public float getLineColorW() {
        return nGetLineColorW();
    }

    public void getLineColor(final ImVec4 dst) {
        nGetLineColor(dst);
    }

    public void setLineColor(final ImVec4 value) {
        nSetLineColor(value.x, value.y, value.z, value.w);
    }

    public void setLineColor(final float valueX, final float valueY, final float valueZ, final float valueW) {
        nSetLineColor(valueX, valueY, valueZ, valueW);
    }

    private native void nGetLineColor(ImVec4 dst); /*
        Jni::ImVec4Cpy(env, THIS->LineColor, dst);
    */

    private native float nGetLineColorX(); /*
        return THIS->LineColor.x;
    */

    private native float nGetLineColorY(); /*
        return THIS->LineColor.y;
    */

    private native float nGetLineColorZ(); /*
        return THIS->LineColor.z;
    */

    private native float nGetLineColorW(); /*
        return THIS->LineColor.w;
    */

    private native void nSetLineColor(float valueX, float valueY, float valueZ, float valueW); /*MANUAL
        ImVec4 value = ImVec4(valueX, valueY, valueZ, valueW);
        THIS->LineColor = value;
    */

    public float getLineWeight() {
        return nGetLineWeight();
    }

    public void setLineWeight(final float value) {
        nSetLineWeight(value);
    }

    private native float nGetLineWeight(); /*
        return THIS->LineWeight;
    */

    private native void nSetLineWeight(float value); /*
        THIS->LineWeight = value;
    */

    public ImVec4 getFillColor() {
        final ImVec4 dst = new ImVec4();
        nGetFillColor(dst);
        return dst;
    }

    public float getFillColorX() {
        return nGetFillColorX();
    }

    public float getFillColorY() {
        return nGetFillColorY();
    }

    public float getFillColorZ() {
        return nGetFillColorZ();
    }

    public float getFillColorW() {
        return nGetFillColorW();
    }

    public void getFillColor(final ImVec4 dst) {
        nGetFillColor(dst);
    }

    public void setFillColor(final ImVec4 value) {
        nSetFillColor(value.x, value.y, value.z, value.w);
    }

    public void setFillColor(final float valueX, final float valueY, final float valueZ, final float valueW) {
        nSetFillColor(valueX, valueY, valueZ, valueW);
    }

    private native void nGetFillColor(ImVec4 dst); /*
        Jni::ImVec4Cpy(env, THIS->FillColor, dst);
    */

    private native float nGetFillColorX(); /*
        return THIS->FillColor.x;
    */

    private native float nGetFillColorY(); /*
        return THIS->FillColor.y;
    */

    private native float nGetFillColorZ(); /*
        return THIS->FillColor.z;
    */

    private native float nGetFillColorW(); /*
        return THIS->FillColor.w;
    */

    private native void nSetFillColor(float valueX, float valueY, float valueZ, float valueW); /*MANUAL
        ImVec4 value = ImVec4(valueX, valueY, valueZ, valueW);
        THIS->FillColor = value;
    */

    public float getFillAlpha() {
        return nGetFillAlpha();
    }

    public void setFillAlpha(final float value) {
        nSetFillAlpha(value);
    }

    private native float nGetFillAlpha(); /*
        return THIS->FillAlpha;
    */

    private native void nSetFillAlpha(float value); /*
        THIS->FillAlpha = value;
    */

    public int getMarker() {
        return nGetMarker();
    }

    public void setMarker(final int value) {
        nSetMarker(value);
    }

    private native int nGetMarker(); /*
        return THIS->Marker;
    */

    private native void nSetMarker(int value); /*
        THIS->Marker = value;
    */

    public float getMarkerSize() {
        return nGetMarkerSize();
    }

    public void setMarkerSize(final float value) {
        nSetMarkerSize(value);
    }

    private native float nGetMarkerSize(); /*
        return THIS->MarkerSize;
    */

    private native void nSetMarkerSize(float value); /*
        THIS->MarkerSize = value;
    */

    public ImVec4 getMarkerLineColor() {
        final ImVec4 dst = new ImVec4();
        nGetMarkerLineColor(dst);
        return dst;
    }

    public float getMarkerLineColorX() {
        return nGetMarkerLineColorX();
    }

    public float getMarkerLineColorY() {
        return nGetMarkerLineColorY();
    }

    public float getMarkerLineColorZ() {
        return nGetMarkerLineColorZ();
    }

    public float getMarkerLineColorW() {
        return nGetMarkerLineColorW();
    }

    public void getMarkerLineColor(final ImVec4 dst) {
        nGetMarkerLineColor(dst);
    }

    public void setMarkerLineColor(final ImVec4 value) {
        nSetMarkerLineColor(value.x, value.y, value.z, value.w);
    }

    public void setMarkerLineColor(final float valueX, final float valueY, final float valueZ, final float valueW) {
        nSetMarkerLineColor(valueX, valueY, valueZ, valueW);
    }

    private native void nGetMarkerLineColor(ImVec4 dst); /*
        Jni::ImVec4Cpy(env, THIS->MarkerLineColor, dst);
    */

    private native float nGetMarkerLineColorX(); /*
        return THIS->MarkerLineColor.x;
    */

    private native float nGetMarkerLineColorY(); /*
        return THIS->MarkerLineColor.y;
    */

    private native float nGetMarkerLineColorZ(); /*
        return THIS->MarkerLineColor.z;
    */

    private native float nGetMarkerLineColorW(); /*
        return THIS->MarkerLineColor.w;
    */

    private native void nSetMarkerLineColor(float valueX, float valueY, float valueZ, float valueW); /*MANUAL
        ImVec4 value = ImVec4(valueX, valueY, valueZ, valueW);
        THIS->MarkerLineColor = value;
    */

    public ImVec4 getMarkerFillColor() {
        final ImVec4 dst = new ImVec4();
        nGetMarkerFillColor(dst);
        return dst;
    }

    public float getMarkerFillColorX() {
        return nGetMarkerFillColorX();
    }

    public float getMarkerFillColorY() {
        return nGetMarkerFillColorY();
    }

    public float getMarkerFillColorZ() {
        return nGetMarkerFillColorZ();
    }

    public float getMarkerFillColorW() {
        return nGetMarkerFillColorW();
    }

    public void getMarkerFillColor(final ImVec4 dst) {
        nGetMarkerFillColor(dst);
    }

    public void setMarkerFillColor(final ImVec4 value) {
        nSetMarkerFillColor(value.x, value.y, value.z, value.w);
    }

    public void setMarkerFillColor(final float valueX, final float valueY, final float valueZ, final float valueW) {
        nSetMarkerFillColor(valueX, valueY, valueZ, valueW);
    }

    private native void nGetMarkerFillColor(ImVec4 dst); /*
        Jni::ImVec4Cpy(env, THIS->MarkerFillColor, dst);
    */

    private native float nGetMarkerFillColorX(); /*
        return THIS->MarkerFillColor.x;
    */

    private native float nGetMarkerFillColorY(); /*
        return THIS->MarkerFillColor.y;
    */

    private native float nGetMarkerFillColorZ(); /*
        return THIS->MarkerFillColor.z;
    */

    private native float nGetMarkerFillColorW(); /*
        return THIS->MarkerFillColor.w;
    */

    private native void nSetMarkerFillColor(float valueX, float valueY, float valueZ, float valueW); /*MANUAL
        ImVec4 value = ImVec4(valueX, valueY, valueZ, valueW);
        THIS->MarkerFillColor = value;
    */

    public float getSize() {
        return nGetSize();
    }

    public void setSize(final float value) {
        nSetSize(value);
    }

    private native float nGetSize(); /*
        return THIS->Size;
    */

    private native void nSetSize(float value); /*
        THIS->Size = value;
    */

    public int getOffset() {
        return nGetOffset();
    }

    public void setOffset(final int value) {
        nSetOffset(value);
    }

    private native int nGetOffset(); /*
        return THIS->Offset;
    */

    private native void nSetOffset(int value); /*
        THIS->Offset = value;
    */

    public int getStride() {
        return nGetStride();
    }

    public void setStride(final int value) {
        nSetStride(value);
    }

    private native int nGetStride(); /*
        return THIS->Stride;
    */

    private native void nSetStride(int value); /*
        THIS->Stride = value;
    */

    public int getFlags() {
        return nGetFlags();
    }

    public void setFlags(final int value) {
        nSetFlags(value);
    }

    private native int nGetFlags(); /*
        return THIS->Flags;
    */

    private native void nSetFlags(int value); /*
        THIS->Flags = value;
    */

    // Per-index color array for lines (ImU32 packed RGBA). Pass null to clear.
    // Ownership: the backing buffer is allocated in native memory and freed on destroy()
    // or when reassigned.
    public void setLineColors(final int[] colors) {
        nSetLineColors(colors, colors != null ? colors.length : 0);
    }

    private native void nSetLineColors(int[] colors, int len); /*
        if (THIS->LineColors) { ImGui::MemFree(THIS->LineColors); THIS->LineColors = NULL; }
        if (colors != NULL && len > 0) {
            ImU32* buf = (ImU32*)ImGui::MemAlloc(len * sizeof(ImU32));
            for (int i = 0; i < len; i++) { buf[i] = (ImU32)colors[i]; }
            THIS->LineColors = buf;
        }
    */

    public void setFillColors(final int[] colors) {
        nSetFillColors(colors, colors != null ? colors.length : 0);
    }

    private native void nSetFillColors(int[] colors, int len); /*
        if (THIS->FillColors) { ImGui::MemFree(THIS->FillColors); THIS->FillColors = NULL; }
        if (colors != NULL && len > 0) {
            ImU32* buf = (ImU32*)ImGui::MemAlloc(len * sizeof(ImU32));
            for (int i = 0; i < len; i++) { buf[i] = (ImU32)colors[i]; }
            THIS->FillColors = buf;
        }
    */

    public void setMarkerSizes(final float[] sizes) {
        nSetMarkerSizes(sizes, sizes != null ? sizes.length : 0);
    }

    private native void nSetMarkerSizes(float[] sizes, int len); /*
        if (THIS->MarkerSizes) { ImGui::MemFree(THIS->MarkerSizes); THIS->MarkerSizes = NULL; }
        if (sizes != NULL && len > 0) {
            float* buf = (float*)ImGui::MemAlloc(len * sizeof(float));
            for (int i = 0; i < len; i++) { buf[i] = sizes[i]; }
            THIS->MarkerSizes = buf;
        }
    */

    public void setMarkerLineColors(final int[] colors) {
        nSetMarkerLineColors(colors, colors != null ? colors.length : 0);
    }

    private native void nSetMarkerLineColors(int[] colors, int len); /*
        if (THIS->MarkerLineColors) { ImGui::MemFree(THIS->MarkerLineColors); THIS->MarkerLineColors = NULL; }
        if (colors != NULL && len > 0) {
            ImU32* buf = (ImU32*)ImGui::MemAlloc(len * sizeof(ImU32));
            for (int i = 0; i < len; i++) { buf[i] = (ImU32)colors[i]; }
            THIS->MarkerLineColors = buf;
        }
    */

    public void setMarkerFillColors(final int[] colors) {
        nSetMarkerFillColors(colors, colors != null ? colors.length : 0);
    }

    private native void nSetMarkerFillColors(int[] colors, int len); /*
        if (THIS->MarkerFillColors) { ImGui::MemFree(THIS->MarkerFillColors); THIS->MarkerFillColors = NULL; }
        if (colors != NULL && len > 0) {
            ImU32* buf = (ImU32*)ImGui::MemAlloc(len * sizeof(ImU32));
            for (int i = 0; i < len; i++) { buf[i] = (ImU32)colors[i]; }
            THIS->MarkerFillColors = buf;
        }
    */

    /*JNI
        #undef THIS
     */
}
