package tool.generator

import com.badlogic.gdx.jnigen.*
import com.badlogic.gdx.utils.Architecture
import com.badlogic.gdx.utils.Os
import groovy.transform.CompileStatic
import org.gradle.api.DefaultTask
import org.gradle.api.file.CopySpec
import org.gradle.api.file.DuplicatesStrategy
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.TaskAction

@CompileStatic
class GenerateLibs extends DefaultTask {
    private static final String[] INCLUDES = [
        'include/imgui',
        'include/imnodes',
        'include/imgui-node-editor',
        'include/imguizmo',
        'include/implot',
//        'include/ImGuiColorTextEdit',
//        'include/ImGuiFileDialog',
        'include/imgui_club/imgui_memory_editor',
        'include/imgui-knobs'
    ]

    @Internal
    String group = 'build'
    @Internal
    String description = 'Generate imgui-java native binaries.'

    private final String[] buildEnvs = System.getProperty('envs')?.split(',')
    private final boolean forWindows = buildEnvs?.contains('windows')
    private final boolean forLinux = buildEnvs?.contains('linux')
    private final boolean forLinuxArm64 = buildEnvs?.contains('linuxarm64')
    private final boolean forMac = buildEnvs?.contains('macos')
    private final boolean forMacArm64 = buildEnvs?.contains('macosarm64')

    private final boolean isLocal = System.properties.containsKey('local')
    private final boolean withFreeType = Boolean.valueOf(System.properties.getProperty('freetype', 'false'))

    private final String sourceDir = project.file('src/generated/java')
    private final String classpath = project.file('build/classes/java/main')
    private final String rootDir = (isLocal ? project.buildDir.path : '/tmp/imgui')
    private final String jniDir = "$rootDir/jni"
    private final String tmpDir = "$rootDir/tmp"
    private final String libsDirName = 'libsNative'

    @TaskAction
    void generate() {
        println 'Generating Native Libraries...'
        println "Build targets: $buildEnvs"
        println "Local: $isLocal"
        println "FreeType: $withFreeType"
        println '====================================='

        if (!buildEnvs) {
            throw new IllegalStateException('No build targets')
        }

        new File(jniDir).deleteDir()
        new File(tmpDir).deleteDir()
        new File("$rootDir/$libsDirName").deleteDir()

        // Generate h/cpp files for JNI
        new NativeCodeGenerator().generate(sourceDir, classpath, jniDir)

        // Copy ImGui h/cpp files
        project.copy { CopySpec spec ->
            INCLUDES.each {
                spec.from(project.rootProject.file(it)) { CopySpec s -> s.include('*.h', '*.cpp', '*.inl') }
            }
            spec.from(project.rootProject.file('imgui-binding/src/main/native'))
            spec.into(jniDir)
            spec.duplicatesStrategy = DuplicatesStrategy.INCLUDE // Allows for duplicate imconfig.h, we ensure the correct one is copied below
        }

        // Ensure we overwrite imconfig.h with our own
        project.copy { CopySpec spec ->
            spec.from(project.rootProject.file('imgui-binding/src/main/native/imconfig.h'))
            spec.into(jniDir)
        }

        if (withFreeType) {
            project.copy { CopySpec spec ->
                spec.from(project.rootProject.file('include/imgui/misc/freetype')) { CopySpec it -> it.include('*.h', '*.cpp') }
                spec.into("$jniDir/misc/freetype")
            }

            // Since we give a possibility to build library without enabled freetype - define should be set like that.
            replaceSourceFileContent("imconfig.h", "//#define IMGUI_ENABLE_FREETYPE", "#define IMGUI_ENABLE_FREETYPE")

            // Binding specific behavior to handle FreeType.
            // By defining IMGUI_ENABLE_FREETYPE, Dear ImGui will default to using the FreeType font renderer.
            // However, we modify the source code to ensure that, even with this, the STB_TrueType renderer is used instead.
            // To use the FreeType font renderer, it must be explicitly forced on the atlas manually.
            replaceSourceFileContent("imgui_draw.cpp", "ImGuiFreeType::GetBuilderForFreeType()", "ImFontAtlasGetBuilderForStbTruetype()")
        }

        // Copy dirent for ImGuiFileDialog
        project.copy { CopySpec spec ->
            spec.from(project.rootProject.file('include/ImGuiFileDialog/dirent')) { CopySpec s -> s.include('*.h', '*.cpp', '*.inl') }
            spec.into(jniDir + '/dirent')
        }

        // Generate platform dependant ant configs and header files
        def buildConfig = new BuildConfig('imgui-java', tmpDir, libsDirName, jniDir)
        BuildTarget[] buildTargets = []

        if (forWindows) {
            def win64 = BuildTarget.newDefaultTarget(Os.Windows, Architecture.Bitness._64)
            addFreeTypeIfEnabled(win64)
            buildTargets += win64
        }

        if (forLinux) {
            buildTargets += createLinuxTarget(Architecture.x86)
        }

        if (forLinuxArm64) {
            buildTargets += createLinuxTarget(Architecture.ARM)
        }

        if (forMac) {
            buildTargets += createMacTarget(Architecture.x86)
        }

        if (forMacArm64) {
            buildTargets += createMacTarget(Architecture.ARM)
        }

        new AntScriptGenerator().generate(buildConfig, buildTargets)

        // Generate native libraries
        // Comment/uncomment lines with OS you need.

        def commonParams = ['-v', '-Dhas-compiler=true', '-Drelease=true', 'clean', 'postcompile'] as String[]

        if (forWindows)
            BuildExecutor.executeAnt(jniDir + '/build-windows64.xml', commonParams)
        if (forLinux)
            BuildExecutor.executeAnt(jniDir + '/build-linux64.xml', commonParams)
        if (forLinuxArm64)
            BuildExecutor.executeAnt(jniDir + '/build-linuxarm64.xml', commonParams)
        if (forMac)
            BuildExecutor.executeAnt(jniDir + '/build-macosx64.xml', commonParams)
        if (forMacArm64)
            BuildExecutor.executeAnt(jniDir + '/build-macosxarm64.xml', commonParams)

        BuildExecutor.executeAnt(jniDir + '/build.xml', '-v', 'pack-natives')

        if (forWindows)
            checkLibExist("windows64/imgui-java64.dll")
        if (forLinux)
            checkLibExist("linux64/libimgui-java64.so")
        if (forLinuxArm64)
            checkLibExist("linuxarm64/libimgui-java64.so")
        if (forMac)
            checkLibExist("macosx64/libimgui-java64.dylib")
        if (forMacArm64)
            checkLibExist("macosxarm64/libimgui-java64.dylib")
    }

