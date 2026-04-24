package imgui.extension.texteditor;

import imgui.ImVec2;
import imgui.binding.ImGuiStructDestroyable;





import imgui.extension.texteditor.flag.TextEditorColor;
import imgui.extension.texteditor.flag.TextEditorScroll;
import imgui.internal.ImGuiContext;


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

    public void setTabSize(final int value) {
        nSetTabSize(value);
    }

    private native void nSetTabSize(int value); /*
        THIS->SetTabSize(value);
    */

    public void setLanguage(final TextEditorLanguage language) {
        nSetLanguage(language == null ? 0 : language.ptr);
    }

    private native void nSetLanguage(long languagePtr); /*
        THIS->SetLanguage(reinterpret_cast<const TextEditor::Language*>(languagePtr));
    */

    public int getTabSize() {
        return nGetTabSize();
    }

    private native int nGetTabSize(); /*
        return THIS->GetTabSize();
    */

    public void setInsertSpacesOnTabs(final boolean value) {
        nSetInsertSpacesOnTabs(value);
    }

    private native void nSetInsertSpacesOnTabs(boolean value); /*
        THIS->SetInsertSpacesOnTabs(value);
    */

    public boolean isInsertSpacesOnTabs() {
        return nIsInsertSpacesOnTabs();
    }

    private native boolean nIsInsertSpacesOnTabs(); /*
        return THIS->IsInsertSpacesOnTabs();
    */

    public void setLineSpacing(final float value) {
        nSetLineSpacing(value);
    }

    private native void nSetLineSpacing(float value); /*
        THIS->SetLineSpacing(value);
    */

    public float getLineSpacing() {
        return nGetLineSpacing();
    }

    private native float nGetLineSpacing(); /*
        return THIS->GetLineSpacing();
    */

    public void setReadOnlyEnabled(final boolean value) {
        nSetReadOnlyEnabled(value);
    }

    private native void nSetReadOnlyEnabled(boolean value); /*
        THIS->SetReadOnlyEnabled(value);
    */

    public boolean isReadOnlyEnabled() {
        return nIsReadOnlyEnabled();
    }

    private native boolean nIsReadOnlyEnabled(); /*
        return THIS->IsReadOnlyEnabled();
    */

    public void setAutoIndentEnabled(final boolean value) {
        nSetAutoIndentEnabled(value);
    }

    private native void nSetAutoIndentEnabled(boolean value); /*
        THIS->SetAutoIndentEnabled(value);
    */

    public boolean isAutoIndentEnabled() {
        return nIsAutoIndentEnabled();
    }

    private native boolean nIsAutoIndentEnabled(); /*
        return THIS->IsAutoIndentEnabled();
    */

    public void setShowWhitespacesEnabled(final boolean value) {
        nSetShowWhitespacesEnabled(value);
    }

    private native void nSetShowWhitespacesEnabled(boolean value); /*
        THIS->SetShowWhitespacesEnabled(value);
    */

    public boolean isShowWhitespacesEnabled() {
        return nIsShowWhitespacesEnabled();
    }

    private native boolean nIsShowWhitespacesEnabled(); /*
        return THIS->IsShowWhitespacesEnabled();
    */

    public void setShowSpacesEnabled(final boolean value) {
        nSetShowSpacesEnabled(value);
    }

    private native void nSetShowSpacesEnabled(boolean value); /*
        THIS->SetShowSpacesEnabled(value);
    */

    public boolean isShowSpacesEnabled() {
        return nIsShowSpacesEnabled();
    }

    private native boolean nIsShowSpacesEnabled(); /*
        return THIS->IsShowSpacesEnabled();
    */

    public void setShowTabsEnabled(final boolean value) {
        nSetShowTabsEnabled(value);
    }

    private native void nSetShowTabsEnabled(boolean value); /*
        THIS->SetShowTabsEnabled(value);
    */

    public boolean isShowTabsEnabled() {
        return nIsShowTabsEnabled();
    }

    private native boolean nIsShowTabsEnabled(); /*
        return THIS->IsShowTabsEnabled();
    */

    public void setShowLineNumbersEnabled(final boolean value) {
        nSetShowLineNumbersEnabled(value);
    }

    private native void nSetShowLineNumbersEnabled(boolean value); /*
        THIS->SetShowLineNumbersEnabled(value);
    */

    public boolean isShowLineNumbersEnabled() {
        return nIsShowLineNumbersEnabled();
    }

    private native boolean nIsShowLineNumbersEnabled(); /*
        return THIS->IsShowLineNumbersEnabled();
    */

    public void setShowScrollbarMiniMapEnabled(final boolean value) {
        nSetShowScrollbarMiniMapEnabled(value);
    }

    private native void nSetShowScrollbarMiniMapEnabled(boolean value); /*
        THIS->SetShowScrollbarMiniMapEnabled(value);
    */

    public boolean isShowScrollbarMiniMapEnabled() {
        return nIsShowScrollbarMiniMapEnabled();
    }

    private native boolean nIsShowScrollbarMiniMapEnabled(); /*
        return THIS->IsShowScrollbarMiniMapEnabled();
    */

    public void setShowPanScrollIndicatorEnabled(final boolean value) {
        nSetShowPanScrollIndicatorEnabled(value);
    }

    private native void nSetShowPanScrollIndicatorEnabled(boolean value); /*
        THIS->SetShowPanScrollIndicatorEnabled(value);
    */

    public boolean isShowPanScrollIndicatorEnabled() {
        return nIsShowPanScrollIndicatorEnabled();
    }

    private native boolean nIsShowPanScrollIndicatorEnabled(); /*
        return THIS->IsShowPanScrollIndicatorEnabled();
    */

    public void setShowMatchingBrackets(final boolean value) {
        nSetShowMatchingBrackets(value);
    }

    private native void nSetShowMatchingBrackets(boolean value); /*
        THIS->SetShowMatchingBrackets(value);
    */

    public boolean isShowingMatchingBrackets() {
        return nIsShowingMatchingBrackets();
    }

    private native boolean nIsShowingMatchingBrackets(); /*
        return THIS->IsShowingMatchingBrackets();
    */

    public void setCompletePairedGlyphs(final boolean value) {
        nSetCompletePairedGlyphs(value);
    }

    private native void nSetCompletePairedGlyphs(boolean value); /*
        THIS->SetCompletePairedGlyphs(value);
    */

    public boolean isCompletingPairedGlyphs() {
        return nIsCompletingPairedGlyphs();
    }

    private native boolean nIsCompletingPairedGlyphs(); /*
        return THIS->IsCompletingPairedGlyphs();
    */

    public void setOverwriteEnabled(final boolean value) {
        nSetOverwriteEnabled(value);
    }

    private native void nSetOverwriteEnabled(boolean value); /*
        THIS->SetOverwriteEnabled(value);
    */

    public boolean isOverwriteEnabled() {
        return nIsOverwriteEnabled();
    }

    private native boolean nIsOverwriteEnabled(); /*
        return THIS->IsOverwriteEnabled();
    */

    public void setMiddleMousePanMode() {
        nSetMiddleMousePanMode();
    }

    private native void nSetMiddleMousePanMode(); /*
        THIS->SetMiddleMousePanMode();
    */

    public void setMiddleMouseScrollMode() {
        nSetMiddleMouseScrollMode();
    }

    private native void nSetMiddleMouseScrollMode(); /*
        THIS->SetMiddleMouseScrollMode();
    */

    public boolean isMiddleMousePanMode() {
        return nIsMiddleMousePanMode();
    }

    private native boolean nIsMiddleMousePanMode(); /*
        return THIS->IsMiddleMousePanMode();
    */

    public void setText(final String text) {
        nSetText(text);
    }

    private native void nSetText(String text); /*MANUAL
        auto text = obj_text == NULL ? NULL : (char*)env->GetStringUTFChars(obj_text, JNI_FALSE);
        THIS->SetText(text);
        if (text != NULL) env->ReleaseStringUTFChars(obj_text, text);
    */

    public String getText() {
        return nGetText();
    }

    private native String nGetText(); /*
        return env->NewStringUTF(THIS->GetText().c_str());
    */

    public String getCursorText(final long cursor) {
        return nGetCursorText(cursor);
    }

    private native String nGetCursorText(long cursor); /*
        return env->NewStringUTF(THIS->GetCursorText(cursor).c_str());
    */

    public String getLineText(final int line) {
        return nGetLineText(line);
    }

    private native String nGetLineText(int line); /*
        return env->NewStringUTF(THIS->GetLineText(line).c_str());
    */

    public String getSectionText(final int startLine, final int startColumn, final int endLine, final int endColumn) {
        return nGetSectionText(startLine, startColumn, endLine, endColumn);
    }

    private native String nGetSectionText(int startLine, int startColumn, int endLine, int endColumn); /*
        return env->NewStringUTF(THIS->GetSectionText(startLine, startColumn, endLine, endColumn).c_str());
    */

    public void replaceSectionText(final int startLine, final int startColumn, final int endLine, final int endColumn, final String text) {
        nReplaceSectionText(startLine, startColumn, endLine, endColumn, text);
    }

    private native void nReplaceSectionText(int startLine, int startColumn, int endLine, int endColumn, String text); /*MANUAL
        auto text = obj_text == NULL ? NULL : (char*)env->GetStringUTFChars(obj_text, JNI_FALSE);
        THIS->ReplaceSectionText(startLine, startColumn, endLine, endColumn, text);
        if (text != NULL) env->ReleaseStringUTFChars(obj_text, text);
    */

    public void clearText() {
        setText("");
    }

    public boolean isEmpty() {
        return nIsEmpty();
    }

    private native boolean nIsEmpty(); /*
        return THIS->IsEmpty();
    */

    public int getLineCount() {
        return nGetLineCount();
    }

    private native int nGetLineCount(); /*
        return THIS->GetLineCount();
    */

    public void render(final String title) {
        nRender(title);
    }

    public void render(final String title, final ImVec2 size) {
        nRender(title, size.x, size.y);
    }

    public void render(final String title, final float sizeX, final float sizeY) {
        nRender(title, sizeX, sizeY);
    }

    public void render(final String title, final ImVec2 size, final boolean border) {
        nRender(title, size.x, size.y, border);
    }

    public void render(final String title, final float sizeX, final float sizeY, final boolean border) {
        nRender(title, sizeX, sizeY, border);
    }

    private native void nRender(String title); /*MANUAL
        auto title = obj_title == NULL ? NULL : (char*)env->GetStringUTFChars(obj_title, JNI_FALSE);
        THIS->Render(title);
        if (title != NULL) env->ReleaseStringUTFChars(obj_title, title);
    */

    private native void nRender(String title, float sizeX, float sizeY); /*MANUAL
        auto title = obj_title == NULL ? NULL : (char*)env->GetStringUTFChars(obj_title, JNI_FALSE);
        ImVec2 size = ImVec2(sizeX, sizeY);
        THIS->Render(title, size);
        if (title != NULL) env->ReleaseStringUTFChars(obj_title, title);
    */

    private native void nRender(String title, float sizeX, float sizeY, boolean border); /*MANUAL
        auto title = obj_title == NULL ? NULL : (char*)env->GetStringUTFChars(obj_title, JNI_FALSE);
        ImVec2 size = ImVec2(sizeX, sizeY);
        THIS->Render(title, size, border);
        if (title != NULL) env->ReleaseStringUTFChars(obj_title, title);
    */

    public void render(final String title, final boolean border) {
        render(title, new ImVec2(), border);
    }

    public void setFocus() {
        nSetFocus();
    }

    private native void nSetFocus(); /*
        THIS->SetFocus();
    */

    public void cut() {
        nCut();
    }

    private native void nCut(); /*
        THIS->Cut();
    */

    public void copy() {
        nCopy();
    }

    private native void nCopy(); /*
        THIS->Copy();
    */

    public void paste() {
        nPaste();
    }

    private native void nPaste(); /*
        THIS->Paste();
    */

    public void undo() {
        nUndo();
    }

    private native void nUndo(); /*
        THIS->Undo();
    */

    public void redo() {
        nRedo();
    }

    private native void nRedo(); /*
        THIS->Redo();
    */

    public boolean canUndo() {
        return nCanUndo();
    }

    private native boolean nCanUndo(); /*
        return THIS->CanUndo();
    */

    public boolean canRedo() {
        return nCanRedo();
    }

    private native boolean nCanRedo(); /*
        return THIS->CanRedo();
    */

    public long getUndoIndex() {
        return nGetUndoIndex();
    }

    private native long nGetUndoIndex(); /*
        return THIS->GetUndoIndex();
    */

    public void setCursor(final int line, final int column) {
        nSetCursor(line, column);
    }

    private native void nSetCursor(int line, int column); /*
        THIS->SetCursor(line, column);
    */

    public void selectAll() {
        nSelectAll();
    }

    private native void nSelectAll(); /*
        THIS->SelectAll();
    */

    public void selectLine(final int line) {
        nSelectLine(line);
    }

    private native void nSelectLine(int line); /*
        THIS->SelectLine(line);
    */

    public void selectLines(final int start, final int end) {
        nSelectLines(start, end);
    }

    private native void nSelectLines(int start, int end); /*
        THIS->SelectLines(start, end);
    */

    public void selectRegion(final int startLine, final int startColumn, final int endLine, final int endColumn) {
        nSelectRegion(startLine, startColumn, endLine, endColumn);
    }

    private native void nSelectRegion(int startLine, int startColumn, int endLine, int endColumn); /*
        THIS->SelectRegion(startLine, startColumn, endLine, endColumn);
    */

    public void selectToBrackets() {
        nSelectToBrackets();
    }

    public void selectToBrackets(final boolean includeBrackets) {
        nSelectToBrackets(includeBrackets);
    }

    private native void nSelectToBrackets(); /*
        THIS->SelectToBrackets();
    */

    private native void nSelectToBrackets(boolean includeBrackets); /*
        THIS->SelectToBrackets(includeBrackets);
    */

    public void growSelectionsToCurlyBrackets() {
        nGrowSelectionsToCurlyBrackets();
    }

    private native void nGrowSelectionsToCurlyBrackets(); /*
        THIS->GrowSelectionsToCurlyBrackets();
    */

    public void shrinkSelectionsToCurlyBrackets() {
        nShrinkSelectionsToCurlyBrackets();
    }

    private native void nShrinkSelectionsToCurlyBrackets(); /*
        THIS->ShrinkSelectionsToCurlyBrackets();
    */

    public void addNextOccurrence() {
        nAddNextOccurrence();
    }

    private native void nAddNextOccurrence(); /*
        THIS->AddNextOccurrence();
    */

    public void selectAllOccurrences() {
        nSelectAllOccurrences();
    }

    private native void nSelectAllOccurrences(); /*
        THIS->SelectAllOccurrences();
    */

    public boolean anyCursorHasSelection() {
        return nAnyCursorHasSelection();
    }

    private native boolean nAnyCursorHasSelection(); /*
        return THIS->AnyCursorHasSelection();
    */

    public boolean allCursorsHaveSelection() {
        return nAllCursorsHaveSelection();
    }

    private native boolean nAllCursorsHaveSelection(); /*
        return THIS->AllCursorsHaveSelection();
    */

    public boolean currentCursorHasSelection() {
        return nCurrentCursorHasSelection();
    }

    private native boolean nCurrentCursorHasSelection(); /*
        return THIS->CurrentCursorHasSelection();
    */

    public void clearCursors() {
        nClearCursors();
    }

    private native void nClearCursors(); /*
        THIS->ClearCursors();
    */

    public long getNumberOfCursors() {
        return nGetNumberOfCursors();
    }

    private native long nGetNumberOfCursors(); /*
        return THIS->GetNumberOfCursors();
    */

    public TextEditorCursorPosition getMainCursorPosition() {
        final TextEditorCursorPosition dst = new TextEditorCursorPosition();
        nGetMainCursorPosition(dst);
        return dst;
    }

    public void getMainCursorPosition(final TextEditorCursorPosition dst) {
        nGetMainCursorPosition(dst);
    }

    private native void nGetMainCursorPosition(TextEditorCursorPosition dst); /*
        Jni::TextEditorCursorPositionCpy(env, THIS->GetMainCursorPosition(), dst);
    */

    public TextEditorCursorPosition getCurrentCursorPosition() {
        final TextEditorCursorPosition dst = new TextEditorCursorPosition();
        nGetCurrentCursorPosition(dst);
        return dst;
    }

    public void getCurrentCursorPosition(final TextEditorCursorPosition dst) {
        nGetCurrentCursorPosition(dst);
    }

    private native void nGetCurrentCursorPosition(TextEditorCursorPosition dst); /*
        Jni::TextEditorCursorPositionCpy(env, THIS->GetCurrentCursorPosition(), dst);
    */

    public TextEditorCursorPosition getCursorPosition(final long cursor) {
        final TextEditorCursorPosition dst = new TextEditorCursorPosition();
        nGetCursorPosition(dst, cursor);
        return dst;
    }

    public void getCursorPosition(final TextEditorCursorPosition dst, final long cursor) {
        nGetCursorPosition(dst, cursor);
    }

    private native void nGetCursorPosition(TextEditorCursorPosition dst, long cursor); /*
        Jni::TextEditorCursorPositionCpy(env, THIS->GetCursorPosition(cursor), dst);
    */

    public TextEditorCursorSelection getCursorSelection(final long cursor) {
        final TextEditorCursorSelection dst = new TextEditorCursorSelection();
        nGetCursorSelection(dst, cursor);
        return dst;
    }

    public void getCursorSelection(final TextEditorCursorSelection dst, final long cursor) {
        nGetCursorSelection(dst, cursor);
    }

    private native void nGetCursorSelection(TextEditorCursorSelection dst, long cursor); /*
        Jni::TextEditorCursorSelectionCpy(env, THIS->GetCursorSelection(cursor), dst);
    */

    public TextEditorCursorSelection getMainCursorSelection() {
        final TextEditorCursorSelection dst = new TextEditorCursorSelection();
        nGetMainCursorSelection(dst);
        return dst;
    }

    public void getMainCursorSelection(final TextEditorCursorSelection dst) {
        nGetMainCursorSelection(dst);
    }

    private native void nGetMainCursorSelection(TextEditorCursorSelection dst); /*
        Jni::TextEditorCursorSelectionCpy(env, THIS->GetMainCursorSelection(), dst);
    */

    public String getWordAtScreenPos(final ImVec2 screenPos) {
        return nGetWordAtScreenPos(screenPos.x, screenPos.y);
    }

    public String getWordAtScreenPos(final float screenPosX, final float screenPosY) {
        return nGetWordAtScreenPos(screenPosX, screenPosY);
    }

    private native String nGetWordAtScreenPos(float screenPosX, float screenPosY); /*MANUAL
        ImVec2 screenPos = ImVec2(screenPosX, screenPosY);
        auto _result = env->NewStringUTF(THIS->GetWordAtScreenPos(screenPos).c_str());
        return _result;
    */

    public void scrollToLine(final int line, final int alignment) {
        nScrollToLine(line, alignment);
    }

    private native void nScrollToLine(int line, int alignment); /*
        THIS->ScrollToLine(line, static_cast<TextEditor::Scroll>(alignment));
    */

    public void scrollToLine(final int line, final TextEditorScroll alignment) {
        scrollToLine(line, alignment.value);
    }

    public int getFirstVisibleLine() {
        return nGetFirstVisibleLine();
    }

    private native int nGetFirstVisibleLine(); /*
        return THIS->GetFirstVisibleLine();
    */

    public int getLastVisibleLine() {
        return nGetLastVisibleLine();
    }

    private native int nGetLastVisibleLine(); /*
        return THIS->GetLastVisibleLine();
    */

    public int getFirstVisibleColumn() {
        return nGetFirstVisibleColumn();
    }

    private native int nGetFirstVisibleColumn(); /*
        return THIS->GetFirstVisibleColumn();
    */

    public int getLastVisibleColumn() {
        return nGetLastVisibleColumn();
    }

    private native int nGetLastVisibleColumn(); /*
        return THIS->GetLastVisibleColumn();
    */

    public float getLineHeight() {
        return nGetLineHeight();
    }

    private native float nGetLineHeight(); /*
        return THIS->GetLineHeight();
    */

    public float getGlyphWidth() {
        return nGetGlyphWidth();
    }

    private native float nGetGlyphWidth(); /*
        return THIS->GetGlyphWidth();
    */

    public void selectFirstOccurrenceOf(final String text) {
        nSelectFirstOccurrenceOf(text);
    }

    public void selectFirstOccurrenceOf(final String text, final boolean caseSensitive) {
        nSelectFirstOccurrenceOf(text, caseSensitive);
    }

    public void selectFirstOccurrenceOf(final String text, final boolean caseSensitive, final boolean wholeWord) {
        nSelectFirstOccurrenceOf(text, caseSensitive, wholeWord);
    }

    private native void nSelectFirstOccurrenceOf(String text); /*MANUAL
        auto text = obj_text == NULL ? NULL : (char*)env->GetStringUTFChars(obj_text, JNI_FALSE);
        THIS->SelectFirstOccurrenceOf(text);
        if (text != NULL) env->ReleaseStringUTFChars(obj_text, text);
    */

    private native void nSelectFirstOccurrenceOf(String text, boolean caseSensitive); /*MANUAL
        auto text = obj_text == NULL ? NULL : (char*)env->GetStringUTFChars(obj_text, JNI_FALSE);
        THIS->SelectFirstOccurrenceOf(text, caseSensitive);
        if (text != NULL) env->ReleaseStringUTFChars(obj_text, text);
    */

    private native void nSelectFirstOccurrenceOf(String text, boolean caseSensitive, boolean wholeWord); /*MANUAL
        auto text = obj_text == NULL ? NULL : (char*)env->GetStringUTFChars(obj_text, JNI_FALSE);
        THIS->SelectFirstOccurrenceOf(text, caseSensitive, wholeWord);
        if (text != NULL) env->ReleaseStringUTFChars(obj_text, text);
    */

    public void selectNextOccurrenceOf(final String text) {
        nSelectNextOccurrenceOf(text);
    }

    public void selectNextOccurrenceOf(final String text, final boolean caseSensitive) {
        nSelectNextOccurrenceOf(text, caseSensitive);
    }

    public void selectNextOccurrenceOf(final String text, final boolean caseSensitive, final boolean wholeWord) {
        nSelectNextOccurrenceOf(text, caseSensitive, wholeWord);
    }

    private native void nSelectNextOccurrenceOf(String text); /*MANUAL
        auto text = obj_text == NULL ? NULL : (char*)env->GetStringUTFChars(obj_text, JNI_FALSE);
        THIS->SelectNextOccurrenceOf(text);
        if (text != NULL) env->ReleaseStringUTFChars(obj_text, text);
    */

    private native void nSelectNextOccurrenceOf(String text, boolean caseSensitive); /*MANUAL
        auto text = obj_text == NULL ? NULL : (char*)env->GetStringUTFChars(obj_text, JNI_FALSE);
        THIS->SelectNextOccurrenceOf(text, caseSensitive);
        if (text != NULL) env->ReleaseStringUTFChars(obj_text, text);
    */

    private native void nSelectNextOccurrenceOf(String text, boolean caseSensitive, boolean wholeWord); /*MANUAL
        auto text = obj_text == NULL ? NULL : (char*)env->GetStringUTFChars(obj_text, JNI_FALSE);
        THIS->SelectNextOccurrenceOf(text, caseSensitive, wholeWord);
        if (text != NULL) env->ReleaseStringUTFChars(obj_text, text);
    */

    public void selectAllOccurrencesOf(final String text) {
        nSelectAllOccurrencesOf(text);
    }

    public void selectAllOccurrencesOf(final String text, final boolean caseSensitive) {
        nSelectAllOccurrencesOf(text, caseSensitive);
    }

    public void selectAllOccurrencesOf(final String text, final boolean caseSensitive, final boolean wholeWord) {
        nSelectAllOccurrencesOf(text, caseSensitive, wholeWord);
    }

    private native void nSelectAllOccurrencesOf(String text); /*MANUAL
        auto text = obj_text == NULL ? NULL : (char*)env->GetStringUTFChars(obj_text, JNI_FALSE);
        THIS->SelectAllOccurrencesOf(text);
        if (text != NULL) env->ReleaseStringUTFChars(obj_text, text);
    */

    private native void nSelectAllOccurrencesOf(String text, boolean caseSensitive); /*MANUAL
        auto text = obj_text == NULL ? NULL : (char*)env->GetStringUTFChars(obj_text, JNI_FALSE);
        THIS->SelectAllOccurrencesOf(text, caseSensitive);
        if (text != NULL) env->ReleaseStringUTFChars(obj_text, text);
    */

    private native void nSelectAllOccurrencesOf(String text, boolean caseSensitive, boolean wholeWord); /*MANUAL
        auto text = obj_text == NULL ? NULL : (char*)env->GetStringUTFChars(obj_text, JNI_FALSE);
        THIS->SelectAllOccurrencesOf(text, caseSensitive, wholeWord);
        if (text != NULL) env->ReleaseStringUTFChars(obj_text, text);
    */

    public void replaceTextInCurrentCursor(final String text) {
        nReplaceTextInCurrentCursor(text);
    }

    private native void nReplaceTextInCurrentCursor(String text); /*MANUAL
        auto text = obj_text == NULL ? NULL : (char*)env->GetStringUTFChars(obj_text, JNI_FALSE);
        THIS->ReplaceTextInCurrentCursor(text);
        if (text != NULL) env->ReleaseStringUTFChars(obj_text, text);
    */

    public void replaceTextInAllCursors(final String text) {
        nReplaceTextInAllCursors(text);
    }

    private native void nReplaceTextInAllCursors(String text); /*MANUAL
        auto text = obj_text == NULL ? NULL : (char*)env->GetStringUTFChars(obj_text, JNI_FALSE);
        THIS->ReplaceTextInAllCursors(text);
        if (text != NULL) env->ReleaseStringUTFChars(obj_text, text);
    */

    public void openFindReplaceWindow() {
        nOpenFindReplaceWindow();
    }

    private native void nOpenFindReplaceWindow(); /*
        THIS->OpenFindReplaceWindow();
    */

    public void closeFindReplaceWindow() {
        nCloseFindReplaceWindow();
    }

    private native void nCloseFindReplaceWindow(); /*
        THIS->CloseFindReplaceWindow();
    */

    public void setFindButtonLabel(final String label) {
        nSetFindButtonLabel(label);
    }

    private native void nSetFindButtonLabel(String label); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        THIS->SetFindButtonLabel(label);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
    */

    public void setFindAllButtonLabel(final String label) {
        nSetFindAllButtonLabel(label);
    }

    private native void nSetFindAllButtonLabel(String label); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        THIS->SetFindAllButtonLabel(label);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
    */

    public void setReplaceButtonLabel(final String label) {
        nSetReplaceButtonLabel(label);
    }

    private native void nSetReplaceButtonLabel(String label); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        THIS->SetReplaceButtonLabel(label);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
    */

    public void setReplaceAllButtonLabel(final String label) {
        nSetReplaceAllButtonLabel(label);
    }

    private native void nSetReplaceAllButtonLabel(String label); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        THIS->SetReplaceAllButtonLabel(label);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
    */

    public boolean hasFindString() {
        return nHasFindString();
    }

    private native boolean nHasFindString(); /*
        return THIS->HasFindString();
    */

    public void findNext() {
        nFindNext();
    }

    private native void nFindNext(); /*
        THIS->FindNext();
    */

    public void findAll() {
        nFindAll();
    }

    private native void nFindAll(); /*
        THIS->FindAll();
    */

    public void addMarker(final int line, final int lineNumberColor, final int textColor, final String lineNumberTooltip, final String textTooltip) {
        nAddMarker(line, lineNumberColor, textColor, lineNumberTooltip, textTooltip);
    }

    private native void nAddMarker(int line, int lineNumberColor, int textColor, String lineNumberTooltip, String textTooltip); /*MANUAL
        auto lineNumberTooltip = obj_lineNumberTooltip == NULL ? NULL : (char*)env->GetStringUTFChars(obj_lineNumberTooltip, JNI_FALSE);
        auto textTooltip = obj_textTooltip == NULL ? NULL : (char*)env->GetStringUTFChars(obj_textTooltip, JNI_FALSE);
        THIS->AddMarker(line, lineNumberColor, textColor, lineNumberTooltip, textTooltip);
        if (lineNumberTooltip != NULL) env->ReleaseStringUTFChars(obj_lineNumberTooltip, lineNumberTooltip);
        if (textTooltip != NULL) env->ReleaseStringUTFChars(obj_textTooltip, textTooltip);
    */

    public void addMarker(final int line, final int lineNumberColor, final int textColor) {
        addMarker(line, lineNumberColor, textColor, "", "");
    }

    public void addMarker(final int line, final int lineNumberColor, final int textColor, final String lineNumberTooltip) {
        addMarker(line, lineNumberColor, textColor, lineNumberTooltip, "");
    }

    public void clearMarkers() {
        nClearMarkers();
    }

    private native void nClearMarkers(); /*
        THIS->ClearMarkers();
    */

    public boolean hasMarkers() {
        return nHasMarkers();
    }

    private native boolean nHasMarkers(); /*
        return THIS->HasMarkers();
    */

    public void indentLines() {
        nIndentLines();
    }

    private native void nIndentLines(); /*
        THIS->IndentLines();
    */

    public void deindentLines() {
        nDeindentLines();
    }

    private native void nDeindentLines(); /*
        THIS->DeindentLines();
    */

    public void moveUpLines() {
        nMoveUpLines();
    }

    private native void nMoveUpLines(); /*
        THIS->MoveUpLines();
    */

    public void moveDownLines() {
        nMoveDownLines();
    }

    private native void nMoveDownLines(); /*
        THIS->MoveDownLines();
    */

    public void toggleComments() {
        nToggleComments();
    }

    private native void nToggleComments(); /*
        THIS->ToggleComments();
    */

    public void selectionToLowerCase() {
        nSelectionToLowerCase();
    }

    private native void nSelectionToLowerCase(); /*
        THIS->SelectionToLowerCase();
    */

    public void selectionToUpperCase() {
        nSelectionToUpperCase();
    }

    private native void nSelectionToUpperCase(); /*
        THIS->SelectionToUpperCase();
    */

    public void stripTrailingWhitespaces() {
        nStripTrailingWhitespaces();
    }

    private native void nStripTrailingWhitespaces(); /*
        THIS->StripTrailingWhitespaces();
    */

    public void tabsToSpaces() {
        nTabsToSpaces();
    }

    private native void nTabsToSpaces(); /*
        THIS->TabsToSpaces();
    */

    public void spacesToTabs() {
        nSpacesToTabs();
    }

    private native void nSpacesToTabs(); /*
        THIS->SpacesToTabs();
    */

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

    public TextEditorLanguage getLanguage() {
        return new TextEditorLanguage(nGetLanguage());
    }

    private native long nGetLanguage(); /*
        return (uintptr_t)THIS->GetLanguage();
    */

    public boolean hasLanguage() {
        return nHasLanguage();
    }

    private native boolean nHasLanguage(); /*
        return THIS->HasLanguage();
    */

    public String getLanguageName() {
        return nGetLanguageName();
    }

    private native String nGetLanguageName(); /*
        return env->NewStringUTF(THIS->GetLanguageName().c_str());
    */

    public static void setImGuiContext(final ImGuiContext ctx) {
        nSetImGuiContext(ctx.ptr);
    }

    private static native void nSetImGuiContext(long ctx); /*
        TextEditor::SetImGuiContext(reinterpret_cast<ImGuiContext*>(ctx));
    */

    public void setUserData(final int line, final long data) {
        nSetUserData(line, data);
    }

    private native void nSetUserData(int line, long data); /*
        THIS->SetUserData(line, reinterpret_cast<void*>(data));
    */

    public long getUserData(final int line) {
        return nGetUserData(line);
    }

    private native long nGetUserData(int line); /*
        return reinterpret_cast<jlong>(THIS->GetUserData(line));
    */

    /*JNI
        #undef THIS
     */
}
