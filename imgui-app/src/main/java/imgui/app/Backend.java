package imgui.app;

public interface Backend {
    public void init(long windowHandle, Color clearColor);
    public void begin();
    public void end();
    public void destroy();
}
