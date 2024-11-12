package imgui.gl3;

import imgui.ImDrawData;
import imgui.ImFontAtlas;
import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.ImGuiViewport;
import imgui.ImVec4;
import imgui.callback.ImPlatformFuncViewport;
import imgui.flag.ImGuiBackendFlags;
import imgui.flag.ImGuiConfigFlags;
import imgui.flag.ImGuiViewportFlags;
import imgui.type.ImInt;

import java.nio.ByteBuffer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.lwjgl.opengl.GL20.glDeleteShader;
import static org.lwjgl.opengl.GL20.glDetachShader;
import static org.lwjgl.opengl.GL20.glGetAttribLocation;
import static org.lwjgl.opengl.GL20.glGetProgramiv;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL32.GL_ACTIVE_TEXTURE;
import static org.lwjgl.opengl.GL32.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL32.GL_ARRAY_BUFFER_BINDING;
import static org.lwjgl.opengl.GL32.GL_BLEND;
import static org.lwjgl.opengl.GL32.GL_BLEND_DST_ALPHA;
import static org.lwjgl.opengl.GL32.GL_BLEND_DST_RGB;
import static org.lwjgl.opengl.GL32.GL_BLEND_EQUATION_ALPHA;
import static org.lwjgl.opengl.GL32.GL_BLEND_EQUATION_RGB;
import static org.lwjgl.opengl.GL32.GL_BLEND_SRC_ALPHA;
import static org.lwjgl.opengl.GL32.GL_BLEND_SRC_RGB;
import static org.lwjgl.opengl.GL32.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL32.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL32.GL_CULL_FACE;
import static org.lwjgl.opengl.GL32.GL_CURRENT_PROGRAM;
import static org.lwjgl.opengl.GL32.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL32.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL32.GL_FALSE;
import static org.lwjgl.opengl.GL32.GL_FILL;
import static org.lwjgl.opengl.GL32.GL_FLOAT;
import static org.lwjgl.opengl.GL32.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL32.GL_FRONT_AND_BACK;
import static org.lwjgl.opengl.GL32.GL_FUNC_ADD;
import static org.lwjgl.opengl.GL32.GL_INFO_LOG_LENGTH;
import static org.lwjgl.opengl.GL32.GL_LINEAR;
import static org.lwjgl.opengl.GL32.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL32.GL_MAJOR_VERSION;
import static org.lwjgl.opengl.GL32.GL_MINOR_VERSION;
import static org.lwjgl.opengl.GL32.GL_ONE;
import static org.lwjgl.opengl.GL32.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL32.GL_POLYGON_MODE;
import static org.lwjgl.opengl.GL32.GL_PRIMITIVE_RESTART;
import static org.lwjgl.opengl.GL32.GL_RGBA;
import static org.lwjgl.opengl.GL32.GL_SCISSOR_BOX;
import static org.lwjgl.opengl.GL32.GL_SCISSOR_TEST;
import static org.lwjgl.opengl.GL32.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL32.GL_STENCIL_TEST;
import static org.lwjgl.opengl.GL32.GL_STREAM_DRAW;
import static org.lwjgl.opengl.GL32.GL_TEXTURE0;
import static org.lwjgl.opengl.GL32.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL32.GL_TEXTURE_BINDING_2D;
import static org.lwjgl.opengl.GL32.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL32.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL32.GL_TRIANGLES;
import static org.lwjgl.opengl.GL32.GL_TRUE;
import static org.lwjgl.opengl.GL32.GL_UNPACK_ALIGNMENT;
import static org.lwjgl.opengl.GL32.GL_UNPACK_SKIP_PIXELS;
import static org.lwjgl.opengl.GL32.GL_UNPACK_SKIP_ROWS;
import static org.lwjgl.opengl.GL32.GL_UNPACK_ROW_LENGTH;
import static org.lwjgl.opengl.GL32.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL32.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL32.GL_UNSIGNED_SHORT;
import static org.lwjgl.opengl.GL32.GL_UPPER_LEFT;
import static org.lwjgl.opengl.GL32.GL_VERTEX_ARRAY_BINDING;
import static org.lwjgl.opengl.GL32.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL32.GL_VIEWPORT;
import static org.lwjgl.opengl.GL32.glActiveTexture;
import static org.lwjgl.opengl.GL32.glAttachShader;
import static org.lwjgl.opengl.GL32.glBindBuffer;
import static org.lwjgl.opengl.GL32.glBindTexture;
import static org.lwjgl.opengl.GL32.glBindVertexArray;
import static org.lwjgl.opengl.GL32.glBlendEquation;
import static org.lwjgl.opengl.GL32.glBlendEquationSeparate;
import static org.lwjgl.opengl.GL32.glBlendFuncSeparate;
import static org.lwjgl.opengl.GL32.glBufferData;
import static org.lwjgl.opengl.GL32.glClear;
import static org.lwjgl.opengl.GL32.glClearColor;
import static org.lwjgl.opengl.GL32.glCompileShader;
import static org.lwjgl.opengl.GL32.glCreateProgram;
import static org.lwjgl.opengl.GL32.glCreateShader;
import static org.lwjgl.opengl.GL32.glDeleteBuffers;
import static org.lwjgl.opengl.GL32.glDeleteProgram;
import static org.lwjgl.opengl.GL32.glDeleteTextures;
import static org.lwjgl.opengl.GL32.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL32.glDisable;
import static org.lwjgl.opengl.GL32.glDrawElements;
import static org.lwjgl.opengl.GL32.glDrawElementsBaseVertex;
import static org.lwjgl.opengl.GL32.glEnable;
import static org.lwjgl.opengl.GL32.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL32.glGenBuffers;
import static org.lwjgl.opengl.GL32.glGenTextures;
import static org.lwjgl.opengl.GL32.glGenVertexArrays;
import static org.lwjgl.opengl.GL32.glGetIntegerv;
import static org.lwjgl.opengl.GL32.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL32.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL32.glGetShaderiv;
import static org.lwjgl.opengl.GL32.glIsEnabled;
import static org.lwjgl.opengl.GL32.glLinkProgram;
import static org.lwjgl.opengl.GL32.glPixelStorei;
import static org.lwjgl.opengl.GL32.glPolygonMode;
import static org.lwjgl.opengl.GL32.glScissor;
import static org.lwjgl.opengl.GL32.glShaderSource;
import static org.lwjgl.opengl.GL32.glTexImage2D;
import static org.lwjgl.opengl.GL32.glTexParameteri;
import static org.lwjgl.opengl.GL32.glUniform1i;
import static org.lwjgl.opengl.GL32.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL32.glUseProgram;
import static org.lwjgl.opengl.GL32.glVertexAttribPointer;
import static org.lwjgl.opengl.GL32.glViewport;
import static org.lwjgl.opengl.GL33.GL_SAMPLER_BINDING;
import static org.lwjgl.opengl.GL33.glBindSampler;
import static org.lwjgl.opengl.GL45.GL_CLIP_ORIGIN;

