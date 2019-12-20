package imgui;

import imgui.enums.*;

import java.nio.Buffer;
import java.nio.ByteBuffer;

//import com.badlogic.gdx.jnigen.JniGenSharedLibraryLoader;

public class ImGui {
    private static final String LIB_NAME_PROP = "imgui.library.name";
    private static final String LIB_NAME_DEFAULT = "imgui-java";

    public static boolean enableLogging = true;
    public static String TAG = "ImGui";
    private static boolean IMGUIINIT = false;
    private static DrawData drawData = new DrawData(100000, 100000, 1000);
    private static ImGuiIO imguiIO = new ImGuiIO();
    private static ImGuiStyle imguiStyle = new ImGuiStyle();
    private static ImVec2 imVec2 = new ImVec2();
    private static ImVec4 imVec4 = new ImVec4();
    private static ImDrawList imDrawList = new ImDrawList(ImDrawList.TYPE_DEFAULT);
    private static ImDrawList imDrawListBackground = new ImDrawList(ImDrawList.TYPE_BACKGROUND);
    private static ImDrawList imDrawListForeground = new ImDrawList(ImDrawList.TYPE_FOREGROUND);

    protected ImGui() {
    }

    public static void init() {
        init(true);
    }

    public static void init(boolean logging) {
        if (ImGui.IMGUIINIT)
            return;
        System.loadLibrary(System.getProperty(LIB_NAME_PROP, LIB_NAME_DEFAULT));
        ImGui.IMGUIINIT = true;
        ImGui.enableLogging = logging;

        Native.init();
        Native.CreateContext();
    }

    public static void destroy() {
        Native.DestroyContext();
    }

    public static ImGuiIO GetIO() {
        return imguiIO;
    }

    public static ImGuiStyle GetStyle() {
        return imguiStyle;
    }

    public static void ShowStyleEditor() {
        Native.ShowStyleEditor();
    }

    public static void ShowDemoWindow() {
        Native.ShowDemoWindow();
    }

    public static void ShowDemoWindow(boolean open) {
        Native.ShowDemoWindow(open);
    }

    public static void ShowMetricsWindow() {
        Native.ShowMetricsWindow();
    }

    public static void ShowMetricsWindow(boolean open) {
        Native.ShowMetricsWindow(open);
    }

    public static void UpdateDisplaySize(float width, float height, float frameBufferWidth, float frameBufferHeight) {
        Native.UpdateDisplaySize(width, height, frameBufferWidth, frameBufferHeight);
    }

    public static void UpdateMousePos(float mouseX, float mouseY) {
        Native.UpdateMousePos(mouseX, mouseY);
    }

    public static void UpdateMouseState(boolean mouseDown0, boolean mouseDown1, boolean mouseDown2, boolean mouseDown3, boolean mouseDown4, boolean mouseDown5) {
        Native.UpdateMouseState(mouseDown0, mouseDown1, mouseDown2, mouseDown3, mouseDown4, mouseDown5);
    }

    public static void UpdateImGui() {
        Native.UpdateImGui(imguiIO, imguiStyle);
    }

    public static void UpdateDeltaTime(float deltaTime) {
        Native.UpdateDeltaTime(deltaTime);
    }

    public static void initKeyMap(int[] keys) {
        Native.initKeyMap(keys);
    }

    public static void UpdateKey(int key, boolean pressed, boolean released, boolean ctrlKey, boolean shiftKey, boolean altKey, boolean superKey) {
        Native.UpdateKey(key, pressed, released, ctrlKey, shiftKey, altKey, superKey);
    }

    public static void UpdateScroll(float amountX, float amountY) {
        Native.UpdateScroll(amountX, amountY);
    }

    public static void UpdateKeyTyped(int c) {
        Native.UpdateKeyTyped(c);
    }

    public static void NewFrame() {
        Native.NewFrame();
    }

    public static void EndFrame() {
        Native.EndFrame();
    }

    public static void Render() {
        Native.Render();
    }

    public static DrawData GetDrawData() {
        ByteBuffer cmdByteBuffer = drawData.cmdByteBuffer;
        ByteBuffer vByteBuffer = drawData.vByteBuffer;
        ByteBuffer iByteBuffer = drawData.iByteBuffer;
        vByteBuffer.position(0);
        iByteBuffer.position(0);
        cmdByteBuffer.position(0);
        Native.GetDrawData(drawData, iByteBuffer, vByteBuffer, cmdByteBuffer);
        return drawData;
    }

    public static void GetTexDataAsRGBA32(TexDataRGBA32 jTexData, Buffer pixelBuffer) {
        Native.GetTexDataAsRGBA32(jTexData, pixelBuffer);
    }

    public static void SetFontTexID(int id) {
        Native.SetFontTexID(id);
    }

    public static void StyleColorsDark() {
        Native.StyleColorsDark();
    }

    public static void StyleColorsClassic() {
        Native.StyleColorsClassic();
    }

    public static void StyleColorsLight() {
        Native.StyleColorsLight();
    }

    public static boolean Begin(String title) {
        return Native.Begin(title);
    }

    public static boolean Begin(String title, ImGuiBoolean p_open, ImGuiWindowFlags flags) {
        return Native.Begin(title, p_open.data, flags.getValue());
    }

    public static void End() {
        Native.End();
    }

    // Child Windows
    // - Use child windows to begin into a self-contained independent scrolling/clipping regions within a host window. Child windows can embed their own child.
    // - For each independent axis of 'size': ==0.0f: use remaining host window size / >0.0f: fixed size / <0.0f: use remaining window size minus abs(size) / Each axis can use a different mode, e.g. ImVec2(0,400).
    // - BeginChild() returns false to indicate the window is collapsed or fully clipped, so you may early out and omit submitting anything to the window.
    //   Always call a matching EndChild() for each BeginChild() call, regardless of its return value [this is due to legacy reason and is inconsistent with most other functions such as BeginMenu/EndMenu, BeginPopup/EndPopup, etc. where the EndXXX call should only be called if the corresponding BeginXXX function returned true.]


    public static boolean BeginChild(String str_id) {
        return Native.BeginChild(str_id);
    }

    public static boolean BeginChild(String str_id, float width, float height) {
        return Native.BeginChild(str_id, width, height);
    }

    public static boolean BeginChild(String str_id, float width, float height, boolean border) {
        return Native.BeginChild(str_id, width, height, border);
    }

    public static boolean BeginChild(String str_id, float width, float height, boolean border, ImGuiWindowFlags flags) {
        return Native.BeginChild(str_id, width, height, border, flags.getValue());
    }

    public static boolean BeginChild(int imGuiID) {
        return Native.BeginChild(imGuiID);
    }

