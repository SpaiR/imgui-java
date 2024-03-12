package imgui.binding;

/**
 * Class extends {@link ImGuiStruct} and provides additional API to allocated and de-allocate native objects.
 */
public abstract class ImGuiStructDestroyable extends ImGuiStruct {
    /**
     * Constructor, which creates natively allocated struct. Those structs should be manually disposed with {@link #destroy()} method.
     */
    public ImGuiStructDestroyable() {
        this(0);
        ptr = create();
    }

    public ImGuiStructDestroyable(final long ptr) {
        super(ptr);
    }

    protected abstract long create();

    /**
     * Method to free natively allocated memory for the struct mapped with pointer.
     */
    public void destroy() {
        nDestroy(ptr);
    }

    private native void nDestroy(long ptr); /*
        delete (void*)ptr;
    */
}
