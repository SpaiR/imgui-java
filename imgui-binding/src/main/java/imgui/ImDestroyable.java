package imgui;

/**
 * While implementing this interface, class could be manually destroyed (free allocated memory on the JNI side).
 * Read javadoc and "BINDING NOTICE" for specific cases.
 */
interface ImDestroyable {
    void destroy();
}
