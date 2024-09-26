package imgui.internal;

import imgui.ImGuiWindowClass;
import imgui.ImVec2;
import imgui.binding.ImGuiStruct;






public final class ImGuiDockNode extends ImGuiStruct {
    public ImGuiDockNode(final long ptr) {
        super(ptr);
        ImGui.init();
    }

    /*JNI
        #include "_common.h"
        #include "_internal.h"
        #define THIS ((ImGuiDockNode*)STRUCT_PTR)
     */

    public int getID() {
        return nGetID();
    }

    public void setID(final int value) {
        nSetID(value);
    }

    private native int nGetID(); /*
        return THIS->ID;
    */

    private native void nSetID(int value); /*
        THIS->ID = value;
    */

     /**
     * Flags shared by all nodes of a same dockspace hierarchy (inherited from the root node)
     */
    public int getSharedFlags() {
        return nGetSharedFlags();
    }

    /**
     * Flags shared by all nodes of a same dockspace hierarchy (inherited from the root node)
     */
    public void setSharedFlags(final int value) {
        nSetSharedFlags(value);
    }

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
     * Flags shared by all nodes of a same dockspace hierarchy (inherited from the root node)
     */
    public boolean hasSharedFlags(final int flags) {
        return (getSharedFlags() & flags) != 0;
    }

    private native int nGetSharedFlags(); /*
        return THIS->SharedFlags;
    */

    private native void nSetSharedFlags(int value); /*
        THIS->SharedFlags = value;
    */

     /**
     * Flags specific to this node
     */
    public int getLocalFlags() {
        return nGetLocalFlags();
    }

    /**
     * Flags specific to this node
     */
    public void setLocalFlags(final int value) {
        nSetLocalFlags(value);
    }

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

    /**
     * Flags specific to this node
     */
    public boolean hasLocalFlags(final int flags) {
        return (getLocalFlags() & flags) != 0;
    }

    private native int nGetLocalFlags(); /*
        return THIS->LocalFlags;
    */

    private native void nSetLocalFlags(int value); /*
        THIS->LocalFlags = value;
    */

     /**
     * Flags specific to this node, applied from windows
     */
    public int getLocalFlagsInWindows() {
        return nGetLocalFlagsInWindows();
    }

    /**
     * Flags specific to this node, applied from windows
     */
    public void setLocalFlagsInWindows(final int value) {
        nSetLocalFlagsInWindows(value);
    }

    /**
     * Flags specific to this node, applied from windows
     */
    public void addLocalFlagsInWindows(final int flags) {
        setLocalFlagsInWindows(getLocalFlagsInWindows() | flags);
    }

    /**
     * Flags specific to this node, applied from windows
     */
    public void removeLocalFlagsInWindows(final int flags) {
        setLocalFlagsInWindows(getLocalFlagsInWindows() & ~(flags));
    }

    /**
     * Flags specific to this node, applied from windows
     */
    public boolean hasLocalFlagsInWindows(final int flags) {
        return (getLocalFlagsInWindows() & flags) != 0;
    }

    private native int nGetLocalFlagsInWindows(); /*
        return THIS->LocalFlagsInWindows;
    */

    private native void nSetLocalFlagsInWindows(int value); /*
        THIS->LocalFlagsInWindows = value;
    */

     /**
     * Effective flags (== SharedFlags | LocalFlagsInNode | LocalFlagsInWindows)
     */
    public int getMergedFlags() {
        return nGetMergedFlags();
    }

    /**
     * Effective flags (== SharedFlags | LocalFlagsInNode | LocalFlagsInWindows)
     */
    public boolean hasMergedFlags(final int flags) {
        return (getMergedFlags() & flags) != 0;
    }

    private native int nGetMergedFlags(); /*
        return THIS->MergedFlags;
    */

    // TODO: State

    public ImGuiDockNode getParentNode() {
        return new ImGuiDockNode(nGetParentNode());
    }

    public void setParentNode(final ImGuiDockNode value) {
        nSetParentNode(value.ptr);
    }

    private native long nGetParentNode(); /*
        return (uintptr_t)THIS->ParentNode;
    */

