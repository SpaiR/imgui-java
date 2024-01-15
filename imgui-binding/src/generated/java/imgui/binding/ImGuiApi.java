package imgui.binding;

/**
 * GENERATED CONTENT
 * A class to hold auto-generated API for Dear ImGui namespace.
 */
public class ImGuiApi {

    // GENERATED API: BEGIN
    /*JNI
        #include "_common.h"
    */

    public ImGuiApi() {
        imgui.ImGui.init();
    }

    private static java.lang.ref.WeakReference<Object> payloadRef = null;

    private static final byte[] PAYLOAD_PLACEHOLDER_DATA = new byte[1];

    /*JNI
        jmethodID jImStringResizeInternalMID;
        jmethodID jInputTextCallbackMID;
    
        jfieldID inputDataSizeID;
        jfieldID inputDataIsDirtyID;
        jfieldID inputDataIsResizedID;
    
        struct InputTextCallbackUserData {
            JNIEnv* env;
            jobject* imString;
            int maxSize;
            jbyteArray jResizedBuf;
            char* resizedBuf;
            jobject* textInputData;
            char* allowedChars;
            jobject* handler;
        };
    
        static int TextEditCallbackStub(ImGuiInputTextCallbackData* data) {
            InputTextCallbackUserData* userData = (InputTextCallbackUserData*)data->UserData;
    
            if (data->EventFlag == ImGuiInputTextFlags_CallbackCharFilter) {
                int allowedCharLength = strlen(userData->allowedChars);
                if(allowedCharLength > 0) {
                    bool found = false;
                    for(int i = 0; i < allowedCharLength; i++) {
                        if(userData->allowedChars[i] == data->EventChar) {
                            found = true;
                            break;
                        }
                    }
                    return found ? 0 : 1;
                }
            } else if (data->EventFlag == ImGuiInputTextFlags_CallbackResize) {
                int newSize = data->BufTextLen;
                if (newSize >= userData->maxSize) {
                    JNIEnv* env = userData->env;
    
                    jbyteArray newBufArr = (jbyteArray)env->CallObjectMethod(*userData->imString, jImStringResizeInternalMID, newSize);
                    char* newBuf = (char*)env->GetPrimitiveArrayCritical(newBufArr, 0);
    
                    data->Buf = newBuf;
    
                    userData->jResizedBuf = newBufArr;
                    userData->resizedBuf = newBuf;
                }
            }
    
            if (userData->handler != NULL) {
                JNIEnv* env = userData->env;
                env->CallObjectMethod(*userData->handler, jInputTextCallbackMID, data);
            }
    
            return 0;
        }
    */

    {
        this.nInitInputTextData();
    }

    private static final imgui.internal.ImGuiContext __gen_getCurrentContext_1 = new imgui.internal.ImGuiContext(0);

    private static final imgui.ImGuiIO __gen_getIO_1 = new imgui.ImGuiIO(0);

    private static final imgui.ImGuiStyle __gen_getStyle_1 = new imgui.ImGuiStyle(0);

    private static final imgui.ImDrawData __gen_getDrawData_1 = new imgui.ImDrawData(0);

    private static final imgui.ImGuiViewport __gen_getMainViewport_1 = new imgui.ImGuiViewport(0);

    private static final imgui.ImGuiPlatformIO __gen_getPlatformIO_1 = new imgui.ImGuiPlatformIO(0);

    public imgui.internal.ImGuiContext createContext() {
        return new imgui.internal.ImGuiContext(this.nCreateContext());
    }

    public imgui.internal.ImGuiContext createContext(final imgui.ImFontAtlas sharedFontAtlas) {
        return new imgui.internal.ImGuiContext(this.nCreateContext(sharedFontAtlas.ptr));
    }

    public void destroyContext() {
        this.nDestroyContext();
    }

    public void destroyContext(final imgui.internal.ImGuiContext ctx) {
        this.nDestroyContext(ctx.ptr);
    }

    public imgui.internal.ImGuiContext getCurrentContext() {
        __gen_getCurrentContext_1.ptr = this.nGetCurrentContext();
        return __gen_getCurrentContext_1;
    }

    public void setCurrentContext(final imgui.internal.ImGuiContext ctx) {
        this.nSetCurrentContext(ctx.ptr);
    }

    public imgui.ImGuiIO getIO() {
        __gen_getIO_1.ptr = this.nGetIO();
        return __gen_getIO_1;
    }

    public imgui.ImGuiStyle getStyle() {
        __gen_getStyle_1.ptr = this.nGetStyle();
        return __gen_getStyle_1;
    }

    public void newFrame() {
        this.nNewFrame();
    }

    public void endFrame() {
        this.nEndFrame();
    }

    public void render() {
        this.nRender();
    }

    public imgui.ImDrawData getDrawData() {
        __gen_getDrawData_1.ptr = this.nGetDrawData();
        return __gen_getDrawData_1;
    }

    public void showDemoWindow() {
        this.nShowDemoWindow();
    }

    public void showDemoWindow(final imgui.type.ImBoolean pOpen) {
        this.nShowDemoWindow(pOpen.getData());
    }

    public void showMetricsWindow() {
        this.nShowMetricsWindow();
    }

    public void showMetricsWindow(final imgui.type.ImBoolean pOpen) {
        this.nShowMetricsWindow(pOpen.getData());
    }

    public void showStackToolWindow() {
        this.nShowStackToolWindow();
    }

    public void showStackToolWindow(final imgui.type.ImBoolean pOpen) {
        this.nShowStackToolWindow(pOpen.getData());
    }

    public void showAboutWindow() {
        this.nShowAboutWindow();
    }

    public void showAboutWindow(final imgui.type.ImBoolean pOpen) {
        this.nShowAboutWindow(pOpen.getData());
    }

    public void showStyleEditor() {
        this.nShowStyleEditor();
    }

    public void showStyleEditor(final imgui.ImGuiStyle ref) {
        this.nShowStyleEditor(ref.ptr);
    }

    public boolean showStyleSelector(final String label) {
        return this.nShowStyleSelector(label);
    }

    public void showFontSelector(final String label) {
        this.nShowFontSelector(label);
    }

    public void showUserGuide() {
        this.nShowUserGuide();
    }

    public String getVersion() {
        return this.nGetVersion();
    }

    public void styleColorsDark() {
        this.nStyleColorsDark();
    }

    public void styleColorsDark(final imgui.ImGuiStyle dst) {
        this.nStyleColorsDark(dst.ptr);
    }

    public void styleColorsLight() {
        this.nStyleColorsLight();
    }

    public void styleColorsLight(final imgui.ImGuiStyle dst) {
        this.nStyleColorsLight(dst.ptr);
    }

    public void styleColorsClassic() {
        this.nStyleColorsClassic();
    }

    public void styleColorsClassic(final imgui.ImGuiStyle dst) {
        this.nStyleColorsClassic(dst.ptr);
    }

    public boolean begin(final String name) {
        return this.nBegin(name);
    }

    public boolean begin(final String name, final imgui.type.ImBoolean pOpen) {
        return this.nBegin(name, pOpen.getData());
    }

    public boolean begin(final String name, final int flags) {
        return this.nBegin(name, flags);
    }

    public boolean begin(final String name, final imgui.type.ImBoolean pOpen, final int flags) {
        return this.nBegin(name, pOpen.getData(), flags);
    }

    public void end() {
        this.nEnd();
    }

    public boolean beginChild(final String strId) {
        return this.nBeginChild(strId);
    }

    public boolean beginChild(final String strId, final imgui.ImVec2 size) {
        return this.beginChild(strId, size.x, size.y);
    }

    public boolean beginChild(final String strId, final float sizeX, final float sizeY) {
        return this.nBeginChild(strId, sizeX, sizeY);
    }

    public boolean beginChild(final String strId, final boolean border) {
        return this.nBeginChild(strId, border);
    }

    public boolean beginChild(final String strId, final imgui.ImVec2 size, final boolean border) {
        return this.beginChild(strId, size.x, size.y, border);
    }

    public boolean beginChild(final String strId, final float sizeX, final float sizeY, final boolean border) {
        return this.nBeginChild(strId, sizeX, sizeY, border);
    }

    public boolean beginChild(final String strId, final boolean border, final int flags) {
        return this.nBeginChild(strId, border, flags);
    }

    public boolean beginChild(final String strId, final imgui.ImVec2 size, final int flags) {
        return this.beginChild(strId, size.x, size.y, flags);
    }

    public boolean beginChild(final String strId, final float sizeX, final float sizeY, final int flags) {
        return this.nBeginChild(strId, sizeX, sizeY, flags);
    }

    public boolean beginChild(final String strId, final imgui.ImVec2 size, final boolean border, final int flags) {
        return this.beginChild(strId, size.x, size.y, border, flags);
    }

    public boolean beginChild(final String strId, final float sizeX, final float sizeY, final boolean border, final int flags) {
        return this.nBeginChild(strId, sizeX, sizeY, border, flags);
    }

    public boolean beginChild(final int id) {
        return this.nBeginChild(id);
    }

    public boolean beginChild(final int id, final imgui.ImVec2 size) {
        return this.beginChild(id, size.x, size.y);
    }

    public boolean beginChild(final int id, final float sizeX, final float sizeY) {
        return this.nBeginChild(id, sizeX, sizeY);
    }

    public boolean beginChild(final int id, final boolean border) {
        return this.nBeginChild(id, border);
    }

    public boolean beginChild(final int id, final imgui.ImVec2 size, final boolean border) {
        return this.beginChild(id, size.x, size.y, border);
    }

    public boolean beginChild(final int id, final float sizeX, final float sizeY, final boolean border) {
        return this.nBeginChild(id, sizeX, sizeY, border);
    }

    public boolean beginChild(final int id, final boolean border, final int flags) {
        return this.nBeginChild(id, border, flags);
    }

    public boolean beginChild(final int id, final imgui.ImVec2 size, final int flags) {
        return this.beginChild(id, size.x, size.y, flags);
    }

    public boolean beginChild(final int id, final float sizeX, final float sizeY, final int flags) {
        return this.nBeginChild(id, sizeX, sizeY, flags);
    }

    public boolean beginChild(final int id, final imgui.ImVec2 size, final boolean border, final int flags) {
        return this.beginChild(id, size.x, size.y, border, flags);
    }

    public boolean beginChild(final int id, final float sizeX, final float sizeY, final boolean border, final int flags) {
        return this.nBeginChild(id, sizeX, sizeY, border, flags);
    }

    public void endChild() {
        this.nEndChild();
    }

    public boolean isWindowAppearing() {
        return this.nIsWindowAppearing();
    }

    public boolean isWindowCollapsed() {
        return this.nIsWindowCollapsed();
    }

    public boolean isWindowFocused() {
        return this.nIsWindowFocused();
    }

    public boolean isWindowFocused(final int flags) {
        return this.nIsWindowFocused(flags);
    }

    public boolean isWindowHovered() {
        return this.nIsWindowHovered();
    }

    public boolean isWindowHovered(final int flags) {
        return this.nIsWindowHovered(flags);
    }

    public imgui.ImDrawList getWindowDrawList() {
        return new imgui.ImDrawList(this.nGetWindowDrawList());
    }

    public float getWindowDpiScale() {
        return this.nGetWindowDpiScale();
    }

    public imgui.ImVec2 getWindowPos() {
        final imgui.ImVec2 dst = new imgui.ImVec2();
        this.getWindowPos(dst);
        return dst;
    }

    public void getWindowPos(final imgui.ImVec2 dst) {
        dst.x = this.getWindowPosX();
        dst.y = this.getWindowPosY();
    }

    public imgui.ImVec2 getWindowSize() {
        final imgui.ImVec2 dst = new imgui.ImVec2();
        this.getWindowSize(dst);
        return dst;
    }

    public void getWindowSize(final imgui.ImVec2 dst) {
        dst.x = this.getWindowSizeX();
        dst.y = this.getWindowSizeY();
    }

    public float getWindowWidth() {
        return this.nGetWindowWidth();
    }

    public float getWindowHeight() {
        return this.nGetWindowHeight();
    }

    public imgui.ImGuiViewport getWindowViewport() {
        return new imgui.ImGuiViewport(this.nGetWindowViewport());
    }

    public void setNextWindowPos(final imgui.ImVec2 pos) {
        this.setNextWindowPos(pos.x, pos.y);
    }

    public void setNextWindowPos(final float posX, final float posY) {
        this.nSetNextWindowPos(posX, posY);
    }

    public void setNextWindowPos(final imgui.ImVec2 pos, final int cond) {
        this.setNextWindowPos(pos.x, pos.y, cond);
    }

    public void setNextWindowPos(final float posX, final float posY, final int cond) {
        this.nSetNextWindowPos(posX, posY, cond);
    }

    public void setNextWindowPos(final imgui.ImVec2 pos, final imgui.ImVec2 pivot) {
        this.setNextWindowPos(pos.x, pos.y, pivot.x, pivot.y);
    }

    public void setNextWindowPos(final float posX, final float posY, final float pivotX, final float pivotY) {
        this.nSetNextWindowPos(posX, posY, pivotX, pivotY);
    }

    public void setNextWindowPos(final imgui.ImVec2 pos, final int cond, final imgui.ImVec2 pivot) {
        this.setNextWindowPos(pos.x, pos.y, cond, pivot.x, pivot.y);
    }

    public void setNextWindowPos(final float posX, final float posY, final int cond, final float pivotX, final float pivotY) {
        this.nSetNextWindowPos(posX, posY, cond, pivotX, pivotY);
    }

    public void setNextWindowSize(final imgui.ImVec2 size) {
        this.setNextWindowSize(size.x, size.y);
    }

    public void setNextWindowSize(final float sizeX, final float sizeY) {
        this.nSetNextWindowSize(sizeX, sizeY);
    }

    public void setNextWindowSize(final imgui.ImVec2 size, final int cond) {
        this.setNextWindowSize(size.x, size.y, cond);
    }

    public void setNextWindowSize(final float sizeX, final float sizeY, final int cond) {
        this.nSetNextWindowSize(sizeX, sizeY, cond);
    }

    public void setNextWindowSizeConstraints(final imgui.ImVec2 sizeMin, final imgui.ImVec2 sizeMax) {
        this.setNextWindowSizeConstraints(sizeMin.x, sizeMin.y, sizeMax.x, sizeMax.y);
    }

    public void setNextWindowSizeConstraints(final float sizeMinX, final float sizeMinY, final float sizeMaxX, final float sizeMaxY) {
        this.nSetNextWindowSizeConstraints(sizeMinX, sizeMinY, sizeMaxX, sizeMaxY);
    }

    public void setNextWindowContentSize(final imgui.ImVec2 size) {
        this.setNextWindowContentSize(size.x, size.y);
    }

    public void setNextWindowContentSize(final float sizeX, final float sizeY) {
        this.nSetNextWindowContentSize(sizeX, sizeY);
    }

    public void setNextWindowCollapsed(final boolean collapsed) {
        this.nSetNextWindowCollapsed(collapsed);
    }

    public void setNextWindowCollapsed(final boolean collapsed, final int cond) {
        this.nSetNextWindowCollapsed(collapsed, cond);
    }

    public void setNextWindowFocus() {
        this.nSetNextWindowFocus();
    }

    public void setNextWindowBgAlpha(final float alpha) {
        this.nSetNextWindowBgAlpha(alpha);
    }

    public void setNextWindowViewport(final int viewportId) {
        this.nSetNextWindowViewport(viewportId);
    }

    public void setWindowFontScale(final float scale) {
        this.nSetWindowFontScale(scale);
    }

    public void setWindowPos(final imgui.ImVec2 pos) {
        this.setWindowPos(pos.x, pos.y);
    }

    public void setWindowPos(final float posX, final float posY) {
        this.nSetWindowPos(posX, posY);
    }

    public void setWindowPos(final imgui.ImVec2 pos, final int cond) {
        this.setWindowPos(pos.x, pos.y, cond);
    }

    public void setWindowPos(final float posX, final float posY, final int cond) {
        this.nSetWindowPos(posX, posY, cond);
    }

    public void setWindowPos(final String name, final imgui.ImVec2 pos) {
        this.setWindowPos(name, pos.x, pos.y);
    }

    public void setWindowPos(final String name, final float posX, final float posY) {
        this.nSetWindowPos(name, posX, posY);
    }

    public void setWindowPos(final String name, final imgui.ImVec2 pos, final int cond) {
        this.setWindowPos(name, pos.x, pos.y, cond);
    }

    public void setWindowPos(final String name, final float posX, final float posY, final int cond) {
        this.nSetWindowPos(name, posX, posY, cond);
    }

    public void setWindowSize(final imgui.ImVec2 size) {
        this.setWindowSize(size.x, size.y);
    }

    public void setWindowSize(final float sizeX, final float sizeY) {
        this.nSetWindowSize(sizeX, sizeY);
    }

    public void setWindowSize(final imgui.ImVec2 size, final int cond) {
        this.setWindowSize(size.x, size.y, cond);
    }

    public void setWindowSize(final float sizeX, final float sizeY, final int cond) {
        this.nSetWindowSize(sizeX, sizeY, cond);
    }

    public void setWindowSize(final String name, final imgui.ImVec2 size) {
        this.setWindowSize(name, size.x, size.y);
    }

    public void setWindowSize(final String name, final float sizeX, final float sizeY) {
        this.nSetWindowSize(name, sizeX, sizeY);
    }

    public void setWindowSize(final String name, final imgui.ImVec2 size, final int cond) {
        this.setWindowSize(name, size.x, size.y, cond);
    }

    public void setWindowSize(final String name, final float sizeX, final float sizeY, final int cond) {
        this.nSetWindowSize(name, sizeX, sizeY, cond);
    }

    public void setWindowCollapsed(final boolean collapsed) {
        this.nSetWindowCollapsed(collapsed);
    }

    public void setWindowCollapsed(final boolean collapsed, final int cond) {
        this.nSetWindowCollapsed(collapsed, cond);
    }

    public void setWindowCollapsed(final String name, final boolean collapsed) {
        this.nSetWindowCollapsed(name, collapsed);
    }

    public void setWindowCollapsed(final String name, final boolean collapsed, final int cond) {
        this.nSetWindowCollapsed(name, collapsed, cond);
    }

    public void setWindowFocus() {
        this.nSetWindowFocus();
    }

    public void setWindowFocus(final String name) {
        this.nSetWindowFocus(name);
    }

    public imgui.ImVec2 getContentRegionAvail() {
        final imgui.ImVec2 dst = new imgui.ImVec2();
        this.getContentRegionAvail(dst);
        return dst;
    }

    public void getContentRegionAvail(final imgui.ImVec2 dst) {
        dst.x = this.getContentRegionAvailX();
        dst.y = this.getContentRegionAvailY();
    }

    public imgui.ImVec2 getContentRegionMax() {
        final imgui.ImVec2 dst = new imgui.ImVec2();
        this.getContentRegionMax(dst);
        return dst;
    }

    public void getContentRegionMax(final imgui.ImVec2 dst) {
        dst.x = this.getContentRegionMaxX();
        dst.y = this.getContentRegionMaxY();
    }

    public imgui.ImVec2 getWindowContentRegionMin() {
        final imgui.ImVec2 dst = new imgui.ImVec2();
        this.getWindowContentRegionMin(dst);
        return dst;
    }

    public void getWindowContentRegionMin(final imgui.ImVec2 dst) {
        dst.x = this.getWindowContentRegionMinX();
        dst.y = this.getWindowContentRegionMinY();
    }

    public imgui.ImVec2 getWindowContentRegionMax() {
        final imgui.ImVec2 dst = new imgui.ImVec2();
        this.getWindowContentRegionMax(dst);
        return dst;
    }

    public void getWindowContentRegionMax(final imgui.ImVec2 dst) {
        dst.x = this.getWindowContentRegionMaxX();
        dst.y = this.getWindowContentRegionMaxY();
    }

    public float getScrollX() {
        return this.nGetScrollX();
    }

    public float getScrollY() {
        return this.nGetScrollY();
    }

    public void setScrollX(final float scrollX) {
        this.nSetScrollX(scrollX);
    }

    public void setScrollY(final float scrollY) {
        this.nSetScrollY(scrollY);
    }

    public float getScrollMaxX() {
        return this.nGetScrollMaxX();
    }

    public float getScrollMaxY() {
        return this.nGetScrollMaxY();
    }

    public void setScrollHereX() {
        this.nSetScrollHereX();
    }

    public void setScrollHereX(final float centerXRatio) {
        this.nSetScrollHereX(centerXRatio);
    }

    public void setScrollHereY() {
        this.nSetScrollHereY();
    }

    public void setScrollHereY(final float centerYRatio) {
        this.nSetScrollHereY(centerYRatio);
    }

    public void setScrollFromPosX(final float localX) {
        this.nSetScrollFromPosX(localX);
    }

    public void setScrollFromPosX(final float localX, final float centerXRatio) {
        this.nSetScrollFromPosX(localX, centerXRatio);
    }

    public void setScrollFromPosY(final float localY) {
        this.nSetScrollFromPosY(localY);
    }

    public void setScrollFromPosY(final float localY, final float centerYRatio) {
        this.nSetScrollFromPosY(localY, centerYRatio);
    }

    public void pushFont(final imgui.ImFont font) {
        this.nPushFont(font.ptr);
    }

    public void popFont() {
        this.nPopFont();
    }

    public void pushStyleColor(final int idx, final int col) {
        this.nPushStyleColor(idx, col);
    }

    public void pushStyleColor(final int idx, final imgui.ImVec4 col) {
        this.pushStyleColor(idx, col.x, col.y, col.z, col.w);
    }

    public void pushStyleColor(final int idx, final float colX, final float colY, final float colZ, final float colW) {
        this.nPushStyleColor(idx, colX, colY, colZ, colW);
    }

    public void popStyleColor() {
        this.nPopStyleColor();
    }

    public void popStyleColor(final int count) {
        this.nPopStyleColor(count);
    }

    public void pushStyleVar(final int idx, final float val) {
        this.nPushStyleVar(idx, val);
    }

    public void pushStyleVar(final int idx, final imgui.ImVec2 val) {
        this.pushStyleVar(idx, val.x, val.y);
    }

    public void pushStyleVar(final int idx, final float valX, final float valY) {
        this.nPushStyleVar(idx, valX, valY);
    }

    public void popStyleVar() {
        this.nPopStyleVar();
    }

    public void popStyleVar(final int count) {
        this.nPopStyleVar(count);
    }

    public void pushAllowKeyboardFocus(final boolean allowKeyboardFocus) {
        this.nPushAllowKeyboardFocus(allowKeyboardFocus);
    }

    public void popAllowKeyboardFocus() {
        this.nPopAllowKeyboardFocus();
    }

    public void pushButtonRepeat(final boolean repeat) {
        this.nPushButtonRepeat(repeat);
    }

    public void popButtonRepeat() {
        this.nPopButtonRepeat();
    }

    public void pushItemWidth(final float itemWidth) {
        this.nPushItemWidth(itemWidth);
    }

    public void popItemWidth() {
        this.nPopItemWidth();
    }

    public void setNextItemWidth(final float itemWidth) {
        this.nSetNextItemWidth(itemWidth);
    }

    public float calcItemWidth() {
        return this.nCalcItemWidth();
    }

    public void pushTextWrapPos() {
        this.nPushTextWrapPos();
    }

    public void pushTextWrapPos(final float wrapLocalPosX) {
        this.nPushTextWrapPos(wrapLocalPosX);
    }

    public void popTextWrapPos() {
        this.nPopTextWrapPos();
    }

    public imgui.ImFont getFont() {
        return new imgui.ImFont(this.nGetFont());
    }

    public float getFontSize() {
        return this.nGetFontSize();
    }

    public imgui.ImVec2 getFontTexUvWhitePixel() {
        final imgui.ImVec2 dst = new imgui.ImVec2();
        this.getFontTexUvWhitePixel(dst);
        return dst;
    }

    public void getFontTexUvWhitePixel(final imgui.ImVec2 dst) {
        dst.x = this.getFontTexUvWhitePixelX();
        dst.y = this.getFontTexUvWhitePixelY();
    }

    public int getColorU32(final int idx) {
        return this.nGetColorU32(idx);
    }

    public int getColorU32(final int idx, final float alphaMul) {
        return this.nGetColorU32(idx, alphaMul);
    }

    public int getColorU32(final imgui.ImVec4 col) {
        return this.getColorU32(col.x, col.y, col.z, col.w);
    }

    public int getColorU32(final float colX, final float colY, final float colZ, final float colW) {
        return this.nGetColorU32(colX, colY, colZ, colW);
    }

    public imgui.ImVec4 getStyleColorVec4(final int idx) {
        final imgui.ImVec4 dst = new imgui.ImVec4();
        this.getStyleColorVec4(dst, idx);
        return dst;
    }

    public void getStyleColorVec4(final imgui.ImVec4 dst, final int idx) {
        dst.x = this.getStyleColorVec4X(idx);
        dst.y = this.getStyleColorVec4Y(idx);
        dst.z = this.getStyleColorVec4Z(idx);
        dst.w = this.getStyleColorVec4W(idx);
    }

    public void separator() {
        this.nSeparator();
    }

    public void sameLine() {
        this.nSameLine();
    }

    public void sameLine(final float offsetFromStartX) {
        this.nSameLine(offsetFromStartX);
    }

    public void sameLine(final float offsetFromStartX, final float spacing) {
        this.nSameLine(offsetFromStartX, spacing);
    }

    public void newLine() {
        this.nNewLine();
    }

    public void spacing() {
        this.nSpacing();
    }

    public void dummy(final imgui.ImVec2 size) {
        this.dummy(size.x, size.y);
    }

    public void dummy(final float sizeX, final float sizeY) {
        this.nDummy(sizeX, sizeY);
    }

    public void indent() {
        this.nIndent();
    }

    public void indent(final float indentW) {
        this.nIndent(indentW);
    }

    public void unindent() {
        this.nUnindent();
    }

    public void unindent(final float indentW) {
        this.nUnindent(indentW);
    }

    public void beginGroup() {
        this.nBeginGroup();
    }

    public void endGroup() {
        this.nEndGroup();
    }

    public imgui.ImVec2 getCursorPos() {
        final imgui.ImVec2 dst = new imgui.ImVec2();
        this.getCursorPos(dst);
        return dst;
    }

    public void getCursorPos(final imgui.ImVec2 dst) {
        dst.x = this.getCursorPosX();
        dst.y = this.getCursorPosY();
    }

    public float getCursorPosX() {
        return this.nGetCursorPosX();
    }

    public float getCursorPosY() {
        return this.nGetCursorPosY();
    }

    public void setCursorPos(final imgui.ImVec2 localPos) {
        this.setCursorPos(localPos.x, localPos.y);
    }

    public void setCursorPos(final float localPosX, final float localPosY) {
        this.nSetCursorPos(localPosX, localPosY);
    }

    public void setCursorPosX(final float localX) {
        this.nSetCursorPosX(localX);
    }

    public void setCursorPosY(final float localY) {
        this.nSetCursorPosY(localY);
    }

    public imgui.ImVec2 getCursorStartPos() {
        final imgui.ImVec2 dst = new imgui.ImVec2();
        this.getCursorStartPos(dst);
        return dst;
    }

    public void getCursorStartPos(final imgui.ImVec2 dst) {
        dst.x = this.getCursorStartPosX();
        dst.y = this.getCursorStartPosY();
    }

    public imgui.ImVec2 getCursorScreenPos() {
        final imgui.ImVec2 dst = new imgui.ImVec2();
        this.getCursorScreenPos(dst);
        return dst;
    }

    public void getCursorScreenPos(final imgui.ImVec2 dst) {
        dst.x = this.getCursorScreenPosX();
        dst.y = this.getCursorScreenPosY();
    }

    public void setCursorScreenPos(final imgui.ImVec2 pos) {
        this.setCursorScreenPos(pos.x, pos.y);
    }

    public void setCursorScreenPos(final float posX, final float posY) {
        this.nSetCursorScreenPos(posX, posY);
    }

    public void alignTextToFramePadding() {
        this.nAlignTextToFramePadding();
    }

    public float getTextLineHeight() {
        return this.nGetTextLineHeight();
    }

    public float getTextLineHeightWithSpacing() {
        return this.nGetTextLineHeightWithSpacing();
    }

    public float getFrameHeight() {
        return this.nGetFrameHeight();
    }

    public float getFrameHeightWithSpacing() {
        return this.nGetFrameHeightWithSpacing();
    }

    public void pushID(final String strId) {
        this.nPushID(strId);
    }

    public void pushID(final String strIdBegin, final String strIdEnd) {
        this.nPushID(strIdBegin, strIdEnd);
    }

    public void pushID(final int intId) {
        this.nPushID(intId);
    }

    public void popID() {
        this.nPopID();
    }

    public int getID(final String strId) {
        return this.nGetID(strId);
    }

    public int getID(final String strIdBegin, final String strIdEnd) {
        return this.nGetID(strIdBegin, strIdEnd);
    }

    public void textUnformatted(final String text) {
        this.nTextUnformatted(text);
    }

    public void textUnformatted(final String text, final String textEnd) {
        this.nTextUnformatted(text, textEnd);
    }

    public void text(final String fmt) {
        this.nText(fmt);
    }

    public void textColored(final imgui.ImVec4 col, final String fmt) {
        this.textColored(col.x, col.y, col.z, col.w, fmt);
    }

    public void textColored(final float colX, final float colY, final float colZ, final float colW, final String fmt) {
        this.nTextColored(colX, colY, colZ, colW, fmt);
    }

    public void textDisabled(final String fmt) {
        this.nTextDisabled(fmt);
    }

    public void textWrapped(final String fmt) {
        this.nTextWrapped(fmt);
    }

    public void labelText(final String label, final String fmt) {
        this.nLabelText(label, fmt);
    }

    public void bulletText(final String fmt) {
        this.nBulletText(fmt);
    }

    public boolean button(final String label) {
        return this.nButton(label);
    }

    public boolean button(final String label, final imgui.ImVec2 size) {
        return this.button(label, size.x, size.y);
    }

    public boolean button(final String label, final float sizeX, final float sizeY) {
        return this.nButton(label, sizeX, sizeY);
    }

    public boolean smallButton(final String label) {
        return this.nSmallButton(label);
    }

    public boolean invisibleButton(final String strId, final imgui.ImVec2 size) {
        return this.invisibleButton(strId, size.x, size.y);
    }

    public boolean invisibleButton(final String strId, final float sizeX, final float sizeY) {
        return this.nInvisibleButton(strId, sizeX, sizeY);
    }

    public boolean invisibleButton(final String strId, final imgui.ImVec2 size, final int flags) {
        return this.invisibleButton(strId, size.x, size.y, flags);
    }

    public boolean invisibleButton(final String strId, final float sizeX, final float sizeY, final int flags) {
        return this.nInvisibleButton(strId, sizeX, sizeY, flags);
    }

    public boolean arrowButton(final String strId, final int dir) {
        return this.nArrowButton(strId, dir);
    }

    public void image(final int userTextureId, final imgui.ImVec2 size) {
        this.image(userTextureId, size.x, size.y);
    }

    public void image(final int userTextureId, final float sizeX, final float sizeY) {
        this.nImage(userTextureId, sizeX, sizeY);
    }

    public void image(final int userTextureId, final imgui.ImVec2 size, final imgui.ImVec2 uv0) {
        this.image(userTextureId, size.x, size.y, uv0.x, uv0.y);
    }

    public void image(final int userTextureId, final float sizeX, final float sizeY, final float uv0X, final float uv0Y) {
        this.nImage(userTextureId, sizeX, sizeY, uv0X, uv0Y);
    }

    public void image(final int userTextureId, final imgui.ImVec2 size, final imgui.ImVec2 uv0, final imgui.ImVec2 uv1) {
        this.image(userTextureId, size.x, size.y, uv0.x, uv0.y, uv1.x, uv1.y);
    }

    public void image(final int userTextureId, final float sizeX, final float sizeY, final float uv0X, final float uv0Y, final float uv1X, final float uv1Y) {
        this.nImage(userTextureId, sizeX, sizeY, uv0X, uv0Y, uv1X, uv1Y);
    }

    public void image(final int userTextureId, final imgui.ImVec2 size, final imgui.ImVec2 uv0, final imgui.ImVec2 uv1, final imgui.ImVec4 tintCol) {
        this.image(userTextureId, size, uv0, uv1, tintCol.x, tintCol.y, tintCol.z, tintCol.w);
    }

    public void image(final int userTextureId, final imgui.ImVec2 size, final imgui.ImVec2 uv0, final imgui.ImVec2 uv1, final float tintColX, final float tintColY, final float tintColZ, final float tintColW) {
        this.image(userTextureId, size.x, size.y, uv0.x, uv0.y, uv1.x, uv1.y, tintColX, tintColY, tintColZ, tintColW);
    }

    public void image(final int userTextureId, final float sizeX, final float sizeY, final float uv0X, final float uv0Y, final float uv1X, final float uv1Y, final imgui.ImVec4 tintCol) {
        this.image(userTextureId, sizeX, sizeY, uv0X, uv0Y, uv1X, uv1Y, tintCol.x, tintCol.y, tintCol.z, tintCol.w);
    }

    public void image(final int userTextureId, final float sizeX, final float sizeY, final float uv0X, final float uv0Y, final float uv1X, final float uv1Y, final float tintColX, final float tintColY, final float tintColZ, final float tintColW) {
        this.nImage(userTextureId, sizeX, sizeY, uv0X, uv0Y, uv1X, uv1Y, tintColX, tintColY, tintColZ, tintColW);
    }

    public void image(final int userTextureId, final imgui.ImVec2 size, final imgui.ImVec2 uv0, final imgui.ImVec2 uv1, final imgui.ImVec4 tintCol, final imgui.ImVec4 borderCol) {
        this.image(userTextureId, size, uv0, uv1, tintCol.x, tintCol.y, tintCol.z, tintCol.w, borderCol.x, borderCol.y, borderCol.z, borderCol.w);
    }

    public void image(final int userTextureId, final imgui.ImVec2 size, final imgui.ImVec2 uv0, final imgui.ImVec2 uv1, final float tintColX, final float tintColY, final float tintColZ, final float tintColW, final float borderColX, final float borderColY, final float borderColZ, final float borderColW) {
        this.image(userTextureId, size.x, size.y, uv0.x, uv0.y, uv1.x, uv1.y, tintColX, tintColY, tintColZ, tintColW, borderColX, borderColY, borderColZ, borderColW);
    }

    public void image(final int userTextureId, final float sizeX, final float sizeY, final float uv0X, final float uv0Y, final float uv1X, final float uv1Y, final imgui.ImVec4 tintCol, final imgui.ImVec4 borderCol) {
        this.image(userTextureId, sizeX, sizeY, uv0X, uv0Y, uv1X, uv1Y, tintCol.x, tintCol.y, tintCol.z, tintCol.w, borderCol.x, borderCol.y, borderCol.z, borderCol.w);
    }

    public void image(final int userTextureId, final float sizeX, final float sizeY, final float uv0X, final float uv0Y, final float uv1X, final float uv1Y, final float tintColX, final float tintColY, final float tintColZ, final float tintColW, final float borderColX, final float borderColY, final float borderColZ, final float borderColW) {
        this.nImage(userTextureId, sizeX, sizeY, uv0X, uv0Y, uv1X, uv1Y, tintColX, tintColY, tintColZ, tintColW, borderColX, borderColY, borderColZ, borderColW);
    }

    public boolean imageButton(final int userTextureId, final imgui.ImVec2 size) {
        return this.imageButton(userTextureId, size.x, size.y);
    }

    public boolean imageButton(final int userTextureId, final float sizeX, final float sizeY) {
        return this.nImageButton(userTextureId, sizeX, sizeY);
    }

    public boolean imageButton(final int userTextureId, final imgui.ImVec2 size, final imgui.ImVec2 uv0) {
        return this.imageButton(userTextureId, size.x, size.y, uv0.x, uv0.y);
    }

    public boolean imageButton(final int userTextureId, final float sizeX, final float sizeY, final float uv0X, final float uv0Y) {
        return this.nImageButton(userTextureId, sizeX, sizeY, uv0X, uv0Y);
    }

    public boolean imageButton(final int userTextureId, final imgui.ImVec2 size, final imgui.ImVec2 uv0, final imgui.ImVec2 uv1) {
        return this.imageButton(userTextureId, size.x, size.y, uv0.x, uv0.y, uv1.x, uv1.y);
    }

    public boolean imageButton(final int userTextureId, final float sizeX, final float sizeY, final float uv0X, final float uv0Y, final float uv1X, final float uv1Y) {
        return this.nImageButton(userTextureId, sizeX, sizeY, uv0X, uv0Y, uv1X, uv1Y);
    }

    public boolean imageButton(final int userTextureId, final imgui.ImVec2 size, final imgui.ImVec2 uv0, final imgui.ImVec2 uv1, final int framePadding) {
        return this.imageButton(userTextureId, size.x, size.y, uv0.x, uv0.y, uv1.x, uv1.y, framePadding);
    }

    public boolean imageButton(final int userTextureId, final float sizeX, final float sizeY, final float uv0X, final float uv0Y, final float uv1X, final float uv1Y, final int framePadding) {
        return this.nImageButton(userTextureId, sizeX, sizeY, uv0X, uv0Y, uv1X, uv1Y, framePadding);
    }

    public boolean imageButton(final int userTextureId, final imgui.ImVec2 size, final imgui.ImVec2 uv0, final imgui.ImVec2 uv1, final imgui.ImVec4 bgCol) {
        return this.imageButton(userTextureId, size, uv0, uv1, bgCol.x, bgCol.y, bgCol.z, bgCol.w);
    }

    public boolean imageButton(final int userTextureId, final imgui.ImVec2 size, final imgui.ImVec2 uv0, final imgui.ImVec2 uv1, final float bgColX, final float bgColY, final float bgColZ, final float bgColW) {
        return this.imageButton(userTextureId, size.x, size.y, uv0.x, uv0.y, uv1.x, uv1.y, bgColX, bgColY, bgColZ, bgColW);
    }