/**
 * This class is a straightforward port of the
 * <a href="https://raw.githubusercontent.com/ocornut/imgui/1ee252772ae9c0a971d06257bb5c89f628fa696a/backends/imgui_impl_opengl3.cpp">imgui_impl_opengl3.cpp</a>.
 * <p>
 * It does support a backup and restoring of the GL state in the same way the original Dear ImGui code does.
 * Some of the very specific OpenGL variables may be ignored here,
 * yet you can copy-paste this class in your codebase and modify the rendering routine in the way you'd like.
 * <p>
 * This implementation has an ability to use a GLSL version provided during the initialization.
 * Please read the documentation for the {@link #init(String)}.
 */
@SuppressWarnings({"checkstyle:DesignForExtension", "checkstyle:NeedBraces", "checkstyle:LocalVariableName", "checkstyle:FinalLocalVariable", "checkstyle:ParameterName", "checkstyle:EmptyBlock", "checkstyle:AvoidNestedBlocks"})
public class ImGuiImplGl3 {
    protected static final String OS = System.getProperty("os.name", "generic").toLowerCase();
    protected static final boolean IS_APPLE = OS.contains("mac") || OS.contains("darwin");

    /**
     * Data class to store implementation specific fields.
     * Same as {@code ImGui_ImplOpenGL3_Data}.
     */
    protected static class Data {
        protected int glVersion = 0; // Extracted at runtime using GL_MAJOR_VERSION, GL_MINOR_VERSION queries (e.g. 320 for GL 3.2)
        protected String glslVersion = "";
        protected int fontTexture = 0;
        protected int shaderHandle = 0;
        protected int attribLocationTex = 0; // Uniforms location
        protected int attribLocationProjMtx = 0;
        protected int attribLocationVtxPos = 0; // Vertex attributes location
        protected int attribLocationVtxUV = 0;
        protected int attribLocationVtxColor = 0;
        protected int vboHandle = 0;
        protected int elementsHandle = 0;
        // protected int vertexBufferSize;
        // protected int indexBufferSize;
        protected boolean hasClipOrigin;
    }

    /**
     * Internal class to store containers for frequently used arrays.
     * This class helps minimize the number of object allocations on the JVM side,
     * thereby improving performance and reducing garbage collection overhead.
     */
    private static final class Properties {
        private final ImVec4 clipRect = new ImVec4();
        private final float[] orthoProjMatrix = new float[4 * 4];
        private final int[] lastActiveTexture = new int[1];
        private final int[] lastProgram = new int[1];
        private final int[] lastTexture = new int[1];
        private final int[] lastSampler = new int[1];
        private final int[] lastArrayBuffer = new int[1];
        private final int[] lastVertexArrayObject = new int[1];
        private final int[] lastPolygonMode = new int[2];
        private final int[] lastViewport = new int[4];
        private final int[] lastScissorBox = new int[4];
        private final int[] lastBlendSrcRgb = new int[1];
        private final int[] lastBlendDstRgb = new int[1];
        private final int[] lastBlendSrcAlpha = new int[1];
        private final int[] lastBlendDstAlpha = new int[1];
        private final int[] lastBlendEquationRgb = new int[1];
        private final int[] lastBlendEquationAlpha = new int[1];
        private boolean lastEnableBlend = false;
        private boolean lastEnableCullFace = false;
        private boolean lastEnableDepthTest = false;
        private boolean lastEnableStencilTest = false;
        private boolean lastEnableScissorTest = false;
        private boolean lastEnablePrimitiveRestart = false;
    }

