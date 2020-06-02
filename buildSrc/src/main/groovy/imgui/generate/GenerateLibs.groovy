package imgui.generate

import com.badlogic.gdx.jnigen.*
import groovy.transform.CompileStatic
import org.gradle.api.DefaultTask
import org.gradle.api.file.CopySpec
import org.gradle.api.tasks.TaskAction

@CompileStatic
class GenerateLibs extends DefaultTask {
    String description = 'Generates native libraries using classes under ":imgui-binding" and Dear-ImGui itself from "imgui" submodule.'

    private final String sourceDir = project.file('src/main/java')
    private final String classpath = project.file('build/classes/java/main')
    private final String jniDir = project.buildDir.path + '/jni'
    private final String tmpFolder = project.buildDir.path + '/tmp'
    private final String libsFolder = 'libsNative'

    @TaskAction
    void generate() {
        // Generate h/cpp files for JNI
        new NativeCodeGenerator().generate(sourceDir, classpath, jniDir)

        // Copy ImGui h/cpp files
        project.copy { CopySpec spec ->
            spec.from(project.rootProject.file('imgui')) { CopySpec it -> it.include('*.h', '*.cpp') }
            spec.from(project.rootProject.file('imgui/misc/freetype')) { CopySpec it -> it.include('*.h', '*.cpp') }
            spec.from(project.rootProject.file('imgui-binding/src/main/native'))
            spec.into(jniDir)
        }

        // Generate platform dependant ant configs and header files
        def buildConfig = new BuildConfig('imgui-java', tmpFolder, libsFolder, jniDir)

        def win32 = BuildTarget.newDefaultTarget(BuildTarget.TargetOs.Windows, false)
        def win64 = BuildTarget.newDefaultTarget(BuildTarget.TargetOs.Windows, true)

        def linux32 = BuildTarget.newDefaultTarget(BuildTarget.TargetOs.Linux, false)
        def linux64 = BuildTarget.newDefaultTarget(BuildTarget.TargetOs.Linux, true)

        def mac64 = BuildTarget.newDefaultTarget(BuildTarget.TargetOs.MacOsX, true)
        mac64.cppFlags += " -stdlib=libc++"
        mac64.cppFlags = mac64.cppFlags.replaceAll("10.5", "10.9")
        mac64.linkerFlags = mac64.linkerFlags.replaceAll("10.5", "10.9")

        // Freetype Deps Config
        win32.cppFlags += " -fstack-protector -I/usr/include/freetype2 -I/usr/include/libpng16 -I/usr/include/harfbuzz -I/usr/include/glib-2.0 -I/usr/lib/glib-2.0/include"
        win32.libraries += "-lfreetype -lbz2 -lssp"
        win64.cppFlags += " -I/usr/include/freetype2 -I/usr/include/libpng16 -I/usr/include/harfbuzz -I/usr/include/glib-2.0 -I/usr/lib/glib-2.0/include"
        win64.libraries += "-lfreetype -lbz2 -lssp"
        linux32.cppFlags += " -I/usr/include/freetype2 -I/usr/include/libpng16 -I/usr/include/harfbuzz -I/usr/include/glib-2.0 -I/usr/lib/glib-2.0/include"
        linux32.linkerFlags += " -lfreetype"
        linux64.cppFlags += " -I/usr/include/freetype2 -I/usr/include/libpng16 -I/usr/include/harfbuzz -I/usr/include/glib-2.0 -I/usr/lib/glib-2.0/include"
        linux64.linkerFlags += " -lfreetype"
        mac64.cppFlags += " -I/usr/local/include/freetype2 -I/usr/local/include/libpng16 -I/usr/local/include/harfbuzz -I/usr/local/include/glib-2.0 -I/usr/local/lib/glib-2.0/include"
        mac64.linkerFlags += " -lfreetype"
        // End

        new AntScriptGenerator().generate(buildConfig, win32, win64, linux32, linux64)

        // Generate native libraries
        // Comment/uncomment lines with OS you need.

        BuildExecutor.executeAnt(jniDir + '/build-windows32.xml', '-v', '-Dhas-compiler=true', '-Drelease=true', 'clean', 'postcompile')
        BuildExecutor.executeAnt(jniDir + '/build-windows64.xml', '-v', '-Dhas-compiler=true', '-Drelease=true', 'clean', 'postcompile')
        BuildExecutor.executeAnt(jniDir + '/build-linux32.xml', '-v', '-Dhas-compiler=true', '-Drelease=true', 'clean', 'postcompile')
        BuildExecutor.executeAnt(jniDir + '/build-linux64.xml', '-v', '-Dhas-compiler=true', '-Drelease=true', 'clean', 'postcompile')
        BuildExecutor.executeAnt(jniDir + '/build-macosx64.xml', '-v', '-Dhas-compiler=true', '-Drelease=true', 'clean', 'postcompile')
        BuildExecutor.executeAnt(jniDir + '/build.xml', '-v', 'pack-natives')
    }
}
