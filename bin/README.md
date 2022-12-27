Folder contains libraries used by the binding and `binding.sha1` sum of the `imgui-binding/src/main` directory libraries has been built upon.

Provide `imgui.library.path` (or `java.library.path`) VM option to the folder with one of those file (ex: `-Dimgui.library.path=./folder/path`).
In the same way you can use 'java.library.path' option instead.

By default, binding expects `imgui-java64` file name.
You can change that by using `imgui.library.name` VM option (ex: `-Dimgui.library.name=custom-lib-name.dll`).

| OS      | Library               |
|---------|-----------------------|
| Windows | imgui-java64.dll      |
| Linux   | libimgui-java64.so    |
| macOS   | libimgui-java64.dylib |

Freetype directory contains same libraries, but with Freetype support.

Hash sum in the `binding.sha1` file is used in CI to see, if there is a need to update native binaries.
