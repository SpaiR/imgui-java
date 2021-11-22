package io.github.spair.imgui.extension.texteditor;

import io.github.spair.imgui.binding.ImGuiStructDestroyable;

import java.util.Map;

public final class TextEditor extends ImGuiStructDestroyable {
    /*JNI
        #include "_texteditor.h"

        #define TEXT_EDITOR ((TextEditor*)STRUCT_PTR)
     */

    public TextEditor() {
    }

    public TextEditor(final long ptr) {
        super(ptr);
    }

    @Override
    protected long create() {
        return nCreate();
    }

    private native long nCreate(); /*
        return (intptr_t)(new TextEditor());
    */

    public void setLanguageDefinition(final TextEditorLanguageDefinition definition) {
        nSetLanguageDefinition(definition.ptr);
    }

    public native void nSetLanguageDefinition(long ptr); /*
        TEXT_EDITOR->SetLanguageDefinition(*((TextEditor::LanguageDefinition*)ptr));
    */

    public native int[] getPalette(); /*
        const auto& palette = TEXT_EDITOR->GetPalette();

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

        TEXT_EDITOR->SetPalette(arr);
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
        }

        TEXT_EDITOR->SetErrorMarkers(markers);
    */

    public void setBreakpoints(final int[] breakpoints) {
        nSetBreakpoints(breakpoints, breakpoints.length);
    }

    private native void nSetBreakpoints(int[] breakpoints, int length); /*
        std::unordered_set<int> set;

        for (int i = 0; i < length; i++) {
            set.emplace(breakpoints[i]);
        }

        TEXT_EDITOR->SetBreakpoints(set);
    */

    public native void render(String title); /*
        TEXT_EDITOR->Render(title);
    */

    public native void setText(String text); /*
        TEXT_EDITOR->SetText(text);
    */

    public native String getText(); /*
        return env->NewStringUTF(TEXT_EDITOR->GetText().c_str());
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
        }

        TEXT_EDITOR->SetTextLines(vec);
    */

    public native String[] getTextLines(); /*
        const auto lines = TEXT_EDITOR->GetTextLines();

        jobjectArray arr = (jobjectArray)env->NewObjectArray(lines.size(),
            env->FindClass("java/lang/String"),
            env->NewStringUTF(""));

        for (int i = 0; i < lines.size(); i++) {
            const auto& str = lines[i];

            env->SetObjectArrayElement(arr, i, env->NewStringUTF(str.c_str()));
        }

        return arr;
    */

    public native String getSelectedText(); /*
       return env->NewStringUTF(TEXT_EDITOR->GetSelectedText().c_str());
    */

    public native String getCurrentLineText(); /*
       return env->NewStringUTF(TEXT_EDITOR->GetCurrentLineText().c_str());
    */

    public native int getTotalLines(); /*
        return TEXT_EDITOR->GetTotalLines();
    */

    public native boolean isOverwrite(); /*
        return TEXT_EDITOR->IsOverwrite();
    */

    public native void setReadOnly(boolean value); /*
        TEXT_EDITOR->SetReadOnly(value);
    */

    public native boolean isReadOnly(); /*
        return TEXT_EDITOR->IsReadOnly();
    */

    public native boolean isTextChanged(); /*
        return TEXT_EDITOR->IsTextChanged();
    */

    public native boolean isCursorPositionChanged(); /*
        return TEXT_EDITOR->IsCursorPositionChanged();
    */

    public native boolean isColorizerEnabled(); /*
        return TEXT_EDITOR->IsColorizerEnabled();
    */

    public native void setColorizerEnable(boolean value); /*
        TEXT_EDITOR->SetColorizerEnable(value);
    */

    public native int getCursorPositionLine(); /*
        return TEXT_EDITOR->GetCursorPosition().mLine;
    */

    public native int getCursorPositionColumn(); /*
        return TEXT_EDITOR->GetCursorPosition().mColumn;
    */

    public native void setCursorPosition(int line, int column); /*
        TEXT_EDITOR->SetCursorPosition({ line, column });
    */

    public native void setHandleMouseInputs(boolean value); /*
        TEXT_EDITOR->SetHandleMouseInputs(value);
    */

    public native boolean isHandleMouseInputsEnabled(); /*
        return TEXT_EDITOR->IsHandleMouseInputsEnabled();
    */

    public native void setHandleKeyboardInputs(boolean value); /*
        TEXT_EDITOR->SetHandleKeyboardInputs(value);
    */

    public native boolean isHandleKeyboardInputsEnabled(); /*
        return TEXT_EDITOR->IsHandleKeyboardInputsEnabled();
    */

