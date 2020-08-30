package imgui;

import imgui.callback.ImListClipperCallback;

/**
 * Helper: Manually clip large list of items.
 * If you are submitting lots of evenly spaced items and you have a random access to the list, you can perform coarse clipping based on visibility to save yourself from processing those items at all.
 * The clipper calculates the range of visible items and advance the cursor to compensate for the non-visible items we have skipped.
 * ImGui already clip items based on their bounds but it needs to measure text size to do so. Coarse clipping before submission makes this cost and your own data fetching/submission cost null.
 * Usage:
 * <pre>
 *     ImGuiListClipper clipper(1000);  // we have 1000 elements, evenly spaced.
 *     while (clipper.Step())
 *         for (int i = clipper.DisplayStart; i {@code <} clipper.DisplayEnd; i++)
 *             ImGui::Text("line number %d", i);
 * </pre>
 * - Step 0: the clipper let you process the first element, regardless of it being visible or not, so we can measure the element height (step skipped if we passed a known height as second arg to constructor).
 * - Step 1: the clipper infer height from first element, calculate the actual range of elements to display, and position the cursor before the first element.
 * - (Step 2: empty step only required if an explicit items_height was passed to constructor or Begin() and user call Step(). Does nothing and switch to Step 3.)
 * - Step 3: the clipper validate that we have reached the expected Y position (corresponding to element DisplayEnd), advance the cursor to the end of the list and then returns 'false' to end the loop.
 * <p>
 * BINDING NOTICE:
 * It's impossible to implement the same API like in the original. Method {@link #forEach(int, int, ImListClipperCallback)} could be used instead.
 * <pre>
 *     ImGuiListClipper.forEach(1000, new ImListClipperCallback() {
 *         public void accept(int index) {
 *             ImGui.text(String.format("line number %d", index));
 *         }
 *     });
 * </pre>
 */
public final class ImGuiListClipper {
    private ImGuiListClipper() {
    }

    /*JNI
        #include <imgui.h>
        #include "jni_callbacks.h"
     */

    /**
     * @param itemsCount Use -1 to ignore (you can call Begin later).
     *                   Use INT_MAX if you don't know how many items you have (in which case the cursor won't be advanced in the final step).
     * @param callback action to do in iterations
     */
    public static void forEach(final int itemsCount, final ImListClipperCallback callback) {
        forEach(itemsCount, -1, callback);
    }

    /**
     * @param itemsCount Use -1 to ignore (you can call Begin later).
     *                   Use INT_MAX if you don't know how many items you have (in which case the cursor won't be advanced in the final step).
     * @param itemsHeight Use -1.0f to be calculated automatically on first step.
     *                    Otherwise pass in the distance between your items, typically GetTextLineHeightWithSpacing() or GetFrameHeightWithSpacing().
     * @param callback action to do in iterations
     */
    public static native void forEach(int itemsCount, int itemsHeight, ImListClipperCallback callback); /*
        ImGuiListClipper clipper(itemsCount);
        while (clipper.Step()) {
            for (int i = clipper.DisplayStart; i < clipper.DisplayEnd; i++) {
                Jni::CallImListClipperCallback(env, callback, i);
            }
        }
    */
}