    private native void nSetParentNode(long value); /*
        THIS->ParentNode = reinterpret_cast<ImGuiDockNode*>(value);
    */

    /**
     * [Split node only] Child nodes (left/right or top/bottom). Consider switching to an array.
     */
    public ImGuiDockNode getChildNodeFirst() {
        return new ImGuiDockNode(nGetChildNodeFirst());
    }

    private native long nGetChildNodeFirst(); /*
        return (uintptr_t)THIS->ChildNodes[0];
    */

    /**
     * [Split node only] Child nodes (left/right or top/bottom). Consider switching to an array.
     */
    public void setChildNodeFirst(final ImGuiDockNode imGuiDockNode) {
        nSetChildNodeFirst(imGuiDockNode.ptr);
    }

    private native void nSetChildNodeFirst(long imGuiDockNodePtr); /*
        THIS->ChildNodes[0] = (ImGuiDockNode*)imGuiDockNodePtr;
    */

    /**
     * [Split node only] Child nodes (left/right or top/bottom). Consider switching to an array.
     */
    public ImGuiDockNode getChildNodeSecond() {
        return new ImGuiDockNode(nGetChildNodeSecond());
    }

    private native long nGetChildNodeSecond(); /*
        return (uintptr_t)THIS->ChildNodes[1];
    */

    /**
     * [Split node only] Child nodes (left/right or top/bottom). Consider switching to an array.
     */
    public void setChildNodeSecond(final ImGuiDockNode imGuiDockNode) {
        nSetChildNodeSecond(imGuiDockNode.ptr);
    }

    private native void nSetChildNodeSecond(long imGuiDockNodePtr); /*
        THIS->ChildNodes[1] = (ImGuiDockNode*)imGuiDockNodePtr;
    */

    // TODO  Windows, TabBar

     /**
     * Current position
     */
    public ImVec2 getPos() {
        final ImVec2 dst = new ImVec2();
        nGetPos(dst);
        return dst;
    }

    /**
     * Current position
     */
    public float getPosX() {
        return nGetPosX();
    }

    /**
     * Current position
     */
    public float getPosY() {
        return nGetPosY();
    }

    /**
     * Current position
     */
    public void getPos(final ImVec2 dst) {
        nGetPos(dst);
    }

    /**
     * Current position
     */
    public void setPos(final ImVec2 value) {
        nSetPos(value.x, value.y);
    }

    /**
     * Current position
     */
    public void setPos(final float valueX, final float valueY) {
        nSetPos(valueX, valueY);
    }

    private native void nGetPos(ImVec2 dst); /*
        Jni::ImVec2Cpy(env, THIS->Pos, dst);
    */

    private native float nGetPosX(); /*
        return THIS->Pos.x;
    */

    private native float nGetPosY(); /*
        return THIS->Pos.y;
    */

    private native void nSetPos(float valueX, float valueY); /*MANUAL
        ImVec2 value = ImVec2(valueX, valueY);
        THIS->Pos = value;
    */

     /**
     * Current size
     */
    public ImVec2 getSize() {
        final ImVec2 dst = new ImVec2();
        nGetSize(dst);
        return dst;
    }

    /**
     * Current size
     */
    public float getSizeX() {
        return nGetSizeX();
    }

    /**
     * Current size
     */
    public float getSizeY() {
        return nGetSizeY();
    }

    /**
     * Current size
     */
    public void getSize(final ImVec2 dst) {
        nGetSize(dst);
    }

    /**
     * Current size
     */
    public void setSize(final ImVec2 value) {
        nSetSize(value.x, value.y);
    }

    /**
     * Current size
     */
    public void setSize(final float valueX, final float valueY) {
        nSetSize(valueX, valueY);
    }

    private native void nGetSize(ImVec2 dst); /*
        Jni::ImVec2Cpy(env, THIS->Size, dst);
    */

    private native float nGetSizeX(); /*
        return THIS->Size.x;
    */

    private native float nGetSizeY(); /*
        return THIS->Size.y;
    */

    private native void nSetSize(float valueX, float valueY); /*MANUAL
        ImVec2 value = ImVec2(valueX, valueY);
        THIS->Size = value;
    */

