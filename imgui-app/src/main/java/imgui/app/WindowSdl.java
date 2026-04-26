package imgui.app;

import imgui.ImGui;
import imgui.sdl3.ImGuiImplSdl3;
import imgui.sdl3.ImGuiImplSdlGpu3;
import org.lwjgl.PointerBuffer;
import org.lwjgl.sdl.SDL_Event;
import org.lwjgl.sdl.SDL_GPUColorTargetInfo;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static org.lwjgl.sdl.SDLError.SDL_GetError;
import static org.lwjgl.sdl.SDLEvents.SDL_EVENT_QUIT;
import static org.lwjgl.sdl.SDLEvents.SDL_EVENT_WINDOW_CLOSE_REQUESTED;
import static org.lwjgl.sdl.SDLEvents.SDL_PollEvent;
import static org.lwjgl.sdl.SDLGPU.SDL_AcquireGPUCommandBuffer;
import static org.lwjgl.sdl.SDLGPU.SDL_BeginGPURenderPass;
import static org.lwjgl.sdl.SDLGPU.SDL_ClaimWindowForGPUDevice;
import static org.lwjgl.sdl.SDLGPU.SDL_CreateGPUDevice;
import static org.lwjgl.sdl.SDLGPU.SDL_DestroyGPUDevice;
import static org.lwjgl.sdl.SDLGPU.SDL_EndGPURenderPass;
import static org.lwjgl.sdl.SDLGPU.SDL_GPU_LOADOP_CLEAR;
import static org.lwjgl.sdl.SDLGPU.SDL_GPU_STOREOP_STORE;
import static org.lwjgl.sdl.SDLGPU.SDL_WaitAndAcquireGPUSwapchainTexture;
import static org.lwjgl.sdl.SDLGPU.SDL_GPU_SHADERFORMAT_DXBC;
import static org.lwjgl.sdl.SDLGPU.SDL_GPU_SHADERFORMAT_METALLIB;
import static org.lwjgl.sdl.SDLGPU.SDL_GPU_SHADERFORMAT_SPIRV;
import static org.lwjgl.sdl.SDLGPU.SDL_GetGPUSwapchainTextureFormat;
import static org.lwjgl.sdl.SDLGPU.SDL_ReleaseWindowFromGPUDevice;
import static org.lwjgl.sdl.SDLGPU.SDL_SubmitGPUCommandBuffer;
import static org.lwjgl.sdl.SDLInit.SDL_INIT_GAMEPAD;
import static org.lwjgl.sdl.SDLInit.SDL_INIT_VIDEO;
import static org.lwjgl.sdl.SDLInit.SDL_Init;
import static org.lwjgl.sdl.SDLInit.SDL_Quit;
import static org.lwjgl.sdl.SDLVideo.SDL_CreateWindow;
import static org.lwjgl.sdl.SDLVideo.SDL_DestroyWindow;
import static org.lwjgl.sdl.SDLVideo.SDL_MaximizeWindow;
import static org.lwjgl.sdl.SDLVideo.SDL_WINDOW_HIGH_PIXEL_DENSITY;
import static org.lwjgl.sdl.SDLVideo.SDL_WINDOW_RESIZABLE;

/**
 * SDL3 + SDL_GPU implementation of {@link Window}.
 *
 * <p>Pumps SDL events through {@link ImGuiImplSdl3#processEvent(long)} each frame, acquires an
 * {@code SDL_GPU} swapchain texture, and submits a clear-load render pass through
 * {@link ImGuiImplSdlGpu3#prepareDrawData(long, long)} +
 * {@link ImGuiImplSdlGpu3#renderDrawData(long, long, long)}. Multi-viewport is not supported
 * (skipped per task scope).
 */
public class WindowSdl extends Window {
    private final ImGuiImplSdl3 imGuiSdl3 = new ImGuiImplSdl3();
    private final ImGuiImplSdlGpu3 imGuiSdlGpu3 = new ImGuiImplSdlGpu3();

    private long handle;
    private long gpuDevice;
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

