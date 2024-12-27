package imgui;

import imgui.binding.ImGuiStruct;

/**
 * Draw command list
 * This is the low-level list of polygons that ImGui:: functions are filling. At the end of the frame,
 * all command lists are passed to your ImGuiIO::RenderDrawListFn function for rendering.
 * Each dear imgui window contains its own ImDrawList. You can use ImGui::GetWindowDrawList() to
 * access the current window draw list and draw custom primitives.
 * You can interleave normal ImGui:: calls and adding primitives to the current draw list.
 * All positions are generally in pixel coordinates (top-left at (0,0), bottom-right at io.DisplaySize),
 * but you are totally free to apply whatever transformation matrix to want to the data (if you apply such transformation you'll want to apply it to ClipRect as well)
 * Important: Primitives are always added to the list and not culled (culling is done at higher-level by ImGui:: functions), if you use this API a lot consider coarse culling your drawn objects.
 */
public final class ImDrawList extends ImGuiStruct {
    public ImDrawList(final long ptr) {
        super(ptr);
    }

    /*JNI
        #include "_common.h"
        #define THIS ((ImDrawList*)STRUCT_PTR)
     */

    /**
     * Flags, you may poke into these to adjust anti-aliasing settings per-primitive.
     */
    public int getFlags() {
        return nGetFlags();
    }

    /**
     * Flags, you may poke into these to adjust anti-aliasing settings per-primitive.
     */
    public void setFlags(final int value) {
        nSetFlags(value);
    }

    /**
     * Flags, you may poke into these to adjust anti-aliasing settings per-primitive.
     */
    public void addFlags(final int flags) {
        setFlags(getFlags() | flags);
    }

    /**
     * Flags, you may poke into these to adjust anti-aliasing settings per-primitive.
     */
    public void removeFlags(final int flags) {
        setFlags(getFlags() & ~(flags));
    }

    /**
     * Flags, you may poke into these to adjust anti-aliasing settings per-primitive.
     */
    public boolean hasFlags(final int flags) {
        return (getFlags() & flags) != 0;
    }

    private native int nGetFlags(); /*
        return THIS->Flags;
    */

    private native void nSetFlags(int value); /*
        THIS->Flags = value;
    */

    /**
     * Render-level scissoring.
     * This is passed down to your render function but not used for CPU-side coarse clipping.
     * Prefer using higher-level ImGui::PushClipRect() to affect logic (hit-testing and widget culling)
     */
    public void pushClipRect(final ImVec2 clipRectMin, final ImVec2 clipRectMax) {
        nPushClipRect(clipRectMin.x, clipRectMin.y, clipRectMax.x, clipRectMax.y);
    }

    /**
     * Render-level scissoring.
     * This is passed down to your render function but not used for CPU-side coarse clipping.
     * Prefer using higher-level ImGui::PushClipRect() to affect logic (hit-testing and widget culling)
     */
    public void pushClipRect(final float clipRectMinX, final float clipRectMinY, final float clipRectMaxX, final float clipRectMaxY) {
        nPushClipRect(clipRectMinX, clipRectMinY, clipRectMaxX, clipRectMaxY);
    }

    /**
     * Render-level scissoring.
     * This is passed down to your render function but not used for CPU-side coarse clipping.
     * Prefer using higher-level ImGui::PushClipRect() to affect logic (hit-testing and widget culling)
     */
    public void pushClipRect(final ImVec2 clipRectMin, final ImVec2 clipRectMax, final boolean intersectWithCurrentClipRect) {
        nPushClipRect(clipRectMin.x, clipRectMin.y, clipRectMax.x, clipRectMax.y, intersectWithCurrentClipRect);
    }

    /**
     * Render-level scissoring.
     * This is passed down to your render function but not used for CPU-side coarse clipping.
     * Prefer using higher-level ImGui::PushClipRect() to affect logic (hit-testing and widget culling)
     */
    public void pushClipRect(final float clipRectMinX, final float clipRectMinY, final float clipRectMaxX, final float clipRectMaxY, final boolean intersectWithCurrentClipRect) {
        nPushClipRect(clipRectMinX, clipRectMinY, clipRectMaxX, clipRectMaxY, intersectWithCurrentClipRect);
    }

    private native void nPushClipRect(float clipRectMinX, float clipRectMinY, float clipRectMaxX, float clipRectMaxY); /*MANUAL
        ImVec2 clipRectMin = ImVec2(clipRectMinX, clipRectMinY);
        ImVec2 clipRectMax = ImVec2(clipRectMaxX, clipRectMaxY);
        THIS->PushClipRect(clipRectMin, clipRectMax);
    */

    private native void nPushClipRect(float clipRectMinX, float clipRectMinY, float clipRectMaxX, float clipRectMaxY, boolean intersectWithCurrentClipRect); /*MANUAL
        ImVec2 clipRectMin = ImVec2(clipRectMinX, clipRectMinY);
        ImVec2 clipRectMax = ImVec2(clipRectMaxX, clipRectMaxY);
        THIS->PushClipRect(clipRectMin, clipRectMax, intersectWithCurrentClipRect);
    */

    public void pushClipRectFullScreen() {
        nPushClipRectFullScreen();
    }

    private native void nPushClipRectFullScreen(); /*
        THIS->PushClipRectFullScreen();
    */

    public void popClipRect() {
        nPopClipRect();
    }

    private native void nPopClipRect(); /*
        THIS->PopClipRect();
    */

    public void pushTextureID(final long textureId) {
        nPushTextureID(textureId);
    }

    private native void nPushTextureID(long textureId); /*
        THIS->PushTextureID((ImTextureID)(uintptr_t)textureId);
    */

    public void popTextureID() {
        nPopTextureID();
    }

    private native void nPopTextureID(); /*
        THIS->PopTextureID();
    */

    public ImVec2 getClipRectMin() {
        final ImVec2 dst = new ImVec2();
        nGetClipRectMin(dst);
        return dst;
    }

    public float getClipRectMinX() {
        return nGetClipRectMinX();
    }

    public float getClipRectMinY() {
        return nGetClipRectMinY();
    }

    public void getClipRectMin(final ImVec2 dst) {
        nGetClipRectMin(dst);
    }

    private native void nGetClipRectMin(ImVec2 dst); /*
        Jni::ImVec2Cpy(env, THIS->GetClipRectMin(), dst);
    */

    private native float nGetClipRectMinX(); /*
        return THIS->GetClipRectMin().x;
    */

    private native float nGetClipRectMinY(); /*
        return THIS->GetClipRectMin().y;
    */

    public ImVec2 getClipRectMax() {
        final ImVec2 dst = new ImVec2();
        nGetClipRectMax(dst);
        return dst;
    }

    public float getClipRectMaxX() {
        return nGetClipRectMaxX();
    }

    public float getClipRectMaxY() {
        return nGetClipRectMaxY();
    }

    public void getClipRectMax(final ImVec2 dst) {
        nGetClipRectMax(dst);
    }

    private native void nGetClipRectMax(ImVec2 dst); /*
        Jni::ImVec2Cpy(env, THIS->GetClipRectMax(), dst);
    */

    private native float nGetClipRectMaxX(); /*
        return THIS->GetClipRectMax().x;
    */

    private native float nGetClipRectMaxY(); /*
        return THIS->GetClipRectMax().y;
    */

    // Primitives
    // - Filled shapes must always use clockwise winding order. The anti-aliasing fringe depends on it. Counter-clockwise shapes will have "inward" anti-aliasing.
    // - For rectangular primitives, "p_min" and "p_max" represent the upper-left and lower-right corners.
    // - For circle primitives, use "num_segments == 0" to automatically calculate tessellation (preferred).
    //   In older versions (until Dear ImGui 1.77) the AddCircle functions defaulted to num_segments == 12.
    //   In future versions we will use textures to provide cheaper and higher-quality circles.
    //   Use AddNgon() and AddNgonFilled() functions if you need to guaranteed a specific number of sides.

    public void addLine(final ImVec2 p1, final ImVec2 p2, final int col) {
        nAddLine(p1.x, p1.y, p2.x, p2.y, col);
    }

    public void addLine(final float p1X, final float p1Y, final float p2X, final float p2Y, final int col) {
        nAddLine(p1X, p1Y, p2X, p2Y, col);
    }

    public void addLine(final ImVec2 p1, final ImVec2 p2, final int col, final float thickness) {
        nAddLine(p1.x, p1.y, p2.x, p2.y, col, thickness);
    }

    public void addLine(final float p1X, final float p1Y, final float p2X, final float p2Y, final int col, final float thickness) {
        nAddLine(p1X, p1Y, p2X, p2Y, col, thickness);
    }

    private native void nAddLine(float p1X, float p1Y, float p2X, float p2Y, int col); /*MANUAL
        ImVec2 p1 = ImVec2(p1X, p1Y);
        ImVec2 p2 = ImVec2(p2X, p2Y);
        THIS->AddLine(p1, p2, col);
    */

    private native void nAddLine(float p1X, float p1Y, float p2X, float p2Y, int col, float thickness); /*MANUAL
        ImVec2 p1 = ImVec2(p1X, p1Y);
        ImVec2 p2 = ImVec2(p2X, p2Y);
        THIS->AddLine(p1, p2, col, thickness);
    */

    public void addRect(final ImVec2 pMin, final ImVec2 pMax, final int col) {
        nAddRect(pMin.x, pMin.y, pMax.x, pMax.y, col);
    }

    public void addRect(final float pMinX, final float pMinY, final float pMaxX, final float pMaxY, final int col) {
        nAddRect(pMinX, pMinY, pMaxX, pMaxY, col);
    }

    public void addRect(final ImVec2 pMin, final ImVec2 pMax, final int col, final float rounding) {
        nAddRect(pMin.x, pMin.y, pMax.x, pMax.y, col, rounding);
    }

    public void addRect(final float pMinX, final float pMinY, final float pMaxX, final float pMaxY, final int col, final float rounding) {
        nAddRect(pMinX, pMinY, pMaxX, pMaxY, col, rounding);
    }

    public void addRect(final ImVec2 pMin, final ImVec2 pMax, final int col, final float rounding, final int flags) {
        nAddRect(pMin.x, pMin.y, pMax.x, pMax.y, col, rounding, flags);
    }

    public void addRect(final float pMinX, final float pMinY, final float pMaxX, final float pMaxY, final int col, final float rounding, final int flags) {
        nAddRect(pMinX, pMinY, pMaxX, pMaxY, col, rounding, flags);
    }

    public void addRect(final ImVec2 pMin, final ImVec2 pMax, final int col, final float rounding, final int flags, final float thickness) {
        nAddRect(pMin.x, pMin.y, pMax.x, pMax.y, col, rounding, flags, thickness);
    }

