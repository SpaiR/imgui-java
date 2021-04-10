package imgui.internal;

import imgui.ImGuiWindowClass;
import imgui.ImVec2;
import imgui.binding.ImGuiStruct;

public final class ImGuiDockNode extends ImGuiStruct {
    private static final ImGuiDockNode PARENT_NODE = new ImGuiDockNode(0);
    private static final ImGuiDockNode CHILD_NODE_FIRST = new ImGuiDockNode(0);
    private static final ImGuiDockNode CHILD_NODE_SECOND = new ImGuiDockNode(0);
    private static final ImGuiWindowClass WINDOW_CLASS = new ImGuiWindowClass(0);
    private static final ImGuiDockNode CENTRAL_NODE = new ImGuiDockNode(0);
    private static final ImGuiDockNode ONLY_NODE_WITH_WINDOWS = new ImGuiDockNode(0);
    private static final ImRect RECT = new ImRect();

    public ImGuiDockNode(final long ptr) {
        super(ptr);
    }

    /*JNI
        #include "_common.h"
        #include <imgui_internal.h>

        #define IMGUI_DOCK_NODE ((ImGuiDockNode*)STRUCT_PTR)
     */

    public native int getID(); /*
       return IMGUI_DOCK_NODE->ID;
    */


    public native void setID(int imGuiID); /*
       IMGUI_DOCK_NODE->ID = imGuiID;
    */

    /**
     * Flags shared by all nodes of a same dockspace hierarchy (inherited from the root node)
     */
    public native int getSharedFlags(); /*
       return IMGUI_DOCK_NODE->SharedFlags;
    */

    /**
     * Flags shared by all nodes of a same dockspace hierarchy (inherited from the root node)
     */
    public native void setSharedFlags(int sharedFlags); /*
       IMGUI_DOCK_NODE->SharedFlags = sharedFlags;
    */

    /**
     * Flags shared by all nodes of a same dockspace hierarchy (inherited from the root node)
     */
    public void addSharedFlags(final int flags) {
        setSharedFlags(getSharedFlags() | flags);
    }

    /**
     * Flags shared by all nodes of a same dockspace hierarchy (inherited from the root node)
     */
    public void removeSharedFlags(final int flags) {
        setSharedFlags(getSharedFlags() & ~(flags));
    }

    /**
     * Flags specific to this node
     */
    public native int getLocalFlags(); /*
       return IMGUI_DOCK_NODE->LocalFlags;
    */

    /**
     * Flags specific to this node
     */
    public native void setLocalFlags(int localFlags); /*
       IMGUI_DOCK_NODE->LocalFlags = localFlags;
    */

    /**
     * Flags specific to this node
     */
    public void addLocalFlags(final int flags) {
        setLocalFlags(getLocalFlags() | flags);
    }

    /**
     * Flags specific to this node
     */
    public void removeLocalFlags(final int flags) {
        setLocalFlags(getLocalFlags() & ~(flags));
    }

    public ImGuiDockNode getParentNode() {
        PARENT_NODE.ptr = nGetParentNode();
        return PARENT_NODE;
    }

    private native long nGetParentNode(); /*
        return (intptr_t)IMGUI_DOCK_NODE->ParentNode;
    */

    public void setParentNode(final ImGuiDockNode imGuiDockNode) {
        nSetParentNode(imGuiDockNode.ptr);
    }

    private native void nSetParentNode(long imGuiDockNodePtr); /*
        IMGUI_DOCK_NODE->ParentNode = (ImGuiDockNode*)imGuiDockNodePtr;
    */

    /**
     * [Split node only] Child nodes (left/right or top/bottom). Consider switching to an array.
     */
    public ImGuiDockNode getChildNodeFirst() {
        CHILD_NODE_FIRST.ptr = nGetChildNodeFirst();
        return CHILD_NODE_FIRST;
    }

    private native long nGetChildNodeFirst(); /*
        return (intptr_t)IMGUI_DOCK_NODE->ChildNodes[0];
    */

    /**
     * [Split node only] Child nodes (left/right or top/bottom). Consider switching to an array.
     */
    public void setChildNodeFirst(final ImGuiDockNode imGuiDockNode) {
        nSetChildNodeFirst(imGuiDockNode.ptr);
    }

