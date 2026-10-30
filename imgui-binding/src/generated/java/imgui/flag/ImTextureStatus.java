package imgui.flag;


/**
 * Status of a texture to communicate with Renderer Backend.
 */
public final class ImTextureStatus {
    private ImTextureStatus() {
    }

    public static final int OK = 0;

    /**
     * Backend destroyed the texture.
     */
    public static final int Destroyed = 1;

    /**
     * Requesting backend to create the texture. Set status OK when done.
     */
    public static final int WantCreate = 2;

    /**
     * Requesting backend to update specific blocks of pixels (write to texture portions which have never been used before). Set status OK when done.
     */
    public static final int WantUpdates = 3;

    /**
     * Requesting backend to destroy the texture. Set status to Destroyed when done.
     */
    public static final int WantDestroy = 4;
}
