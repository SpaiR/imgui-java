package imgui.extension.texteditor;

import imgui.binding.ImGuiStructDestroyable;

public class TextEditorIdentifier extends ImGuiStructDestroyable {

    /*JNI
    #include "_texteditor.h"

    #define IDENTIFIER ((TextEditor::Identifier*)STRUCT_PTR)
     */

    public TextEditorIdentifier() {
    }

    public TextEditorIdentifier(long ptr) {
        super(ptr);
    }

    @Override
    protected long create() {
        return nCreate();
    }

    private native long nCreate(); /*
        return (intptr_t)(new TextEditor::Identifier());
    */

    public native String getDeclaration(); /*
        return env->NewStringUTF(IDENTIFIER->mDeclaration.c_str());
    */

    public native void setDeclaration(String value); /*
        IDENTIFIER->mDeclaration = value;
    */
}