     /**
     * [Split node only] Last explicitly written-to size (overridden when using a splitter affecting the node), used to calculate Size.
     */
    public ImVec2 getSizeRef() {
        final ImVec2 dst = new ImVec2();
        nGetSizeRef(dst);
        return dst;
    }

    /**
     * [Split node only] Last explicitly written-to size (overridden when using a splitter affecting the node), used to calculate Size.
     */
    public float getSizeRefX() {
        return nGetSizeRefX();
    }

    /**
     * [Split node only] Last explicitly written-to size (overridden when using a splitter affecting the node), used to calculate Size.
     */
    public float getSizeRefY() {
        return nGetSizeRefY();
    }

    /**
     * [Split node only] Last explicitly written-to size (overridden when using a splitter affecting the node), used to calculate Size.
     */
    public void getSizeRef(final ImVec2 dst) {
        nGetSizeRef(dst);
    }

    /**
     * [Split node only] Last explicitly written-to size (overridden when using a splitter affecting the node), used to calculate Size.
     */
    public void setSizeRef(final ImVec2 value) {
        nSetSizeRef(value.x, value.y);
    }

    /**
     * [Split node only] Last explicitly written-to size (overridden when using a splitter affecting the node), used to calculate Size.
     */
    public void setSizeRef(final float valueX, final float valueY) {
        nSetSizeRef(valueX, valueY);
    }

    private native void nGetSizeRef(ImVec2 dst); /*
        Jni::ImVec2Cpy(env, THIS->SizeRef, dst);
    */

    private native float nGetSizeRefX(); /*
        return THIS->SizeRef.x;
    */

    private native float nGetSizeRefY(); /*
        return THIS->SizeRef.y;
    */

    private native void nSetSizeRef(float valueX, float valueY); /*MANUAL
        ImVec2 value = ImVec2(valueX, valueY);
        THIS->SizeRef = value;
    */

     /**
     * [Split node only] Split axis (X or Y)
     */
    public int getSplitAxis() {
        return nGetSplitAxis();
    }

    private native int nGetSplitAxis(); /*
        return THIS->SplitAxis;
    */

     /**
     * [Root node only]
     */
    public ImGuiWindowClass getWindowClass() {
        return new ImGuiWindowClass(nGetWindowClass());
    }

    private native long nGetWindowClass(); /*
        return (uintptr_t)&THIS->WindowClass;
    */

    public int getLastBgColor() {
        return nGetLastBgColor();
    }

    public void setLastBgColor(final int value) {
        nSetLastBgColor(value);
    }

    private native int nGetLastBgColor(); /*
        return THIS->LastBgColor;
    */

    private native void nSetLastBgColor(int value); /*
        THIS->LastBgColor = value;
    */

    public ImGuiWindow getHostWindow() {
        return new ImGuiWindow(nGetHostWindow());
    }

    public void setHostWindow(final ImGuiWindow value) {
        nSetHostWindow(value.ptr);
    }

    private native long nGetHostWindow(); /*
        return (uintptr_t)THIS->HostWindow;
    */

    private native void nSetHostWindow(long value); /*
        THIS->HostWindow = reinterpret_cast<ImGuiWindow*>(value);
    */

     /**
     * Generally point to window which is ID is == SelectedTabID, but when CTRL+Tabbing this can be a different window.
     */
    public ImGuiWindow getVisibleWindow() {
        return new ImGuiWindow(nGetVisibleWindow());
    }

    /**
     * Generally point to window which is ID is == SelectedTabID, but when CTRL+Tabbing this can be a different window.
     */
    public void setVisibleWindow(final ImGuiWindow value) {
        nSetVisibleWindow(value.ptr);
    }

    private native long nGetVisibleWindow(); /*
        return (uintptr_t)THIS->VisibleWindow;
    */

    private native void nSetVisibleWindow(long value); /*
        THIS->VisibleWindow = reinterpret_cast<ImGuiWindow*>(value);
    */

     /**
     * [Root node only] Pointer to central node.
     */
    public ImGuiDockNode getCentralNode() {
        return new ImGuiDockNode(nGetCentralNode());
    }