    public boolean imageButton(final int userTextureId, final float sizeX, final float sizeY, final float uv0X, final float uv0Y, final float uv1X, final float uv1Y, final imgui.ImVec4 bgCol) {
        return this.imageButton(userTextureId, sizeX, sizeY, uv0X, uv0Y, uv1X, uv1Y, bgCol.x, bgCol.y, bgCol.z, bgCol.w);
    }

    public boolean imageButton(final int userTextureId, final float sizeX, final float sizeY, final float uv0X, final float uv0Y, final float uv1X, final float uv1Y, final float bgColX, final float bgColY, final float bgColZ, final float bgColW) {
        return this.nImageButton(userTextureId, sizeX, sizeY, uv0X, uv0Y, uv1X, uv1Y, bgColX, bgColY, bgColZ, bgColW);
    }

    public boolean imageButton(final int userTextureId, final imgui.ImVec2 size, final imgui.ImVec2 uv0, final imgui.ImVec2 uv1, final int framePadding, final imgui.ImVec4 bgCol) {
        return this.imageButton(userTextureId, size, uv0, uv1, framePadding, bgCol.x, bgCol.y, bgCol.z, bgCol.w);
    }

    public boolean imageButton(final int userTextureId, final imgui.ImVec2 size, final imgui.ImVec2 uv0, final imgui.ImVec2 uv1, final int framePadding, final float bgColX, final float bgColY, final float bgColZ, final float bgColW) {
        return this.imageButton(userTextureId, size.x, size.y, uv0.x, uv0.y, uv1.x, uv1.y, framePadding, bgColX, bgColY, bgColZ, bgColW);
    }

    public boolean imageButton(final int userTextureId, final float sizeX, final float sizeY, final float uv0X, final float uv0Y, final float uv1X, final float uv1Y, final int framePadding, final imgui.ImVec4 bgCol) {
        return this.imageButton(userTextureId, sizeX, sizeY, uv0X, uv0Y, uv1X, uv1Y, framePadding, bgCol.x, bgCol.y, bgCol.z, bgCol.w);
    }

    public boolean imageButton(final int userTextureId, final float sizeX, final float sizeY, final float uv0X, final float uv0Y, final float uv1X, final float uv1Y, final int framePadding, final float bgColX, final float bgColY, final float bgColZ, final float bgColW) {
        return this.nImageButton(userTextureId, sizeX, sizeY, uv0X, uv0Y, uv1X, uv1Y, framePadding, bgColX, bgColY, bgColZ, bgColW);
    }

    public boolean imageButton(final int userTextureId, final imgui.ImVec2 size, final imgui.ImVec2 uv0, final imgui.ImVec2 uv1, final imgui.ImVec4 bgCol, final imgui.ImVec4 tintCol) {
        return this.imageButton(userTextureId, size, uv0, uv1, bgCol.x, bgCol.y, bgCol.z, bgCol.w, tintCol.x, tintCol.y, tintCol.z, tintCol.w);
    }

    public boolean imageButton(final int userTextureId, final imgui.ImVec2 size, final imgui.ImVec2 uv0, final imgui.ImVec2 uv1, final float bgColX, final float bgColY, final float bgColZ, final float bgColW, final float tintColX, final float tintColY, final float tintColZ, final float tintColW) {
        return this.imageButton(userTextureId, size.x, size.y, uv0.x, uv0.y, uv1.x, uv1.y, bgColX, bgColY, bgColZ, bgColW, tintColX, tintColY, tintColZ, tintColW);
    }

    public boolean imageButton(final int userTextureId, final float sizeX, final float sizeY, final float uv0X, final float uv0Y, final float uv1X, final float uv1Y, final imgui.ImVec4 bgCol, final imgui.ImVec4 tintCol) {
        return this.imageButton(userTextureId, sizeX, sizeY, uv0X, uv0Y, uv1X, uv1Y, bgCol.x, bgCol.y, bgCol.z, bgCol.w, tintCol.x, tintCol.y, tintCol.z, tintCol.w);
    }

    public boolean imageButton(final int userTextureId, final float sizeX, final float sizeY, final float uv0X, final float uv0Y, final float uv1X, final float uv1Y, final float bgColX, final float bgColY, final float bgColZ, final float bgColW, final float tintColX, final float tintColY, final float tintColZ, final float tintColW) {
        return this.nImageButton(userTextureId, sizeX, sizeY, uv0X, uv0Y, uv1X, uv1Y, bgColX, bgColY, bgColZ, bgColW, tintColX, tintColY, tintColZ, tintColW);
    }

    public boolean imageButton(final int userTextureId, final imgui.ImVec2 size, final imgui.ImVec2 uv0, final imgui.ImVec2 uv1, final int framePadding, final imgui.ImVec4 bgCol, final imgui.ImVec4 tintCol) {
        return this.imageButton(userTextureId, size, uv0, uv1, framePadding, bgCol.x, bgCol.y, bgCol.z, bgCol.w, tintCol.x, tintCol.y, tintCol.z, tintCol.w);
    }

    public boolean imageButton(final int userTextureId, final imgui.ImVec2 size, final imgui.ImVec2 uv0, final imgui.ImVec2 uv1, final int framePadding, final float bgColX, final float bgColY, final float bgColZ, final float bgColW, final float tintColX, final float tintColY, final float tintColZ, final float tintColW) {
        return this.imageButton(userTextureId, size.x, size.y, uv0.x, uv0.y, uv1.x, uv1.y, framePadding, bgColX, bgColY, bgColZ, bgColW, tintColX, tintColY, tintColZ, tintColW);
    }

    public boolean imageButton(final int userTextureId, final float sizeX, final float sizeY, final float uv0X, final float uv0Y, final float uv1X, final float uv1Y, final int framePadding, final imgui.ImVec4 bgCol, final imgui.ImVec4 tintCol) {
        return this.imageButton(userTextureId, sizeX, sizeY, uv0X, uv0Y, uv1X, uv1Y, framePadding, bgCol.x, bgCol.y, bgCol.z, bgCol.w, tintCol.x, tintCol.y, tintCol.z, tintCol.w);
    }

    public boolean imageButton(final int userTextureId, final float sizeX, final float sizeY, final float uv0X, final float uv0Y, final float uv1X, final float uv1Y, final int framePadding, final float bgColX, final float bgColY, final float bgColZ, final float bgColW, final float tintColX, final float tintColY, final float tintColZ, final float tintColW) {
        return this.nImageButton(userTextureId, sizeX, sizeY, uv0X, uv0Y, uv1X, uv1Y, framePadding, bgColX, bgColY, bgColZ, bgColW, tintColX, tintColY, tintColZ, tintColW);
    }

    public boolean checkbox(final String label, final boolean v) {
        return this.checkbox(label, new imgui.type.ImBoolean(v));
    }

    public boolean checkbox(final String label, final imgui.type.ImBoolean v) {
        return this.nCheckbox(label, v.getData());
    }

    public boolean checkboxFlags(final String label, final imgui.type.ImInt flags, final int flagsValue) {
        return this.nCheckboxFlags(label, flags.getData(), flagsValue);
    }

    public boolean radioButton(final String label, final boolean active) {
        return this.nRadioButton(label, active);
    }

    public boolean radioButton(final String label, final imgui.type.ImInt v, final int vButton) {
        return this.nRadioButton(label, v.getData(), vButton);
    }

    public void progressBar(final float fraction) {
        this.nProgressBar(fraction);
    }

    public void progressBar(final float fraction, final imgui.ImVec2 sizeArg) {
        this.progressBar(fraction, sizeArg.x, sizeArg.y);
    }

    public void progressBar(final float fraction, final float sizeArgX, final float sizeArgY) {
        this.nProgressBar(fraction, sizeArgX, sizeArgY);
    }

    public void progressBar(final float fraction, final String overlay) {
        this.nProgressBar(fraction, overlay);
    }

    public void progressBar(final float fraction, final imgui.ImVec2 sizeArg, final String overlay) {
        this.progressBar(fraction, sizeArg.x, sizeArg.y, overlay);
    }

    public void progressBar(final float fraction, final float sizeArgX, final float sizeArgY, final String overlay) {
        this.nProgressBar(fraction, sizeArgX, sizeArgY, overlay);
    }

    public void bullet() {
        this.nBullet();
    }

    public boolean beginCombo(final String label, final String previewValue) {
        return this.nBeginCombo(label, previewValue);
    }

    public boolean beginCombo(final String label, final String previewValue, final int flags) {
        return this.nBeginCombo(label, previewValue, flags);
    }

    public void endCombo() {
        this.nEndCombo();
    }

    public boolean combo(final String label, final imgui.type.ImInt currentItem, final String[] items) {
        return this.combo(label, currentItem, items, items.length);
    }

    public boolean combo(final String label, final imgui.type.ImInt currentItem, final String[] items, final int popupMaxHeightInItems) {
        return this.nCombo(label, currentItem.getData(), items, items.length, popupMaxHeightInItems);
    }

    public boolean combo(final String label, final imgui.type.ImInt currentItem, final String itemsSeparatedByZeros) {
        return this.nCombo(label, currentItem.getData(), itemsSeparatedByZeros);
    }

    public boolean combo(final String label, final imgui.type.ImInt currentItem, final String itemsSeparatedByZeros, final int popupMaxHeightInItems) {
        return this.nCombo(label, currentItem.getData(), itemsSeparatedByZeros, popupMaxHeightInItems);
    }

    public boolean dragFloat(final String label, final imgui.type.ImFloat value) {
        return this.nDragFloat(label, value.getData());
    }

    public boolean dragFloat(final String label, final float[] value) {
        return this.nDragFloat(label, value);
    }

    public boolean dragFloat(final String label, final imgui.type.ImFloat value, final float vSpeed) {
        return this.nDragFloat(label, value.getData(), vSpeed);
    }

    public boolean dragFloat(final String label, final float[] value, final float vSpeed) {
        return this.nDragFloat(label, value, vSpeed);
    }

    public boolean dragFloat(final String label, final imgui.type.ImFloat value, final float vSpeed, final float vMin) {
        return this.nDragFloat(label, value.getData(), vSpeed, vMin);
    }

    public boolean dragFloat(final String label, final float[] value, final float vSpeed, final float vMin) {
        return this.nDragFloat(label, value, vSpeed, vMin);
    }

    public boolean dragFloat(final String label, final imgui.type.ImFloat value, final float vSpeed, final float vMin, final float vMax) {
        return this.nDragFloat(label, value.getData(), vSpeed, vMin, vMax);
    }

    public boolean dragFloat(final String label, final float[] value, final float vSpeed, final float vMin, final float vMax) {
        return this.nDragFloat(label, value, vSpeed, vMin, vMax);
    }

    public boolean dragFloat(final String label, final imgui.type.ImFloat value, final float vSpeed, final float vMin, final float vMax, final String format) {
        return this.nDragFloat(label, value.getData(), vSpeed, vMin, vMax, format);
    }

    public boolean dragFloat(final String label, final float[] value, final float vSpeed, final float vMin, final float vMax, final String format) {
        return this.nDragFloat(label, value, vSpeed, vMin, vMax, format);
    }

    public boolean dragFloat(final String label, final imgui.type.ImFloat value, final float vSpeed, final float vMin, final float vMax, final int flags) {
        return this.nDragFloat(label, value.getData(), vSpeed, vMin, vMax, flags);
    }

    public boolean dragFloat(final String label, final imgui.type.ImFloat value, final float vSpeed, final float vMin, final float vMax, final String format, final int flags) {
        return this.nDragFloat(label, value.getData(), vSpeed, vMin, vMax, format, flags);
    }

    public boolean dragFloat(final String label, final float[] value, final float vSpeed, final float vMin, final float vMax, final int flags) {
        return this.nDragFloat(label, value, vSpeed, vMin, vMax, flags);
    }

    public boolean dragFloat(final String label, final float[] value, final float vSpeed, final float vMin, final float vMax, final String format, final int flags) {
        return this.nDragFloat(label, value, vSpeed, vMin, vMax, format, flags);
    }

    public boolean dragFloat2(final String label, final float[] value) {
        return this.nDragFloat2(label, value);
    }

    public boolean dragFloat2(final String label, final float[] value, final float vSpeed) {
        return this.nDragFloat2(label, value, vSpeed);
    }

    public boolean dragFloat2(final String label, final float[] value, final float vSpeed, final float vMin) {
        return this.nDragFloat2(label, value, vSpeed, vMin);
    }

    public boolean dragFloat2(final String label, final float[] value, final float vSpeed, final float vMin, final float vMax) {
        return this.nDragFloat2(label, value, vSpeed, vMin, vMax);
    }

    public boolean dragFloat2(final String label, final float[] value, final float vSpeed, final float vMin, final float vMax, final String format) {
        return this.nDragFloat2(label, value, vSpeed, vMin, vMax, format);
    }

    public boolean dragFloat2(final String label, final float[] value, final float vSpeed, final float vMin, final float vMax, final int flags) {
        return this.nDragFloat2(label, value, vSpeed, vMin, vMax, flags);
    }

    public boolean dragFloat2(final String label, final float[] value, final float vSpeed, final float vMin, final float vMax, final String format, final int flags) {
        return this.nDragFloat2(label, value, vSpeed, vMin, vMax, format, flags);
    }

    public boolean dragFloat3(final String label, final float[] value) {
        return this.nDragFloat3(label, value);
    }

    public boolean dragFloat3(final String label, final float[] value, final float vSpeed) {
        return this.nDragFloat3(label, value, vSpeed);
    }

    public boolean dragFloat3(final String label, final float[] value, final float vSpeed, final float vMin) {
        return this.nDragFloat3(label, value, vSpeed, vMin);
    }

    public boolean dragFloat3(final String label, final float[] value, final float vSpeed, final float vMin, final float vMax) {
        return this.nDragFloat3(label, value, vSpeed, vMin, vMax);
    }

    public boolean dragFloat3(final String label, final float[] value, final float vSpeed, final float vMin, final float vMax, final String format) {
        return this.nDragFloat3(label, value, vSpeed, vMin, vMax, format);
    }

    public boolean dragFloat3(final String label, final float[] value, final float vSpeed, final float vMin, final float vMax, final int flags) {
        return this.nDragFloat3(label, value, vSpeed, vMin, vMax, flags);
    }

    public boolean dragFloat3(final String label, final float[] value, final float vSpeed, final float vMin, final float vMax, final String format, final int flags) {
        return this.nDragFloat3(label, value, vSpeed, vMin, vMax, format, flags);
    }

    public boolean dragFloat4(final String label, final float[] value) {
        return this.nDragFloat4(label, value);
    }

    public boolean dragFloat4(final String label, final float[] value, final float vSpeed) {
        return this.nDragFloat4(label, value, vSpeed);
    }

    public boolean dragFloat4(final String label, final float[] value, final float vSpeed, final float vMin) {
        return this.nDragFloat4(label, value, vSpeed, vMin);
    }

    public boolean dragFloat4(final String label, final float[] value, final float vSpeed, final float vMin, final float vMax) {
        return this.nDragFloat4(label, value, vSpeed, vMin, vMax);
    }

    public boolean dragFloat4(final String label, final float[] value, final float vSpeed, final float vMin, final float vMax, final String format) {
        return this.nDragFloat4(label, value, vSpeed, vMin, vMax, format);
    }

    public boolean dragFloat4(final String label, final float[] value, final float vSpeed, final float vMin, final float vMax, final int flags) {
        return this.nDragFloat4(label, value, vSpeed, vMin, vMax, flags);
    }

    public boolean dragFloat4(final String label, final float[] value, final float vSpeed, final float vMin, final float vMax, final String format, final int flags) {
        return this.nDragFloat4(label, value, vSpeed, vMin, vMax, format, flags);
    }

    public boolean dragFloatRange2(final String label, final imgui.type.ImFloat vCurrentMin, final imgui.type.ImFloat vCurrentMax) {
        return this.nDragFloatRange2(label, vCurrentMin.getData(), vCurrentMax.getData());
    }

    public boolean dragFloatRange2(final String label, final float[] vCurrentMin, final float[] vCurrentMax) {
        return this.nDragFloatRange2(label, vCurrentMin, vCurrentMax);
    }

    public boolean dragFloatRange2(final String label, final imgui.type.ImFloat vCurrentMin, final imgui.type.ImFloat vCurrentMax, final float vSpeed) {
        return this.nDragFloatRange2(label, vCurrentMin.getData(), vCurrentMax.getData(), vSpeed);
    }

    public boolean dragFloatRange2(final String label, final float[] vCurrentMin, final float[] vCurrentMax, final float vSpeed) {
        return this.nDragFloatRange2(label, vCurrentMin, vCurrentMax, vSpeed);
    }

    public boolean dragFloatRange2(final String label, final imgui.type.ImFloat vCurrentMin, final imgui.type.ImFloat vCurrentMax, final float vSpeed, final float vMin) {
        return this.nDragFloatRange2(label, vCurrentMin.getData(), vCurrentMax.getData(), vSpeed, vMin);
    }

    public boolean dragFloatRange2(final String label, final float[] vCurrentMin, final float[] vCurrentMax, final float vSpeed, final float vMin) {
        return this.nDragFloatRange2(label, vCurrentMin, vCurrentMax, vSpeed, vMin);
    }

    public boolean dragFloatRange2(final String label, final imgui.type.ImFloat vCurrentMin, final imgui.type.ImFloat vCurrentMax, final float vSpeed, final float vMin, final float vMax) {
        return this.nDragFloatRange2(label, vCurrentMin.getData(), vCurrentMax.getData(), vSpeed, vMin, vMax);
    }

    public boolean dragFloatRange2(final String label, final float[] vCurrentMin, final float[] vCurrentMax, final float vSpeed, final float vMin, final float vMax) {
        return this.nDragFloatRange2(label, vCurrentMin, vCurrentMax, vSpeed, vMin, vMax);
    }

    public boolean dragFloatRange2(final String label, final imgui.type.ImFloat vCurrentMin, final imgui.type.ImFloat vCurrentMax, final float vSpeed, final float vMin, final float vMax, final String format) {
        return this.nDragFloatRange2(label, vCurrentMin.getData(), vCurrentMax.getData(), vSpeed, vMin, vMax, format);
    }

    public boolean dragFloatRange2(final String label, final float[] vCurrentMin, final float[] vCurrentMax, final float vSpeed, final float vMin, final float vMax, final String format) {
        return this.nDragFloatRange2(label, vCurrentMin, vCurrentMax, vSpeed, vMin, vMax, format);
    }

    public boolean dragFloatRange2(final String label, final imgui.type.ImFloat vCurrentMin, final imgui.type.ImFloat vCurrentMax, final float vSpeed, final float vMin, final float vMax, final String format, final String formatMax) {
        return this.nDragFloatRange2(label, vCurrentMin.getData(), vCurrentMax.getData(), vSpeed, vMin, vMax, format, formatMax);
    }

    public boolean dragFloatRange2(final String label, final float[] vCurrentMin, final float[] vCurrentMax, final float vSpeed, final float vMin, final float vMax, final String format, final String formatMax) {
        return this.nDragFloatRange2(label, vCurrentMin, vCurrentMax, vSpeed, vMin, vMax, format, formatMax);
    }

    public boolean dragFloatRange2(final String label, final imgui.type.ImFloat vCurrentMin, final imgui.type.ImFloat vCurrentMax, final float vSpeed, final float vMin, final float vMax, final String format, final String formatMax, final int flags) {
        return this.nDragFloatRange2(label, vCurrentMin.getData(), vCurrentMax.getData(), vSpeed, vMin, vMax, format, formatMax, flags);
    }

    public boolean dragFloatRange2(final String label, final float[] vCurrentMin, final float[] vCurrentMax, final float vSpeed, final float vMin, final float vMax, final String format, final String formatMax, final int flags) {
        return this.nDragFloatRange2(label, vCurrentMin, vCurrentMax, vSpeed, vMin, vMax, format, formatMax, flags);
    }

    public boolean dragInt(final String label, final imgui.type.ImInt value) {
        return this.nDragInt(label, value.getData());
    }

    public boolean dragInt(final String label, final int[] value) {
        return this.nDragInt(label, value);
    }

    public boolean dragInt(final String label, final imgui.type.ImInt value, final float vSpeed) {
        return this.nDragInt(label, value.getData(), vSpeed);
    }

    public boolean dragInt(final String label, final int[] value, final float vSpeed) {
        return this.nDragInt(label, value, vSpeed);
    }

    public boolean dragInt(final String label, final imgui.type.ImInt value, final float vSpeed, final int vMin) {
        return this.nDragInt(label, value.getData(), vSpeed, vMin);
    }

    public boolean dragInt(final String label, final int[] value, final float vSpeed, final int vMin) {
        return this.nDragInt(label, value, vSpeed, vMin);
    }

    public boolean dragInt(final String label, final imgui.type.ImInt value, final float vSpeed, final int vMin, final int vMax) {
        return this.nDragInt(label, value.getData(), vSpeed, vMin, vMax);
    }

    public boolean dragInt(final String label, final int[] value, final float vSpeed, final int vMin, final int vMax) {
        return this.nDragInt(label, value, vSpeed, vMin, vMax);
    }

    public boolean dragInt(final String label, final imgui.type.ImInt value, final float vSpeed, final int vMin, final int vMax, final String format) {
        return this.nDragInt(label, value.getData(), vSpeed, vMin, vMax, format);
    }

    public boolean dragInt(final String label, final int[] value, final float vSpeed, final int vMin, final int vMax, final String format) {
        return this.nDragInt(label, value, vSpeed, vMin, vMax, format);
    }

    public boolean dragInt(final String label, final imgui.type.ImInt value, final float vSpeed, final int vMin, final int vMax, final int flags) {
        return this.nDragInt(label, value.getData(), vSpeed, vMin, vMax, flags);
    }

    public boolean dragInt(final String label, final imgui.type.ImInt value, final float vSpeed, final int vMin, final int vMax, final String format, final int flags) {
        return this.nDragInt(label, value.getData(), vSpeed, vMin, vMax, format, flags);
    }

    public boolean dragInt(final String label, final int[] value, final float vSpeed, final int vMin, final int vMax, final int flags) {
        return this.nDragInt(label, value, vSpeed, vMin, vMax, flags);
    }

    public boolean dragInt(final String label, final int[] value, final float vSpeed, final int vMin, final int vMax, final String format, final int flags) {
        return this.nDragInt(label, value, vSpeed, vMin, vMax, format, flags);
    }

    public boolean dragInt2(final String label, final int[] value) {
        return this.nDragInt2(label, value);
    }

    public boolean dragInt2(final String label, final int[] value, final float vSpeed) {
        return this.nDragInt2(label, value, vSpeed);
    }

    public boolean dragInt2(final String label, final int[] value, final float vSpeed, final int vMin) {
        return this.nDragInt2(label, value, vSpeed, vMin);
    }

    public boolean dragInt2(final String label, final int[] value, final float vSpeed, final int vMin, final int vMax) {
        return this.nDragInt2(label, value, vSpeed, vMin, vMax);
    }

    public boolean dragInt2(final String label, final int[] value, final float vSpeed, final int vMin, final int vMax, final String format) {
        return this.nDragInt2(label, value, vSpeed, vMin, vMax, format);
    }

    public boolean dragInt2(final String label, final int[] value, final float vSpeed, final int vMin, final int vMax, final int flags) {
        return this.nDragInt2(label, value, vSpeed, vMin, vMax, flags);
    }

    public boolean dragInt2(final String label, final int[] value, final float vSpeed, final int vMin, final int vMax, final String format, final int flags) {
        return this.nDragInt2(label, value, vSpeed, vMin, vMax, format, flags);
    }

    public boolean dragInt3(final String label, final int[] value) {
        return this.nDragInt3(label, value);
    }

    public boolean dragInt3(final String label, final int[] value, final float vSpeed) {
        return this.nDragInt3(label, value, vSpeed);
    }

    public boolean dragInt3(final String label, final int[] value, final float vSpeed, final int vMin) {
        return this.nDragInt3(label, value, vSpeed, vMin);
    }

    public boolean dragInt3(final String label, final int[] value, final float vSpeed, final int vMin, final int vMax) {
        return this.nDragInt3(label, value, vSpeed, vMin, vMax);
    }

    public boolean dragInt3(final String label, final int[] value, final float vSpeed, final int vMin, final int vMax, final String format) {
        return this.nDragInt3(label, value, vSpeed, vMin, vMax, format);
    }

    public boolean dragInt3(final String label, final int[] value, final float vSpeed, final int vMin, final int vMax, final int flags) {
        return this.nDragInt3(label, value, vSpeed, vMin, vMax, flags);
    }

    public boolean dragInt3(final String label, final int[] value, final float vSpeed, final int vMin, final int vMax, final String format, final int flags) {
        return this.nDragInt3(label, value, vSpeed, vMin, vMax, format, flags);
    }

    public boolean dragInt4(final String label, final int[] value) {
        return this.nDragInt4(label, value);
    }

    public boolean dragInt4(final String label, final int[] value, final float vSpeed) {
        return this.nDragInt4(label, value, vSpeed);
    }

    public boolean dragInt4(final String label, final int[] value, final float vSpeed, final int vMin) {
        return this.nDragInt4(label, value, vSpeed, vMin);
    }

    public boolean dragInt4(final String label, final int[] value, final float vSpeed, final int vMin, final int vMax) {
        return this.nDragInt4(label, value, vSpeed, vMin, vMax);
    }

    public boolean dragInt4(final String label, final int[] value, final float vSpeed, final int vMin, final int vMax, final String format) {
        return this.nDragInt4(label, value, vSpeed, vMin, vMax, format);
    }

    public boolean dragInt4(final String label, final int[] value, final float vSpeed, final int vMin, final int vMax, final int flags) {
        return this.nDragInt4(label, value, vSpeed, vMin, vMax, flags);
    }

    public boolean dragInt4(final String label, final int[] value, final float vSpeed, final int vMin, final int vMax, final String format, final int flags) {
        return this.nDragInt4(label, value, vSpeed, vMin, vMax, format, flags);
    }

    public boolean dragIntRange2(final String label, final imgui.type.ImInt vCurrentMin, final imgui.type.ImInt vCurrentMax) {
        return this.nDragIntRange2(label, vCurrentMin.getData(), vCurrentMax.getData());
    }

    public boolean dragIntRange2(final String label, final int[] vCurrentMin, final int[] vCurrentMax) {
        return this.nDragIntRange2(label, vCurrentMin, vCurrentMax);
    }

    public boolean dragIntRange2(final String label, final imgui.type.ImInt vCurrentMin, final imgui.type.ImInt vCurrentMax, final float vSpeed) {
        return this.nDragIntRange2(label, vCurrentMin.getData(), vCurrentMax.getData(), vSpeed);
    }

    public boolean dragIntRange2(final String label, final int[] vCurrentMin, final int[] vCurrentMax, final float vSpeed) {
        return this.nDragIntRange2(label, vCurrentMin, vCurrentMax, vSpeed);
    }

    public boolean dragIntRange2(final String label, final imgui.type.ImInt vCurrentMin, final imgui.type.ImInt vCurrentMax, final float vSpeed, final int vMin) {
        return this.nDragIntRange2(label, vCurrentMin.getData(), vCurrentMax.getData(), vSpeed, vMin);
    }

    public boolean dragIntRange2(final String label, final int[] vCurrentMin, final int[] vCurrentMax, final float vSpeed, final int vMin) {
        return this.nDragIntRange2(label, vCurrentMin, vCurrentMax, vSpeed, vMin);
    }

    public boolean dragIntRange2(final String label, final imgui.type.ImInt vCurrentMin, final imgui.type.ImInt vCurrentMax, final float vSpeed, final int vMin, final int vMax) {
        return this.nDragIntRange2(label, vCurrentMin.getData(), vCurrentMax.getData(), vSpeed, vMin, vMax);
    }

    public boolean dragIntRange2(final String label, final int[] vCurrentMin, final int[] vCurrentMax, final float vSpeed, final int vMin, final int vMax) {
        return this.nDragIntRange2(label, vCurrentMin, vCurrentMax, vSpeed, vMin, vMax);
    }

    public boolean dragIntRange2(final String label, final imgui.type.ImInt vCurrentMin, final imgui.type.ImInt vCurrentMax, final float vSpeed, final int vMin, final int vMax, final String format) {
        return this.nDragIntRange2(label, vCurrentMin.getData(), vCurrentMax.getData(), vSpeed, vMin, vMax, format);
    }

    public boolean dragIntRange2(final String label, final int[] vCurrentMin, final int[] vCurrentMax, final float vSpeed, final int vMin, final int vMax, final String format) {
        return this.nDragIntRange2(label, vCurrentMin, vCurrentMax, vSpeed, vMin, vMax, format);
    }

    public boolean dragIntRange2(final String label, final imgui.type.ImInt vCurrentMin, final imgui.type.ImInt vCurrentMax, final float vSpeed, final int vMin, final int vMax, final String format, final String formatMax) {
        return this.nDragIntRange2(label, vCurrentMin.getData(), vCurrentMax.getData(), vSpeed, vMin, vMax, format, formatMax);
    }

    public boolean dragIntRange2(final String label, final int[] vCurrentMin, final int[] vCurrentMax, final float vSpeed, final int vMin, final int vMax, final String format, final String formatMax) {
        return this.nDragIntRange2(label, vCurrentMin, vCurrentMax, vSpeed, vMin, vMax, format, formatMax);
    }

    public boolean dragIntRange2(final String label, final imgui.type.ImInt vCurrentMin, final imgui.type.ImInt vCurrentMax, final float vSpeed, final int vMin, final int vMax, final String format, final String formatMax, final int flags) {
        return this.nDragIntRange2(label, vCurrentMin.getData(), vCurrentMax.getData(), vSpeed, vMin, vMax, format, formatMax, flags);
    }

    public boolean dragIntRange2(final String label, final int[] vCurrentMin, final int[] vCurrentMax, final float vSpeed, final int vMin, final int vMax, final String format, final String formatMax, final int flags) {
        return this.nDragIntRange2(label, vCurrentMin, vCurrentMax, vSpeed, vMin, vMax, format, formatMax, flags);
    }

    public boolean dragScalar(final String label, final imgui.type.ImFloat pData) {
        return this.nDragScalar(label, pData.getData());
    }

    public boolean dragScalar(final String label, final float[] pData) {
        return this.nDragScalar(label, pData);
    }

    public boolean dragScalar(final String label, final imgui.type.ImFloat pData, final float vSpeed) {
        return this.nDragScalar(label, pData.getData(), vSpeed);
    }

    public boolean dragScalar(final String label, final float[] pData, final float vSpeed) {
        return this.nDragScalar(label, pData, vSpeed);
    }

    public boolean dragScalar(final String label, final imgui.type.ImFloat pData, final float vSpeed, final float pMin) {
        return this.nDragScalar(label, pData.getData(), vSpeed, pMin);
    }

    public boolean dragScalar(final String label, final float[] pData, final float vSpeed, final float pMin) {
        return this.nDragScalar(label, pData, vSpeed, pMin);
    }

    public boolean dragScalar(final String label, final imgui.type.ImFloat pData, final float vSpeed, final float pMin, final float pMax) {
        return this.nDragScalar(label, pData.getData(), vSpeed, pMin, pMax);
    }

    public boolean dragScalar(final String label, final float[] pData, final float vSpeed, final float pMin, final float pMax) {
        return this.nDragScalar(label, pData, vSpeed, pMin, pMax);
    }

    public boolean dragScalar(final String label, final imgui.type.ImFloat pData, final float vSpeed, final float pMin, final float pMax, final String format) {
        return this.nDragScalar(label, pData.getData(), vSpeed, pMin, pMax, format);
    }

    public boolean dragScalar(final String label, final float[] pData, final float vSpeed, final float pMin, final float pMax, final String format) {
        return this.nDragScalar(label, pData, vSpeed, pMin, pMax, format);
    }

    public boolean dragScalar(final String label, final imgui.type.ImFloat pData, final float vSpeed, final float pMin, final float pMax, final String format, final int flags) {
        return this.nDragScalar(label, pData.getData(), vSpeed, pMin, pMax, format, flags);
    }

    public boolean dragScalar(final String label, final float[] pData, final float vSpeed, final float pMin, final float pMax, final String format, final int flags) {
        return this.nDragScalar(label, pData, vSpeed, pMin, pMax, format, flags);
    }

    public boolean dragScalar(final String label, final imgui.type.ImShort pData) {
        return this.nDragScalar(label, pData.getData());
    }

    public boolean dragScalar(final String label, final short[] pData) {
        return this.nDragScalar(label, pData);
    }

    public boolean dragScalar(final String label, final imgui.type.ImShort pData, final float vSpeed) {
        return this.nDragScalar(label, pData.getData(), vSpeed);
    }

    public boolean dragScalar(final String label, final short[] pData, final float vSpeed) {
        return this.nDragScalar(label, pData, vSpeed);
    }

    public boolean dragScalar(final String label, final imgui.type.ImShort pData, final float vSpeed, final short pMin) {
        return this.nDragScalar(label, pData.getData(), vSpeed, pMin);
    }

    public boolean dragScalar(final String label, final short[] pData, final float vSpeed, final short pMin) {
        return this.nDragScalar(label, pData, vSpeed, pMin);
    }

    public boolean dragScalar(final String label, final imgui.type.ImShort pData, final float vSpeed, final short pMin, final short pMax) {
        return this.nDragScalar(label, pData.getData(), vSpeed, pMin, pMax);
    }

    public boolean dragScalar(final String label, final short[] pData, final float vSpeed, final short pMin, final short pMax) {
        return this.nDragScalar(label, pData, vSpeed, pMin, pMax);
    }

    public boolean dragScalar(final String label, final imgui.type.ImShort pData, final float vSpeed, final short pMin, final short pMax, final String format) {
        return this.nDragScalar(label, pData.getData(), vSpeed, pMin, pMax, format);
    }

    public boolean dragScalar(final String label, final short[] pData, final float vSpeed, final short pMin, final short pMax, final String format) {
        return this.nDragScalar(label, pData, vSpeed, pMin, pMax, format);
    }

    public boolean dragScalar(final String label, final imgui.type.ImShort pData, final float vSpeed, final short pMin, final short pMax, final String format, final int flags) {
        return this.nDragScalar(label, pData.getData(), vSpeed, pMin, pMax, format, flags);
    }

    public boolean dragScalar(final String label, final short[] pData, final float vSpeed, final short pMin, final short pMax, final String format, final int flags) {
        return this.nDragScalar(label, pData, vSpeed, pMin, pMax, format, flags);
    }

    public boolean dragScalar(final String label, final imgui.type.ImInt pData) {
        return this.nDragScalar(label, pData.getData());
    }

    public boolean dragScalar(final String label, final int[] pData) {
        return this.nDragScalar(label, pData);
    }

    public boolean dragScalar(final String label, final imgui.type.ImInt pData, final float vSpeed) {
        return this.nDragScalar(label, pData.getData(), vSpeed);
    }

    public boolean dragScalar(final String label, final int[] pData, final float vSpeed) {
        return this.nDragScalar(label, pData, vSpeed);
    }

    public boolean dragScalar(final String label, final imgui.type.ImInt pData, final float vSpeed, final int pMin) {
        return this.nDragScalar(label, pData.getData(), vSpeed, pMin);
    }

    public boolean dragScalar(final String label, final int[] pData, final float vSpeed, final int pMin) {
        return this.nDragScalar(label, pData, vSpeed, pMin);
    }

    public boolean dragScalar(final String label, final imgui.type.ImInt pData, final float vSpeed, final int pMin, final int pMax) {
        return this.nDragScalar(label, pData.getData(), vSpeed, pMin, pMax);
    }

    public boolean dragScalar(final String label, final int[] pData, final float vSpeed, final int pMin, final int pMax) {
        return this.nDragScalar(label, pData, vSpeed, pMin, pMax);
    }

    public boolean dragScalar(final String label, final imgui.type.ImInt pData, final float vSpeed, final int pMin, final int pMax, final String format) {
        return this.nDragScalar(label, pData.getData(), vSpeed, pMin, pMax, format);
    }

    public boolean dragScalar(final String label, final int[] pData, final float vSpeed, final int pMin, final int pMax, final String format) {
        return this.nDragScalar(label, pData, vSpeed, pMin, pMax, format);
    }

    public boolean dragScalar(final String label, final imgui.type.ImInt pData, final float vSpeed, final int pMin, final int pMax, final String format, final int flags) {
        return this.nDragScalar(label, pData.getData(), vSpeed, pMin, pMax, format, flags);
    }

    public boolean dragScalar(final String label, final int[] pData, final float vSpeed, final int pMin, final int pMax, final String format, final int flags) {
        return this.nDragScalar(label, pData, vSpeed, pMin, pMax, format, flags);
    }

    public boolean dragScalar(final String label, final imgui.type.ImLong pData) {
        return this.nDragScalar(label, pData.getData());
    }

    public boolean dragScalar(final String label, final long[] pData) {
        return this.nDragScalar(label, pData);
    }

    public boolean dragScalar(final String label, final imgui.type.ImLong pData, final float vSpeed) {
        return this.nDragScalar(label, pData.getData(), vSpeed);
    }

    public boolean dragScalar(final String label, final long[] pData, final float vSpeed) {
        return this.nDragScalar(label, pData, vSpeed);
    }

    public boolean dragScalar(final String label, final imgui.type.ImLong pData, final float vSpeed, final long pMin) {
        return this.nDragScalar(label, pData.getData(), vSpeed, pMin);
    }

    public boolean dragScalar(final String label, final long[] pData, final float vSpeed, final long pMin) {
        return this.nDragScalar(label, pData, vSpeed, pMin);
    }

    public boolean dragScalar(final String label, final imgui.type.ImLong pData, final float vSpeed, final long pMin, final long pMax) {
        return this.nDragScalar(label, pData.getData(), vSpeed, pMin, pMax);
    }

    public boolean dragScalar(final String label, final long[] pData, final float vSpeed, final long pMin, final long pMax) {
        return this.nDragScalar(label, pData, vSpeed, pMin, pMax);
    }

    public boolean dragScalar(final String label, final imgui.type.ImLong pData, final float vSpeed, final long pMin, final long pMax, final String format) {
        return this.nDragScalar(label, pData.getData(), vSpeed, pMin, pMax, format);
    }

