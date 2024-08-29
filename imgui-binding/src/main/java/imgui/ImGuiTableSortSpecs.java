package imgui;

import imgui.binding.ImGuiStruct;
import imgui.binding.annotation.BindingField;
import imgui.binding.annotation.BindingField.Accessor;
import imgui.binding.annotation.BindingSource;

/**
 * Sorting specifications for a table (often handling sort specs for a single column, occasionally more)
 * Obtained by calling TableGetSortSpecs().
 * When 'SpecsDirty == true' you can sort your data. It will be true with sorting specs have changed since last call, or the first time.
 * Make sure to set 'SpecsDirty = false' after sorting, else you may wastefully sort your data every frame!
 */
@BindingSource
public final class ImGuiTableSortSpecs extends ImGuiStruct {
    public ImGuiTableSortSpecs(final long ptr) {
        super(ptr);
    }

    /*JNI
        #include "_common.h"
        #define THIS ((ImGuiTableSortSpecs*)STRUCT_PTR)
     */

    /**
     * Pointer to sort spec array.
     */
    public ImGuiTableColumnSortSpecs[] getSpecs() {
        final long[] specsPointers = nGetSpecs();
        final ImGuiTableColumnSortSpecs[] specs = new ImGuiTableColumnSortSpecs[specsPointers.length];
        for (int i = 0; i < specsPointers.length; i++) {
            specs[i] = new ImGuiTableColumnSortSpecs(specsPointers[i]);
        }
        return specs;
    }

    private native long[] nGetSpecs(); /*
        const ImGuiTableColumnSortSpecs* specs = THIS->Specs;
        int specsCount = THIS->SpecsCount;
        jlong jBuf[specsCount];
        for (int i = 0; i < specsCount; i++) {
            jBuf[i] = (uintptr_t)specs;
            specs++;
        }
        jlongArray result = env->NewLongArray(specsCount);
        env->SetLongArrayRegion(result, 0, specsCount, jBuf);
        return result;
    */

    /**
     * Sort spec count. Most often 1. May be {@code > } 1 when ImGuiTableFlags_SortMulti is enabled. May be == 0 when ImGuiTableFlags_SortTristate is enabled.
     */
    @BindingField(accessors = { Accessor.GETTER })
    public int SpecsCount;

    /**
     * Set to true when specs have changed since last time! Use this to sort again, then clear the flag.
     */
    @BindingField
    public boolean SpecsDirty;

    /*JNI
        #undef THIS
     */
}
