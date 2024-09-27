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
        super();
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
        return (uintptr_t)(new ImGuiStorage());
    */

    // - Get***() functions find pair, never add/allocate. Pairs are sorted so a query is O(log N)
    // - Set***() functions find pair, insertion on demand if missing.
    // - Sorted insertion is costly, paid once. A typical frame shouldn't need to insert any new pair.

    public void clear() {
        nClear();
    }

    private native void nClear(); /*
        THIS->Clear();
    */

    public int getInt(final int key) {
        return nGetInt(key);
    }

    public int getInt(final int key, final int defaultVal) {
        return nGetInt(key, defaultVal);
    }

    private native int nGetInt(int key); /*
        return THIS->GetInt(key);
    */

    private native int nGetInt(int key, int defaultVal); /*
        return THIS->GetInt(key, defaultVal);
    */

    public void setInt(final int key, final int val) {
        nSetInt(key, val);
    }

    private native void nSetInt(int key, int val); /*
        THIS->SetInt(key, val);
    */

    public boolean getBool(final int key) {
        return nGetBool(key);
    }

    public boolean getBool(final int key, final boolean defaultVal) {
        return nGetBool(key, defaultVal);
    }

    private native boolean nGetBool(int key); /*
        return THIS->GetBool(key);
    */

    private native boolean nGetBool(int key, boolean defaultVal); /*
        return THIS->GetBool(key, defaultVal);
    */

    public void setBool(final int key, final boolean val) {
        nSetBool(key, val);
    }

    private native void nSetBool(int key, boolean val); /*
        THIS->SetBool(key, val);
    */

    public float getFloat(final int key) {
        return nGetFloat(key);
    }

    public float getFloat(final int key, final float defaultVal) {
        return nGetFloat(key, defaultVal);
    }

    private native float nGetFloat(int key); /*
        return THIS->GetFloat(key);
    */

    private native float nGetFloat(int key, float defaultVal); /*
        return THIS->GetFloat(key, defaultVal);
    */

    public void setFloat(final int key, final float val) {
        nSetFloat(key, val);
    }

    private native void nSetFloat(int key, float val); /*
        THIS->SetFloat(key, val);
    */

     /**
     * Use on your own storage if you know only integer are being stored (open/close all tree nodes)
     */
    public void setAllInt(final int val) {
        nSetAllInt(val);
    }

    private native void nSetAllInt(int val); /*
        THIS->SetAllInt(val);
    */

     /**
     * For quicker full rebuild of a storage (instead of an incremental one), you may add all your contents and then sort once.
     */
    public void buildSortByKey() {
        nBuildSortByKey();
    }

    private native void nBuildSortByKey(); /*
        THIS->BuildSortByKey();
    */

    /*JNI
        #undef THIS
     */
}