    public boolean dragScalar(final String label, final long[] pData, final float vSpeed, final long pMin, final long pMax, final String format) {
        return this.nDragScalar(label, pData, vSpeed, pMin, pMax, format);
    }

    public boolean dragScalar(final String label, final imgui.type.ImLong pData, final float vSpeed, final long pMin, final long pMax, final String format, final int flags) {
        return this.nDragScalar(label, pData.getData(), vSpeed, pMin, pMax, format, flags);
    }

    public boolean dragScalar(final String label, final long[] pData, final float vSpeed, final long pMin, final long pMax, final String format, final int flags) {
        return this.nDragScalar(label, pData, vSpeed, pMin, pMax, format, flags);
    }

    public boolean sliderFloat(final String label, final imgui.type.ImFloat value, final float vMin, final float vMax) {
        return this.nSliderFloat(label, value.getData(), vMin, vMax);
    }

    public boolean sliderFloat(final String label, final float[] value, final float vMin, final float vMax) {
        return this.nSliderFloat(label, value, vMin, vMax);
    }

    public boolean sliderFloat(final String label, final imgui.type.ImFloat value, final float vMin, final float vMax, final String format) {
        return this.nSliderFloat(label, value.getData(), vMin, vMax, format);
    }

    public boolean sliderFloat(final String label, final float[] value, final float vMin, final float vMax, final String format) {
        return this.nSliderFloat(label, value, vMin, vMax, format);
    }

    public boolean sliderFloat(final String label, final imgui.type.ImFloat value, final float vMin, final float vMax, final int flags) {
        return this.nSliderFloat(label, value.getData(), vMin, vMax, flags);
    }

    public boolean sliderFloat(final String label, final imgui.type.ImFloat value, final float vMin, final float vMax, final String format, final int flags) {
        return this.nSliderFloat(label, value.getData(), vMin, vMax, format, flags);
    }

    public boolean sliderFloat(final String label, final float[] value, final float vMin, final float vMax, final int flags) {
        return this.nSliderFloat(label, value, vMin, vMax, flags);
    }

    public boolean sliderFloat(final String label, final float[] value, final float vMin, final float vMax, final String format, final int flags) {
        return this.nSliderFloat(label, value, vMin, vMax, format, flags);
    }

    public boolean sliderFloat2(final String label, final float[] value, final float vMin, final float vMax) {
        return this.nSliderFloat2(label, value, vMin, vMax);
    }

    public boolean sliderFloat2(final String label, final float[] value, final float vMin, final float vMax, final String format) {
        return this.nSliderFloat2(label, value, vMin, vMax, format);
    }

    public boolean sliderFloat2(final String label, final float[] value, final float vMin, final float vMax, final int flags) {
        return this.nSliderFloat2(label, value, vMin, vMax, flags);
    }

    public boolean sliderFloat2(final String label, final float[] value, final float vMin, final float vMax, final String format, final int flags) {
        return this.nSliderFloat2(label, value, vMin, vMax, format, flags);
    }

    public boolean sliderFloat3(final String label, final float[] value, final float vMin, final float vMax) {
        return this.nSliderFloat3(label, value, vMin, vMax);
    }

    public boolean sliderFloat3(final String label, final float[] value, final float vMin, final float vMax, final String format) {
        return this.nSliderFloat3(label, value, vMin, vMax, format);
    }

    public boolean sliderFloat3(final String label, final float[] value, final float vMin, final float vMax, final int flags) {
        return this.nSliderFloat3(label, value, vMin, vMax, flags);
    }

    public boolean sliderFloat3(final String label, final float[] value, final float vMin, final float vMax, final String format, final int flags) {
        return this.nSliderFloat3(label, value, vMin, vMax, format, flags);
    }

    public boolean sliderFloat4(final String label, final float[] value, final float vMin, final float vMax) {
        return this.nSliderFloat4(label, value, vMin, vMax);
    }

    public boolean sliderFloat4(final String label, final float[] value, final float vMin, final float vMax, final String format) {
        return this.nSliderFloat4(label, value, vMin, vMax, format);
    }

    public boolean sliderFloat4(final String label, final float[] value, final float vMin, final float vMax, final int flags) {
        return this.nSliderFloat4(label, value, vMin, vMax, flags);
    }

    public boolean sliderFloat4(final String label, final float[] value, final float vMin, final float vMax, final String format, final int flags) {
        return this.nSliderFloat4(label, value, vMin, vMax, format, flags);
    }

    public boolean sliderAngle(final String label, final imgui.type.ImFloat vRad, final float vDegreesMin, final float vDegreesMax) {
        return this.nSliderAngle(label, vRad.getData(), vDegreesMin, vDegreesMax);
    }

    public boolean sliderAngle(final String label, final float[] vRad, final float vDegreesMin, final float vDegreesMax) {
        return this.nSliderAngle(label, vRad, vDegreesMin, vDegreesMax);
    }

    public boolean sliderAngle(final String label, final imgui.type.ImFloat vRad, final float vDegreesMin, final float vDegreesMax, final String format) {
        return this.nSliderAngle(label, vRad.getData(), vDegreesMin, vDegreesMax, format);
    }

    public boolean sliderAngle(final String label, final float[] vRad, final float vDegreesMin, final float vDegreesMax, final String format) {
        return this.nSliderAngle(label, vRad, vDegreesMin, vDegreesMax, format);
    }

    public boolean sliderAngle(final String label, final imgui.type.ImFloat vRad, final float vDegreesMin, final float vDegreesMax, final int flags) {
        return this.nSliderAngle(label, vRad.getData(), vDegreesMin, vDegreesMax, flags);
    }

    public boolean sliderAngle(final String label, final imgui.type.ImFloat vRad, final float vDegreesMin, final float vDegreesMax, final String format, final int flags) {
        return this.nSliderAngle(label, vRad.getData(), vDegreesMin, vDegreesMax, format, flags);
    }

    public boolean sliderAngle(final String label, final float[] vRad, final float vDegreesMin, final float vDegreesMax, final int flags) {
        return this.nSliderAngle(label, vRad, vDegreesMin, vDegreesMax, flags);
    }

    public boolean sliderAngle(final String label, final float[] vRad, final float vDegreesMin, final float vDegreesMax, final String format, final int flags) {
        return this.nSliderAngle(label, vRad, vDegreesMin, vDegreesMax, format, flags);
    }

    public boolean sliderInt(final String label, final imgui.type.ImInt value, final int vMin, final int vMax) {
        return this.nSliderInt(label, value.getData(), vMin, vMax);
    }

    public boolean sliderInt(final String label, final int[] value, final int vMin, final int vMax) {
        return this.nSliderInt(label, value, vMin, vMax);
    }

    public boolean sliderInt(final String label, final imgui.type.ImInt value, final int vMin, final int vMax, final String format) {
        return this.nSliderInt(label, value.getData(), vMin, vMax, format);
    }

    public boolean sliderInt(final String label, final int[] value, final int vMin, final int vMax, final String format) {
        return this.nSliderInt(label, value, vMin, vMax, format);
    }

    public boolean sliderInt(final String label, final imgui.type.ImInt value, final int vMin, final int vMax, final int flags) {
        return this.nSliderInt(label, value.getData(), vMin, vMax, flags);
    }

    public boolean sliderInt(final String label, final imgui.type.ImInt value, final int vMin, final int vMax, final String format, final int flags) {
        return this.nSliderInt(label, value.getData(), vMin, vMax, format, flags);
    }

    public boolean sliderInt(final String label, final int[] value, final int vMin, final int vMax, final int flags) {
        return this.nSliderInt(label, value, vMin, vMax, flags);
    }

    public boolean sliderInt(final String label, final int[] value, final int vMin, final int vMax, final String format, final int flags) {
        return this.nSliderInt(label, value, vMin, vMax, format, flags);
    }

    public boolean sliderInt2(final String label, final int[] value, final int vMin, final int vMax) {
        return this.nSliderInt2(label, value, vMin, vMax);
    }

    public boolean sliderInt2(final String label, final int[] value, final int vMin, final int vMax, final String format) {
        return this.nSliderInt2(label, value, vMin, vMax, format);
    }

    public boolean sliderInt2(final String label, final int[] value, final int vMin, final int vMax, final int flags) {
        return this.nSliderInt2(label, value, vMin, vMax, flags);
    }

    public boolean sliderInt2(final String label, final int[] value, final int vMin, final int vMax, final String format, final int flags) {
        return this.nSliderInt2(label, value, vMin, vMax, format, flags);
    }

    public boolean sliderInt3(final String label, final int[] value, final int vMin, final int vMax) {
        return this.nSliderInt3(label, value, vMin, vMax);
    }

    public boolean sliderInt3(final String label, final int[] value, final int vMin, final int vMax, final String format) {
        return this.nSliderInt3(label, value, vMin, vMax, format);
    }

    public boolean sliderInt3(final String label, final int[] value, final int vMin, final int vMax, final int flags) {
        return this.nSliderInt3(label, value, vMin, vMax, flags);
    }

    public boolean sliderInt3(final String label, final int[] value, final int vMin, final int vMax, final String format, final int flags) {
        return this.nSliderInt3(label, value, vMin, vMax, format, flags);
    }

    public boolean sliderInt4(final String label, final int[] value, final int vMin, final int vMax) {
        return this.nSliderInt4(label, value, vMin, vMax);
    }

    public boolean sliderInt4(final String label, final int[] value, final int vMin, final int vMax, final String format) {
        return this.nSliderInt4(label, value, vMin, vMax, format);
    }

    public boolean sliderInt4(final String label, final int[] value, final int vMin, final int vMax, final int flags) {
        return this.nSliderInt4(label, value, vMin, vMax, flags);
    }

    public boolean sliderInt4(final String label, final int[] value, final int vMin, final int vMax, final String format, final int flags) {
        return this.nSliderInt4(label, value, vMin, vMax, format, flags);
    }

    public boolean sliderScalar(final String label, final imgui.type.ImFloat pData, final float pMin, final float pMax) {
        return this.nSliderScalar(label, pData.getData(), pMin, pMax);
    }

    public boolean sliderScalar(final String label, final imgui.type.ImFloat pData, final float pMin, final float pMax, final String format) {
        return this.nSliderScalar(label, pData.getData(), pMin, pMax, format);
    }

    public boolean sliderScalar(final String label, final imgui.type.ImFloat pData, final float pMin, final float pMax, final String format, final int flags) {
        return this.nSliderScalar(label, pData.getData(), pMin, pMax, format, flags);
    }

    public boolean sliderScalar(final String label, final imgui.type.ImShort pData, final short pMin, final short pMax) {
        return this.nSliderScalar(label, pData.getData(), pMin, pMax);
    }

    public boolean sliderScalar(final String label, final imgui.type.ImShort pData, final short pMin, final short pMax, final String format) {
        return this.nSliderScalar(label, pData.getData(), pMin, pMax, format);
    }

    public boolean sliderScalar(final String label, final imgui.type.ImShort pData, final short pMin, final short pMax, final String format, final int flags) {
        return this.nSliderScalar(label, pData.getData(), pMin, pMax, format, flags);
    }

    public boolean sliderScalar(final String label, final imgui.type.ImInt pData, final int pMin, final int pMax) {
        return this.nSliderScalar(label, pData.getData(), pMin, pMax);
    }

    public boolean sliderScalar(final String label, final imgui.type.ImInt pData, final int pMin, final int pMax, final String format) {
        return this.nSliderScalar(label, pData.getData(), pMin, pMax, format);
    }

    public boolean sliderScalar(final String label, final imgui.type.ImInt pData, final int pMin, final int pMax, final String format, final int flags) {
        return this.nSliderScalar(label, pData.getData(), pMin, pMax, format, flags);
    }

    public boolean sliderScalar(final String label, final imgui.type.ImLong pData, final long pMin, final long pMax) {
        return this.nSliderScalar(label, pData.getData(), pMin, pMax);
    }

    public boolean sliderScalar(final String label, final imgui.type.ImLong pData, final long pMin, final long pMax, final String format) {
        return this.nSliderScalar(label, pData.getData(), pMin, pMax, format);
    }

    public boolean sliderScalar(final String label, final imgui.type.ImLong pData, final long pMin, final long pMax, final String format, final int flags) {
        return this.nSliderScalar(label, pData.getData(), pMin, pMax, format, flags);
    }

    public boolean vSliderFloat(final String label, final imgui.ImVec2 size, final imgui.type.ImFloat value, final float vMin, final float vMax) {
        return this.vSliderFloat(label, size.x, size.y, value, vMin, vMax);
    }

    public boolean vSliderFloat(final String label, final float sizeX, final float sizeY, final imgui.type.ImFloat value, final float vMin, final float vMax) {
        return this.nVSliderFloat(label, sizeX, sizeY, value.getData(), vMin, vMax);
    }

    public boolean vSliderFloat(final String label, final imgui.ImVec2 size, final imgui.type.ImFloat value, final float vMin, final float vMax, final String format) {
        return this.vSliderFloat(label, size.x, size.y, value, vMin, vMax, format);
    }

    public boolean vSliderFloat(final String label, final float sizeX, final float sizeY, final imgui.type.ImFloat value, final float vMin, final float vMax, final String format) {
        return this.nVSliderFloat(label, sizeX, sizeY, value.getData(), vMin, vMax, format);
    }

    public boolean vSliderFloat(final String label, final imgui.ImVec2 size, final imgui.type.ImFloat value, final float vMin, final float vMax, final int flags) {
        return this.vSliderFloat(label, size.x, size.y, value, vMin, vMax, flags);
    }

    public boolean vSliderFloat(final String label, final float sizeX, final float sizeY, final imgui.type.ImFloat value, final float vMin, final float vMax, final int flags) {
        return this.nVSliderFloat(label, sizeX, sizeY, value.getData(), vMin, vMax, flags);
    }

    public boolean vSliderFloat(final String label, final imgui.ImVec2 size, final imgui.type.ImFloat value, final float vMin, final float vMax, final String format, final int flags) {
        return this.vSliderFloat(label, size.x, size.y, value, vMin, vMax, format, flags);
    }

    public boolean vSliderFloat(final String label, final float sizeX, final float sizeY, final imgui.type.ImFloat value, final float vMin, final float vMax, final String format, final int flags) {
        return this.nVSliderFloat(label, sizeX, sizeY, value.getData(), vMin, vMax, format, flags);
    }

    public boolean vSliderInt(final String label, final imgui.ImVec2 size, final imgui.type.ImInt value, final int vMin, final int vMax) {
        return this.vSliderInt(label, size.x, size.y, value, vMin, vMax);
    }

    public boolean vSliderInt(final String label, final float sizeX, final float sizeY, final imgui.type.ImInt value, final int vMin, final int vMax) {
        return this.nVSliderInt(label, sizeX, sizeY, value.getData(), vMin, vMax);
    }

    public boolean vSliderInt(final String label, final imgui.ImVec2 size, final imgui.type.ImInt value, final int vMin, final int vMax, final String format) {
        return this.vSliderInt(label, size.x, size.y, value, vMin, vMax, format);
    }

    public boolean vSliderInt(final String label, final float sizeX, final float sizeY, final imgui.type.ImInt value, final int vMin, final int vMax, final String format) {
        return this.nVSliderInt(label, sizeX, sizeY, value.getData(), vMin, vMax, format);
    }

    public boolean vSliderInt(final String label, final imgui.ImVec2 size, final imgui.type.ImInt value, final int vMin, final int vMax, final int flags) {
        return this.vSliderInt(label, size.x, size.y, value, vMin, vMax, flags);
    }

    public boolean vSliderInt(final String label, final float sizeX, final float sizeY, final imgui.type.ImInt value, final int vMin, final int vMax, final int flags) {
        return this.nVSliderInt(label, sizeX, sizeY, value.getData(), vMin, vMax, flags);
    }

    public boolean vSliderInt(final String label, final imgui.ImVec2 size, final imgui.type.ImInt value, final int vMin, final int vMax, final String format, final int flags) {
        return this.vSliderInt(label, size.x, size.y, value, vMin, vMax, format, flags);
    }

    public boolean vSliderInt(final String label, final float sizeX, final float sizeY, final imgui.type.ImInt value, final int vMin, final int vMax, final String format, final int flags) {
        return this.nVSliderInt(label, sizeX, sizeY, value.getData(), vMin, vMax, format, flags);
    }

    public boolean vSliderScalar(final String label, final imgui.ImVec2 size, final imgui.type.ImFloat pData, final float pMin, final float pMax) {
        return this.vSliderScalar(label, size.x, size.y, pData, pMin, pMax);
    }

    public boolean vSliderScalar(final String label, final float sizeX, final float sizeY, final imgui.type.ImFloat pData, final float pMin, final float pMax) {
        return this.nVSliderScalar(label, sizeX, sizeY, pData.getData(), pMin, pMax);
    }

    public boolean vSliderScalar(final String label, final imgui.ImVec2 size, final imgui.type.ImFloat pData, final float pMin, final float pMax, final String format) {
        return this.vSliderScalar(label, size.x, size.y, pData, pMin, pMax, format);
    }

    public boolean vSliderScalar(final String label, final float sizeX, final float sizeY, final imgui.type.ImFloat pData, final float pMin, final float pMax, final String format) {
        return this.nVSliderScalar(label, sizeX, sizeY, pData.getData(), pMin, pMax, format);
    }

    public boolean vSliderScalar(final String label, final imgui.ImVec2 size, final imgui.type.ImFloat pData, final float pMin, final float pMax, final String format, final int flags) {
        return this.vSliderScalar(label, size.x, size.y, pData, pMin, pMax, format, flags);
    }

    public boolean vSliderScalar(final String label, final float sizeX, final float sizeY, final imgui.type.ImFloat pData, final float pMin, final float pMax, final String format, final int flags) {
        return this.nVSliderScalar(label, sizeX, sizeY, pData.getData(), pMin, pMax, format, flags);
    }

    public boolean vSliderScalar(final String label, final imgui.ImVec2 size, final imgui.type.ImShort pData, final short pMin, final short pMax) {
        return this.vSliderScalar(label, size.x, size.y, pData, pMin, pMax);
    }

    public boolean vSliderScalar(final String label, final float sizeX, final float sizeY, final imgui.type.ImShort pData, final short pMin, final short pMax) {
        return this.nVSliderScalar(label, sizeX, sizeY, pData.getData(), pMin, pMax);
    }

    public boolean vSliderScalar(final String label, final imgui.ImVec2 size, final imgui.type.ImShort pData, final short pMin, final short pMax, final String format) {
        return this.vSliderScalar(label, size.x, size.y, pData, pMin, pMax, format);
    }

    public boolean vSliderScalar(final String label, final float sizeX, final float sizeY, final imgui.type.ImShort pData, final short pMin, final short pMax, final String format) {
        return this.nVSliderScalar(label, sizeX, sizeY, pData.getData(), pMin, pMax, format);
    }

    public boolean vSliderScalar(final String label, final imgui.ImVec2 size, final imgui.type.ImShort pData, final short pMin, final short pMax, final String format, final int flags) {
        return this.vSliderScalar(label, size.x, size.y, pData, pMin, pMax, format, flags);
    }

    public boolean vSliderScalar(final String label, final float sizeX, final float sizeY, final imgui.type.ImShort pData, final short pMin, final short pMax, final String format, final int flags) {
        return this.nVSliderScalar(label, sizeX, sizeY, pData.getData(), pMin, pMax, format, flags);
    }

    public boolean vSliderScalar(final String label, final imgui.ImVec2 size, final imgui.type.ImInt pData, final int pMin, final int pMax) {
        return this.vSliderScalar(label, size.x, size.y, pData, pMin, pMax);
    }

    public boolean vSliderScalar(final String label, final float sizeX, final float sizeY, final imgui.type.ImInt pData, final int pMin, final int pMax) {
        return this.nVSliderScalar(label, sizeX, sizeY, pData.getData(), pMin, pMax);
    }

    public boolean vSliderScalar(final String label, final imgui.ImVec2 size, final imgui.type.ImInt pData, final int pMin, final int pMax, final String format) {
        return this.vSliderScalar(label, size.x, size.y, pData, pMin, pMax, format);
    }

    public boolean vSliderScalar(final String label, final float sizeX, final float sizeY, final imgui.type.ImInt pData, final int pMin, final int pMax, final String format) {
        return this.nVSliderScalar(label, sizeX, sizeY, pData.getData(), pMin, pMax, format);
    }

    public boolean vSliderScalar(final String label, final imgui.ImVec2 size, final imgui.type.ImInt pData, final int pMin, final int pMax, final String format, final int flags) {
        return this.vSliderScalar(label, size.x, size.y, pData, pMin, pMax, format, flags);
    }

    public boolean vSliderScalar(final String label, final float sizeX, final float sizeY, final imgui.type.ImInt pData, final int pMin, final int pMax, final String format, final int flags) {
        return this.nVSliderScalar(label, sizeX, sizeY, pData.getData(), pMin, pMax, format, flags);
    }

    public boolean vSliderScalar(final String label, final imgui.ImVec2 size, final imgui.type.ImLong pData, final long pMin, final long pMax) {
        return this.vSliderScalar(label, size.x, size.y, pData, pMin, pMax);
    }

    public boolean vSliderScalar(final String label, final float sizeX, final float sizeY, final imgui.type.ImLong pData, final long pMin, final long pMax) {
        return this.nVSliderScalar(label, sizeX, sizeY, pData.getData(), pMin, pMax);
    }

    public boolean vSliderScalar(final String label, final imgui.ImVec2 size, final imgui.type.ImLong pData, final long pMin, final long pMax, final String format) {
        return this.vSliderScalar(label, size.x, size.y, pData, pMin, pMax, format);
    }

    public boolean vSliderScalar(final String label, final float sizeX, final float sizeY, final imgui.type.ImLong pData, final long pMin, final long pMax, final String format) {
        return this.nVSliderScalar(label, sizeX, sizeY, pData.getData(), pMin, pMax, format);
    }

    public boolean vSliderScalar(final String label, final imgui.ImVec2 size, final imgui.type.ImLong pData, final long pMin, final long pMax, final String format, final int flags) {
        return this.vSliderScalar(label, size.x, size.y, pData, pMin, pMax, format, flags);
    }

    public boolean vSliderScalar(final String label, final float sizeX, final float sizeY, final imgui.type.ImLong pData, final long pMin, final long pMax, final String format, final int flags) {
        return this.nVSliderScalar(label, sizeX, sizeY, pData.getData(), pMin, pMax, format, flags);
    }

    public boolean inputText(final String label, final imgui.type.ImString text) {
        return this.preInputText(false, label, null, text, 0, 0, imgui.flag.ImGuiInputTextFlags.None, null);
    }

    public boolean inputText(final String label, final imgui.type.ImString text, final int flags) {
        return this.preInputText(false, label, null, text, 0, 0, flags, null);
    }

    public boolean inputText(final String label, final imgui.type.ImString text, final int flags, final imgui.callback.ImGuiInputTextCallback callback) {
        return this.preInputText(false, label, null, text, 0, 0, flags, callback);
    }

    public boolean inputTextMultiline(final String label, final imgui.type.ImString text) {
        return this.preInputText(true, label, null, text, 0, 0, imgui.flag.ImGuiInputTextFlags.None, null);
    }

    public boolean inputTextMultiline(final String label, final imgui.type.ImString text, final float width, final float height) {
        return this.preInputText(true, label, null, text, width, height, imgui.flag.ImGuiInputTextFlags.None, null);
    }

    public boolean inputTextMultiline(final String label, final imgui.type.ImString text, final float width, final float height, final int flags) {
        return this.preInputText(true, label, null, text, width, height, flags, null);
    }

    public boolean inputTextMultiline(final String label, final imgui.type.ImString text, final float width, final float height, final int flags, final imgui.callback.ImGuiInputTextCallback callback) {
        return this.preInputText(true, label, null, text, width, height, flags, callback);
    }

    public boolean inputTextMultiline(final String label, final imgui.type.ImString text, final int flags) {
        return this.preInputText(true, label, null, text, 0, 0, flags, null);
    }

    public boolean inputTextMultiline(final String label, final imgui.type.ImString text, final int flags, final imgui.callback.ImGuiInputTextCallback callback) {
        return this.preInputText(true, label, null, text, 0, 0, flags, callback);
    }

    public boolean inputFloat(final String label, final imgui.type.ImFloat value) {
        return this.nInputFloat(label, value.getData());
    }

    public boolean inputFloat(final String label, final float[] value) {
        return this.nInputFloat(label, value);
    }

    public boolean inputFloat(final String label, final imgui.type.ImFloat value, final float step) {
        return this.nInputFloat(label, value.getData(), step);
    }

    public boolean inputFloat(final String label, final float[] value, final float step) {
        return this.nInputFloat(label, value, step);
    }

    public boolean inputFloat(final String label, final imgui.type.ImFloat value, final float step, final float stepFast) {
        return this.nInputFloat(label, value.getData(), step, stepFast);
    }

    public boolean inputFloat(final String label, final float[] value, final float step, final float stepFast) {
        return this.nInputFloat(label, value, step, stepFast);
    }

    public boolean inputFloat(final String label, final imgui.type.ImFloat value, final float step, final float stepFast, final String format) {
        return this.nInputFloat(label, value.getData(), step, stepFast, format);
    }

    public boolean inputFloat(final String label, final float[] value, final float step, final float stepFast, final String format) {
        return this.nInputFloat(label, value, step, stepFast, format);
    }

    public boolean inputFloat(final String label, final imgui.type.ImFloat value, final float step, final float stepFast, final int flags) {
        return this.nInputFloat(label, value.getData(), step, stepFast, flags);
    }

    public boolean inputFloat(final String label, final imgui.type.ImFloat value, final float step, final float stepFast, final String format, final int flags) {
        return this.nInputFloat(label, value.getData(), step, stepFast, format, flags);
    }

    public boolean inputFloat(final String label, final float[] value, final float step, final float stepFast, final int flags) {
        return this.nInputFloat(label, value, step, stepFast, flags);
    }

    public boolean inputFloat(final String label, final float[] value, final float step, final float stepFast, final String format, final int flags) {
        return this.nInputFloat(label, value, step, stepFast, format, flags);
    }

    public boolean inputFloat2(final String label, final float[] value) {
        return this.nInputFloat2(label, value);
    }

    public boolean inputFloat2(final String label, final float[] value, final String format) {
        return this.nInputFloat2(label, value, format);
    }

    public boolean inputFloat2(final String label, final float[] value, final int flags) {
        return this.nInputFloat2(label, value, flags);
    }

    public boolean inputFloat2(final String label, final float[] value, final String format, final int flags) {
        return this.nInputFloat2(label, value, format, flags);
    }

    public boolean inputFloat3(final String label, final float[] value) {
        return this.nInputFloat3(label, value);
    }

    public boolean inputFloat3(final String label, final float[] value, final String format) {
        return this.nInputFloat3(label, value, format);
    }

    public boolean inputFloat3(final String label, final float[] value, final int flags) {
        return this.nInputFloat3(label, value, flags);
    }

    public boolean inputFloat3(final String label, final float[] value, final String format, final int flags) {
        return this.nInputFloat3(label, value, format, flags);
    }

    public boolean inputFloat4(final String label, final float[] value) {
        return this.nInputFloat4(label, value);
    }

    public boolean inputFloat4(final String label, final float[] value, final String format) {
        return this.nInputFloat4(label, value, format);
    }

    public boolean inputFloat4(final String label, final float[] value, final int flags) {
        return this.nInputFloat4(label, value, flags);
    }

    public boolean inputFloat4(final String label, final float[] value, final String format, final int flags) {
        return this.nInputFloat4(label, value, format, flags);
    }

    public boolean inputInt(final String label, final imgui.type.ImInt value) {
        return this.nInputInt(label, value.getData());
    }

    public boolean inputInt(final String label, final int[] value) {
        return this.nInputInt(label, value);
    }

    public boolean inputInt(final String label, final imgui.type.ImInt value, final int step) {
        return this.nInputInt(label, value.getData(), step);
    }

    public boolean inputInt(final String label, final int[] value, final int step) {
        return this.nInputInt(label, value, step);
    }

    public boolean inputInt(final String label, final imgui.type.ImInt value, final int step, final int stepFast) {
        return this.nInputInt(label, value.getData(), step, stepFast);
    }

    public boolean inputInt(final String label, final int[] value, final int step, final int stepFast) {
        return this.nInputInt(label, value, step, stepFast);
    }

    public boolean inputInt(final String label, final imgui.type.ImInt value, final int step, final int stepFast, final int flags) {
        return this.nInputInt(label, value.getData(), step, stepFast, flags);
    }

    public boolean inputInt(final String label, final int[] value, final int step, final int stepFast, final int flags) {
        return this.nInputInt(label, value, step, stepFast, flags);
    }

    public boolean inputInt2(final String label, final int[] value) {
        return this.nInputInt2(label, value);
    }

    public boolean inputInt2(final String label, final int[] value, final int flags) {
        return this.nInputInt2(label, value, flags);
    }

    public boolean inputInt3(final String label, final int[] value) {
        return this.nInputInt3(label, value);
    }

    public boolean inputInt3(final String label, final int[] value, final int flags) {
        return this.nInputInt3(label, value, flags);
    }

    public boolean inputInt4(final String label, final int[] value) {
        return this.nInputInt4(label, value);
    }

    public boolean inputInt4(final String label, final int[] value, final int flags) {
        return this.nInputInt4(label, value, flags);
    }

    public boolean inputDouble(final String label, final imgui.type.ImDouble value, final double step, final double stepFast) {
        return this.nInputDouble(label, value.getData(), step, stepFast);
    }

    public boolean inputDouble(final String label, final double[] value, final double step, final double stepFast) {
        return this.nInputDouble(label, value, step, stepFast);
    }

    public boolean inputDouble(final String label, final imgui.type.ImDouble value, final double step, final double stepFast, final String format) {
        return this.nInputDouble(label, value.getData(), step, stepFast, format);
    }

    public boolean inputDouble(final String label, final double[] value, final double step, final double stepFast, final String format) {
        return this.nInputDouble(label, value, step, stepFast, format);
    }

    public boolean inputDouble(final String label, final imgui.type.ImDouble value, final double step, final double stepFast, final int flags) {
        return this.nInputDouble(label, value.getData(), step, stepFast, flags);
    }

    public boolean inputDouble(final String label, final imgui.type.ImDouble value, final double step, final double stepFast, final String format, final int flags) {
        return this.nInputDouble(label, value.getData(), step, stepFast, format, flags);
    }

    public boolean inputDouble(final String label, final double[] value, final double step, final double stepFast, final int flags) {
        return this.nInputDouble(label, value, step, stepFast, flags);
    }

    public boolean inputDouble(final String label, final double[] value, final double step, final double stepFast, final String format, final int flags) {
        return this.nInputDouble(label, value, step, stepFast, format, flags);
    }

    public boolean inputScalar(final String label, final imgui.type.ImFloat pData) {
        return this.nInputScalar(label, pData.getData());
    }

    public boolean inputScalar(final String label, final imgui.type.ImFloat pData, final float pStep) {
        return this.nInputScalar(label, pData.getData(), pStep);
    }

    public boolean inputScalar(final String label, final imgui.type.ImFloat pData, final float pStep, final float pStepFast) {
        return this.nInputScalar(label, pData.getData(), pStep, pStepFast);
    }

    public boolean inputScalar(final String label, final imgui.type.ImFloat pData, final float pStep, final float pStepFast, final String format) {
        return this.nInputScalar(label, pData.getData(), pStep, pStepFast, format);
    }

    public boolean inputScalar(final String label, final imgui.type.ImFloat pData, final float pStep, final float pStepFast, final String format, final int flags) {
        return this.nInputScalar(label, pData.getData(), pStep, pStepFast, format, flags);
    }

    public boolean inputScalar(final String label, final imgui.type.ImShort pData) {
        return this.nInputScalar(label, pData.getData());
    }

    public boolean inputScalar(final String label, final imgui.type.ImShort pData, final short pStep) {
        return this.nInputScalar(label, pData.getData(), pStep);
    }

    public boolean inputScalar(final String label, final imgui.type.ImShort pData, final short pStep, final short pStepFast) {
        return this.nInputScalar(label, pData.getData(), pStep, pStepFast);
    }

    public boolean inputScalar(final String label, final imgui.type.ImShort pData, final short pStep, final short pStepFast, final String format) {
        return this.nInputScalar(label, pData.getData(), pStep, pStepFast, format);
    }

    public boolean inputScalar(final String label, final imgui.type.ImShort pData, final short pStep, final short pStepFast, final String format, final int flags) {
        return this.nInputScalar(label, pData.getData(), pStep, pStepFast, format, flags);
    }

    public boolean inputScalar(final String label, final imgui.type.ImInt pData) {
        return this.nInputScalar(label, pData.getData());
    }

    public boolean inputScalar(final String label, final imgui.type.ImInt pData, final int pStep) {
        return this.nInputScalar(label, pData.getData(), pStep);
    }

    public boolean inputScalar(final String label, final imgui.type.ImInt pData, final int pStep, final int pStepFast) {
        return this.nInputScalar(label, pData.getData(), pStep, pStepFast);
    }

    public boolean inputScalar(final String label, final imgui.type.ImInt pData, final int pStep, final int pStepFast, final String format) {
        return this.nInputScalar(label, pData.getData(), pStep, pStepFast, format);
    }

    public boolean inputScalar(final String label, final imgui.type.ImInt pData, final int pStep, final int pStepFast, final String format, final int flags) {
        return this.nInputScalar(label, pData.getData(), pStep, pStepFast, format, flags);
    }

    public boolean inputScalar(final String label, final imgui.type.ImLong pData) {
        return this.nInputScalar(label, pData.getData());
    }

    public boolean inputScalar(final String label, final imgui.type.ImLong pData, final long pStep) {
        return this.nInputScalar(label, pData.getData(), pStep);
    }

    public boolean inputScalar(final String label, final imgui.type.ImLong pData, final long pStep, final long pStepFast) {
        return this.nInputScalar(label, pData.getData(), pStep, pStepFast);
    }

    public boolean inputScalar(final String label, final imgui.type.ImLong pData, final long pStep, final long pStepFast, final String format) {
        return this.nInputScalar(label, pData.getData(), pStep, pStepFast, format);
    }

    public boolean inputScalar(final String label, final imgui.type.ImLong pData, final long pStep, final long pStepFast, final String format, final int flags) {
        return this.nInputScalar(label, pData.getData(), pStep, pStepFast, format, flags);
    }

    public boolean colorEdit3(final String label, final float[] col) {
        return this.nColorEdit3(label, col);
    }

    public boolean colorEdit3(final String label, final float[] col, final int flags) {
        return this.nColorEdit3(label, col, flags);
    }

    public boolean colorEdit4(final String label, final float[] col) {
        return this.nColorEdit4(label, col);
    }

    public boolean colorEdit4(final String label, final float[] col, final int flags) {
        return this.nColorEdit4(label, col, flags);
    }

    public boolean colorPicker3(final String label, final float[] col) {
        return this.nColorPicker3(label, col);
    }

    public boolean colorPicker3(final String label, final float[] col, final int flags) {
        return this.nColorPicker3(label, col, flags);
    }

    public boolean colorPicker4(final String label, final float[] col) {
        return this.nColorPicker4(label, col);
    }

    public boolean colorPicker4(final String label, final float[] col, final int flags) {
        return this.nColorPicker4(label, col, flags);
    }

    public boolean colorPicker4(final String label, final float[] col, final int flags, final imgui.type.ImFloat refCol) {
        return this.nColorPicker4(label, col, flags, refCol.getData());
    }

    public boolean colorButton(final String descId, final imgui.ImVec4 col) {
        return this.colorButton(descId, col.x, col.y, col.z, col.w);
    }

    public boolean colorButton(final String descId, final float colX, final float colY, final float colZ, final float colW) {
        return this.nColorButton(descId, colX, colY, colZ, colW);
    }

    public boolean colorButton(final String descId, final imgui.ImVec4 col, final int flags) {
        return this.colorButton(descId, col.x, col.y, col.z, col.w, flags);
    }

    public boolean colorButton(final String descId, final float colX, final float colY, final float colZ, final float colW, final int flags) {
        return this.nColorButton(descId, colX, colY, colZ, colW, flags);
    }

    public boolean colorButton(final String descId, final imgui.ImVec4 col, final imgui.ImVec2 size) {
        return this.colorButton(descId, col.x, col.y, col.z, col.w, size);
    }

    public boolean colorButton(final String descId, final float colX, final float colY, final float colZ, final float colW, final imgui.ImVec2 size) {
        return this.colorButton(descId, colX, colY, colZ, colW, size.x, size.y);
    }

    public boolean colorButton(final String descId, final imgui.ImVec4 col, final float sizeX, final float sizeY) {
        return this.colorButton(descId, col.x, col.y, col.z, col.w, sizeX, sizeY);
    }

    public boolean colorButton(final String descId, final float colX, final float colY, final float colZ, final float colW, final float sizeX, final float sizeY) {
        return this.nColorButton(descId, colX, colY, colZ, colW, sizeX, sizeY);
    }

    public boolean colorButton(final String descId, final imgui.ImVec4 col, final int flags, final imgui.ImVec2 size) {
        return this.colorButton(descId, col.x, col.y, col.z, col.w, flags, size);
    }

    public boolean colorButton(final String descId, final float colX, final float colY, final float colZ, final float colW, final int flags, final imgui.ImVec2 size) {
        return this.colorButton(descId, colX, colY, colZ, colW, flags, size.x, size.y);
    }

    public boolean colorButton(final String descId, final imgui.ImVec4 col, final int flags, final float sizeX, final float sizeY) {
        return this.colorButton(descId, col.x, col.y, col.z, col.w, flags, sizeX, sizeY);
    }

    public boolean colorButton(final String descId, final float colX, final float colY, final float colZ, final float colW, final int flags, final float sizeX, final float sizeY) {
        return this.nColorButton(descId, colX, colY, colZ, colW, flags, sizeX, sizeY);
    }

    public void setColorEditOptions(final int flags) {
        this.nSetColorEditOptions(flags);
    }

    public boolean treeNode(final String label) {
        return this.nTreeNode(label);
    }

