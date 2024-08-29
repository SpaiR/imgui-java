package imgui.extension.texteditor;

import imgui.binding.ImGuiStructDestroyable;

import java.util.Map;

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
        return (uintptr_t)(new TextEditor::LanguageDefinition());
    */

    public String getName() {
        return nGetName();
    }

    public void setName(final String value) {
        nSetName(value);
    }

    private native String nGetName(); /*
        return env->NewStringUTF(THIS->mName.c_str());
    */

    private native void nSetName(String value); /*MANUAL
        auto value = obj_value == NULL ? NULL : (char*)env->GetStringUTFChars(obj_value, JNI_FALSE);
        THIS->mName = value;
        if (value != NULL) env->ReleaseStringUTFChars(obj_value, value);
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

    public String getCommentStart() {
        return nGetCommentStart();
    }

    public void setCommentStart(final String value) {
        nSetCommentStart(value);
    }

    private native String nGetCommentStart(); /*
        return env->NewStringUTF(THIS->mCommentStart.c_str());
    */

    private native void nSetCommentStart(String value); /*MANUAL
        auto value = obj_value == NULL ? NULL : (char*)env->GetStringUTFChars(obj_value, JNI_FALSE);
        THIS->mCommentStart = value;
        if (value != NULL) env->ReleaseStringUTFChars(obj_value, value);
    */

    public String getCommentEnd() {
        return nGetCommentEnd();
    }

    public void setCommentEnd(final String value) {
        nSetCommentEnd(value);
    }

    private native String nGetCommentEnd(); /*
        return env->NewStringUTF(THIS->mCommentEnd.c_str());
    */

    private native void nSetCommentEnd(String value); /*MANUAL
        auto value = obj_value == NULL ? NULL : (char*)env->GetStringUTFChars(obj_value, JNI_FALSE);
        THIS->mCommentEnd = value;
        if (value != NULL) env->ReleaseStringUTFChars(obj_value, value);
    */

    public String getSingleLineComment() {
        return nGetSingleLineComment();
    }

    public void setSingleLineComment(final String value) {
        nSetSingleLineComment(value);
    }

    private native String nGetSingleLineComment(); /*
        return env->NewStringUTF(THIS->mSingleLineComment.c_str());
    */

    private native void nSetSingleLineComment(String value); /*MANUAL
        auto value = obj_value == NULL ? NULL : (char*)env->GetStringUTFChars(obj_value, JNI_FALSE);
        THIS->mSingleLineComment = value;
        if (value != NULL) env->ReleaseStringUTFChars(obj_value, value);
    */

    public char getPreprocChar() {
        return nGetPreprocChar();
    }

    public void setPreprocChar(final char value) {
        nSetPreprocChar(value);
    }

    private native char nGetPreprocChar(); /*
        return THIS->mPreprocChar;
    */

    private native void nSetPreprocChar(char value); /*
        THIS->mPreprocChar = value;
    */

    public boolean getAutoIndentation() {
        return nGetAutoIndentation();
    }

    public void setAutoIndentation(final boolean value) {
        nSetAutoIndentation(value);
    }

    private native boolean nGetAutoIndentation(); /*
        return THIS->mAutoIndentation;
    */

    private native void nSetAutoIndentation(boolean value); /*
        THIS->mAutoIndentation = value;
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

    public boolean getmCaseSensitive() {
        return nGetmCaseSensitive();
    }

    public void setmCaseSensitive(final boolean value) {
        nSetmCaseSensitive(value);
    }

    private native boolean nGetmCaseSensitive(); /*
        return THIS->mCaseSensitive;
    */

    private native void nSetmCaseSensitive(boolean value); /*
        THIS->mCaseSensitive = value;
    */

    private static final TextEditorLanguageDefinition _CPLUSPLUS_1 = new TextEditorLanguageDefinition(0);

    public static TextEditorLanguageDefinition CPlusPlus() {
        _CPLUSPLUS_1.ptr = nCPlusPlus();
        return _CPLUSPLUS_1;
    }

    private static native long nCPlusPlus(); /*
        return (uintptr_t)&TextEditorLanguageDefinition::CPlusPlus();
    */

    private static final TextEditorLanguageDefinition _HLSL_1 = new TextEditorLanguageDefinition(0);

    public static TextEditorLanguageDefinition HLSL() {
        _HLSL_1.ptr = nHLSL();
        return _HLSL_1;
    }

    private static native long nHLSL(); /*
        return (uintptr_t)&TextEditorLanguageDefinition::HLSL();
    */

    private static final TextEditorLanguageDefinition _GLSL_1 = new TextEditorLanguageDefinition(0);

    public static TextEditorLanguageDefinition GLSL() {
        _GLSL_1.ptr = nGLSL();
        return _GLSL_1;
    }

    private static native long nGLSL(); /*
        return (uintptr_t)&TextEditorLanguageDefinition::GLSL();
    */

    private static final TextEditorLanguageDefinition _C_1 = new TextEditorLanguageDefinition(0);

    public static TextEditorLanguageDefinition C() {
        _C_1.ptr = nC();
        return _C_1;
    }

    private static native long nC(); /*
        return (uintptr_t)&TextEditorLanguageDefinition::C();
    */

    private static final TextEditorLanguageDefinition _SQL_1 = new TextEditorLanguageDefinition(0);

    public static TextEditorLanguageDefinition SQL() {
        _SQL_1.ptr = nSQL();
        return _SQL_1;
    }

    private static native long nSQL(); /*
        return (uintptr_t)&TextEditorLanguageDefinition::SQL();
    */

    private static final TextEditorLanguageDefinition _ANGELSCRIPT_1 = new TextEditorLanguageDefinition(0);

    public static TextEditorLanguageDefinition AngelScript() {
        _ANGELSCRIPT_1.ptr = nAngelScript();
        return _ANGELSCRIPT_1;
    }

    private static native long nAngelScript(); /*
        return (uintptr_t)&TextEditorLanguageDefinition::AngelScript();
    */

    private static final TextEditorLanguageDefinition _LUA_1 = new TextEditorLanguageDefinition(0);

    public static TextEditorLanguageDefinition Lua() {
        _LUA_1.ptr = nLua();
        return _LUA_1;
    }

    private static native long nLua(); /*
        return (uintptr_t)&TextEditorLanguageDefinition::Lua();
    */

    /*JNI
        #undef TextEditorLanguageDefinition
        #undef THIS
     */
}
