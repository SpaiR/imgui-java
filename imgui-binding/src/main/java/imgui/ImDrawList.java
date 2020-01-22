package imgui;

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
public final class ImDrawList {
    public static final int TYPE_WINDOW = 0;
    public static final int TYPE_BACKGROUND = 1;
    public static final int TYPE_FOREGROUND = 2;

    @SuppressWarnings({"FieldCanBeLocal", "unused"})
    private final int drawListType;

    ImDrawList(final int drawListType) {
        this.drawListType = drawListType;
    }

    /*JNI
        #include <imgui.h>
        #include "jni_common.h"

        const static int DRAWLIST_TYPE_WINDOW     = 0;
        const static int DRAWLIST_TYPE_BACKGROUND = 1;
        const static int DRAWLIST_TYPE_FOREGROUND = 2;

        ImDrawList* getDrawList(int drawListType) {
            switch (drawListType) {
                case DRAWLIST_TYPE_WINDOW:
                    return ImGui::GetWindowDrawList();
                case DRAWLIST_TYPE_BACKGROUND:
                    return ImGui::GetBackgroundDrawList();
                case DRAWLIST_TYPE_FOREGROUND:
                    return ImGui::GetForegroundDrawList();
                default:
                    return NULL;
            }
        }

        jfieldID drawListTypeID;
     */

    static native void nInit(); /*
        jclass jImDrawListClass = env->FindClass("imgui/ImDrawList");
        drawListTypeID = env->GetFieldID(jImDrawListClass, "drawListType", "I");
    */

    // Flags, you may poke into these to adjust anti-aliasing settings per-primitive.
    public native int getImDrawListFlags(); /*
        return getDrawList(env->GetIntField(object, drawListTypeID))->Flags;
    */

    public native void setImDrawListFlags(int imDrawListFlags); /*
        getDrawList(env->GetIntField(object, drawListTypeID))->Flags = imDrawListFlags;
    */

    /**
     * Render-level scissoring.
     * This is passed down to your render function but not used for CPU-side coarse clipping.
     * Prefer using higher-level ImGui::PushClipRect() to affect logic (hit-testing and widget culling)
     */
    public native void pushClipRect(float clipRectMinX, float clipRectMinY, float clipRectMaxX, float clipRectMaxY); /*
        getDrawList(env->GetIntField(object, drawListTypeID))->PushClipRect(ImVec2(clipRectMinX, clipRectMinY), ImVec2(clipRectMaxX, clipRectMaxY));
    */

    /**
     * Render-level scissoring.
     * This is passed down to your render function but not used for CPU-side coarse clipping.
     * Prefer using higher-level ImGui::PushClipRect() to affect logic (hit-testing and widget culling)
     */
    public native void pushClipRect(float clipRectMinX, float clipRectMinY, float clipRectMaxX, float clipRectMaxY, boolean intersectWithCurrentClipRect); /*
        getDrawList(env->GetIntField(object, drawListTypeID))->PushClipRect(ImVec2(clipRectMinX, clipRectMinY), ImVec2(clipRectMaxX, clipRectMaxY), intersectWithCurrentClipRect);
    */

    public native void pushClipRectFullScreen(); /*
        getDrawList(env->GetIntField(object, drawListTypeID))->PushClipRectFullScreen();
    */

    public native void popClipRect(); /*
        getDrawList(env->GetIntField(object, drawListTypeID))->PopClipRect();
    */

    public native void pushTextureId(int textureId); /*
        getDrawList(env->GetIntField(object, drawListTypeID))->PushTextureID((ImTextureID)textureId);
    */

    public native void popTextureId(); /*
        getDrawList(env->GetIntField(object, drawListTypeID))->PopTextureID();
    */

    public native void getClipRectMin(ImVec2 dstImVec2); /*
        Jni::ImVec2Cpy(env, getDrawList(env->GetIntField(object, drawListTypeID))->GetClipRectMin(), dstImVec2);
    */

    public native void getClipRectMax(ImVec2 dstImVec2); /*
        Jni::ImVec2Cpy(env, getDrawList(env->GetIntField(object, drawListTypeID))->GetClipRectMax(), dstImVec2);
    */

