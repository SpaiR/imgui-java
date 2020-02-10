# imgui-java

[![Build Status](https://travis-ci.org/SpaiR/imgui-java.svg?branch=master)](https://travis-ci.org/SpaiR/imgui-java)
[![JCenter](https://img.shields.io/bintray/v/spair/io.imgui.java/binding.svg?label=binding)](https://bintray.com/spair/io.imgui.java/binding/_latestVersion)
[![JCenter](https://img.shields.io/bintray/v/spair/io.imgui.java/lwjgl3.svg?label=lwjgl3)](https://bintray.com/spair/io.imgui.java/lwjgl3/_latestVersion)
[![javadoc](https://javadoc.io/badge2/io.imgui.java/binding/javadoc.svg)](https://javadoc.io/doc/io.imgui.java/binding)

A handcrafted Java binding for [Dear ImGui](https://github.com/ocornut/imgui) with no dependencies and ready to use pre-compiled binaries.

It's a straightforward binding, which uses JNI to do a direct calls to Dear ImGui API.<br>
Please read **Binding notice** to get more info about specific of the binding API.<br>
See official [documentation](https://github.com/ocornut/imgui#usage) and [wiki](https://github.com/ocornut/imgui/wiki) to get more info about how to do things in ImGui. 

Binding doesn't force you to use backend renderer which is introduced here. Feel free to use your own render engine if you need so.
See how things are done in [ImGuiImplGl3](https://github.com/SpaiR/imgui-java/blob/v1.75-0.5/imgui-lwjgl3/src/main/java/imgui/gl3/ImGuiImplGl3.java).

Binding has the next version naming: `imguiVersion-bindingVersion`.<br>
For example `1.74-0.1` means that binding uses `1.74` version of **ImGui** and binding itself has version `0.1`.

*Some of the very specific features are still in a wip state.*

## How to try
_Make sure you have installed Java 8 or higher._

You can try this binding by yourself in a three simple steps:

```
git clone --branch v1.75-0.5 https://github.com/SpaiR/imgui-java.git
cd imgui-java
gradlew :imgui-lwjgl3:startExample
```

That's it! This will start an example app [ImGuiGlfwExample](https://github.com/SpaiR/imgui-java/blob/v1.75-0.5/imgui-lwjgl3/src/test/java/ImGuiGlfwExample.java)
which relies on GLFW and LWJGL3. Feel free to modify [ImGuiGlfwExample#showUi](https://github.com/SpaiR/imgui-java/blob/v1.75-0.5/imgui-lwjgl3/src/test/java/ImGuiGlfwExample.java#L335)
method to try different ImGui widgets in action.

**GIF with example:** https://imgur.com/a/E2rfuCR

## How to use

<details>
    <summary><b>Usage ready build.gradle<b></summary>

    repositories {
        jcenter()
        mavenCentral()
    }

    switch (OperatingSystem.current()) {
        case OperatingSystem.LINUX:
            project.ext.nativesType = "natives-linux"
            break
        case OperatingSystem.WINDOWS:
            project.ext.nativesType = "natives-windows"
            break
    }

    ext {
        lwjglVersion = '3.2.3'
        imguiVersion = '1.75-0.5'
    }

    dependencies {
        implementation "io.imgui.java:binding:$imguiVersion"
        implementation "io.imgui.java:lwjgl3:$imguiVersion"
        runtimeOnly "io.imgui.java:$nativesType:$imguiVersion"

        implementation platform("org.lwjgl:lwjgl-bom:$lwjglVersion")

        ['', '-opengl', '-glfw'].each {
            implementation "org.lwjgl:lwjgl$it:$lwjglVersion"
            runtimeOnly "org.lwjgl:lwjgl$it::$nativesType"
        }
    }
</details>

### Step 1
Add jcenter repository:
```
repositories {
    jcenter()
}
```

### Step 2
Add binding dependency:
```
implementation 'io.imgui.java:binding:1.75-0.5'
```

### Step 3
If you want to use LWJGL3 renderer:
```
implementation 'io.imgui.java:lwjgl3:1.75-0.5'
```
**Disclaimer!**<br>
LWJGL3 renderer is based on the `3.2.3` version of the [LWJGL](https://www.lwjgl.org/). 
You'll need to add additional dependencies to it. Specifically `lwjgl` itself, `glfw` and `opengl` modules.
You can find how to do that [here](https://www.lwjgl.org/customize).

### Step 4
Add binary lib dependency:
```
runtimeOnly "io.imgui.java:$imguiNatives:1.75-0.5"
```
Where `imguiNatives` could be:
 - `natives-linux`
 - `natives-linux-x86`
 - `natives-windows`
 - `natives-windows-x86`

_(`x86` suffix is to get 32bit library)_

Or alternatively you can download binaries manually:<br>
Go to the `/bin` folder and pick a native library for your OS. Then you'll need to provide `imgui.library.path` or `java.library.path` 
VM option with path to a folder where you placed downloaded file.

**You are ready to use imgui-java binding!**

#### Binding notice:
* All ImGui methods are available in `camelCase`, not in `PascalCase`.
* In places where in C++ you need to pass `ImVec2` or `ImVec4`, here you'll need to pass two or four float parameters respectively.
* When you need to get an input/output to/from the Dear-ImGui use primitive wrappers: `ImBool`, `ImInt` etc.
* Due to the Java and JNI restrictions we can't provide a fully fledged callbacks to the ImGui::InputText*() methods.
  Partly you could replace some of the features (like setting of the allowed chars to input) by using the ImGuiInputTextData class. 
  Read [javadoc](https://javadoc.io/doc/io.imgui.java/binding) to get more info.

## How to build
To build native libraries you should install `mingw-w64` and `ant`. Modify [GenerateLibs](https://github.com/SpaiR/imgui-java/blob/master/buildSrc/src/main/groovy/imgui/generate/GenerateLibs.groovy)
to build specific binaries you need. After everything is configure run `gradlew :imgui-binding:generateLibs` task.
This will build native libraries and place them in `~/imgui-binding/build/libsNative` folder.

## Credits
This binding is partly based on the work of [xpenatan](https://github.com/xpenatan) and his version [jDear-imgui](https://github.com/xpenatan/jDear-imgui).

## License
See the LICENSE file for license rights and limitations (Apache-2.0).
