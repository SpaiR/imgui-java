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

    private final int drawListType;

    ImDrawList(int drawListType) {
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

    // Primitives
    // - For rectangular primitives, "p_min" and "p_max" represent the upper-left and lower-right corners.

    public native void AddLine(float p1_x, float p1_y, float p2_x, float p2_y, long col); /*
        getDrawList(env->GetIntField(object, drawListTypeID))->AddLine(ImVec2(p1_x, p1_y), ImVec2(p2_x, p2_y), col);
    */

    public native void AddLine(float p1_x, float p1_y, float p2_x, float p2_y, long col, float thickness); /*
        getDrawList(env->GetIntField(object, drawListTypeID))->AddLine(ImVec2(p1_x, p1_y), ImVec2(p2_x, p2_y), col, thickness);
    */

    public native void AddRect(float p_min_x, float p_min_y, float p_max_x, float p_max_y, long col); /*
        getDrawList(env->GetIntField(object, drawListTypeID))->AddRect(ImVec2(p_min_x, p_min_y), ImVec2(p_max_x, p_max_y), col);
     */

    public native void AddRect(float p_min_x, float p_min_y, float p_max_x, float p_max_y, long col, float rounding); /*
        getDrawList(env->GetIntField(object, drawListTypeID))->AddRect(ImVec2(p_min_x, p_min_y), ImVec2(p_max_x, p_max_y), col, rounding);
     */

    public native void AddRect(float p_min_x, float p_min_y, float p_max_x, float p_max_y, long col, float rounding, int rounding_corners_flags); /*
        getDrawList(env->GetIntField(object, drawListTypeID))->AddRect(ImVec2(p_min_x, p_min_y), ImVec2(p_max_x, p_max_y), col, rounding, rounding_corners_flags);
     */

    public native void AddRect(float p_min_x, float p_min_y, float p_max_x, float p_max_y, long col, float rounding, int rounding_corners_flags, float thickness); /*
        getDrawList(env->GetIntField(object, drawListTypeID))->AddRect(ImVec2(p_min_x, p_min_y), ImVec2(p_max_x, p_max_y), col, rounding, rounding_corners_flags, thickness);
     */

    public native void AddRectFilled(float p_min_x, float p_min_y, float p_max_x, float p_max_y, long col); /*
        getDrawList(env->GetIntField(object, drawListTypeID))->AddRectFilled(ImVec2(p_min_x, p_min_y), ImVec2(p_max_x, p_max_y), col);
     */

    public native void AddRectFilled(float p_min_x, float p_min_y, float p_max_x, float p_max_y, long col, float rounding); /*
        getDrawList(env->GetIntField(object, drawListTypeID))->AddRectFilled(ImVec2(p_min_x, p_min_y), ImVec2(p_max_x, p_max_y), col, rounding);
     */

    public native void AddRectFilled(float p_min_x, float p_min_y, float p_max_x, float p_max_y, long col, float rounding, int rounding_corners_flags); /*
        getDrawList(env->GetIntField(object, drawListTypeID))->AddRectFilled(ImVec2(p_min_x, p_min_y), ImVec2(p_max_x, p_max_y), col, rounding, rounding_corners_flags);
     */

    public native void AddRectFilledMultiColor(float p_min_x, float p_min_y, float p_max_x, float p_max_y, long col_upr_left, long col_upr_right, long col_bot_right, long col_bot_left); /*
        getDrawList(env->GetIntField(object, drawListTypeID))->AddRectFilledMultiColor(ImVec2(p_min_x, p_min_y), ImVec2(p_max_x, p_max_y), col_upr_left, col_upr_right, col_bot_right, col_bot_left);
     */

    public native void AddQuad(float p1_x, float p1_y, float p2_x, float p2_y, float p3_x, float p3_y, float p4_x, float p4_y, long col); /*
        getDrawList(env->GetIntField(object, drawListTypeID))->AddQuad(ImVec2(p1_x, p1_y), ImVec2(p2_x, p2_y), ImVec2(p3_x, p3_y), ImVec2(p4_x, p4_y), col);
    */

    public native void AddQuad(float p1_x, float p1_y, float p2_x, float p2_y, float p3_x, float p3_y, float p4_x, float p4_y, long col, float thickness); /*
        getDrawList(env->GetIntField(object, drawListTypeID))->AddQuad(ImVec2(p1_x, p1_y), ImVec2(p2_x, p2_y), ImVec2(p3_x, p3_y), ImVec2(p4_x, p4_y), col, thickness);
    */

    public native void AddQuadFilled(float p1_x, float p1_y, float p2_x, float p2_y, float p3_x, float p3_y, float p4_x, float p4_y, long col); /*
        getDrawList(env->GetIntField(object, drawListTypeID))->AddQuadFilled(ImVec2(p1_x, p1_y), ImVec2(p2_x, p2_y), ImVec2(p3_x, p3_y), ImVec2(p4_x, p4_y), col);
    */

    public native void AddTriangle(float p1_x, float p1_y, float p2_x, float p2_y, float p3_x, float p3_y, long col); /*
        getDrawList(env->GetIntField(object, drawListTypeID))->AddTriangle(ImVec2(p1_x, p1_y), ImVec2(p2_x, p2_y), ImVec2(p3_x, p3_y), col);
    */

    public native void AddTriangle(float p1_x, float p1_y, float p2_x, float p2_y, float p3_x, float p3_y, long col, float thickness); /*
        getDrawList(env->GetIntField(object, drawListTypeID))->AddTriangle(ImVec2(p1_x, p1_y), ImVec2(p2_x, p2_y), ImVec2(p3_x, p3_y), col, thickness);
    */

    public native void AddTriangleFilled(float p1_x, float p1_y, float p2_x, float p2_y, float p3_x, float p3_y, long col); /*
        getDrawList(env->GetIntField(object, drawListTypeID))->AddTriangleFilled(ImVec2(p1_x, p1_y), ImVec2(p2_x, p2_y), ImVec2(p3_x, p3_y), col);
    */

    public native void AddCircle(float centre_x, float centre_y, float radius, float col); /*
        getDrawList(env->GetIntField(object, drawListTypeID))->AddCircle(ImVec2(centre_x, centre_y), radius, col);
    */

    public native void AddCircle(float centre_x, float centre_y, float radius, float col, int num_segments); /*
        getDrawList(env->GetIntField(object, drawListTypeID))->AddCircle(ImVec2(centre_x, centre_y), radius, col, num_segments);
    */

    public native void AddCircle(float centre_x, float centre_y, float radius, float col, int num_segments, float thickness); /*
        getDrawList(env->GetIntField(object, drawListTypeID))->AddCircle(ImVec2(centre_x, centre_y), radius, col, num_segments, thickness);
    */

    public native void AddCircleFilled(float centre_x, float centre_y, float radius, float col); /*
        getDrawList(env->GetIntField(object, drawListTypeID))->AddCircleFilled(ImVec2(centre_x, centre_y), radius, col);
    */

    public native void AddCircleFilled(float centre_x, float centre_y, float radius, float col, int num_segments); /*
        getDrawList(env->GetIntField(object, drawListTypeID))->AddCircleFilled(ImVec2(centre_x, centre_y), radius, col, num_segments);
    */

    public native void AddText(float pos_x, float pos_y, int col, String text_begin); /*
        getDrawList(env->GetIntField(object, drawListTypeID))->AddText(ImVec2(pos_x, pos_y), col, text_begin);
    */

    public native void AddText(float pos_x, float pos_y, int col, String text_begin, String text_end); /*
        getDrawList(env->GetIntField(object, drawListTypeID))->AddText(ImVec2(pos_x, pos_y), col, text_begin, text_end);
    */

    // TODO add text with font support

    public native void AddPolyline(ImVec2[] points, int num_points, long col, boolean closed, float thickness); /*
        int points_num = env->GetArrayLength(points);
        ImVec2 _points[points_num];
        for (int i = 0; i < points_num; i++) {
            jobject jImVec2 = env->GetObjectArrayElement(points, i);
            ImVec2 dst;
            Jni::ImVec2Cpy(env, jImVec2, &dst);
            _points[i] = dst;
        }
        getDrawList(env->GetIntField(object, drawListTypeID))->AddPolyline(_points, num_points, col, closed, thickness);
    */

    // Note: Anti-aliased filling requires points to be in clockwise order.
    public native void AddConvexPolyFilled(ImVec2[] points, int num_points, long col); /*
        int points_num = env->GetArrayLength(points);
        ImVec2 _points[points_num];
        for (int i = 0; i < points_num; i++) {
            jobject jImVec2 = env->GetObjectArrayElement(points, i);
            ImVec2 dst;
            Jni::ImVec2Cpy(env, jImVec2, &dst);
            _points[i] = dst;
        }
        getDrawList(env->GetIntField(object, drawListTypeID))->AddConvexPolyFilled(_points, num_points, col);
    */

    public native void AddBezierCurve(float pos0_x, float pos0_y, float cp0_x, float cp0_y, float cp1_x, float cp1_y, float pos1_x, float pos1_y, long col, float thickness); /*
        getDrawList(env->GetIntField(object, drawListTypeID))->AddBezierCurve(ImVec2(pos0_x, pos0_y), ImVec2(cp0_x, cp0_y), ImVec2(cp1_x, cp1_y), ImVec2(pos1_x, pos1_y), col, thickness);
    */

    public native void AddBezierCurve(float pos0_x, float pos0_y, float cp0_x, float cp0_y, float cp1_x, float cp1_y, float pos1_x, float pos1_y, long col, float thickness, int num_segments); /*
        getDrawList(env->GetIntField(object, drawListTypeID))->AddBezierCurve(ImVec2(pos0_x, pos0_y), ImVec2(cp0_x, cp0_y), ImVec2(cp1_x, cp1_y), ImVec2(pos1_x, pos1_y), col, thickness, num_segments);
    */

    // Image primitives
    // - Read FAQ to understand what ImTextureID is.
    // - "p_min" and "p_max" represent the upper-left and lower-right corners of the rectangle.
    // - "uv_min" and "uv_max" represent the normalized texture coordinates to use for those corners. Using (0,0)->(1,1) texture coordinates will generally display the entire texture.

    public native void AddImage(int textureID, float p_min_x, float p_min_y, float p_max_x, float p_max_y); /*
        ImDrawList* drawList = getDrawList(env->GetIntField(object, drawListTypeID));
        drawList->AddImage((ImTextureID)textureID, ImVec2(p_min_x, p_min_y), ImVec2(p_max_x, p_max_y));
    */

    public native void AddImage(int textureID, float p_min_x, float p_min_y, float p_max_x, float p_max_y, float uv_min_x, float uv_min_y); /*
        ImDrawList* drawList = getDrawList(env->GetIntField(object, drawListTypeID));
        drawList->AddImage((ImTextureID)textureID, ImVec2(p_min_x, p_min_y), ImVec2(p_max_x, p_max_y), ImVec2(uv_min_x, uv_min_y));
    */

    public native void AddImage(int textureID, float p_min_x, float p_min_y, float p_max_x, float p_max_y, float uv_min_x, float uv_min_y, float uv_max_x, float uv_max_y); /*
        ImDrawList* drawList = getDrawList(env->GetIntField(object, drawListTypeID));
        drawList->AddImage((ImTextureID)textureID, ImVec2(p_min_x, p_min_y), ImVec2(p_max_x, p_max_y), ImVec2(uv_min_x, uv_min_y), ImVec2(uv_max_x, uv_max_y));
    */

    public native void AddImage(int textureID, float p_min_x, float p_min_y, float p_max_x, float p_max_y, float uv_min_x, float uv_min_y, float uv_max_x, float uv_max_y, long col); /*
        ImDrawList* drawList = getDrawList(env->GetIntField(object, drawListTypeID));
        drawList->AddImage((ImTextureID)textureID, ImVec2(p_min_x, p_min_y), ImVec2(p_max_x, p_max_y), ImVec2(uv_min_x, uv_min_y), ImVec2(uv_max_x, uv_max_y), col);
    */

    public native void AddImageQuad(int textureID, float p1_x, float p1_y, float p2_x, float p2_y, float p3_x, float p3_y, float p4_x, float p4_y); /*
        ImDrawList* drawList = getDrawList(env->GetIntField(object, drawListTypeID));
        drawList->AddImageQuad((ImTextureID)textureID, ImVec2(p1_x, p1_y), ImVec2(p2_x, p2_y), ImVec2(p3_x, p3_y), ImVec2(p4_x, p4_y));
    */

    public native void AddImageQuad(int textureID, float p1_x, float p1_y, float p2_x, float p2_y, float p3_x, float p3_y, float p4_x, float p4_y, float uv1_x, float uv1_y); /*
        ImDrawList* drawList = getDrawList(env->GetIntField(object, drawListTypeID));
        drawList->AddImageQuad((ImTextureID)textureID, ImVec2(p1_x, p1_y), ImVec2(p2_x, p2_y), ImVec2(p3_x, p3_y), ImVec2(p4_x, p4_y), ImVec2(uv1_x, uv1_y));
    */

    public native void AddImageQuad(int textureID, float p1_x, float p1_y, float p2_x, float p2_y, float p3_x, float p3_y, float p4_x, float p4_y, float uv1_x, float uv1_y, float uv2_x, float uv2_y); /*
        ImDrawList* drawList = getDrawList(env->GetIntField(object, drawListTypeID));
        drawList->AddImageQuad((ImTextureID)textureID, ImVec2(p1_x, p1_y), ImVec2(p2_x, p2_y), ImVec2(p3_x, p3_y), ImVec2(p4_x, p4_y), ImVec2(uv1_x, uv1_y), ImVec2(uv2_x, uv2_y));
    */

    public native void AddImageQuad(int textureID, float p1_x, float p1_y, float p2_x, float p2_y, float p3_x, float p3_y, float p4_x, float p4_y, float uv1_x, float uv1_y, float uv2_x, float uv2_y, float uv3_x, float uv3_y); /*
        ImDrawList* drawList = getDrawList(env->GetIntField(object, drawListTypeID));
        drawList->AddImageQuad((ImTextureID)textureID, ImVec2(p1_x, p1_y), ImVec2(p2_x, p2_y), ImVec2(p3_x, p3_y), ImVec2(p4_x, p4_y), ImVec2(uv1_x, uv1_y), ImVec2(uv2_x, uv2_y), ImVec2(uv3_x, uv3_y));
    */

    public native void AddImageQuad(int textureID, float p1_x, float p1_y, float p2_x, float p2_y, float p3_x, float p3_y, float p4_x, float p4_y, float uv1_x, float uv1_y, float uv2_x, float uv2_y, float uv3_x, float uv3_y, float uv4_x, float uv4_y); /*
        ImDrawList* drawList = getDrawList(env->GetIntField(object, drawListTypeID));
        drawList->AddImageQuad((ImTextureID)textureID, ImVec2(p1_x, p1_y), ImVec2(p2_x, p2_y), ImVec2(p3_x, p3_y), ImVec2(p4_x, p4_y), ImVec2(uv1_x, uv1_y), ImVec2(uv2_x, uv2_y), ImVec2(uv3_x, uv3_y), ImVec2(uv4_x, uv4_y));
    */

    public native void AddImageQuad(int textureID, float p1_x, float p1_y, float p2_x, float p2_y, float p3_x, float p3_y, float p4_x, float p4_y, float uv1_x, float uv1_y, float uv2_x, float uv2_y, float uv3_x, float uv3_y, float uv4_x, float uv4_y, long col); /*
        ImDrawList* drawList = getDrawList(env->GetIntField(object, drawListTypeID));
        drawList->AddImageQuad((ImTextureID)textureID, ImVec2(p1_x, p1_y), ImVec2(p2_x, p2_y), ImVec2(p3_x, p3_y), ImVec2(p4_x, p4_y), ImVec2(uv1_x, uv1_y), ImVec2(uv2_x, uv2_y), ImVec2(uv3_x, uv3_y), ImVec2(uv4_x, uv4_y), col);
    */

    public native void AddImageRounded(int textureID, float p_min_x, float p_min_y, float p_max_x, float p_max_y, float uv_min_x, float uv_min_y, float uv_max_x, float uv_max_y, long col, float rounding); /*
        ImDrawList* drawList = getDrawList(env->GetIntField(object, drawListTypeID));
        drawList->AddImageRounded((ImTextureID)textureID, ImVec2(p_min_x, p_min_y), ImVec2(p_max_x, p_max_y), ImVec2(uv_min_x, uv_min_y), ImVec2(uv_max_x, uv_max_y), col, rounding);
    */

    public native void AddImageRounded(int textureID, float p_min_x, float p_min_y, float p_max_x, float p_max_y, float uv_min_x, float uv_min_y, float uv_max_x, float uv_max_y, long col, float rounding, int imDrawCornerFlags); /*
        ImDrawList* drawList = getDrawList(env->GetIntField(object, drawListTypeID));
        drawList->AddImageRounded((ImTextureID)textureID, ImVec2(p_min_x, p_min_y), ImVec2(p_max_x, p_max_y), ImVec2(uv_min_x, uv_min_y), ImVec2(uv_max_x, uv_max_y), col, rounding, imDrawCornerFlags);
    */

    // Stateful path API, add points then finish with PathFillConvex() or PathStroke()

    public native void PathClear(); /*
        getDrawList(env->GetIntField(object, drawListTypeID))->PathClear();
    */

    public native void PathLineTo(float pos_x, float pos_y); /*
        getDrawList(env->GetIntField(object, drawListTypeID))->PathLineTo(ImVec2(pos_x, pos_y));
    */

    public native void PathLineToMergeDuplicate(float pos_x, float pos_y); /*
        getDrawList(env->GetIntField(object, drawListTypeID))->PathLineToMergeDuplicate(ImVec2(pos_x, pos_y));
    */

    // Note: Anti-aliased filling requires points to be in clockwise order.
    public native void PathFillConvex(long col); /*
        getDrawList(env->GetIntField(object, drawListTypeID))->PathFillConvex(col);
    */

    public native void PathStroke(long col, boolean closed); /*
        getDrawList(env->GetIntField(object, drawListTypeID))->PathStroke(col, closed);
    */

    public native void PathStroke(long col, boolean closed, float thickness); /*
        getDrawList(env->GetIntField(object, drawListTypeID))->PathStroke(col, closed, thickness);
    */

    public native void PathArcTo(float center_x, float center_y, float radius, float a_min, float a_max); /*
        getDrawList(env->GetIntField(object, drawListTypeID))->PathArcTo(ImVec2(center_x, center_y), radius, a_min, a_max);
    */

    public native void PathArcTo(float center_x, float center_y, float radius, float a_min, float a_max, int num_segments); /*
        getDrawList(env->GetIntField(object, drawListTypeID))->PathArcTo(ImVec2(center_x, center_y), radius, a_min, a_max, num_segments);
    */

    // Use precomputed angles for a 12 steps circle
    public native void PathArcToFast(float center_x, float center_y, float radius, int a_min_of_12, int a_max_of_12); /*
        getDrawList(env->GetIntField(object, drawListTypeID))->PathArcToFast(ImVec2(center_x, center_y), radius, a_min_of_12, a_max_of_12);
    */

    public native void PathBezierCurveTo(float p1_x, float p1_y, float p2_x, float p2_y, float p3_x, float p3_y, int num_segments); /*
        getDrawList(env->GetIntField(object, drawListTypeID))->PathBezierCurveTo(ImVec2(p1_x, p1_y), ImVec2(p2_x, p2_y), ImVec2(p3_x, p3_y), num_segments);
    */

    public native void PathRect(float rect_min_x, float rect_min_y, float rect_max_x, float rect_max_y); /*
        getDrawList(env->GetIntField(object, drawListTypeID))->PathRect(ImVec2(rect_min_x, rect_min_y), ImVec2(rect_max_x, rect_max_y));
    */

    public native void PathRect(float rect_min_x, float rect_min_y, float rect_max_x, float rect_max_y, float rounding); /*
        getDrawList(env->GetIntField(object, drawListTypeID))->PathRect(ImVec2(rect_min_x, rect_min_y), ImVec2(rect_max_x, rect_max_y), rounding);
    */

    public native void PathRect(float rect_min_x, float rect_min_y, float rect_max_x, float rect_max_y, float rounding, int imDrawCornerFlags); /*
        getDrawList(env->GetIntField(object, drawListTypeID))->PathRect(ImVec2(rect_min_x, rect_min_y), ImVec2(rect_max_x, rect_max_y), rounding, imDrawCornerFlags);
    */

    // Advanced: Channels
    // - Use to split render into layers. By switching channels to can render out-of-order (e.g. submit foreground primitives before background primitives)
    // - Use to minimize draw calls (e.g. if going back-and-forth between multiple non-overlapping clipping rectangles, prefer to append into separate channels then merge at the end)

    public native void ChannelsSplit(int count); /*
        ImDrawList* drawList = getDrawList(env->GetIntField(object, drawListTypeID));
        drawList->ChannelsSplit(count);
    */

    public native void ChannelsMerge(); /*
        ImDrawList* drawList = getDrawList(env->GetIntField(object, drawListTypeID));
        drawList->ChannelsMerge();
    */

    public native void ChannelsSetCurrent(int n); /*
        ImDrawList* drawList = getDrawList(env->GetIntField(object, drawListTypeID));
        drawList->ChannelsSetCurrent(n);
    */
}
