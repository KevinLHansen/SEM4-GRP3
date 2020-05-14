/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.common.data;

import com.badlogic.gdx.utils.Array;

/**
 *
 * @author rasmusstamm
 */
public class Path {
    private Array<Node> nodes = new Array<Node>();
    private Node goal;
    private float totalCost;

    public Path(Node current, Node goal) {
        nodes.add(current);
        this.goal = goal;
        totalCost = 0;
    }
    
    public Path(Path path) {
        this.goal = path.goal;
        this.nodes = new Array<Node>(path.getNodes());
        this.totalCost = path.getTotalCost();
    }
    
    public int getNodeCount(){
        return nodes.size;
    }

    public Array<Node> getNodes() {
        return nodes;
    }
    
    public void addNode(Node node){
        nodes.add(node);
    }
    
    public void addCost(float cost) {
        totalCost = totalCost + cost;
    }
    
    public void addConnection(Connection con) {
        nodes.add((Node) con.getToNode());
        totalCost = totalCost + con.getCost();
    }

    public float getTotalCost() {
        return totalCost;
    }
    
    public float getF() {
        return totalCost + Heuristic.getInstance().estimate(nodes.get(nodes.size - 1), goal);
    }
    
    public Node get(int i) {
        return nodes.get(i);
    }
    
    public Node remove(int i){
        return nodes.removeIndex(i);
    }
    
    public void set(int i, Node node) {
        nodes.set(i, node);
    }
    
    public void clear() {
        nodes.clear();
        totalCost = 0;
    }
    
}
