package imgui;

import imgui.binding.ImGuiStruct;

/**
 * Sorting specification for one column of a table.
 */
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
    public int getColumnUserID() {
        return nGetColumnUserID();
    }

    private native int nGetColumnUserID(); /*
        return THIS->ColumnUserID;
    */

    /**
     * Index of the column
     */
    public int getColumnIndex() {
        return nGetColumnIndex();
    }

    private native int nGetColumnIndex(); /*
        return THIS->ColumnIndex;
    */

    /**
     * Index within parent ImGuiTableSortSpecs (always stored in order starting from 0, tables sorted on a single criteria will always have a 0 here)
     */
    public int getSortOrder() {
        return nGetSortOrder();
    }

    private native int nGetSortOrder(); /*
        return THIS->SortOrder;
    */

    /**
     * ImGuiSortDirection_Ascending or ImGuiSortDirection_Descending (you can use this or SortSign, whichever is more convenient for your sort function)
     */
    public int getSortDirection() {
        return nGetSortDirection();
    }

    private native int nGetSortDirection(); /*
        return THIS->SortDirection;
    */

    /*JNI
        #undef THIS
     */
}