    public void addRect(final float pMinX, final float pMinY, final float pMaxX, final float pMaxY, final int col, final float rounding, final int flags, final float thickness) {
        nAddRect(pMinX, pMinY, pMaxX, pMaxY, col, rounding, flags, thickness);
    }

    public void addRect(final ImVec2 pMin, final ImVec2 pMax, final int col, final int flags, final float thickness) {
        nAddRect(pMin.x, pMin.y, pMax.x, pMax.y, col, flags, thickness);
    }

    public void addRect(final float pMinX, final float pMinY, final float pMaxX, final float pMaxY, final int col, final int flags, final float thickness) {
        nAddRect(pMinX, pMinY, pMaxX, pMaxY, col, flags, thickness);
    }

    public void addRect(final ImVec2 pMin, final ImVec2 pMax, final int col, final float rounding, final float thickness) {
        nAddRect(pMin.x, pMin.y, pMax.x, pMax.y, col, rounding, thickness);
    }

    public void addRect(final float pMinX, final float pMinY, final float pMaxX, final float pMaxY, final int col, final float rounding, final float thickness) {
        nAddRect(pMinX, pMinY, pMaxX, pMaxY, col, rounding, thickness);
    }

    private native void nAddRect(float pMinX, float pMinY, float pMaxX, float pMaxY, int col); /*MANUAL
        ImVec2 pMin = ImVec2(pMinX, pMinY);
        ImVec2 pMax = ImVec2(pMaxX, pMaxY);
        THIS->AddRect(pMin, pMax, col);
    */

    private native void nAddRect(float pMinX, float pMinY, float pMaxX, float pMaxY, int col, float rounding); /*MANUAL
        ImVec2 pMin = ImVec2(pMinX, pMinY);
        ImVec2 pMax = ImVec2(pMaxX, pMaxY);
        THIS->AddRect(pMin, pMax, col, rounding);
    */

    private native void nAddRect(float pMinX, float pMinY, float pMaxX, float pMaxY, int col, float rounding, int flags); /*MANUAL
        ImVec2 pMin = ImVec2(pMinX, pMinY);
        ImVec2 pMax = ImVec2(pMaxX, pMaxY);
        THIS->AddRect(pMin, pMax, col, rounding, flags);
    */

    private native void nAddRect(float pMinX, float pMinY, float pMaxX, float pMaxY, int col, float rounding, int flags, float thickness); /*MANUAL
        ImVec2 pMin = ImVec2(pMinX, pMinY);
        ImVec2 pMax = ImVec2(pMaxX, pMaxY);
        THIS->AddRect(pMin, pMax, col, rounding, flags, thickness);
    */

    private native void nAddRect(float pMinX, float pMinY, float pMaxX, float pMaxY, int col, int flags, float thickness); /*MANUAL
        ImVec2 pMin = ImVec2(pMinX, pMinY);
        ImVec2 pMax = ImVec2(pMaxX, pMaxY);
        THIS->AddRect(pMin, pMax, col, 0.0f, flags, thickness);
    */

    private native void nAddRect(float pMinX, float pMinY, float pMaxX, float pMaxY, int col, float rounding, float thickness); /*MANUAL
        ImVec2 pMin = ImVec2(pMinX, pMinY);
        ImVec2 pMax = ImVec2(pMaxX, pMaxY);
        THIS->AddRect(pMin, pMax, col, rounding, 0, thickness);
    */

    public void addRectFilled(final ImVec2 pMin, final ImVec2 pMax, final int col) {
        nAddRectFilled(pMin.x, pMin.y, pMax.x, pMax.y, col);
    }

    public void addRectFilled(final float pMinX, final float pMinY, final float pMaxX, final float pMaxY, final int col) {
        nAddRectFilled(pMinX, pMinY, pMaxX, pMaxY, col);
    }

    public void addRectFilled(final ImVec2 pMin, final ImVec2 pMax, final int col, final float rounding) {
        nAddRectFilled(pMin.x, pMin.y, pMax.x, pMax.y, col, rounding);
    }

    public void addRectFilled(final float pMinX, final float pMinY, final float pMaxX, final float pMaxY, final int col, final float rounding) {
        nAddRectFilled(pMinX, pMinY, pMaxX, pMaxY, col, rounding);
    }

    public void addRectFilled(final ImVec2 pMin, final ImVec2 pMax, final int col, final float rounding, final int flags) {
        nAddRectFilled(pMin.x, pMin.y, pMax.x, pMax.y, col, rounding, flags);
    }

    public void addRectFilled(final float pMinX, final float pMinY, final float pMaxX, final float pMaxY, final int col, final float rounding, final int flags) {
        nAddRectFilled(pMinX, pMinY, pMaxX, pMaxY, col, rounding, flags);
    }

    public void addRectFilled(final ImVec2 pMin, final ImVec2 pMax, final int col, final int flags) {
        nAddRectFilled(pMin.x, pMin.y, pMax.x, pMax.y, col, flags);
    }

    public void addRectFilled(final float pMinX, final float pMinY, final float pMaxX, final float pMaxY, final int col, final int flags) {
        nAddRectFilled(pMinX, pMinY, pMaxX, pMaxY, col, flags);
    }

    private native void nAddRectFilled(float pMinX, float pMinY, float pMaxX, float pMaxY, int col); /*MANUAL
        ImVec2 pMin = ImVec2(pMinX, pMinY);
        ImVec2 pMax = ImVec2(pMaxX, pMaxY);
        THIS->AddRectFilled(pMin, pMax, col);
    */

    private native void nAddRectFilled(float pMinX, float pMinY, float pMaxX, float pMaxY, int col, float rounding); /*MANUAL
        ImVec2 pMin = ImVec2(pMinX, pMinY);
        ImVec2 pMax = ImVec2(pMaxX, pMaxY);
        THIS->AddRectFilled(pMin, pMax, col, rounding);
    */

    private native void nAddRectFilled(float pMinX, float pMinY, float pMaxX, float pMaxY, int col, float rounding, int flags); /*MANUAL
        ImVec2 pMin = ImVec2(pMinX, pMinY);
        ImVec2 pMax = ImVec2(pMaxX, pMaxY);
        THIS->AddRectFilled(pMin, pMax, col, rounding, flags);
    */

    private native void nAddRectFilled(float pMinX, float pMinY, float pMaxX, float pMaxY, int col, int flags); /*MANUAL
        ImVec2 pMin = ImVec2(pMinX, pMinY);
        ImVec2 pMax = ImVec2(pMaxX, pMaxY);
        THIS->AddRectFilled(pMin, pMax, col, 0.0f, flags);
    */

    public void addRectFilledMultiColor(final ImVec2 pMin, final ImVec2 pMax, final int colUprLeft, final int colUprRight, final int colBotRight, final int colBotLeft) {
        nAddRectFilledMultiColor(pMin.x, pMin.y, pMax.x, pMax.y, colUprLeft, colUprRight, colBotRight, colBotLeft);
    }

    public void addRectFilledMultiColor(final float pMinX, final float pMinY, final float pMaxX, final float pMaxY, final int colUprLeft, final int colUprRight, final int colBotRight, final int colBotLeft) {
        nAddRectFilledMultiColor(pMinX, pMinY, pMaxX, pMaxY, colUprLeft, colUprRight, colBotRight, colBotLeft);
    }

    private native void nAddRectFilledMultiColor(float pMinX, float pMinY, float pMaxX, float pMaxY, int colUprLeft, int colUprRight, int colBotRight, int colBotLeft); /*MANUAL
        ImVec2 pMin = ImVec2(pMinX, pMinY);
        ImVec2 pMax = ImVec2(pMaxX, pMaxY);
        THIS->AddRectFilledMultiColor(pMin, pMax, colUprLeft, colUprRight, colBotRight, colBotLeft);
    */

    public void addQuad(final ImVec2 p1, final ImVec2 p2, final ImVec2 p3, final ImVec2 p4, final int col) {
        nAddQuad(p1.x, p1.y, p2.x, p2.y, p3.x, p3.y, p4.x, p4.y, col);
    }

    public void addQuad(final float p1X, final float p1Y, final float p2X, final float p2Y, final float p3X, final float p3Y, final float p4X, final float p4Y, final int col) {
        nAddQuad(p1X, p1Y, p2X, p2Y, p3X, p3Y, p4X, p4Y, col);
    }

    public void addQuad(final ImVec2 p1, final ImVec2 p2, final ImVec2 p3, final ImVec2 p4, final int col, final float thickness) {
        nAddQuad(p1.x, p1.y, p2.x, p2.y, p3.x, p3.y, p4.x, p4.y, col, thickness);
    }

    public void addQuad(final float p1X, final float p1Y, final float p2X, final float p2Y, final float p3X, final float p3Y, final float p4X, final float p4Y, final int col, final float thickness) {
        nAddQuad(p1X, p1Y, p2X, p2Y, p3X, p3Y, p4X, p4Y, col, thickness);
    }

    private native void nAddQuad(float p1X, float p1Y, float p2X, float p2Y, float p3X, float p3Y, float p4X, float p4Y, int col); /*MANUAL
        ImVec2 p1 = ImVec2(p1X, p1Y);
        ImVec2 p2 = ImVec2(p2X, p2Y);
        ImVec2 p3 = ImVec2(p3X, p3Y);
        ImVec2 p4 = ImVec2(p4X, p4Y);
        THIS->AddQuad(p1, p2, p3, p4, col);
    */

    private native void nAddQuad(float p1X, float p1Y, float p2X, float p2Y, float p3X, float p3Y, float p4X, float p4Y, int col, float thickness); /*MANUAL
        ImVec2 p1 = ImVec2(p1X, p1Y);
        ImVec2 p2 = ImVec2(p2X, p2Y);
        ImVec2 p3 = ImVec2(p3X, p3Y);
        ImVec2 p4 = ImVec2(p4X, p4Y);
        THIS->AddQuad(p1, p2, p3, p4, col, thickness);
    */

    public void addQuadFilled(final ImVec2 p1, final ImVec2 p2, final ImVec2 p3, final ImVec2 p4, final int col) {
        nAddQuadFilled(p1.x, p1.y, p2.x, p2.y, p3.x, p3.y, p4.x, p4.y, col);
    }

    public void addQuadFilled(final float p1X, final float p1Y, final float p2X, final float p2Y, final float p3X, final float p3Y, final float p4X, final float p4Y, final int col) {
        nAddQuadFilled(p1X, p1Y, p2X, p2Y, p3X, p3Y, p4X, p4Y, col);
    }

