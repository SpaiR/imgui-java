package imgui;

import imgui.binding.ImGuiStruct;
import imgui.binding.annotation.BindingField;
import imgui.binding.annotation.BindingField.Accessor;
import imgui.binding.annotation.BindingSource;

/**
 * Sorting specification for one column of a table.
 */
@BindingSource
public final class ImGuiTableColumnSortSpecs extends ImGuiStruct {

    public ImGuiTableColumnSortSpecs(final long ptr) {
        super(ptr);
    }

    /*JNI
        #include "_common.h"
        #define THIS ((ImGuiTableColumnSortSpecs*)STRUCT_PTR)
     */

    /**
     * User id of the column (if specified by a TableSetupColumn() call)
     */
    @BindingField(accessors = { Accessor.GETTER })
    public int ColumnUserID;

    /**
     * Index of the column
     */
    @BindingField(accessors = { Accessor.GETTER })
    public int ColumnIndex;

    /**
     * Index within parent ImGuiTableSortSpecs (always stored in order starting from 0, tables sorted on a single criteria will always have a 0 here)
     */
    @BindingField(accessors = { Accessor.GETTER })
    public int SortOrder;

    /**
     * ImGuiSortDirection_Ascending or ImGuiSortDirection_Descending (you can use this or SortSign, whichever is more convenient for your sort function)
     */
    @BindingField(accessors = { Accessor.GETTER })
    public int SortDirection;

    /*JNI
        #undef THIS
     */
}