    protected Data data = null;
    private final Properties props = new Properties();

    protected Data newData() {
        return new Data();
    }

    /**
     * Method to do an initialization of the {@link ImGuiImplGl3} state.
     * It SHOULD be called before calling of the {@link ImGuiImplGl3#renderDrawData(ImDrawData)} method.
     * <p>
     * Unlike in the {@link #init(String)} method, here the glslVersion argument is omitted.
     * Thus, a "#version 130" string will be used instead.
     *
     * @return true when initialized
     */
    public boolean init() {
        return init(null);
    }

    /**
     * Method to do an initialization of the {@link ImGuiImplGl3} state.
     * It SHOULD be called before calling of the {@link ImGuiImplGl3#renderDrawData(ImDrawData)} method.
     * <p>
     * Method takes an argument, which should be a valid GLSL string with the version to use.
     * <pre>
     * ----------------------------------------
     * OpenGL    GLSL      GLSL
     * version   version   string
     * ---------------------------------------
     *  2.0       110       "#version 110"
     *  2.1       120       "#version 120"
     *  3.0       130       "#version 130"
     *  3.1       140       "#version 140"
     *  3.2       150       "#version 150"
     *  3.3       330       "#version 330 core"
     *  4.0       400       "#version 400 core"
     *  4.1       410       "#version 410 core"
     *  4.2       420       "#version 410 core"
     *  4.3       430       "#version 430 core"
     *  ES 3.0    300       "#version 300 es"   = WebGL 2.0
     * ---------------------------------------
     * </pre>
     * <p>
     * If the argument is null, then a "#version 130" (150 for APPLE) string will be used by default.
     *
     * @param glslVersion string with the version of the GLSL
     * @return true when initialized
     */
    public boolean init(final String glslVersion) {
        data = newData();

        final ImGuiIO io = ImGui.getIO();
        io.setBackendRendererName("imgui-java_impl_opengl3");

        {
            final int[] major = new int[1];
            final int[] minor = new int[1];
            glGetIntegerv(GL_MAJOR_VERSION, major);
            glGetIntegerv(GL_MINOR_VERSION, minor);
            data.glVersion = major[0] * 100 + minor[0] * 10;
        }

        // We can honor the ImDrawCmd::VtxOffset field, allowing for large meshes.
        if (data.glVersion >= 320) {
            io.addBackendFlags(ImGuiBackendFlags.RendererHasVtxOffset);
        }

        // We can create multi-viewports on the Renderer side (optional)
        io.addBackendFlags(ImGuiBackendFlags.RendererHasViewports);

        if (glslVersion == null) {
            if (IS_APPLE) {
                data.glslVersion = "#version 150";
            } else {
                data.glslVersion = "#version 130";
            }
        } else {
            data.glslVersion = glslVersion;
        }

        // Make an arbitrary GL call (we don't actually need the result)
        // IF YOU GET A CRASH HERE: it probably means the OpenGL function loader didn't do its job. Let us know!
        {
            final int[] currentTexture = new int[1];
            glGetIntegerv(GL_TEXTURE_BINDING_2D, currentTexture);
        }

        data.hasClipOrigin = data.glVersion >= 450;


        if (ImGui.getIO().hasConfigFlags(ImGuiConfigFlags.ViewportsEnable)) {
            initPlatformInterface();
        }

        return true;
    }

    public void shutdown() {
        final ImGuiIO io = ImGui.getIO();

        shutdownPlatformInterface();
        destroyDeviceObjects();

        io.setBackendRendererName(null);
        data = null;
    }

    public void newFrame() {
        if (data.shaderHandle == 0) {
            createDeviceObjects();
        }
    }

