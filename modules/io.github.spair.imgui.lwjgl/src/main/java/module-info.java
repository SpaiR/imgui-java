module io.github.spair.imgui.lwjgl {

    //Public exports
    exports io.github.spair.imgui.lwjgl.gl3;
    exports io.github.spair.imgui.lwjgl.glfw;

    //ImGui
    requires transitive io.github.spair.imgui;

    //LWJGL
    requires transitive org.lwjgl;
    requires transitive org.lwjgl.glfw;
    requires transitive org.lwjgl.opengl;
}
