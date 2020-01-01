package imgui.gl3;

import imgui.ImDrawData;
import imgui.ImGui;
import imgui.TexDataRGBA32;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.util.Objects;

import static org.lwjgl.opengl.GL30.*;

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
    private int vBufferHandle = 0;
    private int iBufferHandle = 0;

    private boolean isFontInitialized = false;

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
    }

    /**
     * Method which renders {@link ImDrawData} from ImGui.
     *
     * @param drawData data used to draw ImGui interface to your current OpenGL context
     */
    public void render(final ImDrawData drawData) {
        if (drawData.cmdListsCount <= 0) {
            return;
        }

        // Avoid rendering when minimized, scale coordinates for retina displays (screen coordinates != framebuffer coordinates)
        final int fbWidth = (int) (drawData.displaySizeX * drawData.framebufferScaleX);
        final int fbHeight = (int) (drawData.displaySizeY * drawData.framebufferScaleY);

        if (fbWidth <= 0 || fbHeight <= 0) {
            return;
        }

        if (!isFontInitialized) {
            isFontInitialized = true;
            vBufferHandle = glGenBuffers();
            iBufferHandle = glGenBuffers();
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, iBufferHandle);
            glBufferData(GL_ELEMENT_ARRAY_BUFFER, drawData.iByteBuffer.capacity(), GL_STATIC_DRAW);
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        }

        backupGlState();
        bind(drawData);

        int verticesOffset = 0;
        int indexOffset = 0;

        for (int i = 0; i < drawData.cmdListsCount; i++) {
            drawData.vByteBuffer.limit(verticesOffset + 4);
            final int verticesSize = (int) drawData.vByteBuffer.getFloat(verticesOffset);

            drawData.iByteBuffer.limit(indexOffset + 2);
            final short indexSize = drawData.iByteBuffer.getShort(indexOffset);
            final int cmdSize = (int) drawData.cmdByteBuffer.getFloat();

            final int verticesStartOffset = verticesOffset + 4;
            final int indexStartOffset = indexOffset + 2;

            drawData.vByteBuffer.position(verticesStartOffset);
            drawData.iByteBuffer.position(indexStartOffset);

            final int newVlimit = verticesStartOffset + verticesSize * ImDrawData.V_BUFFER_SIZE;
            final int newIlimit = indexStartOffset + indexSize * ImDrawData.I_BUFFER_SIZE;

            drawData.vByteBuffer.limit(newVlimit);
            drawData.iByteBuffer.limit(newIlimit);

            glBufferData(GL_ARRAY_BUFFER, drawData.vByteBuffer, GL_STATIC_DRAW);
            glBufferData(GL_ELEMENT_ARRAY_BUFFER, drawData.iByteBuffer, GL_STATIC_DRAW);

            verticesOffset += ((verticesSize) * ImDrawData.V_BUFFER_SIZE) + 4;
            indexOffset += ((indexSize) * ImDrawData.I_BUFFER_SIZE) + 2;

            final float clipOffX = drawData.displayPosX; // (0,0) unless using multi-viewports
            final float clipOffY = drawData.displayPosY;
            final float clipScaleX = drawData.framebufferScaleX; // (1,1) unless using retina display which are often (2,2)
            final float clipScaleY = drawData.framebufferScaleY;

            int idxBufferOffset = 0;

            for (int j = 0; j < cmdSize; j++) {
                final int elemCount = (int) drawData.cmdByteBuffer.getFloat();
                float clipRectX = drawData.cmdByteBuffer.getFloat();
                float clipRectY = drawData.cmdByteBuffer.getFloat();
                float clipRectZ = drawData.cmdByteBuffer.getFloat();
                float clipRectW = drawData.cmdByteBuffer.getFloat();
                final int textureID = (int) drawData.cmdByteBuffer.getFloat();

                clipRectX = (clipRectX - clipOffX) * clipScaleX;
                clipRectY = (clipRectY - clipOffY) * clipScaleY;
                clipRectZ = (clipRectZ - clipOffX) * clipScaleX;
                clipRectW = (clipRectW - clipOffY) * clipScaleY;

                if (clipRectX < fbWidth && clipRectY < fbHeight && clipRectZ >= 0.0f && clipRectW >= 0.0f) {
                    glScissor((int) clipRectX, (int) (fbHeight - clipRectW), (int) (clipRectZ - clipRectX), (int) (clipRectW - clipRectY));
                    glBindTexture(GL_TEXTURE_2D, textureID);
                    glDrawElements(GL_TRIANGLES, elemCount, GL_UNSIGNED_SHORT, idxBufferOffset);
                }

                idxBufferOffset += elemCount * 2;
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
        glDeleteBuffers(iBufferHandle);
        glDeleteBuffers(vBufferHandle);
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
        final TexDataRGBA32 texData = TexDataRGBA32.create();

        gFontTexture = glGenTextures();

        glBindTexture(GL_TEXTURE_2D, gFontTexture);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, texData.width, texData.height, 0, GL_RGBA, GL_UNSIGNED_BYTE, texData.pixelBuffer);

        ImGui.getIO().setFontsTexID(gFontTexture);
    }

    private void backupGlState() {
        glGetIntegerv(GL_ACTIVE_TEXTURE, lastActiveTexture);
        glGetIntegerv(GL_CURRENT_PROGRAM, lastProgram);
        glGetIntegerv(GL_TEXTURE_BINDING_2D, lastTexture);
        // int[] last_sampler; glGetIntegerv(GL_SAMPLER_BINDING, &last_sampler); // do it by yourself if needed
        glGetIntegerv(GL_ARRAY_BUFFER_BINDING, lastArrayBuffer);
        // int[] last_vertex_array_object; glGetIntegerv(GL_VERTEX_ARRAY_BINDING, &last_vertex_array_object); // do it by yourself if needed
        // int[] last_polygon_mode; glGetIntegerv(GL_POLYGON_MODE, last_polygon_mode); // do it by yourself if needed
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

    private void bind(final ImDrawData drawData) {
        glViewport(0, 0, (int) drawData.displaySizeX, (int) drawData.displaySizeY);
        glActiveTexture(GL_TEXTURE0);
        glEnable(GL_BLEND);
        glBlendEquation(GL_FUNC_ADD);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glDisable(GL_CULL_FACE);
        glDisable(GL_DEPTH_TEST);
        glEnable(GL_SCISSOR_TEST);

        final float left = drawData.displayPosX;
        final float right = drawData.displayPosX + drawData.displaySizeX;
        final float top = drawData.displayPosY;
        final float bottom = drawData.displayPosY + drawData.displaySizeY;

        // Orthographic matrix projection
        projMatrix[0] = 2.0f / (right - left);
        projMatrix[5] = 2.0f / (top - bottom);
        projMatrix[10] = -1.0f;
        projMatrix[12] = (right + left) / (left - right);
        projMatrix[13] = (top + bottom) / (bottom - top);
        projMatrix[15] = 1.0f;

        // Bind vertices
        glBindBuffer(GL_ARRAY_BUFFER, vBufferHandle);

        int location = glGetAttribLocation(programId, "Position");
        glEnableVertexAttribArray(location);
        glVertexAttribPointer(location, 2, GL_FLOAT, false, 20, 0);

        location = glGetAttribLocation(programId, "UV");
        glEnableVertexAttribArray(location);
        glVertexAttribPointer(location, 2, GL_FLOAT, false, 20, 8);

        location = glGetAttribLocation(programId, "Color");
        glEnableVertexAttribArray(location);
        glVertexAttribPointer(location, 4, GL_UNSIGNED_BYTE, true, 20, 16);

        //Bind index
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, iBufferHandle);

        // Bind shader
        glUseProgram(programId);
        glUniformMatrix4fv(glGetUniformLocation(programId, "ProjMtx"), false, projMatrix);
        glUniform1i(glGetUniformLocation(programId, "Texture"), 0);
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
