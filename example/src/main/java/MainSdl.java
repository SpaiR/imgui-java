import imgui.ImGui;
import imgui.app.Application;
import imgui.app.Backend;
import imgui.app.Configuration;

/**
 * Smoke-test entry point for the SDL3 + SDL_GPU backend. Run with:
 * <pre>
 * ./gradlew :example:run -PmainClass=MainSdl
 * </pre>
 */
public class MainSdl extends Application {
    @Override
    protected void configure(final Configuration config) {
        config.setTitle("Example Application — SDL3 + SDL_GPU");
        config.setBackend(Backend.SDL);
    }

    @Override
    public void process() {
        ImGui.begin("SDL3 + SDL_GPU");
        ImGui.text("Hello from the SDL backend!");
        ImGui.end();
    }

    public static void main(final String[] args) {
        launch(new MainSdl());
    }
}
