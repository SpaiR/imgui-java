# imgui-java 
[![Build Status](https://travis-ci.org/SpaiR/imgui-java.svg?branch=master)](https://travis-ci.org/SpaiR/imgui-java) [![javadoc](https://javadoc.io/badge2/io.imgui.java/binding/javadoc.svg)](https://javadoc.io/doc/io.imgui.java/binding) [![JCenter](https://img.shields.io/bintray/v/spair/io.imgui.java/binding.svg?label=binding)](https://bintray.com/spair/io.imgui.java/binding/_latestVersion) [![JCenter](https://img.shields.io/bintray/v/spair/io.imgui.java/lwjgl3.svg?label=lwjgl3)](https://bintray.com/spair/io.imgui.java/lwjgl3/_latestVersion)

JNI based binding for [Dear ImGui](https://github.com/ocornut/imgui) with no dependencies, ready to use pre-compiled binaries and a renderer for [LWJGL3](https://www.lwjgl.org/).

Please read **Binding Notice** to get more info about java-specific things of the API.<br>
See official [documentation](https://github.com/ocornut/imgui#usage) and [wiki](https://github.com/ocornut/imgui/wiki) to get more info about how to do things in Dear ImGui. 

imgui-java provides all the data you need to render Dear ImGui. If, for some reason, you want to use your own backend renderer, see how things are done in [ImGuiImplGl3](https://github.com/SpaiR/imgui-java/blob/v1.76-0.8/imgui-lwjgl3/src/main/java/imgui/gl3/ImGuiImplGl3.java).

Binding has the next versioning semantic: `imguiVersion-bindingVersion`.<br>
For example `1.74-0.1` means that imgui-java uses `1.74` version of Dear ImGui and binding itself has the version `0.1`.

*Some of the very specific features are still in a wip state.*

## How to Try
_Make sure you have installed Java 8 or higher._

You can try Dear ImGui with Java by yourself in a three simple steps:

```
git clone --branch v1.76-0.8 https://github.com/SpaiR/imgui-java.git
cd imgui-java
gradlew :imgui-lwjgl3:startExample
```

That's all! You will start an example app [ImGuiGlfwExample](https://github.com/SpaiR/imgui-java/blob/v1.76-0.8/imgui-lwjgl3/src/test/java/ImGuiGlfwExample.java). Feel free to modify [ExampleUi](https://github.com/SpaiR/imgui-java/blob/v1.76-0.8/imgui-lwjgl3/src/test/java/ExampleUi.java) class to try different Dear ImGui widgets in action.

![imgui-java demo](https://i.imgur.com/ljAhD7a.gif)

## How to Use

```
repositories {
    jcenter()
    mavenCentral()
}

ext {
    lwjglVersion = '3.2.3'
    imguiVersion = '1.76-0.8'
}

dependencies {
    implementation "io.imgui.java:binding:$imguiVersion"
    implementation "io.imgui.java:lwjgl3:$imguiVersion"

    // Include all available natives, but it's likely that you want something specific
    runtimeOnly "io.imgui.java:natives-windows:$imguiVersion"
    runtimeOnly "io.imgui.java:natives-windows-x86:$imguiVersion"
    runtimeOnly "io.imgui.java:natives-linux:$imguiVersion"
    runtimeOnly "io.imgui.java:natives-linux-x86:$imguiVersion"

    implementation platform("org.lwjgl:lwjgl-bom:$lwjglVersion")

    ['', '-opengl', '-glfw'].each {
        implementation "org.lwjgl:lwjgl$it:$lwjglVersion"

        // Include all available natives, but it's likely that you want something specific
        runtimeOnly "org.lwjgl:lwjgl$it::natives-windows"
        runtimeOnly "org.lwjgl:lwjgl$it::natives-windows-x86"
        runtimeOnly "org.lwjgl:lwjgl$it::natives-linux"
    }
}
```

Alternatively you can download imgui-java binaries manually from the [release page](https://github.com/SpaiR/imgui-java/releases/latest). You'll need to provide a VM option: `imgui.library.path` or `java.library.path`. It should point to the folder where you've placed downloaded binaries.

**You are ready to use imgui-java binding!**

## Using FreeType
Dear ImGui by default uses an stb_strutype library to render a fonts atlas. It's possible to use FreeType instead to get better fonts quality. See an example in [ImGuiGlfwExample](https://github.com/spair/imgui-java/blob/v1.76-0.8/imgui-lwjgl3/src/test/java/ImGuiGlfwExample.java#L279). [Read more](https://github.com/ocornut/imgui/blob/v1.76/misc/freetype/README.md)

## Binding Notice
* All Dear ImGui methods are available in `camelCase`, not in `PascalCase`.
* To **pass** `ImVec2`/`ImVec4` - provide two/four float numbers.
  To **get** `ImVec2`/`ImVec4` - provide a destination object.
* To get an input/output to/from Dear ImGui - use primitive wrappers: `ImBool`, `ImInt` etc.
* Due to the Java and JNI restrictions we can't provide a fully fledged callbacks to the ImGui::InputText* methods.
  To replace some of the features use an ImGuiInputTextData class.
* Read [javadoc](https://javadoc.io/doc/io.imgui.java/binding) and sources comments to get more info.

## How to Build
To build native libraries you should install `mingw-w64` and `ant`. Modify [GenerateLibs](https://github.com/SpaiR/imgui-java/blob/master/buildSrc/src/main/groovy/imgui/generate/GenerateLibs.groovy)
to build specific binaries you need. After everything is configured, run `gradlew :imgui-binding:generateLibs`.
That will build native libraries and place them in `imgui-binding/build/libsNative` folder.

## Credits
This binding is partly based on the work of [xpenatan](https://github.com/xpenatan) and his version [jDear-imgui](https://github.com/xpenatan/jDear-imgui).

## License
See the LICENSE file for license rights and limitations (Apache-2.0).