    // Primitives
    // - For rectangular primitives, "pMin" and "pMax" represent the upper-left and lower-right corners.

    public native void addLine(float p1X, float p1Y, float p2X, float p2Y, int col); /*
        getDrawList(env->GetIntField(object, drawListTypeID))->AddLine(ImVec2(p1X, p1Y), ImVec2(p2X, p2Y), col);
    */

    public native void addLine(float p1X, float p1Y, float p2X, float p2Y, int col, float thickness); /*
        getDrawList(env->GetIntField(object, drawListTypeID))->AddLine(ImVec2(p1X, p1Y), ImVec2(p2X, p2Y), col, thickness);
    */

    public native void addRect(float pMinX, float pMinY, float pMaxX, float pMaxY, int col); /*
        getDrawList(env->GetIntField(object, drawListTypeID))->AddRect(ImVec2(pMinX, pMinY), ImVec2(pMaxX, pMaxY), col);
     */

    public native void addRect(float pMinX, float pMinY, float pMaxX, float pMaxY, int col, float rounding); /*
        getDrawList(env->GetIntField(object, drawListTypeID))->AddRect(ImVec2(pMinX, pMinY), ImVec2(pMaxX, pMaxY), col, rounding);
     */

    public native void addRect(float pMinX, float pMinY, float pMaxX, float pMaxY, int col, float rounding, int roundingCornersFlags); /*
        getDrawList(env->GetIntField(object, drawListTypeID))->AddRect(ImVec2(pMinX, pMinY), ImVec2(pMaxX, pMaxY), col, rounding, roundingCornersFlags);
     */

    public native void addRect(float pMinX, float pMinY, float pMaxX, float pMaxY, int col, float rounding, int roundingCornersFlags, float thickness); /*
        getDrawList(env->GetIntField(object, drawListTypeID))->AddRect(ImVec2(pMinX, pMinY), ImVec2(pMaxX, pMaxY), col, rounding, roundingCornersFlags, thickness);
     */

    public native void addRectFilled(float pMinX, float pMinY, float pMaxX, float pMaxY, int col); /*
        getDrawList(env->GetIntField(object, drawListTypeID))->AddRectFilled(ImVec2(pMinX, pMinY), ImVec2(pMaxX, pMaxY), col);
     */

    public native void addRectFilled(float pMinX, float pMinY, float pMaxX, float pMaxY, int col, float rounding); /*
        getDrawList(env->GetIntField(object, drawListTypeID))->AddRectFilled(ImVec2(pMinX, pMinY), ImVec2(pMaxX, pMaxY), col, rounding);
     */

    public native void addRectFilled(float pMinX, float pMinY, float pMaxX, float pMaxY, int col, float rounding, int roundingCornersFlags); /*
        getDrawList(env->GetIntField(object, drawListTypeID))->AddRectFilled(ImVec2(pMinX, pMinY), ImVec2(pMaxX, pMaxY), col, rounding, roundingCornersFlags);
     */

    public native void addRectFilledMultiColor(float pMinX, float pMinY, float pMaxX, float pMaxY, long colUprLeft, long colUprRight, long colBotRight, long colBotLeft); /*
        getDrawList(env->GetIntField(object, drawListTypeID))->AddRectFilledMultiColor(ImVec2(pMinX, pMinY), ImVec2(pMaxX, pMaxY), colUprLeft, colUprRight, colBotRight, colBotLeft);
     */

    public native void addQuad(float p1X, float p1Y, float p2X, float p2Y, float p3X, float p3Y, float p4X, float p4Y, int col); /*
        getDrawList(env->GetIntField(object, drawListTypeID))->AddQuad(ImVec2(p1X, p1Y), ImVec2(p2X, p2Y), ImVec2(p3X, p3Y), ImVec2(p4X, p4Y), col);
    */

    public native void addQuad(float p1X, float p1Y, float p2X, float p2Y, float p3X, float p3Y, float p4X, float p4Y, int col, float thickness); /*
        getDrawList(env->GetIntField(object, drawListTypeID))->AddQuad(ImVec2(p1X, p1Y), ImVec2(p2X, p2Y), ImVec2(p3X, p3Y), ImVec2(p4X, p4Y), col, thickness);
    */

