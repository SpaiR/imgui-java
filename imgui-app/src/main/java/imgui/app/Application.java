package imgui.app;

import imgui.ImGui;

/**
 * Application class from which ImGui applications extend.
 * Serves as an abstraction layer to hide all low-level details about window creation and rendering routine.
 *
 * <h2>Life-cycle</h2>
 * <p>The entry point for ImGui applications is the Application class and {@link #launch(Application)} method.
 * It builds a {@link Configuration}, lets the user customize it (including selecting a {@link Backend}), then
 * instantiates the matching {@link Window} subclass ({@link WindowGlfw} or {@link WindowSdl}) and drives the
 * application loop:
 *
 * <ol>
 *     <li>{@link #configure(Configuration)} It's called before window creation, so only basic application setups are expected.</li>
 *     <li>{@link Window#init(Configuration)} The window's {@code init} creates the OS window and triggers
 *         {@link #initImGui(Configuration)} to set up the Dear ImGui context.</li>
 *     <li>{@link #preRun()} Method called once, before application loop.</li>
 *     <li>{@link #preProcess()} Method called every frame, before {@link #process()}.</li>
 *     <li>{@link #process()} Method is meant to be overridden with user application logic.</li>
 *     <li>{@link #postProcess()} Method called every frame, after {@link #process()}.</li>
 *     <li>{@link #postRun()} Method called once, after application loop.</li>
 *     <li>{@link #disposeImGui()} Destroys Dear ImGui context.</li>
 *     <li>{@link Window#dispose()} The window's {@code dispose} releases platform/renderer resources.</li>
 * </ol>
 *
 * <p>As it could be seen, ImGui application differs from the classic one in the way of its life-cycle flow.
 * Instead of creating widgets and adding listeners to them we have an application loop where everything is handled right away.
 * Read more about Immediate GUI mode to understand that paradigm better.
 *
 * <h3>Example</h3>
 * <p>The simplest application example could be done in the next way:
 *
 * <pre>
 * import imgui.ImGui;
 * import imgui.app.Application;
 *
 * public class Main extends Application {
 *     {@code @Override}
 *     public void process() {
 *         ImGui.text("Hello, World!");
 *     }
 *
 *     public static void main(final String[] args) {
 *         launch(new Main());
 *     }
 * }
 * </pre>
 *
 * <p>
 * As its said, {@link #process()} method is meant to be overridden. All your application logic should go there.
 *
 * <h3>Threading</h3>
 * <p>Unlike other Java applications, ImGui is about "one thread for everything". You still can use multi-threading, but be careful.
 * For example, large list of computations could be separated between application ticks. {@link #process()} method is called constantly.
 * Use that wisely and remember that all GUI should be in the main thread.
 */
public abstract class Application {
    private Window window;

    /**
     * Method called before window creation. Could be used to provide basic window information, like title name etc.
     *
     * @param config configuration object with basic window information
     */
    protected void configure(final Configuration config) {
    }

    /**
     * Method to initialize Dear ImGui context. Could be overridden to do custom Dear ImGui setup before application start.
     *
     * @param config configuration object with basic window information
     */
    protected void initImGui(final Configuration config) {
        ImGui.createContext();
    }

    /**
     * Method called every frame, before calling {@link #process()} method.
     */
    protected void preProcess() {
    }

    /**
     * Method called every frame, after calling {@link #process()} method.
     */
    protected void postProcess() {
    }

    /**
     * Method to be overridden by user to provide main application logic.
     */
    public abstract void process();

    /**
     * Method called once, before application run loop.
     */
    protected void preRun() {
    }

    /**
     * Method called once, after application run loop.
     */
    protected void postRun() {
    }

    /**
     * Method to destroy Dear ImGui context.
     */
    protected void disposeImGui() {
        ImGui.destroyContext();
    }

    /**
     * @return the {@link Window} backing this application
     */
    public final Window getWindow() {
        return window;
    }

    /**
     * @return {@link Color} instance, which represents background color for the window
     */
    public final Color getColorBg() {
        return window.getColorBg();
    }

    /**
     * Entry point of any ImGui application. Use it to start the application loop.
     *
     * @param app application instance to run
     */
    public static void launch(final Application app) {
        final Configuration config = new Configuration();
        app.configure(config);
        app.window = (config.getBackend() == Backend.SDL) ? new WindowSdl() : new WindowGlfw();
        app.window.setOwner(app);
        app.window.init(config);
        app.preRun();
        app.window.run();
        app.postRun();
        app.window.dispose();
    }
}
