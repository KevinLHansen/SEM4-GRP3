package dk.sdu.mmmi.cbse.common.data;

// @author Group 3

// A connection between two nodes including the cost of moving from one the other

public class Connection<Node> {
    private Node fromNode;
    private Node toNode;
    private float cost;

    public Connection(Node fromNode, Node toNode, float cost) {
        this.fromNode = fromNode;
        this.toNode = toNode;
        this.cost = cost;
    }

    public Node getFromNode() {
        return fromNode;
    }

    public Node getToNode() {
        return toNode;
    }

    public float getCost() {
        return cost;
    }
}
