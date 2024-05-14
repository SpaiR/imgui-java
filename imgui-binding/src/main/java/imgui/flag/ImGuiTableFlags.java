package imgui.flag;

import imgui.binding.annotation.BindingAstEnum;
import imgui.binding.annotation.BindingSource;

/**
 * Flags for ImGui::BeginTable()
 * [BETA API] API may evolve slightly! If you use this, please update to the next version when it comes out!
 * - Important! Sizing policies have complex and subtle side effects, more so than you would expect.
 * Read comments/demos carefully + experiment with live demos to get acquainted with them.
 * - The DEFAULT sizing policies are:
 * - Default to SizingFixedFit    if ScrollX is on, or if host window has ImGuiWindowFlags_AlwaysAutoResize.
 * - Default to SizingStretchSame if ScrollX is off.
 * - When ScrollX is off:
 * - Table defaults to SizingStretchSame {@code ->} all Columns defaults to ImGuiTableColumnFlags_WidthStretch with same weight.
 * - Columns sizing policy allowed: Stretch (default), Fixed/Auto.
 * - Fixed Columns will generally obtain their requested width (unless the table cannot fit them all).
 * - Stretch Columns will share the remaining width.
 * - Mixed Fixed/Stretch columns is possible but has various side-effects on resizing behaviors.
 * The typical use of mixing sizing policies is: any number of LEADING Fixed columns, followed by one or two TRAILING Stretch columns.
 * (this is because the visible order of columns have subtle but necessary effects on how they react to manual resizing).
 * - When ScrollX is on:
 * - Table defaults to SizingFixedFit {@code ->} all Columns defaults to ImGuiTableColumnFlags_WidthFixed
 * - Columns sizing policy allowed: Fixed/Auto mostly.
 * - Fixed Columns can be enlarged as needed. Table will show an horizontal scrollbar if needed.
 * - When using auto-resizing (non-resizable) fixed columns, querying the content width to use item right-alignment e.g. SetNextItemWidth(-FLT_MIN) doesn't make sense, would create a feedback loop.
 * - Using Stretch columns OFTEN DOES NOT MAKE SENSE if ScrollX is on, UNLESS you have specified a value for 'inner_width' in BeginTable().
 * If you specify a value for 'inner_width' then effectively the scrolling space is known and Stretch or mixed Fixed/Stretch columns become meaningful again.
 * - Read on documentation at the top of imgui_tables.cpp for details.
 */
@BindingSource
public final class ImGuiTableFlags {
    private ImGuiTableFlags() {
    }

    @BindingAstEnum(file = "ast-imgui.json", qualType = "ImGuiTableFlags_")
    public Void __;
}