    private native void nSetChildNodeFirst(long imGuiDockNodePtr); /*
        IMGUI_DOCK_NODE->ChildNodes[0] = (ImGuiDockNode*)imGuiDockNodePtr;
    */

    /**
     * [Split node only] Child nodes (left/right or top/bottom). Consider switching to an array.
     */
    public ImGuiDockNode getChildNodeSecond() {
        CHILD_NODE_SECOND.ptr = nGetChildNodeSecond();
        return CHILD_NODE_SECOND;
    }

    private native long nGetChildNodeSecond(); /*
        return (intptr_t)IMGUI_DOCK_NODE->ChildNodes[1];
    */

    /**
     * [Split node only] Child nodes (left/right or top/bottom). Consider switching to an array.
     */
    public void setChildNodeSecond(final ImGuiDockNode imGuiDockNode) {
        nSetChildNodeSecond(imGuiDockNode.ptr);
    }

    private native void nSetChildNodeSecond(long imGuiDockNodePtr); /*
        IMGUI_DOCK_NODE->ChildNodes[1] = (ImGuiDockNode*)imGuiDockNodePtr;
    */

    // TODO  Windows, TabBar

    /**
     * [Root node only]
     */
    public ImGuiWindowClass getWindowClass() {
        WINDOW_CLASS.ptr = nGetWindowClass();
        return WINDOW_CLASS;
    }

    /**
     * [Root node only]
     */
    private native long nGetWindowClass(); /*
        return (intptr_t)&IMGUI_DOCK_NODE->WindowClass;
    */

    /**
     * [Root node only]
     */
    public void setWindowClass(final ImGuiWindowClass imGuiWindowClass) {
        nSetWindowClass(imGuiWindowClass.ptr);
    }

    /**
     * [Root node only]
     */
    private native void nSetWindowClass(long imGuiWindowClassPtr); /*
        IMGUI_DOCK_NODE->WindowClass = *((ImGuiWindowClass*)imGuiWindowClassPtr);
    */

    /**
     * Current position
     */
    public ImVec2 getPos() {
        final ImVec2 value = new ImVec2();
        getPos(value);
        return value;
    }

    /**
     * Current position
     */
    public native void getPos(ImVec2 dstImVec2); /*
       Jni::ImVec2Cpy(env, &IMGUI_DOCK_NODE->Pos, dstImVec2);
    */

    /**
     * Current position
     */
    public native float getPosX(); /*
       return IMGUI_DOCK_NODE->Pos.x;
    */

    /**
     * Current position
     */
    public native float getPosY(); /*
       return IMGUI_DOCK_NODE->Pos.y;
    */

    /**
     * Current position
     */
    public native void setPos(float x, float y); /*
       IMGUI_DOCK_NODE->Pos.x = x;
       IMGUI_DOCK_NODE->Pos.y = y;
    */

    /**
     * Current size
     */
    public ImVec2 getSize() {
        final ImVec2 value = new ImVec2();
        getSize(value);
        return value;
    }

    /**
     * Current size
     */
    public native void getSize(ImVec2 dstImVec2); /*
       Jni::ImVec2Cpy(env, &IMGUI_DOCK_NODE->Size, dstImVec2);
    */

    /**
     * Current size
     */
    public native float getSizeX(); /*
       return IMGUI_DOCK_NODE->Size.x;
    */

    /**
     * Current size
     */
    public native float getSizeY(); /*
       return IMGUI_DOCK_NODE->Size.y;
    */

    /**
     * Current size
     */
    public native void setSize(float x, float y); /*
       IMGUI_DOCK_NODE->Size.x = x;
       IMGUI_DOCK_NODE->Size.y = y;
    */

    /**
     * [Split node only] Last explicitly written-to size (overridden when using a splitter affecting the node), used to calculate Size.
     */
    public ImVec2 getSizeRef() {
        final ImVec2 value = new ImVec2();
        getSizeRef(value);
        return value;
    }

