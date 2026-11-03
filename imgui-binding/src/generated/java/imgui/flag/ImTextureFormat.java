package imgui.flag;


/**
 * We intentionally support a limited amount of texture formats to limit burden on CPU-side code and extension.
 * Most standard backends only support RGBA32 but we provide a single channel option for low-resource/embedded systems.
 */
public final class ImTextureFormat {
    private ImTextureFormat() {
    }

    /**
     * 4 components per pixel, each is unsigned 8-bit. Total size = TexWidth * TexHeight * 4
     */
    public static final int RGBA32 = 0;

    /**
     * 1 component per pixel, each is unsigned 8-bit. Total size = TexWidth * TexHeight
     */
    public static final int Alpha8 = 1;
}
