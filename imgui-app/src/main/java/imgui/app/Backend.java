package imgui.app;

public interface Backend {

    public default void preCreateWindow() {}
    public default void postCreateWindow(long windowHandle) {}
    public void init(Color clearColor);
    public void begin();
    public void end();
    public void destroy();
    public void resize(long windowHandle, int width, int height);
}