    public boolean treeNode(final String strId, final String fmt) {
        return this.nTreeNode(strId, fmt);
    }

    public boolean treeNodeEx(final String label) {
        return this.nTreeNodeEx(label);
    }

    public boolean treeNodeEx(final String label, final int flags) {
        return this.nTreeNodeEx(label, flags);
    }

    public boolean treeNodeEx(final String strId, final int flags, final String fmt) {
        return this.nTreeNodeEx(strId, flags, fmt);
    }

    public void treePush(final String strId) {
        this.nTreePush(strId);
    }

    public void treePop() {
        this.nTreePop();
    }

    public float getTreeNodeToLabelSpacing() {
        return this.nGetTreeNodeToLabelSpacing();
    }

    public boolean collapsingHeader(final String label) {
        return this.nCollapsingHeader(label);
    }

    public boolean collapsingHeader(final String label, final int flags) {
        return this.nCollapsingHeader(label, flags);
    }

    public boolean collapsingHeader(final String label, final imgui.type.ImBoolean pVisible) {
        return this.nCollapsingHeader(label, pVisible.getData());
    }

    public boolean collapsingHeader(final String label, final imgui.type.ImBoolean pVisible, final int flags) {
        return this.nCollapsingHeader(label, pVisible.getData(), flags);
    }

    public void setNextItemOpen(final boolean isOpen) {
        this.nSetNextItemOpen(isOpen);
    }

    public void setNextItemOpen(final boolean isOpen, final int cond) {
        this.nSetNextItemOpen(isOpen, cond);
    }

    public boolean selectable(final String label) {
        return this.nSelectable(label);
    }

    public boolean selectable(final String label, final boolean selected) {
        return this.nSelectable(label, selected);
    }

    public boolean selectable(final String label, final int flags) {
        return this.nSelectable(label, flags);
    }

    public boolean selectable(final String label, final boolean selected, final int flags) {
        return this.nSelectable(label, selected, flags);
    }

    public boolean selectable(final String label, final int flags, final imgui.ImVec2 size) {
        return this.selectable(label, flags, size.x, size.y);
    }

    public boolean selectable(final String label, final int flags, final float sizeX, final float sizeY) {
        return this.nSelectable(label, flags, sizeX, sizeY);
    }

    public boolean selectable(final String label, final boolean selected, final imgui.ImVec2 size) {
        return this.selectable(label, selected, size.x, size.y);
    }

    public boolean selectable(final String label, final boolean selected, final float sizeX, final float sizeY) {
        return this.nSelectable(label, selected, sizeX, sizeY);
    }

    public boolean selectable(final String label, final boolean selected, final int flags, final imgui.ImVec2 size) {
        return this.selectable(label, selected, flags, size.x, size.y);
    }

    public boolean selectable(final String label, final boolean selected, final int flags, final float sizeX, final float sizeY) {
        return this.nSelectable(label, selected, flags, sizeX, sizeY);
    }

    public boolean selectable(final String label, final imgui.type.ImBoolean pSelected) {
        return this.nSelectable(label, pSelected.getData());
    }

    public boolean selectable(final String label, final imgui.type.ImBoolean pSelected, final int flags) {
        return this.nSelectable(label, pSelected.getData(), flags);
    }

    public boolean selectable(final String label, final imgui.type.ImBoolean pSelected, final imgui.ImVec2 size) {
        return this.selectable(label, pSelected, size.x, size.y);
    }

    public boolean selectable(final String label, final imgui.type.ImBoolean pSelected, final float sizeX, final float sizeY) {
        return this.nSelectable(label, pSelected.getData(), sizeX, sizeY);
    }

    public boolean selectable(final String label, final imgui.type.ImBoolean pSelected, final int flags, final imgui.ImVec2 size) {
        return this.selectable(label, pSelected, flags, size.x, size.y);
    }

    public boolean selectable(final String label, final imgui.type.ImBoolean pSelected, final int flags, final float sizeX, final float sizeY) {
        return this.nSelectable(label, pSelected.getData(), flags, sizeX, sizeY);
    }

    public boolean beginListBox(final String label) {
        return this.nBeginListBox(label);
    }

    public boolean beginListBox(final String label, final imgui.ImVec2 size) {
        return this.beginListBox(label, size.x, size.y);
    }

    public boolean beginListBox(final String label, final float sizeX, final float sizeY) {
        return this.nBeginListBox(label, sizeX, sizeY);
    }

    public void endListBox() {
        this.nEndListBox();
    }

    public boolean listBox(final String label, final imgui.type.ImInt currentItem, final String[] items) {
        return this.listBox(label, currentItem, items, items.length);
    }

    public boolean listBox(final String label, final imgui.type.ImInt currentItem, final String[] items, final int heightInItems) {
        return this.nListBox(label, currentItem.getData(), items, items.length, heightInItems);
    }

    public void plotLines(final String label, final float[] values, final int valuesCount) {
        this.nPlotLines(label, values, valuesCount);
    }

    public void plotLines(final String label, final float[] values, final int valuesCount, final int valuesOffset) {
        this.nPlotLines(label, values, valuesCount, valuesOffset);
    }

    public void plotLines(final String label, final float[] values, final int valuesCount, final int valuesOffset, final String overlayText) {
        this.nPlotLines(label, values, valuesCount, valuesOffset, overlayText);
    }

    public void plotLines(final String label, final float[] values, final int valuesCount, final int valuesOffset, final float scaleMin) {
        this.nPlotLines(label, values, valuesCount, valuesOffset, scaleMin);
    }

    public void plotLines(final String label, final float[] values, final int valuesCount, final int valuesOffset, final String overlayText, final float scaleMin) {
        this.nPlotLines(label, values, valuesCount, valuesOffset, overlayText, scaleMin);
    }

    public void plotLines(final String label, final float[] values, final int valuesCount, final int valuesOffset, final float scaleMin, final float scaleMax) {
        this.nPlotLines(label, values, valuesCount, valuesOffset, scaleMin, scaleMax);
    }

    public void plotLines(final String label, final float[] values, final int valuesCount, final int valuesOffset, final String overlayText, final float scaleMin, final float scaleMax) {
        this.nPlotLines(label, values, valuesCount, valuesOffset, overlayText, scaleMin, scaleMax);
    }

    public void plotLines(final String label, final float[] values, final int valuesCount, final int valuesOffset, final float scaleMin, final float scaleMax, final imgui.ImVec2 graphSize) {
        this.plotLines(label, values, valuesCount, valuesOffset, scaleMin, scaleMax, graphSize.x, graphSize.y);
    }

    public void plotLines(final String label, final float[] values, final int valuesCount, final int valuesOffset, final float scaleMin, final float scaleMax, final float graphSizeX, final float graphSizeY) {
        this.nPlotLines(label, values, valuesCount, valuesOffset, scaleMin, scaleMax, graphSizeX, graphSizeY);
    }

    public void plotLines(final String label, final float[] values, final int valuesCount, final int valuesOffset, final String overlayText, final float scaleMin, final float scaleMax, final imgui.ImVec2 graphSize) {
        this.plotLines(label, values, valuesCount, valuesOffset, overlayText, scaleMin, scaleMax, graphSize.x, graphSize.y);
    }

    public void plotLines(final String label, final float[] values, final int valuesCount, final int valuesOffset, final String overlayText, final float scaleMin, final float scaleMax, final float graphSizeX, final float graphSizeY) {
        this.nPlotLines(label, values, valuesCount, valuesOffset, overlayText, scaleMin, scaleMax, graphSizeX, graphSizeY);
    }

    public void plotLines(final String label, final float[] values, final int valuesCount, final int valuesOffset, final float scaleMin, final float scaleMax, final imgui.ImVec2 graphSize, final int stride) {
        this.plotLines(label, values, valuesCount, valuesOffset, scaleMin, scaleMax, graphSize.x, graphSize.y, stride);
    }

    public void plotLines(final String label, final float[] values, final int valuesCount, final int valuesOffset, final float scaleMin, final float scaleMax, final float graphSizeX, final float graphSizeY, final int stride) {
        this.nPlotLines(label, values, valuesCount, valuesOffset, scaleMin, scaleMax, graphSizeX, graphSizeY, stride);
    }

    public void plotLines(final String label, final float[] values, final int valuesCount, final int valuesOffset, final String overlayText, final float scaleMin, final float scaleMax, final int stride) {
        this.nPlotLines(label, values, valuesCount, valuesOffset, overlayText, scaleMin, scaleMax, stride);
    }

    public void plotLines(final String label, final float[] values, final int valuesCount, final int valuesOffset, final String overlayText, final float scaleMin, final float scaleMax, final imgui.ImVec2 graphSize, final int stride) {
        this.plotLines(label, values, valuesCount, valuesOffset, overlayText, scaleMin, scaleMax, graphSize.x, graphSize.y, stride);
    }

    public void plotLines(final String label, final float[] values, final int valuesCount, final int valuesOffset, final String overlayText, final float scaleMin, final float scaleMax, final float graphSizeX, final float graphSizeY, final int stride) {
        this.nPlotLines(label, values, valuesCount, valuesOffset, overlayText, scaleMin, scaleMax, graphSizeX, graphSizeY, stride);
    }

    public void plotHistogram(final String label, final float[] values, final int valuesCount, final int valuesOffset, final String overlayText) {
        this.nPlotHistogram(label, values, valuesCount, valuesOffset, overlayText);
    }

    public void plotHistogram(final String label, final float[] values, final int valuesCount, final int valuesOffset, final float scaleMin) {
        this.nPlotHistogram(label, values, valuesCount, valuesOffset, scaleMin);
    }

    public void plotHistogram(final String label, final float[] values, final int valuesCount, final int valuesOffset, final String overlayText, final float scaleMin) {
        this.nPlotHistogram(label, values, valuesCount, valuesOffset, overlayText, scaleMin);
    }

    public void plotHistogram(final String label, final float[] values, final int valuesCount, final int valuesOffset, final float scaleMin, final float scaleMax, final imgui.ImVec2 graphSize) {
        this.plotHistogram(label, values, valuesCount, valuesOffset, scaleMin, scaleMax, graphSize.x, graphSize.y);
    }

    public void plotHistogram(final String label, final float[] values, final int valuesCount, final int valuesOffset, final float scaleMin, final float scaleMax, final float graphSizeX, final float graphSizeY) {
        this.nPlotHistogram(label, values, valuesCount, valuesOffset, scaleMin, scaleMax, graphSizeX, graphSizeY);
    }

    public void plotHistogram(final String label, final float[] values, final int valuesCount, final int valuesOffset, final String overlayText, final float scaleMin, final float scaleMax, final imgui.ImVec2 graphSize) {
        this.plotHistogram(label, values, valuesCount, valuesOffset, overlayText, scaleMin, scaleMax, graphSize.x, graphSize.y);
    }

    public void plotHistogram(final String label, final float[] values, final int valuesCount, final int valuesOffset, final String overlayText, final float scaleMin, final float scaleMax, final float graphSizeX, final float graphSizeY) {
        this.nPlotHistogram(label, values, valuesCount, valuesOffset, overlayText, scaleMin, scaleMax, graphSizeX, graphSizeY);
    }

    public void plotHistogram(final String label, final float[] values, final int valuesCount, final int valuesOffset, final float scaleMin, final float scaleMax, final imgui.ImVec2 graphSize, final int stride) {
        this.plotHistogram(label, values, valuesCount, valuesOffset, scaleMin, scaleMax, graphSize.x, graphSize.y, stride);
    }

    public void plotHistogram(final String label, final float[] values, final int valuesCount, final int valuesOffset, final float scaleMin, final float scaleMax, final float graphSizeX, final float graphSizeY, final int stride) {
        this.nPlotHistogram(label, values, valuesCount, valuesOffset, scaleMin, scaleMax, graphSizeX, graphSizeY, stride);
    }

    public void plotHistogram(final String label, final float[] values, final int valuesCount, final int valuesOffset, final String overlayText, final float scaleMin, final float scaleMax, final int stride) {
        this.nPlotHistogram(label, values, valuesCount, valuesOffset, overlayText, scaleMin, scaleMax, stride);
    }

    public void plotHistogram(final String label, final float[] values, final int valuesCount, final int valuesOffset, final String overlayText, final float scaleMin, final float scaleMax, final imgui.ImVec2 graphSize, final int stride) {
        this.plotHistogram(label, values, valuesCount, valuesOffset, overlayText, scaleMin, scaleMax, graphSize.x, graphSize.y, stride);
    }

    public void plotHistogram(final String label, final float[] values, final int valuesCount, final int valuesOffset, final String overlayText, final float scaleMin, final float scaleMax, final float graphSizeX, final float graphSizeY, final int stride) {
        this.nPlotHistogram(label, values, valuesCount, valuesOffset, overlayText, scaleMin, scaleMax, graphSizeX, graphSizeY, stride);
    }

    public void value(final String prefix, final boolean b) {
        this.nValue(prefix, b);
    }

    public void value(final String prefix, final int v) {
        this.nValue(prefix, v);
    }

    public void value(final String prefix, final float v) {
        this.nValue(prefix, v);
    }

    public void value(final String prefix, final float v, final String floatFormat) {
        this.nValue(prefix, v, floatFormat);
    }

    public boolean beginMenuBar() {
        return this.nBeginMenuBar();
    }

    public void endMenuBar() {
        this.nEndMenuBar();
    }

    public boolean beginMainMenuBar() {
        return this.nBeginMainMenuBar();
    }

    public void endMainMenuBar() {
        this.nEndMainMenuBar();
    }

    public boolean beginMenu(final String label) {
        return this.nBeginMenu(label);
    }

    public boolean beginMenu(final String label, final boolean enabled) {
        return this.nBeginMenu(label, enabled);
    }

    public void endMenu() {
        this.nEndMenu();
    }

    public boolean menuItem(final String label) {
        return this.nMenuItem(label);
    }

    public boolean menuItem(final String label, final String shortcut) {
        return this.nMenuItem(label, shortcut);
    }

    public boolean menuItem(final String label, final String shortcut, final boolean selected) {
        return this.nMenuItem(label, shortcut, selected);
    }

    public boolean menuItem(final String label, final String shortcut, final boolean selected, final boolean enabled) {
        return this.nMenuItem(label, shortcut, selected, enabled);
    }

    public boolean menuItem(final String label, final String shortcut, final imgui.type.ImBoolean pSelected) {
        return this.nMenuItem(label, shortcut, pSelected.getData());
    }

    public boolean menuItem(final String label, final String shortcut, final imgui.type.ImBoolean pSelected, final boolean enabled) {
        return this.nMenuItem(label, shortcut, pSelected.getData(), enabled);
    }

    public void beginTooltip() {
        this.nBeginTooltip();
    }

    public void endTooltip() {
        this.nEndTooltip();
    }

    public void setTooltip(final String fmt) {
        this.nSetTooltip(fmt);
    }

    public boolean beginPopup(final String strId) {
        return this.nBeginPopup(strId);
    }

    public boolean beginPopup(final String strId, final int flags) {
        return this.nBeginPopup(strId, flags);
    }

    public boolean beginPopupModal(final String name) {
        return this.nBeginPopupModal(name);
    }

    public boolean beginPopupModal(final String name, final imgui.type.ImBoolean pOpen) {
        return this.nBeginPopupModal(name, pOpen.getData());
    }

    public boolean beginPopupModal(final String name, final int flags) {
        return this.nBeginPopupModal(name, flags);
    }

    public boolean beginPopupModal(final String name, final imgui.type.ImBoolean pOpen, final int flags) {
        return this.nBeginPopupModal(name, pOpen.getData(), flags);
    }

    public void endPopup() {
        this.nEndPopup();
    }

    public void openPopup(final String strId) {
        this.nOpenPopup(strId);
    }

    public void openPopup(final String strId, final int popupFlags) {
        this.nOpenPopup(strId, popupFlags);
    }

    public void openPopup(final int id) {
        this.nOpenPopup(id);
    }

    public void openPopup(final int id, final int popupFlags) {
        this.nOpenPopup(id, popupFlags);
    }

    public void openPopupOnItemClick() {
        this.nOpenPopupOnItemClick();
    }

    public void openPopupOnItemClick(final String strId) {
        this.nOpenPopupOnItemClick(strId);
    }

    public void openPopupOnItemClick(final int popupFlags) {
        this.nOpenPopupOnItemClick(popupFlags);
    }

    public void openPopupOnItemClick(final String strId, final int popupFlags) {
        this.nOpenPopupOnItemClick(strId, popupFlags);
    }

    public void closeCurrentPopup() {
        this.nCloseCurrentPopup();
    }

    public boolean beginPopupContextItem() {
        return this.nBeginPopupContextItem();
    }

    public boolean beginPopupContextItem(final String strId) {
        return this.nBeginPopupContextItem(strId);
    }

    public boolean beginPopupContextItem(final int popupFlags) {
        return this.nBeginPopupContextItem(popupFlags);
    }

    public boolean beginPopupContextItem(final String strId, final int popupFlags) {
        return this.nBeginPopupContextItem(strId, popupFlags);
    }

    public boolean beginPopupContextWindow() {
        return this.nBeginPopupContextWindow();
    }

    public boolean beginPopupContextWindow(final String strId) {
        return this.nBeginPopupContextWindow(strId);
    }

    public boolean beginPopupContextWindow(final int popupFlags) {
        return this.nBeginPopupContextWindow(popupFlags);
    }

    public boolean beginPopupContextWindow(final String strId, final int popupFlags) {
        return this.nBeginPopupContextWindow(strId, popupFlags);
    }

    public boolean beginPopupContextVoid() {
        return this.nBeginPopupContextVoid();
    }

    public boolean beginPopupContextVoid(final String strId) {
        return this.nBeginPopupContextVoid(strId);
    }

    public boolean beginPopupContextVoid(final int popupFlags) {
        return this.nBeginPopupContextVoid(popupFlags);
    }

    public boolean beginPopupContextVoid(final String strId, final int popupFlags) {
        return this.nBeginPopupContextVoid(strId, popupFlags);
    }

    public boolean isPopupOpen(final String strId) {
        return this.nIsPopupOpen(strId);
    }

    public boolean isPopupOpen(final String strId, final int flags) {
        return this.nIsPopupOpen(strId, flags);
    }

    public boolean beginTable(final String strId, final int column) {
        return this.nBeginTable(strId, column);
    }

    public boolean beginTable(final String strId, final int column, final int flags) {
        return this.nBeginTable(strId, column, flags);
    }

    public boolean beginTable(final String strId, final int column, final int flags, final imgui.ImVec2 outerSize) {
        return this.beginTable(strId, column, flags, outerSize.x, outerSize.y);
    }

    public boolean beginTable(final String strId, final int column, final int flags, final float outerSizeX, final float outerSizeY) {
        return this.nBeginTable(strId, column, flags, outerSizeX, outerSizeY);
    }

    public boolean beginTable(final String strId, final int column, final int flags, final float innerWidth) {
        return this.nBeginTable(strId, column, flags, innerWidth);
    }

    public boolean beginTable(final String strId, final int column, final int flags, final imgui.ImVec2 outerSize, final float innerWidth) {
        return this.beginTable(strId, column, flags, outerSize.x, outerSize.y, innerWidth);
    }

    public boolean beginTable(final String strId, final int column, final int flags, final float outerSizeX, final float outerSizeY, final float innerWidth) {
        return this.nBeginTable(strId, column, flags, outerSizeX, outerSizeY, innerWidth);
    }

    public void endTable() {
        this.nEndTable();
    }

    public void tableNextRow() {
        this.nTableNextRow();
    }

    public void tableNextRow(final int rowFlags) {
        this.nTableNextRow(rowFlags);
    }

    public void tableNextRow(final float minRowHeight) {
        this.nTableNextRow(minRowHeight);
    }

    public void tableNextRow(final int rowFlags, final float minRowHeight) {
        this.nTableNextRow(rowFlags, minRowHeight);
    }

    public boolean tableNextColumn() {
        return this.nTableNextColumn();
    }

    public boolean tableSetColumnIndex(final int columnN) {
        return this.nTableSetColumnIndex(columnN);
    }

    public void tableSetupColumn(final String label) {
        this.nTableSetupColumn(label);
    }

    public void tableSetupColumn(final String label, final int flags) {
        this.nTableSetupColumn(label, flags);
    }

    public void tableSetupColumn(final String label, final float initWidthOrWeight) {
        this.nTableSetupColumn(label, initWidthOrWeight);
    }

    public void tableSetupColumn(final String label, final int flags, final float initWidthOrWeight) {
        this.nTableSetupColumn(label, flags, initWidthOrWeight);
    }

    public void tableSetupColumn(final String label, final float initWidthOrWeight, final int userId) {
        this.nTableSetupColumn(label, initWidthOrWeight, userId);
    }

    public void tableSetupColumn(final String label, final int flags, final int userId) {
        this.nTableSetupColumn(label, flags, userId);
    }

    public void tableSetupColumn(final String label, final int flags, final float initWidthOrWeight, final int userId) {
        this.nTableSetupColumn(label, flags, initWidthOrWeight, userId);
    }

    public void tableSetupScrollFreeze(final int cols, final int rows) {
        this.nTableSetupScrollFreeze(cols, rows);
    }

    public void tableHeadersRow() {
        this.nTableHeadersRow();
    }

    public void tableHeader(final String label) {
        this.nTableHeader(label);
    }

    public int tableGetColumnCount() {
        return this.nTableGetColumnCount();
    }

    public int tableGetColumnIndex() {
        return this.nTableGetColumnIndex();
    }

    public int tableGetRowIndex() {
        return this.nTableGetRowIndex();
    }

    public String tableGetColumnName() {
        return this.nTableGetColumnName();
    }

    public String tableGetColumnName(final int columnN) {
        return this.nTableGetColumnName(columnN);
    }

    public int tableGetColumnFlags() {
        return this.nTableGetColumnFlags();
    }

    public int tableGetColumnFlags(final int columnN) {
        return this.nTableGetColumnFlags(columnN);
    }

    public void tableSetColumnEnabled(final int columnN, final boolean v) {
        this.nTableSetColumnEnabled(columnN, v);
    }

    public void tableSetBgColor(final int target, final int color) {
        this.nTableSetBgColor(target, color);
    }

    public void tableSetBgColor(final int target, final int color, final int columnN) {
        this.nTableSetBgColor(target, color, columnN);
    }

    public void columns() {
        this.nColumns();
    }

    public void columns(final int count) {
        this.nColumns(count);
    }

    public void columns(final String id) {
        this.nColumns(id);
    }

    public void columns(final int count, final String id) {
        this.nColumns(count, id);
    }

    public void columns(final String id, final boolean border) {
        this.nColumns(id, border);
    }

    public void columns(final int count, final boolean border) {
        this.nColumns(count, border);
    }

    public void columns(final int count, final String id, final boolean border) {
        this.nColumns(count, id, border);
    }

    public void nextColumn() {
        this.nNextColumn();
    }

    public int getColumnIndex() {
        return this.nGetColumnIndex();
    }

    public float getColumnWidth() {
        return this.nGetColumnWidth();
    }

    public float getColumnWidth(final int columnIndex) {
        return this.nGetColumnWidth(columnIndex);
    }

    public void setColumnWidth(final int columnIndex, final float width) {
        this.nSetColumnWidth(columnIndex, width);
    }

    public float getColumnOffset() {
        return this.nGetColumnOffset();
    }

    public float getColumnOffset(final int columnIndex) {
        return this.nGetColumnOffset(columnIndex);
    }

    public void setColumnOffset(final int columnIndex, final float offsetX) {
        this.nSetColumnOffset(columnIndex, offsetX);
    }

    public int getColumnsCount() {
        return this.nGetColumnsCount();
    }

    public boolean beginTabBar(final String strId) {
        return this.nBeginTabBar(strId);
    }

    public boolean beginTabBar(final String strId, final int flags) {
        return this.nBeginTabBar(strId, flags);
    }

    public void endTabBar() {
        this.nEndTabBar();
    }

    public boolean beginTabItem(final String label) {
        return this.nBeginTabItem(label);
    }

    public boolean beginTabItem(final String label, final imgui.type.ImBoolean pOpen) {
        return this.nBeginTabItem(label, pOpen.getData());
    }

    public boolean beginTabItem(final String label, final int flags) {
        return this.nBeginTabItem(label, flags);
    }

    public boolean beginTabItem(final String label, final imgui.type.ImBoolean pOpen, final int flags) {
        return this.nBeginTabItem(label, pOpen.getData(), flags);
    }

    public void endTabItem() {
        this.nEndTabItem();
    }

    public boolean tabItemButton(final String label) {
        return this.nTabItemButton(label);
    }

    public boolean tabItemButton(final String label, final int flags) {
        return this.nTabItemButton(label, flags);
    }

    public void setTabItemClosed(final String tabOrDockedWindowLabel) {
        this.nSetTabItemClosed(tabOrDockedWindowLabel);
    }

    public int dockSpace(final int id) {
        return this.nDockSpace(id);
    }

    public int dockSpace(final int id, final imgui.ImVec2 size) {
        return this.dockSpace(id, size.x, size.y);
    }

    public int dockSpace(final int id, final float sizeX, final float sizeY) {
        return this.nDockSpace(id, sizeX, sizeY);
    }

    public int dockSpace(final int id, final int flags) {
        return this.nDockSpace(id, flags);
    }

    public int dockSpace(final int id, final imgui.ImVec2 size, final int flags) {
        return this.dockSpace(id, size.x, size.y, flags);
    }

    public int dockSpace(final int id, final float sizeX, final float sizeY, final int flags) {
        return this.nDockSpace(id, sizeX, sizeY, flags);
    }

    public int dockSpace(final int id, final int flags, final imgui.ImGuiWindowClass windowClass) {
        return this.nDockSpace(id, flags, windowClass.ptr);
    }

    public int dockSpace(final int id, final imgui.ImVec2 size, final imgui.ImGuiWindowClass windowClass) {
        return this.dockSpace(id, size.x, size.y, windowClass);
    }

    public int dockSpace(final int id, final float sizeX, final float sizeY, final imgui.ImGuiWindowClass windowClass) {
        return this.nDockSpace(id, sizeX, sizeY, windowClass.ptr);
    }

    public int dockSpace(final int id, final imgui.ImVec2 size, final int flags, final imgui.ImGuiWindowClass windowClass) {
        return this.dockSpace(id, size.x, size.y, flags, windowClass);
    }

    public int dockSpace(final int id, final float sizeX, final float sizeY, final int flags, final imgui.ImGuiWindowClass windowClass) {
        return this.nDockSpace(id, sizeX, sizeY, flags, windowClass.ptr);
    }

    public int dockSpaceOverViewport() {
        return this.nDockSpaceOverViewport();
    }

    public int dockSpaceOverViewport(final imgui.ImGuiViewport viewport) {
        return this.nDockSpaceOverViewport(viewport.ptr);
    }

    public int dockSpaceOverViewport(final int flags) {
        return this.nDockSpaceOverViewport(flags);
    }

    public int dockSpaceOverViewport(final imgui.ImGuiViewport viewport, final int flags) {
        return this.nDockSpaceOverViewport(viewport.ptr, flags);
    }

    public int dockSpaceOverViewport(final int flags, final imgui.ImGuiWindowClass windowClass) {
        return this.nDockSpaceOverViewport(flags, windowClass.ptr);
    }

    public int dockSpaceOverViewport(final imgui.ImGuiViewport viewport, final imgui.ImGuiWindowClass windowClass) {
        return this.nDockSpaceOverViewport(viewport.ptr, windowClass.ptr);
    }

    public int dockSpaceOverViewport(final imgui.ImGuiViewport viewport, final int flags, final imgui.ImGuiWindowClass windowClass) {
        return this.nDockSpaceOverViewport(viewport.ptr, flags, windowClass.ptr);
    }

    public void setNextWindowDockID(final int dockId) {
        this.nSetNextWindowDockID(dockId);
    }

    public void setNextWindowDockID(final int dockId, final int cond) {
        this.nSetNextWindowDockID(dockId, cond);
    }

    public void setNextWindowClass(final imgui.ImGuiWindowClass windowClass) {
        this.nSetNextWindowClass(windowClass.ptr);
    }

    public int getWindowDockID() {
        return this.nGetWindowDockID();
    }

    public boolean isWindowDocked() {
        return this.nIsWindowDocked();
    }

    public void logToTTY() {
        this.nLogToTTY();
    }

    public void logToTTY(final int autoOpenDepth) {
        this.nLogToTTY(autoOpenDepth);
    }

    public void logToFile() {
        this.nLogToFile();
    }

    public void logToFile(final int autoOpenDepth) {
        this.nLogToFile(autoOpenDepth);
    }

    public void logToFile(final String filename) {
        this.nLogToFile(filename);
    }

    public void logToFile(final int autoOpenDepth, final String filename) {
        this.nLogToFile(autoOpenDepth, filename);
    }

    public void logToClipboard() {
        this.nLogToClipboard();
    }

    public void logToClipboard(final int autoOpenDepth) {
        this.nLogToClipboard(autoOpenDepth);
    }

    public void logFinish() {
        this.nLogFinish();
    }

    public void logButtons() {
        this.nLogButtons();
    }

    public void logText(final String fmt) {
        this.nLogText(fmt);
    }

    public boolean beginDragDropSource() {
        return this.nBeginDragDropSource();
    }

    public boolean beginDragDropSource(final int flags) {
        return this.nBeginDragDropSource(flags);
    }

    public boolean setDragDropPayload(final String dataType, final Object payload) {
        return this.setDragDropPayload(dataType, payload, imgui.flag.ImGuiCond.None);
    }

    public boolean setDragDropPayload(final String dataType, final Object payload, final int cond) {
        if (payloadRef == null || payloadRef.get() != payload) {
            payloadRef = new java.lang.ref.WeakReference<>(payload);
        }
        return this.nSetDragDropPayload(dataType, PAYLOAD_PLACEHOLDER_DATA, 1, cond);
    }

    public boolean setDragDropPayload(final Object payload) {
        return this.setDragDropPayload(payload, imgui.flag.ImGuiCond.None);
    }

    public boolean setDragDropPayload(final Object payload, final int cond) {
        return this.setDragDropPayload(String.valueOf(payload.getClass().hashCode()), payload, cond);
    }

    public void endDragDropSource() {
        this.nEndDragDropSource();
    }

    public boolean beginDragDropTarget() {
        return this.nBeginDragDropTarget();
    }

    public <T> T acceptDragDropPayload(final String dataType) {
        return this.acceptDragDropPayload(dataType, imgui.flag.ImGuiDragDropFlags.None);
    }

    public <T> T acceptDragDropPayload(final String dataType, final Class<T> aClass) {
        return this.acceptDragDropPayload(dataType, imgui.flag.ImGuiDragDropFlags.None, aClass);
    }

    public <T> T acceptDragDropPayload(final String dataType, final int flags) {
        return this.acceptDragDropPayload(dataType, flags, null);
    }

    public <T> T acceptDragDropPayload(final String dataType, final int flags, final Class<T> aClass) {
        if (payloadRef != null && this.nAcceptDragDropPayload(dataType, flags)) {
            final Object rawPayload = payloadRef.get();
            if (rawPayload != null) {
                if (aClass == null || rawPayload.getClass().isAssignableFrom(aClass)) {
                    return (T) rawPayload;
                }
            }
        }
        return null;
    }

    public <T> T acceptDragDropPayload(final Class<T> aClass) {
        return this.acceptDragDropPayload(String.valueOf(aClass.hashCode()), imgui.flag.ImGuiDragDropFlags.None, aClass);
    }

    public <T> T acceptDragDropPayload(final Class<T> aClass, final int flags) {
        return this.acceptDragDropPayload(String.valueOf(aClass.hashCode()), flags, aClass);
    }

    public void endDragDropTarget() {
        this.nEndDragDropTarget();
    }

    public <T> T getDragDropPayload() {
        if (payloadRef != null && this.nHasDragDropPayload()) {
            final Object rawPayload = payloadRef.get();
            if (rawPayload != null) {
                return (T) rawPayload;
            }
        }
        return null;
    }

    public <T> T getDragDropPayload(final String dataType) {
        if (payloadRef != null && this.nHasDragDropPayload(dataType)) {
            final Object rawPayload = payloadRef.get();
            if (rawPayload != null) {
                return (T) rawPayload;
            }
        }
        return null;
    }

    public <T> T getDragDropPayload(final Class<T> aClass) {
        return this.getDragDropPayload(String.valueOf(aClass.hashCode()));
    }

    public void beginDisabled() {
        this.nBeginDisabled();
    }

    public void beginDisabled(final boolean disabled) {
        this.nBeginDisabled(disabled);
    }

    public void endDisabled() {
        this.nEndDisabled();
    }

    public void pushClipRect(final imgui.ImVec2 clipRectMin, final imgui.ImVec2 clipRectMax, final boolean intersectWithCurrentClipRect) {
        this.pushClipRect(clipRectMin.x, clipRectMin.y, clipRectMax.x, clipRectMax.y, intersectWithCurrentClipRect);
    }

    public void pushClipRect(final float clipRectMinX, final float clipRectMinY, final float clipRectMaxX, final float clipRectMaxY, final boolean intersectWithCurrentClipRect) {
        this.nPushClipRect(clipRectMinX, clipRectMinY, clipRectMaxX, clipRectMaxY, intersectWithCurrentClipRect);
    }

    public void popClipRect() {
        this.nPopClipRect();
    }

    public void setItemDefaultFocus() {
        this.nSetItemDefaultFocus();
    }

    public void setKeyboardFocusHere() {
        this.nSetKeyboardFocusHere();
    }

    public void setKeyboardFocusHere(final int offset) {
        this.nSetKeyboardFocusHere(offset);
    }

    public boolean isItemHovered() {
        return this.nIsItemHovered();
    }

    public boolean isItemHovered(final int flags) {
        return this.nIsItemHovered(flags);
    }

    public boolean isItemActive() {
        return this.nIsItemActive();
    }

    public boolean isItemFocused() {
        return this.nIsItemFocused();
    }

    public boolean isItemClicked() {
        return this.nIsItemClicked();
    }

    public boolean isItemClicked(final int mouseButton) {
        return this.nIsItemClicked(mouseButton);
    }

    public boolean isItemVisible() {
        return this.nIsItemVisible();
    }

    public boolean isItemEdited() {
        return this.nIsItemEdited();
    }

    public boolean isItemActivated() {
        return this.nIsItemActivated();
    }

    public boolean isItemDeactivated() {
        return this.nIsItemDeactivated();
    }

    public boolean isItemDeactivatedAfterEdit() {
        return this.nIsItemDeactivatedAfterEdit();
    }

    public boolean isItemToggledOpen() {
        return this.nIsItemToggledOpen();
    }

    public boolean isAnyItemHovered() {
        return this.nIsAnyItemHovered();
    }

    public boolean isAnyItemActive() {
        return this.nIsAnyItemActive();
    }

    public boolean isAnyItemFocused() {
        return this.nIsAnyItemFocused();
    }

    public imgui.ImVec2 getItemRectMin() {
        final imgui.ImVec2 dst = new imgui.ImVec2();
        this.getItemRectMin(dst);
        return dst;
    }

    public void getItemRectMin(final imgui.ImVec2 dst) {
        dst.x = this.getItemRectMinX();
        dst.y = this.getItemRectMinY();
    }

    public imgui.ImVec2 getItemRectMax() {
        final imgui.ImVec2 dst = new imgui.ImVec2();
        this.getItemRectMax(dst);
        return dst;
    }

    public void getItemRectMax(final imgui.ImVec2 dst) {
        dst.x = this.getItemRectMaxX();
        dst.y = this.getItemRectMaxY();
    }

    public imgui.ImVec2 getItemRectSize() {
        final imgui.ImVec2 dst = new imgui.ImVec2();
        this.getItemRectSize(dst);
        return dst;
    }

    public void getItemRectSize(final imgui.ImVec2 dst) {
        dst.x = this.getItemRectSizeX();
        dst.y = this.getItemRectSizeY();
    }

    public void setItemAllowOverlap() {
        this.nSetItemAllowOverlap();
    }

    public imgui.ImGuiViewport getMainViewport() {
        __gen_getMainViewport_1.ptr = this.nGetMainViewport();
        return __gen_getMainViewport_1;
    }

    public boolean isRectVisible(final imgui.ImVec2 size) {
        return this.isRectVisible(size.x, size.y);
    }

    public boolean isRectVisible(final float sizeX, final float sizeY) {
        return this.nIsRectVisible(sizeX, sizeY);
    }

    public boolean isRectVisible(final imgui.ImVec2 rectMin, final imgui.ImVec2 rectMax) {
        return this.isRectVisible(rectMin.x, rectMin.y, rectMax.x, rectMax.y);
    }

    public boolean isRectVisible(final float rectMinX, final float rectMinY, final float rectMaxX, final float rectMaxY) {
        return this.nIsRectVisible(rectMinX, rectMinY, rectMaxX, rectMaxY);
    }

    public double getTime() {
        return this.nGetTime();
    }

    public int getFrameCount() {
        return this.nGetFrameCount();
    }

    public imgui.ImDrawList getBackgroundDrawList() {
        return new imgui.ImDrawList(this.nGetBackgroundDrawList());
    }

    public imgui.ImDrawList getBackgroundDrawList(final imgui.ImGuiViewport viewport) {
        return new imgui.ImDrawList(this.nGetBackgroundDrawList(viewport.ptr));
    }

    public imgui.ImDrawList getForegroundDrawList() {
        return new imgui.ImDrawList(this.nGetForegroundDrawList());
    }

    public imgui.ImDrawList getForegroundDrawList(final imgui.ImGuiViewport viewport) {
        return new imgui.ImDrawList(this.nGetForegroundDrawList(viewport.ptr));
    }

    public String getStyleColorName(final int idx) {
        return this.nGetStyleColorName(idx);
    }

    public void setStateStorage(final imgui.ImGuiStorage storage) {
        this.nSetStateStorage(storage.ptr);
    }

    public imgui.ImGuiStorage getStateStorage() {
        return new imgui.ImGuiStorage(this.nGetStateStorage());
    }

    public boolean beginChildFrame(final int id, final imgui.ImVec2 size) {
        return this.beginChildFrame(id, size.x, size.y);
    }

