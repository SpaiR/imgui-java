package imgui;

import imgui.binding.ImGuiStructDestroyable;
import imgui.callback.ImListClipperCallback;
import imgui.internal.ImGuiContext;

import java.util.function.Consumer;

/**
 * Helper: Manually clip large list of items.
 * If you have lots evenly spaced items and you have random access to the list, you can perform coarse
 * clipping based on visibility to only submit items that are in view.
 * The clipper calculates the range of visible items and advance the cursor to compensate for the non-visible items we have skipped.
 * (Dear ImGui already clip items based on their bounds but: it needs to first layout the item to do so, and generally
 * fetching/submitting your own data incurs additional cost. Coarse clipping using ImGuiListClipper allows you to easily
 * scale using lists with tens of thousands of items without a problem)
 * Usage:
 * <pre>
 *  ImGuiListClipper clipper;
 *  clipper.Begin(1000);         // We have 1000 elements, evenly spaced.
 *  while (clipper.Step())
 *  for (int i = clipper.DisplayStart; i {@code <} clipper.DisplayEnd; i++)
 *  ImGui::Text("line number %d", i);
 * </pre>
 * Generally what happens is:
 * - Clipper lets you process the first element (DisplayStart = 0, DisplayEnd = 1) regardless of it being visible or not.
 * - User code submit that one element.
 * - Clipper can measure the height of the first element
 * - Clipper calculate the actual range of elements to display based on the current clipping rectangle, position the cursor before the first visible element.
 * - User code submit visible elements.
 * - The clipper also handles various subtleties related to keyboard/gamepad navigation, wrapping etc.
 */
public final class ImGuiListClipper extends ImGuiStructDestroyable {
    public ImGuiListClipper() {
        super();
    }

    public ImGuiListClipper(final long ptr) {
        super(ptr);
    }

    @Override
    protected long create() {
        return nCreate();
    }

    /*JNI
        #include "_common.h"
        #define THIS ((ImGuiListClipper*)STRUCT_PTR)
     */

    private native long nCreate(); /*
        return (uintptr_t)(new ImGuiListClipper());
    */

    private static final ImGuiContext _GETCTX_1 = new ImGuiContext(0);

    /**
     * Parent UI context
     */
    public ImGuiContext getCtx() {
        _GETCTX_1.ptr = nGetCtx();
        return _GETCTX_1;
    }

    /**
     * Parent UI context
     */
    public void setCtx(final ImGuiContext value) {
        nSetCtx(value.ptr);
    }

    private native long nGetCtx(); /*
        return (uintptr_t)THIS->Ctx;
    */

    private native void nSetCtx(long value); /*
        THIS->Ctx = reinterpret_cast<ImGuiContext*>(value);
    */

    public int getDisplayStart() {
        return nGetDisplayStart();
    }

    public void setDisplayStart(final int value) {
        nSetDisplayStart(value);
    }

    private native int nGetDisplayStart(); /*
        return THIS->DisplayStart;
    */

    private native void nSetDisplayStart(int value); /*
        THIS->DisplayStart = value;
    */

    public int getDisplayEnd() {
        return nGetDisplayEnd();
    }

    public void setDisplayEnd(final int value) {
        nSetDisplayEnd(value);
    }

    private native int nGetDisplayEnd(); /*
        return THIS->DisplayEnd;
    */

    private native void nSetDisplayEnd(int value); /*
        THIS->DisplayEnd = value;
    */

    public int getItemsCount() {
        return nGetItemsCount();
    }

    private native int nGetItemsCount(); /*
        return THIS->ItemsCount;
    */

    public float getItemsHeight() {
        return nGetItemsHeight();
    }

    private native float nGetItemsHeight(); /*
        return THIS->ItemsHeight;
    */

    public float getStartPosY() {
        return nGetStartPosY();
    }

    private native float nGetStartPosY(); /*
        return THIS->StartPosY;
    */

