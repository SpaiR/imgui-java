#include "imgui.h"
#include "imgui_internal.h"
#include "imgui_layout_widget.h"

static ImVector<ImGuiLayout *> layoutStack;

static ImGuiLayout *createOrFind(const char *id)
{
    ImGuiContext &g = *GImGui;
    ImGuiLayout *childLayout = NULL;
    ImGuiID hashID = ImHashStr(id);
    childLayout = (ImGuiLayout *)g.WindowsById.GetVoidPtr(hashID);
    if (childLayout == NULL)
    {
        childLayout = IM_NEW(ImGuiLayout)(id);
        g.WindowsById.SetVoidPtr(childLayout->id, childLayout);
    }
    return childLayout;
}

static ImGuiLayout *pushLayout(const char *id)
{
    ImGuiLayout *parentLayout = NULL;
    if (!layoutStack.empty())
        parentLayout = layoutStack.back();
    ImGuiLayout *childLayout = createOrFind(id);
    childLayout->parentLayout = parentLayout;
    layoutStack.push_back(childLayout);
    return childLayout;
}

ImGuiLayout *ImGuiEx::GetCurrentLayout()
{
    if (layoutStack.empty())
        return NULL;
    return layoutStack.back();
}

static void popLayout()
{
    layoutStack.pop_back();
}

void ImGuiEx::ShowLayoutDebug()
{
    ImGuiLayout *curLayout = ImGuiEx::GetCurrentLayout();
    if (curLayout != NULL)
    {
        curLayout->debug = true;
    }
}

void ImGuiEx::BeginLayoutEx(const char *strID)
{
    ImGuiLayout *parentLayout = ImGuiEx::GetCurrentLayout();
    ImGuiID id = ImHashStr(strID);
    char title[256];
    if (parentLayout)
        ImFormatString(title, IM_ARRAYSIZE(title), "%s/%s_%08X", parentLayout->idStr, strID, id);
    else
        ImFormatString(title, IM_ARRAYSIZE(title), "%s/%08X", strID, id);

    pushLayout(title);
}

