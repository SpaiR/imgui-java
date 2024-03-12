package imgui;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.Properties;

public class ImGui {
    private static final String LIB_PATH_PROP = "imgui.library.path";
    private static final String LIB_NAME_PROP = "imgui.library.name";
    private static final String LIB_NAME_DEFAULT = System.getProperty("os.arch").contains("64") ? "imgui-java64" : "imgui-java";
    private static final String LIB_TMP_DIR_PREFIX = "imgui-java-natives";

    static {
        final String libPath = System.getProperty(LIB_PATH_PROP);
        final String libName = System.getProperty(LIB_NAME_PROP, LIB_NAME_DEFAULT);
        final String fullLibName = resolveFullLibName();

        if (libPath != null) {
            System.load(Paths.get(libPath).resolve(fullLibName).toAbsolutePath().toString());
        } else {
            try {
                System.loadLibrary(libName);
            } catch (Exception | Error e) {
                final String extractedLibAbsPath = tryLoadFromClasspath(fullLibName);
                if (extractedLibAbsPath != null) {
                    System.load(extractedLibAbsPath);
                } else {
                    throw e;
                }
            }
        }

        nInitJni();

        ImFontAtlas.nInit();
        ImGuiPlatformIO.init();

        setAssertCallback(new imgui.assertion.ImAssertCallback() {
            @Override
            public void imAssertCallback(final String assertion, final int line, final String file) {
                System.err.println("Dear ImGui Assertion Failed: " + assertion);
                System.err.println("Assertion Located At: " + file + ":" + line);
                Thread.dumpStack();
            }
        });
    }

    private static String resolveFullLibName() {
        final boolean isWin = System.getProperty("os.name").toLowerCase().contains("win");
        final boolean isMac = System.getProperty("os.name").toLowerCase().contains("mac");
        final String libPrefix;
        final String libSuffix;

        if (isWin) {
            libPrefix = "";
            libSuffix = ".dll";
        } else if (isMac) {
            libPrefix = "lib";
            libSuffix = ".dylib";
        } else {
            libPrefix = "lib";
            libSuffix = ".so";
        }

        return System.getProperty(LIB_NAME_PROP, libPrefix + LIB_NAME_DEFAULT + libSuffix);
    }

    // This method tries to unpack the library binary from classpath into the temp dir.
    private static String tryLoadFromClasspath(final String fullLibName) {
        try (InputStream is = ImGui.class.getClassLoader().getResourceAsStream("io/imgui/java/native-bin/" + fullLibName)) {
            if (is == null) {
                return null;
            }

            final String version = getVersionString().orElse("undefined");
            final Path tmpDir = Paths.get(System.getProperty("java.io.tmpdir")).resolve(LIB_TMP_DIR_PREFIX).resolve(version);
            if (!Files.exists(tmpDir)) {
                Files.createDirectories(tmpDir);
            }

            final Path libBin = tmpDir.resolve(fullLibName);
            try {
                Files.copy(is, libBin, StandardCopyOption.REPLACE_EXISTING);
            } catch (AccessDeniedException e) {
                if (!Files.exists(libBin)) {
                    throw e;
                }
            }

            return libBin.toAbsolutePath().toString();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static Optional<String> getVersionString() {
        final Properties properties = new Properties();
        try (InputStream is = ImGui.class.getResourceAsStream("/imgui/imgui-java.properties")) {
            if (is != null) {
                properties.load(is);
                return Optional.of(properties.get("imgui.java.version").toString());
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        return Optional.empty();
    }

    /**
     * For internal usage.
     * Method is used to initiate static instantiation (loading of the native libraries etc.).
     * Otherwise, native libraries will be loaded on demand and natively mapped objects won't work.
     */
    public static void init() {
    }

    /*JNI
        #include "_common.h"
     */

    private static native void nInitJni(); /*
        Jni::InitJvm(env);
        Jni::InitCommon(env);
        Jni::InitAssertion(env);
        Jni::InitCallbacks(env);
        Jni::InitBindingStruct(env);
    */

    /**
     * Set a custom assertion callback for ImGui assertions.
     * Take note: Any custom assertion callback SHOULD NOT throw any exception.
     * After any callback the application will be terminated, any attempt to bypass this behavior
     * will result in a EXCEPTION_ACCESS_VIOLATION from within the native layer.
     *
     * @param callback The custom ImGui assertion callback
     */
    public static native void setAssertCallback(imgui.assertion.ImAssertCallback callback); /*
        Jni::SetAssertionCallback(callback);
    */
}
