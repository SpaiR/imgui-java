Folder contains libraries used by the binding.
Provide 'imgui.library.path' VM option to a folder with one of those file (ex: -Dimgui.library.path=./some/folder).
The same way you can use 'java.library.path' option as well.
By default binding expects 'imgui-java' ('imgui-java64' for x64 arch) file name.
You can change that by using 'imgui.library.name' VM option (ex: -Dimgui.library.name=custom-lib-name).

- imgui-java.dll        << win32
- imgui-java64.dll      << win64
- libimgui-java.so      << linux32
- libimgui-java64.so    << linux64
- libimgui-java64.dylib << MacOs64
