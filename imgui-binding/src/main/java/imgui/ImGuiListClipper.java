package imgui;

import imgui.binding.ImGuiStructDestroyable;
import imgui.binding.annotation.BindingField;
import imgui.binding.annotation.BindingMethod;
import imgui.binding.annotation.BindingSource;
import imgui.binding.annotation.OptArg;
import imgui.binding.annotation.ReturnValue;
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
@BindingSource
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

    /**
     * Parent UI context
     */
    @BindingField
    @ReturnValue(isStatic = true)
    public ImGuiContext Ctx;

    @BindingField
    public int DisplayStart;

    @BindingField
    public int DisplayEnd;

    @BindingField(accessors = BindingField.Accessor.GETTER)
    public int ItemsCount;

    @BindingField(accessors = BindingField.Accessor.GETTER)
    public float ItemsHeight;

    @BindingField(accessors = BindingField.Accessor.GETTER)
    public float StartPosY;

    @BindingMethod
    public native void Begin(int itemsCount, @OptArg float itemsHeight);

    /**
     * Automatically called on the last call of Step() that returns false.
     */
    @BindingMethod
    public native void End();

    /**
     * Call until it returns false. The DisplayStart/DisplayEnd fields will be set and you can process/draw those items.
     */
    @BindingMethod
    public native boolean Step();

    /**
     * Call IncludeItemByIndex() or IncludeItemsByIndex() *BEFORE* first call to Step() if you need a range of items to not be clipped, regardless of their visibility.
     * (Due to alignment / padding of certain items it is possible that an extra item may be included on either end of the display range).
     */
    @BindingMethod
    public native void IncludeItemByIndex(int itemIndex);

    /**
     * Call IncludeItemByIndex() or IncludeItemsByIndex() *BEFORE* first call to Step() if you need a range of items to not be clipped, regardless of their visibility.
     * (Due to alignment / padding of certain items it is possible that an extra item may be included on either end of the display range).
     *
     * @param itemEnd is exclusive e.g. use (42, 42+1) to make item 42 never clipped.
     */
    @BindingMethod
    public native void IncludeItemsByIndex(int itemBegin, int itemEnd);

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
