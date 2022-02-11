Folder contains libraries used by the binding.

Provide 'imgui.library.path' VM option to the folder with one of those file (ex: -Dimgui.library.path=./some/folder).
In the same way you can use 'java.library.path' option instead.

By default binding expects 'imgui-java64' file name.
You can change that by using 'imgui.library.name' VM option (ex: -Dimgui.library.name=custom-lib-name).

- imgui-java64.dll      << Windows
- libimgui-java64.so    << Linux
- libimgui-java64.dylib << MacOS
- libimgui-javaarm64.dylib << MacOS

Freetype folder contains same libraries, but with Freetype support.
