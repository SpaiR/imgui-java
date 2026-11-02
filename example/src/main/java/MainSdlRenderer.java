import imgui.ImGui;
import imgui.app.Application;
import imgui.app.Backend;
import imgui.app.Configuration;

/**
 * Smoke-test entry point for the SDL3 + SDL_Renderer backend. Run with:
 * <pre>
 * ./gradlew :example:run -PmainClass=MainSdlRenderer
 * </pre>
 */
public class MainSdlRenderer extends Application {
    @Override
    protected void configure(final Configuration config) {
        config.setTitle("Example Application — SDL3 + SDL_Renderer");
        config.setBackend(Backend.SDL_RENDERER);
    }

    @Override
    public void process() {
        ImGui.begin("SDL3 + SDL_Renderer");
        ImGui.text("Hello from the SDL_Renderer backend!");
        ImGui.end();
    }

    public static void main(final String[] args) {
        launch(new MainSdlRenderer());
    }
}
