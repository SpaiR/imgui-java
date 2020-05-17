package imgui.gl3;

import imgui.ImDrawData;
import imgui.ImFontAtlas;
import imgui.ImGui;
import imgui.ImInt;
import imgui.ImVec2;
import imgui.ImVec4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.nio.ByteBuffer;
import java.util.Objects;

import static org.lwjgl.opengl.GL32.*;

/**
 * This class mostly a straightforward port of the
 * <a href="https://raw.githubusercontent.com/ocornut/imgui/v1.74/examples/imgui_impl_opengl3.cpp">imgui_impl_opengl3.cpp</a>
 * adapted for Java and LWJGL realms.
 * <p>
 * It do support a backup of the current GL state before rendering and restoring of its initial state after.
 * But some specific state variables may be missed (all which are hidden under '#ifdef' macros in the original 'imgui_impl_opengl3.cpp' file).
 * <p>
 * This implementation uses shaders with 130 version of GLSL (OpenGL 3.0).
 */
@SuppressWarnings("MagicNumber")
public final class ImGuiImplGl3 {
    private int vboHandle = 0;
    private int elementsHandle = 0;

    // Used to store tmp renderer data
    private final ImVec2 displaySize = new ImVec2();
    private final ImVec2 framebufferScale = new ImVec2();
    private final ImVec2 displayPos = new ImVec2();
    private final ImVec4 clipRect = new ImVec4();

    // Shader stuff
    private int programId = 0;
    private int fragmentShaderId = 0;
    private int vertexShaderId = 0;

    // Texture used by fonts
    private int gFontTexture = 0;

    // Projection matrix for ImGui
    private final float[] projMatrix = new float[4 * 4];

    // Variables used to backup GL state before and after rendering of ImGui
    private final int[] lastActiveTexture = new int[1];
    private final int[] lastProgram = new int[1];
    private final int[] lastTexture = new int[1];
    private final int[] lastArrayBuffer = new int[1];
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
    private boolean lastEnableScissorTest = false;

    /**
     * This method SHOULD be called before calling of {@link ImGuiImplGl3#render(ImDrawData)} method.
     */
    public void init() {
        prepareShader();
        prepareFont();
        vboHandle = glGenBuffers();
        elementsHandle = glGenBuffers();
    }

    /**
     * Method which renders {@link ImDrawData} from ImGui.
     *
     * @param drawData data used to draw ImGui interface to your current OpenGL context
     */
    public void render(final ImDrawData drawData) {
        if (drawData.getCmdListsCount() <= 0) {
            return;
        }

        drawData.getDisplaySize(displaySize);
        drawData.getFramebufferScale(framebufferScale);

        // Avoid rendering when minimized, scale coordinates for retina displays (screen coordinates != framebuffer coordinates)
        final int fbWidth = (int) (displaySize.x * framebufferScale.x);
        final int fbHeight = (int) (displaySize.y * framebufferScale.y);

        if (fbWidth <= 0 || fbHeight <= 0) {
            return;
        }

        drawData.getDisplayPos(displayPos);

        backupGlState();
        bind();

        // Render command lists
        for (int cmdListIdx = 0; cmdListIdx < drawData.getCmdListsCount(); cmdListIdx++) {
            // Upload vertex/index buffers
            glBufferData(GL_ARRAY_BUFFER, drawData.getCmdListVtxBufferData(cmdListIdx), GL_STREAM_DRAW);
            glBufferData(GL_ELEMENT_ARRAY_BUFFER, drawData.getCmdListIdxBufferData(cmdListIdx), GL_STREAM_DRAW);

            for (int cmdBufferIdx = 0; cmdBufferIdx < drawData.getCmdListCmdBufferSize(cmdListIdx); cmdBufferIdx++) {
                drawData.getCmdListCmdBufferClipRect(cmdListIdx, cmdBufferIdx, clipRect);

                final float clipRectX = (clipRect.x - displayPos.x) * framebufferScale.x;
                final float clipRectY = (clipRect.y - displayPos.y) * framebufferScale.y;
                final float clipRectZ = (clipRect.z - displayPos.x) * framebufferScale.x;
                final float clipRectW = (clipRect.w - displayPos.y) * framebufferScale.y;

                if (clipRectX < fbWidth && clipRectY < fbHeight && clipRectZ >= 0.0f && clipRectW >= 0.0f) {
                    // Apply scissor/clipping rectangle
                    glScissor((int) clipRectX, (int) (fbHeight - clipRectW), (int) (clipRectZ - clipRectX), (int) (clipRectW - clipRectY));

                    // Bind texture, Draw
                    final int textureId = drawData.getCmdListCmdBufferTextureId(cmdListIdx, cmdBufferIdx);
                    final int elemCount = drawData.getCmdListCmdBufferElemCount(cmdListIdx, cmdBufferIdx);
                    final int idxBufferOffset = drawData.getCmdListCmdBufferIdxOffset(cmdListIdx, cmdBufferIdx);

                    glBindTexture(GL_TEXTURE_2D, textureId);
                    glDrawElements(GL_TRIANGLES, elemCount, GL_UNSIGNED_SHORT, idxBufferOffset * ImDrawData.SIZEOF_IM_DRAW_IDX);
                }
            }
        }

        unbind();
        restoreModifiedGlState();
    }

