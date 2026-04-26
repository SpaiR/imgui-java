package imgui.app;

/**
 * Abstract base for an application window. Subclasses bind a specific platform/renderer pair
 * (see {@link WindowGlfw}, {@link WindowSdl}) and forward lifecycle hooks back to the owning
 * {@link Application} via {@link #owner}.
 *
 * <p>Direct subclassing is supported but the supported integration path is to extend
 * {@link Application} and select a backend through {@link Configuration#setBackend(Backend)}.
 */
public abstract class Window {
    /**
     * Background color of the window.
     */
    protected final Color colorBg = new Color(.5f, .5f, .5f, 1);

    /**
     * Application owning this window. Set by {@link Application#launch(Application)} prior to
     * {@link #init(Configuration)}. Concrete windows invoke its lifecycle hooks
     * ({@link Application#initImGui(Configuration)}, {@link Application#preProcess()},
     * {@link Application#process()}, {@link Application#postProcess()},
     * {@link Application#disposeImGui()}).
     */
    protected Application owner;

    /**
     * Wires the owning application. Package-private; called by {@link Application#launch(Application)}.
     *
     * @param owner application driving this window
     */
    final void setOwner(final Application owner) {
        this.owner = owner;
    }

    /**
     * Initializes the window and its platform/renderer backends.
     *
     * @param config configuration object with basic window information
     */
    protected abstract void init(Configuration config);

    /**
     * Releases all resources owned by the window and its backends.
     */
    protected abstract void dispose();

    /**
     * Main application loop. Drives {@link #runFrame()} until the window is asked to close.
     */
    protected abstract void run();

    /**
     * Renders a single frame: pumps platform events, calls back into {@link #owner}'s
     * {@link Application#preProcess()} / {@link Application#process()} / {@link Application#postProcess()}
     * hooks, and submits the frame to the renderer.
     */
    protected abstract void runFrame();

    /**
     * @return {@link Color} instance, which represents background color for the window
     */
    public final Color getColorBg() {
        return colorBg;
    }
}
