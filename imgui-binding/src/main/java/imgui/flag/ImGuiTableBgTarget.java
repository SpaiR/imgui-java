package imgui.flag;

import imgui.binding.annotation.BindingAstEnum;
import imgui.binding.annotation.BindingSource;

/**
 * Enum for ImGui::TableSetBgColor()
 * Background colors are rendering in 3 layers:
 * - Layer 0: draw with RowBg0 color if set, otherwise draw with ColumnBg0 if set.
 * - Layer 1: draw with RowBg1 color if set, otherwise draw with ColumnBg1 if set.
 * - Layer 2: draw with CellBg color if set.
 * The purpose of the two row/columns layers is to let you decide if a background color changes should override or blend with the existing color.
 * When using ImGuiTableFlags_RowBg on the table, each row has the RowBg0 color automatically set for odd/even rows.
 * If you set the color of RowBg0 target, your color will override the existing RowBg0 color.
 * If you set the color of RowBg1 or ColumnBg1 target, your color will blend over the RowBg0 color.
 */
@BindingSource
public final class ImGuiTableBgTarget {
    private ImGuiTableBgTarget() {
    }

    @BindingAstEnum(file = "ast-imgui.json", qualType = "ImGuiTableBgTarget_")
    public Void __;
}
