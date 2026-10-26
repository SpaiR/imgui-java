package imgui.sdl3;

import imgui.ImDrawData;
import imgui.ImFontAtlas;
import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.ImVec4;
import imgui.flag.ImGuiBackendFlags;
import imgui.type.ImInt;
import org.lwjgl.sdl.SDL_FColor;
import org.lwjgl.sdl.SDL_Rect;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import static org.lwjgl.sdl.SDLBlendMode.SDL_BLENDMODE_BLEND;
import static org.lwjgl.sdl.SDLPixels.SDL_PIXELFORMAT_RGBA32;
import static org.lwjgl.sdl.SDLRender.SDL_GetRenderClipRect;
import static org.lwjgl.sdl.SDLRender.SDL_GetRenderScale;
import static org.lwjgl.sdl.SDLRender.SDL_GetRenderViewport;
import static org.lwjgl.sdl.SDLRender.SDL_RenderClipEnabled;
import static org.lwjgl.sdl.SDLRender.SDL_RenderViewportSet;
import static org.lwjgl.sdl.SDLRender.SDL_SetRenderClipRect;
import static org.lwjgl.sdl.SDLRender.SDL_SetRenderViewport;
import static org.lwjgl.sdl.SDLRender.SDL_TEXTUREACCESS_STATIC;
import static org.lwjgl.sdl.SDLRender.nSDL_CreateTexture;
import static org.lwjgl.sdl.SDLRender.nSDL_DestroyTexture;
import static org.lwjgl.sdl.SDLRender.nSDL_RenderGeometryRaw;
import static org.lwjgl.sdl.SDLRender.nSDL_SetTextureBlendMode;
import static org.lwjgl.sdl.SDLRender.nSDL_SetTextureScaleMode;
import static org.lwjgl.sdl.SDLRender.nSDL_UpdateTexture;
import static org.lwjgl.sdl.SDLSurface.SDL_SCALEMODE_LINEAR;

/**
 * Java port of upstream {@code imgui_impl_sdlrenderer3.cpp}: Dear ImGui renderer backend for {@code SDL_Renderer}.
 *
 * <p>The backend uses {@code SDL_RenderGeometryRaw} and supports user textures, large meshes through
 * {@link ImGuiBackendFlags#RendererHasVtxOffset} and draw-call clipping. Multi-viewport is not supported.
 *
 * <p>The binding does not expose {@code ImTextureData} yet, so the legacy {@link ImFontAtlas#setTexID(long)} flow
 * is used for the font atlas: {@link #newFrame()} creates the texture lazily and {@link #destroyDeviceObjects()}
 * destroys it. Dynamic atlas updates are therefore not honored.
 *
 * <p>As in upstream, when the renderer output is scaled (HiDPI), the caller must set the renderer scale to
 * {@code ImGuiIO#DisplayFramebufferScale} before rendering. {@code imgui.app.WindowSdlRenderer} does this
 * automatically; custom integrations should call {@code SDL_SetRenderScale} (or set a logical presentation)
 * accordingly.
 *
 * <p>User callbacks registered through {@code ImDrawList#AddCallback} are not exposed by the binding and cannot be
 * dispatched by this port.
 */
public class ImGuiImplSdlRenderer3 {
    private static final int VERTEX_COLOR_OFFSET = 16;

    private long renderer;
    private long fontTexture;

    // CPU staging buffers. ImDrawData#getCmdListVtxBufferData and #getCmdListIdxBufferData share one static buffer,
    // so both are copied out before the draw commands are issued.
    private ByteBuffer vertexBuffer;
    private ByteBuffer indexBuffer;
    private FloatBuffer colorBuffer;

