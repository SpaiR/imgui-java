import imgui.ImGuiViewport;
import imgui.flag.ImGuiColorEditFlags;
import imgui.flag.ImGuiCond;
import imgui.flag.ImGuiDir;
import imgui.flag.ImGuiInputTextFlags;
import imgui.flag.ImGuiStyleVar;
import imgui.flag.ImGuiWindowFlags;
import imgui.internal.ImGui;
import imgui.internal.flag.ImGuiDockNodeFlags;
import imgui.type.ImBoolean;
import imgui.ImColor;
import imgui.type.ImString;
import org.lwjgl.BufferUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL32.*;

@SuppressWarnings({"MagicNumber", "VisibilityModifier"})
final class ExampleUi {
    private static final String IMGUI_DEMO_LINK = "https://raw.githubusercontent.com/ocornut/imgui/05bc204/imgui_demo.cpp";

    private static final int COLOR_DODGERBLUE = ImColor.rgbToColor("#1E90FF");
    private static final int COLOR_CORAL = ImColor.rgbToColor("#FF7F50");
    private static final int COLOR_LIMEGREEN = ImColor.rgbToColor("#32CD32");

    // Test data for payload
    private String dropTargetText = "Drop Here";

    // Resizable input example
    private final ImString resizableStr = new ImString(5);

    // Toggles
    private final ImBoolean showBottomDockedWindow = new ImBoolean(true);
    private final ImBoolean showDemoWindow = new ImBoolean();

    // Attach image example
    private int dukeTexture = 0;

    private boolean isBottomDockedWindowInit = false;

    // To modify background color dynamically
    final float[] backgroundColor = new float[]{0.5f, 0, 0};

    void render() throws Exception {
        final int dockspaceId = ImGui.getID("MyDockSpace");
        showDockSpace(dockspaceId);
        setupBottomDockedWindow(dockspaceId);
        showBottomDockedWindow();

        final ImGuiViewport mainViewport = ImGui.getMainViewport();
        ImGui.setNextWindowSize(600, 300, ImGuiCond.Once);
        ImGui.setNextWindowPos(mainViewport.getWorkPosX() + 10, mainViewport.getWorkPosY() + 10, ImGuiCond.Once);

        ImGui.begin("Custom window");  // Start Custom window

        showWindowImage();
        showToggles();

        ImGui.separator();

        showDragNDrop();
        showBackgroundPicker();

        ImGui.separator();

        showResizableInput();

        ImGui.separator();
        ImGui.newLine();

        showDemoLink();

        ImGui.end();  // End Custom window

        if (showDemoWindow.get()) {
            ImGui.showDemoWindow(showDemoWindow);
        }
    }

    private void showDockSpace(final int dockspaceId) {
        final ImGuiViewport mainViewport = ImGui.getMainViewport();
        final int windowFlags = ImGuiWindowFlags.NoTitleBar | ImGuiWindowFlags.NoCollapse | ImGuiWindowFlags.NoResize
            | ImGuiWindowFlags.NoMove | ImGuiWindowFlags.NoBringToFrontOnFocus | ImGuiWindowFlags.NoNavFocus | ImGuiWindowFlags.NoBackground;

        ImGui.setNextWindowPos(mainViewport.getWorkPosX(), mainViewport.getWorkPosY());
        ImGui.setNextWindowSize(mainViewport.getWorkSizeX(), mainViewport.getWorkSizeY());
        ImGui.setNextWindowViewport(mainViewport.getID());
        ImGui.pushStyleVar(ImGuiStyleVar.WindowRounding, 0);
        ImGui.pushStyleVar(ImGuiStyleVar.WindowBorderSize, 0);

        ImGui.pushStyleVar(ImGuiStyleVar.WindowPadding, 0, 0);
        ImGui.begin("Dockspace Demo", windowFlags);
        ImGui.popStyleVar(3);

        ImGui.dockSpace(dockspaceId, 0, 0, ImGuiDockNodeFlags.PassthruCentralNode);
        ImGui.end();
    }

