package imgui.app;

import imgui.ImGui;
import imgui.sdl3.ImGuiImplSdl3;
import imgui.sdl3.ImGuiImplSdlRenderer3;
import org.lwjgl.sdl.SDL_Event;

import static org.lwjgl.sdl.SDLError.SDL_GetError;
import static org.lwjgl.sdl.SDLEvents.SDL_EVENT_QUIT;
import static org.lwjgl.sdl.SDLEvents.SDL_EVENT_WINDOW_CLOSE_REQUESTED;
import static org.lwjgl.sdl.SDLEvents.SDL_PollEvent;
import static org.lwjgl.sdl.SDLInit.SDL_INIT_GAMEPAD;
import static org.lwjgl.sdl.SDLInit.SDL_INIT_VIDEO;
import static org.lwjgl.sdl.SDLInit.SDL_Init;
import static org.lwjgl.sdl.SDLInit.SDL_Quit;
import static org.lwjgl.sdl.SDLRender.SDL_DestroyRenderer;
import static org.lwjgl.sdl.SDLRender.SDL_RenderClear;
import static org.lwjgl.sdl.SDLRender.SDL_RenderPresent;
import static org.lwjgl.sdl.SDLRender.SDL_SetRenderDrawColorFloat;
import static org.lwjgl.sdl.SDLRender.SDL_SetRenderScale;
import static org.lwjgl.sdl.SDLRender.SDL_SetRenderVSync;
import static org.lwjgl.sdl.SDLRender.nSDL_CreateRenderer;
import static org.lwjgl.sdl.SDLVideo.SDL_CreateWindow;
import static org.lwjgl.sdl.SDLVideo.SDL_DestroyWindow;
import static org.lwjgl.sdl.SDLVideo.SDL_MaximizeWindow;
import static org.lwjgl.sdl.SDLVideo.SDL_WINDOW_HIGH_PIXEL_DENSITY;
import static org.lwjgl.sdl.SDLVideo.SDL_WINDOW_RESIZABLE;

/**
 * SDL3 + {@code SDL_Renderer} implementation of {@link Window}.
 *
 * <p>Pumps SDL events through {@link ImGuiImplSdl3#processEvent(long)} each frame and renders through
 * {@link ImGuiImplSdlRenderer3#renderDrawData(long, long)}. Multi-viewport is not supported.
 */
public class WindowSdlRenderer extends Window {
    private final ImGuiImplSdl3 imGuiSdl3 = new ImGuiImplSdl3();
    private final ImGuiImplSdlRenderer3 imGuiSdlRenderer3 = new ImGuiImplSdlRenderer3();

    private long handle;
    private long renderer;
    private boolean shouldClose;

    @Override
    protected void init(final Configuration config) {
        if (!SDL_Init(SDL_INIT_VIDEO | SDL_INIT_GAMEPAD)) {
            throw new IllegalStateException("Unable to initialize SDL3: " + SDL_GetError());
        }

        final long flags = SDL_WINDOW_HIGH_PIXEL_DENSITY | SDL_WINDOW_RESIZABLE;
        handle = SDL_CreateWindow(config.getTitle(), config.getWidth(), config.getHeight(), flags);
        if (handle == 0L) {
            throw new RuntimeException("Failed to create the SDL window: " + SDL_GetError());
        }
        if (config.isFullScreen()) {
            SDL_MaximizeWindow(handle);
        }

        // Null driver name lets SDL pick the best available renderer.
        renderer = nSDL_CreateRenderer(handle, 0L);
        if (renderer == 0L) {
            throw new RuntimeException("Failed to create the SDL renderer: " + SDL_GetError());
        }
        SDL_SetRenderVSync(renderer, 1);

        owner.initImGui(config);

        imGuiSdl3.initForSDLRenderer(handle, renderer);
        imGuiSdlRenderer3.init(renderer);
    }

    @Override
    protected void dispose() {
        imGuiSdlRenderer3.shutdown();
        imGuiSdl3.shutdown();
        owner.disposeImGui();

        if (renderer != 0L) {
            SDL_DestroyRenderer(renderer);
            renderer = 0L;
        }
        if (handle != 0L) {
            SDL_DestroyWindow(handle);
            handle = 0L;
        }
        SDL_Quit();
    }

    @Override
    protected void run() {
        while (!shouldClose) {
            runFrame();
        }
    }

    @Override
    protected void runFrame() {
        try (SDL_Event event = SDL_Event.calloc()) {
            while (SDL_PollEvent(event)) {
                final int type = event.type();
                imGuiSdl3.processEvent(event.address());
                if (type == SDL_EVENT_QUIT || type == SDL_EVENT_WINDOW_CLOSE_REQUESTED) {
                    shouldClose = true;
                }
            }
        }

        imGuiSdlRenderer3.newFrame();
        imGuiSdl3.newFrame();
        ImGui.newFrame();

        owner.preProcess();
        owner.process();
        owner.postProcess();

        ImGui.render();

        // ImGui works in window coordinates (points) while SDL_Renderer draws in output pixels.
        // Scale here so the geometry, clip rects and mouse coordinates all share the same space.
        SDL_SetRenderScale(renderer, ImGui.getIO().getDisplayFramebufferScaleX(), ImGui.getIO().getDisplayFramebufferScaleY());
        SDL_SetRenderDrawColorFloat(renderer, colorBg.getRed(), colorBg.getGreen(), colorBg.getBlue(), colorBg.getAlpha());
        SDL_RenderClear(renderer);
        imGuiSdlRenderer3.renderDrawData(ImGui.getDrawData());
        SDL_RenderPresent(renderer);
    }

    /**
     * @return native {@code SDL_Window*} handle
     */
    public final long getHandle() {
        return handle;
    }

    /**
     * @return native {@code SDL_Renderer*} handle
     */
    public final long getRenderer() {
        return renderer;
    }
}
