Folder contains libraries used by the binding.
Provide 'java.library.path' VM option to a folder with one of those file (ex: -Djava.library.path=./some/folder).
By default binding expects 'imgui-java' ('imgui-java64' for x64 arch) file name.
You can change that by using 'imgui.library.name' VM option (ex: -Dimgui.library.name=custom-lib-name).

- imgui-java.dll   << win32
- imgui-java64.dll << win64
- libimgui-java.so << linux32
- libimgui-java.so << linux64