bool ImGuiEx::PrepareLayout(float sizeX, float sizeY, float paddingLeft, float paddingRight, float paddingTop, float paddingBottom)
{
    ImGuiContext &g = *GImGui;
    ImGuiWindow *window = g.CurrentWindow;

    ImGuiLayout *curLayout = ImGuiEx::GetCurrentLayout();
    bool ret = true;
    // Update layout

    // Backup windows data
    curLayout->DC = window->DC;
    curLayout->WorkRect = window->WorkRect;
    curLayout->skipping = window->SkipItems;
    curLayout->AutoFitChildAxises = window->AutoFitChildAxises;
    curLayout->Pos = window->Pos;
    curLayout->ContentRegionRect = window->ContentRegionRect;
    // ******** End Backup windows data

    curLayout->sizeParam.x = sizeX;
    curLayout->sizeParam.y = sizeY;

    curLayout->paddingLeft = paddingLeft;
    curLayout->paddingRight = paddingRight;
    curLayout->paddingTop = paddingTop;
    curLayout->paddingBottom = paddingBottom;

    curLayout->position = window->DC.CursorPos;

    ImVec2 contentPosition = curLayout->getPositionPadding();

    curLayout->positionContents = curLayout->position;

    const ImVec2 content_avail = ImGui::GetContentRegionAvail();

    ImVec2 sizeItem = ImFloor(curLayout->sizeParam);

    if (curLayout->sizeParam.x < 0.0f)
    {
        float sizeX = ImMax(content_avail.x + sizeItem.x, 4.0f);
        curLayout->size.x = sizeX;
    }
    if (curLayout->sizeParam.y < 0.0f)
    {
        float sizeY = ImMax(content_avail.y + sizeItem.y, 4.0f);
        curLayout->size.y = sizeY;
    }

    // ***** End Update Layout

    // Write to window object

    window->Pos.x = contentPosition.x;
    window->Pos.y = contentPosition.y;
    window->DC.Indent.x = 0;

    //window->DC.CursorMaxPos.x = contentPosition.x + curLayout->size.x - curLayout->paddingLeft - curLayout->paddingRight;
    //window->DC.CursorMaxPos.y = contentPosition.y + curLayout->size.y - curLayout->paddingTop - curLayout->paddingBottom;
    window->DC.CursorMaxPos.x = contentPosition.x;
    window->DC.CursorMaxPos.y = contentPosition.y;

    window->DC.CursorStartPos.x = contentPosition.x;
    window->DC.CursorStartPos.y = contentPosition.y;

    window->DC.CurrLineSize.y = 0; // necessary to keep position inside layout
    window->DC.CurrLineTextBaseOffset = 0;

    window->DC.CursorPos.x = contentPosition.x;
    window->DC.CursorPos.y = contentPosition.y;

    window->WorkRect.Min.x = contentPosition.x;
    window->WorkRect.Min.y = contentPosition.y;
    window->WorkRect.Max.x = contentPosition.x + curLayout->size.x - curLayout->paddingLeft - curLayout->paddingRight;
    window->WorkRect.Max.y = contentPosition.y + curLayout->size.y - curLayout->paddingTop - curLayout->paddingBottom;

    window->ContentRegionRect.Min.x = contentPosition.x;
    window->ContentRegionRect.Min.y = contentPosition.y;
    window->ContentRegionRect.Max.x = contentPosition.x + curLayout->size.x - curLayout->paddingLeft - curLayout->paddingRight;
    window->ContentRegionRect.Max.y = contentPosition.y + curLayout->size.y - curLayout->paddingTop - curLayout->paddingBottom;

    // ***** End Write to window object

    if (curLayout->clipping)
    {
        ImVec2 min = curLayout->getPositionPadding();
        ImVec2 max = curLayout->getAbsoluteSizePadding();
        //ImGui::drawBoundingBox(min, max, 255, 0, 0);
        ImGui::PushClipRect(min, max, true);
    }

    bool skip_items = false;
    if (window->Collapsed || !window->Active || window->Hidden)
        /* if (window->AutoFitFramesX <= 0 && window->AutoFitFramesY <= 0 && window->HiddenFramesCannotSkipItems <= 0)*/
        skip_items = true;
    window->SkipItems = skip_items;
    ret = !skip_items;
    return true;
}

bool ImGuiEx::BeginLayout(const char *strID, float sizeX, float sizeY, float paddingLeft, float paddingRight, float paddingTop, float paddingBottom)
{
    BeginLayoutEx(strID);
    return PrepareLayout(sizeX, sizeY, paddingLeft, paddingRight, paddingTop, paddingBottom);
}

void ImGuiEx::EndLayout()
{
    ImGuiContext &g = *GImGui;
    ImGuiWindow *window = g.CurrentWindow;
    ImGuiLayout *curLayout = ImGuiEx::GetCurrentLayout();
    if (curLayout == NULL)
        return;

    if (curLayout->clipping)
        ImGui::PopClipRect();

    float x = window->DC.CursorPos.x;
    float y = window->DC.CursorPos.y;

    curLayout->sizeContents.x = window->DC.CursorMaxPos.x - x;
    //curLayout->sizeContents.y = y - curLayout->position.y - g.Style.ItemSpacing.y;

    //curLayout->sizeContents.x = window->DC.CursorMaxPos.x - curLayout->positionContents.x;
    curLayout->sizeContents.y = y - curLayout->positionContents.y - g.Style.ItemSpacing.y;

    // Restore windows data
    window->DC = curLayout->DC;
    window->WorkRect = curLayout->WorkRect;
    window->SkipItems = curLayout->skipping;
    window->AutoFitChildAxises = curLayout->AutoFitChildAxises;
    window->Pos = curLayout->Pos;
    window->ContentRegionRect = curLayout->ContentRegionRect;
    // ********************

    //const ImVec2 content_avail = GetContentRegionAvail();
    ImVec2 sizeItem = curLayout->sizeParam;

    if (sizeItem.x < 0.0f)
    {
        sizeItem.x = curLayout->size.x;
        if (curLayout->size.x < curLayout->sizeContents.x || curLayout->size.x > curLayout->sizeContents.x)
        {
            //special case where dev used MATCH_CONTENT and WRAP_CONTENT wrong. The parent have a WRAP_CONTENT y size and the child have MATCH_CONTENT y size.
            /* curLayout->error = true;
            sizeItem.x = 10;*/
        }
    }
    else if (sizeItem.x == 0.0f)
        sizeItem.x = curLayout->sizeContents.x + curLayout->paddingLeft + curLayout->paddingRight;

    if (sizeItem.y < 0.0f)
    {
        sizeItem.y = curLayout->size.y;
        if (curLayout->size.y < curLayout->sizeContents.y)
        {
            //special case where dev used MATCH_CONTENT and WRAP_CONTENT wrong. The parent have a WRAP_CONTENT y size and the child have MATCH_CONTENT y size.
            //curLayout->error = true;
            //sizeItem.y = curLayout->sizeContents.y;
        }
    }
    else if (sizeItem.y == 0.0f)
        sizeItem.y = curLayout->sizeContents.y + curLayout->paddingBottom;

    curLayout->size = sizeItem;

    ImGui::ItemSize(sizeItem);

    if (curLayout->debug)
    {
        curLayout->drawContentDebug();
        curLayout->drawPaddingAreaDebug();
        curLayout->drawSizeDebug();
        curLayout->debug = false;
    }

    if (curLayout->error)
    {
        curLayout->error = false;
        curLayout->drawError();
    }

    popLayout();
}

