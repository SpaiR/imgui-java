
package imgui.assertion;

/**
 * Callback for native IM_ASSERT calls.
 */
public abstract class ImAssertCallback {
    /**
     * Called from native code to bring the callback into java side.
     * Will force the application to terminate with exit code 1 after forwarding the callback, this is required, otherwise
     * if execution is returned to the native layer, a EXCEPTION_ACCESS_VIOLATION will be thrown.
     * If the callback throws an exception, it will be caught and printed along with a warning to prevent execution
     * from returning to the native layer.
     *
     * @param assertion The assertion string
     * @param line      The line number of the assertion in the source file
     * @param file      The source file where the assertion occurred
     */
    public void imAssert(final String assertion, final int line, final String file) {
        try {
            imAssertCallback(assertion, line, file);
        } catch (Exception ex) {
            System.err.println("WARNING: Exception thrown in Dear ImGui Assertion Callback!");
            System.err.println("Dear ImGui Assertion Failed: " + assertion);
            System.err.println("Assertion Located At: " + file + ":" + line);
            ex.printStackTrace();
        }
        System.exit(1);
    }

    /**
     * The assertion callback from ImGui.
     * Do not throw an exception within this callback, to prevent
     * execution from returned to the native layer and cause a EXCEPTION_ACCESS_VIOLATION,
     * the callback will catch any exceptions and print a warning.
     * <p>
     * There is no way to catch the assertion and continue execution.
     * You may however call <code>System.exit(code)</code> with your own exit code.
     *
     * @param assertion The assertion string
     * @param line      The line number of the assertion in the source file
     * @param file      The source file where the assertion occurred
     */
    public abstract void imAssertCallback(String assertion, int line, String file);
}