    public void begin(final int itemsCount) {
        nBegin(itemsCount);
    }

    public void begin(final int itemsCount, final float itemsHeight) {
        nBegin(itemsCount, itemsHeight);
    }

    private native void nBegin(int itemsCount); /*
        THIS->Begin(itemsCount);
    */

    private native void nBegin(int itemsCount, float itemsHeight); /*
        THIS->Begin(itemsCount, itemsHeight);
    */

    /**
     * Automatically called on the last call of Step() that returns false.
     */
    public void end() {
        nEnd();
    }

    private native void nEnd(); /*
        THIS->End();
    */

    /**
     * Call until it returns false. The DisplayStart/DisplayEnd fields will be set and you can process/draw those items.
     */
    public boolean step() {
        return nStep();
    }

    private native boolean nStep(); /*
        return THIS->Step();
    */

    /**
     * Call IncludeItemByIndex() or IncludeItemsByIndex() *BEFORE* first call to Step() if you need a range of items to not be clipped, regardless of their visibility.
     * (Due to alignment / padding of certain items it is possible that an extra item may be included on either end of the display range).
     */
    public void includeItemByIndex(final int itemIndex) {
        nIncludeItemByIndex(itemIndex);
    }

    private native void nIncludeItemByIndex(int itemIndex); /*
        THIS->IncludeItemByIndex(itemIndex);
    */

    /**
     * Call IncludeItemByIndex() or IncludeItemsByIndex() *BEFORE* first call to Step() if you need a range of items to not be clipped, regardless of their visibility.
     * (Due to alignment / padding of certain items it is possible that an extra item may be included on either end of the display range).
     *
     * @param itemEnd
     * 		is exclusive e.g. use (42, 42+1) to make item 42 never clipped.
     */
    public void includeItemsByIndex(final int itemBegin, final int itemEnd) {
        nIncludeItemsByIndex(itemBegin, itemEnd);
    }

    private native void nIncludeItemsByIndex(int itemBegin, int itemEnd); /*
        THIS->IncludeItemsByIndex(itemBegin, itemEnd);
    */

    /**
     * Shortcut to use {@link ImGuiListClipper} instance.
     *
     * @param clipAction action to use inside the {@link ImGuiListClipper} instance
     */
    public static void forEach(final Consumer<ImGuiListClipper> clipAction) {
        final ImGuiListClipper clipper = new ImGuiListClipper();
        clipAction.accept(clipper);
        clipper.destroy();
    }

    /**
     * @param itemsCount Use -1 to ignore (you can call Begin later).
     *                   Use INT_MAX if you don't know how many items you have (in which case the cursor won't be advanced in the final step).
     * @param callback   action to do in iterations
     */
    public static void forEach(final int itemsCount, final ImListClipperCallback callback) {
        nForEach(itemsCount, -1, callback);
    }

    /**
     * @param itemsCount  Use -1 to ignore (you can call Begin later).
     *                    Use INT_MAX if you don't know how many items you have (in which case the cursor won't be advanced in the final step).
     * @param itemsHeight Use -1.0f to be calculated automatically on first step.
     *                    Otherwise, pass in the distance between your items, typically GetTextLineHeightWithSpacing() or GetFrameHeightWithSpacing().
     * @param callback    action to do in iterations
     */
    public static void forEach(final int itemsCount, final int itemsHeight, final ImListClipperCallback callback) {
        nForEach(itemsCount, itemsHeight, callback);
    }

    private static native void nForEach(int itemsCount, int itemsHeight, ImListClipperCallback callback); /*
        ImGuiListClipper clipper;
        clipper.Begin(itemsCount, itemsHeight);
        while (clipper.Step()) {
            for (int i = clipper.DisplayStart; i < clipper.DisplayEnd; i++) {
                Jni::CallImListClipperCallback(env, callback, i);
            }
        }
    */

    /*JNI
        #undef THIS
     */
}