    /**
     * [Split node only] Last explicitly written-to size (overridden when using a splitter affecting the node), used to calculate Size.
     */
    public native void getSizeRef(ImVec2 dstImVec2); /*
       Jni::ImVec2Cpy(env, &IMGUI_DOCK_NODE->SizeRef, dstImVec2);
    */

    /**
     * [Split node only] Last explicitly written-to size (overridden when using a splitter affecting the node), used to calculate Size.
     */
    public native float getSizeRefX(); /*
       return IMGUI_DOCK_NODE->SizeRef.x;
    */

    /**
     * [Split node only] Last explicitly written-to size (overridden when using a splitter affecting the node), used to calculate Size.
     */
    public native float getSizeRefY(); /*
       return IMGUI_DOCK_NODE->SizeRef.y;
    */

    /**
     * [Split node only] Last explicitly written-to size (overridden when using a splitter affecting the node), used to calculate Size.
     */
    public native void setSizeRef(float x, float y); /*
       IMGUI_DOCK_NODE->SizeRef.x = x;
       IMGUI_DOCK_NODE->SizeRef.y = y;
    */

    /**
     * [Split node only] Split axis (X or Y)
     */
    public native int getSplitAxis(); /*
       return IMGUI_DOCK_NODE->SplitAxis;
    */

    /**
     * [Split node only] Split axis (X or Y)
     */
    public native void setSplitAxis(int splitAxis); /*
       IMGUI_DOCK_NODE->SplitAxis = (ImGuiAxis)splitAxis;
    */


    public native int getState(); /*
       return IMGUI_DOCK_NODE->State;
    */


    public native void setState(int state); /*
       IMGUI_DOCK_NODE->State = (ImGuiDockNodeState)state;
    */

    // TODO HostWindow, VisibleWindow

    /**
     * [Root node only] Pointer to central node.
     */
    public ImGuiDockNode getCentralNode() {
        CENTRAL_NODE.ptr = nGetCentralNode();
        return PARENT_NODE;
    }

    private native long nGetCentralNode(); /*
        return (intptr_t)IMGUI_DOCK_NODE->CentralNode;
    */

    /**
     * [Root node only] Pointer to central node.
     */
    public void setCentralNode(final ImGuiDockNode imGuiDockNode) {
        nSetCentralNode(imGuiDockNode.ptr);
    }

    private native void nSetCentralNode(long imGuiDockNodePtr); /*
        IMGUI_DOCK_NODE->CentralNode = (ImGuiDockNode*)imGuiDockNodePtr;
    */

    /**
     * [Root node only] Set when there is a single visible node within the hierarchy.
     */
    public ImGuiDockNode getOnlyNodeWithWindows() {
        ONLY_NODE_WITH_WINDOWS.ptr = nGetOnlyNodeWithWindows();
        return PARENT_NODE;
    }

    private native long nGetOnlyNodeWithWindows(); /*
        return (intptr_t)IMGUI_DOCK_NODE->OnlyNodeWithWindows;
    */

    /**
     * [Root node only] Set when there is a single visible node within the hierarchy.
     */
    public void setOnlyNodeWithWindows(final ImGuiDockNode imGuiDockNode) {
        nSetOnlyNodeWithWindows(imGuiDockNode.ptr);
    }

    private native void nSetOnlyNodeWithWindows(long imGuiDockNodePtr); /*
        IMGUI_DOCK_NODE->OnlyNodeWithWindows = (ImGuiDockNode*)imGuiDockNodePtr;
    */

    /**
     * Last frame number the node was updated or kept alive explicitly with DockSpace() + int_KeepAliveOnly
     */
    public native int getLastFrameAlive(); /*
       return IMGUI_DOCK_NODE->LastFrameAlive;
    */

    /**
     * Last frame number the node was updated or kept alive explicitly with DockSpace() + int_KeepAliveOnly
     */
    public native void setLastFrameAlive(int lastFrameAlive); /*
       IMGUI_DOCK_NODE->LastFrameAlive = lastFrameAlive;
    */

    /**
     * Last frame number the node was updated.
     */
    public native int getLastFrameActive(); /*
       return IMGUI_DOCK_NODE->LastFrameActive;
    */

