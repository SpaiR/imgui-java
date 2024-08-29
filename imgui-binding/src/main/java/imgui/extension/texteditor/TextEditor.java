package imgui.extension.texteditor;

import imgui.ImVec2;
import imgui.binding.ImGuiStructDestroyable;
import imgui.binding.annotation.ArgValue;
import imgui.binding.annotation.BindingMethod;
import imgui.binding.annotation.BindingSource;
import imgui.binding.annotation.OptArg;
import imgui.binding.annotation.ReturnValue;

import java.util.Map;

@BindingSource
public final class TextEditor extends ImGuiStructDestroyable {
    public TextEditor() {
        super();
    }

    public TextEditor(final long ptr) {
        super(ptr);
    }

    @Override
    protected long create() {
        return nCreate();
    }

    /*JNI
        #include "_texteditor.h"
        #define THIS ((TextEditor*)STRUCT_PTR)
        #define TextEditorLanguageDefinition TextEditor::LanguageDefinition
     */

    private native long nCreate(); /*
        return (uintptr_t)(new TextEditor());
    */

    @BindingMethod
    public native void SetLanguageDefinition(@ArgValue(callPrefix = "*") TextEditorLanguageDefinition aLanguageDef);

    @BindingMethod
    @ReturnValue(callPrefix = "&")
    public native TextEditorLanguageDefinition GetLanguageDefinition();

    public native int[] getPalette(); /*
        const auto& palette = THIS->GetPalette();
        jintArray res = env->NewIntArray(palette.size());
        jint arr[palette.size()];
        for (int i = 0; i < palette.size(); i++) {
            arr[i] = palette[i];
        }
        env->SetIntArrayRegion(res, 0, palette.size(), arr);
        return res;
    */

    public void setPalette(final int[] palette) {
        nSetPalette(palette, palette.length);
    }

    public native void nSetPalette(int[] palette, int length); /*
        std::array<ImU32, (unsigned)TextEditor::PaletteIndex::Max> arr;
        for (int i = 0; i < length; i++) {
            arr[i] = palette[i];
        }
        THIS->SetPalette(arr);
    */

    public void setErrorMarkers(final Map<Integer, String> errorMarkers) {
        final int[] keys = errorMarkers.keySet().stream().mapToInt(i -> i).toArray();
        final String[] values = errorMarkers.values().toArray(new String[0]);
        nSetErrorMarkers(keys, keys.length, values, values.length);
    }

    private native void nSetErrorMarkers(int[] keys, int keysLen, String[] values, int valuesLen); /*
        std::map<int, std::string> markers;
        for (int i = 0; i < keysLen; i++) {
            int key = keys[i];
            jstring string = (jstring)env->GetObjectArrayElement(values, i);
            const char* value = env->GetStringUTFChars(string, JNI_FALSE);
            markers.emplace(std::pair<int, std::string>(key, std::string(value)));
            env->ReleaseStringUTFChars(string, value);
        }
        THIS->SetErrorMarkers(markers);
    */

    public void setBreakpoints(final int[] breakpoints) {
        nSetBreakpoints(breakpoints, breakpoints.length);
    }

    private native void nSetBreakpoints(int[] breakpoints, int length); /*
        std::unordered_set<int> set;
        for (int i = 0; i < length; i++) {
            set.emplace(breakpoints[i]);
        }
        THIS->SetBreakpoints(set);
    */

    @BindingMethod
    public native void Render(String title, @OptArg(callValue = "ImVec2()") ImVec2 aSize, @OptArg boolean aBorder);

    @BindingMethod
    public native void SetText(String text);

    @BindingMethod
    @ReturnValue(callSuffix = ".c_str()")
    public native String GetText();

    public void setTextLines(final String[] lines) {
        nSetTextLines(lines, lines.length);
    }

    private native void nSetTextLines(String[] lines, int length); /*
        std::vector<std::string> vec;
        vec.reserve(length);
        for (int i = 0; i < length; i++) {
            jstring string = (jstring)env->GetObjectArrayElement(lines, i);
            const char* raw = env->GetStringUTFChars(string, JNI_FALSE);
            vec.emplace_back(std::string(raw));
            env->ReleaseStringUTFChars(string, raw);
        }
        THIS->SetTextLines(vec);
    */

    public native String[] getTextLines(); /*
        const auto lines = THIS->GetTextLines();

        jobjectArray arr = env->NewObjectArray(
            lines.size(),
            env->FindClass("java/lang/String"),
            env->NewStringUTF("")
        );

        for (int i = 0; i < lines.size(); i++) {
            const auto& str = lines[i];
            env->SetObjectArrayElement(arr, i, env->NewStringUTF(str.c_str()));
        }
        return arr;
    */

    @BindingMethod
    @ReturnValue(callSuffix = ".c_str()")
    public native String GetSelectedText();