    public static boolean BeginChild(int imGuiID, float width, float height, boolean border) {
        return Native.BeginChild(imGuiID, width, height, border);
    }

    public static boolean BeginChild(int imGuiID, float width, float height, boolean border, ImGuiWindowFlags flags) {
        return Native.BeginChild(imGuiID, width, height, border, flags.getValue());
    }

    public static void EndChild() {
        Native.EndChild();
    }

    // Windows Utilities
    // - "current window" = the window we are appending into while inside a Begin()/End() block. "next window" = next window we will Begin() into.

    public static boolean IsWindowAppearing() {
        return Native.IsWindowAppearing();
    }

    public static boolean IsWindowCollapsed() {
        return Native.IsWindowCollapsed();
    }

    public static boolean IsWindowFocused() {
        return Native.IsWindowFocused();
    }

    public static boolean IsWindowFocused(ImGuiFocusedFlags flags) {
        return Native.IsWindowFocused(flags.getValue());
    }

    public static boolean IsWindowHovered() {
        return Native.IsWindowHovered();
    }

    public static boolean IsWindowHovered(ImGuiHoveredFlags flags) {
        return Native.IsWindowHovered(flags.getValue());
    }

    public static ImDrawList GetWindowDrawList() {
        return imDrawList;
    }

    public static ImDrawList GetBackgroundDrawList() {
        return imDrawListBackground;
    }

    public static ImDrawList GetForegroundDrawList() {
        return imDrawListForeground;
    }

    public static ImVec2 GetWindowPos() {
        Native.GetWindowPos(imVec2);
        return imVec2;
    }

    public static float GetWindowPosX() {
        return Native.GetWindowPosX();
    }

    public static float GetWindowPosY() {
        return Native.GetWindowPosY();
    }

    public static ImVec2 GetWindowSize() {
        Native.GetWindowSize(imVec2);
        return imVec2;
    }

    public static float GetWindowWidth() {
        return Native.GetWindowWidth();
    }

    public static float GetWindowHeight() {
        return Native.GetWindowHeight();
    }

    // Prefer using SetNextXXX functions (before Begin) rather that SetXXX functions (after Begin).

    public static void SetNextWindowPos(float x, float y) {
        Native.SetNextWindowPos(x, y);
    }

    public static void SetNextWindowPos(float x, float y, ImGuiCond cond) {
        Native.SetNextWindowPos(x, y, cond.getValue());
    }

    public static void SetNextWindowPos(float x, float y, ImGuiCond cond, float pivotX, float pivotY) {
        Native.SetNextWindowPos(x, y, cond.getValue(), pivotX, pivotY);
    }

    public static void SetNextWindowSize(int width, int height) {
        Native.SetNextWindowSize(width, height);
    }

    public static void SetNextWindowSize(int width, int height, ImGuiCond cond) {
        Native.SetNextWindowSize(width, height, cond.getValue());
    }

    public static void SetNextWindowSizeConstraints(float minWidth, float minHeight, float maxWidth, float maxHeight) {
        Native.SetNextWindowSizeConstraints(minWidth, minHeight, maxWidth, maxHeight);
    }

    public static void SetNextWindowContentSize(float sizeX, float sizeY) {
        Native.SetNextWindowContentSize(sizeX, sizeY);
    }

    public static void SetNextWindowCollapsed(boolean flag) {
        Native.SetNextWindowCollapsed(flag);
    }

    public static void SetNextWindowFocus() {
        Native.SetNextWindowFocus();
    }

    public static void SetNextWindowBgAlpha(float alpha) {
        Native.SetNextWindowBgAlpha(alpha);
    }

    //TODO add more methods

    public static void SetWindowFocus() {
        Native.SetWindowFocus();
    }

    public static void SetWindowFocus(String name) {
        if (name == null) {
            Native.RemoveWindowFocus();
        } else {
            Native.SetWindowFocus(name);
        }
    }

    // Content region
    // - Those functions are bound to be redesigned soon (they are confusing, incomplete and return values in local window coordinates which increases confusion)

    public static ImVec2 GetContentRegionMax() {
        Native.GetContentRegionMax(imVec2);
        return imVec2;
    }

    public static ImVec2 GetContentRegionAvail() {
        Native.GetContentRegionAvail(imVec2);
        return imVec2;
    }

    public static float GetContentRegionAvailWidth() {
        return Native.GetContentRegionAvailWidth();
    }

    public static ImVec2 GetWindowContentRegionMin() {
        Native.GetWindowContentRegionMin(imVec2);
        return imVec2;
    }

    public static ImVec2 GetWindowContentRegionMax() {
        Native.GetWindowContentRegionMax(imVec2);
        return imVec2;
    }

    public static float GetWindowContentRegionWidth() {
        return Native.GetWindowContentRegionWidth();
    }

    // Windows Scrolling

    public static float GetScrollX() {
        return Native.GetScrollX();
    }

    public static float GetScrollY() {
        return Native.GetScrollY();
    }

    public static float GetScrollMaxX() {
        return Native.GetScrollMaxX();
    }

    public static float GetScrollMaxY() {
        return Native.GetScrollMaxY();
    }

    public static void SetScrollX(float scroll_x) {
        Native.SetScrollX(scroll_x);
    }

    public static void SetScrollY(float scroll_y) {
        Native.SetScrollY(scroll_y);
    }

    public static void SetScrollHereY() {
        Native.SetScrollHereY();
    }

    public static void SetScrollHereY(float center_y_ratio) {
        Native.SetScrollHereY(center_y_ratio);
    }

    public static void SetScrollFromPosY(float local_y) {
        Native.SetScrollFromPosY(local_y);
    }

    public static void SetScrollFromPosY(float local_y, float center_y_ratio) {
        Native.SetScrollFromPosY(local_y, center_y_ratio);
    }

    // Parameters stacks (shared)

    public static void PushStyleColor(ImGuiCol idx, int col) {
        Native.PushStyleColor(idx.getValue(), col);
    }

    public static void PushStyleColor(ImGuiCol idx, float r, float g, float b, float a) {
        Native.PushStyleColor(idx.getValue(), r, g, b, a);
    }

    public static void PopStyleColor() {
        Native.PopStyleColor();
    }

    public static void PopStyleColor(int count) {
        Native.PopStyleColor(count);
    }

    public static void PushStyleVar(ImGuiStyleVar idx, float val) {
        Native.PushStyleVar(idx.getValue(), val);
    }

    public static void PushStyleVar(ImGuiStyleVar idx, float valX, float valY) {
        Native.PushStyleVar(idx.getValue(), valX, valY);
    }

    public static void PopStyleVar() {
        Native.PopStyleVar();
    }

    public static void PopStyleVar(int count) {
        Native.PopStyleVar(count);
    }

