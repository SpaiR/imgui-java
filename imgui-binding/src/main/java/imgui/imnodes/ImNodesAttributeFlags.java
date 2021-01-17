package imgui.imnodes;

/**
 * This enum controls the way the attribute pins behave.
 */
public final class ImNodesAttributeFlags {

    private ImNodesAttributeFlags() {
    }

    public static final int None = 0;

    /**
     * Allow detaching a link by left-clicking and dragging the link at a pin it is connected to.
     * NOTE: the user has to actually delete the link for this to work. A deleted link can be
     * detected by calling IsLinkDestroyed() after EndNodeEditor().
     */
    public static final int EnableLinkDetachWithDragClick = 1 << 0;

    /**
     * Visual snapping of an in progress link will trigger IsLink Created/Destroyed events. Allows
     * for previewing the creation of a link while dragging it across attributes. See here for demo:
     * https://github.com/Nelarius/imnodes/issues/41#issuecomment-647132113 NOTE: the user has to
     * actually delete the link for this to work. A deleted link can be detected by calling
     * IsLinkDestroyed() after EndNodeEditor().
    */
    public static final int EnableLinkCreationOnSnap = 1 << 1;
}