    private native void nAddQuadFilled(float p1X, float p1Y, float p2X, float p2Y, float p3X, float p3Y, float p4X, float p4Y, int col); /*MANUAL
        ImVec2 p1 = ImVec2(p1X, p1Y);
        ImVec2 p2 = ImVec2(p2X, p2Y);
        ImVec2 p3 = ImVec2(p3X, p3Y);
        ImVec2 p4 = ImVec2(p4X, p4Y);
        THIS->AddQuadFilled(p1, p2, p3, p4, col);
    */

    public void addTriangle(final ImVec2 p1, final ImVec2 p2, final ImVec2 p3, final int col) {
        nAddTriangle(p1.x, p1.y, p2.x, p2.y, p3.x, p3.y, col);
    }

    public void addTriangle(final float p1X, final float p1Y, final float p2X, final float p2Y, final float p3X, final float p3Y, final int col) {
        nAddTriangle(p1X, p1Y, p2X, p2Y, p3X, p3Y, col);
    }

    public void addTriangle(final ImVec2 p1, final ImVec2 p2, final ImVec2 p3, final int col, final float thickness) {
        nAddTriangle(p1.x, p1.y, p2.x, p2.y, p3.x, p3.y, col, thickness);
    }

    public void addTriangle(final float p1X, final float p1Y, final float p2X, final float p2Y, final float p3X, final float p3Y, final int col, final float thickness) {
        nAddTriangle(p1X, p1Y, p2X, p2Y, p3X, p3Y, col, thickness);
    }

    private native void nAddTriangle(float p1X, float p1Y, float p2X, float p2Y, float p3X, float p3Y, int col); /*MANUAL
        ImVec2 p1 = ImVec2(p1X, p1Y);
        ImVec2 p2 = ImVec2(p2X, p2Y);
        ImVec2 p3 = ImVec2(p3X, p3Y);
        THIS->AddTriangle(p1, p2, p3, col);
    */

    private native void nAddTriangle(float p1X, float p1Y, float p2X, float p2Y, float p3X, float p3Y, int col, float thickness); /*MANUAL
        ImVec2 p1 = ImVec2(p1X, p1Y);
        ImVec2 p2 = ImVec2(p2X, p2Y);
        ImVec2 p3 = ImVec2(p3X, p3Y);
        THIS->AddTriangle(p1, p2, p3, col, thickness);
    */

    public void addTriangleFilled(final ImVec2 p1, final ImVec2 p2, final ImVec2 p3, final int col) {
        nAddTriangleFilled(p1.x, p1.y, p2.x, p2.y, p3.x, p3.y, col);
    }

    public void addTriangleFilled(final float p1X, final float p1Y, final float p2X, final float p2Y, final float p3X, final float p3Y, final int col) {
        nAddTriangleFilled(p1X, p1Y, p2X, p2Y, p3X, p3Y, col);
    }

    private native void nAddTriangleFilled(float p1X, float p1Y, float p2X, float p2Y, float p3X, float p3Y, int col); /*MANUAL
        ImVec2 p1 = ImVec2(p1X, p1Y);
        ImVec2 p2 = ImVec2(p2X, p2Y);
        ImVec2 p3 = ImVec2(p3X, p3Y);
        THIS->AddTriangleFilled(p1, p2, p3, col);
    */

    public void addCircle(final ImVec2 center, final float radius, final int col) {
        nAddCircle(center.x, center.y, radius, col);
    }

    public void addCircle(final float centerX, final float centerY, final float radius, final int col) {
        nAddCircle(centerX, centerY, radius, col);
    }

    public void addCircle(final ImVec2 center, final float radius, final int col, final int numSegments) {
        nAddCircle(center.x, center.y, radius, col, numSegments);
    }

    public void addCircle(final float centerX, final float centerY, final float radius, final int col, final int numSegments) {
        nAddCircle(centerX, centerY, radius, col, numSegments);
    }

    public void addCircle(final ImVec2 center, final float radius, final int col, final int numSegments, final float thickness) {
        nAddCircle(center.x, center.y, radius, col, numSegments, thickness);
    }

    public void addCircle(final float centerX, final float centerY, final float radius, final int col, final int numSegments, final float thickness) {
        nAddCircle(centerX, centerY, radius, col, numSegments, thickness);
    }

    public void addCircle(final ImVec2 center, final float radius, final int col, final float thickness) {
        nAddCircle(center.x, center.y, radius, col, thickness);
    }

    public void addCircle(final float centerX, final float centerY, final float radius, final int col, final float thickness) {
        nAddCircle(centerX, centerY, radius, col, thickness);
    }

    private native void nAddCircle(float centerX, float centerY, float radius, int col); /*MANUAL
        ImVec2 center = ImVec2(centerX, centerY);
        THIS->AddCircle(center, radius, col);
    */

    private native void nAddCircle(float centerX, float centerY, float radius, int col, int numSegments); /*MANUAL
        ImVec2 center = ImVec2(centerX, centerY);
        THIS->AddCircle(center, radius, col, numSegments);
    */

    private native void nAddCircle(float centerX, float centerY, float radius, int col, int numSegments, float thickness); /*MANUAL
        ImVec2 center = ImVec2(centerX, centerY);
        THIS->AddCircle(center, radius, col, numSegments, thickness);
    */

    private native void nAddCircle(float centerX, float centerY, float radius, int col, float thickness); /*MANUAL
        ImVec2 center = ImVec2(centerX, centerY);
        THIS->AddCircle(center, radius, col, 0, thickness);
    */

    public void addCircleFilled(final ImVec2 center, final float radius, final int col) {
        nAddCircleFilled(center.x, center.y, radius, col);
    }

    public void addCircleFilled(final float centerX, final float centerY, final float radius, final int col) {
        nAddCircleFilled(centerX, centerY, radius, col);
    }

    public void addCircleFilled(final ImVec2 center, final float radius, final int col, final int numSegments) {
        nAddCircleFilled(center.x, center.y, radius, col, numSegments);
    }

    public void addCircleFilled(final float centerX, final float centerY, final float radius, final int col, final int numSegments) {
        nAddCircleFilled(centerX, centerY, radius, col, numSegments);
    }

    private native void nAddCircleFilled(float centerX, float centerY, float radius, int col); /*MANUAL
        ImVec2 center = ImVec2(centerX, centerY);
        THIS->AddCircleFilled(center, radius, col);
    */

    private native void nAddCircleFilled(float centerX, float centerY, float radius, int col, int numSegments); /*MANUAL
        ImVec2 center = ImVec2(centerX, centerY);
        THIS->AddCircleFilled(center, radius, col, numSegments);
    */

    public void addNgon(final ImVec2 center, final float radius, final int col, final int num_segments) {
        nAddNgon(center.x, center.y, radius, col, num_segments);
    }

    public void addNgon(final float centerX, final float centerY, final float radius, final int col, final int num_segments) {
        nAddNgon(centerX, centerY, radius, col, num_segments);
    }

    public void addNgon(final ImVec2 center, final float radius, final int col, final int num_segments, final float thickness) {
        nAddNgon(center.x, center.y, radius, col, num_segments, thickness);
    }

    public void addNgon(final float centerX, final float centerY, final float radius, final int col, final int num_segments, final float thickness) {
        nAddNgon(centerX, centerY, radius, col, num_segments, thickness);
    }

    private native void nAddNgon(float centerX, float centerY, float radius, int col, int num_segments); /*MANUAL
        ImVec2 center = ImVec2(centerX, centerY);
        THIS->AddNgon(center, radius, col, num_segments);
    */

    private native void nAddNgon(float centerX, float centerY, float radius, int col, int num_segments, float thickness); /*MANUAL
        ImVec2 center = ImVec2(centerX, centerY);
        THIS->AddNgon(center, radius, col, num_segments, thickness);
    */

    public void addNgonFilled(final ImVec2 center, final float radius, final int col, final int num_segments) {
        nAddNgonFilled(center.x, center.y, radius, col, num_segments);
    }

    public void addNgonFilled(final float centerX, final float centerY, final float radius, final int col, final int num_segments) {
        nAddNgonFilled(centerX, centerY, radius, col, num_segments);
    }

    private native void nAddNgonFilled(float centerX, float centerY, float radius, int col, int num_segments); /*MANUAL
        ImVec2 center = ImVec2(centerX, centerY);
        THIS->AddNgonFilled(center, radius, col, num_segments);
    */

    public void addText(final ImVec2 pos, final int col, final String textBegin) {
        nAddText(pos.x, pos.y, col, textBegin);
    }

    public void addText(final float posX, final float posY, final int col, final String textBegin) {
        nAddText(posX, posY, col, textBegin);
    }

    public void addText(final ImVec2 pos, final int col, final String textBegin, final String textEnd) {
        nAddText(pos.x, pos.y, col, textBegin, textEnd);
    }

    public void addText(final float posX, final float posY, final int col, final String textBegin, final String textEnd) {
        nAddText(posX, posY, col, textBegin, textEnd);
    }

    private native void nAddText(float posX, float posY, int col, String textBegin); /*MANUAL
        auto textBegin = obj_textBegin == NULL ? NULL : (char*)env->GetStringUTFChars(obj_textBegin, JNI_FALSE);
        ImVec2 pos = ImVec2(posX, posY);
        THIS->AddText(pos, col, textBegin);
        if (textBegin != NULL) env->ReleaseStringUTFChars(obj_textBegin, textBegin);
    */

    private native void nAddText(float posX, float posY, int col, String textBegin, String textEnd); /*MANUAL
        auto textBegin = obj_textBegin == NULL ? NULL : (char*)env->GetStringUTFChars(obj_textBegin, JNI_FALSE);
        auto textEnd = obj_textEnd == NULL ? NULL : (char*)env->GetStringUTFChars(obj_textEnd, JNI_FALSE);
        ImVec2 pos = ImVec2(posX, posY);
        THIS->AddText(pos, col, textBegin, textEnd);
        if (textBegin != NULL) env->ReleaseStringUTFChars(obj_textBegin, textBegin);
        if (textEnd != NULL) env->ReleaseStringUTFChars(obj_textEnd, textEnd);
    */

    public void addText(final ImFont font, final int fontSize, final ImVec2 pos, final int col, final String textBegin) {
        nAddText(font.ptr, fontSize, pos.x, pos.y, col, textBegin);
    }

    public void addText(final ImFont font, final int fontSize, final float posX, final float posY, final int col, final String textBegin) {
        nAddText(font.ptr, fontSize, posX, posY, col, textBegin);
    }

    public void addText(final ImFont font, final int fontSize, final ImVec2 pos, final int col, final String textBegin, final String textEnd) {
        nAddText(font.ptr, fontSize, pos.x, pos.y, col, textBegin, textEnd);
    }

