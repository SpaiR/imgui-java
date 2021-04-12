# imgui-java 
[![Github All Releases](https://img.shields.io/github/downloads/SpaiR/imgui-java/total.svg?logo=github)](https://github.com/SpaiR/imgui-java/releases)
[![CI Build](https://github.com/SpaiR/imgui-java/workflows/CI%20Build/badge.svg)](https://github.com/SpaiR/imgui-java/actions?query=workflow%3A%22CI+Build%22)
[![Maven Central](https://img.shields.io/maven-central/v/io.github.spair/imgui-java-binding?logo=apache-maven)](https://search.maven.org/search?q=g:io.github.spair%20AND%20a:imgui-java-*)
[![binding javadoc](https://javadoc.io/badge2/io.github.spair/imgui-java-binding/javadoc_binding.svg?logo=java)](https://javadoc.io/doc/io.github.spair/imgui-java-binding)
[![app javadoc](https://javadoc.io/badge2/io.github.spair/imgui-java-app/javadoc_app.svg?logo=java)](https://javadoc.io/doc/io.github.spair/imgui-java-app)

## Important News!
_(If you are using raw jars, you can skip this section.)_<br>
Because of [JCenter shutdown](https://jfrog.com/blog/into-the-sunset-bintray-jcenter-gocenter-and-chartcenter/) library has moved to Maven Central.
That fact affects nothing, but the groupId parameter in your Gradle and Maven configuration files.
From version `1.80-1.5.0` use `io.github.spair` instead of `io.imgui.java`. See an updated [How To Use](#how-to-use) section for details.

**<ins>Old versions will not be transferred to Maven Central and will become unavailable after JCenter goes down.</ins>**

---

JNI based binding for [Dear ImGui](https://github.com/ocornut/imgui) with no dependencies.<br>
Read official [documentation](https://github.com/ocornut/imgui#usage) and [wiki](https://github.com/ocornut/imgui/wiki) to see how to work with Dear ImGui.
Almost everything from C++ could be done in Java in the same way.

Binding has an OpenGL renderer and a GLFW backend implementation, using a [LWJGL3](https://www.lwjgl.org/) library. Could be found in [imgui-lwjgl3](https://github.com/SpaiR/imgui-java/blob/v1.82.2/imgui-lwjgl3) module.<br>
They are recommended, yet optional to use. The advantage of Dear ImGui is a portability, so feel free to copy-paste classes or write your own implementations.<br>

Additionally, there is an [imgui-app](https://github.com/SpaiR/imgui-java/blob/v1.82.2/imgui-app) module, which provides **a high abstraction layer**.<br>
It hides all low-level stuff under one class to extend, so you can build your GUI application instantly.

### Features
- **Small and Efficient**<br>
  Binding has a very small memory footprint and uses direct native calls to work.<br>
- **Fully Featured**<br>
  All public API was carefully implemented with Java usage in mind.<br>
- **Multi-Viewports/Docking Branch**<br>
  Binding has a full support of [Multi-Viewports](https://github.com/ocornut/imgui/wiki/Multi-Viewports) and [Docking](https://github.com/ocornut/imgui/wiki/Docking). <br>
- **FreeType Font Renderer**<br>
  FreeType font renderer provides a much better fonts quality. [See how to use](#freetype).<br>
- **Extensions**<br>
  Binding includes several useful [extensions](https://github.com/ocornut/imgui/wiki/Useful-Widgets) for Dear ImGui. [See full list](#extensions).

# How to Try
_Make sure you have installed Java 8 or higher._

<p align="center"><img src="https://i.imgur.com/clYSVjG.png"/></p>

### [Demo](https://i.imgur.com/c0ds1EZ.gif)

You can try binding by yourself in three simple steps:

```
git clone --branch v1.82.2 https://github.com/SpaiR/imgui-java.git
cd imgui-java
./gradlew :example:run
```

See [example](https://github.com/SpaiR/imgui-java/blob/v1.82.2/example) module to try other widgets in action.

# How To Use
**[ImGui in LWJGL YouTube video](https://youtu.be/Xq-eVcNtUbw)** by [GamesWithGabe](https://www.youtube.com/channel/UCQP4qSCj1eHMHisDDR4iPzw).<br>
You can use this video as a basic step-by-step tutorial. It shows how to integrate binding with the usage of jar files. Gradle and Maven dependecies could be used for this purpose as well.

Take a note, that integration itself is a very flexible process. It could be done in one way or another. If you just need a framework for your GUI - use **Application** module. Otherwise, if you need more control, the best way is not just to repeat steps, but to understand what each step does.

## Application
If you don't care about OpenGL or other low-level stuff, then you can use application layer from [imgui-app](https://github.com/SpaiR/imgui-java/blob/v1.82.2/imgui-app) module.
It is a **one jar solution** which includes: GLFW, OpenGL and Dear ImGui itself. So you only need **one dependency** line or **one jar in classpath** to make everything to work. <ins>You don't need to add separate dependencies to LWJGL or native libraries, since they are already included.</ins><br>
Application module is the best choice if everything you care is the GUI for your app.

At the same time, Application gives an option to override any life-cycle method it has. That means that if you're seeking for a bit more low-level control - you can gain it too.

#### Example
A very simple application may look like this:
```
import imgui.ImGui;
import imgui.app.Application;

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

#### Dependencies

<details>
        <summary><b>Gradle</b></summary>

```
repositories {
    mavenCentral()
}

dependencies {
    implementation "io.github.spair:imgui-java-app:1.82.2"
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
        <version>1.82.2</version>
    </dependency>
</dependencies>
```
</details>

<details>
        <summary><b>Raw Jar</b></summary>

1. Go to the [release page](https://github.com/SpaiR/imgui-java/releases/latest);
2. Download `imgui-app-${version}.jar`;
3. Add the jar to your classpath.
</details>

## Binding
Using binding without the wrapper requires to "attach" it to your application manually.
You can refer to [imgui-app](https://github.com/SpaiR/imgui-java/blob/v1.82.2/imgui-app) module and see how things are done there.

### Dependencies
For simplicity, example of dependencies for Gradle/Maven only shows how to add natives for Windows.
Feel free to add other platforms.

| Native Binaries                | System        |
| ------------------------------ | ------------- |
| imgui-java-natives-windows-x86 | Windows 32bit |
| imgui-java-natives-windows     | Windows 64bit |
| imgui-java-natives-linux-x86   | Linux 32bit   |
| imgui-java-natives-linux       | Linux 64bit   |
| imgui-java-natives-macos       | MacOS         |

Take a note, that you also need to add dependencies to [LWJGL](https://www.lwjgl.org/). Examples below shows how to do it as well.

<details>
        <summary><b>Gradle</b></summary>

```
repositories {
    mavenCentral()
}

ext {
    lwjglVersion = '3.2.3'
    imguiVersion = '1.82.2'
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
    <lwjgl.version>3.2.3</lwjgl.version>
    <imgui.java.version>1.82.2</imgui.java.version>
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
 2. Download `imgui-binding-${version}.jar`, `imgui-lwjgl3-${version}.jar` and binary libraries for your OS;
   - imgui-java.dll - Windows 32bit
   - imgui-java64.dll - Windows 64bit
   - libimgui-java.so - Linux 32bit
   - libimgui-java64.so - Linux 64bit
   - libimgui-java64.dylib - MacOS
 3. Add jars to your classpath;
 4. Provide a VM option: `imgui.library.path` or `java.library.path`. It should point to the folder where you've placed downloaded native libraries.
</details>

## Extensions
- [ImNodes](https://github.com/Nelarius/imnodes/tree/857cc860af05ac0f6a4039c2af33d982377b6cf4) | [Example](https://github.com/SpaiR/imgui-java/blob/v1.82.2/example/src/main/java/ExampleImNodes.java) <br>
  A small, dependency-free node editor for dear imgui. (A good choice for simple start.)
- [imgui-node-editor](https://github.com/thedmd/imgui-node-editor/tree/687a72f940c76cf5064e13fe55fa0408c18fcbe4) | [Example](https://github.com/SpaiR/imgui-java/blob/v1.82.2/example/src/main/java/ExampleImGuiNodeEditor.java) <br>
  Node Editor using ImGui. (A bit more complex than ImNodes, but has more features.)

## Freetype
By default, Dear ImGui uses stb-truetype to render fonts. Yet there is an option to use FreeType font renderer. Go to the [imgui_freetype](https://github.com/ocornut/imgui/tree/256594575d95d56dda616c544c509740e74906b4/misc/freetype) to read about the difference.
Binding has this option too. Freetype especially useful when you use custom font with small (~<16px) size. If you use the default font or a large font, stb will be fine for you.

There are two types of precompiled binaries: 1. with stb (the default one) 2. with freetype.
You can decide by yourself, which kind of libraries for any system you want to use.

Take a not, that for Linux and Mac users using of freetype will add an additional dependency to the `libfreetype` itself.
This is not the case for Windows users, since `dll` binaries are compiled fully statically and already include freetype in themselves.

**For fully portable application** use default binaries.<br>
You can still use freetype binaries for Windows builds without worry though.

**For better fonts** use freetype binaries.<br>
Don't forget to make clear for your Linux/Mac users, that they will need to install freetype on their systems as well.

### How To Use
- Maven/Gradle:<br>
  Use the same native libraries as you would, but with `-ft` suffix in the end.
  
  | Native Binaries With FreeType     | System        |
  | --------------------------------- | ------------- |
  | imgui-java-natives-windows-x86-ft | Windows 32bit |
  | imgui-java-natives-windows-ft     | Windows 64bit |
  | imgui-java-natives-linux-x86-ft   | Linux 32bit   |
  | imgui-java-natives-linux-ft       | Linux 64bit   |
  | imgui-java-natives-macos-ft       | MacOS         |
  <details>
        <summary><b>Modified Gradle Example</b></summary>

    ```
    repositories {
        mavenCentral()
    }
    
    ext {
        lwjglVersion = '3.2.3'
        imguiVersion = '1.82.2'
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
- If you're using raw dll/so files, go to the `bin/freetype` folder and take them from there.

# Binding Notice
Binding was made with java usage in mind. Some places of the original library were adapted for that.<br>
For example, in places where in C++ you need to pass a reference value, in Java you pass primitive wrappers: `ImInt`, `ImFloat` etc.<br>

One important thing is how natives structs work. All of them have a public field with a pointer to the natively allocated memory.<br>
By changing the pointer it's possible to use the same Java instance to work with different native structs.<br>
Most of the time you can ignore this fact and just work with objects in a common way.

Read [javadoc](https://javadoc.io/doc/io.github.spair/imgui-java-binding) and source comments to get more info about how to do specific stuff.

# How to Build Native Libraries
### Windows
 - Make sure you have installed and **available in PATH**:
    * Java 8 or higher
    * Ant
    * Mingw-w64 (recommended way: use [MSYS2](https://www.msys2.org/) and install [mingw-w64-x86_64-toolchain](https://packages.msys2.org/group/mingw-w64-x86_64-toolchain) group)
 - Build with: `./gradlew :imgui-binding:generateLibs -Denvs=win64 -Dlocal`
 - Run with: `./gradlew :example:run -DlibPath="../imgui-binding/build/libsNative/windows64"`
 
### Linux
 - Install dependencies: `openjdk8`, `mingw-w64-gcc`, `ant`. (Package names could vary from system to system.)
 - Build with: `./gradlew :imgui-binding:generateLibs -Denvs=linux64 -Dlocal`
 - Run with: `./gradlew :example:run -DlibPath=../imgui-binding/build/libsNative/linux64`
 
### MacOS
 - Check dependencies from "Linux" section and make sure you have them installed.
 - Build with: `./gradlew :imgui-binding:generateLibs -Denvs=mac64 -Dlocal`
 - Run with: `./gradlew :example:run -DlibPath=../imgui-binding/build/libsNative/macosx64`

In `envs` parameter next values could be used `win32`, `win64`, `linux32`, `linux64` or `mac64`.<br>
`-Dlocal` is optional and means that natives will be built under the `./imgui-binding/build/` folder. Otherwise `/tmp/imgui` folder will be used.
On Windows always use local build.

# License
See the LICENSE file for license rights and limitations (Apache-2.0).
