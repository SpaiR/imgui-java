
package imgui.assertion;

/**
 * Callback for native IM_ASSERT calls
 */
public abstract class ImAssertCallback {
    public abstract void imAssert(String assertion);
}