    public void addText(final ImFont font, final int fontSize, final float posX, final float posY, final int col, final String textBegin, final String textEnd) {
        nAddText(font.ptr, fontSize, posX, posY, col, textBegin, textEnd);
    }

    public void addText(final ImFont font, final int fontSize, final ImVec2 pos, final int col, final String textBegin, final String textEnd, final float wrapWidth) {
        nAddText(font.ptr, fontSize, pos.x, pos.y, col, textBegin, textEnd, wrapWidth);
    }

    public void addText(final ImFont font, final int fontSize, final float posX, final float posY, final int col, final String textBegin, final String textEnd, final float wrapWidth) {
        nAddText(font.ptr, fontSize, posX, posY, col, textBegin, textEnd, wrapWidth);
    }

    public void addText(final ImFont font, final int fontSize, final ImVec2 pos, final int col, final String textBegin, final String textEnd, final float wrapWidth, final ImVec4 cpuFineClipRect) {
        nAddText(font.ptr, fontSize, pos.x, pos.y, col, textBegin, textEnd, wrapWidth, cpuFineClipRect.x, cpuFineClipRect.y, cpuFineClipRect.z, cpuFineClipRect.w);
    }

    public void addText(final ImFont font, final int fontSize, final float posX, final float posY, final int col, final String textBegin, final String textEnd, final float wrapWidth, final float cpuFineClipRectX, final float cpuFineClipRectY, final float cpuFineClipRectZ, final float cpuFineClipRectW) {
        nAddText(font.ptr, fontSize, posX, posY, col, textBegin, textEnd, wrapWidth, cpuFineClipRectX, cpuFineClipRectY, cpuFineClipRectZ, cpuFineClipRectW);
    }

    public void addText(final ImFont font, final int fontSize, final ImVec2 pos, final int col, final String textBegin, final float wrapWidth, final ImVec4 cpuFineClipRect) {
        nAddText(font.ptr, fontSize, pos.x, pos.y, col, textBegin, wrapWidth, cpuFineClipRect.x, cpuFineClipRect.y, cpuFineClipRect.z, cpuFineClipRect.w);
    }

    public void addText(final ImFont font, final int fontSize, final float posX, final float posY, final int col, final String textBegin, final float wrapWidth, final float cpuFineClipRectX, final float cpuFineClipRectY, final float cpuFineClipRectZ, final float cpuFineClipRectW) {
        nAddText(font.ptr, fontSize, posX, posY, col, textBegin, wrapWidth, cpuFineClipRectX, cpuFineClipRectY, cpuFineClipRectZ, cpuFineClipRectW);
    }

    public void addText(final ImFont font, final int fontSize, final ImVec2 pos, final int col, final String textBegin, final ImVec4 cpuFineClipRect) {
        nAddText(font.ptr, fontSize, pos.x, pos.y, col, textBegin, cpuFineClipRect.x, cpuFineClipRect.y, cpuFineClipRect.z, cpuFineClipRect.w);
    }

    public void addText(final ImFont font, final int fontSize, final float posX, final float posY, final int col, final String textBegin, final float cpuFineClipRectX, final float cpuFineClipRectY, final float cpuFineClipRectZ, final float cpuFineClipRectW) {
        nAddText(font.ptr, fontSize, posX, posY, col, textBegin, cpuFineClipRectX, cpuFineClipRectY, cpuFineClipRectZ, cpuFineClipRectW);
    }

    public void addText(final ImFont font, final int fontSize, final ImVec2 pos, final int col, final String textBegin, final String textEnd, final ImVec4 cpuFineClipRect) {
        nAddText(font.ptr, fontSize, pos.x, pos.y, col, textBegin, textEnd, cpuFineClipRect.x, cpuFineClipRect.y, cpuFineClipRect.z, cpuFineClipRect.w);
    }

    public void addText(final ImFont font, final int fontSize, final float posX, final float posY, final int col, final String textBegin, final String textEnd, final float cpuFineClipRectX, final float cpuFineClipRectY, final float cpuFineClipRectZ, final float cpuFineClipRectW) {
        nAddText(font.ptr, fontSize, posX, posY, col, textBegin, textEnd, cpuFineClipRectX, cpuFineClipRectY, cpuFineClipRectZ, cpuFineClipRectW);
    }

    private native void nAddText(long font, int fontSize, float posX, float posY, int col, String textBegin); /*MANUAL
        auto textBegin = obj_textBegin == NULL ? NULL : (char*)env->GetStringUTFChars(obj_textBegin, JNI_FALSE);
        ImVec2 pos = ImVec2(posX, posY);
        THIS->AddText(reinterpret_cast<ImFont*>(font), fontSize, pos, col, textBegin);
        if (textBegin != NULL) env->ReleaseStringUTFChars(obj_textBegin, textBegin);
    */

    private native void nAddText(long font, int fontSize, float posX, float posY, int col, String textBegin, String textEnd); /*MANUAL
        auto textBegin = obj_textBegin == NULL ? NULL : (char*)env->GetStringUTFChars(obj_textBegin, JNI_FALSE);
        auto textEnd = obj_textEnd == NULL ? NULL : (char*)env->GetStringUTFChars(obj_textEnd, JNI_FALSE);
        ImVec2 pos = ImVec2(posX, posY);
        THIS->AddText(reinterpret_cast<ImFont*>(font), fontSize, pos, col, textBegin, textEnd);
        if (textBegin != NULL) env->ReleaseStringUTFChars(obj_textBegin, textBegin);
        if (textEnd != NULL) env->ReleaseStringUTFChars(obj_textEnd, textEnd);
    */

    private native void nAddText(long font, int fontSize, float posX, float posY, int col, String textBegin, String textEnd, float wrapWidth); /*MANUAL
        auto textBegin = obj_textBegin == NULL ? NULL : (char*)env->GetStringUTFChars(obj_textBegin, JNI_FALSE);
        auto textEnd = obj_textEnd == NULL ? NULL : (char*)env->GetStringUTFChars(obj_textEnd, JNI_FALSE);
        ImVec2 pos = ImVec2(posX, posY);
        THIS->AddText(reinterpret_cast<ImFont*>(font), fontSize, pos, col, textBegin, textEnd, wrapWidth);
        if (textBegin != NULL) env->ReleaseStringUTFChars(obj_textBegin, textBegin);
        if (textEnd != NULL) env->ReleaseStringUTFChars(obj_textEnd, textEnd);
    */

    private native void nAddText(long font, int fontSize, float posX, float posY, int col, String textBegin, String textEnd, float wrapWidth, float cpuFineClipRectX, float cpuFineClipRectY, float cpuFineClipRectZ, float cpuFineClipRectW); /*MANUAL
        auto textBegin = obj_textBegin == NULL ? NULL : (char*)env->GetStringUTFChars(obj_textBegin, JNI_FALSE);
        auto textEnd = obj_textEnd == NULL ? NULL : (char*)env->GetStringUTFChars(obj_textEnd, JNI_FALSE);
        ImVec2 pos = ImVec2(posX, posY);
        ImVec4 cpuFineClipRect = ImVec4(cpuFineClipRectX, cpuFineClipRectY, cpuFineClipRectZ, cpuFineClipRectW);
        THIS->AddText(reinterpret_cast<ImFont*>(font), fontSize, pos, col, textBegin, textEnd, wrapWidth, &cpuFineClipRect);
        if (textBegin != NULL) env->ReleaseStringUTFChars(obj_textBegin, textBegin);
        if (textEnd != NULL) env->ReleaseStringUTFChars(obj_textEnd, textEnd);
    */

    private native void nAddText(long font, int fontSize, float posX, float posY, int col, String textBegin, float wrapWidth, float cpuFineClipRectX, float cpuFineClipRectY, float cpuFineClipRectZ, float cpuFineClipRectW); /*MANUAL
        auto textBegin = obj_textBegin == NULL ? NULL : (char*)env->GetStringUTFChars(obj_textBegin, JNI_FALSE);
        ImVec2 pos = ImVec2(posX, posY);
        ImVec4 cpuFineClipRect = ImVec4(cpuFineClipRectX, cpuFineClipRectY, cpuFineClipRectZ, cpuFineClipRectW);
        THIS->AddText(reinterpret_cast<ImFont*>(font), fontSize, pos, col, textBegin, NULL, wrapWidth, &cpuFineClipRect);
        if (textBegin != NULL) env->ReleaseStringUTFChars(obj_textBegin, textBegin);
    */

    private native void nAddText(long font, int fontSize, float posX, float posY, int col, String textBegin, float cpuFineClipRectX, float cpuFineClipRectY, float cpuFineClipRectZ, float cpuFineClipRectW); /*MANUAL
        auto textBegin = obj_textBegin == NULL ? NULL : (char*)env->GetStringUTFChars(obj_textBegin, JNI_FALSE);
        ImVec2 pos = ImVec2(posX, posY);
        ImVec4 cpuFineClipRect = ImVec4(cpuFineClipRectX, cpuFineClipRectY, cpuFineClipRectZ, cpuFineClipRectW);
        THIS->AddText(reinterpret_cast<ImFont*>(font), fontSize, pos, col, textBegin, NULL, 0.0f, &cpuFineClipRect);
        if (textBegin != NULL) env->ReleaseStringUTFChars(obj_textBegin, textBegin);
    */

    private native void nAddText(long font, int fontSize, float posX, float posY, int col, String textBegin, String textEnd, float cpuFineClipRectX, float cpuFineClipRectY, float cpuFineClipRectZ, float cpuFineClipRectW); /*MANUAL
        auto textBegin = obj_textBegin == NULL ? NULL : (char*)env->GetStringUTFChars(obj_textBegin, JNI_FALSE);
        auto textEnd = obj_textEnd == NULL ? NULL : (char*)env->GetStringUTFChars(obj_textEnd, JNI_FALSE);
        ImVec2 pos = ImVec2(posX, posY);
        ImVec4 cpuFineClipRect = ImVec4(cpuFineClipRectX, cpuFineClipRectY, cpuFineClipRectZ, cpuFineClipRectW);
        THIS->AddText(reinterpret_cast<ImFont*>(font), fontSize, pos, col, textBegin, textEnd, 0.0f, &cpuFineClipRect);
        if (textBegin != NULL) env->ReleaseStringUTFChars(obj_textBegin, textBegin);
        if (textEnd != NULL) env->ReleaseStringUTFChars(obj_textEnd, textEnd);
    */

    public void addPolyline(final ImVec2[] points, final int numPoint, final int col, final int imDrawFlags, final float thickness) {
        nAddPolyline(points, numPoint, col, imDrawFlags, thickness);
    }

