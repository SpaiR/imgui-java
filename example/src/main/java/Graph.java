import java.util.HashMap;
import java.util.Map;

public final class Graph {
    public int nextNodeId = 1;
    public int nextPinId = 100;

    public final Map<Integer, GraphNode> nodes = new HashMap<>();

    public Graph() {
        final GraphNode first = createGraphNode();
        final GraphNode second = createGraphNode();
        first.outputNodeId = second.nodeId;
    }

    public GraphNode createGraphNode() {
        final GraphNode node = new GraphNode(nextNodeId++, nextPinId++, nextPinId++);
        this.nodes.put(node.nodeId, node);
        return node;
    }

    public GraphNode findByInput(final long inputPinId) {
        for (GraphNode node : nodes.values()) {
            if (node.getInputPinId() == inputPinId) {
                return node;
            }
        }
        return null;
    }

    public GraphNode findByOutput(final long outputPinId) {
        for (GraphNode node : nodes.values()) {
            if (node.getOutputPinId() == outputPinId) {
                return node;
            }
        }
        return null;
    }

    public static final class GraphNode {
        public final int nodeId;
        public final int inputPinId;
        public final int outputPinId;

        public int outputNodeId = -1;

        public GraphNode(final int nodeId, final int inputPinId, final int outputPintId) {
            this.nodeId = nodeId;
            this.inputPinId = inputPinId;
            this.outputPinId = outputPintId;
        }

        public int getInputPinId() {
            return inputPinId;
        }

        public int getOutputPinId() {
            return outputPinId;
        }

        public String getName() {
            return "Node " + (char) (64 + nodeId);
        }
    }
}
