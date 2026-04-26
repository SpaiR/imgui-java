package imgui.app;

/**
 * Backend selection used by {@link Configuration} to choose which {@link Window} subclass {@link Application} instantiates.
 */
public enum Backend {
    /**
     * GLFW platform + OpenGL 3 renderer. Default.
     */
    GLFW,
    /**
     * SDL3 platform + SDL_GPU renderer.
     */
    SDL
}
