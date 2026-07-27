package imgui;

import imgui.binding.ImGuiStruct;
import imgui.binding.annotation.BindingField;
import imgui.binding.annotation.BindingSource;

import static imgui.binding.annotation.BindingField.Accessor.*;

/**
 * Coordinates of a rectangle within a texture.
 * <p>
 * When a texture is in {@code ImTextureStatus_WantUpdates} state, we provide a list of individual rectangles to copy to
 * the graphics system. You may use {@link ImTextureData#getUpdates(int)} for the list, or
 * {@link ImTextureData#getUpdateRect()} for a single bounding box.
 */
@BindingSource
public final class ImTextureRect extends ImGuiStruct {
    public ImTextureRect(final long ptr) {
        super(ptr);
    }

    /*JNI
        #include "_common.h"
        #define THIS ((ImTextureRect*)STRUCT_PTR)
     */

    /**
     * Upper-left x coordinate of rectangle
     */
    @BindingField(accessors = GETTER, callName = "x")
    public int X;

    /**
     * Upper-left y coordinate of rectangle
     */
    @BindingField(accessors = GETTER, callName = "y")
    public int Y;

    /**
     * Width of rectangle (in pixels).
     */
    @BindingField(accessors = GETTER, callName = "w")
    public int W;

    /**
     * Height of rectangle (in pixels).
     */
    @BindingField(accessors = GETTER, callName = "h")
    public int H;

    /*JNI
        #undef THIS
     */
}