static bool renderFrameArrow(bool *value, int arrowColor, int arrowBackgroundHoveredColor, int arrowBackgroundClickedColor)
{
    ImGuiContext &g = *GImGui;
    ImGuiWindow *window = g.CurrentWindow;

    ImDrawList *drawList = ImGui::GetWindowDrawList();
    float getFrameHeight = ImGui::GetFrameHeight();
    float removedSize = 5.5f; // reduce few pixels
    float halfSize = (getFrameHeight) / 2.0f - removedSize;

    ImVec2 vec = ImGui::GetCursorScreenPos();

    float screenPosX = vec.x;
    float screenPosY = vec.y;
    float arrowPaddingLeft = 6;

    float x = screenPosX + halfSize + arrowPaddingLeft;
    float y = screenPosY + getFrameHeight / 2.0f;

    bool hovered = ImGui::IsMouseHoveringRect(ImVec2(x - halfSize - removedSize, y - halfSize - removedSize), ImVec2(x + halfSize + removedSize, y + halfSize + removedSize));
    ImU32 hoveredColor = arrowBackgroundHoveredColor;
    bool isWindowHovered = ImGui::IsWindowHovered();

    if (isWindowHovered)
    {
        if (hovered)
        {
            if (ImGui::IsMouseDown(0))
                hoveredColor = arrowBackgroundClickedColor;
            if (ImGui::IsMouseReleased(0))
                *value = !*value;
        }

        if (hovered)
            drawList->AddCircleFilled(ImVec2(x, y), halfSize * 2, hoveredColor);
    }

    float triA_X = 0;
    float triA_Y = 0;
    float triB_X = 0;
    float triB_Y = 0;
    float triC_X = 0;
    float triC_Y = 0;

    if (*value)
    {
        // arrow down
        float offset = -0.5f;
        triA_X = x - halfSize + offset;
        triA_Y = y - halfSize;
        triB_X = x + halfSize + offset;
        triB_Y = y - halfSize;
        triC_X = x + offset;
        triC_Y = y + halfSize;
    }
    else
    {
        // arrow right
        triA_X = x - halfSize;
        triA_Y = y - halfSize;
        triB_X = x + halfSize;
        triB_Y = y;
        triC_X = x - halfSize;
        triC_Y = y + halfSize;
    }

    drawList->AddTriangleFilled(ImVec2(triA_X, triA_Y), ImVec2(triB_X, triB_Y), ImVec2(triC_X, triC_Y), arrowColor);

    float bk = g.Style.ItemSpacing.y;
    g.Style.ItemSpacing.y = 0;
    ImGui::ItemSize(ImVec2(halfSize * 2 + 3, getFrameHeight));
    g.Style.ItemSpacing.y = bk;

    return *value;
}

static int OPEN_KEY = 13213;