    protected void setupRenderState(final ImDrawData drawData, final int fbWidth, final int fbHeight, final int gVertexArrayObject) {
        // Setup render state: alpha-blending enabled, no face culling, no depth testing, scissor enabled, polygon fill
        glEnable(GL_BLEND);
        glBlendEquation(GL_FUNC_ADD);
        glBlendFuncSeparate(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA, GL_ONE, GL_ONE_MINUS_SRC_ALPHA);
        glDisable(GL_CULL_FACE);
        glDisable(GL_DEPTH_TEST);
        glDisable(GL_STENCIL_TEST);
        glEnable(GL_SCISSOR_TEST);

        if (data.glVersion >= 310) {
            glDisable(GL_PRIMITIVE_RESTART);
        }
        if (data.glVersion >= 200) {
            glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
        }

        // Support for GL 4.5 rarely used glClipControl(GL_UPPER_LEFT)
        boolean clipOriginLowerLeft = true;
        if (data.hasClipOrigin) {
            final int[] currentClipOrigin = new int[1];
            glGetIntegerv(GL_CLIP_ORIGIN, currentClipOrigin);
            if (currentClipOrigin[0] == GL_UPPER_LEFT) {
                clipOriginLowerLeft = false;
            }
        }

        // Setup viewport, orthographic projection matrix
        // Our visible imgui space lies from draw_data->DisplayPos (top left) to draw_data->DisplayPos+data_data->DisplaySize (bottom right).
        // DisplayPos is (0,0) for single viewport apps.
        glViewport(0, 0, fbWidth, fbHeight);
        float L = drawData.getDisplayPosX();
        float R = drawData.getDisplayPosX() + drawData.getDisplaySizeX();
        float T = drawData.getDisplayPosY();
        float B = drawData.getDisplayPosY() + drawData.getDisplaySizeY();

        // Swap top and bottom if origin is upper left
        if (data.hasClipOrigin && !clipOriginLowerLeft) {
            float tmp = T;
            T = B;
            B = tmp;
        }

        props.orthoProjMatrix[0] = 2.0f / (R - L);
        props.orthoProjMatrix[5] = 2.0f / (T - B);
        props.orthoProjMatrix[10] = -1.0f;
        props.orthoProjMatrix[12] = (R + L) / (L - R);
        props.orthoProjMatrix[13] = (T + B) / (B - T);
        props.orthoProjMatrix[15] = 1.0f;

        glUseProgram(data.shaderHandle);
        glUniform1i(data.attribLocationTex, 0);
        glUniformMatrix4fv(data.attribLocationProjMtx, false, props.orthoProjMatrix);

        if (data.glVersion >= 330) {
            glBindSampler(0, 0);
        }

        glBindVertexArray(gVertexArrayObject);

        // Bind vertex/index buffers and setup attributes for ImDrawVert
        glBindBuffer(GL_ARRAY_BUFFER, data.vboHandle);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, data.elementsHandle);
        glEnableVertexAttribArray(data.attribLocationVtxPos);
        glEnableVertexAttribArray(data.attribLocationVtxUV);
        glEnableVertexAttribArray(data.attribLocationVtxColor);
        glVertexAttribPointer(data.attribLocationVtxPos, 2, GL_FLOAT, false, ImDrawData.sizeOfImDrawVert(), 0);
        glVertexAttribPointer(data.attribLocationVtxUV, 2, GL_FLOAT, false, ImDrawData.sizeOfImDrawVert(), 8);
        glVertexAttribPointer(data.attribLocationVtxColor, 4, GL_UNSIGNED_BYTE, true, ImDrawData.sizeOfImDrawVert(), 16);
    }

    /**
     * OpenGL3 Render function.
     * Note that this implementation is little overcomplicated because we are saving/setting up/restoring every OpenGL state explicitly.
     * This is in order to be able to run within an OpenGL engine that doesn't do so.
     *
     * @param drawData draw data to render
     */
    public void renderDrawData(final ImDrawData drawData) {
        // Avoid rendering when minimized, scale coordinates for retina displays (screen coordinates != framebuffer coordinates)
        final int fbWidth = (int) (drawData.getDisplaySizeX() * drawData.getFramebufferScaleX());
        final int fbHeight = (int) (drawData.getDisplaySizeY() * drawData.getFramebufferScaleY());
        if (fbWidth <= 0 || fbHeight <= 0) {
            return;
        }

        if (drawData.getCmdListsCount() <= 0) {
            return;
        }

        glGetIntegerv(GL_ACTIVE_TEXTURE, props.lastActiveTexture);
        glActiveTexture(GL_TEXTURE0);
        glGetIntegerv(GL_CURRENT_PROGRAM, props.lastProgram);
        glGetIntegerv(GL_TEXTURE_BINDING_2D, props.lastTexture);
        if (data.glVersion >= 330) {
            glGetIntegerv(GL_SAMPLER_BINDING, props.lastSampler);
        }
        glGetIntegerv(GL_ARRAY_BUFFER_BINDING, props.lastArrayBuffer);
        glGetIntegerv(GL_VERTEX_ARRAY_BINDING, props.lastVertexArrayObject);
        if (data.glVersion >= 200) {
            glGetIntegerv(GL_POLYGON_MODE, props.lastPolygonMode);
        }
        glGetIntegerv(GL_VIEWPORT, props.lastViewport);
        glGetIntegerv(GL_SCISSOR_BOX, props.lastScissorBox);
        glGetIntegerv(GL_BLEND_SRC_RGB, props.lastBlendSrcRgb);
        glGetIntegerv(GL_BLEND_DST_RGB, props.lastBlendDstRgb);
        glGetIntegerv(GL_BLEND_SRC_ALPHA, props.lastBlendSrcAlpha);
        glGetIntegerv(GL_BLEND_DST_ALPHA, props.lastBlendDstAlpha);
        glGetIntegerv(GL_BLEND_EQUATION_RGB, props.lastBlendEquationRgb);
        glGetIntegerv(GL_BLEND_EQUATION_ALPHA, props.lastBlendEquationAlpha);
        props.lastEnableBlend = glIsEnabled(GL_BLEND);
        props.lastEnableCullFace = glIsEnabled(GL_CULL_FACE);
        props.lastEnableDepthTest = glIsEnabled(GL_DEPTH_TEST);
        props.lastEnableStencilTest = glIsEnabled(GL_STENCIL_TEST);
        props.lastEnableScissorTest = glIsEnabled(GL_SCISSOR_TEST);
        if (data.glVersion >= 310) {
            props.lastEnablePrimitiveRestart = glIsEnabled(GL_PRIMITIVE_RESTART);
        }

        // Setup desired GL state
        // Recreate the VAO every time (this is to easily allow multiple GL contexts to be rendered to. VAO are not shared among GL contexts)
        // The renderer would actually work without any VAO bound, but then our VertexAttrib calls would overwrite the default one currently bound.
        final int vertexArrayObject = glGenVertexArrays();
        setupRenderState(drawData, fbWidth, fbHeight, vertexArrayObject);

        // Will project scissor/clipping rectangles into framebuffer space
        final float clipOffX = drawData.getDisplayPosX(); // (0,0) unless using multi-viewports
        final float clipOffY = drawData.getDisplayPosY(); // (0,0) unless using multi-viewports
        final float clipScaleX = drawData.getFramebufferScaleX(); // (1,1) unless using retina display which are often (2,2)
        final float clipScaleY = drawData.getFramebufferScaleY(); // (1,1) unless using retina display which are often (2,2)

        // Render command lists
        for (int n = 0; n < drawData.getCmdListsCount(); n++) {
            // FIXME: this is a straightforward port from Dear ImGui and it doesn't work with multi-viewports.
            //        So we keep solution we used before.
            // Upload vertex/index buffers
            // final int vtxBufferSize = drawData.getCmdListVtxBufferSize(n) * ImDrawData.sizeOfImDrawVert();
            // final int idxBufferSize = drawData.getCmdListIdxBufferSize(n) * ImDrawData.sizeOfImDrawIdx();
            // if (data.vertexBufferSize < vtxBufferSize) {
            //     data.vertexBufferSize = vtxBufferSize;
            //     glBufferData(GL_ARRAY_BUFFER, data.vertexBufferSize, GL_STREAM_DRAW);
            // }
            // if (data.indexBufferSize < idxBufferSize) {
            //     data.indexBufferSize = idxBufferSize;
            //     glBufferData(GL_ELEMENT_ARRAY_BUFFER, data.indexBufferSize, GL_STREAM_DRAW);
            // }
            // glBufferSubData(GL_ARRAY_BUFFER, 0, drawData.getCmdListVtxBufferData(n));
            // glBufferSubData(GL_ELEMENT_ARRAY_BUFFER, 0, drawData.getCmdListIdxBufferData(n));

            glBufferData(GL_ARRAY_BUFFER, drawData.getCmdListVtxBufferData(n), GL_STREAM_DRAW);
            glBufferData(GL_ELEMENT_ARRAY_BUFFER, drawData.getCmdListIdxBufferData(n), GL_STREAM_DRAW);

            for (int cmdIdx = 0; cmdIdx < drawData.getCmdListCmdBufferSize(n); cmdIdx++) {
                // TODO:
                // if userCallback
                // else

                drawData.getCmdListCmdBufferClipRect(props.clipRect, n, cmdIdx);

                final float clipMinX = (props.clipRect.x - clipOffX) * clipScaleX;
                final float clipMinY = (props.clipRect.y - clipOffY) * clipScaleY;
                final float clipMaxX = (props.clipRect.z - clipOffX) * clipScaleX;
                final float clipMaxY = (props.clipRect.w - clipOffY) * clipScaleY;

                if (clipMaxX <= clipMinX || clipMaxY <= clipMinY) {
                    continue;
                }

                // Apply scissor/clipping rectangle (Y is inverted in OpenGL)
                glScissor((int) clipMinX, (int) (fbHeight - clipMaxY), (int) (clipMaxX - clipMinX), (int) (clipMaxY - clipMinY));

                // Bind texture, Draw
                final long textureId = drawData.getCmdListCmdBufferTextureId(n, cmdIdx);
                final int elemCount = drawData.getCmdListCmdBufferElemCount(n, cmdIdx);
                final int idxOffset = drawData.getCmdListCmdBufferIdxOffset(n, cmdIdx);
                final int vtxOffset = drawData.getCmdListCmdBufferVtxOffset(n, cmdIdx);
                final long indices = idxOffset * (long) ImDrawData.sizeOfImDrawIdx();
                final int type = ImDrawData.sizeOfImDrawIdx() == 2 ? GL_UNSIGNED_SHORT : GL_UNSIGNED_INT;

                glBindTexture(GL_TEXTURE_2D, (int) textureId);

                if (data.glVersion >= 320) {
                    glDrawElementsBaseVertex(GL_TRIANGLES, elemCount, type, indices, vtxOffset);
                } else {
                    glDrawElements(GL_TRIANGLES, elemCount, type, indices);
                }
            }
        }

        // Destroy the temporary VAO
        glDeleteVertexArrays(vertexArrayObject);

        // Restore modified GL state
        glUseProgram(props.lastProgram[0]);
        glBindTexture(GL_TEXTURE_2D, props.lastTexture[0]);
        if (data.glVersion >= 330) {
            glBindSampler(0, props.lastSampler[0]);
        }
        glActiveTexture(props.lastActiveTexture[0]);
        glBindVertexArray(props.lastVertexArrayObject[0]);
        glBindBuffer(GL_ARRAY_BUFFER, props.lastArrayBuffer[0]);
        glBlendEquationSeparate(props.lastBlendEquationRgb[0], props.lastBlendEquationAlpha[0]);
        glBlendFuncSeparate(props.lastBlendSrcRgb[0], props.lastBlendDstRgb[0], props.lastBlendSrcAlpha[0], props.lastBlendDstAlpha[0]);
        if (props.lastEnableBlend) glEnable(GL_BLEND);
        else glDisable(GL_BLEND);
        if (props.lastEnableCullFace) glEnable(GL_CULL_FACE);
        else glDisable(GL_CULL_FACE);
        if (props.lastEnableDepthTest) glEnable(GL_DEPTH_TEST);
        else glDisable(GL_DEPTH_TEST);
        if (props.lastEnableStencilTest) glEnable(GL_STENCIL_TEST);
        else glDisable(GL_STENCIL_TEST);
        if (props.lastEnableScissorTest) glEnable(GL_SCISSOR_TEST);
        else glDisable(GL_SCISSOR_TEST);
        if (data.glVersion >= 310) {
            if (props.lastEnablePrimitiveRestart) {
                glEnable(GL_PRIMITIVE_RESTART);
            } else {
                glDisable(GL_PRIMITIVE_RESTART);
            }
        }
        if (data.glVersion >= 200) {
            glPolygonMode(GL_FRONT_AND_BACK, props.lastPolygonMode[0]);
        }
        glViewport(props.lastViewport[0], props.lastViewport[1], props.lastViewport[2], props.lastViewport[3]);
        glScissor(props.lastScissorBox[0], props.lastScissorBox[1], props.lastScissorBox[2], props.lastScissorBox[3]);
    }

    public boolean createFontsTexture() {
        final ImFontAtlas fontAtlas = ImGui.getIO().getFonts();

        // Build texture atlas
        // Load as RGBA 32-bit (75% of the memory is wasted, but default font is so small) because it is more likely to be compatible with user's existing shaders.
        // If your ImTextureId represent a higher-level concept than just a GL texture id, consider calling GetTexDataAsAlpha8() instead to save on GPU memory.
        final ImInt width = new ImInt();
        final ImInt height = new ImInt();
        final ByteBuffer pixels = fontAtlas.getTexDataAsRGBA32(width, height);

        final int[] lastTexture = new int[1];
        glGetIntegerv(GL_TEXTURE_BINDING_2D, lastTexture);
        data.fontTexture = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, data.fontTexture);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glPixelStorei(GL_UNPACK_ALIGNMENT, 4); // Not on WebGL/ES
        glPixelStorei(GL_UNPACK_SKIP_PIXELS, 0); // Not on WebGL/ES
        glPixelStorei(GL_UNPACK_SKIP_ROWS, 0); // Not on WebGL/ES
        glPixelStorei(GL_UNPACK_ROW_LENGTH, 0); // Not on WebGL/ES
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width.get(), height.get(), 0, GL_RGBA, GL_UNSIGNED_BYTE, pixels);

        // Store our identifier
        fontAtlas.setTexID(data.fontTexture);

        glBindTexture(GL_TEXTURE_2D, lastTexture[0]);

        return true;
    }

    public void destroyFontsTexture() {
        final ImGuiIO io = ImGui.getIO();
        if (data.fontTexture != 0) {
            glDeleteTextures(data.fontTexture);
            io.getFonts().setTexID(0);
            data.fontTexture = 0;
        }
    }

    protected boolean checkShader(final int handle, final String desc) {
        final int[] status = new int[1];
        final int[] logLength = new int[1];
        glGetShaderiv(handle, GL_COMPILE_STATUS, status);
        glGetShaderiv(handle, GL_INFO_LOG_LENGTH, logLength);
        if (status[0] == GL_FALSE) {
            System.err.printf("%s: failed to compile %s! With GLSL: %s\n", this, desc, data.glslVersion);
        }
        if (logLength[0] > 1) {
            final String log = glGetShaderInfoLog(handle);
            System.err.println(log);
        }
        return status[0] == GL_TRUE;
    }

    protected boolean checkProgram(final int handle, final String desc) {
        final int[] status = new int[1];
        final int[] logLength = new int[1];
        glGetProgramiv(handle, GL_LINK_STATUS, status);
        glGetProgramiv(handle, GL_INFO_LOG_LENGTH, logLength);
        if (status[0] == GL_FALSE) {
            System.err.printf("%s: failed to link %s! With GLSL: %s\n", this, desc, data.glslVersion);
        }
        if (logLength[0] > 1) {
            final String log = glGetProgramInfoLog(handle);
            System.err.println(log);
        }
        return status[0] == GL_TRUE;
    }

    protected int parseGlslVersionString(final String glslVersion) {
        final Pattern p = Pattern.compile("\\d+");
        final Matcher m = p.matcher(glslVersion);

        if (m.find()) {
            return Integer.parseInt(m.group());
        }

        return 130;
    }

    protected boolean createDeviceObjects() {
        // Backup GL state
        final int[] lastTexture = new int[1];
        final int[] lastArrayBuffer = new int[1];
        final int[] lastVertexArray = new int[1];
        glGetIntegerv(GL_TEXTURE_BINDING_2D, lastTexture);
        glGetIntegerv(GL_ARRAY_BUFFER_BINDING, lastArrayBuffer);
        glGetIntegerv(GL_VERTEX_ARRAY_BINDING, lastVertexArray);

        final int glslVersionValue = parseGlslVersionString(data.glslVersion);

        // Select shaders matching our GLSL versions
        final CharSequence vertexShader;
        final CharSequence fragmentShader;

        if (glslVersionValue < 130) {
            vertexShader = vertexShaderGlsl120();
            fragmentShader = fragmentShaderGlsl120();
        } else if (glslVersionValue >= 410) {
            vertexShader = vertexShaderGlsl410Core();
            fragmentShader = fragmentShaderGlsl410Core();
        } else if (glslVersionValue == 300) {
            vertexShader = vertexShaderGlsl300es();
            fragmentShader = fragmentShaderGlsl300es();
        } else {
            vertexShader = vertexShaderGlsl130();
            fragmentShader = fragmentShaderGlsl130();
        }

        // Create shaders
        final int vertHandle = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertHandle, vertexShader);
        glCompileShader(vertHandle);
        checkShader(vertHandle, "vertex shader");

        final int fragHandle = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragHandle, fragmentShader);
        glCompileShader(fragHandle);
        checkShader(fragHandle, "fragment shader");

        // Link
        data.shaderHandle = glCreateProgram();
        glAttachShader(data.shaderHandle, vertHandle);
        glAttachShader(data.shaderHandle, fragHandle);
        glLinkProgram(data.shaderHandle);
        checkProgram(data.shaderHandle, "shader program");

        glDetachShader(data.shaderHandle, vertHandle);
        glDetachShader(data.shaderHandle, fragHandle);
        glDeleteShader(vertHandle);
        glDeleteShader(fragHandle);

        data.attribLocationTex = glGetUniformLocation(data.shaderHandle, "Texture");
        data.attribLocationProjMtx = glGetUniformLocation(data.shaderHandle, "ProjMtx");
        data.attribLocationVtxPos = glGetAttribLocation(data.shaderHandle, "Position");
        data.attribLocationVtxUV = glGetAttribLocation(data.shaderHandle, "UV");
        data.attribLocationVtxColor = glGetAttribLocation(data.shaderHandle, "Color");

        // Create buffers
        data.vboHandle = glGenBuffers();
        data.elementsHandle = glGenBuffers();

        createFontsTexture();

        // Restore modified GL state
        glBindTexture(GL_TEXTURE_2D, lastTexture[0]);
        glBindBuffer(GL_ARRAY_BUFFER, lastArrayBuffer[0]);
        glBindVertexArray(lastVertexArray[0]);

        return true;
    }

    public void destroyDeviceObjects() {
        if (data.vboHandle != 0) {
            glDeleteBuffers(data.vboHandle);
            data.vboHandle = 0;
        }
        if (data.elementsHandle != 0) {
            glDeleteBuffers(data.elementsHandle);
            data.elementsHandle = 0;
        }
        if (data.shaderHandle != 0) {
            glDeleteProgram(data.shaderHandle);
            data.shaderHandle = 0;
        }
        destroyFontsTexture();
    }

    //--------------------------------------------------------------------------------------------------------
    // MULTI-VIEWPORT / PLATFORM INTERFACE SUPPORT
    // This is an _advanced_ and _optional_ feature, allowing the backend to create and handle multiple viewports simultaneously.
    // If you are new to dear imgui or creating a new binding for dear imgui, it is recommended that you completely ignore this section first..
    //--------------------------------------------------------------------------------------------------------

    private final class RendererRenderWindowFunction extends ImPlatformFuncViewport {
        @Override
        public void accept(final ImGuiViewport vp) {
            if (!vp.hasFlags(ImGuiViewportFlags.NoRendererClear)) {
                glClearColor(0, 0, 0, 0);
                glClear(GL_COLOR_BUFFER_BIT);
            }
            renderDrawData(vp.getDrawData());
        }
    }

    protected void initPlatformInterface() {
        ImGui.getPlatformIO().setRendererRenderWindow(new RendererRenderWindowFunction());
    }

    protected void shutdownPlatformInterface() {
        ImGui.destroyPlatformWindows();
    }

    protected String vertexShaderGlsl120() {
        return data.glslVersion + "\n"
            + "uniform mat4 ProjMtx;\n"
            + "attribute vec2 Position;\n"
            + "attribute vec2 UV;\n"
            + "attribute vec4 Color;\n"
            + "varying vec2 Frag_UV;\n"
            + "varying vec4 Frag_Color;\n"
            + "void main()\n"
            + "{\n"
            + "    Frag_UV = UV;\n"
            + "    Frag_Color = Color;\n"
            + "    gl_Position = ProjMtx * vec4(Position.xy,0,1);\n"
            + "}\n";
    }

    protected String vertexShaderGlsl130() {
        return data.glslVersion + "\n"
            + "uniform mat4 ProjMtx;\n"
            + "in vec2 Position;\n"
            + "in vec2 UV;\n"
            + "in vec4 Color;\n"
            + "out vec2 Frag_UV;\n"
            + "out vec4 Frag_Color;\n"
            + "void main()\n"
            + "{\n"
            + "    Frag_UV = UV;\n"
            + "    Frag_Color = Color;\n"
            + "    gl_Position = ProjMtx * vec4(Position.xy,0,1);\n"
            + "}\n";
    }

    private String vertexShaderGlsl300es() {
        return data.glslVersion + "\n"
            + "precision highp float;\n"
            + "layout (location = 0) in vec2 Position;\n"
            + "layout (location = 1) in vec2 UV;\n"
            + "layout (location = 2) in vec4 Color;\n"
            + "uniform mat4 ProjMtx;\n"
            + "out vec2 Frag_UV;\n"
            + "out vec4 Frag_Color;\n"
            + "void main()\n"
            + "{\n"
            + "    Frag_UV = UV;\n"
            + "    Frag_Color = Color;\n"
            + "    gl_Position = ProjMtx * vec4(Position.xy,0,1);\n"
            + "}\n";
    }

    protected String vertexShaderGlsl410Core() {
        return data.glslVersion + "\n"
            + "layout (location = 0) in vec2 Position;\n"
            + "layout (location = 1) in vec2 UV;\n"
            + "layout (location = 2) in vec4 Color;\n"
            + "uniform mat4 ProjMtx;\n"
            + "out vec2 Frag_UV;\n"
            + "out vec4 Frag_Color;\n"
            + "void main()\n"
            + "{\n"
            + "    Frag_UV = UV;\n"
            + "    Frag_Color = Color;\n"
            + "    gl_Position = ProjMtx * vec4(Position.xy,0,1);\n"
            + "}\n";
    }

    protected String fragmentShaderGlsl120() {
        return data.glslVersion + "\n"
            + "#ifdef GL_ES\n"
            + "    precision mediump float;\n"
            + "#endif\n"
            + "uniform sampler2D Texture;\n"
            + "varying vec2 Frag_UV;\n"
            + "varying vec4 Frag_Color;\n"
            + "void main()\n"
            + "{\n"
            + "    gl_FragColor = Frag_Color * texture2D(Texture, Frag_UV.st);\n"
            + "}\n";
    }

    protected String fragmentShaderGlsl130() {
        return data.glslVersion + "\n"
            + "uniform sampler2D Texture;\n"
            + "in vec2 Frag_UV;\n"
            + "in vec4 Frag_Color;\n"
            + "out vec4 Out_Color;\n"
            + "void main()\n"
            + "{\n"
            + "    Out_Color = Frag_Color * texture(Texture, Frag_UV.st);\n"
            + "}\n";
    }

    protected String fragmentShaderGlsl300es() {
        return data.glslVersion + "\n"
            + "precision mediump float;\n"
            + "uniform sampler2D Texture;\n"
            + "in vec2 Frag_UV;\n"
            + "in vec4 Frag_Color;\n"
            + "layout (location = 0) out vec4 Out_Color;\n"
            + "void main()\n"
            + "{\n"
            + "    Out_Color = Frag_Color * texture(Texture, Frag_UV.st);\n"
            + "}\n";
    }

    protected String fragmentShaderGlsl410Core() {
        return data.glslVersion + "\n"
            + "in vec2 Frag_UV;\n"
            + "in vec4 Frag_Color;\n"
            + "uniform sampler2D Texture;\n"
            + "layout (location = 0) out vec4 Out_Color;\n"
            + "void main()\n"
            + "{\n"
            + "    Out_Color = Frag_Color * texture(Texture, Frag_UV.st);\n"
            + "}\n";
    }
}
