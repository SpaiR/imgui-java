package imgui.extension.texteditor;

import imgui.binding.ImGuiStruct;






public final class TextEditorLanguage extends ImGuiStruct {
    public TextEditorLanguage(final long ptr) {
        super(ptr);
    }

    /*JNI
        #include "_texteditor.h"
        #define THIS ((const TextEditor::Language*)STRUCT_PTR)
     */

    public String getName() {
        return nGetName();
    }

    private native String nGetName(); /*
        return env->NewStringUTF(THIS->name.c_str());
    */

    public boolean getCaseSensitive() {
        return nGetCaseSensitive();
    }

    private native boolean nGetCaseSensitive(); /*
        return THIS->caseSensitive;
    */

    public int getPreprocess() {
        return nGetPreprocess();
    }

    private native int nGetPreprocess(); /*
        return THIS->preprocess;
    */

    public String getSingleLineComment() {
        return nGetSingleLineComment();
    }

    private native String nGetSingleLineComment(); /*
        return env->NewStringUTF(THIS->singleLineComment.c_str());
    */

    public String getSingleLineCommentAlt() {
        return nGetSingleLineCommentAlt();
    }

    private native String nGetSingleLineCommentAlt(); /*
        return env->NewStringUTF(THIS->singleLineCommentAlt.c_str());
    */

    public String getCommentStart() {
        return nGetCommentStart();
    }

    private native String nGetCommentStart(); /*
        return env->NewStringUTF(THIS->commentStart.c_str());
    */

    public String getCommentEnd() {
        return nGetCommentEnd();
    }

    private native String nGetCommentEnd(); /*
        return env->NewStringUTF(THIS->commentEnd.c_str());
    */

    public boolean getHasSingleQuotedStrings() {
        return nGetHasSingleQuotedStrings();
    }

    private native boolean nGetHasSingleQuotedStrings(); /*
        return THIS->hasSingleQuotedStrings;
    */

    public boolean getHasDoubleQuotedStrings() {
        return nGetHasDoubleQuotedStrings();
    }

    private native boolean nGetHasDoubleQuotedStrings(); /*
        return THIS->hasDoubleQuotedStrings;
    */

    public String getOtherStringStart() {
        return nGetOtherStringStart();
    }

    private native String nGetOtherStringStart(); /*
        return env->NewStringUTF(THIS->otherStringStart.c_str());
    */

    public String getOtherStringEnd() {
        return nGetOtherStringEnd();
    }

    private native String nGetOtherStringEnd(); /*
        return env->NewStringUTF(THIS->otherStringEnd.c_str());
    */

    public String getOtherStringAltStart() {
        return nGetOtherStringAltStart();
    }

    private native String nGetOtherStringAltStart(); /*
        return env->NewStringUTF(THIS->otherStringAltStart.c_str());
    */

    public String getOtherStringAltEnd() {
        return nGetOtherStringAltEnd();
    }

    private native String nGetOtherStringAltEnd(); /*
        return env->NewStringUTF(THIS->otherStringAltEnd.c_str());
    */

    public int getStringEscape() {
        return nGetStringEscape();
    }

    private native int nGetStringEscape(); /*
        return THIS->stringEscape;
    */

    public static TextEditorLanguage C() {
        return new TextEditorLanguage(nC());
    }

    private static native long nC(); /*
        return (uintptr_t)TextEditor::Language::C();
    */

    public static TextEditorLanguage Cpp() {
        return new TextEditorLanguage(nCpp());
    }

    private static native long nCpp(); /*
        return (uintptr_t)TextEditor::Language::Cpp();
    */

    public static TextEditorLanguage Cs() {
        return new TextEditorLanguage(nCs());
    }

    private static native long nCs(); /*
        return (uintptr_t)TextEditor::Language::Cs();
    */

    public static TextEditorLanguage AngelScript() {
        return new TextEditorLanguage(nAngelScript());
    }

    private static native long nAngelScript(); /*
        return (uintptr_t)TextEditor::Language::AngelScript();
    */

    public static TextEditorLanguage Lua() {
        return new TextEditorLanguage(nLua());
    }

    private static native long nLua(); /*
        return (uintptr_t)TextEditor::Language::Lua();
    */

    public static TextEditorLanguage Python() {
        return new TextEditorLanguage(nPython());
    }

    private static native long nPython(); /*
        return (uintptr_t)TextEditor::Language::Python();
    */

    public static TextEditorLanguage Glsl() {
        return new TextEditorLanguage(nGlsl());
    }

    private static native long nGlsl(); /*
        return (uintptr_t)TextEditor::Language::Glsl();
    */

    public static TextEditorLanguage Hlsl() {
        return new TextEditorLanguage(nHlsl());
    }

    private static native long nHlsl(); /*
        return (uintptr_t)TextEditor::Language::Hlsl();
    */

    public static TextEditorLanguage Json() {
        return new TextEditorLanguage(nJson());
    }

    private static native long nJson(); /*
        return (uintptr_t)TextEditor::Language::Json();
    */

    public static TextEditorLanguage Markdown() {
        return new TextEditorLanguage(nMarkdown());
    }

    private static native long nMarkdown(); /*
        return (uintptr_t)TextEditor::Language::Markdown();
    */

    public static TextEditorLanguage Sql() {
        return new TextEditorLanguage(nSql());
    }

    private static native long nSql(); /*
        return (uintptr_t)TextEditor::Language::Sql();
    */

    /*JNI
        #undef THIS
     */
}
