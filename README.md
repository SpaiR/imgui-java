# imgui-java 
[![Build Status](https://travis-ci.org/SpaiR/imgui-java.svg?branch=master)](https://travis-ci.org/SpaiR/imgui-java) [![javadoc](https://javadoc.io/badge2/io.imgui.java/imgui-java-binding/javadoc.svg)](https://javadoc.io/doc/io.imgui.java/imgui-java-binding) [![JCenter](https://img.shields.io/bintray/v/spair/io.imgui.java/imgui-java-binding.svg?label=binding)](https://bintray.com/spair/io.imgui.java/imgui-java-binding/_latestVersion) [![JCenter](https://img.shields.io/bintray/v/spair/io.imgui.java/imgui-java-lwjgl3.svg?label=lwjgl3)](https://bintray.com/spair/io.imgui.java/imgui-java-lwjgl3/_latestVersion)

JNI based binding for [Dear ImGui](https://github.com/ocornut/imgui) with no dependencies, ready to use pre-compiled binaries and renderer for [LWJGL3](https://www.lwjgl.org/).

Please read **Binding Notice** to get more info about java-specific things of the API.<br>
See official [documentation](https://github.com/ocornut/imgui#usage) and [wiki](https://github.com/ocornut/imgui/wiki) to get more info about how to do things in Dear ImGui. 

Binding provides all the data you need to render Dear ImGui. If, for some reason, you want to use your own backend renderer, see [ImGuiImplGl3](https://github.com/SpaiR/imgui-java/blob/v1.77-0.17.2/imgui-lwjgl3/src/main/java/imgui/gl3/ImGuiImplGl3.java) for reference.

Versioning semantic of the binding: `imguiVersion-bindingVersion`.<br>
For example `1.74-0.1` means that imgui-java uses `1.74` version of Dear ImGui and binding itself has the version `0.1`.

Additional available features:
 - [Multi-Viewports/Docking API](#using-multi-viewports-and-docking)
 - [FreeType font renderer](#using-freetype)

## How to Try
_Make sure you have installed Java 8 or higher._

You can try Dear ImGui with Java by yourself in a three simple steps:

```
git clone --branch v1.77-0.17.2 https://github.com/SpaiR/imgui-java.git
cd imgui-java
gradlew :imgui-lwjgl3:startExample
```

That's all! You will start an example app [ImGuiGlfwExample](https://github.com/SpaiR/imgui-java/blob/v1.77-0.17.2/imgui-lwjgl3/src/test/java/ImGuiGlfwExample.java). Feel free to modify [ExampleUi](https://github.com/SpaiR/imgui-java/blob/v1.77-0.17.2/imgui-lwjgl3/src/test/java/ExampleUi.java) class to try different Dear ImGui widgets in action.

![imgui-java demo](https://i.imgur.com/ljAhD7a.gif)

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
    imguiVersion = '1.77-0.17.2'
}

switch (OperatingSystem.current()) {
	case OperatingSystem.LINUX:
		project.ext.natives = "natives-linux"
		break
	case OperatingSystem.MAC_OS:
		project.ext.natives = "natives-macos"
		break
	case OperatingSystem.WINDOWS:
		project.ext.natives = System.getProperty("os.arch").contains("64") ? "natives-windows" : "natives-windows-x86"
		break
}

dependencies {
    implementation "io.imgui.java:binding:$imguiVersion"
    implementation "io.imgui.java:lwjgl3:$imguiVersion"
    runtimeOnly "io.imgui.java:$natives:$imguiVersion"

    implementation platform("org.lwjgl:lwjgl-bom:$lwjglVersion")

    ['', '-opengl', '-glfw'].each {
        implementation "org.lwjgl:lwjgl$it:$lwjglVersion"
        runtimeOnly "org.lwjgl:lwjgl$it::$natives"
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
    <imgui.java.version>1.77-0.17.2</imgui.java.version>
</properties>

<!-- Resolve OS version for native libraries -->
<!-- imgui-java uses the same naming convention as LWJGL3 -->
<profiles>
    <profile>
        <id>lwjgl-natives-linux-amd64</id>
        <activation>
            <os>
                <family>unix</family>
                <arch>amd64</arch>
            </os>
        </activation>
        <properties>
            <natives>natives-linux</natives>
        </properties>
    </profile>
    <profile>
        <id>lwjgl-natives-macos-amd64</id>
        <activation>
            <os>
                <family>mac</family>
                <arch>amd64</arch>
            </os>
        </activation>
        <properties>
            <natives>natives-macos</lwjgl.natives>
        </properties>
    </profile>
    <profile>
        <id>lwjgl-natives-windows-amd64</id>
        <activation>
            <os>
                <family>windows</family>
                <arch>amd64</arch>
            </os>
        </activation>
        <properties>
            <natives>natives-windows</natives>
        </properties>
    </profile>
    <profile>
        <id>lwjgl-natives-windows-x86</id>
        <activation>
            <os>
                <family>windows</family>
                <arch>x86</arch>
            </os>
        </activation>
        <properties>
            <natives>natives-windows-x86</natives>
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
        <artifactId>binding</artifactId>
        <version>${imgui.java.version}</version>
    </dependency>
    <dependency>
        <groupId>io.imgui.java</groupId>
        <artifactId>lwjgl3</artifactId>
        <version>${imgui.java.version}</version>
    </dependency>
    <dependency>
        <groupId>io.imgui.java</groupId>
        <artifactId>${natives}</artifactId>
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
        <classifier>${natives}</classifier>
    </dependency>
    <dependency>
        <groupId>org.lwjgl</groupId>
        <artifactId>lwjgl-glfw</artifactId>
        <classifier>${natives}</classifier>
    </dependency>
    <dependency>
        <groupId>org.lwjgl</groupId>
        <artifactId>lwjgl-opengl</artifactId>
        <classifier>${natives}</classifier>
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
Binding based on the Dear ImGui [docking](https://github.com/ocornut/imgui/tree/docking) branch, commit: [05bc204dbd80dfebb3dab1511caf1cb980620c76](https://github.com/ocornut/imgui/tree/05bc204dbd80dfebb3dab1511caf1cb980620c76).
That branch contains two important features: [multi-viewports](https://github.com/ocornut/imgui/issues/1542) and [docking](https://github.com/ocornut/imgui/issues/2109).
See an official documentation about how to work with them.

##### Important!
Take a note that multi-viewports api is **VERY** complex to implement. It's highly recommended to use `imgui.glfw.ImGuiImplGlfw` class as it's done in the example.
Otherwise, if you're using your own backed implementation, there are no guarantees it will work.

## Using FreeType
Dear ImGui by default uses a stb_strutype library to render a fonts atlas. It's possible to use FreeType instead to get better fonts quality. See an example in [ImGuiGlfwExample](https://github.com/spair/imgui-java/blob/v1.77-0.17.2/imgui-lwjgl3/src/test/java/ImGuiGlfwExample.java). [Read more](https://github.com/ocornut/imgui/blob/v1.76/misc/freetype/README.md)

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

## How to Build
To build native libraries you need:
 - Unix-like system (MacOS to build natives for Mac)
 - Installed dependencies: `mingw-w64`, `ant`, `freetype2`

After you've configured everything, run `gradlew :imgui-binding:generateLibs -Denvs=${envShortcut} -Dlocal`.<br>
`envShortcut` could be `win32`, `win64`, `linux32`, `linux64` or `mac64`.<br>
`-Dlocal` is optional and means that natives will be built under the `./imgui-binding/build/` folder. Otherwise `/tmp/imgui` folder will be used.

## Credits
Binding partly based on the work of [xpenatan](https://github.com/xpenatan) and his version [jDear-imgui](https://github.com/xpenatan/jDear-imgui).

## License
See the LICENSE file for license rights and limitations (Apache-2.0).