    void checkLibExist(String libName) {
        def path = new File("$rootDir/$libsDirName/$libName")
        if (!path.exists()) {
            logger.error("Failed to build $libName!")
            throw new IllegalStateException("$path does not exist")
        }
    }

    BuildTarget createLinuxTarget(Architecture arch) {
        def linuxTarget = BuildTarget.newDefaultTarget(Os.Linux, Architecture.Bitness._64, arch)
        linuxTarget.compilerPrefix = "" // TODO: test with GNU gcc
        linuxTarget.libName = "libimgui-java64.so" // Lib for arm64 will be named the same for consistency
        addFreeTypeIfEnabled(linuxTarget)
        return linuxTarget
    }

    BuildTarget createMacTarget(Architecture arch) {
        def minMacOsVersion = '10.15'
        def macTarget = BuildTarget.newDefaultTarget(Os.MacOsX, Architecture.Bitness._64, arch)
        macTarget.libName = "libimgui-java64.dylib" // Lib for arm64 will be named the same for consistency.
        macTarget.cppFlags += ' -std=c++14'
        macTarget.cppFlags = macTarget.cppFlags.replace('10.7', minMacOsVersion)
        macTarget.linkerFlags = macTarget.linkerFlags.replace('10.7', minMacOsVersion)
        addFreeTypeIfEnabled(macTarget)
        return macTarget
    }

    void addFreeTypeIfEnabled(BuildTarget target) {
        if (!withFreeType) {
            return
        }

        def freetypeVendorDir = project.rootProject.file('build/vendor/freetype')
        if (!freetypeVendorDir.exists()) {
            logger.error("$freetypeVendorDir doesn't exist! Run 'buildSrc/scripts/vendor_freetype.sh' for your platform beforehand!")
            throw new IllegalStateException("Unable to build library for FreeType")
        }

        target.cppFlags += " -I$freetypeVendorDir/include"
        target.linkerFlags += " -L${project.rootProject.file("$freetypeVendorDir/lib")}"
        target.libraries += ' -lfreetype'
    }

    void replaceSourceFileContent(String fileName, String replaceWhat, String replaceWith) {
        def sourceFile = new File("$jniDir/$fileName")
        def sourceTxt = sourceFile.text
        def sourceTxtModified = sourceTxt.replace(replaceWhat, replaceWith)
        if (sourceTxt == sourceTxtModified) {
            throw new IllegalStateException("Unable to replace [$fileName] with content [$replaceWith]!")
        }
        sourceFile.text = sourceTxtModified
    }
}
