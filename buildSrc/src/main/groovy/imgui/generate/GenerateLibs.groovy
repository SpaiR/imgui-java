package imgui.generate

import com.badlogic.gdx.jnigen.*
import groovy.transform.CompileStatic
import org.gradle.api.DefaultTask
import org.gradle.api.file.CopySpec
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.TaskAction

@CompileStatic
class GenerateLibs extends DefaultTask {
    @Internal
    String group = 'build'
    @Internal
    String description = 'Generate imgui-java native binaries.'

    private final String[] buildEnvs = System.getProperty('envs')?.split(',')
    private final boolean forWin32 = buildEnvs?.contains('win32')
    private final boolean forWin64 = buildEnvs?.contains('win64')
    private final boolean forLinux32 = buildEnvs?.contains('linux32')
    private final boolean forLinux64 = buildEnvs?.contains('linux64')
    private final boolean forMac64 = buildEnvs?.contains('mac64')

    private final boolean isLocal = System.properties.containsKey("local")
    private final boolean withFreeType = System.properties.containsKey("freetype")

    private final String sourceDir = project.file('src/main/java')
    private final String classpath = project.file('build/classes/java/main')
    private final String jniDir = (isLocal ? project.buildDir.path : '/tmp/imgui') + '/jni'
    private final String tmpFolder = (isLocal ? project.buildDir.path : '/tmp/imgui') + '/tmp'
    private final String libsFolder = 'libsNative'

    @TaskAction
    void generate() {
        println 'Generating Native Libraries...'
        println "Build targets: $buildEnvs"
        println "Local: $isLocal"
        println "FreeType: $withFreeType"
        println '====================================='

        if (!buildEnvs) {
            println 'No build targets. Task is cancelled.'
            return
        }

        // Generate h/cpp files for JNI
        new NativeCodeGenerator().generate(sourceDir, classpath, jniDir)

        // Copy ImGui h/cpp files
        project.copy { CopySpec spec ->
            ['include/imgui', 'include/imnodes', 'include/imgui-node-editor', 'include/implot'].each {
                spec.from(project.rootProject.file(it)) { CopySpec s -> s.include('*.h', '*.cpp', '*.inl') }
            }
            spec.from(project.rootProject.file('imgui-binding/src/main/native'))
            spec.into(jniDir)
        }

        if (withFreeType) {
            project.copy { CopySpec spec ->
                spec.from(project.rootProject.file('include/imgui/misc/freetype')) { CopySpec it -> it.include('*.h', '*.cpp') }
                spec.into("$jniDir/misc/freetype")
            }

            enableDefine('IMGUI_ENABLE_FREETYPE')
        }

        // Generate platform dependant ant configs and header files
        def buildConfig = new BuildConfig('imgui-java', tmpFolder, libsFolder, jniDir)
        def buildTargets = [] as BuildTarget[]

        if (forWin32) {
            def win32 = BuildTarget.newDefaultTarget(BuildTarget.TargetOs.Windows, false)
            addFreeTypeIfEnabled(win32)
            buildTargets += win32
        }
        if (forWin64) {
            def win64 = BuildTarget.newDefaultTarget(BuildTarget.TargetOs.Windows, true)
            addFreeTypeIfEnabled(win64)
            buildTargets += win64
        }

        if (forLinux32) {
            def linux32 = BuildTarget.newDefaultTarget(BuildTarget.TargetOs.Linux, false)
            addFreeTypeIfEnabled(linux32)
            buildTargets += linux32
        }
        if (forLinux64) {
            def linux64 = BuildTarget.newDefaultTarget(BuildTarget.TargetOs.Linux, true)
            addFreeTypeIfEnabled(linux64)
            buildTargets += linux64
        }

        if (forMac64) {
            def minMacOsVersion = '10.15'
            def mac64 = BuildTarget.newDefaultTarget(BuildTarget.TargetOs.MacOsX, true)
            mac64.cppFlags += ' -std=c++14'
            mac64.cppFlags = mac64.cppFlags.replace('10.7', minMacOsVersion)
            mac64.linkerFlags = mac64.linkerFlags.replace('10.7', minMacOsVersion)
            addFreeTypeIfEnabled(mac64)
            buildTargets += mac64
        }

        new AntScriptGenerator().generate(buildConfig, buildTargets)

        // Generate native libraries
        // Comment/uncomment lines with OS you need.

        def commonParams = ['-v', '-Dhas-compiler=true', '-Drelease=true', 'clean', 'postcompile'] as String[]

        if (forWin32)
            BuildExecutor.executeAnt(jniDir + '/build-windows32.xml', commonParams)
        if (forWin64)
            BuildExecutor.executeAnt(jniDir + '/build-windows64.xml', commonParams)
        if (forLinux32)
            BuildExecutor.executeAnt(jniDir + '/build-linux32.xml', commonParams)
        if (forLinux64)
            BuildExecutor.executeAnt(jniDir + '/build-linux64.xml', commonParams)
        if (forMac64)
            BuildExecutor.executeAnt(jniDir + '/build-macosx64.xml', commonParams)

        BuildExecutor.executeAnt(jniDir + '/build.xml', '-v', 'pack-natives')
    }

    void addFreeTypeIfEnabled(BuildTarget target) {
        if (!withFreeType) {
            return
        }

        if (target.os == BuildTarget.TargetOs.Windows) {
            if (!target.is64Bit) {
                target.cppFlags += ' -fstack-protector'
            }
            target.libraries += '-lbz2 -lssp'
        }

        target.cppFlags += ' -I/usr/include/freetype2 -I/usr/include/libpng16 -I/usr/include/harfbuzz -I/usr/include/glib-2.0 -I/usr/lib/glib-2.0/include'
        target.libraries += ' -lfreetype'

        if (target.os == BuildTarget.TargetOs.MacOsX) {
            target.cppFlags.replace('-I/usr/include/', '-I/usr/local/include/')
        }
    }

    void enableDefine(String define) {
        project.file("$jniDir/imconfig.h").text += "#define $define"
    }
}
