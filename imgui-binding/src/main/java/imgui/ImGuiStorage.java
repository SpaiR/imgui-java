package imgui;

import imgui.binding.ImGuiStructDestroyable;
import imgui.binding.annotation.BindingMethod;
import imgui.binding.annotation.BindingSource;
import imgui.binding.annotation.OptArg;

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
@BindingSource
public final class ImGuiStorage extends ImGuiStructDestroyable {
    public ImGuiStorage() {
    }

    public ImGuiStorage(final long ptr) {
        super(ptr);
    }

    @Override
    protected long create() {
        return nCreate();
    }

    /*JNI
        #include "_common.h"
        #define THIS ((ImGuiStorage*)STRUCT_PTR)
     */

    private native long nCreate(); /*
        return (intptr_t)(new ImGuiStorage());
    */

    // - Get***() functions find pair, never add/allocate. Pairs are sorted so a query is O(log N)
    // - Set***() functions find pair, insertion on demand if missing.
    // - Sorted insertion is costly, paid once. A typical frame shouldn't need to insert any new pair.

    @BindingMethod
    public native void Clear();

    @BindingMethod
    public native int GetInt(int key, @OptArg int defaultVal);

    @BindingMethod
    public native void SetInt(int key, int val);

    @BindingMethod
    public native boolean GetBool(int key, @OptArg boolean defaultVal);

    @BindingMethod
    public native void SetBool(int key, boolean val);

    @BindingMethod
    public native float GetFloat(int key, @OptArg float defaultVal);

    @BindingMethod
    public native void SetFloat(int key, float val);

    /**
     * Use on your own storage if you know only integer are being stored (open/close all tree nodes)
     */
    @BindingMethod
    public native void SetAllInt(int val);

    /**
     * For quicker full rebuild of a storage (instead of an incremental one), you may add all your contents and then sort once.
     */
    @BindingMethod
    public native void BuildSortByKey();

    /*JNI
        #undef THIS
     */
}
