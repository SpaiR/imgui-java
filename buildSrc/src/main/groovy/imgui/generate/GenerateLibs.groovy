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
            spec.from(project.rootProject.file('imgui-binding/src/main/native'))
            spec.into(jniDir)
        }

        // Generate platform dependant ant configs and header files
        def buildConfig = new BuildConfig('imgui-java', tmpFolder, libsFolder, jniDir)

        def win32 = BuildTarget.newDefaultTarget(BuildTarget.TargetOs.Windows, false)
        win32.compilerPrefix = 'mingw32-'
        def win64 = BuildTarget.newDefaultTarget(BuildTarget.TargetOs.Windows, true)

        def linux32 = BuildTarget.newDefaultTarget(BuildTarget.TargetOs.Linux, false)
        def linux64 = BuildTarget.newDefaultTarget(BuildTarget.TargetOs.Linux, true)

        new AntScriptGenerator().generate(buildConfig, win32, win64)

        // Generate native libraries
        // Comment/uncomment lines with OS you need.

        BuildExecutor.executeAnt(jniDir + '/build-windows32.xml', '-v', '-Dhas-compiler=true', '-Drelease=true', 'clean', 'postcompile')
        BuildExecutor.executeAnt(jniDir + '/build-windows64.xml', '-v', '-Dhas-compiler=true', '-Drelease=true', 'clean', 'postcompile')
        //BuildExecutor.executeAnt(jniDir + '/build-linux32.xml', '-v', '-Dhas-compiler=true', '-Drelease=true', 'clean', 'postcompile')
        //BuildExecutor.executeAnt(jniDir + '/build-linux64.xml', '-v', '-Dhas-compiler=true', '-Drelease=true', 'clean', 'postcompile')
        BuildExecutor.executeAnt(jniDir + '/build.xml', '-v', 'pack-natives')

        // Ant creates this folder in the root of the project. Since it will be empty we delete it
        project.rootProject.file(libsFolder).deleteDir()
    }
}
