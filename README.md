# imgui-java 
![CI Build](https://github.com/SpaiR/imgui-java/workflows/CI%20Build/badge.svg) [![javadoc](https://javadoc.io/badge2/io.imgui.java/imgui-java-binding/javadoc.svg)](https://javadoc.io/doc/io.imgui.java/imgui-java-binding) [![JCenter](https://img.shields.io/bintray/v/spair/io.imgui.java/imgui-java-binding.svg?label=binding)](https://bintray.com/spair/io.imgui.java/imgui-java-binding/_latestVersion) [![JCenter](https://img.shields.io/bintray/v/spair/io.imgui.java/imgui-java-lwjgl3.svg?label=lwjgl3)](https://bintray.com/spair/io.imgui.java/imgui-java-lwjgl3/_latestVersion)

JNI based binding for [Dear ImGui](https://github.com/ocornut/imgui) with no dependencies, ready to use pre-compiled binaries and renderer for [LWJGL3](https://www.lwjgl.org/).

Please read **Binding Notice** to get more info about java-specific things of the API.<br>
See official [documentation](https://github.com/ocornut/imgui#usage) and [wiki](https://github.com/ocornut/imgui/wiki) to get more info about how to do things in Dear ImGui. 

Binding provides all the data you need to render Dear ImGui. If, for some reason, you want to use your own backend renderer, see [ImGuiImplGl3](https://github.com/SpaiR/imgui-java/blob/v1.79-1.4.0/imgui-lwjgl3/src/main/java/imgui/gl3/ImGuiImplGl3.java) for reference.

Versioning semantic of the binding: `imguiVersion-bindingVersion`.<br>
For example `1.74-0.1` means that imgui-java uses `1.74` version of Dear ImGui and binding itself has the version `0.1`.

Additional available features:
 - [Multi-Viewports/Docking](#using-multi-viewports-and-docking)
 - [FreeType font renderer](#using-freetype)

## How to Try
_Make sure you have installed Java 8 or higher._

You can try Dear ImGui with Java by yourself in a three simple steps:

```
git clone --branch v1.79-1.4.0 https://github.com/SpaiR/imgui-java.git
cd imgui-java
gradlew :imgui-lwjgl3:startExample
```

That's all! You will start an example app [ImGuiGlfwExample](https://github.com/SpaiR/imgui-java/blob/v1.79-1.4.0/imgui-lwjgl3/src/test/java/ImGuiGlfwExample.java). Feel free to modify [ExampleUi](https://github.com/SpaiR/imgui-java/blob/v1.79-1.4.0/imgui-lwjgl3/src/test/java/ExampleUi.java) class to try different Dear ImGui widgets in action.

![imgui-java demo](https://i.imgur.com/WbnnhCn.gif)

## How to Use

<details>
        <summary><b>With Gradle</b></summary>

```
repositories {
    jcenter()
    mavenCentral()
}

ext {
    lwjglVersion = '3.2.3'
    imguiVersion = '1.79-1.4.0'
}

switch (OperatingSystem.current()) {
	case OperatingSystem.LINUX:
		project.ext.imguiNatives = "imgui-java-natives-linux"
		project.ext.lwjglNatives = "natives-linux"
		break
	case OperatingSystem.MAC_OS:
		project.ext.imguiNatives = "imgui-java-natives-macos"
		project.ext.lwjglNatives = "natives-macos"
		break
	case OperatingSystem.WINDOWS:
		project.ext.imguiNatives = System.getProperty("os.arch").contains("64") ? "imgui-java-natives-windows" : "imgui-java-natives-windows-x86"
		project.ext.lwjglNatives = System.getProperty("os.arch").contains("64") ? "natives-windows" : "natives-windows-x86"
		break
}

dependencies {
    implementation "io.imgui.java:imgui-java-binding:$imguiVersion"
    implementation "io.imgui.java:imgui-java-lwjgl3:$imguiVersion"
    runtimeOnly "io.imgui.java:$imguiNatives:$imguiVersion"

    implementation platform("org.lwjgl:lwjgl-bom:$lwjglVersion")

    ['', '-opengl', '-glfw'].each {
        implementation "org.lwjgl:lwjgl$it:$lwjglVersion"
        runtimeOnly "org.lwjgl:lwjgl$it::$lwjglNatives"
    }
}
```
</details>

<details>
        <summary><b>With Maven</b></summary>

```
<!-- Used to import imgui-java -->
<repositories>
    <repository>
        <id>jcenter</id>
        <url>https://jcenter.bintray.com/</url>
    </repository>
</repositories>

<properties>
    <lwjgl.version>3.2.3</lwjgl.version>
    <imgui.java.version>1.79-1.4.0</imgui.java.version>
</properties>

<!-- Resolve OS version for native libraries -->
<!-- imgui-java uses the same naming convention as LWJGL3 -->
<profiles>
    <profile>
        <id>natives-linux-amd64</id>
        <activation>
            <os>
                <family>unix</family>
                <arch>amd64</arch>
            </os>
        </activation>
        <properties>
            <imguiNatives>imgui-java-natives-linux</imguiNatives>
            <lwjglNatives>natives-linux</lwjglNatives>
        </properties>
    </profile>
    <profile>
        <id>natives-macos-amd64</id>
        <activation>
            <os>
                <family>mac</family>
                <arch>amd64</arch>
            </os>
        </activation>
        <properties>
            <imguiNatives>imgui-java-natives-macos</lwjgl.imguiNatives>
            <lwjglNatives>natives-macos</lwjgl.lwjglNatives>
        </properties>
    </profile>
    <profile>
        <id>natives-windows-amd64</id>
        <activation>
            <os>
                <family>windows</family>
                <arch>amd64</arch>
            </os>
        </activation>
        <properties>
            <imguiNatives>imgui-java-natives-windows</imguiNatives>
            <lwjglNatives>natives-windows</lwjglNatives>
        </properties>
    </profile>
    <profile>
        <id>natives-windows-x86</id>
        <activation>
            <os>
                <family>windows</family>
                <arch>x86</arch>
            </os>
        </activation>
        <properties>
            <imguiNatives>imgui-java-natives-windows-x86</imguiNatives>
            <lwjglNatives>natives-windows-x86</lwjglNatives>
        </properties>
    </profile>
</profiles>

<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.lwjgl</groupId>
            <artifactId>lwjgl-bom</artifactId>
            <version>${lwjgl.version}</version>
            <scope>import</scope>
            <type>pom</type>
        </dependency>
    </dependencies>
</dependencyManagement>

<dependencies>
    <!-- imgui-java -->
    <dependency>
        <groupId>io.imgui.java</groupId>
        <artifactId>imgui-java-binding</artifactId>
        <version>${imgui.java.version}</version>
    </dependency>
    <dependency>
        <groupId>io.imgui.java</groupId>
        <artifactId>imgui-java-lwjgl3</artifactId>
        <version>${imgui.java.version}</version>
    </dependency>
    <dependency>
        <groupId>io.imgui.java</groupId>
        <artifactId>${imguiNatives}</artifactId>
        <version>${imgui.java.version}</version>
    </dependency>

    <!-- LWJGL -->
    <dependency>
        <groupId>org.lwjgl</groupId>
        <artifactId>lwjgl</artifactId>
    </dependency>
    <dependency>
        <groupId>org.lwjgl</groupId>
        <artifactId>lwjgl-glfw</artifactId>
    </dependency>
    <dependency>
        <groupId>org.lwjgl</groupId>
        <artifactId>lwjgl-opengl</artifactId>
    </dependency>
    <dependency>
        <groupId>org.lwjgl</groupId>
        <artifactId>lwjgl</artifactId>
        <classifier>${lwjglNatives}</classifier>
    </dependency>
    <dependency>
        <groupId>org.lwjgl</groupId>
        <artifactId>lwjgl-glfw</artifactId>
        <classifier>${lwjglNatives}</classifier>
    </dependency>
    <dependency>
        <groupId>org.lwjgl</groupId>
        <artifactId>lwjgl-opengl</artifactId>
        <classifier>${lwjglNatives}</classifier>
    </dependency>
</dependencies>
```
</details>

<details>
        <summary><b>With Raw Jars</b></summary>

 - Go to the [release page](https://github.com/SpaiR/imgui-java/releases/latest)
 - Download `imgui-binding-${version}.jar`, `imgui-lwjgl3-${version}.jar` and binary libraries for your OS
   - imgui-java.dll - Windows 32bit
   - imgui-java64.dll - Windows 64bit
   - libimgui-java.so - Linux 32bit
   - libimgui-java64.so - Linux 64bit
   - libimgui-java64.dylib - MacOsX 64bit
 - Add jars to your classpath.
 - Provide a VM option: `imgui.library.path` or `java.library.path`. It should point to the folder where you've placed downloaded native libraries.
</details>

Important!<br>
If you're using native libs directly, you'll need to provide a VM option: `imgui.library.path` or `java.library.path`. It should point to the folder where you've placed downloaded binaries.

**You are ready to use imgui-java binding!**

## Using Multi-Viewports and Docking
Binding based on the Dear ImGui [docking](https://github.com/ocornut/imgui/tree/docking) branch, commit: [682249396f02b8c21e5ff333ab4a1969c89387ad](https://github.com/ocornut/imgui/tree/682249396f02b8c21e5ff333ab4a1969c89387ad).
That branch contains two important features: [multi-viewports](https://github.com/ocornut/imgui/issues/1542) and [docking](https://github.com/ocornut/imgui/issues/2109).
See an official documentation about how to work with them.

##### Important!
Take a note that multi-viewports api is **VERY** complex to implement. It's highly recommended to use `imgui.glfw.ImGuiImplGlfw` class as it's done in the example.
Otherwise, if you're using your own backed implementation, there are no guarantees it will work.

## Using FreeType
Dear ImGui by default uses a stb_strutype library to render a fonts atlas. It's possible to use FreeType instead to get better fonts quality. [Read more](https://github.com/ocornut/imgui/blob/v1.78/misc/freetype/README.md)

## Binding Notice
* All Dear ImGui methods are available in `camelCase`, not in `PascalCase`.
* To **pass** `ImVec2`/`ImVec4` - provide two/four float numbers.
  To **get** `ImVec2`/`ImVec4` - provide a destination object.
* To get an input/output to/from Dear ImGui - use primitive wrappers: `ImBool`, `ImInt` etc.
* Due to the Java and JNI restrictions we can't provide a fully fledged callbacks to the `ImGui::InputText` methods.
  To replace some features use an ImGuiInputTextData class.
* All classes which are represent Dear ImGui structs have a field with a pointer to those structs.
  Field is public and could be modified without restrictions. So by changing the pointer it's possible to use the same java class to modify different
  native structs. Commonly you don't need to mind about that.
  Just keep in mind that you are able to do advanced stuff with it like: save pointers to the structs to modify them later. 
* Read [javadoc](https://javadoc.io/doc/io.imgui.java/binding) and sources comments to get more info.

## How to Build Native Libraries
Using Windows:
 - Make sure you have installed and **available in PATH**:
    * Java 8 or higher
    * Ant
    * Mingw-w64 (recommended way: use [MSYS2](https://www.msys2.org/) and install [mingw-w64-x86_64-toolchain](https://packages.msys2.org/group/mingw-w64-x86_64-toolchain) group)
 - Build with: `gradlew :imgui-binding:generateLibs -Denvs=win64 -Dlocal`
 - Run with: `gradlew :imgui-lwjgl3:startExample -DlibPath=../imgui-binding/build/libsNative/windows64`
 
Using Linux:
 - Install dependencies: `openjdk8`, `mingw-w64-gcc`, `ant`. (Packages name could vary from system to system.)
 - Build with: `./gradlew :imgui-binding:generateLibs -Denvs=linux64 -Dlocal`
 - Run with: `./gradlew :imgui-lwjgl3:startExample -DlibPath=../imgui-binding/build/libsNative/linux64`
 
Using MacOS:
 - Check dependencies from "Using Linux" paragraph and make sure you have them installed.
 - Build with: `./gradlew :imgui-binding:generateLibs -Denvs=mac64 -Dlocal`
 - Run with: `./gradlew :imgui-lwjgl3:startExample -DlibPath=../imgui-binding/build/libsNative/macosx64`

In `envs` parameter next values could be used `win32`, `win64`, `linux32`, `linux64` or `mac64`.<br>
`-Dlocal` is optional and means that natives will be built under the `./imgui-binding/build/` folder. Otherwise `/tmp/imgui` folder will be used.
On Windows OS always use local build.

## Credits
Binding partly based on the work of [xpenatan](https://github.com/xpenatan) and his version [jDear-imgui](https://github.com/xpenatan/jDear-imgui).

## License
See the LICENSE file for license rights and limitations (Apache-2.0).
