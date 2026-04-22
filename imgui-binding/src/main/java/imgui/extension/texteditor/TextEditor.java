package imgui.extension.texteditor;

import imgui.ImVec2;
import imgui.binding.ImGuiStructDestroyable;
import imgui.binding.annotation.ArgValue;
import imgui.binding.annotation.BindingMethod;
import imgui.binding.annotation.BindingSource;
import imgui.binding.annotation.OptArg;
import imgui.binding.annotation.ReturnValue;
import imgui.extension.texteditor.flag.TextEditorColor;
import imgui.extension.texteditor.flag.TextEditorScroll;
import imgui.internal.ImGuiContext;

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

        static jintArray TextEditorPaletteToArray(JNIEnv* env, const TextEditor::Palette& palette) {
            const auto size = static_cast<jsize>(palette.size());
            jintArray result = env->NewIntArray(size);
            std::vector<jint> values(static_cast<size_t>(size));
            for (size_t i = 0; i < palette.size(); ++i) {
                values[i] = static_cast<jint>(palette[i]);
            }
            env->SetIntArrayRegion(result, 0, size, values.data());
            return result;
        }

        static void TextEditorArrayToPalette(JNIEnv* env, jintArray source, TextEditor::Palette& destination) {
            const auto maxCount = static_cast<jsize>(destination.size());
            auto valueCount = env->GetArrayLength(source);
            valueCount = valueCount < maxCount ? valueCount : maxCount;
            std::vector<jint> values(static_cast<size_t>(valueCount));
            env->GetIntArrayRegion(source, 0, valueCount, values.data());
            for (jsize i = 0; i < valueCount; ++i) {
                destination[static_cast<size_t>(i)] = static_cast<ImU32>(values[static_cast<size_t>(i)]);
            }
        }
     */

    private native long nCreate(); /*
        return (uintptr_t)(new TextEditor());
    */

    @BindingMethod
    public native void SetTabSize(int value);

    public void setLanguage(final TextEditorLanguage language) {
        nSetLanguage(language == null ? 0 : language.ptr);
    }

    private native void nSetLanguage(long languagePtr); /*
        THIS->SetLanguage(reinterpret_cast<const TextEditor::Language*>(languagePtr));
    */

    @BindingMethod
    public native int GetTabSize();

    @BindingMethod
    public native void SetInsertSpacesOnTabs(boolean value);

    @BindingMethod
    public native boolean IsInsertSpacesOnTabs();

    @BindingMethod
    public native void SetLineSpacing(float value);

    @BindingMethod
    public native float GetLineSpacing();

    @BindingMethod
    public native void SetReadOnlyEnabled(boolean value);

    @BindingMethod
    public native boolean IsReadOnlyEnabled();

    @BindingMethod
    public native void SetAutoIndentEnabled(boolean value);

    @BindingMethod
    public native boolean IsAutoIndentEnabled();

    @BindingMethod
    public native void SetShowWhitespacesEnabled(boolean value);

    @BindingMethod
    public native boolean IsShowWhitespacesEnabled();

    @BindingMethod
    public native void SetShowSpacesEnabled(boolean value);

    @BindingMethod
    public native boolean IsShowSpacesEnabled();

    @BindingMethod
    public native void SetShowTabsEnabled(boolean value);

    @BindingMethod
    public native boolean IsShowTabsEnabled();

    @BindingMethod
    public native void SetShowLineNumbersEnabled(boolean value);

    @BindingMethod
    public native boolean IsShowLineNumbersEnabled();

    @BindingMethod
    public native void SetShowScrollbarMiniMapEnabled(boolean value);

    @BindingMethod
    public native boolean IsShowScrollbarMiniMapEnabled();

    @BindingMethod
    public native void SetShowPanScrollIndicatorEnabled(boolean value);

    @BindingMethod
    public native boolean IsShowPanScrollIndicatorEnabled();

    @BindingMethod
    public native void SetShowMatchingBrackets(boolean value);

    @BindingMethod
    public native boolean IsShowingMatchingBrackets();

    @BindingMethod
    public native void SetCompletePairedGlyphs(boolean value);

    @BindingMethod
    public native boolean IsCompletingPairedGlyphs();

    @BindingMethod
    public native void SetOverwriteEnabled(boolean value);

    @BindingMethod
    public native boolean IsOverwriteEnabled();

    @BindingMethod
    public native void SetMiddleMousePanMode();

    @BindingMethod
    public native void SetMiddleMouseScrollMode();

    @BindingMethod
    public native boolean IsMiddleMousePanMode();

    @BindingMethod
    public native void SetText(String text);

    @BindingMethod
    @ReturnValue(callSuffix = ".c_str()")
    public native String GetText();

    @BindingMethod
    @ReturnValue(callSuffix = ".c_str()")
    public native String GetCursorText(long cursor);

    @BindingMethod
    @ReturnValue(callSuffix = ".c_str()")
    public native String GetLineText(int line);

    @BindingMethod
    @ReturnValue(callSuffix = ".c_str()")
    public native String GetSectionText(int startLine, int startColumn, int endLine, int endColumn);

    @BindingMethod
    public native void ReplaceSectionText(int startLine, int startColumn, int endLine, int endColumn, String text);

    public void clearText() {
        setText("");
    }

    @BindingMethod
    public native boolean IsEmpty();

    @BindingMethod
    public native int GetLineCount();

    @BindingMethod
    public native void Render(String title, @OptArg ImVec2 size, @OptArg boolean border);

    public void render(final String title, final boolean border) {
        render(title, new ImVec2(), border);
    }

    @BindingMethod
    public native void SetFocus();

    @BindingMethod
    public native void Cut();

    @BindingMethod
    public native void Copy();

    @BindingMethod
    public native void Paste();

    @BindingMethod
    public native void Undo();

    @BindingMethod
    public native void Redo();

    @BindingMethod
    public native boolean CanUndo();

    @BindingMethod
    public native boolean CanRedo();

    @BindingMethod
    public native long GetUndoIndex();

    @BindingMethod
    public native void SetCursor(int line, int column);

    @BindingMethod
    public native void SelectAll();

    @BindingMethod
    public native void SelectLine(int line);

    @BindingMethod
    public native void SelectLines(int start, int end);

    @BindingMethod
    public native void SelectRegion(int startLine, int startColumn, int endLine, int endColumn);

    @BindingMethod
    public native void SelectToBrackets(@OptArg boolean includeBrackets);

    @BindingMethod
    public native void GrowSelectionsToCurlyBrackets();

    @BindingMethod
    public native void ShrinkSelectionsToCurlyBrackets();

    @BindingMethod
    public native void AddNextOccurrence();

    @BindingMethod
    public native void SelectAllOccurrences();

    @BindingMethod
    public native boolean AnyCursorHasSelection();

    @BindingMethod
    public native boolean AllCursorsHaveSelection();

    @BindingMethod
    public native boolean CurrentCursorHasSelection();

    @BindingMethod
    public native void ClearCursors();

    @BindingMethod
    public native long GetNumberOfCursors();

    @BindingMethod
    public native TextEditorCursorPosition GetMainCursorPosition();

    @BindingMethod
    public native TextEditorCursorPosition GetCurrentCursorPosition();

    @BindingMethod
    public native TextEditorCursorPosition GetCursorPosition(long cursor);

    @BindingMethod
    public native TextEditorCursorSelection GetCursorSelection(long cursor);

    @BindingMethod
    public native TextEditorCursorSelection GetMainCursorSelection();

    @BindingMethod
    @ReturnValue(callSuffix = ".c_str()")
    public native String GetWordAtScreenPos(ImVec2 screenPos);

    @BindingMethod
    public native void ScrollToLine(int line, @ArgValue(staticCast = "TextEditor::Scroll") int alignment);

    public void scrollToLine(final int line, final TextEditorScroll alignment) {
        scrollToLine(line, alignment.value);
    }

    @BindingMethod
    public native int GetFirstVisibleLine();

    @BindingMethod
    public native int GetLastVisibleLine();

    @BindingMethod
    public native int GetFirstVisibleColumn();

    @BindingMethod
    public native int GetLastVisibleColumn();

    @BindingMethod
    public native float GetLineHeight();

    @BindingMethod
    public native float GetGlyphWidth();

    @BindingMethod
    public native void SelectFirstOccurrenceOf(String text, @OptArg boolean caseSensitive, @OptArg boolean wholeWord);

    @BindingMethod
    public native void SelectNextOccurrenceOf(String text, @OptArg boolean caseSensitive, @OptArg boolean wholeWord);

    @BindingMethod
    public native void SelectAllOccurrencesOf(String text, @OptArg boolean caseSensitive, @OptArg boolean wholeWord);

    @BindingMethod
    public native void ReplaceTextInCurrentCursor(String text);

    @BindingMethod
    public native void ReplaceTextInAllCursors(String text);

    @BindingMethod
    public native void OpenFindReplaceWindow();

    @BindingMethod
    public native void CloseFindReplaceWindow();

    @BindingMethod
    public native void SetFindButtonLabel(String label);

    @BindingMethod
    public native void SetFindAllButtonLabel(String label);

    @BindingMethod
    public native void SetReplaceButtonLabel(String label);

    @BindingMethod
    public native void SetReplaceAllButtonLabel(String label);

    @BindingMethod
    public native boolean HasFindString();

    @BindingMethod
    public native void FindNext();

    @BindingMethod
    public native void FindAll();

    @BindingMethod
    public native void AddMarker(int line, int lineNumberColor, int textColor, String lineNumberTooltip, String textTooltip);

    public void addMarker(final int line, final int lineNumberColor, final int textColor) {
        addMarker(line, lineNumberColor, textColor, "", "");
    }

    public void addMarker(final int line, final int lineNumberColor, final int textColor, final String lineNumberTooltip) {
        addMarker(line, lineNumberColor, textColor, lineNumberTooltip, "");
    }

    @BindingMethod
    public native void ClearMarkers();

    @BindingMethod
    public native boolean HasMarkers();

    @BindingMethod
    public native void IndentLines();

    @BindingMethod
    public native void DeindentLines();

    @BindingMethod
    public native void MoveUpLines();

    @BindingMethod
    public native void MoveDownLines();

    @BindingMethod
    public native void ToggleComments();

    @BindingMethod
    public native void SelectionToLowerCase();

    @BindingMethod
    public native void SelectionToUpperCase();

    @BindingMethod
    public native void StripTrailingWhitespaces();

    @BindingMethod
    public native void TabsToSpaces();

    @BindingMethod
    public native void SpacesToTabs();

    public native int[] getPalette(); /*
        return TextEditorPaletteToArray(env, THIS->GetPalette());
    */

    public void setPalette(final int[] palette) {
        nSetPalette(palette);
    }

    private native void nSetPalette(int[] palette); /*
        auto newPalette = THIS->GetPalette();
        TextEditorArrayToPalette(env, obj_palette, newPalette);
        THIS->SetPalette(newPalette);
    */

    public static native int[] getDefaultPalette(); /*
        return TextEditorPaletteToArray(env, TextEditor::GetDefaultPalette());
    */

    public static void setDefaultPalette(final int[] palette) {
        nSetDefaultPalette(palette);
    }

    private static native void nSetDefaultPalette(int[] palette); /*
        auto newPalette = TextEditor::GetDefaultPalette();
        TextEditorArrayToPalette(env, obj_palette, newPalette);
        TextEditor::SetDefaultPalette(newPalette);
    */

    public static native int[] getDarkPalette(); /*
        return TextEditorPaletteToArray(env, TextEditor::GetDarkPalette());
    */

    public static native int[] getLightPalette(); /*
        return TextEditorPaletteToArray(env, TextEditor::GetLightPalette());
    */

    public void setDarkPalette() {
        setPalette(getDarkPalette());
    }

    public void setLightPalette() {
        setPalette(getLightPalette());
    }

    public native int getPaletteColor(int colorIndex); /*
        const auto& palette = THIS->GetPalette();
        const auto index = static_cast<size_t>(colorIndex);
        if (index >= palette.size()) {
            jclass exClass = env->FindClass("java/lang/ArrayIndexOutOfBoundsException");
            env->ThrowNew(exClass, "Invalid TextEditor color index");
            return 0;
        }
        return static_cast<jint>(palette[index]);
    */

    public int getPaletteColor(final TextEditorColor color) {
        return getPaletteColor(color.value);
    }

    public native void setPaletteColor(int colorIndex, int value); /*
        auto palette = THIS->GetPalette();
        const auto index = static_cast<size_t>(colorIndex);
        if (index >= palette.size()) {
            jclass exClass = env->FindClass("java/lang/ArrayIndexOutOfBoundsException");
            env->ThrowNew(exClass, "Invalid TextEditor color index");
            return;
        }
        palette[index] = static_cast<ImU32>(value);
        THIS->SetPalette(palette);
    */

    public void setPaletteColor(final TextEditorColor color, final int value) {
        setPaletteColor(color.value, value);
    }

    @BindingMethod
    public native TextEditorLanguage GetLanguage();

    @BindingMethod
    public native boolean HasLanguage();

    @BindingMethod
    @ReturnValue(callSuffix = ".c_str()")
    public native String GetLanguageName();

    @BindingMethod
    public static native void SetImGuiContext(ImGuiContext ctx);

    @BindingMethod
    public native void SetUserData(int line, @ArgValue(reinterpretCast = "void*") long data);

    @BindingMethod
    @ReturnValue(callPrefix = "reinterpret_cast<jlong>(", callSuffix = ")")
    public native long GetUserData(int line);

    /*JNI
        #undef THIS
     */
}