bool ImGuiEx::PrepareCollapseLayout(const char *title, float sizeX, float sizeY, ImGuiCollapseLayoutOptions options)
{
    ImGuiContext &g = *GImGui;
    ImGuiWindow *window = g.CurrentWindow;
    ImDrawList *drawList = window->DrawList;
    ImGuiLayout *rootLayout = ImGuiEx::GetCurrentLayout();

    float frameHeight = ImGui::GetFrameHeight();

    bool flag = rootLayout->map.GetBool(OPEN_KEY, false);
    bool *isOpen = &flag;

    sizeY = *isOpen ? sizeY : ImLayout::WRAP_PARENT;

    ImGuiEx::PrepareLayout(sizeX, sizeY, 1, 1, 1, 1);

    rootLayout->map.SetFloat(120, options.paddingLeft);
    rootLayout->map.SetFloat(121, options.paddingRight);
    rootLayout->map.SetFloat(122, options.paddingTop);
    rootLayout->map.SetFloat(123, options.paddingBottom);

    ImGuiEx::BeginLayout("frame", ImLayout::MATCH_PARENT, frameHeight, 0, 0, 0, 0);
    ImGuiLayout *frameLayout = ImGuiEx::GetCurrentLayout();

    ImVec2 mousePos = ImGui::GetMousePos();

    drawList->AddRectFilled(rootLayout->position, ImVec2(rootLayout->getAbsoluteSize().x, frameLayout->getAbsoluteSize().y), options.frameColor, options.borderRound, options.roundingCorners);

    renderFrameArrow(isOpen, options.arrowColor, options.arrowBackgroundHoveredColor, options.arrowBackgroundClickedColor);

    rootLayout->map.SetBool(OPEN_KEY, *isOpen);

    ImGui::SameLine();

    ImGuiEx::BeginAlign("align", ImLayout::WRAP_PARENT, ImLayout::MATCH_PARENT, 0, 0.5f, 0, 0);

    ImGui::Text(title);

    ImGuiEx::EndAlign();

    ImGui::SameLine();

    return *isOpen;
}

void ImGuiEx::BeginCollapseLayoutEx(bool *isOpen, const char *title, float sizeX, float sizeY, ImGuiCollapseLayoutOptions options)
{
    ImGuiEx::BeginLayoutEx(title);
    ImGuiLayout *rootLayout = ImGuiEx::GetCurrentLayout();
    rootLayout->map.SetBool(OPEN_KEY, *isOpen);
    bool flag = ImGuiEx::PrepareCollapseLayout(title, sizeX, sizeY, options);
    *isOpen = flag;
}

bool ImGuiEx::BeginCollapseLayoutEx(const char *title, float sizeX, float sizeY, ImGuiCollapseLayoutOptions options)
{
    ImGuiEx::BeginLayoutEx(title);
    return ImGuiEx::PrepareCollapseLayout(title, sizeX, sizeY, options);
}

bool ImGuiEx::BeginCollapseLayout(const char *title, float sizeX, float sizeY, ImGuiCollapseLayoutOptions options)
{
    bool flag = ImGuiEx::BeginCollapseLayoutEx(title, sizeX, sizeY, options);
    ImGuiEx::EndCollapseFrameLayout();
    return flag;
}

void ImGuiEx::BeginCollapseLayout(bool *isOpen, const char *title, float sizeX, float sizeY, ImGuiCollapseLayoutOptions options)
{
    ImGuiEx::BeginCollapseLayoutEx(isOpen, title, sizeX, sizeY, options);
    ImGuiEx::EndCollapseFrameLayout();
}

void ImGuiEx::EndCollapseFrameLayout()
{
    ImGuiContext &g = *GImGui;
    float bk = g.Style.ItemSpacing.y;

    g.Style.ItemSpacing.y = 0;
    ImGuiEx::EndLayout(); // end frame
    g.Style.ItemSpacing.y = bk;
    ImGuiLayout *rootLayout = ImGuiEx::GetCurrentLayout();

    float paddingLeft = rootLayout->map.GetFloat(120, 0.0f);
    float paddingRight = rootLayout->map.GetFloat(121, 0.0f);
    float paddingTop = rootLayout->map.GetFloat(122, 0.0f);
    float paddingBottom = rootLayout->map.GetFloat(123, 0.0f);

    ImGuiEx::BeginLayout("content", ImLayout::MATCH_PARENT, ImLayout::WRAP_PARENT, paddingLeft, paddingRight, paddingTop, paddingBottom);
    ImGuiLayout *collapseLayout = ImGuiEx::GetCurrentLayout();
}

