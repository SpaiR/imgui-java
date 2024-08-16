package imgui.extension.texteditor;

import imgui.binding.ImGuiStructDestroyable;
import imgui.binding.annotation.BindingField;
import imgui.binding.annotation.BindingMethod;
import imgui.binding.annotation.BindingSource;
import imgui.binding.annotation.ReturnValue;
import imgui.binding.annotation.TypeStdString;

import java.util.Map;

@BindingSource
public final class TextEditorLanguageDefinition extends ImGuiStructDestroyable {
    public TextEditorLanguageDefinition() {
        super();
    }

    public TextEditorLanguageDefinition(final long ptr) {
        super(ptr);
    }

    @Override
    protected long create() {
        return nCreate();
    }

    /*JNI
        #include "_texteditor.h"
        #define THIS ((TextEditor::LanguageDefinition*)STRUCT_PTR)
        #define TextEditorLanguageDefinition TextEditor::LanguageDefinition
     */

    private native long nCreate(); /*
        return (intptr_t)(new TextEditor::LanguageDefinition());
    */

    @BindingField(callName = "mName")
    @TypeStdString
    @ReturnValue(callSuffix = ".c_str()")
    public String Name;

    public void setKeywords(final String[] keywords) {
        nSetKeywords(keywords, keywords.length);
    }

    private native void nSetKeywords(String[] keywords, int length); /*
        std::unordered_set<std::string> set;
        for (int i = 0; i < length; i++) {
            jstring string = (jstring)env->GetObjectArrayElement(keywords, i);
            const char* raw = env->GetStringUTFChars(string, JNI_FALSE);
            set.emplace(std::string(raw));
            env->ReleaseStringUTFChars(string, raw);
        }
        THIS->mKeywords = set;
    */

    public void setIdentifiers(final Map<String, String> identifiers) {
        final String[] keys = identifiers.keySet().toArray(new String[0]);
        final String[] decl = identifiers.values().toArray(new String[0]);
        nSetIdentifiers(keys, keys.length, decl, decl.length);
    }

    private native void nSetIdentifiers(String[] keys, int keysLen, String[] decl, int declLen); /*
        std::unordered_map<std::string, TextEditor::Identifier> identifiers;
        for (int i = 0; i < keysLen; i++) {
            jstring string1 = (jstring)env->GetObjectArrayElement(keys, i);
            jstring string2 = (jstring)env->GetObjectArrayElement(decl, i);
            const char* key = env->GetStringUTFChars(string1, JNI_FALSE);
            const char* value = env->GetStringUTFChars(string2, JNI_FALSE);
            TextEditor::Identifier id;
            id.mDeclaration = std::string(value);
            identifiers.insert(std::pair<std::string, TextEditor::Identifier>(std::string(key), id));
            env->ReleaseStringUTFChars(string1, key);
            env->ReleaseStringUTFChars(string2, value);
        }
        THIS->mIdentifiers = identifiers;
    */

    public void setPreprocIdentifiers(final Map<String, String> identifiers) {
        final String[] keys = identifiers.keySet().toArray(new String[0]);
        final String[] decl = identifiers.values().toArray(new String[0]);
        nSetPreprocIdentifiers(keys, keys.length, decl, decl.length);
    }

    private native void nSetPreprocIdentifiers(String[] keys, int keysLen, String[] decl, int declLen); /*
        std::unordered_map<std::string, TextEditor::Identifier> preprocIdentifiers;
        for (int i = 0; i < keysLen; i++) {
            jstring string1 = (jstring)env->GetObjectArrayElement(keys, i);
            jstring string2 = (jstring)env->GetObjectArrayElement(decl, i);
            const char* key = env->GetStringUTFChars(string1, JNI_FALSE);
            const char* value = env->GetStringUTFChars(string2, JNI_FALSE);
            TextEditor::Identifier id;
            id.mDeclaration = std::string(value);
            preprocIdentifiers.insert(std::pair<std::string, TextEditor::Identifier>(std::string(key), id));
            env->ReleaseStringUTFChars(string1, key);
            env->ReleaseStringUTFChars(string2, value);
        }
        THIS->mPreprocIdentifiers = preprocIdentifiers;
    */

    @BindingField(callName = "mCommentStart")
    @TypeStdString
    @ReturnValue(callSuffix = ".c_str()")
    public String CommentStart;

    @BindingField(callName = "mCommentEnd")
    @TypeStdString
    @ReturnValue(callSuffix = ".c_str()")
    public String CommentEnd;

    @BindingField(callName = "mSingleLineComment")
    @TypeStdString
    @ReturnValue(callSuffix = ".c_str()")
    public String SingleLineComment;

    @BindingField(callName = "mPreprocChar")
    public char PreprocChar;

    @BindingField(callName = "mAutoIndentation")
    public boolean AutoIndentation;

    public void setTokenRegexStrings(final Map<String, Integer> tokenRegexStrings) {
        final String[] keys = tokenRegexStrings.keySet().toArray(new String[0]);
        final int[] paletteIndexes = tokenRegexStrings.values().stream().mapToInt(i -> i).toArray();
        nSetTokenRegexStrings(keys, keys.length, paletteIndexes, paletteIndexes.length);
    }

    private native void nSetTokenRegexStrings(String[] keys, int keysLen, int[] paletteIndexes, int paletteIndexesLen); /*
        std::vector<std::pair<std::string, TextEditor::PaletteIndex>> tokenRegexStrings;
        for (int i = 0; i < keysLen; i++) {
            jstring string = (jstring)env->GetObjectArrayElement(keys, i);
            const char* key = env->GetStringUTFChars(string, JNI_FALSE);
            int value = paletteIndexes[i];
            tokenRegexStrings.emplace_back(
                std::pair<std::string, TextEditor::PaletteIndex>(
                    std::string(key),
                    static_cast<TextEditor::PaletteIndex>(value)
                )
            );
            env->ReleaseStringUTFChars(string, key);
        }
        THIS->mTokenRegexStrings = tokenRegexStrings;
    */

    @BindingField
    public boolean mCaseSensitive;

    @BindingMethod(name = "CPlusPlus")
    @ReturnValue(isStatic = true, callPrefix = "&")
    public static native TextEditorLanguageDefinition CPlusPlus();

    @BindingMethod(name = "HLSL")
    @ReturnValue(isStatic = true, callPrefix = "&")
    public static native TextEditorLanguageDefinition HLSL();

    @BindingMethod(name = "GLSL")
    @ReturnValue(isStatic = true, callPrefix = "&")
    public static native TextEditorLanguageDefinition GLSL();

    @BindingMethod(name = "C")
    @ReturnValue(isStatic = true, callPrefix = "&")
    public static native TextEditorLanguageDefinition C();

    @BindingMethod(name = "SQL")
    @ReturnValue(isStatic = true, callPrefix = "&")
    public static native TextEditorLanguageDefinition SQL();

    @BindingMethod(name = "AngelScript")
    @ReturnValue(isStatic = true, callPrefix = "&")
    public static native TextEditorLanguageDefinition AngelScript();

    @BindingMethod(name = "Lua")
    @ReturnValue(isStatic = true, callPrefix = "&")
    public static native TextEditorLanguageDefinition Lua();

    /*JNI
        #undef TextEditorLanguageDefinition
        #undef THIS
     */
}
