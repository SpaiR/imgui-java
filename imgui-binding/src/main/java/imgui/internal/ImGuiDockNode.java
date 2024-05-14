package imgui.internal;

import imgui.ImGuiWindowClass;
import imgui.ImVec2;
import imgui.binding.ImGuiStruct;
import imgui.binding.annotation.BindingField;
import imgui.binding.annotation.BindingMethod;
import imgui.binding.annotation.BindingSource;
import imgui.binding.annotation.ReturnValue;

@BindingSource
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

    @BindingField
    public int ID;

    /**
     * Flags shared by all nodes of a same dockspace hierarchy (inherited from the root node)
     */
    @BindingField(isFlag = true)
    public int SharedFlags;

    /**
     * Flags specific to this node
     */
    @BindingField(isFlag = true)
    public int LocalFlags;

    /**
     * Flags specific to this node, applied from windows
     */
    @BindingField(isFlag = true)
    public int LocalFlagsInWindows;

    /**
     * Effective flags (== SharedFlags | LocalFlagsInNode | LocalFlagsInWindows)
     */
    @BindingField(isFlag = true, accessors = BindingField.Accessor.GETTER)
    public int MergedFlags;

    // TODO: State

    @BindingField
    public ImGuiDockNode ParentNode;

    /**
     * [Split node only] Child nodes (left/right or top/bottom). Consider switching to an array.
     */
    public ImGuiDockNode getChildNodeFirst() {
        return new ImGuiDockNode(nGetChildNodeFirst());
    }

    private native long nGetChildNodeFirst(); /*
        return (intptr_t)THIS->ChildNodes[0];
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
        return (intptr_t)THIS->ChildNodes[1];
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
    @BindingField
    public ImVec2 Pos;

    /**
     * Current size
     */
    @BindingField
    public ImVec2 Size;

    /**
     * [Split node only] Last explicitly written-to size (overridden when using a splitter affecting the node), used to calculate Size.
     */
    @BindingField
    public ImVec2 SizeRef;

    /**
     * [Split node only] Split axis (X or Y)
     */
    @BindingField(accessors = BindingField.Accessor.GETTER)
    public int SplitAxis;

    /**
     * [Root node only]
     */
    @BindingField(accessors = BindingField.Accessor.GETTER)
    @ReturnValue(callPrefix = "&")
    public ImGuiWindowClass WindowClass;

    @BindingField
    public int LastBgColor;

    @BindingField
    public ImGuiWindow HostWindow;

    /**
     * Generally point to window which is ID is == SelectedTabID, but when CTRL+Tabbing this can be a different window.
     */
    @BindingField
    public ImGuiWindow VisibleWindow;

    /**
     * [Root node only] Pointer to central node.
     */
    @BindingField
    public ImGuiDockNode CentralNode;

    /**
     * [Root node only] Set when there is a single visible node within the hierarchy.
     */
    @BindingField
    public ImGuiDockNode OnlyNodeWithWindows;

    /**
     * Last frame number the node was updated or kept alive explicitly with DockSpace() + int_KeepAliveOnly
     */
    @BindingField
    public int LastFrameAlive;

    /**
     * Last frame number the node was updated.
     */
    @BindingField
    public int LastFrameActive;

    /**
     * Last frame number the node was focused.
     */
    @BindingField
    public int LastFrameFocused;

    /**
     * [Root node only] Which of our child docking node (any ancestor in the hierarchy) was last focused.
     */
    @BindingField
    public int LastFocusedNodeId;

    /**
     * [Leaf node only] Which of our tab/window is selected.
     */
    @BindingField
    public int SelectedTabId;

    /**
     * [Leaf node only] Set when closing a specific tab/window.
     */
    @BindingField
    public int WantCloseTabId;

    @BindingField
    public int AuthorityForPos;

    @BindingField
    public int AuthorityForSize;

    @BindingField
    public int AuthorityForViewport;

    /**
     * Set to false when the node is hidden (usually disabled as it has no active window)
     */
    @BindingField
    public boolean IsVisible;

    @BindingField
    public boolean IsFocused;

    @BindingField
    public boolean HasCloseButton;

    @BindingField
    public boolean HasWindowMenuButton;

    @BindingField
    public boolean HasCentralNodeChild;

    /**
     * Set when closing all tabs at once.
     */
    @BindingField
    public boolean WantCloseAll;

    @BindingField
    public boolean WantLockSizeOnce;

    /**
     * After a node extraction we need to transition toward moving the newly created host window
     */
    @BindingField
    public boolean WantMouseMove;

    @BindingField
    public boolean WantHiddenTabBarUpdate;

    @BindingField
    public boolean WantHiddenTabBarToggle;

    @BindingMethod
    public native boolean IsRootNode();

    @BindingMethod
    public native boolean IsDockSpace();

    @BindingMethod
    public native boolean IsFloatingNode();

    @BindingMethod
    public native boolean IsCentralNode();

    /**
     * Hidden tab bar can be shown back by clicking the small triangle
     */
    @BindingMethod
    public native boolean IsHiddenTabBar();

    /**
     * Never show a tab bar
     */
    @BindingMethod
    public native boolean IsNoTabBar();

    @BindingMethod
    public native boolean IsSplitNode();

    @BindingMethod
    public native boolean IsLeafNode();

    @BindingMethod
    public native boolean IsEmpty();

    @BindingMethod
    public native ImRect Rect();

    @BindingMethod
    public native void UpdateMergedFlags();

    /*JNI
        #undef THIS
     */
}