    public native void addQuadFilled(float p1X, float p1Y, float p2X, float p2Y, float p3X, float p3Y, float p4X, float p4Y, int col); /*
        getDrawList(env->GetIntField(object, drawListTypeID))->AddQuadFilled(ImVec2(p1X, p1Y), ImVec2(p2X, p2Y), ImVec2(p3X, p3Y), ImVec2(p4X, p4Y), col);
    */

    public native void addTriangle(float p1X, float p1Y, float p2X, float p2Y, float p3X, float p3Y, int col); /*
        getDrawList(env->GetIntField(object, drawListTypeID))->AddTriangle(ImVec2(p1X, p1Y), ImVec2(p2X, p2Y), ImVec2(p3X, p3Y), col);
    */

    public native void addTriangle(float p1X, float p1Y, float p2X, float p2Y, float p3X, float p3Y, int col, float thickness); /*
        getDrawList(env->GetIntField(object, drawListTypeID))->AddTriangle(ImVec2(p1X, p1Y), ImVec2(p2X, p2Y), ImVec2(p3X, p3Y), col, thickness);
    */

    public native void addTriangleFilled(float p1X, float p1Y, float p2X, float p2Y, float p3X, float p3Y, int col); /*
        getDrawList(env->GetIntField(object, drawListTypeID))->AddTriangleFilled(ImVec2(p1X, p1Y), ImVec2(p2X, p2Y), ImVec2(p3X, p3Y), col);
    */

    public native void addCircle(float centreX, float centreY, float radius, int col); /*
        getDrawList(env->GetIntField(object, drawListTypeID))->AddCircle(ImVec2(centreX, centreY), radius, col);
    */

    public native void addCircle(float centreX, float centreY, float radius, int col, int numSegments); /*
        getDrawList(env->GetIntField(object, drawListTypeID))->AddCircle(ImVec2(centreX, centreY), radius, col, numSegments);
    */

    public native void addCircle(float centreX, float centreY, float radius, int col, int numSegments, float thickness); /*
        getDrawList(env->GetIntField(object, drawListTypeID))->AddCircle(ImVec2(centreX, centreY), radius, col, numSegments, thickness);
    */

    public native void addCircleFilled(float centreX, float centreY, float radius, int col); /*
        getDrawList(env->GetIntField(object, drawListTypeID))->AddCircleFilled(ImVec2(centreX, centreY), radius, col);
    */

    public native void addCircleFilled(float centreX, float centreY, float radius, int col, int numSegments); /*
        getDrawList(env->GetIntField(object, drawListTypeID))->AddCircleFilled(ImVec2(centreX, centreY), radius, col, numSegments);
    */

    public native void addText(float posX, float posY, int col, String textBegin); /*
        getDrawList(env->GetIntField(object, drawListTypeID))->AddText(ImVec2(posX, posY), col, textBegin);
    */

    public native void addText(float posX, float posY, int col, String textBegin, String textEnd); /*
        getDrawList(env->GetIntField(object, drawListTypeID))->AddText(ImVec2(posX, posY), col, textBegin, textEnd);
    */

    // TODO add text with font support

    public native void addPolyline(ImVec2[] points, int numPoints, int col, boolean closed, float thickness); /*
        int points_num = env->GetArrayLength(points);
        ImVec2 _points[points_num];
        for (int i = 0; i < points_num; i++) {
            jobject jImVec2 = env->GetObjectArrayElement(points, i);
            ImVec2 dst;
            Jni::ImVec2Cpy(env, jImVec2, &dst);
            _points[i] = dst;
        }
        getDrawList(env->GetIntField(object, drawListTypeID))->AddPolyline(_points, numPoints, col, closed, thickness);
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
        getDrawList(env->GetIntField(object, drawListTypeID))->AddConvexPolyFilled(_points, numPoints, col);
    */

