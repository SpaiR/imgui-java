package imgui.extension.texteditor;

import imgui.binding.ImGuiStruct;
import imgui.binding.annotation.BindingField;
import imgui.binding.annotation.BindingSource;
import imgui.binding.annotation.ReturnValue;
import imgui.binding.annotation.TypeStdString;

@BindingSource
public final class TextEditorLanguage extends ImGuiStruct {
    public TextEditorLanguage(final long ptr) {
        super(ptr);
    }

    /*JNI
        #include "_texteditor.h"
        #define THIS ((const TextEditor::Language*)STRUCT_PTR)
     */

    @BindingField(accessors = BindingField.Accessor.GETTER, callName = "name")
    @TypeStdString
    @ReturnValue(callSuffix = ".c_str()")
    public String Name;

    @BindingField(accessors = BindingField.Accessor.GETTER, callName = "caseSensitive")
    public boolean CaseSensitive;

    @BindingField(accessors = BindingField.Accessor.GETTER, callName = "preprocess")
    public int Preprocess;

    @BindingField(accessors = BindingField.Accessor.GETTER, callName = "singleLineComment")
    @TypeStdString
    @ReturnValue(callSuffix = ".c_str()")
    public String SingleLineComment;

    @BindingField(accessors = BindingField.Accessor.GETTER, callName = "singleLineCommentAlt")
    @TypeStdString
    @ReturnValue(callSuffix = ".c_str()")
    public String SingleLineCommentAlt;

    @BindingField(accessors = BindingField.Accessor.GETTER, callName = "commentStart")
    @TypeStdString
    @ReturnValue(callSuffix = ".c_str()")
    public String CommentStart;

    @BindingField(accessors = BindingField.Accessor.GETTER, callName = "commentEnd")
    @TypeStdString
    @ReturnValue(callSuffix = ".c_str()")
    public String CommentEnd;

    @BindingField(accessors = BindingField.Accessor.GETTER, callName = "hasSingleQuotedStrings")
    public boolean HasSingleQuotedStrings;

    @BindingField(accessors = BindingField.Accessor.GETTER, callName = "hasDoubleQuotedStrings")
    public boolean HasDoubleQuotedStrings;

    @BindingField(accessors = BindingField.Accessor.GETTER, callName = "otherStringStart")
    @TypeStdString
    @ReturnValue(callSuffix = ".c_str()")
    public String OtherStringStart;

    @BindingField(accessors = BindingField.Accessor.GETTER, callName = "otherStringEnd")
    @TypeStdString
    @ReturnValue(callSuffix = ".c_str()")
    public String OtherStringEnd;

    @BindingField(accessors = BindingField.Accessor.GETTER, callName = "otherStringAltStart")
    @TypeStdString
    @ReturnValue(callSuffix = ".c_str()")
    public String OtherStringAltStart;

    @BindingField(accessors = BindingField.Accessor.GETTER, callName = "otherStringAltEnd")
    @TypeStdString
    @ReturnValue(callSuffix = ".c_str()")
    public String OtherStringAltEnd;

    @BindingField(accessors = BindingField.Accessor.GETTER, callName = "stringEscape")
    public int StringEscape;

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