    // Parameters stacks (current window)

    public static void PushItemWidth(float item_width) {
        Native.PushItemWidth(item_width);
    }

    public static void PopItemWidth() {
        Native.PopItemWidth();
    }

    public static void SetNextItemWidth(float item_width) {
        Native.SetNextItemWidth(item_width);
    }

    public static float CalcItemWidth() {
        return Native.CalcItemWidth();
    }

    public static void PushTextWrapPos() {
        Native.PushTextWrapPos();
    }

    public static void PushTextWrapPos(float wrap_local_pos_x) {
        Native.PushTextWrapPos(wrap_local_pos_x);
    }

    public static void PopTextWrapPos() {
        Native.PopTextWrapPos();
    }

    public static void PushAllowKeyboardFocus(boolean allow_keyboard_focus) {
        Native.PushAllowKeyboardFocus(allow_keyboard_focus);
    }

    public static void PushButtonRepeat() {
        Native.PopAllowKeyboardFocus();
    }

    public static void PushButtonRepeat(boolean repeat) {
        Native.PushButtonRepeat(repeat);
    }

    public static void PopButtonRepeat() {
        Native.PopButtonRepeat();
    }

    // Cursor / Layout
    // - By "cursor" we mean the current output position.
    // - The typical widget behavior is to output themselves at the current cursor position, then move the cursor one line down.

    public static void Separator() {
        Native.Separator();
    }

    public static void SameLine() {
        Native.SameLine();
    }

    public static void SameLine(float offsetFromStartX) {
        Native.SameLine(offsetFromStartX);
    }

    public static void SameLine(float offsetFromStartX, float spacing) {
        Native.SameLine(offsetFromStartX, spacing);
    }

    public static void NewLine() {
        Native.NewLine();
    }

    public static void Spacing() {
        Native.Spacing();
    }

    public static void Dummy(float width, float height) {
        Native.Dummy(width, height);
    }

    public static void Indent() {
        Native.Indent();
    }

    public static void Indent(float indentW) {
        Native.Indent(indentW);
    }

    public static void Unindent() {
        Native.Unindent();
    }

    public static void Unindent(float indentW) {
        Native.Unindent(indentW);
    }

    public static void BeginGroup() {
        Native.BeginGroup();
    }

    public static void EndGroup() {
        Native.EndGroup();
    }

    public static void GetCursorPos() {
        //TODO impl
    }

    public static float GetCursorPosX() {
        return Native.GetCursorPosX();
    }

    public static float GetCursorPosY() {
        return Native.GetCursorPosY();
    }

    public static void SetCursorPos(float x, float y) {
        Native.SetCursorPos(x, y);
    }

    public static void SetCursorPosX(float x) {
        Native.SetCursorPosX(x);
    }

    public static void SetCursorPosY(float y) {
        Native.SetCursorPosY(y);
    }

    public static ImVec2 GetCursorStartPos() {
        Native.GetCursorStartPos(imVec2);
        return imVec2;
    }

    public static ImVec2 GetCursorScreenPos() {
        Native.GetCursorScreenPos(imVec2);
        return imVec2;
    }

    public static void SetCursorScreenPos(float x, float y) {
        Native.SetCursorScreenPos(x, y);
    }

    public static void AlignTextToFramePadding() {
        Native.AlignTextToFramePadding();
    }

    public static float GetTextLineHeight() {
        return Native.GetTextLineHeight();
    }

    public static float GetTextLineHeightWithSpacing() {
        return Native.GetTextLineHeightWithSpacing();
    }

    public static float GetFrameHeight() {
        return Native.GetFrameHeight();
    }

    public static float GetFrameHeightWithSpacing() {
        return Native.GetFrameHeightWithSpacing();
    }

    // ID stack/scopes
    // - Read the FAQ for more details about how ID are handled in dear imgui. If you are creating widgets in a loop you most
    //   likely want to push a unique identifier (e.g. object pointer, loop index) to uniquely differentiate them.
    // - The resulting ID are hashes of the entire stack.
    // - You can also use the "Label##foobar" syntax within widget label to distinguish them from each others.
    // - In this header file we use the "label"/"name" terminology to denote a string that will be displayed and used as an ID,
    //   whereas "str_id" denote a string that is only used as an ID and not normally displayed.

    public static void PushID(String str_id) {
        Native.PushID(str_id);
    }

    public static void PushID(String str_id_begin, String str_id_end) {
        Native.PushID(str_id_begin, str_id_end);
    }

    public static void PushID(int int_id) {
        Native.PushID(int_id);
    }

    public static void PopID() {
        Native.PopID();
    }

    public static int GetID(String str_id) {
        return Native.GetID(str_id);
    }

    public static int GetID(String str_id_begin, String str_id_end) {
        return Native.GetID(str_id_begin, str_id_end);
    }

    // TODO GetID ptr_id

    // Widgets: Text

    public static void TextUnformatted(String text) {
        Native.TextUnformatted(text);
    }

    public static void TextUnformatted(String text, String text_end) {
        Native.TextUnformatted(text, text_end);
    }

    public static void Text(String text) {
        Native.Text(text);
    }

    public static void TextColored(float r, float g, float b, float a, String text) {
        Native.TextColored(r, g, b, a, text);
    }

    public static void TextDisabled(String text) {
        Native.TextDisabled(text);
    }

    public static void TextWrapped(String text) {
        Native.TextWrapped(text);
    }

    public static void LabelText(String label, String text) {
        Native.LabelText(label, text);
    }

    public static void BulletText(String text) {
        Native.BulletText(text);
    }

    // Widgets: Main
    // - Most widgets return true when the value has been changed or when pressed/selected

    /**
     * button
     */
    public static boolean Button(String label) {
        return Native.Button(label);
    }

    /**
     * button
     */
    public static boolean Button(String label, float width, float height) {
        return Native.Button(label, width, height);
    }

    /**
     * button with FramePadding=(0,0) to easily embed within text
     */
    public static boolean SmallButton(String label) {
        return Native.SmallButton(label);
    }

    /**
     * button behavior without the visuals, frequently useful to build custom behaviors using the public api (along with IsItemActive, IsItemHovered, etc.)
     */
    public static boolean InvisibleButton(String strId, float width, float height) {
        return Native.InvisibleButton(strId, width, height);
    }

    /**
     * square button with an arrow shape
     */
    public static boolean ArrowButton(String strId, ImGuiDir dir) {
        return Native.ArrowButton(strId, dir.toInt());
    }

    public static void Image(int textureID, float sizeX, float sizeY) {
        Native.Image(textureID, sizeX, sizeY);
    }