    private native void nAddPolyline(ImVec2[] obj_points, int numPoint, int col, int imDrawFlags, float thickness); /*MANUAL
        int pointsLength = env->GetArrayLength(obj_points);
        ImVec2 points[pointsLength];
        for (int i = 0; i < pointsLength; i++) {
            jobject src = env->GetObjectArrayElement(obj_points, i);
            ImVec2 dst;
            Jni::ImVec2Cpy(env, src, &dst);
            points[i] = dst;
        };
        THIS->AddPolyline(points, numPoint, col, imDrawFlags, thickness);
    */

    public void addConvexPolyFilled(final ImVec2[] points, final int numPoints, final int col) {
        nAddConvexPolyFilled(points, numPoints, col);
    }

    private native void nAddConvexPolyFilled(ImVec2[] obj_points, int numPoints, int col); /*MANUAL
        int pointsLength = env->GetArrayLength(obj_points);
        ImVec2 points[pointsLength];
        for (int i = 0; i < pointsLength; i++) {
            jobject src = env->GetObjectArrayElement(obj_points, i);
            ImVec2 dst;
            Jni::ImVec2Cpy(env, src, &dst);
            points[i] = dst;
        };
        THIS->AddConvexPolyFilled(points, numPoints, col);
    */

    /**
     * Cubic Bezier (4 control points)
     */
    public void addBezierCubic(final ImVec2 p1, final ImVec2 p2, final ImVec2 p3, final ImVec2 p4, final int col, final float thickness) {
        nAddBezierCubic(p1.x, p1.y, p2.x, p2.y, p3.x, p3.y, p4.x, p4.y, col, thickness);
    }

    /**
     * Cubic Bezier (4 control points)
     */
    public void addBezierCubic(final float p1X, final float p1Y, final float p2X, final float p2Y, final float p3X, final float p3Y, final float p4X, final float p4Y, final int col, final float thickness) {
        nAddBezierCubic(p1X, p1Y, p2X, p2Y, p3X, p3Y, p4X, p4Y, col, thickness);
    }

    /**
     * Cubic Bezier (4 control points)
     */
    public void addBezierCubic(final ImVec2 p1, final ImVec2 p2, final ImVec2 p3, final ImVec2 p4, final int col, final float thickness, final int numSegments) {
        nAddBezierCubic(p1.x, p1.y, p2.x, p2.y, p3.x, p3.y, p4.x, p4.y, col, thickness, numSegments);
    }

    /**
     * Cubic Bezier (4 control points)
     */
    public void addBezierCubic(final float p1X, final float p1Y, final float p2X, final float p2Y, final float p3X, final float p3Y, final float p4X, final float p4Y, final int col, final float thickness, final int numSegments) {
        nAddBezierCubic(p1X, p1Y, p2X, p2Y, p3X, p3Y, p4X, p4Y, col, thickness, numSegments);
    }

    private native void nAddBezierCubic(float p1X, float p1Y, float p2X, float p2Y, float p3X, float p3Y, float p4X, float p4Y, int col, float thickness); /*MANUAL
        ImVec2 p1 = ImVec2(p1X, p1Y);
        ImVec2 p2 = ImVec2(p2X, p2Y);
        ImVec2 p3 = ImVec2(p3X, p3Y);
        ImVec2 p4 = ImVec2(p4X, p4Y);
        THIS->AddBezierCubic(p1, p2, p3, p4, col, thickness);
    */

    private native void nAddBezierCubic(float p1X, float p1Y, float p2X, float p2Y, float p3X, float p3Y, float p4X, float p4Y, int col, float thickness, int numSegments); /*MANUAL
        ImVec2 p1 = ImVec2(p1X, p1Y);
        ImVec2 p2 = ImVec2(p2X, p2Y);
        ImVec2 p3 = ImVec2(p3X, p3Y);
        ImVec2 p4 = ImVec2(p4X, p4Y);
        THIS->AddBezierCubic(p1, p2, p3, p4, col, thickness, numSegments);
    */

    /**
     * Quadratic Bezier (3 control points)
     */
    public void addBezierQuadratic(final ImVec2 p1, final ImVec2 p2, final ImVec2 p3, final int col, final float thickness) {
        nAddBezierQuadratic(p1.x, p1.y, p2.x, p2.y, p3.x, p3.y, col, thickness);
    }

    /**
     * Quadratic Bezier (3 control points)
     */
    public void addBezierQuadratic(final float p1X, final float p1Y, final float p2X, final float p2Y, final float p3X, final float p3Y, final int col, final float thickness) {
        nAddBezierQuadratic(p1X, p1Y, p2X, p2Y, p3X, p3Y, col, thickness);
    }

    /**
     * Quadratic Bezier (3 control points)
     */
    public void addBezierQuadratic(final ImVec2 p1, final ImVec2 p2, final ImVec2 p3, final int col, final float thickness, final int numSegments) {
        nAddBezierQuadratic(p1.x, p1.y, p2.x, p2.y, p3.x, p3.y, col, thickness, numSegments);
    }

    /**
     * Quadratic Bezier (3 control points)
     */
    public void addBezierQuadratic(final float p1X, final float p1Y, final float p2X, final float p2Y, final float p3X, final float p3Y, final int col, final float thickness, final int numSegments) {
        nAddBezierQuadratic(p1X, p1Y, p2X, p2Y, p3X, p3Y, col, thickness, numSegments);
    }

    private native void nAddBezierQuadratic(float p1X, float p1Y, float p2X, float p2Y, float p3X, float p3Y, int col, float thickness); /*MANUAL
        ImVec2 p1 = ImVec2(p1X, p1Y);
        ImVec2 p2 = ImVec2(p2X, p2Y);
        ImVec2 p3 = ImVec2(p3X, p3Y);
        THIS->AddBezierQuadratic(p1, p2, p3, col, thickness);
    */

    private native void nAddBezierQuadratic(float p1X, float p1Y, float p2X, float p2Y, float p3X, float p3Y, int col, float thickness, int numSegments); /*MANUAL
        ImVec2 p1 = ImVec2(p1X, p1Y);
        ImVec2 p2 = ImVec2(p2X, p2Y);
        ImVec2 p3 = ImVec2(p3X, p3Y);
        THIS->AddBezierQuadratic(p1, p2, p3, col, thickness, numSegments);
    */

    // Image primitives
    // - Read FAQ to understand what ImTextureID is.
    // - "pMin" and "pMax" represent the upper-left and lower-right corners of the rectangle.
    // - "uvMin" and "uvMax" represent the normalized texture coordinates to use for those corners. Using (0,0)->(1,1) texture coordinates will generally display the entire texture.

    public void addImage(final long textureID, final ImVec2 pMin, final ImVec2 pMax) {
        nAddImage(textureID, pMin.x, pMin.y, pMax.x, pMax.y);
    }

    public void addImage(final long textureID, final float pMinX, final float pMinY, final float pMaxX, final float pMaxY) {
        nAddImage(textureID, pMinX, pMinY, pMaxX, pMaxY);
    }

    public void addImage(final long textureID, final ImVec2 pMin, final ImVec2 pMax, final ImVec2 uvMin) {
        nAddImage(textureID, pMin.x, pMin.y, pMax.x, pMax.y, uvMin.x, uvMin.y);
    }

    public void addImage(final long textureID, final float pMinX, final float pMinY, final float pMaxX, final float pMaxY, final float uvMinX, final float uvMinY) {
        nAddImage(textureID, pMinX, pMinY, pMaxX, pMaxY, uvMinX, uvMinY);
    }

    public void addImage(final long textureID, final ImVec2 pMin, final ImVec2 pMax, final ImVec2 uvMin, final ImVec2 uvMax) {
        nAddImage(textureID, pMin.x, pMin.y, pMax.x, pMax.y, uvMin.x, uvMin.y, uvMax.x, uvMax.y);
    }

    public void addImage(final long textureID, final float pMinX, final float pMinY, final float pMaxX, final float pMaxY, final float uvMinX, final float uvMinY, final float uvMaxX, final float uvMaxY) {
        nAddImage(textureID, pMinX, pMinY, pMaxX, pMaxY, uvMinX, uvMinY, uvMaxX, uvMaxY);
    }

    public void addImage(final long textureID, final ImVec2 pMin, final ImVec2 pMax, final ImVec2 uvMin, final ImVec2 uvMax, final int col) {
        nAddImage(textureID, pMin.x, pMin.y, pMax.x, pMax.y, uvMin.x, uvMin.y, uvMax.x, uvMax.y, col);
    }

    public void addImage(final long textureID, final float pMinX, final float pMinY, final float pMaxX, final float pMaxY, final float uvMinX, final float uvMinY, final float uvMaxX, final float uvMaxY, final int col) {
        nAddImage(textureID, pMinX, pMinY, pMaxX, pMaxY, uvMinX, uvMinY, uvMaxX, uvMaxY, col);
    }

    private native void nAddImage(long textureID, float pMinX, float pMinY, float pMaxX, float pMaxY); /*MANUAL
        ImVec2 pMin = ImVec2(pMinX, pMinY);
        ImVec2 pMax = ImVec2(pMaxX, pMaxY);
        THIS->AddImage((ImTextureID)(uintptr_t)textureID, pMin, pMax);
    */

    private native void nAddImage(long textureID, float pMinX, float pMinY, float pMaxX, float pMaxY, float uvMinX, float uvMinY); /*MANUAL
        ImVec2 pMin = ImVec2(pMinX, pMinY);
        ImVec2 pMax = ImVec2(pMaxX, pMaxY);
        ImVec2 uvMin = ImVec2(uvMinX, uvMinY);
        THIS->AddImage((ImTextureID)(uintptr_t)textureID, pMin, pMax, uvMin);
    */

    private native void nAddImage(long textureID, float pMinX, float pMinY, float pMaxX, float pMaxY, float uvMinX, float uvMinY, float uvMaxX, float uvMaxY); /*MANUAL
        ImVec2 pMin = ImVec2(pMinX, pMinY);
        ImVec2 pMax = ImVec2(pMaxX, pMaxY);
        ImVec2 uvMin = ImVec2(uvMinX, uvMinY);
        ImVec2 uvMax = ImVec2(uvMaxX, uvMaxY);
        THIS->AddImage((ImTextureID)(uintptr_t)textureID, pMin, pMax, uvMin, uvMax);
    */

    private native void nAddImage(long textureID, float pMinX, float pMinY, float pMaxX, float pMaxY, float uvMinX, float uvMinY, float uvMaxX, float uvMaxY, int col); /*MANUAL
        ImVec2 pMin = ImVec2(pMinX, pMinY);
        ImVec2 pMax = ImVec2(pMaxX, pMaxY);
        ImVec2 uvMin = ImVec2(uvMinX, uvMinY);
        ImVec2 uvMax = ImVec2(uvMaxX, uvMaxY);
        THIS->AddImage((ImTextureID)(uintptr_t)textureID, pMin, pMax, uvMin, uvMax, col);
    */

