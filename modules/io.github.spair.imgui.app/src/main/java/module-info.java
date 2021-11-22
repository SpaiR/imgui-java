module io.github.spair.imgui.app {

    //ImGui
    requires transitive io.github.spair.imgui;
    requires transitive io.github.spair.imgui.lwjgl;

    //LWJGL
    requires transitive org.lwjgl.glfw;
    requires transitive org.lwjgl.opengl;
}
