/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.common.enemy;

import com.badlogic.gdx.utils.Array;
import dk.sdu.mmmi.cbse.common.data.Connection;
import dk.sdu.mmmi.cbse.common.data.Heuristic;
import dk.sdu.mmmi.cbse.common.data.Node;
import dk.sdu.mmmi.cbse.common.data.Path;
import java.util.ArrayList;

/**
 *
 * @author rasmusstamm
 */
public class AStar {
    private Path fringe;
    private Node initial;
    private Node current;
    private Node goal;
    private PQHeap pqHeap;
    
    public Path doTheThing(Node initial, Node goal) {
        
        pqHeap = new PQHeap();
        
        Path path = new Path(initial, goal);
        
        //pqHeap.insert(path);
        
        while (!path.get(path.getNodeCount() - 1).equals(goal)) {
            System.out.println(path.getF());
            expand(path);
            path = pqHeap.extractMin();
        }
        
        System.out.println("poggers");
        
        return path;
        
    }
    
    private void expand(Path path) {
        
        Node node = path.get(path.getNodeCount() - 1);
        
        System.out.println("Expanding: " + node.getIndex());
        System.out.println("Cost: " + path.getTotalCost());
        //System.out.println("Node count: " + path.getNodeCount());
        
        for (Connection con : node.getConnections()) {
            Path newPath = new Path(path);
            if(!newPath.getNodes().contains((Node) con.getToNode(), false)){
                newPath.addConnection(con);
            
                pqHeap.insert(newPath);
            }
        }
    }
    
    private class PQHeap {
        private Array<Path> minHeap = new Array<Path>();
        
        public Path extractMin(){
            System.out.println("heapsize " + minHeap.size);
            Path min = minHeap.get(0);
            
            minHeap.set(0, minHeap.get(minHeap.size - 1));
            minHeap.removeIndex(minHeap.size - 1);
            
            minHeapify(0);
            
            return min;
        }
        
        public void insert(Path path) {
            minHeap.add(path);
            
            int i = minHeap.size - 1;
            
            while(i > 0 && minHeap.get(parent(i)).getF() > minHeap.get(i).getF()) {
                swap(i, parent(i));
                i = parent(i);
            }
        } 

        private void minHeapify(int i) {
            int l = left(i);
            int r = right(i);
            
            int smallest;
            
            if (l <= minHeap.size - 1 && minHeap.get(l).getF() < minHeap.get(i).getF()) {
                smallest = l;
            } else {
                smallest = i;
            }
            if (r <= minHeap.size - 1 && minHeap.get(r).getF() < minHeap.get(smallest).getF()) {
                smallest = r;
            }
            
            if (smallest != i) {
                swap(i, smallest);
                minHeapify(smallest);
            }
            
        }

        private int left(int i) {
            return 2 * i + 1; 
        }

        private int right(int i) {
            return 2 * i + 2;
        }
        
        private int parent(int i) {
            return (i-1) / 2;
        }

        private void swap(int a, int b) {
            Path temp;
            
            temp = minHeap.get(a);
            minHeap.set(a, minHeap.get(b));
            minHeap.set(b, temp);
        }
    }
}