void ImGuiEx::EndCollapseLayout()
{
    ImGuiContext &g = *GImGui;
    ImGuiWindow *window = g.CurrentWindow;
    ImDrawList *drawList = window->DrawList;

    ImU32 borderColor = ImGui::GetColorU32(ImVec4(0x40 / 255.0f, 0x40 / 255.0f, 0x49 / 255.0f, 255 / 255.0f));
    int borderRound = 4;
    int roundingCorners = ImDrawCornerFlags_TopLeft | ImDrawCornerFlags_TopRight;

    ImGuiEx::EndLayout(); // end content

    ImGuiLayout *rootLayout = ImGuiEx::GetCurrentLayout();

    ImGuiEx::EndLayout(); // end root

    ImVec2 borderPosition = rootLayout->position;
    ImVec2 borderSize = rootLayout->getAbsoluteSize();

    drawList->AddRect(borderPosition, borderSize, borderColor, borderRound, roundingCorners, 1.0f);
}

void ImGuiEx::BeginAlign(const char *strID, float sizeX, float sizeY, float alignX, float alignY, float offsetX, float offsetY)
{
    ImGuiEx::BeginLayout(strID, sizeX, sizeY);
    ImGuiEx::AlignLayout(alignX, alignY, offsetX, offsetY);
}

void ImGuiEx::AlignLayout(float alignX, float alignY, float offsetX, float offsetY)
{
    ImGuiContext &g = *GImGui;
    ImGuiWindow *window = g.CurrentWindow;
    ImGuiLayout *curLayout = ImGuiEx::GetCurrentLayout();
    if (curLayout == NULL)
        return;

    ImVec2 regionAvail = ImGui::GetContentRegionAvail();
    float totalX = regionAvail.x;
    float totalY = regionAvail.y;

    ImVec2 posPad = curLayout->getPositionPadding();
    ImVec2 absSizePad = curLayout->getAbsoluteSizePadding();

    if (alignX >= 0.0f && curLayout->sizeParam.x != ImLayout::WRAP_PARENT)
    {

        float addX = ImFloor((totalX - curLayout->sizeContents.x) * alignX);
        float newX = posPad.x + addX + offsetX;
        if (newX < posPad.x)
        {
            curLayout->error = true;
            newX = posPad.x;
        }
        else if (newX + curLayout->sizeContents.x > absSizePad.x)
        {
            curLayout->error = true;
            newX = absSizePad.x - curLayout->sizeContents.x;
        }
        curLayout->positionContents.x = newX;
    }

    if (alignY >= 0.0f && curLayout->sizeParam.y != ImLayout::WRAP_PARENT)
    {
        float addY = ImFloor((totalY - curLayout->sizeContents.y) * alignY);
        float newY = posPad.y + addY + offsetY;
        if (newY < posPad.y)
        {
            curLayout->error = true;
            newY = posPad.y;
        }
        else if (newY + curLayout->sizeContents.y > absSizePad.y)
        {
            curLayout->error = true;
            newY = absSizePad.y - curLayout->sizeContents.y;
        }
        curLayout->positionContents.y = newY;
    }
    window->DC.CursorMaxPos.x = curLayout->positionContents.x;
    window->DC.CursorMaxPos.y = curLayout->positionContents.y;

    window->DC.CursorStartPos.x = curLayout->positionContents.x;
    window->DC.CursorStartPos.y = curLayout->positionContents.y;

    window->Pos.x = curLayout->positionContents.x;
    window->Pos.y = curLayout->positionContents.y;

    window->DC.CursorPos.x = curLayout->positionContents.x;
    window->DC.CursorPos.y = curLayout->positionContents.y;
}

void ImGuiEx::EndAlign()
{
    ImGuiEx::EndLayout();
}