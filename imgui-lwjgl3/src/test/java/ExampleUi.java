import imgui.ImBool;
import imgui.ImColor;
import imgui.ImGui;
import imgui.ImString;
import imgui.ImVec2;
import imgui.flag.ImGuiColorEditFlags;
import imgui.flag.ImGuiCond;
import imgui.flag.ImGuiInputTextFlags;
import org.lwjgl.BufferUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL32.*;

@SuppressWarnings({"MagicNumber", "VisibilityModifier"})
final class ExampleUi {
    private static final String IMGUI_DEMO_LINK = "https://raw.githubusercontent.com/ocornut/imgui/v1.76/imgui_demo.cpp";

    private static final int DODGERBLUE_COLOR = ImColor.rgbToColor("#1E90FF");
    private static final int CORAL_COLOR = ImColor.rgbToColor("#FF7F50");
    private static final int LIMEGREEN_COLOR = ImColor.rgbToColor("#32CD32");

    // Test data for payload
    private final byte[] testPayload = "Test Payload".getBytes();
    private String dropTargetText = "Drop Here";

    // To modify background color dynamically
    final float[] backgroundColor = new float[]{0.5f, 0, 0};

    // Resizable input example
    private final ImString resizableStr = new ImString(5);
    private final ImBool showDemoWindow = new ImBool();

    // Attach image example
    private int dukeTexture;
    final ImVec2 windowSize = new ImVec2(); // Vector to store "Custom Window" size
    final ImVec2 windowPos = new ImVec2(); // Vector to store "Custom Window" position

    void init() throws Exception {
        dukeTexture = loadTexture(ImageIO.read(new File("src/test/resources/Duke_waving.png")));
    }

    void render() {
        ImGui.setNextWindowSize(600, 300, ImGuiCond.Once);
        ImGui.setNextWindowPos(10, 10, ImGuiCond.Once);

        ImGui.begin("Custom window");  // Start Custom window

        // Draw an image in the bottom-right corner of the window
        ImGui.getWindowSize(windowSize);
        ImGui.getWindowPos(windowPos);
        final float xPoint = windowPos.x + windowSize.x - 100;
        final float yPoint = windowPos.y + windowSize.y;
        ImGui.getWindowDrawList().addImage(dukeTexture, xPoint, yPoint - 180, xPoint + 100, yPoint);

        // Checkbox to show demo window
        ImGui.checkbox("Show demo window", showDemoWindow);

        ImGui.separator();

        // Drag'n'Drop functionality
        ImGui.button("Drag me");
        if (ImGui.beginDragDropSource()) {
            ImGui.setDragDropPayload("payload_type", testPayload, testPayload.length);
            ImGui.text("Drag started");
            ImGui.endDragDropSource();
        }
        ImGui.sameLine();
        ImGui.text(dropTargetText);
        if (ImGui.beginDragDropTarget()) {
            final byte[] payload = ImGui.acceptDragDropPayload("payload_type");
            if (payload != null) {
                dropTargetText = new String(payload);
            }
            ImGui.endDragDropTarget();
        }

        // Color picker
        ImGui.alignTextToFramePadding();
        ImGui.text("Background color:");
        ImGui.sameLine();
        ImGui.colorEdit3("##click_counter_col", backgroundColor, ImGuiColorEditFlags.NoInputs | ImGuiColorEditFlags.NoDragDrop);

        ImGui.separator();

        // Input field with auto-resize ability
        ImGui.text("You can use text inputs with auto-resizable strings!");
        ImGui.inputText("Resizable input", resizableStr, ImGuiInputTextFlags.CallbackResize);
        ImGui.text("text len:");
        ImGui.sameLine();
        ImGui.textColored(DODGERBLUE_COLOR, Integer.toString(resizableStr.getLength()));
        ImGui.sameLine();
        ImGui.text("| buffer size:");
        ImGui.sameLine();
        ImGui.textColored(CORAL_COLOR, Integer.toString(resizableStr.getBufferSize()));

        ImGui.separator();
        ImGui.newLine();

        // Link to the original demo file
        ImGui.text("Consider to look the original ImGui demo: ");
        ImGui.setNextItemWidth(500);
        ImGui.textColored(LIMEGREEN_COLOR, IMGUI_DEMO_LINK);
        ImGui.sameLine();
        if (ImGui.button("Copy")) {
            ImGui.setClipboardText(IMGUI_DEMO_LINK);
        }

        ImGui.end();  // End Custom window

        if (showDemoWindow.get()) {
            ImGui.showDemoWindow(showDemoWindow);
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
