package imgui;

import imgui.binding.ImGuiStruct;
import imgui.binding.annotation.ArgValue;
import imgui.binding.annotation.BindingField;
import imgui.binding.annotation.BindingMethod;
import imgui.binding.annotation.BindingSource;
import imgui.binding.annotation.OptArg;

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
@BindingSource
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
    @BindingField(isFlag = true)
    public int Flags;

    /**
     * Render-level scissoring.
     * This is passed down to your render function but not used for CPU-side coarse clipping.
     * Prefer using higher-level ImGui::PushClipRect() to affect logic (hit-testing and widget culling)
     */
    @BindingMethod
    public native void PushClipRect(ImVec2 clipRectMin, ImVec2 clipRectMax, @OptArg boolean intersectWithCurrentClipRect);

    @BindingMethod
    public native void PushClipRectFullScreen();

    @BindingMethod
    public native void PopClipRect();

    @BindingMethod
    public native void PushTextureID(@ArgValue(callPrefix = "(ImTextureID)(uintptr_t)") long textureId);

    @BindingMethod
    public native void PopTextureID();

    @BindingMethod
    public native ImVec2 GetClipRectMin();

    @BindingMethod
    public native ImVec2 GetClipRectMax();

    // Primitives
    // - Filled shapes must always use clockwise winding order. The anti-aliasing fringe depends on it. Counter-clockwise shapes will have "inward" anti-aliasing.
    // - For rectangular primitives, "p_min" and "p_max" represent the upper-left and lower-right corners.
    // - For circle primitives, use "num_segments == 0" to automatically calculate tessellation (preferred).
    //   In older versions (until Dear ImGui 1.77) the AddCircle functions defaulted to num_segments == 12.
    //   In future versions we will use textures to provide cheaper and higher-quality circles.
    //   Use AddNgon() and AddNgonFilled() functions if you need to guaranteed a specific number of sides.

    @BindingMethod
    public native void AddLine(ImVec2 p1, ImVec2 p2, int col, @OptArg float thickness);

    @BindingMethod
    public native void AddRect(ImVec2 pMin, ImVec2 pMax, int col, @OptArg(callValue = "0.0f") float rounding, @OptArg(callValue = "0") int flags, @OptArg float thickness);

    @BindingMethod
    public native void AddRectFilled(ImVec2 pMin, ImVec2 pMax, int col, @OptArg(callValue = "0.0f") float rounding, @OptArg int flags);

    @BindingMethod
    public native void AddRectFilledMultiColor(ImVec2 pMin, ImVec2 pMax, int colUprLeft, int colUprRight, int colBotRight, int colBotLeft);

    @BindingMethod
    public native void AddQuad(ImVec2 p1, ImVec2 p2, ImVec2 p3, ImVec2 p4, int col, @OptArg float thickness);

    @BindingMethod
    public native void AddQuadFilled(ImVec2 p1, ImVec2 p2, ImVec2 p3, ImVec2 p4, int col);

    @BindingMethod
    public native void AddTriangle(ImVec2 p1, ImVec2 p2, ImVec2 p3, int col, @OptArg float thickness);

    @BindingMethod
    public native void AddTriangleFilled(ImVec2 p1, ImVec2 p2, ImVec2 p3, int col);

    @BindingMethod
    public native void AddCircle(ImVec2 center, float radius, int col, @OptArg(callValue = "0") int numSegments, @OptArg float thickness);

    @BindingMethod
    public native void AddCircleFilled(ImVec2 center, float radius, int col, @OptArg int numSegments);

    @BindingMethod
    public native void AddNgon(ImVec2 center, float radius, int col, int num_segments, @OptArg float thickness);

    @BindingMethod
    public native void AddNgonFilled(ImVec2 center, float radius, int col, int num_segments);

    @BindingMethod
    public native void AddText(ImVec2 pos, int col, String textBegin, @OptArg String textEnd);

    @BindingMethod
    public native void AddText(ImFont font, int fontSize, ImVec2 pos, int col, String textBegin, @OptArg(callValue = "NULL") String textEnd, @OptArg(callValue = "0.0f") float wrapWidth, @OptArg @ArgValue(callPrefix = "&") ImVec4 cpuFineClipRect);

    @BindingMethod
    public native void AddPolyline(ImVec2[] points, int numPoint, int col, int imDrawFlags, float thickness);

    @BindingMethod
    public native void AddConvexPolyFilled(ImVec2[] points, int numPoints, int col);

    /**
     * Cubic Bezier (4 control points)
     */
    @BindingMethod
    public native void AddBezierCubic(ImVec2 p1, ImVec2 p2, ImVec2 p3, ImVec2 p4, int col, float thickness, @OptArg int numSegments);

    /**
     * Quadratic Bezier (3 control points)
     */
    @BindingMethod
    public native void AddBezierQuadratic(ImVec2 p1, ImVec2 p2, ImVec2 p3, int col, float thickness, @OptArg int numSegments);

    // Image primitives
    // - Read FAQ to understand what ImTextureID is.
    // - "pMin" and "pMax" represent the upper-left and lower-right corners of the rectangle.
    // - "uvMin" and "uvMax" represent the normalized texture coordinates to use for those corners. Using (0,0)->(1,1) texture coordinates will generally display the entire texture.

    @BindingMethod
    public native void AddImage(@ArgValue(callPrefix = "(ImTextureID)(uintptr_t)") long textureID, ImVec2 pMin, ImVec2 pMax, @OptArg ImVec2 uvMin, @OptArg ImVec2 uvMax, @OptArg int col);

    @BindingMethod
    public native void AddImageQuad(@ArgValue(callPrefix = "(ImTextureID)(uintptr_t)") long textureID, ImVec2 p1, ImVec2 p2, ImVec2 p3, ImVec2 p4, @OptArg ImVec2 uv1, @OptArg ImVec2 uv2, @OptArg ImVec2 uv3, @OptArg ImVec2 uv4, @OptArg int col);

    @BindingMethod
    public native void AddImageRounded(@ArgValue(callPrefix = "(ImTextureID)(uintptr_t)") long textureID, ImVec2 pMin, ImVec2 pMax, ImVec2 uvMin, ImVec2 uvMax, int col, float rounding, @OptArg int imDrawFlags);

    // Stateful path API, add points then finish with PathFillConvex() or PathStroke()
    // - Filled shapes must always use clockwise winding order. The anti-aliasing fringe depends on it. Counter-clockwise shapes will have "inward" anti-aliasing.

    @BindingMethod
    public native void PathClear();

    @BindingMethod
    public native void PathLineTo(ImVec2 pos);

    @BindingMethod
    public native void PathLineToMergeDuplicate(ImVec2 pos);

    /**
     * Note: Anti-aliased filling requires points to be in clockwise order.
     */
    @BindingMethod
    public native void PathFillConvex(int col);

    @BindingMethod
    public native void PathStroke(int col, @OptArg(callValue = "0") int imDrawFlags, @OptArg float thickness);

    @BindingMethod
    public native void PathArcTo(ImVec2 center, float radius, float aMin, float aMax, @OptArg int numSegments);

    /**
     * Use precomputed angles for a 12 steps circle
     */
    @BindingMethod
    public native void PathArcToFast(ImVec2 center, float radius, int aMinOf12, int aMaxOf12);

    /**
     * Cubic Bezier (4 control points)
     */
    @BindingMethod
    public native void PathBezierCubicCurveTo(ImVec2 p2, ImVec2 p3, ImVec2 p4, @OptArg int numSegments);

    /**
     * Quadratic Bezier (3 control points)
     */
    @BindingMethod
    public native void PathBezierQuadraticCurveTo(ImVec2 p2, ImVec2 p3, @OptArg int numSegments);

    @BindingMethod
    public native void PathRect(ImVec2 rectMin, ImVec2 rectMax, @OptArg(callValue = "0.0f") float rounding, @OptArg int imDrawFlags);

    // Advanced: Channels
    // - Use to split render into layers. By switching channels to can render out-of-order (e.g. submit FG primitives before BG primitives)
    // - Use to minimize draw calls (e.g. if going back-and-forth between multiple clipping rectangles, prefer to append into separate channels then merge at the end)
    //   Prefer using your own persistent instance of ImDrawListSplitter as you can stack them.
    //   Using the ImDrawList::ChannelsXXXX you cannot stack a split over another.

    @BindingMethod
    public native void ChannelsSplit(int count);

    @BindingMethod
    public native void ChannelsMerge();

    @BindingMethod
    public native void ChannelsSetCurrent(int n);

    // Advanced: Primitives allocations
    // - We render triangles (three vertices)
    // - All primitives needs to be reserved via PrimReserve() beforehand.

    @BindingMethod
    public native void PrimReserve(int idxCount, int vtxCount);

    @BindingMethod
    public native void PrimUnreserve(int idxCount, int vtxCount);

    @BindingMethod
    public native void PrimRect(ImVec2 a, ImVec2 b, int col);

    @BindingMethod
    public native void PrimRectUV(ImVec2 a, ImVec2 b, ImVec2 uvA, ImVec2 uvB, int col);

    @BindingMethod
    public native void PrimQuadUV(ImVec2 a, ImVec2 b, ImVec2 c, ImVec2 d, ImVec2 uvA, ImVec2 uvB, ImVec2 uvC, ImVec2 uvD, int col);

    @BindingMethod
    public native void PrimWriteVtx(ImVec2 pos, ImVec2 uv, int col);

    @BindingMethod
    public native void PrimWriteIdx(@ArgValue(callPrefix = "(ImDrawIdx)") int idx);

    @BindingMethod
    public native void PrimVtx(ImVec2 pos, ImVec2 uv, int col);

    /*JNI
        #undef THIS
     */
}
