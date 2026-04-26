package imgui.sdl3;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Precompiled shader blobs ported 1-to-1 from upstream {@code imgui_impl_sdlgpu3_shaders.h}.
 *
 * <p>The byte content of each blob matches the upstream {@code const uint8_t name[len]} array
 * byte-for-byte. Blobs are bundled as classpath resources under
 * {@code imgui/sdl3/shaders/*.bin} and lazily loaded on first access — direct {@code byte[]}
 * literals would exceed the 64&nbsp;KB Java method-code limit on a single static initializer.
 *
 * <p>Only the desktop variants (SPIR-V / DXBC / macOS Metal) are exposed here. The upstream
 * iOS / iOS-Simulator Metal blobs are not ported (desktop LWJGL only).
 */
public final class ImGuiImplSdlGpu3Shaders {
    /**
     * Upstream {@code spirv_vertex[1732]}.
     */
    public static final byte[] SPIRV_VERTEX = load("spirv_vertex.bin");
    /**
     * Upstream {@code spirv_fragment[844]}.
     */
    public static final byte[] SPIRV_FRAGMENT = load("spirv_fragment.bin");
    /**
     * Upstream {@code dxbc_vertex[1064]}.
     */
    public static final byte[] DXBC_VERTEX = load("dxbc_vertex.bin");
    /**
     * Upstream {@code dxbc_fragment[744]}.
     */
    public static final byte[] DXBC_FRAGMENT = load("dxbc_fragment.bin");
    /**
     * Upstream macOS {@code metallib_vertex[3892]}.
     */
    public static final byte[] METALLIB_VERTEX = load("metallib_vertex.bin");
    /**
     * Upstream macOS {@code metallib_fragment[3787]}.
     */
    public static final byte[] METALLIB_FRAGMENT = load("metallib_fragment.bin");

    private ImGuiImplSdlGpu3Shaders() {
    }

    private static byte[] load(final String name) {
        final String resource = "/imgui/sdl3/shaders/" + name;
        try (final InputStream in = ImGuiImplSdlGpu3Shaders.class.getResourceAsStream(resource)) {
            if (in == null) {
                throw new IllegalStateException("Missing classpath resource: " + resource);
            }
            try (final ByteArrayOutputStream buf = new ByteArrayOutputStream()) {
                final byte[] chunk = new byte[4096];
                int n;
                while ((n = in.read(chunk)) > 0) {
                    buf.write(chunk, 0, n);
                }
                return buf.toByteArray();
            }
        } catch (IOException ex) {
            throw new IllegalStateException("Failed to load shader resource: " + resource, ex);
        }
    }
}