    @BindingMethod
    @ReturnValue(callSuffix = ".c_str()")
    public native String GetCurrentLineText();

    @BindingMethod
    public native int GetTotalLines();

    @BindingMethod
    public native boolean IsOverwrite();

    @BindingMethod
    public native void SetReadOnly(boolean aValue);

    @BindingMethod
    public native boolean IsReadOnly();

    @BindingMethod
    public native boolean IsTextChanged();

    @BindingMethod
    public native boolean IsCursorPositionChanged();

    @BindingMethod
    public native boolean IsColorizerEnabled();

    @BindingMethod
    public native void SetColorizerEnable(boolean aValue);

    @BindingMethod
    public native TextEditorCoordinates GetCursorPosition();

    @BindingMethod
    public native void SetCursorPosition(TextEditorCoordinates aPosition);

    @BindingMethod
    public native void SetHandleMouseInputs(boolean aValue);

    @BindingMethod
    public native boolean IsHandleMouseInputsEnabled();

    @BindingMethod
    public native void SetHandleKeyboardInputs(boolean aValue);

    @BindingMethod
    public native boolean IsHandleKeyboardInputsEnabled();

    @BindingMethod
    public native void SetImGuiChildIgnored(boolean aValue);

    @BindingMethod
    public native boolean IsImGuiChildIgnored();

    @BindingMethod
    public native void SetShowWhitespaces(boolean aValue);

    @BindingMethod
    public native boolean IsShowingWhitespaces();

    @BindingMethod
    public native void SetTabSize(int aValue);

    @BindingMethod
    public native int GetTabSize();

    @BindingMethod
    public native void InsertText(String aValue);

    @BindingMethod
    public native void MoveUp(@OptArg int aAmount, @OptArg boolean aSelect);

    @BindingMethod
    public native void MoveDown(@OptArg int aAmount, @OptArg boolean aSelect);

    @BindingMethod
    public native void MoveLeft(@OptArg int aAmount, @OptArg boolean aSelect, @OptArg boolean aWordMode);

    @BindingMethod
    public native void MoveRight(@OptArg int aAmount, @OptArg boolean aSelect, @OptArg boolean aWordMode);

    @BindingMethod
    public native void MoveTop(@OptArg boolean aSelect);

    @BindingMethod
    public native void MoveBottom(@OptArg boolean aSelect);

    @BindingMethod
    public native void MoveHome(@OptArg boolean aSelect);

    @BindingMethod
    public native void MoveEnd(@OptArg boolean aSelect);

    @BindingMethod
    public native void SetSelectionStart(TextEditorCoordinates aPosition);

    @BindingMethod
    public native void SetSelectionEnd(TextEditorCoordinates aPosition);

    @BindingMethod
    public native void SetSelection(TextEditorCoordinates aStart, TextEditorCoordinates aEnd, @OptArg @ArgValue(staticCast = "TextEditor::SelectionMode") int aMode);

    @BindingMethod
    public native void SelectWordUnderCursor();

    @BindingMethod
    public native void SelectAll();

    @BindingMethod
    public native boolean HasSelection();

    @BindingMethod
    public native void Copy();

    @BindingMethod
    public native void Cut();

    @BindingMethod
    public native void Paste();

    @BindingMethod
    public native void Delete();

    @BindingMethod
    public native boolean CanUndo();

    @BindingMethod
    public native boolean CanRedo();

    @BindingMethod
    public native void Undo(@OptArg int aSteps);

    @BindingMethod
    public native void Redo(@OptArg int aSteps);

    public native int[] getDarkPalette(); /*
        const auto& palette = THIS->GetDarkPalette();
        jintArray res = env->NewIntArray(palette.size());
        jint arr[palette.size()];
        for (int i = 0; i < palette.size(); i++) {
            arr[i] = palette[i];
        }
        env->SetIntArrayRegion(res, 0, palette.size(), arr);
        return res;
    */

    public native int[] getLightPalette(); /*
        const auto& palette = THIS->GetLightPalette();
        jintArray res = env->NewIntArray(palette.size());
        jint arr[palette.size()];
        for (int i = 0; i < palette.size(); i++) {
            arr[i] = palette[i];
        }
        env->SetIntArrayRegion(res, 0, palette.size(), arr);
        return res;
    */

    public native int[] getRetroBluePalette(); /*
        const auto& palette = THIS->GetRetroBluePalette();
        jintArray res = env->NewIntArray(palette.size());
        jint arr[palette.size()];
        for (int i = 0; i < palette.size(); i++) {
            arr[i] = palette[i];
        }
        env->SetIntArrayRegion(res, 0, palette.size(), arr);
        return res;
    */

    /*JNI
        #undef TextEditorLanguageDefinition
        #undef THIS
     */
}