    public static void Image(int textureID, float sizeX, float sizeY, float uv0_x, float uv0_y, float uv1_x, float uv1_y) {
        Native.Image(textureID, sizeX, sizeY, uv0_x, uv0_y, uv1_x, uv1_y);
    }

    public static boolean ImageButton(int textureID, float sizeX, float sizeY) {
        return Native.ImageButton(textureID, sizeX, sizeY);
    }

    public static boolean ImageButton(int textureID, float sizeX, float sizeY, float uv0_x, float uv0_y, float uv1_x, float uv1_y, int frame_padding) {
        return Native.ImageButton(textureID, sizeX, sizeY, uv0_x, uv0_y, uv1_x, uv1_y, frame_padding);
    }

    public static boolean Checkbox(String label, ImGuiBoolean value) {
        return Native.Checkbox(label, value.data);
    }

    public static boolean CheckboxFlags(String label, ImGuiInt flags, ImGuiTabBarFlags flagsValues) {
        return Native.CheckboxFlags(label, flags.data, flagsValues.getValue());
    }

    public static boolean RadioButton(String label, boolean active) {
        return Native.RadioButton(label, active);
    }

    public static boolean RadioButton(String label, ImGuiInt value, int v_button) {
        return Native.RadioButton(label, value.data, v_button);
    }

    public static void ProgressBar(float fraction) {
        Native.ProgressBar(fraction);
    }

    public static void ProgressBar(float fraction, float size_arg_x, float size_arg_y) {
        Native.ProgressBar(fraction, size_arg_x, size_arg_y);
    }

    public static void Bullet() {
        Native.Bullet();
    }

    // Widgets: Combo Box
    // - The new BeginCombo()/EndCombo() api allows you to manage your contents and selection state however you want it, by creating e.g. Selectable() items.
    // - The old Combo() api are helpers over BeginCombo()/EndCombo() which are kept available for convenience purpose.

    public static boolean BeginCombo(String label, String preview_value) {
        return Native.BeginCombo(label, preview_value);
    }

    public static boolean BeginCombo(String label, String preview_value, ImGuiComboFlags flags) {
        return Native.BeginCombo(label, preview_value, flags.getValue());
    }

    public static void EndCombo() {
        Native.EndCombo();
    }

    public static boolean Combo(String label, ImGuiInt current_item, String[] items, int items_count) {
        return Native.Combo(label, current_item.data, items, items_count);
    }

    public static boolean Combo(String label, ImGuiInt current_item, String[] items, int items_count, int popup_max_height_in_items) {
        return Native.Combo(label, current_item.data, items, items_count, popup_max_height_in_items);
    }

    public static boolean Combo(String label, ImGuiInt current_item, String items_separated_by_zeros) {
        return Native.Combo(label, current_item.data, items_separated_by_zeros);
    }

    public static boolean Combo(String label, ImGuiInt current_item, String items_separated_by_zeros, int popup_max_height_in_items) {
        return Native.Combo(label, current_item.data, items_separated_by_zeros, popup_max_height_in_items);
    }

    // Widgets: Drags
    // - CTRL+Click on any drag box to turn them into an input box. Manually input values aren't clamped and can go off-bounds.
    // - For all the Float2/Float3/Float4/Int2/Int3/Int4 versions of every functions, note that a 'float v[X]' function argument is the same as 'float* v', the array syntax is just a way to document the number of elements that are expected to be accessible. You can pass address of your first element out of a contiguous set, e.g. &myvector.x
    // - Adjust format string to decorate the value with a prefix, a suffix, or adapt the editing and display precision e.g. "%.3f" -> 1.234; "%5.2f secs" -> 01.23 secs; "Biscuit: %.0f" -> Biscuit: 1; etc.
    // - Speed are per-pixel of mouse movement (v_speed=0.2f: mouse needs to move by 5 pixels to increase value by 1). For gamepad/keyboard navigation, minimum speed is Max(v_speed, minimum_step_at_given_precision).

    public static boolean DragFloat(String label, ImGuiFloat v) {
        return Native.DragFloat(label, v.data);
    }

    public static boolean DragFloat(String label, ImGuiFloat v, float v_speed) {
        return Native.DragFloat(label, v.data, v_speed);
    }

    public static boolean DragFloat(String label, ImGuiFloat v, float v_speed, float v_min, float v_max, String format) {
        return Native.DragFloat(label, v.data, v_speed, v_min, v_max, format);
    }

    public static boolean DragFloat(String label, ImGuiFloat v, float v_speed, float v_min, float v_max, String format, float power) {
        return Native.DragFloat(label, v.data, v_speed, v_min, v_max, format, power);
    }

    public static boolean DragFloat2(String label, float[] v) {
        return Native.DragFloat2(label, v);
    }

    public static boolean DragFloat2(String label, float[] v, float v_speed, float v_min, float v_max, String format, float power) {
        return Native.DragFloat2(label, v, v_speed, v_min, v_max, format, power);
    }

    public static boolean DragFloat3(String label, float[] v) {
        return Native.DragFloat3(label, v);
    }

    public static boolean DragFloat3(String label, float[] v, float v_speed, float v_min, float v_max, String format, float power) {
        return Native.DragFloat3(label, v, v_speed, v_min, v_max, format, power);
    }

    public static boolean DragFloat4(String label, float[] v) {
        return Native.DragFloat4(label, v);
    }

    public static boolean DragFloat4(String label, float[] v, float v_speed, float v_min, float v_max, String format, float power) {
        return Native.DragFloat4(label, v, v_speed, v_min, v_max, format, power);
    }

    public static boolean DragFloatRange2(String label, ImGuiFloat v_current_min, ImGuiFloat v_current_max) {
        return Native.DragFloatRange2(label, v_current_min.data, v_current_max.data);
    }

    public static boolean DragFloatRange2(String label, ImGuiFloat v_current_min, ImGuiFloat v_current_max, float v_speed, float v_min, float v_max, String format, String format_max, float power) {
        return Native.DragFloatRange2(label, v_current_min.data, v_current_max.data, v_speed, v_min, v_max, format, format_max, power);
    }

    public static boolean DragInt(String label, ImGuiInt v) {
        return Native.DragInt(label, v.data);
    }

    public static boolean DragInt(String label, ImGuiInt v, float v_speed) {
        return Native.DragInt(label, v.data, v_speed);
    }

    public static boolean DragInt(String label, ImGuiInt v, float v_speed, float v_min, float v_max, String format) {
        return Native.DragInt(label, v.data, v_speed, v_min, v_max, format);
    }

    public static boolean DragInt2(String label, int[] v) {
        return Native.DragInt2(label, v);
    }

    public static boolean DragInt2(String label, int[] v, float v_speed, float v_min, float v_max, String format) {
        return Native.DragInt2(label, v, v_speed, v_min, v_max, format);
    }