    /**
     * Last frame number the node was updated.
     */
    public native void setLastFrameActive(int lastFrameActive); /*
       IMGUI_DOCK_NODE->LastFrameActive = lastFrameActive;
    */

    /**
     * Last frame number the node was focused.
     */
    public native int getLastFrameFocused(); /*
       return IMGUI_DOCK_NODE->LastFrameFocused;
    */

    /**
     * Last frame number the node was focused.
     */
    public native void setLastFrameFocused(int lastFrameFocused); /*
       IMGUI_DOCK_NODE->LastFrameFocused = lastFrameFocused;
    */

    /**
     * [Root node only] Which of our child docking node (any ancestor in the hierarchy) was last focused.
     */
    public native int getLastFocusedNodeId(); /*
       return IMGUI_DOCK_NODE->LastFocusedNodeId;
    */

    /**
     * [Root node only] Which of our child docking node (any ancestor in the hierarchy) was last focused.
     */
    public native void setLastFocusedNodeId(int lastFocusedNodeId); /*
       IMGUI_DOCK_NODE->LastFocusedNodeId = lastFocusedNodeId;
    */

    /**
     * [Leaf node only] Which of our tab/window is selected.
     */
    public native int getSelectedTabId(); /*
       return IMGUI_DOCK_NODE->SelectedTabId;
    */

    /**
     * [Leaf node only] Which of our tab/window is selected.
     */
    public native void setSelectedTabId(int selectedTabId); /*
       IMGUI_DOCK_NODE->SelectedTabId = selectedTabId;
    */

    /**
     * [Leaf node only] Set when closing a specific tab/window.
     */
    public native int getWantCloseTabId(); /*
       return IMGUI_DOCK_NODE->WantCloseTabId;
    */

    /**
     * [Leaf node only] Set when closing a specific tab/window.
     */
    public native void setWantCloseTabId(int wantCloseTabId); /*
       IMGUI_DOCK_NODE->WantCloseTabId = wantCloseTabId;
    */


    public native int getAuthorityForPos(); /*
       return IMGUI_DOCK_NODE->AuthorityForPos;
    */


    public native void setAuthorityForPos(int authorityForPos); /*
       IMGUI_DOCK_NODE->AuthorityForPos = authorityForPos;
    */


    public native int getAuthorityForSize(); /*
       return IMGUI_DOCK_NODE->AuthorityForSize;
    */


    public native void setAuthorityForSize(int authorityForSize); /*
       IMGUI_DOCK_NODE->AuthorityForSize = authorityForSize;
    */


    public native int getAuthorityForViewport(); /*
       return IMGUI_DOCK_NODE->AuthorityForViewport;
    */


    public native void setAuthorityForViewport(int authorityForViewport); /*
       IMGUI_DOCK_NODE->AuthorityForViewport = authorityForViewport;
    */

    /**
     * Set to false when the node is hidden (usually disabled as it has no active window)
     */
    public native boolean getIsVisible(); /*
       return IMGUI_DOCK_NODE->IsVisible;
    */

    /**
     * Set to false when the node is hidden (usually disabled as it has no active window)
     */
    public native void setIsVisible(boolean isVisible); /*
       IMGUI_DOCK_NODE->IsVisible = isVisible;
    */


    public native boolean getIsFocused(); /*
       return IMGUI_DOCK_NODE->IsFocused;
    */


    public native void setIsFocused(boolean isFocused); /*
       IMGUI_DOCK_NODE->IsFocused = isFocused;
    */


    public native boolean getHasCloseButton(); /*
       return IMGUI_DOCK_NODE->HasCloseButton;
    */


    public native void setHasCloseButton(boolean hasCloseButton); /*
       IMGUI_DOCK_NODE->HasCloseButton = hasCloseButton;
    */


    public native boolean getHasWindowMenuButton(); /*
       return IMGUI_DOCK_NODE->HasWindowMenuButton;
    */


    public native void setHasWindowMenuButton(boolean hasWindowMenuButton); /*
       IMGUI_DOCK_NODE->HasWindowMenuButton = hasWindowMenuButton;
    */

