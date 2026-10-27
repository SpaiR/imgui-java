import imgui.ImGui;
import imgui.app.Application;
import imgui.app.Backend;
import imgui.app.Configuration;

/**
 * Smoke-test entry point for the Vulkan backend. Run with:
 * <pre>
 * ./gradlew :example:run -PmainClass=ExampleVulkan
 * </pre>
 */
public class ExampleVulkan extends Application {
    @Override
    protected void configure(final Configuration config) {
        config.setTitle("Example Application — Vulkan");
        config.setBackend(Backend.VULKAN);
    }

    @Override
    public void process() {
        ImGui.begin("Vulkan");
        ImGui.text("Hello from the Vulkan backend!");
        ImGui.end();
    }

    public static void main(final String[] args) {
        launch(new ExampleVulkan());
    }
}