    public static boolean DragInt3(String label, int[] v) {
        return Native.DragInt3(label, v);
    }

    public static boolean DragInt3(String label, int[] v, float v_speed, float v_min, float v_max, String format) {
        return Native.DragInt3(label, v, v_speed, v_min, v_max, format);
    }

    public static boolean DragInt4(String label, int[] v) {
        return Native.DragInt4(label, v);
    }

    public static boolean DragInt4(String label, int[] v, float v_speed, float v_min, float v_max, String format) {
        return Native.DragInt4(label, v, v_speed, v_min, v_max, format);
    }

    public static boolean DragIntRange2(String label, ImGuiInt v_current_min, ImGuiInt v_current_max) {
        return Native.DragIntRange2(label, v_current_min.data, v_current_max.data);
    }

    public static boolean DragIntRange2(String label, ImGuiInt v_current_min, ImGuiInt v_current_max, float v_speed, float v_min, float v_max, String format, String format_max) {
        return Native.DragIntRange2(label, v_current_min.data, v_current_max.data, v_speed, v_min, v_max, format, format_max);
    }

    //TODO impl
//    public static boolean DragScalar(String label, ImGuiDataType_ data_type, ImGuiInt v, ImGuiInt v_current_max, float v_speed, float v_min, float v_max, String format, String format_max) {
//        return ImGuiNative.DragScalar(label, data_type.getValue(), v.data, v_current_max.data, v_speed, v_min, v_max, format, format_max);
//    }


    // Widgets: Sliders
    // - CTRL+Click on any slider to turn them into an input box. Manually input values aren't clamped and can go off-bounds.
    // - Adjust format string to decorate the value with a prefix, a suffix, or adapt the editing and display precision e.g. "%.3f" -> 1.234; "%5.2f secs" -> 01.23 secs; "Biscuit: %.0f" -> Biscuit: 1; etc.

    public static boolean SliderFloat(String label, ImGuiFloat v, float v_min, float v_max) {
        return Native.SliderFloat(label, v.data, v_min, v_max);
    }

    public static boolean SliderFloat(String label, ImGuiFloat v, float v_min, float v_max, String format) {
        return Native.SliderFloat(label, v.data, v_min, v_max, format);
    }

    public static boolean SliderFloat(String label, ImGuiFloat v, float v_min, float v_max, String format, float power) {
        return Native.SliderFloat(label, v.data, v_min, v_max, format, power);
    }

    public static boolean SliderFloat2(String label, float[] v, float v_min, float v_max) {
        return Native.SliderFloat2(label, v, v_min, v_max);
    }

    public static boolean SliderFloat2(String label, float[] v, float v_min, float v_max, String format, float power) {
        return Native.SliderFloat2(label, v, v_min, v_max, format, power);
    }

    public static boolean SliderFloat3(String label, float[] v, float v_min, float v_max) {
        return Native.SliderFloat3(label, v, v_min, v_max);
    }

    public static boolean SliderFloat3(String label, float[] v, float v_min, float v_max, String format, float power) {
        return Native.SliderFloat3(label, v, v_min, v_max, format, power);
    }

    public static boolean SliderFloat4(String label, float[] v, float v_min, float v_max) {
        return Native.SliderFloat4(label, v, v_min, v_max);
    }

    public static boolean SliderFloat4(String label, float[] v, float v_min, float v_max, String format, float power) {
        return Native.SliderFloat4(label, v, v_min, v_max, format, power);
    }

    public static boolean SliderAngle(String label, ImGuiFloat v) {
        return Native.SliderAngle(label, v.data);
    }

    public static boolean SliderAngle(String label, ImGuiFloat v, float v_degrees_min, float v_degrees_max, String format) {
        return Native.SliderAngle(label, v.data, v_degrees_min, v_degrees_max, format);
    }

    public static boolean SliderInt(String label, ImGuiInt v, int v_min, int v_max) {
        return Native.SliderInt(label, v.data, v_min, v_max);
    }

    public static boolean SliderInt(String label, ImGuiInt v, int v_min, int v_max, String format) {
        return Native.SliderInt(label, v.data, v_min, v_max, format);
    }

    public static boolean SliderInt2(String label, int[] v, int v_min, int v_max) {
        return Native.SliderInt2(label, v, v_min, v_max);
    }

    public static boolean SliderInt2(String label, int[] v, int v_min, int v_max, String format) {
        return Native.SliderInt2(label, v, v_min, v_max, format);
    }

    public static boolean SliderInt3(String label, int[] v, int v_min, int v_max) {
        return Native.SliderInt3(label, v, v_min, v_max);
    }

    public static boolean SliderInt3(String label, int[] v, int v_min, int v_max, String format) {
        return Native.SliderInt3(label, v, v_min, v_max, format);
    }

    public static boolean SliderInt4(String label, int[] v, int v_min, int v_max) {
        return Native.SliderInt4(label, v, v_min, v_max);
    }

    public static boolean SliderInt4(String label, int[] v, int v_min, int v_max, String format) {
        return Native.SliderInt4(label, v, v_min, v_max, format);
    }

    public static boolean SliderScalar(String label, ImGuiDataType data_type, ImGuiInt v, int v_min, int v_max) {
        return Native.SliderScalar(label, data_type.getValue(), v.data, v_min, v_max);
    }

    public static boolean SliderScalar(String label, ImGuiDataType data_type, ImGuiInt v, int v_min, int v_max, String format, float power) {
        return Native.SliderScalar(label, data_type.getValue(), v.data, v_min, v_max, format, power);
    }

    public static boolean SliderScalar(String label, ImGuiDataType data_type, ImGuiFloat v, float v_min, float v_max) {
        return Native.SliderScalar(label, data_type.getValue(), v.data, v_min, v_max);
    }

    public static boolean SliderScalar(String label, ImGuiDataType data_type, ImGuiFloat v, float v_min, float v_max, String format, float power) {
        return Native.SliderScalar(label, data_type.getValue(), v.data, v_min, v_max, format, power);
    }

    public static boolean VSliderFloat(String label, float sizeX, float sizeY, ImGuiFloat v, float v_min, float v_max) {
        return Native.VSliderFloat(label, sizeX, sizeY, v.data, v_min, v_max);
    }

    public static boolean VSliderFloat(String label, float sizeX, float sizeY, ImGuiFloat v, float v_min, float v_max, String format, float power) {
        return Native.VSliderFloat(label, sizeX, sizeY, v.data, v_min, v_max, format, power);
    }

    public static boolean VSliderInt(String label, float sizeX, float sizeY, ImGuiInt v, int v_min, int v_max) {
        return Native.VSliderInt(label, sizeX, sizeY, v.data, v_min, v_max);
    }

