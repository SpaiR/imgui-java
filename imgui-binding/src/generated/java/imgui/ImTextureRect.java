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
     * Upper-left x coordinate of rectangle
     */
    public int getX() {
        return nGetX();
    }

    private native int nGetX(); /*
        return THIS->x;
    */

    /**
     * Upper-left y coordinate of rectangle
     */
    public int getY() {
        return nGetY();
    }

    private native int nGetY(); /*
        return THIS->y;
    */

    /**
     * Width of rectangle (in pixels).
     */
    public int getW() {
        return nGetW();
    }

    private native int nGetW(); /*
        return THIS->w;
    */

    /**
     * Height of rectangle (in pixels).
     */
    public int getH() {
        return nGetH();
    }

    private native int nGetH(); /*
        return THIS->h;
    */

    /*JNI
        #undef THIS
     */
}
