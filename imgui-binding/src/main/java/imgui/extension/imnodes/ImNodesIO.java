package imgui.extension.imnodes;

import imgui.binding.ImGuiStruct;
import imgui.binding.annotation.BindingField;
import imgui.binding.annotation.BindingSource;

@BindingSource
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
    @BindingField
    public int AltMouseButton;

    /**
     * Panning speed when dragging an element and mouse is outside the main editor view.
     */
    @BindingField
    public float AutoPanningSpeed;

    /*JNI
        #undef THIS
     */
}