    public static boolean VSliderInt(String label, float sizeX, float sizeY, ImGuiInt v, int v_min, int v_max, String format) {
        return Native.VSliderInt(label, sizeX, sizeY, v.data, v_min, v_max, format);
    }

    public static boolean VSliderScalar(String label, float sizeX, float sizeY, ImGuiDataType data_type, ImGuiFloat v, float v_min, float v_max) {
        return Native.VSliderScalar(label, sizeX, sizeY, data_type.getValue(), v.data, v_min, v_max);
    }

    public static boolean VSliderScalar(String label, float sizeX, float sizeY, ImGuiDataType data_type, ImGuiFloat v, float v_min, float v_max, String format, float power) {
        return Native.VSliderScalar(label, sizeX, sizeY, data_type.getValue(), v.data, v_min, v_max, format, power);
    }

    public static boolean VSliderScalar(String label, float sizeX, float sizeY, ImGuiDataType data_type, ImGuiInt v, int v_min, int v_max) {
        return Native.VSliderScalar(label, sizeX, sizeY, data_type.getValue(), v.data, v_min, v_max);
    }

    public static boolean VSliderScalar(String label, float sizeX, float sizeY, ImGuiDataType data_type, ImGuiInt v, int v_min, int v_max, String format, float power) {
        return Native.VSliderScalar(label, sizeX, sizeY, data_type.getValue(), v.data, v_min, v_max, format, power);
    }

    // Widgets: Input with Keyboard
    // - If you want to use InputText() with a dynamic string type such as std::string or your own, see misc/cpp/imgui_stdlib.h
    // - Most of the ImGuiInputTextFlags flags are only useful for InputText() and not for InputFloatX, InputIntX, InputDouble etc.

    public static boolean InputText(String label, ImGuiString text) {
        return Native.InputText(label, text.data, text.data.length, 0, text.inputData, text.inputData.maxChar, text.inputData.allowedChar, text.inputData.allowedChar.length());
    }

    public static boolean InputText(String label, ImGuiString text, ImGuiInputTextFlags flags) {
        return Native.InputText(label, text.data, text.data.length, flags.getValue(), text.inputData, text.inputData.maxChar, text.inputData.allowedChar, text.inputData.allowedChar.length());
    }

    public static boolean InputFloat(String label, ImGuiFloat v) {
        return Native.InputFloat(label, v.data);
    }

    public static boolean InputFloat(String label, ImGuiFloat v, float step, float step_fast, String format) {
        return Native.InputFloat(label, v.data, step, step_fast, format);
    }

    public static boolean InputFloat(String label, ImGuiFloat v, float step, float step_fast, String format, ImGuiInputTextFlags flags) {
        return Native.InputFloat(label, v.data, step, step_fast, format, flags.getValue());
    }

    public static boolean InputInt(String label, ImGuiInt v) {
        return Native.InputInt(label, v.data);
    }

    public static boolean InputInt(String label, ImGuiInt v, float step, float step_fast) {
        return Native.InputInt(label, v.data, step, step_fast);
    }

    public static boolean InputInt(String label, ImGuiInt v, float step, float step_fast, ImGuiInputTextFlags flags) {
        return Native.InputInt(label, v.data, step, step_fast, flags.getValue());
    }

    public static boolean InputDouble(String label, ImGuiDouble v) {
        return Native.InputDouble(label, v.data);
    }

    public static boolean InputDouble(String label, ImGuiDouble v, float step, float step_fast, String format) {
        return Native.InputDouble(label, v.data, step, step_fast, format);
    }

    public static boolean InputDouble(String label, ImGuiDouble v, float step, float step_fast, String format, ImGuiInputTextFlags flags) {
        return Native.InputDouble(label, v.data, step, step_fast, format, flags.getValue());
    }

    // Widgets: Trees
    // - TreeNode functions return true when the node is open, in which case you need to also call TreePop() when you are finished displaying the tree node contents.

    public static boolean TreeNode(String label) {
        return Native.TreeNode(label);
    }

    public static boolean TreeNode(String str_id, String label) {
        return Native.TreeNode(str_id, label);
    }

    public static boolean TreeNode(int ptr_id, String label) {
        return Native.TreeNode(ptr_id, label);
    }

    public static boolean TreeNodeEx(String label) {
        return Native.TreeNodeEx(label);
    }

    public static boolean TreeNodeEx(String label, ImGuiTreeNodeFlags flags) {
        return Native.TreeNodeEx(label, flags.getValue());
    }

    public static boolean TreeNodeEx(String str_id, ImGuiTreeNodeFlags flags, String label) {
        return Native.TreeNodeEx(str_id, flags.getValue(), label);
    }

    public static boolean TreeNodeEx(int ptr_id, ImGuiTreeNodeFlags flags, String label) {
        return Native.TreeNodeEx(ptr_id, flags.getValue(), label);
    }

    public static void TreePush() {
        Native.TreePush();
    }

    public static void TreePush(String str_id) {
        Native.TreePush(str_id);
    }

    public static void TreePush(int ptr_id) {
        Native.TreePush(ptr_id);
    }

    public static void TreePop() {
        Native.TreePop();
    }

    public static void TreeAdvanceToLabelPos() {
        Native.TreeAdvanceToLabelPos();
    }

    public static float GetTreeNodeToLabelSpacing() {
        return Native.GetTreeNodeToLabelSpacing();
    }

    public static void SetNextTreeNodeOpen(boolean is_open) {
        Native.SetNextTreeNodeOpen(is_open);
    }

    public static void SetNextTreeNodeOpen(boolean is_open, ImGuiCond cond) {
        Native.SetNextTreeNodeOpen(is_open, cond.getValue());
    }

    public static boolean CollapsingHeader(String label) {
        return Native.CollapsingHeader(label);
    }

    public static boolean CollapsingHeader(String label, ImGuiTreeNodeFlags flags) {
        return Native.CollapsingHeader(label, flags.getValue());
    }

    public static boolean CollapsingHeader(String label, ImGuiBoolean p_open) {
        return Native.CollapsingHeader(label, p_open.data);
    }

    public static boolean CollapsingHeader(String label, ImGuiBoolean p_open, ImGuiTreeNodeFlags flags) {
        return Native.CollapsingHeader(label, p_open.data, flags.getValue());
    }


    // Widgets: Selectables
    // - A selectable highlights when hovered, and can display another color when selected.
    // - Neighbors selectable extend their highlight bounds in order to leave no gap between them.

    public static boolean Selectable(String label) {
        return Native.Selectable(label);
    }

    public static boolean Selectable(String label, boolean selected) {
        return Native.Selectable(label, selected);
    }

