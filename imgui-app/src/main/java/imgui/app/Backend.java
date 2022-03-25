package imgui.app;

public interface Backend {
    default void preCreateWindow() {
    }

    default void postCreateWindow(long windowHandle) {
    }

    void init(Color clearColor);

    void begin();

    void end();

    void destroy();

    void resize(long windowHandle, int width, int height);
}
