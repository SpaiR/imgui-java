package imgui;

import imgui.binding.ImGuiStruct;

/**
 * Coordinates of a rectangle within a texture.
 * <p>
 * When a texture is in {@code ImTextureStatus_WantUpdates} state, we provide a list of individual rectangles to copy to
 * the graphics system. You may use {@link ImTextureData#getUpdates(int)} for the list, or
 * {@link ImTextureData#getUpdateRect()} for a single bounding box.
 */
public final class ImTextureRect extends ImGuiStruct {
    public ImTextureRect(final long ptr) {
        super(ptr);
    }

    /*JNI
        #include "_common.h"
        #define THIS ((ImTextureRect*)STRUCT_PTR)
     */

    /**
     * Upper-left x coordinate of rectangle to update.
     */
    public native int getX(); /*
        return THIS->x;
    */

    /**
     * Upper-left y coordinate of rectangle to update.
     */
    public native int getY(); /*
        return THIS->y;
    */

    /**
     * Width of rectangle to update (in pixels).
     */
    public native int getW(); /*
        return THIS->w;
    */

    /**
     * Height of rectangle to update (in pixels).
     */
    public native int getH(); /*
        return THIS->h;
    */

    /*JNI
        #undef THIS
     */
}