    /**
     * [Root node only] Pointer to central node.
     */
    public void setCentralNode(final ImGuiDockNode value) {
        nSetCentralNode(value.ptr);
    }

    private native long nGetCentralNode(); /*
        return (uintptr_t)THIS->CentralNode;
    */

    private native void nSetCentralNode(long value); /*
        THIS->CentralNode = reinterpret_cast<ImGuiDockNode*>(value);
    */

     /**
     * [Root node only] Set when there is a single visible node within the hierarchy.
     */
    public ImGuiDockNode getOnlyNodeWithWindows() {
        return new ImGuiDockNode(nGetOnlyNodeWithWindows());
    }

    /**
     * [Root node only] Set when there is a single visible node within the hierarchy.
     */
    public void setOnlyNodeWithWindows(final ImGuiDockNode value) {
        nSetOnlyNodeWithWindows(value.ptr);
    }

    private native long nGetOnlyNodeWithWindows(); /*
        return (uintptr_t)THIS->OnlyNodeWithWindows;
    */

    private native void nSetOnlyNodeWithWindows(long value); /*
        THIS->OnlyNodeWithWindows = reinterpret_cast<ImGuiDockNode*>(value);
    */

     /**
     * Last frame number the node was updated or kept alive explicitly with DockSpace() + int_KeepAliveOnly
     */
    public int getLastFrameAlive() {
        return nGetLastFrameAlive();
    }

    /**
     * Last frame number the node was updated or kept alive explicitly with DockSpace() + int_KeepAliveOnly
     */
    public void setLastFrameAlive(final int value) {
        nSetLastFrameAlive(value);
    }

    private native int nGetLastFrameAlive(); /*
        return THIS->LastFrameAlive;
    */

    private native void nSetLastFrameAlive(int value); /*
        THIS->LastFrameAlive = value;
    */

     /**
     * Last frame number the node was updated.
     */
    public int getLastFrameActive() {
        return nGetLastFrameActive();
    }

    /**
     * Last frame number the node was updated.
     */
    public void setLastFrameActive(final int value) {
        nSetLastFrameActive(value);
    }

    private native int nGetLastFrameActive(); /*
        return THIS->LastFrameActive;
    */

    private native void nSetLastFrameActive(int value); /*
        THIS->LastFrameActive = value;
    */

     /**
     * Last frame number the node was focused.
     */
    public int getLastFrameFocused() {
        return nGetLastFrameFocused();
    }

    /**
     * Last frame number the node was focused.
     */
    public void setLastFrameFocused(final int value) {
        nSetLastFrameFocused(value);
    }

    private native int nGetLastFrameFocused(); /*
        return THIS->LastFrameFocused;
    */

    private native void nSetLastFrameFocused(int value); /*
        THIS->LastFrameFocused = value;
    */

     /**
     * [Root node only] Which of our child docking node (any ancestor in the hierarchy) was last focused.
     */
    public int getLastFocusedNodeId() {
        return nGetLastFocusedNodeId();
    }

    /**
     * [Root node only] Which of our child docking node (any ancestor in the hierarchy) was last focused.
     */
    public void setLastFocusedNodeId(final int value) {
        nSetLastFocusedNodeId(value);
    }

    private native int nGetLastFocusedNodeId(); /*
        return THIS->LastFocusedNodeId;
    */

    private native void nSetLastFocusedNodeId(int value); /*
        THIS->LastFocusedNodeId = value;
    */

     /**
     * [Leaf node only] Which of our tab/window is selected.
     */
    public int getSelectedTabId() {
        return nGetSelectedTabId();
    }

    /**
     * [Leaf node only] Which of our tab/window is selected.
     */
    public void setSelectedTabId(final int value) {
        nSetSelectedTabId(value);
    }

    private native int nGetSelectedTabId(); /*
        return THIS->SelectedTabId;
    */

    private native void nSetSelectedTabId(int value); /*
        THIS->SelectedTabId = value;
    */

     /**
     * [Leaf node only] Set when closing a specific tab/window.
     */
    public int getWantCloseTabId() {
        return nGetWantCloseTabId();
    }

