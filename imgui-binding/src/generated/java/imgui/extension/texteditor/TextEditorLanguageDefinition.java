package imgui.extension.texteditor;

import imgui.binding.ImGuiStructDestroyable;

import java.util.Map;

public final class TextEditorLanguageDefinition extends ImGuiStructDestroyable {
    /*JNI
        #include "_texteditor.h"

        #define LANG_DEF ((TextEditor::LanguageDefinition*)STRUCT_PTR)
     */

    public TextEditorLanguageDefinition() {
    }

    public TextEditorLanguageDefinition(final long ptr) {
        super(ptr);
    }

    @Override
    protected long create() {
        return nCreate();
    }

    private native long nCreate(); /*
        return (intptr_t)(new TextEditor::LanguageDefinition());
    */

    public native void setName(String name); /*
        LANG_DEF->mName = name;
    */

    public void setKeywords(final String[] keywords) {
        nSetKeywords(keywords, keywords.length);
    }

    private native void nSetKeywords(String[] keywords, int length); /*
        std::unordered_set<std::string> set;

        for (int i = 0; i < length; i++) {
            jstring string = (jstring)env->GetObjectArrayElement(keywords, i);
            const char* raw = env->GetStringUTFChars(string, JNI_FALSE);

            set.emplace(std::string(raw));
        }

        LANG_DEF->mKeywords = set;
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
            const char* key = env->GetStringUTFChars(string1, JNI_FALSE);

            jstring string2 = (jstring)env->GetObjectArrayElement(decl, i);
            const char* value = env->GetStringUTFChars(string2, JNI_FALSE);

            TextEditor::Identifier id;
            id.mDeclaration = std::string(value);

            identifiers.insert(std::pair<std::string, TextEditor::Identifier>(std::string(key), id));
        }

        LANG_DEF->mIdentifiers = identifiers;
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
            const char* key = env->GetStringUTFChars(string1, JNI_FALSE);

            jstring string2 = (jstring)env->GetObjectArrayElement(decl, i);
            const char* value = env->GetStringUTFChars(string2, JNI_FALSE);

            TextEditor::Identifier id;
            id.mDeclaration = std::string(value);

            preprocIdentifiers.insert(std::pair<std::string, TextEditor::Identifier>(std::string(key), id));
        }

        LANG_DEF->mPreprocIdentifiers = preprocIdentifiers;
    */

    public native void setCommentStart(String value); /*
        LANG_DEF->mCommentStart = value;
    */

    public native void setCommentEnd(String value); /*
        LANG_DEF->mCommentEnd = value;
    */

    public native void setSingleLineComment(String value); /*
        LANG_DEF->mSingleLineComment = value;
    */

    public native void setPreprocChar(char value); /*
        LANG_DEF->mPreprocChar = value;
    */

    public native void setAutoIdentation(boolean value); /*
        LANG_DEF->mAutoIndentation = value;
    */

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

            tokenRegexStrings.emplace_back(std::pair<std::string, TextEditor::PaletteIndex>(std::string(key),
                static_cast<TextEditor::PaletteIndex>(value)));
        }

        LANG_DEF->mTokenRegexStrings = tokenRegexStrings;
    */

    private native void setCaseSensitive(boolean value); /*
        LANG_DEF->mCaseSensitive = value;
    */

    public static TextEditorLanguageDefinition cPlusPlus() {
        return new TextEditorLanguageDefinition(nCPlusPlus());
    }

    public static TextEditorLanguageDefinition hlsl() {
        return new TextEditorLanguageDefinition(nHLSL());
    }

    public static TextEditorLanguageDefinition glsl() {
        return new TextEditorLanguageDefinition(nGLSL());
    }

    public static TextEditorLanguageDefinition c() {
        return new TextEditorLanguageDefinition(nC());
    }

    public static TextEditorLanguageDefinition sql() {
        return new TextEditorLanguageDefinition(nSQL());
    }

    public static TextEditorLanguageDefinition angelScript() {
        return new TextEditorLanguageDefinition(nAngelScript());
    }

    public static TextEditorLanguageDefinition lua() {
        return new TextEditorLanguageDefinition(nLua());
    }

    private static native long nCPlusPlus(); /*
        return (intptr_t)&TextEditor::LanguageDefinition::CPlusPlus();
    */

    private static native long nHLSL(); /*
        return (intptr_t)&TextEditor::LanguageDefinition::HLSL();
    */

    private static native long nGLSL(); /*
        return (intptr_t)&TextEditor::LanguageDefinition::GLSL();
    */

    private static native long nC(); /*
        return (intptr_t)&TextEditor::LanguageDefinition::C();
    */

    private static native long nSQL(); /*
        return (intptr_t)&TextEditor::LanguageDefinition::SQL();
    */

    private static native long nAngelScript(); /*
        return (intptr_t)&TextEditor::LanguageDefinition::AngelScript();
    */

    private static native long nLua(); /*
        return (intptr_t)&TextEditor::LanguageDefinition::Lua();
    */
}
