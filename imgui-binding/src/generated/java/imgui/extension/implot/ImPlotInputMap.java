package imgui.extension.implot;

import imgui.binding.ImGuiStructDestroyable;

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
        return (uintptr_t)(new ImPlotInputMap());
    */

    /**
     * LMB enables panning when held
     */
    public int getPan() {
        return nGetPan();
    }

    /**
     * LMB enables panning when held
     */
    public void setPan(final int value) {
        nSetPan(value);
    }

    private native int nGetPan(); /*
        return THIS->Pan;
    */

    private native void nSetPan(int value); /*
        THIS->Pan = value;
    */

    /**
     * Optional modifier that must be held for panning/fitting
     */
    public int getPanMod() {
        return nGetPanMod();
    }

    /**
     * Optional modifier that must be held for panning/fitting
     */
    public void setPanMod(final int value) {
        nSetPanMod(value);
    }

    private native int nGetPanMod(); /*
        return THIS->PanMod;
    */

    private native void nSetPanMod(int value); /*
        THIS->PanMod = value;
    */

    /**
     * LMB initiates fit when double clicked
     */
    public int getFit() {
        return nGetFit();
    }

    /**
     * LMB initiates fit when double clicked
     */
    public void setFit(final int value) {
        nSetFit(value);
    }

    private native int nGetFit(); /*
        return THIS->Fit;
    */

    private native void nSetFit(int value); /*
        THIS->Fit = value;
    */

    /**
     * RMB begins box selection when pressed and confirms
     * selection when released
     */
    public int getSelect() {
        return nGetSelect();
    }

    /**
     * RMB begins box selection when pressed and confirms
     * selection when released
     */
    public void setSelect(final int value) {
        nSetSelect(value);
    }

    private native int nGetSelect(); /*
        return THIS->Select;
    */

    private native void nSetSelect(int value); /*
        THIS->Select = value;
    */

    /**
     * LMB cancels active box selection when pressed;
     * cannot be the same as Select
     */
    public int getSelectCancel() {
        return nGetSelectCancel();
    }

    /**
     * LMB cancels active box selection when pressed;
     * cannot be the same as Select
     */
    public void setSelectCancel(final int value) {
        nSetSelectCancel(value);
    }

    private native int nGetSelectCancel(); /*
        return THIS->SelectCancel;
    */

    private native void nSetSelectCancel(int value); /*
        THIS->SelectCancel = value;
    */

    /**
     * Optional modifier that must be held for box selection
     */
    public int getSelectMod() {
        return nGetSelectMod();
    }

    /**
     * Optional modifier that must be held for box selection
     */
    public void setSelectMod(final int value) {
        nSetSelectMod(value);
    }

    private native int nGetSelectMod(); /*
        return THIS->SelectMod;
    */

    private native void nSetSelectMod(int value); /*
        THIS->SelectMod = value;
    */

    /**
     * Alt expands active box selection horizontally
     * to plot edge when held
     */
    public int getSelectHorzMod() {
        return nGetSelectHorzMod();
    }

    /**
     * Alt expands active box selection horizontally
     * to plot edge when held
     */
    public void setSelectHorzMod(final int value) {
        nSetSelectHorzMod(value);
    }

    private native int nGetSelectHorzMod(); /*
        return THIS->SelectHorzMod;
    */

    private native void nSetSelectHorzMod(int value); /*
        THIS->SelectHorzMod = value;
    */

    /**
     * Shift expands active box selection vertically
     * to plot edge when held
     */
    public int getSelectVertMod() {
        return nGetSelectVertMod();
    }

    /**
     * Shift expands active box selection vertically
     * to plot edge when held
     */
    public void setSelectVertMod(final int value) {
        nSetSelectVertMod(value);
    }

    private native int nGetSelectVertMod(); /*
        return THIS->SelectVertMod;
    */

    private native void nSetSelectVertMod(int value); /*
        THIS->SelectVertMod = value;
    */

    /**
     * RMB opens context menus (if enabled) when clicked
     */
    public int getMenu() {
        return nGetMenu();
    }

    /**
     * RMB opens context menus (if enabled) when clicked
     */
    public void setMenu(final int value) {
        nSetMenu(value);
    }

    private native int nGetMenu(); /*
        return THIS->Menu;
    */

    private native void nSetMenu(int value); /*
        THIS->Menu = value;
    */

    /**
     * Ctrl when held, all input is ignored;
     * used to enable axis/plots as DND sources
     */
    public int getOverrideMod() {
        return nGetOverrideMod();
    }

    /**
     * Ctrl when held, all input is ignored;
     * used to enable axis/plots as DND sources
     */
    public void setOverrideMod(final int value) {
        nSetOverrideMod(value);
    }

    private native int nGetOverrideMod(); /*
        return THIS->OverrideMod;
    */

    private native void nSetOverrideMod(int value); /*
        THIS->OverrideMod = value;
    */

    /**
     * Optional modifier that must be held for scroll wheel zooming
     */
    public int getZoomMod() {
        return nGetZoomMod();
    }

    /**
     * Optional modifier that must be held for scroll wheel zooming
     */
    public void setZoomMod(final int value) {
        nSetZoomMod(value);
    }

    private native int nGetZoomMod(); /*
        return THIS->ZoomMod;
    */

    private native void nSetZoomMod(int value); /*
        THIS->ZoomMod = value;
    */

    /**
     * Zoom rate for scroll (e.g. 0.1f = 10% plot range every scroll click);
     * make negative to invert
     */
    public float getZoomRate() {
        return nGetZoomRate();
    }

    /**
     * Zoom rate for scroll (e.g. 0.1f = 10% plot range every scroll click);
     * make negative to invert
     */
    public void setZoomRate(final float value) {
        nSetZoomRate(value);
    }

    private native float nGetZoomRate(); /*
        return THIS->ZoomRate;
    */

    private native void nSetZoomRate(float value); /*
        THIS->ZoomRate = value;
    */

    /*JNI
        #undef THIS
     */
}