    /**
     * Call this method in the end of your app working state to dispose resources.
     */
    public void dispose() {
        glDeleteTextures(gFontTexture);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        glDeleteBuffers(elementsHandle);
        glDeleteBuffers(vboHandle);
        glDetachShader(programId, vertexShaderId);
        glDetachShader(programId, fragmentShaderId);
        glDeleteProgram(programId);
    }

    private void prepareShader() {
        final CharSequence fragShaderSource = readFromResources("default.frag");
        final CharSequence vertShaderSource = readFromResources("default.vert");

        fragmentShaderId = loadAndCompileShader(GL_FRAGMENT_SHADER, fragShaderSource);
        vertexShaderId = loadAndCompileShader(GL_VERTEX_SHADER, vertShaderSource);

        programId = glCreateProgram();
        glAttachShader(programId, fragmentShaderId);
        glAttachShader(programId, vertexShaderId);
        glLinkProgram(programId);

        if (glGetProgrami(programId, GL_LINK_STATUS) != GL_TRUE) {
            throw new IllegalStateException(glGetProgramInfoLog(programId));
        }
    }

    private void prepareFont() {
        gFontTexture = glGenTextures();

        final ImFontAtlas fontAtlas = ImGui.getIO().getFonts();
        final ImInt width = new ImInt();
        final ImInt height = new ImInt();
        final ByteBuffer buffer = fontAtlas.getTexDataAsRGBA32(width, height);

        glBindTexture(GL_TEXTURE_2D, gFontTexture);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width.get(), height.get(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);

        fontAtlas.setTexID(gFontTexture);
    }

    private void backupGlState() {
        glGetIntegerv(GL_ACTIVE_TEXTURE, lastActiveTexture);
        glGetIntegerv(GL_CURRENT_PROGRAM, lastProgram);
        glGetIntegerv(GL_TEXTURE_BINDING_2D, lastTexture);
        glGetIntegerv(GL_ARRAY_BUFFER_BINDING, lastArrayBuffer);
        glGetIntegerv(GL_VIEWPORT, lastViewport);
        glGetIntegerv(GL_SCISSOR_BOX, lastScissorBox);
        glGetIntegerv(GL_BLEND_SRC_RGB, lastBlendSrcRgb);
        glGetIntegerv(GL_BLEND_DST_RGB, lastBlendDstRgb);
        glGetIntegerv(GL_BLEND_SRC_ALPHA, lastBlendSrcAlpha);
        glGetIntegerv(GL_BLEND_DST_ALPHA, lastBlendDstAlpha);
        glGetIntegerv(GL_BLEND_EQUATION_RGB, lastBlendEquationRgb);
        glGetIntegerv(GL_BLEND_EQUATION_ALPHA, lastBlendEquationAlpha);
        lastEnableBlend = glIsEnabled(GL_BLEND);
        lastEnableCullFace = glIsEnabled(GL_CULL_FACE);
        lastEnableDepthTest = glIsEnabled(GL_DEPTH_TEST);
        lastEnableScissorTest = glIsEnabled(GL_SCISSOR_TEST);
    }

