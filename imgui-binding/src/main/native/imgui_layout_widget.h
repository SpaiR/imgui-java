#pragma once

#include "imgui.h"
#include "imgui_internal.h"

namespace ImGuiEx
{
inline void drawBoundingBox(float x1, float y1, float x2, float y2, int r, int g, int b, bool clipping = false)
{
    ImDrawList *drawList = clipping ? ImGui::GetWindowDrawList() : ImGui::GetForegroundDrawList();

    float rounding = 0;
    int color = ImGui::GetColorU32(ImVec4(r / 255.0f, g / 255.0f, b / 255.0f, 205.0f / 255.0f));
    drawList->AddRect(ImVec2(x1, y1), ImVec2(x2, y2), color, rounding);
}

inline void drawBoundingBox(ImVec2 min, ImVec2 max, int r, int g, int b, bool clipping = false)
{
    ImGuiEx::drawBoundingBox(min.x, min.y, max.x, max.y, r, g, b, clipping);
}
}; // namespace ImGuiEx

struct ImGuiLayout
{

public:
    char *idStr;
    ImGuiID id;
    ImGuiLayout *parentLayout;

    bool clipping;
    bool debug;

    ImGuiStorage map;

    ImVec2 position;  // Position of the layout
    ImVec2 size;      // Size of the layout
    ImVec2 sizeParam; // Size parameter used when calling beginLayout
    float paddingLeft;
    float paddingRight;
    float paddingTop;
    float paddingBottom;
    ImVec2 positionContents; // Position of contents.
    ImVec2 sizeContents;     // Size of contents. calculated after the first frame

    bool error;

    // Window Backup data
    ImGuiWindowTempData DC;
    ImRect WorkRect;
    bool skipping;
    int AutoFitChildAxises;
    ImVec2 Pos;
    ImRect ContentRegionRect;

    ImGuiLayout(const char *idStr)
    {
        this->idStr = ImStrdup(idStr);
        this->id = ImHashStr(idStr);
        paddingLeft = 0;
        paddingRight = 0;
        paddingTop = 0;
        paddingBottom = 0;
        clipping = true;
        debug = false;
        error = false;
        skipping = false;
    }

    bool haveParent() { return parentLayout != NULL; }

    ImVec2 getAbsoluteSize()
    {
        return ImVec2(position.x + size.x, position.y + size.y);
    }

    ImVec2 getContentSize()
    {
        return ImVec2(position.x + sizeContents.x, position.y + sizeContents.y);
    }

    ImVec2 getAbsoluteSizePadding()
    {
        return ImVec2(position.x + size.x - paddingRight, position.y + size.y - paddingBottom);
    }

    ImVec2 getContentSizePadding()
    {
        return ImVec2(position.x + sizeContents.x + paddingLeft, position.y + sizeContents.y);
    }

    ImVec2 getPositionPadding()
    {
        return ImVec2(position.x + paddingLeft, position.y + paddingTop);
    }

    void drawSizeDebug()
    {
        // Render layout space
        //Green
        ImGuiEx::drawBoundingBox(position, getAbsoluteSize(), 0, 255, 0);
    }

    inline void drawContentDebug()
    {
        // Render content space
        // Blue
        ImVec2 max = ImVec2(positionContents.x + sizeContents.x, positionContents.y + sizeContents.y);
        ImGuiEx::drawBoundingBox(positionContents, max, 0, 0, 255);
    }

    void drawPaddingAreaDebug()
    {
        // Render size with padding
        ImGuiEx::drawBoundingBox(getPositionPadding(), getAbsoluteSizePadding(), 255, 255, 255);
    }

    void drawError()
    {
        ImGuiEx::drawBoundingBox(position, getAbsoluteSize(), 255, 0, 0, true);
    }
};

struct ImGuiCollapseLayoutOptions
{
public:
    float paddingLeft;
    float paddingRight;
    float paddingTop;
    float paddingBottom;
    ImU32 arrowColor;
    ImU32 arrowBackgroundHoveredColor;
    ImU32 arrowBackgroundClickedColor;
    ImU32 frameColor;
    ImU32 borderColor;
    int borderRound;
    int roundingCorners;

    ImGuiCollapseLayoutOptions()
    {
        paddingLeft = 2;
        paddingRight = 2;
        paddingTop = 2;
        paddingBottom = 2;
        arrowColor = ImGui::GetColorU32(ImVec4(0xFF / 255.0f, 0xFF / 255.0f, 0xFF / 255.0f, 0xFF / 255.0f));
        arrowBackgroundHoveredColor = ImGui::GetColorU32(ImVec4(0x77 / 255.0f, 0x77 / 255.0f, 0x77 / 255.0f, 0xFF / 255.0f));
        arrowBackgroundClickedColor = ImGui::GetColorU32(ImVec4(0x55 / 255.0f, 0x55 / 255.0f, 0x55 / 255.0f, 0xFF / 255.0f));
        frameColor = ImGui::GetColorU32(ImVec4(0x24 / 255.0f, 0x24 / 255.0f, 0x24 / 255.0f, 255 / 255.0f));
        borderColor = ImGui::GetColorU32(ImVec4(0x40 / 255.0f, 0x40 / 255.0f, 0x49 / 255.0f, 255 / 255.0f));
        borderRound = 4;
        roundingCorners = ImDrawCornerFlags_TopLeft | ImDrawCornerFlags_TopRight;
    }
};

namespace ImGuiEx
{
void ShowLayoutDebug();

void BeginLayoutEx(const char *id);
bool PrepareLayout(float sizeX, float sizeY, float paddingLeft = 0, float paddingRight = 0, float paddingTop = 0, float paddingBottom = 0);
bool BeginLayout(const char *id, float sizeX, float sizeY, float paddingLeft = 0, float paddingRight = 0, float paddingTop = 0, float paddingBottom = 0);
void EndLayout();
ImGuiLayout *GetCurrentLayout();

bool BeginCollapseLayoutEx(const char *title, float sizeX, float sizeY, ImGuiCollapseLayoutOptions options = ImGuiCollapseLayoutOptions());
void BeginCollapseLayoutEx(bool *isOpen, const char *title, float sizeX, float sizeY, ImGuiCollapseLayoutOptions options = ImGuiCollapseLayoutOptions());
bool PrepareCollapseLayout(const char *title, float sizeX, float sizeY, ImGuiCollapseLayoutOptions options = ImGuiCollapseLayoutOptions());
bool BeginCollapseLayout(const char *title, float sizeX, float sizeY, ImGuiCollapseLayoutOptions options = ImGuiCollapseLayoutOptions());
void BeginCollapseLayout(bool *isOpen, const char *title, float sizeX, float sizeY, ImGuiCollapseLayoutOptions options = ImGuiCollapseLayoutOptions());
void EndCollapseFrameLayout();
void EndCollapseLayout();

void BeginAlign(const char *id, float sizeX, float sizeY, float alignX = 0.0f, float alignY = 0.0f, float offsetX = 0, float offsetY = 0);
void AlignLayout(float alignX = 0.0f, float alignY = 0.0f, float offsetX = 0, float offsetY = 0);
void EndAlign();
}; // namespace ImGuiEx

namespace ImLayout
{
static float WRAP_PARENT = 0;
static float MATCH_PARENT = -0.1f;
} // namespace ImLayout