import imgui.ImFontConfig;
import imgui.ImFontGlyphRangesBuilder;
import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.ImGuiPlatformIO;
import imgui.ImTextureData;
import imgui.app.Application;
import imgui.app.Configuration;
import imgui.flag.ImGuiBackendFlags;
import imgui.flag.ImGuiConfigFlags;
import imgui.flag.ImGuiInputTextFlags;
import imgui.flag.ImGuiWindowFlags;
import imgui.flag.ImTextureFormat;
import imgui.flag.ImTextureStatus;
import imgui.type.ImString;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main extends Application {
    private final ImString str = new ImString(5);
    private final float[] flt = new float[1];
    private int count = 0;

    @Override
    protected void configure(final Configuration config) {
        config.setTitle("Example Application");
    }

    @Override
    protected void initImGui(final Configuration config) {
        super.initImGui(config);

        final ImGuiIO io = ImGui.getIO();
        io.setIniFilename(null);                                // We don't want to save .ini file
        io.addConfigFlags(ImGuiConfigFlags.NavEnableKeyboard);  // Enable Keyboard Controls
        io.addConfigFlags(ImGuiConfigFlags.DockingEnable);      // Enable Docking
        io.addConfigFlags(ImGuiConfigFlags.ViewportsEnable);    // Enable Multi-Viewport / Platform Windows
        io.setConfigViewportsNoTaskBarIcon(true);

        initFonts(io);
    }

    /**
     * Example of fonts configuration
     * For more information read: https://github.com/ocornut/imgui/blob/33cdbe97b8fd233c6c12ca216e76398c2e89b0d8/docs/FONTS.md
     */
    private void initFonts(final ImGuiIO io) {
        // This enables FreeType font renderer, which is disabled by default.
        io.getFonts().setFreeTypeRenderer(true);

        // Add default font for latin glyphs
        io.getFonts().addFontDefault();

        // You can use the ImFontGlyphRangesBuilder helper to create glyph ranges based on text input.
        // For example: for a game where your script is known, if you can feed your entire script to it (using addText) and only build the characters the game needs.
        // Here we are using it just to combine all required glyphs in one place
        final ImFontGlyphRangesBuilder rangesBuilder = new ImFontGlyphRangesBuilder(); // Glyphs ranges provide
        rangesBuilder.addRanges(io.getFonts().getGlyphRangesDefault());
        rangesBuilder.addRanges(io.getFonts().getGlyphRangesCyrillic());
        rangesBuilder.addRanges(io.getFonts().getGlyphRangesJapanese());
        rangesBuilder.addRanges(FontAwesomeIcons._IconRange);

        // Font config for additional fonts
        // This is a natively allocated struct so don't forget to call destroy after atlas is built
        final ImFontConfig fontConfig = new ImFontConfig();
        fontConfig.setMergeMode(true);  // Enable merge mode to merge cyrillic, japanese and icons with default font

        final short[] glyphRanges = rangesBuilder.buildRanges();
        io.getFonts().addFontFromMemoryTTF(loadFromResources("Tahoma.ttf"), 14, fontConfig, glyphRanges); // cyrillic glyphs
        io.getFonts().addFontFromMemoryTTF(loadFromResources("NotoSansCJKjp-Medium.otf"), 14, fontConfig, glyphRanges); // japanese glyphs
        io.getFonts().addFontFromMemoryTTF(loadFromResources("fa-regular-400.ttf"), 14, fontConfig, glyphRanges); // font awesome
        io.getFonts().addFontFromMemoryTTF(loadFromResources("fa-solid-900.ttf"), 14, fontConfig, glyphRanges); // font awesome
        io.getFonts().build();

        fontConfig.destroy();
    }

    @Override
    public void process() {
        if (ImGui.begin("Demo", ImGuiWindowFlags.AlwaysAutoResize)) {
            ImGui.text("OS: [" + System.getProperty("os.name") + "] Arch: [" + System.getProperty("os.arch") + "]");
            ImGui.text("Hello, World! " + FontAwesomeIcons.Smile);
            if (ImGui.button(FontAwesomeIcons.Save + " Save")) {
                count++;
            }
            ImGui.sameLine();
            ImGui.text(String.valueOf(count));
            ImGui.sameLine();
            // imgui 1.91 TextLink — inline hyperlink widget, returns true when clicked.
            if (ImGui.textLink("increment")) {
                count++;
            }
            ImGui.inputText("string", str, ImGuiInputTextFlags.CallbackResize);
            ImGui.text("Result: " + str.get());
            ImGui.sliderFloat("float", flt, 0, 1);

            // imgui 1.91 TextLinkOpenURL — hyperlink that opens a URL on click.
            ImGui.text("Learn more:");
            ImGui.sameLine();
            ImGui.textLinkOpenURL("Dear ImGui on GitHub", "https://github.com/ocornut/imgui");

            ImGui.separator();
            ImGui.text("Extra");
            Extra.show(this);

            ImGui.separator();
            showTextureManagement();
        }
        ImGui.end();
    }

    /**
     * Demonstrates Dear ImGui 1.92's texture-management system (ImGuiBackendFlags_RendererHasTextures).
     * <p>
     * The GL3 backend now drives texture create/update/destroy through {@link ImTextureData}, so the font atlas is
     * uploaded (and incrementally updated) automatically each frame. Here we simply inspect the live texture list that
     * the backend maintains and render the managed atlas texture inline.
     */
    private void showTextureManagement() {
        if (!ImGui.collapsingHeader("Texture Management")) {
            return;
        }

        final ImGuiIO io = ImGui.getIO();
        final boolean hasTextures = io.hasBackendFlags(ImGuiBackendFlags.RendererHasTextures);
        ImGui.text("RendererHasTextures: " + (hasTextures ? "enabled" : "disabled"));

        final ImGuiPlatformIO platformIO = ImGui.getPlatformIO();
        final int texturesCount = platformIO.getTexturesSize();
        ImGui.text("Textures managed by Dear ImGui: " + texturesCount);

        for (int i = 0; i < texturesCount; i++) {
            final ImTextureData tex = platformIO.getTextures(i);
            ImGui.bulletText(String.format(
                "#%d  %dx%d  %s  status=%s  texID=%d",
                tex.getUniqueID(), tex.getWidth(), tex.getHeight(),
                formatName(tex.getFormat()), statusName(tex.getStatus()), tex.getTexID()));

            // Render any RGBA texture that has already been uploaded (the font atlas is the usual one).
            if (tex.getStatus() == ImTextureStatus.OK && tex.getTexID() != 0 && tex.getFormat() == ImTextureFormat.RGBA32) {
                final float preview = 256.0f;
                final float aspect = tex.getHeight() / (float) tex.getWidth();
                ImGui.image(tex.getTexID(), preview, preview * aspect);
            }
        }
    }

    private static String formatName(final int format) {
        if (format == ImTextureFormat.RGBA32) {
            return "RGBA32";
        } else if (format == ImTextureFormat.Alpha8) {
            return "Alpha8";
        }
        return "Unknown(" + format + ")";
    }

    private static String statusName(final int status) {
        if (status == ImTextureStatus.OK) {
            return "OK";
        } else if (status == ImTextureStatus.WantCreate) {
            return "WantCreate";
        } else if (status == ImTextureStatus.WantUpdates) {
            return "WantUpdates";
        } else if (status == ImTextureStatus.WantDestroy) {
            return "WantDestroy";
        } else if (status == ImTextureStatus.Destroyed) {
            return "Destroyed";
        }
        return "Unknown(" + status + ")";
    }

    private static byte[] loadFromResources(String name) {
        try {
            return Files.readAllBytes(Paths.get(Main.class.getResource(name).toURI()));
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(final String[] args) {
        launch(new Main());
        System.exit(0);
    }
}
