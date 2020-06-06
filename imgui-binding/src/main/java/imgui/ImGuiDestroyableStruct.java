package imgui;

/**
 * When implement, class could be manually destroyed (free allocated memory on the JNI side).
 */
interface ImGuiDestroyableStruct {
    void destroy();
}
