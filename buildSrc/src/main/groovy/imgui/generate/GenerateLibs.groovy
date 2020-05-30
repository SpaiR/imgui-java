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

//        def win32 = BuildTarget.newDefaultTarget(BuildTarget.TargetOs.Windows, false)
//        def win64 = BuildTarget.newDefaultTarget(BuildTarget.TargetOs.Windows, true)
//
//        def linux32 = BuildTarget.newDefaultTarget(BuildTarget.TargetOs.Linux, false)
//        def linux64 = BuildTarget.newDefaultTarget(BuildTarget.TargetOs.Linux, true)

        def mac = BuildTarget.newDefaultTarget(BuildTarget.TargetOs.MacOsX, true)


        // Freetype Deps Config
//        win32.cppFlags += " -fstack-protector -I/usr/X11/include/freetype2 -I/usr/X11/include/libpng16 -I/usr/X11/include/harfbuzz -I/usr/X11/include/glib-2.0 -I/usr/X11/lib/glib-2.0/include"
//        win32.libraries += "-lfreetype -lbz2 -lssp"
//        win64.cppFlags += " -I/usr/X11/include/freetype2 -I/usr/X11/include/libpng16 -I/usr/X11/include/harfbuzz -I/usr/X11/include/glib-2.0 -I/usr/X11/lib/glib-2.0/include"
//        win64.libraries += "-lfreetype -lbz2 -lssp"
//        linux32.cppFlags += " -I/usr/X11/include/freetype2 -I/usr/X11/include/libpng16 -I/usr/X11/include/harfbuzz -I/usr/X11/include/glib-2.0 -I/usr/X11/lib/glib-2.0/include"
//        linux32.linkerFlags += " -lfreetype"
//        mac.compilerPrefix += "-sstdlib=libc++"
        mac.cppFlags += " -stdlib=libc++"
        mac.cppFlags += " -I/usr/local/include/freetype2 -I/usr/local/include/libpng16 -I/usr/local/include/harfbuzz -I/usr/local/include/glib-2.0 -I/usr/local/lib/glib-2.0/include"
        mac.linkerFlags += " -lfreetype"
        mac.cppFlags = mac.cppFlags.replaceAll("10.5", "10.10")
        mac.linkerFlags = mac.linkerFlags.replaceAll("10.5", "10.10")
        // End

//        new AntScriptGenerator().generate(buildConfig, win32, win64, linux32, linux64)
        new AntScriptGenerator().generate(buildConfig, mac)

        // Generate native libraries
        // Comment/uncomment lines with OS you need.
//
//        BuildExecutor.executeAnt(jniDir + '/build-windows32.xml', '-v', '-Dhas-compiler=true', '-Drelease=true', 'clean', 'postcompile')
//        BuildExecutor.executeAnt(jniDir + '/build-windows64.xml', '-v', '-Dhas-compiler=true', '-Drelease=true', 'clean', 'postcompile')
//        BuildExecutor.executeAnt(jniDir + '/build-linux32.xml', '-v', '-Dhas-compiler=true', '-Drelease=true', 'clean', 'postcompile')
//        BuildExecutor.executeAnt(jniDir + '/build-linux64.xml', '-v', '-Dhas-compiler=true', '-Drelease=true', 'clean', 'postcompile')
        BuildExecutor.executeAnt(jniDir + '/build-macosx64.xml', '-v', '-Dhas-compiler=true', '-Drelease=true', 'clean', 'postcompile')
//        BuildExecutor.executeAnt(jniDir + '/build.xml', '-v', 'pack-natives')

    }
}