    private void restoreModifiedGlState() {
        glUseProgram(lastProgram[0]);
        glBindTexture(GL_TEXTURE_2D, lastTexture[0]);
        glActiveTexture(lastActiveTexture[0]);
        glBindBuffer(GL_ARRAY_BUFFER, lastArrayBuffer[0]);
        glBlendEquationSeparate(lastBlendEquationRgb[0], lastBlendEquationAlpha[0]);
        glBlendFuncSeparate(lastBlendSrcRgb[0], lastBlendDstRgb[0], lastBlendSrcAlpha[0], lastBlendDstAlpha[0]);
        // @formatter:off CHECKSTYLE:OFF
        if (lastEnableBlend) glEnable(GL_BLEND); else glDisable(GL_BLEND);
        if (lastEnableCullFace) glEnable(GL_CULL_FACE); else glDisable(GL_CULL_FACE);
        if (lastEnableDepthTest) glEnable(GL_DEPTH_TEST); else glDisable(GL_DEPTH_TEST);
        if (lastEnableScissorTest) glEnable(GL_SCISSOR_TEST); else glDisable(GL_SCISSOR_TEST);
        // @formatter:on CHECKSTYLE:ON
        glViewport(lastViewport[0], lastViewport[1], lastViewport[2], lastViewport[3]);
        glScissor(lastScissorBox[0], lastScissorBox[1], lastScissorBox[2], lastScissorBox[3]);
    }

    private void bind() {
        glViewport(0, 0, (int) displaySize.x, (int) displaySize.y);
        glActiveTexture(GL_TEXTURE0);
        glEnable(GL_BLEND);
        glBlendEquation(GL_FUNC_ADD);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glDisable(GL_CULL_FACE);
        glDisable(GL_DEPTH_TEST);
        glEnable(GL_SCISSOR_TEST);

        final float left = displayPos.x;
        final float right = displayPos.x + displaySize.x;
        final float top = displayPos.y;
        final float bottom = displayPos.y + displaySize.y;

        // Orthographic matrix projection
        projMatrix[0] = 2.0f / (right - left);
        projMatrix[5] = 2.0f / (top - bottom);
        projMatrix[10] = -1.0f;
        projMatrix[12] = (right + left) / (left - right);
        projMatrix[13] = (top + bottom) / (bottom - top);
        projMatrix[15] = 1.0f;

        // Bind shader
        glUseProgram(programId);
        glUniform1i(glGetUniformLocation(programId, "Texture"), 0);
        glUniformMatrix4fv(glGetUniformLocation(programId, "ProjMtx"), false, projMatrix);

        // Bind vertex/index buffers and setup attributes for ImDrawVert
        glBindBuffer(GL_ARRAY_BUFFER, vboHandle);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, elementsHandle);

        int location = glGetAttribLocation(programId, "Position");
        glEnableVertexAttribArray(location);
        glVertexAttribPointer(location, 2, GL_FLOAT, false, ImDrawData.SIZEOF_IM_DRAW_VERT, 0);

        location = glGetAttribLocation(programId, "UV");
        glEnableVertexAttribArray(location);
        glVertexAttribPointer(location, 2, GL_FLOAT, false, ImDrawData.SIZEOF_IM_DRAW_VERT, 8);

        location = glGetAttribLocation(programId, "Color");
        glEnableVertexAttribArray(location);
        glVertexAttribPointer(location, 4, GL_UNSIGNED_BYTE, true, ImDrawData.SIZEOF_IM_DRAW_VERT, 16);
    }

    private void unbind() {
        // Unbind vertices
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        glDisableVertexAttribArray(glGetAttribLocation(programId, "Position"));
        glDisableVertexAttribArray(glGetAttribLocation(programId, "UV"));
        glDisableVertexAttribArray(glGetAttribLocation(programId, "Color"));

        // Unbind index
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        glFlush();

        // Unbind shader
        glUseProgram(0);
    }

    private int loadAndCompileShader(final int type, final CharSequence source) {
        final int id = glCreateShader(type);

        glShaderSource(id, source);
        glCompileShader(id);

        if (glGetShaderi(id, GL_COMPILE_STATUS) != GL_TRUE) {
            throw new IllegalStateException(glGetShaderInfoLog(id));
        }

        return id;
    }

    private CharSequence readFromResources(final String path) {
        final StringBuilder builder = new StringBuilder();

        try (InputStream in = getClass().getClassLoader().getResourceAsStream(path);
             BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(in)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append('\n');
            }
        } catch (IOException ex) {
            throw new UncheckedIOException("Failed to read from resources!", ex);
        }

        return builder.toString();
    }
}