    /**
     * Set when closing all tabs at once.
     */
    public native boolean getWantCloseAll(); /*
       return IMGUI_DOCK_NODE->WantCloseAll;
    */

    /**
     * Set when closing all tabs at once.
     */
    public native void setWantCloseAll(boolean wantCloseAll); /*
       IMGUI_DOCK_NODE->WantCloseAll = wantCloseAll;
    */


    public native boolean getWantLockSizeOnce(); /*
       return IMGUI_DOCK_NODE->WantLockSizeOnce;
    */


    public native void setWantLockSizeOnce(boolean wantLockSizeOnce); /*
       IMGUI_DOCK_NODE->WantLockSizeOnce = wantLockSizeOnce;
    */

    /**
     * After a node extraction we need to transition toward moving the newly created host window
     */
    public native boolean getWantMouseMove(); /*
       return IMGUI_DOCK_NODE->WantMouseMove;
    */

    /**
     * After a node extraction we need to transition toward moving the newly created host window
     */
    public native void setWantMouseMove(boolean wantMouseMove); /*
       IMGUI_DOCK_NODE->WantMouseMove = wantMouseMove;
    */


    public native boolean getWantHiddenTabBarUpdate(); /*
       return IMGUI_DOCK_NODE->WantHiddenTabBarUpdate;
    */


    public native void setWantHiddenTabBarUpdate(boolean wantHiddenTabBarUpdate); /*
       IMGUI_DOCK_NODE->WantHiddenTabBarUpdate = wantHiddenTabBarUpdate;
    */


    public native boolean getWantHiddenTabBarToggle(); /*
       return IMGUI_DOCK_NODE->WantHiddenTabBarToggle;
    */


    public native void setWantHiddenTabBarToggle(boolean wantHiddenTabBarToggle); /*
       IMGUI_DOCK_NODE->WantHiddenTabBarToggle = wantHiddenTabBarToggle;
    */

    /**
     * Update by DockNodeTreeUpdatePosSize() write-filtering
     */
    public native boolean getMarkedForPosSizeWrite(); /*
       return IMGUI_DOCK_NODE->MarkedForPosSizeWrite;
    */

    /**
     * Update by DockNodeTreeUpdatePosSize() write-filtering
     */
    public native void setMarkedForPosSizeWrite(boolean markedForPosSizeWrite); /*
       IMGUI_DOCK_NODE->MarkedForPosSizeWrite = markedForPosSizeWrite;
    */

    public native boolean isRootNode(); /*
        return IMGUI_DOCK_NODE->IsRootNode();
    */

    public native boolean isDockSpace(); /*
        return IMGUI_DOCK_NODE->IsDockSpace();
    */

    public native boolean isFloatingNode(); /*
        return IMGUI_DOCK_NODE->IsFloatingNode();
    */

    public native boolean isCentralNode(); /*
        return IMGUI_DOCK_NODE->IsCentralNode();
    */

    /**
     * Hidden tab bar can be shown back by clicking the small triangle
     */
    public native boolean isHiddenTabBar(); /*
        return IMGUI_DOCK_NODE->IsHiddenTabBar();
    */

    /**
     * Never show a tab bar
     */
    public native boolean isNoTabBar(); /*
        return IMGUI_DOCK_NODE->IsNoTabBar();
    */

    public native boolean isSplitNode(); /*
        return IMGUI_DOCK_NODE->IsSplitNode();
    */

    public native boolean isLeafNode(); /*
        return IMGUI_DOCK_NODE->IsLeafNode();
    */

    public native boolean isEmpty(); /*
        return IMGUI_DOCK_NODE->IsEmpty();
    */

    public native int getMergedFlags(); /*
        return IMGUI_DOCK_NODE->GetMergedFlags();
    */

    public ImRect rect() {
        nRect(RECT.min, RECT.max);
        return RECT;
    }

    private native void nRect(ImVec2 minDstImVec2, ImVec2 maxDstImVec2); /*
        ImRect rect = IMGUI_DOCK_NODE->Rect();
        Jni::ImVec2Cpy(env, &rect.Min, minDstImVec2);
        Jni::ImVec2Cpy(env, &rect.Max, maxDstImVec2);
    */
}