    public native void addBezierCurve(float pos0X, float pos0Y, float cp0X, float cp0Y, float cp1X, float cp1Y, float pos1X, float pos1Y, int col, float thickness); /*
        getDrawList(env->GetIntField(object, drawListTypeID))->AddBezierCurve(ImVec2(pos0X, pos0Y), ImVec2(cp0X, cp0Y), ImVec2(cp1X, cp1Y), ImVec2(pos1X, pos1Y), col, thickness);
    */

    public native void addBezierCurve(float pos0X, float pos0Y, float cp0X, float cp0Y, float cp1X, float cp1Y, float pos1X, float pos1Y, int col, float thickness, int numSegments); /*
        getDrawList(env->GetIntField(object, drawListTypeID))->AddBezierCurve(ImVec2(pos0X, pos0Y), ImVec2(cp0X, cp0Y), ImVec2(cp1X, cp1Y), ImVec2(pos1X, pos1Y), col, thickness, numSegments);
    */

    // Image primitives
    // - Read FAQ to understand what ImTextureID is.
    // - "pMin" and "pMax" represent the upper-left and lower-right corners of the rectangle.
    // - "uvMin" and "uvMax" represent the normalized texture coordinates to use for those corners. Using (0,0)->(1,1) texture coordinates will generally display the entire texture.

    public native void addImage(int textureID, float pMinX, float pMinY, float pMaxX, float pMaxY); /*
        ImDrawList* drawList = getDrawList(env->GetIntField(object, drawListTypeID));
        drawList->AddImage((ImTextureID)textureID, ImVec2(pMinX, pMinY), ImVec2(pMaxX, pMaxY));
    */

    public native void addImage(int textureID, float pMinX, float pMinY, float pMaxX, float pMaxY, float uvMinX, float uvMinY); /*
        ImDrawList* drawList = getDrawList(env->GetIntField(object, drawListTypeID));
        drawList->AddImage((ImTextureID)textureID, ImVec2(pMinX, pMinY), ImVec2(pMaxX, pMaxY), ImVec2(uvMinX, uvMinY));
    */

    public native void addImage(int textureID, float pMinX, float pMinY, float pMaxX, float pMaxY, float uvMinX, float uvMinY, float uvMaxX, float uvMaxY); /*
        ImDrawList* drawList = getDrawList(env->GetIntField(object, drawListTypeID));
        drawList->AddImage((ImTextureID)textureID, ImVec2(pMinX, pMinY), ImVec2(pMaxX, pMaxY), ImVec2(uvMinX, uvMinY), ImVec2(uvMaxX, uvMaxY));
    */

    public native void addImage(int textureID, float pMinX, float pMinY, float pMaxX, float pMaxY, float uvMinX, float uvMinY, float uvMaxX, float uvMaxY, int col); /*
        ImDrawList* drawList = getDrawList(env->GetIntField(object, drawListTypeID));
        drawList->AddImage((ImTextureID)textureID, ImVec2(pMinX, pMinY), ImVec2(pMaxX, pMaxY), ImVec2(uvMinX, uvMinY), ImVec2(uvMaxX, uvMaxY), col);
    */

    public native void addImageQuad(int textureID, float p1X, float p1Y, float p2X, float p2Y, float p3X, float p3Y, float p4X, float p4Y); /*
        ImDrawList* drawList = getDrawList(env->GetIntField(object, drawListTypeID));
        drawList->AddImageQuad((ImTextureID)textureID, ImVec2(p1X, p1Y), ImVec2(p2X, p2Y), ImVec2(p3X, p3Y), ImVec2(p4X, p4Y));
    */

    public native void addImageQuad(int textureID, float p1X, float p1Y, float p2X, float p2Y, float p3X, float p3Y, float p4X, float p4Y, float uv1X, float uv1Y); /*
        ImDrawList* drawList = getDrawList(env->GetIntField(object, drawListTypeID));
        drawList->AddImageQuad((ImTextureID)textureID, ImVec2(p1X, p1Y), ImVec2(p2X, p2Y), ImVec2(p3X, p3Y), ImVec2(p4X, p4Y), ImVec2(uv1X, uv1Y));
    */