    /**
     * Initializes the renderer backend. Mirrors {@code ImGui_ImplSDLRenderer3_Init}.
     *
     * @param sdlRenderer native {@code SDL_Renderer*} pointer
     * @return {@code true} on success
     */
    public final boolean init(final long sdlRenderer) {
        if (sdlRenderer == 0L) {
            throw new IllegalArgumentException("ImGuiImplSdlRenderer3.init: SDL_Renderer handle must not be null");
        }
        final ImGuiIO io = ImGui.getIO();
        io.setBackendRendererName("imgui_impl_sdlrenderer3 (lwjgl)");
        io.addBackendFlags(ImGuiBackendFlags.RendererHasVtxOffset);
        renderer = sdlRenderer;
        return true;
    }

    /**
     * Mirrors {@code ImGui_ImplSDLRenderer3_Shutdown}.
     */
    public final void shutdown() {
        destroyDeviceObjects();
        final ImGuiIO io = ImGui.getIO();
        io.setBackendRendererName(null);
        io.removeBackendFlags(ImGuiBackendFlags.RendererHasVtxOffset);
        renderer = 0L;

        if (vertexBuffer != null) {
            MemoryUtil.memFree(vertexBuffer);
            vertexBuffer = null;
        }
        if (indexBuffer != null) {
            MemoryUtil.memFree(indexBuffer);
            indexBuffer = null;
        }
        if (colorBuffer != null) {
            MemoryUtil.memFree(colorBuffer);
            colorBuffer = null;
        }
    }

    /**
     * Mirrors {@code ImGui_ImplSDLRenderer3_NewFrame}. Creates the legacy font texture when needed.
     */
    public final void newFrame() {
        if (renderer == 0L) {
            return;
        }
        if (fontTexture == 0L) {
            createFontsTexture();
        }
    }

    /**
     * Builds the font atlas texture and stores its handle on the atlas via {@link ImFontAtlas#setTexID(long)}.
     *
     * <p>This method exists because the binding uses the legacy texture flow instead of upstream's
     * {@code ImTextureData}-driven updates.
     */
    public final void createFontsTexture() {
        if (renderer == 0L) {
            return;
        }
        final ImFontAtlas atlas = ImGui.getIO().getFonts();
        final ImInt width = new ImInt();
        final ImInt height = new ImInt();
        final ByteBuffer pixels = atlas.getTexDataAsRGBA32(width, height);
        final int w = width.get();
        final int h = height.get();

        final long texture = nSDL_CreateTexture(renderer, SDL_PIXELFORMAT_RGBA32, SDL_TEXTUREACCESS_STATIC, w, h);
        if (texture == 0L) {
            throw new IllegalStateException("ImGuiImplSdlRenderer3: SDL_CreateTexture failed for the font atlas");
        }
        final ByteBuffer src = pixels.duplicate();
        src.position(0);
        nSDL_UpdateTexture(texture, 0L, MemoryUtil.memAddress(src), w * 4);
        nSDL_SetTextureBlendMode(texture, SDL_BLENDMODE_BLEND);
        nSDL_SetTextureScaleMode(texture, SDL_SCALEMODE_LINEAR);

        fontTexture = texture;
        atlas.setTexID(texture);
    }

    /**
     * Mirrors {@code ImGui_ImplSDLRenderer3_CreateDeviceObjects}. Upstream creates no persistent device objects here;
     * the font texture is created lazily by {@link #newFrame()}.
     */
    public final void createDeviceObjects() {
        // Intentionally empty: upstream has no persistent device objects for SDL_Renderer.
    }

    /**
     * Mirrors {@code ImGui_ImplSDLRenderer3_DestroyDeviceObjects}.
     */
    public final void destroyDeviceObjects() {
        if (fontTexture != 0L) {
            nSDL_DestroyTexture(fontTexture);
            fontTexture = 0L;
            ImGui.getIO().getFonts().setTexID(0L);
        }
    }

    /**
     * Renders the given draw data using the renderer passed to {@link #init(long)}.
     *
     * @param drawData draw data to render
     */
    public final void renderDrawData(final ImDrawData drawData) {
        renderDrawData(drawData, renderer);
    }