    public boolean beginChildFrame(final int id, final float sizeX, final float sizeY) {
        return this.nBeginChildFrame(id, sizeX, sizeY);
    }

    public boolean beginChildFrame(final int id, final imgui.ImVec2 size, final int flags) {
        return this.beginChildFrame(id, size.x, size.y, flags);
    }

    public boolean beginChildFrame(final int id, final float sizeX, final float sizeY, final int flags) {
        return this.nBeginChildFrame(id, sizeX, sizeY, flags);
    }

    public void endChildFrame() {
        this.nEndChildFrame();
    }

    public imgui.ImVec2 calcTextSize(final String text) {
        final imgui.ImVec2 dst = new imgui.ImVec2();
        this.calcTextSize(dst, text);
        return dst;
    }

    public void calcTextSize(final imgui.ImVec2 dst, final String text) {
        dst.x = this.calcTextSizeX(text);
        dst.y = this.calcTextSizeY(text);
    }

    public imgui.ImVec2 calcTextSize(final String text, final String textEnd) {
        final imgui.ImVec2 dst = new imgui.ImVec2();
        this.calcTextSize(dst, text, textEnd);
        return dst;
    }

    public void calcTextSize(final imgui.ImVec2 dst, final String text, final String textEnd) {
        dst.x = this.calcTextSizeX(text, textEnd);
        dst.y = this.calcTextSizeY(text, textEnd);
    }

    public imgui.ImVec2 calcTextSize(final String text, final String textEnd, final boolean hideTextAfterDoubleHash) {
        final imgui.ImVec2 dst = new imgui.ImVec2();
        this.calcTextSize(dst, text, textEnd, hideTextAfterDoubleHash);
        return dst;
    }

    public void calcTextSize(final imgui.ImVec2 dst, final String text, final String textEnd, final boolean hideTextAfterDoubleHash) {
        dst.x = this.calcTextSizeX(text, textEnd, hideTextAfterDoubleHash);
        dst.y = this.calcTextSizeY(text, textEnd, hideTextAfterDoubleHash);
    }

    public imgui.ImVec2 calcTextSize(final String text, final String textEnd, final float wrapWidth) {
        final imgui.ImVec2 dst = new imgui.ImVec2();
        this.calcTextSize(dst, text, textEnd, wrapWidth);
        return dst;
    }

    public void calcTextSize(final imgui.ImVec2 dst, final String text, final String textEnd, final float wrapWidth) {
        dst.x = this.calcTextSizeX(text, textEnd, wrapWidth);
        dst.y = this.calcTextSizeY(text, textEnd, wrapWidth);
    }

    public imgui.ImVec2 calcTextSize(final String text, final String textEnd, final boolean hideTextAfterDoubleHash, final float wrapWidth) {
        final imgui.ImVec2 dst = new imgui.ImVec2();
        this.calcTextSize(dst, text, textEnd, hideTextAfterDoubleHash, wrapWidth);
        return dst;
    }

    public void calcTextSize(final imgui.ImVec2 dst, final String text, final String textEnd, final boolean hideTextAfterDoubleHash, final float wrapWidth) {
        dst.x = this.calcTextSizeX(text, textEnd, hideTextAfterDoubleHash, wrapWidth);
        dst.y = this.calcTextSizeY(text, textEnd, hideTextAfterDoubleHash, wrapWidth);
    }

    public imgui.ImVec4 colorConvertU32ToFloat4(final int in) {
        final imgui.ImVec4 dst = new imgui.ImVec4();
        this.colorConvertU32ToFloat4(dst, in);
        return dst;
    }

    public void colorConvertU32ToFloat4(final imgui.ImVec4 dst, final int in) {
        dst.x = this.colorConvertU32ToFloat4X(in);
        dst.y = this.colorConvertU32ToFloat4Y(in);
        dst.z = this.colorConvertU32ToFloat4Z(in);
        dst.w = this.colorConvertU32ToFloat4W(in);
    }

    public int colorConvertFloat4ToU32(final imgui.ImVec4 in) {
        return this.colorConvertFloat4ToU32(in.x, in.y, in.z, in.w);
    }

    public int colorConvertFloat4ToU32(final float inX, final float inY, final float inZ, final float inW) {
        return this.nColorConvertFloat4ToU32(inX, inY, inZ, inW);
    }

    public void colorConvertRGBtoHSV(final float[] rgb, final float[] hsv) {
        this.nColorConvertRGBtoHSV(rgb, hsv);
    }

    public void colorConvertHSVtoRGB(final float[] hsv, final float[] rgb) {
        this.nColorConvertHSVtoRGB(hsv, rgb);
    }

    public int getKeyIndex(final int imguiKey) {
        return this.nGetKeyIndex(imguiKey);
    }

    public boolean isKeyDown(final int userKeyIndex) {
        return this.nIsKeyDown(userKeyIndex);
    }

    public boolean isKeyPressed(final int userKeyIndex) {
        return this.nIsKeyPressed(userKeyIndex);
    }

    public boolean isKeyPressed(final int userKeyIndex, final boolean repeat) {
        return this.nIsKeyPressed(userKeyIndex, repeat);
    }

    public boolean isKeyReleased(final int userKeyIndex) {
        return this.nIsKeyReleased(userKeyIndex);
    }

    public int getKeyPressedAmount(final int keyIndex, final float repeatDelay, final float rate) {
        return this.nGetKeyPressedAmount(keyIndex, repeatDelay, rate);
    }

    public void captureKeyboardFromApp() {
        this.nCaptureKeyboardFromApp();
    }

    public void captureKeyboardFromApp(final boolean wantCaptureKeyboardValue) {
        this.nCaptureKeyboardFromApp(wantCaptureKeyboardValue);
    }

    public boolean isMouseDown(final int button) {
        return this.nIsMouseDown(button);
    }

    public boolean isMouseClicked(final int button) {
        return this.nIsMouseClicked(button);
    }

    public boolean isMouseClicked(final int button, final boolean repeat) {
        return this.nIsMouseClicked(button, repeat);
    }

    public boolean isMouseReleased(final int button) {
        return this.nIsMouseReleased(button);
    }

    public boolean isMouseDoubleClicked(final int button) {
        return this.nIsMouseDoubleClicked(button);
    }

    public int getMouseClickedCount(final int button) {
        return this.nGetMouseClickedCount(button);
    }

    public boolean isMouseHoveringRect(final imgui.ImVec2 rMin, final imgui.ImVec2 rMax) {
        return this.isMouseHoveringRect(rMin.x, rMin.y, rMax.x, rMax.y);
    }

    public boolean isMouseHoveringRect(final float rMinX, final float rMinY, final float rMaxX, final float rMaxY) {
        return this.nIsMouseHoveringRect(rMinX, rMinY, rMaxX, rMaxY);
    }

    public boolean isMouseHoveringRect(final imgui.ImVec2 rMin, final imgui.ImVec2 rMax, final boolean clip) {
        return this.isMouseHoveringRect(rMin.x, rMin.y, rMax.x, rMax.y, clip);
    }

    public boolean isMouseHoveringRect(final float rMinX, final float rMinY, final float rMaxX, final float rMaxY, final boolean clip) {
        return this.nIsMouseHoveringRect(rMinX, rMinY, rMaxX, rMaxY, clip);
    }

    public boolean isMousePosValid(final imgui.ImVec2 mousePos) {
        return this.isMousePosValid(mousePos.x, mousePos.y);
    }

    public boolean isMousePosValid(final float mousePosX, final float mousePosY) {
        return this.nIsMousePosValid(mousePosX, mousePosY);
    }

    public boolean isAnyMouseDown() {
        return this.nIsAnyMouseDown();
    }

    public imgui.ImVec2 getMousePos() {
        final imgui.ImVec2 dst = new imgui.ImVec2();
        this.getMousePos(dst);
        return dst;
    }

    public void getMousePos(final imgui.ImVec2 dst) {
        dst.x = this.getMousePosX();
        dst.y = this.getMousePosY();
    }

    public imgui.ImVec2 getMousePosOnOpeningCurrentPopup() {
        final imgui.ImVec2 dst = new imgui.ImVec2();
        this.getMousePosOnOpeningCurrentPopup(dst);
        return dst;
    }

    public void getMousePosOnOpeningCurrentPopup(final imgui.ImVec2 dst) {
        dst.x = this.getMousePosOnOpeningCurrentPopupX();
        dst.y = this.getMousePosOnOpeningCurrentPopupY();
    }

    public boolean isMouseDragging(final int button) {
        return this.nIsMouseDragging(button);
    }

    public boolean isMouseDragging(final int button, final float lockThreshold) {
        return this.nIsMouseDragging(button, lockThreshold);
    }

    public imgui.ImVec2 getMouseDragDelta() {
        final imgui.ImVec2 dst = new imgui.ImVec2();
        this.getMouseDragDelta(dst);
        return dst;
    }

    public void getMouseDragDelta(final imgui.ImVec2 dst) {
        dst.x = this.getMouseDragDeltaX();
        dst.y = this.getMouseDragDeltaY();
    }

    public imgui.ImVec2 getMouseDragDelta(final int button) {
        final imgui.ImVec2 dst = new imgui.ImVec2();
        this.getMouseDragDelta(dst, button);
        return dst;
    }

    public void getMouseDragDelta(final imgui.ImVec2 dst, final int button) {
        dst.x = this.getMouseDragDeltaX(button);
        dst.y = this.getMouseDragDeltaY(button);
    }

    public imgui.ImVec2 getMouseDragDelta(final float lockThreshold) {
        final imgui.ImVec2 dst = new imgui.ImVec2();
        this.getMouseDragDelta(dst, lockThreshold);
        return dst;
    }

    public void getMouseDragDelta(final imgui.ImVec2 dst, final float lockThreshold) {
        dst.x = this.getMouseDragDeltaX(lockThreshold);
        dst.y = this.getMouseDragDeltaY(lockThreshold);
    }

    public imgui.ImVec2 getMouseDragDelta(final int button, final float lockThreshold) {
        final imgui.ImVec2 dst = new imgui.ImVec2();
        this.getMouseDragDelta(dst, button, lockThreshold);
        return dst;
    }

    public void getMouseDragDelta(final imgui.ImVec2 dst, final int button, final float lockThreshold) {
        dst.x = this.getMouseDragDeltaX(button, lockThreshold);
        dst.y = this.getMouseDragDeltaY(button, lockThreshold);
    }

    public void resetMouseDragDelta() {
        this.nResetMouseDragDelta();
    }

    public void resetMouseDragDelta(final int button) {
        this.nResetMouseDragDelta(button);
    }

    public int getMouseCursor() {
        return this.nGetMouseCursor();
    }

    public void setMouseCursor(final int cursorType) {
        this.nSetMouseCursor(cursorType);
    }

    public void captureMouseFromApp() {
        this.nCaptureMouseFromApp();
    }

    public void captureMouseFromApp(final boolean wantCaptureMouseValue) {
        this.nCaptureMouseFromApp(wantCaptureMouseValue);
    }

    public String getClipboardText() {
        return this.nGetClipboardText();
    }

    public void setClipboardText(final String text) {
        this.nSetClipboardText(text);
    }

    public void loadIniSettingsFromDisk(final String iniFilename) {
        this.nLoadIniSettingsFromDisk(iniFilename);
    }

    public void loadIniSettingsFromMemory(final String iniData) {
        this.nLoadIniSettingsFromMemory(iniData);
    }

    public void loadIniSettingsFromMemory(final String iniData, final long iniSize) {
        this.nLoadIniSettingsFromMemory(iniData, iniSize);
    }

    public void saveIniSettingsToDisk(final String iniFilename) {
        this.nSaveIniSettingsToDisk(iniFilename);
    }

    public void saveIniSettingsToMemory() {
        this.nSaveIniSettingsToMemory();
    }

    public void saveIniSettingsToMemory(final int outIniSize) {
        this.nSaveIniSettingsToMemory(outIniSize);
    }

    public boolean debugCheckVersionAndDataLayout(final String versionStr, final long szIo, final long szStyle, final long szVec2, final long szVec4, final long szDrawvert, final long szDrawidx) {
        return this.nDebugCheckVersionAndDataLayout(versionStr, szIo, szStyle, szVec2, szVec4, szDrawvert, szDrawidx);
    }

    public imgui.ImGuiPlatformIO getPlatformIO() {
        __gen_getPlatformIO_1.ptr = this.nGetPlatformIO();
        return __gen_getPlatformIO_1;
    }

    public void updatePlatformWindows() {
        this.nUpdatePlatformWindows();
    }

    public void renderPlatformWindowsDefault() {
        this.nRenderPlatformWindowsDefault();
    }

    public void destroyPlatformWindows() {
        this.nDestroyPlatformWindows();
    }

    public imgui.ImGuiViewport findViewportByID(final int id) {
        return new imgui.ImGuiViewport(this.nFindViewportByID(id));
    }

    public imgui.ImGuiViewport findViewportByPlatformHandle(final long platformHandle) {
        return new imgui.ImGuiViewport(this.nFindViewportByPlatformHandle(platformHandle));
    }

    public int getColorU32i(final int col) {
        return this.nGetColorU32i(col);
    }

    private boolean preInputText(final boolean multiline, final String label, final String hint, final imgui.type.ImString text, final float width, final float height, final int flags, final imgui.callback.ImGuiInputTextCallback callback) {
        final imgui.type.ImString.InputData inputData = text.inputData;
        int inputFlags = flags;
    
        if (inputData.isResizable) {
            inputFlags |= imgui.flag.ImGuiInputTextFlags.CallbackResize;
        }
    
        if (!inputData.allowedChars.isEmpty()) {
            inputFlags |= imgui.flag.ImGuiInputTextFlags.CallbackCharFilter;
        }
    
        String hintLabel = hint;
        if (hintLabel == null) {
            hintLabel = "";
        }
    
        return this.nInputText(
            multiline,
            hint != null,
            label,
            hintLabel,
            text,
            text.getData(),
            text.getData().length,
            width,
            height,
            inputFlags,
            inputData,
            inputData.allowedChars,
            callback
        );
    }

    public float getWindowPosX() {
        return this.nGetWindowPosX();
    }

    public float getWindowPosY() {
        return this.nGetWindowPosY();
    }

    public float getWindowSizeX() {
        return this.nGetWindowSizeX();
    }

    public float getWindowSizeY() {
        return this.nGetWindowSizeY();
    }

    public float getContentRegionAvailX() {
        return this.nGetContentRegionAvailX();
    }

    public float getContentRegionAvailY() {
        return this.nGetContentRegionAvailY();
    }

    public float getContentRegionMaxX() {
        return this.nGetContentRegionMaxX();
    }

    public float getContentRegionMaxY() {
        return this.nGetContentRegionMaxY();
    }

    public float getWindowContentRegionMinX() {
        return this.nGetWindowContentRegionMinX();
    }

    public float getWindowContentRegionMinY() {
        return this.nGetWindowContentRegionMinY();
    }

    public float getWindowContentRegionMaxX() {
        return this.nGetWindowContentRegionMaxX();
    }

    public float getWindowContentRegionMaxY() {
        return this.nGetWindowContentRegionMaxY();
    }

    public float getFontTexUvWhitePixelX() {
        return this.nGetFontTexUvWhitePixelX();
    }

    public float getFontTexUvWhitePixelY() {
        return this.nGetFontTexUvWhitePixelY();
    }

    public float getStyleColorVec4X(final int idx) {
        return this.nGetStyleColorVec4X(idx);
    }

    public float getStyleColorVec4Y(final int idx) {
        return this.nGetStyleColorVec4Y(idx);
    }

    public float getStyleColorVec4Z(final int idx) {
        return this.nGetStyleColorVec4Z(idx);
    }

    public float getStyleColorVec4W(final int idx) {
        return this.nGetStyleColorVec4W(idx);
    }

    public float getCursorStartPosX() {
        return this.nGetCursorStartPosX();
    }

    public float getCursorStartPosY() {
        return this.nGetCursorStartPosY();
    }

    public float getCursorScreenPosX() {
        return this.nGetCursorScreenPosX();
    }

    public float getCursorScreenPosY() {
        return this.nGetCursorScreenPosY();
    }

    public float getItemRectMinX() {
        return this.nGetItemRectMinX();
    }

    public float getItemRectMinY() {
        return this.nGetItemRectMinY();
    }

    public float getItemRectMaxX() {
        return this.nGetItemRectMaxX();
    }

    public float getItemRectMaxY() {
        return this.nGetItemRectMaxY();
    }

    public float getItemRectSizeX() {
        return this.nGetItemRectSizeX();
    }

    public float getItemRectSizeY() {
        return this.nGetItemRectSizeY();
    }

    public float calcTextSizeX(final String text) {
        return this.nCalcTextSizeX(text);
    }

    public float calcTextSizeY(final String text) {
        return this.nCalcTextSizeY(text);
    }

    public float calcTextSizeX(final String text, final String textEnd) {
        return this.nCalcTextSizeX(text, textEnd);
    }

    public float calcTextSizeY(final String text, final String textEnd) {
        return this.nCalcTextSizeY(text, textEnd);
    }

    public float calcTextSizeX(final String text, final String textEnd, final boolean hideTextAfterDoubleHash) {
        return this.nCalcTextSizeX(text, textEnd, hideTextAfterDoubleHash);
    }

    public float calcTextSizeY(final String text, final String textEnd, final boolean hideTextAfterDoubleHash) {
        return this.nCalcTextSizeY(text, textEnd, hideTextAfterDoubleHash);
    }

    public float calcTextSizeX(final String text, final String textEnd, final float wrapWidth) {
        return this.nCalcTextSizeX(text, textEnd, wrapWidth);
    }

    public float calcTextSizeY(final String text, final String textEnd, final float wrapWidth) {
        return this.nCalcTextSizeY(text, textEnd, wrapWidth);
    }

    public float calcTextSizeX(final String text, final String textEnd, final boolean hideTextAfterDoubleHash, final float wrapWidth) {
        return this.nCalcTextSizeX(text, textEnd, hideTextAfterDoubleHash, wrapWidth);
    }

    public float calcTextSizeY(final String text, final String textEnd, final boolean hideTextAfterDoubleHash, final float wrapWidth) {
        return this.nCalcTextSizeY(text, textEnd, hideTextAfterDoubleHash, wrapWidth);
    }

    public float colorConvertU32ToFloat4X(final int in) {
        return this.nColorConvertU32ToFloat4X(in);
    }

    public float colorConvertU32ToFloat4Y(final int in) {
        return this.nColorConvertU32ToFloat4Y(in);
    }

    public float colorConvertU32ToFloat4Z(final int in) {
        return this.nColorConvertU32ToFloat4Z(in);
    }

    public float colorConvertU32ToFloat4W(final int in) {
        return this.nColorConvertU32ToFloat4W(in);
    }

    public float getMousePosX() {
        return this.nGetMousePosX();
    }

    public float getMousePosY() {
        return this.nGetMousePosY();
    }

    public float getMousePosOnOpeningCurrentPopupX() {
        return this.nGetMousePosOnOpeningCurrentPopupX();
    }

    public float getMousePosOnOpeningCurrentPopupY() {
        return this.nGetMousePosOnOpeningCurrentPopupY();
    }

    public float getMouseDragDeltaX() {
        return this.nGetMouseDragDeltaX();
    }

    public float getMouseDragDeltaY() {
        return this.nGetMouseDragDeltaY();
    }

    public float getMouseDragDeltaX(final int button) {
        return this.nGetMouseDragDeltaX(button);
    }

    public float getMouseDragDeltaY(final int button) {
        return this.nGetMouseDragDeltaY(button);
    }

    public float getMouseDragDeltaX(final float lockThreshold) {
        return this.nGetMouseDragDeltaX(lockThreshold);
    }

    public float getMouseDragDeltaY(final float lockThreshold) {
        return this.nGetMouseDragDeltaY(lockThreshold);
    }

    public float getMouseDragDeltaX(final int button, final float lockThreshold) {
        return this.nGetMouseDragDeltaX(button, lockThreshold);
    }

    public float getMouseDragDeltaY(final int button, final float lockThreshold) {
        return this.nGetMouseDragDeltaY(button, lockThreshold);
    }

    private native long nCreateContext(); /*
        return (intptr_t)ImGui::CreateContext();
    */

    private native long nCreateContext(long sharedFontAtlas); /*
        return (intptr_t)ImGui::CreateContext((ImFontAtlas *)sharedFontAtlas);
    */

    private native void nDestroyContext(); /*
        ImGui::DestroyContext();
    */

    private native void nDestroyContext(long ctx); /*
        ImGui::DestroyContext((ImGuiContext *)ctx);
    */

    private native long nGetCurrentContext(); /*
        return (intptr_t)ImGui::GetCurrentContext();
    */

    private native void nSetCurrentContext(long ctx); /*
        ImGui::SetCurrentContext((ImGuiContext *)ctx);
    */

    private native long nGetIO(); /*
        return (intptr_t)&ImGui::GetIO();
    */

    private native long nGetStyle(); /*
        return (intptr_t)&ImGui::GetStyle();
    */

    private native void nNewFrame(); /*
        ImGui::NewFrame();
    */

    private native void nEndFrame(); /*
        ImGui::EndFrame();
    */

    private native void nRender(); /*
        ImGui::Render();
    */

    private native long nGetDrawData(); /*
        return (intptr_t)ImGui::GetDrawData();
    */

    private native void nShowDemoWindow(); /*
        ImGui::ShowDemoWindow();
    */

    private native void nShowDemoWindow(boolean[] pOpen); /*
        ImGui::ShowDemoWindow((bool *)&pOpen[0]);
    */

    private native void nShowMetricsWindow(); /*
        ImGui::ShowMetricsWindow();
    */

    private native void nShowMetricsWindow(boolean[] pOpen); /*
        ImGui::ShowMetricsWindow((bool *)&pOpen[0]);
    */

    private native void nShowStackToolWindow(); /*
        ImGui::ShowStackToolWindow();
    */

    private native void nShowStackToolWindow(boolean[] pOpen); /*
        ImGui::ShowStackToolWindow((bool *)&pOpen[0]);
    */

    private native void nShowAboutWindow(); /*
        ImGui::ShowAboutWindow();
    */

    private native void nShowAboutWindow(boolean[] pOpen); /*
        ImGui::ShowAboutWindow((bool *)&pOpen[0]);
    */

    private native void nShowStyleEditor(); /*
        ImGui::ShowStyleEditor();
    */

    private native void nShowStyleEditor(long ref); /*
        ImGui::ShowStyleEditor((ImGuiStyle *)ref);
    */

    private native boolean nShowStyleSelector(String label); /*
        return ImGui::ShowStyleSelector((const char *)label);
    */

    private native void nShowFontSelector(String label); /*
        ImGui::ShowFontSelector((const char *)label);
    */

    private native void nShowUserGuide(); /*
        ImGui::ShowUserGuide();
    */

    private native String nGetVersion(); /*
        return env->NewStringUTF(ImGui::GetVersion());
    */

    private native void nStyleColorsDark(); /*
        ImGui::StyleColorsDark();
    */

    private native void nStyleColorsDark(long dst); /*
        ImGui::StyleColorsDark((ImGuiStyle *)dst);
    */

    private native void nStyleColorsLight(); /*
        ImGui::StyleColorsLight();
    */

    private native void nStyleColorsLight(long dst); /*
        ImGui::StyleColorsLight((ImGuiStyle *)dst);
    */

    private native void nStyleColorsClassic(); /*
        ImGui::StyleColorsClassic();
    */

    private native void nStyleColorsClassic(long dst); /*
        ImGui::StyleColorsClassic((ImGuiStyle *)dst);
    */

    private native boolean nBegin(String name); /*
        return ImGui::Begin((const char *)name);
    */

    private native boolean nBegin(String name, boolean[] pOpen); /*
        return ImGui::Begin((const char *)name, (bool *)&pOpen[0]);
    */

    private native boolean nBegin(String name, int flags); /*
        return ImGui::Begin((const char *)name, NULL, (ImGuiWindowFlags)flags);
    */

    private native boolean nBegin(String name, boolean[] pOpen, int flags); /*
        return ImGui::Begin((const char *)name, (bool *)&pOpen[0], (ImGuiWindowFlags)flags);
    */

    private native void nEnd(); /*
        ImGui::End();
    */

    private native boolean nBeginChild(String strId); /*
        return ImGui::BeginChild((const char *)strId);
    */

    private native boolean nBeginChild(String strId, float sizeX, float sizeY); /*
        return ImGui::BeginChild((const char *)strId, (const ImVec2 &)ImVec2(sizeX, sizeY));
    */

    private native boolean nBeginChild(String strId, boolean border); /*
        return ImGui::BeginChild((const char *)strId, ImVec2(0, 0), (bool)border);
    */

    private native boolean nBeginChild(String strId, float sizeX, float sizeY, boolean border); /*
        return ImGui::BeginChild((const char *)strId, (const ImVec2 &)ImVec2(sizeX, sizeY), (bool)border);
    */

    private native boolean nBeginChild(String strId, boolean border, int flags); /*
        return ImGui::BeginChild((const char *)strId, ImVec2(0, 0), (bool)border, (ImGuiWindowFlags)flags);
    */

    private native boolean nBeginChild(String strId, float sizeX, float sizeY, int flags); /*
        return ImGui::BeginChild((const char *)strId, (const ImVec2 &)ImVec2(sizeX, sizeY), false, (ImGuiWindowFlags)flags);
    */

    private native boolean nBeginChild(String strId, float sizeX, float sizeY, boolean border, int flags); /*
        return ImGui::BeginChild((const char *)strId, (const ImVec2 &)ImVec2(sizeX, sizeY), (bool)border, (ImGuiWindowFlags)flags);
    */

    private native boolean nBeginChild(int id); /*
        return ImGui::BeginChild((ImGuiID)id);
    */

    private native boolean nBeginChild(int id, float sizeX, float sizeY); /*
        return ImGui::BeginChild((ImGuiID)id, (const ImVec2 &)ImVec2(sizeX, sizeY));
    */

    private native boolean nBeginChild(int id, boolean border); /*
        return ImGui::BeginChild((ImGuiID)id, ImVec2(0, 0), (bool)border);
    */

    private native boolean nBeginChild(int id, float sizeX, float sizeY, boolean border); /*
        return ImGui::BeginChild((ImGuiID)id, (const ImVec2 &)ImVec2(sizeX, sizeY), (bool)border);
    */

    private native boolean nBeginChild(int id, boolean border, int flags); /*
        return ImGui::BeginChild((ImGuiID)id, ImVec2(0, 0), (bool)border, (ImGuiWindowFlags)flags);
    */

    private native boolean nBeginChild(int id, float sizeX, float sizeY, int flags); /*
        return ImGui::BeginChild((ImGuiID)id, (const ImVec2 &)ImVec2(sizeX, sizeY), false, (ImGuiWindowFlags)flags);
    */

    private native boolean nBeginChild(int id, float sizeX, float sizeY, boolean border, int flags); /*
        return ImGui::BeginChild((ImGuiID)id, (const ImVec2 &)ImVec2(sizeX, sizeY), (bool)border, (ImGuiWindowFlags)flags);
    */

    private native void nEndChild(); /*
        ImGui::EndChild();
    */

    private native boolean nIsWindowAppearing(); /*
        return ImGui::IsWindowAppearing();
    */

    private native boolean nIsWindowCollapsed(); /*
        return ImGui::IsWindowCollapsed();
    */

    private native boolean nIsWindowFocused(); /*
        return ImGui::IsWindowFocused();
    */

    private native boolean nIsWindowFocused(int flags); /*
        return ImGui::IsWindowFocused((ImGuiFocusedFlags)flags);
    */

    private native boolean nIsWindowHovered(); /*
        return ImGui::IsWindowHovered();
    */

    private native boolean nIsWindowHovered(int flags); /*
        return ImGui::IsWindowHovered((ImGuiHoveredFlags)flags);
    */

    private native long nGetWindowDrawList(); /*
        return (intptr_t)ImGui::GetWindowDrawList();
    */

    private native float nGetWindowDpiScale(); /*
        return ImGui::GetWindowDpiScale();
    */

    private native float nGetWindowWidth(); /*
        return ImGui::GetWindowWidth();
    */

    private native float nGetWindowHeight(); /*
        return ImGui::GetWindowHeight();
    */

    private native long nGetWindowViewport(); /*
        return (intptr_t)ImGui::GetWindowViewport();
    */

    private native void nSetNextWindowPos(float posX, float posY); /*
        ImGui::SetNextWindowPos((const ImVec2 &)ImVec2(posX, posY));
    */

    private native void nSetNextWindowPos(float posX, float posY, int cond); /*
        ImGui::SetNextWindowPos((const ImVec2 &)ImVec2(posX, posY), (ImGuiCond)cond);
    */

    private native void nSetNextWindowPos(float posX, float posY, float pivotX, float pivotY); /*
        ImGui::SetNextWindowPos((const ImVec2 &)ImVec2(posX, posY), 0, (const ImVec2 &)ImVec2(pivotX, pivotY));
    */

    private native void nSetNextWindowPos(float posX, float posY, int cond, float pivotX, float pivotY); /*
        ImGui::SetNextWindowPos((const ImVec2 &)ImVec2(posX, posY), (ImGuiCond)cond, (const ImVec2 &)ImVec2(pivotX, pivotY));
    */

    private native void nSetNextWindowSize(float sizeX, float sizeY); /*
        ImGui::SetNextWindowSize((const ImVec2 &)ImVec2(sizeX, sizeY));
    */

    private native void nSetNextWindowSize(float sizeX, float sizeY, int cond); /*
        ImGui::SetNextWindowSize((const ImVec2 &)ImVec2(sizeX, sizeY), (ImGuiCond)cond);
    */

    private native void nSetNextWindowSizeConstraints(float sizeMinX, float sizeMinY, float sizeMaxX, float sizeMaxY); /*
        ImGui::SetNextWindowSizeConstraints(ImVec2(sizeMinX, sizeMinY), ImVec2(sizeMaxX, sizeMaxY));
    */

    private native void nSetNextWindowContentSize(float sizeX, float sizeY); /*
        ImGui::SetNextWindowContentSize((const ImVec2 &)ImVec2(sizeX, sizeY));
    */

    private native void nSetNextWindowCollapsed(boolean collapsed); /*
        ImGui::SetNextWindowCollapsed((bool)collapsed);
    */

    private native void nSetNextWindowCollapsed(boolean collapsed, int cond); /*
        ImGui::SetNextWindowCollapsed((bool)collapsed, (ImGuiCond)cond);
    */

    private native void nSetNextWindowFocus(); /*
        ImGui::SetNextWindowFocus();
    */

    private native void nSetNextWindowBgAlpha(float alpha); /*
        ImGui::SetNextWindowBgAlpha((float)alpha);
    */

    private native void nSetNextWindowViewport(int viewportId); /*
        ImGui::SetNextWindowViewport((ImGuiID)viewportId);
    */

    private native void nSetWindowFontScale(float scale); /*
        ImGui::SetWindowFontScale((float)scale);
    */

    private native void nSetWindowPos(float posX, float posY); /*
        ImGui::SetWindowPos((const ImVec2 &)ImVec2(posX, posY));
    */

    private native void nSetWindowPos(float posX, float posY, int cond); /*
        ImGui::SetWindowPos((const ImVec2 &)ImVec2(posX, posY), (ImGuiCond)cond);
    */

    private native void nSetWindowPos(String name, float posX, float posY); /*
        ImGui::SetWindowPos((const char *)name, (const ImVec2 &)ImVec2(posX, posY));
    */

    private native void nSetWindowPos(String name, float posX, float posY, int cond); /*
        ImGui::SetWindowPos((const char *)name, (const ImVec2 &)ImVec2(posX, posY), (ImGuiCond)cond);
    */

    private native void nSetWindowSize(float sizeX, float sizeY); /*
        ImGui::SetWindowSize((const ImVec2 &)ImVec2(sizeX, sizeY));
    */

    private native void nSetWindowSize(float sizeX, float sizeY, int cond); /*
        ImGui::SetWindowSize((const ImVec2 &)ImVec2(sizeX, sizeY), (ImGuiCond)cond);
    */

    private native void nSetWindowSize(String name, float sizeX, float sizeY); /*
        ImGui::SetWindowSize((const char *)name, (const ImVec2 &)ImVec2(sizeX, sizeY));
    */

    private native void nSetWindowSize(String name, float sizeX, float sizeY, int cond); /*
        ImGui::SetWindowSize((const char *)name, (const ImVec2 &)ImVec2(sizeX, sizeY), (ImGuiCond)cond);
    */

    private native void nSetWindowCollapsed(boolean collapsed); /*
        ImGui::SetWindowCollapsed((bool)collapsed);
    */

    private native void nSetWindowCollapsed(boolean collapsed, int cond); /*
        ImGui::SetWindowCollapsed((bool)collapsed, (ImGuiCond)cond);
    */

    private native void nSetWindowCollapsed(String name, boolean collapsed); /*
        ImGui::SetWindowCollapsed((const char *)name, (bool)collapsed);
    */

    private native void nSetWindowCollapsed(String name, boolean collapsed, int cond); /*
        ImGui::SetWindowCollapsed((const char *)name, (bool)collapsed, (ImGuiCond)cond);
    */

    private native void nSetWindowFocus(); /*
        ImGui::SetWindowFocus();
    */

    private native void nSetWindowFocus(String name); /*
        ImGui::SetWindowFocus((const char *)name);
    */

    private native float nGetScrollX(); /*
        return ImGui::GetScrollX();
    */

    private native float nGetScrollY(); /*
        return ImGui::GetScrollY();
    */

    private native void nSetScrollX(float scrollX); /*
        ImGui::SetScrollX((float)scrollX);
    */

    private native void nSetScrollY(float scrollY); /*
        ImGui::SetScrollY((float)scrollY);
    */

    private native float nGetScrollMaxX(); /*
        return ImGui::GetScrollMaxX();
    */

    private native float nGetScrollMaxY(); /*
        return ImGui::GetScrollMaxY();
    */

    private native void nSetScrollHereX(); /*
        ImGui::SetScrollHereX();
    */

    private native void nSetScrollHereX(float centerXRatio); /*
        ImGui::SetScrollHereX((float)centerXRatio);
    */

    private native void nSetScrollHereY(); /*
        ImGui::SetScrollHereY();
    */

    private native void nSetScrollHereY(float centerYRatio); /*
        ImGui::SetScrollHereY((float)centerYRatio);
    */

    private native void nSetScrollFromPosX(float localX); /*
        ImGui::SetScrollFromPosX((float)localX);
    */

    private native void nSetScrollFromPosX(float localX, float centerXRatio); /*
        ImGui::SetScrollFromPosX((float)localX, (float)centerXRatio);
    */

    private native void nSetScrollFromPosY(float localY); /*
        ImGui::SetScrollFromPosY((float)localY);
    */

    private native void nSetScrollFromPosY(float localY, float centerYRatio); /*
        ImGui::SetScrollFromPosY((float)localY, (float)centerYRatio);
    */

    private native void nPushFont(long font); /*
        ImGui::PushFont((ImFont *)font);
    */

    private native void nPopFont(); /*
        ImGui::PopFont();
    */

    private native void nPushStyleColor(int idx, int col); /*
        ImGui::PushStyleColor((ImGuiCol)idx, (ImU32)col);
    */

    private native void nPushStyleColor(int idx, float colX, float colY, float colZ, float colW); /*
        ImGui::PushStyleColor((ImGuiCol)idx, (const ImVec4 &)ImVec4(colX, colY, colZ, colW));
    */

    private native void nPopStyleColor(); /*
        ImGui::PopStyleColor();
    */

    private native void nPopStyleColor(int count); /*
        ImGui::PopStyleColor((int)count);
    */

    private native void nPushStyleVar(int idx, float val); /*
        ImGui::PushStyleVar((ImGuiStyleVar)idx, (float)val);
    */

    private native void nPushStyleVar(int idx, float valX, float valY); /*
        ImGui::PushStyleVar((ImGuiStyleVar)idx, (const ImVec2 &)ImVec2(valX, valY));
    */

    private native void nPopStyleVar(); /*
        ImGui::PopStyleVar();
    */

    private native void nPopStyleVar(int count); /*
        ImGui::PopStyleVar((int)count);
    */

    private native void nPushAllowKeyboardFocus(boolean allowKeyboardFocus); /*
        ImGui::PushAllowKeyboardFocus((bool)allowKeyboardFocus);
    */

    private native void nPopAllowKeyboardFocus(); /*
        ImGui::PopAllowKeyboardFocus();
    */

    private native void nPushButtonRepeat(boolean repeat); /*
        ImGui::PushButtonRepeat((bool)repeat);
    */

    private native void nPopButtonRepeat(); /*
        ImGui::PopButtonRepeat();
    */

    private native void nPushItemWidth(float itemWidth); /*
        ImGui::PushItemWidth((float)itemWidth);
    */

    private native void nPopItemWidth(); /*
        ImGui::PopItemWidth();
    */

    private native void nSetNextItemWidth(float itemWidth); /*
        ImGui::SetNextItemWidth((float)itemWidth);
    */

    private native float nCalcItemWidth(); /*
        return ImGui::CalcItemWidth();
    */

    private native void nPushTextWrapPos(); /*
        ImGui::PushTextWrapPos();
    */

    private native void nPushTextWrapPos(float wrapLocalPosX); /*
        ImGui::PushTextWrapPos((float)wrapLocalPosX);
    */

    private native void nPopTextWrapPos(); /*
        ImGui::PopTextWrapPos();
    */

    private native long nGetFont(); /*
        return (intptr_t)ImGui::GetFont();
    */

    private native float nGetFontSize(); /*
        return ImGui::GetFontSize();
    */

    private native int nGetColorU32(int idx); /*
        return ImGui::GetColorU32((ImGuiCol)idx);
    */

    private native int nGetColorU32(int idx, float alphaMul); /*
        return ImGui::GetColorU32((ImGuiCol)idx, (float)alphaMul);
    */

    private native int nGetColorU32(float colX, float colY, float colZ, float colW); /*
        return ImGui::GetColorU32((const ImVec4 &)ImVec4(colX, colY, colZ, colW));
    */