    public static boolean Selectable(String label, boolean selected, int flags, float sizeX, float sizeY) {
        return Native.Selectable(label, selected, flags, sizeX, sizeY);
    }

    public static boolean Selectable(String label, ImGuiBoolean selected) {
        return Native.Selectable(label, selected.data);
    }

    public static boolean Selectable(String label, ImGuiBoolean selected, int flags, float sizeX, float sizeY) {
        return Native.Selectable(label, selected.data, flags, sizeX, sizeY);
    }

    // Widgets: List Boxes
    // - FIXME: To be consistent with all the newer API, ListBoxHeader/ListBoxFooter should in reality be called BeginListBox/EndListBox. Will rename them.

    public static void ListBox(String label, ImGuiInt current_item, String[] items, int items_count) {
        Native.ListBox(label, current_item.data, items, items_count);
    }

    public static void ListBoxHeader(String label) {
        Native.ListBoxHeader(label);
    }

    public static void ListBoxHeader(String label, float sizeX, float sizeY) {
        Native.ListBoxHeader(label, sizeX, sizeY);
    }

    public static void ListBoxHeader(String label, int items_count) {
        Native.ListBoxHeader(label, items_count);
    }

    public static void ListBoxHeader(String label, int items_count, int height_in_items) {
        Native.ListBoxHeader(label, items_count, height_in_items);
    }

    // Widgets: Menus

    public static boolean BeginMainMenuBar() {
        return Native.BeginMainMenuBar();
    }

    public static void EndMainMenuBar() {
        Native.EndMainMenuBar();
    }

    public static boolean BeginMenuBar() {
        return Native.BeginMenuBar();
    }

    public static void EndMenuBar() {
        Native.EndMenuBar();
    }

    public static boolean BeginMenu(String label) {
        return Native.BeginMenu(label);
    }

    public static boolean BeginMenu(String label, boolean enabled) {
        return Native.BeginMenu(label, enabled);
    }

    public static void EndMenu() {
        Native.EndMenu();
    }

    public static boolean MenuItem(String label) {
        return Native.MenuItem(label);
    }

    public static boolean MenuItem(String label, String shortcut, boolean selected, boolean enabled) {
        return Native.MenuItem(label, shortcut, selected, enabled);
    }

    public static boolean MenuItem(String label, String shortcut, ImGuiBoolean p_selected) {
        return Native.MenuItem(label, shortcut, p_selected.data);
    }

    public static boolean MenuItem(String label, String shortcut, ImGuiBoolean p_selected, boolean enabled) {
        return Native.MenuItem(label, shortcut, p_selected.data, enabled);
    }

    // Tooltips

    public static void BeginTooltip() {
        Native.BeginTooltip();
    }

    public static void EndTooltip() {
        Native.EndTooltip();
    }

    public static void SetTooltip(String text) {
        Native.SetTooltip(text);
    }

    // Popups, Modals
    // The properties of popups windows are:
    // - They block normal mouse hovering detection outside them. (*)
    // - Unless modal, they can be closed by clicking anywhere outside them, or by pressing ESCAPE.
    // - Their visibility state (~bool) is held internally by imgui instead of being held by the programmer as we are used to with regular Begin() calls.
    //   User can manipulate the visibility state by calling OpenPopup().
    // (*) One can use IsItemHovered(ImGuiHoveredFlags_AllowWhenBlockedByPopup) to bypass it and detect hovering even when normally blocked by a popup.
    // Those three properties are connected. The library needs to hold their visibility state because it can close popups at any time.

    public static void OpenPopup(String str_id) {
        Native.OpenPopup(str_id);
    }

    public static boolean BeginPopup(String str_id) {
        return Native.BeginPopup(str_id);
    }

    public static boolean BeginPopup(String str_id, ImGuiWindowFlags flags) {
        return Native.BeginPopup(str_id, flags.getValue());
    }

    public static boolean BeginPopupContextItem() {
        return Native.BeginPopupContextItem();
    }

    public static boolean BeginPopupContextItem(String str_id, int mouse_button) {
        return Native.BeginPopupContextItem(str_id, mouse_button);
    }

    public static boolean BeginPopupContextWindow() {
        return Native.BeginPopupContextWindow();
    }

    public static boolean BeginPopupContextWindow(String str_id, int mouse_button, boolean also_over_items) {
        return Native.BeginPopupContextWindow(str_id, mouse_button, also_over_items);
    }

    public static boolean BeginPopupContextVoid() {
        return Native.BeginPopupContextVoid();
    }

    public static boolean BeginPopupContextVoid(String str_id, int mouse_button) {
        return Native.BeginPopupContextVoid(str_id, mouse_button);
    }

    public static boolean BeginPopupModal(String name) {
        return Native.BeginPopupModal(name);
    }

    public static boolean BeginPopupModal(String name, ImGuiBoolean p_open, ImGuiWindowFlags flags) {
        return Native.BeginPopupModal(name, p_open.data, flags.getValue());
    }

    public static void EndPopup() {
        Native.EndPopup();
    }

    public static boolean OpenPopupOnItemClick() {
        return Native.OpenPopupOnItemClick();
    }

    public static boolean OpenPopupOnItemClick(String str_id, int mouse_button) {
        return Native.OpenPopupOnItemClick(str_id, mouse_button);
    }

    public static boolean IsPopupOpen(String str_id) {
        return Native.IsPopupOpen(str_id);
    }

    public static void CloseCurrentPopup() {
        Native.CloseCurrentPopup();
    }

    // Columns
    // - You can also use SameLine(pos_x) to mimic simplified columns.
    // - The columns API is work-in-progress and rather lacking (columns are arguably the worst part of dear imgui at the moment!)

    public static void Columns() {
        Native.Columns();
    }

    public static void Columns(int count) {
        Native.Columns(count);
    }

    public static void Columns(int count, String id) {
        Native.Columns(count, id);
    }

    public static void Columns(int count, String id, boolean border) {
        Native.Columns(count, id, border);
    }

    public static void NextColumn() {
        Native.NextColumn();
    }

    public static int GetColumnIndex() {
        return Native.GetColumnIndex();
    }

    public static float GetColumnWidth() {
        return Native.GetColumnWidth();
    }

    public static float GetColumnWidth(int column_index) {
        return Native.GetColumnWidth(column_index);
    }

    public static void SetColumnWidth(int column_index, float width) {
        Native.SetColumnWidth(column_index, width);
    }

    public static float GetColumnOffset() {
        return Native.GetColumnOffset();
    }

    public static float GetColumnOffset(int column_index) {
        return Native.GetColumnOffset(column_index);
    }

