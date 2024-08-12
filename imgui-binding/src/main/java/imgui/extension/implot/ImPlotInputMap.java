package imgui.extension.implot;

import imgui.binding.ImGuiStructDestroyable;
import imgui.binding.annotation.BindingField;
import imgui.binding.annotation.BindingSource;

@BindingSource
public final class ImPlotInputMap extends ImGuiStructDestroyable {
    public ImPlotInputMap() {
        super();
    }

    public ImPlotInputMap(final long ptr) {
        super(ptr);
    }

    @Override
    protected long create() {
        return nCreate();
    }

    /*JNI
        #include "_implot.h"
        #define THIS ((ImPlotInputMap*)STRUCT_PTR)
     */

    private native long nCreate(); /*
        return (intptr_t)(new ImPlotInputMap());
    */

    /**
     * LMB enables panning when held
     */
    @BindingField
    public int Pan;

    /**
     * Optional modifier that must be held for panning/fitting
     */
    @BindingField
    public int PanMod;

    /**
     * LMB initiates fit when double clicked
     */
    @BindingField
    public int Fit;

    /**
     * RMB begins box selection when pressed and confirms
     * selection when released
     */
    @BindingField
    public int Select;

    /**
     * LMB cancels active box selection when pressed;
     * cannot be the same as Select
     */
    @BindingField
    public int SelectCancel;

    /**
     * Optional modifier that must be held for box selection
     */
    @BindingField
    public int SelectMod;

    /**
     * Alt expands active box selection horizontally
     * to plot edge when held
     */
    @BindingField
    public int SelectHorzMod;

    /**
     * Shift expands active box selection vertically
     * to plot edge when held
     */
    @BindingField
    public int SelectVertMod;

    /**
     * RMB opens context menus (if enabled) when clicked
     */
    @BindingField
    public int Menu;

    /**
     * Ctrl when held, all input is ignored;
     * used to enable axis/plots as DND sources
     */
    @BindingField
    public int OverrideMod;

    /**
     * Optional modifier that must be held for scroll wheel zooming
     */
    @BindingField
    public int ZoomMod;

    /**
     * Zoom rate for scroll (e.g. 0.1f = 10% plot range every scroll click);
     * make negative to invert
     */
    @BindingField
    public float ZoomRate;

    /*JNI
        #undef THIS
     */
}