    /**
     * Renders the given draw data using the given renderer. Mirrors {@code ImGui_ImplSDLRenderer3_RenderDrawData}.
     *
     * @param drawDataPtr native {@code ImDrawData*} pointer
     * @param sdlRenderer native {@code SDL_Renderer*} pointer
     */
    public final void renderDrawData(final long drawDataPtr, final long sdlRenderer) {
        if (drawDataPtr == 0L) {
            return;
        }
        renderDrawData(new ImDrawData(drawDataPtr), sdlRenderer);
    }

    /**
     * Renders the given draw data using the given renderer. Mirrors {@code ImGui_ImplSDLRenderer3_RenderDrawData}.
     *
     * @param drawData draw data to render
     * @param sdlRenderer native {@code SDL_Renderer*} pointer
     */
    public final void renderDrawData(final ImDrawData drawData, final long sdlRenderer) {
        if (drawData == null || sdlRenderer == 0L || drawData.getCmdListsCount() <= 0) {
            return;
        }
        if (fontTexture == 0L) {
            createFontsTexture();
        }

        final int vertexStride = ImDrawData.sizeOfImDrawVert();
        final int indexStride = ImDrawData.sizeOfImDrawIdx();
        final ImVec4 clipRect = new ImVec4();

        try (MemoryStack stack = MemoryStack.stackPush()) {
            final FloatBuffer renderScaleXBuffer = stack.mallocFloat(1);
            final FloatBuffer renderScaleYBuffer = stack.mallocFloat(1);
            SDL_GetRenderScale(sdlRenderer, renderScaleXBuffer, renderScaleYBuffer);

            // If the user already set a scale through SDL_RenderSetScale, SDL applies it to the geometry itself.
            final float renderScaleX = renderScaleXBuffer.get(0) == 1.0f ? drawData.getFramebufferScaleX() : 1.0f;
            final float renderScaleY = renderScaleYBuffer.get(0) == 1.0f ? drawData.getFramebufferScaleY() : 1.0f;

            final int fbWidth = (int) (drawData.getDisplaySizeX() * renderScaleX);
            final int fbHeight = (int) (drawData.getDisplaySizeY() * renderScaleY);
            if (fbWidth == 0 || fbHeight == 0) {
                return;
            }

            final boolean viewportEnabled = SDL_RenderViewportSet(sdlRenderer);
            final boolean clipEnabled = SDL_RenderClipEnabled(sdlRenderer);
            final SDL_Rect oldViewport = SDL_Rect.calloc(stack);
            final SDL_Rect oldClipRect = SDL_Rect.calloc(stack);
            SDL_GetRenderViewport(sdlRenderer, oldViewport);
            SDL_GetRenderClipRect(sdlRenderer, oldClipRect);

            setupRenderState(sdlRenderer);

            final float clipOffX = drawData.getDisplayPosX();
            final float clipOffY = drawData.getDisplayPosY();
            final SDL_Rect scissorRect = SDL_Rect.calloc(stack);

            for (int n = 0; n < drawData.getCmdListsCount(); n++) {
                // Copy the shared static buffers one at a time: fetching both without copying would leave the
                // vertex buffer pointing at index data.
                vertexBuffer = copyTo(vertexBuffer, drawData.getCmdListVtxBufferData(n));
                indexBuffer = copyTo(indexBuffer, drawData.getCmdListIdxBufferData(n));

                final int vertexCount = drawData.getCmdListVtxBufferSize(n);
                final long vertexAddress = MemoryUtil.memAddress(vertexBuffer);
                final long indexAddress = MemoryUtil.memAddress(indexBuffer);
                final int cmdCount = drawData.getCmdListCmdBufferSize(n);

                for (int cmd = 0; cmd < cmdCount; cmd++) {
                    drawData.getCmdListCmdBufferClipRect(clipRect, n, cmd);

                    float clipMinX = (clipRect.x - clipOffX) * renderScaleX;
                    float clipMinY = (clipRect.y - clipOffY) * renderScaleY;
                    float clipMaxX = (clipRect.z - clipOffX) * renderScaleX;
                    float clipMaxY = (clipRect.w - clipOffY) * renderScaleY;
                    clipMinX = Math.max(0.0f, clipMinX);
                    clipMinY = Math.max(0.0f, clipMinY);
                    clipMaxX = Math.min(fbWidth, clipMaxX);
                    clipMaxY = Math.min(fbHeight, clipMaxY);
                    if (clipMaxX <= clipMinX || clipMaxY <= clipMinY) {
                        continue;
                    }

                    scissorRect.x((int) clipMinX).y((int) clipMinY)
                            .w((int) (clipMaxX - clipMinX)).h((int) (clipMaxY - clipMinY));
                    SDL_SetRenderClipRect(sdlRenderer, scissorRect);

                    final int elementCount = drawData.getCmdListCmdBufferElemCount(n, cmd);
                    final int vertexOffset = drawData.getCmdListCmdBufferVtxOffset(n, cmd);
                    final int indexOffset = drawData.getCmdListCmdBufferIdxOffset(n, cmd);
                    final int numVertices = vertexCount - vertexOffset;
                    if (elementCount == 0 || numVertices <= 0) {
                        continue;
                    }

                    final FloatBuffer colors = ensureColorBuffer(numVertices * 4);
                    colors.clear();
                    for (int i = 0; i < numVertices; i++) {
                        final int offset = (vertexOffset + i) * vertexStride + VERTEX_COLOR_OFFSET;
                        colors.put((vertexBuffer.get(offset) & 0xFF) / 255.0f);
                        colors.put((vertexBuffer.get(offset + 1) & 0xFF) / 255.0f);
                        colors.put((vertexBuffer.get(offset + 2) & 0xFF) / 255.0f);
                        colors.put((vertexBuffer.get(offset + 3) & 0xFF) / 255.0f);
                    }
                    colors.flip();

                    final long textureId = drawData.getCmdListCmdBufferTextureId(n, cmd);
                    final long xyAddress = vertexAddress + (long) vertexOffset * vertexStride;
                    nSDL_RenderGeometryRaw(
                            sdlRenderer,
                            textureId,
                            xyAddress, vertexStride,
                            MemoryUtil.memAddress(colors), SDL_FColor.SIZEOF,
                            xyAddress + 8, vertexStride,
                            numVertices,
                            indexAddress + (long) indexOffset * indexStride, elementCount, indexStride);
                }
            }

            SDL_SetRenderViewport(sdlRenderer, viewportEnabled ? oldViewport : null);
            SDL_SetRenderClipRect(sdlRenderer, clipEnabled ? oldClipRect : null);
        }
    }

    private static void setupRenderState(final long sdlRenderer) {
        // Clear out any viewports and clip rects set by the user.
        SDL_SetRenderViewport(sdlRenderer, null);
        SDL_SetRenderClipRect(sdlRenderer, null);
    }

    private static ByteBuffer copyTo(final ByteBuffer destination, final ByteBuffer source) {
        final int size = source.remaining();
        ByteBuffer result = destination;
        if (result == null || result.capacity() < size) {
            if (result != null) {
                MemoryUtil.memFree(result);
            }
            result = MemoryUtil.memAlloc(size);
        }
        result.clear();
        result.put(source.duplicate());
        result.flip();
        return result;
    }

    private FloatBuffer ensureColorBuffer(final int floats) {
        if (colorBuffer == null || colorBuffer.capacity() < floats) {
            if (colorBuffer != null) {
                MemoryUtil.memFree(colorBuffer);
            }
            colorBuffer = MemoryUtil.memAllocFloat(floats);
        }
        return colorBuffer;
    }
}