    public native void addImageQuad(int textureID, float p1X, float p1Y, float p2X, float p2Y, float p3X, float p3Y, float p4X, float p4Y, float uv1X, float uv1Y, float uv2X, float uv2Y); /*
        ImDrawList* drawList = getDrawList(env->GetIntField(object, drawListTypeID));
        drawList->AddImageQuad((ImTextureID)textureID, ImVec2(p1X, p1Y), ImVec2(p2X, p2Y), ImVec2(p3X, p3Y), ImVec2(p4X, p4Y), ImVec2(uv1X, uv1Y), ImVec2(uv2X, uv2Y));
    */

    public native void addImageQuad(int textureID, float p1X, float p1Y, float p2X, float p2Y, float p3X, float p3Y, float p4X, float p4Y, float uv1X, float uv1Y, float uv2X, float uv2Y, float uv3X, float uv3Y); /*
        ImDrawList* drawList = getDrawList(env->GetIntField(object, drawListTypeID));
        drawList->AddImageQuad((ImTextureID)textureID, ImVec2(p1X, p1Y), ImVec2(p2X, p2Y), ImVec2(p3X, p3Y), ImVec2(p4X, p4Y), ImVec2(uv1X, uv1Y), ImVec2(uv2X, uv2Y), ImVec2(uv3X, uv3Y));
    */

    public native void addImageQuad(int textureID, float p1X, float p1Y, float p2X, float p2Y, float p3X, float p3Y, float p4X, float p4Y, float uv1X, float uv1Y, float uv2X, float uv2Y, float uv3X, float uv3Y, float uv4X, float uv4Y); /*
        ImDrawList* drawList = getDrawList(env->GetIntField(object, drawListTypeID));
        drawList->AddImageQuad((ImTextureID)textureID, ImVec2(p1X, p1Y), ImVec2(p2X, p2Y), ImVec2(p3X, p3Y), ImVec2(p4X, p4Y), ImVec2(uv1X, uv1Y), ImVec2(uv2X, uv2Y), ImVec2(uv3X, uv3Y), ImVec2(uv4X, uv4Y));
    */

    public native void addImageQuad(int textureID, float p1X, float p1Y, float p2X, float p2Y, float p3X, float p3Y, float p4X, float p4Y, float uv1X, float uv1Y, float uv2X, float uv2Y, float uv3X, float uv3Y, float uv4X, float uv4Y, int col); /*
        ImDrawList* drawList = getDrawList(env->GetIntField(object, drawListTypeID));
        drawList->AddImageQuad((ImTextureID)textureID, ImVec2(p1X, p1Y), ImVec2(p2X, p2Y), ImVec2(p3X, p3Y), ImVec2(p4X, p4Y), ImVec2(uv1X, uv1Y), ImVec2(uv2X, uv2Y), ImVec2(uv3X, uv3Y), ImVec2(uv4X, uv4Y), col);
    */

    public native void addImageRounded(int textureID, float pMinX, float pMinY, float pMaxX, float pMaxY, float uvMinX, float uvMinY, float uvMaxX, float uvMaxY, int col, float rounding); /*
        ImDrawList* drawList = getDrawList(env->GetIntField(object, drawListTypeID));
        drawList->AddImageRounded((ImTextureID)textureID, ImVec2(pMinX, pMinY), ImVec2(pMaxX, pMaxY), ImVec2(uvMinX, uvMinY), ImVec2(uvMaxX, uvMaxY), col, rounding);
    */

    public native void addImageRounded(int textureID, float pMinX, float pMinY, float pMaxX, float pMaxY, float uvMinX, float uvMinY, float uvMaxX, float uvMaxY, int col, float rounding, int imDrawCornerFlags); /*
        ImDrawList* drawList = getDrawList(env->GetIntField(object, drawListTypeID));
        drawList->AddImageRounded((ImTextureID)textureID, ImVec2(pMinX, pMinY), ImVec2(pMaxX, pMaxY), ImVec2(uvMinX, uvMinY), ImVec2(uvMaxX, uvMaxY), col, rounding, imDrawCornerFlags);
    */

    // Stateful path API, add points then finish with PathFillConvex() or PathStroke()

