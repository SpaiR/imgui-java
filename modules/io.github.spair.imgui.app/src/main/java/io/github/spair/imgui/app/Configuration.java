package io.github.spair.imgui.app;

/**
 * Data class to provide basic information about the window. Like, the title name etc.
 */
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

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(final int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(final int height) {
        this.height = height;
    }

    public boolean isFullScreen() {
        return fullScreen;
    }

    public void setFullScreen(final boolean fullScreen) {
        this.fullScreen = fullScreen;
    }
}
