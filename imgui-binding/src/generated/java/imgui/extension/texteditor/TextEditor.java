package imgui.extension.texteditor;

import imgui.ImVec2;
import imgui.binding.ImGuiStructDestroyable;






import java.util.Map;


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

    public void setLanguageDefinition(final TextEditorLanguageDefinition aLanguageDef) {
        nSetLanguageDefinition(aLanguageDef.ptr);
    }

    private native void nSetLanguageDefinition(long aLanguageDef); /*
        THIS->SetLanguageDefinition(*reinterpret_cast<TextEditorLanguageDefinition*>(aLanguageDef));
    */

    public TextEditorLanguageDefinition getLanguageDefinition() {
        return new TextEditorLanguageDefinition(nGetLanguageDefinition());
    }

    private native long nGetLanguageDefinition(); /*
        return (uintptr_t)&THIS->GetLanguageDefinition();
    */

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

    public void render(final String title) {
        nRender(title);
    }

    public void render(final String title, final ImVec2 aSize) {
        nRender(title, aSize.x, aSize.y);
    }

    public void render(final String title, final float aSizeX, final float aSizeY) {
        nRender(title, aSizeX, aSizeY);
    }

    public void render(final String title, final ImVec2 aSize, final boolean aBorder) {
        nRender(title, aSize.x, aSize.y, aBorder);
    }

    public void render(final String title, final float aSizeX, final float aSizeY, final boolean aBorder) {
        nRender(title, aSizeX, aSizeY, aBorder);
    }

    public void render(final String title, final boolean aBorder) {
        nRender(title, aBorder);
    }

    private native void nRender(String title); /*MANUAL
        auto title = obj_title == NULL ? NULL : (char*)env->GetStringUTFChars(obj_title, JNI_FALSE);
        THIS->Render(title);
        if (title != NULL) env->ReleaseStringUTFChars(obj_title, title);
    */

    private native void nRender(String title, float aSizeX, float aSizeY); /*MANUAL
        auto title = obj_title == NULL ? NULL : (char*)env->GetStringUTFChars(obj_title, JNI_FALSE);
        ImVec2 aSize = ImVec2(aSizeX, aSizeY);
        THIS->Render(title, aSize);
        if (title != NULL) env->ReleaseStringUTFChars(obj_title, title);
    */

    private native void nRender(String title, float aSizeX, float aSizeY, boolean aBorder); /*MANUAL
        auto title = obj_title == NULL ? NULL : (char*)env->GetStringUTFChars(obj_title, JNI_FALSE);
        ImVec2 aSize = ImVec2(aSizeX, aSizeY);
        THIS->Render(title, aSize, aBorder);
        if (title != NULL) env->ReleaseStringUTFChars(obj_title, title);
    */

    private native void nRender(String title, boolean aBorder); /*MANUAL
        auto title = obj_title == NULL ? NULL : (char*)env->GetStringUTFChars(obj_title, JNI_FALSE);
        THIS->Render(title, ImVec2(), aBorder);
        if (title != NULL) env->ReleaseStringUTFChars(obj_title, title);
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

    public String getSelectedText() {
        return nGetSelectedText();
    }

    private native String nGetSelectedText(); /*
        return env->NewStringUTF(THIS->GetSelectedText().c_str());
    */

    public String getCurrentLineText() {
        return nGetCurrentLineText();
    }

    private native String nGetCurrentLineText(); /*
        return env->NewStringUTF(THIS->GetCurrentLineText().c_str());
    */

    public int getTotalLines() {
        return nGetTotalLines();
    }

    private native int nGetTotalLines(); /*
        return THIS->GetTotalLines();
    */

    public boolean isOverwrite() {
        return nIsOverwrite();
    }

    private native boolean nIsOverwrite(); /*
        return THIS->IsOverwrite();
    */

    public void setReadOnly(final boolean aValue) {
        nSetReadOnly(aValue);
    }

    private native void nSetReadOnly(boolean aValue); /*
        THIS->SetReadOnly(aValue);
    */

    public boolean isReadOnly() {
        return nIsReadOnly();
    }

    private native boolean nIsReadOnly(); /*
        return THIS->IsReadOnly();
    */

    public boolean isTextChanged() {
        return nIsTextChanged();
    }

    private native boolean nIsTextChanged(); /*
        return THIS->IsTextChanged();
    */

    public boolean isCursorPositionChanged() {
        return nIsCursorPositionChanged();
    }

    private native boolean nIsCursorPositionChanged(); /*
        return THIS->IsCursorPositionChanged();
    */

    public boolean isColorizerEnabled() {
        return nIsColorizerEnabled();
    }

    private native boolean nIsColorizerEnabled(); /*
        return THIS->IsColorizerEnabled();
    */

    public void setColorizerEnable(final boolean aValue) {
        nSetColorizerEnable(aValue);
    }

    private native void nSetColorizerEnable(boolean aValue); /*
        THIS->SetColorizerEnable(aValue);
    */

    public TextEditorCoordinates getCursorPosition() {
        final TextEditorCoordinates dst = new TextEditorCoordinates();
        nGetCursorPosition(dst);
        return dst;
    }

    public void getCursorPosition(final TextEditorCoordinates dst) {
        nGetCursorPosition(dst);
    }

    private native void nGetCursorPosition(TextEditorCoordinates dst); /*
        Jni::TextEditorCoordinatesCpy(env, THIS->GetCursorPosition(), dst);
    */

    public void setCursorPosition(final TextEditorCoordinates aPosition) {
        nSetCursorPosition(aPosition.mLine, aPosition.mColumn);
    }

    public void setCursorPosition(final int aPositionLine, final int aPositionColumn) {
        nSetCursorPosition(aPositionLine, aPositionColumn);
    }

    private native void nSetCursorPosition(int aPositionLine, int aPositionColumn); /*
        THIS->SetCursorPosition(TextEditor::Coordinates(aPositionLine, aPositionColumn));
    */

    public void setHandleMouseInputs(final boolean aValue) {
        nSetHandleMouseInputs(aValue);
    }

    private native void nSetHandleMouseInputs(boolean aValue); /*
        THIS->SetHandleMouseInputs(aValue);
    */

    public boolean isHandleMouseInputsEnabled() {
        return nIsHandleMouseInputsEnabled();
    }

    private native boolean nIsHandleMouseInputsEnabled(); /*
        return THIS->IsHandleMouseInputsEnabled();
    */

    public void setHandleKeyboardInputs(final boolean aValue) {
        nSetHandleKeyboardInputs(aValue);
    }

    private native void nSetHandleKeyboardInputs(boolean aValue); /*
        THIS->SetHandleKeyboardInputs(aValue);
    */

    public boolean isHandleKeyboardInputsEnabled() {
        return nIsHandleKeyboardInputsEnabled();
    }

    private native boolean nIsHandleKeyboardInputsEnabled(); /*
        return THIS->IsHandleKeyboardInputsEnabled();
    */

    public void setImGuiChildIgnored(final boolean aValue) {
        nSetImGuiChildIgnored(aValue);
    }

    private native void nSetImGuiChildIgnored(boolean aValue); /*
        THIS->SetImGuiChildIgnored(aValue);
    */

    public boolean isImGuiChildIgnored() {
        return nIsImGuiChildIgnored();
    }

    private native boolean nIsImGuiChildIgnored(); /*
        return THIS->IsImGuiChildIgnored();
    */

    public void setShowWhitespaces(final boolean aValue) {
        nSetShowWhitespaces(aValue);
    }

    private native void nSetShowWhitespaces(boolean aValue); /*
        THIS->SetShowWhitespaces(aValue);
    */

    public boolean isShowingWhitespaces() {
        return nIsShowingWhitespaces();
    }

    private native boolean nIsShowingWhitespaces(); /*
        return THIS->IsShowingWhitespaces();
    */

    public void setTabSize(final int aValue) {
        nSetTabSize(aValue);
    }

    private native void nSetTabSize(int aValue); /*
        THIS->SetTabSize(aValue);
    */

    public int getTabSize() {
        return nGetTabSize();
    }

    private native int nGetTabSize(); /*
        return THIS->GetTabSize();
    */

    public void insertText(final String aValue) {
        nInsertText(aValue);
    }

    private native void nInsertText(String aValue); /*MANUAL
        auto aValue = obj_aValue == NULL ? NULL : (char*)env->GetStringUTFChars(obj_aValue, JNI_FALSE);
        THIS->InsertText(aValue);
        if (aValue != NULL) env->ReleaseStringUTFChars(obj_aValue, aValue);
    */

    public void moveUp() {
        nMoveUp();
    }

    public void moveUp(final int aAmount) {
        nMoveUp(aAmount);
    }

    public void moveUp(final int aAmount, final boolean aSelect) {
        nMoveUp(aAmount, aSelect);
    }

    private native void nMoveUp(); /*
        THIS->MoveUp();
    */

    private native void nMoveUp(int aAmount); /*
        THIS->MoveUp(aAmount);
    */

    private native void nMoveUp(int aAmount, boolean aSelect); /*
        THIS->MoveUp(aAmount, aSelect);
    */

    public void moveDown() {
        nMoveDown();
    }

    public void moveDown(final int aAmount) {
        nMoveDown(aAmount);
    }

    public void moveDown(final int aAmount, final boolean aSelect) {
        nMoveDown(aAmount, aSelect);
    }

    private native void nMoveDown(); /*
        THIS->MoveDown();
    */

    private native void nMoveDown(int aAmount); /*
        THIS->MoveDown(aAmount);
    */

    private native void nMoveDown(int aAmount, boolean aSelect); /*
        THIS->MoveDown(aAmount, aSelect);
    */

    public void moveLeft() {
        nMoveLeft();
    }

    public void moveLeft(final int aAmount) {
        nMoveLeft(aAmount);
    }

    public void moveLeft(final int aAmount, final boolean aSelect) {
        nMoveLeft(aAmount, aSelect);
    }

    public void moveLeft(final int aAmount, final boolean aSelect, final boolean aWordMode) {
        nMoveLeft(aAmount, aSelect, aWordMode);
    }

    private native void nMoveLeft(); /*
        THIS->MoveLeft();
    */

    private native void nMoveLeft(int aAmount); /*
        THIS->MoveLeft(aAmount);
    */

    private native void nMoveLeft(int aAmount, boolean aSelect); /*
        THIS->MoveLeft(aAmount, aSelect);
    */

    private native void nMoveLeft(int aAmount, boolean aSelect, boolean aWordMode); /*
        THIS->MoveLeft(aAmount, aSelect, aWordMode);
    */

    public void moveRight() {
        nMoveRight();
    }

    public void moveRight(final int aAmount) {
        nMoveRight(aAmount);
    }

    public void moveRight(final int aAmount, final boolean aSelect) {
        nMoveRight(aAmount, aSelect);
    }

    public void moveRight(final int aAmount, final boolean aSelect, final boolean aWordMode) {
        nMoveRight(aAmount, aSelect, aWordMode);
    }

    private native void nMoveRight(); /*
        THIS->MoveRight();
    */

    private native void nMoveRight(int aAmount); /*
        THIS->MoveRight(aAmount);
    */

    private native void nMoveRight(int aAmount, boolean aSelect); /*
        THIS->MoveRight(aAmount, aSelect);
    */

    private native void nMoveRight(int aAmount, boolean aSelect, boolean aWordMode); /*
        THIS->MoveRight(aAmount, aSelect, aWordMode);
    */

    public void moveTop() {
        nMoveTop();
    }

    public void moveTop(final boolean aSelect) {
        nMoveTop(aSelect);
    }

    private native void nMoveTop(); /*
        THIS->MoveTop();
    */

    private native void nMoveTop(boolean aSelect); /*
        THIS->MoveTop(aSelect);
    */

    public void moveBottom() {
        nMoveBottom();
    }

    public void moveBottom(final boolean aSelect) {
        nMoveBottom(aSelect);
    }

    private native void nMoveBottom(); /*
        THIS->MoveBottom();
    */

    private native void nMoveBottom(boolean aSelect); /*
        THIS->MoveBottom(aSelect);
    */

    public void moveHome() {
        nMoveHome();
    }

    public void moveHome(final boolean aSelect) {
        nMoveHome(aSelect);
    }

    private native void nMoveHome(); /*
        THIS->MoveHome();
    */

    private native void nMoveHome(boolean aSelect); /*
        THIS->MoveHome(aSelect);
    */

    public void moveEnd() {
        nMoveEnd();
    }

    public void moveEnd(final boolean aSelect) {
        nMoveEnd(aSelect);
    }

    private native void nMoveEnd(); /*
        THIS->MoveEnd();
    */

    private native void nMoveEnd(boolean aSelect); /*
        THIS->MoveEnd(aSelect);
    */

    public void setSelectionStart(final TextEditorCoordinates aPosition) {
        nSetSelectionStart(aPosition.mLine, aPosition.mColumn);
    }

    public void setSelectionStart(final int aPositionLine, final int aPositionColumn) {
        nSetSelectionStart(aPositionLine, aPositionColumn);
    }

    private native void nSetSelectionStart(int aPositionLine, int aPositionColumn); /*
        THIS->SetSelectionStart(TextEditor::Coordinates(aPositionLine, aPositionColumn));
    */

    public void setSelectionEnd(final TextEditorCoordinates aPosition) {
        nSetSelectionEnd(aPosition.mLine, aPosition.mColumn);
    }

    public void setSelectionEnd(final int aPositionLine, final int aPositionColumn) {
        nSetSelectionEnd(aPositionLine, aPositionColumn);
    }

    private native void nSetSelectionEnd(int aPositionLine, int aPositionColumn); /*
        THIS->SetSelectionEnd(TextEditor::Coordinates(aPositionLine, aPositionColumn));
    */

    public void setSelection(final TextEditorCoordinates aStart, final TextEditorCoordinates aEnd) {
        nSetSelection(aStart.mLine, aStart.mColumn, aEnd.mLine, aEnd.mColumn);
    }

    public void setSelection(final int aStartLine, final int aStartColumn, final int aEndLine, final int aEndColumn) {
        nSetSelection(aStartLine, aStartColumn, aEndLine, aEndColumn);
    }

    public void setSelection(final TextEditorCoordinates aStart, final TextEditorCoordinates aEnd, final int aMode) {
        nSetSelection(aStart.mLine, aStart.mColumn, aEnd.mLine, aEnd.mColumn, aMode);
    }

    public void setSelection(final int aStartLine, final int aStartColumn, final int aEndLine, final int aEndColumn, final int aMode) {
        nSetSelection(aStartLine, aStartColumn, aEndLine, aEndColumn, aMode);
    }

    private native void nSetSelection(int aStartLine, int aStartColumn, int aEndLine, int aEndColumn); /*
        THIS->SetSelection(TextEditor::Coordinates(aStartLine, aStartColumn), TextEditor::Coordinates(aEndLine, aEndColumn));
    */

    private native void nSetSelection(int aStartLine, int aStartColumn, int aEndLine, int aEndColumn, int aMode); /*
        THIS->SetSelection(TextEditor::Coordinates(aStartLine, aStartColumn), TextEditor::Coordinates(aEndLine, aEndColumn), static_cast<TextEditor::SelectionMode>(aMode));
    */

    public void selectWordUnderCursor() {
        nSelectWordUnderCursor();
    }

    private native void nSelectWordUnderCursor(); /*
        THIS->SelectWordUnderCursor();
    */

    public void selectAll() {
        nSelectAll();
    }

    private native void nSelectAll(); /*
        THIS->SelectAll();
    */

    public boolean hasSelection() {
        return nHasSelection();
    }

    private native boolean nHasSelection(); /*
        return THIS->HasSelection();
    */

    public void copy() {
        nCopy();
    }

    private native void nCopy(); /*
        THIS->Copy();
    */

    public void cut() {
        nCut();
    }

    private native void nCut(); /*
        THIS->Cut();
    */

    public void paste() {
        nPaste();
    }

    private native void nPaste(); /*
        THIS->Paste();
    */

    public void delete() {
        nDelete();
    }

    private native void nDelete(); /*
        THIS->Delete();
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

    public void undo() {
        nUndo();
    }

    public void undo(final int aSteps) {
        nUndo(aSteps);
    }

    private native void nUndo(); /*
        THIS->Undo();
    */

    private native void nUndo(int aSteps); /*
        THIS->Undo(aSteps);
    */

    public void redo() {
        nRedo();
    }

    public void redo(final int aSteps) {
        nRedo(aSteps);
    }

    private native void nRedo(); /*
        THIS->Redo();
    */

    private native void nRedo(int aSteps); /*
        THIS->Redo(aSteps);
    */

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