    public void addImageQuad(final long textureID, final ImVec2 p1, final ImVec2 p2, final ImVec2 p3, final ImVec2 p4) {
        nAddImageQuad(textureID, p1.x, p1.y, p2.x, p2.y, p3.x, p3.y, p4.x, p4.y);
    }

    public void addImageQuad(final long textureID, final float p1X, final float p1Y, final float p2X, final float p2Y, final float p3X, final float p3Y, final float p4X, final float p4Y) {
        nAddImageQuad(textureID, p1X, p1Y, p2X, p2Y, p3X, p3Y, p4X, p4Y);
    }

    public void addImageQuad(final long textureID, final ImVec2 p1, final ImVec2 p2, final ImVec2 p3, final ImVec2 p4, final ImVec2 uv1) {
        nAddImageQuad(textureID, p1.x, p1.y, p2.x, p2.y, p3.x, p3.y, p4.x, p4.y, uv1.x, uv1.y);
    }

    public void addImageQuad(final long textureID, final float p1X, final float p1Y, final float p2X, final float p2Y, final float p3X, final float p3Y, final float p4X, final float p4Y, final float uv1X, final float uv1Y) {
        nAddImageQuad(textureID, p1X, p1Y, p2X, p2Y, p3X, p3Y, p4X, p4Y, uv1X, uv1Y);
    }

    public void addImageQuad(final long textureID, final ImVec2 p1, final ImVec2 p2, final ImVec2 p3, final ImVec2 p4, final ImVec2 uv1, final ImVec2 uv2) {
        nAddImageQuad(textureID, p1.x, p1.y, p2.x, p2.y, p3.x, p3.y, p4.x, p4.y, uv1.x, uv1.y, uv2.x, uv2.y);
    }

    public void addImageQuad(final long textureID, final float p1X, final float p1Y, final float p2X, final float p2Y, final float p3X, final float p3Y, final float p4X, final float p4Y, final float uv1X, final float uv1Y, final float uv2X, final float uv2Y) {
        nAddImageQuad(textureID, p1X, p1Y, p2X, p2Y, p3X, p3Y, p4X, p4Y, uv1X, uv1Y, uv2X, uv2Y);
    }

    public void addImageQuad(final long textureID, final ImVec2 p1, final ImVec2 p2, final ImVec2 p3, final ImVec2 p4, final ImVec2 uv1, final ImVec2 uv2, final ImVec2 uv3) {
        nAddImageQuad(textureID, p1.x, p1.y, p2.x, p2.y, p3.x, p3.y, p4.x, p4.y, uv1.x, uv1.y, uv2.x, uv2.y, uv3.x, uv3.y);
    }

    public void addImageQuad(final long textureID, final float p1X, final float p1Y, final float p2X, final float p2Y, final float p3X, final float p3Y, final float p4X, final float p4Y, final float uv1X, final float uv1Y, final float uv2X, final float uv2Y, final float uv3X, final float uv3Y) {
        nAddImageQuad(textureID, p1X, p1Y, p2X, p2Y, p3X, p3Y, p4X, p4Y, uv1X, uv1Y, uv2X, uv2Y, uv3X, uv3Y);
    }

    public void addImageQuad(final long textureID, final ImVec2 p1, final ImVec2 p2, final ImVec2 p3, final ImVec2 p4, final ImVec2 uv1, final ImVec2 uv2, final ImVec2 uv3, final ImVec2 uv4) {
        nAddImageQuad(textureID, p1.x, p1.y, p2.x, p2.y, p3.x, p3.y, p4.x, p4.y, uv1.x, uv1.y, uv2.x, uv2.y, uv3.x, uv3.y, uv4.x, uv4.y);
    }

    public void addImageQuad(final long textureID, final float p1X, final float p1Y, final float p2X, final float p2Y, final float p3X, final float p3Y, final float p4X, final float p4Y, final float uv1X, final float uv1Y, final float uv2X, final float uv2Y, final float uv3X, final float uv3Y, final float uv4X, final float uv4Y) {
        nAddImageQuad(textureID, p1X, p1Y, p2X, p2Y, p3X, p3Y, p4X, p4Y, uv1X, uv1Y, uv2X, uv2Y, uv3X, uv3Y, uv4X, uv4Y);
    }

    public void addImageQuad(final long textureID, final ImVec2 p1, final ImVec2 p2, final ImVec2 p3, final ImVec2 p4, final ImVec2 uv1, final ImVec2 uv2, final ImVec2 uv3, final ImVec2 uv4, final int col) {
        nAddImageQuad(textureID, p1.x, p1.y, p2.x, p2.y, p3.x, p3.y, p4.x, p4.y, uv1.x, uv1.y, uv2.x, uv2.y, uv3.x, uv3.y, uv4.x, uv4.y, col);
    }

    public void addImageQuad(final long textureID, final float p1X, final float p1Y, final float p2X, final float p2Y, final float p3X, final float p3Y, final float p4X, final float p4Y, final float uv1X, final float uv1Y, final float uv2X, final float uv2Y, final float uv3X, final float uv3Y, final float uv4X, final float uv4Y, final int col) {
        nAddImageQuad(textureID, p1X, p1Y, p2X, p2Y, p3X, p3Y, p4X, p4Y, uv1X, uv1Y, uv2X, uv2Y, uv3X, uv3Y, uv4X, uv4Y, col);
    }

    private native void nAddImageQuad(long textureID, float p1X, float p1Y, float p2X, float p2Y, float p3X, float p3Y, float p4X, float p4Y); /*MANUAL
        ImVec2 p1 = ImVec2(p1X, p1Y);
        ImVec2 p2 = ImVec2(p2X, p2Y);
        ImVec2 p3 = ImVec2(p3X, p3Y);
        ImVec2 p4 = ImVec2(p4X, p4Y);
        THIS->AddImageQuad((ImTextureID)(uintptr_t)textureID, p1, p2, p3, p4);
    */

    private native void nAddImageQuad(long textureID, float p1X, float p1Y, float p2X, float p2Y, float p3X, float p3Y, float p4X, float p4Y, float uv1X, float uv1Y); /*MANUAL
        ImVec2 p1 = ImVec2(p1X, p1Y);
        ImVec2 p2 = ImVec2(p2X, p2Y);
        ImVec2 p3 = ImVec2(p3X, p3Y);
        ImVec2 p4 = ImVec2(p4X, p4Y);
        ImVec2 uv1 = ImVec2(uv1X, uv1Y);
        THIS->AddImageQuad((ImTextureID)(uintptr_t)textureID, p1, p2, p3, p4, uv1);
    */

    private native void nAddImageQuad(long textureID, float p1X, float p1Y, float p2X, float p2Y, float p3X, float p3Y, float p4X, float p4Y, float uv1X, float uv1Y, float uv2X, float uv2Y); /*MANUAL
        ImVec2 p1 = ImVec2(p1X, p1Y);
        ImVec2 p2 = ImVec2(p2X, p2Y);
        ImVec2 p3 = ImVec2(p3X, p3Y);
        ImVec2 p4 = ImVec2(p4X, p4Y);
        ImVec2 uv1 = ImVec2(uv1X, uv1Y);
        ImVec2 uv2 = ImVec2(uv2X, uv2Y);
        THIS->AddImageQuad((ImTextureID)(uintptr_t)textureID, p1, p2, p3, p4, uv1, uv2);
    */

    private native void nAddImageQuad(long textureID, float p1X, float p1Y, float p2X, float p2Y, float p3X, float p3Y, float p4X, float p4Y, float uv1X, float uv1Y, float uv2X, float uv2Y, float uv3X, float uv3Y); /*MANUAL
        ImVec2 p1 = ImVec2(p1X, p1Y);
        ImVec2 p2 = ImVec2(p2X, p2Y);
        ImVec2 p3 = ImVec2(p3X, p3Y);
        ImVec2 p4 = ImVec2(p4X, p4Y);
        ImVec2 uv1 = ImVec2(uv1X, uv1Y);
        ImVec2 uv2 = ImVec2(uv2X, uv2Y);
        ImVec2 uv3 = ImVec2(uv3X, uv3Y);
        THIS->AddImageQuad((ImTextureID)(uintptr_t)textureID, p1, p2, p3, p4, uv1, uv2, uv3);
    */

    private native void nAddImageQuad(long textureID, float p1X, float p1Y, float p2X, float p2Y, float p3X, float p3Y, float p4X, float p4Y, float uv1X, float uv1Y, float uv2X, float uv2Y, float uv3X, float uv3Y, float uv4X, float uv4Y); /*MANUAL
        ImVec2 p1 = ImVec2(p1X, p1Y);
        ImVec2 p2 = ImVec2(p2X, p2Y);
        ImVec2 p3 = ImVec2(p3X, p3Y);
        ImVec2 p4 = ImVec2(p4X, p4Y);
        ImVec2 uv1 = ImVec2(uv1X, uv1Y);
        ImVec2 uv2 = ImVec2(uv2X, uv2Y);
        ImVec2 uv3 = ImVec2(uv3X, uv3Y);
        ImVec2 uv4 = ImVec2(uv4X, uv4Y);
        THIS->AddImageQuad((ImTextureID)(uintptr_t)textureID, p1, p2, p3, p4, uv1, uv2, uv3, uv4);
    */

    private native void nAddImageQuad(long textureID, float p1X, float p1Y, float p2X, float p2Y, float p3X, float p3Y, float p4X, float p4Y, float uv1X, float uv1Y, float uv2X, float uv2Y, float uv3X, float uv3Y, float uv4X, float uv4Y, int col); /*MANUAL
        ImVec2 p1 = ImVec2(p1X, p1Y);
        ImVec2 p2 = ImVec2(p2X, p2Y);
        ImVec2 p3 = ImVec2(p3X, p3Y);
        ImVec2 p4 = ImVec2(p4X, p4Y);
        ImVec2 uv1 = ImVec2(uv1X, uv1Y);
        ImVec2 uv2 = ImVec2(uv2X, uv2Y);
        ImVec2 uv3 = ImVec2(uv3X, uv3Y);
        ImVec2 uv4 = ImVec2(uv4X, uv4Y);
        THIS->AddImageQuad((ImTextureID)(uintptr_t)textureID, p1, p2, p3, p4, uv1, uv2, uv3, uv4, col);
    */