    /**
     * [Leaf node only] Set when closing a specific tab/window.
     */
    public void setWantCloseTabId(final int value) {
        nSetWantCloseTabId(value);
    }

    private native int nGetWantCloseTabId(); /*
        return THIS->WantCloseTabId;
    */

    private native void nSetWantCloseTabId(int value); /*
        THIS->WantCloseTabId = value;
    */

    public int getAuthorityForPos() {
        return nGetAuthorityForPos();
    }

    public void setAuthorityForPos(final int value) {
        nSetAuthorityForPos(value);
    }

    private native int nGetAuthorityForPos(); /*
        return THIS->AuthorityForPos;
    */

    private native void nSetAuthorityForPos(int value); /*
        THIS->AuthorityForPos = value;
    */

    public int getAuthorityForSize() {
        return nGetAuthorityForSize();
    }

    public void setAuthorityForSize(final int value) {
        nSetAuthorityForSize(value);
    }

    private native int nGetAuthorityForSize(); /*
        return THIS->AuthorityForSize;
    */

    private native void nSetAuthorityForSize(int value); /*
        THIS->AuthorityForSize = value;
    */

    public int getAuthorityForViewport() {
        return nGetAuthorityForViewport();
    }

    public void setAuthorityForViewport(final int value) {
        nSetAuthorityForViewport(value);
    }

    private native int nGetAuthorityForViewport(); /*
        return THIS->AuthorityForViewport;
    */

    private native void nSetAuthorityForViewport(int value); /*
        THIS->AuthorityForViewport = value;
    */

     /**
     * Set to false when the node is hidden (usually disabled as it has no active window)
     */
    public boolean getIsVisible() {
        return nGetIsVisible();
    }

    /**
     * Set to false when the node is hidden (usually disabled as it has no active window)
     */
    public void setIsVisible(final boolean value) {
        nSetIsVisible(value);
    }

    private native boolean nGetIsVisible(); /*
        return THIS->IsVisible;
    */

    private native void nSetIsVisible(boolean value); /*
        THIS->IsVisible = value;
    */

    public boolean getIsFocused() {
        return nGetIsFocused();
    }

    public void setIsFocused(final boolean value) {
        nSetIsFocused(value);
    }

    private native boolean nGetIsFocused(); /*
        return THIS->IsFocused;
    */

    private native void nSetIsFocused(boolean value); /*
        THIS->IsFocused = value;
    */

    public boolean getHasCloseButton() {
        return nGetHasCloseButton();
    }

    public void setHasCloseButton(final boolean value) {
        nSetHasCloseButton(value);
    }

    private native boolean nGetHasCloseButton(); /*
        return THIS->HasCloseButton;
    */

    private native void nSetHasCloseButton(boolean value); /*
        THIS->HasCloseButton = value;
    */

    public boolean getHasWindowMenuButton() {
        return nGetHasWindowMenuButton();
    }

    public void setHasWindowMenuButton(final boolean value) {
        nSetHasWindowMenuButton(value);
    }

    private native boolean nGetHasWindowMenuButton(); /*
        return THIS->HasWindowMenuButton;
    */

    private native void nSetHasWindowMenuButton(boolean value); /*
        THIS->HasWindowMenuButton = value;
    */

    public boolean getHasCentralNodeChild() {
        return nGetHasCentralNodeChild();
    }

    public void setHasCentralNodeChild(final boolean value) {
        nSetHasCentralNodeChild(value);
    }

    private native boolean nGetHasCentralNodeChild(); /*
        return THIS->HasCentralNodeChild;
    */

    private native void nSetHasCentralNodeChild(boolean value); /*
        THIS->HasCentralNodeChild = value;
    */

     /**
     * Set when closing all tabs at once.
     */
    public boolean getWantCloseAll() {
        return nGetWantCloseAll();
    }

    /**
     * Set when closing all tabs at once.
     */
    public void setWantCloseAll(final boolean value) {
        nSetWantCloseAll(value);
    }

    private native boolean nGetWantCloseAll(); /*
        return THIS->WantCloseAll;
    */

    private native void nSetWantCloseAll(boolean value); /*
        THIS->WantCloseAll = value;
    */

    public boolean getWantLockSizeOnce() {
        return nGetWantLockSizeOnce();
    }

