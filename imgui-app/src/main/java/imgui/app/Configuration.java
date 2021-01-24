package imgui.app;

import lombok.Data;

/**
 * Data class to provide basic information about the window. Like, the title name etc.
 */
@Data
public class Configuration {
    /**
     * Application title.
     */
    private String title = "ImGui Java Application";
    /**
     * Application window width.
     */
    private int width = 1280;
    /**
     * Application window height.
     */
    private int height = 768;
    /**
     * When true, application will be maximized by default.
     */
    private boolean fullScreen = false;
}
