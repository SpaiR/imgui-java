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
        #include <stdint.h>
        #include <imgui.h>
        #include "jni_common.h"
        #include "jni_binding_struct.h"

        #define IM_DRAW_LIST ((ImDrawList*)STRUCT_PTR)
     */

    /**
     * Flags, you may poke into these to adjust anti-aliasing settings per-primitive.
     */
    public native int getImDrawListFlags(); /*
        return IM_DRAW_LIST->Flags;
    */

    /**
     * Flags, you may poke into these to adjust anti-aliasing settings per-primitive.
     */
    public native void setImDrawListFlags(int imDrawListFlags); /*
        IM_DRAW_LIST->Flags = imDrawListFlags;
    */

    /**
     * Flags, you may poke into these to adjust anti-aliasing settings per-primitive.
     */
    public void addImDrawListFlags(final int flags) {
        setImDrawListFlags(getImDrawListFlags() | flags);
    }

    /**
     * Flags, you may poke into these to adjust anti-aliasing settings per-primitive.
     */
    public void removeImDrawListFlags(final int flags) {
        setImDrawListFlags(getImDrawListFlags() & ~(flags));
    }

    /**
     * Flags, you may poke into these to adjust anti-aliasing settings per-primitive.
     */
    public boolean hasImDrawListFlags(final int flags) {
        return (getImDrawListFlags() & flags) != 0;
    }

    /**
     * Render-level scissoring.
     * This is passed down to your render function but not used for CPU-side coarse clipping.
     * Prefer using higher-level ImGui::PushClipRect() to affect logic (hit-testing and widget culling)
     */
    public native void pushClipRect(float clipRectMinX, float clipRectMinY, float clipRectMaxX, float clipRectMaxY); /*
        IM_DRAW_LIST->PushClipRect(ImVec2(clipRectMinX, clipRectMinY), ImVec2(clipRectMaxX, clipRectMaxY));
    */

    /**
     * Render-level scissoring.
     * This is passed down to your render function but not used for CPU-side coarse clipping.
     * Prefer using higher-level ImGui::PushClipRect() to affect logic (hit-testing and widget culling)
     */
    public native void pushClipRect(float clipRectMinX, float clipRectMinY, float clipRectMaxX, float clipRectMaxY, boolean intersectWithCurrentClipRect); /*
        IM_DRAW_LIST->PushClipRect(ImVec2(clipRectMinX, clipRectMinY), ImVec2(clipRectMaxX, clipRectMaxY), intersectWithCurrentClipRect);
    */

    public native void pushClipRectFullScreen(); /*
        IM_DRAW_LIST->PushClipRectFullScreen();
    */

    public native void popClipRect(); /*
        IM_DRAW_LIST->PopClipRect();
    */

    public native void pushTextureId(int textureId); /*
        IM_DRAW_LIST->PushTextureID((ImTextureID)(intptr_t)textureId);
    */

    public native void popTextureId(); /*
        IM_DRAW_LIST->PopTextureID();
    */

    public native void getClipRectMin(ImVec2 dstImVec2); /*
        Jni::ImVec2Cpy(env, IM_DRAW_LIST->GetClipRectMin(), dstImVec2);
    */

    public native float getClipRectMinX(); /*
        return IM_DRAW_LIST->GetClipRectMin().x;
    */

    public native float getClipRectMinY(); /*
        return IM_DRAW_LIST->GetClipRectMin().y;
    */

    public native void getClipRectMax(ImVec2 dstImVec2); /*
        Jni::ImVec2Cpy(env, IM_DRAW_LIST->GetClipRectMax(), dstImVec2);
    */

    public native float getClipRectMaxX(); /*
        return IM_DRAW_LIST->GetClipRectMax().x;
    */

    public native float getClipRectMaxY(); /*
        return IM_DRAW_LIST->GetClipRectMax().y;
    */

    // Primitives
    // - For rectangular primitives, "pMin" and "pMax" represent the upper-left and lower-right corners.
    // - For circle primitives, use "num_segments == 0" to automatically calculate tessellation (preferred).
    //   In older versions (until Dear ImGui 1.77) the AddCircle functions defaulted to num_segments == 12.
    //   In future versions we will use textures to provide cheaper and higher-quality circles.
    //   Use AddNgon() and AddNgonFilled() functions if you need to guaranteed a specific number of sides.

    public native void addLine(float p1X, float p1Y, float p2X, float p2Y, int col); /*
        IM_DRAW_LIST->AddLine(ImVec2(p1X, p1Y), ImVec2(p2X, p2Y), col);
    */

    public native void addLine(float p1X, float p1Y, float p2X, float p2Y, int col, float thickness); /*
        IM_DRAW_LIST->AddLine(ImVec2(p1X, p1Y), ImVec2(p2X, p2Y), col, thickness);
    */

    public native void addRect(float pMinX, float pMinY, float pMaxX, float pMaxY, int col); /*
        IM_DRAW_LIST->AddRect(ImVec2(pMinX, pMinY), ImVec2(pMaxX, pMaxY), col);
     */

    public native void addRect(float pMinX, float pMinY, float pMaxX, float pMaxY, int col, float rounding); /*
        IM_DRAW_LIST->AddRect(ImVec2(pMinX, pMinY), ImVec2(pMaxX, pMaxY), col, rounding);
     */

    public native void addRect(float pMinX, float pMinY, float pMaxX, float pMaxY, int col, float rounding, int roundingCornersFlags); /*
        IM_DRAW_LIST->AddRect(ImVec2(pMinX, pMinY), ImVec2(pMaxX, pMaxY), col, rounding, roundingCornersFlags);
     */

    public native void addRect(float pMinX, float pMinY, float pMaxX, float pMaxY, int col, float rounding, int roundingCornersFlags, float thickness); /*
        IM_DRAW_LIST->AddRect(ImVec2(pMinX, pMinY), ImVec2(pMaxX, pMaxY), col, rounding, roundingCornersFlags, thickness);
     */

    public native void addRectFilled(float pMinX, float pMinY, float pMaxX, float pMaxY, int col); /*
        IM_DRAW_LIST->AddRectFilled(ImVec2(pMinX, pMinY), ImVec2(pMaxX, pMaxY), col);
     */

    public native void addRectFilled(float pMinX, float pMinY, float pMaxX, float pMaxY, int col, float rounding); /*
        IM_DRAW_LIST->AddRectFilled(ImVec2(pMinX, pMinY), ImVec2(pMaxX, pMaxY), col, rounding);
     */

    public native void addRectFilled(float pMinX, float pMinY, float pMaxX, float pMaxY, int col, float rounding, int roundingCornersFlags); /*
        IM_DRAW_LIST->AddRectFilled(ImVec2(pMinX, pMinY), ImVec2(pMaxX, pMaxY), col, rounding, roundingCornersFlags);
     */

    public native void addRectFilledMultiColor(float pMinX, float pMinY, float pMaxX, float pMaxY, long colUprLeft, long colUprRight, long colBotRight, long colBotLeft); /*
        IM_DRAW_LIST->AddRectFilledMultiColor(ImVec2(pMinX, pMinY), ImVec2(pMaxX, pMaxY), colUprLeft, colUprRight, colBotRight, colBotLeft);
     */

    public native void addQuad(float p1X, float p1Y, float p2X, float p2Y, float p3X, float p3Y, float p4X, float p4Y, int col); /*
        IM_DRAW_LIST->AddQuad(ImVec2(p1X, p1Y), ImVec2(p2X, p2Y), ImVec2(p3X, p3Y), ImVec2(p4X, p4Y), col);
    */

    public native void addQuad(float p1X, float p1Y, float p2X, float p2Y, float p3X, float p3Y, float p4X, float p4Y, int col, float thickness); /*
        IM_DRAW_LIST->AddQuad(ImVec2(p1X, p1Y), ImVec2(p2X, p2Y), ImVec2(p3X, p3Y), ImVec2(p4X, p4Y), col, thickness);
    */

    public native void addQuadFilled(float p1X, float p1Y, float p2X, float p2Y, float p3X, float p3Y, float p4X, float p4Y, int col); /*
        IM_DRAW_LIST->AddQuadFilled(ImVec2(p1X, p1Y), ImVec2(p2X, p2Y), ImVec2(p3X, p3Y), ImVec2(p4X, p4Y), col);
    */

    public native void addTriangle(float p1X, float p1Y, float p2X, float p2Y, float p3X, float p3Y, int col); /*
        IM_DRAW_LIST->AddTriangle(ImVec2(p1X, p1Y), ImVec2(p2X, p2Y), ImVec2(p3X, p3Y), col);
    */

    public native void addTriangle(float p1X, float p1Y, float p2X, float p2Y, float p3X, float p3Y, int col, float thickness); /*
        IM_DRAW_LIST->AddTriangle(ImVec2(p1X, p1Y), ImVec2(p2X, p2Y), ImVec2(p3X, p3Y), col, thickness);
    */

    public native void addTriangleFilled(float p1X, float p1Y, float p2X, float p2Y, float p3X, float p3Y, int col); /*
        IM_DRAW_LIST->AddTriangleFilled(ImVec2(p1X, p1Y), ImVec2(p2X, p2Y), ImVec2(p3X, p3Y), col);
    */

    public native void addCircle(float centreX, float centreY, float radius, int col); /*
        IM_DRAW_LIST->AddCircle(ImVec2(centreX, centreY), radius, col);
    */

    public native void addCircle(float centreX, float centreY, float radius, int col, int numSegments); /*
        IM_DRAW_LIST->AddCircle(ImVec2(centreX, centreY), radius, col, numSegments);
    */

    public native void addCircle(float centreX, float centreY, float radius, int col, int numSegments, float thickness); /*
        IM_DRAW_LIST->AddCircle(ImVec2(centreX, centreY), radius, col, numSegments, thickness);
    */

    public native void addCircleFilled(float centreX, float centreY, float radius, int col); /*
        IM_DRAW_LIST->AddCircleFilled(ImVec2(centreX, centreY), radius, col);
    */

    public native void addCircleFilled(float centreX, float centreY, float radius, int col, int numSegments); /*
        IM_DRAW_LIST->AddCircleFilled(ImVec2(centreX, centreY), radius, col, numSegments);
    */

    public native void addNgon(float centreX, float centreY, float radius, int col, int numSegments); /*
        IM_DRAW_LIST->AddNgon(ImVec2(centreX, centreY), radius, col, numSegments);
    */

    public native void addNgon(float centreX, float centreY, float radius, int col, int numSegments, float thickness); /*
        IM_DRAW_LIST->AddNgon(ImVec2(centreX, centreY), radius, col, numSegments, thickness);
    */

    public native void addNgonFilled(float centreX, float centreY, float radius, int col, int numSegments); /*
        IM_DRAW_LIST->AddNgonFilled(ImVec2(centreX, centreY), radius, col, numSegments);
    */

    public native void addText(float posX, float posY, int col, String text); /*
        IM_DRAW_LIST->AddText(ImVec2(posX, posY), col, text);
    */

    public void addText(final ImFont imFont, final float fontSize, final float posX, final float posY, final int col, final String text) {
        nAddText(imFont.ptr, fontSize, posX, posY, col, text);
    }

    private native void nAddText(long imFontPtr, float fontSize, float posX, float posY, int col, String text); /*
        IM_DRAW_LIST->AddText((ImFont*)imFontPtr, fontSize, ImVec2(posX, posY), col, text);
    */

    public void addText(final ImFont imFont, final float fontSize, final float posX, final float posY, final int col, final String text, final float wrapWidth) {
        nAddText(imFont.ptr, fontSize, posX, posY, col, text, wrapWidth);
    }

    private native void nAddText(long imFontPtr, float fontSize, float posX, float posY, int col, String text, float wrapWidth); /*
        IM_DRAW_LIST->AddText((ImFont*)imFontPtr, fontSize, ImVec2(posX, posY), col, text, NULL, wrapWidth);
    */

    public void addText(final ImFont imFont, final float fontSize, final float posX, final float posY, final int col, final String text, final float wrapWidth, final float cpuFineClipRectX, final float cpuFineClipRectY, final float cpuFineClipRectZ, final float cpuFineClipRectV) {
        nAddText(imFont.ptr, fontSize, posX, posY, col, text, wrapWidth, cpuFineClipRectX, cpuFineClipRectY, cpuFineClipRectZ, cpuFineClipRectV);
    }

    private native void nAddText(long imFontPtr, float fontSize, float posX, float posY, int col, String text, float wrapWidth, float cpuFineClipRectX, float cpuFineClipRectY, float cpuFineClipRectZ, float cpuFineClipRectV); /*
        ImVec4 cpuFineClipRect = ImVec4(cpuFineClipRectX, cpuFineClipRectY, cpuFineClipRectZ, cpuFineClipRectV);
        IM_DRAW_LIST->AddText((ImFont*)imFontPtr, fontSize, ImVec2(posX, posY), col, text, NULL, wrapWidth, &cpuFineClipRect);
    */

    public native void addPolyline(ImVec2[] points, int numPoints, int col, boolean closed, float thickness); /*
        int points_num = env->GetArrayLength(points);
        ImVec2 _points[points_num];
        for (int i = 0; i < points_num; i++) {
            jobject jImVec2 = env->GetObjectArrayElement(points, i);
            ImVec2 dst;
            Jni::ImVec2Cpy(env, jImVec2, &dst);
            _points[i] = dst;
        }
        IM_DRAW_LIST->AddPolyline(_points, numPoints, col, closed, thickness);
    */

    // Note: Anti-aliased filling requires points to be in clockwise order.
    public native void addConvexPolyFilled(ImVec2[] points, int numPoints, int col); /*
        int points_num = env->GetArrayLength(points);
        ImVec2 _points[points_num];
        for (int i = 0; i < points_num; i++) {
            jobject jImVec2 = env->GetObjectArrayElement(points, i);
            ImVec2 dst;
            Jni::ImVec2Cpy(env, jImVec2, &dst);
            _points[i] = dst;
        }
        IM_DRAW_LIST->AddConvexPolyFilled(_points, numPoints, col);
    */

    public native void addBezierCurve(float p1X, float p1Y, float p2X, float p2Y, float p3X, float p3Y, float p4X, float p4Y, int col, float thickness); /*
        IM_DRAW_LIST->AddBezierCurve(ImVec2(p1X, p1Y), ImVec2(p2X, p2Y), ImVec2(p3X, p3Y), ImVec2(p4X, p4Y), col, thickness);
    */

    public native void addBezierCurve(float p1X, float p1Y, float p2X, float p2Y, float p3X, float p3Y, float p4X, float p4Y, int col, float thickness, int numSegments); /*
        IM_DRAW_LIST->AddBezierCurve(ImVec2(p1X, p1Y), ImVec2(p2X, p2Y), ImVec2(p3X, p3Y), ImVec2(p4X, p4Y), col, thickness, numSegments);
    */

    // Image primitives
    // - Read FAQ to understand what ImTextureID is.
    // - "pMin" and "pMax" represent the upper-left and lower-right corners of the rectangle.
    // - "uvMin" and "uvMax" represent the normalized texture coordinates to use for those corners. Using (0,0)->(1,1) texture coordinates will generally display the entire texture.

    public native void addImage(int textureID, float pMinX, float pMinY, float pMaxX, float pMaxY); /*
        IM_DRAW_LIST->AddImage((ImTextureID)(intptr_t)textureID, ImVec2(pMinX, pMinY), ImVec2(pMaxX, pMaxY));
    */

    public native void addImage(int textureID, float pMinX, float pMinY, float pMaxX, float pMaxY, float uvMinX, float uvMinY); /*
        IM_DRAW_LIST->AddImage((ImTextureID)(intptr_t)textureID, ImVec2(pMinX, pMinY), ImVec2(pMaxX, pMaxY), ImVec2(uvMinX, uvMinY));
    */

    public native void addImage(int textureID, float pMinX, float pMinY, float pMaxX, float pMaxY, float uvMinX, float uvMinY, float uvMaxX, float uvMaxY); /*
        IM_DRAW_LIST->AddImage((ImTextureID)(intptr_t)textureID, ImVec2(pMinX, pMinY), ImVec2(pMaxX, pMaxY), ImVec2(uvMinX, uvMinY), ImVec2(uvMaxX, uvMaxY));
    */

    public native void addImage(int textureID, float pMinX, float pMinY, float pMaxX, float pMaxY, float uvMinX, float uvMinY, float uvMaxX, float uvMaxY, int col); /*
        IM_DRAW_LIST->AddImage((ImTextureID)(intptr_t)textureID, ImVec2(pMinX, pMinY), ImVec2(pMaxX, pMaxY), ImVec2(uvMinX, uvMinY), ImVec2(uvMaxX, uvMaxY), col);
    */

    public native void addImageQuad(int textureID, float p1X, float p1Y, float p2X, float p2Y, float p3X, float p3Y, float p4X, float p4Y); /*
        IM_DRAW_LIST->AddImageQuad((ImTextureID)(intptr_t)textureID, ImVec2(p1X, p1Y), ImVec2(p2X, p2Y), ImVec2(p3X, p3Y), ImVec2(p4X, p4Y));
    */

    public native void addImageQuad(int textureID, float p1X, float p1Y, float p2X, float p2Y, float p3X, float p3Y, float p4X, float p4Y, float uv1X, float uv1Y); /*
        IM_DRAW_LIST->AddImageQuad((ImTextureID)(intptr_t)textureID, ImVec2(p1X, p1Y), ImVec2(p2X, p2Y), ImVec2(p3X, p3Y), ImVec2(p4X, p4Y), ImVec2(uv1X, uv1Y));
    */

    public native void addImageQuad(int textureID, float p1X, float p1Y, float p2X, float p2Y, float p3X, float p3Y, float p4X, float p4Y, float uv1X, float uv1Y, float uv2X, float uv2Y); /*
        IM_DRAW_LIST->AddImageQuad((ImTextureID)(intptr_t)textureID, ImVec2(p1X, p1Y), ImVec2(p2X, p2Y), ImVec2(p3X, p3Y), ImVec2(p4X, p4Y), ImVec2(uv1X, uv1Y), ImVec2(uv2X, uv2Y));
    */

    public native void addImageQuad(int textureID, float p1X, float p1Y, float p2X, float p2Y, float p3X, float p3Y, float p4X, float p4Y, float uv1X, float uv1Y, float uv2X, float uv2Y, float uv3X, float uv3Y); /*
        IM_DRAW_LIST->AddImageQuad((ImTextureID)(intptr_t)textureID, ImVec2(p1X, p1Y), ImVec2(p2X, p2Y), ImVec2(p3X, p3Y), ImVec2(p4X, p4Y), ImVec2(uv1X, uv1Y), ImVec2(uv2X, uv2Y), ImVec2(uv3X, uv3Y));
    */

    public native void addImageQuad(int textureID, float p1X, float p1Y, float p2X, float p2Y, float p3X, float p3Y, float p4X, float p4Y, float uv1X, float uv1Y, float uv2X, float uv2Y, float uv3X, float uv3Y, float uv4X, float uv4Y); /*
        IM_DRAW_LIST->AddImageQuad((ImTextureID)(intptr_t)textureID, ImVec2(p1X, p1Y), ImVec2(p2X, p2Y), ImVec2(p3X, p3Y), ImVec2(p4X, p4Y), ImVec2(uv1X, uv1Y), ImVec2(uv2X, uv2Y), ImVec2(uv3X, uv3Y), ImVec2(uv4X, uv4Y));
    */

    public native void addImageQuad(int textureID, float p1X, float p1Y, float p2X, float p2Y, float p3X, float p3Y, float p4X, float p4Y, float uv1X, float uv1Y, float uv2X, float uv2Y, float uv3X, float uv3Y, float uv4X, float uv4Y, int col); /*
        IM_DRAW_LIST->AddImageQuad((ImTextureID)(intptr_t)textureID, ImVec2(p1X, p1Y), ImVec2(p2X, p2Y), ImVec2(p3X, p3Y), ImVec2(p4X, p4Y), ImVec2(uv1X, uv1Y), ImVec2(uv2X, uv2Y), ImVec2(uv3X, uv3Y), ImVec2(uv4X, uv4Y), col);
    */

    public native void addImageRounded(int textureID, float pMinX, float pMinY, float pMaxX, float pMaxY, float uvMinX, float uvMinY, float uvMaxX, float uvMaxY, int col, float rounding); /*
        IM_DRAW_LIST->AddImageRounded((ImTextureID)(intptr_t)textureID, ImVec2(pMinX, pMinY), ImVec2(pMaxX, pMaxY), ImVec2(uvMinX, uvMinY), ImVec2(uvMaxX, uvMaxY), col, rounding);
    */

    public native void addImageRounded(int textureID, float pMinX, float pMinY, float pMaxX, float pMaxY, float uvMinX, float uvMinY, float uvMaxX, float uvMaxY, int col, float rounding, int imDrawCornerFlags); /*
        IM_DRAW_LIST->AddImageRounded((ImTextureID)(intptr_t)textureID, ImVec2(pMinX, pMinY), ImVec2(pMaxX, pMaxY), ImVec2(uvMinX, uvMinY), ImVec2(uvMaxX, uvMaxY), col, rounding, imDrawCornerFlags);
    */

    // Stateful path API, add points then finish with PathFillConvex() or PathStroke()

    public native void pathClear(); /*
        IM_DRAW_LIST->PathClear();
    */

    public native void pathLineTo(float posX, float posY); /*
        IM_DRAW_LIST->PathLineTo(ImVec2(posX, posY));
    */

    public native void pathLineToMergeDuplicate(float posX, float posY); /*
        IM_DRAW_LIST->PathLineToMergeDuplicate(ImVec2(posX, posY));
    */

    // Note: Anti-aliased filling requires points to be in clockwise order.
    public native void pathFillConvex(int col); /*
        IM_DRAW_LIST->PathFillConvex(col);
    */

    public native void pathStroke(int col, boolean closed); /*
        IM_DRAW_LIST->PathStroke(col, closed);
    */

    public native void pathStroke(int col, boolean closed, float thickness); /*
        IM_DRAW_LIST->PathStroke(col, closed, thickness);
    */

    public native void pathArcTo(float centerX, float centerY, float radius, float aMin, float aMax); /*
        IM_DRAW_LIST->PathArcTo(ImVec2(centerX, centerY), radius, aMin, aMax);
    */

    public native void pathArcTo(float centerX, float centerY, float radius, float aMin, float aMax, int numSegments); /*
        IM_DRAW_LIST->PathArcTo(ImVec2(centerX, centerY), radius, aMin, aMax, numSegments);
    */

    // Use precomputed angles for a 12 steps circle
    public native void pathArcToFast(float centerX, float centerY, float radius, int aMinOf12, int aMaxOf12); /*
        IM_DRAW_LIST->PathArcToFast(ImVec2(centerX, centerY), radius, aMinOf12, aMaxOf12);
    */

    public native void pathBezierCurveTo(float p2X, float p2Y, float p3X, float p3Y, float p4X, float p4Y, int numSegments); /*
        IM_DRAW_LIST->PathBezierCurveTo(ImVec2(p2X, p2Y), ImVec2(p3X, p3Y), ImVec2(p4X, p4Y), numSegments);
    */

    public native void pathRect(float rectMinX, float rectMinY, float rectMaxX, float rectMaxY); /*
        IM_DRAW_LIST->PathRect(ImVec2(rectMinX, rectMinY), ImVec2(rectMaxX, rectMaxY));
    */

    public native void pathRect(float rectMinX, float rectMinY, float rectMaxX, float rectMaxY, float rounding); /*
        IM_DRAW_LIST->PathRect(ImVec2(rectMinX, rectMinY), ImVec2(rectMaxX, rectMaxY), rounding);
    */

    public native void pathRect(float rectMinX, float rectMinY, float rectMaxX, float rectMaxY, float rounding, int imDrawCornerFlags); /*
        IM_DRAW_LIST->PathRect(ImVec2(rectMinX, rectMinY), ImVec2(rectMaxX, rectMaxY), rounding, imDrawCornerFlags);
    */

    // Advanced: Channels
    // - Use to split render into layers. By switching channels to can render out-of-order (e.g. submit FG primitives before BG primitives)
    // - Use to minimize draw calls (e.g. if going back-and-forth between multiple clipping rectangles, prefer to append into separate channels then merge at the end)
    //   Prefer using your own persistent instance of ImDrawListSplitter as you can stack them.
    //   Using the ImDrawList::ChannelsXXXX you cannot stack a split over another.

    public native void channelsSplit(int count); /*
        IM_DRAW_LIST->ChannelsSplit(count);
    */

    public native void channelsMerge(); /*
        IM_DRAW_LIST->ChannelsMerge();
    */

    public native void channelsSetCurrent(int n); /*
        IM_DRAW_LIST->ChannelsSetCurrent(n);
    */
}