    public void setWantLockSizeOnce(final boolean value) {
        nSetWantLockSizeOnce(value);
    }

    private native boolean nGetWantLockSizeOnce(); /*
        return THIS->WantLockSizeOnce;
    */

    private native void nSetWantLockSizeOnce(boolean value); /*
        THIS->WantLockSizeOnce = value;
    */

     /**
     * After a node extraction we need to transition toward moving the newly created host window
     */
    public boolean getWantMouseMove() {
        return nGetWantMouseMove();
    }

    /**
     * After a node extraction we need to transition toward moving the newly created host window
     */
    public void setWantMouseMove(final boolean value) {
        nSetWantMouseMove(value);
    }

    private native boolean nGetWantMouseMove(); /*
        return THIS->WantMouseMove;
    */

    private native void nSetWantMouseMove(boolean value); /*
        THIS->WantMouseMove = value;
    */

    public boolean getWantHiddenTabBarUpdate() {
        return nGetWantHiddenTabBarUpdate();
    }

    public void setWantHiddenTabBarUpdate(final boolean value) {
        nSetWantHiddenTabBarUpdate(value);
    }

    private native boolean nGetWantHiddenTabBarUpdate(); /*
        return THIS->WantHiddenTabBarUpdate;
    */

    private native void nSetWantHiddenTabBarUpdate(boolean value); /*
        THIS->WantHiddenTabBarUpdate = value;
    */

    public boolean getWantHiddenTabBarToggle() {
        return nGetWantHiddenTabBarToggle();
    }

    public void setWantHiddenTabBarToggle(final boolean value) {
        nSetWantHiddenTabBarToggle(value);
    }

    private native boolean nGetWantHiddenTabBarToggle(); /*
        return THIS->WantHiddenTabBarToggle;
    */

    private native void nSetWantHiddenTabBarToggle(boolean value); /*
        THIS->WantHiddenTabBarToggle = value;
    */

    public boolean isRootNode() {
        return nIsRootNode();
    }

    private native boolean nIsRootNode(); /*
        return THIS->IsRootNode();
    */

    public boolean isDockSpace() {
        return nIsDockSpace();
    }

    private native boolean nIsDockSpace(); /*
        return THIS->IsDockSpace();
    */

    public boolean isFloatingNode() {
        return nIsFloatingNode();
    }

    private native boolean nIsFloatingNode(); /*
        return THIS->IsFloatingNode();
    */

    public boolean isCentralNode() {
        return nIsCentralNode();
    }

    private native boolean nIsCentralNode(); /*
        return THIS->IsCentralNode();
    */

     /**
     * Hidden tab bar can be shown back by clicking the small triangle
     */
    public boolean isHiddenTabBar() {
        return nIsHiddenTabBar();
    }

    private native boolean nIsHiddenTabBar(); /*
        return THIS->IsHiddenTabBar();
    */

     /**
     * Never show a tab bar
     */
    public boolean isNoTabBar() {
        return nIsNoTabBar();
    }

    private native boolean nIsNoTabBar(); /*
        return THIS->IsNoTabBar();
    */

    public boolean isSplitNode() {
        return nIsSplitNode();
    }

    private native boolean nIsSplitNode(); /*
        return THIS->IsSplitNode();
    */

    public boolean isLeafNode() {
        return nIsLeafNode();
    }

    private native boolean nIsLeafNode(); /*
        return THIS->IsLeafNode();
    */

    public boolean isEmpty() {
        return nIsEmpty();
    }

    private native boolean nIsEmpty(); /*
        return THIS->IsEmpty();
    */

    public ImRect rect() {
        final ImRect dst = new ImRect();
        nRect(dst);
        return dst;
    }

    public void rect(final ImRect dst) {
        nRect(dst);
    }

    private native void nRect(ImRect dst); /*
        Jni::ImRectCpy(env, THIS->Rect(), dst);
    */

    public void updateMergedFlags() {
        nUpdateMergedFlags();
    }

    private native void nUpdateMergedFlags(); /*
        THIS->UpdateMergedFlags();
    */

    /*JNI
        #undef THIS
     */
}