        // Create SDL_GPU device matching whatever shader format we ship.
        final int shaderFormats = SDL_GPU_SHADERFORMAT_SPIRV | SDL_GPU_SHADERFORMAT_DXBC | SDL_GPU_SHADERFORMAT_METALLIB;
        gpuDevice = SDL_CreateGPUDevice(shaderFormats, false, (java.nio.ByteBuffer) null);
        if (gpuDevice == 0L) {
            throw new RuntimeException("Failed to create the SDL_GPU device: " + SDL_GetError());
        }
        if (!SDL_ClaimWindowForGPUDevice(gpuDevice, handle)) {
            throw new RuntimeException("Failed to claim window for SDL_GPU device: " + SDL_GetError());
        }

        owner.initImGui(config);

        imGuiSdl3.initForSDLGPU(handle);

        final ImGuiImplSdlGpu3.InitInfo initInfo = new ImGuiImplSdlGpu3.InitInfo()
                .setDevice(gpuDevice)
                .setColorTargetFormat(SDL_GetGPUSwapchainTextureFormat(gpuDevice, handle));
        imGuiSdlGpu3.init(initInfo);
    }

    @Override
    protected void dispose() {
        imGuiSdlGpu3.shutdown();
        imGuiSdl3.shutdown();
        owner.disposeImGui();

        if (gpuDevice != 0L && handle != 0L) {
            SDL_ReleaseWindowFromGPUDevice(gpuDevice, handle);
        }
        if (gpuDevice != 0L) {
            SDL_DestroyGPUDevice(gpuDevice);
            gpuDevice = 0L;
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
        // Pump events
        try (SDL_Event event = SDL_Event.calloc()) {
            while (SDL_PollEvent(event)) {
                final int type = event.type();
                imGuiSdl3.processEvent(event.address());
                if (type == SDL_EVENT_QUIT || type == SDL_EVENT_WINDOW_CLOSE_REQUESTED) {
                    shouldClose = true;
                }
            }
        }

        // Begin a Dear ImGui frame
        imGuiSdlGpu3.newFrame();
        imGuiSdl3.newFrame();
        ImGui.newFrame();

        owner.preProcess();
        owner.process();
        owner.postProcess();

        ImGui.render();

        final long commandBuffer = SDL_AcquireGPUCommandBuffer(gpuDevice);
        if (commandBuffer != 0L) {
            imGuiSdlGpu3.prepareDrawData(ImGui.getDrawData().ptr, commandBuffer);

            try (MemoryStack stack = MemoryStack.stackPush()) {
                final PointerBuffer pSwapchain = stack.callocPointer(1);
                final IntBuffer pW = stack.mallocInt(1);
                final IntBuffer pH = stack.mallocInt(1);
                final boolean acquired = SDL_WaitAndAcquireGPUSwapchainTexture(commandBuffer, handle, pSwapchain, pW, pH);
                final long swapchainTexture = pSwapchain.get(0);
                if (acquired && swapchainTexture != 0L) {
                    final SDL_GPUColorTargetInfo.Buffer colorTargets = SDL_GPUColorTargetInfo.calloc(1, stack);
                    final SDL_GPUColorTargetInfo target = colorTargets.get(0);
                    target.texture(swapchainTexture);
                    target.load_op(SDL_GPU_LOADOP_CLEAR);
                    target.store_op(SDL_GPU_STOREOP_STORE);
                    target.clear_color().set(colorBg.getRed(), colorBg.getGreen(), colorBg.getBlue(), colorBg.getAlpha());

                    final long renderPass = SDL_BeginGPURenderPass(commandBuffer, colorTargets, null);
                    if (renderPass != 0L) {
                        imGuiSdlGpu3.renderDrawData(ImGui.getDrawData().ptr, commandBuffer, renderPass);
                        SDL_EndGPURenderPass(renderPass);
                    }
                }
            }

            SDL_SubmitGPUCommandBuffer(commandBuffer);
        }
    }

    /**
     * @return native {@code SDL_Window*} handle
     */
    public final long getHandle() {
        return handle;
    }

    /**
     * @return native {@code SDL_GPUDevice*} handle
     */
    public final long getGpuDevice() {
        return gpuDevice;
    }
}