    private native void nSeparator(); /*
        ImGui::Separator();
    */

    private native void nSameLine(); /*
        ImGui::SameLine();
    */

    private native void nSameLine(float offsetFromStartX); /*
        ImGui::SameLine((float)offsetFromStartX);
    */

    private native void nSameLine(float offsetFromStartX, float spacing); /*
        ImGui::SameLine((float)offsetFromStartX, (float)spacing);
    */

    private native void nNewLine(); /*
        ImGui::NewLine();
    */

    private native void nSpacing(); /*
        ImGui::Spacing();
    */

    private native void nDummy(float sizeX, float sizeY); /*
        ImGui::Dummy((const ImVec2 &)ImVec2(sizeX, sizeY));
    */

    private native void nIndent(); /*
        ImGui::Indent();
    */

    private native void nIndent(float indentW); /*
        ImGui::Indent((float)indentW);
    */

    private native void nUnindent(); /*
        ImGui::Unindent();
    */

    private native void nUnindent(float indentW); /*
        ImGui::Unindent((float)indentW);
    */

    private native void nBeginGroup(); /*
        ImGui::BeginGroup();
    */

    private native void nEndGroup(); /*
        ImGui::EndGroup();
    */

    private native float nGetCursorPosX(); /*
        return ImGui::GetCursorPos().x;
    */

    private native float nGetCursorPosY(); /*
        return ImGui::GetCursorPos().y;
    */

    private native void nSetCursorPos(float localPosX, float localPosY); /*
        ImGui::SetCursorPos((const ImVec2 &)ImVec2(localPosX, localPosY));
    */

    private native void nSetCursorPosX(float localX); /*
        ImGui::SetCursorPosX((float)localX);
    */

    private native void nSetCursorPosY(float localY); /*
        ImGui::SetCursorPosY((float)localY);
    */

    private native void nSetCursorScreenPos(float posX, float posY); /*
        ImGui::SetCursorScreenPos((const ImVec2 &)ImVec2(posX, posY));
    */

    private native void nAlignTextToFramePadding(); /*
        ImGui::AlignTextToFramePadding();
    */

    private native float nGetTextLineHeight(); /*
        return ImGui::GetTextLineHeight();
    */

    private native float nGetTextLineHeightWithSpacing(); /*
        return ImGui::GetTextLineHeightWithSpacing();
    */

    private native float nGetFrameHeight(); /*
        return ImGui::GetFrameHeight();
    */

    private native float nGetFrameHeightWithSpacing(); /*
        return ImGui::GetFrameHeightWithSpacing();
    */

    private native void nPushID(String strId); /*
        ImGui::PushID((const char *)strId);
    */

    private native void nPushID(String strIdBegin, String strIdEnd); /*
        ImGui::PushID((const char *)strIdBegin, (const char *)strIdEnd);
    */

    private native void nPushID(int intId); /*
        ImGui::PushID((int)intId);
    */

    private native void nPopID(); /*
        ImGui::PopID();
    */

    private native int nGetID(String strId); /*
        return ImGui::GetID((const char *)strId);
    */

    private native int nGetID(String strIdBegin, String strIdEnd); /*
        return ImGui::GetID((const char *)strIdBegin, (const char *)strIdEnd);
    */

    private native void nTextUnformatted(String text); /*
        ImGui::TextUnformatted((const char *)text);
    */

    private native void nTextUnformatted(String text, String textEnd); /*
        ImGui::TextUnformatted((const char *)text, (const char *)textEnd);
    */

    private native void nText(String fmt); /*
        ImGui::Text((const char *)fmt, NULL);
    */

    private native void nTextColored(float colX, float colY, float colZ, float colW, String fmt); /*
        ImGui::TextColored((const ImVec4 &)ImVec4(colX, colY, colZ, colW), (const char *)fmt, NULL);
    */

    private native void nTextDisabled(String fmt); /*
        ImGui::TextDisabled((const char *)fmt, NULL);
    */

    private native void nTextWrapped(String fmt); /*
        ImGui::TextWrapped((const char *)fmt, NULL);
    */

    private native void nLabelText(String label, String fmt); /*
        ImGui::LabelText((const char *)label, (const char *)fmt, NULL);
    */

    private native void nBulletText(String fmt); /*
        ImGui::BulletText((const char *)fmt, NULL);
    */

    private native boolean nButton(String label); /*
        return ImGui::Button((const char *)label);
    */

    private native boolean nButton(String label, float sizeX, float sizeY); /*
        return ImGui::Button((const char *)label, (const ImVec2 &)ImVec2(sizeX, sizeY));
    */

    private native boolean nSmallButton(String label); /*
        return ImGui::SmallButton((const char *)label);
    */

    private native boolean nInvisibleButton(String strId, float sizeX, float sizeY); /*
        return ImGui::InvisibleButton((const char *)strId, (const ImVec2 &)ImVec2(sizeX, sizeY));
    */

    private native boolean nInvisibleButton(String strId, float sizeX, float sizeY, int flags); /*
        return ImGui::InvisibleButton((const char *)strId, (const ImVec2 &)ImVec2(sizeX, sizeY), (ImGuiButtonFlags)flags);
    */

    private native boolean nArrowButton(String strId, int dir); /*
        return ImGui::ArrowButton((const char *)strId, (ImGuiDir)dir);
    */

    private native void nImage(int userTextureId, float sizeX, float sizeY); /*
        ImGui::Image((ImTextureID)(intptr_t)userTextureId, (const ImVec2 &)ImVec2(sizeX, sizeY));
    */

    private native void nImage(int userTextureId, float sizeX, float sizeY, float uv0X, float uv0Y); /*
        ImGui::Image((ImTextureID)(intptr_t)userTextureId, (const ImVec2 &)ImVec2(sizeX, sizeY), (const ImVec2 &)ImVec2(uv0X, uv0Y));
    */

    private native void nImage(int userTextureId, float sizeX, float sizeY, float uv0X, float uv0Y, float uv1X, float uv1Y); /*
        ImGui::Image((ImTextureID)(intptr_t)userTextureId, (const ImVec2 &)ImVec2(sizeX, sizeY), (const ImVec2 &)ImVec2(uv0X, uv0Y), (const ImVec2 &)ImVec2(uv1X, uv1Y));
    */

    private native void nImage(int userTextureId, float sizeX, float sizeY, float uv0X, float uv0Y, float uv1X, float uv1Y, float tintColX, float tintColY, float tintColZ, float tintColW); /*
        ImGui::Image((ImTextureID)(intptr_t)userTextureId, (const ImVec2 &)ImVec2(sizeX, sizeY), (const ImVec2 &)ImVec2(uv0X, uv0Y), (const ImVec2 &)ImVec2(uv1X, uv1Y), (const ImVec4 &)ImVec4(tintColX, tintColY, tintColZ, tintColW));
    */

    private native void nImage(int userTextureId, float sizeX, float sizeY, float uv0X, float uv0Y, float uv1X, float uv1Y, float tintColX, float tintColY, float tintColZ, float tintColW, float borderColX, float borderColY, float borderColZ, float borderColW); /*
        ImGui::Image((ImTextureID)(intptr_t)userTextureId, (const ImVec2 &)ImVec2(sizeX, sizeY), (const ImVec2 &)ImVec2(uv0X, uv0Y), (const ImVec2 &)ImVec2(uv1X, uv1Y), (const ImVec4 &)ImVec4(tintColX, tintColY, tintColZ, tintColW), (const ImVec4 &)ImVec4(borderColX, borderColY, borderColZ, borderColW));
    */

    private native boolean nImageButton(int userTextureId, float sizeX, float sizeY); /*
        return ImGui::ImageButton((ImTextureID)(intptr_t)userTextureId, (const ImVec2 &)ImVec2(sizeX, sizeY));
    */

    private native boolean nImageButton(int userTextureId, float sizeX, float sizeY, float uv0X, float uv0Y); /*
        return ImGui::ImageButton((ImTextureID)(intptr_t)userTextureId, (const ImVec2 &)ImVec2(sizeX, sizeY), (const ImVec2 &)ImVec2(uv0X, uv0Y));
    */

    private native boolean nImageButton(int userTextureId, float sizeX, float sizeY, float uv0X, float uv0Y, float uv1X, float uv1Y); /*
        return ImGui::ImageButton((ImTextureID)(intptr_t)userTextureId, (const ImVec2 &)ImVec2(sizeX, sizeY), (const ImVec2 &)ImVec2(uv0X, uv0Y), (const ImVec2 &)ImVec2(uv1X, uv1Y));
    */

    private native boolean nImageButton(int userTextureId, float sizeX, float sizeY, float uv0X, float uv0Y, float uv1X, float uv1Y, int framePadding); /*
        return ImGui::ImageButton((ImTextureID)(intptr_t)userTextureId, (const ImVec2 &)ImVec2(sizeX, sizeY), (const ImVec2 &)ImVec2(uv0X, uv0Y), (const ImVec2 &)ImVec2(uv1X, uv1Y), (int)framePadding);
    */

    private native boolean nImageButton(int userTextureId, float sizeX, float sizeY, float uv0X, float uv0Y, float uv1X, float uv1Y, float bgColX, float bgColY, float bgColZ, float bgColW); /*
        return ImGui::ImageButton((ImTextureID)(intptr_t)userTextureId, (const ImVec2 &)ImVec2(sizeX, sizeY), (const ImVec2 &)ImVec2(uv0X, uv0Y), (const ImVec2 &)ImVec2(uv1X, uv1Y), -1, (const ImVec4 &)ImVec4(bgColX, bgColY, bgColZ, bgColW));
    */

    private native boolean nImageButton(int userTextureId, float sizeX, float sizeY, float uv0X, float uv0Y, float uv1X, float uv1Y, int framePadding, float bgColX, float bgColY, float bgColZ, float bgColW); /*
        return ImGui::ImageButton((ImTextureID)(intptr_t)userTextureId, (const ImVec2 &)ImVec2(sizeX, sizeY), (const ImVec2 &)ImVec2(uv0X, uv0Y), (const ImVec2 &)ImVec2(uv1X, uv1Y), (int)framePadding, (const ImVec4 &)ImVec4(bgColX, bgColY, bgColZ, bgColW));
    */

    private native boolean nImageButton(int userTextureId, float sizeX, float sizeY, float uv0X, float uv0Y, float uv1X, float uv1Y, float bgColX, float bgColY, float bgColZ, float bgColW, float tintColX, float tintColY, float tintColZ, float tintColW); /*
        return ImGui::ImageButton((ImTextureID)(intptr_t)userTextureId, (const ImVec2 &)ImVec2(sizeX, sizeY), (const ImVec2 &)ImVec2(uv0X, uv0Y), (const ImVec2 &)ImVec2(uv1X, uv1Y), -1, (const ImVec4 &)ImVec4(bgColX, bgColY, bgColZ, bgColW), (const ImVec4 &)ImVec4(tintColX, tintColY, tintColZ, tintColW));
    */

    private native boolean nImageButton(int userTextureId, float sizeX, float sizeY, float uv0X, float uv0Y, float uv1X, float uv1Y, int framePadding, float bgColX, float bgColY, float bgColZ, float bgColW, float tintColX, float tintColY, float tintColZ, float tintColW); /*
        return ImGui::ImageButton((ImTextureID)(intptr_t)userTextureId, (const ImVec2 &)ImVec2(sizeX, sizeY), (const ImVec2 &)ImVec2(uv0X, uv0Y), (const ImVec2 &)ImVec2(uv1X, uv1Y), (int)framePadding, (const ImVec4 &)ImVec4(bgColX, bgColY, bgColZ, bgColW), (const ImVec4 &)ImVec4(tintColX, tintColY, tintColZ, tintColW));
    */

    private native boolean nCheckbox(String label, boolean[] v); /*
        return ImGui::Checkbox((const char *)label, (bool *)&v[0]);
    */

    private native boolean nCheckboxFlags(String label, int[] flags, int flagsValue); /*
        return ImGui::CheckboxFlags((const char *)label, (int *)&flags[0], (int)flagsValue);
    */

    private native boolean nRadioButton(String label, boolean active); /*
        return ImGui::RadioButton((const char *)label, (bool)active);
    */

    private native boolean nRadioButton(String label, int[] v, int vButton); /*
        return ImGui::RadioButton((const char *)label, (int *)&v[0], (int)vButton);
    */

    private native void nProgressBar(float fraction); /*
        ImGui::ProgressBar((float)fraction);
    */

    private native void nProgressBar(float fraction, float sizeArgX, float sizeArgY); /*
        ImGui::ProgressBar((float)fraction, (const ImVec2 &)ImVec2(sizeArgX, sizeArgY));
    */

    private native void nProgressBar(float fraction, String overlay); /*
        ImGui::ProgressBar((float)fraction, ImVec2(-FLT_MIN, 0), (const char *)overlay);
    */

    private native void nProgressBar(float fraction, float sizeArgX, float sizeArgY, String overlay); /*
        ImGui::ProgressBar((float)fraction, (const ImVec2 &)ImVec2(sizeArgX, sizeArgY), (const char *)overlay);
    */

    private native void nBullet(); /*
        ImGui::Bullet();
    */

    private native boolean nBeginCombo(String label, String previewValue); /*
        return ImGui::BeginCombo((const char *)label, (const char *)previewValue);
    */

    private native boolean nBeginCombo(String label, String previewValue, int flags); /*
        return ImGui::BeginCombo((const char *)label, (const char *)previewValue, (ImGuiComboFlags)flags);
    */

    private native void nEndCombo(); /*
        ImGui::EndCombo();
    */

    private native boolean nCombo(String label, int[] currentItem, String[] items, int itemsCount, int popupMaxHeightInItems); /*
        const char* itemsChars[itemsCount];
        for (int i = 0; i < itemsCount; i++) {
            jstring str = (jstring)env->GetObjectArrayElement(items, i);
            const char* rawStr = env->GetStringUTFChars(str, JNI_FALSE);
            itemsChars[i] = rawStr;
        }
        bool flag = ImGui::Combo(label, &currentItem[0], itemsChars, itemsCount, popupMaxHeightInItems);
        for (int i = 0; i< itemsCount; i++) {
            jstring str = (jstring)env->GetObjectArrayElement(items, i);
            env->ReleaseStringUTFChars(str, itemsChars[i]);
        }
        return flag;
    */

    private native boolean nCombo(String label, int[] currentItem, String itemsSeparatedByZeros); /*
        return ImGui::Combo(label, &currentItem[0], itemsSeparatedByZeros);
    */

    private native boolean nCombo(String label, int[] currentItem, String itemsSeparatedByZeros, int popupMaxHeightInItems); /*
        return ImGui::Combo(label, &currentItem[0], itemsSeparatedByZeros, popupMaxHeightInItems);
    */

    private native boolean nDragFloat(String label, float[] value); /*
        return ImGui::DragFloat(label, &value[0]);
    */

    private native boolean nDragFloat(String label, float[] value, float vSpeed); /*
        return ImGui::DragFloat(label, &value[0], vSpeed);
    */

    private native boolean nDragFloat(String label, float[] value, float vSpeed, float vMin); /*
        return ImGui::DragFloat(label, &value[0], vSpeed, vMin);
    */

    private native boolean nDragFloat(String label, float[] value, float vSpeed, float vMin, float vMax); /*
        return ImGui::DragFloat(label, &value[0], vSpeed, vMin, vMax);
    */

    private native boolean nDragFloat(String label, float[] value, float vSpeed, float vMin, float vMax, String format); /*
        return ImGui::DragFloat(label, &value[0], vSpeed, vMin, vMax, format);
    */

    private native boolean nDragFloat(String label, float[] value, float vSpeed, float vMin, float vMax, int flags); /*
        return ImGui::DragFloat(label, &value[0], vSpeed, vMin, vMax, "%.3f", flags);
    */

    private native boolean nDragFloat(String label, float[] value, float vSpeed, float vMin, float vMax, String format, int flags); /*
        return ImGui::DragFloat(label, &value[0], vSpeed, vMin, vMax, format, flags);
    */

    private native boolean nDragFloat2(String label, float[] value); /*
        return ImGui::DragFloat2(label, &value[0]);
    */

    private native boolean nDragFloat2(String label, float[] value, float vSpeed); /*
        return ImGui::DragFloat2(label, &value[0], vSpeed);
    */

    private native boolean nDragFloat2(String label, float[] value, float vSpeed, float vMin); /*
        return ImGui::DragFloat2(label, &value[0], vSpeed, vMin);
    */

    private native boolean nDragFloat2(String label, float[] value, float vSpeed, float vMin, float vMax); /*
        return ImGui::DragFloat2(label, &value[0], vSpeed, vMin, vMax);
    */

    private native boolean nDragFloat2(String label, float[] value, float vSpeed, float vMin, float vMax, String format); /*
        return ImGui::DragFloat2(label, &value[0], vSpeed, vMin, vMax, format);
    */

    private native boolean nDragFloat2(String label, float[] value, float vSpeed, float vMin, float vMax, int flags); /*
        return ImGui::DragFloat2(label, &value[0], vSpeed, vMin, vMax, "%.3f", flags);
    */

    private native boolean nDragFloat2(String label, float[] value, float vSpeed, float vMin, float vMax, String format, int flags); /*
        return ImGui::DragFloat2(label, &value[0], vSpeed, vMin, vMax, format, flags);
    */

    private native boolean nDragFloat3(String label, float[] value); /*
        return ImGui::DragFloat3(label, &value[0]);
    */

    private native boolean nDragFloat3(String label, float[] value, float vSpeed); /*
        return ImGui::DragFloat3(label, &value[0], vSpeed);
    */

    private native boolean nDragFloat3(String label, float[] value, float vSpeed, float vMin); /*
        return ImGui::DragFloat3(label, &value[0], vSpeed, vMin);
    */

    private native boolean nDragFloat3(String label, float[] value, float vSpeed, float vMin, float vMax); /*
        return ImGui::DragFloat3(label, &value[0], vSpeed, vMin, vMax);
    */

    private native boolean nDragFloat3(String label, float[] value, float vSpeed, float vMin, float vMax, String format); /*
        return ImGui::DragFloat3(label, &value[0], vSpeed, vMin, vMax, format);
    */

    private native boolean nDragFloat3(String label, float[] value, float vSpeed, float vMin, float vMax, int flags); /*
        return ImGui::DragFloat3(label, &value[0], vSpeed, vMin, vMax, "%.3f", flags);
    */

    private native boolean nDragFloat3(String label, float[] value, float vSpeed, float vMin, float vMax, String format, int flags); /*
        return ImGui::DragFloat3(label, &value[0], vSpeed, vMin, vMax, format, flags);
    */

    private native boolean nDragFloat4(String label, float[] value); /*
        return ImGui::DragFloat4(label, &value[0]);
    */

    private native boolean nDragFloat4(String label, float[] value, float vSpeed); /*
        return ImGui::DragFloat4(label, &value[0], vSpeed);
    */

    private native boolean nDragFloat4(String label, float[] value, float vSpeed, float vMin); /*
        return ImGui::DragFloat4(label, &value[0], vSpeed, vMin);
    */

    private native boolean nDragFloat4(String label, float[] value, float vSpeed, float vMin, float vMax); /*
        return ImGui::DragFloat4(label, &value[0], vSpeed, vMin, vMax);
    */

    private native boolean nDragFloat4(String label, float[] value, float vSpeed, float vMin, float vMax, String format); /*
        return ImGui::DragFloat4(label, &value[0], vSpeed, vMin, vMax, format);
    */

    private native boolean nDragFloat4(String label, float[] value, float vSpeed, float vMin, float vMax, int flags); /*
        return ImGui::DragFloat4(label, &value[0], vSpeed, vMin, vMax, "%.3f", flags);
    */

    private native boolean nDragFloat4(String label, float[] value, float vSpeed, float vMin, float vMax, String format, int flags); /*
        return ImGui::DragFloat4(label, &value[0], vSpeed, vMin, vMax, format, flags);
    */

    private native boolean nDragFloatRange2(String label, float[] vCurrentMin, float[] vCurrentMax); /*
        return ImGui::DragFloatRange2(label, &vCurrentMin[0], &vCurrentMax[0]);
    */

    private native boolean nDragFloatRange2(String label, float[] vCurrentMin, float[] vCurrentMax, float vSpeed); /*
        return ImGui::DragFloatRange2(label, &vCurrentMin[0], &vCurrentMax[0], vSpeed);
    */

    private native boolean nDragFloatRange2(String label, float[] vCurrentMin, float[] vCurrentMax, float vSpeed, float vMin); /*
        return ImGui::DragFloatRange2(label, &vCurrentMin[0], &vCurrentMax[0], vSpeed, vMin);
    */

    private native boolean nDragFloatRange2(String label, float[] vCurrentMin, float[] vCurrentMax, float vSpeed, float vMin, float vMax); /*
        return ImGui::DragFloatRange2(label, &vCurrentMin[0], &vCurrentMax[0], vSpeed, vMin, vMax);
    */

    private native boolean nDragFloatRange2(String label, float[] vCurrentMin, float[] vCurrentMax, float vSpeed, float vMin, float vMax, String format); /*
        return ImGui::DragFloatRange2(label, &vCurrentMin[0], &vCurrentMax[0], vSpeed, vMin, vMax, format);
    */

    private native boolean nDragFloatRange2(String label, float[] vCurrentMin, float[] vCurrentMax, float vSpeed, float vMin, float vMax, String format, String formatMax); /*
        return ImGui::DragFloatRange2(label, &vCurrentMin[0], &vCurrentMax[0], vSpeed, vMin, vMax, format, formatMax);
    */

    private native boolean nDragFloatRange2(String label, float[] vCurrentMin, float[] vCurrentMax, float vSpeed, float vMin, float vMax, String format, String formatMax, int flags); /*
        return ImGui::DragFloatRange2(label, &vCurrentMin[0], &vCurrentMax[0], vSpeed, vMin, vMax, format, formatMax, flags);
    */

    private native boolean nDragInt(String label, int[] value); /*
        return ImGui::DragInt(label, &value[0]);
    */

    private native boolean nDragInt(String label, int[] value, float vSpeed); /*
        return ImGui::DragInt(label, &value[0], vSpeed);
    */

    private native boolean nDragInt(String label, int[] value, float vSpeed, int vMin); /*
        return ImGui::DragInt(label, &value[0], vSpeed, vMin);
    */

    private native boolean nDragInt(String label, int[] value, float vSpeed, int vMin, int vMax); /*
        return ImGui::DragInt(label, &value[0], vSpeed, vMin, vMax);
    */

    private native boolean nDragInt(String label, int[] value, float vSpeed, int vMin, int vMax, String format); /*
        return ImGui::DragInt(label, &value[0], vSpeed, vMin, vMax, format);
    */

    private native boolean nDragInt(String label, int[] value, float vSpeed, int vMin, int vMax, int flags); /*
        return ImGui::DragInt(label, &value[0], vSpeed, vMin, vMax, "%d", flags);
    */

    private native boolean nDragInt(String label, int[] value, float vSpeed, int vMin, int vMax, String format, int flags); /*
        return ImGui::DragInt(label, &value[0], vSpeed, vMin, vMax, format, flags);
    */

    private native boolean nDragInt2(String label, int[] value); /*
        return ImGui::DragInt2(label, &value[0]);
    */

    private native boolean nDragInt2(String label, int[] value, float vSpeed); /*
        return ImGui::DragInt2(label, &value[0], vSpeed);
    */

    private native boolean nDragInt2(String label, int[] value, float vSpeed, int vMin); /*
        return ImGui::DragInt2(label, &value[0], vSpeed, vMin);
    */

    private native boolean nDragInt2(String label, int[] value, float vSpeed, int vMin, int vMax); /*
        return ImGui::DragInt2(label, &value[0], vSpeed, vMin, vMax);
    */

    private native boolean nDragInt2(String label, int[] value, float vSpeed, int vMin, int vMax, String format); /*
        return ImGui::DragInt2(label, &value[0], vSpeed, vMin, vMax, format);
    */

    private native boolean nDragInt2(String label, int[] value, float vSpeed, int vMin, int vMax, int flags); /*
        return ImGui::DragInt2(label, &value[0], vSpeed, vMin, vMax, "%d", flags);
    */

    private native boolean nDragInt2(String label, int[] value, float vSpeed, int vMin, int vMax, String format, int flags); /*
        return ImGui::DragInt2(label, &value[0], vSpeed, vMin, vMax, format, flags);
    */

    private native boolean nDragInt3(String label, int[] value); /*
        return ImGui::DragInt3(label, &value[0]);
    */

    private native boolean nDragInt3(String label, int[] value, float vSpeed); /*
        return ImGui::DragInt3(label, &value[0], vSpeed);
    */

    private native boolean nDragInt3(String label, int[] value, float vSpeed, int vMin); /*
        return ImGui::DragInt3(label, &value[0], vSpeed, vMin);
    */

    private native boolean nDragInt3(String label, int[] value, float vSpeed, int vMin, int vMax); /*
        return ImGui::DragInt3(label, &value[0], vSpeed, vMin, vMax);
    */

    private native boolean nDragInt3(String label, int[] value, float vSpeed, int vMin, int vMax, String format); /*
        return ImGui::DragInt3(label, &value[0], vSpeed, vMin, vMax, format);
    */

    private native boolean nDragInt3(String label, int[] value, float vSpeed, int vMin, int vMax, int flags); /*
        return ImGui::DragInt3(label, &value[0], vSpeed, vMin, vMax, "%d", flags);
    */

    private native boolean nDragInt3(String label, int[] value, float vSpeed, int vMin, int vMax, String format, int flags); /*
        return ImGui::DragInt3(label, &value[0], vSpeed, vMin, vMax, format, flags);
    */

    private native boolean nDragInt4(String label, int[] value); /*
        return ImGui::DragInt4(label, &value[0]);
    */

    private native boolean nDragInt4(String label, int[] value, float vSpeed); /*
        return ImGui::DragInt4(label, &value[0], vSpeed);
    */

    private native boolean nDragInt4(String label, int[] value, float vSpeed, int vMin); /*
        return ImGui::DragInt4(label, &value[0], vSpeed, vMin);
    */

    private native boolean nDragInt4(String label, int[] value, float vSpeed, int vMin, int vMax); /*
        return ImGui::DragInt4(label, &value[0], vSpeed, vMin, vMax);
    */

    private native boolean nDragInt4(String label, int[] value, float vSpeed, int vMin, int vMax, String format); /*
        return ImGui::DragInt4(label, &value[0], vSpeed, vMin, vMax, format);
    */

    private native boolean nDragInt4(String label, int[] value, float vSpeed, int vMin, int vMax, int flags); /*
        return ImGui::DragInt4(label, &value[0], vSpeed, vMin, vMax, "%d", flags);
    */

    private native boolean nDragInt4(String label, int[] value, float vSpeed, int vMin, int vMax, String format, int flags); /*
        return ImGui::DragInt4(label, &value[0], vSpeed, vMin, vMax, format, flags);
    */

    private native boolean nDragIntRange2(String label, int[] vCurrentMin, int[] vCurrentMax); /*
        return ImGui::DragIntRange2(label, &vCurrentMin[0], &vCurrentMax[0]);
    */

    private native boolean nDragIntRange2(String label, int[] vCurrentMin, int[] vCurrentMax, float vSpeed); /*
        return ImGui::DragIntRange2(label, &vCurrentMin[0], &vCurrentMax[0], vSpeed);
    */

    private native boolean nDragIntRange2(String label, int[] vCurrentMin, int[] vCurrentMax, float vSpeed, int vMin); /*
        return ImGui::DragIntRange2(label, &vCurrentMin[0], &vCurrentMax[0], vSpeed, vMin);
    */

    private native boolean nDragIntRange2(String label, int[] vCurrentMin, int[] vCurrentMax, float vSpeed, int vMin, int vMax); /*
        return ImGui::DragIntRange2(label, &vCurrentMin[0], &vCurrentMax[0], vSpeed, vMin, vMax);
    */

    private native boolean nDragIntRange2(String label, int[] vCurrentMin, int[] vCurrentMax, float vSpeed, int vMin, int vMax, String format); /*
        return ImGui::DragIntRange2(label, &vCurrentMin[0], &vCurrentMax[0], vSpeed, vMin, vMax, format);
    */

    private native boolean nDragIntRange2(String label, int[] vCurrentMin, int[] vCurrentMax, float vSpeed, int vMin, int vMax, String format, String formatMax); /*
        return ImGui::DragIntRange2(label, &vCurrentMin[0], &vCurrentMax[0], vSpeed, vMin, vMax, format, formatMax);
    */

    private native boolean nDragIntRange2(String label, int[] vCurrentMin, int[] vCurrentMax, float vSpeed, int vMin, int vMax, String format, String formatMax, int flags); /*
        return ImGui::DragIntRange2(label, &vCurrentMin[0], &vCurrentMax[0], vSpeed, vMin, vMax, format, formatMax, flags);
    */

    private native boolean nDragScalar(String label, float[] pData); /*
        return ImGui::DragScalar(label, ImGuiDataType_Float, &pData[0]);
    */

    private native boolean nDragScalar(String label, float[] pData, float vSpeed); /*
        return ImGui::DragScalar(label, ImGuiDataType_Float, &pData[0], vSpeed);
    */

    private native boolean nDragScalar(String label, float[] pData, float vSpeed, float pMin); /*
        return ImGui::DragScalar(label, ImGuiDataType_Float, &pData[0], vSpeed, &pMin);
    */

    private native boolean nDragScalar(String label, float[] pData, float vSpeed, float pMin, float pMax); /*
        return ImGui::DragScalar(label, ImGuiDataType_Float, &pData[0], vSpeed, &pMin, &pMax);
    */

    private native boolean nDragScalar(String label, float[] pData, float vSpeed, float pMin, float pMax, String format); /*
        return ImGui::DragScalar(label, ImGuiDataType_Float, &pData[0], vSpeed, &pMin, &pMax, format);
    */

    private native boolean nDragScalar(String label, float[] pData, float vSpeed, float pMin, float pMax, String format, int flags); /*
        return ImGui::DragScalar(label, ImGuiDataType_Float, &pData[0], vSpeed, &pMin, &pMax, format, flags);
    */

    private native boolean nDragScalar(String label, short[] pData); /*
        return ImGui::DragScalar(label, ImGuiDataType_S16, &pData[0]);
    */

    private native boolean nDragScalar(String label, short[] pData, float vSpeed); /*
        return ImGui::DragScalar(label, ImGuiDataType_S16, &pData[0], vSpeed);
    */

    private native boolean nDragScalar(String label, short[] pData, float vSpeed, short pMin); /*
        return ImGui::DragScalar(label, ImGuiDataType_S16, &pData[0], vSpeed, &pMin);
    */

    private native boolean nDragScalar(String label, short[] pData, float vSpeed, short pMin, short pMax); /*
        return ImGui::DragScalar(label, ImGuiDataType_S16, &pData[0], vSpeed, &pMin, &pMax);
    */

    private native boolean nDragScalar(String label, short[] pData, float vSpeed, short pMin, short pMax, String format); /*
        return ImGui::DragScalar(label, ImGuiDataType_S16, &pData[0], vSpeed, &pMin, &pMax, format);
    */

    private native boolean nDragScalar(String label, short[] pData, float vSpeed, short pMin, short pMax, String format, int flags); /*
        return ImGui::DragScalar(label, ImGuiDataType_S16, &pData[0], vSpeed, &pMin, &pMax, format, flags);
    */

    private native boolean nDragScalar(String label, int[] pData); /*
        return ImGui::DragScalar(label, ImGuiDataType_S32, &pData[0]);
    */

    private native boolean nDragScalar(String label, int[] pData, float vSpeed); /*
        return ImGui::DragScalar(label, ImGuiDataType_S32, &pData[0], vSpeed);
    */

    private native boolean nDragScalar(String label, int[] pData, float vSpeed, int pMin); /*
        return ImGui::DragScalar(label, ImGuiDataType_S32, &pData[0], vSpeed, &pMin);
    */

    private native boolean nDragScalar(String label, int[] pData, float vSpeed, int pMin, int pMax); /*
        return ImGui::DragScalar(label, ImGuiDataType_S32, &pData[0], vSpeed, &pMin, &pMax);
    */

    private native boolean nDragScalar(String label, int[] pData, float vSpeed, int pMin, int pMax, String format); /*
        return ImGui::DragScalar(label, ImGuiDataType_S32, &pData[0], vSpeed, &pMin, &pMax, format);
    */

    private native boolean nDragScalar(String label, int[] pData, float vSpeed, int pMin, int pMax, String format, int flags); /*
        return ImGui::DragScalar(label, ImGuiDataType_S32, &pData[0], vSpeed, &pMin, &pMax, format, flags);
    */

    private native boolean nDragScalar(String label, long[] pData); /*
        return ImGui::DragScalar(label, ImGuiDataType_S64, &pData[0]);
    */

    private native boolean nDragScalar(String label, long[] pData, float vSpeed); /*
        return ImGui::DragScalar(label, ImGuiDataType_S64, &pData[0], vSpeed);
    */

    private native boolean nDragScalar(String label, long[] pData, float vSpeed, long pMin); /*
        return ImGui::DragScalar(label, ImGuiDataType_S64, &pData[0], vSpeed, &pMin);
    */

    private native boolean nDragScalar(String label, long[] pData, float vSpeed, long pMin, long pMax); /*
        return ImGui::DragScalar(label, ImGuiDataType_S64, &pData[0], vSpeed, &pMin, &pMax);
    */

    private native boolean nDragScalar(String label, long[] pData, float vSpeed, long pMin, long pMax, String format); /*
        return ImGui::DragScalar(label, ImGuiDataType_S64, &pData[0], vSpeed, &pMin, &pMax, format);
    */

    private native boolean nDragScalar(String label, long[] pData, float vSpeed, long pMin, long pMax, String format, int flags); /*
        return ImGui::DragScalar(label, ImGuiDataType_S64, &pData[0], vSpeed, &pMin, &pMax, format, flags);
    */

    private native boolean nSliderFloat(String label, float[] value, float vMin, float vMax); /*
        return ImGui::SliderFloat(label, &value[0], vMin, vMax);
    */

    private native boolean nSliderFloat(String label, float[] value, float vMin, float vMax, String format); /*
        return ImGui::SliderFloat(label, &value[0], vMin, vMax, format);
    */

    private native boolean nSliderFloat(String label, float[] value, float vMin, float vMax, int flags); /*
        return ImGui::SliderFloat(label, &value[0], vMin, vMax, "%.3f", flags);
    */

    private native boolean nSliderFloat(String label, float[] value, float vMin, float vMax, String format, int flags); /*
        return ImGui::SliderFloat(label, &value[0], vMin, vMax, format, flags);
    */

    private native boolean nSliderFloat2(String label, float[] value, float vMin, float vMax); /*
        return ImGui::SliderFloat2(label, &value[0], vMin, vMax);
    */

    private native boolean nSliderFloat2(String label, float[] value, float vMin, float vMax, String format); /*
        return ImGui::SliderFloat2(label, &value[0], vMin, vMax, format);
    */

    private native boolean nSliderFloat2(String label, float[] value, float vMin, float vMax, int flags); /*
        return ImGui::SliderFloat2(label, &value[0], vMin, vMax, "%.3f", flags);
    */

    private native boolean nSliderFloat2(String label, float[] value, float vMin, float vMax, String format, int flags); /*
        return ImGui::SliderFloat2(label, &value[0], vMin, vMax, format, flags);
    */

    private native boolean nSliderFloat3(String label, float[] value, float vMin, float vMax); /*
        return ImGui::SliderFloat3(label, &value[0], vMin, vMax);
    */

    private native boolean nSliderFloat3(String label, float[] value, float vMin, float vMax, String format); /*
        return ImGui::SliderFloat3(label, &value[0], vMin, vMax, format);
    */

    private native boolean nSliderFloat3(String label, float[] value, float vMin, float vMax, int flags); /*
        return ImGui::SliderFloat3(label, &value[0], vMin, vMax, "%.3f", flags);
    */

    private native boolean nSliderFloat3(String label, float[] value, float vMin, float vMax, String format, int flags); /*
        return ImGui::SliderFloat3(label, &value[0], vMin, vMax, format, flags);
    */

    private native boolean nSliderFloat4(String label, float[] value, float vMin, float vMax); /*
        return ImGui::SliderFloat4(label, &value[0], vMin, vMax);
    */

    private native boolean nSliderFloat4(String label, float[] value, float vMin, float vMax, String format); /*
        return ImGui::SliderFloat4(label, &value[0], vMin, vMax, format);
    */

    private native boolean nSliderFloat4(String label, float[] value, float vMin, float vMax, int flags); /*
        return ImGui::SliderFloat4(label, &value[0], vMin, vMax, "%.3f", flags);
    */

    private native boolean nSliderFloat4(String label, float[] value, float vMin, float vMax, String format, int flags); /*
        return ImGui::SliderFloat4(label, &value[0], vMin, vMax, format, flags);
    */

    private native boolean nSliderAngle(String label, float[] vRad, float vDegreesMin, float vDegreesMax); /*
        return ImGui::SliderAngle(label, &vRad[0], vDegreesMin, vDegreesMax);
    */

    private native boolean nSliderAngle(String label, float[] vRad, float vDegreesMin, float vDegreesMax, String format); /*
        return ImGui::SliderAngle(label, &vRad[0], vDegreesMin, vDegreesMax, format);
    */

    private native boolean nSliderAngle(String label, float[] vRad, float vDegreesMin, float vDegreesMax, int flags); /*
        return ImGui::SliderAngle(label, &vRad[0], vDegreesMin, vDegreesMax, "%.0f deg", flags);
    */

    private native boolean nSliderAngle(String label, float[] vRad, float vDegreesMin, float vDegreesMax, String format, int flags); /*
        return ImGui::SliderAngle(label, &vRad[0], vDegreesMin, vDegreesMax, format, flags);
    */

    private native boolean nSliderInt(String label, int[] value, int vMin, int vMax); /*
        return ImGui::SliderInt(label, &value[0], vMin, vMax);
    */

    private native boolean nSliderInt(String label, int[] value, int vMin, int vMax, String format); /*
        return ImGui::SliderInt(label, &value[0], vMin, vMax, format);
    */

