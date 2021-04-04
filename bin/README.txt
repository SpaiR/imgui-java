Folder contains libraries used by the binding.

Provide 'imgui.library.path' VM option to the folder with one of those file (ex: -Dimgui.library.path=./some/folder).
In the same way you can use 'java.library.path' option instead.

By default binding expects 'imgui-java' ('imgui-java64' for x64 arch) file name.
You can change that by using 'imgui.library.name' VM option (ex: -Dimgui.library.name=custom-lib-name).

- imgui-java.dll        << Windows 32bit
- imgui-java64.dll      << Windows 64bit
- libimgui-java.so      << Linux 32bit
- libimgui-java64.so    << Linux 64bit
- libimgui-java64.dylib << MacOS

Freetype folder contains same libraries, but with Freetype support.