    public void addImageRounded(final long textureID, final ImVec2 pMin, final ImVec2 pMax, final ImVec2 uvMin, final ImVec2 uvMax, final int col, final float rounding) {
        nAddImageRounded(textureID, pMin.x, pMin.y, pMax.x, pMax.y, uvMin.x, uvMin.y, uvMax.x, uvMax.y, col, rounding);
    }

    public void addImageRounded(final long textureID, final float pMinX, final float pMinY, final float pMaxX, final float pMaxY, final float uvMinX, final float uvMinY, final float uvMaxX, final float uvMaxY, final int col, final float rounding) {
        nAddImageRounded(textureID, pMinX, pMinY, pMaxX, pMaxY, uvMinX, uvMinY, uvMaxX, uvMaxY, col, rounding);
    }

    public void addImageRounded(final long textureID, final ImVec2 pMin, final ImVec2 pMax, final ImVec2 uvMin, final ImVec2 uvMax, final int col, final float rounding, final int imDrawFlags) {
        nAddImageRounded(textureID, pMin.x, pMin.y, pMax.x, pMax.y, uvMin.x, uvMin.y, uvMax.x, uvMax.y, col, rounding, imDrawFlags);
    }

    public void addImageRounded(final long textureID, final float pMinX, final float pMinY, final float pMaxX, final float pMaxY, final float uvMinX, final float uvMinY, final float uvMaxX, final float uvMaxY, final int col, final float rounding, final int imDrawFlags) {
        nAddImageRounded(textureID, pMinX, pMinY, pMaxX, pMaxY, uvMinX, uvMinY, uvMaxX, uvMaxY, col, rounding, imDrawFlags);
    }

    private native void nAddImageRounded(long textureID, float pMinX, float pMinY, float pMaxX, float pMaxY, float uvMinX, float uvMinY, float uvMaxX, float uvMaxY, int col, float rounding); /*MANUAL
        ImVec2 pMin = ImVec2(pMinX, pMinY);
        ImVec2 pMax = ImVec2(pMaxX, pMaxY);
        ImVec2 uvMin = ImVec2(uvMinX, uvMinY);
        ImVec2 uvMax = ImVec2(uvMaxX, uvMaxY);
        THIS->AddImageRounded((ImTextureID)(uintptr_t)textureID, pMin, pMax, uvMin, uvMax, col, rounding);
    */

    private native void nAddImageRounded(long textureID, float pMinX, float pMinY, float pMaxX, float pMaxY, float uvMinX, float uvMinY, float uvMaxX, float uvMaxY, int col, float rounding, int imDrawFlags); /*MANUAL
        ImVec2 pMin = ImVec2(pMinX, pMinY);
        ImVec2 pMax = ImVec2(pMaxX, pMaxY);
        ImVec2 uvMin = ImVec2(uvMinX, uvMinY);
        ImVec2 uvMax = ImVec2(uvMaxX, uvMaxY);
        THIS->AddImageRounded((ImTextureID)(uintptr_t)textureID, pMin, pMax, uvMin, uvMax, col, rounding, imDrawFlags);
    */

    // Stateful path API, add points then finish with PathFillConvex() or PathStroke()
    // - Filled shapes must always use clockwise winding order. The anti-aliasing fringe depends on it. Counter-clockwise shapes will have "inward" anti-aliasing.

    public void pathClear() {
        nPathClear();
    }

    private native void nPathClear(); /*
        THIS->PathClear();
    */

    public void pathLineTo(final ImVec2 pos) {
        nPathLineTo(pos.x, pos.y);
    }

    public void pathLineTo(final float posX, final float posY) {
        nPathLineTo(posX, posY);
    }

    private native void nPathLineTo(float posX, float posY); /*MANUAL
        ImVec2 pos = ImVec2(posX, posY);
        THIS->PathLineTo(pos);
    */

    public void pathLineToMergeDuplicate(final ImVec2 pos) {
        nPathLineToMergeDuplicate(pos.x, pos.y);
    }

    public void pathLineToMergeDuplicate(final float posX, final float posY) {
        nPathLineToMergeDuplicate(posX, posY);
    }

    private native void nPathLineToMergeDuplicate(float posX, float posY); /*MANUAL
        ImVec2 pos = ImVec2(posX, posY);
        THIS->PathLineToMergeDuplicate(pos);
    */

    /**
     * Note: Anti-aliased filling requires points to be in clockwise order.
     */
    public void pathFillConvex(final int col) {
        nPathFillConvex(col);
    }

    private native void nPathFillConvex(int col); /*
        THIS->PathFillConvex(col);
    */

    public void pathStroke(final int col) {
        nPathStroke(col);
    }

    public void pathStroke(final int col, final int imDrawFlags) {
        nPathStroke(col, imDrawFlags);
    }

    public void pathStroke(final int col, final int imDrawFlags, final float thickness) {
        nPathStroke(col, imDrawFlags, thickness);
    }

    public void pathStroke(final int col, final float thickness) {
        nPathStroke(col, thickness);
    }

    private native void nPathStroke(int col); /*
        THIS->PathStroke(col);
    */

    private native void nPathStroke(int col, int imDrawFlags); /*
        THIS->PathStroke(col, imDrawFlags);
    */

    private native void nPathStroke(int col, int imDrawFlags, float thickness); /*
        THIS->PathStroke(col, imDrawFlags, thickness);
    */

    private native void nPathStroke(int col, float thickness); /*
        THIS->PathStroke(col, 0, thickness);
    */

    public void pathArcTo(final ImVec2 center, final float radius, final float aMin, final float aMax) {
        nPathArcTo(center.x, center.y, radius, aMin, aMax);
    }

    public void pathArcTo(final float centerX, final float centerY, final float radius, final float aMin, final float aMax) {
        nPathArcTo(centerX, centerY, radius, aMin, aMax);
    }

    public void pathArcTo(final ImVec2 center, final float radius, final float aMin, final float aMax, final int numSegments) {
        nPathArcTo(center.x, center.y, radius, aMin, aMax, numSegments);
    }

    public void pathArcTo(final float centerX, final float centerY, final float radius, final float aMin, final float aMax, final int numSegments) {
        nPathArcTo(centerX, centerY, radius, aMin, aMax, numSegments);
    }

    private native void nPathArcTo(float centerX, float centerY, float radius, float aMin, float aMax); /*MANUAL
        ImVec2 center = ImVec2(centerX, centerY);
        THIS->PathArcTo(center, radius, aMin, aMax);
    */

    private native void nPathArcTo(float centerX, float centerY, float radius, float aMin, float aMax, int numSegments); /*MANUAL
        ImVec2 center = ImVec2(centerX, centerY);
        THIS->PathArcTo(center, radius, aMin, aMax, numSegments);
    */

    /**
     * Use precomputed angles for a 12 steps circle
     */
    public void pathArcToFast(final ImVec2 center, final float radius, final int aMinOf12, final int aMaxOf12) {
        nPathArcToFast(center.x, center.y, radius, aMinOf12, aMaxOf12);
    }

    /**
     * Use precomputed angles for a 12 steps circle
     */
    public void pathArcToFast(final float centerX, final float centerY, final float radius, final int aMinOf12, final int aMaxOf12) {
        nPathArcToFast(centerX, centerY, radius, aMinOf12, aMaxOf12);
    }

    private native void nPathArcToFast(float centerX, float centerY, float radius, int aMinOf12, int aMaxOf12); /*MANUAL
        ImVec2 center = ImVec2(centerX, centerY);
        THIS->PathArcToFast(center, radius, aMinOf12, aMaxOf12);
    */

    /**
     * Cubic Bezier (4 control points)
     */
    public void pathBezierCubicCurveTo(final ImVec2 p2, final ImVec2 p3, final ImVec2 p4) {
        nPathBezierCubicCurveTo(p2.x, p2.y, p3.x, p3.y, p4.x, p4.y);
    }

    /**
     * Cubic Bezier (4 control points)
     */
    public void pathBezierCubicCurveTo(final float p2X, final float p2Y, final float p3X, final float p3Y, final float p4X, final float p4Y) {
        nPathBezierCubicCurveTo(p2X, p2Y, p3X, p3Y, p4X, p4Y);
    }

    /**
     * Cubic Bezier (4 control points)
     */
    public void pathBezierCubicCurveTo(final ImVec2 p2, final ImVec2 p3, final ImVec2 p4, final int numSegments) {
        nPathBezierCubicCurveTo(p2.x, p2.y, p3.x, p3.y, p4.x, p4.y, numSegments);
    }

    /**
     * Cubic Bezier (4 control points)
     */
    public void pathBezierCubicCurveTo(final float p2X, final float p2Y, final float p3X, final float p3Y, final float p4X, final float p4Y, final int numSegments) {
        nPathBezierCubicCurveTo(p2X, p2Y, p3X, p3Y, p4X, p4Y, numSegments);
    }

    private native void nPathBezierCubicCurveTo(float p2X, float p2Y, float p3X, float p3Y, float p4X, float p4Y); /*MANUAL
        ImVec2 p2 = ImVec2(p2X, p2Y);
        ImVec2 p3 = ImVec2(p3X, p3Y);
        ImVec2 p4 = ImVec2(p4X, p4Y);
        THIS->PathBezierCubicCurveTo(p2, p3, p4);
    */

    private native void nPathBezierCubicCurveTo(float p2X, float p2Y, float p3X, float p3Y, float p4X, float p4Y, int numSegments); /*MANUAL
        ImVec2 p2 = ImVec2(p2X, p2Y);
        ImVec2 p3 = ImVec2(p3X, p3Y);
        ImVec2 p4 = ImVec2(p4X, p4Y);
        THIS->PathBezierCubicCurveTo(p2, p3, p4, numSegments);
    */

    /**
     * Quadratic Bezier (3 control points)
     */
    public void pathBezierQuadraticCurveTo(final ImVec2 p2, final ImVec2 p3) {
        nPathBezierQuadraticCurveTo(p2.x, p2.y, p3.x, p3.y);
    }

    /**
     * Quadratic Bezier (3 control points)
     */
    public void pathBezierQuadraticCurveTo(final float p2X, final float p2Y, final float p3X, final float p3Y) {
        nPathBezierQuadraticCurveTo(p2X, p2Y, p3X, p3Y);
    }

    /**
     * Quadratic Bezier (3 control points)
     */
    public void pathBezierQuadraticCurveTo(final ImVec2 p2, final ImVec2 p3, final int numSegments) {
        nPathBezierQuadraticCurveTo(p2.x, p2.y, p3.x, p3.y, numSegments);
    }

    /**
     * Quadratic Bezier (3 control points)
     */
    public void pathBezierQuadraticCurveTo(final float p2X, final float p2Y, final float p3X, final float p3Y, final int numSegments) {
        nPathBezierQuadraticCurveTo(p2X, p2Y, p3X, p3Y, numSegments);
    }

