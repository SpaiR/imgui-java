package imgui.app;

/**
 * Application class from which ImGui applications extend.
 * Serves as an abstraction layer to hide all low-level details about window creation and rendering routine.
 *
 * <p><h3>Life-cycle</h3>
 * The entry point for ImGui applications is the Application class and {@link #launch(Application)} method.
 * It initializes application instance and starts the main application loop.
 *
 * <ol>
 *     <li>{@link #configure(Configuration)} It's called before window creation, so only basic application setups are expected.</li>
 *     <li>{@link #initWindow(Configuration)} Method creates application window.</li>
 *     <li>{@link #initImGui(Configuration)} Method initializes Dear ImGui context. Could be used to do Dear ImGui setup as well.</li>
 *     <li>{@link #preRun()} Method called once, before application loop.</li>
 *     <li>{@link #preProcess()} Method called every frame, before {@link #process()}.</li>
 *     <li>{@link #process()} Method is meant to be overridden with user application logic.</li>
 *     <li>{@link #postProcess()} Method called every frame, after {@link #process()}.</li>
 *     <li>{@link #postRun()} Method called once, after application loop.</li>
 *     <li>{@link #disposeImGui()} Destroys Dear ImGui context.</li>
 *     <li>{@link #disposeWindow()} Destroys application window.</li>
 * </ol>
 *
 * <p>As it could be seen, ImGui application differs from the classic one in the way of its life-cycle flow.
 * Instead of creating widgets and adding listeners to them we have an application loop where everything is handled right away.
 * Read more about Immediate GUI mode to understand that paradigm better.
 *
 * <p><h3>Example</h3>
 * The simplest application example could be done in the next way:
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
 * <p>As its said, {@link #process()} method is meant to be overridden. All your application logic should go there.
 *
 * <p><h3>Threading</h3>
 * Unlike other Java applications, ImGui is about "one thread for everything". You still can use multi-threading, but be careful.
 * For example, large list of computations could be separated between application ticks. {@link #process()} method is called constantly.
 * Use that wisely and remember that all GUI should be in the main thread.
 */
public abstract class Application extends Window {
    /**
     * Method called before window creation. Could be used to provide basic window information, like title name etc.
     *
     * @param config configuration object with basic window information
     */
    protected void configure(final Configuration config) {
    }

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
     * Entry point of any ImGui application. Use it to start the application loop.
     *
     * @param app application instance to run
     */
    public static void launch(final Application app) {
        initialize(app);
        app.preRun();
        app.run();
        app.postRun();
        app.dispose();
    }

    private static void initialize(final Application app) {
        final Configuration config = new Configuration();
        app.configure(config);
        app.init(config);
    }
}
