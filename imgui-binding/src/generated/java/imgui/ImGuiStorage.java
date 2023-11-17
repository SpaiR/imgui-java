package imgui;

import imgui.binding.ImGuiStructDestroyable;

/**
 * Helper: Key-Value storage
 * Typically you don't have to worry about this since a storage is held within each Window.
 * We use it to e.g. store collapse state for a tree (Int 0/1)
 * This is optimized for efficient lookup (dichotomy into a contiguous buffer) and rare insertion (typically tied to user interactions aka max once a frame)
 * You can use it as custom user storage for temporary values. Declare your own storage if, for example:
 * - You want to manipulate the open/close state of a particular sub-tree in your interface (tree node uses Int 0/1 to store their state).
 * - You want to store custom debug data easily without adding or editing structures in your code (probably not efficient, but convenient)
 * Types are NOT stored, so it is up to you to make sure your Key don't collide with different types.
 */
public final class ImGuiStorage extends ImGuiStructDestroyable {
    public ImGuiStorage() {
    }

    public ImGuiStorage(final long ptr) {
        super(ptr);
    }

    /*JNI
        #include "_common.h"

        #define IMGUI_STORAGE ((ImGuiStorage*)STRUCT_PTR)
     */

    @Override
    protected long create() {
        return nCreate();
    }

    private native long nCreate(); /*
        return (intptr_t)(new ImGuiStorage());
    */

    // - Get***() functions find pair, never add/allocate. Pairs are sorted so a query is O(log N)
    // - Set***() functions find pair, insertion on demand if missing.
    // - Sorted insertion is costly, paid once. A typical frame shouldn't need to insert any new pair.

    public native void clear(); /*
        IMGUI_STORAGE->Clear();
    */

    public native int getInt(int imGuiID); /*
        return IMGUI_STORAGE->GetInt(imGuiID);
    */

    public native int getInt(int imGuiID, int defaultVal); /*
        return IMGUI_STORAGE->GetInt(imGuiID, defaultVal);
    */

    public native void setInt(int imGuiID, int val); /*
        IMGUI_STORAGE->SetInt(imGuiID, val);
    */

    public native boolean getBool(int imGuiID); /*
        return IMGUI_STORAGE->GetBool(imGuiID);
    */

    public native boolean getBool(int imGuiID, boolean defaultVal); /*
        return IMGUI_STORAGE->GetBool(imGuiID, defaultVal);
    */

    public native void setBool(int imGuiID, boolean val); /*
        IMGUI_STORAGE->SetBool(imGuiID, val);
    */

    public native float getFloat(int imGuiID); /*
        return IMGUI_STORAGE->GetFloat(imGuiID);
    */

    public native float getFloat(int imGuiID, float defaultVal); /*
        return IMGUI_STORAGE->GetFloat(imGuiID, defaultVal);
    */

    public native void setFloat(int imGuiID, float val); /*
        IMGUI_STORAGE->SetFloat(imGuiID, val);
    */

    /**
     * Use on your own storage if you know only integer are being stored (open/close all tree nodes)
     */
    public native void setAllInt(int val); /*
        IMGUI_STORAGE->SetAllInt(val);
    */

    /**
     * For quicker full rebuild of a storage (instead of an incremental one), you may add all your contents and then sort once.
     */
    public native void buildSortByKey(); /*
        IMGUI_STORAGE->BuildSortByKey();
    */
}