    public native void setImGuiChildIgnored(boolean value); /*
        TEXT_EDITOR->SetImGuiChildIgnored(value);
    */

    public native boolean isImGuiChildIgnored(); /*
        return TEXT_EDITOR->IsImGuiChildIgnored();
    */

    public native void setShowWhitespaces(boolean value); /*
        TEXT_EDITOR->SetShowWhitespaces(value);
    */

    public native boolean isShowingWhitespaces(); /*
        return TEXT_EDITOR->IsShowingWhitespaces();
    */

    public native void setTabSize(int value); /*
        TEXT_EDITOR->SetTabSize(value);
    */

    public native int getTabSize(); /*
        return TEXT_EDITOR->GetTabSize();
    */

    public native void insertText(String value); /*
        TEXT_EDITOR->InsertText(value);
    */

    public native void moveUp(int amount, boolean select); /*
        TEXT_EDITOR->MoveUp(amount, select);
    */

    public native void moveDown(int amount, boolean select); /*
        TEXT_EDITOR->MoveDown(amount, select);
    */

    public native void moveLeft(int amount, boolean select, boolean wordMode); /*
        TEXT_EDITOR->MoveLeft(amount, select, wordMode);
    */

    public native void moveRight(int amount, boolean select, boolean wordMode); /*
        TEXT_EDITOR->MoveRight(amount, select, wordMode);
    */

    public native void moveTop(boolean select); /*
        TEXT_EDITOR->MoveTop(select);
    */

    public native void moveBottom(boolean select); /*
        TEXT_EDITOR->MoveBottom(select);
    */

    public native void moveHome(boolean select); /*
        TEXT_EDITOR->MoveHome(select);
    */

    public native void moveEnd(boolean select); /*
        TEXT_EDITOR->MoveEnd(select);
    */

    public native void setSelectionStart(int line, int column); /*
        TEXT_EDITOR->SetSelectionStart({ line, column });
    */

    public native void setSelectionEnd(int line, int column); /*
        TEXT_EDITOR->SetSelectionEnd({ line, column });
    */

    public native void setSelection(int lineStart, int columnStart, int lineEnd, int columnEnd, int selectionMode); /*
        TEXT_EDITOR->SetSelection({ lineStart, columnStart }, { lineEnd, columnEnd },
            static_cast<TextEditor::SelectionMode>(selectionMode));
    */

    public native void selectWordUnderCursor(); /*
        TEXT_EDITOR->SelectWordUnderCursor();
    */

    public native void selectAll(); /*
        TEXT_EDITOR->SelectAll();
    */

    public native boolean hasSelection(); /*
        return TEXT_EDITOR->HasSelection();
    */

    public native void copy(); /*
        TEXT_EDITOR->Copy();
    */

    public native void cut(); /*
        TEXT_EDITOR->Cut();
    */

    public native void paste(); /*
        TEXT_EDITOR->Paste();
    */

    public native void delete(); /*
        TEXT_EDITOR->Delete();
    */

    public native boolean canUndo(); /*
        return TEXT_EDITOR->CanUndo();
    */

    public native boolean canRedo(); /*
        return TEXT_EDITOR->CanRedo();
    */

    public native void undo(int steps); /*
        TEXT_EDITOR->Undo(steps);
    */

    public native void redo(int steps); /*
        TEXT_EDITOR->Redo(steps);
    */

    public native int[] getDarkPalette(); /*
        const auto& palette = TEXT_EDITOR->GetDarkPalette();

        jintArray res = env->NewIntArray(palette.size());

        jint arr[palette.size()];
        for (int i = 0; i < palette.size(); i++) {
            arr[i] = palette[i];
        }

        env->SetIntArrayRegion(res, 0, palette.size(), arr);

        return res;
    */

    public native int[] getLightPalette(); /*
        const auto& palette = TEXT_EDITOR->GetLightPalette();

        jintArray res = env->NewIntArray(palette.size());

        jint arr[palette.size()];
        for (int i = 0; i < palette.size(); i++) {
            arr[i] = palette[i];
        }

        env->SetIntArrayRegion(res, 0, palette.size(), arr);

        return res;
    */

    public native int[] getRetroBluePalette(); /*
        const auto& palette = TEXT_EDITOR->GetRetroBluePalette();

        jintArray res = env->NewIntArray(palette.size());

        jint arr[palette.size()];
        for (int i = 0; i < palette.size(); i++) {
            arr[i] = palette[i];
        }

        env->SetIntArrayRegion(res, 0, palette.size(), arr);

        return res;
    */
}