    private native boolean nSliderInt(String label, int[] value, int vMin, int vMax, int flags); /*
        return ImGui::SliderInt(label, &value[0], vMin, vMax, "%d", flags);
    */

    private native boolean nSliderInt(String label, int[] value, int vMin, int vMax, String format, int flags); /*
        return ImGui::SliderInt(label, &value[0], vMin, vMax, format, flags);
    */

    private native boolean nSliderInt2(String label, int[] value, int vMin, int vMax); /*
        return ImGui::SliderInt2(label, &value[0], vMin, vMax);
    */

    private native boolean nSliderInt2(String label, int[] value, int vMin, int vMax, String format); /*
        return ImGui::SliderInt2(label, &value[0], vMin, vMax, format);
    */

    private native boolean nSliderInt2(String label, int[] value, int vMin, int vMax, int flags); /*
        return ImGui::SliderInt2(label, &value[0], vMin, vMax, "%d", flags);
    */

    private native boolean nSliderInt2(String label, int[] value, int vMin, int vMax, String format, int flags); /*
        return ImGui::SliderInt2(label, &value[0], vMin, vMax, format, flags);
    */

    private native boolean nSliderInt3(String label, int[] value, int vMin, int vMax); /*
        return ImGui::SliderInt3(label, &value[0], vMin, vMax);
    */

    private native boolean nSliderInt3(String label, int[] value, int vMin, int vMax, String format); /*
        return ImGui::SliderInt3(label, &value[0], vMin, vMax, format);
    */

    private native boolean nSliderInt3(String label, int[] value, int vMin, int vMax, int flags); /*
        return ImGui::SliderInt3(label, &value[0], vMin, vMax, "%d", flags);
    */

    private native boolean nSliderInt3(String label, int[] value, int vMin, int vMax, String format, int flags); /*
        return ImGui::SliderInt3(label, &value[0], vMin, vMax, format, flags);
    */

    private native boolean nSliderInt4(String label, int[] value, int vMin, int vMax); /*
        return ImGui::SliderInt4(label, &value[0], vMin, vMax);
    */

    private native boolean nSliderInt4(String label, int[] value, int vMin, int vMax, String format); /*
        return ImGui::SliderInt4(label, &value[0], vMin, vMax, format);
    */

    private native boolean nSliderInt4(String label, int[] value, int vMin, int vMax, int flags); /*
        return ImGui::SliderInt4(label, &value[0], vMin, vMax, "%d", flags);
    */

    private native boolean nSliderInt4(String label, int[] value, int vMin, int vMax, String format, int flags); /*
        return ImGui::SliderInt4(label, &value[0], vMin, vMax, format, flags);
    */

    private native boolean nSliderScalar(String label, float[] pData, float pMin, float pMax); /*
        return ImGui::SliderScalar(label, ImGuiDataType_Float, &pData[0], &pMin, &pMax);
    */

    private native boolean nSliderScalar(String label, float[] pData, float pMin, float pMax, String format); /*
        return ImGui::SliderScalar(label, ImGuiDataType_Float, &pData[0], &pMin, &pMax, format);
    */

    private native boolean nSliderScalar(String label, float[] pData, float pMin, float pMax, String format, int flags); /*
        return ImGui::SliderScalar(label, ImGuiDataType_Float, &pData[0], &pMin, &pMax, format, flags);
    */

    private native boolean nSliderScalar(String label, short[] pData, short pMin, short pMax); /*
        return ImGui::SliderScalar(label, ImGuiDataType_S16, &pData[0], &pMin, &pMax);
    */

    private native boolean nSliderScalar(String label, short[] pData, short pMin, short pMax, String format); /*
        return ImGui::SliderScalar(label, ImGuiDataType_S16, &pData[0], &pMin, &pMax, format);
    */

    private native boolean nSliderScalar(String label, short[] pData, short pMin, short pMax, String format, int flags); /*
        return ImGui::SliderScalar(label, ImGuiDataType_S16, &pData[0], &pMin, &pMax, format, flags);
    */

    private native boolean nSliderScalar(String label, int[] pData, int pMin, int pMax); /*
        return ImGui::SliderScalar(label, ImGuiDataType_S32, &pData[0], &pMin, &pMax);
    */

    private native boolean nSliderScalar(String label, int[] pData, int pMin, int pMax, String format); /*
        return ImGui::SliderScalar(label, ImGuiDataType_S32, &pData[0], &pMin, &pMax, format);
    */

    private native boolean nSliderScalar(String label, int[] pData, int pMin, int pMax, String format, int flags); /*
        return ImGui::SliderScalar(label, ImGuiDataType_S32, &pData[0], &pMin, &pMax, format, flags);
    */

    private native boolean nSliderScalar(String label, long[] pData, long pMin, long pMax); /*
        return ImGui::SliderScalar(label, ImGuiDataType_S64, &pData[0], &pMin, &pMax);
    */

    private native boolean nSliderScalar(String label, long[] pData, long pMin, long pMax, String format); /*
        return ImGui::SliderScalar(label, ImGuiDataType_S64, &pData[0], &pMin, &pMax, format);
    */

    private native boolean nSliderScalar(String label, long[] pData, long pMin, long pMax, String format, int flags); /*
        return ImGui::SliderScalar(label, ImGuiDataType_S64, &pData[0], &pMin, &pMax, format, flags);
    */

    private native boolean nVSliderFloat(String label, float sizeX, float sizeY, float[] value, float vMin, float vMax); /*
        return ImGui::VSliderFloat(label, ImVec2(sizeX, sizeY), &value[0], vMin, vMax);
    */

    private native boolean nVSliderFloat(String label, float sizeX, float sizeY, float[] value, float vMin, float vMax, String format); /*
        return ImGui::VSliderFloat(label, ImVec2(sizeX, sizeY), &value[0], vMin, vMax, format);
    */

    private native boolean nVSliderFloat(String label, float sizeX, float sizeY, float[] value, float vMin, float vMax, int flags); /*
        return ImGui::VSliderFloat(label, ImVec2(sizeX, sizeY), &value[0], vMin, vMax, "%.3f", flags);
    */

    private native boolean nVSliderFloat(String label, float sizeX, float sizeY, float[] value, float vMin, float vMax, String format, int flags); /*
        return ImGui::VSliderFloat(label, ImVec2(sizeX, sizeY), &value[0], vMin, vMax, format, flags);
    */

    private native boolean nVSliderInt(String label, float sizeX, float sizeY, int[] value, int vMin, int vMax); /*
        return ImGui::VSliderInt(label, ImVec2(sizeX, sizeY), &value[0], vMin, vMax);
    */

    private native boolean nVSliderInt(String label, float sizeX, float sizeY, int[] value, int vMin, int vMax, String format); /*
        return ImGui::VSliderInt(label, ImVec2(sizeX, sizeY), &value[0], vMin, vMax, format);
    */

    private native boolean nVSliderInt(String label, float sizeX, float sizeY, int[] value, int vMin, int vMax, int flags); /*
        return ImGui::VSliderInt(label, ImVec2(sizeX, sizeY), &value[0], vMin, vMax, "%d", flags);
    */

    private native boolean nVSliderInt(String label, float sizeX, float sizeY, int[] value, int vMin, int vMax, String format, int flags); /*
        return ImGui::VSliderInt(label, ImVec2(sizeX, sizeY), &value[0], vMin, vMax, format, flags);
    */

    private native boolean nVSliderScalar(String label, float sizeX, float sizeY, float[] pData, float pMin, float pMax); /*
        return ImGui::VSliderScalar(label, ImVec2(sizeX, sizeY), ImGuiDataType_Float, &pData[0], &pMin, &pMax);
    */

    private native boolean nVSliderScalar(String label, float sizeX, float sizeY, float[] pData, float pMin, float pMax, String format); /*
        return ImGui::VSliderScalar(label, ImVec2(sizeX, sizeY), ImGuiDataType_Float, &pData[0], &pMin, &pMax, format);
    */

    private native boolean nVSliderScalar(String label, float sizeX, float sizeY, float[] pData, float pMin, float pMax, String format, int flags); /*
        return ImGui::VSliderScalar(label, ImVec2(sizeX, sizeY), ImGuiDataType_Float, &pData[0], &pMin, &pMax, format, flags);
    */

    private native boolean nVSliderScalar(String label, float sizeX, float sizeY, short[] pData, short pMin, short pMax); /*
        return ImGui::VSliderScalar(label, ImVec2(sizeX, sizeY), ImGuiDataType_S16, &pData[0], &pMin, &pMax);
    */

    private native boolean nVSliderScalar(String label, float sizeX, float sizeY, short[] pData, short pMin, short pMax, String format); /*
        return ImGui::VSliderScalar(label, ImVec2(sizeX, sizeY), ImGuiDataType_S16, &pData[0], &pMin, &pMax, format);
    */

    private native boolean nVSliderScalar(String label, float sizeX, float sizeY, short[] pData, short pMin, short pMax, String format, int flags); /*
        return ImGui::VSliderScalar(label, ImVec2(sizeX, sizeY), ImGuiDataType_S16, &pData[0], &pMin, &pMax, format, flags);
    */

    private native boolean nVSliderScalar(String label, float sizeX, float sizeY, int[] pData, int pMin, int pMax); /*
        return ImGui::VSliderScalar(label, ImVec2(sizeX, sizeY), ImGuiDataType_S32, &pData[0], &pMin, &pMax);
    */

    private native boolean nVSliderScalar(String label, float sizeX, float sizeY, int[] pData, int pMin, int pMax, String format); /*
        return ImGui::VSliderScalar(label, ImVec2(sizeX, sizeY), ImGuiDataType_S32, &pData[0], &pMin, &pMax, format);
    */

    private native boolean nVSliderScalar(String label, float sizeX, float sizeY, int[] pData, int pMin, int pMax, String format, int flags); /*
        return ImGui::VSliderScalar(label, ImVec2(sizeX, sizeY), ImGuiDataType_S32, &pData[0], &pMin, &pMax, format, flags);
    */

    private native boolean nVSliderScalar(String label, float sizeX, float sizeY, long[] pData, long pMin, long pMax); /*
        return ImGui::VSliderScalar(label, ImVec2(sizeX, sizeY), ImGuiDataType_S64, &pData[0], &pMin, &pMax);
    */

    private native boolean nVSliderScalar(String label, float sizeX, float sizeY, long[] pData, long pMin, long pMax, String format); /*
        return ImGui::VSliderScalar(label, ImVec2(sizeX, sizeY), ImGuiDataType_S64, &pData[0], &pMin, &pMax, format);
    */

    private native boolean nVSliderScalar(String label, float sizeX, float sizeY, long[] pData, long pMin, long pMax, String format, int flags); /*
        return ImGui::VSliderScalar(label, ImVec2(sizeX, sizeY), ImGuiDataType_S64, &pData[0], &pMin, &pMax, format, flags);
    */

    private native boolean nInputText(boolean multiline, boolean hint, String label, String hintLabel, imgui.type.ImString imString, byte[] buf, int maxSize, float width, float height, int flags, imgui.type.ImString.InputData textInputData, String allowedChars, imgui.callback.ImGuiInputTextCallback callback); /*
        InputTextCallbackUserData userData;
        userData.imString = &imString;
        userData.maxSize = maxSize;
        userData.jResizedBuf = NULL;
        userData.resizedBuf = NULL;
        userData.textInputData = &textInputData;
        userData.env = env;
        userData.allowedChars = allowedChars;
        userData.handler = callback != NULL ? &callback : NULL;
    
        bool valueChanged;
    
        if (multiline) {
            valueChanged = ImGui::InputTextMultiline(label, buf, maxSize, ImVec2(width, height), flags, &TextEditCallbackStub, &userData);
        } else if (hint) {
            valueChanged = ImGui::InputTextWithHint(label, hintLabel, buf, maxSize, flags, &TextEditCallbackStub, &userData);
        } else {
            valueChanged = ImGui::InputText(label, buf, maxSize, flags, &TextEditCallbackStub, &userData);
        }
    
        if (valueChanged) {
            int size;
    
            if (userData.jResizedBuf != NULL) {
                size = strlen(userData.resizedBuf);
                env->ReleasePrimitiveArrayCritical(userData.jResizedBuf, userData.resizedBuf, 0);
            } else {
                size = strlen(buf);
            }
    
            env->SetIntField(textInputData, inputDataSizeID, size);
            env->SetBooleanField(textInputData, inputDataIsDirtyID, true);
        }
    
        return valueChanged;
    */

    private native boolean nInputFloat(String label, float[] value); /*
        return ImGui::InputFloat(label, &value[0]);
    */

    private native boolean nInputFloat(String label, float[] value, float step); /*
        return ImGui::InputFloat(label, &value[0], step);
    */

    private native boolean nInputFloat(String label, float[] value, float step, float stepFast); /*
        return ImGui::InputFloat(label, &value[0], step, stepFast);
    */

    private native boolean nInputFloat(String label, float[] value, float step, float stepFast, String format); /*
        return ImGui::InputFloat(label, &value[0], step, stepFast, format);
    */

    private native boolean nInputFloat(String label, float[] value, float step, float stepFast, int flags); /*
        return ImGui::InputFloat(label, &value[0], step, stepFast, "%.3f", flags);
    */

    private native boolean nInputFloat(String label, float[] value, float step, float stepFast, String format, int flags); /*
        return ImGui::InputFloat(label, &value[0], step, stepFast, format, flags);
    */

    private native boolean nInputFloat2(String label, float[] value); /*
        return ImGui::InputFloat2(label, &value[0]);
    */

    private native boolean nInputFloat2(String label, float[] value, String format); /*
        return ImGui::InputFloat2(label, &value[0], format);
    */

    private native boolean nInputFloat2(String label, float[] value, int flags); /*
        return ImGui::InputFloat2(label, &value[0], "%.3f", flags);
    */

    private native boolean nInputFloat2(String label, float[] value, String format, int flags); /*
        return ImGui::InputFloat2(label, &value[0], format, flags);
    */

    private native boolean nInputFloat3(String label, float[] value); /*
        return ImGui::InputFloat3(label, &value[0]);
    */

    private native boolean nInputFloat3(String label, float[] value, String format); /*
        return ImGui::InputFloat3(label, &value[0], format);
    */

    private native boolean nInputFloat3(String label, float[] value, int flags); /*
        return ImGui::InputFloat3(label, &value[0], "%.3f", flags);
    */

    private native boolean nInputFloat3(String label, float[] value, String format, int flags); /*
        return ImGui::InputFloat3(label, &value[0], format, flags);
    */

    private native boolean nInputFloat4(String label, float[] value); /*
        return ImGui::InputFloat4(label, &value[0]);
    */

    private native boolean nInputFloat4(String label, float[] value, String format); /*
        return ImGui::InputFloat4(label, &value[0], format);
    */

    private native boolean nInputFloat4(String label, float[] value, int flags); /*
        return ImGui::InputFloat4(label, &value[0], "%.3f", flags);
    */

    private native boolean nInputFloat4(String label, float[] value, String format, int flags); /*
        return ImGui::InputFloat4(label, &value[0], format, flags);
    */

    private native boolean nInputInt(String label, int[] value); /*
        return ImGui::InputInt(label, &value[0]);
    */

    private native boolean nInputInt(String label, int[] value, int step); /*
        return ImGui::InputInt(label, &value[0], step);
    */

    private native boolean nInputInt(String label, int[] value, int step, int stepFast); /*
        return ImGui::InputInt(label, &value[0], step, stepFast);
    */

    private native boolean nInputInt(String label, int[] value, int step, int stepFast, int flags); /*
        return ImGui::InputInt(label, &value[0], step, stepFast, flags);
    */

    private native boolean nInputInt2(String label, int[] value); /*
        return ImGui::InputInt2(label, &value[0]);
    */

    private native boolean nInputInt2(String label, int[] value, int flags); /*
        return ImGui::InputInt2(label, &value[0], flags);
    */

    private native boolean nInputInt3(String label, int[] value); /*
        return ImGui::InputInt3(label, &value[0]);
    */

    private native boolean nInputInt3(String label, int[] value, int flags); /*
        return ImGui::InputInt3(label, &value[0], flags);
    */

    private native boolean nInputInt4(String label, int[] value); /*
        return ImGui::InputInt4(label, &value[0]);
    */

    private native boolean nInputInt4(String label, int[] value, int flags); /*
        return ImGui::InputInt4(label, &value[0], flags);
    */

    private native boolean nInputDouble(String label, double[] value, double step, double stepFast); /*
        return ImGui::InputDouble(label, &value[0], step, stepFast);
    */

    private native boolean nInputDouble(String label, double[] value, double step, double stepFast, String format); /*
        return ImGui::InputDouble(label, &value[0], step, stepFast, format);
    */

    private native boolean nInputDouble(String label, double[] value, double step, double stepFast, int flags); /*
        return ImGui::InputDouble(label, &value[0], step, stepFast, "%.6f", flags);
    */

    private native boolean nInputDouble(String label, double[] value, double step, double stepFast, String format, int flags); /*
        return ImGui::InputDouble(label, &value[0], step, stepFast, format, flags);
    */

    private native boolean nInputScalar(String label, float[] pData); /*
        return ImGui::InputScalar(label, ImGuiDataType_Float, &pData[0]);
    */

    private native boolean nInputScalar(String label, float[] pData, float pStep); /*
        return ImGui::InputScalar(label, ImGuiDataType_Float, &pData[0], &pStep);
    */

    private native boolean nInputScalar(String label, float[] pData, float pStep, float pStepFast); /*
        return ImGui::InputScalar(label, ImGuiDataType_Float, &pData[0], &pStep, &pStepFast);
    */

    private native boolean nInputScalar(String label, float[] pData, float pStep, float pStepFast, String format); /*
        return ImGui::InputScalar(label, ImGuiDataType_Float, &pData[0], &pStep, &pStepFast, format);
    */

    private native boolean nInputScalar(String label, float[] pData, float pStep, float pStepFast, String format, int flags); /*
        return ImGui::InputScalar(label, ImGuiDataType_Float, &pData[0], &pStep, &pStepFast, format, flags);
    */

    private native boolean nInputScalar(String label, short[] pData); /*
        return ImGui::InputScalar(label, ImGuiDataType_S16, &pData[0]);
    */

    private native boolean nInputScalar(String label, short[] pData, short pStep); /*
        return ImGui::InputScalar(label, ImGuiDataType_S16, &pData[0], &pStep);
    */

    private native boolean nInputScalar(String label, short[] pData, short pStep, short pStepFast); /*
        return ImGui::InputScalar(label, ImGuiDataType_S16, &pData[0], &pStep, &pStepFast);
    */

    private native boolean nInputScalar(String label, short[] pData, short pStep, short pStepFast, String format); /*
        return ImGui::InputScalar(label, ImGuiDataType_S16, &pData[0], &pStep, &pStepFast, format);
    */

    private native boolean nInputScalar(String label, short[] pData, short pStep, short pStepFast, String format, int flags); /*
        return ImGui::InputScalar(label, ImGuiDataType_S16, &pData[0], &pStep, &pStepFast, format, flags);
    */

    private native boolean nInputScalar(String label, int[] pData); /*
        return ImGui::InputScalar(label, ImGuiDataType_S32, &pData[0]);
    */

    private native boolean nInputScalar(String label, int[] pData, int pStep); /*
        return ImGui::InputScalar(label, ImGuiDataType_S32, &pData[0], &pStep);
    */

    private native boolean nInputScalar(String label, int[] pData, int pStep, int pStepFast); /*
        return ImGui::InputScalar(label, ImGuiDataType_S32, &pData[0], &pStep, &pStepFast);
    */

    private native boolean nInputScalar(String label, int[] pData, int pStep, int pStepFast, String format); /*
        return ImGui::InputScalar(label, ImGuiDataType_S32, &pData[0], &pStep, &pStepFast, format);
    */

    private native boolean nInputScalar(String label, int[] pData, int pStep, int pStepFast, String format, int flags); /*
        return ImGui::InputScalar(label, ImGuiDataType_S32, &pData[0], &pStep, &pStepFast, format, flags);
    */

    private native boolean nInputScalar(String label, long[] pData); /*
        return ImGui::InputScalar(label, ImGuiDataType_S64, &pData[0]);
    */

    private native boolean nInputScalar(String label, long[] pData, long pStep); /*
        return ImGui::InputScalar(label, ImGuiDataType_S64, &pData[0], &pStep);
    */

    private native boolean nInputScalar(String label, long[] pData, long pStep, long pStepFast); /*
        return ImGui::InputScalar(label, ImGuiDataType_S64, &pData[0], &pStep, &pStepFast);
    */

    private native boolean nInputScalar(String label, long[] pData, long pStep, long pStepFast, String format); /*
        return ImGui::InputScalar(label, ImGuiDataType_S64, &pData[0], &pStep, &pStepFast, format);
    */

    private native boolean nInputScalar(String label, long[] pData, long pStep, long pStepFast, String format, int flags); /*
        return ImGui::InputScalar(label, ImGuiDataType_S64, &pData[0], &pStep, &pStepFast, format, flags);
    */

    private native boolean nColorEdit3(String label, float[] col); /*
        return ImGui::ColorEdit3(label, &col[0]);
    */

    private native boolean nColorEdit3(String label, float[] col, int flags); /*
        return ImGui::ColorEdit3(label, &col[0], flags);
    */

    private native boolean nColorEdit4(String label, float[] col); /*
        return ImGui::ColorEdit4(label, &col[0]);
    */

    private native boolean nColorEdit4(String label, float[] col, int flags); /*
        return ImGui::ColorEdit4(label, &col[0], flags);
    */

    private native boolean nColorPicker3(String label, float[] col); /*
        return ImGui::ColorPicker3(label, &col[0]);
    */

    private native boolean nColorPicker3(String label, float[] col, int flags); /*
        return ImGui::ColorPicker3(label, &col[0], flags);
    */

    private native boolean nColorPicker4(String label, float[] col); /*
        return ImGui::ColorPicker4(label, &col[0]);
    */

    private native boolean nColorPicker4(String label, float[] col, int flags); /*
        return ImGui::ColorPicker4(label, &col[0], flags);
    */

    private native boolean nColorPicker4(String label, float[] col, int flags, float[] refCol); /*
        return ImGui::ColorPicker4(label, &col[0], flags, &refCol[0]);
    */

    private native boolean nColorButton(String descId, float colX, float colY, float colZ, float colW); /*
        return ImGui::ColorButton((const char *)descId, (const ImVec4 &)ImVec4(colX, colY, colZ, colW));
    */

    private native boolean nColorButton(String descId, float colX, float colY, float colZ, float colW, int flags); /*
        return ImGui::ColorButton((const char *)descId, (const ImVec4 &)ImVec4(colX, colY, colZ, colW), (ImGuiColorEditFlags)flags);
    */

    private native boolean nColorButton(String descId, float colX, float colY, float colZ, float colW, float sizeX, float sizeY); /*
        return ImGui::ColorButton((const char *)descId, (const ImVec4 &)ImVec4(colX, colY, colZ, colW), 0, (ImVec2)ImVec2(sizeX, sizeY));
    */

    private native boolean nColorButton(String descId, float colX, float colY, float colZ, float colW, int flags, float sizeX, float sizeY); /*
        return ImGui::ColorButton((const char *)descId, (const ImVec4 &)ImVec4(colX, colY, colZ, colW), (ImGuiColorEditFlags)flags, (ImVec2)ImVec2(sizeX, sizeY));
    */

    private native void nSetColorEditOptions(int flags); /*
        ImGui::SetColorEditOptions((ImGuiColorEditFlags)flags);
    */

    private native boolean nTreeNode(String label); /*
        return ImGui::TreeNode((const char *)label);
    */

    private native boolean nTreeNode(String strId, String fmt); /*
        return ImGui::TreeNode((const char *)strId, (const char *)fmt, NULL);
    */

    private native boolean nTreeNodeEx(String label); /*
        return ImGui::TreeNodeEx((const char *)label);
    */

    private native boolean nTreeNodeEx(String label, int flags); /*
        return ImGui::TreeNodeEx((const char *)label, (ImGuiTreeNodeFlags)flags);
    */

    private native boolean nTreeNodeEx(String strId, int flags, String fmt); /*
        return ImGui::TreeNodeEx((const char *)strId, (ImGuiTreeNodeFlags)flags, (const char *)fmt, NULL);
    */

    private native void nTreePush(String strId); /*
        ImGui::TreePush((const char *)strId);
    */

    private native void nTreePop(); /*
        ImGui::TreePop();
    */

    private native float nGetTreeNodeToLabelSpacing(); /*
        return ImGui::GetTreeNodeToLabelSpacing();
    */

    private native boolean nCollapsingHeader(String label); /*
        return ImGui::CollapsingHeader((const char *)label);
    */

    private native boolean nCollapsingHeader(String label, int flags); /*
        return ImGui::CollapsingHeader((const char *)label, (ImGuiTreeNodeFlags)flags);
    */

    private native boolean nCollapsingHeader(String label, boolean[] pVisible); /*
        return ImGui::CollapsingHeader((const char *)label, (bool *)&pVisible[0]);
    */

    private native boolean nCollapsingHeader(String label, boolean[] pVisible, int flags); /*
        return ImGui::CollapsingHeader((const char *)label, (bool *)&pVisible[0], (ImGuiTreeNodeFlags)flags);
    */

    private native void nSetNextItemOpen(boolean isOpen); /*
        ImGui::SetNextItemOpen((bool)isOpen);
    */

    private native void nSetNextItemOpen(boolean isOpen, int cond); /*
        ImGui::SetNextItemOpen((bool)isOpen, (ImGuiCond)cond);
    */

    private native boolean nSelectable(String label); /*
        return ImGui::Selectable((const char *)label);
    */

    private native boolean nSelectable(String label, boolean selected); /*
        return ImGui::Selectable((const char *)label, (bool)selected);
    */

    private native boolean nSelectable(String label, int flags); /*
        return ImGui::Selectable((const char *)label, false, (ImGuiSelectableFlags)flags);
    */

    private native boolean nSelectable(String label, boolean selected, int flags); /*
        return ImGui::Selectable((const char *)label, (bool)selected, (ImGuiSelectableFlags)flags);
    */

    private native boolean nSelectable(String label, int flags, float sizeX, float sizeY); /*
        return ImGui::Selectable((const char *)label, false, (ImGuiSelectableFlags)flags, (const ImVec2 &)ImVec2(sizeX, sizeY));
    */

    private native boolean nSelectable(String label, boolean selected, float sizeX, float sizeY); /*
        return ImGui::Selectable((const char *)label, (bool)selected, 0, (const ImVec2 &)ImVec2(sizeX, sizeY));
    */

    private native boolean nSelectable(String label, boolean selected, int flags, float sizeX, float sizeY); /*
        return ImGui::Selectable((const char *)label, (bool)selected, (ImGuiSelectableFlags)flags, (const ImVec2 &)ImVec2(sizeX, sizeY));
    */

    private native boolean nSelectable(String label, boolean[] pSelected); /*
        return ImGui::Selectable((const char *)label, (bool *)&pSelected[0]);
    */

    private native boolean nSelectable(String label, boolean[] pSelected, int flags); /*
        return ImGui::Selectable((const char *)label, (bool *)&pSelected[0], (ImGuiSelectableFlags)flags);
    */

    private native boolean nSelectable(String label, boolean[] pSelected, float sizeX, float sizeY); /*
        return ImGui::Selectable((const char *)label, (bool *)&pSelected[0], 0, (const ImVec2 &)ImVec2(sizeX, sizeY));
    */

    private native boolean nSelectable(String label, boolean[] pSelected, int flags, float sizeX, float sizeY); /*
        return ImGui::Selectable((const char *)label, (bool *)&pSelected[0], (ImGuiSelectableFlags)flags, (const ImVec2 &)ImVec2(sizeX, sizeY));
    */

    private native boolean nBeginListBox(String label); /*
        return ImGui::BeginListBox((const char *)label);
    */

    private native boolean nBeginListBox(String label, float sizeX, float sizeY); /*
        return ImGui::BeginListBox((const char *)label, (const ImVec2 &)ImVec2(sizeX, sizeY));
    */

    private native void nEndListBox(); /*
        ImGui::EndListBox();
    */

    private native boolean nListBox(String label, int[] currentItem, String[] items, int itemsCount, int heightInItems); /*
        const char* itemsChars[itemsCount];
        for (int i = 0; i < itemsCount; i++) {
            jstring str = (jstring)env->GetObjectArrayElement(items, i);
            const char* rawStr = env->GetStringUTFChars(str, JNI_FALSE);
            itemsChars[i] = rawStr;
        }
        bool flag = ImGui::ListBox(label, &currentItem[0], itemsChars, itemsCount, heightInItems);
        for (int i = 0; i< itemsCount; i++) {
            jstring str = (jstring)env->GetObjectArrayElement(items, i);
            env->ReleaseStringUTFChars(str, itemsChars[i]);
        }
        return flag;
    */

    private native void nPlotLines(String label, float[] values, int valuesCount); /*
        ImGui::PlotLines(label, &values[0], valuesCount);
    */

    private native void nPlotLines(String label, float[] values, int valuesCount, int valuesOffset); /*
        ImGui::PlotLines(label, &values[0], valuesCount, valuesOffset);
    */

    private native void nPlotLines(String label, float[] values, int valuesCount, int valuesOffset, String overlayText); /*
        ImGui::PlotLines(label, &values[0], valuesCount, valuesOffset, overlayText);
    */

    private native void nPlotLines(String label, float[] values, int valuesCount, int valuesOffset, float scaleMin); /*
        ImGui::PlotLines(label, &values[0], valuesCount, valuesOffset, NULL, scaleMin);
    */

    private native void nPlotLines(String label, float[] values, int valuesCount, int valuesOffset, String overlayText, float scaleMin); /*
        ImGui::PlotLines(label, &values[0], valuesCount, valuesOffset, overlayText, scaleMin);
    */

    private native void nPlotLines(String label, float[] values, int valuesCount, int valuesOffset, float scaleMin, float scaleMax); /*
        ImGui::PlotLines(label, &values[0], valuesCount, valuesOffset, NULL, scaleMin, scaleMax);
    */

    private native void nPlotLines(String label, float[] values, int valuesCount, int valuesOffset, String overlayText, float scaleMin, float scaleMax); /*
        ImGui::PlotLines(label, &values[0], valuesCount, valuesOffset, overlayText, scaleMin, scaleMax);
    */

    private native void nPlotLines(String label, float[] values, int valuesCount, int valuesOffset, float scaleMin, float scaleMax, float graphSizeX, float graphSizeY); /*
        ImGui::PlotLines(label, &values[0], valuesCount, valuesOffset, NULL, scaleMin, scaleMax, ImVec2(graphSizeX, graphSizeY));
    */

    private native void nPlotLines(String label, float[] values, int valuesCount, int valuesOffset, String overlayText, float scaleMin, float scaleMax, float graphSizeX, float graphSizeY); /*
        ImGui::PlotLines(label, &values[0], valuesCount, valuesOffset, overlayText, scaleMin, scaleMax, ImVec2(graphSizeX, graphSizeY));
    */

    private native void nPlotLines(String label, float[] values, int valuesCount, int valuesOffset, float scaleMin, float scaleMax, float graphSizeX, float graphSizeY, int stride); /*
        ImGui::PlotLines(label, &values[0], valuesCount, valuesOffset, NULL, scaleMin, scaleMax, ImVec2(graphSizeX, graphSizeY), stride);
    */

    private native void nPlotLines(String label, float[] values, int valuesCount, int valuesOffset, String overlayText, float scaleMin, float scaleMax, int stride); /*
        ImGui::PlotLines(label, &values[0], valuesCount, valuesOffset, overlayText, scaleMin, scaleMax, ImVec2(0, 0), stride);
    */

    private native void nPlotLines(String label, float[] values, int valuesCount, int valuesOffset, String overlayText, float scaleMin, float scaleMax, float graphSizeX, float graphSizeY, int stride); /*
        ImGui::PlotLines(label, &values[0], valuesCount, valuesOffset, overlayText, scaleMin, scaleMax, ImVec2(graphSizeX, graphSizeY), stride);
    */

    private native void nPlotHistogram(String label, float[] values, int valuesCount, int valuesOffset, String overlayText); /*
        ImGui::PlotHistogram(label, &values[0], valuesCount, valuesOffset, overlayText);
    */

    private native void nPlotHistogram(String label, float[] values, int valuesCount, int valuesOffset, float scaleMin); /*
        ImGui::PlotHistogram(label, &values[0], valuesCount, valuesOffset, NULL, scaleMin);
    */

    private native void nPlotHistogram(String label, float[] values, int valuesCount, int valuesOffset, String overlayText, float scaleMin); /*
        ImGui::PlotHistogram(label, &values[0], valuesCount, valuesOffset, overlayText, scaleMin);
    */

    private native void nPlotHistogram(String label, float[] values, int valuesCount, int valuesOffset, float scaleMin, float scaleMax, float graphSizeX, float graphSizeY); /*
        ImGui::PlotHistogram(label, &values[0], valuesCount, valuesOffset, NULL, scaleMin, scaleMax, ImVec2(graphSizeX, graphSizeY));
    */

    private native void nPlotHistogram(String label, float[] values, int valuesCount, int valuesOffset, String overlayText, float scaleMin, float scaleMax, float graphSizeX, float graphSizeY); /*
        ImGui::PlotHistogram(label, &values[0], valuesCount, valuesOffset, overlayText, scaleMin, scaleMax, ImVec2(graphSizeX, graphSizeY));
    */

    private native void nPlotHistogram(String label, float[] values, int valuesCount, int valuesOffset, float scaleMin, float scaleMax, float graphSizeX, float graphSizeY, int stride); /*
        ImGui::PlotHistogram(label, &values[0], valuesCount, valuesOffset, NULL, scaleMin, scaleMax, ImVec2(graphSizeX, graphSizeY), stride);
    */

    private native void nPlotHistogram(String label, float[] values, int valuesCount, int valuesOffset, String overlayText, float scaleMin, float scaleMax, int stride); /*
        ImGui::PlotHistogram(label, &values[0], valuesCount, valuesOffset, overlayText, scaleMin, scaleMax, ImVec2(0, 0), stride);
    */

    private native void nPlotHistogram(String label, float[] values, int valuesCount, int valuesOffset, String overlayText, float scaleMin, float scaleMax, float graphSizeX, float graphSizeY, int stride); /*
        ImGui::PlotHistogram(label, &values[0], valuesCount, valuesOffset, overlayText, scaleMin, scaleMax, ImVec2(graphSizeX, graphSizeY), stride);
    */

    private native void nValue(String prefix, boolean b); /*
        ImGui::Value((const char *)prefix, (bool)b);
    */

    private native void nValue(String prefix, int v); /*
        ImGui::Value((const char *)prefix, (int)v);
    */

    private native void nValue(String prefix, float v); /*
        ImGui::Value((const char *)prefix, (float)v);
    */

    private native void nValue(String prefix, float v, String floatFormat); /*
        ImGui::Value((const char *)prefix, (float)v, (const char *)floatFormat);
    */

    private native boolean nBeginMenuBar(); /*
        return ImGui::BeginMenuBar();
    */

    private native void nEndMenuBar(); /*
        ImGui::EndMenuBar();
    */

    private native boolean nBeginMainMenuBar(); /*
        return ImGui::BeginMainMenuBar();
    */

    private native void nEndMainMenuBar(); /*
        ImGui::EndMainMenuBar();
    */

    private native boolean nBeginMenu(String label); /*
        return ImGui::BeginMenu((const char *)label);
    */

    private native boolean nBeginMenu(String label, boolean enabled); /*
        return ImGui::BeginMenu((const char *)label, (bool)enabled);
    */

    private native void nEndMenu(); /*
        ImGui::EndMenu();
    */

    private native boolean nMenuItem(String label); /*
        return ImGui::MenuItem((const char *)label);
    */

    private native boolean nMenuItem(String label, String shortcut); /*
        return ImGui::MenuItem((const char *)label, (const char *)shortcut);
    */

    private native boolean nMenuItem(String label, String shortcut, boolean selected); /*
        return ImGui::MenuItem((const char *)label, (const char *)shortcut, (bool)selected);
    */

    private native boolean nMenuItem(String label, String shortcut, boolean selected, boolean enabled); /*
        return ImGui::MenuItem((const char *)label, (const char *)shortcut, (bool)selected, (bool)enabled);
    */

    private native boolean nMenuItem(String label, String shortcut, boolean[] pSelected); /*
        return ImGui::MenuItem((const char *)label, (const char *)shortcut, (bool *)&pSelected[0]);
    */

    private native boolean nMenuItem(String label, String shortcut, boolean[] pSelected, boolean enabled); /*
        return ImGui::MenuItem((const char *)label, (const char *)shortcut, (bool *)&pSelected[0], (bool)enabled);
    */

    private native void nBeginTooltip(); /*
        ImGui::BeginTooltip();
    */

    private native void nEndTooltip(); /*
        ImGui::EndTooltip();
    */

    private native void nSetTooltip(String fmt); /*
        ImGui::SetTooltip((const char *)fmt, NULL);
    */

    private native boolean nBeginPopup(String strId); /*
        return ImGui::BeginPopup((const char *)strId);
    */

    private native boolean nBeginPopup(String strId, int flags); /*
        return ImGui::BeginPopup((const char *)strId, (ImGuiWindowFlags)flags);
    */

    private native boolean nBeginPopupModal(String name); /*
        return ImGui::BeginPopupModal((const char *)name);
    */

    private native boolean nBeginPopupModal(String name, boolean[] pOpen); /*
        return ImGui::BeginPopupModal((const char *)name, (bool *)&pOpen[0]);
    */

    private native boolean nBeginPopupModal(String name, int flags); /*
        return ImGui::BeginPopupModal((const char *)name, NULL, (ImGuiWindowFlags)flags);
    */

    private native boolean nBeginPopupModal(String name, boolean[] pOpen, int flags); /*
        return ImGui::BeginPopupModal((const char *)name, (bool *)&pOpen[0], (ImGuiWindowFlags)flags);
    */

    private native void nEndPopup(); /*
        ImGui::EndPopup();
    */