    private native void nPathBezierQuadraticCurveTo(float p2X, float p2Y, float p3X, float p3Y); /*MANUAL
        ImVec2 p2 = ImVec2(p2X, p2Y);
        ImVec2 p3 = ImVec2(p3X, p3Y);
        THIS->PathBezierQuadraticCurveTo(p2, p3);
    */

    private native void nPathBezierQuadraticCurveTo(float p2X, float p2Y, float p3X, float p3Y, int numSegments); /*MANUAL
        ImVec2 p2 = ImVec2(p2X, p2Y);
        ImVec2 p3 = ImVec2(p3X, p3Y);
        THIS->PathBezierQuadraticCurveTo(p2, p3, numSegments);
    */

    public void pathRect(final ImVec2 rectMin, final ImVec2 rectMax) {
        nPathRect(rectMin.x, rectMin.y, rectMax.x, rectMax.y);
    }

    public void pathRect(final float rectMinX, final float rectMinY, final float rectMaxX, final float rectMaxY) {
        nPathRect(rectMinX, rectMinY, rectMaxX, rectMaxY);
    }

    public void pathRect(final ImVec2 rectMin, final ImVec2 rectMax, final float rounding) {
        nPathRect(rectMin.x, rectMin.y, rectMax.x, rectMax.y, rounding);
    }

    public void pathRect(final float rectMinX, final float rectMinY, final float rectMaxX, final float rectMaxY, final float rounding) {
        nPathRect(rectMinX, rectMinY, rectMaxX, rectMaxY, rounding);
    }

    public void pathRect(final ImVec2 rectMin, final ImVec2 rectMax, final float rounding, final int imDrawFlags) {
        nPathRect(rectMin.x, rectMin.y, rectMax.x, rectMax.y, rounding, imDrawFlags);
    }

    public void pathRect(final float rectMinX, final float rectMinY, final float rectMaxX, final float rectMaxY, final float rounding, final int imDrawFlags) {
        nPathRect(rectMinX, rectMinY, rectMaxX, rectMaxY, rounding, imDrawFlags);
    }

    public void pathRect(final ImVec2 rectMin, final ImVec2 rectMax, final int imDrawFlags) {
        nPathRect(rectMin.x, rectMin.y, rectMax.x, rectMax.y, imDrawFlags);
    }

    public void pathRect(final float rectMinX, final float rectMinY, final float rectMaxX, final float rectMaxY, final int imDrawFlags) {
        nPathRect(rectMinX, rectMinY, rectMaxX, rectMaxY, imDrawFlags);
    }

    private native void nPathRect(float rectMinX, float rectMinY, float rectMaxX, float rectMaxY); /*MANUAL
        ImVec2 rectMin = ImVec2(rectMinX, rectMinY);
        ImVec2 rectMax = ImVec2(rectMaxX, rectMaxY);
        THIS->PathRect(rectMin, rectMax);
    */

    private native void nPathRect(float rectMinX, float rectMinY, float rectMaxX, float rectMaxY, float rounding); /*MANUAL
        ImVec2 rectMin = ImVec2(rectMinX, rectMinY);
        ImVec2 rectMax = ImVec2(rectMaxX, rectMaxY);
        THIS->PathRect(rectMin, rectMax, rounding);
    */

    private native void nPathRect(float rectMinX, float rectMinY, float rectMaxX, float rectMaxY, float rounding, int imDrawFlags); /*MANUAL
        ImVec2 rectMin = ImVec2(rectMinX, rectMinY);
        ImVec2 rectMax = ImVec2(rectMaxX, rectMaxY);
        THIS->PathRect(rectMin, rectMax, rounding, imDrawFlags);
    */

    private native void nPathRect(float rectMinX, float rectMinY, float rectMaxX, float rectMaxY, int imDrawFlags); /*MANUAL
        ImVec2 rectMin = ImVec2(rectMinX, rectMinY);
        ImVec2 rectMax = ImVec2(rectMaxX, rectMaxY);
        THIS->PathRect(rectMin, rectMax, 0.0f, imDrawFlags);
    */

    // Advanced: Channels
    // - Use to split render into layers. By switching channels to can render out-of-order (e.g. submit FG primitives before BG primitives)
    // - Use to minimize draw calls (e.g. if going back-and-forth between multiple clipping rectangles, prefer to append into separate channels then merge at the end)
    //   Prefer using your own persistent instance of ImDrawListSplitter as you can stack them.
    //   Using the ImDrawList::ChannelsXXXX you cannot stack a split over another.

    public void channelsSplit(final int count) {
        nChannelsSplit(count);
    }

    private native void nChannelsSplit(int count); /*
        THIS->ChannelsSplit(count);
    */

    public void channelsMerge() {
        nChannelsMerge();
    }

    private native void nChannelsMerge(); /*
        THIS->ChannelsMerge();
    */

    public void channelsSetCurrent(final int n) {
        nChannelsSetCurrent(n);
    }

    private native void nChannelsSetCurrent(int n); /*
        THIS->ChannelsSetCurrent(n);
    */

    // Advanced: Primitives allocations
    // - We render triangles (three vertices)
    // - All primitives needs to be reserved via PrimReserve() beforehand.

    public void primReserve(final int idxCount, final int vtxCount) {
        nPrimReserve(idxCount, vtxCount);
    }

    private native void nPrimReserve(int idxCount, int vtxCount); /*
        THIS->PrimReserve(idxCount, vtxCount);
    */

    public void primUnreserve(final int idxCount, final int vtxCount) {
        nPrimUnreserve(idxCount, vtxCount);
    }

    private native void nPrimUnreserve(int idxCount, int vtxCount); /*
        THIS->PrimUnreserve(idxCount, vtxCount);
    */

    public void primRect(final ImVec2 a, final ImVec2 b, final int col) {
        nPrimRect(a.x, a.y, b.x, b.y, col);
    }

    public void primRect(final float aX, final float aY, final float bX, final float bY, final int col) {
        nPrimRect(aX, aY, bX, bY, col);
    }

    private native void nPrimRect(float aX, float aY, float bX, float bY, int col); /*MANUAL
        ImVec2 a = ImVec2(aX, aY);
        ImVec2 b = ImVec2(bX, bY);
        THIS->PrimRect(a, b, col);
    */

    public void primRectUV(final ImVec2 a, final ImVec2 b, final ImVec2 uvA, final ImVec2 uvB, final int col) {
        nPrimRectUV(a.x, a.y, b.x, b.y, uvA.x, uvA.y, uvB.x, uvB.y, col);
    }

    public void primRectUV(final float aX, final float aY, final float bX, final float bY, final float uvAX, final float uvAY, final float uvBX, final float uvBY, final int col) {
        nPrimRectUV(aX, aY, bX, bY, uvAX, uvAY, uvBX, uvBY, col);
    }

    private native void nPrimRectUV(float aX, float aY, float bX, float bY, float uvAX, float uvAY, float uvBX, float uvBY, int col); /*MANUAL
        ImVec2 a = ImVec2(aX, aY);
        ImVec2 b = ImVec2(bX, bY);
        ImVec2 uvA = ImVec2(uvAX, uvAY);
        ImVec2 uvB = ImVec2(uvBX, uvBY);
        THIS->PrimRectUV(a, b, uvA, uvB, col);
    */

    public void primQuadUV(final ImVec2 a, final ImVec2 b, final ImVec2 c, final ImVec2 d, final ImVec2 uvA, final ImVec2 uvB, final ImVec2 uvC, final ImVec2 uvD, final int col) {
        nPrimQuadUV(a.x, a.y, b.x, b.y, c.x, c.y, d.x, d.y, uvA.x, uvA.y, uvB.x, uvB.y, uvC.x, uvC.y, uvD.x, uvD.y, col);
    }

    public void primQuadUV(final float aX, final float aY, final float bX, final float bY, final float cX, final float cY, final float dX, final float dY, final float uvAX, final float uvAY, final float uvBX, final float uvBY, final float uvCX, final float uvCY, final float uvDX, final float uvDY, final int col) {
        nPrimQuadUV(aX, aY, bX, bY, cX, cY, dX, dY, uvAX, uvAY, uvBX, uvBY, uvCX, uvCY, uvDX, uvDY, col);
    }

    private native void nPrimQuadUV(float aX, float aY, float bX, float bY, float cX, float cY, float dX, float dY, float uvAX, float uvAY, float uvBX, float uvBY, float uvCX, float uvCY, float uvDX, float uvDY, int col); /*MANUAL
        ImVec2 a = ImVec2(aX, aY);
        ImVec2 b = ImVec2(bX, bY);
        ImVec2 c = ImVec2(cX, cY);
        ImVec2 d = ImVec2(dX, dY);
        ImVec2 uvA = ImVec2(uvAX, uvAY);
        ImVec2 uvB = ImVec2(uvBX, uvBY);
        ImVec2 uvC = ImVec2(uvCX, uvCY);
        ImVec2 uvD = ImVec2(uvDX, uvDY);
        THIS->PrimQuadUV(a, b, c, d, uvA, uvB, uvC, uvD, col);
    */

    public void primWriteVtx(final ImVec2 pos, final ImVec2 uv, final int col) {
        nPrimWriteVtx(pos.x, pos.y, uv.x, uv.y, col);
    }

    public void primWriteVtx(final float posX, final float posY, final float uvX, final float uvY, final int col) {
        nPrimWriteVtx(posX, posY, uvX, uvY, col);
    }

    private native void nPrimWriteVtx(float posX, float posY, float uvX, float uvY, int col); /*MANUAL
        ImVec2 pos = ImVec2(posX, posY);
        ImVec2 uv = ImVec2(uvX, uvY);
        THIS->PrimWriteVtx(pos, uv, col);
    */

    public void primWriteIdx(final int idx) {
        nPrimWriteIdx(idx);
    }

    private native void nPrimWriteIdx(int idx); /*
        THIS->PrimWriteIdx((ImDrawIdx)idx);
    */

    public void primVtx(final ImVec2 pos, final ImVec2 uv, final int col) {
        nPrimVtx(pos.x, pos.y, uv.x, uv.y, col);
    }

    public void primVtx(final float posX, final float posY, final float uvX, final float uvY, final int col) {
        nPrimVtx(posX, posY, uvX, uvY, col);
    }

    private native void nPrimVtx(float posX, float posY, float uvX, float uvY, int col); /*MANUAL
        ImVec2 pos = ImVec2(posX, posY);
        ImVec2 uv = ImVec2(uvX, uvY);
        THIS->PrimVtx(pos, uv, col);
    */

    /*JNI
        #undef THIS
     */
}