    private void setupBottomDockedWindow(final int dockspaceId) {
        if (!isBottomDockedWindowInit) {
            ImGui.dockBuilderRemoveNode(dockspaceId);
            ImGui.dockBuilderAddNode(dockspaceId, ImGuiDockNodeFlags.DockSpace);

            final int dockIdBottom = ImGui.dockBuilderSplitNode(dockspaceId, ImGuiDir.Down, .25f, null, null);

            ImGui.dockBuilderDockWindow("Bottom Docked Window", dockIdBottom);
            ImGui.dockBuilderSetNodeSize(dockIdBottom, 150f, 150f);
            ImGui.dockBuilderFinish(dockspaceId);

            isBottomDockedWindowInit = true;
        }
    }

    private void showBottomDockedWindow() {
        if (showBottomDockedWindow.get()) {
            ImGui.begin("Bottom Docked Window", showBottomDockedWindow, ImGuiWindowFlags.AlwaysAutoResize);
            ImGui.text("An example of how to create docked windows.");
            ImGui.end();
        }
    }

    private void showWindowImage() throws IOException {
        if (dukeTexture == 0) {
            dukeTexture = loadTexture(ImageIO.read(new File("src/test/resources/Duke_waving.png")));
        }

        // Draw an image in the bottom-right corner of the window
        final float xPoint = ImGui.getWindowPosX() + ImGui.getWindowSizeX() - 100;
        final float yPoint = ImGui.getWindowPosY() + ImGui.getWindowSizeY();
        ImGui.getWindowDrawList().addImage(dukeTexture, xPoint, yPoint - 180, xPoint + 100, yPoint);
    }

    private void showToggles() {
        ImGui.checkbox("Show Demo Window", showDemoWindow);
        ImGui.checkbox("Show Bottom Docked Window", showBottomDockedWindow);
        if (ImGui.button("Reset Bottom Dock Window")) {
            isBottomDockedWindowInit = false;
        }
    }

    private void showDragNDrop() {
        ImGui.button("Drag me");
        if (ImGui.beginDragDropSource()) {
            ImGui.setDragDropPayloadObject("payload_type", "Test Payload");
            ImGui.text("Drag started");
            ImGui.endDragDropSource();
        }
        ImGui.sameLine();
        ImGui.text(dropTargetText);
        if (ImGui.beginDragDropTarget()) {
            final Object payload = ImGui.acceptDragDropPayloadObject("payload_type");
            if (payload != null) {
                dropTargetText = (String) payload;
            }
            ImGui.endDragDropTarget();
        }
    }

    private void showBackgroundPicker() {
        ImGui.alignTextToFramePadding();
        ImGui.text("Background color:");
        ImGui.sameLine();
        ImGui.colorEdit3("##click_counter_col", backgroundColor, ImGuiColorEditFlags.NoInputs | ImGuiColorEditFlags.NoDragDrop);
    }

    private void showResizableInput() {
        ImGui.text("You can use text inputs with auto-resizable strings!");
        ImGui.inputText("Resizable input", resizableStr, ImGuiInputTextFlags.CallbackResize);
        ImGui.text("text len:");
        ImGui.sameLine();
        ImGui.textColored(COLOR_DODGERBLUE, Integer.toString(resizableStr.getLength()));
        ImGui.sameLine();
        ImGui.text("| buffer size:");
        ImGui.sameLine();
        ImGui.textColored(COLOR_CORAL, Integer.toString(resizableStr.getBufferSize()));
    }

    private void showDemoLink() {
        ImGui.text("Consider to look the original ImGui demo: ");
        ImGui.setNextItemWidth(500);
        ImGui.textColored(COLOR_LIMEGREEN, IMGUI_DEMO_LINK);
        ImGui.sameLine();
        if (ImGui.button("Copy")) {
            ImGui.setClipboardText(IMGUI_DEMO_LINK);
        }
    }

    private int loadTexture(final BufferedImage image) {
        final int[] pixels = new int[image.getWidth() * image.getHeight()];
        image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());

        final ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * 4); // 4 for RGBA, 3 for RGB
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                final int pixel = pixels[y * image.getWidth() + x];
                buffer.put((byte) ((pixel >> 16) & 0xFF));
                buffer.put((byte) ((pixel >> 8) & 0xFF));
                buffer.put((byte) (pixel & 0xFF));
                buffer.put((byte) ((pixel >> 24) & 0xFF));
            }
        }
        buffer.flip();

        final int textureID = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, textureID);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, image.getWidth(), image.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);

        return textureID;
    }
}
