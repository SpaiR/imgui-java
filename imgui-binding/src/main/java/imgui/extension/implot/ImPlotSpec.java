package imgui.extension.implot;

import imgui.ImVec4;
import imgui.binding.ImGuiStructDestroyable;
import imgui.binding.annotation.BindingField;
import imgui.binding.annotation.BindingSource;

@BindingSource
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

    @BindingField
    public ImVec4 LineColor;

    @BindingField
    public float LineWeight;

    @BindingField
    public ImVec4 FillColor;

    @BindingField
    public float FillAlpha;

    @BindingField
    public int Marker;

    @BindingField
    public float MarkerSize;

    @BindingField
    public ImVec4 MarkerLineColor;

    @BindingField
    public ImVec4 MarkerFillColor;

    @BindingField
    public float Size;

    @BindingField
    public int Offset;

    @BindingField
    public int Stride;

    @BindingField
    public int Flags;

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