    public native void pathClear(); /*
        getDrawList(env->GetIntField(object, drawListTypeID))->PathClear();
    */

    public native void pathLineTo(float posX, float posY); /*
        getDrawList(env->GetIntField(object, drawListTypeID))->PathLineTo(ImVec2(posX, posY));
    */

    public native void pathLineToMergeDuplicate(float posX, float posY); /*
        getDrawList(env->GetIntField(object, drawListTypeID))->PathLineToMergeDuplicate(ImVec2(posX, posY));
    */

    // Note: Anti-aliased filling requires points to be in clockwise order.
    public native void pathFillConvex(int col); /*
        getDrawList(env->GetIntField(object, drawListTypeID))->PathFillConvex(col);
    */

    public native void pathStroke(int col, boolean closed); /*
        getDrawList(env->GetIntField(object, drawListTypeID))->PathStroke(col, closed);
    */

    public native void pathStroke(int col, boolean closed, float thickness); /*
        getDrawList(env->GetIntField(object, drawListTypeID))->PathStroke(col, closed, thickness);
    */

    public native void pathArcTo(float centerX, float centerY, float radius, float aMin, float aMax); /*
        getDrawList(env->GetIntField(object, drawListTypeID))->PathArcTo(ImVec2(centerX, centerY), radius, aMin, aMax);
    */

    public native void pathArcTo(float centerX, float centerY, float radius, float aMin, float aMax, int numSegments); /*
        getDrawList(env->GetIntField(object, drawListTypeID))->PathArcTo(ImVec2(centerX, centerY), radius, aMin, aMax, numSegments);
    */

    // Use precomputed angles for a 12 steps circle
    public native void pathArcToFast(float centerX, float centerY, float radius, int aMinOf12, int aMaxOf12); /*
        getDrawList(env->GetIntField(object, drawListTypeID))->PathArcToFast(ImVec2(centerX, centerY), radius, aMinOf12, aMaxOf12);
    */

    public native void pathBezierCurveTo(float p1X, float p1Y, float p2X, float p2Y, float p3X, float p3Y, int numSegments); /*
        getDrawList(env->GetIntField(object, drawListTypeID))->PathBezierCurveTo(ImVec2(p1X, p1Y), ImVec2(p2X, p2Y), ImVec2(p3X, p3Y), numSegments);
    */

    public native void pathRect(float rectMinX, float rectMinY, float rectMaxX, float rectMaxY); /*
        getDrawList(env->GetIntField(object, drawListTypeID))->PathRect(ImVec2(rectMinX, rectMinY), ImVec2(rectMaxX, rectMaxY));
    */

    public native void pathRect(float rectMinX, float rectMinY, float rectMaxX, float rectMaxY, float rounding); /*
        getDrawList(env->GetIntField(object, drawListTypeID))->PathRect(ImVec2(rectMinX, rectMinY), ImVec2(rectMaxX, rectMaxY), rounding);
    */

    public native void pathRect(float rectMinX, float rectMinY, float rectMaxX, float rectMaxY, float rounding, int imDrawCornerFlags); /*
        getDrawList(env->GetIntField(object, drawListTypeID))->PathRect(ImVec2(rectMinX, rectMinY), ImVec2(rectMaxX, rectMaxY), rounding, imDrawCornerFlags);
    */

    // Advanced: Channels
    // - Use to split render into layers. By switching channels to can render out-of-order (e.g. submit foreground primitives before background primitives)
    // - Use to minimize draw calls (e.g. if going back-and-forth between multiple non-overlapping clipping rectangles, prefer to append into separate channels then merge at the end)

    public native void channelsSplit(int count); /*
        ImDrawList* drawList = getDrawList(env->GetIntField(object, drawListTypeID));
        drawList->ChannelsSplit(count);
    */

    public native void channelsMerge(); /*
        ImDrawList* drawList = getDrawList(env->GetIntField(object, drawListTypeID));
        drawList->ChannelsMerge();
    */

    public native void channelsSetCurrent(int n); /*
        ImDrawList* drawList = getDrawList(env->GetIntField(object, drawListTypeID));
        drawList->ChannelsSetCurrent(n);
    */
}
