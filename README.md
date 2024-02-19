<div align="center">

# ImGui Java

**JNI based binding for [Dear ImGui](https://github.com/ocornut/imgui)**

[![Github All Releases](https://img.shields.io/github/downloads/SpaiR/imgui-java/total.svg?logo=github)](https://github.com/SpaiR/imgui-java/releases)
[![CI](https://github.com/SpaiR/imgui-java/actions/workflows/ci.yml/badge.svg)](https://github.com/SpaiR/imgui-java/actions/workflows/ci.yml)<br>
[![Maven Central](https://img.shields.io/maven-central/v/io.github.spair/imgui-java-binding?logo=apache-maven)](https://search.maven.org/search?q=g:io.github.spair%20AND%20a:imgui-java-*)
[![binding javadoc](https://javadoc.io/badge2/io.github.spair/imgui-java-binding/javadoc_binding.svg?logo=java)](https://javadoc.io/doc/io.github.spair/imgui-java-binding)
[![app javadoc](https://javadoc.io/badge2/io.github.spair/imgui-java-app/javadoc_app.svg?logo=java)](https://javadoc.io/doc/io.github.spair/imgui-java-app)

</div>

---

### Features

- **JNI based**<br>
  Native communication layer made with generated JNI code. No dependencies, no problems.
- **Small and Efficient**<br>
  Binding has a small memory footprint and uses direct native calls to work.<br>
- **Fully Featured**<br>
  All public API was carefully implemented with Java usage in mind.<br>
- **Multi-Viewports / Docking Branch**<br>
  Binding has a full support of [Multi-Viewports](https://github.com/ocornut/imgui/wiki/Multi-Viewports) and [Docking](https://github.com/ocornut/imgui/wiki/Docking). <br>
- **FreeType Font Renderer**<br>
  FreeType font renderer provides a much better fonts quality. [See how to use](#freetype).<br>
- **Extensions**<br>
  Binding includes several useful [extensions](https://github.com/ocornut/imgui/wiki/Useful-Extensions) for Dear ImGui. [See full list](#extensions).

To understand how to use ImGui Java - read official [documentation](https://github.com/ocornut/imgui#usage) and [wiki](https://github.com/ocornut/imgui/wiki).
Binding adopts C++ API for Java, but almost everything can be used in the same manner. 

ImGui Java has a ready to use implementation for GLFW and OpenGL API using [LWJGL3](https://www.lwjgl.org/) library. See `imgui-lwjgl3` module.<br>
Implementation is optional, yet optional to use. Advantage of Dear ImGui is total portability, so feel free to copy-paste classes or write your own implementations.

Additionally, there is an `imgui-app` module, which provides **a high abstraction layer**.<br>
It hides all low-level code under one class to extend. With it, you can build your GUI application instantly.

# Support
[![ko-fi](https://ko-fi.com/img/githubbutton_sm.svg)](https://ko-fi.com/P5P5BF17Q)

You can support the project to motivate its further development.

# How to Try

_**Make sure you have installed <u>JDK 8</u> or higher.**_

<p align="center"><img src="https://i.imgur.com/JjhR9in.png"/></p>

### [Demo](https://i.imgur.com/c0ds1EZ.gif)

You can try binding by yourself in three simple steps:

```
git clone git@github.com:SpaiR/imgui-java.git
cd imgui-java
./gradlew :example:run
```

See `example` module to try other widgets in action.

# How to Use

**[ImGui in LWJGL YouTube video](https://youtu.be/Xq-eVcNtUbw)** by [GamesWithGabe](https://www.youtube.com/channel/UCQP4qSCj1eHMHisDDR4iPzw).<br>
You can use this video as a basic step-by-step tutorial. It shows how to integrate binding with the usage of jar files.<br>
Gradle and Maven dependencies could be used for this purpose as well.

Take a note, that integration itself is a very flexible process. It could be done in one way or another. If you just need a framework for your GUI - use [Application](#application) module. 
Otherwise, if you need more control, the best way is not just to repeat steps, but to understand what each step does.

## Application

If you don't care about OpenGL and other low-level stuff, then you can use application layer from `imgui-app` module.
It is a **one jar solution** which includes: GLFW, OpenGL and Dear ImGui itself. 
So you only need **one dependency** line or **one jar in classpath** to make everything to work. 
<ins>You don't need to add separate dependencies to LWJGL or native libraries, since they are already included.</ins>

**Application module is the best choice if everything you care is the GUI itself.**

At the same time, Application gives options to override any life-cycle method it has. 
That means that if you are seeking for a bit more low-level control - you can gain it as well.

### Example

A very simple application may look like this:
```
import imgui.ImGui;
import imgui.app.Application;
import imgui.app.Configuration;

public class Main extends Application {
    @Override
    protected void configure(Configuration config) {
        config.setTitle("Dear ImGui is Awesome!");
    }

    @Override
    public void process() {
        ImGui.text("Hello, World!");
    }

    public static void main(String[] args) {
        launch(new Main());
    }
}
```

Read `imgui.app.Application` [javadoc](https://javadoc.io/doc/io.github.spair/imgui-java-app) to understand how it works under the hood.

### Dependencies

![Maven Central](https://img.shields.io/maven-central/v/io.github.spair/imgui-java-binding?color=green&label=version&style=flat-square)

<details>
        <summary><b>Gradle</b></summary>

```
repositories {
    mavenCentral()
}

dependencies {
    implementation "io.github.spair:imgui-java-app:${version}"
}
```
</details>

<details>
        <summary><b>Maven</b></summary>

```
<dependencies>
    <dependency>
        <groupId>io.github.spair</groupId>
        <artifactId>imgui-java-app</artifactId>
        <version>${version}</version>
    </dependency>
</dependencies>
```
</details>

<details>
        <summary><b>Raw Jar</b></summary>

1. Go to the [release page](https://github.com/SpaiR/imgui-java/releases/latest);
2. Download `java-libraries.zip`;
3. Get `imgui-app-${version}-all.jar`;
4. Add the jar to your classpath.

Jar with `all` classifier already contains `binding` and `lwjgl3` modules.<br>
If you're using jar without the `all` classifier, add appropriate jars as well.

Both jars, with or without `all` classifier, have all required native libraries already.

</details>

#### Java Module System

If using Java 9 modules, you will need to require the `imgui.app` module.

## Binding

Using binding without `imgui-app` module requires to "attach" it to the application manually.
You can refer to `imgui-app` module to see how things are done there.

### Dependencies

![Maven Central](https://img.shields.io/maven-central/v/io.github.spair/imgui-java-binding?color=green&label=version&style=flat-square)

For simplicity, example of dependencies for Gradle / Maven only shows how to add natives for Windows. Feel free to add other platforms.

| Native Binaries                | System      |
|--------------------------------|-------------|
| imgui-java-natives-windows     | Windows     |
| imgui-java-natives-linux       | Linux       |
| imgui-java-natives-macos       | macOS       |
| imgui-java-natives-macos-arm64 | macOS-arm64 |

Take a note, that you also need to add dependencies to [LWJGL](https://www.lwjgl.org/) library. Examples below shows how to do it as well.

<details>
        <summary><b>Gradle</b></summary>

```
repositories {
    mavenCentral()
}

ext {
    lwjglVersion = '3.3.3'
    imguiVersion = "${version}"
}

dependencies {
    implementation platform("org.lwjgl:lwjgl-bom:$lwjglVersion")

    ['', '-opengl', '-glfw'].each {
        implementation "org.lwjgl:lwjgl$it:$lwjglVersion"
        implementation "org.lwjgl:lwjgl$it::natives-windows"
    }
    
    implementation "io.github.spair:imgui-java-binding:$imguiVersion"
    implementation "io.github.spair:imgui-java-lwjgl3:$imguiVersion"
    
    implementation "io.github.spair:imgui-java-natives-windows:$imguiVersion"
}
```
</details>

<details>
        <summary><b>Maven</b></summary>

```
<properties>
    <lwjgl.version>3.3.1</lwjgl.version>
    <imgui.java.version>${version}</imgui.java.version>
</properties>

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
        <classifier>natives-windows</classifier>
    </dependency>
    <dependency>
        <groupId>org.lwjgl</groupId>
        <artifactId>lwjgl-glfw</artifactId>
        <classifier>natives-windows</classifier>
    </dependency>
    <dependency>
        <groupId>org.lwjgl</groupId>
        <artifactId>lwjgl-opengl</artifactId>
        <classifier>natives-windows</classifier>
    </dependency>
    
    <dependency>
        <groupId>io.github.spair</groupId>
        <artifactId>imgui-java-binding</artifactId>
        <version>${imgui.java.version}</version>
    </dependency>
    <dependency>
        <groupId>io.github.spair</groupId>
        <artifactId>imgui-java-lwjgl3</artifactId>
        <version>${imgui.java.version}</version>
    </dependency>
    <dependency>
        <groupId>io.github.spair</groupId>
        <artifactId>imgui-java-natives-windows</artifactId>
        <version>${imgui.java.version}</version>
    </dependency>
</dependencies>
```
</details>

<details>
        <summary><b>Raw Jars</b></summary>

1. Go to the [release page](https://github.com/SpaiR/imgui-java/releases/latest);
2. Download `java-libraries.zip` and `native-libraries.zip` (`native-libraries-with-freetype.zip` for FreeType font rendering);
3. Get `imgui-binding-${version}.jar` and `imgui-lwjgl3-${version}.jar` from `java-libraries`, and binary libraries for required OS from `native-libraries` archive;
4. Add jars to your classpath;
5. Provide a VM option with location of files from the `native-libraries` (or `native-libraries-with-freetype`) archive.

VM option example:
- **-Dimgui.library.path=_${path}_**
- **-Djava.library.path=_${path}_**

Both `imgui.library.path` and `java.library.path` are equal with the difference, that `java.library.path` is standard JVM option to provide native libraries.

</details>

#### Java Module System

If using Java 9 modules, ImGui Java has Automatic Module Names:

| Package                        | Module                    |
|--------------------------------|---------------------------|
| imgui-java-app                 | imgui.app                 |
| imgui-java-binding             | imgui.binding             |
| imgui-java-lwjgl3              | imgui.lwjgl3              |
| imgui-java-natives-windows     | imgui.natives.windows     |
| imgui-java-natives-linux       | imgui.natives.linux       |
| imgui-java-natives-macos       | imgui.natives.macos       |
| imgui-java-natives-macos-arm64 | imgui.natives.macos-arm64 |

## Extensions

All extensions are already included in the binding and can be used as it is.
See examples in the `example` module for more information about how to use them.

- [ImNodes](https://github.com/Nelarius/imnodes/tree/857cc860af05ac0f6a4039c2af33d982377b6cf4) | [Example](https://github.com/SpaiR/imgui-java/blob/main/example/src/main/java/ExampleImNodes.java) <br>
  A small, dependency-free node editor for Dear ImGui. (A good choice for simple start.)
- [imgui-node-editor](https://github.com/thedmd/imgui-node-editor/tree/687a72f940c76cf5064e13fe55fa0408c18fcbe4) | [Example](https://github.com/SpaiR/imgui-java/blob/main/example/src/main/java/ExampleImGuiNodeEditor.java) <br>
  Node Editor using ImGui. (A bit more complex than ImNodes, but has more features.)
- [ImGuizmo](https://github.com/CedricGuillemet/ImGuizmo/tree/f7bbbe39971d9d45816417a70e9b53a0f698c56e) | [Example](https://github.com/SpaiR/imgui-java/blob/main/example/src/main/java/ExampleImGuizmo.java) <br>
  Immediate mode 3D gizmo for scene editing and other controls based on Dear ImGui.
- [implot](https://github.com/epezent/implot/tree/555ff688a8134bc0c602123149abe9c17d577475) | [Example](https://github.com/SpaiR/imgui-java/blob/main/example/src/main/java/ExampleImPlot.java) <br>
  Advanced 2D Plotting for Dear ImGui.  
- [ImGuiColorTextEdit](https://github.com/BalazsJako/ImGuiColorTextEdit/tree/0a88824f7de8d0bd11d8419066caa7d3469395c4) | [Example](https://github.com/SpaiR/imgui-java/blob/main/example/src/main/java/ExampleImGuiColorTextEdit.java) <br>
  Syntax highlighting text editor for ImGui.
- [ImGuiFileDialog](https://github.com/aiekick/ImGuiFileDialog/tree/4d42dfba125cbd4780a90fbc5f75e7dfbae64060) | [Example](https://github.com/SpaiR/imgui-java/blob/main/example/src/main/java/ExampleImGuiFileDialog.java) <br>
  A file selection dialog built for ImGui.
- [ImGui Club MemoryEditor](https://github.com/ocornut/imgui_club/tree/d4cd9896e15a03e92702a578586c3f91bbde01e8) | [Example](https://github.com/SpaiR/imgui-java/blob/main/example/src/main/java/ExampleImGuiMemoryEditor.java) <br>
  Memory editor and viewer for ImGui.

## Freetype

By default, Dear ImGui uses stb-truetype to render fonts. Yet there is an option to use FreeType font renderer. 
Go to the [imgui_freetype](https://github.com/ocornut/imgui/tree/256594575d95d56dda616c544c509740e74906b4/misc/freetype) to read about the difference.
Binding has this option too. Freetype especially useful when you use custom font with small (~<16px) size. 
If you use the default font or a large font, stb will be fine for you.

There are two types of precompiled binaries: 1. with stb (the default one) 2. with freetype.
You can decide by yourself, which kind of libraries for any system you want to use.

Take a note, that for Linux and macOS users using of freetype will add a dependency to the `libfreetype` itself.
This is not the case for Windows users, since `dll` binaries are compiled fully statically and already include freetype in themselves.

**For fully portable application** use default binaries.<br>
You can still use freetype binaries for Windows builds without worry though.

**For better fonts** use freetype binaries.<br>
Don't forget to make clear for your Linux/Mac users, that they will need to install freetype on their systems as well.

### Dependencies

![Maven Central](https://img.shields.io/maven-central/v/io.github.spair/imgui-java-binding?color=green&label=version&style=flat-square)

Use the same native libraries as you would, but with `-ft` suffix in the end.

<details>
    <summary><b>Modified Gradle Example</b></summary>

    ```
    repositories {
        mavenCentral()
    }
    
    ext {
        lwjglVersion = '3.3.3'
        imguiVersion = "${version}"
    }
    
    dependencies {
        implementation platform("org.lwjgl:lwjgl-bom:$lwjglVersion")
    
        ['', '-opengl', '-glfw'].each {
            implementation "org.lwjgl:lwjgl$it:$lwjglVersion"
            implementation "org.lwjgl:lwjgl$it::natives-windows"
        }
        
        implementation "io.github.spair:imgui-java-binding:$imguiVersion"
        implementation "io.github.spair:imgui-java-lwjgl3:$imguiVersion"
        
        // This is the main difference
        implementation "io.github.spair:imgui-java-natives-windows-ft:$imguiVersion"
    }
    ```
</details>

| Native Binaries With FreeType     | System      |
|-----------------------------------|-------------|
| imgui-java-natives-windows-ft     | Windows     |
| imgui-java-natives-linux-ft       | Linux       |
| imgui-java-natives-macos-ft       | macOS       |
| imgui-java-natives-macos-arm64-ft | macOS-arm64 |

If you're using raw dll/so files, go to the [release page](https://github.com/SpaiR/imgui-java/releases/latest) and use libraries from the `native-libraries-with-freetype.zip` archive.

# Binding Notice

Binding was made with Java usage in mind. Some places of the original library were adapted for that.<br>
For example, in places where in C++ you need to pass a reference value, in Java you pass primitive wrappers: `ImInt`, `ImFloat` etc.<br>

One important thing is how natives structs work. All of them have a public field with a pointer to the natively allocated memory.<br>
By changing the pointer it's possible to use the same Java instance to work with different native structs.<br>
Most of the time you can ignore this fact and just work with objects in a common way.

Read [javadoc](https://javadoc.io/doc/io.github.spair/imgui-java-binding) and source comments to get more info about how to do specific stuff.

# How to Build Native Libraries

Ensure you've downloaded git submodules. That could be achieved:
- When cloning the repository: `git clone --recurse-submodules https://github.com/SpaiR/imgui-java.git`
- When the repository cloned: `git submodule init` + `git submodule update`

### Windows

 - Make sure you have installed and **available in PATH**:
    * JDK 8 or higher
    * Ant
    * Mingw-w64 (recommended way: use [MSYS2](https://www.msys2.org/) and install [mingw-w64-x86_64-toolchain](https://packages.msys2.org/group/mingw-w64-x86_64-toolchain) group)
 - Build with: `./gradlew imgui-binding:generateLibs -Denvs=windows -Dlocal`
 - Run with: `./gradlew example:run -PlibPath="../imgui-binding/build/libsNative/windows64"`
 
### Linux

 - Install dependencies: `openjdk8`, `mingw-w64-gcc`, `ant`. (Package names could vary from system to system.)
 - Build with: `./gradlew imgui-binding:generateLibs -Denvs=linux -Dlocal`
 - Run with: `./gradlew example:run -PlibPath=../imgui-binding/build/libsNative/linux64`
 
### macOS

 - Check dependencies from "Linux" section and make sure you have them installed.
 - Build with: `./gradlew imgui-binding:generateLibs -Denvs=macos -Dlocal`
 - Run with: `./gradlew example:run -PlibPath=../imgui-binding/build/libsNative/macosx64`
 
### macOS-arm64

 - Check dependencies from "Linux" section and make sure you have them installed.
 - Build with: `./gradlew imgui-binding:generateLibs -Denvs=macosarm64 -Dlocal`
 - Run with: `./gradlew example:run -PlibPath=../imgui-binding/build/libsNative/macosxarm64`

In `envs` parameter next values could be used `windows`, `linux` or `macos` or `macosarm64`.<br>
`-Dlocal` is optional and means that natives will be built under the `./imgui-binding/build/` folder. Otherwise `/tmp/imgui` folder will be used.
On Windows always use local build.

# License

See the LICENSE file for license rights and limitations (MIT).
