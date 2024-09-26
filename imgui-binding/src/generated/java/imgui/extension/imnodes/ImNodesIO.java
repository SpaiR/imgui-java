package imgui.extension.imnodes;

import imgui.binding.ImGuiStruct;




public final class ImNodesIO extends ImGuiStruct {
    public ImNodesIO(final long ptr) {
        super(ptr);
    }

    /*JNI
        #include "_imnodes.h"
        #define THIS ((ImNodesIO*)STRUCT_PTR)
     */

     /**
     * Holding alt mouse button pans the node area, by default middle mouse button will be used
     * Set based on ImGuiMouseButton values
     */
    public int getAltMouseButton() {
        return nGetAltMouseButton();
    }

    /**
     * Holding alt mouse button pans the node area, by default middle mouse button will be used
     * Set based on ImGuiMouseButton values
     */
    public void setAltMouseButton(final int value) {
        nSetAltMouseButton(value);
    }

    private native int nGetAltMouseButton(); /*
        return THIS->AltMouseButton;
    */

    private native void nSetAltMouseButton(int value); /*
        THIS->AltMouseButton = value;
    */

     /**
     * Panning speed when dragging an element and mouse is outside the main editor view.
     */
    public float getAutoPanningSpeed() {
        return nGetAutoPanningSpeed();
    }

    /**
     * Panning speed when dragging an element and mouse is outside the main editor view.
     */
    public void setAutoPanningSpeed(final float value) {
        nSetAutoPanningSpeed(value);
    }

    private native float nGetAutoPanningSpeed(); /*
        return THIS->AutoPanningSpeed;
    */

    private native void nSetAutoPanningSpeed(float value); /*
        THIS->AutoPanningSpeed = value;
    */

    /*JNI
        #undef THIS
     */
}