    public static void SetColumnOffset(int column_index, float offset_x) {
        Native.SetColumnOffset(column_index, offset_x);
    }

    public static int GetColumnsCount() {
        return Native.GetColumnsCount();
    }

    // Tab Bars, Tabs
    // [BETA API] API may evolve!

    public static boolean BeginTabBar(String str_id) {
        return Native.BeginTabBar(str_id);
    }

    public static boolean BeginTabBar(String str_id, ImGuiTabBarFlags flags) {
        return Native.BeginTabBar(str_id, flags.getValue());
    }

    public static void EndTabBar() {
        Native.EndTabBar();
    }

    public static boolean BeginTabItem(String label) {
        return Native.BeginTabItem(label);
    }

    public static boolean BeginTabItem(String label, ImGuiBoolean p_open, ImGuiTabItemFlags flags) {
        return Native.BeginTabItem(label, p_open.data, flags.getValue());
    }

    public static void EndTabItem() {
        Native.EndTabItem();
    }

    public static void SetTabItemClosed(String tab_or_docked_window_label) {
        Native.SetTabItemClosed(tab_or_docked_window_label);
    }

    // Docking
    // [BETA API] Enable with io.ConfigFlags |= ImGuiConfigFlags_DockingEnable.
    // Note: you DO NOT need to call DockSpace() to use most Docking facilities!
    // To dock windows: if io.ConfigDockingWithShift == false: drag window from their title bar.
    // To dock windows: if io.ConfigDockingWithShift == true: hold SHIFT anywhere while moving windows.
    // Use DockSpace() to create an explicit dock node _within_ an existing window. See Docking demo for details.

    // TODO: Available after docking comes from BETA
//    public static void DockSpace(int id) {
//        Native.DockSpace(id);
//    }

    // TODO: Available after docking comes from BETA
//    public static void DockSpace(int id, float sizeX, float sizeY) {
//        Native.DockSpace(id, sizeX, sizeY);
//    }

    // TODO: Available after docking comes from BETA
//    public static void DockSpace(int id, float sizeX, float sizeY, ImGuiDockNodeFlags flags) {
//        Native.DockSpace(id, sizeX, sizeY, flags.getValue());
//    }

    // Focus, Activation
    // - Prefer using "SetItemDefaultFocus()" over "if (IsWindowAppearing()) SetScrollHereY()" when applicable to signify "this is the default item"

    public static void SetItemDefaultFocus() {
        Native.SetItemDefaultFocus();
    }

    public static void SetKeyboardFocusHere() {
        Native.SetKeyboardFocusHere();
    }

    public static void SetKeyboardFocusHere(int offset) {
        Native.SetKeyboardFocusHere(offset);
    }

    // Item/Widgets Utilities
    // - Most of the functions are referring to the last/previous item we submitted.
    // - See Demo Window under "Widgets->Querying Status" for an interactive visualization of most of those functions.

    public static boolean IsItemHovered() {
        return Native.IsItemHovered();
    }

    public static boolean IsItemHovered(ImGuiHoveredFlags flags) {
        return Native.IsItemHovered(flags.getValue());
    }

    public static boolean IsItemActive() {
        return Native.IsItemActive();
    }

    public static boolean IsItemFocused() {
        return Native.IsItemFocused();
    }

    public static boolean IsItemClicked() {
        return Native.IsItemClicked();
    }

    public static boolean IsItemClicked(int mouse_button) {
        return Native.IsItemClicked(mouse_button);
    }

    public static boolean IsItemVisible() {
        return Native.IsItemVisible();
    }

    public static boolean IsItemEdited() {
        return Native.IsItemEdited();
    }

    public static boolean IsItemActivated() {
        return Native.IsItemActivated();
    }

    public static boolean IsItemDeactivated() {
        return Native.IsItemDeactivated();
    }

    public static boolean IsItemDeactivatedAfterEdit() {
        return Native.IsItemDeactivatedAfterEdit();
    }

    public static boolean IsAnyItemHovered() {
        return Native.IsAnyItemHovered();
    }

    public static boolean IsAnyItemActive() {
        return Native.IsAnyItemActive();
    }

    public static boolean IsAnyItemFocused() {
        return Native.IsAnyItemFocused();
    }

    public static ImVec2 GetItemRectMin() {
        Native.GetItemRectMin(imVec2);
        return imVec2;
    }

    public static ImVec2 GetItemRectMax() {
        Native.GetItemRectMax(imVec2);
        return imVec2;
    }

    public static ImVec2 GetItemRectSize() {
        Native.GetItemRectSize(imVec2);
        return imVec2;
    }

    public static void SetItemAllowOverlap() {
        Native.SetItemAllowOverlap();
    }

    // Miscellaneous Utilities

    public static boolean BeginChildFrame(int id, float width, float height) {
        return Native.BeginChildFrame(id, width, height);
    }

    public static boolean BeginChildFrame(int id, float width, float height, ImGuiWindowFlags flags) {
        return Native.BeginChildFrame(id, width, height, flags.getValue());
    }

    public static void EndChildFrame() {
        Native.EndChildFrame();
    }

    // Inputs Utilities

    public static boolean IsMouseDown(int button) {
        return Native.IsMouseDown(button);
    }

    public static boolean IsMouseClicked(int button) {
        return Native.IsMouseClicked(button);
    }

    public static boolean IsMouseClicked(int button, boolean repeat) {
        return Native.IsMouseClicked(button, repeat);
    }

    public static boolean IsMouseReleased(int button) {
        return Native.IsMouseReleased(button);
    }

    public static boolean IsMouseDragging() {
        return Native.IsMouseDragging();
    }

    public static boolean IsMouseDragging(int button) {
        return Native.IsMouseDragging(button);
    }

    public static boolean IsMouseDragging(int button, float lock_threshold) {
        return Native.IsMouseDragging(button, lock_threshold);
    }

    public static boolean IsMouseHoveringRect(float minX, float minY, float maxX, float maxY) {
        return Native.IsMouseHoveringRect(minX, minY, maxX, maxY);
    }

    public static boolean IsMouseHoveringRect(float minX, float minY, float maxX, float maxY, boolean clip) {
        return Native.IsMouseHoveringRect(minX, minY, maxX, maxY, clip);
    }

    // Internal methods

    public static void PushMultiItemsWidths(int components, float w_full) {
        NativeInternal.PushMultiItemsWidths(components, w_full);
    }

    public static void ItemAdd(float x1, float y1, float x2, float y2, String id) {
        NativeInternal.ItemAdd(x1, y1, x2, y2, id);
    }

    public static void ItemSize(float x1, float y1, float x2, float y2, float text_offset_y) {
        NativeInternal.ItemSize(x1, y1, x2, y2, text_offset_y);
    }
}