    private native void nOpenPopup(String strId); /*
        ImGui::OpenPopup((const char *)strId);
    */

    private native void nOpenPopup(String strId, int popupFlags); /*
        ImGui::OpenPopup((const char *)strId, (ImGuiPopupFlags)popupFlags);
    */

    private native void nOpenPopup(int id); /*
        ImGui::OpenPopup((ImGuiID)id);
    */

    private native void nOpenPopup(int id, int popupFlags); /*
        ImGui::OpenPopup((ImGuiID)id, (ImGuiPopupFlags)popupFlags);
    */

    private native void nOpenPopupOnItemClick(); /*
        ImGui::OpenPopupOnItemClick();
    */

    private native void nOpenPopupOnItemClick(String strId); /*
        ImGui::OpenPopupOnItemClick((const char *)strId);
    */

    private native void nOpenPopupOnItemClick(int popupFlags); /*
        ImGui::OpenPopupOnItemClick(NULL, (ImGuiPopupFlags)popupFlags);
    */

    private native void nOpenPopupOnItemClick(String strId, int popupFlags); /*
        ImGui::OpenPopupOnItemClick((const char *)strId, (ImGuiPopupFlags)popupFlags);
    */

    private native void nCloseCurrentPopup(); /*
        ImGui::CloseCurrentPopup();
    */

    private native boolean nBeginPopupContextItem(); /*
        return ImGui::BeginPopupContextItem();
    */

    private native boolean nBeginPopupContextItem(String strId); /*
        return ImGui::BeginPopupContextItem((const char *)strId);
    */

    private native boolean nBeginPopupContextItem(int popupFlags); /*
        return ImGui::BeginPopupContextItem(NULL, (ImGuiPopupFlags)popupFlags);
    */

    private native boolean nBeginPopupContextItem(String strId, int popupFlags); /*
        return ImGui::BeginPopupContextItem((const char *)strId, (ImGuiPopupFlags)popupFlags);
    */

    private native boolean nBeginPopupContextWindow(); /*
        return ImGui::BeginPopupContextWindow();
    */

    private native boolean nBeginPopupContextWindow(String strId); /*
        return ImGui::BeginPopupContextWindow((const char *)strId);
    */

    private native boolean nBeginPopupContextWindow(int popupFlags); /*
        return ImGui::BeginPopupContextWindow(NULL, (ImGuiPopupFlags)popupFlags);
    */

    private native boolean nBeginPopupContextWindow(String strId, int popupFlags); /*
        return ImGui::BeginPopupContextWindow((const char *)strId, (ImGuiPopupFlags)popupFlags);
    */

    private native boolean nBeginPopupContextVoid(); /*
        return ImGui::BeginPopupContextVoid();
    */

    private native boolean nBeginPopupContextVoid(String strId); /*
        return ImGui::BeginPopupContextVoid((const char *)strId);
    */

    private native boolean nBeginPopupContextVoid(int popupFlags); /*
        return ImGui::BeginPopupContextVoid(NULL, (ImGuiPopupFlags)popupFlags);
    */

    private native boolean nBeginPopupContextVoid(String strId, int popupFlags); /*
        return ImGui::BeginPopupContextVoid((const char *)strId, (ImGuiPopupFlags)popupFlags);
    */

    private native boolean nIsPopupOpen(String strId); /*
        return ImGui::IsPopupOpen((const char *)strId);
    */

    private native boolean nIsPopupOpen(String strId, int flags); /*
        return ImGui::IsPopupOpen((const char *)strId, (ImGuiPopupFlags)flags);
    */

    private native boolean nBeginTable(String strId, int column); /*
        return ImGui::BeginTable((const char *)strId, (int)column);
    */

    private native boolean nBeginTable(String strId, int column, int flags); /*
        return ImGui::BeginTable((const char *)strId, (int)column, (ImGuiTableFlags)flags);
    */

    private native boolean nBeginTable(String strId, int column, int flags, float outerSizeX, float outerSizeY); /*
        return ImGui::BeginTable((const char *)strId, (int)column, (ImGuiTableFlags)flags, (const ImVec2 &)ImVec2(outerSizeX, outerSizeY));
    */

    private native boolean nBeginTable(String strId, int column, int flags, float innerWidth); /*
        return ImGui::BeginTable((const char *)strId, (int)column, (ImGuiTableFlags)flags, ImVec2(0.0f, 0.0f), (float)innerWidth);
    */

    private native boolean nBeginTable(String strId, int column, int flags, float outerSizeX, float outerSizeY, float innerWidth); /*
        return ImGui::BeginTable((const char *)strId, (int)column, (ImGuiTableFlags)flags, (const ImVec2 &)ImVec2(outerSizeX, outerSizeY), (float)innerWidth);
    */

    private native void nEndTable(); /*
        ImGui::EndTable();
    */

    private native void nTableNextRow(); /*
        ImGui::TableNextRow();
    */

    private native void nTableNextRow(int rowFlags); /*
        ImGui::TableNextRow((ImGuiTableRowFlags)rowFlags);
    */

    private native void nTableNextRow(float minRowHeight); /*
        ImGui::TableNextRow(0, (float)minRowHeight);
    */

    private native void nTableNextRow(int rowFlags, float minRowHeight); /*
        ImGui::TableNextRow((ImGuiTableRowFlags)rowFlags, (float)minRowHeight);
    */

    private native boolean nTableNextColumn(); /*
        return ImGui::TableNextColumn();
    */

    private native boolean nTableSetColumnIndex(int columnN); /*
        return ImGui::TableSetColumnIndex((int)columnN);
    */

    private native void nTableSetupColumn(String label); /*
        ImGui::TableSetupColumn((const char *)label);
    */

    private native void nTableSetupColumn(String label, int flags); /*
        ImGui::TableSetupColumn((const char *)label, (ImGuiTableColumnFlags)flags);
    */

    private native void nTableSetupColumn(String label, float initWidthOrWeight); /*
        ImGui::TableSetupColumn((const char *)label, 0, (float)initWidthOrWeight);
    */

    private native void nTableSetupColumn(String label, int flags, float initWidthOrWeight); /*
        ImGui::TableSetupColumn((const char *)label, (ImGuiTableColumnFlags)flags, (float)initWidthOrWeight);
    */

    private native void nTableSetupColumn(String label, float initWidthOrWeight, int userId); /*
        ImGui::TableSetupColumn((const char *)label, 0, (float)initWidthOrWeight, (ImGuiID)userId);
    */

    private native void nTableSetupColumn(String label, int flags, int userId); /*
        ImGui::TableSetupColumn((const char *)label, (ImGuiTableColumnFlags)flags, 0.0f, (ImGuiID)userId);
    */

    private native void nTableSetupColumn(String label, int flags, float initWidthOrWeight, int userId); /*
        ImGui::TableSetupColumn((const char *)label, (ImGuiTableColumnFlags)flags, (float)initWidthOrWeight, (ImGuiID)userId);
    */

    private native void nTableSetupScrollFreeze(int cols, int rows); /*
        ImGui::TableSetupScrollFreeze((int)cols, (int)rows);
    */

    private native void nTableHeadersRow(); /*
        ImGui::TableHeadersRow();
    */

    private native void nTableHeader(String label); /*
        ImGui::TableHeader((const char *)label);
    */

    private native int nTableGetColumnCount(); /*
        return ImGui::TableGetColumnCount();
    */

    private native int nTableGetColumnIndex(); /*
        return ImGui::TableGetColumnIndex();
    */

    private native int nTableGetRowIndex(); /*
        return ImGui::TableGetRowIndex();
    */

    private native String nTableGetColumnName(); /*
        return env->NewStringUTF(ImGui::TableGetColumnName());
    */

    private native String nTableGetColumnName(int columnN); /*
        return env->NewStringUTF(ImGui::TableGetColumnName((int)columnN));
    */

    private native int nTableGetColumnFlags(); /*
        return ImGui::TableGetColumnFlags();
    */

    private native int nTableGetColumnFlags(int columnN); /*
        return ImGui::TableGetColumnFlags((int)columnN);
    */

    private native void nTableSetColumnEnabled(int columnN, boolean v); /*
        ImGui::TableSetColumnEnabled((int)columnN, (bool)v);
    */

    private native void nTableSetBgColor(int target, int color); /*
        ImGui::TableSetBgColor((ImGuiTableBgTarget)target, (ImU32)color);
    */

    private native void nTableSetBgColor(int target, int color, int columnN); /*
        ImGui::TableSetBgColor((ImGuiTableBgTarget)target, (ImU32)color, (int)columnN);
    */

    private native void nColumns(); /*
        ImGui::Columns();
    */

    private native void nColumns(int count); /*
        ImGui::Columns((int)count);
    */

    private native void nColumns(String id); /*
        ImGui::Columns(1, (const char *)id);
    */

    private native void nColumns(int count, String id); /*
        ImGui::Columns((int)count, (const char *)id);
    */

    private native void nColumns(String id, boolean border); /*
        ImGui::Columns(1, (const char *)id, (bool)border);
    */

    private native void nColumns(int count, boolean border); /*
        ImGui::Columns((int)count, NULL, (bool)border);
    */

    private native void nColumns(int count, String id, boolean border); /*
        ImGui::Columns((int)count, (const char *)id, (bool)border);
    */

    private native void nNextColumn(); /*
        ImGui::NextColumn();
    */

    private native int nGetColumnIndex(); /*
        return ImGui::GetColumnIndex();
    */

    private native float nGetColumnWidth(); /*
        return ImGui::GetColumnWidth();
    */

    private native float nGetColumnWidth(int columnIndex); /*
        return ImGui::GetColumnWidth((int)columnIndex);
    */

    private native void nSetColumnWidth(int columnIndex, float width); /*
        ImGui::SetColumnWidth((int)columnIndex, (float)width);
    */

    private native float nGetColumnOffset(); /*
        return ImGui::GetColumnOffset();
    */

    private native float nGetColumnOffset(int columnIndex); /*
        return ImGui::GetColumnOffset((int)columnIndex);
    */

    private native void nSetColumnOffset(int columnIndex, float offsetX); /*
        ImGui::SetColumnOffset((int)columnIndex, (float)offsetX);
    */

    private native int nGetColumnsCount(); /*
        return ImGui::GetColumnsCount();
    */

    private native boolean nBeginTabBar(String strId); /*
        return ImGui::BeginTabBar((const char *)strId);
    */

    private native boolean nBeginTabBar(String strId, int flags); /*
        return ImGui::BeginTabBar((const char *)strId, (ImGuiTabBarFlags)flags);
    */

    private native void nEndTabBar(); /*
        ImGui::EndTabBar();
    */

    private native boolean nBeginTabItem(String label); /*
        return ImGui::BeginTabItem((const char *)label);
    */

    private native boolean nBeginTabItem(String label, boolean[] pOpen); /*
        return ImGui::BeginTabItem((const char *)label, (bool *)&pOpen[0]);
    */

    private native boolean nBeginTabItem(String label, int flags); /*
        return ImGui::BeginTabItem((const char *)label, NULL, (ImGuiTabItemFlags)flags);
    */

    private native boolean nBeginTabItem(String label, boolean[] pOpen, int flags); /*
        return ImGui::BeginTabItem((const char *)label, (bool *)&pOpen[0], (ImGuiTabItemFlags)flags);
    */

    private native void nEndTabItem(); /*
        ImGui::EndTabItem();
    */

    private native boolean nTabItemButton(String label); /*
        return ImGui::TabItemButton((const char *)label);
    */

    private native boolean nTabItemButton(String label, int flags); /*
        return ImGui::TabItemButton((const char *)label, (ImGuiTabItemFlags)flags);
    */

    private native void nSetTabItemClosed(String tabOrDockedWindowLabel); /*
        ImGui::SetTabItemClosed((const char *)tabOrDockedWindowLabel);
    */

    private native int nDockSpace(int id); /*
        return ImGui::DockSpace((ImGuiID)id);
    */

    private native int nDockSpace(int id, float sizeX, float sizeY); /*
        return ImGui::DockSpace((ImGuiID)id, (const ImVec2 &)ImVec2(sizeX, sizeY));
    */

    private native int nDockSpace(int id, int flags); /*
        return ImGui::DockSpace((ImGuiID)id, ImVec2(0, 0), (ImGuiDockNodeFlags)flags);
    */

    private native int nDockSpace(int id, float sizeX, float sizeY, int flags); /*
        return ImGui::DockSpace((ImGuiID)id, (const ImVec2 &)ImVec2(sizeX, sizeY), (ImGuiDockNodeFlags)flags);
    */

    private native int nDockSpace(int id, int flags, long windowClass); /*
        return ImGui::DockSpace((ImGuiID)id, ImVec2(0, 0), (ImGuiDockNodeFlags)flags, (const ImGuiWindowClass *)windowClass);
    */

    private native int nDockSpace(int id, float sizeX, float sizeY, long windowClass); /*
        return ImGui::DockSpace((ImGuiID)id, (const ImVec2 &)ImVec2(sizeX, sizeY), 0, (const ImGuiWindowClass *)windowClass);
    */

    private native int nDockSpace(int id, float sizeX, float sizeY, int flags, long windowClass); /*
        return ImGui::DockSpace((ImGuiID)id, (const ImVec2 &)ImVec2(sizeX, sizeY), (ImGuiDockNodeFlags)flags, (const ImGuiWindowClass *)windowClass);
    */

    private native int nDockSpaceOverViewport(); /*
        return ImGui::DockSpaceOverViewport();
    */

    private native int nDockSpaceOverViewport(long viewport); /*
        return ImGui::DockSpaceOverViewport((const ImGuiViewport *)viewport);
    */

    private native int nDockSpaceOverViewport(int flags); /*
        return ImGui::DockSpaceOverViewport(NULL, (ImGuiDockNodeFlags)flags);
    */

    private native int nDockSpaceOverViewport(long viewport, int flags); /*
        return ImGui::DockSpaceOverViewport((const ImGuiViewport *)viewport, (ImGuiDockNodeFlags)flags);
    */

    private native int nDockSpaceOverViewport(int flags, long windowClass); /*
        return ImGui::DockSpaceOverViewport(NULL, (ImGuiDockNodeFlags)flags, (const ImGuiWindowClass *)windowClass);
    */

    private native int nDockSpaceOverViewport(long viewport, long windowClass); /*
        return ImGui::DockSpaceOverViewport((const ImGuiViewport *)viewport, 0, (const ImGuiWindowClass *)windowClass);
    */

    private native int nDockSpaceOverViewport(long viewport, int flags, long windowClass); /*
        return ImGui::DockSpaceOverViewport((const ImGuiViewport *)viewport, (ImGuiDockNodeFlags)flags, (const ImGuiWindowClass *)windowClass);
    */

    private native void nSetNextWindowDockID(int dockId); /*
        ImGui::SetNextWindowDockID((ImGuiID)dockId);
    */

    private native void nSetNextWindowDockID(int dockId, int cond); /*
        ImGui::SetNextWindowDockID((ImGuiID)dockId, (ImGuiCond)cond);
    */

    private native void nSetNextWindowClass(long windowClass); /*
        ImGui::SetNextWindowClass((const ImGuiWindowClass *)windowClass);
    */

    private native int nGetWindowDockID(); /*
        return ImGui::GetWindowDockID();
    */

    private native boolean nIsWindowDocked(); /*
        return ImGui::IsWindowDocked();
    */

    private native void nLogToTTY(); /*
        ImGui::LogToTTY();
    */

    private native void nLogToTTY(int autoOpenDepth); /*
        ImGui::LogToTTY((int)autoOpenDepth);
    */

    private native void nLogToFile(); /*
        ImGui::LogToFile();
    */

    private native void nLogToFile(int autoOpenDepth); /*
        ImGui::LogToFile((int)autoOpenDepth);
    */

    private native void nLogToFile(String filename); /*
        ImGui::LogToFile(-1, (const char *)filename);
    */

    private native void nLogToFile(int autoOpenDepth, String filename); /*
        ImGui::LogToFile((int)autoOpenDepth, (const char *)filename);
    */

    private native void nLogToClipboard(); /*
        ImGui::LogToClipboard();
    */

    private native void nLogToClipboard(int autoOpenDepth); /*
        ImGui::LogToClipboard((int)autoOpenDepth);
    */

    private native void nLogFinish(); /*
        ImGui::LogFinish();
    */

    private native void nLogButtons(); /*
        ImGui::LogButtons();
    */

    private native void nLogText(String fmt); /*
        ImGui::LogText((const char *)fmt, NULL);
    */

    private native boolean nBeginDragDropSource(); /*
        return ImGui::BeginDragDropSource();
    */

    private native boolean nBeginDragDropSource(int flags); /*
        return ImGui::BeginDragDropSource((ImGuiDragDropFlags)flags);
    */

    private native boolean nSetDragDropPayload(String dataType, byte[] data, int sz, int cond); /*
        return ImGui::SetDragDropPayload(dataType, &data[0], sz, cond);
    */

    private native void nEndDragDropSource(); /*
        ImGui::EndDragDropSource();
    */

    private native boolean nBeginDragDropTarget(); /*
        return ImGui::BeginDragDropTarget();
    */

    private native boolean nAcceptDragDropPayload(String dataType, int flags); /*
        return ImGui::AcceptDragDropPayload(dataType, flags) != NULL;
    */

    private native void nEndDragDropTarget(); /*
        ImGui::EndDragDropTarget();
    */

    private native void nBeginDisabled(); /*
        ImGui::BeginDisabled();
    */

    private native void nBeginDisabled(boolean disabled); /*
        ImGui::BeginDisabled((bool)disabled);
    */

    private native void nEndDisabled(); /*
        ImGui::EndDisabled();
    */

    private native void nPushClipRect(float clipRectMinX, float clipRectMinY, float clipRectMaxX, float clipRectMaxY, boolean intersectWithCurrentClipRect); /*
        ImGui::PushClipRect((const ImVec2 &)ImVec2(clipRectMinX, clipRectMinY), (const ImVec2 &)ImVec2(clipRectMaxX, clipRectMaxY), (bool)intersectWithCurrentClipRect);
    */

    private native void nPopClipRect(); /*
        ImGui::PopClipRect();
    */

    private native void nSetItemDefaultFocus(); /*
        ImGui::SetItemDefaultFocus();
    */

    private native void nSetKeyboardFocusHere(); /*
        ImGui::SetKeyboardFocusHere();
    */

    private native void nSetKeyboardFocusHere(int offset); /*
        ImGui::SetKeyboardFocusHere((int)offset);
    */

    private native boolean nIsItemHovered(); /*
        return ImGui::IsItemHovered();
    */

    private native boolean nIsItemHovered(int flags); /*
        return ImGui::IsItemHovered((ImGuiHoveredFlags)flags);
    */

    private native boolean nIsItemActive(); /*
        return ImGui::IsItemActive();
    */

    private native boolean nIsItemFocused(); /*
        return ImGui::IsItemFocused();
    */

    private native boolean nIsItemClicked(); /*
        return ImGui::IsItemClicked();
    */

    private native boolean nIsItemClicked(int mouseButton); /*
        return ImGui::IsItemClicked((ImGuiMouseButton)mouseButton);
    */

    private native boolean nIsItemVisible(); /*
        return ImGui::IsItemVisible();
    */

    private native boolean nIsItemEdited(); /*
        return ImGui::IsItemEdited();
    */

    private native boolean nIsItemActivated(); /*
        return ImGui::IsItemActivated();
    */

    private native boolean nIsItemDeactivated(); /*
        return ImGui::IsItemDeactivated();
    */

    private native boolean nIsItemDeactivatedAfterEdit(); /*
        return ImGui::IsItemDeactivatedAfterEdit();
    */

    private native boolean nIsItemToggledOpen(); /*
        return ImGui::IsItemToggledOpen();
    */

    private native boolean nIsAnyItemHovered(); /*
        return ImGui::IsAnyItemHovered();
    */

    private native boolean nIsAnyItemActive(); /*
        return ImGui::IsAnyItemActive();
    */

    private native boolean nIsAnyItemFocused(); /*
        return ImGui::IsAnyItemFocused();
    */

    private native void nSetItemAllowOverlap(); /*
        ImGui::SetItemAllowOverlap();
    */

    private native long nGetMainViewport(); /*
        return (intptr_t)ImGui::GetMainViewport();
    */

    private native boolean nIsRectVisible(float sizeX, float sizeY); /*
        return ImGui::IsRectVisible((const ImVec2 &)ImVec2(sizeX, sizeY));
    */

    private native boolean nIsRectVisible(float rectMinX, float rectMinY, float rectMaxX, float rectMaxY); /*
        return ImGui::IsRectVisible((const ImVec2 &)ImVec2(rectMinX, rectMinY), (const ImVec2 &)ImVec2(rectMaxX, rectMaxY));
    */

    private native double nGetTime(); /*
        return ImGui::GetTime();
    */

    private native int nGetFrameCount(); /*
        return ImGui::GetFrameCount();
    */

    private native long nGetBackgroundDrawList(); /*
        return (intptr_t)ImGui::GetBackgroundDrawList();
    */

    private native long nGetBackgroundDrawList(long viewport); /*
        return (intptr_t)ImGui::GetBackgroundDrawList((ImGuiViewport *)viewport);
    */

    private native long nGetForegroundDrawList(); /*
        return (intptr_t)ImGui::GetForegroundDrawList();
    */

    private native long nGetForegroundDrawList(long viewport); /*
        return (intptr_t)ImGui::GetForegroundDrawList((ImGuiViewport *)viewport);
    */

    private native String nGetStyleColorName(int idx); /*
        return env->NewStringUTF(ImGui::GetStyleColorName((ImGuiCol)idx));
    */

    private native void nSetStateStorage(long storage); /*
        ImGui::SetStateStorage((ImGuiStorage *)storage);
    */

    private native long nGetStateStorage(); /*
        return (intptr_t)ImGui::GetStateStorage();
    */

    private native boolean nBeginChildFrame(int id, float sizeX, float sizeY); /*
        return ImGui::BeginChildFrame((ImGuiID)id, (const ImVec2 &)ImVec2(sizeX, sizeY));
    */

    private native boolean nBeginChildFrame(int id, float sizeX, float sizeY, int flags); /*
        return ImGui::BeginChildFrame((ImGuiID)id, (const ImVec2 &)ImVec2(sizeX, sizeY), (ImGuiWindowFlags)flags);
    */

    private native void nEndChildFrame(); /*
        ImGui::EndChildFrame();
    */

    private native int nColorConvertFloat4ToU32(float inX, float inY, float inZ, float inW); /*
        return ImGui::ColorConvertFloat4ToU32((const ImVec4 &)ImVec4(inX, inY, inZ, inW));
    */

    private native void nColorConvertRGBtoHSV(float[] rgb, float[] hsv); /*
        ImGui::ColorConvertRGBtoHSV(rgb[0], rgb[1], rgb[2], hsv[0], hsv[1], hsv[2]);
    */

    private native void nColorConvertHSVtoRGB(float[] hsv, float[] rgb); /*
        ImGui::ColorConvertHSVtoRGB(hsv[0], hsv[1], hsv[2], rgb[0], rgb[1], rgb[2]);
    */

    private native int nGetKeyIndex(int imguiKey); /*
        return ImGui::GetKeyIndex((ImGuiKey)imguiKey);
    */

    private native boolean nIsKeyDown(int userKeyIndex); /*
        return ImGui::IsKeyDown((int)userKeyIndex);
    */

    private native boolean nIsKeyPressed(int userKeyIndex); /*
        return ImGui::IsKeyPressed((int)userKeyIndex);
    */

    private native boolean nIsKeyPressed(int userKeyIndex, boolean repeat); /*
        return ImGui::IsKeyPressed((int)userKeyIndex, (bool)repeat);
    */

    private native boolean nIsKeyReleased(int userKeyIndex); /*
        return ImGui::IsKeyReleased((int)userKeyIndex);
    */

    private native int nGetKeyPressedAmount(int keyIndex, float repeatDelay, float rate); /*
        return ImGui::GetKeyPressedAmount((int)keyIndex, (float)repeatDelay, (float)rate);
    */

    private native void nCaptureKeyboardFromApp(); /*
        ImGui::CaptureKeyboardFromApp();
    */

    private native void nCaptureKeyboardFromApp(boolean wantCaptureKeyboardValue); /*
        ImGui::CaptureKeyboardFromApp((bool)wantCaptureKeyboardValue);
    */

    private native boolean nIsMouseDown(int button); /*
        return ImGui::IsMouseDown((ImGuiMouseButton)button);
    */

    private native boolean nIsMouseClicked(int button); /*
        return ImGui::IsMouseClicked((ImGuiMouseButton)button);
    */

    private native boolean nIsMouseClicked(int button, boolean repeat); /*
        return ImGui::IsMouseClicked((ImGuiMouseButton)button, (bool)repeat);
    */

    private native boolean nIsMouseReleased(int button); /*
        return ImGui::IsMouseReleased((ImGuiMouseButton)button);
    */

    private native boolean nIsMouseDoubleClicked(int button); /*
        return ImGui::IsMouseDoubleClicked((ImGuiMouseButton)button);
    */

    private native int nGetMouseClickedCount(int button); /*
        return ImGui::GetMouseClickedCount((ImGuiMouseButton)button);
    */

    private native boolean nIsMouseHoveringRect(float rMinX, float rMinY, float rMaxX, float rMaxY); /*
        return ImGui::IsMouseHoveringRect((const ImVec2 &)ImVec2(rMinX, rMinY), (const ImVec2 &)ImVec2(rMaxX, rMaxY));
    */

    private native boolean nIsMouseHoveringRect(float rMinX, float rMinY, float rMaxX, float rMaxY, boolean clip); /*
        return ImGui::IsMouseHoveringRect((const ImVec2 &)ImVec2(rMinX, rMinY), (const ImVec2 &)ImVec2(rMaxX, rMaxY), (bool)clip);
    */

    private native boolean nIsMousePosValid(float mousePosX, float mousePosY); /*
        ImVec2 pos = ImVec2(mousePosX, mousePosY);
        return ImGui::IsMousePosValid(&pos);
    */

    private native boolean nIsAnyMouseDown(); /*
        return ImGui::IsAnyMouseDown();
    */

    private native boolean nIsMouseDragging(int button); /*
        return ImGui::IsMouseDragging((ImGuiMouseButton)button);
    */

    private native boolean nIsMouseDragging(int button, float lockThreshold); /*
        return ImGui::IsMouseDragging((ImGuiMouseButton)button, (float)lockThreshold);
    */

    private native void nResetMouseDragDelta(); /*
        ImGui::ResetMouseDragDelta();
    */

    private native void nResetMouseDragDelta(int button); /*
        ImGui::ResetMouseDragDelta((ImGuiMouseButton)button);
    */

    private native int nGetMouseCursor(); /*
        return ImGui::GetMouseCursor();
    */

    private native void nSetMouseCursor(int cursorType); /*
        ImGui::SetMouseCursor((ImGuiMouseCursor)cursorType);
    */

    private native void nCaptureMouseFromApp(); /*
        ImGui::CaptureMouseFromApp();
    */

    private native void nCaptureMouseFromApp(boolean wantCaptureMouseValue); /*
        ImGui::CaptureMouseFromApp((bool)wantCaptureMouseValue);
    */

    private native String nGetClipboardText(); /*
        return env->NewStringUTF(ImGui::GetClipboardText());
    */

    private native void nSetClipboardText(String text); /*
        ImGui::SetClipboardText((const char *)text);
    */

    private native void nLoadIniSettingsFromDisk(String iniFilename); /*
        ImGui::LoadIniSettingsFromDisk((const char *)iniFilename);
    */

    private native void nLoadIniSettingsFromMemory(String iniData); /*
        ImGui::LoadIniSettingsFromMemory((const char *)iniData);
    */

    private native void nLoadIniSettingsFromMemory(String iniData, long iniSize); /*
        ImGui::LoadIniSettingsFromMemory((const char *)iniData, (size_t)iniSize);
    */

    private native void nSaveIniSettingsToDisk(String iniFilename); /*
        ImGui::SaveIniSettingsToDisk((const char *)iniFilename);
    */

    private native void nSaveIniSettingsToMemory(); /*
        ImGui::SaveIniSettingsToMemory();
    */

    private native void nSaveIniSettingsToMemory(int outIniSize); /*
        ImGui::SaveIniSettingsToMemory((size_t*)&outIniSize);
    */

    private native boolean nDebugCheckVersionAndDataLayout(String versionStr, long szIo, long szStyle, long szVec2, long szVec4, long szDrawvert, long szDrawidx); /*
        return ImGui::DebugCheckVersionAndDataLayout((const char *)versionStr, (size_t)szIo, (size_t)szStyle, (size_t)szVec2, (size_t)szVec4, (size_t)szDrawvert, (size_t)szDrawidx);
    */

    private native long nGetPlatformIO(); /*
        return (intptr_t)&ImGui::GetPlatformIO();
    */

    private native void nUpdatePlatformWindows(); /*
        ImGui::UpdatePlatformWindows();
    */

    private native void nRenderPlatformWindowsDefault(); /*
        ImGui::RenderPlatformWindowsDefault();
    */

    private native void nDestroyPlatformWindows(); /*
        ImGui::DestroyPlatformWindows();
    */

    private native long nFindViewportByID(int id); /*
        return (intptr_t)ImGui::FindViewportByID((ImGuiID)id);
    */

    private native long nFindViewportByPlatformHandle(long platformHandle); /*
        return (intptr_t)ImGui::FindViewportByPlatformHandle((void*)platformHandle);
    */

    private native int nGetColorU32i(int col); /*
        return ImGui::GetColorU32((ImU32)col);
    */

    private native boolean nHasDragDropPayload(); /*
        const ImGuiPayload* payload = ImGui::GetDragDropPayload();
        return payload != NULL && payload->Data != NULL;
    */

    private native boolean nHasDragDropPayload(String dataType); /*
        const ImGuiPayload* payload = ImGui::GetDragDropPayload();
        return payload != NULL && payload->IsDataType(dataType);
    */

    private native void nInitInputTextData(); /*
        jclass jInputDataClass = env->FindClass("imgui/type/ImString$InputData");
        inputDataSizeID = env->GetFieldID(jInputDataClass, "size", "I");
        inputDataIsDirtyID = env->GetFieldID(jInputDataClass, "isDirty", "Z");
        inputDataIsResizedID = env->GetFieldID(jInputDataClass, "isResized", "Z");
    
        jclass jImString = env->FindClass("imgui/type/ImString");
        jImStringResizeInternalMID = env->GetMethodID(jImString, "resizeInternal", "(I)[B");
    
        jclass jCallback = env->FindClass("imgui/callback/ImGuiInputTextCallback");
        jInputTextCallbackMID = env->GetMethodID(jCallback, "accept", "(J)V");
    */

    private native float nGetWindowPosX(); /*
        return ImGui::GetWindowPos().x;
    */

    private native float nGetWindowPosY(); /*
        return ImGui::GetWindowPos().y;
    */

    private native float nGetWindowSizeX(); /*
        return ImGui::GetWindowSize().x;
    */

    private native float nGetWindowSizeY(); /*
        return ImGui::GetWindowSize().y;
    */

    private native float nGetContentRegionAvailX(); /*
        return ImGui::GetContentRegionAvail().x;
    */

    private native float nGetContentRegionAvailY(); /*
        return ImGui::GetContentRegionAvail().y;
    */

    private native float nGetContentRegionMaxX(); /*
        return ImGui::GetContentRegionMax().x;
    */

    private native float nGetContentRegionMaxY(); /*
        return ImGui::GetContentRegionMax().y;
    */

    private native float nGetWindowContentRegionMinX(); /*
        return ImGui::GetWindowContentRegionMin().x;
    */

    private native float nGetWindowContentRegionMinY(); /*
        return ImGui::GetWindowContentRegionMin().y;
    */

    private native float nGetWindowContentRegionMaxX(); /*
        return ImGui::GetWindowContentRegionMax().x;
    */

    private native float nGetWindowContentRegionMaxY(); /*
        return ImGui::GetWindowContentRegionMax().y;
    */

    private native float nGetFontTexUvWhitePixelX(); /*
        return ImGui::GetFontTexUvWhitePixel().x;
    */

    private native float nGetFontTexUvWhitePixelY(); /*
        return ImGui::GetFontTexUvWhitePixel().y;
    */

    private native float nGetStyleColorVec4X(int idx); /*
        return ImGui::GetStyleColorVec4((ImGuiCol)idx).x;
    */

    private native float nGetStyleColorVec4Y(int idx); /*
        return ImGui::GetStyleColorVec4((ImGuiCol)idx).y;
    */

    private native float nGetStyleColorVec4Z(int idx); /*
        return ImGui::GetStyleColorVec4((ImGuiCol)idx).z;
    */

    private native float nGetStyleColorVec4W(int idx); /*
        return ImGui::GetStyleColorVec4((ImGuiCol)idx).w;
    */

    private native float nGetCursorStartPosX(); /*
        return ImGui::GetCursorStartPos().x;
    */

    private native float nGetCursorStartPosY(); /*
        return ImGui::GetCursorStartPos().y;
    */

    private native float nGetCursorScreenPosX(); /*
        return ImGui::GetCursorScreenPos().x;
    */

    private native float nGetCursorScreenPosY(); /*
        return ImGui::GetCursorScreenPos().y;
    */

    private native float nGetItemRectMinX(); /*
        return ImGui::GetItemRectMin().x;
    */

    private native float nGetItemRectMinY(); /*
        return ImGui::GetItemRectMin().y;
    */

    private native float nGetItemRectMaxX(); /*
        return ImGui::GetItemRectMax().x;
    */

    private native float nGetItemRectMaxY(); /*
        return ImGui::GetItemRectMax().y;
    */

    private native float nGetItemRectSizeX(); /*
        return ImGui::GetItemRectSize().x;
    */

    private native float nGetItemRectSizeY(); /*
        return ImGui::GetItemRectSize().y;
    */

    private native float nCalcTextSizeX(String text); /*
        return ImGui::CalcTextSize((const char *)text).x;
    */

    private native float nCalcTextSizeY(String text); /*
        return ImGui::CalcTextSize((const char *)text).y;
    */

    private native float nCalcTextSizeX(String text, String textEnd); /*
        return ImGui::CalcTextSize((const char *)text, (const char *)textEnd).x;
    */

    private native float nCalcTextSizeY(String text, String textEnd); /*
        return ImGui::CalcTextSize((const char *)text, (const char *)textEnd).y;
    */

    private native float nCalcTextSizeX(String text, String textEnd, boolean hideTextAfterDoubleHash); /*
        return ImGui::CalcTextSize((const char *)text, (const char *)textEnd, (bool)hideTextAfterDoubleHash).x;
    */

    private native float nCalcTextSizeY(String text, String textEnd, boolean hideTextAfterDoubleHash); /*
        return ImGui::CalcTextSize((const char *)text, (const char *)textEnd, (bool)hideTextAfterDoubleHash).y;
    */

    private native float nCalcTextSizeX(String text, String textEnd, float wrapWidth); /*
        return ImGui::CalcTextSize((const char *)text, (const char *)textEnd, false, (float)wrapWidth).x;
    */

    private native float nCalcTextSizeY(String text, String textEnd, float wrapWidth); /*
        return ImGui::CalcTextSize((const char *)text, (const char *)textEnd, false, (float)wrapWidth).y;
    */

    private native float nCalcTextSizeX(String text, String textEnd, boolean hideTextAfterDoubleHash, float wrapWidth); /*
        return ImGui::CalcTextSize((const char *)text, (const char *)textEnd, (bool)hideTextAfterDoubleHash, (float)wrapWidth).x;
    */

    private native float nCalcTextSizeY(String text, String textEnd, boolean hideTextAfterDoubleHash, float wrapWidth); /*
        return ImGui::CalcTextSize((const char *)text, (const char *)textEnd, (bool)hideTextAfterDoubleHash, (float)wrapWidth).y;
    */

    private native float nColorConvertU32ToFloat4X(int in); /*
        return ImGui::ColorConvertU32ToFloat4((ImU32)in).x;
    */

    private native float nColorConvertU32ToFloat4Y(int in); /*
        return ImGui::ColorConvertU32ToFloat4((ImU32)in).y;
    */

    private native float nColorConvertU32ToFloat4Z(int in); /*
        return ImGui::ColorConvertU32ToFloat4((ImU32)in).z;
    */

    private native float nColorConvertU32ToFloat4W(int in); /*
        return ImGui::ColorConvertU32ToFloat4((ImU32)in).w;
    */

    private native float nGetMousePosX(); /*
        return ImGui::GetMousePos().x;
    */

    private native float nGetMousePosY(); /*
        return ImGui::GetMousePos().y;
    */

    private native float nGetMousePosOnOpeningCurrentPopupX(); /*
        return ImGui::GetMousePosOnOpeningCurrentPopup().x;
    */

    private native float nGetMousePosOnOpeningCurrentPopupY(); /*
        return ImGui::GetMousePosOnOpeningCurrentPopup().y;
    */

    private native float nGetMouseDragDeltaX(); /*
        return ImGui::GetMouseDragDelta().x;
    */

    private native float nGetMouseDragDeltaY(); /*
        return ImGui::GetMouseDragDelta().y;
    */

    private native float nGetMouseDragDeltaX(int button); /*
        return ImGui::GetMouseDragDelta((ImGuiMouseButton)button).x;
    */

    private native float nGetMouseDragDeltaY(int button); /*
        return ImGui::GetMouseDragDelta((ImGuiMouseButton)button).y;
    */

    private native float nGetMouseDragDeltaX(float lockThreshold); /*
        return ImGui::GetMouseDragDelta(0, (float)lockThreshold).x;
    */

    private native float nGetMouseDragDeltaY(float lockThreshold); /*
        return ImGui::GetMouseDragDelta(0, (float)lockThreshold).y;
    */

    private native float nGetMouseDragDeltaX(int button, float lockThreshold); /*
        return ImGui::GetMouseDragDelta((ImGuiMouseButton)button, (float)lockThreshold).x;
    */

    private native float nGetMouseDragDeltaY(int button, float lockThreshold); /*
        return ImGui::GetMouseDragDelta((ImGuiMouseButton)button, (float)lockThreshold).y;
    */
    // GENERATED API: END
}
