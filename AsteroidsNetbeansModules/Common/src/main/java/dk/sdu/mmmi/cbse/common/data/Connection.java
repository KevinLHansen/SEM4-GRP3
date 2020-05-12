/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.common.data;

/**
 *
 * @author rasmusstamm
 */
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
